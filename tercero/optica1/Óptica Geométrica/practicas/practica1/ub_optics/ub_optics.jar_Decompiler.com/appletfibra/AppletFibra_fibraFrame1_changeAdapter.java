package appletfibra;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class AppletFibra_fibraFrame1_changeAdapter implements ChangeListener {
   AppletFibra adaptee;

   AppletFibra_fibraFrame1_changeAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void stateChanged(ChangeEvent e) {
      this.adaptee.fibraFrame1_stateChanged(e);
   }
}
