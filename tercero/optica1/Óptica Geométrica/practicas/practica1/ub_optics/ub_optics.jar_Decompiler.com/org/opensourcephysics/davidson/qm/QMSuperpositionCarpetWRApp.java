package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.controls.XML;

public class QMSuperpositionCarpetWRApp extends QMSuperpositionCarpetApp {
   public static XML.ObjectLoader getLoader() {
      return new QMSuperpositionLoader();
   }

   public static void main(String[] var0) {
      QMSuperpositionCarpetWRApp var1 = new QMSuperpositionCarpetWRApp();
      new QMSuperpositionControl(var1, var0);
   }
}
