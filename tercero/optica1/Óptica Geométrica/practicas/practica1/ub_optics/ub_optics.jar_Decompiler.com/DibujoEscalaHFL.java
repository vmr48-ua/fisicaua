import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class DibujoEscalaHFL extends Canvas {
   public void paint(Graphics var1) {
      var1.setColor(Color.yellow);
      var1.drawLine(30, 0, 800, 0);

      int var2;
      for(var2 = 0; var2 < 800; var2 += 50) {
         var1.drawLine(30 + var2, 10, 30 + var2, 0);
      }

      for(var2 = 0; var2 < 800; var2 += 100) {
         var1.drawString("" + var2 * 2, 30 + var2, 18);
      }

      var1.drawLine(0, 19, 800, 19);
   }

   public void redraw() {
      this.repaint();
   }
}
