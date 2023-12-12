import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class PanelElegir2 extends JPanel implements ActionListener {
   String[] texto0 = new String[]{"Filtre", "Filtro", "Filter"};
   String[] texto1 = new String[]{"Il·luminant", "Iluminante", "Illuminant"};
   String[] texto2 = new String[]{"Magenta", "Magenta", "Magenta"};
   String[] texto3 = new String[]{"Groc", "Amarillo", "Yellow"};
   String[] texto4 = new String[]{"Cian", "Cian", "Cyan"};
   String[] texto5 = new String[]{"Vermell", "Rojo", "Red"};
   String[] texto6 = new String[]{"Verd", "Verde", "Green"};
   String[] texto7 = new String[]{"Blau", "Azul", "Blue"};
   String[] texto8 = new String[]{"Acceptar", "Aceptar", "Accept"};
   String[] texto9 = new String[]{"Cancelar", "Cancelar", "Cancel"};
   String[] texto10 = new String[]{"Llegir fitxer", "Leer fichero", "Read file"};
   Font fuente1;
   Font fuente2;
   Color color;
   JTextField jTextField1;
   JTextField jTextField2;
   ButtonGroup grupoBotones1;
   ButtonGroup grupoBotones2;
   JToggleButton jButton1M;
   JToggleButton jButton1Y;
   JToggleButton jButton1C;
   JToggleButton jButton1R;
   JToggleButton jButton1G;
   JToggleButton jButton1B;
   JToggleButton jButton1F;
   JToggleButton jButton2A;
   JToggleButton jButton2B;
   JToggleButton jButton2C;
   JToggleButton jButton2D;
   JToggleButton jButton2E;
   JButton jButtonA;
   JButton jButtonC;

   public PanelElegir2() {
      this.fuente1 = Colores.fuente1;
      this.fuente2 = Colores.fuente2;
      this.color = Colores.color;
      this.jTextField1 = new JTextField(this.texto0[Colores.lang]);
      this.jTextField2 = new JTextField(this.texto1[Colores.lang]);
      this.grupoBotones1 = new ButtonGroup();
      this.grupoBotones2 = new ButtonGroup();
      this.jButton1M = new JToggleButton(this.texto2[Colores.lang]);
      this.jButton1Y = new JToggleButton(this.texto3[Colores.lang]);
      this.jButton1C = new JToggleButton(this.texto4[Colores.lang]);
      this.jButton1R = new JToggleButton(this.texto5[Colores.lang]);
      this.jButton1G = new JToggleButton(this.texto6[Colores.lang], true);
      this.jButton1B = new JToggleButton(this.texto7[Colores.lang]);
      this.jButton1F = new JToggleButton(this.texto10[Colores.lang]);
      this.jButton2A = new JToggleButton("A");
      this.jButton2B = new JToggleButton("B");
      this.jButton2C = new JToggleButton("C", true);
      this.jButton2D = new JToggleButton("D");
      this.jButton2E = new JToggleButton("E");
      this.jButtonA = new JButton(this.texto8[Colores.lang]);
      this.jButtonC = new JButton(this.texto9[Colores.lang]);
      this.setLayout((LayoutManager)null);
      this.setBounds(0, 0, 270, 500);
      this.jTextField1.setFont(this.fuente1);
      this.jTextField1.setForeground(this.color);
      this.jTextField1.setBackground(Color.white);
      this.jTextField1.setBorder(BorderFactory.createRaisedBevelBorder());
      this.jTextField1.setHorizontalAlignment(0);
      this.jTextField1.setBounds(new Rectangle(10, 30, 120, 40));
      this.jTextField1.setEditable(false);
      this.jTextField2.setFont(this.fuente1);
      this.jTextField2.setForeground(this.color);
      this.jTextField2.setBackground(Color.white);
      this.jTextField2.setBorder(BorderFactory.createRaisedBevelBorder());
      this.jTextField2.setHorizontalAlignment(0);
      this.jTextField2.setBounds(new Rectangle(140, 30, 120, 40));
      this.jTextField2.setEditable(false);
      this.jButton1M.setBounds(new Rectangle(10, 84, 120, 20));
      this.jButton1M.setForeground(this.color);
      this.grupoBotones1.add(this.jButton1M);
      this.jButton1Y.setBounds(new Rectangle(10, 104, 120, 20));
      this.jButton1Y.setForeground(this.color);
      this.grupoBotones1.add(this.jButton1Y);
      this.jButton1C.setBounds(new Rectangle(10, 124, 120, 20));
      this.jButton1C.setForeground(this.color);
      this.grupoBotones1.add(this.jButton1C);
      this.jButton1R.setBounds(new Rectangle(10, 144, 120, 20));
      this.jButton1R.setForeground(this.color);
      this.grupoBotones1.add(this.jButton1R);
      this.jButton1G.setBounds(new Rectangle(10, 164, 120, 20));
      this.jButton1G.setForeground(this.color);
      this.grupoBotones1.add(this.jButton1G);
      this.jButton1B.setBounds(new Rectangle(10, 184, 120, 20));
      this.jButton1B.setForeground(this.color);
      this.grupoBotones1.add(this.jButton1B);
      this.jButton1F.setBounds(new Rectangle(10, 204, 120, 20));
      this.jButton1F.setForeground(this.color);
      this.grupoBotones1.add(this.jButton1F);
      this.jButton2A.setBounds(new Rectangle(140, 84, 120, 20));
      this.jButton2A.setForeground(this.color);
      this.grupoBotones2.add(this.jButton2A);
      this.jButton2B.setBounds(new Rectangle(140, 104, 120, 20));
      this.jButton2B.setForeground(this.color);
      this.grupoBotones2.add(this.jButton2B);
      this.jButton2C.setBounds(new Rectangle(140, 124, 120, 20));
      this.jButton2C.setForeground(this.color);
      this.grupoBotones2.add(this.jButton2C);
      this.jButton2D.setBounds(new Rectangle(140, 144, 120, 20));
      this.jButton2D.setForeground(this.color);
      this.grupoBotones2.add(this.jButton2D);
      this.jButton2E.setBounds(new Rectangle(140, 164, 120, 20));
      this.jButton2E.setForeground(this.color);
      this.grupoBotones2.add(this.jButton2E);
      this.jButtonA.setBounds(new Rectangle(10, 243, 120, 30));
      this.jButtonA.setBorder(BorderFactory.createRaisedBevelBorder());
      this.jButtonA.setForeground(this.color);
      this.jButtonA.addActionListener(this);
      this.jButtonA.setActionCommand("jButtonA");
      this.jButtonC.setBounds(new Rectangle(140, 243, 120, 30));
      this.jButtonC.setBorder(BorderFactory.createRaisedBevelBorder());
      this.jButtonC.setForeground(this.color);
      this.jButtonC.addActionListener(this);
      this.jButtonC.setActionCommand("jButtonC");
      this.add(this.jTextField1, (Object)null);
      this.add(this.jTextField2, (Object)null);
      this.add(this.jButton1M, (Object)null);
      this.add(this.jButton1Y, (Object)null);
      this.add(this.jButton1C, (Object)null);
      this.add(this.jButton1R, (Object)null);
      this.add(this.jButton1G, (Object)null);
      this.add(this.jButton1B, (Object)null);
      this.add(this.jButton1F, (Object)null);
      this.add(this.jButton2E, (Object)null);
      this.add(this.jButton2A, (Object)null);
      this.add(this.jButton2B, (Object)null);
      this.add(this.jButton2C, (Object)null);
      this.add(this.jButton2D, (Object)null);
      this.add(this.jButtonA, (Object)null);
      this.add(this.jButtonC, (Object)null);
   }

   public void actionPerformed(ActionEvent var1) {
      String var2 = var1.getActionCommand();
      if (var2 == "jButtonA") {
         byte var3 = 2;
         byte var4 = 4;
         if (this.jButton1M.isSelected()) {
            var3 = 0;
         }

         if (this.jButton1Y.isSelected()) {
            var3 = 1;
         }

         if (this.jButton1C.isSelected()) {
            var3 = 2;
         }

         if (this.jButton1R.isSelected()) {
            var3 = 3;
         }

         if (this.jButton1G.isSelected()) {
            var3 = 4;
         }

         if (this.jButton1B.isSelected()) {
            var3 = 5;
         }

         if (this.jButton1F.isSelected()) {
            var3 = 6;
         }

         if (this.jButton2A.isSelected()) {
            var4 = 0;
         }

         if (this.jButton2B.isSelected()) {
            var4 = 1;
         }

         if (this.jButton2C.isSelected()) {
            var4 = 2;
         }

         if (this.jButton2D.isSelected()) {
            var4 = 3;
         }

         if (this.jButton2E.isSelected()) {
            var4 = 4;
         }

         if (var3 == 6) {
            FileDialog var5 = new FileDialog(new Frame(), "Selector");
            var5.setForeground(Color.blue);
            var5.setFont(this.fuente2);
            var5.show();
            String var6 = var5.getFile();
            String var7 = var5.getDirectory();
            String var8 = var7 + var6;
            if (var7 == null || var6 == null) {
               Colores.panelBase.remove(Colores.panelElegir2);
               Colores.panelBase.repaint();
               return;
            }

            boolean var10 = false;
            double[] var12 = new double[2];

            int var9;
            try {
               FileReader var13 = new FileReader(var8);
               BufferedReader var14 = new BufferedReader(var13);
               int var20 = 0;

               while(true) {
                  var9 = Varios.leeLineaAscii(var14, var12, 2);
                  if (var9 == -1) {
                     var14.close();
                     Colores.espectro[6].numDatosEspectro = var20;
                     Colores.panelBase.remove(Colores.panelElegir2);
                     Colores.panelBase.repaint();
                     break;
                  }

                  if (var9 == -2) {
                     var14.close();
                     Colores.panelBase.remove(Colores.panelElegir2);
                     Colores.panelBase.repaint();
                     return;
                  }

                  int var11 = (int)var12[0];
                  if (var11 >= 400 && var11 <= 700) {
                     Colores.espectro[6].datoColorL[var20] = (int)var12[0];
                     Colores.espectro[6].datoColorY[var20] = var12[1];
                     ++var20;
                  }
               }
            } catch (IOException var19) {
               Colores.panelBase.remove(Colores.panelElegir2);
               Colores.panelBase.repaint();
               return;
            }

            double var21 = 0.0D;
            double var15 = 1.0D;

            for(var9 = 0; var9 < Colores.espectro[6].numDatosEspectro; ++var9) {
               if (Colores.espectro[6].datoColorY[var9] > var21) {
                  var21 = Colores.espectro[6].datoColorY[var9];
               }

               if (Colores.espectro[6].datoColorY[var9] < var15) {
                  var15 = Colores.espectro[6].datoColorY[var9];
               }
            }

            if (var21 < 1.0D) {
               var21 = 1.0D;
            }

            var15 -= 0.01D;
            double var17 = var21 - var15;

            for(var9 = 0; var9 < Colores.espectro[6].numDatosEspectro; ++var9) {
               Colores.espectro[6].datoColorY[var9] = (Colores.espectro[6].datoColorY[var9] - var15) / var17;
            }
         }

         Colores.panelBase.remove(Colores.panelElegir2);
         Colores.panelBase.add(Colores.productoIF);
         ProductoIF.calculaDatosProducto(var4, var3);
         Colores.panelBase.repaint();
      }

      if (var2 == "jButtonC") {
         Colores.panelBase.remove(Colores.panelElegir2);
         Colores.panelBase.repaint();
      }

   }
}
