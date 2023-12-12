package optfourier;

import java.awt.Frame;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import local.fourier.Fft2;
import local.imagenes.OptImagen;

public class Holog {
   static int numImagen;
   static int numImagenDestino = 0;
   static int numImagen1 = 0;
   static int numImagen2 = 0;
   static int numOperacio = 0;
   static int maxNumImagen = 20;
   static OptImagen[] imagen;
   static boolean[] controlImagen;
   static String[] desti;
   static String[][] im;
   static String[][] existe;
   static String[] carrega;
   String[] salva = new String[]{"Desat al disc arxiu finestra n. ", "Guardado en el disco archivo ventana n. ", "Saved to disk file in window n. "};
   String[] tf = new String[]{"TF ", "TF ", "FT "};
   String[] finestra = new String[]{"finestra ", "ventana ", "window "};
   String[] resultat = new String[]{"Resultat a la finestra ", "Resultado en la ventana ", "Result at window "};
   String[] escena = new String[]{"Escena ", "Escena ", "Scene "};
   String[] ref = new String[]{"Referència ", "Referencia ", "Reference "};
   String[] entre = new String[]{" entre les imatges ", " entre las imágenes ", " between images "};
   String[] y = new String[]{" i ", " y ", " and "};
   String[] holograma = new String[]{"Holograma", "Holograma", "Hologram"};
   String[][] dela = new String[][]{{" de la", " de la", " of"}, {" de les", " de las", " of"}};
   String[] guarda = new String[]{"Desat\n", "Guardado\n", "Saved\n"};
   static String[] gran;
   String[] factor = new String[]{"Factor", "Factor", "Factor"};
   String[][] filcol = new String[][]{{"Fila inicial?", "Fila inicial?", "First row?"}, {"Nombre de files zoom", "Número de filas zoom", "Number of rows to zoom"}, {"Columna inicial?", "Columna inicial?", "First column?"}, {"Nombre de columnes zoom", "Número de columnas zoom", "Number of columns to zoom"}};
   static String[] sobrescriure;
   String[] diferents = new String[]{"Imatges de diferent mida", "Imágenes de diferente tamaño", "Different size images"};
   String[] iguals = new String[]{"Imatges origen i destí iguals", "Imagenes origen y destino iguales", "Same original and final image"};
   static int nmin;
   static SimpleDateFormat hms;
   static String resp;
   String fichero = new String();
   static String[][] SiNo;
   String[][] errorFilas = new String[][]{{"Primera fila incorrecta", "Primera fila incorrecta", "Non valid first row"}, {"Nombre de files incorrecte", "Número de filas incorrecto", "Non valid number of rows"}, {"Primera columna incorrecta", "Primera columna incorrecta", "Non valid first column"}, {"Nombre de columnes incorrecte", "Número de columnas incorrecto", "Non valid number of columns"}};
   static String[][] numberError;
   static ImageIcon icon_joc;
   static String[] denom;
   double sumDen = 1.0E-8D;
   static double[] newreal;
   static double[] newimagin;
   static double[] newrealr;
   static double[] newimagir;
   static double[] newreals;
   static double[] newimagis;
   X2n x2n = new X2n();

   public Holog() {
      try {
         numImagen = -1;
         String st_icon = "jocon.jpg";
         URL url_icon = this.getClass().getResource(st_icon);
         icon_joc = new ImageIcon(url_icon, "Òptica de Fourier");
      } catch (Exception var3) {
         System.out.println("No carga icono");
      }

   }

   public static void carregar() {
      JFrame f = new JFrame();
      String[] preguntas = new String[]{desti[OptFou.idiom]};
      String[] var10000 = new String[1];
      String var10003 = resp;
      var10000[0] = String.valueOf(nmin);
      String[] respuestas = var10000;
      boolean control = false;
      if (icon_joc != null) {
         f.setIconImage(icon_joc.getImage());
      }

      if (Dialogos.optPreguntas(f, preguntas, respuestas, OptFou.idiom)) {
         try {
            numImagen = Integer.valueOf(respuestas[0]);
         } catch (NumberFormatException var9) {
            errorNumber(1);
            return;
         }

         Frame fr;
         Object[] options;
         JOptionPane hola;
         JDialog dialog;
         if (numImagen <= maxNumImagen) {
            if (controlImagen[numImagen]) {
               fr = new Frame();
               if (icon_joc != null) {
                  fr.setIconImage(icon_joc.getImage());
               }

               options = new Object[]{SiNo[0][OptFou.idiom], SiNo[1][OptFou.idiom]};
               hola = new JOptionPane(existe[0][OptFou.idiom] + ". " + sobrescriure[OptFou.idiom], 1, -1, (Icon)null, options);
               dialog = hola.createDialog(fr, "");
               dialog.setVisible(true);
               if (options[0].equals(hola.getValue())) {
                  imagen[numImagen].setVisible(false);
                  controlImagen[numImagen] = false;
                  --nmin;
               }
            }

            if (!controlImagen[numImagen]) {
               String nombreImagen = im[0][OptFou.idiom] + numImagen;
               imagen[numImagen] = new OptImagen(nombreImagen, 256, 256);
               if (icon_joc != null) {
                  imagen[numImagen].setIconImage(icon_joc.getImage());
               }

               if (imagen[numImagen].leeFicheroDeImagen(imagen[numImagen]) != 0) {
                  control = true;
                  controlImagen[numImagen] = true;

                  try {
                     OptFou.vect.get(numImagen);
                  } catch (ArrayIndexOutOfBoundsException var8) {
                     OptFou.vect.add(numImagen, nombreImagen);
                  }

                  OptFou.jCombo.setSelectedIndex(numImagen);
                  ++nmin;
                  if (nmin > 0 && !OptFou.hab) {
                     OptFou.habilita();
                  }
               }
            }
         } else {
            fr = new Frame();
            if (icon_joc != null) {
               fr.setIconImage(icon_joc.getImage());
            }

            options = new Object[]{OptFou.aceptar[OptFou.idiom]};
            hola = new JOptionPane("n > 19!", 0, -1, (Icon)null, options);
            dialog = hola.createDialog(fr, "");
            dialog.setVisible(true);
         }

         if (control) {
            if (imagen[numImagen].ncol * imagen[numImagen].nfil > 412500) {
               imagen[numImagen].setVisible(false);
               --nmin;
               controlImagen[numImagen] = false;
               fr = new Frame();
               if (icon_joc != null) {
                  fr.setIconImage(icon_joc.getImage());
               }

               options = new Object[]{OptFou.aceptar[OptFou.idiom]};
               hola = new JOptionPane(gran[OptFou.idiom], 0, -1, (Icon)null, options);
               dialog = hola.createDialog(fr, "");
               dialog.setVisible(true);
               return;
            }

            ++numOperacio;
            FrameInfo.info.append(" " + numOperacio + ". " + carrega[OptFou.idiom] + numImagen + ": " + imagen[numImagen].fichero + "\n\n");
            Date fecha = new Date();
            String hora = hms.format(fecha);
            OptFou.textoTime[numImagen] = hora;
            OptFou.texto[numImagen] = " " + imagen[numImagen].fichero;
            OptFou.textoDim[numImagen] = imagen[numImagen].ncol + " x " + imagen[numImagen].nfil;
            OptFou.jTextoTime.setText(OptFou.textoTime[numImagen]);
            OptFou.jTextoDim.setText(OptFou.textoDim[numImagen]);
            OptFou.jTexto.setText(OptFou.texto[numImagen]);
            OptFou.save.setText(OptFou.salvat[OptFou.idiom]);
            OptFou.save.setIcon(OptFou.save_joc);
            OptFou.salvado[numImagen] = true;
         }
      }

   }

