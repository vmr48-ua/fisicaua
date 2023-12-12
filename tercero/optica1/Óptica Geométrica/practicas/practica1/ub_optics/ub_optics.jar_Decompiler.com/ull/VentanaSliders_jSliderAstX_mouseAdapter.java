package ull;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class VentanaSliders_jSliderAstX_mouseAdapter extends MouseAdapter {
   VentanaSliders adaptee;

   VentanaSliders_jSliderAstX_mouseAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseClicked(MouseEvent e) {
      this.adaptee.jSliderAstX_mouseClicked(e);
   }
}
