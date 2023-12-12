package org.opensourcephysics.davidson.gravitation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.opensourcephysics.controls.AnimationControl;
import org.opensourcephysics.davidson.applets.AbstractEmbeddableAnimation;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.PlottingPanel;

public class PlanetApp extends AbstractEmbeddableAnimation implements PropertyChangeListener {
   PlottingPanel plottingPanel = new PlottingPanel("x", "y", "Planetary Orbits");
   DrawingFrame drawingFrame;
   Planet trajectory;

   public PlanetApp() {
      this.drawingFrame = new DrawingFrame("Planet App", this.plottingPanel);
      this.trajectory = new Planet();
      this.plottingPanel.addDrawable(this.trajectory);
      this.plottingPanel.setSquareAspect(true);
      this.drawingFrame.setSize(450, 450);
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

   protected void doStep() {
      this.trajectory.stepTime();
      this.plottingPanel.setMessage("t=" + super.decimalFormat.format(this.trajectory.state[4]));
      this.plottingPanel.setMessage("E=" + super.decimalFormat.format(this.trajectory.getEnergy()), 2);
      this.plottingPanel.repaint();
   }

   public void resetAnimation() {
      super.resetAnimation();
      super.control.setValue("x0", 1);
      super.control.setValue("y0", 0);
      super.control.setValue("vx0", 0);
      super.control.setValue("vy0", 6.283185307179586D);
      super.control.setValue("dt", 0.01D);
      this.initializeAnimation();
   }

   public void initializeAnimation() {
      super.initializeAnimation();
      double var1 = super.control.getDouble("x0");
      double var3 = super.control.getDouble("vx0");
      double var5 = super.control.getDouble("y0");
      double var7 = super.control.getDouble("vy0");
      this.trajectory.ode_solver.setStepSize(super.control.getDouble("dt"));
      this.trajectory.initialize(var1, var3, var5, var7);
      this.plottingPanel.setPreferredMinMax(-5.0D * var1, 5.0D * var1, -5.0D * var5, 5.0D * var5);
      this.plottingPanel.repaint();
   }

   public static void main(String[] var0) {
      AnimationControl.createApp(new PlanetApp(), var0);
   }
}
