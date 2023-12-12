package org.opensourcephysics.display2d;

import java.awt.Color;
import javax.swing.JFrame;
import org.opensourcephysics.display.Measurable;

public interface Plot2D extends Measurable {
   void setAll(Object var1);

   void setAll(Object var1, double var2, double var4, double var6, double var8);

   void setGridData(GridData var1);

   GridData getGridData();

   double indexToX(int var1);

   double indexToY(int var1);

   int xToIndex(double var1);

   int yToIndex(double var1);

   boolean isAutoscaleZ();

   double getFloor();

   double getCeiling();

   void setAutoscaleZ(boolean var1, double var2, double var4);

   void setFloorCeilColor(Color var1, Color var2);

   void setColorPalette(Color[] var1);

   void setPaletteType(int var1);

   void setGridLineColor(Color var1);

   void setShowGridLines(boolean var1);

   JFrame showLegend();

   void setVisible(boolean var1);

   void setIndexes(int[] var1);

   void update();
}
