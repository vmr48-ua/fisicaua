package appletfibra;

public class DataMode {
   String ErrorMessage;
   boolean ErrorFlag = false;

   double bessi0(double x) {
      double ax;
      double ans;
      double y;
      if ((ax = Math.abs(x)) < 3.75D) {
         y = x / 3.75D;
         y *= y;
         ans = 1.0D + y * (3.5156229D + y * (3.0899424D + y * (1.2067492D + y * (0.2659732D + y * (0.0360768D + y * 0.0045813D)))));
      } else {
         y = 3.75D / ax;
         ans = Math.exp(ax) / Math.sqrt(ax) * (0.39894228D + y * (0.01328592D + y * (0.00225319D + y * (-0.00157565D + y * (0.00916281D + y * (-0.02057706D + y * (0.02635537D + y * (-0.01647633D + y * 0.00392377D))))))));
      }

      return ans;
   }

   double bessi1(double x) {
      double ax;
      double ans;
      double y;
      if ((ax = Math.abs(x)) < 3.75D) {
         y = x / 3.75D;
         y *= y;
         ans = ax * (0.5D + y * (0.87890594D + y * (0.51498869D + y * (0.15084934D + y * (0.02658733D + y * (0.00301532D + y * 3.2411E-4D))))));
      } else {
         y = 3.75D / ax;
         ans = 0.02282967D + y * (-0.02895312D + y * (0.01787654D - y * 0.00420059D));
         ans = 0.39894228D + y * (-0.03988024D + y * (-0.00362018D + y * (0.00163801D + y * (-0.01031555D + y * ans))));
         ans *= Math.exp(ax) / Math.sqrt(ax);
      }

      return x < 0.0D ? -ans : ans;
   }

   double bessk1(double x) {
      double y;
      double ans;
      if (x <= 2.0D) {
         y = x * x / 4.0D;
         ans = Math.log(x / 2.0D) * this.bessi1(x) + 1.0D / x * (1.0D + y * (0.15443144D + y * (-0.67278579D + y * (-0.18156897D + y * (-0.01919402D + y * (-0.00110404D + y * -4.686E-5D))))));
      } else {
         y = 2.0D / x;
         ans = Math.exp(-x) / Math.sqrt(x) * (1.25331414D + y * (0.23498619D + y * (-0.0365562D + y * (0.01504268D + y * (-0.00780353D + y * (0.00325614D + y * -6.8245E-4D))))));
      }

      return ans;
   }

   double bessk0(double x) {
      double y;
      double ans;
      if (x <= 2.0D) {
         y = x * x / 4.0D;
         ans = -Math.log(x / 2.0D) * this.bessi0(x) + -0.57721566D + y * (0.4227842D + y * (0.23069756D + y * (0.0348859D + y * (0.00262698D + y * (1.075E-4D + y * 7.4E-6D)))));
      } else {
         y = 2.0D / x;
         ans = Math.exp(-x) / Math.sqrt(x) * (1.25331414D + y * (-0.07832358D + y * (0.02189568D + y * (-0.01062446D + y * (0.00587872D + y * (-0.0025154D + y * 5.3208E-4D))))));
      }

      return ans;
   }

   double bessk(int n, double x) {
      if (n < 2) {
         this.ErrorMessage = "Index n less than 2 in bessk";
         this.ErrorFlag = true;
         return 0.0D;
      } else {
         double tox = 2.0D / x;
         double bkm = this.bessk0(x);
         double bk = this.bessk1(x);

         for(int j = 1; j < n; ++j) {
            double bkp = bkm + (double)j * tox * bk;
            bkm = bk;
            bk = bkp;
         }

         return bk;
      }
   }

   double bessj1(double x) {
      double ax;
      double y;
      double ans;
      double ans1;
      double ans2;
      if ((ax = Math.abs(x)) < 8.0D) {
         y = x * x;
         ans1 = x * (7.2362614232E10D + y * (-7.895059235E9D + y * (2.423968531E8D + y * (-2972611.439D + y * (15704.4826D + y * -30.16036606D)))));
         ans2 = 1.44725228442E11D + y * (2.300535178E9D + y * (1.858330474E7D + y * (99447.43394D + y * (376.9991397D + y * 1.0D))));
         ans = ans1 / ans2;
      } else {
         double z = 8.0D / ax;
         y = z * z;
         double xx = ax - 2.356194491D;
         ans1 = 1.0D + y * (0.00183105D + y * (-3.516396496E-5D + y * (2.457520174E-6D + y * -2.40337019E-7D)));
         ans2 = 0.04687499995D + y * (-2.002690873E-4D + y * (8.449199096E-6D + y * (-8.8228987E-7D + y * 1.05787412E-7D)));
         ans = Math.sqrt(0.636619772D / ax) * (Math.cos(xx) * ans1 - z * Math.sin(xx) * ans2);
         if (x < 0.0D) {
            ans = -ans;
         }
      }

      return ans;
   }

