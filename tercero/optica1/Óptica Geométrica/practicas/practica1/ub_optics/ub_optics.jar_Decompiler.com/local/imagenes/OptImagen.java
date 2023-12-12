package local.imagenes;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import local.dialogos.OptDialogos;

public class OptImagen extends OptVentanaNormal {
   public BufferedImage buffImage = null;
   public Graphics2D g2 = null;
   public int[] mapa;
   public Image imag = null;
   public String nombre;
   public int nfil;
   public int ncol;
   public int bytesPixel;
   public String fichero;

   public OptImagen(String titulo, int ancho, int alto) {
      super(titulo, ancho, alto);
      this.nombre = titulo;
   }

   public void paint(Graphics g) {
      if (this.buffImage != null) {
         g.drawImage(this.buffImage, 5, 25, this);
      }

   }

   public int creaVentanaOptImagen(Frame parent, int nfil0, int ncol0) {
      this.bytesPixel = 0;
      this.mapa = new int[nfil0 * ncol0];

      for(int i = 0; i < nfil0 * ncol0; ++i) {
         this.mapa[i] = -1;
      }

      this.imag = this.createImage(new MemoryImageSource(ncol0, nfil0, this.mapa, 0, nfil0));
      this.nombre = "";
      this.buffImage = new BufferedImage(ncol0, nfil0, 2);
      this.g2 = this.buffImage.createGraphics();

      do {
         this.g2.drawImage(this.imag, 0, 0, this);
      } while(this.buffImage.getRGB(ncol0 - 1, nfil0 - 1) == 0);

      this.setVisible(true);
      this.nfil = nfil0;
      this.ncol = ncol0;
      return 0;
   }

