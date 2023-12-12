package appletfibra;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class AppletFibra_sliderTextPhi_changeAdapter implements ChangeListener {
   AppletFibra adaptee;

   AppletFibra_sliderTextPhi_changeAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void stateChanged(ChangeEvent e) {
      this.adaptee.sliderTextPhi_stateChanged(e);
   }
}
