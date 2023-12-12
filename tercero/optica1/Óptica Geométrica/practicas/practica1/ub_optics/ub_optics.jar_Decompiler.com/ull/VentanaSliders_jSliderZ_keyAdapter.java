package ull;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class VentanaSliders_jSliderZ_keyAdapter extends KeyAdapter {
   VentanaSliders adaptee;

   VentanaSliders_jSliderZ_keyAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void keyReleased(KeyEvent e) {
      this.adaptee.jSliderZ_keyReleased(e);
   }

   public void keyTyped(KeyEvent e) {
      this.adaptee.jSliderZ_keyTyped(e);
   }
}
