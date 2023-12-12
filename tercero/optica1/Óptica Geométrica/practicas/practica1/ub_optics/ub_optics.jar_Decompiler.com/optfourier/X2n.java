package optfourier;

import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class X2n {
   static ImageIcon icon_joc = null;
   static String[] mensaje = new String[]{"S'han afegit zeros a la imatge. Nova mida: ", "Se han añadido ceros a la imagen. Nuevo tamaño:  ", "Zeros has been added to the image. New size: "};

   public void xto2n(int nfil, int ncol, double[] x, double[] y) {
      boolean quadfil = false;
      boolean quadcol = false;
      int n = false;
      double[] newx = null;
      double[] newy = null;
      int newnfil = 0;
      int newncol = 0;

      int n;
      for(n = 0; n < 11; ++n) {
         if ((double)nfil == Math.pow(2.0D, (double)n)) {
            quadfil = true;
         }

         if ((double)ncol == Math.pow(2.0D, (double)n)) {
            quadcol = true;
         }
      }

      if (quadfil && quadcol) {
         newx = x;
         newy = y;
         newnfil = nfil;
         newncol = ncol;
      }

      if (!quadfil || !quadcol) {
         for(n = 0; n < 11; ++n) {
            if ((double)nfil > Math.pow(2.0D, (double)n) && (double)nfil < Math.pow(2.0D, (double)(n + 1))) {
               newnfil = (int)Math.pow(2.0D, (double)(n + 1));
            }

            if ((double)ncol > Math.pow(2.0D, (double)n) && (double)ncol < Math.pow(2.0D, (double)(n + 1))) {
               newncol = (int)Math.pow(2.0D, (double)(n + 1));
            }
         }

         if (newncol > newnfil) {
            newnfil = newncol;
         } else {
            newncol = newnfil;
         }

         newx = new double[newnfil * newncol];
         newy = new double[newnfil * newncol];

         for(int i = 0; i < nfil; ++i) {
            for(int j = 0; j < ncol; ++j) {
               newx[j + newncol / 2 - ncol / 2 + (i + newnfil / 2 - nfil / 2) * newncol] = x[j + i * ncol];
               newy[j + newncol / 2 - ncol / 2 + (i + newnfil / 2 - nfil / 2) * newncol] = y[j + i * ncol];
            }
         }

         try {
            String st_icon = "jocon.jpg";
            URL url_icon = this.getClass().getResource(st_icon);
            icon_joc = new ImageIcon(url_icon, "Òptica de Fourier");
         } catch (Exception var16) {
            System.out.println("No carga icono");
         }

         JFrame fr = new JFrame();
         if (icon_joc != null) {
            fr.setIconImage(icon_joc.getImage());
         }

         Object[] options = new Object[]{OptFou.aceptar[OptFou.idiom]};
         JOptionPane hola = new JOptionPane(mensaje[OptFou.idiom] + newnfil + "x" + newnfil, 1, -1, (Icon)null, options);
         JDialog dialog = hola.createDialog(fr, "");
         dialog.setVisible(true);
      }

      Holog.newreal = new double[newnfil * newncol];
      Holog.newimagin = new double[newnfil * newncol];
      Holog.newreal = newx;
      Holog.newimagin = newy;
   }

   public void xsto2n(int nfil1, int ncol1, double[] x, double[] y, int nfil2, int ncol2, double[] z, double[] w) {
      boolean quadfil1 = false;
      boolean quadcol1 = false;
      boolean quadfil2 = false;
      boolean quadcol2 = false;
      int n = false;
      double[] newx = null;
      double[] newy = null;
      double[] newz = null;
      double[] neww = null;
      int newnfil1 = 0;
      int newncol1 = 0;
      int newnfil2 = 0;
      int newncol2 = 0;

      int n;
      for(n = 0; n < 11; ++n) {
         if ((double)nfil1 == Math.pow(2.0D, (double)n)) {
            quadfil1 = true;
         }

         if ((double)ncol1 == Math.pow(2.0D, (double)n)) {
            quadcol1 = true;
         }

         if ((double)nfil2 == Math.pow(2.0D, (double)n)) {
            quadfil2 = true;
         }

         if ((double)ncol2 == Math.pow(2.0D, (double)n)) {
            quadcol2 = true;
         }
      }

      if (quadfil1 && quadcol1) {
         newx = x;
         newy = y;
         newnfil1 = nfil1;
         newncol1 = ncol1;
      }

      if (quadfil2 && quadcol2) {
         newz = z;
         neww = w;
         newnfil2 = nfil2;
         newncol2 = ncol2;
      }

      int i;
      int j;
      if (!quadfil1 || !quadcol1) {
         for(n = 0; n < 11; ++n) {
            if ((double)nfil1 > Math.pow(2.0D, (double)n) && (double)nfil1 < Math.pow(2.0D, (double)(n + 1))) {
               newnfil1 = (int)Math.pow(2.0D, (double)(n + 1));
            }

            if ((double)ncol1 > Math.pow(2.0D, (double)n) && (double)ncol1 < Math.pow(2.0D, (double)(n + 1))) {
               newncol1 = (int)Math.pow(2.0D, (double)(n + 1));
            }
         }

         if (newncol1 > newnfil1) {
            newnfil1 = newncol1;
         } else {
            newncol1 = newnfil1;
         }

         newx = new double[newnfil1 * newncol1];
         newy = new double[newnfil1 * newncol1];

         for(i = 0; i < nfil1; ++i) {
            for(j = 0; j < ncol1; ++j) {
               newx[j + newncol1 / 2 - ncol1 / 2 + (i + newnfil1 / 2 - nfil1 / 2) * newncol1] = x[j + i * ncol1];
               newy[j + newncol1 / 2 - ncol1 / 2 + (i + newnfil1 / 2 - nfil1 / 2) * newncol1] = y[j + i * ncol1];
            }
         }
      }

      Holog.newreals = new double[newnfil1 * newncol1];
      Holog.newimagis = new double[newnfil1 * newncol1];
      Holog.newreals = newx;
      Holog.newimagis = newy;
      if (!quadfil2 || !quadcol2) {
         for(n = 0; n < 11; ++n) {
            if ((double)nfil2 > Math.pow(2.0D, (double)n) && (double)nfil2 < Math.pow(2.0D, (double)(n + 1))) {
               newnfil2 = (int)Math.pow(2.0D, (double)(n + 1));
            }

            if ((double)ncol2 > Math.pow(2.0D, (double)n) && (double)ncol2 < Math.pow(2.0D, (double)(n + 1))) {
               newncol2 = (int)Math.pow(2.0D, (double)(n + 1));
            }
         }

         if (newncol2 > newnfil2) {
            newnfil2 = newncol2;
         } else {
            newncol2 = newnfil2;
         }

         newz = new double[newnfil2 * newncol2];
         neww = new double[newnfil2 * newncol2];

         for(i = 0; i < nfil2; ++i) {
            for(j = 0; j < ncol2; ++j) {
               newz[j + newncol2 / 2 - ncol2 / 2 + (i + newnfil2 / 2 - nfil2 / 2) * newncol2] = z[j + i * ncol2];
               neww[j + newncol2 / 2 - ncol2 / 2 + (i + newnfil2 / 2 - nfil2 / 2) * newncol2] = w[j + i * ncol2];
            }
         }
      }

      Holog.newrealr = new double[newnfil2 * newncol2];
      Holog.newimagir = new double[newnfil2 * newncol2];
      Holog.newrealr = newz;
      Holog.newimagir = neww;
   }
}
