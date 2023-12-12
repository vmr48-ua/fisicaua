package optfourier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import local.imagenes.OptImagen;

public class Visualitzacio extends JFrame {
   JPanel jPanel1 = new JPanel();
   JPanel jPanel2 = new JPanel();
   JPanel jPanel3 = new JPanel();
   JPanel jPanel4 = new JPanel();
   JPanel jPanel5 = new JPanel();
   static JSlider jSliderH = new JSlider();
   static JSlider jSliderV = new JSlider(1);
   JLabel jLabel1 = new JLabel();
   static int tipo;
   static int numImagen;
   String[][] label = new String[][]{{"Lectura de fila", "Lectura de fila", "Row scan"}, {"Lectura de columna", "Lectura de columna", "Column scan"}};
   String[] salva = new String[]{"Salvar", "Salvar", "Save"};
   BorderLayout borderLayout1 = new BorderLayout();
   JPanel jPanel6 = new JPanel();
   JPanel jPanel7 = new JPanel();
   JButton jButton1 = new JButton();
   JButton jButton2 = new JButton();
   BorderLayout borderLayout2 = new BorderLayout();
   JPanel jPanel8 = new JPanel();
   BorderLayout borderLayout3 = new BorderLayout();
   JPanel jPanel9 = new JPanel();
   JPanel jPanel10 = new JPanel();
   BorderLayout borderLayout4 = new BorderLayout();
   Graphics g;
   OptImagen auxImagen = new OptImagen("", 256, 256);
   Graphics2D g2;
   static int[] mapa;
   static int[] mapaPlot;
   PlotXY plotXY;
   JLabel jLabel2;
   static ImageIcon icon_joc = null;

