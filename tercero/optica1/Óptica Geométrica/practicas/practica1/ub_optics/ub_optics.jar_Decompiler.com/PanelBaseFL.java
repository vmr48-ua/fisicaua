import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

class PanelBaseFL extends JPanel implements ActionListener {
   static JPanel panelMP;
   Font fuente1 = new Font("Dialog", 1, 24);
   Font fuente2 = new Font("Dialog", 1, 12);
   Color colorTexto1;
   Color colorTexto2;

   public PanelBaseFL() {
      this.colorTexto1 = Color.white;
      this.colorTexto2 = Color.black;
      this.setLayout((LayoutManager)null);
      this.setSize(SistemaFL.ancho, SistemaFL.alto);
      panelMP = new JPanel();
      panelMP.setLayout((LayoutManager)null);
      panelMP.setBackground(Color.gray);
      panelMP.setBounds(0, 220, 800, 230);
      JLabel var1 = new JLabel(SistemaFL.text[0][SistemaFL.lang]);
      var1.setBounds(50, 10, 200, 30);
      var1.setForeground(this.colorTexto1);
      var1.setFont(this.fuente2);
      panelMP.add(var1);
      JButton var2 = new JButton(SistemaFL.text[1][SistemaFL.lang]);
      var2.setBounds(10, 40, 200, 30);
      var2.setForeground(this.colorTexto2);
      var2.setFont(this.fuente2);
      var2.addActionListener(this);
      var2.setActionCommand("buttonAntep");
      panelMP.add(var2);
      JButton var3 = new JButton(SistemaFL.text[2][SistemaFL.lang]);
      var3.setBounds(10, 70, 200, 30);
      var3.setForeground(this.colorTexto2);
      var3.setFont(this.fuente2);
      var3.addActionListener(this);
      var3.setActionCommand("buttonExactoFL");
      panelMP.add(var3);
      JButton var4 = new JButton(SistemaFL.text[3][SistemaFL.lang]);
      var4.setBounds(10, 100, 200, 30);
      var4.setForeground(this.colorTexto2);
      var4.setFont(this.fuente2);
      var4.addActionListener(this);
      var4.setActionCommand("buttonLeer");
      panelMP.add(var4);
      JButton var5 = new JButton(SistemaFL.text[4][SistemaFL.lang]);
      var5.setBounds(10, 130, 200, 30);
      var5.setForeground(this.colorTexto2);
      var5.setFont(this.fuente2);
      var5.addActionListener(this);
      var5.setActionCommand("buttonGrabar");
      panelMP.add(var5);
      JButton var6 = new JButton(SistemaFL.text[5][SistemaFL.lang]);
      var6.setBounds(10, 160, 200, 30);
      var6.setForeground(this.colorTexto2);
      var6.setFont(this.fuente2);
      var6.addActionListener(this);
      var6.setActionCommand("buttonTeoria");
      panelMP.add(var6);
      JButton var7 = new JButton(SistemaFL.text[6][SistemaFL.lang]);
      var7.setBounds(10, 190, 200, 30);
      var7.setForeground(this.colorTexto2);
      var7.setFont(this.fuente2);
      var7.addActionListener(this);
      var7.setActionCommand("buttonSalida");
      panelMP.add(var7);
      this.add(panelMP);
   }

