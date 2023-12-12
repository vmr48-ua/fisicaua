package org.opensourcephysics.davidson.electrodynamics;

import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.opensourcephysics.controls.AnimationControl;
import org.opensourcephysics.controls.Control;
import org.opensourcephysics.davidson.applets.AbstractEmbeddableAnimation;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display2d.ContourPlot;
import org.opensourcephysics.display2d.GridPointData;
import org.opensourcephysics.display2d.VectorPlot;

public class RadiationApp extends AbstractEmbeddableAnimation implements PropertyChangeListener {
   DrawingPanel vectorPanel = new DrawingPanel();
   protected DrawingFrame vectorFrame;
   VectorPlot vectorField;
   GridPointData vdataset;
   DrawingPanel contourPanel;
   protected DrawingFrame contourFrame;
   ContourPlot contour;
   GridPointData cdataset;
   MovingCharge charge;
   double xmin;
   double xmax;
   double ymin;
   double ymax;

   public RadiationApp() {
      this.vectorFrame = new DrawingFrame("Electric Field", this.vectorPanel);
      this.contourPanel = new DrawingPanel();
      this.contourFrame = new DrawingFrame("Magnetic Field", this.contourPanel);
      this.xmin = -10.0D;
      this.xmax = 10.0D;
      this.ymin = -10.0D;
      this.ymax = 10.0D;
      this.vectorPanel.setPreferredMinMax(this.xmin, this.xmax, this.ymin, this.ymax);
      this.vectorPanel.setBuffered(true);
      this.contourPanel.setPreferredMinMax(this.xmin, this.xmax, this.ymin, this.ymax);
      this.contourPanel.setBuffered(true);
      this.contourFrame.setLocation(50, 300);
      this.charge = new MovingCharge();
   }

   public void initializeAnimation() {
      super.initializeAnimation();
      int var1 = super.control.getInt("size");
      var1 = Math.max(4, var1);
      String var2 = this.charge.setXFunction(super.control.getString("f[x]"));
      if (var2 != null) {
         super.control.println(var2);
      }

      var2 = this.charge.setYFunction(super.control.getString("f[y]"));
      if (var2 != null) {
         super.control.println(var2);
      }

      this.charge.dt = super.control.getDouble("dt");
      this.initArrays(var1);
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

   private void initArrays(int var1) {
      this.charge.reset();
      this.vdataset = new GridPointData(var1, var1, 3);
      this.vdataset.setScale(this.xmin, this.xmax, this.ymin, this.ymax);
      this.vectorField = new VectorPlot(this.vdataset);
      double var2 = super.control.getDouble("E scale min");
      double var4 = super.control.getDouble("E scale max");
      this.vectorField.setAutoscaleZ(false, var2, var4);
      this.vectorPanel.clear();
      this.vectorPanel.addDrawable(this.vectorField);
      this.cdataset = new GridPointData(var1, var1, 1);
      this.cdataset.setScale(this.xmin, this.xmax, this.ymin, this.ymax);
      this.contour = new ContourPlot(this.cdataset);
      var2 = super.control.getDouble("B scale min");
      var4 = super.control.getDouble("B scale max");
      this.contour.setAutoscaleZ(false, var2, var4);
      this.contourPanel.clear();
      this.contourPanel.addDrawable(this.contour);
      this.vectorPanel.addDrawable(this.charge);
      this.contourPanel.addDrawable(this.charge);
      this.calculateFields();
      this.vectorField.update();
      this.vectorPanel.invalidateImage();
      this.vectorPanel.repaint();
      this.contour.update();
      this.contourPanel.invalidateImage();
      this.contourPanel.repaint();
   }

   private void calculateFields() {
      double[] var1 = new double[4];
      double[][][] var2 = this.vdataset.getData();
      double[][][] var3 = this.cdataset.getData();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         for(int var6 = 0; var6 < var4; ++var6) {
            double var7 = var2[var5][var6][0];
            double var9 = var2[var5][var6][1];
            this.charge.calcRetardedField(var7, var9, var1);
            var2[var5][var6][2] = var1[2];
            var2[var5][var6][3] = var1[0];
            var2[var5][var6][4] = var1[1];
            var3[var5][var6][2] = var1[3];
         }
      }

   }

   public void resetAnimation() {
      super.control.setValue("f[x]", "5 * cos (t * 0.9 / 5.0)");
      super.control.setValue("f[y]", "0");
      super.control.setValue("size", 31);
      super.control.setValue("dt", 0.5D);
      super.control.setValue("E scale min", 0);
      super.control.setValue("E scale max", 0.2D);
      super.control.setValue("B scale min", -1);
      super.control.setValue("B scale max", 1);
      this.initializeAnimation();
   }

   protected void doStep() {
      this.charge.step();
      this.calculateFields();
      this.vectorField.update();
      this.contour.update();
      this.vectorPanel.render();
      if (this.contourFrame.isVisible()) {
         this.contourPanel.render();
      }

   }

   public void setControl(Control var1) {
      this.stopAnimation();
      if (var1 == null) {
         super.control = null;
      } else {
         super.control = var1;
         this.resetAnimation();
         super.objectManager.clearAll();
         super.objectManager.addView("contourFrame", this.contourFrame);
         super.objectManager.addView("vectorFrame", this.vectorFrame);
         if (super.control instanceof Container) {
            super.objectManager.addView("controlFrame", (Container)super.control);
         }

      }
   }

   public static void main(String[] var0) {
      AnimationControl.createApp(new RadiationApp(), var0);
   }
}