   public static void generar(String nombre) {
      JFrame f = new JFrame();
      String[] preguntas = new String[]{desti[OptFou.idiom]};
      String[] var10000 = new String[1];
      String var10003 = resp;
      var10000[0] = String.valueOf(nmin);
      String[] respuestas = var10000;
      boolean control = false;
      nombre = Selector.nombre;
      if (icon_joc != null) {
         f.setIconImage(icon_joc.getImage());
      }

      if (Dialogos.optPreguntas(f, preguntas, respuestas, OptFou.idiom)) {
         try {
            numImagen = Integer.valueOf(respuestas[0]);
         } catch (NumberFormatException var10) {
            errorNumber(1);
            return;
         }

         Frame fr;
         Object[] options;
         JOptionPane hola;
         JDialog dialog;
         if (numImagen <= maxNumImagen) {
            if (controlImagen[numImagen]) {
               fr = new Frame();
               if (icon_joc != null) {
                  fr.setIconImage(icon_joc.getImage());
               }

               options = new Object[]{SiNo[0][OptFou.idiom], SiNo[1][OptFou.idiom]};
               hola = new JOptionPane(existe[0][OptFou.idiom] + ". " + sobrescriure[OptFou.idiom], 1, -1, (Icon)null, options);
               dialog = hola.createDialog(fr, "");
               dialog.setVisible(true);
               if (options[0].equals(hola.getValue())) {
                  imagen[numImagen].setVisible(false);
                  controlImagen[numImagen] = false;
                  --nmin;
               }
            }

            if (!controlImagen[numImagen]) {
               String nombreImagen = im[0][OptFou.idiom] + numImagen;
               imagen[numImagen] = new OptImagen(nombreImagen, 128, 128);
               if (icon_joc != null) {
                  imagen[numImagen].setIconImage(icon_joc.getImage());
               }

               GeneraImagen figura = new GeneraImagen(nombreImagen, 128, 128);
               figura.generaImagen(nombre);
               if (icon_joc != null) {
                  figura.setIconImage(icon_joc.getImage());
               }

               imagen[numImagen] = figura;
               imagen[numImagen].ncol = figura.ncol;
               imagen[numImagen].nfil = figura.nfil;
               imagen[numImagen].buffImage = figura.buffImage;
               imagen[numImagen].bytesPixel = figura.bytesPixel;
               control = true;
               controlImagen[numImagen] = true;

               try {
                  OptFou.vect.get(numImagen);
               } catch (ArrayIndexOutOfBoundsException var9) {
                  OptFou.vect.add(numImagen, nombreImagen);
               }

               OptFou.jCombo.setSelectedIndex(numImagen);
               ++nmin;
               if (nmin > 0 && !OptFou.hab) {
                  OptFou.habilita();
               }
            } else {
               fr = new Frame();
               if (icon_joc != null) {
                  fr.setIconImage(icon_joc.getImage());
               }

               options = new Object[]{OptFou.aceptar[OptFou.idiom]};
               hola = new JOptionPane(existe[0][OptFou.idiom], 0, -1, (Icon)null, options);
               dialog = hola.createDialog(fr, "");
               dialog.setVisible(true);
            }
         } else {
            fr = new Frame();
            if (icon_joc != null) {
               fr.setIconImage(icon_joc.getImage());
            }

            options = new Object[]{OptFou.aceptar[OptFou.idiom]};
            hola = new JOptionPane("n > 19!", 0, -1, (Icon)null, options);
            dialog = hola.createDialog(fr, "");
            dialog.setVisible(true);
         }

         if (control) {
            if (imagen[numImagen].ncol * imagen[numImagen].nfil > 412500) {
               imagen[numImagen].setVisible(false);
               --nmin;
               controlImagen[numImagen] = false;
               fr = new Frame();
               if (icon_joc != null) {
                  fr.setIconImage(icon_joc.getImage());
               }

               options = new Object[]{OptFou.aceptar[OptFou.idiom]};
               hola = new JOptionPane(gran[OptFou.idiom], 0, -1, (Icon)null, options);
               dialog = hola.createDialog(fr, "");
               dialog.setVisible(true);
               return;
            }

            ++numOperacio;
            FrameInfo.info.append(" " + numOperacio + ". " + carrega[OptFou.idiom] + numImagen + ": " + nombre + "\n\n");
            Date fecha = new Date();
            String hora = hms.format(fecha);
            OptFou.textoTime[numImagen] = hora;
            OptFou.textoDim[numImagen] = imagen[numImagen].ncol + " x " + imagen[numImagen].nfil;
            OptFou.texto[numImagen] = " " + nombre;
            OptFou.jTextoTime.setText(OptFou.textoTime[numImagen]);
            OptFou.jTextoDim.setText(OptFou.textoDim[numImagen]);
            OptFou.jTexto.setText(OptFou.texto[numImagen]);
            OptFou.save.setText("");
            OptFou.save.setIcon((Icon)null);
            OptFou.salvado[numImagen] = false;
         }
      }

   }

   public void salvar() {
      JFrame f = new JFrame();
      String[] preguntas = new String[]{im[0][OptFou.idiom] + " n.?  "};
      String[] respuestas = new String[]{Integer.toString(OptFou.jCombo.getSelectedIndex())};
      boolean control = false;
      if (icon_joc != null) {
         f.setIconImage(icon_joc.getImage());
      }

      if (Dialogos.optPreguntas(f, preguntas, respuestas, OptFou.idiom)) {
         int numero;
         try {
            numero = Integer.valueOf(respuestas[0]);
         } catch (NumberFormatException var10) {
            errorNumber(1);
            return;
         }

         Frame fr;
         Object[] options;
         JOptionPane hola;
         JDialog dialog;
         if (numero <= maxNumImagen) {
            if (controlImagen[numero]) {
               imagen[numero].grabaFicheroDeImagen(imagen[numero]);
               control = true;
            } else {
               fr = new Frame();
               if (icon_joc != null) {
                  fr.setIconImage(icon_joc.getImage());
               }

               options = new Object[]{OptFou.aceptar[OptFou.idiom]};
               hola = new JOptionPane(existe[1][OptFou.idiom], 0, -1, (Icon)null, options);
               dialog = hola.createDialog(fr, "");
               dialog.setVisible(true);
            }
         } else {
            fr = new Frame();
            if (icon_joc != null) {
               fr.setIconImage(icon_joc.getImage());
            }

            options = new Object[]{OptFou.aceptar[OptFou.idiom]};
            hola = new JOptionPane("n > 19!", 0, -1, (Icon)null, options);
            dialog = hola.createDialog(fr, "");
            dialog.setVisible(true);
         }

         if (control) {
            ++numOperacio;
            FrameInfo.info.append(" " + numOperacio + ". " + this.salva[OptFou.idiom] + numero + "\n\n");
            OptFou.save.setText(OptFou.salvat[OptFou.idiom]);
            OptFou.save.setIcon(OptFou.save_joc);
            OptFou.salvado[numero] = true;
         }
      }

   }

