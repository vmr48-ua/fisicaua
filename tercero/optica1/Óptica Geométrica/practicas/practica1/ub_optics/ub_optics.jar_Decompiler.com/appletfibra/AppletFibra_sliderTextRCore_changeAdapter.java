package appletfibra;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class AppletFibra_sliderTextRCore_changeAdapter implements ChangeListener {
   AppletFibra adaptee;

   AppletFibra_sliderTextRCore_changeAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void stateChanged(ChangeEvent e) {
      this.adaptee.sliderTextRCore_stateChanged(e);
   }
}
