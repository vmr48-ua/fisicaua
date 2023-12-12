package dispersion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DispersionApplet_comboBox_p_material_actionAdapter implements ActionListener {
   DispersionApplet adaptee;

   DispersionApplet_comboBox_p_material_actionAdapter(DispersionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.comboBox_p_material_actionPerformed(e);
   }
}
