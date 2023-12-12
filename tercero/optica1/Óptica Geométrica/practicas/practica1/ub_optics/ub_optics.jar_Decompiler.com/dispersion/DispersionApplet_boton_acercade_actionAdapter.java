package dispersion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DispersionApplet_boton_acercade_actionAdapter implements ActionListener {
   DispersionApplet adaptee;

   DispersionApplet_boton_acercade_actionAdapter(DispersionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.boton_acercade_actionPerformed(e);
   }
}
