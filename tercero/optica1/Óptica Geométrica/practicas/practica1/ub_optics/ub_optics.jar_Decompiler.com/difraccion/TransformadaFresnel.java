package difraccion;

public class TransformadaFresnel {
   public void transformadaFresnel(double distancia, int dim_x, int dim_y, double[] parte_real, double[] parte_imag, double pix_x, double pix_y, double lambda) {
      double longonda = lambda * 1.0E-6D;
      this.transformada_directa(distancia, dim_x, dim_y, parte_real, parte_imag, pix_x, pix_y, longonda);
      Fft2.fft2r2(dim_y, dim_x, -1, parte_real, parte_imag);
      double cosfase = Math.cos(6.283185307179586D * distancia / longonda);
      double senfase = Math.sin(6.283185307179586D * distancia / longonda);

      for(int i = 0; i < dim_y; ++i) {
         for(int j = 0; j < dim_x; ++j) {
            double temp_re = cosfase * parte_real[j + i * dim_x] - senfase * parte_imag[j + i * dim_x];
            double temp_im = cosfase * parte_imag[j + i * dim_x] + senfase * parte_real[j + i * dim_x];
            parte_real[j + i * dim_x] = temp_re;
            parte_imag[j + i * dim_x] = temp_im;
         }
      }

   }

   public void transformada_directa(double distancia, int tam_x, int tam_y, double[] real, double[] imag, double delta_x, double delta_y, double lambda) {
      int tam_total = tam_x * tam_y;
      double[] fase_re = new double[tam_total];
      double[] fase_im = new double[tam_total];
      Fft2.fft2r2(tam_y, tam_x, 1, real, imag);
      double longonda = lambda;
      double factor_u = 1.0D / ((double)tam_x * delta_x);
      double factor_v = 1.0D / ((double)tam_y * delta_y);

      int i;
      int j;
      for(i = 0; i < tam_y; ++i) {
         for(j = 0; j < tam_x; ++j) {
            double x_hip = factor_u * ((double)j - (double)tam_x * 0.5D);
            double y_hip = factor_v * ((double)i - (double)tam_y * 0.5D);
            double hipot = x_hip * x_hip + y_hip * y_hip;
            fase_re[j + i * tam_x] = Math.cos(-3.141592653589793D * longonda * hipot * distancia);
            fase_im[j + i * tam_x] = Math.sin(-3.141592653589793D * longonda * hipot * distancia);
         }
      }

      for(i = 0; i < tam_y; ++i) {
         for(j = 0; j < tam_x; ++j) {
            double temp_re = fase_re[j + i * tam_x] * real[j + i * tam_x] - fase_im[j + i * tam_x] * imag[j + i * tam_x];
            double temp_im = fase_re[j + i * tam_x] * imag[j + i * tam_x] + fase_im[j + i * tam_x] * real[j + i * tam_x];
            real[j + i * tam_x] = temp_re;
            imag[j + i * tam_x] = temp_im;
         }
      }

   }

   public void transformadaFresnelSigno(double distancia, int dim_x, int dim_y, double[] parte_real, double[] parte_imag, double pix_x, double pix_y, double lambda) {
      double longonda = lambda * 1.0E-6D;
      double[] real = new double[dim_x * dim_y * 2 * 2];
      double[] imag = new double[dim_x * dim_y * 2 * 2];
      this.transformada_directa_signo(distancia, dim_x, dim_y, parte_real, parte_imag, pix_x, pix_y, longonda);
      Fft2.fft2r2(dim_y, dim_x, -1, parte_real, parte_imag);
      double cosfase = Math.cos(6.283185307179586D * distancia / longonda);
      double senfase = Math.sin(6.283185307179586D * distancia / longonda);

      for(int i = 0; i < dim_y; ++i) {
         for(int j = 0; j < dim_x; ++j) {
            double temp_re = cosfase * parte_real[j + i * dim_x] - senfase * parte_imag[j + i * dim_x];
            double temp_im = cosfase * parte_imag[j + i * dim_x] + senfase * parte_real[j + i * dim_x];
            parte_real[j + i * dim_x] = temp_re;
            parte_imag[j + i * dim_x] = temp_im;
         }
      }

   }

