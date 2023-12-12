package org.opensourcephysics.display2d;

import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLLoader;

public class ArrayData implements GridData {
   protected double[][][] data;
   protected double left;
   protected double right;
   protected double bottom;
   protected double top;
   protected double dx = 0.0D;
   protected double dy = 0.0D;
   protected boolean cellData = false;
   protected String[] names;

   public ArrayData(int var1, int var2, int var3) {
      if (var2 >= 1 && var1 >= 1) {
         if (var3 < 1) {
            throw new IllegalArgumentException("Number of 2d data components must be positive. Your ncomponents=" + var3);
         } else {
            this.data = new double[var3][var1][var2];
            this.setScale(0.0D, (double)var1, 0.0D, (double)var2);
            this.names = new String[var3];

            for(int var4 = 0; var4 < var3; ++var4) {
               this.names[var4] = "Component_" + var4;
            }

         }
      } else {
         throw new IllegalArgumentException("Number of dataset rows and columns must be positive. Your row=" + var2 + "  col=" + var1);
      }
   }

   public void setComponentName(int var1, String var2) {
      this.names[var1] = var2;
   }

   public String getComponentName(int var1) {
      return this.names[var1];
   }

   public int getComponentCount() {
      return this.data.length;
   }

   public void setScale(double var1, double var3, double var5, double var7) {
      this.cellData = false;
      this.left = var1;
      this.right = var3;
      this.bottom = var5;
      this.top = var7;
      int var9 = this.data[0].length;
      int var10 = this.data[0][0].length;
      this.dx = 0.0D;
      if (var9 > 1) {
         this.dx = (this.right - this.left) / (double)(var9 - 1);
      }

      this.dy = 0.0D;
      if (var10 > 1) {
         this.dy = (this.bottom - this.top) / (double)(var10 - 1);
      }

      if (this.dx == 0.0D) {
         this.left -= 0.5D;
         this.right += 0.5D;
      }

      if (this.dy == 0.0D) {
         this.bottom -= 0.5D;
         this.top += 0.5D;
      }

   }

   public boolean isCellData() {
      return this.cellData;
   }

   public double getValue(int var1, int var2, int var3) {
      return this.data[var3][var1][var2];
   }

   public void setValue(int var1, int var2, int var3, double var4) {
      this.data[var3][var1][var2] = var4;
   }

   public int getNx() {
      return this.data[0].length;
   }

   public int getNy() {
      return this.data[0][0].length;
   }

   public void setCellScale(double var1, double var3, double var5, double var7) {
      this.cellData = true;
      int var9 = this.data[0].length;
      int var10 = this.data[0][0].length;
      this.dx = 0.0D;
      if (var9 > 1) {
         this.dx = (var3 - var1) / (double)var9;
      }

      this.dy = 0.0D;
      if (var10 > 1) {
         this.dy = (var5 - var7) / (double)var10;
      }

      this.left = var1 + this.dx / 2.0D;
      this.right = var3 - this.dx / 2.0D;
      this.bottom = var5 - this.dy / 2.0D;
      this.top = var7 + this.dy / 2.0D;
   }

   public void setCenteredCellScale(double var1, double var3, double var5, double var7) {
      int var9 = this.data[0].length;
      int var10 = this.data[0][0].length;
      double var11 = var9 > 1 ? (var3 - var1) / (double)(var9 - 1) / 2.0D : 0.0D;
      var1 -= var11;
      var3 += var11;
      var11 = var10 > 1 ? (var7 - var5) / (double)(var10 - 1) / 2.0D : 0.0D;
      var5 -= var11;
      var7 += var11;
      this.setCellScale(var1, var3, var5, var7);
   }

   public double interpolate(double var1, double var3, int var5) {
      int var6 = (int)((var1 - this.left) / this.dx);
      var6 = Math.max(0, var6);
      var6 = Math.min(this.data[0].length - 2, var6);
      int var7 = -((int)((this.top - var3) / this.dy));
      var7 = Math.max(0, var7);
      var7 = Math.min(this.data[0][0].length - 2, var7);
      double var8 = (var1 - this.left) / this.dx - (double)var6;
      double var10 = -(this.top - var3) / this.dy - (double)var7;
      if (var6 < 0) {
         return (1.0D - var10) * this.data[var5][0][var7] + var10 * this.data[var5][0][var7 + 1];
      } else {
         return var7 < 0 ? (1.0D - var8) * this.data[var5][var6][0] + var8 * this.data[var5][var6 + 1][0] : (1.0D - var8) * (1.0D - var10) * this.data[var5][var6][var7] + var8 * (1.0D - var10) * this.data[var5][var6 + 1][var7] + var8 * var10 * this.data[var5][var6 + 1][var7 + 1] + (1.0D - var8) * var10 * this.data[var5][var6][var7 + 1];
      }
   }

