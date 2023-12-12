package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.controls.XML;

public class QMSuperpositionProbabilityWRApp extends QMSuperpositionProbabilityApp {
   public static XML.ObjectLoader getLoader() {
      return new QMSuperpositionLoader();
   }

   public static void main(String[] var0) {
      QMSuperpositionProbabilityWRApp var1 = new QMSuperpositionProbabilityWRApp();
      new QMSuperpositionControl(var1, var0);
   }
}
