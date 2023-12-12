package difraccion;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class DifraccionApplet_jSlider_fraun_nobjetos_keyAdapter extends KeyAdapter {
   DifraccionApplet adaptee;

   DifraccionApplet_jSlider_fraun_nobjetos_keyAdapter(DifraccionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void keyPressed(KeyEvent e) {
      this.adaptee.jSlider_fraun_nobjetos_keyPressed(e);
   }

   public void keyTyped(KeyEvent e) {
      this.adaptee.jSlider_fraun_nobjetos_keyTyped(e);
   }
}
