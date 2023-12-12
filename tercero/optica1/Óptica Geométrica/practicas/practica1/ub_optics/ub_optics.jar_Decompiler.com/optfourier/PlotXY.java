package optfourier;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D.Double;
import javax.swing.JPanel;

class PlotXY extends JPanel {
   int num;
   boolean filocol;
   int ncol;
   int nfil;
   String s;

   public PlotXY() {
      this.ncol = Holog.imagen[Visualitzacio.numImagen].ncol;
      this.nfil = Holog.imagen[Visualitzacio.numImagen].nfil;
      this.s = "" + this.nfil + "";
   }

   public void get_linea(int lin, boolean filocol) {
      this.num = lin;
      this.filocol = filocol;
      if (filocol) {
         this.s = "" + this.ncol + "";
      }

   }

   public void paint(Graphics g) {
      double x_inicio = 0.0D;
      double x_final = 0.0D;
      double y_inicio = 0.0D;
      double y_final = 0.0D;
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(new Color(0, 0, 0));
      g2.fillRect(30, 10, 256, 256);
      g2.drawString("256", 8, 15);
      g2.drawString("  0", 8, 275);
      g2.drawString(this.s, 265, 280);
      g2.setColor(new Color(204, 204, 255));
      int i;
      if (this.filocol) {
         for(i = 1; i < this.nfil; ++i) {
            x_inicio = 30.0D + (double)(i - 1) * 256.0D / (double)this.nfil;
            x_final = 30.0D + (double)i * 256.0D / (double)this.nfil;
            y_inicio = 266.0D - (double)Visualitzacio.mapaPlot[this.num + this.ncol * (i - 1)];
            y_final = 266.0D - (double)Visualitzacio.mapaPlot[this.num + this.ncol * i];
            g2.fill(new Double(x_inicio, y_final, x_final - x_inicio, 266.0D - y_final));
         }
      } else {
         for(i = 1; i < this.ncol; ++i) {
            x_inicio = 30.0D + (double)(i - 1) * 256.0D / (double)this.ncol;
            x_final = 30.0D + (double)i * 256.0D / (double)this.ncol;
            y_inicio = 266.0D - (double)Visualitzacio.mapaPlot[i - 1 + this.ncol * this.num];
            y_final = 266.0D - (double)Visualitzacio.mapaPlot[i + this.ncol * this.num];
            g2.fill(new Double(x_inicio, y_final, x_final - x_inicio, 266.0D - y_final));
         }
      }

   }
}
