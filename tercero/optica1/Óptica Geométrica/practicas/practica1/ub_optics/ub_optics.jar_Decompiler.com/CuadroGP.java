import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

class CuadroGP extends JPanel {
   DatosEspectro colorGrafica;

   CuadroGP(DatosEspectro var1) {
      this.setBorder(BorderFactory.createLoweredBevelBorder());
      this.colorGrafica = var1;
   }

   public void paint(Graphics var1) {
      this.paintComponent(var1);
      this.paintBorder(var1);
      ProductoIF.dibujaGrafica(var1, this.colorGrafica);
   }
}
