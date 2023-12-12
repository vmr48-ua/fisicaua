package org.opensourcephysics.numerics;

public class RK45MultiStep extends RK45 {
   private static int maxMessages = 4;
   private double fixedStepSize = 0.1D;
   protected int maxIterations = 200;

   public RK45MultiStep(ODE var1) {
      super(var1);
   }

   public double step() {
      super.error_code = 0;
      return this.fixedStepSize > 0.0D ? this.fixedStepSize - this.plus() : this.fixedStepSize - this.minus();
   }

   public void setMaxIterations(int var1) {
      this.maxIterations = Math.max(1, var1);
   }

   private double plus() {
      double var1 = this.fixedStepSize;
      if (super.getStepSize() <= 0.0D || super.getStepSize() > this.fixedStepSize || this.fixedStepSize - super.getStepSize() == this.fixedStepSize) {
         super.setStepSize(this.fixedStepSize);
      }

      int var3 = 0;

      while(var1 > super.tol * this.fixedStepSize) {
         ++var3;
         double var4 = var1;
         if (var1 < super.getStepSize()) {
            double var6 = super.getStepSize();
            super.setStepSize(var1);
            double var8 = super.step();
            var1 -= var8;
            super.setStepSize(var6);
         } else {
            var1 -= super.step();
         }

         if (super.error_code != 0 || Math.abs(var4 - var1) <= 1.401298464324817E-45D || super.tol * this.fixedStepSize / 10.0D > super.getStepSize() || var3 > this.maxIterations) {
            super.error_code = 1;
            if (super.enableExceptions) {
               throw new ODESolverException("RK45 ODE solver did not converge.");
            }

            if (maxMessages > 0) {
               --maxMessages;
               if (maxMessages == 0) {
                  System.err.println("Further warnings suppressed.");
               }
            }
            break;
         }
      }

      return var1;
   }

   private double minus() {
      double var1 = this.fixedStepSize;
      if (super.getStepSize() >= 0.0D || super.getStepSize() < this.fixedStepSize || this.fixedStepSize - super.getStepSize() == this.fixedStepSize) {
         super.setStepSize(this.fixedStepSize);
      }

      int var3 = 0;

      while(var1 < super.tol * this.fixedStepSize) {
         ++var3;
         double var4 = var1;
         if (var1 > super.getStepSize()) {
            double var6 = super.getStepSize();
            super.setStepSize(var1);
            double var8 = super.step();
            var1 -= var8;
            super.setStepSize(var6);
         } else {
            var1 -= super.step();
         }

         if (super.error_code != 0 || Math.abs(var4 - var1) <= 1.401298464324817E-45D || super.tol * this.fixedStepSize / 10.0D < super.getStepSize() || var3 > this.maxIterations) {
            super.error_code = 1;
            if (super.enableExceptions) {
               throw new ODESolverException("RK45 ODE solver did not converge.");
            }

            if (maxMessages > 0) {
               --maxMessages;
               System.err.println("Warning: RK45MultiStep did not converge. Remainder=" + var1);
               if (maxMessages == 0) {
                  System.err.println("Further warnings surppressed.");
               }
            }
            break;
         }
      }

      return var1;
   }

   public void setMaximumNumberOfErrorMessages(int var1) {
      maxMessages = var1;
   }

   public void initialize(double var1) {
      this.fixedStepSize = var1;
      super.initialize(var1 / 2.0D);
   }

   public void setStepSize(double var1) {
      maxMessages = 4;
      this.fixedStepSize = var1;
      if (var1 < 0.0D) {
         super.setStepSize(Math.max(-Math.abs(super.getStepSize()), var1));
      } else {
         super.setStepSize(Math.min(super.getStepSize(), var1));
      }

      super.setStepSize(var1);
   }

   public double getStepSize() {
      return this.fixedStepSize;
   }

   public int getErrorCode() {
      return super.error_code;
   }
}
