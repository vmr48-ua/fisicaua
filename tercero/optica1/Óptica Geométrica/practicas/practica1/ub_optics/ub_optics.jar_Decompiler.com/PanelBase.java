import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.JPanel;

class PanelBase extends JPanel {
   public PanelBase() {
      this.setLayout((LayoutManager)null);
      this.setBounds(0, 25, 270, Colores.alto - 25);
      this.setBackground(Color.gray);
   }
}
