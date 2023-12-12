package local.imagenes;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class OptVentanaNormal extends Frame implements WindowListener {
   public OptVentanaNormal(String var1, int var2, int var3) {
      super(var1);
      this.setSize(var2 + 10, var3 + 30);
      this.addWindowListener(this);
   }

   public void windowClosing(WindowEvent var1) {
      this.dispose();
   }

   public void windowActivated(WindowEvent var1) {
   }

   public void windowClosed(WindowEvent var1) {
   }

   public void windowDeactivated(WindowEvent var1) {
   }

   public void windowDeiconified(WindowEvent var1) {
   }

   public void windowIconified(WindowEvent var1) {
   }

   public void windowOpened(WindowEvent var1) {
   }
}
