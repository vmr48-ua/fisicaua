package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.ejs.control.EjsControlFrame;

public class QMSuperpositionWRApp extends QMSuperpositionApp {
   public void resetAnimation() {
      super.resetAnimation();
      if (super.control instanceof EjsControlFrame) {
         ((EjsControlFrame)super.control).loadDefaultXML();
      }

      this.initializeAnimation();
   }

   public static void main(String[] var0) {
      QMSuperpositionWRApp var1 = new QMSuperpositionWRApp();
      new QMSuperpositionControl(var1, var0);
   }
}
