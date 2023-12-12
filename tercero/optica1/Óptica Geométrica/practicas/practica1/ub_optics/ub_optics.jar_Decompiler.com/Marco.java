import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

class Marco extends JFrame implements WindowListener {
   public Marco(String var1) {
      super(var1);
      this.setResizable(true);
      this.setDefaultCloseOperation(3);
      this.addWindowListener(this);
   }

   public void windowClosing(WindowEvent var1) {
      System.exit(0);
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
