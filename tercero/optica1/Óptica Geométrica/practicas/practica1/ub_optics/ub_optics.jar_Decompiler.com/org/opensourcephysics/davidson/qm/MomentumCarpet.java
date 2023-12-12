package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.PlottingPanel;
import org.opensourcephysics.display2d.ArrayData;
import org.opensourcephysics.display2d.ComplexCarpet;
import org.opensourcephysics.numerics.FFT;

public class MomentumCarpet {
   PlottingPanel panel = new PlottingPanel("p", "t", "Momentum Carpet");
   DrawingFrame frame;
   double[][] line;
   double[] fftData;
   double[] p;
   FFT fft;
   ComplexCarpet plot;
   ArrayData arraydata;

   public MomentumCarpet(int var1, int var2) {
      this.frame = new DrawingFrame(this.panel);
      this.arraydata = new ArrayData(var1, var2, 3);
      this.fftData = new double[2 * var1];
      this.fft = new FFT(var1);
      this.plot = new ComplexCarpet(this.arraydata);
      this.plot.setAutoscaleZ(true, 0.0D, 1.0D);
      this.plot.setShowGridLines(false);
      this.panel.addDrawable(this.plot);
   }

   void resize(int var1, int var2) {
      if (this.arraydata.getNx() != var1 || this.arraydata.getNy() != var2) {
         this.arraydata = new ArrayData(var1, var2, 3);
         this.plot.setGridData(this.arraydata);
         this.fftData = new double[2 * var1];
         this.line = new double[3][var1];
         this.fft = new FFT(var1);
      }

   }

   void setScale(double var1, double var3, double var5, double var7) {
      double var9 = (var3 - var1) / (double)(this.arraydata.getNx() - 1);
      this.p = this.fft.getNaturalFreq(var9 / 6.283185307179586D);
      this.arraydata.setScale(this.p[0], this.p[this.p.length - 1], var5, var7);
   }

   void addRow(QMSuperposition var1) {
      this.updateFFT(var1);
      int var2 = 0;

      for(int var3 = this.fftData.length / 2; var2 < var3; ++var2) {
         double var4 = this.fftData[2 * var2];
         double var6 = this.fftData[2 * var2];
         this.line[0][var2] = Math.sqrt(var4 * var4 + var6 * var6);
         this.line[1][var2] = var4;
         this.line[2][var2] = var6;
      }

      this.plot.setTopRow(this.line);
      this.plot.update();
      this.panel.repaint();
   }

   void updateFFT(QMSuperposition var1) {
      double[] var2 = var1.getRePsi();
      double[] var3 = var1.getImPsi();
      double[] var4 = var1.getX();
      int var5 = var2.length;
      int var6 = (int)((double)var5 * var4[0] / (var4[var5 - 1] - var4[0]));
      var6 = Math.abs(var6) + 1;

      for(int var7 = 0; var7 < var5; ++var7) {
         int var8 = (var6 + var7) % var5;
         this.fftData[2 * var8] = var2[var7];
         this.fftData[2 * var8 + 1] = var3[var7];
      }

      this.fft.transform(this.fftData);
      this.fft.toNaturalOrder(this.fftData);
   }

   void reset() {
      double[][][] var1 = this.arraydata.getData();
      int var2 = 0;

      for(int var3 = this.arraydata.getNx(); var2 < var3; ++var2) {
         int var4 = 0;

         for(int var5 = this.arraydata.getNy(); var4 < var5; ++var4) {
            var1[0][var2][var4] = 0.0D;
            var1[1][var2][var4] = 0.0D;
            var1[2][var2][var4] = 0.0D;
         }
      }

      this.plot.update();
      this.panel.repaint();
   }
}