   public void transformada_directa_signo(double distancia, int tam_x, int tam_y, double[] real, double[] imag, double delta_x, double delta_y, double lambda) {
      double x_trans = 0.0D;
      int tam_total = tam_x * tam_y;
      double[] fase_re = new double[tam_total];
      double[] fase_im = new double[tam_total];
      double longonda = lambda;
      double factor_u = 1.0D / ((double)tam_x * delta_x);
      double factor_v = 1.0D / ((double)tam_y * delta_y);
      double[] tf_re = new double[tam_x];
      double[] tf_im = new double[tam_x];

      int i;
      int j;
      for(i = 0; i < tam_x; ++i) {
         for(j = 0; j < tam_x; ++j) {
            tf_re[i] += real[j + 128 * tam_x] * Math.cos(6.283185307179586D * (double)(j * i) / (double)tam_x);
            tf_im[i] += real[j + 128 * tam_x] * Math.sin(6.283185307179586D * (double)(j * i) / (double)tam_x);
         }
      }

      for(j = 0; j < tam_y; ++j) {
         for(i = 0; i < tam_y; ++i) {
            real[i + j * tam_x] = tf_re[i];
            imag[i + j * tam_x] = tf_im[i];
         }
      }

      for(i = 0; i < tam_y; ++i) {
         for(j = 0; j < tam_x; ++j) {
            double x_hip = factor_u * ((double)j - (double)tam_x * 0.5D);
            double y_hip = factor_v * ((double)i - (double)tam_y * 0.5D);
            double hipot = x_hip * x_hip + y_hip * y_hip;
            fase_re[j + i * tam_x] = Math.cos(-3.141592653589793D * longonda * hipot * distancia);
            fase_im[j + i * tam_x] = Math.sin(-3.141592653589793D * longonda * hipot * distancia);
         }
      }

      for(i = 0; i < tam_y; ++i) {
         for(j = 0; j < tam_x; ++j) {
            double temp_re = fase_re[j + i * tam_x] * real[j + i * tam_x] - fase_im[j + i * tam_x] * imag[j + i * tam_x];
            double temp_im = fase_re[j + i * tam_x] * imag[j + i * tam_x] + fase_im[j + i * tam_x] * real[j + i * tam_x];
            real[j + i * tam_x] = temp_re;
            imag[j + i * tam_x] = temp_im;
         }
      }

   }

   public void integralesFresnel_numericalRecipes(double x, double s, double c) {
      double EPS = 6.0E-8D;
      int MAXIT = 100;
      double FPMIN = 1.0E-30D;
      double XMIN = 1.5D;
      double PIBY2 = 1.5707963267948966D;
      double ax = Math.abs(x);
      if (ax < Math.sqrt(FPMIN)) {
         s = 0.0D;
         c = ax;
      } else {
         int n;
         if (ax <= XMIN) {
            double sums = 0.0D;
            double sum = 0.0D;
            double sumc = ax;
            double sign = 1.0D;
            double fact = PIBY2 * ax * ax;
            boolean odd = true;
            double term = ax;
            n = 3;

            int k;
            for(k = 1; k <= MAXIT; ++k) {
               term *= fact / (double)k;
               sum += sign * term / (double)n;
               double test = Math.abs(sum) * EPS;
               if (odd) {
                  sign = -sign;
                  sums = sum;
                  sum = sumc;
               } else {
                  sumc = sum;
                  sum = sums;
               }

               if (term < test) {
                  break;
               }

               odd = !odd;
               n += 2;
            }

            if (k > MAXIT) {
               System.out.println("series failed in integralesFresnel");
            }

            s = sums;
            c = sumc;
         } else {
            double pix2 = 3.141592653589793D * ax * ax;
            double b_re = 1.0D;
            double b_im = -pix2;
            double cc_re = 1.0D / FPMIN;
            double cc_im = 0.0D;
            double h_re = b_re / (b_re * b_re + b_im * b_im);
            double h_im = -b_im / (b_re * b_re + b_im * b_im);
            double d_re = h_re;
            double d_im = h_im;
            n = -1;

            int j;
            for(j = 2; j <= MAXIT; ++j) {
               n += 2;
               double a = (double)(-n * (n + 1));
               b_re += 4.0D;
               b_im = b_im;
               d_re = (a * d_re + b_re) / ((a * d_re + b_re) * (a * d_re + b_re) + (a * d_im + b_im) * (a * d_im + b_im));
               d_im = -1.0D * (a * d_im + b_im) / ((a * d_re + b_re) * (a * d_re + b_re) + (a * d_im + b_im) * (a * d_im + b_im));
               cc_re = b_re + a * cc_re / (cc_re * cc_re + cc_im * cc_im);
               cc_im = b_im + -a * cc_im / (cc_re * cc_re + cc_im * cc_im);
               double del_re = cc_re * d_re - cc_im * d_im;
               double del_im = cc_re * d_im + cc_im * d_re;
               h_re = h_re * del_re - h_im * del_im;
               h_im = h_re * del_im + h_im * del_re;
               if (Math.abs(del_re - 1.0D) + Math.abs(del_im) < EPS) {
                  break;
               }
            }

            if (j > MAXIT) {
               System.out.println("cf failed in integralesFresnel");
            }

            h_re = ax * h_re + ax * h_im;
            h_im = ax * h_im - ax * h_re;
            double cs_re = 0.5D * (1.0D + (h_re + h_im) * Math.sin(0.5D * pix2) - Math.cos(0.5D * pix2) * (h_re - h_im));
            double cs_im = 0.5D * (1.0D - Math.cos(0.5D * pix2) * (h_re + h_im) + Math.sin(0.5D * pix2) * (h_im - h_re));
            c = cs_re;
            s = cs_im;
         }
      }

      if (x < 0.0D) {
         c = -c;
         s = -s;
      }

   }

