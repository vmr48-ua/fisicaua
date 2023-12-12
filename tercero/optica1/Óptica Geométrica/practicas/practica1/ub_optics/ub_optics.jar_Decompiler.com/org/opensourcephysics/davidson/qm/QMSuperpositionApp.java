package org.opensourcephysics.davidson.qm;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import org.opensourcephysics.controls.AbstractAnimation;
import org.opensourcephysics.controls.AnimationControl;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.display.ComplexDataset;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.OSPFrame;
import org.opensourcephysics.display.PlottingPanel;
import org.opensourcephysics.ejs.control.EjsControlFrame;
import org.opensourcephysics.numerics.DoubleArray;
import org.opensourcephysics.numerics.Function;
import org.opensourcephysics.numerics.ParsedFunction;
import org.opensourcephysics.numerics.ParserException;
import org.opensourcephysics.numerics.Util;

public class QMSuperpositionApp extends AbstractAnimation implements PropertyChangeListener {
   String intialRe = "{0.707,0.707,0,0,0,0}";
   String intialIm = "{0,0,0,0,0,0}";
   String potentail = "x*x/2";
   PlottingPanel dataPanel;
   PlottingPanel psiPanel = new PlottingPanel("x", "|Psi|", "Psi(x)");
   DrawingFrame psiFrame;
   OSPFrame dataFrame;
   DoubleArray recoef;
   DoubleArray imcoef;
   ComplexDataset psiDataset;
   QMSuperposition superposition;
   double time;
   double dt;

   public QMSuperpositionApp() {
      this.psiFrame = new DrawingFrame(this.psiPanel);
      this.recoef = new DoubleArray(this.intialRe);
      this.imcoef = new DoubleArray(this.intialIm);
      this.psiDataset = new ComplexDataset();
      this.time = 0.0D;
      this.psiPanel.limitAutoscaleY(-0.05D, 0.05D);
      this.psiPanel.addDrawable(this.psiDataset);
   }

   public void initializeAnimation() {
      super.initializeAnimation();
      if (super.control.getBoolean("hide frame")) {
         this.psiFrame.setKeepHidden(true);
      } else if (this.psiFrame.getDrawingPanel() != null) {
         this.psiFrame.setKeepHidden(false);
      }

      double var1 = super.control.getDouble("psi range") / 2.0D;
      this.psiPanel.limitAutoscaleY(-var1, var1);
      this.time = 0.0D;
      String var3 = super.control.getString("dt");
      String var4 = super.control.getString("time format");
      super.decimalFormat = new DecimalFormat(var4);
      double var5 = Util.evalMath(var3);
      if (Double.isNaN(var5)) {
         super.control.println("Error reading dt.");
      } else {
         this.dt = var5;
      }

      double var7 = this.psiPanel.getPreferredXMin();
      var3 = super.control.getString("x min");
      var5 = Util.evalMath(var3);
      if (Double.isNaN(var5)) {
         super.control.println("Error reading xmin.");
      } else {
         var7 = var5;
      }

      double var9 = this.psiPanel.getPreferredXMax();
      var3 = super.control.getString("x max");
      var5 = Util.evalMath(var3);
      if (Double.isNaN(var5)) {
         super.control.println("Error reading xmax.");
      } else {
         var9 = var5;
      }

      var3 = super.control.getString("energy scale");
      var5 = Util.evalMath(var3);
      double var11 = 1.0D;
      if (Double.isNaN(var5)) {
         super.control.println("Error reading energy scale.");
      } else {
         var11 = var5;
      }

      int var13 = super.control.getInt("numpts");

      DoubleArray var14;
      try {
         var14 = new DoubleArray(super.control.getString("re coef"));
         this.recoef = var14;
      } catch (NumberFormatException var19) {
         super.control.println("Invalid real coefficient values.");
         super.control.setValue("re coef", this.recoef.getDefault());
      }

      try {
         var14 = new DoubleArray(super.control.getString("im coef"));
         this.imcoef = var14;
      } catch (NumberFormatException var18) {
         super.control.println("Invalid imaginary coefficient values.");
         super.control.setValue("im coef", this.imcoef.getDefault());
      }

      if (super.control.getString("V(x)").trim().equals("ring")) {
         this.superposition = new EigenstateRingSuperposition(var13, var7, var9);
      } else if (super.control.getString("V(x)").trim().equals("well")) {
         this.superposition = new EigenstateWellSuperposition(var13, var7, var9);
      } else if (super.control.getString("V(x)").trim().equals("sho")) {
         this.superposition = new EigenstateSHOSuperposition(var13, var7, var9);
      } else {
         Object var15;
         try {
            var15 = new ParsedFunction(super.control.getString("V(x)"));
         } catch (ParserException var17) {
            super.control.println("Error parsing potential function. Potential set to zero.");
            var15 = Util.constantFunction(0.0D);
         }

         this.superposition = new EigenstateShootingSuperposition((Function)var15, var13, var7, var9);
      }

      if (!this.superposition.setCoef(this.recoef.getArray(), this.imcoef.getArray())) {
         super.control.println("Eigenfunction did not converge.");
      }

      this.superposition.setEnergyScale(var11);
      this.superposition.update(this.time);
      this.psiDataset.setCentered(true);
      String var20 = super.control.getString("style").toLowerCase();
      if (var20 != null && var20.equals("reim")) {
         this.psiDataset.setMarkerShape(1);
         this.psiPanel.setYLabel("Re(Psi) & Im(Psi)");
      } else if (var20 != null && var20.equals("ampwithphase")) {
         this.psiDataset.setMarkerShape(2);
         this.psiDataset.setCentered(false);
         this.psiPanel.limitAutoscaleY(0.0D, 2.0D * var1);
         this.psiPanel.setYLabel("|Psi|");
      } else {
         this.psiDataset.setMarkerShape(2);
         this.psiPanel.setYLabel("|Psi|");
      }

      this.superposition.getPsi(this.psiDataset);
      this.psiPanel.setMessage("t=" + super.decimalFormat.format(this.time));
      if (this.dataPanel != null) {
         this.dataPanel.setMessage("t=" + super.decimalFormat.format(this.time));
      }

      this.psiPanel.repaint();
   }

