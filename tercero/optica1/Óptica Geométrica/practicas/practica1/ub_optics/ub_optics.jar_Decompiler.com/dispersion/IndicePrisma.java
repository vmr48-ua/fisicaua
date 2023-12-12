package dispersion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.JPanel;

public class IndicePrisma extends JPanel {
   double n0;
   double nA;
   double nB;
   int lang;
   String[] textograf = new String[]{"Longitud de onda (nm)", "Longitud d'ona (nm)", "Wave lenght (nm)"};
   DecimalFormatSymbols df_symb = new DecimalFormatSymbols();
   DecimalFormat df;

   public IndicePrisma() {
      this.df = new DecimalFormat("#.##", this.df_symb);

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setBackground(Color.white);
      this.setMinimumSize(new Dimension(353, 218));
      this.setPreferredSize(new Dimension(353, 218));
   }

   public void putAtributos(double n_0, double a, double b, int idioma) {
      this.n0 = n_0;
      this.nA = a;
      this.nB = b;
      this.lang = idioma;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      Dimension dim = this.getSize();
      int dim_Lx = dim.width;
      int dim_Ly = dim.height;
      char pto = '.';
      this.df_symb.setDecimalSeparator(pto);
      this.df.setDecimalFormatSymbols(this.df_symb);
      Font fuente = new Font("Dialog", 1, 12);
      g2.setFont(fuente);
      g2.setPaint(Color.white);
      g2.drawLine(30, 10, 30, dim_Ly - 30);
      g2.drawLine(30, dim_Ly - 30, dim_Lx - 10, dim_Ly - 30);
      g2.drawLine(dim_Lx - 10, 10, dim_Lx - 10, dim_Ly - 30);
      g2.drawLine(30, 10, dim_Lx - 10, 10);
      g2.drawString("400", 20, dim_Ly - 15);
      g2.drawString("700", dim_Lx - 20, dim_Ly - 15);
      g2.drawString("n", 10, dim_Ly / 2 - 5);
      g2.drawString(this.textograf[this.lang], dim_Lx / 2 - 50, dim_Ly - 10);
      double delta_lambda = 300.0D / (double)(dim_Lx - 40);
      double indc_max = this.n0 + this.nA / 160000.0D + this.nB / 2.56E10D;
      double indc_min = this.n0 + this.nA / 490000.0D + this.nB / 2.401E11D;
      g2.drawString(this.df.format(indc_max), 5, 15);
      g2.drawString(this.df.format(indc_min), 5, dim_Ly - 30);
      g2.setPaint(Color.white);
      if (this.n0 != 0.0D) {
         for(int i = 30; i < dim_Lx - 10; ++i) {
            double lambda = (double)(i - 30) * delta_lambda + 400.0D;
            double lambda2 = lambda * lambda;
            double lambda4 = lambda2 * lambda2;
            double lambdaB = (double)(i - 30 + 1) * delta_lambda + 400.0D;
            double lambdaB2 = lambdaB * lambdaB;
            double lambdaB4 = lambdaB2 * lambdaB2;
            double indc = this.n0 + this.nA / lambda2 + this.nB / lambda4;
            double indcB = this.n0 + this.nA / lambdaB2 + this.nB / lambdaB4;
            double posindcA = (double)(dim_Ly - 40) * (indc - indc_min) / (indc_max - indc_min);
            double posindcB = (double)(dim_Ly - 40) * (indcB - indc_min) / (indc_max - indc_min);
            g2.drawLine(i, (int)((double)(dim_Ly - 30) - posindcA), i + 1, (int)((double)(dim_Ly - 30) - posindcB));
         }
      }

      YoungColor ycolores = new YoungColor();
      if (this.n0 != 0.0D) {
         Color ncolor = ycolores.lambda2RGB(587);
         g2.setPaint(ncolor);
         double indc_d = this.n0 + this.nA / 345273.76D + this.nB / 1.1921396934453761E11D;
         double posindc_d = (double)(dim_Ly - 40) * (indc_d - indc_min) / (indc_max - indc_min);
         int x = (int)(187.60000000000002D / delta_lambda) + 30;
         g2.drawLine(x, (int)((double)(dim_Ly - 30) - posindc_d), x, dim_Ly - 30);
         g2.drawString("d", x, (int)((double)(dim_Ly - 30) - posindc_d - 5.0D));
         ncolor = ycolores.lambda2RGB(486);
         g2.setPaint(ncolor);
         double indc_F = this.n0 + this.nA / 236293.21000000002D + this.nB / 5.583448109210411E10D;
         double posindc_F = (double)(dim_Ly - 40) * (indc_F - indc_min) / (indc_max - indc_min);
         x = (int)(86.10000000000002D / delta_lambda) + 30;
         g2.drawLine(x, (int)((double)(dim_Ly - 30) - posindc_F), x, dim_Ly - 30);
         g2.drawString("F", x, (int)((double)(dim_Ly - 30) - posindc_F - 5.0D));
         ncolor = ycolores.lambda2RGB(656);
         g2.setPaint(ncolor);
         double indc_C = this.n0 + this.nA / 430729.68999999994D + this.nB / 1.8552806584749603E11D;
         double posindc_C = (double)(dim_Ly - 40) * (indc_C - indc_min) / (indc_max - indc_min);
         x = (int)(256.29999999999995D / delta_lambda) + 30;
         g2.drawLine(x, (int)((double)(dim_Ly - 30) - posindc_C), x, dim_Ly - 30);
         g2.drawString("C", x, (int)((double)(dim_Ly - 30) - posindc_C - 5.0D));
      }

   }
}
