package ull;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class VentanaSliders_jSliderAstY_keyAdapter extends KeyAdapter {
   VentanaSliders adaptee;

   VentanaSliders_jSliderAstY_keyAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void keyReleased(KeyEvent e) {
      this.adaptee.jSliderAstY_keyReleased(e);
   }

   public void keyTyped(KeyEvent e) {
      this.adaptee.jSliderAstY_keyTyped(e);
   }
}
