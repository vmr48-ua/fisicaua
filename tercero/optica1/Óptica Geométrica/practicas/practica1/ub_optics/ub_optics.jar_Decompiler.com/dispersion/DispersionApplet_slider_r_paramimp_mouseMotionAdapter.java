package dispersion;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class DispersionApplet_slider_r_paramimp_mouseMotionAdapter extends MouseMotionAdapter {
   DispersionApplet adaptee;

   DispersionApplet_slider_r_paramimp_mouseMotionAdapter(DispersionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseDragged(MouseEvent e) {
      this.adaptee.slider_r_paramimp_mouseDragged(e);
   }
}
