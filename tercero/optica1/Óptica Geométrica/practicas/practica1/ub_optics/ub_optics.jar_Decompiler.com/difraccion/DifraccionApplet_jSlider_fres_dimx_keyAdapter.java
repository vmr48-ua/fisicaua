package difraccion;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class DifraccionApplet_jSlider_fres_dimx_keyAdapter extends KeyAdapter {
   DifraccionApplet adaptee;

   DifraccionApplet_jSlider_fres_dimx_keyAdapter(DifraccionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void keyPressed(KeyEvent e) {
      this.adaptee.jSlider_fres_dimx_keyPressed(e);
   }

   public void keyTyped(KeyEvent e) {
      this.adaptee.jSlider_fres_dimx_keyTyped(e);
   }
}
