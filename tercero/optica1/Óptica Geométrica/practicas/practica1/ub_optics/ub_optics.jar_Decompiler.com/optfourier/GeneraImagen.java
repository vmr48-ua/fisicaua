package optfourier;

import local.imagenes.OptImagen;

public class GeneraImagen extends OptImagen {
   String[][] data = new String[][]{{"Quadrat", "Escletxa Vertical", "Escletxa Horitzontal", "Cercle", "Escena"}, {"Cuadrado", "Rendija Vertical", "Rendija Horizontal", "Círculo", "Escena"}, {"Square", "Vertical Slot", "Horizontal Slot", "Circle", "Scene"}};

   public GeneraImagen(String titulo, int ancho, int alto) {
      super(titulo, ancho, alto);
      super.nombre = titulo;
      super.ncol = ancho;
      super.nfil = alto;
   }

   public void generaImagen(String nom) {
      int nfil = super.nfil;
      int ncol = super.ncol;
      int[] mapa = new int[nfil * ncol];
      double radi = 64.0D;
      int lado = 8;
      int centro = 64;
      int i;
      int j;
      if (nom == this.data[OptFou.idiom][0]) {
         for(i = 0; i < nfil; ++i) {
            for(j = 0; j < ncol; ++j) {
               if (i > centro - lado && i < centro + lado && j > centro - lado && j < centro + lado) {
                  mapa[i * nfil + j] = 255;
               }
            }
         }
      }

      if (nom == this.data[OptFou.idiom][1]) {
         for(i = 0; i < nfil; ++i) {
            for(j = 0; j < ncol; ++j) {
               if (j > centro - lado && j < centro + lado) {
                  mapa[i * nfil + j] = 255;
               }
            }
         }
      }

      if (nom == this.data[OptFou.idiom][2]) {
         for(i = 0; i < nfil; ++i) {
            for(j = 0; j < ncol; ++j) {
               if (i > centro - lado && i < centro + lado) {
                  mapa[i * nfil + j] = 255;
               }
            }
         }
      }

      int i;
      int j;
      double punt;
      if (nom == this.data[OptFou.idiom][3]) {
         for(i = 0; i < nfil; ++i) {
            for(j = 0; j < ncol; ++j) {
               punt = Math.pow((double)i - 64.0D, 2.0D) + Math.pow((double)j - 64.0D, 2.0D);
               if (punt <= radi) {
                  mapa[i * nfil + j] = 255;
               }
            }
         }
      }

      if (nom == this.data[OptFou.idiom][4]) {
         for(i = 0; i < nfil; ++i) {
            for(j = 0; j < ncol; ++j) {
               punt = Math.pow((double)i - 32.0D, 2.0D) + Math.pow((double)j - 64.0D, 2.0D);
               if (punt <= radi) {
                  mapa[i * nfil + j] = 255;
               }

               if (i > 3 * centro / 2 - lado && i < 3 * centro / 2 + lado && j > centro - lado && j < centro + lado) {
                  mapa[i * nfil + j] = 255;
               }
            }
         }
      }

      for(i = 0; i < nfil * ncol; ++i) {
         mapa[i] |= -16777216 | mapa[i] << 16 | mapa[i] << 8;
      }

      this.creaVentanaOptImagen(this, nfil, ncol);

      for(i = 0; i < nfil; ++i) {
         for(j = 0; j < ncol; ++j) {
            super.buffImage.setRGB(j, i, mapa[i * nfil + j]);
         }
      }

      super.g2 = super.buffImage.createGraphics();

      do {
         super.g2.drawImage(super.buffImage, 0, 0, this);
      } while(super.buffImage.getRGB(nfil - 1, nfil - 1) == 0);

      super.bytesPixel = 1;
   }
}
