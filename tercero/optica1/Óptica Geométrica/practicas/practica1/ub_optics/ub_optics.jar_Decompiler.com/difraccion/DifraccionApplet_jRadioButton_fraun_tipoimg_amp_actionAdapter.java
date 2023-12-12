package difraccion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DifraccionApplet_jRadioButton_fraun_tipoimg_amp_actionAdapter implements ActionListener {
   DifraccionApplet adaptee;

   DifraccionApplet_jRadioButton_fraun_tipoimg_amp_actionAdapter(DifraccionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jRadioButton_fraun_tipoimg_amp_actionPerformed(e);
   }
}
