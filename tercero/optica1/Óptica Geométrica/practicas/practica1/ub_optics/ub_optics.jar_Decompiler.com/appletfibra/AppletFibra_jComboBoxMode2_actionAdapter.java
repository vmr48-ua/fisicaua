package appletfibra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AppletFibra_jComboBoxMode2_actionAdapter implements ActionListener {
   AppletFibra adaptee;

   AppletFibra_jComboBoxMode2_actionAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jComboBoxMode2_actionPerformed(e);
   }
}
