package org.apache.commons.math.util;

public final class MathUtils {
   private static final byte NB = -1;
   private static final short NS = -1;
   private static final byte PB = 1;
   private static final short PS = 1;
   private static final byte ZB = 0;
   private static final short ZS = 0;

   private MathUtils() {
   }

   public static int addAndCheck(int x, int y) {
      long s = (long)x + (long)y;
      if (s >= -2147483648L && s <= 2147483647L) {
         return (int)s;
      } else {
         throw new ArithmeticException("overflow: add");
      }
   }

   public static long binomialCoefficient(int n, int k) {
      if (n < k) {
         throw new IllegalArgumentException("must have n >= k for binomial coefficient (n,k)");
      } else if (n < 0) {
         throw new IllegalArgumentException("must have n >= 0 for binomial coefficient (n,k)");
      } else if (n != k && k != 0) {
         if (k != 1 && k != n - 1) {
            long result = Math.round(binomialCoefficientDouble(n, k));
            if (result == Long.MAX_VALUE) {
               throw new ArithmeticException("result too large to represent in a long integer");
            } else {
               return result;
            }
         } else {
            return (long)n;
         }
      } else {
         return 1L;
      }
   }

   public static double binomialCoefficientDouble(int n, int k) {
      return Math.floor(Math.exp(binomialCoefficientLog(n, k)) + 0.5D);
   }

   public static double binomialCoefficientLog(int n, int k) {
      if (n < k) {
         throw new IllegalArgumentException("must have n >= k for binomial coefficient (n,k)");
      } else if (n < 0) {
         throw new IllegalArgumentException("must have n >= 0 for binomial coefficient (n,k)");
      } else if (n != k && k != 0) {
         if (k != 1 && k != n - 1) {
            double logSum = 0.0D;

            for(int i = k + 1; i <= n; ++i) {
               logSum += Math.log((double)i);
            }

            for(int i = 2; i <= n - k; ++i) {
               logSum -= Math.log((double)i);
            }

            return logSum;
         } else {
            return Math.log((double)n);
         }
      } else {
         return 0.0D;
      }
   }

   public static double cosh(double x) {
      return (Math.exp(x) + Math.exp(-x)) / 2.0D;
   }

   public static boolean equals(double x, double y) {
      return Double.isNaN(x) && Double.isNaN(y) || x == y;
   }

   public static long factorial(int n) {
      long result = Math.round(factorialDouble(n));
      if (result == Long.MAX_VALUE) {
         throw new ArithmeticException("result too large to represent in a long integer");
      } else {
         return result;
      }
   }

   public static double factorialDouble(int n) {
      if (n < 0) {
         throw new IllegalArgumentException("must have n >= 0 for n!");
      } else {
         return Math.floor(Math.exp(factorialLog(n)) + 0.5D);
      }
   }

   public static double factorialLog(int n) {
      if (n < 0) {
         throw new IllegalArgumentException("must have n > 0 for n!");
      } else {
         double logSum = 0.0D;

         for(int i = 2; i <= n; ++i) {
            logSum += Math.log((double)i);
         }

         return logSum;
      }
   }

   public static int gcd(int u, int v) {
      if (u * v == 0) {
         return Math.abs(u) + Math.abs(v);
      } else {
         if (u > 0) {
            u = -u;
         }

         if (v > 0) {
            v = -v;
         }

         int k;
         for(k = 0; (u & 1) == 0 && (v & 1) == 0 && k < 31; ++k) {
            u /= 2;
            v /= 2;
         }

         if (k == 31) {
            throw new ArithmeticException("overflow: gcd is 2^31");
         } else {
            int t = (u & 1) == 1 ? v : -(u / 2);

            do {
               while((t & 1) == 0) {
                  t /= 2;
               }

               if (t > 0) {
                  u = -t;
               } else {
                  v = t;
               }

               t = (v - u) / 2;
            } while(t != 0);

            return -u * (1 << k);
         }
      }
   }

   public static int hash(double value) {
      long bits = Double.doubleToLongBits(value);
      return (int)(bits ^ bits >>> 32);
   }

   public static byte indicator(byte x) {
      return (byte)(x >= 0 ? 1 : -1);
   }

   public static double indicator(double x) {
      if (Double.isNaN(x)) {
         return Double.NaN;
      } else {
         return x >= 0.0D ? 1.0D : -1.0D;
      }
   }

   public static float indicator(float x) {
      if (Float.isNaN(x)) {
         return Float.NaN;
      } else {
         return x >= 0.0F ? 1.0F : -1.0F;
      }
   }