   public void transformadaFresnelSemiplano(double distancia, int dim_x, int dim_y, double[] parte_real, double pix_x, double pix_y, double lambda) {
      double cosfres = 0.0D;
      double senfres = 0.0D;
      double longonda = lambda * 1.0E-6D;
      int MAXIT = 100;
      double EPS = 6.0E-8D;
      double FPMIN = 1.0E-30D;

      for(int j = 0; j < dim_x; ++j) {
         double x = ((double)j - (double)dim_x / 2.0D) * pix_x;
         double v = x * Math.sqrt(2.0D / (longonda * distancia));
         double ax = Math.abs(v);
         if (ax > 1.5D) {
            double g = (1.0D + 0.926D * ax) / (2.0D + (1.792D + 3.104D * ax) * ax);
            double f = 1.0D / (2.0D + (4.142D + (3.492D + 6.67D * ax) * ax) * ax);
            double cospix2 = Math.cos(1.5707963267948966D * ax * ax);
            double senpix2 = Math.sin(1.5707963267948966D * ax * ax);
            cosfres = 0.5D - (cospix2 * g - senpix2 * f);
            senfres = 0.5D + cospix2 * f + senpix2 * g;
            if (x < 0.0D) {
               cosfres = -cosfres;
               senfres = -senfres;
            }
         } else {
            double sums = 0.0D;
            double sum = 0.0D;
            double sumc = ax;
            double sign = 1.0D;
            double fact = 1.5707963267948966D * ax * ax;
            boolean odd = true;
            double term = ax;
            int n = 3;

            for(int k = 1; k <= MAXIT; ++k) {
               term *= fact / (double)k;
               sum += sign * term / (double)n;
               double test = Math.abs(sum) * EPS;
               if (odd) {
                  sign = -sign;
                  sums = sum;
                  sum = sumc;
               } else {
                  sumc = sum;
                  sum = sums;
               }

               if (term < test) {
                  break;
               }

               odd = !odd;
               n += 2;
            }

            senfres = sums;
            cosfres = sumc;
            if (x < 0.0D) {
               cosfres = -sumc;
               senfres = -sums;
            }
         }

         for(int i = 0; i < dim_y; ++i) {
            parte_real[j + i * dim_x] = Math.sqrt(0.5D * ((0.5D - cosfres) * (0.5D - cosfres) + (0.5D - senfres) * (0.5D - senfres)));
         }
      }

   }

   public void integralesFresnel(double x, double s, double c) {
      double ax = Math.abs(x);
      double g = (1.0D + 0.926D * ax) / (2.0D + 1.792D * ax + 3.104D * ax * ax);
      double f = 1.0D / (2.0D + 4.142D * ax + 3.492D * ax * ax + 6.67D * ax * ax * ax);
      double cospix2 = Math.cos(1.5707963267948966D * ax * ax);
      double senpix2 = Math.sin(1.5707963267948966D * ax * ax);
      c = 0.5D - (cospix2 * g - senpix2 * f);
      s = 0.5D + cospix2 * f + senpix2 * g;
      if (x < 0.0D) {
         c = -c;
         s = -s;
      }

   }
}
