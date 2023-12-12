package ull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class VentanaSliders_jRadioButtonAmet_actionAdapter implements ActionListener {
   VentanaSliders adaptee;

   VentanaSliders_jRadioButtonAmet_actionAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jRadioButtonAmet_actionPerformed(e);
   }
}
