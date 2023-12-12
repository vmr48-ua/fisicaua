package ull;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class VentanaSliders_jTextField2_keyAdapter extends KeyAdapter {
   VentanaSliders adaptee;

   VentanaSliders_jTextField2_keyAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void keyTyped(KeyEvent e) {
      this.adaptee.jTextField2_keyTyped(e);
   }
}
