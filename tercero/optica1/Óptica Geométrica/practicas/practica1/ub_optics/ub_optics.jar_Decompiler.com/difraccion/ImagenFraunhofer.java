package difraccion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import javax.swing.JPanel;

public class ImagenFraunhofer extends JPanel {
   int lambda;
   int tipo_objeto;
   double dim_x;
   double dim_y;
   double dist_f;
   int num_objetos;
   int label_tipoimg;
   double umbral;
   int dim_Lx = 256;
   int dim_Ly = 256;
   int[] pixels;
   MemoryImageSource source;
   Image img;

   public ImagenFraunhofer() {
      this.pixels = new int[this.dim_Lx * this.dim_Ly];

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setBackground(Color.black);
      this.setMinimumSize(new Dimension(256, 256));
      this.setPreferredSize(new Dimension(256, 256));
      int dim_Total = this.dim_Lx * this.dim_Ly;
      this.pixels = new int[dim_Total];
      int value = this.getBackground().getRGB();

      for(int i = 0; i < dim_Total; ++i) {
         this.pixels[i] = value;
      }

      this.source = new MemoryImageSource(this.dim_Lx, this.dim_Ly, this.pixels, 0, this.dim_Lx);
      this.source.setAnimated(true);
      this.img = this.createImage(this.source);
   }

   public void putAtributos(int tipo_obj, double tam_x, double tam_y, int num_obj, int l_onda, double distancia_focal, int tipoimg, double fraun_umbral) {
      this.num_objetos = num_obj;
      this.lambda = l_onda;
      this.tipo_objeto = tipo_obj;
      this.dim_x = tam_x;
      this.dim_y = tam_y;
      this.dist_f = distancia_focal;
      this.label_tipoimg = tipoimg;
      this.umbral = fraun_umbral;
      this.calculaImagen();
   }

   public void paint(Graphics g) {
      g.drawImage(this.img, 0, 0, this);
   }

