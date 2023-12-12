import java.awt.LayoutManager;
import javax.swing.JPanel;

class PanelCompleto extends JPanel {
   public PanelCompleto() {
      this.setLayout((LayoutManager)null);
      Sistema.panelLeyenda = new PanelLeyenda();
      Sistema.panelLeyenda.setBounds(0, 0, Sistema.ancho, Sistema.altoLeyenda);
      this.add(Sistema.panelLeyenda);
      Sistema.panelSistema = new PanelSistema();
      this.add(Sistema.panelSistema);
      Sistema.panelSistema.setBounds(0, Sistema.altoLeyenda, Sistema.ancho, Sistema.altoDibujo);
   }
}
