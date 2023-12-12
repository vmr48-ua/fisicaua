package org.opensourcephysics.davidson.gravitation;

import java.awt.Graphics;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.InteractiveCircle;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.numerics.ODE;
import org.opensourcephysics.numerics.ODESolver;
import org.opensourcephysics.numerics.RK45MultiStep;

public class TrajectoryClassical extends InteractiveCircle implements ODE {
   static final double GM = 1.0D;
   double[] state = new double[]{0.0D, 0.0D, 0.0D, 0.0D, 0.0D};
   ODESolver ode_solver = new RK45MultiStep(this);
   Trail trail = new Trail();
   double vt = 1.0D;

   public TrajectoryClassical() {
      super.pixRadius = 4;
      this.setXY(2.0D, 0.0D);
   }

   double getTime() {
      return this.state[4];
   }

   public double[] getState() {
      return this.state;
   }

   public void getRate(double[] var1, double[] var2) {
      double var3 = var1[0];
      var2[0] = var1[1];
      var2[1] = var3 * var1[3] * var1[3] - 1.0D / var3 / var3;
      var2[2] = var1[3];
      var2[3] = -2.0D * var1[3] * var1[1] / var3;
      var2[4] = 1.0D;
   }

   public void setXY(double var1, double var3) {
      super.setXY(var1, var3);
      this.initialize(Math.sqrt(var1 * var1 + var3 * var3), Math.atan2(var3, var1), this.vt);
   }

   void initialize(double var1, double var3, double var5) {
      this.vt = var5;
      super.x = var1 * Math.cos(var3);
      super.y = var1 * Math.sin(var3);
      this.trail.clear();
      this.trail.addPoint(super.x, super.y);
      this.state[0] = Math.sqrt(super.x * super.x + super.y * super.y);
      this.state[1] = 0.0D;
      this.state[2] = Math.atan2(super.y, super.x);
      this.state[3] = this.vt / this.state[0];
   }

   public void stepTime() {
      this.ode_solver.step();
      super.x = this.state[0] * Math.cos(this.state[2]);
      super.y = this.state[0] * Math.sin(this.state[2]);
      this.trail.addPoint(super.x, super.y);
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      this.trail.draw(var1, var2);
      super.draw(var1, var2);
   }
}
