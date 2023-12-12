package difraccion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DifraccionApplet_jRadioButton_fres_semip_actionAdapter implements ActionListener {
   DifraccionApplet adaptee;

   DifraccionApplet_jRadioButton_fres_semip_actionAdapter(DifraccionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jRadioButton_fres_semip_actionPerformed(e);
   }
}
