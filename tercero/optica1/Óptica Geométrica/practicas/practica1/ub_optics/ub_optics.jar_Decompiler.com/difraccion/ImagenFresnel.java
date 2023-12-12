package difraccion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import javax.swing.JPanel;

public class ImagenFresnel extends JPanel {
   int lambda;
   int tipo_objeto;
   double dim_x;
   double dim_y;
   int label_tipoimg;
   double umbral;
   int dim_Lx = 256;
   int dim_Ly = 256;
   int dim_Total;
   int[] pixels;
   MemoryImageSource source;
   Image img;

   public ImagenFresnel() {
      this.dim_Total = this.dim_Lx * this.dim_Ly;
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

   public void putAtributos(double tam_x, double tam_y, double[] matriz, int l_onda, int tipoimg, double fres_umbral) {
      this.lambda = l_onda;
      this.dim_x = tam_x;
      this.dim_y = tam_y;
      this.label_tipoimg = tipoimg;
      this.umbral = fres_umbral;
      double[] brillo = new double[this.dim_Total];
      YoungColor ycolores = new YoungColor();
      Color ncolor = ycolores.lambda2RGB(this.lambda);
      float[] hsb = new float[3];
      hsb = Color.RGBtoHSB(ncolor.getRed(), ncolor.getGreen(), ncolor.getBlue(), hsb);

      int i;
      for(i = 0; i < this.dim_Total; ++i) {
         brillo[i] = matriz[i];
      }

      if (this.label_tipoimg == 1) {
         for(i = 0; i < this.dim_Total; ++i) {
            brillo[i] *= brillo[i];
         }
      }

      if (this.label_tipoimg == 2) {
         for(i = 0; i < this.dim_Total; ++i) {
            brillo[i] *= brillo[i];
            brillo[i] = Math.log(brillo[i]);
         }
      }

      double max_brillo = brillo[0];
      double min_brillo = brillo[0];

      int i;
      for(i = 0; i < this.dim_Total; ++i) {
         if (max_brillo < brillo[i]) {
            max_brillo = brillo[i];
         }

         if (min_brillo > brillo[i]) {
            min_brillo = brillo[i];
         }
      }

      for(i = 0; i < this.dim_Total; ++i) {
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

   public void paint(Graphics g) {
      g.drawImage(this.img, 0, 0, this);
   }
}
