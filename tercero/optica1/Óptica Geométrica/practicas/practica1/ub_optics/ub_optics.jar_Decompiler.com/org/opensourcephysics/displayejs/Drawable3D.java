package org.opensourcephysics.displayejs;

import java.awt.Graphics2D;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.DrawingPanel;

public interface Drawable3D extends Drawable {
   Object3D[] getObjects3D(DrawingPanel3D var1);

   void draw(DrawingPanel3D var1, Graphics2D var2, int var3);

   void drawQuickly(DrawingPanel3D var1, Graphics2D var2);

   void needsToProject(DrawingPanel var1);
}
