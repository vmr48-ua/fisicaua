package appletfibra;

public class DataFibra {
   double radi = 5.0D;
   double n1 = 1.46D;
   double n2 = 1.44D;
   double p = 2.0D;
   int index;
   double lambda = 633.0D;
   char tipus = 's';
   double L = 0.05D;
   double k = 0.0D;
   int num_lam = 100;
   double[] n;

   public DataFibra() {
      this.n = new double[this.num_lam + 1];
      this.lambda = 633.0D;
   }

   double index(double r) {
      double retval = 1.0D;
      switch(this.tipus) {
      case 'g':
      case 'j':
         int i = (int)(Math.abs(r) / this.radi * (double)this.num_lam);
         if (i < this.num_lam) {
            retval = this.n[i];
         } else {
            retval = this.n2;
         }
         break;
      case 's':
         if (r < this.radi) {
            retval = this.n1;
         } else {
            retval = this.n2;
         }
      }

      return retval;
   }

   double N_SubLam(int i) {
      double retval = 1.0D;
      double r = (double)i * this.radi / (double)this.num_lam;
      if (r > this.radi) {
         retval = this.n2;
      }

      switch(this.tipus) {
      case 'g':
         retval = this.n1 * Math.sqrt(1.0D - 2.0D * Math.pow(r / this.radi, this.p) * (this.n1 * this.n1 - this.n2 * this.n2) / (2.0D * this.n1 * this.n1));
         break;
      case 's':
         if (r <= this.radi) {
            retval = this.n1;
         }
      }

      return retval;
   }

   void CreaLams() {
      for(int i = 0; i < this.num_lam; ++i) {
         this.n[i] = this.N_SubLam(i);
      }

      this.n[this.num_lam] = this.n2;
   }

   int Refraccio(double theta1, double phi1, double vnx, double vny, DataRaig DR, double n1, double n2) {
      int ret = false;
      DataFibra.Punt V = new DataFibra.Punt();
      DataFibra.Punt W = new DataFibra.Punt();
      DataFibra.Punt N = new DataFibra.Punt();
      V.x = Math.sin(theta1) * Math.cos(phi1);
      V.y = Math.sin(theta1) * Math.sin(phi1);
      V.z = Math.cos(theta1);
      N.x = vnx;
      N.y = vny;
      N.z = 0.0D;
      double nv = Math.sqrt(N.x * N.x + N.y * N.y);
      N.x /= nv;
      N.y /= nv;
      nv = N.x * V.x + N.y * V.y;
      if (nv < 0.0D) {
         N.x = -N.x;
         N.y = -N.y;
      }

      nv = N.x * V.x + N.y * V.y;
      double del = n2 * n2 - n1 * n1 + nv * nv * n1 * n1;
      double theta2;
      double phi2;
      byte ret;
      if (del < 0.0D) {
         W.x = V.x - 2.0D * N.x * nv;
         W.y = V.y - 2.0D * N.y * nv;
         W.z = V.z;
         theta2 = theta1;
         phi2 = Math.atan2(W.y, W.x);
         ret = -1;
      } else {
         W.x = 1.0D / n2 * ((Math.sqrt(del) - n1 * nv) * N.x + n1 * V.x);
         W.y = 1.0D / n2 * ((Math.sqrt(del) - n1 * nv) * N.y + n1 * V.y);
         W.z = n1 / n2 * V.z;
         theta2 = Math.acos(W.z);
         phi2 = Math.atan2(W.y, W.x);
         ret = 1;
      }

      DR.theta = theta2;
      DR.fi = phi2;
      return ret;
   }

