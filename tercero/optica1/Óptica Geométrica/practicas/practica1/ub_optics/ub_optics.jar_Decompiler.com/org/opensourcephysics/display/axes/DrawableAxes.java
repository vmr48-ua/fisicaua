package org.opensourcephysics.display.axes;

import java.awt.Color;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.DrawingPanel;

public interface DrawableAxes extends Drawable {
   void setXLabel(String var1, String var2);

   String getXLabel();

   void setYLabel(String var1, String var2);

   String getYLabel();

   void setTitle(String var1, String var2);

   String getTitle();

   void setVisible(boolean var1);

   void setInteriorBackground(Color var1);

   void setShowMajorXGrid(boolean var1);

   void setShowMinorXGrid(boolean var1);

   void setShowMajorYGrid(boolean var1);

   void setShowMinorYGrid(boolean var1);

   void resizeFonts(double var1, DrawingPanel var3);
}
