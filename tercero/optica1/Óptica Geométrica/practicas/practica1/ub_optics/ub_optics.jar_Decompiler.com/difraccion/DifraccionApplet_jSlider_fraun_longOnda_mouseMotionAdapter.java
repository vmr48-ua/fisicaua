package difraccion;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class DifraccionApplet_jSlider_fraun_longOnda_mouseMotionAdapter extends MouseMotionAdapter {
   DifraccionApplet adaptee;

   DifraccionApplet_jSlider_fraun_longOnda_mouseMotionAdapter(DifraccionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseDragged(MouseEvent e) {
      this.adaptee.jSlider_fraun_longOnda_mouseDragged(e);
   }
}
