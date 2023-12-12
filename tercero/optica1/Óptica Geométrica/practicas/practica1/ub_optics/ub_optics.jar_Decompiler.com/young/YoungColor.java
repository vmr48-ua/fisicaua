package young;

import java.awt.Color;
import java.awt.color.ColorSpace;

public class YoungColor {
   static double[][] spectrum_xy = new double[][]{{380.0D, 0.1813D, 0.0197D}, {385.0D, 0.1809D, 0.0195D}, {390.0D, 0.1803D, 0.0194D}, {395.0D, 0.1795D, 0.019D}, {400.0D, 0.1784D, 0.0187D}, {405.0D, 0.1771D, 0.0184D}, {410.0D, 0.1755D, 0.0181D}, {415.0D, 0.1732D, 0.0178D}, {420.0D, 0.1706D, 0.0179D}, {425.0D, 0.1679D, 0.0187D}, {430.0D, 0.165D, 0.0203D}, {435.0D, 0.1622D, 0.0225D}, {440.0D, 0.159D, 0.0257D}, {445.0D, 0.1554D, 0.03D}, {450.0D, 0.151D, 0.0364D}, {455.0D, 0.1459D, 0.0452D}, {460.0D, 0.1389D, 0.0589D}, {465.0D, 0.1295D, 0.0779D}, {470.0D, 0.1152D, 0.109D}, {475.0D, 0.0957D, 0.1591D}, {480.0D, 0.0728D, 0.2292D}, {485.0D, 0.0452D, 0.3275D}, {490.0D, 0.021D, 0.4401D}, {495.0D, 0.0073D, 0.5625D}, {500.0D, 0.0056D, 0.6745D}, {505.0D, 0.0219D, 0.7526D}, {510.0D, 0.0495D, 0.8023D}, {515.0D, 0.085D, 0.817D}, {520.0D, 0.1252D, 0.8102D}, {525.0D, 0.1664D, 0.7922D}, {530.0D, 0.2071D, 0.7663D}, {535.0D, 0.2436D, 0.7399D}, {540.0D, 0.2786D, 0.7113D}, {545.0D, 0.3132D, 0.6813D}, {550.0D, 0.3473D, 0.6501D}, {555.0D, 0.3812D, 0.6182D}, {560.0D, 0.4142D, 0.5858D}, {565.0D, 0.4469D, 0.5531D}, {570.0D, 0.479D, 0.521D}, {575.0D, 0.5096D, 0.4904D}, {580.0D, 0.5386D, 0.4614D}, {585.0D, 0.5654D, 0.4346D}, {590.0D, 0.59D, 0.41D}, {595.0D, 0.6116D, 0.3884D}, {600.0D, 0.6306D, 0.3694D}, {605.0D, 0.6471D, 0.3529D}, {610.0D, 0.6612D, 0.3388D}, {615.0D, 0.6731D, 0.3269D}, {620.0D, 0.6827D, 0.3173D}, {625.0D, 0.6898D, 0.3102D}, {630.0D, 0.6955D, 0.3045D}, {635.0D, 0.701D, 0.299D}, {640.0D, 0.7059D, 0.2941D}, {645.0D, 0.7103D, 0.2898D}, {650.0D, 0.7137D, 0.2863D}, {655.0D, 0.7156D, 0.2844D}, {660.0D, 0.7168D, 0.2832D}, {665.0D, 0.7179D, 0.2821D}, {670.0D, 0.7187D, 0.2813D}, {675.0D, 0.7193D, 0.2807D}, {680.0D, 0.7198D, 0.2802D}, {685.0D, 0.72D, 0.28D}, {690.0D, 0.7202D, 0.2798D}, {695.0D, 0.7203D, 0.2797D}, {700.0D, 0.7204D, 0.2796D}, {705.0D, 0.7203D, 0.2797D}, {710.0D, 0.7202D, 0.2798D}, {715.0D, 0.7201D, 0.2799D}, {720.0D, 0.7199D, 0.2801D}, {725.0D, 0.7197D, 0.2803D}, {730.0D, 0.7195D, 0.2806D}, {735.0D, 0.7192D, 0.2808D}, {740.0D, 0.7189D, 0.2811D}, {745.0D, 0.7186D, 0.2814D}, {750.0D, 0.7183D, 0.2817D}, {755.0D, 0.718D, 0.282D}, {760.0D, 0.7176D, 0.2824D}, {765.0D, 0.7172D, 0.2828D}, {770.0D, 0.7169D, 0.2831D}, {775.0D, 0.7165D, 0.2835D}, {780.0D, 0.7161D, 0.2839D}};
   int n_lambdas = 81;

