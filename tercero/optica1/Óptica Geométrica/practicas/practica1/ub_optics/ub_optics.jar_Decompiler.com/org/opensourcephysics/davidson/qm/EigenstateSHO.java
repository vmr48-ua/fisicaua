package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.numerics.Factorials;
import org.opensourcephysics.numerics.Function;
import org.opensourcephysics.numerics.Hermite;
import org.opensourcephysics.numerics.Polynomial;

public class EigenstateSHO implements Function {
   static final double PISQRT = Math.sqrt(3.141592653589793D);
   int n;
   Polynomial hermite;

   public EigenstateSHO(int var1) {
      this.hermite = Hermite.getPolynomial(var1);
      this.n = var1;
      double var2 = Math.sqrt(Math.pow(2.0D, (double)var1) * Factorials.factorial(var1) * PISQRT);
      this.hermite = this.hermite.divide(var2);
   }

   public double evaluate(double var1) {
      return Math.exp(-var1 * var1 / 2.0D) * this.hermite.evaluate(var1);
   }

   public String toString() {
      return "exp(-x*x)*(" + this.hermite.toString() + ")";
   }

   public static void main(String[] var0) {
      EigenstateSHO var1 = new EigenstateSHO(3);
      System.out.println("eigenstate= " + var1);
      System.out.println("\nHermite polynomials");

      for(int var2 = 0; var2 < 5; ++var2) {
         System.out.println(Hermite.getPolynomial(var2));
      }

   }
}
