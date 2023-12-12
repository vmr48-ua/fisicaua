package org.opensourcephysics.davidson.qm;

import java.awt.Color;
import org.opensourcephysics.controls.AnimationControl;
import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.PlottingPanel;

public class QMSuperpositionProbabilityApp extends QMSuperpositionApp {
   Dataset rhoDataset = new Dataset();

   public QMSuperpositionProbabilityApp() {
      super.dataPanel = new PlottingPanel("x", "Psi*Psi", "Rho(x)");
      super.dataFrame = new DrawingFrame(super.dataPanel);
      this.rhoDataset.setConnected(true);
      this.rhoDataset.setMarkerShape(5);
      this.rhoDataset.setMarkerColor(Color.lightGray);
      super.dataPanel.limitAutoscaleY(0.0D, 0.1D);
      super.dataPanel.addDrawable(this.rhoDataset);
   }

   public void initializeAnimation() {
      super.initializeAnimation();
      double var1 = super.control.getDouble("rho range");
      super.dataPanel.limitAutoscaleY(0.0D, var1);
      super.superposition.getRho(this.rhoDataset);
      super.dataPanel.setMessage("t=" + super.decimalFormat.format(super.time));
      super.dataPanel.repaint();
   }

   public void doStep() {
      super.doStep();
      super.superposition.getRho(this.rhoDataset);
      super.dataPanel.setMessage("t=" + super.decimalFormat.format(super.time));
      super.dataPanel.render();
   }

   void setValues() {
      super.control.setValue("numpts", 300);
      super.control.setValue("dt", 0.1D);
      super.control.setValue("x min", -5);
      super.control.setValue("x max", 5);
      super.control.setValue("psi range", 0.1D);
      super.control.setValue("rho range", 0.004D);
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
      QMSuperpositionProbabilityApp var1 = new QMSuperpositionProbabilityApp();
      AnimationControl var2 = new AnimationControl(var1);
      var1.setControl(var2);
      var2.loadXML(var0);
   }
}
