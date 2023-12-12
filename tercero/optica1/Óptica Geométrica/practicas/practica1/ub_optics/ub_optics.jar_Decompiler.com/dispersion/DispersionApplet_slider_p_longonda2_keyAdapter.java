package dispersion;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class DispersionApplet_slider_p_longonda2_keyAdapter extends KeyAdapter {
   DispersionApplet adaptee;

   DispersionApplet_slider_p_longonda2_keyAdapter(DispersionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void keyPressed(KeyEvent e) {
      this.adaptee.slider_p_longonda2_keyPressed(e);
   }

   public void keyTyped(KeyEvent e) {
      this.adaptee.slider_p_longonda2_keyTyped(e);
   }
}