   public Color lambda2RGB(int lambda) {
      double lambda_x = 0.0D;
      double lambda_y = 0.0D;
      ColorSpace cs = ColorSpace.getInstance(1000);
      float[] coordcolor = new float[]{0.0F, 0.0F, 0.0F};
      float[] hsb = new float[]{0.0F, 0.0F, 0.0F};

      for(int i = 0; i < this.n_lambdas - 1; ++i) {
         if ((double)lambda >= spectrum_xy[i][0] && (double)lambda <= spectrum_xy[i + 1][0]) {
            lambda_x = spectrum_xy[i][1] + ((double)lambda - spectrum_xy[i][0]) / (spectrum_xy[i + 1][0] - spectrum_xy[i][0]) * (spectrum_xy[i + 1][1] - spectrum_xy[i][1]);
            lambda_y = spectrum_xy[i][2] + ((double)lambda - spectrum_xy[i][0]) / (spectrum_xy[i + 1][0] - spectrum_xy[i][0]) * (spectrum_xy[i + 1][2] - spectrum_xy[i][2]);
            break;
         }
      }

      coordcolor[0] = (float)lambda_x;
      coordcolor[1] = (float)lambda_y;
      coordcolor[2] = (float)(1.0D - lambda_x - lambda_y);
      if (coordcolor[2] < 0.0F) {
         coordcolor[2] = 0.0F;
      }

      float[] coordrgb = cs.fromCIEXYZ(coordcolor);
      double r = (double)coordrgb[0];
      double g = (double)coordrgb[1];
      double b = (double)coordrgb[2];
      Color color;
      if (r >= 0.0D && r <= 1.0D && g >= 0.0D && g <= 1.0D && b >= 0.0D && b <= 1.0D) {
         color = new Color((float)r, (float)g, (float)b);
         hsb = Color.RGBtoHSB((int)(r * 255.0D), (int)(g * 255.0D), (int)(b * 255.0D), hsb);
         hsb[1] = 1.0F;
         hsb[2] = 1.0F;
         int rgbint = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
         Color color = new Color(rgbint);
         return color;
      } else {
         color = new Color(0, 0, 0);
         return color;
      }
   }

   public Color falsalambda2RGB(int lambda) {
      int red = 0;
      int green = 0;
      int blue = 0;

      for(int i = 0; i <= 83; ++i) {
         if (lambda == 380 + i) {
            red = 250 - i * 3;
            green = 0;
            blue = 250;
         }

         if (lambda == 464 + i) {
            red = 0;
            green = 0 + i * 3;
            blue = 250;
         }

         if (lambda == 548 + i) {
            red = 0;
            green = 250;
            blue = 250 - i * 3;
         }

         if (lambda == 632 + i) {
            red = 0 + i * 3;
            green = 250;
            blue = 0;
         }

         if (lambda == 716 + i) {
            red = 250;
            green = 250 - i * 3;
            blue = 0;
         }

         if (lambda == 800) {
            red = 250;
            green = 0;
            blue = 0;
         }
      }

      Color colorRgb = new Color(red, green, blue);
      return colorRgb;
   }
}
