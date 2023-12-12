package appletfibra;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class AppletFibra_jLabelVinfo_propertyChangeAdapter implements PropertyChangeListener {
   AppletFibra adaptee;

   AppletFibra_jLabelVinfo_propertyChangeAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void propertyChange(PropertyChangeEvent e) {
      this.adaptee.jLabelVinfo_propertyChange(e);
   }
}