   public void tf(String s) {
      int contrast = 1;
      double maxi = 0.0D;
      double mini = 1.0E30D;
      boolean control = false;
      JFrame f = new JFrame();
      if (icon_joc != null) {
         f.setIconImage(icon_joc.getImage());
      }

      String[] preguntas;
      String[] respuestas;
      Object[] options;
      JOptionPane hola;
      JDialog dialog;
      String[] var10000;
      String var10003;
      Frame fr;
      if (s == OptFou.etiqueta[9][OptFou.idiom]) {
         preguntas = new String[]{OptFou.etiqueta[1][OptFou.idiom] + im[1][OptFou.idiom] + "n. ?", desti[OptFou.idiom], OptFou.etiqueta[9][OptFou.idiom] + " ?"};
         var10000 = new String[]{Integer.toString(OptFou.jCombo.getSelectedIndex()), null, null};
         var10003 = resp;
         var10000[1] = String.valueOf(nmin);
         var10000[2] = "5";
         respuestas = var10000;
         if (!Dialogos.optPreguntas(f, preguntas, respuestas, OptFou.idiom)) {
            return;
         }

         try {
            numImagen = Integer.valueOf(respuestas[0]);
            numImagenDestino = Integer.valueOf(respuestas[1]);
         } catch (NumberFormatException var20) {
            errorNumber(1);
            return;
         }

         if (!controlImagen[numImagen]) {
            fr = new Frame();
            if (icon_joc != null) {
               fr.setIconImage(icon_joc.getImage());
            }

            options = new Object[]{OptFou.aceptar[OptFou.idiom]};
            hola = new JOptionPane(existe[1][OptFou.idiom], 0, -1, (Icon)null, options);
            dialog = hola.createDialog(fr, "");
            dialog.setVisible(true);
            --nmin;
            return;
         }

         if (this.imagIgual(numImagen, numImagenDestino)) {
            return;
         }

         if (controlImagen[Integer.valueOf(respuestas[1])]) {
            JFrame fr = new JFrame();
            if (icon_joc != null) {
               fr.setIconImage(icon_joc.getImage());
            }

            options = new Object[]{SiNo[0][OptFou.idiom], SiNo[1][OptFou.idiom]};
            hola = new JOptionPane(existe[0][OptFou.idiom] + ". " + sobrescriure[OptFou.idiom], 1, -1, (Icon)null, options);
            dialog = hola.createDialog(fr, "");
            dialog.setVisible(true);
            if (!options[0].equals(hola.getValue())) {
               return;
            }

            imagen[numImagenDestino].setVisible(false);
            --nmin;
         }

         try {
            contrast = Integer.valueOf(respuestas[2]);
         } catch (NumberFormatException var19) {
            errorNumber(1);
            return;
         }

         control = true;
         ++nmin;
      } else {
         preguntas = new String[]{OptFou.etiqueta[1][OptFou.idiom] + im[1][OptFou.idiom] + "n. ?", desti[OptFou.idiom]};
         var10000 = new String[]{Integer.toString(OptFou.jCombo.getSelectedIndex()), null};
         var10003 = resp;
         var10000[1] = String.valueOf(nmin);
         respuestas = var10000;
         if (!Dialogos.optPreguntas(f, preguntas, respuestas, OptFou.idiom)) {
            return;
         }

         try {
            numImagen = Integer.valueOf(respuestas[0]);
            numImagenDestino = Integer.valueOf(respuestas[1]);
         } catch (NumberFormatException var18) {
            errorNumber(1);
            return;
         }

         if (this.imagIgual(numImagen, numImagenDestino)) {
            return;
         }

         if (!controlImagen[numImagen]) {
            fr = new Frame();
            if (icon_joc != null) {
               fr.setIconImage(icon_joc.getImage());
            }

            options = new Object[]{OptFou.aceptar[OptFou.idiom]};
            hola = new JOptionPane(existe[1][OptFou.idiom], 0, -1, (Icon)null, options);
            dialog = hola.createDialog(fr, "");
            dialog.setVisible(true);
            return;
         }

         if (controlImagen[numImagenDestino]) {
            fr = new Frame();
            if (icon_joc != null) {
               fr.setIconImage(icon_joc.getImage());
            }

            options = new Object[]{SiNo[0][OptFou.idiom], SiNo[1][OptFou.idiom]};
            hola = new JOptionPane(existe[0][OptFou.idiom] + ". " + sobrescriure[OptFou.idiom], 1, -1, (Icon)null, options);
            dialog = hola.createDialog(fr, "");
            dialog.setVisible(true);
            if (!options[0].equals(hola.getValue())) {
               return;
            }

            imagen[numImagenDestino].setVisible(false);
            --nmin;
         }

         ++nmin;
         control = true;
      }

      if (control) {
         controlImagen[numImagen] = true;
         controlImagen[numImagenDestino] = true;
         String nombreImagen = im[0][OptFou.idiom] + numImagenDestino;
         double[] real = new double[imagen[numImagen].nfil * imagen[numImagen].ncol];
         double[] imagin = new double[imagen[numImagen].nfil * imagen[numImagen].ncol];

         int i;
         int newnfil;
         for(i = 0; i < imagen[numImagen].nfil; ++i) {
            for(newnfil = 0; newnfil < imagen[numImagen].ncol; ++newnfil) {
               real[i * imagen[numImagen].nfil + newnfil] = (double)(imagen[numImagen].buffImage.getRGB(newnfil, i) & 255);
            }
         }

         for(i = 0; i < imagen[numImagen].nfil * imagen[numImagen].ncol; ++i) {
            imagin[i] = 0.0D;
         }

         this.x2n.xto2n(imagen[numImagen].nfil, imagen[numImagen].ncol, real, imagin);
         double[] amplitud = new double[newreal.length];
         newnfil = (int)Math.sqrt((double)newreal.length);
         Fft2.fft2r2(newnfil, newnfil, 1, newreal, newimagin);

         for(int i = 0; i < newreal.length; ++i) {
            if (s == OptFou.etiqueta[8][OptFou.idiom]) {
               amplitud[i] = Math.sqrt(newreal[i] * newreal[i] + newimagin[i] * newimagin[i]);
            }

            if (s == OptFou.etiqueta[10][OptFou.idiom]) {
               amplitud[i] = Math.log(newreal[i] * newreal[i] + newimagin[i] * newimagin[i] + 1.0D) / Math.log(10.0D);
            }

            if (s == OptFou.etiqueta[9][OptFou.idiom]) {
               amplitud[i] = Math.pow(newreal[i] * newreal[i] + newimagin[i] * newimagin[i], 1.0D / (double)contrast);
            }

            if (s == OptFou.etiqueta[11][OptFou.idiom]) {
               amplitud[i] = Math.atan2(newimagin[i], newreal[i]) + 3.141592653589793D;
            }

            if (maxi < amplitud[i]) {
               maxi = amplitud[i];
            }

            if (mini > amplitud[i]) {
               mini = amplitud[i];
            }
         }

         int[] mapa = new int[newreal.length];

         int i;
         for(i = 0; i < newreal.length; ++i) {
            mapa[i] = (int)(254.0D * (amplitud[i] - mini) / (maxi - mini));
            mapa[i] |= -16777216 | mapa[i] << 16 | mapa[i] << 8;
         }

         imagen[numImagenDestino] = new OptImagen(im[0][OptFou.idiom] + numImagenDestino, newnfil, newnfil);
         if (icon_joc != null) {
            imagen[numImagenDestino].setIconImage(icon_joc.getImage());
         }

         imagen[numImagenDestino].creaVentanaOptImagen(imagen[numImagenDestino], newnfil, newnfil);

         try {
            OptFou.vect.get(numImagenDestino);
         } catch (ArrayIndexOutOfBoundsException var17) {
            OptFou.vect.add(numImagenDestino, nombreImagen);
         }

         OptFou.jCombo.setSelectedIndex(numImagenDestino);

         for(i = 0; i < newnfil; ++i) {
            for(int j = 0; j < newnfil; ++j) {
               imagen[numImagenDestino].buffImage.setRGB(j, i, mapa[i * newnfil + j]);
            }
         }

         imagen[numImagenDestino].g2 = imagen[numImagenDestino].buffImage.createGraphics();

         do {
            imagen[numImagenDestino].g2.drawImage(imagen[numImagenDestino].buffImage, 0, 0, imagen[numImagenDestino]);
         } while(imagen[numImagenDestino].buffImage.getRGB(newnfil - 1, newnfil - 1) == 0);

         imagen[numImagenDestino].bytesPixel = 1;
         ++numOperacio;
         if (s == OptFou.etiqueta[9][OptFou.idiom]) {
            FrameInfo.info.append(" " + numOperacio + ". " + this.tf[OptFou.idiom] + this.finestra[OptFou.idiom] + numImagen + " ( " + s + " (" + contrast + ") " + " ). \n");
         } else {
            FrameInfo.info.append(" " + numOperacio + ". " + this.tf[OptFou.idiom] + this.finestra[OptFou.idiom] + numImagen + " ( " + s + " ). \n");
         }

         FrameInfo.info.append(" " + this.resultat[OptFou.idiom] + numImagenDestino + ".\n\n");
         Date fecha = new Date();
         String hora = hms.format(fecha);
         OptFou.textoTime[numImagenDestino] = hora;
         OptFou.textoDim[numImagenDestino] = newnfil + " x " + newnfil;
         if (s == OptFou.etiqueta[9][OptFou.idiom]) {
            OptFou.texto[numImagenDestino] = " " + s + " (" + contrast + ") " + im[0][OptFou.idiom] + numImagen;
         } else {
            OptFou.texto[numImagenDestino] = " " + s + im[1][OptFou.idiom] + numImagen;
         }

         OptFou.jTextoTime.setText(OptFou.textoTime[numImagenDestino]);
         OptFou.jTextoDim.setText(OptFou.textoDim[numImagenDestino]);
         OptFou.jTexto.setText(OptFou.texto[numImagenDestino]);
         OptFou.save.setText("");
         OptFou.save.setIcon((Icon)null);
         OptFou.salvado[numImagenDestino] = false;
      }

   }

