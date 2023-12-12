package ull;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class VentanaSliders_jSliderAstY_mouseMotionAdapter extends MouseMotionAdapter {
   VentanaSliders adaptee;

   VentanaSliders_jSliderAstY_mouseMotionAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseDragged(MouseEvent e) {
      this.adaptee.jSliderAstY_mouseDragged(e);
   }
}
