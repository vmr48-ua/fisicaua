package org.opensourcephysics.davidson.qm;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.nfunk.JEParser;
import org.nfunk.jep.type.Complex;
import org.opensourcephysics.controls.AnimationControl;
import org.opensourcephysics.davidson.applets.AbstractEmbeddableAnimation;
import org.opensourcephysics.display.ComplexDataset;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.PlottingPanel;
import org.opensourcephysics.numerics.ParserException;

public class ComplexFunctionApp extends AbstractEmbeddableAnimation implements PropertyChangeListener {
   private JEParser parser;
   private double xmin = -10.0D;
   private double xmax = 10.0D;
   private int numpts = 400;
   private String functionStr = "e^(i*(2*x-3*t))+e^(i*(2.3*x-3.2*t))";
   private double dt = 0.1D;
   private double time = 0.0D;
   private ComplexDataset dataset = new ComplexDataset();
   PlottingPanel plotPanel = new PlottingPanel("x", "f(x,t)", (String)null);
   DrawingFrame drawingFrame;

   public ComplexFunctionApp() {
      this.drawingFrame = new DrawingFrame(this.plotPanel);
      this.plotPanel.setAutoscaleX(true);
      this.plotPanel.setAutoscaleY(true);
      this.plotPanel.setSquareAspect(false);
      this.plotPanel.addDrawable(this.dataset);

      try {
         this.parser = new JEParser("0", "x", "t", true);
      } catch (ParserException var2) {
      }

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

   public void resetAnimation() {
      this.stopAnimation();
      super.control.clearMessages();
      this.xmin = -10.0D;
      this.xmax = 10.0D;
      this.numpts = 400;
      this.functionStr = "e^(i*(2*x-3*t))+e^(i*(2.3*x-3.2*t))";
      super.control.setValue("f[x,t]", this.functionStr);
      super.control.setValue("dt", 0.1D);
      super.control.setValue("x min", -10);
      super.control.setValue("x max", 10);
      super.control.setValue("autoscale y", true);
      super.control.setValue("y min", -1);
      super.control.setValue("y max", 1);
      super.control.setValue("number of points", 400);
      super.control.setValue("style", "phase");
      super.control.setValue("title", "Complex Function Plotter");
      super.control.setValue("x label", "x");
      super.control.setValue("y label", "$\\Psi$(x,t)");
      this.initializeAnimation();
   }

   public void initializeAnimation() {
      super.initializeAnimation();
      this.time = 0.0D;
      this.drawingFrame.setTitle(super.control.getString("title"));
      this.plotPanel.setTitle(super.control.getString("title"));
      this.plotPanel.setXLabel(super.control.getString("x label"));
      this.plotPanel.setYLabel(super.control.getString("y label"));
      this.xmin = super.control.getDouble("x min");
      this.xmax = super.control.getDouble("x max");
      this.dt = super.control.getDouble("dt");
      this.numpts = super.control.getInt("number of points");
      this.plotPanel.setPreferredMinMaxX(this.xmin, this.xmax);
      if (super.control.getBoolean("autoscale y")) {
         this.plotPanel.setAutoscaleY(true);
      } else {
         double var1 = super.control.getDouble("y min");
         double var3 = super.control.getDouble("y max");
         this.plotPanel.setPreferredMinMaxY(var1, var3);
      }

      try {
         this.functionStr = super.control.getString("f[x,t]");
         this.parser.setFunction(this.functionStr);
      } catch (ParserException var5) {
         super.control.println(var5.getMessage());
      }

      if (this.numpts < 1) {
         super.control.println("Number of points must be > 1.");
      } else {
         String var6 = super.control.getString("style");
         if (var6 != null && var6.equals("reim")) {
            this.dataset.setMarkerShape(1);
         } else if (var6 != null && var6.equals("amp")) {
            this.dataset.setMarkerShape(0);
         } else {
            this.dataset.setMarkerShape(2);
         }

         this.refresh();
      }
   }

   void refresh() {
      this.plotFunction();
      this.plotPanel.repaint();
   }

   void plotFunction() {
      this.dataset.clear();
      double var1 = this.xmin;
      double var3 = (this.xmax - this.xmin) / (double)(this.numpts - 1);

      for(int var5 = 0; var5 < this.numpts; ++var5) {
         Complex var6 = this.parser.evaluateComplex(var1, this.time);
         this.dataset.append(var1, var6.re(), var6.im());
         var1 += var3;
      }

      this.plotPanel.setMessage("time=" + super.decimalFormat.format(this.time));
   }

   protected void doStep() {
      this.time += this.dt;
      this.plotFunction();
      this.plotPanel.render();
   }

   public static void main(String[] var0) {
      ComplexFunctionApp var1 = new ComplexFunctionApp();
      AnimationControl var2 = new AnimationControl(var1);
      var1.setControl(var2);
      var2.loadXML(var0);
   }
}