   public void calculaImagen() {
      int dim_Total = this.dim_Lx * this.dim_Ly;
      double half_dim_Lx = (double)this.dim_Lx / 2.0D;
      double half_dim_Ly = (double)this.dim_Ly / 2.0D;
      YoungColor ycolores = new YoungColor();
      Color ncolor = ycolores.lambda2RGB(this.lambda);
      double delta_pix = 5.0D / (double)this.dim_Lx;
      double dim_x_max = 3.0D;
      double dist_separa = 0.0D;
      double[] int_objeto = new double[dim_Total];
      double[] int_difred = new double[dim_Total];
      double[] brillo = new double[dim_Total];
      this.pixels = new int[dim_Total];
      if (this.num_objetos < 5) {
         dist_separa = 2.0D * dim_x_max;
      } else if (this.num_objetos > 4 && this.num_objetos < 9) {
         dist_separa = dim_x_max / 2.0D;
      } else if (this.num_objetos > 8 && this.num_objetos < 11) {
         dist_separa = dim_x_max / 4.0D;
      }

      double semi_periodo = 0.0D;
      if (this.num_objetos == 1) {
         if (this.tipo_objeto == 0) {
            if (this.dim_x > this.dim_y) {
               semi_periodo = (double)this.lambda * 1.0E-6D * this.dist_f / (2.0D * this.dim_x);
            } else {
               semi_periodo = (double)this.lambda * 1.0E-6D * this.dist_f / (2.0D * this.dim_y);
            }
         } else if (this.tipo_objeto == 1) {
            semi_periodo = (double)this.lambda * 1.0E-6D * this.dist_f * 1.22D / (8.0D * this.dim_x);
         } else {
            semi_periodo = (double)this.lambda * 1.0E-6D * this.dist_f / (2.0D * this.dim_x);
         }
      } else {
         semi_periodo = (double)this.lambda * 1.0E-6D * this.dist_f / (2.0D * (double)this.num_objetos * (dim_x_max + dist_separa));
      }

      if (semi_periodo * (double)this.dim_Lx > 7.5D) {
         delta_pix = 5.0D / (double)this.dim_Lx;
      } else if (semi_periodo * (double)this.dim_Lx <= 7.5D && semi_periodo * (double)this.dim_Lx > 5.0D) {
         delta_pix = 3.0D / (double)this.dim_Lx;
      } else if (semi_periodo * (double)this.dim_Lx <= 5.0D && semi_periodo * (double)this.dim_Lx > 2.0D) {
         delta_pix = 2.5D / (double)this.dim_Lx;
      } else if (semi_periodo * (double)this.dim_Lx <= 2.0D && semi_periodo * (double)this.dim_Lx > 1.0D) {
         delta_pix = 0.5D / (double)this.dim_Lx;
      } else {
         delta_pix = 0.25D / (double)this.dim_Lx;
      }

      float[] hsb = new float[3];
      hsb = Color.RGBtoHSB(ncolor.getRed(), ncolor.getGreen(), ncolor.getBlue(), hsb);
      double lambda_f = (double)this.lambda * 1.0E-6D * this.dist_f;
      double inv_lambda_f = 1.0D / lambda_f;
      int i;
      int j;
      double x;
      double y;
      double sinc_x;
      double sinc_y;
      if (this.tipo_objeto == 0) {
         double factor_rect = this.dim_x * this.dim_y * inv_lambda_f;

         for(i = 0; i < this.dim_Ly; ++i) {
            for(j = 0; j < this.dim_Lx; ++j) {
               x = this.dim_x * ((double)j - half_dim_Lx) * delta_pix * inv_lambda_f;
               y = this.dim_y * ((double)i - half_dim_Ly) * delta_pix * inv_lambda_f;
               if (x != 0.0D) {
                  sinc_x = Math.abs(Math.sin(3.141592653589793D * x) / (3.141592653589793D * x));
               } else {
                  sinc_x = 1.0D;
               }

               if (y != 0.0D) {
                  sinc_y = Math.abs(Math.sin(3.141592653589793D * y) / (3.141592653589793D * y));
               } else {
                  sinc_y = 1.0D;
               }

               int_objeto[j + i * this.dim_Lx] = factor_rect * sinc_x * sinc_y;
            }
         }
      } else if (this.tipo_objeto == 1) {
         double radio = Math.sqrt(this.dim_x * this.dim_x + this.dim_y * this.dim_y);

         for(i = 0; i < this.dim_Ly; ++i) {
            for(j = 0; j < this.dim_Lx; ++j) {
               double r_x = ((double)j - half_dim_Lx) * delta_pix;
               double r_y = ((double)i - half_dim_Ly) * delta_pix;
               double r = Math.sqrt(r_x * r_x + r_y * r_y);
               if (r != 0.0D) {
                  double var_bessel = 6.283185307179586D * radio * r * inv_lambda_f;
                  double bessel = Math.abs(this.bessj1(var_bessel));
                  int_objeto[j + i * this.dim_Lx] = bessel / var_bessel;
               } else {
                  int_objeto[j + i * this.dim_Lx] = 0.5D;
               }
            }
         }
      } else {
         double factor_rend = this.dim_x * 64.0D * inv_lambda_f;

         for(i = 0; i < this.dim_Ly; ++i) {
            for(j = 0; j < this.dim_Lx; ++j) {
               x = this.dim_x * ((double)j - half_dim_Lx) * delta_pix * inv_lambda_f;
               y = 64.0D * ((double)i - half_dim_Ly) * delta_pix * inv_lambda_f;
               if (x != 0.0D) {
                  sinc_x = Math.abs(Math.sin(3.141592653589793D * x) / (3.141592653589793D * x));
               } else {
                  sinc_x = 1.0D;
               }

               if (y != 0.0D) {
                  sinc_y = Math.abs(Math.sin(3.141592653589793D * y) / (3.141592653589793D * y));
               } else {
                  sinc_y = 1.0D;
               }

               int_objeto[j + i * this.dim_Lx] = factor_rend * sinc_x * sinc_y;
            }
         }
      }

      if (this.num_objetos == 1) {
         for(i = 0; i < dim_Total; ++i) {
            int_difred[i] = 1.0D;
         }
      } else {
         double periodo_x = dist_separa + dim_x_max;
         double param = 3.141592653589793D * periodo_x * inv_lambda_f;

         for(i = 0; i < this.dim_Ly; ++i) {
            for(j = 0; j < this.dim_Lx; ++j) {
               x = ((double)j - half_dim_Lx) * delta_pix;
               double sin_x = Math.sin(param * x);
               double sin_nx = Math.sin((double)this.num_objetos * param * x);
               if (sin_x != 0.0D) {
                  int_difred[j + i * this.dim_Lx] = Math.abs(sin_nx / sin_x);
               } else {
                  int_difred[j + i * this.dim_Lx] = (double)this.num_objetos * 1.0D;
               }
            }
         }
      }

      for(i = 0; i < dim_Total; ++i) {
         brillo[i] = int_objeto[i] * int_difred[i];
      }

      if (this.label_tipoimg == 1) {
         for(i = 0; i < dim_Total; ++i) {
            brillo[i] *= brillo[i];
         }
      }

      if (this.label_tipoimg == 2) {
         for(i = 0; i < dim_Total; ++i) {
            brillo[i] *= brillo[i];
            brillo[i] = Math.log(brillo[i]);
         }
      }

      double max_brillo = brillo[0];
      double min_brillo = brillo[0];

      for(i = 0; i < dim_Total; ++i) {
         if (max_brillo < brillo[i]) {
            max_brillo = brillo[i];
         }

         if (min_brillo > brillo[i]) {
            min_brillo = brillo[i];
         }
      }

      for(i = 0; i < dim_Total; ++i) {
         if (this.label_tipoimg != 2) {
            if (brillo[i] > max_brillo * this.umbral) {
               brillo[i] = 1.0D;
            } else {
               brillo[i] = (brillo[i] - min_brillo) / (max_brillo * this.umbral - min_brillo);
            }
         } else {
            brillo[i] = (brillo[i] - min_brillo) / (max_brillo - min_brillo);
         }

         hsb[2] = (float)brillo[i];
         Color clin = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
         this.pixels[i] = clin.getRGB();
      }

      this.img = this.createImage(new MemoryImageSource(this.dim_Lx, this.dim_Ly, this.pixels, 0, this.dim_Lx));
   }

   public double bessj1(double x) {
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
}