   public double[] interpolate(double var1, double var3, int[] var5, double[] var6) {
      int var7 = (int)((var1 - this.left) / this.dx);
      var7 = Math.max(0, var7);
      var7 = Math.min(this.data[0].length - 2, var7);
      int var8 = -((int)((this.top - var3) / this.dy));
      var8 = Math.max(0, var8);
      var8 = Math.min(this.data[0][0].length - 2, var8);
      if (var7 < 0 && var8 < 0) {
         int var16 = 0;

         for(int var10 = var5.length; var16 < var10; ++var16) {
            var6[var16] = this.data[var5[var16]][0][0];
         }

         return var6;
      } else {
         double var9;
         int var12;
         int var17;
         if (var7 < 0) {
            var9 = -(this.top - var3) / this.dy - (double)var8;
            var17 = 0;

            for(var12 = var5.length; var17 < var12; ++var17) {
               var6[var17] = (1.0D - var9) * this.data[var5[var17]][0][var8] + var9 * this.data[var5[var17]][0][var8 + 1];
            }

            return var6;
         } else if (var8 < 0) {
            var9 = (var1 - this.left) / this.dx - (double)var7;
            var17 = 0;

            for(var12 = var5.length; var17 < var12; ++var17) {
               var6[var17] = (1.0D - var9) * this.data[var5[var17]][var7][0] + var9 * this.data[var5[var17]][var7 + 1][0];
            }

            return var6;
         } else {
            var9 = (var1 - this.left) / this.dx - (double)var7;
            double var11 = -(this.top - var3) / this.dy - (double)var8;
            int var13 = 0;

            for(int var14 = var5.length; var13 < var14; ++var13) {
               int var15 = var5[var13];
               var6[var13] = (1.0D - var9) * (1.0D - var11) * this.data[var15][var7][var8] + var9 * (1.0D - var11) * this.data[var15][var7 + 1][var8] + var9 * var11 * this.data[var15][var7 + 1][var8 + 1] + (1.0D - var9) * var11 * this.data[var15][var7][var8 + 1];
            }

            return var6;
         }
      }
   }

   public double[][][] getData() {
      return this.data;
   }

   public double[] getZRange(int var1) {
      double var2 = this.data[var1][0][0];
      double var4 = var2;
      int var6 = 0;

      for(int var7 = this.data[0].length; var6 < var7; ++var6) {
         int var8 = 0;

         for(int var9 = this.data[0][0].length; var8 < var9; ++var8) {
            double var10 = this.data[var1][var6][var8];
            if (var10 > var4) {
               var4 = var10;
            }

            if (var10 < var2) {
               var2 = var10;
            }
         }
      }

      return new double[]{var2, var4};
   }

   public final double getLeft() {
      return this.left;
   }

   public final double getRight() {
      return this.right;
   }

   public final double getTop() {
      return this.top;
   }

   public final double getBottom() {
      return this.bottom;
   }

   public final double getDx() {
      return this.dx;
   }

   public final double getDy() {
      return this.dy;
   }

   public double indexToX(int var1) {
      return this.data == null ? Double.NaN : this.left + this.dx * (double)var1;
   }

   public double indexToY(int var1) {
      return this.data == null ? Double.NaN : this.top + this.dy * (double)var1;
   }

   public int xToIndex(double var1) {
      if (this.data == null) {
         return 0;
      } else {
         int var3 = this.getNx();
         double var4 = (this.right - this.left) / (double)var3;
         int var6 = (int)((var1 - this.left) / var4);
         if (var6 < 0) {
            return 0;
         } else {
            return var6 >= var3 ? var3 - 1 : var6;
         }
      }
   }

   public int yToIndex(double var1) {
      if (this.data == null) {
         return 0;
      } else {
         int var3 = this.getNy();
         double var4 = (this.top - this.bottom) / (double)var3;
         int var6 = (int)((this.top - var1) / var4);
         if (var6 < 0) {
            return 0;
         } else {
            return var6 >= var3 ? var3 - 1 : var6;
         }
      }
   }

   public static XML.ObjectLoader getLoader() {
      return new ArrayData.Loader();
   }

   private static class Loader extends XMLLoader {
      private Loader() {
      }

      public void saveObject(XMLControl var1, Object var2) {
         ArrayData var3 = (ArrayData)var2;
         var1.setValue("left", var3.left);
         var1.setValue("right", var3.right);
         var1.setValue("bottom", var3.bottom);
         var1.setValue("top", var3.top);
         var1.setValue("dx", var3.dx);
         var1.setValue("dy", var3.dy);
         var1.setValue("is cell data", var3.cellData);
         var1.setValue("data", var3.data);
      }

      public Object createObject(XMLControl var1) {
         return new ArrayData(1, 1, 1);
      }

      public Object loadObject(XMLControl var1, Object var2) {
         ArrayData var3 = (ArrayData)var2;
         double[][][] var4 = (double[][][])((double[][][])var1.getObject("data"));
         var3.data = var4;
         var3.left = var1.getDouble("left");
         var3.right = var1.getDouble("right");
         var3.bottom = var1.getDouble("bottom");
         var3.top = var1.getDouble("top");
         var3.dx = var1.getDouble("dx");
         var3.dy = var1.getDouble("dy");
         var3.cellData = var1.getBoolean("is cell data");
         return var2;
      }

      // $FF: synthetic method
      Loader(Object var1) {
         this();
      }
   }
}
