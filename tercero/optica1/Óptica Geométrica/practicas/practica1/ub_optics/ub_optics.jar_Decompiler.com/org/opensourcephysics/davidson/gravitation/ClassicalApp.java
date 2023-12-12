package org.opensourcephysics.davidson.gravitation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.opensourcephysics.controls.AnimationControl;
import org.opensourcephysics.davidson.applets.AbstractEmbeddableAnimation;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.PlottingPanel;

public class ClassicalApp extends AbstractEmbeddableAnimation implements PropertyChangeListener {
   PlottingPanel plottingPanel = PlottingPanel.createPolarType2("Classical Trajectories", 1.0D);
   DrawingFrame drawingFrame;
   TrajectoryClassical trajectory;

   public ClassicalApp() {
      this.drawingFrame = new DrawingFrame("Particle", this.plottingPanel);
      this.trajectory = new TrajectoryClassical();
      this.plottingPanel.addDrawable(this.trajectory);
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
      this.plottingPanel.setMessage("t=" + super.decimalFormat.format(this.trajectory.getTime()));
      this.plottingPanel.repaint();
   }

   public void resetAnimation() {
      super.resetAnimation();
      super.control.setValue("r_init", 1);
      super.control.setValue("phi_init", 0);
      super.control.setValue("v_init tangential", 1);
      super.control.setValue("dt", 0.2D);
      this.initializeAnimation();
   }

   public void initializeAnimation() {
      super.initializeAnimation();
      double var1 = super.control.getDouble("r_init");
      double var3 = super.control.getDouble("phi_init");
      double var5 = super.control.getDouble("v_init tangential");
      this.trajectory.ode_solver.setStepSize(super.control.getDouble("dt"));
      this.trajectory.initialize(var1, var3, var5);
      this.plottingPanel.setPreferredMinMax(-4.0D * var1, 4.0D * var1, -4.0D * var1, 4.0D * var1);
      this.plottingPanel.repaint();
   }

   public static void main(String[] var0) {
      AnimationControl.createApp(new ClassicalApp(), var0);
   }
}
