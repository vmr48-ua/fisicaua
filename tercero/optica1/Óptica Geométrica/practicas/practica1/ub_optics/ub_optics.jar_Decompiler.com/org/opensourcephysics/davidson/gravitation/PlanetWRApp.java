package org.opensourcephysics.davidson.gravitation;

import org.opensourcephysics.ejs.control.EjsControlFrame;

public class PlanetWRApp extends PlanetApp {
   public void resetAnimation() {
      super.resetAnimation();
      if (super.control instanceof EjsControlFrame) {
         ((EjsControlFrame)super.control).loadDefaultXML();
      }

      this.initializeAnimation();
   }

   public static void main(String[] var0) {
      PlanetApp var1 = new PlanetApp();
      PlanetControl var2 = new PlanetControl(var1);
      var1.setControl(var2);
      var2.loadXML(var0);
      var1.initializeAnimation();
   }
}
