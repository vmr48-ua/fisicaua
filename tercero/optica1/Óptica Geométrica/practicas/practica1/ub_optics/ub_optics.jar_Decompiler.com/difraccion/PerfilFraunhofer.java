package difraccion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D.Double;
import javax.swing.JPanel;

public class PerfilFraunhofer extends JPanel {
   int lambda;
   int tipo_objeto;
   double dim_x;
   double dim_y;
   double dist_f;
   int num_objetos;
   boolean label_int_log = false;
   boolean zoom = false;
   int dim_Lx = 256;
   int dim_Ly = 256;

   public PerfilFraunhofer() {
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
   }

   public void putAtributos(int tipo_obj, double tam_x, double tam_y, int num_obj, int l_onda, double distancia_focal, boolean label_zoom) {
      this.num_objetos = num_obj;
      this.lambda = l_onda;
      this.tipo_objeto = tipo_obj;
      this.dim_x = tam_x;
      this.dim_y = tam_y;
      this.dist_f = distancia_focal;
      this.zoom = label_zoom;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      Dimension dim = this.getSize();
      int dim_Total = this.dim_Lx * this.dim_Ly;
      double half_dim_Lx = (double)this.dim_Lx / 2.0D;
      double half_dim_Ly = (double)this.dim_Ly / 2.0D;
      YoungColor ycolores = new YoungColor();
      Color ncolor = ycolores.lambda2RGB(this.lambda);
      double delta_pix = 10.0D / (double)this.dim_Lx;
      double dim_x_max = 3.0D;
      double dist_separa = 0.0D;
      double[] int_objeto = new double[dim_Total];
      double[] int_difred = new double[dim_Total];
      double[] brillo = new double[dim_Total];
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

      double brillo_0;
      if (this.zoom) {
         if (this.tipo_objeto == 0) {
            brillo_0 = (double)this.lambda * 1.0E-6D * this.dist_f / this.dim_x;
         } else if (this.tipo_objeto == 1) {
            brillo_0 = 1.22D * (double)this.lambda * 1.0E-6D * this.dist_f / (2.0D * this.dim_x);
         } else {
            brillo_0 = (double)this.lambda * 1.0E-6D * this.dist_f / this.dim_x;
         }

         delta_pix = 2.0D * brillo_0 / (double)this.dim_Lx;
      }

      double lambda_f = (double)this.lambda * 1.0E-6D * this.dist_f;
      double inv_lambda_f = 1.0D / lambda_f;
      int j;
      int i;
      double x;
      double sinc_x;
      if (this.tipo_objeto == 0) {
         double factor_rect = this.dim_x * this.dim_x * this.dim_y * this.dim_y * inv_lambda_f * inv_lambda_f;

         for(i = 0; i < this.dim_Ly; ++i) {
            for(j = 0; j < this.dim_Lx; ++j) {
               x = this.dim_x * ((double)j - half_dim_Lx) * delta_pix * inv_lambda_f;
               if (x != 0.0D) {
                  sinc_x = Math.sin(3.141592653589793D * x) / (3.141592653589793D * x);
               } else {
                  sinc_x = 1.0D;
               }

               int_objeto[j + i * this.dim_Lx] = factor_rect * sinc_x * sinc_x;
            }
         }
      } else if (this.tipo_objeto == 1) {
         double radio = Math.sqrt(this.dim_x * this.dim_x + this.dim_y * this.dim_y);

         for(i = 0; i < this.dim_Ly; ++i) {
            for(j = 0; j < this.dim_Lx; ++j) {
               double r_x = ((double)j - half_dim_Lx) * delta_pix;
               double r = Math.sqrt(r_x * r_x);
               if (r != 0.0D) {
                  double var_bessel = 6.283185307179586D * radio * r * inv_lambda_f;
                  double bessel = this.bessj1(var_bessel);
                  int_objeto[j + i * this.dim_Lx] = bessel * bessel / (var_bessel * var_bessel);
               } else {
                  int_objeto[j + i * this.dim_Lx] = 0.25D;
               }
            }
         }
      } else {
         double factor_rend = this.dim_x * this.dim_x * 64.0D * 64.0D * inv_lambda_f * inv_lambda_f;

         for(i = 0; i < this.dim_Ly; ++i) {
            for(j = 0; j < this.dim_Lx; ++j) {
               x = this.dim_x * ((double)j - half_dim_Lx) * delta_pix * inv_lambda_f;
               if (x != 0.0D) {
                  sinc_x = Math.sin(3.141592653589793D * x) / (3.141592653589793D * x);
               } else {
                  sinc_x = 1.0D;
               }

               int_objeto[j + i * this.dim_Lx] = factor_rend * sinc_x * sinc_x;
            }
         }
      }

      int i;
      if (this.num_objetos == 1) {
         for(i = 0; i < dim_Total; ++i) {
            int_difred[i] = 1.0D;
         }
      } else {
         double periodo_x;
         double param;
         double sin_x;
         double sin_nx;
         if (this.num_objetos > 1 && this.num_objetos < 11) {
            periodo_x = dist_separa + dim_x_max;
            param = 3.141592653589793D * periodo_x * inv_lambda_f;

            for(i = 0; i < this.dim_Ly; ++i) {
               for(j = 0; j < this.dim_Lx; ++j) {
                  x = ((double)j - half_dim_Lx) * delta_pix;
                  sin_x = Math.sin(param * x);
                  sin_nx = Math.sin((double)this.num_objetos * param * x);
                  if (sin_x != 0.0D) {
                     int_difred[j + i * this.dim_Lx] = sin_nx * sin_nx / (sin_x * sin_x);
                  } else {
                     int_difred[j + i * this.dim_Lx] = (double)this.num_objetos * (double)this.num_objetos * 1.0D;
                  }
               }
            }
         } else {
            periodo_x = dist_separa + dim_x_max;
            double var10000 = dist_separa + dim_x_max;
            param = 3.141592653589793D * periodo_x * inv_lambda_f;

            for(i = 0; i < this.dim_Ly; ++i) {
               for(int j = 0; j < this.dim_Lx; ++j) {
                  x = ((double)j - half_dim_Lx) * delta_pix;
                  sin_x = Math.sin(param * x);
                  sin_nx = Math.sin((double)(this.num_objetos / 100) * param * x);
                  if (sin_x != 0.0D) {
                     int_difred[j + i * this.dim_Lx] = sin_nx * sin_nx / (sin_x * sin_x);
                  } else {
                     int_difred[j + i * this.dim_Lx] = (double)this.num_objetos * (double)this.num_objetos * 1.0D;
                  }
               }
            }
         }
      }

      for(i = 0; i < dim_Total; ++i) {
         brillo[i] = int_objeto[i] * int_difred[i];
      }

      brillo_0 = brillo[0];
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

      brillo_0 = (brillo_0 - min_brillo) / (max_brillo - min_brillo);
      if (brillo_0 >= 0.0D && brillo_0 <= 1.0D) {
         brillo_0 = (double)this.dim_Ly - 10.0D + 5.0D - brillo_0 * (double)(this.dim_Ly - 10);
      } else {
         brillo_0 = 0.0D;
      }

      for(i = 0; i < dim_Total; ++i) {
         brillo[i] = (brillo[i] - min_brillo) / (max_brillo - min_brillo);
         brillo[i] = (double)this.dim_Ly - 10.0D + 5.0D - brillo[i] * (double)(this.dim_Ly - 10);
      }

      g2.setPaint(ncolor);

      for(i = 0; i < this.dim_Lx; ++i) {
         g2.draw(new Double((double)(i - 1), brillo_0, (double)i, brillo[i]));
         brillo_0 = brillo[i];
      }

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
