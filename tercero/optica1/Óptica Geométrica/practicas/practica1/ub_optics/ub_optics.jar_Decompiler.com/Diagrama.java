import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Diagrama extends JPanel {
   static int nfil = 500;
   static int ncol = 480;
   static int[][] a;
   static int a1a;
   static int b1a;
   static int a2a;
   static int b2a;
   int i;
   int j;
   int v;
   int vR;
   int vG;
   int vB;
   static BufferedImage buffImage;
   Graphics graf = null;
   DatosColor colorPunto = new DatosColor();
   int[][] Cpunto = new int[][]{{0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 255, 255, 0, 0, 0}, {0, 0, 255, 255, 0, 0, 0}};
   int[][] C0 = new int[][]{{0, 0, 255, 255, 255, 0, 0}, {0, 255, 0, 0, 0, 255, 0}, {255, 0, 0, 0, 0, 0, 255}, {255, 0, 0, 0, 0, 0, 255}, {255, 0, 0, 0, 0, 0, 255}, {255, 0, 0, 0, 0, 0, 255}, {255, 0, 0, 0, 0, 0, 255}, {0, 255, 0, 0, 0, 255, 0}, {0, 0, 255, 255, 255, 0, 0}};
   int[][] C1 = new int[][]{{0, 0, 0, 255, 255, 0, 0}, {0, 0, 255, 255, 255, 0, 0}, {0, 255, 255, 0, 255, 0, 0}, {0, 0, 0, 0, 255, 0, 0}, {0, 0, 0, 0, 255, 0, 0}, {0, 0, 0, 0, 255, 0, 0}, {0, 0, 0, 0, 255, 0, 0}, {0, 0, 0, 0, 255, 0, 0}, {0, 0, 0, 0, 255, 0, 0}};
   int[][] C2 = new int[][]{{0, 0, 255, 255, 255, 0, 0}, {0, 255, 0, 0, 0, 255, 0}, {255, 0, 0, 0, 0, 0, 255}, {0, 0, 0, 0, 0, 0, 255}, {0, 0, 0, 0, 255, 255, 0}, {0, 0, 0, 255, 0, 0, 0}, {0, 0, 255, 0, 0, 0, 0}, {0, 255, 0, 0, 0, 0, 0}, {255, 255, 255, 255, 255, 255, 255}};
   int[][] C3 = new int[][]{{0, 0, 255, 255, 255, 0, 0}, {0, 255, 0, 0, 0, 255, 0}, {255, 0, 0, 0, 0, 0, 255}, {0, 0, 0, 0, 0, 255, 0}, {0, 0, 255, 255, 255, 0, 0}, {0, 0, 0, 0, 0, 255, 0}, {255, 0, 0, 0, 0, 0, 255}, {0, 255, 0, 0, 0, 255, 0}, {0, 0, 255, 255, 255, 0, 0}};
   int[][] C4 = new int[][]{{0, 0, 0, 0, 0, 255, 0}, {0, 0, 0, 0, 255, 255, 0}, {0, 0, 0, 255, 255, 255, 0}, {0, 0, 255, 255, 0, 255, 0}, {0, 255, 255, 0, 0, 255, 0}, {255, 255, 0, 0, 0, 255, 0}, {255, 255, 255, 255, 255, 255, 0}, {0, 0, 0, 0, 0, 255, 0}, {0, 0, 0, 0, 0, 255, 0}};
   int[][] C5 = new int[][]{{255, 255, 255, 255, 255, 255, 0}, {255, 0, 0, 0, 0, 0, 0}, {255, 0, 0, 0, 0, 0, 0}, {255, 255, 255, 255, 255, 0, 0}, {255, 0, 0, 0, 0, 255, 0}, {0, 0, 0, 0, 0, 0, 255}, {0, 0, 0, 0, 0, 0, 255}, {255, 0, 0, 0, 0, 255, 0}, {0, 255, 255, 255, 255, 0, 0}};
   int[][] C6 = new int[][]{{0, 0, 255, 255, 255, 0, 0}, {0, 255, 0, 0, 0, 255, 0}, {255, 0, 0, 0, 0, 0, 0}, {255, 0, 0, 0, 0, 0, 0}, {255, 0, 255, 255, 255, 0, 0}, {255, 255, 0, 0, 0, 255, 255}, {255, 0, 0, 0, 0, 0, 255}, {0, 255, 0, 0, 0, 255, 0}, {0, 0, 255, 255, 255, 0, 0}};
   int[][] C7 = new int[][]{{0, 255, 255, 255, 255, 255, 255}, {0, 0, 0, 0, 0, 255, 255}, {0, 0, 0, 0, 255, 255, 0}, {0, 0, 0, 255, 255, 0, 0}, {0, 0, 0, 255, 0, 0, 0}, {0, 0, 255, 0, 0, 0, 0}, {0, 0, 255, 0, 0, 0, 0}, {0, 255, 0, 0, 0, 0, 0}, {0, 255, 0, 0, 0, 0, 0}};
   int[][] C8 = new int[][]{{0, 0, 255, 255, 255, 0, 0}, {0, 255, 0, 0, 0, 255, 0}, {255, 0, 0, 0, 0, 0, 255}, {0, 255, 0, 0, 0, 255, 0}, {0, 0, 255, 255, 255, 0, 0}, {0, 255, 0, 0, 0, 255, 0}, {255, 0, 0, 0, 0, 0, 255}, {0, 255, 0, 0, 0, 255, 0}, {0, 0, 255, 255, 255, 0, 0}};
   int[][] C9 = new int[][]{{0, 0, 255, 255, 255, 0, 0}, {0, 255, 0, 0, 0, 255, 0}, {255, 0, 0, 0, 0, 0, 255}, {0, 255, 0, 0, 0, 255, 255}, {0, 0, 255, 255, 255, 0, 255}, {0, 0, 0, 0, 0, 0, 255}, {0, 0, 0, 0, 0, 0, 255}, {0, 255, 0, 0, 0, 255, 0}, {0, 0, 255, 255, 255, 0, 0}};
   int[][] Cx = new int[][]{{255, 0, 0, 0, 0, 0, 255}, {255, 255, 0, 0, 0, 255, 255}, {0, 255, 255, 0, 255, 255, 0}, {0, 0, 255, 255, 255, 0, 0}, {0, 0, 0, 255, 0, 0, 0}, {0, 0, 255, 255, 255, 0, 0}, {0, 255, 255, 0, 255, 255, 0}, {255, 255, 0, 0, 0, 255, 255}, {255, 0, 0, 0, 0, 0, 255}};
   int[][] Cy = new int[][]{{255, 0, 0, 0, 0, 0, 255}, {255, 255, 0, 0, 0, 255, 255}, {0, 255, 255, 0, 0, 255, 255}, {0, 0, 255, 255, 255, 255, 0}, {0, 0, 0, 255, 255, 255, 0}, {0, 0, 0, 0, 255, 255, 0}, {0, 0, 0, 255, 255, 0, 0}, {0, 0, 255, 255, 0, 0, 0}, {0, 255, 255, 0, 0, 0, 0}};
   int[][] CA = new int[][]{{0, 0, 0, 255, 0, 0, 0}, {0, 0, 255, 0, 255, 0, 0}, {0, 255, 0, 0, 0, 255, 0}, {255, 0, 0, 0, 0, 0, 255}, {255, 0, 0, 0, 0, 0, 255}, {255, 255, 255, 255, 255, 255, 255}, {255, 0, 0, 0, 0, 0, 255}, {255, 0, 0, 0, 0, 0, 255}, {255, 0, 0, 0, 0, 0, 255}};
   int[][] CB = new int[][]{{255, 255, 255, 255, 255, 0, 0}, {0, 255, 0, 0, 0, 255, 0}, {0, 255, 0, 0, 0, 0, 255}, {0, 255, 0, 0, 0, 255, 0}, {0, 255, 255, 255, 255, 0, 0}, {0, 255, 0, 0, 0, 255, 0}, {0, 255, 0, 0, 0, 0, 255}, {0, 255, 0, 0, 0, 255, 0}, {255, 255, 255, 255, 255, 0, 0}};
   int[][] Cc = new int[][]{{0, 0, 255, 255, 255, 0, 0}, {0, 255, 0, 0, 0, 255, 0}, {255, 0, 0, 0, 0, 0, 0}, {255, 0, 0, 0, 0, 0, 0}, {255, 0, 0, 0, 0, 0, 0}, {255, 0, 0, 0, 0, 0, 0}, {255, 0, 0, 0, 0, 0, 0}, {0, 255, 0, 0, 0, 255, 0}, {0, 0, 255, 255, 255, 0, 0}};
   int[][] CD = new int[][]{{255, 255, 255, 255, 255, 0, 0}, {0, 255, 0, 0, 0, 255, 0}, {0, 255, 0, 0, 0, 0, 255}, {0, 255, 0, 0, 0, 0, 255}, {0, 255, 0, 0, 0, 0, 255}, {0, 255, 0, 0, 0, 0, 255}, {0, 255, 0, 0, 0, 0, 255}, {0, 255, 0, 0, 0, 255, 0}, {255, 255, 255, 255, 255, 0, 0}};
   int[][] CE = new int[][]{{255, 255, 255, 255, 255, 255, 255}, {0, 255, 0, 0, 0, 0, 0}, {0, 255, 0, 0, 0, 0, 0}, {0, 255, 0, 0, 0, 0, 0}, {0, 255, 255, 255, 255, 0, 0}, {0, 255, 0, 0, 0, 0, 0}, {0, 255, 0, 0, 0, 0, 0}, {0, 255, 0, 0, 0, 0, 0}, {255, 255, 255, 255, 255, 255, 255}};

   public Diagrama() {
      this.setBounds(0, 25, ncol, nfil - 25);
      buffImage = new BufferedImage(ncol, nfil, 2);
      this.generaCuadroXYZ();
   }

   public void paint(Graphics var1) {
      if (buffImage != null) {
         var1.drawImage(buffImage, 0, 0, this);
      }

   }

   int generaCuadroXYZ() {
      double[] var2 = Colores.tablaXY.xf;
      double[] var3 = Colores.tablaXY.yf;
      int[] var4 = Colores.tablaXY.lambda;
      int[] var5 = Colores.tablaXY.x;
      int[] var6 = Colores.tablaXY.y;
      byte var1 = 0;
      var4[var1] = 400;
      var2[var1] = 0.1733D;
      var3[var1] = 0.0048D;
      var1 = 1;
      var4[var1] = 450;
      var2[var1] = 0.1566D;
      var3[var1] = 0.0177D;
      var1 = 2;
      var4[var1] = 460;
      var2[var1] = 0.144D;
      var3[var1] = 0.0297D;
      var1 = 3;
      var4[var1] = 465;
      var2[var1] = 0.1355D;
      var3[var1] = 0.0399D;
      var1 = 4;
      var4[var1] = 470;
      var2[var1] = 0.1241D;
      var3[var1] = 0.0578D;
      var1 = 5;
      var4[var1] = 472;
      var2[var1] = 0.1187D;
      var3[var1] = 0.0678D;
      var1 = 6;
      var4[var1] = 475;
      var2[var1] = 0.1096D;
      var3[var1] = 0.0868D;
      var1 = 7;
      var4[var1] = 480;
      var2[var1] = 0.0913D;
      var3[var1] = 0.1327D;
      var1 = 8;
      var4[var1] = 485;
      var2[var1] = 0.0687D;
      var3[var1] = 0.2007D;
      var1 = 9;
      var4[var1] = 490;
      var2[var1] = 0.0454D;
      var3[var1] = 0.295D;
      var1 = 10;
      var4[var1] = 495;
      var2[var1] = 0.0235D;
      var3[var1] = 0.4127D;
      var1 = 11;
      var4[var1] = 500;
      var2[var1] = 0.0082D;
      var3[var1] = 0.5384D;
      var1 = 12;
      var4[var1] = 503;
      var2[var1] = 0.004D;
      var3[var1] = 0.6104D;
      var1 = 13;
      var4[var1] = 505;
      var2[var1] = 0.004D;
      var3[var1] = 0.6548D;
      var1 = 14;
      var4[var1] = 507;
      var2[var1] = 0.006D;
      var3[var1] = 0.6961D;
      var1 = 15;
      var4[var1] = 510;
      var2[var1] = 0.0139D;
      var3[var1] = 0.7502D;
      var1 = 16;
      var4[var1] = 512;
      var2[var1] = 0.0222D;
      var3[var1] = 0.7796D;
      var1 = 17;
      var4[var1] = 513;
      var2[var1] = 0.0273D;
      var3[var1] = 0.7921D;
      var1 = 18;
      var4[var1] = 514;
      var2[var1] = 0.0328D;
      var3[var1] = 0.8029D;
      var1 = 19;
      var4[var1] = 515;
      var2[var1] = 0.0388D;
      var3[var1] = 0.812D;
      var1 = 20;
      var4[var1] = 516;
      var2[var1] = 0.0453D;
      var3[var1] = 0.8194D;
      var1 = 21;
      var4[var1] = 517;
      var2[var1] = 0.0522D;
      var3[var1] = 0.8252D;
      var1 = 22;
      var4[var1] = 518;
      var2[var1] = 0.0593D;
      var3[var1] = 0.8294D;
      var1 = 23;
      var4[var1] = 519;
      var2[var1] = 0.0667D;
      var3[var1] = 0.8323D;
      var1 = 24;
      var4[var1] = 520;
      var2[var1] = 0.0743D;
      var3[var1] = 0.8338D;
      var1 = 25;
      var4[var1] = 521;
      var2[var1] = 0.082D;
      var3[var1] = 0.8341D;
      var1 = 26;
      var4[var1] = 522;
      var2[var1] = 0.0899D;
      var3[var1] = 0.8334D;
      var1 = 27;
      var4[var1] = 523;
      var2[var1] = 0.0979D;
      var3[var1] = 0.8316D;
      var1 = 28;
      var4[var1] = 524;
      var2[var1] = 0.106D;
      var3[var1] = 0.8292D;
      var1 = 29;
      var4[var1] = 525;
      var2[var1] = 0.1142D;
      var3[var1] = 0.8262D;
      var1 = 30;
      var4[var1] = 527;
      var2[var1] = 0.13D;
      var3[var1] = 0.8189D;
      var1 = 31;
      var4[var1] = 530;
      var2[var1] = 0.1547D;
      var3[var1] = 0.8059D;
      var1 = 32;
      var4[var1] = 535;
      var2[var1] = 0.1929D;
      var3[var1] = 0.7816D;
      var1 = 33;
      var4[var1] = 540;
      var2[var1] = 0.2297D;
      var3[var1] = 0.7543D;
      var1 = 34;
      var4[var1] = 545;
      var2[var1] = 0.2658D;
      var3[var1] = 0.7243D;
      var1 = 35;
      var4[var1] = 550;
      var2[var1] = 0.3016D;
      var3[var1] = 0.6923D;
      var1 = 36;
      var4[var1] = 555;
      var2[var1] = 0.3374D;
      var3[var1] = 0.6588D;
      var1 = 37;
      var4[var1] = 560;
      var2[var1] = 0.3731D;
      var3[var1] = 0.6244D;
      var1 = 38;
      var4[var1] = 570;
      var2[var1] = 0.4441D;
      var3[var1] = 0.5547D;
      var1 = 39;
      var4[var1] = 580;
      var2[var1] = 0.5125D;
      var3[var1] = 0.4866D;
      var1 = 40;
      var4[var1] = 590;
      var2[var1] = 0.5751D;
      var3[var1] = 0.4242D;
      var1 = 41;
      var4[var1] = 600;
      var2[var1] = 0.627D;
      var3[var1] = 0.3725D;
      var1 = 42;
      var4[var1] = 610;
      var2[var1] = 0.6658D;
      var3[var1] = 0.334D;
      var1 = 43;
      var4[var1] = 620;
      var2[var1] = 0.6915D;
      var3[var1] = 0.3083D;
      var1 = 44;
      var4[var1] = 640;
      var2[var1] = 0.719D;
      var3[var1] = 0.2809D;
      var1 = 45;
      var4[var1] = 700;
      var2[var1] = 0.7347D;
      var3[var1] = 0.2653D;
      var1 = 46;
      var4[var1] = 400;
      var2[var1] = 0.1733D;
      var3[var1] = 0.0048D;
      double[] var7 = Colores.tablaXYT.xt;
      double[] var8 = Colores.tablaXYT.yt;
      double[] var9 = Colores.tablaXYT.zt;
      int[] var10 = Colores.tablaXYT.lambda;
      var1 = 0;
      var10[var1] = 400;
      var7[var1] = 0.0143D;
      var8[var1] = 4.0E-4D;
      var9[var1] = 0.0679D;
      var1 = 1;
      var10[var1] = 405;
      var7[var1] = 0.0232D;
      var8[var1] = 6.0E-4D;
      var9[var1] = 0.1102D;
      var1 = 2;
      var10[var1] = 410;
      var7[var1] = 0.0435D;
      var8[var1] = 0.0012D;
      var9[var1] = 0.2074D;
      var1 = 3;
      var10[var1] = 415;
      var7[var1] = 0.0776D;
      var8[var1] = 0.0022D;
      var9[var1] = 0.3713D;
      var1 = 4;
      var10[var1] = 420;
      var7[var1] = 0.1344D;
      var8[var1] = 0.004D;
      var9[var1] = 0.6456D;
      var1 = 5;
      var10[var1] = 425;
      var7[var1] = 0.2148D;
      var8[var1] = 0.0073D;
      var9[var1] = 1.0391D;
      var1 = 6;
      var10[var1] = 430;
      var7[var1] = 0.2839D;
      var8[var1] = 0.0116D;
      var9[var1] = 1.3856D;
      var1 = 7;
      var10[var1] = 435;
      var7[var1] = 0.3285D;
      var8[var1] = 0.0168D;
      var9[var1] = 1.623D;
      var1 = 8;
      var10[var1] = 440;
      var7[var1] = 0.3483D;
      var8[var1] = 0.023D;
      var9[var1] = 1.7471D;
      var1 = 9;
      var10[var1] = 445;
      var7[var1] = 0.3481D;
      var8[var1] = 0.0298D;
      var9[var1] = 1.7826D;
      var1 = 10;
      var10[var1] = 450;
      var7[var1] = 0.3362D;
      var8[var1] = 0.038D;
      var9[var1] = 1.7721D;
      var1 = 11;
      var10[var1] = 455;
      var7[var1] = 0.3187D;
      var8[var1] = 0.048D;
      var9[var1] = 1.7441D;
      var1 = 12;
      var10[var1] = 460;
      var7[var1] = 0.2908D;
      var8[var1] = 0.06D;
      var9[var1] = 1.6692D;
      var1 = 13;
      var10[var1] = 465;
      var7[var1] = 0.2511D;
      var8[var1] = 0.0739D;
      var9[var1] = 1.5281D;
      var1 = 14;
      var10[var1] = 470;
      var7[var1] = 0.1954D;
      var8[var1] = 0.091D;
      var9[var1] = 1.2876D;
      var1 = 15;
      var10[var1] = 475;
      var7[var1] = 0.1421D;
      var8[var1] = 0.1126D;
      var9[var1] = 1.0419D;
      var1 = 16;
      var10[var1] = 480;
      var7[var1] = 0.0956D;
      var8[var1] = 0.139D;
      var9[var1] = 0.813D;
      var1 = 17;
      var10[var1] = 485;
      var7[var1] = 0.058D;
      var8[var1] = 0.1693D;
      var9[var1] = 0.6162D;
      var1 = 18;
      var10[var1] = 490;
      var7[var1] = 0.032D;
      var8[var1] = 0.208D;
      var9[var1] = 0.4652D;
      var1 = 19;
      var10[var1] = 495;
      var7[var1] = 0.0147D;
      var8[var1] = 0.2586D;
      var9[var1] = 0.3533D;
      var1 = 20;
      var10[var1] = 500;
      var7[var1] = 0.0049D;
      var8[var1] = 0.323D;
      var9[var1] = 0.272D;
      var1 = 21;
      var10[var1] = 505;
      var7[var1] = 0.0024D;
      var8[var1] = 0.4073D;
      var9[var1] = 0.2123D;
      var1 = 22;
      var10[var1] = 510;
      var7[var1] = 0.0093D;
      var8[var1] = 0.503D;
      var9[var1] = 0.1582D;
      var1 = 23;
      var10[var1] = 515;
      var7[var1] = 0.0291D;
      var8[var1] = 0.6082D;
      var9[var1] = 0.1117D;
      var1 = 24;
      var10[var1] = 520;
      var7[var1] = 0.0633D;
      var8[var1] = 0.71D;
      var9[var1] = 0.0782D;
      var1 = 25;
      var10[var1] = 525;
      var7[var1] = 0.1096D;
      var8[var1] = 0.7932D;
      var9[var1] = 0.0573D;
      var1 = 26;
      var10[var1] = 530;
      var7[var1] = 0.1655D;
      var8[var1] = 0.862D;
      var9[var1] = 0.0422D;
      var1 = 27;
      var10[var1] = 535;
      var7[var1] = 0.2257D;
      var8[var1] = 0.9149D;
      var9[var1] = 0.0298D;
      var1 = 28;
      var10[var1] = 540;
      var7[var1] = 0.2904D;
      var8[var1] = 0.954D;
      var9[var1] = 0.0203D;
      var1 = 29;
      var10[var1] = 545;
      var7[var1] = 0.3597D;
      var8[var1] = 0.9803D;
      var9[var1] = 0.0134D;
      var1 = 30;
      var10[var1] = 550;
      var7[var1] = 0.4334D;
      var8[var1] = 0.995D;
      var9[var1] = 0.0087D;
      var1 = 31;
      var10[var1] = 555;
      var7[var1] = 0.5121D;
      var8[var1] = 1.0D;
      var9[var1] = 0.0057D;
      var1 = 32;
      var10[var1] = 560;
      var7[var1] = 0.5945D;
      var8[var1] = 0.995D;
      var9[var1] = 0.0039D;
      var1 = 33;
      var10[var1] = 565;
      var7[var1] = 0.6784D;
      var8[var1] = 0.9786D;
      var9[var1] = 0.0027D;
      var1 = 34;
      var10[var1] = 570;
      var7[var1] = 0.7621D;
      var8[var1] = 0.952D;
      var9[var1] = 0.0021D;
      var1 = 35;
      var10[var1] = 575;
      var7[var1] = 0.8425D;
      var8[var1] = 0.9154D;
      var9[var1] = 0.0018D;
      var1 = 36;
      var10[var1] = 580;
      var7[var1] = 0.9163D;
      var8[var1] = 0.87D;
      var9[var1] = 0.0017D;
      var1 = 37;
      var10[var1] = 585;
      var7[var1] = 0.9786D;
      var8[var1] = 0.8163D;
      var9[var1] = 0.0014D;
      var1 = 38;
      var10[var1] = 590;
      var7[var1] = 1.0263D;
      var8[var1] = 0.757D;
      var9[var1] = 0.0011D;
      var1 = 39;
      var10[var1] = 595;
      var7[var1] = 1.0576D;
      var8[var1] = 0.6949D;
      var9[var1] = 0.001D;
      var1 = 40;
      var10[var1] = 600;
      var7[var1] = 1.0622D;
      var8[var1] = 0.631D;
      var9[var1] = 8.0E-4D;
      var1 = 41;
      var10[var1] = 605;
      var7[var1] = 1.0456D;
      var8[var1] = 0.5668D;
      var9[var1] = 6.0E-4D;
      var1 = 42;
      var10[var1] = 610;
      var7[var1] = 1.0026D;
      var8[var1] = 0.503D;
      var9[var1] = 3.0E-4D;
      var1 = 43;
      var10[var1] = 615;
      var7[var1] = 0.9384D;
      var8[var1] = 0.4412D;
      var9[var1] = 2.0E-4D;
      var1 = 44;
      var10[var1] = 620;
      var7[var1] = 0.8544D;
      var8[var1] = 0.381D;
      var9[var1] = 2.0E-4D;
      var1 = 45;
      var10[var1] = 625;
      var7[var1] = 0.7514D;
      var8[var1] = 0.321D;
      var9[var1] = 1.0E-4D;
      var1 = 46;
      var10[var1] = 630;
      var7[var1] = 0.6424D;
      var8[var1] = 0.265D;
      var9[var1] = 0.0D;
      var1 = 47;
      var10[var1] = 635;
      var7[var1] = 0.5419D;
      var8[var1] = 0.217D;
      var9[var1] = 0.0D;
      var1 = 48;
      var10[var1] = 640;
      var7[var1] = 0.4479D;
      var8[var1] = 0.175D;
      var9[var1] = 0.0D;
      var1 = 49;
      var10[var1] = 645;
      var7[var1] = 0.3608D;
      var8[var1] = 0.1382D;
      var9[var1] = 0.0D;
      var1 = 50;
      var10[var1] = 650;
      var7[var1] = 0.2835D;
      var8[var1] = 0.107D;
      var9[var1] = 0.0D;
      var1 = 51;
      var10[var1] = 655;
      var7[var1] = 0.2187D;
      var8[var1] = 0.0816D;
      var9[var1] = 0.0D;
      var1 = 52;
      var10[var1] = 660;
      var7[var1] = 0.1649D;
      var8[var1] = 0.061D;
      var9[var1] = 0.0D;
      var1 = 53;
      var10[var1] = 665;
      var7[var1] = 0.1212D;
      var8[var1] = 0.0446D;
      var9[var1] = 0.0D;
      var1 = 54;
      var10[var1] = 670;
      var7[var1] = 0.0874D;
      var8[var1] = 0.032D;
      var9[var1] = 0.0D;
      var1 = 55;
      var10[var1] = 675;
      var7[var1] = 0.0636D;
      var8[var1] = 0.0232D;
      var9[var1] = 0.0D;
      var1 = 56;
      var10[var1] = 680;
      var7[var1] = 0.0468D;
      var8[var1] = 0.017D;
      var9[var1] = 0.0D;
      var1 = 57;
      var10[var1] = 685;
      var7[var1] = 0.0329D;
      var8[var1] = 0.0119D;
      var9[var1] = 0.0D;
      var1 = 58;
      var10[var1] = 690;
      var7[var1] = 0.0227D;
      var8[var1] = 0.0082D;
      var9[var1] = 0.0D;
      var1 = 59;
      var10[var1] = 695;
      var7[var1] = 0.0158D;
      var8[var1] = 0.0057D;
      var9[var1] = 0.0D;
      var1 = 60;
      var10[var1] = 700;
      var7[var1] = 0.0114D;
      var8[var1] = 0.0041D;
      var9[var1] = 0.0D;
      var1 = 61;
      var10[var1] = 705;
      var7[var1] = 0.0081D;
      var8[var1] = 0.0029D;
      var9[var1] = 0.0D;
      var1 = 62;
      var10[var1] = 710;
      var7[var1] = 0.0058D;
      var8[var1] = 0.0021D;
      var9[var1] = 0.0D;
      var1 = 63;
      var10[var1] = 715;
      var7[var1] = 0.0041D;
      var8[var1] = 0.0015D;
      var9[var1] = 0.0D;
      var1 = 64;
      var10[var1] = 720;
      var7[var1] = 0.0029D;
      var8[var1] = 0.001D;
      var9[var1] = 0.0D;

      int var35;
      for(var35 = 0; var35 <= 46; ++var35) {
         var5[var35] = (int)(50.0D + var2[var35] * 500.0D + 0.5D);
         var6[var35] = (int)((double)((float)nfil) - var3[var35] * 500.0D + 0.5D) - 50;
      }

      int var11 = 0;
      int var12 = 0;

      int var17;
      for(var35 = 0; var35 < nfil; ++var35) {
         for(var17 = 0; var17 < ncol; ++var17) {
            a[var35][var17 * 3] = 0;
            a[var35][var17 * 3 + 1] = 0;
            a[var35][var17 * 3 + 2] = 0;
         }
      }

      var35 = var6[25];
      var17 = var5[25];
      this.colorPunto.x = var2[24];
      this.colorPunto.y = var3[24];
      int var18 = Varios.xyrgb(this.colorPunto);
      a[var35][var17 * 3] = Colores.tablaMonitor[(int)this.colorPunto.r];
      a[var35][var17 * 3 + 1] = Colores.tablaMonitor[(int)this.colorPunto.g];
      a[var35][var17 * 3 + 2] = Colores.tablaMonitor[(int)this.colorPunto.b];
      var35 = var6[0];
      var17 = var5[0];
      this.colorPunto.x = var2[0];
      this.colorPunto.y = var3[0];
      var18 = Varios.xyrgb(this.colorPunto);
      a[var35][var17 * 3] = Colores.tablaMonitor[(int)this.colorPunto.r];
      a[var35][var17 * 3 + 1] = Colores.tablaMonitor[(int)this.colorPunto.g];
      a[var35][var17 * 3 + 2] = Colores.tablaMonitor[(int)this.colorPunto.b];

      for(var35 = var6[25] + 1; var35 < var6[0]; ++var35) {
         int var19;
         int var20;
         int var21;
         int var22;
         int var23;
         for(var19 = 25; var19 >= 0; --var19) {
            if (var6[var19] != var6[var19 + 1]) {
               if (var6[var19] == var35) {
                  var11 = var5[var19];
               } else if (var6[var19] > var35) {
                  var21 = var5[var19];
                  var20 = var5[var19 + 1];
                  var23 = var6[var19];
                  var22 = var6[var19 + 1];
                  var11 = var20 + (int)((double)(var35 - var22) * (double)(var21 - var20) / (double)(var23 - var22));
                  break;
               }
            }
         }

         for(var19 = 25; var19 <= 46; ++var19) {
            if (var6[var19] != var6[var19 - 1]) {
               if (var6[var19] == var35) {
                  var12 = var5[var19];
               } else if (var6[var19] > var35) {
                  var21 = var5[var19];
                  var20 = var5[var19 - 1];
                  var23 = var6[var19];
                  var22 = var6[var19 - 1];
                  var12 = var20 + (int)((double)(var35 - var22) * (double)(var21 - var20) / (double)(var23 - var22));
                  break;
               }
            }
         }

         double var31 = 0.4D;
         double var33 = 0.03D;

         for(var17 = var11; var17 <= var12; ++var17) {
            double var13 = (double)(var17 - 50) / 500.0D;
            double var15 = (double)(nfil - 50 - var35) / 500.0D;
            this.colorPunto.x = var13;
            this.colorPunto.y = var15;
            var18 = Varios.xyrgb(this.colorPunto);
            if (var18 >= 1) {
               int var24 = Colores.tablaMonitor[(int)this.colorPunto.r];
               int var25 = Colores.tablaMonitor[(int)this.colorPunto.g];
               int var26 = Colores.tablaMonitor[(int)this.colorPunto.b];
               double var27 = (var13 - 0.333D) * (var13 - 0.333D) + (var15 - 0.333D) * (var15 - 0.333D);
               double var29 = 1.0D + var31 * Math.exp(-var27 / var33);
               var24 = (int)((double)var24 * var29);
               var25 = (int)((double)var25 * var29);
               var26 = (int)((double)var26 * var29);
               if (var24 > 255) {
                  var24 = 255;
               }

               if (var25 > 255) {
                  var25 = 255;
               }

               if (var26 > 255) {
                  var26 = 255;
               }

               a[var35][var17 * 3] = var24;
               a[var35][var17 * 3 + 1] = var25;
               a[var35][var17 * 3 + 2] = var26;
            }
         }
      }

      for(var17 = 49; var17 <= 50; ++var17) {
         for(var35 = 20; var35 < nfil - 43; ++var35) {
            a[var35][var17 * 3] = 255;
            a[var35][var17 * 3 + 1] = 255;
            a[var35][var17 * 3 + 2] = 0;
         }
      }

      var35 = nfil - 50;

      for(var17 = 43; var17 < ncol - 20; ++var17) {
         a[var35][var17 * 3] = 255;
         a[var35][var17 * 3 + 1] = 255;
         a[var35][var17 * 3 + 2] = 0;
      }

      for(var35 = nfil - 50; var35 > 20; var35 -= 50) {
         for(var17 = 43; var17 < 50; ++var17) {
            a[var35][var17 * 3] = 255;
            a[var35][var17 * 3 + 1] = 255;
            a[var35][var17 * 3 + 2] = 0;
         }
      }

      for(var17 = 50; var17 < ncol - 20; var17 += 50) {
         for(var35 = nfil - 43; var35 > nfil - 50; --var35) {
            a[var35][var17 * 3] = 255;
            a[var35][var17 * 3 + 1] = 255;
            a[var35][var17 * 3 + 2] = 0;
         }
      }

      Varios.DibujaLetra1(this.C0, nfil - 40, 45);
      Varios.DibujaLetra1(this.C0, nfil - 40, 290);
      Varios.DibujaLetra1(this.Cpunto, nfil - 40, 300);
      Varios.DibujaLetra1(this.C5, nfil - 40, 310);
      Varios.DibujaLetra1(this.C0, nfil - 55, 20);
      Varios.DibujaLetra1(this.C0, nfil - 305, 10);
      Varios.DibujaLetra1(this.Cpunto, nfil - 305, 20);
      Varios.DibujaLetra1(this.C5, nfil - 305, 30);
      Varios.DibujaLetra1(this.Cx, nfil - 40, 450);
      Varios.DibujaLetra1(this.Cy, nfil - 470, 20);
      Varios.DibujaLetra2(this.C4, nfil - 70, 85);
      Varios.DibujaLetra2(this.C5, nfil - 70, 95);
      Varios.DibujaLetra2(this.C0, nfil - 70, 105);
      Varios.DibujaLetra2(this.C4, nfil - 125, 60);
      Varios.DibujaLetra2(this.C8, nfil - 125, 70);
      Varios.DibujaLetra2(this.C0, nfil - 125, 80);
      Varios.DibujaLetra2(this.C4, nfil - 195, 20);
      Varios.DibujaLetra2(this.C9, nfil - 195, 30);
      Varios.DibujaLetra2(this.C0, nfil - 195, 40);
      Varios.DibujaLetra2(this.C5, nfil - 330, 20);
      Varios.DibujaLetra2(this.C0, nfil - 330, 30);
      Varios.DibujaLetra2(this.C0, nfil - 330, 40);
      Varios.DibujaLetra2(this.C5, nfil - 420, 20);
      Varios.DibujaLetra2(this.C1, nfil - 420, 30);
      Varios.DibujaLetra2(this.C0, nfil - 420, 40);
      Varios.DibujaLetra2(this.C5, nfil - 480, 70);
      Varios.DibujaLetra2(this.C2, nfil - 480, 80);
      Varios.DibujaLetra2(this.C0, nfil - 480, 90);
      Varios.DibujaLetra2(this.C5, nfil - 470, 130);
      Varios.DibujaLetra2(this.C3, nfil - 470, 140);
      Varios.DibujaLetra2(this.C0, nfil - 470, 150);
      Varios.DibujaLetra2(this.C5, nfil - 445, 170);
      Varios.DibujaLetra2(this.C4, nfil - 445, 180);
      Varios.DibujaLetra2(this.C0, nfil - 445, 190);
      Varios.DibujaLetra2(this.C5, nfil - 415, 200);
      Varios.DibujaLetra2(this.C5, nfil - 415, 210);
      Varios.DibujaLetra2(this.C0, nfil - 415, 220);
      Varios.DibujaLetra2(this.C5, nfil - 380, 240);
      Varios.DibujaLetra2(this.C6, nfil - 380, 250);
      Varios.DibujaLetra2(this.C0, nfil - 380, 260);
      Varios.DibujaLetra2(this.C5, nfil - 340, 280);
      Varios.DibujaLetra2(this.C7, nfil - 340, 290);
      Varios.DibujaLetra2(this.C0, nfil - 340, 300);
      Varios.DibujaLetra2(this.C5, nfil - 310, 310);
      Varios.DibujaLetra2(this.C8, nfil - 310, 320);
      Varios.DibujaLetra2(this.C0, nfil - 310, 330);
      Varios.DibujaLetra2(this.C5, nfil - 280, 340);
      Varios.DibujaLetra2(this.C9, nfil - 280, 350);
      Varios.DibujaLetra2(this.C0, nfil - 280, 360);
      Varios.DibujaLetra2(this.C6, nfil - 250, 370);
      Varios.DibujaLetra2(this.C0, nfil - 250, 380);
      Varios.DibujaLetra2(this.C0, nfil - 250, 390);
      Varios.DibujaLetra2(this.C6, nfil - 215, 405);
      Varios.DibujaLetra2(this.C2, nfil - 215, 415);
      Varios.DibujaLetra2(this.C0, nfil - 215, 425);
      Varios.DibujaLetra2(this.C7, nfil - 190, 430);
      Varios.DibujaLetra2(this.C0, nfil - 190, 440);
      Varios.DibujaLetra2(this.C0, nfil - 190, 450);
      Varios.DibujaPuntoFijo(0.333D, 0.333D, 2);
      Varios.DibujaLetra3(this.CE, nfil - 210, 220);
      Varios.DibujaPuntoFijo(0.447D, 0.407D, 2);
      Varios.DibujaLetra3(this.CA, nfil - 250, 280);
      Varios.DibujaPuntoFijo(0.348D, 0.352D, 2);
      Varios.DibujaLetra3(this.CB, nfil - 225, 235);
      Varios.DibujaPuntoFijo(0.31D, 0.316D, 2);
      Varios.DibujaLetra3(this.Cc, nfil - 200, 200);
      Varios.DibujaPuntoFijo(0.313D, 0.329D, 2);
      Varios.DibujaLetra3(this.CD, nfil - 225, 190);

      for(var35 = 0; var35 < nfil; ++var35) {
         for(var17 = 0; var17 < ncol; ++var17) {
            this.vR = a[var35][var17 * 3];
            this.vG = a[var35][var17 * 3 + 1];
            this.vB = a[var35][var17 * 3 + 2];
            this.v = this.vR << 16 | this.vG << 8 | this.vB;
            this.v |= -16777216;
            buffImage.setRGB(var17, var35, this.v);
         }
      }

      this.setVisible(true);
      return var35;
   }

   static {
      a = new int[nfil][ncol * 3];
      a1a = 10;
      b1a = 10;
      a2a = ncol - 20;
      b2a = nfil - 50;
      buffImage = null;
   }
}
