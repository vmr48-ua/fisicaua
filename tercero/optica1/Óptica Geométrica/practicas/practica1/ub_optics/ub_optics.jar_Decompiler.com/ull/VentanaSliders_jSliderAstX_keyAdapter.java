package ull;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class VentanaSliders_jSliderAstX_keyAdapter extends KeyAdapter {
   VentanaSliders adaptee;

   VentanaSliders_jSliderAstX_keyAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void keyReleased(KeyEvent e) {
      this.adaptee.jSliderAstX_keyReleased(e);
   }

   public void keyTyped(KeyEvent e) {
      this.adaptee.jSliderAstX_keyTyped(e);
   }
}
