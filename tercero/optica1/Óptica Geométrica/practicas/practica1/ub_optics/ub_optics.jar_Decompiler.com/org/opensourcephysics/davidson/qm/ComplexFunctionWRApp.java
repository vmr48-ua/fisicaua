package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.ejs.control.EjsControlFrame;

public class ComplexFunctionWRApp extends ComplexFunctionApp {
   public void resetAnimation() {
      super.plotPanel.setVisible(false);
      super.resetAnimation();
      if (super.control instanceof EjsControlFrame) {
         ((EjsControlFrame)super.control).loadDefaultXML();
      }

      this.initializeAnimation();
   }

   public void initializeAnimation() {
      super.plotPanel.setVisible(false);
      super.initializeAnimation();
      this.plotFunction();
      super.plotPanel.setVisible(true);
      super.plotPanel.repaint();
      if (super.control instanceof EjsControlFrame) {
         ((EjsControlFrame)super.control).getFrame().setTitle(super.control.getString("title"));
      }

   }

   void refresh() {
   }

   public static void main(String[] var0) {
      ComplexFunctionWRApp var1 = new ComplexFunctionWRApp();
      ComplexFunctionControl var2 = new ComplexFunctionControl(var1);
      var1.setControl(var2);
      var2.loadXML(var0);
      var1.initializeAnimation();
   }
}
