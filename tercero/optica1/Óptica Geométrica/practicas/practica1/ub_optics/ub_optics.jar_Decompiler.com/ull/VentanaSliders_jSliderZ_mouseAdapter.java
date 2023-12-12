package ull;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class VentanaSliders_jSliderZ_mouseAdapter extends MouseAdapter {
   VentanaSliders adaptee;

   VentanaSliders_jSliderZ_mouseAdapter(VentanaSliders adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseClicked(MouseEvent e) {
      this.adaptee.jSliderZ_mouseClicked(e);
   }
}
