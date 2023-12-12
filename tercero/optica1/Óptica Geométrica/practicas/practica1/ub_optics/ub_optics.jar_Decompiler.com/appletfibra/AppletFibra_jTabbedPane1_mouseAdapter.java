package appletfibra;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AppletFibra_jTabbedPane1_mouseAdapter extends MouseAdapter {
   AppletFibra adaptee;

   AppletFibra_jTabbedPane1_mouseAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseClicked(MouseEvent e) {
      this.adaptee.jTabbedPane1_mouseClicked(e);
   }
}
