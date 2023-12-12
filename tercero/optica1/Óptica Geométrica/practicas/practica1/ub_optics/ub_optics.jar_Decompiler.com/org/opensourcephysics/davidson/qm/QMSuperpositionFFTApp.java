package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.controls.AnimationControl;
import org.opensourcephysics.display.ComplexDataset;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.PlottingPanel;
import org.opensourcephysics.numerics.FFT;

public class QMSuperpositionFFTApp extends QMSuperpositionApp {
   ComplexDataset fftDataset = new ComplexDataset();
   double[] fftData;
   double[] p;
   int gutter = 0;
   double xmin;
   double xmax;
   FFT fft;

   public QMSuperpositionFFTApp() {
      super.intialRe = "{0.707,0.707,0,0,0,0}";
      super.intialIm = "{0,0,0,0,0,0}";
      super.potentail = "sho";
      super.dataPanel = new PlottingPanel("p", "|Phi(p)|", "Momentum Space");
      super.dataFrame = new DrawingFrame(super.dataPanel);
      super.dataPanel.addDrawable(this.fftDataset);
   }

   public void initializeAnimation() {
      super.initializeAnimation();
      this.fftDataset.setCentered(true);
      String var1 = super.control.getString("style").toLowerCase();
      if (var1 != null && var1.equals("reim")) {
         this.fftDataset.setMarkerShape(1);
         super.dataPanel.setYLabel("Re(Phi) & Im(Phi)");
      } else if (var1 != null && var1.equals("ampwithphase")) {
         this.fftDataset.setMarkerShape(2);
         this.fftDataset.setCentered(false);
         super.dataPanel.setYLabel("|Phi|");
      } else {
         this.fftDataset.setMarkerShape(2);
         super.dataPanel.setYLabel("|Phi|");
      }

      this.gutter = super.control.getInt("gutter points");
      if (super.control.getString("V(x)").trim().equals("ring")) {
         this.gutter = 0;
      }

      int var2 = super.superposition.getNumpts();
      this.fftData = new double[2 * var2 + 4 * this.gutter];
      this.fft = new FFT(var2 + 2 * this.gutter);
      double var3 = (super.superposition.getXMax() - super.superposition.getXMin()) / (double)(var2 - 1);
      this.xmin = super.superposition.getXMin() - (double)this.gutter * var3;
      this.xmax = super.superposition.getXMax() + (double)this.gutter * var3;
      this.p = this.fft.getNaturalOmega(this.xmin, this.xmax);
      double var5 = super.control.getDouble("p range");
      double var7 = super.control.getDouble("p max");
      super.dataPanel.setPreferredMinMaxX(-var7, var7);
      if (var1 != null && var1.equals("ampwithphase")) {
         super.dataPanel.limitAutoscaleY(0.0D, 2.0D * var5);
      } else {
         super.dataPanel.limitAutoscaleY(-var5, var5);
      }

      super.dataPanel.setMessage("t=" + super.decimalFormat.format(super.time));
      this.updateFFT();
      super.dataPanel.repaint();
   }

   public void doStep() {
      super.doStep();
      this.updateFFT();
      super.dataPanel.setMessage("t=" + super.decimalFormat.format(super.time));
      super.dataPanel.render();
   }

   void updateFFT() {
      double[] var1 = super.superposition.getRePsi();
      double[] var2 = super.superposition.getImPsi();
      int var3 = var1.length + 2 * this.gutter;
      int var4 = (int)((double)var3 * this.xmin / (this.xmax - this.xmin));
      int var5 = Math.abs(var4) + 1 - this.gutter;
      int var6 = var1.length;
      int var7 = var5;

      int var8;
      for(var8 = 0; var7 < var6; ++var7) {
         this.fftData[var8] = var1[var7];
         this.fftData[var8 + 1] = var2[var7];
         var8 += 2;
      }

      var7 = 0;

      for(var8 = 2 * (var3 - var5 - 1); var7 < var5; ++var7) {
         this.fftData[var8] = var1[var7];
         this.fftData[var8 + 1] = var2[var7];
         var8 += 2;
      }

      this.fft.transform(this.fftData);
      this.fft.setNormalization((double)var6);
      this.fft.toNaturalOrder(this.fftData);
      this.fftDataset.clear();
      this.fftDataset.append(this.p, this.fftData);
   }

   void setValues() {
      super.control.setValue("numpts", 512);
      super.control.setValue("gutter points", 128);
      super.control.setValue("dt", 0.1D);
      super.control.setValue("x min", -5);
      super.control.setValue("x max", 5);
      super.control.setValue("psi range", 0.1D);
      super.control.setValue("p max", 10);
      super.control.setValue("p range", 0.01D);
      super.control.setValue("re coef", super.intialRe);
      super.control.setValue("im coef", super.intialIm);
      super.control.setValue("V(x)", super.potentail);
      super.control.setValue("energy scale", 1);
      super.control.setValue("psi range", 0.1D);
      super.control.setValue("time format", "0.00");
      super.control.setValue("style", "phase");
      super.control.setValue("hide frame", false);
   }

   public static void main(String[] var0) {
      QMSuperpositionFFTApp var1 = new QMSuperpositionFFTApp();
      AnimationControl var2 = new AnimationControl(var1);
      var1.setControl(var2);
      var2.loadXML(var0);
   }
}
