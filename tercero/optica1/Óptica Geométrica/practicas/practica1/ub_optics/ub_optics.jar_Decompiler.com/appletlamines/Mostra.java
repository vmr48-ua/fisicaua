package appletlamines;

import org.apache.commons.math.complex.Complex;
import org.apache.commons.math.complex.ComplexUtils;

public class Mostra {
   public double thick = 1.0E-7D;
   public double thick_subs = 0.001D;
   public double lambda = 6.33E-7D;
   public double angle;
   public double A = 2.71D;
   public double B = 26400.0D;
   public double C = 1.3E8D;
   public double k0 = 0.0D;
   public double D = 2253.4D;
   public double As = 1.51D;
   public double Bs = 2360.0D;
   public double Cs = 1.39E8D;
   public double k0s = 0.0D;
   public double Ds = 285.22D;
   public boolean flag_substrate = true;
   public double thick_max = 3.0E-6D;
   Complex exponent2 = new Complex(-2.0D, 0.0D);

   public double n(double lambda) {
      if (lambda <= 0.0D) {
         return 1.0D;
      } else {
         double aux = 1.0D / (lambda * lambda * 1.0E18D);
         aux = this.A + this.B * aux + this.C * aux * aux;
         return aux;
      }
   }

   public double k(double lambda) {
      if (lambda <= 0.0D) {
         return 0.0D;
      } else {
         double aux = 1.0D / (lambda * 1.0E9D);
         aux = this.k0 * Math.exp(this.D * aux);
         return aux;
      }
   }

   public double ns(double lambda) {
      if (lambda <= 0.0D) {
         return 1.0D;
      } else {
         double aux = 1.0D / (lambda * lambda * 1.0E18D);
         aux = this.As + this.Bs * aux + this.Cs * aux * aux;
         return aux;
      }
   }

   public double ks(double lambda) {
      if (lambda <= 0.0D) {
         return 0.0D;
      } else {
         double aux = 1.0D / (lambda * 1.0E9D);
         aux = this.k0s * Math.exp(this.Ds * aux);
         return aux;
      }
   }

   public double getR1(char pol) {
      if (this.angle >= 1.5707963267948966D) {
         return 1.0D;
      } else {
         Complex r01 = this.r_ab(1.0D, 0.0D, this.n(this.lambda), this.k(this.lambda), 1.0D, this.angle, pol);
         Complex r12 = this.r_ab(this.n(this.lambda), this.k(this.lambda), this.ns(this.lambda), this.ks(this.lambda), 1.0D, this.angle, pol);
         Complex phaseth = this.Phase(this.n(this.lambda), this.ks(this.lambda), this.thick, 1.0D, this.angle);
         phaseth = ComplexUtils.pow(phaseth, this.exponent2);
         Complex r = r01.add(r12.multiply(phaseth)).divide((new Complex(1.0D, 0.0D)).add(r01.multiply(r12.multiply(phaseth))));
         return Math.pow(r.abs(), 2.0D);
      }
   }

   public void getRT1(double[] RT, char pol) {
      if (this.angle >= 1.5707963267948966D) {
         RT[0] = 1.0D;
         RT[1] = 0.0D;
      } else {
         Complex r01 = this.r_ab(1.0D, 0.0D, this.n(this.lambda), this.k(this.lambda), 1.0D, this.angle, pol);
         Complex r12 = this.r_ab(this.n(this.lambda), this.k(this.lambda), this.ns(this.lambda), this.ks(this.lambda), 1.0D, this.angle, pol);
         Complex phaseth = this.Phase(this.n(this.lambda), this.k(this.lambda), this.thick, 1.0D, this.angle);
         Complex t01 = this.t_ab(1.0D, 0.0D, this.n(this.lambda), this.k(this.lambda), 1.0D, this.angle, pol);
         Complex t12 = this.t_ab(this.n(this.lambda), this.k(this.lambda), this.ns(this.lambda), this.ks(this.lambda), 1.0D, this.angle, pol);
         Complex t = t01.multiply(t12).divide(phaseth.add(r01.multiply(r12.multiply(phaseth.conjugate()))));
         phaseth = ComplexUtils.pow(phaseth, this.exponent2);
         Complex r = r01.add(r12.multiply(phaseth)).divide((new Complex(1.0D, 0.0D)).add(r01.multiply(r12.multiply(phaseth))));
         RT[0] = Math.pow(r.abs(), 2.0D);
         RT[1] = Math.pow(t.abs(), 2.0D);
      }
   }

