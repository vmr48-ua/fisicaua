package org.opensourcephysics.davidson.qm;

public interface QMWavefunction {
   double[] getRePsi();

   double[] getImPsi();

   double[] getX();

   int getNumpts();

   double getXMin();

   double getXMax();

   void setEnergyScale(double var1);

   double getEnergyScale();
}
