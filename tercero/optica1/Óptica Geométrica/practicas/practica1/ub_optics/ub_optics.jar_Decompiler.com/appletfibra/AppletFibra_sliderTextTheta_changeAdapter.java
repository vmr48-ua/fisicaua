package appletfibra;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class AppletFibra_sliderTextTheta_changeAdapter implements ChangeListener {
   AppletFibra adaptee;

   AppletFibra_sliderTextTheta_changeAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void stateChanged(ChangeEvent e) {
      this.adaptee.sliderTextTheta_stateChanged(e);
   }
}
