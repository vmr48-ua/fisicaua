package org.apache.commons.math.complex;

import org.apache.commons.math.util.MathUtils;

public class ComplexUtils {
   private ComplexUtils() {
   }

   public static Complex acos(Complex z) {
      return z.isNaN() ? Complex.NaN : Complex.I.negate().multiply(log(z.add(Complex.I.multiply(sqrt1z(z)))));
   }

   public static Complex asin(Complex z) {
      return z.isNaN() ? Complex.NaN : Complex.I.negate().multiply(log(sqrt1z(z).add(Complex.I.multiply(z))));
   }

   public static Complex atan(Complex z) {
      return z.isNaN() ? Complex.NaN : Complex.I.multiply(log(Complex.I.add(z).divide(Complex.I.subtract(z)))).divide(new Complex(2.0D, 0.0D));
   }

   public static Complex cos(Complex z) {
      if (z.isNaN()) {
         return Complex.NaN;
      } else {
         double a = z.getReal();
         double b = z.getImaginary();
         return new Complex(Math.cos(a) * MathUtils.cosh(b), -Math.sin(a) * MathUtils.sinh(b));
      }
   }

   public static Complex cosh(Complex z) {
      if (z.isNaN()) {
         return Complex.NaN;
      } else {
         double a = z.getReal();
         double b = z.getImaginary();
         return new Complex(MathUtils.cosh(a) * Math.cos(b), MathUtils.sinh(a) * Math.sin(b));
      }
   }

   public static Complex exp(Complex z) {
      if (z.isNaN()) {
         return Complex.NaN;
      } else {
         double b = z.getImaginary();
         double expA = Math.exp(z.getReal());
         return new Complex(expA * Math.cos(b), expA * Math.sin(b));
      }
   }

   public static Complex log(Complex z) {
      return z.isNaN() ? Complex.NaN : new Complex(Math.log(z.abs()), Math.atan2(z.getImaginary(), z.getReal()));
   }

   public static Complex polar2Complex(double r, double theta) {
      if (r < 0.0D) {
         throw new IllegalArgumentException("Complex modulus must not be negative");
      } else {
         return new Complex(r * Math.cos(theta), r * Math.sin(theta));
      }
   }

   public static Complex pow(Complex y, Complex x) {
      return exp(x.multiply(log(y)));
   }

   public static Complex sin(Complex z) {
      if (z.isNaN()) {
         return Complex.NaN;
      } else {
         double a = z.getReal();
         double b = z.getImaginary();
         return new Complex(Math.sin(a) * MathUtils.cosh(b), Math.cos(a) * MathUtils.sinh(b));
      }
   }

   public static Complex sinh(Complex z) {
      if (z.isNaN()) {
         return Complex.NaN;
      } else {
         double a = z.getReal();
         double b = z.getImaginary();
         return new Complex(MathUtils.sinh(a) * Math.cos(b), MathUtils.cosh(a) * Math.sin(b));
      }
   }

   public static Complex sqrt(Complex z) {
      if (z.isNaN()) {
         return Complex.NaN;
      } else {
         double a = z.getReal();
         double b = z.getImaginary();
         double t = Math.sqrt((Math.abs(a) + z.abs()) / 2.0D);
         return a >= 0.0D ? new Complex(t, b / (2.0D * t)) : new Complex(Math.abs(b) / (2.0D * t), MathUtils.indicator(b) * t);
      }
   }

   public static Complex sqrt1z(Complex z) {
      return sqrt(Complex.ONE.subtract(z.multiply(z)));
   }

   public static Complex tan(Complex z) {
      if (z.isNaN()) {
         return Complex.NaN;
      } else {
         double a2 = 2.0D * z.getReal();
         double b2 = 2.0D * z.getImaginary();
         double d = Math.cos(a2) + MathUtils.cosh(b2);
         return new Complex(Math.sin(a2) / d, MathUtils.sinh(b2) / d);
      }
   }

   public static Complex tanh(Complex z) {
      if (z.isNaN()) {
         return Complex.NaN;
      } else {
         double a2 = 2.0D * z.getReal();
         double b2 = 2.0D * z.getImaginary();
         double d = MathUtils.cosh(a2) + Math.cos(b2);
         return new Complex(MathUtils.sinh(a2) / d, Math.sin(b2) / d);
      }
   }
}
