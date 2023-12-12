package difraccion;

import java.awt.Font;
import java.awt.Frame;
import java.awt.TextArea;

public class Fft2 {
   public static void fft2r2(int nfil, int ncol, int isign, double[] x, double[] y) {
      double[] a = new double[nfil * ncol];
      double[] b = new double[nfil * ncol];
      double[] seno = new double[nfil * ncol];
      double[] cose = new double[nfil * ncol];
      int[] inte = new int[1024];
      if (nfil < ncol) {
         ;
      }

      int nf = nfil / 2;
      int nc = ncol / 2;
      double rnfil = 1.0D / Math.sqrt((double)nfil);
      double rncol = 1.0D / Math.sqrt((double)ncol);
      double rntot = rnfil * rncol;
      int mf = (int)(Math.log((double)nfil) / Math.log(2.0D) + 0.1D);
      int mc = (int)(Math.log((double)ncol) / Math.log(2.0D) + 0.1D);
      double fd = (double)isign * 6.283185D / (double)ncol;
      int ld = ncol;

      int i;
      for(i = 0; i < mc; ++i) {
         ld /= 2;
         inte[i] = ld;
      }

      double app;
      for(i = 0; i < ncol; ++i) {
         app = (double)i * fd;
         cose[i] = Math.cos(app);
         seno[i] = Math.sin(app);
      }

      int j;
      int ii;
      int jj;
      int k;
      int ik;
      for(ii = 0; ii < nfil; ++ii) {
         k = ii * ncol;

         for(i = 0; i < nc; ++i) {
            ik = k + i;
            j = i + nc;
            jj = k + j;
            a[i] = x[jj];
            b[i] = y[jj];
            a[j] = x[ik];
            b[j] = y[ik];
         }

         fftr2(ncol, mc, isign, a, b, inte, seno, cose);

         for(i = 0; i < nc; ++i) {
            ik = k + i;
            j = i + nc;
            jj = k + j;
            x[jj] = a[i];
            y[jj] = b[i];
            x[ik] = a[j];
            y[ik] = b[j];
         }
      }

      if (nfil != ncol) {
         fd = (double)isign * 6.283185D / (double)nfil;
         ld = nfil;

         for(i = 0; i < mf; ++i) {
            ld /= 2;
            inte[i] = ld;
         }

         for(i = 0; i < nfil; ++i) {
            app = (double)i * fd;
            cose[i] = Math.cos(app);
            seno[i] = Math.sin(app);
         }
      }

      k = nf * ncol;

      for(jj = 0; jj < ncol; ++jj) {
         for(i = 0; i < nf; ++i) {
            j = i + nf;
            ii = i * ncol + jj;
            ik = ii + k;
            a[i] = x[ik];
            b[i] = y[ik];
            a[j] = x[ii];
            b[j] = y[ii];
         }

         fftr2(nfil, mf, isign, a, b, inte, seno, cose);

         for(i = 0; i < nf; ++i) {
            j = i + nf;
            ii = i * ncol + jj;
            ik = ii + k;
            x[ik] = a[i] * rntot;
            y[ik] = b[i] * rntot;
            x[ii] = a[j] * rntot;
            y[ii] = b[j] * rntot;
         }
      }

   }

   public static void fftr2(int l, int n, int s, double[] x, double[] y, int[] inte, double[] seno, double[] cose) {
      int id = 0;
      int nd = 1;

      int i;
      int nc;
      int k;
      for(int m = 1; m <= n; ++m) {
         int nt = nd;
         nd += nd;
         int lt = l / nt;
         int lc = lt / 2;
         nc = 0;

         for(int ib = 0; ib < nt; ++ib) {
            int lci = lt * ib;

            for(i = 0; i < lc; ++i) {
               int j = i + lci;
               k = j + lc;
               double qu = x[k] * cose[nc] - y[k] * seno[nc];
               double qd = x[k] * seno[nc] + y[k] * cose[nc];
               x[k] = x[j] - qu;
               y[k] = y[j] - qd;
               x[j] += qu;
               y[j] += qd;
            }

            for(i = 1; i < n; ++i) {
               id = i;
               if (inte[i] > nc) {
                  break;
               }

               nc -= inte[i];
            }

            nc += inte[id];
         }
      }

      nc = 0;

      for(k = 0; k < l; ++k) {
         if (nc > k) {
            double hu = x[nc];
            double hd = y[nc];
            x[nc] = x[k];
            y[nc] = y[k];
            x[k] = hu;
            y[k] = hd;
         }

         for(i = 0; i < n; ++i) {
            id = i;
            if (inte[i] > nc) {
               break;
            }

            nc -= inte[i];
         }

         nc += inte[id];
      }

   }

