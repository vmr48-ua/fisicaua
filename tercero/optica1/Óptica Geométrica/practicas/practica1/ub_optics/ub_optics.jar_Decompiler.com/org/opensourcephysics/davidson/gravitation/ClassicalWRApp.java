package org.opensourcephysics.davidson.gravitation;

import org.opensourcephysics.ejs.control.EjsControlFrame;

public class ClassicalWRApp extends ClassicalApp {
   public void resetAnimation() {
      super.resetAnimation();
      if (super.control instanceof EjsControlFrame) {
         ((EjsControlFrame)super.control).loadDefaultXML();
      }

      this.initializeAnimation();
   }

   public static void main(String[] var0) {
      ClassicalWRApp var1 = new ClassicalWRApp();
      ClassicalControl var2 = new ClassicalControl(var1);
      var1.setControl(var2);
      var2.loadXML(var0);
      var1.initializeAnimation();
   }
}
