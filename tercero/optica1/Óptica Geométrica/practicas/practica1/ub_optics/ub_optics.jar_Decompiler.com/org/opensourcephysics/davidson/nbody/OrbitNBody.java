package org.opensourcephysics.davidson.nbody;

import java.awt.Color;
import java.awt.Graphics;
import org.opensourcephysics.display.Arrow;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.numerics.ODE;
import org.opensourcephysics.numerics.ODEAdaptiveSolver;
import org.opensourcephysics.numerics.ODEMultistepSolver;

public class OrbitNBody implements ODE, Drawable {
   int n;
   double[] state = new double[1];
   double[] force = new double[0];
   double[] zeros = new double[1];
   OrbitNBody.Mass[] masses;
   ODEAdaptiveSolver ode_solver = ODEMultistepSolver.MultistepRK45(this);

   public OrbitNBody() {
      this.ode_solver.setTolerance(1.0E-9D);
      this.setNBodies(0);
   }

   void setNBodies(int var1) {
      if (var1 != this.state.length / 4) {
         this.n = var1;
         this.state = new double[4 * var1 + 1];
         this.force = new double[2 * var1];
         this.zeros = new double[2 * var1];
         this.masses = new OrbitNBody.Mass[var1];

         for(int var2 = 0; var2 < var1; ++var2) {
            this.masses[var2] = new OrbitNBody.Mass(1.0D);
         }

         this.ode_solver.initialize(this.ode_solver.getStepSize());
      }

   }

   void initialize(double var1) {
      this.ode_solver.initialize(var1);
      this.computeAcceleration(this.state);

      for(int var3 = 0; var3 < this.n; ++var3) {
         this.masses[var3].clear();
         this.masses[var3].setXY(this.state[4 * var3], this.state[4 * var3 + 2], this.force[2 * var3], this.force[2 * var3 + 1]);
      }

   }

   void computeAcceleration(double[] var1) {
      System.arraycopy(this.zeros, 0, this.force, 0, this.force.length);

      for(int var2 = 0; var2 < this.n; ++var2) {
         for(int var3 = var2 + 1; var3 < this.n; ++var3) {
            double var4 = var1[4 * var2] - var1[4 * var3];
            double var6 = var1[4 * var2 + 2] - var1[4 * var3 + 2];
            double var8 = var4 * var4 + var6 * var6;
            double var10 = var8 * Math.sqrt(var8);
            double var12 = var4 / var10;
            double var14 = var6 / var10;
            double[] var10000 = this.force;
            var10000[2 * var2] -= this.masses[var3].m * var12;
            var10000 = this.force;
            var10000[2 * var2 + 1] -= this.masses[var3].m * var14;
            var10000 = this.force;
            var10000[2 * var3] += this.masses[var2].m * var12;
            var10000 = this.force;
            var10000[2 * var3 + 1] += this.masses[var2].m * var14;
         }
      }

   }

   public void advanceTime() {
      this.ode_solver.step();

      for(int var1 = 0; var1 < this.n; ++var1) {
         this.masses[var1].setXY(this.state[4 * var1], this.state[4 * var1 + 2], this.force[2 * var1], this.force[2 * var1 + 1]);
      }

   }

   public void getRate(double[] var1, double[] var2) {
      this.computeAcceleration(var1);

      for(int var3 = 0; var3 < this.n; ++var3) {
         int var4 = 4 * var3;
         var2[var4] = var1[var4 + 1];
         var2[var4 + 1] = this.force[2 * var3];
         var2[var4 + 2] = var1[var4 + 3];
         var2[var4 + 3] = this.force[2 * var3 + 1];
      }

      var2[var1.length - 1] = 1.0D;
   }

   public double getTime() {
      return this.state[this.state.length - 1];
   }

   public double[] getState() {
      return this.state;
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      for(int var3 = 0; var3 < this.n; ++var3) {
         this.masses[var3].draw(var1, var2);
      }

   }

   class Mass {
      double m = 1.0D;
      Trail trail = new Trail();
      Arrow arrow = new Arrow(0.0D, 0.0D, 0.0D, 0.0D);

      Mass(double var2) {
         this.m = var2;
         this.trail.color = Color.darkGray;
      }

      void clear() {
         this.trail.clear();
      }

      public void setXY(double var1, double var3, double var5, double var7) {
         this.arrow.setXY(var1, var3);
         this.arrow.setXlength(this.m * var5 / 10.0D);
         this.arrow.setYlength(this.m * var7 / 10.0D);
         this.trail.addPoint(var1, var3);
      }

      public void setArrow(double var1, double var3, double var5, double var7) {
         this.arrow.setXY(var1, var3);
         this.arrow.setXlength(this.m * var5 / 10.0D);
         this.arrow.setYlength(this.m * var7 / 10.0D);
         this.trail.moveToPoint(var1, var3);
      }

      public void draw(DrawingPanel var1, Graphics var2) {
         this.trail.draw(var1, var2);
         this.arrow.draw(var1, var2);
      }
   }
}
