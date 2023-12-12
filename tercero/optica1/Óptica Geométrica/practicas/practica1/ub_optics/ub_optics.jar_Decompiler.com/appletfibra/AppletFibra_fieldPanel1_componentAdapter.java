package appletfibra;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class AppletFibra_fieldPanel1_componentAdapter extends ComponentAdapter {
   AppletFibra adaptee;

   AppletFibra_fieldPanel1_componentAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void componentShown(ComponentEvent e) {
      this.adaptee.fieldPanel1_componentShown(e);
   }
}
