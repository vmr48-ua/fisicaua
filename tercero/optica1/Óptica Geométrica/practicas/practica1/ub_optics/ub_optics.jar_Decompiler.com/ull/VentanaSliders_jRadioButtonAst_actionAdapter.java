package ull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class VentanaSliders_jRadioButtonAst_actionAdapter implements ActionListener {
   VentanaSliders adaptee;

   VentanaSliders_jRadioButtonAst_actionAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jRadioButtonAst_actionPerformed(e);
   }
}
