package org.opensourcephysics.numerics;

public interface ODESolver {
   void initialize(double var1);

   double step();

   void setStepSize(double var1);

   double getStepSize();
}
