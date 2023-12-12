package ull;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import javax.swing.JComponent;

public class Calculo extends JComponent {
   static double miopia;
   static double astx;
   static double asty;
   double n = 1.0D;
   static double s_prima = 22.26D;
   static double s_eix = -5001.0D;
   static double s_primaX = 22.26D;
   static double s_primaY = 22.26D;
   double s_corregida;
   static double scercano = -250.0D;
   static double sremoto;
   static double sremotoX;
   static double sremotoY;
   static double scercanoX = -250.0D;
   static double scercanoY = -250.0D;
   double s_retina;
   double f_max;
   double f_min;
   double f;
   static boolean acomoda = true;
   static boolean acomodaX = true;
   static boolean acomodaY = true;
   double iris = 5.0D;
   static double r = 0.5D;
   static double rX = 0.5D;
   static double rY = 0.5D;
   int fil = 256;
   static int[] psf;
   Image impsf;
   BufferedImage bufimpsf;
   double beta;
   double h_ob;
   Graphics2D g2;

   public void calcula(double miopia, double s_eix) {
      s_eix = -s_eix * 10.0D;
      Calculo.s_eix = s_eix;
      miopia *= 0.001D;
      Calculo.miopia = miopia;
      this.s_retina = 22.26D;
      double distx = 0.0D;
      scercano = -250.0D;
      double sabs = -s_eix;
      psf = new int[this.fil * this.fil];
      this.f_max = this.s_retina;
      double D_lejos = 1.0D / this.f_max - miopia;
      this.f_max = 1.0D / D_lejos;
      sremoto = this.s_retina * this.f_max / (this.n * (this.f_max - this.s_retina));
      this.f_min = this.n * scercano * this.s_retina / (this.n * scercano - this.s_retina);
      double D_cerca = 1.0D / this.f_min - miopia;
      this.f_min = 1.0D / D_cerca;
      scercano = this.s_retina * this.f_min / (this.n * (this.f_min - this.s_retina));
      this.f = this.n * s_eix * this.s_retina / (this.n * s_eix - this.s_retina);
      if (s_eix < -5000.0D) {
         this.f = this.f_max;
      }

      int temp;
      if (!Double.isInfinite(sremoto)) {
         sremoto *= 100.0D;
         temp = (int)Math.round(sremoto);
         sremoto = (double)temp / 100.0D;
      }

      scercano *= 100.0D;
      temp = (int)scercano;
      scercano = (double)temp / 100.0D;
      if (miopia > 0.0D) {
         if (scercano < 0.0D && sabs >= Math.abs(scercano)) {
            acomoda = true;
         } else {
            this.f = this.f_min;
            acomoda = false;
         }
      } else if (miopia < 0.0D) {
         if (sabs <= Math.abs(sremoto) && sabs >= Math.abs(scercano)) {
            acomoda = true;
         } else if (sabs > Math.abs(sremoto)) {
            this.f = this.f_max;
            acomoda = false;
         } else if (sabs < Math.abs(scercano)) {
            this.f = this.f_min;
            acomoda = false;
         }
      } else if (miopia == 0.0D) {
         if (sabs < Math.abs(scercano)) {
            acomoda = false;
            this.f = this.f_min;
         } else {
            acomoda = true;
         }
      }

      this.h_ob = sabs * 10.0D / 25.0D;
      if (!acomoda) {
         s_prima = this.n * this.f * s_eix / (this.n * s_eix + this.f);
         distx = Math.abs(this.s_retina - s_prima);
         r = this.iris * distx / s_prima;
         r = r * s_eix / this.s_retina;
         r = r * 128.0D / this.h_ob;

         for(int i = 0; i < this.fil; ++i) {
            for(int j = 0; j < this.fil; ++j) {
               double punt = Math.pow((double)i - 128.0D, 2.0D) + Math.pow((double)j - 128.0D, 2.0D);
               if (punt <= r * r) {
                  psf[i * this.fil + j] = 255;
               }

               psf[i] |= -16777216 | psf[i] << 16 | psf[i] << 8;
            }
         }
      } else {
         s_prima = this.s_retina;
         r = 0.5D;

         int i;
         for(i = 0; i < this.fil * this.fil; ++i) {
            psf[i] = 0;
         }

         psf[this.fil / 2 * this.fil + this.fil / 2] = 255;

         for(i = 0; i < this.fil * this.fil; ++i) {
            psf[i] |= -16777216 | psf[i] << 16 | psf[i] << 8;
         }

         try {
            this.impsf = this.createImage(new MemoryImageSource(this.fil, this.fil, psf, 0, this.fil));
            this.bufimpsf = new BufferedImage(this.fil, this.fil, 2);
            this.g2 = this.bufimpsf.createGraphics();

            do {
               this.g2.drawImage(this.impsf, 0, 0, this);
            } while(this.bufimpsf.getRGB(this.fil - 1, this.fil - 1) == 0);

            Ull.panIm3.setImage(this.bufimpsf);
            Ull.panIm3.repaint();
         } catch (Exception var18) {
            var18.printStackTrace();
         }
      }

   }

