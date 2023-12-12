package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.controls.AnimationControl;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.PlottingPanel;
import org.opensourcephysics.display.Stripchart;

public class QMSuperpositionExpectationXApp extends QMSuperpositionApp {
   Stripchart expectationDataset = new Stripchart(1.0D, 1.0D);

   public QMSuperpositionExpectationXApp() {
      super.dataPanel = new PlottingPanel("t", "<x>", "Position Expectation");
      super.dataFrame = new DrawingFrame(super.dataPanel);
   }

   public void initializeAnimation() {
      super.initializeAnimation();
      double var1 = super.control.getDouble("time domain");
      this.expectationDataset = new Stripchart(var1, super.superposition.getXMax() - super.superposition.getXMin());
      this.expectationDataset.enable(super.control.getBoolean("stripchart"));
      this.expectationDataset.setConnected(true);
      this.expectationDataset.setMarkerShape(0);
      super.dataPanel.clear();
      super.dataPanel.addDrawable(this.expectationDataset);
      super.dataPanel.limitAutoscaleY(super.superposition.getXMin(), super.superposition.getXMax());
      this.expectationDataset.clear();
      this.expectationDataset.append(super.time, this.getExpectationX());
      super.dataPanel.setMessage("t=" + super.decimalFormat.format(super.time));
      super.dataPanel.repaint();
   }

   public void doStep() {
      super.doStep();
      this.expectationDataset.append(super.time, this.getExpectationX());
      super.dataPanel.setMessage("t=" + super.decimalFormat.format(super.time));
      super.dataPanel.render();
   }

   double getExpectationX() {
      double[] var1 = super.superposition.getRePsi();
      double[] var2 = super.superposition.getImPsi();
      double[] var3 = super.superposition.getX();
      double var4 = 0.0D;
      double var6 = 0.0D;
      int var8 = 0;

      for(int var9 = var3.length; var8 < var9; ++var8) {
         double var10 = var1[var8] * var1[var8] + var2[var8] * var2[var8];
         var4 += var10;
         var6 += var10 * var3[var8];
      }

      return var6 / var4;
   }

   void setValues() {
      super.control.setValue("numpts", 300);
      super.control.setValue("dt", 0.1D);
      super.control.setValue("x min", -5);
      super.control.setValue("x max", 5);
      super.control.setValue("psi range", 0.1D);
      super.control.setValue("stripchart", true);
      super.control.setValue("time domain", 20);
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
      QMSuperpositionExpectationXApp var1 = new QMSuperpositionExpectationXApp();
      AnimationControl var2 = new AnimationControl(var1);
      var1.setControl(var2);
      var2.loadXML(var0);
   }
}
