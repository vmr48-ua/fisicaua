package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.controls.XML;

public class QMSuperpositionFFTWRApp extends QMSuperpositionFFTApp {
   public void resetAnimation() {
      super.resetAnimation();
      super.dataFrame.setVisible(true);
   }

   public static XML.ObjectLoader getLoader() {
      return new QMSuperpositionLoader();
   }

   public static void main(String[] var0) {
      QMSuperpositionFFTWRApp var1 = new QMSuperpositionFFTWRApp();
      new QMSuperpositionControl(var1, var0);
   }
}
