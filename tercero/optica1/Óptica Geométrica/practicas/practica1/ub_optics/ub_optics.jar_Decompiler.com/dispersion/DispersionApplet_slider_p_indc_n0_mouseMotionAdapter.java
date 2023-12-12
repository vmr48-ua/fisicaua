package dispersion;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class DispersionApplet_slider_p_indc_n0_mouseMotionAdapter extends MouseMotionAdapter {
   DispersionApplet adaptee;

   DispersionApplet_slider_p_indc_n0_mouseMotionAdapter(DispersionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseDragged(MouseEvent e) {
      this.adaptee.slider_p_indc_n0_mouseDragged(e);
   }
}
