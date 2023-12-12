package appletfibra;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AppletFibra_jRadioButtonR5_mouseAdapter extends MouseAdapter {
   AppletFibra adaptee;

   AppletFibra_jRadioButtonR5_mouseAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseClicked(MouseEvent e) {
      this.adaptee.jRadioButtonR5_mouseClicked(e);
   }

   public void mousePressed(MouseEvent e) {
      this.adaptee.jRadioButtonR5_mousePressed(e);
   }
}
