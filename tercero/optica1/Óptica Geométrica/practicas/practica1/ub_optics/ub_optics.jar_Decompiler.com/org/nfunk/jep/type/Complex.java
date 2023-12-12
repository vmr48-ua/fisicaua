package org.nfunk.jep.type;

public class Complex {
   private double re;
   private double im;

   public Complex() {
      this.re = 0.0D;
      this.im = 0.0D;
   }

   public Complex(double var1) {
      this.re = var1;
      this.im = 0.0D;
   }

   public Complex(Number var1) {
      this.re = var1.doubleValue();
      this.im = 0.0D;
   }

   public Complex(Complex var1) {
      this.re = var1.re;
      this.im = var1.im;
   }

   public Complex(double var1, double var3) {
      this.re = var1;
      this.im = var3;
   }

   public double re() {
      return this.re;
   }

   public double im() {
      return this.im;
   }

   public void set(Complex var1) {
      this.re = var1.re;
      this.im = var1.im;
   }

   public void set(double var1, double var3) {
      this.re = var1;
      this.im = var3;
   }

   public void setRe(double var1) {
      this.re = var1;
   }

   public void setIm(double var1) {
      this.im = var1;
   }

   public boolean equals(Complex var1, double var2) {
      double var4 = this.re - var1.re;
      double var6 = this.im - var1.im;
      return var4 * var4 + var6 * var6 <= var2 * var2;
   }

   public String toString() {
      return "(" + this.re + ", " + this.im + ")";
   }

   public double abs() {
      double var1 = Math.abs(this.re);
      double var3 = Math.abs(this.im);
      if (var1 == 0.0D && var3 == 0.0D) {
         return 0.0D;
      } else {
         double var5;
         if (var1 > var3) {
            var5 = var3 / var1;
            return var1 * Math.sqrt(1.0D + var5 * var5);
         } else {
            var5 = var1 / var3;
            return var3 * Math.sqrt(1.0D + var5 * var5);
         }
      }
   }

   public double abs2() {
      return this.re * this.re + this.im * this.im;
   }

   public double arg() {
      return Math.atan2(this.im, this.re);
   }

   public Complex neg() {
      return new Complex(-this.re, -this.im);
   }

   public Complex mul(double var1) {
      return new Complex(this.re * var1, this.im * var1);
   }

   public Complex mul(Complex var1) {
      return new Complex(this.re * var1.re - this.im * var1.im, this.im * var1.re + this.re * var1.im);
   }

   public Complex div(Complex var1) {
      double var2;
      double var4;
      double var6;
      double var8;
      if (Math.abs(var1.re) >= Math.abs(var1.im)) {
         var6 = var1.im / var1.re;
         var8 = var1.re + var6 * var1.im;
         var2 = (this.re + var6 * this.im) / var8;
         var4 = (this.im - var6 * this.re) / var8;
      } else {
         var6 = var1.re / var1.im;
         var8 = var1.im + var6 * var1.re;
         var2 = (this.re * var6 + this.im) / var8;
         var4 = (this.im * var6 - this.re) / var8;
      }

      return new Complex(var2, var4);
   }

   public Complex power(double var1) {
      double var3 = Math.pow(this.abs(), var1);
      boolean var5 = false;
      byte var6 = 0;
      if (this.im == 0.0D && this.re < 0.0D) {
         var5 = true;
         var6 = 2;
      }

      if (this.re == 0.0D && this.im > 0.0D) {
         var5 = true;
         var6 = 1;
      }

      if (this.re == 0.0D && this.im < 0.0D) {
         var5 = true;
         var6 = -1;
      }

      if (var5 && (double)var6 * var1 == (double)((int)((double)var6 * var1))) {
         short[] var10 = new short[]{0, 1, 0, -1};
         short[] var8 = new short[]{1, 0, -1, 0};
         int var9 = (int)((double)var6 * var1) % 4;
         if (var9 < 0) {
            var9 += 4;
         }

         return new Complex(var3 * (double)var8[var9], var3 * (double)var10[var9]);
      } else {
         double var7 = var1 * this.arg();
         return new Complex(var3 * Math.cos(var7), var3 * Math.sin(var7));
      }
   }

