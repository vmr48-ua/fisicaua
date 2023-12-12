import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class DibujoEscalaVFL extends Canvas {
   public void paint(Graphics var1) {
      var1.setColor(Color.yellow);
      var1.drawLine(29, 0, 29, 210);

      for(int var2 = -40; var2 <= 40; var2 += 20) {
         var1.drawString("" + var2, 0, 115 - var2 * 2);
         var1.drawLine(20, 105 - var2 * 2, 30, 105 - var2 * 2);
      }

   }

   public void redraw() {
      this.repaint();
   }
}
