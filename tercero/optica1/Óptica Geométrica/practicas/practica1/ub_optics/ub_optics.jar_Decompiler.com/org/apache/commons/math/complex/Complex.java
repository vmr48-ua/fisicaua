package org.apache.commons.math.complex;

import java.io.Serializable;
import org.apache.commons.math.util.MathUtils;

public class Complex implements Serializable {
   private static final long serialVersionUID = -6530173849413811929L;
   public static final Complex I = new Complex(0.0D, 1.0D);
   public static final Complex NaN = new Complex(Double.NaN, Double.NaN);
   public static final Complex ONE = new Complex(1.0D, 0.0D);
   public static final Complex ZERO = new Complex(0.0D, 0.0D);
   protected double imaginary;
   protected double real;

   public Complex(double real, double imaginary) {
      this.real = real;
      this.imaginary = imaginary;
   }

   public double abs() {
      if (this.isNaN()) {
         return Double.NaN;
      } else if (this.isInfinite()) {
         return Double.POSITIVE_INFINITY;
      } else {
         double q;
         if (Math.abs(this.real) < Math.abs(this.imaginary)) {
            if (this.imaginary == 0.0D) {
               return Math.abs(this.real);
            } else {
               q = this.real / this.imaginary;
               return Math.abs(this.imaginary) * Math.sqrt(1.0D + q * q);
            }
         } else if (this.real == 0.0D) {
            return Math.abs(this.imaginary);
         } else {
            q = this.imaginary / this.real;
            return Math.abs(this.real) * Math.sqrt(1.0D + q * q);
         }
      }
   }

   public Complex add(Complex rhs) {
      return new Complex(this.real + rhs.getReal(), this.imaginary + rhs.getImaginary());
   }

   public Complex conjugate() {
      return this.isNaN() ? NaN : new Complex(this.real, -this.imaginary);
   }

   public Complex divide(Complex rhs) {
      if (!this.isNaN() && !rhs.isNaN()) {
         double c = rhs.getReal();
         double d = rhs.getImaginary();
         if (c == 0.0D && d == 0.0D) {
            return NaN;
         } else if (rhs.isInfinite() && !this.isInfinite()) {
            return ZERO;
         } else {
            double q;
            double denominator;
            if (Math.abs(c) < Math.abs(d)) {
               if (d == 0.0D) {
                  return new Complex(this.real / c, this.imaginary / c);
               } else {
                  q = c / d;
                  denominator = c * q + d;
                  return new Complex((this.real * q + this.imaginary) / denominator, (this.imaginary * q - this.real) / denominator);
               }
            } else if (c == 0.0D) {
               return new Complex(this.imaginary / d, -this.real / c);
            } else {
               q = d / c;
               denominator = d * q + c;
               return new Complex((this.imaginary * q + this.real) / denominator, (this.imaginary - this.real * q) / denominator);
            }
         }
      } else {
         return NaN;
      }
   }

   public boolean equals(Object other) {
      boolean ret;
      if (this == other) {
         ret = true;
      } else if (other == null) {
         ret = false;
      } else {
         try {
            Complex rhs = (Complex)other;
            if (rhs.isNaN()) {
               ret = this.isNaN();
            } else {
               ret = Double.doubleToRawLongBits(this.real) == Double.doubleToRawLongBits(rhs.getReal()) && Double.doubleToRawLongBits(this.imaginary) == Double.doubleToRawLongBits(rhs.getImaginary());
            }
         } catch (ClassCastException var4) {
            ret = false;
         }
      }

      return ret;
   }

   public int hashCode() {
      return this.isNaN() ? 7 : 37 * (17 * MathUtils.hash(this.imaginary) + MathUtils.hash(this.real));
   }

   public double getImaginary() {
      return this.imaginary;
   }

   public double getReal() {
      return this.real;
   }

   public boolean isNaN() {
      return Double.isNaN(this.real) || Double.isNaN(this.imaginary);
   }

   public boolean isInfinite() {
      return !this.isNaN() && (Double.isInfinite(this.real) || Double.isInfinite(this.imaginary));
   }

   public Complex multiply(Complex rhs) {
      return !this.isNaN() && !rhs.isNaN() ? new Complex(this.real * rhs.real - this.imaginary * rhs.imaginary, this.real * rhs.imaginary + this.imaginary * rhs.real) : NaN;
   }

   public Complex negate() {
      return this.isNaN() ? NaN : new Complex(-this.real, -this.imaginary);
   }

   public Complex subtract(Complex rhs) {
      return !this.isNaN() && !rhs.isNaN() ? new Complex(this.real - rhs.getReal(), this.imaginary - rhs.getImaginary()) : NaN;
   }
}