   public void getRT1i(double[] RT, char pol) {
      if (this.angle >= 1.5707963267948966D) {
         RT[0] = 1.0D;
         RT[1] = 0.0D;
      } else {
         Complex r01 = this.r_ab(this.ns(this.lambda), this.ks(this.lambda), this.n(this.lambda), this.k(this.lambda), 1.0D, this.angle, pol);
         Complex r12 = this.r_ab(this.n(this.lambda), this.k(this.lambda), 1.0D, 0.0D, 1.0D, this.angle, pol);
         Complex phaseth = this.Phase(this.n(this.lambda), this.k(this.lambda), this.thick, 1.0D, this.angle);
         Complex t01 = this.t_ab(this.ns(this.lambda), this.ks(this.lambda), this.n(this.lambda), this.k(this.lambda), 1.0D, this.angle, pol);
         Complex t12 = this.t_ab(this.n(this.lambda), this.k(this.lambda), 1.0D, 0.0D, 1.0D, this.angle, pol);
         Complex t = t01.multiply(t12).divide(phaseth.add(r01.multiply(r12.multiply(phaseth.conjugate()))));
         phaseth = ComplexUtils.pow(phaseth, this.exponent2);
         Complex r = r01.add(r12.multiply(phaseth)).divide((new Complex(1.0D, 0.0D)).add(r01.multiply(r12.multiply(phaseth))));
         RT[0] = Math.pow(r.abs(), 2.0D);
         RT[1] = Math.pow(t.abs(), 2.0D);
      }
   }

   public void getRT2(double[] RT, char pol) {
      if (this.angle >= 1.5707963267948966D) {
         RT[0] = 1.0D;
         RT[1] = 0.0D;
      } else {
         Complex r = this.r_ab(this.ns(this.lambda), this.ks(this.lambda), 1.0D, 0.0D, 1.0D, this.angle, pol);
         Complex t = this.t_ab(this.ns(this.lambda), this.ks(this.lambda), 1.0D, 0.0D, 1.0D, this.angle, pol);
         RT[0] = Math.pow(r.abs(), 2.0D);
         RT[1] = Math.pow(t.abs(), 2.0D);
      }
   }

   public void getRT(double[] RT, char pol) {
      if (this.angle >= 1.5707963267948966D) {
         RT[0] = 1.0D;
         RT[1] = 0.0D;
      } else {
         double[] RT1 = new double[2];
         double[] RT2 = new double[2];
         double[] RT1i = new double[2];
         this.getRT1(RT1, pol);
         this.getRT1i(RT1i, pol);
         this.getRT2(RT2, pol);
         RT[0] = RT1[0] + RT1[1] * RT1i[1] * RT2[0] / (1.0D - RT1i[0] * RT2[0]);
         RT[1] = RT1[1] * RT2[1] / (1.0D - RT1i[0] * RT2[0]);
      }
   }

   public Complex r_ab(double na, double ka, double nb, double kb, double n0, double angle, char pol) {
      new Complex(0.0D, 0.0D);
      new Complex(0.0D, 0.0D);
      new Complex(0.0D, 0.0D);
      new Complex(0.0D, 0.0D);
      double p = 1.0D + (ka * ka - na * na) * Math.pow(n0 * Math.sin(angle) / (na * na + ka * ka), 2.0D);
      double q = -2.0D * na * ka * Math.pow(n0 * Math.sin(angle) / (na * na + ka * ka), 2.0D);
      Complex CosTheta_a = new Complex(Math.sqrt((Math.sqrt(p * p + q * q) + p) / 2.0D), -Math.sqrt((Math.sqrt(p * p + q * q) - p) / 2.0D));
      p = 1.0D + (kb * kb - nb * nb) * Math.pow(n0 * Math.sin(angle) / (nb * nb + kb * kb), 2.0D);
      q = -2.0D * nb * kb * Math.pow(n0 * Math.sin(angle) / (nb * nb + kb * kb), 2.0D);
      Complex CosTheta_b = new Complex(Math.sqrt((Math.sqrt(p * p + q * q) + p) / 2.0D), -Math.sqrt((Math.sqrt(p * p + q * q) - p) / 2.0D));
      Complex Na;
      Complex Nb;
      if (pol == 's') {
         Na = (new Complex(na, -ka)).multiply(CosTheta_a);
         Nb = (new Complex(nb, -kb)).multiply(CosTheta_b);
      } else {
         Na = (new Complex(na, -ka)).divide(CosTheta_a);
         Nb = (new Complex(nb, -kb)).divide(CosTheta_b);
      }

      return Na.subtract(Nb).divide(Nb.add(Na));
   }

