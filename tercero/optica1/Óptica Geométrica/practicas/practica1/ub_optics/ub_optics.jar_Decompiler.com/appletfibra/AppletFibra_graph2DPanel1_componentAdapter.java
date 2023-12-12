package appletfibra;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class AppletFibra_graph2DPanel1_componentAdapter extends ComponentAdapter {
   AppletFibra adaptee;

   AppletFibra_graph2DPanel1_componentAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void componentShown(ComponentEvent e) {
      this.adaptee.graph2DPanel1_componentShown(e);
   }
}
