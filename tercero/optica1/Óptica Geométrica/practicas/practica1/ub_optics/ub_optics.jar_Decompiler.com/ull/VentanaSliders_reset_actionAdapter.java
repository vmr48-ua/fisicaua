package ull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class VentanaSliders_reset_actionAdapter implements ActionListener {
   VentanaSliders adaptee;

   VentanaSliders_reset_actionAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.reset_actionPerformed(e);
   }
}
