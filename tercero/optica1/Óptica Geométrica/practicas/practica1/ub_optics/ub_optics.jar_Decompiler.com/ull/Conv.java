package ull;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import local.fourier.Fft2;

public class Conv extends JComponent {
   Image conv;
   Image imagen;
   BufferedImage bufconv;
   int fil = 256;
   Graphics2D g2;
   double radiX;
   double radiY;
   double radi;
   double punt;
   double rquad;
   double temp;
   Calculo calc = new Calculo();

   public void convoluciona() {
      this.radi = Calculo.r;
      this.radiX = Calculo.rX;
      this.radiY = Calculo.rY;
      int[] pixels = new int[this.fil * this.fil];
      int[] psf = new int[this.fil * this.fil];
      double[] matriur = new double[this.fil * this.fil];
      double[] matriui = new double[this.fil * this.fil];
      double[] psfr = new double[this.fil * this.fil];
      double[] psfi = new double[this.fil * this.fil];
      String url_string;
      if (VentanaSliders.numImagen == 0) {
         url_string = Ull.nombre[Ull.lang];
      } else if (VentanaSliders.numImagen == 1) {
         url_string = "monitor.jpg";
      } else {
         url_string = "arbre.jpg";
      }

      try {
         URL url_imagen = this.getClass().getResource(url_string);
         this.imagen = ImageIO.read(url_imagen);
      } catch (Exception var16) {
         System.err.println("Algo pasa con la imagen");
      }

      int i;
      int i;
      if (!VentanaSliders.ast) {
         if (!Calculo.acomoda) {
            for(i = 0; i < this.fil; ++i) {
               for(i = 0; i < this.fil; ++i) {
                  this.punt = Math.pow((double)i - 128.0D, 2.0D) + Math.pow((double)i - 128.0D, 2.0D);
                  if (this.punt <= this.radi * this.radi) {
                     psf[i * this.fil + i] = 255;
                  }
               }
            }
         }
      } else if (!Calculo.acomodaX || !Calculo.acomodaY) {
         for(i = 0; i < this.fil; ++i) {
            for(i = 0; i < this.fil; ++i) {
               this.punt = Math.pow((double)i - 128.0D, 2.0D) + Math.pow((double)i - 128.0D, 2.0D);
               this.rquad = this.radiX * this.radiX * Math.pow((double)i - 128.0D, 2.0D) + this.radiY * this.radiY * Math.pow((double)i - 128.0D, 2.0D);
               this.rquad = this.radiX * this.radiX * this.radiY * this.radiY * this.punt / this.rquad;
               this.punt *= 100.0D;
               this.temp = (double)((int)Math.round(this.punt));
               this.punt = this.temp / 100.0D;
               this.rquad *= 100.0D;
               this.temp = (double)((int)Math.round(this.rquad));
               this.rquad = this.temp / 100.0D;
               if (this.punt <= this.rquad) {
                  psf[i * this.fil + i] = 255;
               }

               psf[this.fil / 2 + this.fil / 2 * this.fil] = 255;
            }
         }
      }

      if (!Calculo.acomoda) {
         PixelGrabber pg = new PixelGrabber(this.imagen, 0, 0, this.imagen.getWidth(this), this.imagen.getHeight(this), pixels, 0, this.imagen.getWidth(this));

         try {
            pg.grabPixels();
         } catch (InterruptedException var15) {
            System.err.println("interrupted waiting for pixels!");
            return;
         }

         for(i = 0; i < this.fil * this.fil; ++i) {
            int p = pixels[i];
            pixels[i] = 255 & p >> 16;
            int g = 255 & p >> 8;
            int var12 = 255 & p;
         }

         for(i = 0; i < this.fil * this.fil; ++i) {
            matriur[i] = (double)pixels[i];
            psfr[i] = (double)psf[i];
         }

         Fft2.convolucio(this.fil, this.fil, matriur, matriui, psfr, psfi);
         double max = Math.sqrt(matriur[0] * matriur[0] + matriui[0] * matriui[0]);
         double min = Math.sqrt(matriur[0] * matriur[0] + matriui[0] * matriui[0]);

         for(int i = 0; i < this.fil * this.fil; ++i) {
            matriur[i] = Math.sqrt(matriur[i] * matriur[i] + matriui[i] * matriui[i]);
            if (matriur[i] > max) {
               max = matriur[i];
            }

            if (matriur[i] < min) {
               min = matriur[i];
            }
         }

         Color color = Color.black;

         for(int i = 0; i < this.fil * this.fil; ++i) {
            pixels[i] = (int)(255.0D * (matriur[i] - min) / (max - min));
            pixels[i] |= -16777216 | pixels[i] << 16 | pixels[i] << 8;
            psf[i] |= -16777216 | psf[i] << 16 | psf[i] << 8;
         }

         try {
            this.conv = this.createImage(new MemoryImageSource(this.fil, this.fil, pixels, 0, this.fil));
            this.bufconv = new BufferedImage(this.fil, this.fil, 2);
            this.g2 = this.bufconv.createGraphics();

            do {
               this.g2.drawImage(this.conv, 0, 0, this);
            } while(this.bufconv.getRGB(this.fil - 1, this.fil - 1) == 0);

            Ull.panIm2.setImage(this.bufconv);
            Ull.panIm2.repaint();
         } catch (Exception var18) {
            var18.printStackTrace();
         }

         try {
            this.conv = this.createImage(new MemoryImageSource(this.fil, this.fil, psf, 0, this.fil));
            this.bufconv = new BufferedImage(this.fil, this.fil, 2);
            this.g2 = this.bufconv.createGraphics();

            do {
               this.g2.drawImage(this.conv, 0, 0, this);
            } while(this.bufconv.getRGB(this.fil - 1, this.fil - 1) == 0);

            Ull.panIm3.setImage(this.bufconv);
            Ull.panIm3.repaint();
         } catch (Exception var17) {
            var17.printStackTrace();
         }
      } else {
         Ull.panIm2.setImage(this.imagen);
         Ull.panIm2.repaint();
      }

   }
}
