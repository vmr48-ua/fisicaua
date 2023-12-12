package org.opensourcephysics.displayejs;

import org.opensourcephysics.controls.Control;
import org.opensourcephysics.display.Interactive;

public interface InteractiveElement extends Drawable3D, InteractionSource, Interactive, Measurable3D {
   int TARGET_POSITION = 0;
   int TARGET_SIZE = 1;

   void setX(double var1);

   double getX();

   void setY(double var1);

   double getY();

   void setZ(double var1);

   double getZ();

   void setXY(double var1, double var3);

   void setXYZ(double var1, double var3, double var5);

   void setSizeX(double var1);

   double getSizeX();

   void setSizeY(double var1);

   double getSizeY();

   void setSizeZ(double var1);

   double getSizeZ();

   void setSizeXY(double var1, double var3);

   void setSizeXYZ(double var1, double var3, double var5);

   void setVisible(boolean var1);

   boolean isVisible();

   Style getStyle();

   void setResolution(Resolution var1);

   Resolution getResolution();

   void setGroup(Group var1);

   Group getGroup();

   void setGroupEnabled(boolean var1);

   boolean isGroupEnabled();

   void setGroupEnabled(int var1, boolean var2);

   boolean isGroupEnabled(int var1);

   void setSet(ElementSet var1, int var2);

   ElementSet getSet();

   int getSetIndex();

   void setControl(Control var1);

   Control getControl();

   String toXML();

   void copyFrom(InteractiveElement var1);

   void setDataObject(Object var1);

   Object getDataObject();
}
