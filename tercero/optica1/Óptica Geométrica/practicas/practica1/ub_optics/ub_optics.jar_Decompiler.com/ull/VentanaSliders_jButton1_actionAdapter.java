package ull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class VentanaSliders_jButton1_actionAdapter implements ActionListener {
   VentanaSliders adaptee;

   VentanaSliders_jButton1_actionAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jButton1_actionPerformed(e);
   }
}
