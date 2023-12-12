package org.opensourcephysics.davidson.gravitation;

import java.awt.Graphics;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.numerics.ODE;
import org.opensourcephysics.numerics.ODESolver;
import org.opensourcephysics.numerics.RK45MultiStep;

public class Planet extends Circle implements ODE {
   static final double GM = 39.47841760435743D;
   double[] state = new double[]{0.0D, 0.0D, 0.0D, 0.0D, 0.0D};
   ODESolver ode_solver = new RK45MultiStep(this);
   Trail trail = new Trail();

   public Planet() {
      super(0.0D, 0.0D);
      super.pixRadius = 4;
   }

   public double[] getState() {
      return this.state;
   }

   public void getRate(double[] var1, double[] var2) {
      double var3 = var1[0] * var1[0] + var1[2] * var1[2];
      double var5 = var3 * Math.sqrt(var3);
      var2[0] = var1[1];
      var2[1] = var5 == 0.0D ? 0.0D : -39.47841760435743D * var1[0] / var5;
      var2[2] = var1[3];
      var2[3] = var5 == 0.0D ? 0.0D : -39.47841760435743D * var1[2] / var5;
      var2[4] = 1.0D;
   }

   void initialize(double var1, double var3, double var5, double var7) {
      super.x = var1;
      super.y = var5;
      this.trail.clear();
      this.trail.addPoint(var1, var5);
      this.state[0] = var1;
      this.state[1] = var3;
      this.state[2] = var5;
      this.state[3] = var7;
      this.state[4] = 0.0D;
   }

   public void stepTime() {
      this.ode_solver.step();
      super.x = this.state[0];
      super.y = this.state[2];
      this.trail.addPoint(super.x, super.y);
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      this.trail.draw(var1, var2);
      super.draw(var1, var2);
   }

   public double getEnergy() {
      double var1 = 0.5D * (this.state[1] * this.state[1] + this.state[3] * this.state[3]);
      double var3 = Math.sqrt(this.state[0] * this.state[0] + this.state[2] * this.state[2]);
      double var5 = var3 == 0.0D ? 0.0D : -39.47841760435743D / var3;
      return var5 + var1;
   }
}
