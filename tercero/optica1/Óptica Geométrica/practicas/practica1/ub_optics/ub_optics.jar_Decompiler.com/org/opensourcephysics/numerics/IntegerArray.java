package org.opensourcephysics.numerics;

import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;

public class IntegerArray {
   public static int NumberFormatError = 1;
   public static int ArrayIndexOutOfBoundsError = 2;
   protected int[] array;
   protected String defaultString;
   protected int[] defaultArray;
   protected int errorcode = 0;

   public IntegerArray(int var1) {
      this.array = new int[var1];
      this.defaultArray = this.array;
   }

   public IntegerArray(int[] var1) {
      this.defaultArray = (int[])((int[])var1.clone());
      this.array = this.defaultArray;
   }

   public IntegerArray(String var1) throws NumberFormatException {
      this.array = this.toInteger(var1);
      this.defaultString = var1;
      this.defaultArray = this.array;
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
            var1 = var1 + Integer.toString(this.array[var2]);
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

   public int[] getArray(String var1) {
      this.set(var1);
      return this.array;
   }

   public int[] getArray() {
      return this.array;
   }

   public boolean set(String var1) {
      this.errorcode = 0;

      try {
         this.array = this.toInteger(var1);
         return true;
      } catch (NumberFormatException var3) {
         this.errorcode = NumberFormatError;
         this.array = this.toInteger(this.defaultString);
         return false;
      } catch (ArrayIndexOutOfBoundsException var4) {
         this.errorcode = ArrayIndexOutOfBoundsError;
         this.array = this.toInteger(this.defaultString);
         return false;
      }
   }

   public void setDefaultArray(int[] var1) {
      this.defaultArray = (int[])((int[])var1.clone());
      this.array = this.defaultArray;
   }

   protected int[] toInteger(String var1) throws ArrayIndexOutOfBoundsException {
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
            int[] var10 = new int[var5.length];
            int var6 = 0;

            for(int var7 = var5.length; var6 < var7; ++var6) {
               try {
                  var10[var6] = Integer.parseInt(var5[var6].trim());
               } catch (NumberFormatException var9) {
                  var10[var6] = 0;
                  this.errorcode = NumberFormatError;
               }
            }

            return var10;
         }
      }
   }

   public static XML.ObjectLoader getLoader() {
      return new IntegerArray.Loader();
   }

   static class Loader implements XML.ObjectLoader {
      public void saveObject(XMLControl var1, Object var2) {
         IntegerArray var3 = (IntegerArray)var2;
         var1.setValue("data", var3.getArray());
      }

      public Object createObject(XMLControl var1) {
         return new IntegerArray((int[])((int[])var1.getObject("data")));
      }

      public Object loadObject(XMLControl var1, Object var2) {
         IntegerArray var3 = (IntegerArray)var2;
         int[] var4 = (int[])((int[])var1.getObject("data"));
         var3.array = var4;
         var3.defaultArray = var4;
         return var2;
      }
   }
}
