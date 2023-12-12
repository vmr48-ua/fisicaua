package appletfibra;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class AppletFibra_jListModes1_listSelectionAdapter implements ListSelectionListener {
   AppletFibra adaptee;

   AppletFibra_jListModes1_listSelectionAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void valueChanged(ListSelectionEvent e) {
      this.adaptee.jListModes1_valueChanged(e);
   }
}