   public Complex power(Complex var1) {
      if (var1.im == 0.0D) {
         return this.power(var1.re);
      } else {
         double var2 = Math.log(this.abs());
         double var4 = this.arg();
         double var6 = var2 * var1.re - var4 * var1.im;
         double var8 = var2 * var1.im + var4 * var1.re;
         double var10 = Math.exp(var6);
         return new Complex(var10 * Math.cos(var8), var10 * Math.sin(var8));
      }
   }

   public Complex log() {
      return new Complex(Math.log(this.abs()), this.arg());
   }

   public Complex sqrt() {
      Complex var1;
      if (this.re == 0.0D && this.im == 0.0D) {
         var1 = new Complex(0.0D, 0.0D);
      } else {
         double var2 = Math.abs(this.re);
         double var4 = Math.abs(this.im);
         double var6;
         double var8;
         if (var2 >= var4) {
            var8 = var4 / var2;
            var6 = Math.sqrt(var2) * Math.sqrt(0.5D * (1.0D + Math.sqrt(1.0D + var8 * var8)));
         } else {
            var8 = var2 / var4;
            var6 = Math.sqrt(var4) * Math.sqrt(0.5D * (var8 + Math.sqrt(1.0D + var8 * var8)));
         }

         if (this.re >= 0.0D) {
            var1 = new Complex(var6, this.im / (2.0D * var6));
         } else {
            if (this.im < 0.0D) {
               var6 = -var6;
            }

            var1 = new Complex(this.im / (2.0D * var6), var6);
         }
      }

      return var1;
   }

   public Complex sin() {
      double var1 = -this.im;
      double var3 = this.re;
      double var13 = Math.exp(var1);
      double var5 = var13 * Math.cos(var3);
      double var7 = var13 * Math.sin(var3);
      var13 = Math.exp(-var1);
      double var9 = var13 * Math.cos(-var3);
      double var11 = var13 * Math.sin(-var3);
      var5 -= var9;
      var7 -= var11;
      return new Complex(0.5D * var7, -0.5D * var5);
   }

   public Complex cos() {
      double var1 = -this.im;
      double var3 = this.re;
      double var13 = Math.exp(var1);
      double var5 = var13 * Math.cos(var3);
      double var7 = var13 * Math.sin(var3);
      var13 = Math.exp(-var1);
      double var9 = var13 * Math.cos(-var3);
      double var11 = var13 * Math.sin(-var3);
      var5 += var9;
      var7 += var11;
      return new Complex(0.5D * var5, 0.5D * var7);
   }

   public Complex tan() {
      double var1 = -this.im;
      double var3 = this.re;
      double var13 = Math.exp(var1);
      double var5 = var13 * Math.cos(var3);
      double var7 = var13 * Math.sin(var3);
      var13 = Math.exp(-var1);
      double var9 = var13 * Math.cos(-var3);
      double var11 = var13 * Math.sin(-var3);
      var5 -= var9;
      var7 -= var11;
      Complex var15 = new Complex(0.5D * var5, 0.5D * var7);
      var1 = -this.im;
      var3 = this.re;
      var13 = Math.exp(var1);
      var5 = var13 * Math.cos(var3);
      var7 = var13 * Math.sin(var3);
      var13 = Math.exp(-var1);
      var9 = var13 * Math.cos(-var3);
      var11 = var13 * Math.sin(-var3);
      var5 += var9;
      var7 += var11;
      Complex var16 = new Complex(0.5D * var5, 0.5D * var7);
      return var15.div(var16);
   }

   public Complex asin() {
      double var2 = 1.0D - (this.re * this.re - this.im * this.im);
      double var4 = 0.0D - (this.re * this.im + this.im * this.re);
      Complex var1 = new Complex(var2, var4);
      var1 = var1.sqrt();
      var1.re += -this.im;
      var1.im += this.re;
      var2 = Math.log(var1.abs());
      var4 = var1.arg();
      var1.re = var4;
      var1.im = -var2;
      return var1;
   }