   public Visualitzacio(int numImagen) {
      this.g2 = (Graphics2D)this.g;
      this.jLabel2 = new JLabel();
      Visualitzacio.numImagen = numImagen;

      try {
         String st_icon = "jocon.jpg";
         URL url_icon = this.getClass().getResource(st_icon);
         icon_joc = new ImageIcon(url_icon, "Òptica de Fourier");
      } catch (Exception var5) {
         System.out.println("No carga icono");
      }

      if (icon_joc != null) {
         this.setIconImage(icon_joc.getImage());
      }

      try {
         this.jbInit();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setSize(400, 490);
      this.setResizable(false);
      this.plotXY = new PlotXY();
      this.plotXY.setBackground(new Color(0, 0, 0));
      this.plotXY.setMaximumSize(new Dimension(300, 300));
      this.plotXY.setMinimumSize(new Dimension(300, 300));
      this.plotXY.setPreferredSize(new Dimension(300, 300));
      this.jPanel1.setBackground(new Color(204, 204, 204));
      this.jPanel1.setMaximumSize(new Dimension(50, 10));
      this.jPanel1.setMinimumSize(new Dimension(50, 10));
      this.jPanel1.setPreferredSize(new Dimension(26, 10));
      this.jPanel5.setBackground(new Color(204, 204, 204));
      this.jPanel5.setPreferredSize(new Dimension(50, 10));
      this.jPanel5.setLayout(this.borderLayout3);
      this.jPanel5.setMaximumSize(new Dimension(50, 10));
      this.jPanel5.setMinimumSize(new Dimension(50, 10));
      this.jPanel3.setBackground(new Color(204, 204, 204));
      this.jPanel3.setMinimumSize(new Dimension(10, 75));
      this.jPanel3.setPreferredSize(new Dimension(10, 75));
      this.jPanel3.setLayout(this.borderLayout1);
      this.jPanel3.setMaximumSize(new Dimension(10, 75));
      this.jPanel4.setBackground(new Color(204, 204, 204));
      this.jPanel4.setPreferredSize(new Dimension(10, 75));
      this.jPanel4.setLayout(this.borderLayout2);
      this.jPanel4.setMaximumSize(new Dimension(10, 75));
      this.jPanel4.setMinimumSize(new Dimension(10, 75));
      this.jPanel2.setPreferredSize(new Dimension(300, 300));
      this.jPanel2.setBackground(new Color(0, 0, 0));
      this.jPanel2.setLayout(new BorderLayout());
      this.jPanel2.setMaximumSize(new Dimension(300, 300));
      this.jPanel2.setMinimumSize(new Dimension(300, 300));
      jSliderH.setMaximum(Holog.imagen[numImagen].ncol - 1);
      jSliderV.setMinimum(0);
      jSliderV.setMaximum(Holog.imagen[numImagen].ncol - 1);
      jSliderH.setBackground(new Color(204, 204, 204));
      jSliderH.setPreferredSize(new Dimension(300, 16));
      jSliderV.setBackground(new Color(204, 204, 204));
      jSliderV.setPreferredSize(new Dimension(16, 300));
      this.jLabel1.setFont(new Font("Dialog", 1, 16));
      this.jLabel1.setForeground(new Color(0, 0, 0));
      this.jLabel1.setPreferredSize(new Dimension(400, 35));
      this.jLabel1.setHorizontalAlignment(0);
      this.jLabel1.setHorizontalTextPosition(0);
      this.jLabel1.setText("");
      this.jLabel2.setBackground(UIManager.getColor("info"));
      this.jLabel2.setFont(new Font("Dialog", 1, 16));
      this.jLabel2.setForeground(new Color(0, 0, 0));
      this.jLabel2.setPreferredSize(new Dimension(400, 40));
      this.jLabel2.setHorizontalAlignment(0);
      this.jLabel2.setHorizontalTextPosition(0);
      this.jLabel2.setText(OptFou.titol0[OptFou.idiom] + numImagen);
      this.jPanel6.setBackground(new Color(204, 204, 204));
      this.jPanel6.setPreferredSize(new Dimension(10, 25));
      this.jPanel7.setBackground(new Color(204, 204, 204));
      this.jPanel7.setPreferredSize(new Dimension(10, 50));
      this.jButton1.setForeground(new Color(0, 0, 0));
      this.jButton1.setPreferredSize(new Dimension(81, 27));
      this.jButton1.setRequestFocusEnabled(false);
      this.jButton1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Visualitzacio.this.jButton1_actionPerformed(e);
         }
      });
      this.jButton2.setForeground(new Color(0, 0, 0));
      this.jButton2.setPreferredSize(new Dimension(81, 27));
      this.jButton2.setRequestFocusEnabled(false);
      this.jButton2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Visualitzacio.this.jButton2_actionPerformed(e);
         }
      });
      this.jPanel8.setBackground(new Color(204, 204, 204));
      this.jPanel8.setPreferredSize(new Dimension(50, 10));
      this.jPanel9.setBackground(new Color(204, 204, 204));
      this.jPanel10.setBackground(new Color(204, 204, 204));
      this.jPanel10.setLayout(this.borderLayout4);
      this.jPanel10.setPreferredSize(new Dimension(40, 0));
      this.getContentPane().add(this.jPanel1, "West");
      this.getContentPane().add(this.jPanel2, "Center");
      this.getContentPane().add(this.jPanel3, "South");
      this.jPanel3.add(this.jPanel6, "North");
      this.jPanel3.add(this.jPanel7, "South");
      this.jPanel7.add(this.jButton1, (Object)null);
      this.jPanel7.add(this.jPanel8, (Object)null);
      this.jPanel7.add(this.jButton2, (Object)null);
      this.jPanel6.add(jSliderH, (Object)null);
      this.getContentPane().add(this.jPanel4, "North");
      this.jPanel4.add(this.jLabel1, "South");
      this.jPanel4.add(this.jLabel2, "North");
      this.getContentPane().add(this.jPanel5, "East");
      this.jPanel5.add(this.jPanel9, "West");
      this.jPanel5.add(this.jPanel10, "Center");
      this.jPanel2.add(this.plotXY, "Center");
      this.jPanel10.add(jSliderV, (Object)null);
      this.jButton1.setText(this.salva[OptFou.idiom]);
      this.jButton2.setText(OptFou.etiqueta[7][OptFou.idiom]);
      Holog.imagen[numImagen].g2 = Holog.imagen[numImagen].buffImage.createGraphics();
      jSliderH.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            Visualitzacio.this.jSliderH_mouseClicked(e);
         }
      });
      jSliderH.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            Visualitzacio.this.jSliderH_mouseDragged(e);
         }
      });
      jSliderV.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            Visualitzacio.this.jSliderH_mouseClicked(e);
         }
      });
      jSliderV.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            Visualitzacio.this.jSliderV_mouseDragged(e);
         }
      });
      jSliderV.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            Visualitzacio.this.jSliderV_keyPressed(e);
         }
      });
      jSliderH.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            Visualitzacio.this.jSliderH_keyPressed(e);
         }
      });
      mapa = new int[Holog.imagen[numImagen].nfil * Holog.imagen[numImagen].ncol];
      mapaPlot = new int[Holog.imagen[numImagen].nfil * Holog.imagen[numImagen].ncol];

      for(int i = 0; i < Holog.imagen[numImagen].nfil; ++i) {
         for(int j = 0; j < Holog.imagen[numImagen].ncol; ++j) {
            mapa[i * Holog.imagen[numImagen].ncol + j] = Holog.imagen[numImagen].buffImage.getRGB(j, i) & 255;
            mapaPlot[i * Holog.imagen[numImagen].ncol + j] = Holog.imagen[numImagen].buffImage.getRGB(j, i) & 255;
            mapa[i] |= -16777216 | mapa[i] << 16 | mapa[i] << 8;
         }
      }

   }

   void jButton1_actionPerformed(ActionEvent e) {
      JFileChooser eligeArchivo = new JFileChooser();
      int numFila = Holog.imagen[numImagen].ncol - 1 - jSliderV.getValue();
      boolean vale;
      String[] coordenada;
      int valorRetorno;
      Frame fr;
      Object[] options;
      JOptionPane hola;
      JDialog dialog;
      File archivo;
      String pathfile;
      String nombrefile;
      File file;
      boolean var15;
      DataOutputStream outData;
      int i;
      String nombreArchivo;
      if (tipo == 0) {
         vale = false;
         coordenada = new String[Holog.imagen[numImagen].nfil];

         for(valorRetorno = 0; valorRetorno < Holog.imagen[numImagen].ncol; ++valorRetorno) {
            coordenada[valorRetorno] = "\n " + valorRetorno + "\t" + mapaPlot[numFila * Holog.imagen[numImagen].nfil + valorRetorno];
         }

         do {
            valorRetorno = eligeArchivo.showSaveDialog(this);
            if (valorRetorno == 0) {
               if (eligeArchivo.getSelectedFile().exists()) {
                  fr = new Frame();
                  if (icon_joc != null) {
                     fr.setIconImage(icon_joc.getImage());
                  }

                  options = new Object[]{Holog.SiNo[0][OptFou.idiom], Holog.SiNo[1][OptFou.idiom]};
                  hola = new JOptionPane(Holog.existe[0][OptFou.idiom] + ". " + Holog.sobrescriure[OptFou.idiom], 1, -1, (Icon)null, options);
                  dialog = hola.createDialog(fr, "");
                  dialog.setVisible(true);
                  if (options[0].equals(hola.getValue())) {
                     archivo = eligeArchivo.getSelectedFile();
                     pathfile = archivo.getPath();
                     nombrefile = archivo.getName();
                     file = new File(pathfile);

                     try {
                        var15 = file.createNewFile();
                     } catch (Exception var19) {
                        System.out.println(var19);
                     }

                     var15 = file.canWrite();

                     try {
                        outData = new DataOutputStream(new FileOutputStream(archivo));
                        outData.writeBytes(this.label[0][OptFou.idiom] + " " + numFila + "\n");

                        for(i = 0; i < Holog.imagen[numImagen].ncol; ++i) {
                           outData.writeBytes(coordenada[i]);
                        }

                        outData.close();
                     } catch (Exception var21) {
                        System.out.println(var21);
                     }

                     nombreArchivo = eligeArchivo.getDescription(archivo);
                     System.out.println(nombreArchivo);
                     vale = true;
                  } else {
                     vale = false;
                  }
               }
            } else {
               vale = true;
            }
         } while(!vale);
      }

      if (tipo == 1) {
         vale = false;
         coordenada = new String[Holog.imagen[numImagen].nfil];

         for(valorRetorno = 0; valorRetorno < Holog.imagen[numImagen].nfil; ++valorRetorno) {
            coordenada[valorRetorno] = "\n " + valorRetorno + "\t" + mapaPlot[jSliderH.getValue() * Holog.imagen[numImagen].nfil + valorRetorno];
         }

         do {
            valorRetorno = eligeArchivo.showSaveDialog(this);
            if (valorRetorno == 0) {
               if (eligeArchivo.getSelectedFile().exists()) {
                  fr = new Frame();
                  if (icon_joc != null) {
                     fr.setIconImage(icon_joc.getImage());
                  }

                  options = new Object[]{Holog.SiNo[0][OptFou.idiom], Holog.SiNo[1][OptFou.idiom]};
                  hola = new JOptionPane(Holog.existe[0][OptFou.idiom] + ". " + Holog.sobrescriure[OptFou.idiom], 1, -1, (Icon)null, options);
                  dialog = hola.createDialog(fr, "");
                  dialog.setVisible(true);
                  if (options[0].equals(hola.getValue())) {
                     archivo = eligeArchivo.getSelectedFile();
                     pathfile = archivo.getPath();
                     nombrefile = archivo.getName();
                     file = new File(pathfile);

                     try {
                        var15 = file.createNewFile();
                     } catch (Exception var18) {
                        System.out.println(var18);
                     }

                     var15 = file.canWrite();

                     try {
                        outData = new DataOutputStream(new FileOutputStream(archivo));
                        outData.writeBytes(this.label[1][OptFou.idiom] + " " + jSliderH.getValue() + "\n");

                        for(i = 0; i < Holog.imagen[numImagen].nfil; ++i) {
                           outData.writeBytes(coordenada[i]);
                        }

                        outData.close();
                     } catch (Exception var20) {
                        System.out.println(var20);
                     }

                     nombreArchivo = eligeArchivo.getDescription(archivo);
                     System.out.println(nombreArchivo);
                     vale = true;
                  } else {
                     vale = false;
                  }
               }
            } else {
               vale = true;
            }
         } while(!vale);
      }

   }

   void jButton2_actionPerformed(ActionEvent e) {
      this.setVisible(false);
      OptFou.jMenuItem15.setEnabled(true);
      OptFou.jMenuItem16.setEnabled(true);
      jSliderH.setEnabled(true);
      jSliderV.setEnabled(true);

      int i;
      for(i = 0; i < Holog.imagen[numImagen].nfil * Holog.imagen[numImagen].ncol; ++i) {
         mapa[i] |= -16777216 | mapa[i] << 16 | mapa[i] << 8;
      }

      for(i = 0; i < Holog.imagen[numImagen].nfil; ++i) {
         for(int j = 0; j < Holog.imagen[numImagen].ncol; ++j) {
            Holog.imagen[numImagen].buffImage.setRGB(j, i, mapa[i * Holog.imagen[numImagen].ncol + j]);
         }
      }

      Holog.imagen[numImagen].repaint();
   }

   static void setTipo(int tipo) {
      Visualitzacio.tipo = tipo;
      if (tipo == 0) {
         jSliderH.setEnabled(false);
      }

      if (tipo == 1) {
         jSliderV.setEnabled(false);
      }

   }

   void jSliderH_mouseDragged(MouseEvent e) {
      try {
         this.jLabel1.setText(this.label[tipo][OptFou.idiom] + ": " + jSliderH.getValue());
         this.pintaImagen(jSliderH.getValue(), 1);
         this.plotXY.get_linea(jSliderH.getValue(), true);
         this.repaint();
      } catch (Exception var6) {
         try {
            int i;
            for(i = 0; i < Holog.imagen[numImagen].nfil * Holog.imagen[numImagen].ncol; ++i) {
               mapa[i] |= -16777216 | mapa[i] << 16 | mapa[i] << 8;
            }

            for(i = 0; i < Holog.imagen[numImagen].nfil; ++i) {
               for(int j = 0; j < Holog.imagen[numImagen].ncol; ++j) {
                  Holog.imagen[numImagen].buffImage.setRGB(j, i, mapa[i * Holog.imagen[numImagen].nfil + j]);
               }

               System.out.print("excepcion");
            }

            Holog.imagen[numImagen].repaint();
         } catch (Exception var5) {
         }
      }

   }

   void jSliderH_mouseClicked(MouseEvent e) {
      this.jSliderH_mouseDragged(e);
   }

   void jSliderH_keyPressed(KeyEvent e) {
      try {
         this.jLabel1.setText(this.label[tipo][OptFou.idiom] + ": " + jSliderH.getValue());
         this.pintaImagen(jSliderH.getValue(), 1);
         this.plotXY.get_linea(jSliderH.getValue(), true);
         this.repaint();
      } catch (Exception var6) {
         try {
            int i;
            for(i = 0; i < Holog.imagen[numImagen].nfil * Holog.imagen[numImagen].ncol; ++i) {
               mapa[i] |= -16777216 | mapa[i] << 16 | mapa[i] << 8;
            }

            for(i = 0; i < Holog.imagen[numImagen].nfil; ++i) {
               for(int j = 0; j < Holog.imagen[numImagen].ncol; ++j) {
                  Holog.imagen[numImagen].buffImage.setRGB(j, i, mapa[i * Holog.imagen[numImagen].nfil + j]);
               }

               System.out.print("excepcion");
            }

            Holog.imagen[numImagen].repaint();
         } catch (Exception var5) {
         }
      }

   }

   void jSliderV_mouseDragged(MouseEvent e) {
      int numFila = Holog.imagen[numImagen].ncol - 1 - jSliderV.getValue();

      try {
         this.jLabel1.setText(this.label[tipo][OptFou.idiom] + ": " + numFila);
         this.plotXY.get_linea(numFila, false);
         this.pintaImagen(numFila, 0);
         this.repaint();
      } catch (Exception var7) {
         try {
            for(int i = 0; i < Holog.imagen[numImagen].nfil; ++i) {
               for(int j = 0; j < Holog.imagen[numImagen].ncol; ++j) {
                  Holog.imagen[numImagen].buffImage.setRGB(j, i, mapa[i * Holog.imagen[numImagen].nfil + j]);
               }

               System.out.print("excepcion");
            }

            Holog.imagen[numImagen].repaint();
         } catch (Exception var6) {
         }
      }

   }

   void jSliderV_mouseClicked(MouseEvent e) {
      try {
         this.jSliderV_mouseDragged(e);
      } catch (Exception var3) {
      }

   }

   void jSliderV_keyPressed(KeyEvent e) {
      int numFila = Holog.imagen[numImagen].ncol - 1 - jSliderV.getValue();

      try {
         this.jLabel1.setText(this.label[tipo][OptFou.idiom] + ": " + numFila);
         this.plotXY.get_linea(numFila, false);
         this.pintaImagen(numFila, 0);
         this.repaint();
      } catch (Exception var7) {
         try {
            for(int i = 0; i < Holog.imagen[numImagen].nfil; ++i) {
               for(int j = 0; j < Holog.imagen[numImagen].ncol; ++j) {
                  Holog.imagen[numImagen].buffImage.setRGB(j, i, mapa[i * Holog.imagen[numImagen].nfil + j]);
               }

               System.out.print("excepcion");
            }

            Holog.imagen[numImagen].repaint();
         } catch (Exception var6) {
         }
      }

   }

   void pintaImagen(int numFila, int tipo) {
      int i;
      for(i = 0; i < Holog.imagen[numImagen].nfil * Holog.imagen[numImagen].ncol; ++i) {
         mapa[i] |= -16777216 | mapa[i] << 16 | mapa[i] << 8;
      }

      for(i = 0; i < Holog.imagen[numImagen].nfil; ++i) {
         for(int j = 0; j < Holog.imagen[numImagen].ncol; ++j) {
            Holog.imagen[numImagen].buffImage.setRGB(j, i, mapa[i * Holog.imagen[numImagen].nfil + j]);
         }
      }

      Holog.imagen[numImagen].g2.setColor(Color.red);
      if (tipo == 0) {
         Holog.imagen[numImagen].g2.drawLine(0, numFila, Holog.imagen[numImagen].ncol, numFila);
      }

      if (tipo == 1) {
         Holog.imagen[numImagen].g2.drawLine(numFila, 0, numFila, Holog.imagen[numImagen].nfil);
      }

      Holog.imagen[numImagen].repaint();
   }
}
