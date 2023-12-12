package appletfibra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AppletFibra_jComboBoxMode1_actionAdapter implements ActionListener {
   AppletFibra adaptee;

   AppletFibra_jComboBoxMode1_actionAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jComboBoxMode1_actionPerformed(e);
   }
}
