package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.controls.AnimationControl;

public class QMSuperpositionMomentumCarpetApp extends QMSuperpositionApp {
   MomentumCarpet carpet = new MomentumCarpet(100, 100);

   public QMSuperpositionMomentumCarpetApp() {
      super.dataFrame = this.carpet.frame;
      super.dataPanel = this.carpet.panel;
   }

   public void initializeAnimation() {
      super.initializeAnimation();
      if (super.control.getBoolean("hide frame")) {
         super.psiFrame.setKeepHidden(true);
      } else {
         super.psiFrame.setKeepHidden(false);
         super.psiFrame.setVisible(true);
      }

      int var1 = super.superposition.getNumpts();
      int var2 = super.control.getInt("numdt");
      double var3 = super.control.getDouble("p max");
      this.carpet.resize(var1, var2);
      this.carpet.setScale(super.superposition.getXMin(), super.superposition.getXMax(), super.time, super.time + super.dt * (double)var2);
      this.carpet.panel.setPreferredMinMaxX(-var3, var3);
      this.carpet.reset();
   }

   public void doStep() {
      super.doStep();
      this.carpet.addRow(super.superposition);
   }

   void setValues() {
      super.control.setValue("numpts", 300);
      super.control.setValue("numdt", 100);
      super.control.setValue("dt", 0.1D);
      super.control.setValue("x min", -5);
      super.control.setValue("x max", 5);
      super.control.setValue("psi range", 0.1D);
      super.control.setValue("p max", 2);
      super.control.setValue("re coef", "{0.707,0.707,0,0,0,0}");
      super.control.setValue("im coef", "{0,0,0,0,0,0}");
      super.control.setValue("V(x)", "x*x/2");
      super.control.setValue("energy scale", 1);
      super.control.setValue("psi range", 0.1D);
      super.control.setValue("time format", "0.00");
      super.control.setValue("style", "phase");
      super.control.setValue("hide frame", false);
   }

   public static void main(String[] var0) {
      QMSuperpositionMomentumCarpetApp var1 = new QMSuperpositionMomentumCarpetApp();
      AnimationControl var2 = new AnimationControl(var1);
      var1.setControl(var2);
      var2.loadXML(var0);
   }
}