   public int leeFicheroDeImagen(Frame parent) {
      Font fuente = new Font("Dialog", 1, 12);
      String[] preguntas = new String[]{"N. de files", "N. de columnes", "Imatge en gris o en color (g/c)"};
      String[] respuestas = new String[]{"256", "256", "g"};
      short bitplanes = true;
      FileDialog selector = new FileDialog(parent, "Selector");
      selector.setFont(fuente);
      selector.show();
      this.fichero = selector.getFile();
      String dir = selector.getDirectory();
      String dirFichero = String.valueOf(String.valueOf(dir)).concat(String.valueOf(String.valueOf(this.fichero)));
      if (dir != null && this.fichero != null) {
         File file = new File(dirFichero);
         byte[] datos = new byte[3];

         byte var28;
         try {
            FileInputStream inFile = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(inFile);
            datos[0] = dis.readByte();
            datos[1] = dis.readByte();
            datos[2] = dis.readByte();
            inFile.close();
         } catch (EOFException var36) {
            OptDialogos.optEscribeFlash(parent, "Error de fi de fitxer");
            var28 = -1;
            return var28;
         } catch (FileNotFoundException var37) {
            OptDialogos.optEscribeFlash(parent, "No es troba el fitxer");
            var28 = -2;
            return var28;
         } catch (IOException var38) {
            OptDialogos.optEscribeFlash(parent, "Error de lectura");
            var28 = -3;
            return var28;
         }

         if ((datos[0] != -1 || datos[1] != -40 || datos[2] != -1) && (datos[0] != 71 || datos[1] != 73 || datos[2] != 70)) {
            Object var29 = null;

            byte var32;
            byte[] dato;
            try {
               FileInputStream inFile = new FileInputStream(file);
               int numDatos = inFile.available();
               dato = new byte[numDatos];
               inFile.read(dato, 0, numDatos);
               inFile.close();
            } catch (EOFException var33) {
               OptDialogos.optEscribeFlash(parent, "Error de lectura");
               var32 = -1;
               return var32;
            } catch (FileNotFoundException var34) {
               OptDialogos.optEscribeFlash(parent, "No es troba el fitxer");
               var32 = -1;
               return var32;
            } catch (IOException var35) {
               OptDialogos.optEscribeFlash(parent, "Error de lectura");
               var32 = -1;
               return var32;
            }

            int i;
            int j;
            int v;
            int vR;
            int vG;
            int vB;
            if (datos[0] == 66 && datos[1] == 77) {
               int l2 = dato[10] | dato[11] << 8 | dato[12] << 16 | dato[13] << 24;
               int v1 = dato[18];
               if (v1 < 0) {
                  v1 += 256;
               }

               int v2 = dato[19];
               if (v2 < 0) {
                  v2 += 256;
               }

               this.ncol = v1 | v2 << 8;
               v1 = dato[22];
               if (v1 < 0) {
                  v1 += 256;
               }

               v2 = dato[23];
               if (v2 < 0) {
                  v2 += 256;
               }

               this.nfil = v1 | v2 << 8;
               short bitplanes = (short)dato[26];
               short bits = (short)dato[28];
               int tipo = bits / 8;
               this.bytesPixel = tipo;
               this.buffImage = new BufferedImage(this.ncol, this.nfil, 2);
               if (tipo == 1) {
                  for(i = this.nfil - 1; i >= 0; --i) {
                     for(j = 0; j < this.ncol; ++j) {
                        v = dato[l2];
                        if (v < 0) {
                           v += 256;
                        }

                        v |= v << 16 | v << 8;
                        v |= -16777216;
                        this.buffImage.setRGB(j, i, v);
                        ++l2;
                     }

                     while(j % 4 != 0) {
                        ++j;
                        ++l2;
                     }
                  }
               }

               if (tipo == 3) {
                  for(i = this.nfil - 1; i >= 0; --i) {
                     for(j = 0; j < this.ncol * 3; j += 3) {
                        vR = dato[l2 + 2];
                        if (vR < 0) {
                           vR += 256;
                        }

                        vG = dato[l2 + 1];
                        if (vG < 0) {
                           vG += 256;
                        }

                        vB = dato[l2];
                        if (vB < 0) {
                           vB += 256;
                        }

                        v = vR << 16 | vG << 8 | vB;
                        v |= -16777216;
                        this.buffImage.setRGB(j / 3, i, v);
                        l2 += 3;
                     }

                     while(j % 4 != 0) {
                        ++j;
                        ++l2;
                     }
                  }
               }
            } else {
               String dialogo = OptDialogos.optPreguntas(parent, preguntas, respuestas);
               if (dialogo == "Cancelar") {
                  return 0;
               }

               this.nfil = Integer.valueOf(respuestas[0]);
               this.ncol = Integer.valueOf(respuestas[1]);
               this.buffImage = new BufferedImage(this.ncol, this.nfil, 2);
               char letra = respuestas[2].charAt(0);
               if (letra == 'g') {
                  this.bytesPixel = 1;
               } else {
                  this.bytesPixel = 3;
               }

               if (this.bytesPixel == 1) {
                  for(i = 0; i < this.nfil; ++i) {
                     for(j = 0; j < this.ncol; ++j) {
                        v = dato[i * this.ncol + j];
                        if (v < 0) {
                           v += 256;
                        }

                        v |= v << 16 | v << 8;
                        v |= -16777216;
                        this.buffImage.setRGB(j, i, v);
                     }
                  }
               } else {
                  for(i = 0; i < this.nfil; ++i) {
                     for(j = 0; j < this.ncol; ++j) {
                        vR = dato[i * this.ncol * 3 + j * 3];
                        if (vR < 0) {
                           vR += 256;
                        }

                        vG = dato[i * this.ncol * 3 + j * 3 + 1];
                        if (vG < 0) {
                           vG += 256;
                        }

                        vB = dato[i * this.ncol * 3 + j * 3 + 2];
                        if (vB < 0) {
                           vB += 256;
                        }

                        v = vR << 16 | vG << 8 | vB;
                        v |= -16777216;
                        this.buffImage.setRGB(j, i, v);
                     }
                  }
               }
            }
         } else {
            this.imag = Toolkit.getDefaultToolkit().getImage(dirFichero);

            do {
               do {
                  this.nfil = this.imag.getHeight(this);
                  this.ncol = this.imag.getWidth(this);
               } while(this.nfil < 0);
            } while(this.ncol < 0);

            this.bytesPixel = 3;
            this.buffImage = new BufferedImage(this.ncol, this.nfil, 2);
            this.g2 = this.buffImage.createGraphics();

            do {
               this.g2.drawImage(this.imag, 0, 0, this);
            } while(this.buffImage.getRGB(this.ncol - 1, this.nfil - 1) == 0);
         }

         this.setBounds(100, 100, this.ncol + 10, this.nfil + 30);
         this.setVisible(true);
         return 1;
      } else {
         return 0;
      }
   }

