package org.opensourcephysics.display;

import java.awt.Color;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLLoader;

public class CircleLoader extends XMLLoader {
   public void saveObject(XMLControl var1, Object var2) {
      Circle var3 = (Circle)var2;
      var1.setValue("x", var3.x);
      var1.setValue("y", var3.y);
      var1.setValue("drawing r", var3.pixRadius);
      var1.setValue("color", var3.color);
   }

   public Object createObject(XMLControl var1) {
      return new Circle();
   }

   public Object loadObject(XMLControl var1, Object var2) {
      Circle var3 = (Circle)var2;
      var3.x = var1.getDouble("x");
      var3.y = var1.getDouble("y");
      int var4 = var1.getInt("drawing r");
      var3.pixRadius = var4 == 0 ? 6 : var4;
      var3.color = (Color)var1.getObject("color");
      return var2;
   }
}
