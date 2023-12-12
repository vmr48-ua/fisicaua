package appletfibra;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class AppletFibra_sliderTextLambda_changeAdapter implements ChangeListener {
   AppletFibra adaptee;

   AppletFibra_sliderTextLambda_changeAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void stateChanged(ChangeEvent e) {
      this.adaptee.sliderTextLambda_stateChanged(e);
   }
}
