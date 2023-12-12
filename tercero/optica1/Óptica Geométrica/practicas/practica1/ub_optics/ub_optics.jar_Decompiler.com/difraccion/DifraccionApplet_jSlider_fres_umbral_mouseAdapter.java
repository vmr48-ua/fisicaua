package difraccion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class DifraccionApplet_jSlider_fres_umbral_mouseAdapter extends MouseAdapter {
   DifraccionApplet adaptee;

   DifraccionApplet_jSlider_fres_umbral_mouseAdapter(DifraccionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void mouseClicked(MouseEvent e) {
      this.adaptee.jSlider_fres_umbral_mouseClicked(e);
   }
}