   public Complex Phase(double na, double ka, double thickness, double n0, double angle) {
      new Complex(0.0D, 0.0D);
      double p = 1.0D + (ka * ka - na * na) * Math.pow(n0 * Math.sin(angle) / (na * na + ka * ka), 2.0D);
      double q = -2.0D * na * ka * Math.pow(n0 * Math.sin(angle) / (na * na + ka * ka), 2.0D);
      Complex CosTheta = new Complex(Math.sqrt((Math.sqrt(p * p + q * q) + p) / 2.0D), -Math.sqrt((Math.sqrt(p * p + q * q) - p) / 2.0D));
      Complex Na = new Complex(na, -ka);
      Complex I = new Complex(0.0D, 1.0D);
      Complex Beta = (new Complex(6.283185307179586D * thickness / this.lambda, 0.0D)).multiply(Na);
      Beta = Beta.multiply(CosTheta);
      return ComplexUtils.exp(I.multiply(Beta));
   }

   public Complex t_ab(double na, double ka, double nb, double kb, double n0, double angle, char pol) {
      new Complex(0.0D, 0.0D);
      new Complex(0.0D, 0.0D);
      new Complex(0.0D, 0.0D);
      new Complex(0.0D, 0.0D);
      double p = 1.0D + (ka * ka - na * na) * Math.pow(n0 * Math.sin(angle) / (na * na + ka * ka), 2.0D);
      double q = -2.0D * na * ka * Math.pow(n0 * Math.sin(angle) / (na * na + ka * ka), 2.0D);
      Complex CosTheta_a = new Complex(Math.sqrt((Math.sqrt(p * p + q * q) + p) / 2.0D), -Math.sqrt((Math.sqrt(p * p + q * q) - p) / 2.0D));
      p = 1.0D + (kb * kb - nb * nb) * Math.pow(n0 * Math.sin(angle) / (nb * nb + kb * kb), 2.0D);
      q = -2.0D * nb * kb * Math.pow(n0 * Math.sin(angle) / (nb * nb + kb * kb), 2.0D);
      Complex CosTheta_b = new Complex(Math.sqrt((Math.sqrt(p * p + q * q) + p) / 2.0D), -Math.sqrt((Math.sqrt(p * p + q * q) - p) / 2.0D));
      Complex Na;
      Complex Nb;
      if (pol == 's') {
         Na = (new Complex(na, -ka)).multiply(CosTheta_a);
         Nb = (new Complex(nb, -kb)).multiply(CosTheta_b);
      } else {
         Na = (new Complex(na, -ka)).divide(CosTheta_a);
         Nb = (new Complex(nb, -kb)).divide(CosTheta_b);
      }

      return (new Complex(2.0D, 0.0D)).multiply(Na).divide(Na.add(Nb));
   }

   public double[][] getSpectrumRT(double lambdai, double lambdaf, int numint) {
      double[][] SpectrumRT = new double[2][numint + 1];
      double[] RT = new double[2];
      double auxlambda = this.lambda;

      for(int i = 0; i <= numint; ++i) {
         this.lambda = 1.0D / lambdai + (1.0D / lambdaf - 1.0D / lambdai) / (double)numint * (double)i;
         this.lambda = 1.0D / this.lambda;
         this.getRT(RT, 's');
         SpectrumRT[0][i] = 0.5D * RT[0];
         SpectrumRT[1][i] = 0.5D * RT[1];
         this.getRT(RT, 'p');
         SpectrumRT[0][i] += 0.5D * RT[0];
         SpectrumRT[1][i] += 0.5D * RT[1];
      }

      this.lambda = auxlambda;
      return SpectrumRT;
   }

   public void UpdateSubstrate(boolean flag_subs) {
      this.flag_substrate = flag_subs;
      if (this.flag_substrate) {
         this.As = 1.51D;
         this.Bs = 2360.0D;
         this.Cs = 1.39E8D;
         this.k0s = 0.0D;
         this.Ds = 285.22D;
      } else {
         this.As = 1.0D;
         this.Bs = this.Cs = this.k0s = 0.0D;
      }

   }
}
