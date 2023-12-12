package org.opensourcephysics.displayejs;

import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Interactive;

public interface InteractionTarget extends Interactive {
   InteractionSource getSource();

   Point3D getHotspot(DrawingPanel var1);

   void updateHotspot(DrawingPanel var1, Point3D var2);
}
