import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.JLabel;
import javax.swing.JPanel;

class PanelDiagrama extends JPanel {
   JLabel label;

   public PanelDiagrama() {
      this.setLayout((LayoutManager)null);
      this.setBounds(270, 25, Colores.ancho - 270, Colores.alto - 25);
      this.setBackground(Color.gray);
      this.label = new JLabel(Colores.texto0[Colores.lang]);
      this.label.setBounds(80, 0, 400, 25);
      this.label.setForeground(Color.white);
      this.add(this.label);
   }
}
