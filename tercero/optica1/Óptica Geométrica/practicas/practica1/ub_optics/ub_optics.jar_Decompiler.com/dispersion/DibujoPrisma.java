package dispersion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class DibujoPrisma extends JPanel {
   double angmedioprisma;
   double ang_in;
   int[] lambdas = new int[7];
   double[] index = new double[7];
   double[] angdesv = new double[7];
   double[] epsilon2prima = new double[7];

   public DibujoPrisma() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setBackground(Color.black);
      this.setMinimumSize(new Dimension(355, 200));
      this.setPreferredSize(new Dimension(355, 200));
   }

   public void putAtributos(double angprisma, double angprisma_in, int[] longonda, double[] indices, double[] angdesviacion, double[] ange2prima_prisma) {
      this.angmedioprisma = angprisma / 2.0D;
      this.ang_in = angprisma_in;

      for(int i = 0; i < 7; ++i) {
         this.lambdas[i] = longonda[i];
         this.index[i] = indices[i];
         this.angdesv[i] = angdesviacion[i];
         this.epsilon2prima[i] = ange2prima_prisma[i];
      }

   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      Dimension dim = this.getSize();
      int dim_Lx = dim.width;
      int dim_Ly = dim.height;
      YoungColor ycolores = new YoungColor();
      double h = 100.0D;
      double d = h * Math.tan(this.angmedioprisma);
      double l = Math.sqrt(h * h + d * d);
      double lprima = l / 2.0D;
      double deltaxD = 25.0D;
      double deltaxI = 35.0D;
      double pAx = (double)dim_Lx / 2.0D;
      double pAy = 50.0D;
      double var10000 = pAy + h;
      double pBx = pAx - h * Math.tan(this.angmedioprisma);
      double pBy = pAy + h;
      double pCx = pAx + h * Math.tan(this.angmedioprisma);
      double pCy = pAy + h;
      double pDx = pAx - lprima * Math.sin(this.angmedioprisma);
      double pDy = pAy + lprima * Math.cos(this.angmedioprisma);
      double pDprimax = pDx - deltaxD;
      double pDprimay = pDy - deltaxD * Math.tan(this.angmedioprisma);
      double pIx = 0.0D;
      double pIy = pDy + pDx * Math.tan(this.ang_in - this.angmedioprisma);
      double[] epsilon1prima = new double[7];
      double[] dist_out = new double[7];
      double[] pSx = new double[7];
      double[] pSy = new double[7];

      for(int i = 0; i < 7; ++i) {
         epsilon1prima[i] = Math.asin(Math.sin(this.ang_in) / this.index[i]);
         dist_out[i] = lprima * (Math.cos(epsilon1prima[i]) / Math.cos(2.0D * this.angmedioprisma - epsilon1prima[i]));
         pSx[i] = pAx + dist_out[i] * Math.sin(this.angmedioprisma);
         pSy[i] = pAy + dist_out[i] * Math.cos(this.angmedioprisma);
         if (pSy[i] > pBy) {
            pSy[i] = pBy;
            pSx[i] = pBx + lprima * (Math.cos(epsilon1prima[i]) / Math.sin(this.angmedioprisma - epsilon1prima[i]));
         }
      }

      double[] pOx = new double[7];
      double[] pOy = new double[7];

      for(int i = 0; i < 7; ++i) {
         pOx[i] = (double)dim_Lx;
         pOy[i] = pSy[i] + ((double)dim_Lx - pSx[i]) * Math.tan(this.epsilon2prima[i] - this.angmedioprisma);
      }

      g2.setPaint(Color.white);
      g2.drawLine((int)pIx, (int)pIy, (int)pDx, (int)pDy);
      g2.drawLine((int)pAx, (int)pAy, (int)pBx, (int)pBy);
      g2.drawLine((int)pCx, (int)pCy, (int)pAx, (int)pAy);
      g2.setPaint(Color.gray);
      g2.drawLine((int)pBx, (int)pBy, (int)pCx, (int)pCy);
      g2.drawLine((int)pDprimax, (int)pDprimay, (int)pDx, (int)pDy);

      for(int i = 0; i < 7; ++i) {
         Color ncolor = ycolores.lambda2RGB(this.lambdas[i]);
         g2.setPaint(ncolor);
         g2.drawLine((int)pDx, (int)pDy, (int)pSx[i], (int)pSy[i]);
         if (pSy[i] < pBy && this.epsilon2prima[i] < 3.141592653589793D) {
            g2.drawLine((int)pSx[i], (int)pSy[i], (int)pOx[i], (int)pOy[i]);
         }
      }

   }
}
