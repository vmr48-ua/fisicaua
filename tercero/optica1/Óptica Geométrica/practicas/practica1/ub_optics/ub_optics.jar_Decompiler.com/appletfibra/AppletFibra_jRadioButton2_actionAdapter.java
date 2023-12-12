package appletfibra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AppletFibra_jRadioButton2_actionAdapter implements ActionListener {
   AppletFibra adaptee;

   AppletFibra_jRadioButton2_actionAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jRadioButton2_actionPerformed(e);
   }
}