   public static void correlacioMf(int nfil, int ncol, double[] xr, double[] xi, double[] yr, double[] yi) {
      fft2r2(nfil, ncol, 1, xr, xi);
      fft2r2(nfil, ncol, 1, yr, yi);
      double xr0 = 0.0D;
      double yr0 = 0.0D;
      double xi0 = 0.0D;
      double yi0 = 0.0D;

      for(int i = 0; i < nfil * ncol; ++i) {
         xr0 = xr[i];
         xi0 = xi[i];
         yr0 = yr[i];
         yi0 = yi[i];
         xr[i] = xr0 * yr0 + xi0 * yi0;
         xi[i] = -xr0 * yi0 + xi0 * yr0;
      }

      fft2r2(nfil, ncol, -1, xr, xi);
   }

   public static void correlacioPof(int nfil, int ncol, double[] xr, double[] xi, double[] yr, double[] yi) {
      fft2r2(nfil, ncol, 1, xr, xi);
      fft2r2(nfil, ncol, 1, yr, yi);
      double xr0 = 0.0D;
      double yr0 = 0.0D;
      double xi0 = 0.0D;
      double yi0 = 0.0D;

      for(int i = 0; i < nfil * ncol; ++i) {
         xr0 = xr[i];
         xi0 = xi[i];
         yr0 = yr[i];
         yi0 = yi[i];
         xr[i] = (xr0 * yr0 + xi0 * yi0) / Math.sqrt(yr0 * yr0 + yi0 * yi0 + 1.0E-8D);
         xi[i] = (-xr0 * yi0 + xi0 * yr0) / Math.sqrt(yr0 * yr0 + yi0 * yi0 + 1.0E-8D);
      }

      fft2r2(nfil, ncol, -1, xr, xi);
   }

   public static void correlacioIf(int nfil, int ncol, double[] xr, double[] xi, double[] yr, double[] yi) {
      fft2r2(nfil, ncol, 1, xr, xi);
      fft2r2(nfil, ncol, 1, yr, yi);
      double xr0 = 0.0D;
      double yr0 = 0.0D;
      double xi0 = 0.0D;
      double yi0 = 0.0D;

      for(int i = 0; i < nfil * ncol; ++i) {
         xr0 = xr[i];
         xi0 = xi[i];
         yr0 = yr[i];
         yi0 = yi[i];
         xr[i] = (xr0 * yr0 + xi0 * yi0) / (yr0 * yr0 + yi0 * yi0 + 1.0E-8D);
         xi[i] = (-xr0 * yi0 + xi0 * yr0) / (yr0 * yr0 + yi0 * yi0 + 1.0E-8D);
      }

      fft2r2(nfil, ncol, -1, xr, xi);
   }

   public static void convolucio(int nfil, int ncol, double[] xr, double[] xi, double[] yr, double[] yi) {
      fft2r2(nfil, ncol, 1, xr, xi);
      fft2r2(nfil, ncol, 1, yr, yi);
      double xr0 = 0.0D;
      double yr0 = 0.0D;
      double xi0 = 0.0D;
      double yi0 = 0.0D;

      for(int i = 0; i < nfil * ncol; ++i) {
         xr0 = xr[i];
         xi0 = xi[i];
         yr0 = yr[i];
         yi0 = yi[i];
         xr[i] = xr0 * yr0 - xi0 * yi0;
         xi[i] = xr0 * yi0 + xi0 * yr0;
      }

      fft2r2(nfil, ncol, -1, xr, xi);
   }

