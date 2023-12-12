package appletfibra;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class AppletFibra_this_componentAdapter extends ComponentAdapter {
   AppletFibra adaptee;

   AppletFibra_this_componentAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void componentShown(ComponentEvent e) {
      this.adaptee.this_componentShown(e);
   }
}
