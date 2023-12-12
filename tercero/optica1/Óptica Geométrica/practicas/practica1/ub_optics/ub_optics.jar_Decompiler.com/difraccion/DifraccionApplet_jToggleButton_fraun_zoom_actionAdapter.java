package difraccion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DifraccionApplet_jToggleButton_fraun_zoom_actionAdapter implements ActionListener {
   DifraccionApplet adaptee;

   DifraccionApplet_jToggleButton_fraun_zoom_actionAdapter(DifraccionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jToggleButton_fraun_zoom_actionPerformed(e);
   }
}
