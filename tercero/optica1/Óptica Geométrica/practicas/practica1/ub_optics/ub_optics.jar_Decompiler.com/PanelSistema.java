import java.awt.Font;
import java.awt.LayoutManager;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

class PanelSistema extends JPanel {
   SistemaFL sistemaFL;
   SistemaFC sistemaFC;

   public PanelSistema() {
      this.setLayout((LayoutManager)null);
      new Font("Helvetica", 1, 24);
      new Font("Helvetica", 1, 14);
      JTabbedPane var3 = new JTabbedPane();
      this.sistemaFL = new SistemaFL();
      this.sistemaFC = new SistemaFC();
      var3.addTab(Sistema.text[3][Sistema.lang], (Icon)null, this.sistemaFC, Sistema.text[4][Sistema.lang]);
      var3.addTab(Sistema.text[1][Sistema.lang], (Icon)null, this.sistemaFL, Sistema.text[2][Sistema.lang]);
      var3.setSize(Sistema.ancho, Sistema.altoDibujo);
      this.add(var3);
      var3.setSelectedIndex(1);
   }
}
