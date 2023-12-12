package appletfibra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AppletFibra_jMenuItem1_actionAdapter implements ActionListener {
   AppletFibra adaptee;

   AppletFibra_jMenuItem1_actionAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jMenuItem1_actionPerformed(e);
   }
}
