package ull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Ull_jButton2_actionAdapter implements ActionListener {
   Ull adaptee;

   Ull_jButton2_actionAdapter(Ull adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      this.adaptee.jButton2_actionPerformed(e);
   }
}
