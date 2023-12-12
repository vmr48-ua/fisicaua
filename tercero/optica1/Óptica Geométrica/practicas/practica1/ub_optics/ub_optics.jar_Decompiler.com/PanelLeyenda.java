import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class PanelLeyenda extends JPanel implements ActionListener {
   public PanelLeyenda() {
      this.setSize(Sistema.ancho, Sistema.altoLeyenda);
      this.setLayout((LayoutManager)null);
      Font var1 = new Font("Helvetica", 1, 24);
      JLabel var2 = new JLabel(Sistema.text[0][Sistema.lang]);
      var2.setBounds(250, 0, 400, 40);
      var2.setBackground(Color.white);
      var2.setForeground(Color.black);
      var2.setFont(var1);
      Font var3 = new Font("Dialog", 1, 12);
      JButton var4 = new JButton(Sistema.text[5][Sistema.lang]);
      var4.setBounds(680, 10, 95, 22);
      var4.setForeground(Color.black);
      var4.setFont(var3);
      var4.addActionListener(this);
      var4.setActionCommand("buttonAcerca");
      this.add(var4);
   }

   public void actionPerformed(ActionEvent var1) {
      double[] var4 = new double[8];
      String var5 = var1.getActionCommand();
      if (var5 == "buttonAcerca") {
         JFrame var6 = new JFrame();
         Object[] var7 = new Object[]{Sistema.acerca_etiqueta[0][Sistema.lang]};
         Object var8 = null;
         JOptionPane var9 = new JOptionPane(Sistema.acerca_etiqueta[1][Sistema.lang], 1, -1, (Icon)var8, var7);
         JDialog var10 = var9.createDialog(var6, Sistema.text[5][Sistema.lang]);
         var10.setResizable(false);
         var10.show();
      }

   }
}
