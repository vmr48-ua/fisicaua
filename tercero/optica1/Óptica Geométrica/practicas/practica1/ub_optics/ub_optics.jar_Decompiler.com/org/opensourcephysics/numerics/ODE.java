package org.opensourcephysics.numerics;

public interface ODE {
   double[] getState();

   void getRate(double[] var1, double[] var2);
}