   public void filtres(String s) {
      double maxi = 0.0D;
      double mini = 1.0E30D;
      boolean control = false;
      JFrame f = new JFrame();
      if (icon_joc != null) {
         f.setIconImage(icon_joc.getImage());
      }

      String[] preguntas = new String[]{this.escena[OptFou.idiom] + "?", this.ref[OptFou.idiom] + "?", desti[OptFou.idiom]};
      String[] var10000 = new String[]{"0", "1", null};
      String var10003 = resp;
      var10000[2] = String.valueOf(nmin);
      String[] respuestas = var10000;
      if (Dialogos.optPreguntas(f, preguntas, respuestas, OptFou.idiom)) {
         try {
            numImagen1 = Integer.valueOf(respuestas[0]);
            numImagen2 = Integer.valueOf(respuestas[1]);
            numImagenDestino = Integer.valueOf(respuestas[2]);
         } catch (NumberFormatException var30) {
            errorNumber(1);
            return;
         }

         Frame fr;
         Object[] options;
         JOptionPane hola;
         JDialog dialog;
         if (controlImagen[numImagen1] && controlImagen[numImagen2]) {
            if (!this.imagDif(numImagen1, numImagen2)) {
               if (!this.imagIgual(numImagen1, numImagenDestino)) {
                  if (!this.imagIgual(numImagen2, numImagenDestino)) {
                     if (controlImagen[Integer.valueOf(respuestas[2])]) {
                        fr = new Frame();
                        if (icon_joc != null) {
                           fr.setIconImage(icon_joc.getImage());
                        }

                        options = new Object[]{SiNo[0][OptFou.idiom], SiNo[1][OptFou.idiom]};
                        hola = new JOptionPane(existe[0][OptFou.idiom] + ". " + sobrescriure[OptFou.idiom], 1, -1, (Icon)null, options);
                        dialog = hola.createDialog(fr, "");
                        dialog.setVisible(true);
                        if (!options[0].equals(hola.getValue())) {
                           return;
                        }

                        imagen[numImagenDestino].setVisible(false);
                        --nmin;
                     }

                     ++nmin;
                     control = true;
                     if (control) {
                        controlImagen[numImagen1] = true;
                        controlImagen[numImagen2] = true;
                        controlImagen[numImagenDestino] = true;
                        String nombreImagen = im[0][OptFou.idiom] + numImagenDestino;
                        double[] reals = new double[imagen[numImagen1].nfil * imagen[numImagen1].ncol];
                        double[] imagis = new double[imagen[numImagen1].nfil * imagen[numImagen1].ncol];
                        double[] realr = new double[imagen[numImagen1].nfil * imagen[numImagen1].ncol];
                        double[] imagir = new double[imagen[numImagen1].nfil * imagen[numImagen1].ncol];

                        int newnfil;
                        for(newnfil = 0; newnfil < imagen[numImagen1].nfil; ++newnfil) {
                           for(int j = 0; j < imagen[numImagen1].ncol; ++j) {
                              reals[newnfil * imagen[numImagen1].nfil + j] = (double)(imagen[numImagen1].buffImage.getRGB(j, newnfil) & 255);
                              realr[newnfil * imagen[numImagen2].nfil + j] = (double)(imagen[numImagen2].buffImage.getRGB(j, newnfil) & 255);
                           }
                        }

                        for(newnfil = 0; newnfil < imagen[numImagen1].nfil * imagen[numImagen1].ncol; ++newnfil) {
                           imagis[newnfil] = 0.0D;
                           imagir[newnfil] = 0.0D;
                        }

                        this.x2n.xsto2n(imagen[numImagen1].nfil, imagen[numImagen1].ncol, reals, imagis, imagen[numImagen2].nfil, imagen[numImagen2].ncol, realr, imagir);
                        newnfil = (int)Math.sqrt((double)newreals.length);
                        if (s == OptFou.etiqueta[12][OptFou.idiom]) {
                           Fft2.correlacioMf(newnfil, newnfil, newreals, newimagis, newrealr, newimagir);
                        }

                        if (s == OptFou.etiqueta[13][OptFou.idiom]) {
                           Fft2.correlacioPof(newnfil, newnfil, newreals, newimagis, newrealr, newimagir);
                        }

                        if (s == OptFou.etiqueta[14][OptFou.idiom]) {
                           JFrame fr = new JFrame();
                           if (icon_joc != null) {
                              fr.setIconImage(icon_joc.getImage());
                           }

                           String[] pregunta = new String[]{denom[OptFou.idiom]};
                           String[] respuesta = new String[]{"0.1"};
                           this.sumDen = 1.0E-8D;
                           if (!Dialogos.optPreguntas(f, pregunta, respuesta, OptFou.idiom)) {
                              --nmin;
                              return;
                           }

                           try {
                              this.sumDen = Double.valueOf(respuesta[0]);
                           } catch (NumberFormatException var29) {
                              errorNumber(0);
                              --nmin;
                              controlImagen[numImagenDestino] = false;
                              return;
                           }

                           Fft2.fft2r2(newnfil, newnfil, 1, newreals, newimagis);
                           Fft2.fft2r2(newnfil, newnfil, 1, newrealr, newimagir);
                           double xr0 = 0.0D;
                           double yr0 = 0.0D;
                           double xi0 = 0.0D;
                           double yi0 = 0.0D;

                           for(int i = 0; i < newnfil * newnfil; ++i) {
                              xr0 = newreals[i];
                              xi0 = newimagis[i];
                              yr0 = newrealr[i];
                              yi0 = newimagir[i];
                              newreals[i] = (xr0 * yr0 + xi0 * yi0) / (yr0 * yr0 + yi0 * yi0 + this.sumDen);
                              newimagis[i] = (-xr0 * yi0 + xi0 * yr0) / (yr0 * yr0 + yi0 * yi0 + this.sumDen);
                           }

                           Fft2.fft2r2(newnfil, newnfil, -1, newreals, newimagis);
                        }

                        if (s == OptFou.etiqueta[15][OptFou.idiom]) {
                           Fft2.convolucio(newnfil, newnfil, newreals, newimagis, newrealr, newimagir);
                        }

                        double[] amplitud = new double[newnfil * newnfil];

                        for(int i = 0; i < newnfil * newnfil; ++i) {
                           amplitud[i] = Math.sqrt(newreals[i] * newreals[i] + newimagis[i] * newimagis[i]);
                           if (maxi < amplitud[i]) {
                              maxi = amplitud[i];
                           }

                           if (mini > amplitud[i]) {
                              mini = amplitud[i];
                           }
                        }

                        int[] mapa = new int[newnfil * newnfil];

                        int i;
                        for(i = 0; i < newnfil * newnfil; ++i) {
                           mapa[i] = (int)(254.0D * (amplitud[i] - mini) / (maxi - mini));
                           mapa[i] |= -16777216 | mapa[i] << 16 | mapa[i] << 8;
                        }

                        imagen[numImagenDestino] = new OptImagen(im[0][OptFou.idiom] + numImagenDestino, newnfil, newnfil);
                        if (icon_joc != null) {
                           imagen[numImagenDestino].setIconImage(icon_joc.getImage());
                        }

                        imagen[numImagenDestino].creaVentanaOptImagen(imagen[numImagenDestino], newnfil, newnfil);

                        try {
                           OptFou.vect.get(numImagenDestino);
                        } catch (ArrayIndexOutOfBoundsException var28) {
                           OptFou.vect.add(numImagenDestino, nombreImagen);
                        }

                        OptFou.jCombo.setSelectedIndex(numImagenDestino);

                        for(i = 0; i < newnfil; ++i) {
                           for(int j = 0; j < newnfil; ++j) {
                              imagen[numImagenDestino].buffImage.setRGB(j, i, mapa[i * newnfil + j]);
                           }
                        }

                        imagen[numImagenDestino].g2 = imagen[numImagenDestino].buffImage.createGraphics();

                        do {
                           imagen[numImagenDestino].g2.drawImage(imagen[numImagenDestino].buffImage, 0, 0, imagen[numImagenDestino]);
                        } while(imagen[numImagenDestino].buffImage.getRGB(newnfil - 1, newnfil - 1) == 0);

                        imagen[numImagenDestino].bytesPixel = 1;
                        ++numOperacio;
                        FrameInfo.info.append(" " + numOperacio + ". " + s + this.entre[OptFou.idiom] + numImagen1 + this.y[OptFou.idiom] + numImagen2 + ".\n");
                        if (s == OptFou.etiqueta[14][OptFou.idiom]) {
                           FrameInfo.info.append(" " + denom[OptFou.idiom] + ": " + this.sumDen + "\n");
                        }

                        FrameInfo.info.append(" " + this.resultat[OptFou.idiom] + numImagenDestino + ".\n\n");
                        Date fecha = new Date();
                        String hora = hms.format(fecha);
                        OptFou.textoTime[numImagenDestino] = hora;
                        OptFou.textoDim[numImagenDestino] = " " + imagen[numImagenDestino].ncol + " x " + imagen[numImagenDestino].nfil;
                        OptFou.texto[numImagenDestino] = " " + s + im[2][OptFou.idiom] + numImagen1 + this.y[OptFou.idiom] + numImagen2;
                        OptFou.jTextoTime.setText(OptFou.textoTime[numImagenDestino]);
                        OptFou.jTextoDim.setText(OptFou.textoDim[numImagenDestino]);
                        OptFou.jTexto.setText(OptFou.texto[numImagenDestino]);
                        OptFou.save.setText("");
                        OptFou.save.setIcon((Icon)null);
                        OptFou.salvado[numImagenDestino] = false;
                     }

                  }
               }
            }
         } else {
            fr = new Frame();
            if (icon_joc != null) {
               fr.setIconImage(icon_joc.getImage());
            }

            options = new Object[]{OptFou.aceptar[OptFou.idiom]};
            hola = new JOptionPane(existe[1][OptFou.idiom], 0, -1, (Icon)null, options);
            dialog = hola.createDialog(fr, "");
            dialog.setVisible(true);
         }
      }
   }

