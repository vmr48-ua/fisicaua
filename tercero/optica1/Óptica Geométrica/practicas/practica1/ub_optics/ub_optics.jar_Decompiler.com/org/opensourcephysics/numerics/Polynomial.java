package org.opensourcephysics.numerics;

import java.util.ArrayList;

public class Polynomial implements Function {
   protected double[] coefficients;

   public Polynomial(double[] var1) {
      this.coefficients = var1;
   }

   public double[] getCoefficients() {
      return (double[])((double[])this.coefficients.clone());
   }

   public Polynomial(String[] var1) {
      this.coefficients = new double[var1.length];
      int var2 = 0;

      for(int var3 = var1.length; var2 < var3; ++var2) {
         try {
            this.coefficients[var2] = Double.parseDouble(var1[var2]);
         } catch (NumberFormatException var5) {
            this.coefficients[var2] = 0.0D;
         }
      }

   }

   public static double evalPolynomial(double var0, double[] var2) {
      int var3 = var2.length - 1;
      double var4 = var2[var3];

      for(int var6 = var3 - 1; var6 >= 0; --var6) {
         var4 = var2[var6] + var4 * var0;
      }

      return var4;
   }

   public Polynomial add(double var1) {
      int var3 = this.coefficients.length;
      double[] var4 = new double[var3];
      var4[0] = this.coefficients[0] + var1;

      for(int var5 = 1; var5 < var3; ++var5) {
         var4[var5] = this.coefficients[var5];
      }

      return new Polynomial(var4);
   }

   public Polynomial add(Polynomial var1) {
      int var2 = Math.max(var1.degree(), this.degree()) + 1;
      double[] var3 = new double[var2];

      for(int var4 = 0; var4 < var2; ++var4) {
         var3[var4] = this.coefficient(var4) + var1.coefficient(var4);
      }

      return new Polynomial(var3);
   }

   public double coefficient(int var1) {
      return var1 < this.coefficients.length ? this.coefficients[var1] : 0.0D;
   }

   public Polynomial deflate(double var1) {
      int var3 = this.degree();
      double var4 = this.coefficients[var3];
      double[] var6 = new double[var3];

      for(int var7 = var3 - 1; var7 >= 0; --var7) {
         var6[var7] = var4;
         var4 = var4 * var1 + this.coefficients[var7];
      }

      return new Polynomial(var6);
   }

   public int degree() {
      return this.coefficients.length - 1;
   }

   public Polynomial derivative() {
      int var1 = this.degree();
      double[] var2;
      if (var1 == 0) {
         var2 = new double[]{0.0D};
         return new Polynomial(var2);
      } else {
         var2 = new double[var1];

         for(int var3 = 1; var3 <= var1; ++var3) {
            var2[var3 - 1] = this.coefficients[var3] * (double)var3;
         }

         return new Polynomial(var2);
      }
   }

   public Polynomial divide(double var1) {
      return this.multiply(1.0D / var1);
   }

   public Polynomial divide(Polynomial var1) {
      return this.divideWithRemainder(var1)[0];
   }

   public Polynomial[] divideWithRemainder(Polynomial var1) {
      Polynomial[] var2 = new Polynomial[2];
      int var3 = this.degree();
      int var4 = var1.degree();
      double[] var5;
      if (var3 < var4) {
         var5 = new double[]{0.0D};
         var2[0] = new Polynomial(var5);
         var2[1] = var1;
         return var2;
      } else {
         var5 = new double[var3 - var4 + 1];
         double[] var6 = new double[var3 + 1];

         for(int var7 = 0; var7 <= var3; ++var7) {
            var6[var7] = this.coefficients[var7];
         }

         double var11 = 1.0D / var1.coefficient(var4);

         int var10;
         for(int var9 = var3 - var4; var9 >= 0; --var9) {
            var5[var9] = var6[var4 + var9] * var11;

            for(var10 = var4 + var9 - 1; var10 >= var9; --var10) {
               var6[var10] -= var5[var9] * var1.coefficient(var10 - var9);
            }
         }

         double[] var12 = new double[var4];

         for(var10 = 0; var10 < var4; ++var10) {
            var12[var10] = var6[var10];
         }

         var2[0] = new Polynomial(var5);
         var2[1] = new Polynomial(var12);
         return var2;
      }
   }

   public Polynomial integral() {
      return this.integral(0.0D);
   }

