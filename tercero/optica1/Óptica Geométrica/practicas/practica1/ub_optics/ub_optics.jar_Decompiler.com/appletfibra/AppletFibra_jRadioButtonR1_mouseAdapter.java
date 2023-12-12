package appletfibra;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AppletFibra_jRadioButtonR1_mouseAdapter extends MouseAdapter {
   AppletFibra adaptee;

   AppletFibra_jRadioButtonR1_mouseAdapter(AppletFibra adaptee) {
      this.adaptee = adaptee;
   }

   public void mousePressed(MouseEvent e) {
      this.adaptee.jRadioButtonR1_mousePressed(e);
   }

   public void mouseClicked(MouseEvent e) {
      this.adaptee.jRadioButtonR1_mouseClicked(e);
   }
}
