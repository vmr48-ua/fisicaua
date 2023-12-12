package ull;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class VentanaSliders_jSliderZ_mouseMotionAdapter extends MouseMotionAdapter {
   VentanaSliders adaptee;

   VentanaSliders_jSliderZ_mouseMotionAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseDragged(MouseEvent e) {
      this.adaptee.jSliderZ_mouseDragged(e);
   }
}
