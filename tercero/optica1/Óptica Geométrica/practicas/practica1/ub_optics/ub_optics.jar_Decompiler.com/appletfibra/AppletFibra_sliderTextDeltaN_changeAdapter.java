package appletfibra;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class AppletFibra_sliderTextDeltaN_changeAdapter implements ChangeListener {
   AppletFibra adaptee;

   AppletFibra_sliderTextDeltaN_changeAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void stateChanged(ChangeEvent e) {
      this.adaptee.sliderTextDeltaN_stateChanged(e);
   }
}