   double bessj0(double x) {
      double ax;
      double y;
      double ans;
      double ans1;
      double ans2;
      if ((ax = Math.abs(x)) < 8.0D) {
         y = x * x;
         ans1 = 5.7568490574E10D + y * (-1.3362590354E10D + y * (6.516196407E8D + y * (-1.121442418E7D + y * (77392.33017D + y * -184.9052456D))));
         ans2 = 5.7568490411E10D + y * (1.029532985E9D + y * (9494680.718D + y * (59272.64853D + y * (267.8532712D + y * 1.0D))));
         ans = ans1 / ans2;
      } else {
         double z = 8.0D / ax;
         y = z * z;
         double xx = ax - 0.785398164D;
         ans1 = 1.0D + y * (-0.001098628627D + y * (2.734510407E-5D + y * (-2.073370639E-6D + y * 2.093887211E-7D)));
         ans2 = -0.01562499995D + y * (1.430488765E-4D + y * (-6.911147651E-6D + y * (7.621095161E-7D - y * 9.34935152E-8D)));
         ans = Math.sqrt(0.636619772D / ax) * (Math.cos(xx) * ans1 - z * Math.sin(xx) * ans2);
      }

      return ans;
   }

   boolean IsEven(int N) {
      return N % 2 == 0;
   }

   double bessj(int n, double x) {
      double ACC = 40.0D;
      double BIGNO = 1.0E10D;
      double BIGNI = 1.0E-10D;
      if (n < 2) {
         this.ErrorMessage = "Index n less than 2 in bessk";
         this.ErrorFlag = true;
         return 0.0D;
      } else {
         double ax = Math.abs(x);
         if (ax == 0.0D) {
            return 0.0D;
         } else {
            int j;
            double bj;
            double bjm;
            double bjp;
            double tox;
            double ans;
            if (ax > (double)n) {
               tox = 2.0D / ax;
               bjm = this.bessj0(ax);
               bj = this.bessj1(ax);

               for(j = 1; j < n; ++j) {
                  bjp = (double)j * tox * bj - bjm;
                  bjm = bj;
                  bj = bjp;
               }

               ans = bj;
            } else {
               tox = 2.0D / ax;
               int m = 2 * ((n + (int)Math.sqrt(ACC * (double)n)) / 2);
               int jsum = 0;
               double sum = 0.0D;
               ans = 0.0D;
               bjp = 0.0D;
               bj = 1.0D;

               for(j = m; j > 0; --j) {
                  bjm = (double)j * tox * bj - bjp;
                  bjp = bj;
                  bj = bjm;
                  if (Math.abs(bjm) > BIGNO) {
                     bj = bjm * BIGNI;
                     bjp *= BIGNI;
                     ans *= BIGNI;
                     sum *= BIGNI;
                  }

                  if ((double)jsum != 0.0D) {
                     sum += bj;
                  }

                  if (jsum == 0) {
                     jsum = 1;
                  } else {
                     jsum = 0;
                  }

                  if (j == n) {
                     ans = bjp;
                  }
               }

               sum = 2.0D * sum - bj;
               ans /= sum;
            }

            return x < 0.0D && !this.IsEven(n) ? -ans : ans;
         }
      }
   }

   double J(int n, double x) {
      double retval;
      if (n == 0) {
         retval = this.bessj0(x);
      } else if (n == 1) {
         retval = this.bessj1(x);
      } else if (n == -1) {
         retval = -this.bessj1(x);
      } else if (n > 1) {
         retval = this.bessj(n, x);
      } else {
         retval = Math.pow(-1.0D, (double)n) * this.bessj(-n, x);
      }

      return retval;
   }

   double K(int n, double x) {
      double retval;
      if (n == 0) {
         retval = this.bessk0(x);
      } else if (n == 1) {
         retval = this.bessk1(x);
      } else if (n == -1) {
         retval = this.bessk1(x);
      } else if (n > 1) {
         retval = this.bessk(n, x);
      } else {
         retval = this.bessk(n, x);
      }

      return retval;
   }

   double Jp(int n, double x) {
      return 0.5D * (this.J(n - 1, x) - this.J(n + 1, x));
   }

   double Jpp(int n, double x) {
      return 0.25D * (this.J(n - 2, x) + this.J(n + 2, x));
   }

   double LHS(int n, double x) {
      double aux = this.J(n, x);
      if (aux != 0.0D) {
         aux = x * this.J(n + 1, x) / aux;
      } else {
         aux = x * 1.0E20D;
      }

      return aux;
   }

