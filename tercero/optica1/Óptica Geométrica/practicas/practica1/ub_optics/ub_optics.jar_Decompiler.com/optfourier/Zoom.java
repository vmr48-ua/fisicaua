package optfourier;

import java.awt.Frame;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import local.imagenes.OptImagen;

public class Zoom {
   ImageIcon icon_joc = null;

   public Zoom() {
      try {
         String st_icon = "jocon.jpg";
         URL url_icon = this.getClass().getResource(st_icon);
         this.icon_joc = new ImageIcon(url_icon, "Òptica de Fourier");
      } catch (Exception var3) {
         System.out.println("No carga icono");
      }

   }

   public void hacezoom(OptImagen imag, float zcol, float zfil, int fil0, int fil, int col0, int col, int[] matcz) {
      Frame f = new Frame();
      if (this.icon_joc != null) {
         f.setIconImage(this.icon_joc.getImage());
      }

      boolean control = false;
      String[] interp = new String[]{"Interpolació de píxels?", "¿Interpolación de píxels?", "Pixel interpolation?"};
      int nfiltot = imag.nfil;
      int ncoltot = imag.ncol;
      int fil1 = fil0 + fil - 1;
      int col1 = col0 + col - 1;
      int ibr = 0;
      int jbr = 0;
      int iib = false;
      int jjb = 0;
      int deltac = false;
      int deltaf = false;
      int m = false;
      if (fil1 >= nfiltot) {
         fil1 = nfiltot - 1;
         fil = fil1 - fil0 + 1;
      }

      if (col1 >= ncoltot) {
         col1 = ncoltot - 1;
         col = col1 - col0 + 1;
      }

      int nfilz = (int)((float)fil * zfil);
      int ncolz = (int)((float)col * zcol);
      System.out.println(nfilz);
      int[] matc = new int[nfiltot * ncoltot];

      int i;
      int j;
      for(i = 0; i < nfiltot; ++i) {
         for(j = 0; j < ncoltot; ++j) {
            matc[i * col + j] = imag.buffImage.getRGB(j, i) & 255;
         }
      }

      if (zfil > 1.0F || zcol > 1.0F) {
         Object[] options = new Object[]{Holog.SiNo[0][OptFou.idiom], Holog.SiNo[1][OptFou.idiom]};
         JOptionPane hola = new JOptionPane(interp[OptFou.idiom], 1, -1, (Icon)null, options);
         JDialog dialog = hola.createDialog(f, "");
         dialog.setVisible(true);
         if (options[0].equals(hola.getValue())) {
            control = false;
         }
      }

      int ib;
      int ii;
      int im;
      int iim;
      int jb;
      int jj;
      if (zfil < 1.0F) {
         i = fil0;
         ib = 0;

         for(ii = 0; i <= fil1; ++ib) {
            if (!((float)ib * zfil < (float)ii)) {
               im = i * ncoltot;
               iim = ii * ncolz;
               j = col0;
               jb = 0;

               for(jj = 0; j <= col1; ++jb) {
                  if (!((float)jb * zcol < (float)jj)) {
                     matcz[iim + jj] = matc[im + j];
                     ++jj;
                     if (jj >= ncolz) {
                        break;
                     }
                  }

                  ++j;
               }

               ++ii;
               if (ii >= nfilz) {
                  break;
               }
            }

            ++i;
         }

      } else {
         if (zcol < 1.0F && zfil > 1.0F) {
            j = fil0;
            jb = 0;

            for(jj = 0; j <= fil1; ++jb) {
               if (!((float)jb * zcol < (float)jj)) {
                  i = col0;
                  ib = 0;

                  for(ii = 0; i <= col1; ++ib) {
                     if (!((float)ib * zcol < (float)ii)) {
                        im = i * ncoltot;
                        iim = ii * ncolz;
                        matcz[iim + jj] = matc[im + j];
                        ++ii;
                        if (ii >= nfilz) {
                           break;
                        }
                     }

                     ++i;
                  }

                  ++jj;
                  if (jj >= ncolz) {
                     break;
                  }
               }

               ++j;
            }
         }

         int[] di = new int[fil];
         int[] dj = new int[col];
         i = fil0;
         ib = 0;

         for(ii = 0; i <= fil1; ++ii) {
            if (!((float)ib * zfil > (float)ii)) {
               if (ii >= nfilz) {
                  break;
               }

               im = i * ncoltot;
               iim = ii * ncolz;
               j = col0;
               jb = 0;

               for(jj = 0; j <= col1; ++jj) {
                  if (!((float)jb * zcol > (float)jj)) {
                     if (jj >= ncolz) {
                        break;
                     }

                     matcz[iim + jj] = matc[im + j];
                     jbr = jb;
                     dj[jb] = jj;
                     ++j;
                     ++jb;
                  }
               }

               di[ib] = ii;
               ibr = ib;
               ++i;
               ++ib;
            }
         }

         float db;
         int m;
         if (control) {
            for(i = 0; i <= ibr; ++i) {
               ii = di[i];
               iim = ii * ncolz;

               for(j = 0; j < jbr; ++j) {
                  jj = dj[j];
                  jjb = dj[j + 1];
                  int deltac = jjb - jj;

                  for(jb = jj + 1; jb < jjb; ++jb) {
                     db = (float)(matcz[iim + jjb] - matcz[iim + jj]);
                     db /= (float)deltac;
                     jb = jj + 1;

                     for(m = 1; jb < jj + deltac; ++m) {
                        matcz[iim + jb] = matcz[iim + jj] + (int)(db * (float)m);
                        ++jb;
                     }
                  }
               }

               for(jb = jjb + 1; jb < ncolz; ++jb) {
                  matcz[iim + jb] = matcz[iim + jjb];
               }
            }
         } else {
            for(i = 0; i <= ibr; ++i) {
               ii = di[i];
               iim = ii * ncolz;

               for(j = 0; j < jbr; ++j) {
                  jj = dj[j];
                  jjb = dj[j + 1];

                  for(jb = jj + 1; jb < jjb; ++jb) {
                     matcz[iim + jb] = matcz[iim + jj];
                  }
               }
            }
         }

         int iib;
         if (control) {
            for(jj = 0; jj < ncolz; ++jj) {
               for(i = 0; i < ibr; ++i) {
                  ii = di[i];
                  iib = di[i + 1];
                  iim = ii * ncolz;
                  int deltaf = iib - ii;
                  db = (float)(matcz[iib * ncolz + jj] - matcz[iim + jj]);
                  db /= (float)deltaf;
                  ib = ii + 1;

                  for(m = 1; ib < ii + deltaf; ++m) {
                     matcz[ib * ncolz + jj] = matcz[iim + jj] + (int)(db * (float)m);
                     ++ib;
                  }
               }
            }
         } else {
            for(jj = 0; jj < ncolz; ++jj) {
               for(i = 0; i < ibr; ++i) {
                  ii = di[i];
                  iib = di[i + 1];
                  iim = ii * ncolz;

                  for(ib = ii + 1; ib < iib; ++ib) {
                     matcz[ib * ncolz + jj] = matcz[iim + jj];
                  }
               }
            }
         }

      }
   }
}
