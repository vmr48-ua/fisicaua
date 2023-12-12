package dispersion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class DispersionApplet_slider_p_indc_n0_mouseAdapter extends MouseAdapter {
   DispersionApplet adaptee;

   DispersionApplet_slider_p_indc_n0_mouseAdapter(DispersionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseClicked(MouseEvent e) {
      this.adaptee.slider_p_indc_n0_mouseClicked(e);
   }
}
