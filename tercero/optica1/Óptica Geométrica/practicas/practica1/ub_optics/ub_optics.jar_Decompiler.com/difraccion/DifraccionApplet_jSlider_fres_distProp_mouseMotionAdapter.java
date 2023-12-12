package difraccion;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class DifraccionApplet_jSlider_fres_distProp_mouseMotionAdapter extends MouseMotionAdapter {
   DifraccionApplet adaptee;

   DifraccionApplet_jSlider_fres_distProp_mouseMotionAdapter(DifraccionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseDragged(MouseEvent e) {
      this.adaptee.jSlider_fres_distProp_mouseDragged(e);
   }
}
