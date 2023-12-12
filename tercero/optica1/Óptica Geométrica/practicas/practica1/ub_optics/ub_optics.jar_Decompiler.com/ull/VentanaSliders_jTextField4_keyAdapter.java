package ull;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class VentanaSliders_jTextField4_keyAdapter extends KeyAdapter {
   VentanaSliders adaptee;

   VentanaSliders_jTextField4_keyAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void keyTyped(KeyEvent e) {
      this.adaptee.jTextField4_keyTyped(e);
   }
}