   public void codBurck() {
      double contrast = 1.0D;
      double maxi = 0.0D;
      double mini = 1.0E30D;
      boolean control = false;
      JFrame f = new JFrame();
      if (icon_joc != null) {
         f.setIconImage(icon_joc.getImage());
      }

      String[] preguntas = new String[]{this.holograma[OptFou.idiom] + im[1][OptFou.idiom] + "? ", desti[OptFou.idiom], "% max " + this.tf[OptFou.idiom] + "? [0-100]"};
      String[] var10000 = new String[]{Integer.toString(OptFou.jCombo.getSelectedIndex()), null, null};
      String var10003 = resp;
      var10000[1] = String.valueOf(nmin);
      var10000[2] = "100";
      String[] respuestas = var10000;
      if (Dialogos.optPreguntas(f, preguntas, respuestas, OptFou.idiom)) {
         try {
            numImagen = Integer.valueOf(respuestas[0]);
            numImagenDestino = Integer.valueOf(respuestas[1]);
         } catch (NumberFormatException var34) {
            errorNumber(1);
            return;
         }

         Frame fr;
         Object[] options;
         JOptionPane hola;
         JDialog dialog;
         if (!controlImagen[numImagen]) {
            fr = new Frame();
            if (icon_joc != null) {
               fr.setIconImage(icon_joc.getImage());
            }

            options = new Object[]{OptFou.aceptar[OptFou.idiom]};
            hola = new JOptionPane(existe[1][OptFou.idiom], 0, -1, (Icon)null, options);
            dialog = hola.createDialog(fr, "");
            dialog.setVisible(true);
         } else if (!this.imagIgual(numImagen, numImagenDestino)) {
            if (controlImagen[Integer.valueOf(respuestas[1])]) {
               fr = new Frame();
               if (icon_joc != null) {
                  fr.setIconImage(icon_joc.getImage());
               }

               options = new Object[]{SiNo[0][OptFou.idiom], SiNo[1][OptFou.idiom]};
               hola = new JOptionPane(existe[0][OptFou.idiom] + ". " + sobrescriure[OptFou.idiom], 1, -1, (Icon)null, options);
               dialog = hola.createDialog(fr, "");
               dialog.setVisible(true);
               if (!options[0].equals(hola.getValue())) {
                  return;
               }

               imagen[numImagenDestino].setVisible(false);
               --nmin;
            }

            try {
               contrast = Double.valueOf(respuestas[2]);
            } catch (NumberFormatException var33) {
               errorNumber(0);
               return;
            }

            ++nmin;
            control = true;
            if (control) {
               if (9 * imagen[numImagen].ncol * imagen[numImagen].nfil > 412500) {
                  --nmin;
                  fr = new Frame();
                  if (icon_joc != null) {
                     fr.setIconImage(icon_joc.getImage());
                  }

                  options = new Object[]{OptFou.aceptar[OptFou.idiom]};
                  hola = new JOptionPane(gran[OptFou.idiom], 0, -1, (Icon)null, options);
                  dialog = hola.createDialog(fr, "");
                  dialog.setVisible(true);
                  return;
               }

               controlImagen[numImagen] = true;
               controlImagen[numImagenDestino] = true;
               String nombreImagen = im[0][OptFou.idiom] + numImagenDestino;
               double[] real = new double[imagen[numImagen].nfil * imagen[numImagen].ncol];
               double[] imagin = new double[imagen[numImagen].nfil * imagen[numImagen].ncol];

               int newnfil;
               for(newnfil = 0; newnfil < imagen[numImagen].nfil; ++newnfil) {
                  for(int j = 0; j < imagen[numImagen].ncol; ++j) {
                     real[newnfil * imagen[numImagen].nfil + j] = (double)(imagen[numImagen].buffImage.getRGB(j, newnfil) & 255);
                  }
               }

               for(newnfil = 0; newnfil < imagen[numImagen].nfil * imagen[numImagen].ncol; ++newnfil) {
                  imagin[newnfil] = 0.0D;
               }

               this.x2n.xto2n(imagen[numImagen].nfil, imagen[numImagen].ncol, real, imagin);
               newnfil = (int)Math.sqrt((double)newreal.length);
               Fft2.fft2r2(newnfil, newnfil, 1, newreal, newimagin);
               double[] A3 = new double[newnfil * newnfil];
               double[] A1 = new double[newnfil * newnfil];
               double[] A2 = new double[newnfil * newnfil];
               int[] mapa = new int[9 * newnfil * newnfil];
               if (contrast == -1.0D) {
                  double rea = 0.0D;
                  double ima = 0.0D;

                  for(int i = 0; i < newnfil * newnfil; ++i) {
                     rea = newreal[i];
                     ima = newimagin[i];
                     newreal[i] = rea / Math.sqrt(rea * rea + ima * ima);
                     newimagin[i] = ima / Math.sqrt(rea * rea + ima * ima);
                  }

                  contrast = 1.0D;
               }

               int i;
               for(i = 0; i < newnfil * newnfil; ++i) {
                  double x1 = 0.0D;
                  double y1 = 0.0D;
                  double y2 = 0.0D;
                  double z2 = 0.0D;
                  double x3 = 0.0D;
                  double z3 = 0.0D;
                  x1 = newreal[i] + newimagin[i] * 0.577350269D;
                  y1 = newimagin[i] / 0.866025403D;
                  y2 = (-0.866025403D * newreal[i] + 0.5D * newimagin[i]) / 0.866025403D;
                  z2 = (-0.866025403D * newreal[i] - 0.5D * newimagin[i]) / 0.866025403D;
                  x3 = newreal[i] - newimagin[i] * 0.577350269D;
                  z3 = -newimagin[i] / 0.866025403D;
                  if (x1 >= 0.0D && y1 >= 0.0D) {
                     A1[i] = x1;
                     A2[i] = y1;
                     A3[i] = 0.0D;
                  } else if (y2 >= 0.0D && z2 >= 0.0D) {
                     A1[i] = 0.0D;
                     A2[i] = y2;
                     A3[i] = z2;
                  } else if (x3 >= 0.0D && z3 >= 0.0D) {
                     A1[i] = x3;
                     A2[i] = 0.0D;
                     A3[i] = z3;
                  } else {
                     A1[i] = 0.0D;
                     A2[i] = 0.0D;
                     A3[i] = 0.0D;
                  }
               }

               for(i = 0; i < newnfil * newnfil; ++i) {
                  if (maxi < A1[i]) {
                     maxi = A1[i];
                  }

                  if (mini > A1[i]) {
                     mini = A1[i];
                  }

                  if (maxi < A2[i]) {
                     maxi = A2[i];
                  }

                  if (mini > A2[i]) {
                     mini = A2[i];
                  }

                  if (maxi < A3[i]) {
                     maxi = A3[i];
                  }

                  if (mini > A3[i]) {
                     mini = A3[i];
                  }
               }

               maxi = contrast / 100.0D * maxi;

               int j;
               for(i = 0; i < newnfil; ++i) {
                  for(j = 0; j < newnfil; ++j) {
                     mapa[3 * i * 3 * newnfil + 3 * j] = (int)(254.0D * (A1[i * newnfil + j] - mini) / (maxi - mini));
                     mapa[3 * i * 3 * newnfil + 3 * j + 1] = (int)(254.0D * (A2[i * newnfil + j] - mini) / (maxi - mini));
                     mapa[3 * i * 3 * newnfil + 3 * j + 2] = (int)(254.0D * (A3[i * newnfil + j] - mini) / (maxi - mini));
                     mapa[(3 * i + 1) * 3 * newnfil + 3 * j] = (int)(254.0D * (A1[i * newnfil + j] - mini) / (maxi - mini));
                     mapa[(3 * i + 1) * 3 * newnfil + 3 * j + 1] = (int)(254.0D * (A2[i * newnfil + j] - mini) / (maxi - mini));
                     mapa[(3 * i + 1) * 3 * newnfil + 3 * j + 2] = (int)(254.0D * (A3[i * newnfil + j] - mini) / (maxi - mini));
                     mapa[(3 * i + 2) * 3 * newnfil + 3 * j] = (int)(254.0D * (A1[i * newnfil + j] - mini) / (maxi - mini));
                     mapa[(3 * i + 2) * 3 * newnfil + 3 * j + 1] = (int)(254.0D * (A2[i * newnfil + j] - mini) / (maxi - mini));
                     mapa[(3 * i + 2) * 3 * newnfil + 3 * j + 2] = (int)(254.0D * (A3[i * newnfil + j] - mini) / (maxi - mini));
                  }
               }

               for(i = 0; i < 9 * newnfil * newnfil; ++i) {
                  if (mapa[i] > 254) {
                     mapa[i] = 254;
                  }

                  mapa[i] |= -16777216 | mapa[i] << 16 | mapa[i] << 8;
               }

               imagen[numImagenDestino] = new OptImagen(im[0][OptFou.idiom] + numImagenDestino, 3 * newnfil, 3 * newnfil);
               if (icon_joc != null) {
                  imagen[numImagenDestino].setIconImage(icon_joc.getImage());
               }

               imagen[numImagenDestino].creaVentanaOptImagen(imagen[numImagenDestino], 3 * newnfil, 3 * newnfil);

               try {
                  OptFou.vect.get(numImagenDestino);
               } catch (ArrayIndexOutOfBoundsException var32) {
                  OptFou.vect.add(numImagenDestino, nombreImagen);
               }

               OptFou.jCombo.setSelectedIndex(numImagenDestino);

               for(i = 0; i < imagen[numImagenDestino].nfil; ++i) {
                  for(j = 0; j < imagen[numImagenDestino].ncol; ++j) {
                     imagen[numImagenDestino].buffImage.setRGB(j, i, mapa[i * imagen[numImagenDestino].nfil + j]);
                  }
               }

               imagen[numImagenDestino].g2 = imagen[numImagenDestino].buffImage.createGraphics();

               do {
                  imagen[numImagenDestino].g2.drawImage(imagen[numImagenDestino].buffImage, 0, 0, imagen[numImagenDestino]);
               } while(imagen[numImagenDestino].buffImage.getRGB(imagen[numImagenDestino].nfil - 1, imagen[numImagenDestino].nfil - 1) == 0);

               imagen[numImagenDestino].bytesPixel = 1;
               ++numOperacio;
               FrameInfo.info.append(" " + numOperacio + ". " + this.holograma[OptFou.idiom] + this.dela[0][OptFou.idiom] + im[1][OptFou.idiom] + numImagen + " (" + contrast + "% max).\n");
               FrameInfo.info.append(" " + this.resultat[OptFou.idiom] + numImagenDestino + ".\n\n");
               Date fecha = new Date();
               String hora = hms.format(fecha);
               OptFou.textoTime[numImagenDestino] = hora;
               OptFou.textoDim[numImagenDestino] = imagen[numImagenDestino].ncol + " x " + imagen[numImagenDestino].nfil;
               OptFou.texto[numImagenDestino] = " " + this.holograma[OptFou.idiom] + im[1][OptFou.idiom] + numImagen + " (" + contrast + "%)";
               OptFou.jTextoTime.setText(OptFou.textoTime[numImagenDestino]);
               OptFou.jTextoDim.setText(OptFou.textoDim[numImagenDestino]);
               OptFou.jTexto.setText(OptFou.texto[numImagenDestino]);
               OptFou.save.setText("");
               OptFou.save.setIcon((Icon)null);
               OptFou.salvado[numImagenDestino] = false;
            }

         }
      }
   }

