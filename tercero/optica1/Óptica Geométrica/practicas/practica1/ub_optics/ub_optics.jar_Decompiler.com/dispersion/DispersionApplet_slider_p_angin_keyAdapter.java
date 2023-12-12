package dispersion;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class DispersionApplet_slider_p_angin_keyAdapter extends KeyAdapter {
   DispersionApplet adaptee;

   DispersionApplet_slider_p_angin_keyAdapter(DispersionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void keyTyped(KeyEvent e) {
      this.adaptee.slider_p_angin_keyTyped(e);
   }

   public void keyPressed(KeyEvent e) {
      this.adaptee.slider_p_angin_keyPressed(e);
   }
}