   public static int indicator(int x) {
      return x >= 0 ? 1 : -1;
   }

   public static long indicator(long x) {
      return x >= 0L ? 1L : -1L;
   }

   public static short indicator(short x) {
      return (short)(x >= 0 ? 1 : -1);
   }

   public static int lcm(int a, int b) {
      return Math.abs(mulAndCheck(a / gcd(a, b), b));
   }

   public static int mulAndCheck(int x, int y) {
      long m = (long)x * (long)y;
      if (m >= -2147483648L && m <= 2147483647L) {
         return (int)m;
      } else {
         throw new ArithmeticException("overflow: mul");
      }
   }

   public static double round(double x, int scale) {
      return round(x, scale, 4);
   }

   public static double round(double x, int scale, int roundingMethod) {
      double sign = indicator(x);
      double factor = Math.pow(10.0D, (double)scale) * sign;
      return roundUnscaled(x * factor, sign, roundingMethod) / factor;
   }

   public static float round(float x, int scale) {
      return round(x, scale, 4);
   }

   public static float round(float x, int scale, int roundingMethod) {
      float sign = indicator(x);
      float factor = (float)Math.pow(10.0D, (double)scale) * sign;
      return (float)roundUnscaled((double)(x * factor), (double)sign, roundingMethod) / factor;
   }

   private static double roundUnscaled(double unscaled, double sign, int roundingMethod) {
      double fraction;
      switch(roundingMethod) {
      case 0:
         unscaled = Math.ceil(unscaled);
         break;
      case 1:
         unscaled = Math.floor(unscaled);
         break;
      case 2:
         if (sign == -1.0D) {
            unscaled = Math.floor(unscaled);
         } else {
            unscaled = Math.ceil(unscaled);
         }
         break;
      case 3:
         if (sign == -1.0D) {
            unscaled = Math.ceil(unscaled);
         } else {
            unscaled = Math.floor(unscaled);
         }
         break;
      case 4:
         fraction = Math.abs(unscaled - Math.floor(unscaled));
         if (fraction >= 0.5D) {
            unscaled = Math.ceil(unscaled);
         } else {
            unscaled = Math.floor(unscaled);
         }
         break;
      case 5:
         fraction = Math.abs(unscaled - Math.floor(unscaled));
         if (fraction > 0.5D) {
            unscaled = Math.ceil(unscaled);
         } else {
            unscaled = Math.floor(unscaled);
         }
         break;
      case 6:
         fraction = Math.abs(unscaled - Math.floor(unscaled));
         if (fraction > 0.5D) {
            unscaled = Math.ceil(unscaled);
         } else if (fraction < 0.5D) {
            unscaled = Math.floor(unscaled);
         } else if (Math.floor(unscaled) / 2.0D == Math.floor(Math.floor(unscaled) / 2.0D)) {
            unscaled = Math.floor(unscaled);
         } else {
            unscaled = Math.ceil(unscaled);
         }
         break;
      case 7:
         if (unscaled != Math.floor(unscaled)) {
            throw new ArithmeticException("Inexact result from rounding");
         }
         break;
      default:
         throw new IllegalArgumentException("Invalid rounding method.");
      }

      return unscaled;
   }

   public static byte sign(byte x) {
      return (byte)(x == 0 ? 0 : (x > 0 ? 1 : -1));
   }

   public static double sign(double x) {
      if (Double.isNaN(x)) {
         return Double.NaN;
      } else {
         return x == 0.0D ? 0.0D : (x > 0.0D ? 1.0D : -1.0D);
      }
   }

   public static float sign(float x) {
      if (Float.isNaN(x)) {
         return Float.NaN;
      } else {
         return x == 0.0F ? 0.0F : (x > 0.0F ? 1.0F : -1.0F);
      }
   }

   public static int sign(int x) {
      return x == 0 ? 0 : (x > 0 ? 1 : -1);
   }

   public static long sign(long x) {
      return x == 0L ? 0L : (x > 0L ? 1L : -1L);
   }

   public static short sign(short x) {
      return (short)(x == 0 ? 0 : (x > 0 ? 1 : -1));
   }

   public static double sinh(double x) {
      return (Math.exp(x) - Math.exp(-x)) / 2.0D;
   }

   public static int subAndCheck(int x, int y) {
      long s = (long)x - (long)y;
      if (s >= -2147483648L && s <= 2147483647L) {
         return (int)s;
      } else {
         throw new ArithmeticException("overflow: subtract");
      }
   }
}
