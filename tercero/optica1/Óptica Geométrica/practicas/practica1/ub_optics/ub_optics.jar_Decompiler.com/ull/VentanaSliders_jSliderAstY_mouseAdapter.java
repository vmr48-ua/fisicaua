package ull;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class VentanaSliders_jSliderAstY_mouseAdapter extends MouseAdapter {
   VentanaSliders adaptee;

   VentanaSliders_jSliderAstY_mouseAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseClicked(MouseEvent e) {
      this.adaptee.jSliderAstY_mouseClicked(e);
   }
}