   int Inter_Recta_Cil(double radi, double x0, double y0, double z0, double theta, double phi, DataFibra.Punt P) {
      double tiny = 1.0E-6D;
      if (radi == 0.0D) {
         P.x = x0;
         P.y = y0;
         P.z = z0;
         return 0;
      } else {
         double a = Math.sin(theta) * Math.sin(theta);
         double b = 2.0D * Math.sin(theta) * (x0 * Math.cos(phi) + y0 * Math.sin(phi));
         double c = x0 * x0 + y0 * y0 - radi * radi;
         double del = b * b - 4.0D * a * c;
         if (del < 0.0D) {
            P.x = x0;
            P.y = y0;
            P.z = z0;
            return 0;
         } else {
            double t1 = (-b + Math.sqrt(del)) / (2.0D * a);
            double t2 = (-b - Math.sqrt(del)) / (2.0D * a);
            t1 = Math.abs(t1) < tiny ? 0.0D : t1;
            t2 = Math.abs(t2) < tiny ? 0.0D : t2;
            double z1 = z0 + t1 * Math.cos(theta);
            double z2 = z0 + t2 * Math.cos(theta);
            double t;
            if (z1 > z0 && z2 > z0) {
               if (z1 < z2) {
                  t = t1;
               } else {
                  t = t2;
               }
            } else if (z1 > z0) {
               t = t1;
            } else {
               if (!(z2 > z0)) {
                  P.x = x0;
                  P.y = y0;
                  P.z = z0;
                  return 0;
               }

               t = t2;
            }

            P.x = x0 + t * Math.sin(theta) * Math.cos(phi);
            P.y = y0 + t * Math.sin(theta) * Math.sin(phi);
            P.z = z0 + t * Math.cos(theta);
            return 1;
         }
      }
   }

   int NextSegmentStep(DataFibra F, DataRaig R, int idx) {
      DataFibra.Punt P = new DataFibra.Punt();
      DataRaig DR = new DataRaig();
      int inter = this.Inter_Recta_Cil(F.radi, R.x[idx - 1], R.y[idx - 1], R.z[idx - 1], R.theta_v[idx - 1], R.phi_v[idx - 1], P);
      R.x[idx] = P.x;
      R.y[idx] = P.y;
      R.z[idx] = P.z;
      if (inter == 0) {
         R.x[idx] = R.x[idx - 1];
         R.y[idx] = R.y[idx - 1];
         R.z[idx] = R.z[idx - 1];
         R.phif = R.phi_v[idx] = R.phi_v[idx - 1];
         R.thetaf = R.theta_v[idx] = R.theta_v[idx - 1];
         return 1;
      } else if (R.z[idx] > F.L * 1000.0D) {
         double t = (F.L * 1000.0D - R.z[idx - 1]) / Math.cos(R.theta_v[idx - 1]);
         R.x[idx] = R.x[idx - 1] + t * Math.sin(R.theta_v[idx - 1]) * Math.cos(R.phi_v[idx - 1]);
         R.y[idx] = R.y[idx - 1] + t * Math.sin(R.theta_v[idx - 1]) * Math.sin(R.phi_v[idx - 1]);
         R.z[idx] = R.z[idx - 1] + t * Math.cos(R.theta_v[idx - 1]);
         R.rf = Math.sqrt(R.x[idx] * R.x[idx] + R.y[idx] * R.y[idx]);
         R.phif = R.phi_v[idx] = R.phi_v[idx - 1];
         R.thetaf = Math.asin(F.n1 * Math.sin(R.theta_v[idx - 1]));
         return 2;
      } else {
         int ref = this.Refraccio(R.theta_v[idx - 1], R.phi_v[idx - 1], -R.x[idx], -R.y[idx], DR, F.n1, F.n2);
         R.theta_v[idx] = DR.theta;
         R.phi_v[idx] = DR.fi;
         if (ref != -1) {
            R.rf = Math.sqrt(R.x[idx] * R.x[idx] + R.y[idx] * R.y[idx]);
            R.phif = R.phi_v[idx];
            R.thetaf = R.theta_v[idx];
            return 3;
         } else {
            return 0;
         }
      }
   }