   public int grabaFicheroDeImagen(Frame parent) {
      Font fuente = new Font("Dialog", 1, 12);
      int numDatos = this.nfil * this.ncol * this.bytesPixel;
      byte[] dato = null;
      byte[] dato = new byte[numDatos];
      FileDialog selector = new FileDialog(parent, "Selector");
      selector.setFont(fuente);
      selector.show();
      String fichero = selector.getFile();
      String dir = selector.getDirectory();
      String dirFichero = String.valueOf(String.valueOf(dir)).concat(String.valueOf(String.valueOf(fichero)));
      if (dir != null && fichero != null) {
         File file = new File(dirFichero);
         OptImagen.OptPreguntaFormatoFichero tipoFormato = new OptImagen.OptPreguntaFormatoFichero(this, parent);
         tipoFormato.setSize(300, 140);
         tipoFormato.setLocation(400, 100);
         tipoFormato.show();
         int v;
         int i;
         int j;
         int vR;
         int vG;
         int vB;
         FileOutputStream outFile;
         if (tipoFormato.formato == "BMP") {
            short res0 = false;
            short res1 = false;
            int infosize = true;
            short bitplanes = true;
            int tipo = true;
            int bicompres = false;
            int bisizeimage = false;
            int bix = false;
            int biy = false;
            int biclr = false;
            int biclrimp = false;
            short headersize;
            if (this.bytesPixel == 1) {
               headersize = 1078;
            } else {
               headersize = 54;
            }

            int bytesFila = this.ncol * this.bytesPixel;
            int resto = bytesFila % 4;
            if (resto != 0) {
               bytesFila = (bytesFila / 4 + 1) * 4;
            }

            int filesize = headersize + this.nfil * bytesFila;
            short bits = (short)(this.bytesPixel * 8);
            dato = new byte[filesize + this.nfil * 4];
            dato[0] = 66;
            dato[1] = 77;
            dato[2] = (byte)(filesize & 255);
            dato[3] = (byte)((filesize & '\uff00') >> 8);
            dato[4] = (byte)((filesize & 16711680) >> 16);
            dato[5] = (byte)((filesize & -16777216) >> 24);
            dato[10] = (byte)(headersize & 255);
            dato[11] = (byte)((headersize & '\uff00') >> 8);
            dato[12] = (byte)((headersize & 16711680) >> 16);
            dato[13] = (byte)((headersize & -16777216) >> 24);
            dato[14] = 40;
            dato[18] = (byte)(this.ncol & 255);
            dato[19] = (byte)((this.ncol & '\uff00') >> 8);
            dato[22] = (byte)(this.nfil & 255);
            dato[23] = (byte)((this.nfil & '\uff00') >> 8);
            dato[26] = 1;
            dato[28] = (byte)(bits & 255);
            if (this.bytesPixel == 1) {
               int l2 = 54;

               for(i = 0; i < 256; ++i) {
                  v = i;
                  if (i > 127) {
                     v = i - 256;
                  }

                  for(j = 0; j < 3; ++j) {
                     dato[l2 + 4 * i + j] = (byte)v;
                  }

                  dato[l2 + 4 * i + 3] = 0;
               }
            }

            int extremo = headersize;
            if (this.bytesPixel == 1) {
               for(i = 0; i < this.nfil; ++i) {
                  for(j = 0; j < this.ncol; ++j) {
                     v = this.buffImage.getRGB(j, this.nfil - i - 1);
                     v &= 255;
                     if (v > 127) {
                        v -= 256;
                     }

                     dato[extremo] = (byte)v;
                     ++extremo;
                  }

                  while(j % 4 != 0) {
                     ++j;
                     dato[extremo] = 0;
                     ++extremo;
                  }
               }
            } else {
               for(i = 0; i < this.nfil; ++i) {
                  for(j = 0; j < this.ncol * 3; j += 3) {
                     v = this.buffImage.getRGB(j / 3, this.nfil - i - 1);
                     vR = v & 255;
                     if (vR > 127) {
                        vR -= 256;
                     }

                     dato[extremo] = (byte)vR;
                     ++extremo;
                     vG = v & '\uff00';
                     vG >>= 8;
                     if (vG > 127) {
                        vG -= 256;
                     }

                     dato[extremo] = (byte)vG;
                     ++extremo;
                     vB = v & 16711680;
                     vB >>= 16;
                     if (vB > 127) {
                        vB -= 256;
                     }

                     dato[extremo] = (byte)vB;
                     ++extremo;
                  }

                  while(j % 4 != 0) {
                     dato[extremo] = 0;
                     ++j;
                     ++extremo;
                  }
               }
            }

            byte var43;
            try {
               outFile = new FileOutputStream(file);
               DataOutputStream outData = new DataOutputStream(outFile);
               outData.write(dato, 0, extremo);
               outData.close();
               outFile.close();
            } catch (EOFException var47) {
               OptDialogos.optEscribeFlash(parent, "Error de grabació");
               var43 = -1;
               return var43;
            } catch (FileNotFoundException var48) {
               OptDialogos.optEscribeFlash(parent, "Error de grabació");
               var43 = -1;
               return var43;
            } catch (IOException var49) {
               OptDialogos.optEscribeFlash(parent, "Error de grabació");
               var43 = -1;
               return var43;
            }
         } else {
            if (this.bytesPixel == 1) {
               for(i = 0; i < this.nfil; ++i) {
                  for(j = 0; j < this.ncol; ++j) {
                     v = this.buffImage.getRGB(j, i);
                     v &= 255;
                     if (v > 127) {
                        v -= 256;
                     }

                     dato[i * this.ncol + j] = (byte)v;
                  }
               }
            } else {
               for(i = 0; i < this.nfil; ++i) {
                  for(j = 0; j < this.ncol; ++j) {
                     v = this.buffImage.getRGB(j, i);
                     vR = v & 16711680;
                     vR >>= 16;
                     if (vR > 127) {
                        vR -= 256;
                     }

                     dato[i * this.ncol * 3 + j * 3] = (byte)vR;
                     vG = v & '\uff00';
                     vG >>= 8;
                     if (vG > 127) {
                        vG -= 256;
                     }

                     dato[i * this.ncol * 3 + j * 3 + 1] = (byte)vG;
                     vB = v & 255;
                     if (vR > 127) {
                        vB -= 256;
                     }

                     dato[i * this.ncol * 3 + j * 3 + 2] = (byte)vB;
                  }
               }
            }

            byte id1;
            try {
               outFile = new FileOutputStream(file);
               outFile.write(dato, 0, numDatos);
               outFile.close();
            } catch (EOFException var44) {
               OptDialogos.optEscribeFlash(parent, "Error de grabació");
               id1 = -1;
               return id1;
            } catch (FileNotFoundException var45) {
               OptDialogos.optEscribeFlash(parent, "Error de grabació");
               id1 = -1;
               return id1;
            } catch (IOException var46) {
               OptDialogos.optEscribeFlash(parent, "Error de grabació");
               id1 = -1;
               return id1;
            }
         }

         return 1;
      } else {
         return 0;
      }
   }

