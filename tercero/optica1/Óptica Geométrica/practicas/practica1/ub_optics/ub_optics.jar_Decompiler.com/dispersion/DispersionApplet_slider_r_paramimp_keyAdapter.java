package dispersion;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class DispersionApplet_slider_r_paramimp_keyAdapter extends KeyAdapter {
   DispersionApplet adaptee;

   DispersionApplet_slider_r_paramimp_keyAdapter(DispersionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void keyPressed(KeyEvent e) {
      this.adaptee.slider_r_paramimp_keyPressed(e);
   }

   public void keyReleased(KeyEvent e) {
      this.adaptee.slider_r_paramimp_keyReleased(e);
   }
}