   public void joint(String s) {
      double maxi = 0.0D;
      double mini = 1.0E30D;
      boolean control = false;
      JFrame f = new JFrame();
      if (icon_joc != null) {
         f.setIconImage(icon_joc.getImage());
      }

      String[] preguntas = new String[]{this.escena[OptFou.idiom] + "?", this.ref[OptFou.idiom] + "?", desti[OptFou.idiom]};
      String[] var10000 = new String[]{"0", "1", null};
      String var10003 = resp;
      var10000[2] = String.valueOf(nmin);
      String[] respuestas = var10000;
      if (Dialogos.optPreguntas(f, preguntas, respuestas, OptFou.idiom)) {
         try {
            numImagen1 = Integer.valueOf(respuestas[0]);
            numImagen2 = Integer.valueOf(respuestas[1]);
            numImagenDestino = Integer.valueOf(respuestas[2]);
         } catch (NumberFormatException var28) {
            errorNumber(1);
            return;
         }

         Frame fr;
         Object[] options;
         JOptionPane hola;
         JDialog dialog;
         if (controlImagen[numImagen1] && controlImagen[numImagen2]) {
            if (!this.imagDif(numImagen1, numImagen2)) {
               if (!this.imagIgual(numImagen1, numImagenDestino)) {
                  if (!this.imagIgual(numImagen2, numImagenDestino)) {
                     if (controlImagen[Integer.valueOf(respuestas[2])]) {
                        fr = new Frame();
                        if (icon_joc != null) {
                           fr.setIconImage(icon_joc.getImage());
                        }

                        options = new Object[]{SiNo[0][OptFou.idiom], SiNo[1][OptFou.idiom]};
                        hola = new JOptionPane(existe[0][OptFou.idiom] + ". " + sobrescriure[OptFou.idiom], 1, -1, (Icon)null, options);
                        dialog = hola.createDialog(fr, "");
                        dialog.setVisible(true);
                        if (!options[0].equals(hola.getValue())) {
                           return;
                        }

                        imagen[numImagenDestino].setVisible(false);
                        --nmin;
                     }

                     ++nmin;
                     control = true;
                     if (control) {
                        if (16 * imagen[numImagen1].ncol * imagen[numImagen1].nfil > 412500) {
                           --nmin;
                           fr = new Frame();
                           if (icon_joc != null) {
                              fr.setIconImage(icon_joc.getImage());
                           }

                           options = new Object[]{OptFou.aceptar[OptFou.idiom]};
                           hola = new JOptionPane(gran[OptFou.idiom], 0, -1, (Icon)null, options);
                           dialog = hola.createDialog(fr, "");
                           dialog.setVisible(true);
                           return;
                        }

                        String nombreImagen = im[0][OptFou.idiom] + numImagenDestino;
                        controlImagen[numImagen1] = true;
                        controlImagen[numImagen2] = true;
                        controlImagen[numImagenDestino] = true;
                        int fil = 4 * imagen[numImagen1].nfil;
                        int col = 4 * imagen[numImagen1].ncol;
                        int dim = fil * col;
                        int centre = dim / 2 + fil / 2;
                        double[] reals = new double[dim];
                        double[] imagis = new double[dim];
                        double[] realr = new double[dim];
                        double[] imagir = new double[dim];
                        double[] real = new double[dim];
                        double[] imag = new double[dim];

                        int newdim;
                        int newnfil;
                        for(newdim = 0; newdim < imagen[numImagen1].nfil; ++newdim) {
                           for(newnfil = 0; newnfil < imagen[numImagen1].ncol; ++newnfil) {
                              real[newdim * col + newnfil] = (double)(imagen[numImagen1].buffImage.getRGB(newnfil, newdim) & 255);
                              real[newdim * col + newnfil + imagen[numImagen1].ncol] = (double)(imagen[numImagen2].buffImage.getRGB(newnfil, newdim) & 255);
                              reals[newdim * col + newnfil] = (double)(imagen[numImagen1].buffImage.getRGB(newnfil, newdim) & 255);
                              realr[newdim * col + newnfil + imagen[numImagen1].ncol] = (double)(imagen[numImagen2].buffImage.getRGB(newnfil, newdim) & 255);
                           }
                        }

                        for(newdim = 0; newdim < dim; ++newdim) {
                           imag[newdim] = imagir[newdim] = imagis[newdim] = 0.0D;
                        }

                        this.x2n.xto2n(fil, col, real, imag);
                        this.x2n.xsto2n(fil, col, reals, imagis, fil, col, realr, imagir);
                        newdim = newreal.length;
                        newnfil = (int)Math.sqrt((double)newdim);
                        int newcentre = newdim / 2 + newnfil / 2;
                        Fft2.fft2r2(fil, col, 1, newreal, newimagin);
                        Fft2.fft2r2(fil, col, 1, newreals, newimagis);
                        Fft2.fft2r2(fil, col, 1, newrealr, newimagir);

                        int i;
                        for(i = 0; i < newdim; ++i) {
                           newreal[i] = Math.pow(newreal[i], 2.0D) + Math.pow(newimagin[i], 2.0D);
                           newreals[i] = Math.pow(newreals[i], 2.0D) + Math.pow(newimagis[i], 2.0D);
                           newrealr[i] = Math.pow(newrealr[i], 2.0D) + Math.pow(newimagir[i], 2.0D);
                           newreal[i] = newreal[i] - newreals[i] - newrealr[i];
                           newimagin[i] = newimagis[i] = newimagir[i] = 0.0D;
                        }

                        if (s == OptFou.etiqueta[17][OptFou.idiom]) {
                           for(i = 0; i < newdim; ++i) {
                              if (newreal[i] > 0.0D) {
                                 newreal[i] = 1.0D;
                              } else {
                                 newreal[i] = -1.0D;
                              }

                              newreal[newcentre] = 1.0D;
                           }
                        }

                        Fft2.fft2r2(newnfil, newnfil, -1, newreal, newimagin);

                        for(i = 0; i < newdim; ++i) {
                           newreal[i] = Math.pow(newreal[i], 2.0D) + Math.pow(newimagin[i], 2.0D);
                           if (maxi < newreal[i]) {
                              maxi = newreal[i];
                           }

                           if (mini > newreal[i]) {
                              mini = newreal[i];
                           }
                        }

                        int[] mapa = new int[newdim];

                        int i;
                        for(i = 0; i < newdim; ++i) {
                           mapa[i] = (int)(254.0D * (newreal[i] - mini) / (maxi - mini));
                           mapa[i] |= -16777216 | mapa[i] << 16 | mapa[i] << 8;
                        }

                        imagen[numImagenDestino] = new OptImagen(im[0][OptFou.idiom] + numImagenDestino, newnfil, newnfil);
                        if (icon_joc != null) {
                           imagen[numImagenDestino].setIconImage(icon_joc.getImage());
                        }

                        imagen[numImagenDestino].creaVentanaOptImagen(imagen[numImagenDestino], newnfil, newnfil);

                        try {
                           OptFou.vect.get(numImagenDestino);
                        } catch (ArrayIndexOutOfBoundsException var27) {
                           OptFou.vect.add(numImagenDestino, nombreImagen);
                        }

                        OptFou.jCombo.setSelectedIndex(numImagenDestino);

                        for(i = 0; i < newnfil; ++i) {
                           for(int j = 0; j < newnfil; ++j) {
                              imagen[numImagenDestino].buffImage.setRGB(j, i, mapa[i * newnfil + j]);
                           }
                        }

                        imagen[numImagenDestino].g2 = imagen[numImagenDestino].buffImage.createGraphics();

                        do {
                           imagen[numImagenDestino].g2.drawImage(imagen[numImagenDestino].buffImage, 0, 0, imagen[numImagenDestino]);
                        } while(imagen[numImagenDestino].buffImage.getRGB(newnfil - 1, newnfil - 1) == 0);

                        imagen[numImagenDestino].bytesPixel = 1;
                        ++numOperacio;
                        FrameInfo.info.append(" " + numOperacio + ". Joint power spectrum" + this.dela[1][OptFou.idiom] + im[2][OptFou.idiom] + numImagen1 + this.y[OptFou.idiom] + numImagen2 + " (" + s + "). \n");
                        FrameInfo.info.append(" " + this.resultat[OptFou.idiom] + numImagenDestino + ".\n\n");
                        Date fecha = new Date();
                        String hora = hms.format(fecha);
                        OptFou.textoTime[numImagenDestino] = hora;
                        OptFou.textoDim[numImagenDestino] = imagen[numImagenDestino].ncol + " x " + imagen[numImagenDestino].nfil;
                        OptFou.texto[numImagenDestino] = " " + s + im[2][OptFou.idiom] + numImagen1 + this.y[OptFou.idiom] + numImagen2;
                        OptFou.jTextoTime.setText(OptFou.textoTime[numImagenDestino]);
                        OptFou.jTextoDim.setText(OptFou.textoDim[numImagenDestino]);
                        OptFou.jTexto.setText(OptFou.texto[numImagenDestino]);
                        OptFou.save.setText("");
                        OptFou.save.setIcon((Icon)null);
                        OptFou.salvado[numImagenDestino] = false;
                     }

                  }
               }
            }
         } else {
            fr = new Frame();
            if (icon_joc != null) {
               fr.setIconImage(icon_joc.getImage());
            }

            options = new Object[]{OptFou.aceptar[OptFou.idiom]};
            hola = new JOptionPane(existe[1][OptFou.idiom], 1, -1, (Icon)null, options);
            dialog = hola.createDialog(fr, "");
            dialog.setVisible(true);
         }
      }
   }

