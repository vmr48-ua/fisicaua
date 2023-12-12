package appletfibra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AppletFibra_jButtonAfegir_actionAdapter implements ActionListener {
   AppletFibra adaptee;

   AppletFibra_jButtonAfegir_actionAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jButtonAfegir_actionPerformed(e);
   }
}
