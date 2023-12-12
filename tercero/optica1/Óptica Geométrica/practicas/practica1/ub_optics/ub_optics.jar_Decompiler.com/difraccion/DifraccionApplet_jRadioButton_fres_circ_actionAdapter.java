package difraccion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DifraccionApplet_jRadioButton_fres_circ_actionAdapter implements ActionListener {
   DifraccionApplet adaptee;

   DifraccionApplet_jRadioButton_fres_circ_actionAdapter(DifraccionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jRadioButton_fres_circ_actionPerformed(e);
   }
}
