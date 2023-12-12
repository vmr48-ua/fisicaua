package org.opensourcephysics.display2d;

public interface GridData {
   double interpolate(double var1, double var3, int var5);

   double[] interpolate(double var1, double var3, int[] var5, double[] var6);

   void setScale(double var1, double var3, double var5, double var7);

   void setCellScale(double var1, double var3, double var5, double var7);

   void setCenteredCellScale(double var1, double var3, double var5, double var7);

   boolean isCellData();

   void setComponentName(int var1, String var2);

   String getComponentName(int var1);

   int getComponentCount();

   double getValue(int var1, int var2, int var3);

   void setValue(int var1, int var2, int var3, double var4);

   int getNx();

   int getNy();

   double[][][] getData();

   double[] getZRange(int var1);

   double getLeft();

   double getRight();

   double getTop();

   double getBottom();

   double getDx();

   double getDy();

   double indexToX(int var1);

   double indexToY(int var1);

   int xToIndex(double var1);

   int yToIndex(double var1);
}
