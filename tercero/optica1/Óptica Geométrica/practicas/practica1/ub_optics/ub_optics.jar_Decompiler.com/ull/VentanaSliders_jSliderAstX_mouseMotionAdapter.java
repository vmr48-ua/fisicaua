package ull;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class VentanaSliders_jSliderAstX_mouseMotionAdapter extends MouseMotionAdapter {
   VentanaSliders adaptee;

   VentanaSliders_jSliderAstX_mouseMotionAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseDragged(MouseEvent e) {
      this.adaptee.jSliderAstX_mouseDragged(e);
   }
}
