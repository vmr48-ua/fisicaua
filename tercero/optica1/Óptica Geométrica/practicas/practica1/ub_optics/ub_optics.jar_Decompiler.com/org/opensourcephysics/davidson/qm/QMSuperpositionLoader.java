package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.controls.AnimationControl;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;

public class QMSuperpositionLoader implements XML.ObjectLoader {
   public Object createObject(XMLControl var1) {
      QMSuperpositionApp var2 = new QMSuperpositionApp();
      AnimationControl var3 = new AnimationControl(var2);
      var2.setControl(var3);
      return var2;
   }

   public void saveObject(XMLControl var1, Object var2) {
   }

   public Object loadObject(XMLControl var1, Object var2) {
      QMSuperpositionApp var3 = (QMSuperpositionApp)var2;
      var3.stopAnimation();
      var3.initializeAnimation();
      var3.psiFrame.setVisible(true);
      if (var3.dataFrame != null) {
         var3.dataFrame.setVisible(true);
      }

      return var2;
   }
}
