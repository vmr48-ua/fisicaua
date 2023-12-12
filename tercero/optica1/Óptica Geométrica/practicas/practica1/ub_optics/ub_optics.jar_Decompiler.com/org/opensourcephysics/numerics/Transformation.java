package org.opensourcephysics.numerics;

public interface Transformation extends Cloneable {
   Object clone();

   double[] direct(double[] var1);

   double[] inverse(double[] var1) throws UnsupportedOperationException;
}
