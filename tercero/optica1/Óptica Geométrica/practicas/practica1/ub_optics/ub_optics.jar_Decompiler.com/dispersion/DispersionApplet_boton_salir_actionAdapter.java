package dispersion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DispersionApplet_boton_salir_actionAdapter implements ActionListener {
   DispersionApplet adaptee;

   DispersionApplet_boton_salir_actionAdapter(DispersionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.boton_salir_actionPerformed(e);
   }
}
