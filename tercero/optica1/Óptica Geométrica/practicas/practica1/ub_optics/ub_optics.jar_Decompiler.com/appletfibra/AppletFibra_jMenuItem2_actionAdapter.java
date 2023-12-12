package appletfibra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AppletFibra_jMenuItem2_actionAdapter implements ActionListener {
   AppletFibra adaptee;

   AppletFibra_jMenuItem2_actionAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jMenuItem2_actionPerformed(e);
   }
}