   class OptPreguntaFormatoFichero extends Dialog {
      Font fuente;
      String formato;

      public OptPreguntaFormatoFichero(OptImagen this$0, Frame parent) {
         super(parent, " ", true);
         this.setLayout((LayoutManager)null);
         this.fuente = new Font("Dialog", 1, 12);
         Label pregunta = new Label("Format del fitxer");
         pregunta.setBounds(50, 40, 280, 30);
         pregunta.setFont(this.fuente);
         this.add(pregunta);
         Button bmp = new Button("BMP");
         bmp.addActionListener(new OptImagen.OptPreguntaFormatoFichero.DialogHandler());
         bmp.setBounds(20, 100, 50, 30);
         bmp.setFont(this.fuente);
         this.add(bmp);
         Button raw = new Button("RAW");
         raw.addActionListener(new OptImagen.OptPreguntaFormatoFichero.DialogHandler());
         raw.setBounds(90, 100, 50, 30);
         raw.setFont(this.fuente);
         this.add(raw);
      }

      class DialogHandler extends WindowAdapter implements ActionListener {
         public void actionPerformed(ActionEvent e) {
            OptPreguntaFormatoFichero.this.formato = e.getActionCommand();
            OptPreguntaFormatoFichero.this.hide();
         }
      }
   }
}
