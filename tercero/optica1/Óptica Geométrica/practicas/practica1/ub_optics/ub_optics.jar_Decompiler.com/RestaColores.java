import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RestaColores extends JPanel {
   static String[] texto00 = new String[]{"Barreja", "Mezcla", "Substractive"};
   static String[] texto01 = new String[]{"Substractiva", "Sustractiva", "Mixture"};
   static String[] texto0 = new String[]{"Intensitat", "Intensidad", "Intensity"};
   static String[] texto1 = new String[]{"Il·luminant", "Iluminante", "Illuminant"};
   static String[] texto2 = new String[]{"Groc", "Amarillo", "Yellow"};
   static String[] texto2a = new String[]{"Cian", "Cian", "Cyan"};
   static String[] texto3 = new String[]{"Il x Mag x Groc", "Il x Mag x Am", "Il. x Mag x Yellow"};
   static String[] texto4 = new String[]{"Il x Mag x Groc x Cian", "Il x Mag x Am x Cian", "Il. x Mag x Yell x Cyan"};
   static String[] texto5 = new String[]{"Resultat", "Resultado", "Result"};
   static String[] texto6 = new String[]{"L. d'ona dom.", "L. de onda dom.", "Dom. Wave L. "};
   static String[] texto7 = new String[]{"Puresa", "Pureza", "Purity"};
   static String[] texto8 = new String[]{"Color problema", "Color problema", "Test colour"};
   static Graphics grafic;
   static int alto = 66;
   static int ancho = 86;
   static int numI = 4;
   static DatosEspectro iluminante = new DatosEspectro();
   static DatosEspectro magenta = new DatosEspectro();
   static DatosEspectro amarillo = new DatosEspectro();
   static DatosEspectro cyan = new DatosEspectro();
   static DatosEspectro productoIM = new DatosEspectro();
   static DatosEspectro productoIMA = new DatosEspectro();
   static DatosEspectro productoIMAC = new DatosEspectro();
   static JLabel fraseNP;
   static JLabel fraseXYZ;
   static JLabel frasexy;
   static JLabel fraseLambda;
   static JLabel frasePur;
   static JLabel fraseIluminante;
   static Cuadro cuadroI;
   static Cuadro cuadroIM;
   static Cuadro cuadroIMA;
   static Cuadro cuadroIMAC;
   static Cuadro cuadroP;
   static CuadroGR cuadroGI;
   static CuadroGR cuadroGM;
   static CuadroGR cuadroGIM;
   static CuadroGR cuadroGA;
   static CuadroGR cuadroGIMA;
   static CuadroGR cuadroGC;
   static CuadroGR cuadroGIMAC;
   static JSlider sliderI = new JSlider();
   static RestaColores.SliderListener listenerI;
   static JSlider sliderM = new JSlider();
   static RestaColores.SliderListener listenerM;
   static JSlider sliderA = new JSlider();
   static RestaColores.SliderListener listenerA;
   static JSlider sliderC = new JSlider();
   static RestaColores.SliderListener listenerC;
   static Cuadro cuadritoI;
   static Cuadro cuadritoM;
   static Cuadro cuadritoA;
   static Cuadro cuadritoC;
   static DatosColor colorProblema = new DatosColor();
   int VR;
   int VG;
   int VB;
   int RR = 50;
   int GG = 50;
   int BB = 50;
   double X = 50.0D;
   double Y = 50.0D;
   double Z = 50.0D;
   double x = 0.333D;
   double y = 0.333D;

   public RestaColores() {
      byte var1 = 2;
      this.setLayout((LayoutManager)null);
      this.setBounds(0, 0, 270, 500);
      this.setBackground(new Color(220, 220, 220));
      grafic = Colores.diagrama.getGraphics();
      JLabel var2 = new JLabel(texto00[Colores.lang]);
      var2.setBounds(2, 2, ancho, 15);
      var2.setForeground(Colores.color);
      var2.setFont(Colores.fuente3);
      this.add(var2);
      JLabel var3 = new JLabel(texto01[Colores.lang]);
      var3.setBounds(2, 18, ancho, 15);
      var3.setForeground(Colores.color);
      var3.setFont(Colores.fuente3);
      this.add(var3);
      JLabel var4 = new JLabel("400 - 700");
      var4.setBounds(2, 40, ancho, 15);
      var4.setForeground(Colores.color);
      var4.setFont(Colores.fuente3);
      this.add(var4);
      JLabel var5 = new JLabel("nm");
      var5.setBounds(20, 50, ancho, 15);
      var5.setForeground(Colores.color);
      var5.setFont(Colores.fuente3);
      this.add(var5);
      cuadroGI = new CuadroGR(iluminante);
      cuadroGI.setBounds(92, var1, ancho, alto);
      cuadroGI.setBackground(Color.white);
      this.add(cuadroGI);
      cuadroI = new Cuadro();
      cuadroI.setBounds(181, var1, ancho, alto);
      this.add(cuadroI);
      fraseIluminante = new JLabel(texto1[Colores.lang]);
      fraseIluminante.setBounds(140, var1 + alto + 3, 100, 15);
      fraseIluminante.setForeground(Colores.color);
      fraseIluminante.setFont(Colores.fuente3);
      this.add(fraseIluminante);
      JLabel var6 = new JLabel(texto0[Colores.lang]);
      var6.setBounds(30, var1 + alto + 3, ancho, 15);
      var6.setForeground(Colores.color);
      var6.setFont(Colores.fuente3);
      this.add(var6);
      cuadritoI = new Cuadro();
      cuadritoI.setBounds(2, var1 + alto + 6, 15, 15);
      cuadritoI.setBackground(Color.white);
      this.add(cuadritoI);
      sliderI.setValue(50);
      sliderI.setMinimum(0);
      sliderI.setMaximum(100);
      sliderI.setBackground(new Color(220, 220, 220));
      sliderI.setBounds(22, var1 + alto + 16, 150, 15);
      listenerI = new RestaColores.SliderListener();
      sliderI.addChangeListener(listenerI);
      this.add(sliderI);
      int var15 = var1 + 100;
      cuadroGM = new CuadroGR(magenta);
      cuadroGM.setBounds(2, var15, ancho, alto);
      cuadroGM.setBackground(Color.white);
      this.add(cuadroGM);
      cuadroGIM = new CuadroGR(productoIM);
      cuadroGIM.setBounds(92, var15, ancho, alto);
      cuadroGIM.setBackground(Color.white);
      this.add(cuadroGIM);
      cuadroIM = new Cuadro();
      cuadroIM.setBounds(181, var15, ancho, alto);
      this.add(cuadroIM);
      JLabel var7 = new JLabel("Magenta");
      var7.setBounds(30, var15 + alto + 3, ancho, 15);
      var7.setForeground(Colores.color);
      var7.setFont(Colores.fuente3);
      this.add(var7);
      JLabel var8 = new JLabel("Il. x Mag.");
      var8.setBounds(100, var15 + alto + 3, 150, 15);
      var8.setForeground(Colores.color);
      var8.setFont(Colores.fuente3);
      this.add(var8);
      cuadritoM = new Cuadro();
      cuadritoM.setBounds(2, var15 + alto + 6, 15, 15);
      cuadritoM.setBackground(Color.magenta);
      this.add(cuadritoM);
      sliderM.setValue(50);
      sliderM.setMinimum(0);
      sliderM.setMaximum(100);
      sliderM.setBackground(new Color(220, 220, 220));
      sliderM.setBounds(22, var15 + alto + 16, 150, 15);
      listenerM = new RestaColores.SliderListener();
      sliderM.addChangeListener(listenerM);
      this.add(sliderM);
      var15 += 100;
      cuadroGA = new CuadroGR(amarillo);
      cuadroGA.setBounds(2, var15, ancho, alto);
      cuadroGA.setBackground(Color.white);
      this.add(cuadroGA);
      cuadroGIMA = new CuadroGR(productoIMA);
      cuadroGIMA.setBounds(92, var15, ancho, alto);
      cuadroGIMA.setBackground(Color.white);
      this.add(cuadroGIMA);
      cuadroIMA = new Cuadro();
      cuadroIMA.setBounds(181, var15, ancho, alto);
      this.add(cuadroIMA);
      JLabel var9 = new JLabel(texto2[Colores.lang]);
      var9.setBounds(30, var15 + alto + 3, ancho, 15);
      var9.setForeground(Colores.color);
      var9.setFont(Colores.fuente3);
      this.add(var9);
      JLabel var10 = new JLabel(texto3[Colores.lang]);
      var10.setBounds(100, var15 + alto + 3, 150, 15);
      var10.setForeground(Colores.color);
      var10.setFont(Colores.fuente3);
      this.add(var10);
      cuadritoA = new Cuadro();
      cuadritoA.setBounds(2, var15 + alto + 6, 15, 15);
      cuadritoA.setBackground(Color.yellow);
      this.add(cuadritoA);
      sliderA.setValue(50);
      sliderA.setMinimum(0);
      sliderA.setMaximum(100);
      sliderA.setBackground(new Color(220, 220, 220));
      sliderA.setBounds(22, var15 + alto + 16, 150, 15);
      listenerA = new RestaColores.SliderListener();
      sliderA.addChangeListener(listenerA);
      this.add(sliderA);
      var15 += 100;
      cuadroGC = new CuadroGR(cyan);
      cuadroGC.setBounds(2, var15, ancho, alto);
      cuadroGC.setBackground(Color.white);
      this.add(cuadroGC);
      cuadroGIMAC = new CuadroGR(productoIMAC);
      cuadroGIMAC.setBounds(92, var15, ancho, alto);
      cuadroGIMAC.setBackground(Color.white);
      this.add(cuadroGIMAC);
      cuadroIMAC = new Cuadro();
      cuadroIMAC.setBounds(181, var15, ancho, alto);
      this.add(cuadroIMAC);
      JLabel var11 = new JLabel(texto2a[Colores.lang]);
      var11.setBounds(30, var15 + alto + 3, ancho, 15);
      var11.setForeground(Colores.color);
      var11.setFont(Colores.fuente3);
      this.add(var11);
      JLabel var12 = new JLabel(texto4[Colores.lang]);
      var12.setBounds(100, var15 + alto + 3, 150, 15);
      var12.setForeground(Colores.color);
      var12.setFont(Colores.fuente3);
      this.add(var12);
      cuadritoC = new Cuadro();
      cuadritoC.setBounds(2, var15 + alto + 6, 15, 15);
      cuadritoC.setBackground(Color.cyan);
      this.add(cuadritoC);
      sliderC.setValue(50);
      sliderC.setMinimum(0);
      sliderC.setMaximum(100);
      sliderC.setBackground(new Color(220, 220, 220));
      sliderC.setBounds(22, var15 + alto + 16, 150, 15);
      listenerC = new RestaColores.SliderListener();
      sliderC.addChangeListener(listenerC);
      this.add(sliderC);
      var15 += 100;
      cuadroP = new Cuadro();
      cuadroP.setBounds(181, var15, ancho, alto);
      this.add(cuadroP);
      JLabel var13 = new JLabel(texto8[Colores.lang]);
      var13.setBounds(170, var15 + alto + 3, 120, 15);
      var13.setForeground(Colores.color);
      var13.setFont(Colores.fuente3);
      this.add(var13);
      fraseNP = new JLabel();
      fraseNP.setBounds(200, var15 + alto + 16, 50, 15);
      fraseNP.setForeground(Colores.color);
      fraseNP.setFont(Colores.fuente3);
      this.add(fraseNP);
      JLabel var14 = new JLabel(texto5[Colores.lang]);
      var14.setBounds(10, var15, ancho * 2, 30);
      var14.setForeground(Colores.color);
      var14.setFont(Colores.fuente1);
      this.add(var14);
      fraseXYZ = new JLabel("X=---  Y=---  Z=---");
      fraseXYZ.setBounds(10, var15 + 15, ancho * 2, 30);
      fraseXYZ.setForeground(Colores.color);
      fraseXYZ.setFont(Colores.fuente3);
      this.add(fraseXYZ);
      frasexy = new JLabel("x = ---    y = ---");
      frasexy.setBounds(10, var15 + 30, ancho * 2, 30);
      frasexy.setForeground(Colores.color);
      frasexy.setFont(Colores.fuente3);
      this.add(frasexy);
      fraseLambda = new JLabel(texto6[Colores.lang] + ": ---");
      fraseLambda.setBounds(10, var15 + 45, ancho * 2, 30);
      fraseLambda.setForeground(Colores.color);
      fraseLambda.setFont(Colores.fuente3);
      this.add(fraseLambda);
      frasePur = new JLabel(texto7[Colores.lang] + ": ---");
      frasePur.setBounds(10, var15 + 60, ancho * 2, 30);
      frasePur.setForeground(Colores.color);
      frasePur.setFont(Colores.fuente3);
      this.add(frasePur);
   }

   static void dibujaGrafica(Graphics var0, DatosEspectro var1) {
      int[] var3 = new int[var1.numDatosEspectro];
      int[] var4 = new int[var1.numDatosEspectro];
      var0.setColor(Colores.color);

      int var2;
      for(var2 = 0; var2 < var1.numDatosEspectro; ++var2) {
         var3[var2] = 2 + (int)((var1.xg[var2] - 400.0D) * (double)(ancho - 4) / 300.0D);
         var4[var2] = alto - 2 - (int)(var1.yg[var2] * (double)(alto - 4));
      }

      for(var2 = 1; var2 < var1.numDatosEspectro; ++var2) {
         var0.drawLine(var3[var2 - 1], var4[var2 - 1], var3[var2], var4[var2]);
      }

   }

   static void calculaTodo(int var0) {
      String var4 = "C";
      if (numI >= 0 && numI <= 4) {
         int var1;
         label99: {
            double var2;
            label100: {
               switch(var0) {
               case 0:
                  switch(numI) {
                  case 0:
                     var4 = "A";
                     break;
                  case 1:
                     var4 = "B";
                     break;
                  case 2:
                     var4 = "C";
                     break;
                  case 3:
                     var4 = "D";
                     break;
                  case 4:
                     var4 = "E";
                  }

                  fraseIluminante.setText(texto1[Colores.lang] + " " + var4);
                  int var5 = (int)(Math.random() * 30.0D);
                  boolean var6 = false;
                  boolean var7 = false;
                  boolean var8 = false;
                  int var29 = Colores.problemaR[var5] * 255 / 300;
                  int var30 = Colores.problemaG[var5] * 255 / 300;
                  int var31 = Colores.problemaB[var5] * 255 / 300;
                  colorProblema.R = (double)var29;
                  colorProblema.G = (double)var30;
                  colorProblema.B = (double)var31;
                  var29 = Colores.tablaMonitor[var29];
                  var30 = Colores.tablaMonitor[var30];
                  var31 = Colores.tablaMonitor[var31];
                  fraseNP.setText("nº " + var5);
                  cuadroP.setBackground(new Color(var29, var30, var31));
                  Varios.RGBXYZ(colorProblema);
                  double var9 = colorProblema.X + colorProblema.Y + colorProblema.Z;
                  colorProblema.x = colorProblema.X / var9;
                  colorProblema.y = colorProblema.Y / var9;
                  grafic = Colores.diagrama.getGraphics();
               case 1:
                  var2 = (double)sliderI.getValue() * 0.01D;
                  iluminante.numDatosEspectro = Colores.iluminante[numI].numDatosEspectro;

                  for(var1 = 0; var1 < iluminante.numDatosEspectro; ++var1) {
                     iluminante.datoColorL[var1] = Colores.iluminante[numI].datoColorL[var1];
                     iluminante.datoColorY[var1] = Colores.iluminante[numI].datoColorY[var1] * var2 + 0.001D;
                  }

                  Varios.CalculaXYZ(iluminante);
                  Varios.XYZRGB(iluminante);
                  Varios.normalizaRGB(iluminante);
                  cuadroI.setBackground(new Color(iluminante.VR, iluminante.VG, iluminante.VB));
                  Varios.preparaGrafica(iluminante);
                  cuadroGI.repaint();
               case 2:
                  var2 = (double)sliderM.getValue() * 0.01D;
                  magenta.numDatosEspectro = Colores.espectro[0].numDatosEspectro;

                  for(var1 = 0; var1 < magenta.numDatosEspectro; ++var1) {
                     magenta.datoColorL[var1] = Colores.espectro[0].datoColorL[var1];
                     magenta.datoColorY[var1] = 1.0D - (1.0D - Colores.espectro[0].datoColorY[var1]) * var2 + 0.001D;
                  }

                  Varios.IluminantexFiltro(iluminante, magenta, productoIM);
                  Varios.CalculaXYZ(productoIM);
                  Varios.XYZRGB(productoIM);
                  Varios.normalizaRGB(productoIM);
                  cuadroIM.setBackground(new Color(productoIM.VR, productoIM.VG, productoIM.VB));
                  Varios.preparaGrafica(magenta);
                  cuadroGM.repaint();
                  Varios.preparaGrafica(productoIM);
                  cuadroGIM.repaint();
               case 3:
                  break;
               case 4:
                  break label100;
               default:
                  break label99;
               }

               var2 = (double)sliderA.getValue() * 0.01D;
               amarillo.numDatosEspectro = Colores.espectro[1].numDatosEspectro;

               for(var1 = 0; var1 < amarillo.numDatosEspectro; ++var1) {
                  amarillo.datoColorL[var1] = Colores.espectro[1].datoColorL[var1];
                  amarillo.datoColorY[var1] = 1.0D - (1.0D - Colores.espectro[1].datoColorY[var1]) * var2 + 0.001D;
               }

               Varios.IluminantexFiltro(productoIM, amarillo, productoIMA);
               Varios.CalculaXYZ(productoIMA);
               Varios.XYZRGB(productoIMA);
               Varios.normalizaRGB(productoIMA);
               cuadroIMA.setBackground(new Color(productoIMA.VR, productoIMA.VG, productoIMA.VB));
               Varios.preparaGrafica(amarillo);
               cuadroGA.repaint();
               Varios.preparaGrafica(productoIMA);
               cuadroGIMA.repaint();
            }

            var2 = (double)sliderC.getValue() * 0.01D;
            cyan.numDatosEspectro = Colores.espectro[2].numDatosEspectro;

            for(var1 = 0; var1 < cyan.numDatosEspectro; ++var1) {
               cyan.datoColorL[var1] = Colores.espectro[2].datoColorL[var1];
               cyan.datoColorY[var1] = 1.0D - (1.0D - Colores.espectro[2].datoColorY[var1]) * var2 + 0.001D;
            }

            Varios.IluminantexFiltro(productoIMA, cyan, productoIMAC);
            Varios.CalculaXYZ(productoIMAC);
            Varios.XYZRGB(productoIMAC);
            Varios.normalizaRGB(productoIMAC);
            Varios.preparaGrafica(cyan);
            cuadroGC.repaint();
            Varios.preparaGrafica(productoIMAC);
            cuadroGIMAC.repaint();
         }

         cuadroIMAC.setBackground(new Color(productoIMAC.VR, productoIMAC.VG, productoIMAC.VB));
         if (var0 == 0) {
            fraseXYZ.setText("X=---  Y=---  Z=---");
            frasexy.setText("x = ---    y = ---");
            fraseLambda.setText(texto6[Colores.lang] + ": ---");
            frasePur.setText(texto7[Colores.lang] + ": ---");
            grafic.drawImage(Diagrama.buffImage, 20, 20, Diagrama.ncol - 20, Diagrama.nfil - 20, 20, 20, Diagrama.ncol - 20, Diagrama.nfil - 20, Colores.diagrama);
         } else {
            double var11 = (double)((int)(productoIMAC.X * 10.0D)) / 10.0D;
            double var13 = (double)((int)(productoIMAC.Y * 10.0D)) / 10.0D;
            double var15 = (double)((int)(productoIMAC.Z * 10.0D)) / 10.0D;
            fraseXYZ.setText("X=" + var11 + "  Y=" + var13 + "  Z=" + var15);
            double var21 = productoIMAC.X + productoIMAC.Y + productoIMAC.Z;
            double var17 = productoIMAC.X / var21;
            double var19 = productoIMAC.Y / var21;
            double var23 = (double)((int)(1000.0D * var17)) / 1000.0D;
            double var25 = (double)((int)(1000.0D * var19)) / 1000.0D;
            frasexy.setText("x = " + var23 + "    y = " + var25);
            var1 = Varios.OndaDominante(productoIMAC);
            if (var1 < 1) {
               productoIMAC.ondaDominante = 0;
            }

            double var27 = (double)((int)(100.0D * productoIMAC.pureza)) / 100.0D;
            if (grafic != null && var27 >= 0.01D) {
               Varios.DibujaLinea(grafic, productoIMAC.xd, productoIMAC.yd, productoIMAC.xm, productoIMAC.ym);
               Varios.DibujaPunto(grafic, var17, var19, 3);
               Varios.DibujaPuntoHueco(grafic, colorProblema.x, colorProblema.y, 3);
            } else {
               grafic.drawImage(Diagrama.buffImage, 20, 20, Diagrama.ncol - 20, Diagrama.nfil - 20, 20, 20, Diagrama.ncol - 20, Diagrama.nfil - 20, Colores.diagrama);
            }

            if (var27 < 0.01D) {
               fraseLambda.setText(texto6[Colores.lang] + ": ---");
               frasePur.setText(texto7[Colores.lang] + ": ---");
            } else {
               fraseLambda.setText(texto6[Colores.lang] + ": " + productoIMAC.ondaDominante + " nm");
               frasePur.setText(texto7[Colores.lang] + ": " + var27);
            }

         }
      }
   }

   class SliderListener implements ChangeListener {
      int i;

      public SliderListener() {
      }

      public void stateChanged(ChangeEvent var1) {
         JSlider var2 = (JSlider)var1.getSource();
         if (var2 == RestaColores.sliderI) {
            RestaColores.calculaTodo(1);
         }

         if (var2 == RestaColores.sliderM) {
            RestaColores.calculaTodo(2);
         }

         if (var2 == RestaColores.sliderA) {
            RestaColores.calculaTodo(3);
         }

         if (var2 == RestaColores.sliderC) {
            RestaColores.calculaTodo(4);
         }

      }
   }
}
