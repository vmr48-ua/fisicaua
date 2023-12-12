package appletfibra;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AppletFibra_jPanelFibra1_mouseAdapter extends MouseAdapter {
   AppletFibra adaptee;

   AppletFibra_jPanelFibra1_mouseAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseClicked(MouseEvent e) {
      this.adaptee.jPanelFibra1_mouseClicked(e);
   }
}