   public void calcula(double astx, double asty, double s_eix) {
      s_eix = -s_eix * 10.0D;
      Calculo.s_eix = s_eix;
      astx *= 0.001D;
      asty *= 0.001D;
      Calculo.astx = astx;
      Calculo.asty = asty;
      this.s_retina = 22.26D;
      double distx = 0.0D;
      scercano = -250.0D;
      double sabs = -s_eix;
      psf = new int[this.fil * this.fil];
      double fX = this.n * s_eix * this.s_retina / (this.n * s_eix - this.s_retina);
      double fY = fX;
      if (s_eix < -5000.0D) {
         this.f = this.f_max;
      }

      double f_maxX = this.s_retina;
      double D_lejosX = 1.0D / f_maxX - astx;
      f_maxX = 1.0D / D_lejosX;
      sremotoX = this.s_retina * f_maxX / (this.n * (f_maxX - this.s_retina));
      double f_minX = this.n * scercano * this.s_retina / (this.n * scercano - this.s_retina);
      double D_cercaX = 1.0D / f_minX - astx;
      f_minX = 1.0D / D_cercaX;
      scercanoX = this.s_retina * f_minX / (this.n * (f_minX - this.s_retina));
      int temp;
      if (!Double.isInfinite(sremotoX)) {
         sremotoX *= 100.0D;
         temp = (int)Math.round(sremotoX);
         sremotoX = (double)temp / 100.0D;
      }

      scercanoX *= 100.0D;
      temp = (int)scercanoX;
      scercanoX = (double)temp / 100.0D;
      double f_maxY = this.s_retina;
      double D_lejosY = 1.0D / f_maxY - asty;
      f_maxY = 1.0D / D_lejosY;
      sremotoY = this.s_retina * f_maxY / (this.n * (f_maxY - this.s_retina));
      double f_minY = this.n * scercano * this.s_retina / (this.n * scercano - this.s_retina);
      double D_cercaY = 1.0D / f_minY - asty;
      f_minY = 1.0D / D_cercaY;
      scercanoY = this.s_retina * f_minY / (this.n * (f_minY - this.s_retina));
      if (!Double.isInfinite(sremotoY)) {
         sremotoY *= 100.0D;
         temp = (int)Math.round(sremotoY);
         sremotoY = (double)temp / 100.0D;
      }

      scercanoY *= 100.0D;
      temp = (int)scercanoY;
      scercanoY = (double)temp / 100.0D;
      if (astx > 0.0D) {
         if (scercanoX < 0.0D && sabs >= Math.abs(scercanoX)) {
            acomodaX = true;
         } else {
            fX = f_minX;
            acomodaX = false;
         }
      } else if (astx < 0.0D) {
         if (sabs <= Math.abs(sremotoX) && sabs >= Math.abs(scercanoX)) {
            acomodaX = true;
         } else if (sabs > Math.abs(sremotoX)) {
            fX = f_maxX;
            acomodaX = false;
         } else if (sabs < Math.abs(scercanoX)) {
            fX = f_minX;
            acomodaX = false;
         }
      } else if (astx == 0.0D) {
         if (sabs < Math.abs(scercanoX)) {
            acomodaX = false;
            fX = f_minX;
         } else {
            acomodaX = true;
         }
      }

      if (asty > 0.0D) {
         if (scercanoY < 0.0D && sabs >= Math.abs(scercanoY)) {
            acomodaY = true;
         } else {
            fY = f_minY;
            acomodaY = false;
         }
      } else if (asty < 0.0D) {
         if (sabs <= Math.abs(sremotoY) && sabs >= Math.abs(scercanoY)) {
            acomodaY = true;
         } else if (sabs > Math.abs(sremotoY)) {
            fY = f_maxY;
            acomodaY = false;
         } else if (sabs < Math.abs(scercanoY)) {
            fY = f_minY;
            acomodaY = false;
         }
      } else if (asty == 0.0D) {
         if (sabs < Math.abs(scercanoY)) {
            acomodaY = false;
            fY = f_minY;
         } else {
            acomodaY = true;
         }
      }

      this.h_ob = sabs * 10.0D / 25.0D;
      if (!acomodaX) {
         s_prima = this.n * fX * s_eix / (this.n * s_eix + fX);
         s_primaX = s_prima;
         distx = Math.abs(this.s_retina - s_prima);
         rX = this.iris * distx / s_prima;
         rX = rX * s_eix / this.s_retina;
         rX = rX * 128.0D / this.h_ob;
      } else {
         rX = 0.5D;
         s_primaX = 22.26D;
      }

      if (!acomodaY) {
         s_prima = this.n * fY * s_eix / (this.n * s_eix + fY);
         s_primaY = s_prima;
         distx = Math.abs(this.s_retina - s_prima);
         rY = this.iris * distx / s_prima;
         rY = rY * s_eix / this.s_retina;
         rY = rY * 128.0D / this.h_ob;
      } else {
         rY = 0.5D;
         s_primaY = 22.26D;
      }

      if (acomodaX && acomodaY) {
         acomoda = true;
         s_prima = this.s_retina;

         int i;
         for(i = 0; i < this.fil * this.fil; ++i) {
            psf[i] = 0;
         }

         psf[this.fil / 2 * this.fil + this.fil / 2] = 255;

         for(i = 0; i < this.fil * this.fil; ++i) {
            psf[i] |= -16777216 | psf[i] << 16 | psf[i] << 8;
         }

         try {
            this.impsf = this.createImage(new MemoryImageSource(this.fil, this.fil, psf, 0, this.fil));
            this.bufimpsf = new BufferedImage(this.fil, this.fil, 2);
            this.g2 = this.bufimpsf.createGraphics();

            do {
               this.g2.drawImage(this.impsf, 0, 0, this);
            } while(this.bufimpsf.getRGB(this.fil - 1, this.fil - 1) == 0);

            Ull.panIm3.setImage(this.bufimpsf);
            Ull.panIm3.repaint();
         } catch (Exception var42) {
            var42.printStackTrace();
         }
      } else {
         acomoda = false;

         for(int i = 0; i < this.fil; ++i) {
            for(int j = 0; j < this.fil; ++j) {
               double punt = Math.pow((double)i - 128.0D, 2.0D) + Math.pow((double)j - 128.0D, 2.0D);
               double rquad = rX * rX * Math.pow((double)j - 128.0D, 2.0D) + rY * rY * Math.pow((double)i - 128.0D, 2.0D);
               rquad = rX * rX * rY * rY * punt / rquad;
               punt *= 100.0D;
               temp = (int)Math.round(punt);
               punt = (double)temp / 100.0D;
               rquad *= 100.0D;
               temp = (int)Math.round(rquad);
               rquad = (double)temp / 100.0D;
               if (punt <= rquad) {
                  psf[j * this.fil + i] = 255;
               }

               psf[this.fil / 2 + this.fil / 2 * this.fil] = 255;
               psf[i] |= -16777216 | psf[i] << 16 | psf[i] << 8;
            }
         }
      }

   }
}
