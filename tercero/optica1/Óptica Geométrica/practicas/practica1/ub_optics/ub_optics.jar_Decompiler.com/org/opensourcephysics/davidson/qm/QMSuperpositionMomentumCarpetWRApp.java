package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.controls.XML;

public class QMSuperpositionMomentumCarpetWRApp extends QMSuperpositionMomentumCarpetApp {
   public static XML.ObjectLoader getLoader() {
      return new QMSuperpositionLoader();
   }

   public static void main(String[] var0) {
      QMSuperpositionMomentumCarpetWRApp var1 = new QMSuperpositionMomentumCarpetWRApp();
      new QMSuperpositionControl(var1, var0);
   }
}
