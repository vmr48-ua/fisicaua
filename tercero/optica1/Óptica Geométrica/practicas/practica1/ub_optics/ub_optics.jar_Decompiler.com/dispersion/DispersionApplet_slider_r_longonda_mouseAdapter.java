package dispersion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class DispersionApplet_slider_r_longonda_mouseAdapter extends MouseAdapter {
   DispersionApplet adaptee;

   DispersionApplet_slider_r_longonda_mouseAdapter(DispersionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseClicked(MouseEvent e) {
      this.adaptee.slider_r_longonda_mouseClicked(e);
   }
}