   int NextSegmentGraded(DataFibra F, DataRaig R, int idx) {
      double tiny = 1.0E-6D;
      DataFibra.Punt P1 = new DataFibra.Punt();
      DataFibra.Punt P2 = new DataFibra.Punt();
      DataRaig DR = new DataRaig();
      double z1 = 0.0D;
      double y1 = 0.0D;
      double x1 = 0.0D;
      double z2 = 0.0D;
      double y2 = 0.0D;
      double x2 = 0.0D;
      double r = Math.sqrt(R.x[idx - 1] * R.x[idx - 1] + R.y[idx - 1] * R.y[idx - 1]);
      double sign = R.x[idx - 1] * Math.cos(R.phi_v[idx - 1]) + R.y[idx - 1] * Math.sin(R.phi_v[idx - 1]);
      double lam = r / F.radi * (double)F.num_lam;
      int lam1;
      int lam2;
      if (Math.abs(lam - (double)((int)(lam + 0.5D))) < tiny) {
         lam2 = (int)(lam + 0.5D) + 1;
         lam1 = lam2 - 2;
      } else {
         lam2 = (int)(lam + 0.5D);
         lam1 = (int)(lam - 0.5D);
      }

      if (lam1 < 0) {
         lam1 = 0;
      }

      int inter1 = this.Inter_Recta_Cil(F.radi * (double)lam1 / (double)F.num_lam, R.x[idx - 1], R.y[idx - 1], R.z[idx - 1], R.theta_v[idx - 1], R.phi_v[idx - 1], P1);
      x1 = P1.x;
      y1 = P1.y;
      z1 = P1.z;
      int inter2 = this.Inter_Recta_Cil(F.radi * (double)lam2 / (double)F.num_lam, R.x[idx - 1], R.y[idx - 1], R.z[idx - 1], R.theta_v[idx - 1], R.phi_v[idx - 1], P2);
      x2 = P2.x;
      y2 = P2.y;
      z2 = P2.z;
      byte inter;
      if (inter1 != 0 && inter2 != 0) {
         if (z1 < z2) {
            R.x[idx] = x1;
            R.y[idx] = y1;
            R.z[idx] = z1;
            inter = 1;
         } else {
            R.x[idx] = x2;
            R.y[idx] = y2;
            R.z[idx] = z2;
            inter = 2;
         }
      } else if (inter1 != 0) {
         R.x[idx] = x1;
         R.y[idx] = y1;
         R.z[idx] = z1;
         inter = 1;
      } else {
         if (inter2 == 0) {
            R.x[idx] = R.x[idx - 1];
            R.y[idx] = R.y[idx - 1];
            R.z[idx] = R.z[idx - 1];
            R.phif = R.phi_v[idx] = R.phi_v[idx - 1];
            R.thetaf = R.theta_v[idx] = R.theta_v[idx - 1];
            return 1;
         }

         R.x[idx] = x2;
         R.y[idx] = y2;
         R.z[idx] = z2;
         inter = 2;
      }

      double n1;
      if (R.z[idx] > F.L * 1000.0D) {
         double t = (F.L * 1000.0D - R.z[idx - 1]) / Math.cos(R.theta_v[idx - 1]);
         R.x[idx] = R.x[idx - 1] + t * Math.sin(R.theta_v[idx - 1]) * Math.cos(R.phi_v[idx - 1]);
         R.y[idx] = R.y[idx - 1] + t * Math.sin(R.theta_v[idx - 1]) * Math.sin(R.phi_v[idx - 1]);
         R.z[idx] = R.z[idx - 1] + t * Math.cos(R.theta_v[idx - 1]);
         R.rf = Math.sqrt(R.x[idx] * R.x[idx] + R.y[idx] * R.y[idx]);
         R.phif = R.phi_v[idx] = R.phi_v[idx - 1];
         n1 = F.index(R.rf);
         R.thetaf = Math.asin(n1 * Math.sin(R.theta_v[idx - 1]));
         return 2;
      } else {
         double n2;
         if (inter == 1) {
            n1 = F.n[lam1];
            n2 = F.n[lam1 - 1];
         } else {
            n1 = F.n[lam2 - 1];
            if (lam2 <= F.num_lam) {
               n2 = F.n[lam2];
            } else {
               n2 = F.n2;
            }
         }

         this.Refraccio(R.theta_v[idx - 1], R.phi_v[idx - 1], -R.x[idx], -R.y[idx], DR, n1, n2);
         R.theta_v[idx] = DR.theta;
         R.phi_v[idx] = DR.fi;
         if (Math.sqrt(R.x[idx] * R.x[idx] + R.y[idx] * R.y[idx]) > F.radi && R.x[idx] * Math.cos(R.phi_v[idx]) + R.y[idx] * Math.sin(R.phi_v[idx]) > 0.0D) {
            R.rf = F.radi;
            R.phif = R.phi_v[idx];
            R.thetaf = R.theta_v[idx];
            return 3;
         } else {
            return 0;
         }
      }
   }