   public static void HologramaFresnel(double z, double lambda, double[] ampli, double[] partReal, double[] partImag, double L, double N, double LR, double NR) {
      double x = 0.0D;
      double y = 0.0D;
      double x0 = 0.0D;
      double y0 = 0.0D;
      double argument = 0.0D;
      Frame info = new Frame("Progres càlcul holograma");
      TextArea area = new TextArea("Progres: ", 380, 100, 0);
      info.setSize(400, 120);
      area.setFont(new Font("Dialog", 1, 12));
      area.setEditable(false);
      info.add(area);
      info.show();

      for(int k = 0; (double)k < NR; ++k) {
         int tpc = (int)(100.0D * (double)k / NR);
         area.replaceRange("Progres: " + tpc + " %. Processant fila " + k + "/" + (int)NR + "    \n", 0, 100);
         y0 = ((double)k - 0.5D * NR) * LR / NR;

         for(int l = 0; (double)l < NR; ++l) {
            if (ampli[k * (int)NR + l] != 0.0D) {
               x0 = ((double)l - 0.5D * NR) * LR / NR;

               for(int i = 0; (double)i < N; ++i) {
                  y = ((double)i - 0.5D * N) * L / N;

                  for(int j = 0; (double)j < N; ++j) {
                     x = ((double)j - 0.5D * N) * L / N;
                     argument = 6.283185307179586D * z / lambda + 3.141592653589793D * ((x - x0) * (x - x0) + (y - y0) * (y - y0)) / (lambda * z);
                     partReal[i * (int)N + j] += ampli[k * (int)NR + l] / z * Math.cos(argument);
                     partImag[i * (int)N + j] += ampli[k * (int)NR + l] / z * Math.sin(argument);
                  }
               }
            }
         }
      }

      info.hide();
   }

   public static void CorbaFase(double[] partReal, double[] partImag, int[] mapa, int nfil, int ncol) {
      for(int i = 0; i < nfil * ncol; ++i) {
         mapa[i] = (int)(254.0D * (Math.atan2(partImag[i], partReal[i]) + 3.141592653589793D) / 6.283185307179586D);
         mapa[i] |= -16777216 | mapa[i] << 16 | mapa[i] << 8;
      }

   }

   public static void Fresnel(int nfil, int ncol, double maxi, double mini, double[] partReal, double[] partImag, double[] intensitat, int[] mapa, int[] mapaPlot, double z, double lambda, double costat) {
      double pr = 0.0D;
      double pi = 0.0D;
      double u = 0.0D;
      double v = 0.0D;
      fft2r2(nfil, ncol, 1, partReal, partImag);

      int i;
      for(i = 0; i < nfil; ++i) {
         for(int j = 0; j < ncol; ++j) {
            u = ((double)i - (double)nfil / 2.0D) / costat;
            v = ((double)j - (double)ncol / 2.0D) / costat;
            pr = partReal[i * nfil + j];
            pi = partImag[i * nfil + j];
            partReal[i * nfil + j] = pr * Math.cos(3.141592653589793D * lambda * z * (u * u + v * v)) + pi * Math.sin(3.141592653589793D * lambda * z * (u * u + v * v));
            partImag[i * nfil + j] = pi * Math.cos(3.141592653589793D * lambda * z * (u * u + v * v)) - pr * Math.sin(3.141592653589793D * lambda * z * (u * u + v * v));
         }
      }

      fft2r2(nfil, ncol, -1, partReal, partImag);
      normaliza(nfil, ncol, maxi, mini, partReal, partImag, mapa, mapaPlot);

      for(i = 0; i < nfil * ncol; ++i) {
         intensitat[i] = partReal[i] * partReal[i] + partImag[i] * partImag[i];
      }

   }

   public static void normaliza(int nfil, int ncol, double maxi, double mini, double[] partReal, double[] partImag, int[] mapa, int[] mapaPlot) {
      double amplitud = 0.0D;

      int i;
      for(i = 0; i < nfil * ncol; ++i) {
         amplitud = partReal[i] * partReal[i] + partImag[i] * partImag[i];
         if (maxi < amplitud) {
            maxi = amplitud;
         }

         if (mini > amplitud) {
            mini = amplitud;
         }
      }

      for(i = 0; i < nfil * ncol; ++i) {
         mapaPlot[i] = mapa[i] = (int)(254.0D * (partReal[i] * partReal[i] + partImag[i] * partImag[i] - mini) / (maxi - mini));
         mapa[i] |= -16777216 | mapa[i] << 16 | mapa[i] << 8;
      }

   }
}
