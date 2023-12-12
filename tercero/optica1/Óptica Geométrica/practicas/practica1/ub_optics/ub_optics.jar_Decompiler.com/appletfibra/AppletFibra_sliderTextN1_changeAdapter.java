package appletfibra;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class AppletFibra_sliderTextN1_changeAdapter implements ChangeListener {
   AppletFibra adaptee;

   AppletFibra_sliderTextN1_changeAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void stateChanged(ChangeEvent e) {
      this.adaptee.sliderTextN1_stateChanged(e);
   }
}