   public Complex acos() {
      double var2 = 1.0D - (this.re * this.re - this.im * this.im);
      double var4 = 0.0D - (this.re * this.im + this.im * this.re);
      Complex var1 = new Complex(var2, var4);
      var1 = var1.sqrt();
      var2 = -var1.im;
      var4 = var1.re;
      var1.re = this.re + var2;
      var1.im = this.im + var4;
      var2 = Math.log(var1.abs());
      var4 = var1.arg();
      var1.re = var4;
      var1.im = -var2;
      return var1;
   }

   public Complex atan() {
      Complex var5 = new Complex(-this.re, 1.0D - this.im);
      double var1 = this.re;
      double var3 = 1.0D + this.im;
      var5 = var5.div(new Complex(var1, var3));
      var1 = Math.log(var5.abs());
      var3 = var5.arg();
      var5.re = 0.5D * var3;
      var5.im = -0.5D * var1;
      return var5;
   }

   public Complex sinh() {
      double var1 = Math.exp(this.re);
      double var3 = var1 * Math.cos(this.im);
      double var5 = var1 * Math.sin(this.im);
      var1 = Math.exp(-this.re);
      double var7 = var1 * Math.cos(-this.im);
      double var9 = var1 * Math.sin(-this.im);
      var3 -= var7;
      var5 -= var9;
      return new Complex(0.5D * var3, 0.5D * var5);
   }

   public Complex cosh() {
      double var1 = Math.exp(this.re);
      double var3 = var1 * Math.cos(this.im);
      double var5 = var1 * Math.sin(this.im);
      var1 = Math.exp(-this.re);
      double var7 = var1 * Math.cos(-this.im);
      double var9 = var1 * Math.sin(-this.im);
      var3 += var7;
      var5 += var9;
      return new Complex(0.5D * var3, 0.5D * var5);
   }

   public Complex tanh() {
      double var1 = Math.exp(this.re);
      double var3 = var1 * Math.cos(this.im);
      double var5 = var1 * Math.sin(this.im);
      var1 = Math.exp(-this.re);
      double var7 = var1 * Math.cos(-this.im);
      double var9 = var1 * Math.sin(-this.im);
      var3 -= var7;
      var5 -= var9;
      Complex var11 = new Complex(0.5D * var3, 0.5D * var5);
      var1 = Math.exp(this.re);
      var3 = var1 * Math.cos(this.im);
      var5 = var1 * Math.sin(this.im);
      var1 = Math.exp(-this.re);
      var7 = var1 * Math.cos(-this.im);
      var9 = var1 * Math.sin(-this.im);
      var3 += var7;
      var5 += var9;
      Complex var12 = new Complex(0.5D * var3, 0.5D * var5);
      return var11.div(var12);
   }

   public Complex asinh() {
      Complex var1 = new Complex(this.re * this.re - this.im * this.im + 1.0D, this.re * this.im + this.im * this.re);
      var1 = var1.sqrt();
      var1.re += this.re;
      var1.im += this.im;
      double var2 = var1.arg();
      var1.re = Math.log(var1.abs());
      var1.im = var2;
      return var1;
   }

   public Complex acosh() {
      Complex var1 = new Complex(this.re * this.re - this.im * this.im - 1.0D, this.re * this.im + this.im * this.re);
      var1 = var1.sqrt();
      var1.re += this.re;
      var1.im += this.im;
      double var2 = var1.arg();
      var1.re = Math.log(var1.abs());
      var1.im = var2;
      return var1;
   }

   public Complex atanh() {
      Complex var5 = new Complex(1.0D + this.re, this.im);
      double var1 = 1.0D - this.re;
      double var3 = -this.im;
      var5 = var5.div(new Complex(var1, var3));
      var1 = Math.log(var5.abs());
      var3 = var5.arg();
      var5.re = 0.5D * var1;
      var5.im = 0.5D * var3;
      return var5;
   }

   public boolean isInfinite() {
      return Double.isInfinite(this.re) || Double.isInfinite(this.im);
   }

   public boolean isNaN() {
      return Double.isNaN(this.re) || Double.isNaN(this.im);
   }
}
