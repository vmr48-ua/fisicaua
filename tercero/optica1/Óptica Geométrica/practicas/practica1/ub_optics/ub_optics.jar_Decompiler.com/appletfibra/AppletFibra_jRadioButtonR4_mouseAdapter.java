package appletfibra;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AppletFibra_jRadioButtonR4_mouseAdapter extends MouseAdapter {
   AppletFibra adaptee;

   AppletFibra_jRadioButtonR4_mouseAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseClicked(MouseEvent e) {
      this.adaptee.jRadioButtonR4_mouseClicked(e);
   }

   public void mousePressed(MouseEvent e) {
      this.adaptee.jRadioButtonR4_mousePressed(e);
   }
}
