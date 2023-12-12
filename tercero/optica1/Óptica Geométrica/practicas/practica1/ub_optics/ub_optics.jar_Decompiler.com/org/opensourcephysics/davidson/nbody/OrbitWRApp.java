package org.opensourcephysics.davidson.nbody;

import org.opensourcephysics.ejs.control.EjsControlFrame;

public class OrbitWRApp extends OrbitApp {
   public void resetAnimation() {
      super.resetAnimation();
      if (super.control instanceof EjsControlFrame) {
         ((EjsControlFrame)super.control).loadDefaultXML();
      }

      this.initializeAnimation();
   }

   public static void main(String[] var0) {
      OrbitWRApp var1 = new OrbitWRApp();
      OrbitControl var2 = new OrbitControl(var1);
      var1.setControl(var2);
      var2.loadXML(var0);
      var1.initializeAnimation();
   }
}
