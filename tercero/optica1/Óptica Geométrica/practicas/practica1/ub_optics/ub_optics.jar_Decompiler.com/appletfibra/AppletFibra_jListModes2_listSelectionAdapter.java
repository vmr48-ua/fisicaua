package appletfibra;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class AppletFibra_jListModes2_listSelectionAdapter implements ListSelectionListener {
   AppletFibra adaptee;

   AppletFibra_jListModes2_listSelectionAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void valueChanged(ListSelectionEvent e) {
      this.adaptee.jListModes2_valueChanged(e);
   }
}
