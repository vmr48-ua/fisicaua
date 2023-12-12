package org.opensourcephysics.numerics;

import java.text.DecimalFormat;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;

public class DoubleArray {
   public static int NumberFormatError = 1;
   public static int ArrayIndexOutOfBoundsError = 2;
   protected DecimalFormat format = new DecimalFormat("0.00");
   protected DecimalFormat formatExp = new DecimalFormat("0.00#E0");
   protected double[] array;
   protected String defaultString;
   protected double[] defaultArray;
   protected int errorcode = 0;

   public DoubleArray(int var1) {
      this.array = new double[var1];
      this.defaultArray = this.array;
   }

   public DoubleArray(double[] var1) {
      this.defaultArray = (double[])((double[])var1.clone());
      this.array = this.defaultArray;
   }

   public DoubleArray(String var1) throws NumberFormatException {
      this.array = this.toDouble(var1);
      this.defaultString = var1;
      this.defaultArray = this.array;
   }

   public void setDecimalFormat(String var1) {
      this.format = new DecimalFormat(var1);
      this.formatExp = this.format;
   }

   public String getDefault() {
      return this.defaultString;
   }

   public String toString() {
      if (this.errorcode > 0) {
         return this.defaultString;
      } else {
         String var1 = "{";
         int var2 = 0;

         for(int var3 = this.array.length; var2 < var3; ++var2) {
            var1 = var1 + (!(Math.abs(this.array[var2]) < 0.1D) && !(Math.abs(this.array[var2]) > 1000.0D) ? this.format.format(this.array[var2]) : this.formatExp.format(this.array[var2]));
            if (var2 < var3 - 1) {
               var1 = var1 + ", ";
            }
         }

         var1 = var1 + "}";
         return var1;
      }
   }

   public int getError() {
      return this.errorcode;
   }

   public double[] getArray(String var1) {
      this.set(var1);
      return this.array;
   }

   public double[] getArray() {
      return this.array;
   }

   public boolean set(String var1) {
      this.errorcode = 0;

      try {
         this.array = this.toDouble(var1);
         return true;
      } catch (NumberFormatException var3) {
         this.errorcode = NumberFormatError;
         this.array = this.toDouble(this.defaultString);
         return false;
      } catch (ArrayIndexOutOfBoundsException var4) {
         this.errorcode = ArrayIndexOutOfBoundsError;
         this.array = this.toDouble(this.defaultString);
         return false;
      }
   }

   public void setDefaultArray(double[] var1) {
      this.defaultArray = (double[])((double[])var1.clone());
      this.array = this.defaultArray;
   }

   protected double[] toDouble(String var1) throws ArrayIndexOutOfBoundsException {
      if (var1 == null) {
         var1 = "{}";
      }

      Object var2 = null;
      int var3 = var1.indexOf("{") + 1;
      int var4 = var1.indexOf("}");
      if (var4 - var3 <= 0) {
         this.errorcode = ArrayIndexOutOfBoundsError;
         return this.defaultArray;
      } else {
         String[] var5 = var1.substring(var3, var4).split(",");
         if (this.array != null && this.array.length != var5.length) {
            throw new ArrayIndexOutOfBoundsException("Array length cannot be changed in DoubleArray. " + var1);
         } else {
            double[] var10 = new double[var5.length];
            int var6 = 0;

            for(int var7 = var5.length; var6 < var7; ++var6) {
               try {
                  var10[var6] = Double.parseDouble(var5[var6]);
               } catch (NumberFormatException var9) {
                  this.errorcode = NumberFormatError;
               }
            }

            return var10;
         }
      }
   }

   public static XML.ObjectLoader getLoader() {
      return new DoubleArray.Loader();
   }

   static class Loader implements XML.ObjectLoader {
      public void saveObject(XMLControl var1, Object var2) {
         DoubleArray var3 = (DoubleArray)var2;
         var1.setValue("data", var3.getArray());
      }

      public Object createObject(XMLControl var1) {
         return new DoubleArray((double[])((double[])var1.getObject("data")));
      }

      public Object loadObject(XMLControl var1, Object var2) {
         DoubleArray var3 = (DoubleArray)var2;
         double[] var4 = (double[])((double[])var1.getObject("data"));
         var3.array = var4;
         var3.defaultArray = var4;
         return var2;
      }
   }
}