   public void RayTrace(DataRaig R) {
      int nexts = 0;
      R.ri = R.h;
      R.thetai = R.theta / 180.0D * 3.141592653589793D;
      R.phii = R.fi / 180.0D * 3.141592653589793D;
      R.z[0] = -this.L * 1000.0D * 0.05D;
      R.y[0] = R.z[0] / Math.cos(R.thetai) * Math.sin(R.thetai) * Math.sin(R.phii);
      R.x[0] = R.ri + R.z[0] / Math.cos(R.thetai) * Math.sin(R.thetai) * Math.cos(R.phii);
      R.y[1] = R.z[1] = 0.0D;
      R.x[1] = R.ri;
      if (R.thetai == 0.0D) {
         R.x[2] = R.x[1];
         R.y[2] = R.y[1];
         R.z[2] = this.L * 1000.0D;
         R.x[3] = R.x[1];
         R.y[3] = R.y[1];
         R.z[3] = this.L * 1000.0D * 1.05D;
         R.rf = R.ri;
         R.thetaf = 0.0D;
         R.phif = R.phii;
         R.N = 4;
      } else if (Math.abs(R.ri) > this.radi) {
         R.N = 1;
      } else {
         double nl1 = 1.0D;
         double nl2 = this.index(R.ri);
         R.phi_v[1] = R.phii;
         double theta0 = R.theta_v[1] = Math.asin(nl1 / nl2 * Math.sin(R.thetai));
         double var10;
         if (theta0 != 0.0D) {
            var10 = theta0 / Math.abs(theta0);
         } else {
            var10 = 1.0D;
         }

         int i;
         for(i = 2; i < R.Nmax; R.N = i) {
            if (this.tipus == 's') {
               nexts = this.NextSegmentStep(this, R, i);
               if (nexts != 0) {
                  break;
               }
            } else {
               nexts = this.NextSegmentGraded(this, R, i);
               if (nexts != 0) {
                  break;
               }
            }

            if (i + 1 == R.Nmax) {
               R.AugEspai();
            }

            ++i;
         }

         if (i + 1 == R.Nmax) {
            R.AugEspai();
         }

         if (nexts == 2) {
            ++i;
            R.N = i;
            R.x[i] = R.x[i - 1] + 0.05D * this.L * 1000.0D * Math.tan(R.thetaf) * Math.cos(R.phif);
            R.y[i] = R.y[i - 1] + 0.05D * this.L * 1000.0D * Math.tan(R.thetaf) * Math.sin(R.phif);
            R.z[i] = R.z[i - 1] + 0.05D * this.L * 1000.0D;
            ++R.N;
         }

         if (nexts == 3) {
            ++i;
            R.N = i;
            if (R.thetaf == 0.0D) {
               R.x[i] = R.x[i - 1];
               R.y[i] = R.y[i - 1];
               R.z[i] = this.L;
            } else {
               double R1 = this.radi;
               double R2 = this.radi * 1.1D;
               double factor = R.x[i - 1] * Math.cos(R.thetaf) + R.y[i - 1] * Math.sin(R.thetaf);
               factor = Math.sqrt(factor * factor + R2 * R2 - R1 * R1) - factor;
               factor /= Math.sin(R.thetaf);
               R.x[i] = R.x[i - 1] + factor * Math.sin(R.thetaf) * Math.cos(R.phif);
               R.y[i] = R.y[i - 1] + factor * Math.sin(R.thetaf) * Math.sin(R.phif);
               R.z[i] = R.z[i - 1] + factor * Math.cos(R.thetaf);
            }

            ++R.N;
         }

      }
   }

   public class Punt {
      double x;
      double y;
      double z;
   }
}