   public Polynomial integral(double var1) {
      int var3 = this.coefficients.length + 1;
      double[] var4 = new double[var3];
      var4[0] = var1;

      for(int var5 = 1; var5 < var3; ++var5) {
         var4[var5] = this.coefficients[var5 - 1] / (double)var5;
      }

      return new Polynomial(var4);
   }

   public Polynomial multiply(double var1) {
      int var3 = this.coefficients.length;
      double[] var4 = new double[var3];

      for(int var5 = 0; var5 < var3; ++var5) {
         var4[var5] = this.coefficients[var5] * var1;
      }

      return new Polynomial(var4);
   }

   public Polynomial multiply(Polynomial var1) {
      int var2 = var1.degree() + this.degree();
      double[] var3 = new double[var2 + 1];

      for(int var4 = 0; var4 <= var2; ++var4) {
         var3[var4] = 0.0D;

         for(int var5 = 0; var5 <= var4; ++var5) {
            var3[var4] += var1.coefficient(var5) * this.coefficient(var4 - var5);
         }
      }

      return new Polynomial(var3);
   }

   public double[] roots() {
      return this.roots(Util.defaultNumericalPrecision);
   }

   public double[] roots(double var1) {
      double var3 = 0.0D;
      if (this.degree() < 1) {
         return new double[0];
      } else {
         Polynomial var5 = this.derivative();

         int var6;
         for(var6 = 0; var6 < 100 && Math.abs(var5.evaluate(var3)) < var1; ++var6) {
            var3 = Math.random();
         }

         Polynomial var7 = this;
         ArrayList var8 = new ArrayList(this.degree());

         while(true) {
            double var9 = Root.newton(var7, var5, var3, var1);
            if (Double.isNaN(var9)) {
               break;
            }

            var8.add(new Double(var9));
            var7 = var7.deflate(var9);
            if (var7.degree() == 0) {
               break;
            }

            var5 = var7.derivative();
            var3 = 0.0D;

            for(var6 = 0; var6 < 100 && Math.abs(var5.evaluate(var3)) < var1; ++var6) {
               var3 = Math.random();
            }
         }

         double[] var12 = new double[var8.size()];
         int var10 = 0;

         for(int var11 = var8.size(); var10 < var11; ++var10) {
            var12[var10] = (Double)var8.get(var10);
         }

         return var12;
      }
   }

   public Polynomial subtract(double var1) {
      return this.add(-var1);
   }

   public Polynomial subtract(Polynomial var1) {
      int var2 = Math.max(var1.degree(), this.degree()) + 1;
      double[] var3 = new double[var2];

      for(int var4 = 0; var4 < var2; ++var4) {
         var3[var4] = this.coefficient(var4) - var1.coefficient(var4);
      }

      return new Polynomial(var3);
   }

   public String toString() {
      if (this.coefficients != null && this.coefficients.length >= 1) {
         StringBuffer var1 = new StringBuffer();
         boolean var2 = false;
         int var3 = 0;

         for(int var4 = this.coefficients.length; var3 < var4; ++var3) {
            if (this.coefficients[var3] != 0.0D) {
               if (var2) {
                  var1.append(this.coefficients[var3] > 0.0D ? " + " : " ");
               } else {
                  var2 = true;
               }

               if (var3 == 0 || this.coefficients[var3] != 1.0D) {
                  var1.append(Double.toString(this.coefficients[var3]));
               }

               if (var3 > 0) {
                  var1.append(" x^" + var3);
               }
            }
         }

         String var5 = var1.toString();
         if (var5.equals("")) {
            return "0";
         } else {
            return var5;
         }
      } else {
         return "Polynomial coefficients are undefined.";
      }
   }

   public double evaluate(double var1) {
      int var3 = this.coefficients.length;
      --var3;

      double var10000;
      double var4;
      for(var4 = this.coefficients[var3]; var3 > 0; var4 = var10000 + this.coefficients[var3]) {
         var10000 = var4 * var1;
         --var3;
      }

      return var4;
   }

   public double[] valueAndDerivative(double var1) {
      int var3 = this.coefficients.length;
      double[] var4 = new double[2];
      --var3;
      var4[0] = this.coefficients[var3];

      double var10002;
      for(var4[1] = 0.0D; var3 > 0; var4[0] = var10002 + this.coefficients[var3]) {
         var4[1] = var4[1] * var1 + var4[0];
         var10002 = var4[0] * var1;
         --var3;
      }

      return var4;
   }
}
