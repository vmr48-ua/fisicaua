package dispersion;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class DispersionApplet_slider_p_longonda5_keyAdapter extends KeyAdapter {
   DispersionApplet adaptee;

   DispersionApplet_slider_p_longonda5_keyAdapter(DispersionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void keyPressed(KeyEvent e) {
      this.adaptee.slider_p_longonda5_keyPressed(e);
   }

   public void keyTyped(KeyEvent e) {
      this.adaptee.slider_p_longonda5_keyTyped(e);
   }
}
