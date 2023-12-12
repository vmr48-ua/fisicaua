package org.opensourcephysics.numerics;

public interface ODEAdaptiveSolver extends ODESolver {
   int NO_ERROR = 0;
   int DID_NOT_CONVERGE = 1;
   int BISECTION_EVENT_NOT_FOUND = 2;

   void setTolerance(double var1);

   double getTolerance();

   int getErrorCode();
}
