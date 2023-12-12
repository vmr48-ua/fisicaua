import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class PanelElegir1 extends JPanel implements ActionListener {
   String[] textoI = new String[]{"Il·luminant", "Iluminante", "Illuminant"};
   String[] textoAcept = new String[]{"Acceptar", "Aceptar", "Accept"};
   String[] textoCanc = new String[]{"Cancelar", "Cancelar", "Cancel"};
   Font fuente1;
   Font fuente2;
   Color color;
   JTextField jTextField;
   ButtonGroup grupoBotones;
   JToggleButton jButtonA;
   JToggleButton jButtonB;
   JToggleButton jButtonC;
   JToggleButton jButtonD;
   JToggleButton jButtonE;
   JButton jButtonAcept;
   JButton jButtonCanc;

   public PanelElegir1() {
      this.fuente1 = Colores.fuente1;
      this.fuente2 = Colores.fuente2;
      this.color = Colores.color;
      this.jTextField = new JTextField(this.textoI[Colores.lang]);
      this.grupoBotones = new ButtonGroup();
      this.jButtonA = new JToggleButton("A");
      this.jButtonB = new JToggleButton("B");
      this.jButtonC = new JToggleButton("C", true);
      this.jButtonD = new JToggleButton("D");
      this.jButtonE = new JToggleButton("E");
      this.jButtonAcept = new JButton(this.textoAcept[Colores.lang]);
      this.jButtonCanc = new JButton(this.textoCanc[Colores.lang]);
      this.setLayout((LayoutManager)null);
      this.setBounds(0, 0, 270, 500);
      this.jTextField.setFont(this.fuente1);
      this.jTextField.setForeground(this.color);
      this.jTextField.setBackground(Color.white);
      this.jTextField.setBorder(BorderFactory.createRaisedBevelBorder());
      this.jTextField.setHorizontalAlignment(0);
      this.jTextField.setBounds(new Rectangle(10, 30, 110, 40));
      this.jTextField.setEditable(false);
      this.jButtonA.setBounds(new Rectangle(10, 84, 110, 20));
      this.jButtonA.setForeground(this.color);
      this.grupoBotones.add(this.jButtonA);
      this.jButtonB.setBounds(new Rectangle(10, 104, 110, 20));
      this.jButtonB.setForeground(this.color);
      this.grupoBotones.add(this.jButtonB);
      this.jButtonC.setBounds(new Rectangle(10, 124, 110, 20));
      this.jButtonC.setForeground(this.color);
      this.grupoBotones.add(this.jButtonC);
      this.jButtonD.setBounds(new Rectangle(10, 144, 110, 20));
      this.jButtonD.setForeground(this.color);
      this.grupoBotones.add(this.jButtonD);
      this.jButtonE.setBounds(new Rectangle(10, 164, 110, 20));
      this.jButtonE.setForeground(this.color);
      this.grupoBotones.add(this.jButtonE);
      this.jButtonAcept.setBounds(new Rectangle(10, 223, 110, 30));
      this.jButtonAcept.setBorder(BorderFactory.createRaisedBevelBorder());
      this.jButtonAcept.setForeground(this.color);
      this.jButtonAcept.addActionListener(this);
      this.jButtonAcept.setActionCommand("Aceptar");
      this.jButtonCanc.setBounds(new Rectangle(140, 223, 110, 30));
      this.jButtonCanc.setBorder(BorderFactory.createRaisedBevelBorder());
      this.jButtonCanc.setForeground(this.color);
      this.jButtonCanc.addActionListener(this);
      this.jButtonCanc.setActionCommand("Cancelar");
      this.add(this.jTextField, (Object)null);
      this.add(this.jButtonA, (Object)null);
      this.add(this.jButtonB, (Object)null);
      this.add(this.jButtonC, (Object)null);
      this.add(this.jButtonD, (Object)null);
      this.add(this.jButtonE, (Object)null);
      this.add(this.jButtonAcept, (Object)null);
      this.add(this.jButtonCanc, (Object)null);
   }

   public void actionPerformed(ActionEvent var1) {
      String var2 = var1.getActionCommand();
      if (var2 == "Aceptar") {
         byte var3 = 2;
         if (this.jButtonA.isSelected()) {
            var3 = 0;
         }

         if (this.jButtonB.isSelected()) {
            var3 = 1;
         }

         if (this.jButtonC.isSelected()) {
            var3 = 2;
         }

         if (this.jButtonD.isSelected()) {
            var3 = 3;
         }

         if (this.jButtonE.isSelected()) {
            var3 = 4;
         }

         Colores.panelBase.remove(Colores.panelElegir1);
         Colores.panelBase.repaint();
         Colores.panelBase.add(Colores.restaColores);
         RestaColores.numI = var3;
         RestaColores.calculaTodo(0);
      }

      if (var2 == "Cancelar") {
         Colores.panelBase.remove(Colores.panelElegir1);
         Colores.panelBase.repaint();
      }

   }
}
