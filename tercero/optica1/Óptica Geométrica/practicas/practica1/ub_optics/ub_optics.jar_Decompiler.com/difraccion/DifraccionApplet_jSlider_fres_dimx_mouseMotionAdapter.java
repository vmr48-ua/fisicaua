package difraccion;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class DifraccionApplet_jSlider_fres_dimx_mouseMotionAdapter extends MouseMotionAdapter {
   DifraccionApplet adaptee;

   DifraccionApplet_jSlider_fres_dimx_mouseMotionAdapter(DifraccionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseDragged(MouseEvent e) {
      this.adaptee.jSlider_fres_dimx_mouseDragged(e);
   }
}
