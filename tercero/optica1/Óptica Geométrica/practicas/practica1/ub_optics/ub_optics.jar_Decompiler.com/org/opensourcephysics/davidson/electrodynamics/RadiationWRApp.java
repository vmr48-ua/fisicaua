package org.opensourcephysics.davidson.electrodynamics;

import org.opensourcephysics.ejs.control.EjsControlFrame;

public class RadiationWRApp extends RadiationApp {
   public void showB() {
      if (super.control.getBoolean("showBField")) {
         super.contourFrame.setVisible(true);
         super.contourFrame.repaint();
      } else {
         super.contourFrame.setVisible(false);
      }

   }

   public void resetAnimation() {
      super.resetAnimation();
      if (super.control instanceof EjsControlFrame) {
         ((EjsControlFrame)super.control).loadDefaultXML();
      }

      this.initializeAnimation();
   }

   public static void main(String[] var0) {
      RadiationWRApp var1 = new RadiationWRApp();
      RadiationControl var2 = new RadiationControl(var1);
      var1.setControl(var2);
      var2.loadXML(var0);
      var1.initializeAnimation();
   }
}