   public void zoomea() {
      JFrame f = new JFrame();
      if (icon_joc != null) {
         f.setIconImage(icon_joc.getImage());
      }

      boolean control = false;
      boolean control0 = false;
      int fila0 = false;
      int columna0 = false;
      int filas = false;
      int columnas = false;
      float zfilas = 1.0F;
      float zcolumnas = 1.0F;
      Zoom m = new Zoom();
      String[] preguntas = new String[]{"Zoom" + im[1][OptFou.idiom], this.factor[OptFou.idiom], desti[OptFou.idiom], this.filcol[0][OptFou.idiom], this.filcol[1][OptFou.idiom], this.filcol[2][OptFou.idiom], this.filcol[3][OptFou.idiom]};
      String[] var10000 = new String[]{Integer.toString(OptFou.jCombo.getSelectedIndex()), "1", null, null, null, null, null};
      String var10003 = resp;
      var10000[2] = String.valueOf(nmin);
      var10000[3] = "0";
      var10000[4] = "128";
      var10000[5] = "0";
      var10000[6] = "128";
      String[] respuestas = var10000;
      if (Dialogos.optPreguntas(f, preguntas, respuestas, OptFou.idiom)) {
         int fila0;
         int columna0;
         int filas;
         int columnas;
         try {
            numImagen = Integer.valueOf(respuestas[0]);
            numImagenDestino = Integer.valueOf(respuestas[2]);
            fila0 = Integer.valueOf(respuestas[3]);
            filas = Integer.valueOf(respuestas[4]);
            columna0 = Integer.valueOf(respuestas[5]);
            columnas = Integer.valueOf(respuestas[6]);
         } catch (NumberFormatException var21) {
            errorNumber(1);
            return;
         }

         try {
            zcolumnas = Float.valueOf(respuestas[1]);
         } catch (NumberFormatException var20) {
            errorNumber(0);
            return;
         }

         Frame fr;
         Object[] options;
         JOptionPane hola;
         JDialog dialog;
         if (zcolumnas <= 0.0F) {
            fr = new Frame();
            if (icon_joc != null) {
               fr.setIconImage(icon_joc.getImage());
            }

            options = new Object[]{OptFou.aceptar[OptFou.idiom]};
            hola = new JOptionPane(this.factor[OptFou.idiom] + " <=0", 0, -1, (Icon)null, options);
            dialog = hola.createDialog(fr, "");
            dialog.setVisible(true);
         } else if (!this.imagIgual(numImagen, numImagenDestino)) {
            if (!controlImagen[numImagen]) {
               fr = new Frame();
               if (icon_joc != null) {
                  fr.setIconImage(icon_joc.getImage());
               }

               options = new Object[]{OptFou.aceptar[OptFou.idiom]};
               hola = new JOptionPane(existe[1][OptFou.idiom], 0, -1, (Icon)null, options);
               dialog = hola.createDialog(fr, "");
               dialog.setVisible(true);
            } else if (!controlImagen[numImagen]) {
               fr = new Frame();
               if (icon_joc != null) {
                  fr.setIconImage(icon_joc.getImage());
               }

               options = new Object[]{OptFou.aceptar[OptFou.idiom]};
               hola = new JOptionPane(existe[1][OptFou.idiom], 0, -1, (Icon)null, options);
               dialog = hola.createDialog(fr, "");
               dialog.setVisible(true);
            } else {
               if (controlImagen[numImagenDestino]) {
                  fr = new Frame();
                  if (icon_joc != null) {
                     fr.setIconImage(icon_joc.getImage());
                  }

                  options = new Object[]{SiNo[0][OptFou.idiom], SiNo[1][OptFou.idiom]};
                  hola = new JOptionPane(existe[0][OptFou.idiom] + ". " + sobrescriure[OptFou.idiom], 1, -1, (Icon)null, options);
                  dialog = hola.createDialog(fr, "");
                  dialog.setVisible(true);
                  if (!options[0].equals(hola.getValue())) {
                     return;
                  }

                  imagen[numImagenDestino].setVisible(false);
                  --nmin;
               }

               if (fila0 <= imagen[numImagen].nfil && fila0 >= 0) {
                  if (filas <= imagen[numImagen].nfil && filas >= 0) {
                     if (columna0 <= imagen[numImagen].ncol && columna0 >= 0) {
                        if (columnas <= imagen[numImagen].ncol && columnas >= 0) {
                           control = true;
                           ++nmin;
                           if (control) {
                              int fil = (int)(zcolumnas * (float)filas);
                              int col = (int)(zcolumnas * (float)columnas);
                              if (fil * col > 412500) {
                                 --nmin;
                                 Frame fr = new Frame();
                                 if (icon_joc != null) {
                                    fr.setIconImage(icon_joc.getImage());
                                 }

                                 Object[] options = new Object[]{OptFou.aceptar[OptFou.idiom]};
                                 JOptionPane hola = new JOptionPane(gran[OptFou.idiom], 0, -1, (Icon)null, options);
                                 JDialog dialog = hola.createDialog(fr, "");
                                 dialog.setVisible(true);
                                 return;
                              }

                              controlImagen[numImagen] = true;
                              controlImagen[numImagenDestino] = true;
                              String nombreImagen = im[0][OptFou.idiom] + numImagenDestino;
                              int[] mapa = new int[fil * col];
                              m.hacezoom(imagen[numImagen], zcolumnas, zcolumnas, fila0, filas, columna0, columnas, mapa);

                              int i;
                              for(i = 0; i < fil * col; ++i) {
                                 mapa[i] |= -16777216 | mapa[i] << 16 | mapa[i] << 8;
                              }

                              imagen[numImagenDestino] = new OptImagen(im[0][OptFou.idiom] + numImagenDestino, fil, col);
                              if (icon_joc != null) {
                                 imagen[numImagenDestino].setIconImage(icon_joc.getImage());
                              }

                              imagen[numImagenDestino].creaVentanaOptImagen(imagen[numImagenDestino], fil, col);

                              try {
                                 OptFou.vect.get(numImagenDestino);
                              } catch (ArrayIndexOutOfBoundsException var19) {
                                 OptFou.vect.add(numImagenDestino, nombreImagen);
                              }

                              OptFou.jCombo.setSelectedIndex(numImagenDestino);

                              for(i = 0; i < fil; ++i) {
                                 for(int j = 0; j < col; ++j) {
                                    imagen[numImagenDestino].buffImage.setRGB(j, i, mapa[i * fil + j]);
                                 }
                              }

                              imagen[numImagenDestino].g2 = imagen[numImagenDestino].buffImage.createGraphics();

                              do {
                                 imagen[numImagenDestino].g2.drawImage(imagen[numImagenDestino].buffImage, 0, 0, imagen[numImagenDestino]);
                              } while(imagen[numImagenDestino].buffImage.getRGB(fil - 1, fil - 1) == 0);

                              imagen[numImagenDestino].bytesPixel = 1;
                              ++numOperacio;
                              FrameInfo.info.append(" " + numOperacio + ". Zoom " + this.factor[OptFou.idiom] + " " + zcolumnas + this.dela[0][OptFou.idiom] + im[1][OptFou.idiom] + numImagen + ".\n");
                              FrameInfo.info.append(" " + this.resultat[OptFou.idiom] + numImagenDestino + ".\n\n");
                              Date fecha = new Date();
                              String hora = hms.format(fecha);
                              OptFou.textoTime[numImagenDestino] = hora;
                              OptFou.textoDim[numImagenDestino] = imagen[numImagenDestino].ncol + " x " + imagen[numImagenDestino].nfil;
                              OptFou.texto[numImagenDestino] = " Zoom " + this.factor[OptFou.idiom] + " " + zcolumnas + im[1][OptFou.idiom] + numImagen;
                              OptFou.jTextoTime.setText(OptFou.textoTime[numImagenDestino]);
                              OptFou.jTextoDim.setText(OptFou.textoDim[numImagenDestino]);
                              OptFou.jTexto.setText(OptFou.texto[numImagenDestino]);
                              OptFou.save.setText("");
                              OptFou.save.setIcon((Icon)null);
                              OptFou.salvado[numImagenDestino] = false;
                           }

                        } else {
                           fr = new Frame();
                           if (icon_joc != null) {
                              fr.setIconImage(icon_joc.getImage());
                           }

                           options = new Object[]{OptFou.aceptar[OptFou.idiom]};
                           hola = new JOptionPane(this.errorFilas[3][OptFou.idiom], 0, -1, (Icon)null, options);
                           dialog = hola.createDialog(fr, "");
                           dialog.setVisible(true);
                        }
                     } else {
                        fr = new Frame();
                        if (icon_joc != null) {
                           fr.setIconImage(icon_joc.getImage());
                        }

                        options = new Object[]{OptFou.aceptar[OptFou.idiom]};
                        hola = new JOptionPane(this.errorFilas[2][OptFou.idiom], 0, -1, (Icon)null, options);
                        dialog = hola.createDialog(fr, "");
                        dialog.setVisible(true);
                     }
                  } else {
                     fr = new Frame();
                     if (icon_joc != null) {
                        fr.setIconImage(icon_joc.getImage());
                     }

                     options = new Object[]{OptFou.aceptar[OptFou.idiom]};
                     hola = new JOptionPane(this.errorFilas[1][OptFou.idiom], 0, -1, (Icon)null, options);
                     dialog = hola.createDialog(fr, "");
                     dialog.setVisible(true);
                  }
               } else {
                  fr = new Frame();
                  if (icon_joc != null) {
                     fr.setIconImage(icon_joc.getImage());
                  }

                  options = new Object[]{OptFou.aceptar[OptFou.idiom]};
                  hola = new JOptionPane(this.errorFilas[0][OptFou.idiom], 0, -1, (Icon)null, options);
                  dialog = hola.createDialog(fr, "");
                  dialog.setVisible(true);
               }
            }
         }
      }
   }

