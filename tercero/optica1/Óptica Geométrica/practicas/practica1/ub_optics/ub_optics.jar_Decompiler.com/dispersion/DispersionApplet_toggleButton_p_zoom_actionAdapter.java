package dispersion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DispersionApplet_toggleButton_p_zoom_actionAdapter implements ActionListener {
   DispersionApplet adaptee;

   DispersionApplet_toggleButton_p_zoom_actionAdapter(DispersionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.toggleButton_p_zoom_actionPerformed(e);
   }
}
