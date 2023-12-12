package appletfibra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AppletFibra_jButtonExit_actionAdapter implements ActionListener {
   AppletFibra adaptee;

   AppletFibra_jButtonExit_actionAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jButtonExit_actionPerformed(e);
   }
}
