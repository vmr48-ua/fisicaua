package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.controls.AnimationControl;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.PlottingPanel;
import org.opensourcephysics.display.Stripchart;
import org.opensourcephysics.numerics.FFT;

public class QMSuperpositionExpectationPApp extends QMSuperpositionApp {
   Stripchart expectationDataset = new Stripchart(1.0D, 1.0D);
   double[] fftData;
   double[] p;
   FFT fft;

   public QMSuperpositionExpectationPApp() {
      super.dataPanel = new PlottingPanel("t", "<P>", "Momentum Expectation");
      super.dataFrame = new DrawingFrame(super.dataPanel);
   }

   public void initializeAnimation() {
      super.initializeAnimation();
      int var1 = super.superposition.getNumpts();
      this.fftData = new double[2 * var1];
      this.fft = new FFT(var1);
      double var2 = (super.superposition.getXMax() - super.superposition.getXMin()) / (double)(var1 - 1);
      this.p = this.fft.getNaturalFreq(var2 / 6.283185307179586D);
      double var4 = super.control.getDouble("time domain");
      this.expectationDataset = new Stripchart(var4, super.superposition.getXMax() - super.superposition.getXMin());
      this.expectationDataset.enable(super.control.getBoolean("stripchart"));
      this.expectationDataset.setConnected(true);
      this.expectationDataset.setMarkerShape(0);
      super.dataPanel.clear();
      super.dataPanel.addDrawable(this.expectationDataset);
      super.dataPanel.setMessage("t=" + super.decimalFormat.format(super.time));
      super.dataPanel.repaint();
      double var6 = super.control.getDouble("p range");
      super.dataPanel.setPreferredMinMaxY(-var6 / 2.0D, var6 / 2.0D);
      this.expectationDataset.clear();
      this.updateFFT();
      this.expectationDataset.append(super.time, this.getExpectationP());
   }

   public void doStep() {
      super.doStep();
      this.updateFFT();
      this.expectationDataset.append(super.time, this.getExpectationP());
      super.dataPanel.setMessage("t=" + super.decimalFormat.format(super.time));
      super.dataPanel.render();
   }

   void updateFFT() {
      double[] var1 = super.superposition.getRePsi();
      double[] var2 = super.superposition.getImPsi();
      double[] var3 = super.superposition.getX();
      int var4 = var1.length;
      int var5 = (int)((double)var4 * var3[0] / (var3[var4 - 1] - var3[0]));
      var5 = Math.abs(var5) + 1;

      for(int var6 = 0; var6 < var4; ++var6) {
         int var7 = (var5 + var6) % var4;
         this.fftData[2 * var7] = var1[var6];
         this.fftData[2 * var7 + 1] = var2[var6];
      }

      this.fft.transform(this.fftData);
      this.fft.toNaturalOrder(this.fftData);
   }

   double getExpectationP() {
      double var1 = 0.0D;
      double var3 = 0.0D;
      int var5 = 0;

      for(int var6 = this.p.length; var5 < var6; ++var5) {
         double var7 = this.fftData[2 * var5] * this.fftData[2 * var5] + this.fftData[2 * var5 + 1] * this.fftData[2 * var5 + 1];
         var1 += var7;
         var3 += var7 * this.p[var5];
      }

      return var3 / var1;
   }

   void setValues() {
      super.control.setValue("numpts", 300);
      super.control.setValue("dt", 0.1D);
      super.control.setValue("x min", -5);
      super.control.setValue("x max", 5);
      super.control.setValue("psi range", 0.1D);
      super.control.setValue("p range", 2);
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
      QMSuperpositionExpectationPApp var1 = new QMSuperpositionExpectationPApp();
      AnimationControl var2 = new AnimationControl(var1);
      var1.setControl(var2);
      var2.loadXML(var0);
   }
}
