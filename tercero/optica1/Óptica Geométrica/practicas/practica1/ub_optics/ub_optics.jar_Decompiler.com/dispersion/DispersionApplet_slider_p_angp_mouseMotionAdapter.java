package dispersion;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class DispersionApplet_slider_p_angp_mouseMotionAdapter extends MouseMotionAdapter {
   DispersionApplet adaptee;

   DispersionApplet_slider_p_angp_mouseMotionAdapter(DispersionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseDragged(MouseEvent e) {
      this.adaptee.slider_p_angp_mouseDragged(e);
   }
}
