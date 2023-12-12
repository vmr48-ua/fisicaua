package org.opensourcephysics.display2d;

import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLLoader;

public class GridPointData implements GridData {
   protected double[][][] data;
   protected double left;
   protected double right;
   protected double bottom;
   protected double top;
   protected double dx = 0.0D;
   protected double dy = 0.0D;
   protected boolean cellData = false;
   protected String[] names;

   public GridPointData(int var1, int var2, int var3) {
      if (var2 >= 1 && var1 >= 1) {
         if (var3 < 1) {
            throw new IllegalArgumentException("Number of 2d data components must be positive. Your ncomponents=" + var3);
         } else {
            this.data = new double[var1][var2][var3 + 2];
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

   public GridPointData createGridPointData(int var1) {
      GridPointData var2 = new GridPointData(this.data.length, this.data[0].length, var1 + 2);
      var2.setScale(this.left, this.right, this.bottom, this.top);
      return var2;
   }

   public void setComponentName(int var1, String var2) {
      this.names[var1] = var2;
   }

   public String getComponentName(int var1) {
      return this.names[var1];
   }

   public int getComponentCount() {
      return this.data[0][0].length - 2;
   }

   public void setScale(double var1, double var3, double var5, double var7) {
      this.cellData = false;
      this.left = var1;
      this.right = var3;
      this.bottom = var5;
      this.top = var7;
      int var9 = this.data.length;
      int var10 = this.data[0].length;
      this.dx = 0.0D;
      if (var9 > 1) {
         this.dx = (this.right - this.left) / (double)(var9 - 1);
      }

      this.dy = 0.0D;
      if (var10 > 1) {
         this.dy = (this.bottom - this.top) / (double)(var10 - 1);
      }

      double var11 = this.left;

      for(int var13 = 0; var13 < var9; ++var13) {
         double var14 = this.top;

         for(int var16 = 0; var16 < var10; ++var16) {
            this.data[var13][var16][0] = var11;
            this.data[var13][var16][1] = var14;
            var14 += this.dy;
         }

         var11 += this.dx;
      }

   }

   public void setCellScale(double var1, double var3, double var5, double var7) {
      this.cellData = true;
      int var9 = this.data.length;
      int var10 = this.data[0].length;
      this.dx = 0.0D;
      if (var9 > 1) {
         this.dx = (var3 - var1) / (double)var9;
      }

      this.dy = 0.0D;
      if (var10 > 1) {
         this.dy = (var5 - var7) / (double)var10;
      }

      double var11 = var1 + this.dx / 2.0D;

      for(int var13 = 0; var13 < var9; ++var13) {
         double var14 = var7 + this.dy / 2.0D;

         for(int var16 = 0; var16 < var10; ++var16) {
            this.data[var13][var16][0] = var11;
            this.data[var13][var16][1] = var14;
            var14 += this.dy;
         }

         var11 += this.dx;
      }

      this.left = var1 + this.dx / 2.0D;
      this.right = var3 - this.dx / 2.0D;
      this.bottom = var5 - this.dy / 2.0D;
      this.top = var7 + this.dy / 2.0D;
   }

   public void setCenteredCellScale(double var1, double var3, double var5, double var7) {
      int var9 = this.data.length;
      int var10 = this.data[0].length;
      double var11 = var9 > 1 ? (var3 - var1) / (double)(var9 - 1) / 2.0D : 0.0D;
      var1 -= var11;
      var3 += var11;
      var11 = var10 > 1 ? (var7 - var5) / (double)(var10 - 1) / 2.0D : 0.0D;
      var5 -= var11;
      var7 += var11;
      this.setCellScale(var1, var3, var5, var7);
   }

   public boolean isCellData() {
      return this.cellData;
   }

   public double getValue(int var1, int var2, int var3) {
      return this.data[var1][var2][var3 + 2];
   }

   public void setValue(int var1, int var2, int var3, double var4) {
      this.data[var1][var2][var3 + 2] = var4;
   }

   public int getNx() {
      return this.data.length;
   }

   public int getNy() {
      return this.data[0].length;
   }

   public double[] getZRange(int var1) {
      int var2 = 2 + var1;
      double var3 = this.data[0][0][var2];
      double var5 = var3;
      int var7 = 0;

      for(int var8 = this.data.length; var7 < var8; ++var7) {
         int var9 = 0;

         for(int var10 = this.data[0].length; var9 < var10; ++var9) {
            double var11 = this.data[var7][var9][var2];
            if (var11 > var5) {
               var5 = var11;
            }

            if (var11 < var3) {
               var3 = var11;
            }
         }
      }

      return new double[]{var3, var5};
   }

   public double[] getVertex(double var1, double var3) {
      int var5 = (int)Math.floor((var1 - this.left) / this.dx);
      var5 = Math.max(0, var5);
      var5 = Math.min(var5, this.data.length - 1);
      int var6 = (int)Math.floor(-(this.top - var3) / this.dy);
      var6 = Math.max(0, var6);
      var6 = Math.min(var6, this.data[0].length - 1);
      return this.data[var5][var6];
   }

   public double interpolate(double var1, double var3, int var5) {
      int var6 = (int)((var1 - this.data[0][0][0]) / this.dx);
      var6 = Math.max(0, var6);
      var6 = Math.min(this.data.length - 2, var6);
      int var7 = (int)((var3 - this.data[0][0][1]) / this.dy);
      var7 = Math.max(0, var7);
      var7 = Math.min(this.data[0].length - 2, var7);
      double var8 = (var1 - this.data[var6][var7][0]) / this.dx;
      double var10 = (var3 - this.data[var6][var7][1]) / this.dy;
      var5 += 2;
      return (1.0D - var8) * (1.0D - var10) * this.data[var6][var7][var5] + var8 * (1.0D - var10) * this.data[var6 + 1][var7][var5] + var8 * var10 * this.data[var6 + 1][var7 + 1][var5] + (1.0D - var8) * var10 * this.data[var6][var7 + 1][var5];
   }

   public double[] interpolate(double var1, double var3, int[] var5, double[] var6) {
      int var7 = (int)((var1 - this.data[0][0][0]) / this.dx);
      var7 = Math.max(0, var7);
      var7 = Math.min(this.data.length - 2, var7);
      int var8 = (int)((var3 - this.data[0][0][1]) / this.dy);
      var8 = Math.max(0, var8);
      var8 = Math.min(this.data[0].length - 2, var8);
      double var9 = (var1 - this.data[var7][var8][0]) / this.dx;
      double var11 = (var3 - this.data[var7][var8][1]) / this.dy;
      int var13 = 0;

      for(int var14 = var5.length; var13 < var14; ++var13) {
         int var15 = var5[var13] + 2;
         var6[var13] = (1.0D - var9) * (1.0D - var11) * this.data[var7][var8][var15] + var9 * (1.0D - var11) * this.data[var7 + 1][var8][var15] + var9 * var11 * this.data[var7 + 1][var8 + 1][var15] + (1.0D - var9) * var11 * this.data[var7][var8 + 1][var15];
      }

      return var6;
   }

   public double[][][] getData() {
      return this.data;
   }

   public void setData(double[][][] var1) {
      this.data = var1;
      int var2 = this.data.length - 1;
      int var3 = this.data[0].length - 1;
      this.left = this.data[0][0][0];
      this.right = this.data[var2][var3][0];
      this.top = this.data[0][0][1];
      this.bottom = this.data[var2][var3][1];
      this.dx = (this.right - this.left) / (double)var2;
      this.dy = (this.bottom - this.top) / (double)var3;
      this.cellData = false;
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
      return this.data == null ? Double.NaN : this.data[var1][0][0];
   }

   public double indexToY(int var1) {
      return this.data == null ? Double.NaN : this.data[0][var1][1];
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
      return new GridPointData.Loader();
   }

   private static class Loader extends XMLLoader {
      private Loader() {
      }

      public void saveObject(XMLControl var1, Object var2) {
         GridPointData var3 = (GridPointData)var2;
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
         return new GridPointData(1, 1, 1);
      }

      public Object loadObject(XMLControl var1, Object var2) {
         GridPointData var3 = (GridPointData)var2;
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
