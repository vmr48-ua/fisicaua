import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SumaColores extends JPanel {
   static String[] texto0 = new String[]{"Barreja additiva", "Mezcla aditiva", "Additive mixture"};
   static String[] texto1 = new String[]{"Color problema", "Color problema", "Test colour"};
   static String[] texto2 = new String[]{"Long. d'ona dominant", "Long. de onda dominante", "Dominant wave length"};
   static String[] texto3 = new String[]{"Puresa", "Pureza", "Purity"};
   static Cuadro cuadroP;
   static Cuadro cuadroS;
   static Cuadro cuadroR;
   static Cuadro cuadroG;
   static Cuadro cuadroB;
   static JLabel fraseNP;
   static JLabel fraseRGB;
   static JLabel fraseXYZ;
   static JLabel frasexy;
   static JLabel fraseLambda;
   static JLabel frasePur;
   static JSlider sliderR = new JSlider();
   static SumaColores.SliderListener listenerR;
   static JSlider sliderG = new JSlider();
   static SumaColores.SliderListener listenerG;
   static JSlider sliderB = new JSlider();
   static SumaColores.SliderListener listenerB;
   Graphics grafic;
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
   double xP;
   double yP;
   DatosColor colorResultante = new DatosColor();
   DatosColor colorProblema = new DatosColor();

   public SumaColores() {
      this.setLayout((LayoutManager)null);
      this.setBounds(0, 0, 270, 500);
      this.setBackground(new Color(220, 220, 220));
      JLabel var1 = new JLabel(texto0[Colores.lang]);
      var1.setBounds(20, 10, 250, 30);
      var1.setForeground(Colores.color);
      var1.setFont(Colores.fuente0);
      this.add(var1);
      cuadroP = new Cuadro();
      cuadroP.setBounds(20, 50, 100, 100);
      this.add(cuadroP);
      JLabel var2 = new JLabel(texto1[Colores.lang]);
      var2.setBounds(10, 190, 200, 30);
      var2.setForeground(Colores.color);
      var2.setFont(Colores.fuente2);
      this.add(var2);
      fraseNP = new JLabel();
      fraseNP.setBounds(10, 220, 200, 30);
      fraseNP.setForeground(Colores.color);
      fraseNP.setFont(Colores.fuente2);
      this.add(fraseNP);
      this.VR = this.VG = this.VB = Colores.tablaMonitor[128];
      cuadroS = new Cuadro();
      cuadroS.setBounds(140, 50, 100, 100);
      cuadroS.setBackground(new Color(this.VR, this.VG, this.VB));
      this.add(cuadroS);
      cuadroR = new Cuadro();
      cuadroR.setBounds(140, 160, 25, 25);
      cuadroR.setBackground(Color.red);
      this.add(cuadroR);
      sliderR.setValue(50);
      sliderR.setMinimum(0);
      sliderR.setMaximum(100);
      sliderR.setOrientation(1);
      sliderR.setBackground(new Color(220, 220, 220));
      sliderR.setBounds(145, 185, 25, 200);
      listenerR = new SumaColores.SliderListener();
      sliderR.addChangeListener(listenerR);
      this.add(sliderR);
      cuadroG = new Cuadro();
      cuadroG.setBounds(180, 160, 25, 25);
      cuadroG.setBackground(Color.green);
      this.add(cuadroG);
      sliderG.setValue(50);
      sliderG.setMinimum(0);
      sliderG.setMaximum(100);
      sliderG.setOrientation(1);
      sliderG.setBackground(new Color(220, 220, 220));
      sliderG.setBounds(185, 185, 25, 200);
      listenerG = new SumaColores.SliderListener();
      sliderG.addChangeListener(listenerG);
      this.add(sliderG);
      cuadroB = new Cuadro();
      cuadroB.setBounds(220, 160, 25, 25);
      cuadroB.setBackground(Color.blue);
      this.add(cuadroB);
      sliderB.setValue(50);
      sliderB.setMinimum(0);
      sliderB.setMaximum(100);
      sliderB.setOrientation(1);
      sliderB.setBackground(new Color(220, 220, 220));
      sliderB.setBounds(225, 185, 25, 200);
      listenerB = new SumaColores.SliderListener();
      sliderB.addChangeListener(listenerB);
      this.add(sliderB);
      fraseRGB = new JLabel("R = " + this.RR + "    G = " + this.GG + "    B = " + this.BB);
      fraseRGB.setBounds(20, 380, 250, 30);
      fraseRGB.setForeground(Colores.color);
      fraseRGB.setFont(Colores.fuente2);
      this.add(fraseRGB);
      fraseXYZ = new JLabel("X = " + this.X + "    Y = " + this.Y + "    Z = " + this.Z);
      fraseXYZ.setBounds(20, 400, 250, 30);
      fraseXYZ.setForeground(Colores.color);
      fraseXYZ.setFont(Colores.fuente2);
      this.add(fraseXYZ);
      frasexy = new JLabel("x = " + this.x + "    y = " + this.y);
      frasexy.setBounds(20, 420, 250, 30);
      frasexy.setForeground(Colores.color);
      frasexy.setFont(Colores.fuente2);
      this.add(frasexy);
      fraseLambda = new JLabel(texto2[Colores.lang] + ": ---");
      fraseLambda.setBounds(20, 440, 250, 30);
      fraseLambda.setForeground(Colores.color);
      fraseLambda.setFont(Colores.fuente2);
      this.add(fraseLambda);
      frasePur = new JLabel(texto3[Colores.lang] + ": 0");
      frasePur.setBounds(20, 460, 250, 30);
      frasePur.setForeground(Colores.color);
      frasePur.setFont(Colores.fuente2);
      this.add(frasePur);
   }

   class SliderListener implements ChangeListener {
      int i;

      public SliderListener() {
      }

      public void stateChanged(ChangeEvent var1) {
         JSlider var2 = (JSlider)var1.getSource();
         double var3;
         if (var2 == SumaColores.sliderR) {
            SumaColores.this.RR = var2.getValue();
            var3 = (double)SumaColores.this.RR * 2.5D;
            SumaColores.this.VR = Colores.tablaMonitor[(int)var3];
            SumaColores.fraseRGB.setText("R = " + SumaColores.this.RR + "    G = " + SumaColores.this.GG + "    B = " + SumaColores.this.BB);
         }

         if (var2 == SumaColores.sliderG) {
            SumaColores.this.GG = var2.getValue();
            var3 = (double)SumaColores.this.GG * 2.5D;
            SumaColores.this.VG = Colores.tablaMonitor[(int)var3];
            SumaColores.fraseRGB.setText("R = " + SumaColores.this.RR + "    G = " + SumaColores.this.GG + "    B = " + SumaColores.this.BB);
         }

         if (var2 == SumaColores.sliderB) {
            SumaColores.this.BB = var2.getValue();
            var3 = (double)SumaColores.this.BB * 2.5D;
            SumaColores.this.VB = Colores.tablaMonitor[(int)var3];
            SumaColores.fraseRGB.setText("R = " + SumaColores.this.RR + "    G = " + SumaColores.this.GG + "    B = " + SumaColores.this.BB);
         }

         SumaColores.cuadroS.setBackground(new Color(SumaColores.this.VR, SumaColores.this.VG, SumaColores.this.VB));
         SumaColores.this.colorResultante.R = (double)SumaColores.this.RR;
         SumaColores.this.colorResultante.G = (double)SumaColores.this.GG;
         SumaColores.this.colorResultante.B = (double)SumaColores.this.BB;
         Varios.RGBXYZ(SumaColores.this.colorResultante);
         SumaColores.this.X = (double)((int)(SumaColores.this.colorResultante.X * 10.0D)) / 10.0D;
         SumaColores.this.Y = (double)((int)(SumaColores.this.colorResultante.Y * 10.0D)) / 10.0D;
         SumaColores.this.Z = (double)((int)(SumaColores.this.colorResultante.Z * 10.0D)) / 10.0D;
         SumaColores.fraseXYZ.setText("X = " + SumaColores.this.X + "    Y = " + SumaColores.this.Y + "    Z = " + SumaColores.this.Z);
         double var5 = SumaColores.this.colorResultante.X + SumaColores.this.colorResultante.Y + SumaColores.this.colorResultante.Z;
         SumaColores.this.x = SumaColores.this.colorResultante.X / var5;
         SumaColores.this.y = SumaColores.this.colorResultante.Y / var5;
         double var7 = (double)((int)(1000.0D * SumaColores.this.x)) / 1000.0D;
         double var9 = (double)((int)(1000.0D * SumaColores.this.y)) / 1000.0D;
         SumaColores.frasexy.setText("x = " + var7 + "    y = " + var9);
         SumaColores.this.colorResultante.x = SumaColores.this.x;
         SumaColores.this.colorResultante.y = SumaColores.this.y;
         this.i = Varios.OndaDominante(SumaColores.this.colorResultante);
         if (this.i < 1) {
            SumaColores.this.colorResultante.ondaDominante = 0;
         }

         double var11 = (double)((int)(100.0D * SumaColores.this.colorResultante.pureza)) / 100.0D;
         if (SumaColores.this.grafic != null) {
            if (var11 >= 0.01D) {
               Varios.DibujaLinea(SumaColores.this.grafic, SumaColores.this.colorResultante.xd, SumaColores.this.colorResultante.yd, SumaColores.this.colorResultante.xm, SumaColores.this.colorResultante.ym);
               Varios.DibujaPunto(SumaColores.this.grafic, SumaColores.this.x, SumaColores.this.y, 3);
               Varios.DibujaPuntoHueco(SumaColores.this.grafic, SumaColores.this.colorProblema.x, SumaColores.this.colorProblema.y, 3);
            } else {
               SumaColores.this.grafic.drawImage(Diagrama.buffImage, 20, 20, Diagrama.ncol - 20, Diagrama.nfil - 20, 20, 20, Diagrama.ncol - 20, Diagrama.nfil - 20, Colores.diagrama);
            }

            if (var11 < 0.01D) {
               SumaColores.fraseLambda.setText(SumaColores.texto2[Colores.lang] + ": ---");
            } else {
               SumaColores.fraseLambda.setText(SumaColores.texto2[Colores.lang] + ": " + SumaColores.this.colorResultante.ondaDominante + " nm");
            }

            SumaColores.frasePur.setText(SumaColores.texto3[Colores.lang] + ": " + var11);
         }
      }
   }
}