   double RHS(int n, double Y) {
      if (Y == 0.0D) {
         return 0.0D;
      } else {
         double aux = this.K(n, Y);
         if (aux != 0.0D) {
            aux = Y * this.K(n + 1, Y) / aux;
         } else {
            aux = Y * 1.0E20D;
         }

         return aux;
      }
   }

   void Jd0d1(int n, double x, double[] d0, double[] d1) {
      d0[0] = this.J(n, x);
      d1[0] = this.Jp(n, x);
   }

   double LHSmRHS(int N, double X, double V) {
      return X > V ? 1.0E50D : this.LHS(N, X) - this.RHS(N, Math.sqrt(V * V - X * X));
   }

   void Jd1d2(int N, double x, double[] d0, double[] d1) {
      d0[0] = this.Jp(N, x);
      d1[0] = this.Jpp(N, x);
   }

   double drtnewt(int n, double x1, double xacc) {
      int JMAX = 20;
      double[] pf = new double[1];
      double[] pdf = new double[1];
      double rtn = x1;

      for(int j = 1; j <= JMAX; ++j) {
         this.Jd0d1(n, rtn, pf, pdf);
         double f = pf[0];
         double df = pdf[0];
         double dx = f / df;
         rtn -= dx;
         if (Math.abs(dx) < xacc) {
            return rtn;
         }
      }

      this.ErrorMessage = "Maximum number of iterations exceeded in rtnewt";
      this.ErrorFlag = true;
      return 0.0D;
   }

   double SIGN(double a, double b) {
      return b >= 0.0D ? Math.abs(a) : -Math.abs(a);
   }

   double zbrent(int n, double V, double x1, double x2, double tol) {
      int ITMAX = 100;
      double EPS = 3.0E-8D;
      double a = x1;
      double b = x2;
      double c = x2;
      double d = x2;
      double e = x2;
      double fa = this.LHSmRHS(n, x1, V);
      double fb = this.LHSmRHS(n, x2, V);
      if ((!(fa > 0.0D) || !(fb > 0.0D)) && (!(fa < 0.0D) || !(fb < 0.0D))) {
         double fc = fb;

         for(int iter = 1; iter <= ITMAX; ++iter) {
            if (fb > 0.0D && fc > 0.0D || fb < 0.0D && fc < 0.0D) {
               c = a;
               fc = fa;
               e = d = b - a;
            }

            if (Math.abs(fc) < Math.abs(fb)) {
               a = b;
               b = c;
               c = a;
               fa = fb;
               fb = fc;
               fc = fa;
            }

            double tol1 = 2.0D * EPS * Math.abs(b) + 0.5D * tol;
            double xm = 0.5D * (c - b);
            if (Math.abs(xm) <= tol1 || fb == 0.0D) {
               return b;
            }

            if (Math.abs(e) >= tol1 && Math.abs(fa) > Math.abs(fb)) {
               double s = fb / fa;
               double p;
               double q;
               if (a == c) {
                  p = 2.0D * xm * s;
                  q = 1.0D - s;
               } else {
                  q = fa / fc;
                  double r = fb / fc;
                  p = s * (2.0D * xm * q * (q - r) - (b - a) * (r - 1.0D));
                  q = (q - 1.0D) * (r - 1.0D) * (s - 1.0D);
               }

               if (p > 0.0D) {
                  q = -q;
               }

               p = Math.abs(p);
               double min1 = 3.0D * xm * q - Math.abs(tol1 * q);
               double min2 = Math.abs(e * q);
               if (2.0D * p < (min1 < min2 ? min1 : min2)) {
                  e = d;
                  d = p / q;
               } else {
                  d = xm;
                  e = xm;
               }
            } else {
               d = xm;
               e = xm;
            }

            a = b;
            fa = fb;
            if (Math.abs(d) > tol1) {
               b += d;
            } else {
               b += this.SIGN(tol1, xm);
            }

            fb = this.LHSmRHS(n, b, V);
         }

         this.ErrorMessage = "Maximum number of iterations exceeded in zbrent";
         this.ErrorFlag = true;
         return 0.0D;
      } else {
         return 0.0D;
      }
   }

