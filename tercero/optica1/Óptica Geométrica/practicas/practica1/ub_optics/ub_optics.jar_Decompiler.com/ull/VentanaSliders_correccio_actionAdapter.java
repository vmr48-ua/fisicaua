package ull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class VentanaSliders_correccio_actionAdapter implements ActionListener {
   VentanaSliders adaptee;

   VentanaSliders_correccio_actionAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.correccio_actionPerformed(e);
   }
}
