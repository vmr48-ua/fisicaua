package appletfibra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AppletFibra_jButtonEditar_actionAdapter implements ActionListener {
   AppletFibra adaptee;

   AppletFibra_jButtonEditar_actionAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jButtonEditar_actionPerformed(e);
   }
}
