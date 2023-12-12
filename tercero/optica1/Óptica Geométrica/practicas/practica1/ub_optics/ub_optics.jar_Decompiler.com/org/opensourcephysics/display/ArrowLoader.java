package org.opensourcephysics.display;

import java.awt.Color;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLLoader;

public class ArrowLoader extends XMLLoader {
   public void saveObject(XMLControl var1, Object var2) {
      Arrow var3 = (Arrow)var2;
      var1.setValue("x", var3.x);
      var1.setValue("y", var3.y);
      var1.setValue("a", var3.a);
      var1.setValue("b", var3.b);
      var1.setValue("head size", (double)var3.headSize);
      var1.setValue("color", var3.color);
   }

   public Object createObject(XMLControl var1) {
      return new Arrow(0.0D, 0.0D, 0.0D, 0.0D);
   }

   public Object loadObject(XMLControl var1, Object var2) {
      Arrow var3 = (Arrow)var2;
      var3.x = var1.getDouble("x");
      var3.y = var1.getDouble("y");
      var3.a = var1.getDouble("a");
      var3.b = var1.getDouble("b");
      var3.headSize = (float)var1.getDouble("head size");
      var3.color = (Color)var1.getObject("color");
      return var2;
   }
}
