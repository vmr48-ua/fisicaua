package org.opensourcephysics.display2d;

public class ComplexCarpet extends ComplexInterpolatedPlot {
   public ComplexCarpet(GridData var1) {
      super(var1);
      this.setShowGridLines(false);
   }

   public void clearData() {
      double[][][] var1;
      int var2;
      int var3;
      int var4;
      int var5;
      if (super.griddata instanceof ArrayData) {
         var1 = super.griddata.getData();
         var2 = 0;

         for(var3 = super.griddata.getNx(); var2 < var3; ++var2) {
            var4 = 0;

            for(var5 = super.griddata.getNy(); var4 < var5; ++var4) {
               var1[0][var2][var4] = 0.0D;
               var1[1][var2][var4] = 0.0D;
               var1[2][var2][var4] = 0.0D;
            }
         }
      } else {
         var1 = super.griddata.getData();
         var2 = 0;

         for(var3 = super.griddata.getNx(); var2 < var3; ++var2) {
            var4 = 0;

            for(var5 = super.griddata.getNy(); var4 < var5; ++var4) {
               var1[var3][var5][2] = 0.0D;
               var1[var3][var5][3] = 0.0D;
               var1[var3][var5][4] = 0.0D;
            }
         }
      }

      this.update();
   }

   public void setTopRow(double[][] var1) {
      if (super.image != null) {
         if (super.rgbData[0].length == super.image.getWidth() * super.image.getHeight()) {
            int var4;
            int var5;
            int var6;
            if (super.griddata instanceof ArrayData) {
               for(int var2 = 0; var2 < var1.length; ++var2) {
                  double[][] var3 = super.griddata.getData()[var2];
                  var4 = var3[0].length - 1;
                  var5 = 0;

                  for(var6 = var3.length; var5 < var6; ++var5) {
                     System.arraycopy(var3[var5], 0, var3[var5], 1, var4);
                     var3[var5][0] = var1[var2][var5];
                  }
               }
            } else {
               double[][][] var21 = super.griddata.getData();
               int var23 = 0;

               for(var4 = var21.length; var23 < var4; ++var23) {
                  var5 = var1.length;
                  var6 = var21[0].length - 1;

                  for(int var7 = var6; var7 > 0; --var7) {
                     System.arraycopy(var21[var23][var7 - 1], 2, var21[var23][var7], 2, var5);
                  }

                  for(var6 = 0; var6 < var5; ++var6) {
                     var21[var23][0][2 + var6] = var1[var6][var23];
                  }
               }
            }

            double var22 = (super.griddata.getBottom() - super.griddata.getTop()) / (double)(super.image.getHeight() - 1);
            var4 = 1 + (int)Math.abs(super.griddata.getDy() / var22);
            var5 = var4 * super.image.getWidth();
            var6 = super.rgbData[0].length - var5;
            System.arraycopy(super.rgbData[0], 0, super.rgbData[0], var5, var6);
            System.arraycopy(super.rgbData[1], 0, super.rgbData[1], var5, var6);
            System.arraycopy(super.rgbData[2], 0, super.rgbData[2], var5, var6);
            double[] var24 = new double[3];
            int[] var8 = new int[]{super.ampIndex, super.reIndex, super.imIndex};
            byte[] var9 = new byte[3];
            double var10 = super.griddata.getTop();
            double var12 = (super.griddata.getRight() - super.griddata.getLeft()) / (double)(super.image.getWidth() - 1);
            int var14 = 0;

            for(int var15 = super.image.getHeight(); var14 < var4; ++var14) {
               double var16 = super.griddata.getLeft();
               int var18 = 0;

               for(int var19 = super.image.getWidth(); var18 < var19; ++var18) {
                  super.colorMap.samplesToComponents(super.griddata.interpolate(var16, var10, var8, var24), var9);
                  int var20 = var22 < 0.0D ? var14 * var19 + var18 : (var15 - var14 - 1) * var19 + var18;
                  super.rgbData[0][var20] = var9[0];
                  super.rgbData[1][var20] = var9[1];
                  super.rgbData[2][var20] = var9[2];
                  var16 += var12;
               }

               var10 += var22;
            }

         }
      }
   }

   public void setAutoscaleZ(boolean var1, double var2, double var4) {
      this.setAutoscaleZ(var1, var4);
   }
}
