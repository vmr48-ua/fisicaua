package org.opensourcephysics.controls;

import java.util.List;

public interface XMLProperty {
   String getPropertyName();

   String getPropertyType();

   Class getPropertyClass();

   XMLProperty getParentProperty();

   int getLevel();

   List getPropertyContent();

   XMLControl getChildControl(String var1);

   XMLControl[] getChildControls();

   void setValue(String var1);
}
