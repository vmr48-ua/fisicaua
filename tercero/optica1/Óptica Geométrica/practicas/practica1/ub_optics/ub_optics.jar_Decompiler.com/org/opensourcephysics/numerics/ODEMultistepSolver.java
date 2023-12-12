package org.opensourcephysics.numerics;

import org.opensourcephysics.controls.OSPLog;

public class ODEMultistepSolver implements ODEAdaptiveSolver {
   private static int maxMessages = 4;
   protected int err_code = 0;
   protected int maxIterations = 200;
   protected boolean enableExceptions = false;
   protected String err_msg = "";
   protected ODEAdaptiveSolver odeEngine;
   protected double fixedStepSize = 0.1D;
   protected ODEMultistepSolver.InternalODE internalODE;

   public ODEMultistepSolver(ODE var1) {
      this.odeEngine = new DormandPrince45(this.setODE(var1));
   }

   protected ODEMultistepSolver() {
   }

   protected ODEMultistepSolver.InternalODE setODE(ODE var1) {
      this.internalODE = new ODEMultistepSolver.InternalODE(var1);
      return this.internalODE;
   }

   public static ODEAdaptiveSolver MultistepRK45(ODE var0) {
      ODEMultistepSolver var1 = new ODEMultistepSolver();
      var1.odeEngine = new RK45(var1.setODE(var0));
      return var1;
   }

   public void enableRuntimeExpecptions(boolean var1) {
      this.enableExceptions = var1;
   }

   public void setMaxIterations(int var1) {
      this.maxIterations = Math.max(1, var1);
   }

   public void setTolerance(double var1) {
      this.odeEngine.setTolerance(var1);
   }

   public double getTolerance() {
      return this.odeEngine.getTolerance();
   }

   public int getErrorCode() {
      return this.err_code;
   }

   public double step() {
      this.internalODE.setInitialConditions();
      double var1 = 0.0D;
      if (this.fixedStepSize > 0.0D) {
         var1 = this.plus();
      } else {
         var1 = this.minus();
      }

      if (this.err_code == 0) {
         this.internalODE.update();
         return this.fixedStepSize - var1;
      } else {
         return 0.0D;
      }
   }

   private double plus() {
      double var1 = this.odeEngine.getTolerance();
      double var3 = this.fixedStepSize;
      if (this.odeEngine.getStepSize() <= 0.0D || this.odeEngine.getStepSize() > this.fixedStepSize || this.fixedStepSize - this.odeEngine.getStepSize() == this.fixedStepSize) {
         this.odeEngine.setStepSize(this.fixedStepSize);
      }

      int var5 = 0;

      while(var3 > var1 * this.fixedStepSize) {
         ++var5;
         double var6 = var3;
         if (var3 < this.odeEngine.getStepSize()) {
            double var8 = this.odeEngine.getStepSize();
            this.odeEngine.setStepSize(var3);
            double var10 = this.odeEngine.step();
            var3 -= var10;
            this.odeEngine.setStepSize(var8);
         } else {
            var3 -= this.odeEngine.step();
         }

         if (this.odeEngine.getErrorCode() != 0 || Math.abs(var6 - var3) <= 1.401298464324817E-45D || var1 * this.fixedStepSize / 10.0D > this.odeEngine.getStepSize() || var5 > this.maxIterations) {
            this.err_msg = "ODEMultiStep did not converge. Remainder=" + var3;
            this.err_code = 1;
            if (this.enableExceptions) {
               throw new ODESolverException(this.err_msg);
            }

            if (maxMessages > 0) {
               --maxMessages;
               OSPLog.warning(this.err_msg);
            }
            break;
         }
      }

      return var3;
   }

   private double minus() {
      double var1 = this.odeEngine.getTolerance();
      double var3 = this.fixedStepSize;
      if (this.odeEngine.getStepSize() >= 0.0D || this.odeEngine.getStepSize() < this.fixedStepSize || this.fixedStepSize - this.odeEngine.getStepSize() == this.fixedStepSize) {
         this.odeEngine.setStepSize(this.fixedStepSize);
      }

      int var5 = 0;

      while(true) {
         double var6;
         do {
            if (!(var3 < var1 * this.fixedStepSize)) {
               return var3;
            }

            ++var5;
            var6 = var3;
            if (var3 > this.odeEngine.getStepSize()) {
               double var8 = this.odeEngine.getStepSize();
               this.odeEngine.setStepSize(var3);
               double var10 = this.odeEngine.step();
               var3 -= var10;
               this.odeEngine.setStepSize(var8);
            } else {
               var3 -= this.odeEngine.step();
            }
         } while(this.odeEngine.getErrorCode() == 0 && !(Math.abs(var6 - var3) <= 1.401298464324817E-45D) && !(var1 * this.fixedStepSize / 10.0D < this.odeEngine.getStepSize()) && var5 <= this.maxIterations);

         this.err_msg = "ODEMultiStep did not converge. Remainder=" + var3;
         this.err_code = 1;
         if (this.enableExceptions) {
            throw new ODESolverException(this.err_msg);
         }

         if (maxMessages > 0) {
            --maxMessages;
            OSPLog.warning(this.err_msg);
         }
      }
   }

   public void initialize(double var1) {
      maxMessages = 4;
      this.err_msg = "";
      this.err_code = 0;
      this.fixedStepSize = var1;
      this.internalODE.setInitialConditions();
      this.odeEngine.initialize(var1);
   }

   public void setStepSize(double var1) {
      maxMessages = 4;
      this.fixedStepSize = var1;
      if (var1 < 0.0D) {
         this.odeEngine.setStepSize(Math.max(-Math.abs(this.odeEngine.getStepSize()), var1));
      } else {
         this.odeEngine.setStepSize(Math.min(this.odeEngine.getStepSize(), var1));
      }

   }

   public void setMaximumNumberOfErrorMessages(int var1) {
      maxMessages = var1;
   }

   public double getStepSize() {
      return this.fixedStepSize;
   }

   protected final class InternalODE implements ODE {
      private ODE ode;
      private double[] engineState = new double[0];

      InternalODE(ODE var2) {
         this.ode = var2;
         this.setInitialConditions();
      }

      public void getRate(double[] var1, double[] var2) {
         this.ode.getRate(var1, var2);
      }

      public double[] getState() {
         return this.engineState;
      }

      public void setInitialConditions() {
         double[] var1 = this.ode.getState();
         if (var1 != null) {
            if (this.engineState == null || this.engineState.length != var1.length) {
               this.engineState = new double[var1.length];
            }

            System.arraycopy(var1, 0, this.engineState, 0, var1.length);
         }
      }

      public void update() {
         System.arraycopy(this.engineState, 0, this.ode.getState(), 0, this.engineState.length);
      }
   }
}
