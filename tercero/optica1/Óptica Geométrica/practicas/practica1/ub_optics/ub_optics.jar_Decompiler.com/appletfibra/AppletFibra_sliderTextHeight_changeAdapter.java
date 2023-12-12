package appletfibra;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class AppletFibra_sliderTextHeight_changeAdapter implements ChangeListener {
   AppletFibra adaptee;

   AppletFibra_sliderTextHeight_changeAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void stateChanged(ChangeEvent e) {
      this.adaptee.sliderTextHeight_stateChanged(e);
   }
}
