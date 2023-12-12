package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.controls.AnimationControl;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.PlottingPanel;
import org.opensourcephysics.display2d.ArrayData;
import org.opensourcephysics.display2d.ComplexCarpet;
import org.opensourcephysics.display2d.GridData;

public class QMSuperpositionCarpetApp extends QMSuperpositionApp {
   ScaleFunction zscale = ScaleFunction.getSqrtScale(0.0D, 1.0D);
   ComplexCarpet carpet = new ComplexCarpet((GridData)null);
   double[][] wave;

   public QMSuperpositionCarpetApp() {
      super.dataPanel = new PlottingPanel("x", "t", "Space Carpet");
      super.dataFrame = new DrawingFrame(super.dataPanel);
      super.dataPanel.addDrawable(this.carpet);
   }

   public void initializeAnimation() {
      super.initializeAnimation();
      int var1 = super.superposition.getNumpts();
      int var2 = super.control.getInt("numdt");
      this.wave = new double[3][var1];
      ArrayData var3 = new ArrayData(var1, var2, 3);
      double var4 = super.control.getDouble("psi range") / 2.0D;
      this.carpet.setAutoscaleZ(false, 0.0D, var4);
      this.zscale.setMinMax(0.0D, var4);
      var3.setScale(super.superposition.getXMin(), super.superposition.getXMax(), super.time, super.time + super.dt * (double)var2);
      this.carpet.setGridData(var3);
      this.carpet.clearData();
      super.dataPanel.repaint();
   }

   public void doStep() {
      super.doStep();
      double[] var1 = super.superposition.getRePsi();
      double[] var2 = super.superposition.getImPsi();
      int var3 = 0;

      for(int var4 = var1.length; var3 < var4; ++var3) {
         double var5 = Math.sqrt(var1[var3] * var1[var3] + var2[var3] * var2[var3]);
         this.wave[0][var3] = this.zscale.evaluate(var5);
         this.wave[1][var3] = var1[var3];
         this.wave[2][var3] = var2[var3];
      }

      this.carpet.setTopRow(this.wave);
      super.dataPanel.render();
   }

   void setValues() {
      super.control.setValue("numpts", 300);
      super.control.setValue("numdt", 100);
      super.control.setValue("dt", 0.1D);
      super.control.setValue("x min", -5);
      super.control.setValue("x max", 5);
      super.control.setValue("psi range", 0.1D);
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
      QMSuperpositionCarpetApp var1 = new QMSuperpositionCarpetApp();
      AnimationControl var2 = new AnimationControl(var1);
      var1.setControl(var2);
      var2.loadXML(var0);
   }
}
