package ull;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class VentanaSliders_jTextField3_keyAdapter extends KeyAdapter {
   VentanaSliders adaptee;

   VentanaSliders_jTextField3_keyAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void keyTyped(KeyEvent e) {
      this.adaptee.jTextField3_keyTyped(e);
   }
}
