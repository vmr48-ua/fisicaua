package org.opensourcephysics.davidson.nbody;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import org.opensourcephysics.controls.AbstractAnimation;
import org.opensourcephysics.controls.AnimationControl;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.InteractiveMouseHandler;
import org.opensourcephysics.display.InteractivePanel;
import org.opensourcephysics.display.PlottingPanel;

public class OrbitApp extends AbstractAnimation implements InteractiveMouseHandler, PropertyChangeListener {
   ArrayList particles = new ArrayList();
   double a = 4.0D;
   PlottingPanel plottingPanel = new PlottingPanel("x", "y", (String)null);
   DrawingFrame drawingFrame;
   OrbitNBody nbody;
   protected int stepsPerDisplay;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$davidson$nbody$OrbitParticle;

   public OrbitApp() {
      this.drawingFrame = new DrawingFrame("Orbits", this.plottingPanel);
      this.nbody = new OrbitNBody();
      this.stepsPerDisplay = 1;
      this.plottingPanel.setPreferredMinMax(-this.a / 2.0D, this.a / 2.0D, -this.a / 2.0D, this.a / 2.0D);
      this.plottingPanel.addDrawable(this.nbody);
      this.plottingPanel.setSquareAspect(true);
      this.plottingPanel.setInteractiveMouseHandler(this);
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
      for(int var1 = 0; var1 < this.stepsPerDisplay; ++var1) {
         this.nbody.advanceTime();
      }

      this.saveState();
      this.plottingPanel.setMessage("t=" + super.decimalFormat.format(this.nbody.getTime()));
      this.plottingPanel.render();
   }

   public void initializeAnimation() {
      super.initializeAnimation();
      this.stepsPerDisplay = super.control.getInt("steps per display");
      this.setState();
      this.plottingPanel.setMessage("t=" + super.decimalFormat.format(this.nbody.getTime()));
      this.nbody.initialize(super.control.getDouble("dt"));
   }

   public void resetAnimation() {
      super.resetAnimation();
      this.plottingPanel.removeObjectsOfClass(class$org$opensourcephysics$davidson$nbody$OrbitParticle == null ? (class$org$opensourcephysics$davidson$nbody$OrbitParticle = class$("org.opensourcephysics.davidson.nbody.OrbitParticle")) : class$org$opensourcephysics$davidson$nbody$OrbitParticle);
      super.control.setValue("x", 1);
      super.control.setValue("vx", 0);
      super.control.setValue("y", 0);
      super.control.setValue("vy", 0.5D);
      super.control.setValue("m", 1);
      super.control.setValue("dt", 0.1D);
      super.control.setValue("steps per display", 1);
      this.initializeAnimation();
   }

   public void addMass() {
      boolean var1 = this.isRunning();
      if (var1) {
         this.stopAnimation();
      }

      double var2 = super.control.getDouble("x");
      double var4 = super.control.getDouble("vx");
      double var6 = super.control.getDouble("y");
      double var8 = super.control.getDouble("vy");
      double var10 = super.control.getDouble("m");
      OrbitParticle var12 = new OrbitParticle(var2, var4, var6, var8, var10);
      this.plottingPanel.addDrawable(var12);
      this.initializeAnimation();
      if (var1) {
         this.startAnimation();
      }

      this.plottingPanel.repaint();
   }

   void setState() {
      this.particles = this.plottingPanel.getDrawables(class$org$opensourcephysics$davidson$nbody$OrbitParticle == null ? (class$org$opensourcephysics$davidson$nbody$OrbitParticle = class$("org.opensourcephysics.davidson.nbody.OrbitParticle")) : class$org$opensourcephysics$davidson$nbody$OrbitParticle);
      int var1 = this.particles.size();
      this.nbody.setNBodies(var1);
      double[] var2 = this.nbody.state;

      int var3;
      for(var3 = 0; var3 < var1; ++var3) {
         OrbitParticle var4 = (OrbitParticle)this.particles.get(var3);
         var2[4 * var3] = var4.x;
         var2[4 * var3 + 1] = var4.vx;
         var2[4 * var3 + 2] = var4.y;
         var2[4 * var3 + 3] = var4.vy;
         this.nbody.masses[var3].m = var4.m;
      }

      this.nbody.computeAcceleration(var2);

      for(var3 = 0; var3 < var1; ++var3) {
         this.nbody.masses[var3].setArrow(var2[4 * var3], var2[4 * var3 + 2], this.nbody.force[2 * var3], this.nbody.force[2 * var3 + 1]);
      }

   }

   void saveState() {
      int var1 = this.particles.size();
      if (this.nbody != null && this.nbody.masses != null && var1 == this.nbody.masses.length) {
         double[] var2 = this.nbody.state;

         for(int var3 = 0; var3 < var1; ++var3) {
            OrbitParticle var4 = (OrbitParticle)this.particles.get(var3);
            var4.x = var2[4 * var3];
            var4.vx = var2[4 * var3 + 1];
            var4.y = var2[4 * var3 + 2];
            var4.vy = var2[4 * var3 + 3];
         }

      }
   }

   public void handleMouseAction(InteractivePanel var1, MouseEvent var2) {
      var1.handleMouseAction(var1, var2);
      if (var1.getMouseAction() == 3) {
         this.setState();
         var1.repaint();
      }

   }

   public static XML.ObjectLoader getLoader() {
      return new OrbitAppLoader();
   }

   public static void main(String[] var0) {
      AnimationControl var1 = AnimationControl.createApp(new OrbitApp());
      var1.addButton("addMass", "Add Mass", "Adds a mass with the given intial conditions");
      var1.loadXML(var0);
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
