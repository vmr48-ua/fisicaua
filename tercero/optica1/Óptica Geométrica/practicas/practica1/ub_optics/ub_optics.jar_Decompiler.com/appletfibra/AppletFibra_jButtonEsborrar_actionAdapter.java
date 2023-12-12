package appletfibra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AppletFibra_jButtonEsborrar_actionAdapter implements ActionListener {
   AppletFibra adaptee;

   AppletFibra_jButtonEsborrar_actionAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jButtonEsborrar_actionPerformed(e);
   }
}