   void normCoef() {
      double[] var1 = this.superposition.getReCoef();
      double[] var2 = this.superposition.getImCoef();
      double var3 = 0.0D;
      int var5 = 0;

      int var6;
      for(var6 = var1.length; var5 < var6; ++var5) {
         var3 += var1[var5] * var1[var5] + var2[var5] * var2[var5];
      }

      if (var3 == 0.0D) {
         var3 = 1.0D;
         var1[0] = 1.0D;
         var2[0] = 0.0D;
      } else {
         var3 = 1.0D / Math.sqrt(var3);
         var5 = 0;

         for(var6 = var1.length; var5 < var6; ++var5) {
            var1[var5] *= var3;
            var2[var5] *= var3;
         }

      }
   }

   public void doStep() {
      this.time += this.dt;
      this.superposition.update(this.time);
      this.superposition.getPsi(this.psiDataset);
      this.psiPanel.setMessage("t=" + super.decimalFormat.format(this.time));
      if (this.dataPanel != null) {
         this.dataPanel.setMessage("t=" + super.decimalFormat.format(this.time));
      }

      this.psiPanel.render();
      if (this.time >= 3.4028234663852886E38D) {
         super.control.calculationDone("Done");
      }

   }

   public void resetAnimation() {
      super.resetAnimation();
      this.setValues();
      if (super.control instanceof EjsControlFrame) {
         ((EjsControlFrame)super.control).loadDefaultXML();
      }

      this.initializeAnimation();
   }

   void setValues() {
      super.control.setValue("numpts", 300);
      super.control.setValue("psi range", 0.1D);
      super.control.setValue("dt", 0.1D);
      super.control.setValue("x min", -5);
      super.control.setValue("x max", 5);
      super.control.setValue("re coef", this.intialRe);
      super.control.setValue("im coef", this.intialIm);
      super.control.setValue("V(x)", "x*x/2");
      super.control.setValue("energy scale", 1);
      super.control.setValue("time format", "0.00");
      super.control.setValue("style", "phase");
      super.control.setValue("hide frame", false);
   }

   public static XML.ObjectLoader getLoader() {
      return new QMSuperpositionLoader();
   }

   public void propertyChange(PropertyChangeEvent var1) {
      boolean var2 = this.isRunning();
      if (var2) {
         this.stopAnimation();
      }

      this.initializeAnimation();
      if (var2) {
         this.startAnimation();
      }

   }

   public static void main(String[] var0) {
      QMSuperpositionApp var1 = new QMSuperpositionApp();
      AnimationControl var2 = new AnimationControl(var1);
      var1.setControl(var2);
      var2.loadXML(var0);
   }
}