   public void actionPerformed(ActionEvent var1) {
      double[] var4 = new double[8];
      String var5 = var1.getActionCommand();
      if (var5 == "buttonAntep") {
         this.remove(panelMP);
         AnteproyectoFL.abrir();
         this.repaint();
      }

      if (var5 == "buttonExactoFL") {
         this.remove(panelMP);
         ExactoFL.abrir();
         this.repaint();
      }

      int var2;
      FileDialog var6;
      String var7;
      String var8;
      String var9;
      if (var5 == "buttonLeer") {
         var6 = new FileDialog(new Frame(), "Selector");
         var6.setForeground(Color.black);
         var6.setFont(this.fuente2);
         var6.show();
         var7 = var6.getFile();
         var8 = var6.getDirectory();
         var9 = var8 + var7;
         if (var8 == null || var7 == null) {
            return;
         }

         try {
            FileReader var10 = new FileReader(var9);
            BufferedReader var11 = new BufferedReader(var10);
            int var3 = leeLineaAscii(var11, var4, 4);
            if (var3 < 0) {
               var11.close();
               return;
            }

            SistemaFL.existeObjeto[0] = (int)var4[0];
            SistemaFL.real[0] = (int)var4[1];
            SistemaFL.valZObj[0] = var4[2];
            SistemaFL.campo[0] = var4[3];
            var3 = leeLineaAscii(var11, var4, 4);
            if (var3 < 0) {
               var11.close();
               return;
            }

            SistemaFL.existeObjeto[1] = (int)var4[0];
            SistemaFL.real[1] = (int)var4[1];
            SistemaFL.valZObj[1] = var4[2];
            SistemaFL.campo[1] = var4[3];
            var2 = 1;

            label106:
            while(true) {
               if (var2 >= 7) {
                  var2 = 1;

                  while(true) {
                     if (var2 >= 7) {
                        break label106;
                     }

                     var3 = leeLineaAscii(var11, var4, 8);
                     if (var3 == -1) {
                        var11.close();
                        break label106;
                     }

                     if (var3 == -2) {
                        var11.close();
                        return;
                     }

                     SistemaFL.existe[var2] = (int)var4[0];
                     SistemaFL.tipoE[var2] = (int)var4[1];
                     SistemaFL.valZE[var2] = var4[2];
                     SistemaFL.valPE[var2] = var4[3];
                     SistemaFL.valDE[var2] = var4[4];
                     ExactoFL.valBE[var2] = var4[5];
                     ExactoFL.indice[var2] = var4[6];
                     ExactoFL.abbe[var2] = var4[7];
                     ++var2;
                  }
               }

               SistemaFL.existe[var2] = 0;
               SistemaFL.tipoE[var2] = 1;
               SistemaFL.valPE[var2] = 0.0D;
               SistemaFL.valDE[var2] = 70.0D;
               AnteproyectoFL.datosCorrederaHLente(var2);
               ++var2;
            }
         } catch (IOException var17) {
            return;
         }

         this.remove(panelMP);
         AnteproyectoFL.abrir();
         this.repaint();
      }

      if (var5 == "buttonGrabar") {
         if (SistemaFL.existeObjeto[0] == 0) {
            return;
         }

         var6 = new FileDialog(new Frame(), "Selector");
         var6.setForeground(Color.black);
         var6.setFont(this.fuente2);
         var6.show();
         var7 = var6.getFile();
         var8 = var6.getDirectory();
         var9 = var8 + var7;
         if (var8 == null || var7 == null) {
            return;
         }

         try {
            FileWriter var22 = new FileWriter(var9);
            BufferedWriter var23 = new BufferedWriter(var22);
            String var12 = SistemaFL.existeObjeto[0] + "  " + SistemaFL.real[0] + "  " + SistemaFL.valZObj[0] + "  " + SistemaFL.campo[0];
            var23.write(var12);
            var23.newLine();
            var12 = SistemaFL.existeObjeto[1] + "  " + SistemaFL.real[1] + "  " + SistemaFL.valZObj[1] + "  " + SistemaFL.campo[1];
            var23.write(var12);
            var23.newLine();

            for(var2 = 1; var2 < 7; ++var2) {
               String var13 = SistemaFL.existe[var2] + "  " + SistemaFL.tipoE[var2] + "  " + SistemaFL.valZE[var2] + "  " + SistemaFL.valPE[var2] + "  " + SistemaFL.valDE[var2] + "  " + ExactoFL.valBE[var2] + "  " + ExactoFL.indice[var2] + "  " + ExactoFL.abbe[var2];
               var23.write(var13);
               var23.newLine();
            }

            var23.close();
         } catch (IOException var16) {
            return;
         }
      }

      if (var5 == "buttonTeoria") {
         JFrame var18 = new JFrame(SistemaFL.text[7][SistemaFL.lang]);
         var18.setForeground(Color.black);
         var18.setFont(this.fuente2);
         var18.setSize(700, 500);
         JTextPane var19 = new JTextPane();
         var18.getContentPane().add(var19);
         var19.setBackground(new Color(204, 204, 204));
         JScrollPane var20 = new JScrollPane(var19);
         var20.setBackground(new Color(204, 204, 204));
         var18.getContentPane().add(var20);
         var18.show();
         URL var21 = null;

         try {
            var21 = this.getClass().getResource(SistemaFL.strTeoria);
         } catch (Exception var15) {
            System.err.println("Error al cargar el texto");
         }

         try {
            var19.setPage(var21);
         } catch (IOException var14) {
            System.err.println("Error al leer una URL mala");
         }
      }

      if (var5 == "buttonSalida") {
         System.exit(0);
      }

   }

   static int leeLineaAscii(BufferedReader var0, double[] var1, int var2) {
      boolean var4 = false;
      int var5 = 0;
      char[] var6 = new char[100];
      char[] var7 = new char[100];

      try {
         String var8 = var0.readLine();
         if (var8 == null) {
            return -1;
         } else {
            while(true) {
               String var9 = var8.trim();
               var6 = var9.toCharArray();
               if (var6.length < 1) {
                  break;
               }

               int var3;
               for(var3 = 0; var3 < var6.length && !Character.isWhitespace(var6[var3]); ++var3) {
                  var7[var3] = var6[var3];
               }

               String var10 = new String(var7, 0, var3);
               var1[var5] = Double.parseDouble(var10);
               ++var5;
               if (var5 > var2 || var6.length <= var3) {
                  break;
               }

               var8 = new String(var6, var3, var6.length - var3);
            }

            return var5;
         }
      } catch (EOFException var13) {
         return -2;
      } catch (IOException var14) {
         return -2;
      }
   }
}
