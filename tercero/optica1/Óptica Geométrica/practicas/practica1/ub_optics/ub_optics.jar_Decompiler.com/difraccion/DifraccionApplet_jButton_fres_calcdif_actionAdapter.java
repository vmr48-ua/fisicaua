package difraccion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DifraccionApplet_jButton_fres_calcdif_actionAdapter implements ActionListener {
   DifraccionApplet adaptee;

   DifraccionApplet_jButton_fres_calcdif_actionAdapter(DifraccionApplet adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jButton_fres_calcdif_actionPerformed(e);
   }
}