   boolean imagDif(int n, int m) {
      if (imagen[n].ncol == imagen[m].ncol && imagen[n].nfil == imagen[m].nfil) {
         return false;
      } else {
         Frame f = new Frame();
         if (icon_joc != null) {
            f.setIconImage(icon_joc.getImage());
         }

         Object[] options = new Object[]{OptFou.aceptar[OptFou.idiom]};
         JOptionPane hola = new JOptionPane(this.diferents[OptFou.idiom], 0, -1, (Icon)null, options);
         JDialog dialog = hola.createDialog(f, (String)null);
         dialog.setVisible(true);
         return true;
      }
   }

   boolean imagIgual(int n, int m) {
      if (n == m) {
         Frame f = new Frame();
         if (icon_joc != null) {
            f.setIconImage(icon_joc.getImage());
         }

         Object[] options = new Object[]{OptFou.aceptar[OptFou.idiom]};
         JOptionPane hola = new JOptionPane(this.iguals[OptFou.idiom], 0, -1, (Icon)null, options);
         JDialog dialog = hola.createDialog(f, (String)null);
         dialog.setVisible(true);
         return true;
      } else {
         return false;
      }
   }

   static void errorNumber(int sencer) {
      Frame f = new Frame();
      if (icon_joc != null) {
         f.setIconImage(icon_joc.getImage());
      }

      Object[] options = new Object[]{OptFou.aceptar[OptFou.idiom]};
      JOptionPane hola;
      if (sencer == 1) {
         hola = new JOptionPane(numberError[1][OptFou.idiom], 0, -1, (Icon)null, options);
      } else {
         hola = new JOptionPane(numberError[0][OptFou.idiom], 0, -1, (Icon)null, options);
      }

      JDialog dialog = hola.createDialog(f, (String)null);
      dialog.setVisible(true);
   }

   static {
      imagen = new OptImagen[maxNumImagen + 1];
      controlImagen = new boolean[maxNumImagen + 1];
      desti = new String[]{"Desti finestra ", "Destino ventana ", "Window destination "};
      im = new String[][]{{"Imatge ", "Imagen ", "Image "}, {" imatge ", " imagen ", " image "}, {" imatges ", " imágenes ", " images "}};
      existe = new String[][]{{"Ja existeix", "Ya existe", "It already exists"}, {"No existeix", "No existe", "Not yet created"}};
      carrega = new String[]{"Arxiu carregat a la finestra ", "Archivo cargado en la ventana ", "File loaded in window "};
      gran = new String[]{"Imatge massa gran", "Imagen demasiado grande", "Too large image"};
      sobrescriure = new String[]{"Sobrescriure?", "Sobreescribir?", "Overwrite it?"};
      nmin = 0;
      hms = new SimpleDateFormat("HH:mm:ss");
      resp = new String();
      SiNo = new String[][]{{"Acceptar", "Aceptar", "OK"}, {"No", "No", "No"}};
      numberError = new String[][]{{"El camp ha de ser un nombre", "El campo ha de ser un número", "Field has to be a number"}, {"El camp ha de ser un nombre sencer", "El campo ha de ser un número entero", "Field has to be an integer number"}};
      icon_joc = null;
      denom = new String[]{"Sumand del denominador", "Sumando del denominador", "Adding term of denominator"};
   }
}
