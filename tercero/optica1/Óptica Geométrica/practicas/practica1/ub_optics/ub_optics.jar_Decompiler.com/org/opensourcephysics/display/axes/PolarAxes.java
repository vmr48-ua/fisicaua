package org.opensourcephysics.display.axes;

import java.awt.Color;
import java.awt.Graphics;
import org.opensourcephysics.display.DrawingPanel;

public interface PolarAxes extends DrawableAxes {
   void setDeltaR(double var1);

   void autoscaleDeltaR(boolean var1);

   double getDeltaR();

   void setDeltaTheta(double var1);

   double getDeltaTheta();

   void drawRings(double var1, DrawingPanel var3, Graphics var4);

   void drawSpokes(double var1, DrawingPanel var3, Graphics var4);

   void setInteriorBackground(Color var1);
}
