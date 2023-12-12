package difraccion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class DifraccionApplet_jSlider_fraun_nobjetos_mouseAdapter extends MouseAdapter {
   DifraccionApplet adaptee;

   DifraccionApplet_jSlider_fraun_nobjetos_mouseAdapter(DifraccionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseClicked(MouseEvent e) {
      this.adaptee.jSlider_fraun_nobjetos_mouseClicked(e);
   }
}
