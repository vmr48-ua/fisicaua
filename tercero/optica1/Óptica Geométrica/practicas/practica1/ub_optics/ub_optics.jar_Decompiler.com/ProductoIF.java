import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProductoIF extends JPanel {
   static String[] textoF = new String[]{"Filtre", "Filtro", "Filter"};
   static String[] textoI = new String[]{"Il·luminant ", "Iluminante ", "Illuminant "};
   static String[] textoPr = new String[]{"Producte", "Producto", "Product"};
   static String[] textoL = new String[]{"L. O. D. ", "L. O. D. ", "D. W. L. "};
   static String[] textoPu = new String[]{"Puresa", "Pureza", "Purity"};
   static Font fuente1;
   static Font fuente2;
   static Font fuente3;
   static Font fuente4;
   static Color color;
   static Graphics grafic;
   int numFiltro;
   int numIluminante;
   static CuadroGP cuadroGI;
   static CuadroGP cuadroGF;
   static CuadroGP cuadroGP;
   static Cuadro cuadroFI;
   static Cuadro cuadroFF;
   static Cuadro cuadroFP;
   static JLabel fraseNI;
   static JLabel fraseNF;
   static JLabel fraseNP;
   static JLabel frasexyI;
   static JLabel frasexyF;
   static JLabel frasexyP;
   static JLabel fraseLambdaI;
   static JLabel fraseLambdaF;
   static JLabel fraseLambdaP;
   static JLabel frasePurI;
   static JLabel frasePurF;
   static JLabel frasePurP;

   public ProductoIF() {
      byte var1 = 0;
      short var2 = 170;
      this.setLayout((LayoutManager)null);
      this.setBounds(0, 0, 270, 500);
      cuadroGI = new CuadroGP(Colores.iluminante[2]);
      cuadroGI.setBounds(6, 10 + var1, 128, 128);
      cuadroGI.setBackground(Color.white);
      this.add(cuadroGI);
      cuadroFI = new Cuadro();
      cuadroFI.setBounds(140, 10 + var1, 120, 50);
      this.add(cuadroFI);
      fraseNI = new JLabel(textoI[Colores.lang]);
      fraseNI.setBounds(140, 70 + var1, 130, 20);
      fraseNI.setForeground(color);
      fraseNI.setFont(fuente3);
      this.add(fraseNI);
      frasexyI = new JLabel("");
      frasexyI.setBounds(140, 90 + var1, 130, 20);
      frasexyI.setForeground(color);
      frasexyI.setFont(fuente3);
      this.add(frasexyI);
      fraseLambdaI = new JLabel("");
      fraseLambdaI.setBounds(140, 110 + var1, 130, 20);
      fraseLambdaI.setForeground(color);
      fraseLambdaI.setFont(fuente3);
      this.add(fraseLambdaI);
      frasePurI = new JLabel("");
      frasePurI.setBounds(140, 130 + var1, 130, 20);
      frasePurI.setForeground(color);
      frasePurI.setFont(fuente3);
      this.add(frasePurI);
      int var3 = var1 + var2;
      cuadroGF = new CuadroGP(Colores.espectro[4]);
      cuadroGF.setBounds(6, 10 + var3, 128, 128);
      cuadroGF.setBackground(Color.white);
      this.add(cuadroGF);
      cuadroFF = new Cuadro();
      cuadroFF.setBounds(140, 10 + var3, 120, 50);
      this.add(cuadroFF);
      fraseNF = new JLabel(textoF[Colores.lang]);
      fraseNF.setBounds(140, 70 + var3, 130, 20);
      fraseNF.setForeground(color);
      fraseNF.setFont(fuente3);
      this.add(fraseNF);
      frasexyF = new JLabel("");
      frasexyF.setBounds(140, 90 + var3, 130, 20);
      frasexyF.setForeground(color);
      frasexyF.setFont(fuente3);
      this.add(frasexyF);
      fraseLambdaF = new JLabel("");
      fraseLambdaF.setBounds(140, 110 + var3, 130, 20);
      fraseLambdaF.setForeground(color);
      fraseLambdaF.setFont(fuente3);
      this.add(fraseLambdaF);
      frasePurF = new JLabel("");
      frasePurF.setBounds(140, 130 + var3, 130, 20);
      frasePurF.setForeground(color);
      frasePurF.setFont(fuente3);
      this.add(frasePurF);
      var3 += var2;
      cuadroGP = new CuadroGP(Colores.colorProducto[0]);
      cuadroGP.setBounds(6, 10 + var3, 128, 128);
      cuadroGP.setBackground(Color.white);
      this.add(cuadroGP);
      cuadroFP = new Cuadro();
      cuadroFP.setBounds(140, 10 + var3, 120, 50);
      this.add(cuadroFP);
      fraseNP = new JLabel(textoPr[Colores.lang]);
      fraseNP.setBounds(140, 70 + var3, 130, 20);
      fraseNP.setForeground(color);
      fraseNP.setFont(fuente3);
      this.add(fraseNP);
      frasexyP = new JLabel("");
      frasexyP.setBounds(140, 90 + var3, 130, 20);
      frasexyP.setForeground(color);
      frasexyP.setFont(fuente3);
      this.add(frasexyP);
      fraseLambdaP = new JLabel("");
      fraseLambdaP.setBounds(140, 110 + var3, 130, 20);
      fraseLambdaP.setForeground(color);
      fraseLambdaP.setFont(fuente3);
      this.add(fraseLambdaP);
      frasePurP = new JLabel("");
      frasePurP.setBounds(140, 130 + var3, 130, 20);
      frasePurP.setForeground(color);
      frasePurP.setFont(fuente3);
      this.add(frasePurP);
   }

   static void calculaDatosProducto(int var0, int var1) {
      String var2 = "C";
      switch(var0) {
      case 0:
         var2 = "A";
         break;
      case 1:
         var2 = "B";
         break;
      case 2:
         var2 = "C";
         break;
      case 3:
         var2 = "D";
         break;
      case 4:
         var2 = "E";
      }

      grafic = Colores.diagrama.getGraphics();
      Varios.CalculaXYZ(Colores.iluminante[var0]);
      Varios.XYZRGB(Colores.iluminante[var0]);
      Varios.normalizaRGB(Colores.iluminante[var0]);
      Varios.CalculaXYZ(Colores.espectro[var1]);
      Varios.XYZRGB(Colores.espectro[var1]);
      Varios.normalizaRGB(Colores.espectro[var1]);
      Varios.IluminantexFiltro(Colores.iluminante[var0], Colores.espectro[var1], Colores.colorProducto[0]);
      fraseNI = new JLabel(textoI[Colores.lang] + var2);
      int var9 = Colores.iluminante[var0].VR;
      int var10 = Colores.iluminante[var0].VG;
      int var11 = Colores.iluminante[var0].VB;
      cuadroFI.setBackground(new Color(var9, var10, var11));
      cuadroGI.colorGrafica = Colores.iluminante[var0];
      Varios.preparaGrafica(Colores.iluminante[var0]);
      double var3 = (double)((int)(Colores.iluminante[var0].x * 1000.0D)) / 1000.0D;
      double var5 = (double)((int)(Colores.iluminante[var0].y * 1000.0D)) / 1000.0D;
      frasexyI.setText("x = " + var3 + "  y = " + var5);
      int var12 = Colores.iluminante[var0].ondaDominante;
      fraseLambdaI.setText(textoL[Colores.lang] + ": " + var12 + " nm");
      double var7 = (double)((int)(Colores.iluminante[var0].pureza * 100.0D)) / 100.0D;
      frasePurI.setText(textoPu[Colores.lang] + ": " + var7);
      var9 = Colores.espectro[var1].VR;
      var10 = Colores.espectro[var1].VG;
      var11 = Colores.espectro[var1].VB;
      cuadroFF.setBackground(new Color(var9, var10, var11));
      cuadroGF.colorGrafica = Colores.espectro[var1];
      Varios.preparaGrafica(Colores.espectro[var1]);
      var3 = (double)((int)(Colores.espectro[var1].x * 1000.0D)) / 1000.0D;
      var5 = (double)((int)(Colores.espectro[var1].y * 1000.0D)) / 1000.0D;
      frasexyF.setText("x = " + var3 + "  y = " + var5);
      var12 = Colores.espectro[var1].ondaDominante;
      fraseLambdaF.setText(textoL[Colores.lang] + ": " + var12 + " nm");
      var7 = (double)((int)(Colores.espectro[var1].pureza * 100.0D)) / 100.0D;
      frasePurF.setText(textoPu[Colores.lang] + ": " + var7);
      if (grafic != null && var7 >= 0.01D) {
         Varios.DibujaLineaSB(grafic, Colores.espectro[var1].xd, Colores.espectro[var1].yd, Colores.espectro[var1].xm, Colores.espectro[var1].ym);
         Varios.DibujaPunto(grafic, Colores.espectro[var1].x, Colores.espectro[var1].y, 3);
      }

      var9 = Colores.colorProducto[0].VR;
      var10 = Colores.colorProducto[0].VG;
      var11 = Colores.colorProducto[0].VB;
      cuadroFP.setBackground(new Color(var9, var10, var11));
      cuadroGP.colorGrafica = Colores.colorProducto[0];
      Varios.preparaGrafica(Colores.colorProducto[0]);
      var3 = (double)((int)(Colores.colorProducto[0].x * 1000.0D)) / 1000.0D;
      var5 = (double)((int)(Colores.colorProducto[0].y * 1000.0D)) / 1000.0D;
      frasexyP.setText("x = " + var3 + "  y = " + var5);
      var12 = Colores.colorProducto[0].ondaDominante;
      fraseLambdaP.setText(textoL[Colores.lang] + ": " + var12 + " nm");
      var7 = (double)((int)(Colores.colorProducto[0].pureza * 100.0D)) / 100.0D;
      frasePurP.setText(textoPu[Colores.lang] + ": " + var7);
      if (grafic != null && var7 >= 0.01D) {
         Varios.DibujaLineaSB(grafic, Colores.colorProducto[0].xd, Colores.colorProducto[0].yd, Colores.colorProducto[0].xm, Colores.colorProducto[0].ym);
         Varios.DibujaPunto(grafic, Colores.colorProducto[0].x, Colores.colorProducto[0].y, 3);
      }

   }

   static void dibujaGrafica(Graphics var0, DatosEspectro var1) {
      int[] var3 = new int[var1.numDatosEspectro];
      int[] var4 = new int[var1.numDatosEspectro];
      var0.setFont(fuente4);
      var0.setColor(color);
      var0.drawRect(18, 5, 100, 100);
      var0.drawLine(13, 5, 18, 5);
      var0.drawLine(13, 105, 18, 105);
      var0.drawLine(15, 55, 18, 55);
      var0.drawString("1.0", 3, 13);
      var0.drawString("0.0", 3, 103);
      var0.drawLine(18, 105, 18, 110);
      var0.drawLine(51, 105, 51, 110);
      var0.drawLine(84, 105, 84, 110);
      var0.drawLine(118, 105, 118, 110);
      var0.drawString("400", 8, 120);
      var0.drawString("500", 41, 120);
      var0.drawString("600", 74, 120);
      var0.drawString("700", 107, 120);

      int var2;
      for(var2 = 0; var2 < var1.numDatosEspectro; ++var2) {
         var3[var2] = (int)((var1.xg[var2] - 400.0D) * 98.0D / 300.0D) + 20;
         var4[var2] = 102 - (int)(var1.yg[var2] * 96.0D);
      }

      for(var2 = 1; var2 < var1.numDatosEspectro; ++var2) {
         var0.drawLine(var3[var2 - 1], var4[var2 - 1], var3[var2], var4[var2]);
      }

   }

   static {
      fuente1 = Colores.fuente1;
      fuente2 = Colores.fuente2;
      fuente3 = Colores.fuente3;
      fuente4 = Colores.fuente4;
      color = Colores.color;
   }
}
