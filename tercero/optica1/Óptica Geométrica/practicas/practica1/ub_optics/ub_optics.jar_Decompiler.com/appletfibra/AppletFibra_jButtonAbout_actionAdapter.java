package appletfibra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AppletFibra_jButtonAbout_actionAdapter implements ActionListener {
   AppletFibra adaptee;

   AppletFibra_jButtonAbout_actionAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jButtonAbout_actionPerformed(e);
   }
}