   double[] ZeroBess(int n, int[] maxnum, double V) {
      int num = 0;
      if (V == 0.0D) {
         V = 1.0E-4D;
      }

      maxnum[0] = 0;
      double x0 = 0.07853981633974483D;
      int var10002;
      double JNX;
      if (this.IsEven(n)) {
         while(x0 < V) {
            JNX = this.J(n, x0);

            while(JNX * this.J(n, x0 += 0.07853981633974483D) > 0.0D) {
            }

            var10002 = maxnum[0]++;
         }
      } else {
         while(x0 < V) {
            JNX = this.J(n, x0);

            while(JNX * this.J(n, x0 += 0.07853981633974483D) > 0.0D) {
            }

            var10002 = maxnum[0]++;
         }
      }

      if (x0 > V) {
         var10002 = maxnum[0]--;
      }

      if (maxnum[0] <= 0) {
         double[] zero = null;
         return (double[])zero;
      } else {
         double[] zero = new double[maxnum[0]];
         x0 = 0.07853981633974483D;
         if (this.IsEven(n)) {
            while(num < maxnum[0]) {
               JNX = this.J(n, x0);

               while(JNX * this.J(n, x0 += 0.07853981633974483D) > 0.0D) {
               }

               zero[num] = this.drtnewt(n, x0, 1.0E-5D);
               x0 += 0.07853981633974483D;
               ++num;
            }
         } else {
            while(num < maxnum[0]) {
               JNX = this.J(n, x0);

               while(JNX * this.J(n, x0 += 0.07853981633974483D) > 0.0D) {
               }

               zero[num] = this.drtnewt(n, x0, 1.0E-5D);
               x0 += 0.07853981633974483D;
               ++num;
            }
         }

         return zero;
      }
   }

   double dmin(double a, double b) {
      return a < b ? a : b;
   }

   double[] Modes(int n, double V, int[] nummod) {
      int[] numzero = new int[1];
      if (V > 100.0D) {
         V = 100.0D;
      }

      double[] zero = this.ZeroBess(n, numzero, V);
      double[] X = new double[numzero[0] + 1];
      if (numzero[0] != 0) {
         X[0] = this.zbrent(n, V, 0.0D, zero[0] - 1.0E-7D, 1.0E-6D);

         for(int i = 1; i < numzero[0]; ++i) {
            X[i] = this.zbrent(n, V, zero[i - 1] + 1.0E-7D, zero[i] - 1.0E-7D, 1.0E-6D);
         }

         X[numzero[0]] = this.zbrent(n, V, zero[numzero[0] - 1] + 1.0E-7D, V - 1.0E-7D, 1.0E-6D);
         if (X[numzero[0]] != 0.0D) {
            nummod[0] = numzero[0] + 1;
         } else {
            nummod[0] = numzero[0];
         }
      } else {
         X[0] = this.zbrent(n, V, 0.0D, V, 1.0E-6D);
         if (X[0] < V) {
            nummod[0] = 1;
         } else {
            nummod[0] = 0;
         }
      }

      return X;
   }

   double[][] CalcNumModes(double a, double lambda, double n1, double n2, int minl, int[][] nummod, int num_nummod) {
      double[][] pModes = new double[num_nummod][];
      double V = 6.283185307179586D / lambda * Math.sqrt(n1 * n1 - n2 * n2) * a;

      for(int i = 0; i < num_nummod; ++i) {
         pModes[i] = this.Modes(minl + i, V, nummod[i]);
      }

      return pModes;
   }

   double I(int m, double r, double a, double lambda, double X, double Y, double C) {
      double retval;
      if (r <= a) {
         retval = this.J(m, X / a * r);
      } else {
         retval = C * this.K(m, Y / a * r);
      }

      return retval;
   }

   void CalcPerfilMode(int l, double a, double lambda, double X, double Y, double[] valx, double[] valy, double[] valn, int numval) {
      double Irp = this.I(l, a + 1.0E-6D, a, lambda, X, Y, 1.0D);
      double Irm = this.I(l, a - 1.0E-6D, a, lambda, X, Y, 1.0D);
      double C = Irm / Irp;
      int numval1 = (int)((double)numval / 1.2D);
      int numval2 = numval - numval1;

      int i;
      for(i = 1; i <= numval1; ++i) {
         valx[i - 1] = a / ((double)numval1 - 1.0D) * (double)(i - 1);
         valy[i - 1] = this.I(l, a / ((double)numval1 - 1.0D) * (double)(i - 1), a, lambda, X, Y, C);
         valy[i - 1] *= valy[i - 1];
         valn[i - 1] = 1.0D;
         valx[i - 1] /= 1000.0D;
      }

      for(i = 1; i <= numval2; ++i) {
         valx[i + numval1 - 1] = a + a * 0.2D / (double)(numval2 - 1) * (double)(i - 1);
         valy[i + numval1 - 1] = this.I(l, a + a * 0.2D / ((double)numval2 - 1.0D) * (double)(i - 1), a, lambda, X, Y, C);
         valy[i + numval1 - 1] *= valy[i + numval1 - 1];
         valn[i + numval1 - 1] = 0.0D;
         valx[i + numval1 - 1] /= 1000.0D;
      }

   }
}
