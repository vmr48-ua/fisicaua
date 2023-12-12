package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.display.ComplexDataset;
import org.opensourcephysics.display.Dataset;

public interface QMSuperposition extends QMWavefunction {
   double[][] getEigenstates();

   double getEigenValue(int var1);

   boolean setCoef(double[] var1, double[] var2);

   double[] getReCoef();

   double[] getImCoef();

   void update(double var1);

   ComplexDataset getPsi(ComplexDataset var1);

   Dataset getRho(Dataset var1);
}
