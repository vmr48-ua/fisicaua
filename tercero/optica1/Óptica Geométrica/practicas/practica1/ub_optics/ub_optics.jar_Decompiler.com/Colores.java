import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Colores extends JApplet {
   static String[] texto0 = new String[]{"    =====    C O L O R I M E T R I A    =====", "    =====    C O L O R I M E T R I A    =====", "    =====    C O L O R I M E T R Y    ====="};
   static String[] textoMA = new String[]{"Barreja additiva", "Mezcla aditiva", "Additive mixture"};
   static String[] textoMS = new String[]{"Barreja substractiva", "Mezcla sustractiva", "Substractive mixture"};
   static String[] textoFI = new String[]{"Filtre x Il·luminant", "Filtro x Iluminante", "Filter x Illuminant"};
   static String[] textoSA = new String[]{"Sortida", "Salida", "Exit"};
   static String[] textoAD = new String[]{"En quant a", "Acerca de", "About"};
   static String[][] acerca_etiqueta = new String[][]{{"Acceptar", "Aceptar", "OK"}, {"Applet de Colorimetria versió 1.0 \n\nGrup d'Innovació Docent en Òptica Física i Fotònica \nUniversitat de Barcelona, 2003 \nLa utilització d'aquest programa està sota una llicència de Creative Commons \nVeure condicions a http://www.publicacions.ub.es/doi/licencia/resum-deriv.htm\n\nCurs d'Òptica en Java DOI: 10.1344/401.000000050 \nApplet de Colorimetria DOI: 10.1344/203.000000098", "Applet de Colorimetría versión 1.0 \n\nGrup d'Innovació Docent en Òptica Física i Fotònica \nUniversitat de Barcelona, 2003 \nLa utilización de este programa está bajo una licencia de Creative Commons \nVer condiciones en http://creativecommons.org/license/by-nc-sa/2.0/ \n \nCurso de Óptica en Java DOI: 10.1344/401.000000050 \nApplet de Colorimetría DOI: 10.1344/203.000000098", "Colorimetry Applet version 1.0 \n\nGrup d'Innovació Docent en Òptica Física i Fotònica \nUniversitat de Barcelona, 2003 \nThe use of this program is under a Creative Commons license \nSee conditions in http://creativecommons.org/license/by-nc-sa/2.0/ \n \nJava Optics Course DOI: 10.1344/401.000000050 \n Colorimetry Applet DOI: 10.1344/203.000000098"}};
   boolean isStandalone = false;
   static PanelBase panelBase;
   static PanelDiagrama panelDiagrama;
   static Diagrama diagrama;
   static SumaColores sumaColores;
   static RestaColores restaColores;
   static PanelElegir1 panelElegir1;
   static PanelElegir2 panelElegir2;
   static ProductoIF productoIF;
   static int ancho = 750;
   static int alto = 550;
   static int lang = 0;
   static int MAXESPECTROS = 5;
   static TablaXYT tablaXYT;
   static TablaXY tablaXY;
   static int[] tablaMonitor = new int[256];
   static int[] problemaR = new int[30];
   static int[] problemaG = new int[30];
   static int[] problemaB = new int[30];
   static int numColorProb;
   static DatosEspectro[] espectro = new DatosEspectro[9];
   static DatosEspectro[] iluminante = new DatosEspectro[5];
   static DatosEspectro[] colorProducto = new DatosEspectro[3];
   static Font fuente0 = new Font("Dialog", 1, 24);
   static Font fuente1 = new Font("Dialog", 1, 14);
   static Font fuente2 = new Font("Dialog", 1, 12);
   static Font fuente3 = new Font("Dialog", 1, 11);
   static Font fuente4 = new Font("Dialog", 1, 8);
   static Color color;

   public void init() {
      try {
         lang = Integer.valueOf(this.getParameter("llengua"));
      } catch (Exception var2) {
         lang = 0;
      }

      this.jbInit();
   }

   private void jbInit() {
      this.setSize(new Dimension(ancho, alto));
      this.getContentPane().setLayout((LayoutManager)null);
      panelDiagrama = new PanelDiagrama();
      this.getContentPane().add(panelDiagrama);
      panelBase = new PanelBase();
      this.getContentPane().add(panelBase);
      byte var4 = 0;
      byte var5 = 76;
      Varios.CalculaTablaXY(var4, var5);
      tablaXY = new TablaXY();
      tablaXY.xf = new double[280];
      tablaXY.yf = new double[280];
      tablaXY.x = new int[280];
      tablaXY.y = new int[280];
      tablaXY.lambda = new int[280];
      tablaXYT = new TablaXYT();
      tablaXYT.xt = new double[280];
      tablaXYT.yt = new double[280];
      tablaXYT.zt = new double[280];
      tablaXYT.lambda = new int[280];

      int var1;
      for(var1 = 0; var1 < 5; ++var1) {
         iluminante[var1] = new DatosEspectro();
      }

      iluminante[0].letra = 'A';
      iluminante[0].numDatosEspectro = 31;
      iluminante[0].datoColorL[0] = 400;
      iluminante[0].datoColorY[0] = 14.71D;
      iluminante[0].datoColorL[1] = 410;
      iluminante[0].datoColorY[1] = 17.68D;
      iluminante[0].datoColorL[2] = 420;
      iluminante[0].datoColorY[2] = 21.0D;
      iluminante[0].datoColorL[3] = 430;
      iluminante[0].datoColorY[3] = 24.67D;
      iluminante[0].datoColorL[4] = 440;
      iluminante[0].datoColorY[4] = 28.7D;
      iluminante[0].datoColorL[5] = 450;
      iluminante[0].datoColorY[5] = 33.09D;
      iluminante[0].datoColorL[6] = 460;
      iluminante[0].datoColorY[6] = 37.81D;
      iluminante[0].datoColorL[7] = 470;
      iluminante[0].datoColorY[7] = 42.87D;
      iluminante[0].datoColorL[8] = 480;
      iluminante[0].datoColorY[8] = 48.24D;
      iluminante[0].datoColorL[9] = 490;
      iluminante[0].datoColorY[9] = 53.91D;
      iluminante[0].datoColorL[10] = 500;
      iluminante[0].datoColorY[10] = 59.86D;
      iluminante[0].datoColorL[11] = 510;
      iluminante[0].datoColorY[11] = 66.06D;
      iluminante[0].datoColorL[12] = 520;
      iluminante[0].datoColorY[12] = 72.5D;
      iluminante[0].datoColorL[13] = 530;
      iluminante[0].datoColorY[13] = 79.13D;
      iluminante[0].datoColorL[14] = 540;
      iluminante[0].datoColorY[14] = 85.95D;
      iluminante[0].datoColorL[15] = 550;
      iluminante[0].datoColorY[15] = 92.91D;
      iluminante[0].datoColorL[16] = 560;
      iluminante[0].datoColorY[16] = 100.0D;
      iluminante[0].datoColorL[17] = 570;
      iluminante[0].datoColorY[17] = 107.18D;
      iluminante[0].datoColorL[18] = 580;
      iluminante[0].datoColorY[18] = 114.44D;
      iluminante[0].datoColorL[19] = 590;
      iluminante[0].datoColorY[19] = 121.73D;
      iluminante[0].datoColorL[20] = 600;
      iluminante[0].datoColorY[20] = 129.04D;
      iluminante[0].datoColorL[21] = 610;
      iluminante[0].datoColorY[21] = 136.35D;
      iluminante[0].datoColorL[22] = 620;
      iluminante[0].datoColorY[22] = 143.62D;
      iluminante[0].datoColorL[23] = 630;
      iluminante[0].datoColorY[23] = 150.84D;
      iluminante[0].datoColorL[24] = 640;
      iluminante[0].datoColorY[24] = 157.98D;
      iluminante[0].datoColorL[25] = 650;
      iluminante[0].datoColorY[25] = 165.03D;
      iluminante[0].datoColorL[26] = 660;
      iluminante[0].datoColorY[26] = 171.96D;
      iluminante[0].datoColorL[27] = 670;
      iluminante[0].datoColorY[27] = 178.77D;
      iluminante[0].datoColorL[28] = 680;
      iluminante[0].datoColorY[28] = 185.43D;
      iluminante[0].datoColorL[29] = 690;
      iluminante[0].datoColorY[29] = 191.93D;
      iluminante[0].datoColorL[30] = 700;
      iluminante[0].datoColorY[30] = 198.26D;
      double var6 = 0.0D;

      for(var1 = 0; var1 < 31; ++var1) {
         if (iluminante[0].datoColorY[var1] > var6) {
            var6 = iluminante[0].datoColorY[var1];
         }
      }

      for(var1 = 0; var1 < 31; ++var1) {
         iluminante[0].datoColorY[var1] /= var6;
      }

      iluminante[1].letra = 'B';
      iluminante[1].numDatosEspectro = 31;
      iluminante[1].datoColorL[0] = 400;
      iluminante[1].datoColorY[0] = 41.3D;
      iluminante[1].datoColorL[1] = 410;
      iluminante[1].datoColorY[1] = 52.1D;
      iluminante[1].datoColorL[2] = 420;
      iluminante[1].datoColorY[2] = 63.2D;
      iluminante[1].datoColorL[3] = 430;
      iluminante[1].datoColorY[3] = 73.1D;
      iluminante[1].datoColorL[4] = 440;
      iluminante[1].datoColorY[4] = 80.8D;
      iluminante[1].datoColorL[5] = 450;
      iluminante[1].datoColorY[5] = 85.4D;
      iluminante[1].datoColorL[6] = 460;
      iluminante[1].datoColorY[6] = 88.3D;
      iluminante[1].datoColorL[7] = 470;
      iluminante[1].datoColorY[7] = 92.0D;
      iluminante[1].datoColorL[8] = 480;
      iluminante[1].datoColorY[8] = 95.2D;
      iluminante[1].datoColorL[9] = 490;
      iluminante[1].datoColorY[9] = 96.5D;
      iluminante[1].datoColorL[10] = 500;
      iluminante[1].datoColorY[10] = 94.2D;
      iluminante[1].datoColorL[11] = 510;
      iluminante[1].datoColorY[11] = 90.7D;
      iluminante[1].datoColorL[12] = 520;
      iluminante[1].datoColorY[12] = 89.5D;
      iluminante[1].datoColorL[13] = 530;
      iluminante[1].datoColorY[13] = 92.2D;
      iluminante[1].datoColorL[14] = 540;
      iluminante[1].datoColorY[14] = 96.9D;
      iluminante[1].datoColorL[15] = 550;
      iluminante[1].datoColorY[15] = 101.0D;
      iluminante[1].datoColorL[16] = 560;
      iluminante[1].datoColorY[16] = 102.8D;
      iluminante[1].datoColorL[17] = 570;
      iluminante[1].datoColorY[17] = 102.6D;
      iluminante[1].datoColorL[18] = 580;
      iluminante[1].datoColorY[18] = 101.0D;
      iluminante[1].datoColorL[19] = 590;
      iluminante[1].datoColorY[19] = 99.2D;
      iluminante[1].datoColorL[20] = 600;
      iluminante[1].datoColorY[20] = 98.0D;
      iluminante[1].datoColorL[21] = 610;
      iluminante[1].datoColorY[21] = 98.5D;
      iluminante[1].datoColorL[22] = 620;
      iluminante[1].datoColorY[22] = 99.7D;
      iluminante[1].datoColorL[23] = 630;
      iluminante[1].datoColorY[23] = 101.0D;
      iluminante[1].datoColorL[24] = 640;
      iluminante[1].datoColorY[24] = 102.2D;
      iluminante[1].datoColorL[25] = 650;
      iluminante[1].datoColorY[25] = 103.9D;
      iluminante[1].datoColorL[26] = 660;
      iluminante[1].datoColorY[26] = 105.0D;
      iluminante[1].datoColorL[27] = 670;
      iluminante[1].datoColorY[27] = 104.9D;
      iluminante[1].datoColorL[28] = 680;
      iluminante[1].datoColorY[28] = 103.9D;
      iluminante[1].datoColorL[29] = 690;
      iluminante[1].datoColorY[29] = 101.6D;
      iluminante[1].datoColorL[30] = 700;
      iluminante[1].datoColorY[30] = 99.1D;
      var6 = 0.0D;

      for(var1 = 0; var1 < 31; ++var1) {
         if (iluminante[1].datoColorY[var1] > var6) {
            var6 = iluminante[1].datoColorY[var1];
         }
      }

      for(var1 = 0; var1 < 31; ++var1) {
         iluminante[1].datoColorY[var1] /= var6;
      }

      iluminante[2].letra = 'C';
      iluminante[2].numDatosEspectro = 31;
      iluminante[2].datoColorL[0] = 400;
      iluminante[2].datoColorY[0] = 63.3D;
      iluminante[2].datoColorL[1] = 410;
      iluminante[2].datoColorY[1] = 80.6D;
      iluminante[2].datoColorL[2] = 420;
      iluminante[2].datoColorY[2] = 98.1D;
      iluminante[2].datoColorL[3] = 430;
      iluminante[2].datoColorY[3] = 112.4D;
      iluminante[2].datoColorL[4] = 440;
      iluminante[2].datoColorY[4] = 121.5D;
      iluminante[2].datoColorL[5] = 450;
      iluminante[2].datoColorY[5] = 124.0D;
      iluminante[2].datoColorL[6] = 460;
      iluminante[2].datoColorY[6] = 123.1D;
      iluminante[2].datoColorL[7] = 470;
      iluminante[2].datoColorY[7] = 123.8D;
      iluminante[2].datoColorL[8] = 480;
      iluminante[2].datoColorY[8] = 123.9D;
      iluminante[2].datoColorL[9] = 490;
      iluminante[2].datoColorY[9] = 120.7D;
      iluminante[2].datoColorL[10] = 500;
      iluminante[2].datoColorY[10] = 112.1D;
      iluminante[2].datoColorL[11] = 510;
      iluminante[2].datoColorY[11] = 102.3D;
      iluminante[2].datoColorL[12] = 520;
      iluminante[2].datoColorY[12] = 96.9D;
      iluminante[2].datoColorL[13] = 530;
      iluminante[2].datoColorY[13] = 98.0D;
      iluminante[2].datoColorL[14] = 540;
      iluminante[2].datoColorY[14] = 102.1D;
      iluminante[2].datoColorL[15] = 550;
      iluminante[2].datoColorY[15] = 105.2D;
      iluminante[2].datoColorL[16] = 560;
      iluminante[2].datoColorY[16] = 105.3D;
      iluminante[2].datoColorL[17] = 570;
      iluminante[2].datoColorY[17] = 102.3D;
      iluminante[2].datoColorL[18] = 580;
      iluminante[2].datoColorY[18] = 97.8D;
      iluminante[2].datoColorL[19] = 590;
      iluminante[2].datoColorY[19] = 93.2D;
      iluminante[2].datoColorL[20] = 600;
      iluminante[2].datoColorY[20] = 89.7D;
      iluminante[2].datoColorL[21] = 610;
      iluminante[2].datoColorY[21] = 88.4D;
      iluminante[2].datoColorL[22] = 620;
      iluminante[2].datoColorY[22] = 88.1D;
      iluminante[2].datoColorL[23] = 630;
      iluminante[2].datoColorY[23] = 88.0D;
      iluminante[2].datoColorL[24] = 640;
      iluminante[2].datoColorY[24] = 87.8D;
      iluminante[2].datoColorL[25] = 650;
      iluminante[2].datoColorY[25] = 88.2D;
      iluminante[2].datoColorL[26] = 660;
      iluminante[2].datoColorY[26] = 87.9D;
      iluminante[2].datoColorL[27] = 670;
      iluminante[2].datoColorY[27] = 86.3D;
      iluminante[2].datoColorL[28] = 680;
      iluminante[2].datoColorY[28] = 84.0D;
      iluminante[2].datoColorL[29] = 690;
      iluminante[2].datoColorY[29] = 80.2D;
      iluminante[2].datoColorL[30] = 700;
      iluminante[2].datoColorY[30] = 76.3D;
      var6 = 0.0D;

      for(var1 = 0; var1 < 31; ++var1) {
         if (iluminante[2].datoColorY[var1] > var6) {
            var6 = iluminante[2].datoColorY[var1];
         }
      }

      for(var1 = 0; var1 < 31; ++var1) {
         iluminante[2].datoColorY[var1] /= var6;
      }

      iluminante[3].letra = 'D';
      iluminante[3].numDatosEspectro = 31;
      iluminante[3].datoColorL[0] = 400;
      iluminante[3].datoColorY[0] = 82.76D;
      iluminante[3].datoColorL[1] = 410;
      iluminante[3].datoColorY[1] = 91.49D;
      iluminante[3].datoColorL[2] = 420;
      iluminante[3].datoColorY[2] = 93.43D;
      iluminante[3].datoColorL[3] = 430;
      iluminante[3].datoColorY[3] = 86.68D;
      iluminante[3].datoColorL[4] = 440;
      iluminante[3].datoColorY[4] = 104.87D;
      iluminante[3].datoColorL[5] = 450;
      iluminante[3].datoColorY[5] = 117.01D;
      iluminante[3].datoColorL[6] = 460;
      iluminante[3].datoColorY[6] = 117.81D;
      iluminante[3].datoColorL[7] = 470;
      iluminante[3].datoColorY[7] = 114.86D;
      iluminante[3].datoColorL[8] = 480;
      iluminante[3].datoColorY[8] = 115.92D;
      iluminante[3].datoColorL[9] = 490;
      iluminante[3].datoColorY[9] = 108.81D;
      iluminante[3].datoColorL[10] = 500;
      iluminante[3].datoColorY[10] = 109.35D;
      iluminante[3].datoColorL[11] = 510;
      iluminante[3].datoColorY[11] = 107.8D;
      iluminante[3].datoColorL[12] = 520;
      iluminante[3].datoColorY[12] = 104.79D;
      iluminante[3].datoColorL[13] = 530;
      iluminante[3].datoColorY[13] = 107.69D;
      iluminante[3].datoColorL[14] = 540;
      iluminante[3].datoColorY[14] = 104.41D;
      iluminante[3].datoColorL[15] = 550;
      iluminante[3].datoColorY[15] = 104.05D;
      iluminante[3].datoColorL[16] = 560;
      iluminante[3].datoColorY[16] = 100.0D;
      iluminante[3].datoColorL[17] = 570;
      iluminante[3].datoColorY[17] = 96.33D;
      iluminante[3].datoColorL[18] = 580;
      iluminante[3].datoColorY[18] = 95.79D;
      iluminante[3].datoColorL[19] = 590;
      iluminante[3].datoColorY[19] = 88.69D;
      iluminante[3].datoColorL[20] = 600;
      iluminante[3].datoColorY[20] = 90.0D;
      iluminante[3].datoColorL[21] = 610;
      iluminante[3].datoColorY[21] = 89.6D;
      iluminante[3].datoColorL[22] = 620;
      iluminante[3].datoColorY[22] = 87.7D;
      iluminante[3].datoColorL[23] = 630;
      iluminante[3].datoColorY[23] = 83.29D;
      iluminante[3].datoColorL[24] = 640;
      iluminante[3].datoColorY[24] = 83.7D;
      iluminante[3].datoColorL[25] = 650;
      iluminante[3].datoColorY[25] = 80.03D;
      iluminante[3].datoColorL[26] = 660;
      iluminante[3].datoColorY[26] = 80.21D;
      iluminante[3].datoColorL[27] = 670;
      iluminante[3].datoColorY[27] = 82.28D;
      iluminante[3].datoColorL[28] = 680;
      iluminante[3].datoColorY[28] = 78.28D;
      iluminante[3].datoColorL[29] = 690;
      iluminante[3].datoColorY[29] = 69.72D;
      iluminante[3].datoColorL[30] = 700;
      iluminante[3].datoColorY[30] = 71.61D;
      var6 = 0.0D;

      for(var1 = 0; var1 < 31; ++var1) {
         if (iluminante[3].datoColorY[var1] > var6) {
            var6 = iluminante[3].datoColorY[var1];
         }
      }

      for(var1 = 0; var1 < 31; ++var1) {
         iluminante[3].datoColorY[var1] /= var6;
      }

      iluminante[4].letra = 'E';
      iluminante[4].numDatosEspectro = 31;

      for(var1 = 0; var1 < 31; ++var1) {
         iluminante[4].datoColorL[var1] = iluminante[0].datoColorL[var1];
         iluminante[4].datoColorY[var1] = 1.0D;
      }

      for(var1 = 0; var1 < 7; ++var1) {
         espectro[var1] = new DatosEspectro();
      }

      espectro[0].letra = 'M';
      espectro[0].numDatosEspectro = 31;
      espectro[0].datoColorL[0] = 400;
      espectro[0].datoColorY[0] = 1.0D;
      espectro[0].datoColorL[1] = 410;
      espectro[0].datoColorY[1] = 1.0D;
      espectro[0].datoColorL[2] = 420;
      espectro[0].datoColorY[2] = 1.0D;
      espectro[0].datoColorL[3] = 430;
      espectro[0].datoColorY[3] = 1.0D;
      espectro[0].datoColorL[4] = 440;
      espectro[0].datoColorY[4] = 1.0D;
      espectro[0].datoColorL[5] = 450;
      espectro[0].datoColorY[5] = 1.0D;
      espectro[0].datoColorL[6] = 460;
      espectro[0].datoColorY[6] = 1.0D;
      espectro[0].datoColorL[7] = 470;
      espectro[0].datoColorY[7] = 1.0D;
      espectro[0].datoColorL[8] = 480;
      espectro[0].datoColorY[8] = 0.96D;
      espectro[0].datoColorL[9] = 490;
      espectro[0].datoColorY[9] = 0.88D;
      espectro[0].datoColorL[10] = 500;
      espectro[0].datoColorY[10] = 0.09D;
      espectro[0].datoColorL[11] = 510;
      espectro[0].datoColorY[11] = 0.003D;
      espectro[0].datoColorL[12] = 520;
      espectro[0].datoColorY[12] = 0.001D;
      espectro[0].datoColorL[13] = 530;
      espectro[0].datoColorY[13] = 0.0D;
      espectro[0].datoColorL[14] = 540;
      espectro[0].datoColorY[14] = 0.0D;
      espectro[0].datoColorL[15] = 550;
      espectro[0].datoColorY[15] = 0.0D;
      espectro[0].datoColorL[16] = 560;
      espectro[0].datoColorY[16] = 0.0D;
      espectro[0].datoColorL[17] = 570;
      espectro[0].datoColorY[17] = 0.0D;
      espectro[0].datoColorL[18] = 580;
      espectro[0].datoColorY[18] = 0.001D;
      espectro[0].datoColorL[19] = 590;
      espectro[0].datoColorY[19] = 0.003D;
      espectro[0].datoColorL[20] = 600;
      espectro[0].datoColorY[20] = 0.09D;
      espectro[0].datoColorL[21] = 610;
      espectro[0].datoColorY[21] = 0.88D;
      espectro[0].datoColorL[22] = 620;
      espectro[0].datoColorY[22] = 0.96D;
      espectro[0].datoColorL[23] = 630;
      espectro[0].datoColorY[23] = 1.0D;
      espectro[0].datoColorL[24] = 640;
      espectro[0].datoColorY[24] = 1.0D;
      espectro[0].datoColorL[25] = 650;
      espectro[0].datoColorY[25] = 1.0D;
      espectro[0].datoColorL[26] = 660;
      espectro[0].datoColorY[26] = 1.0D;
      espectro[0].datoColorL[27] = 670;
      espectro[0].datoColorY[27] = 1.0D;
      espectro[0].datoColorL[28] = 680;
      espectro[0].datoColorY[28] = 1.0D;
      espectro[0].datoColorL[29] = 690;
      espectro[0].datoColorY[29] = 1.0D;
      espectro[0].datoColorL[30] = 700;
      espectro[0].datoColorY[30] = 1.0D;
      espectro[1].letra = 'A';
      espectro[1].numDatosEspectro = 31;
      espectro[1].datoColorL[0] = 400;
      espectro[1].datoColorY[0] = 0.0D;
      espectro[1].datoColorL[1] = 410;
      espectro[1].datoColorY[1] = 0.0D;
      espectro[1].datoColorL[2] = 420;
      espectro[1].datoColorY[2] = 0.0D;
      espectro[1].datoColorL[3] = 430;
      espectro[1].datoColorY[3] = 0.0D;
      espectro[1].datoColorL[4] = 440;
      espectro[1].datoColorY[4] = 0.0D;
      espectro[1].datoColorL[5] = 450;
      espectro[1].datoColorY[5] = 0.0D;
      espectro[1].datoColorL[6] = 460;
      espectro[1].datoColorY[6] = 0.0D;
      espectro[1].datoColorL[7] = 470;
      espectro[1].datoColorY[7] = 0.0D;
      espectro[1].datoColorL[8] = 480;
      espectro[1].datoColorY[8] = 0.001D;
      espectro[1].datoColorL[9] = 490;
      espectro[1].datoColorY[9] = 0.003D;
      espectro[1].datoColorL[10] = 500;
      espectro[1].datoColorY[10] = 0.09D;
      espectro[1].datoColorL[11] = 510;
      espectro[1].datoColorY[11] = 0.88D;
      espectro[1].datoColorL[12] = 520;
      espectro[1].datoColorY[12] = 0.96D;
      espectro[1].datoColorL[13] = 530;
      espectro[1].datoColorY[13] = 1.0D;
      espectro[1].datoColorL[14] = 540;
      espectro[1].datoColorY[14] = 1.0D;
      espectro[1].datoColorL[15] = 550;
      espectro[1].datoColorY[15] = 1.0D;
      espectro[1].datoColorL[16] = 560;
      espectro[1].datoColorY[16] = 1.0D;
      espectro[1].datoColorL[17] = 570;
      espectro[1].datoColorY[17] = 1.0D;
      espectro[1].datoColorL[18] = 580;
      espectro[1].datoColorY[18] = 1.0D;
      espectro[1].datoColorL[19] = 590;
      espectro[1].datoColorY[19] = 1.0D;
      espectro[1].datoColorL[20] = 600;
      espectro[1].datoColorY[20] = 1.0D;
      espectro[1].datoColorL[21] = 610;
      espectro[1].datoColorY[21] = 1.0D;
      espectro[1].datoColorL[22] = 620;
      espectro[1].datoColorY[22] = 1.0D;
      espectro[1].datoColorL[23] = 630;
      espectro[1].datoColorY[23] = 1.0D;
      espectro[1].datoColorL[24] = 640;
      espectro[1].datoColorY[24] = 1.0D;
      espectro[1].datoColorL[25] = 650;
      espectro[1].datoColorY[25] = 1.0D;
      espectro[1].datoColorL[26] = 660;
      espectro[1].datoColorY[26] = 1.0D;
      espectro[1].datoColorL[27] = 670;
      espectro[1].datoColorY[27] = 1.0D;
      espectro[1].datoColorL[28] = 680;
      espectro[1].datoColorY[28] = 1.0D;
      espectro[1].datoColorL[29] = 690;
      espectro[1].datoColorY[29] = 1.0D;
      espectro[1].datoColorL[30] = 700;
      espectro[1].datoColorY[30] = 1.0D;
      espectro[2].letra = 'C';
      espectro[2].numDatosEspectro = 31;
      espectro[2].datoColorL[0] = 400;
      espectro[2].datoColorY[0] = 1.0D;
      espectro[2].datoColorL[1] = 410;
      espectro[2].datoColorY[1] = 1.0D;
      espectro[2].datoColorL[2] = 420;
      espectro[2].datoColorY[2] = 1.0D;
      espectro[2].datoColorL[3] = 430;
      espectro[2].datoColorY[3] = 1.0D;
      espectro[2].datoColorL[4] = 440;
      espectro[2].datoColorY[4] = 1.0D;
      espectro[2].datoColorL[5] = 450;
      espectro[2].datoColorY[5] = 1.0D;
      espectro[2].datoColorL[6] = 460;
      espectro[2].datoColorY[6] = 1.0D;
      espectro[2].datoColorL[7] = 470;
      espectro[2].datoColorY[7] = 1.0D;
      espectro[2].datoColorL[8] = 480;
      espectro[2].datoColorY[8] = 1.0D;
      espectro[2].datoColorL[9] = 490;
      espectro[2].datoColorY[9] = 1.0D;
      espectro[2].datoColorL[10] = 500;
      espectro[2].datoColorY[10] = 1.0D;
      espectro[2].datoColorL[11] = 510;
      espectro[2].datoColorY[11] = 1.0D;
      espectro[2].datoColorL[12] = 520;
      espectro[2].datoColorY[12] = 1.0D;
      espectro[2].datoColorL[13] = 530;
      espectro[2].datoColorY[13] = 1.0D;
      espectro[2].datoColorL[14] = 540;
      espectro[2].datoColorY[14] = 1.0D;
      espectro[2].datoColorL[15] = 550;
      espectro[2].datoColorY[15] = 1.0D;
      espectro[2].datoColorL[16] = 560;
      espectro[2].datoColorY[16] = 1.0D;
      espectro[2].datoColorL[17] = 570;
      espectro[2].datoColorY[17] = 1.0D;
      espectro[2].datoColorL[18] = 580;
      espectro[2].datoColorY[18] = 0.96D;
      espectro[2].datoColorL[19] = 590;
      espectro[2].datoColorY[19] = 0.88D;
      espectro[2].datoColorL[20] = 600;
      espectro[2].datoColorY[20] = 0.09D;
      espectro[2].datoColorL[21] = 610;
      espectro[2].datoColorY[21] = 0.003D;
      espectro[2].datoColorL[22] = 620;
      espectro[2].datoColorY[22] = 0.001D;
      espectro[2].datoColorL[23] = 630;
      espectro[2].datoColorY[23] = 0.0D;
      espectro[2].datoColorL[24] = 640;
      espectro[2].datoColorY[24] = 0.0D;
      espectro[2].datoColorL[25] = 650;
      espectro[2].datoColorY[25] = 0.0D;
      espectro[2].datoColorL[26] = 660;
      espectro[2].datoColorY[26] = 0.0D;
      espectro[2].datoColorL[27] = 670;
      espectro[2].datoColorY[27] = 0.0D;
      espectro[2].datoColorL[28] = 680;
      espectro[2].datoColorY[28] = 0.0D;
      espectro[2].datoColorL[29] = 690;
      espectro[2].datoColorY[29] = 0.0D;
      espectro[2].datoColorL[30] = 700;
      espectro[2].datoColorY[30] = 0.0D;
      espectro[3].letra = 'R';
      espectro[3].numDatosEspectro = 31;
      espectro[3].datoColorL[0] = 400;
      espectro[3].datoColorY[0] = 0.091D;
      espectro[3].datoColorL[1] = 410;
      espectro[3].datoColorY[1] = 0.075D;
      espectro[3].datoColorL[2] = 420;
      espectro[3].datoColorY[2] = 0.065D;
      espectro[3].datoColorL[3] = 430;
      espectro[3].datoColorY[3] = 0.061D;
      espectro[3].datoColorL[4] = 440;
      espectro[3].datoColorY[4] = 0.06D;
      espectro[3].datoColorL[5] = 450;
      espectro[3].datoColorY[5] = 0.063D;
      espectro[3].datoColorL[6] = 460;
      espectro[3].datoColorY[6] = 0.062D;
      espectro[3].datoColorL[7] = 470;
      espectro[3].datoColorY[7] = 0.055D;
      espectro[3].datoColorL[8] = 480;
      espectro[3].datoColorY[8] = 0.043D;
      espectro[3].datoColorL[9] = 490;
      espectro[3].datoColorY[9] = 0.028D;
      espectro[3].datoColorL[10] = 500;
      espectro[3].datoColorY[10] = 0.016D;
      espectro[3].datoColorL[11] = 510;
      espectro[3].datoColorY[11] = 0.009D;
      espectro[3].datoColorL[12] = 520;
      espectro[3].datoColorY[12] = 0.006D;
      espectro[3].datoColorL[13] = 530;
      espectro[3].datoColorY[13] = 0.005D;
      espectro[3].datoColorL[14] = 540;
      espectro[3].datoColorY[14] = 0.005D;
      espectro[3].datoColorL[15] = 550;
      espectro[3].datoColorY[15] = 0.004D;
      espectro[3].datoColorL[16] = 560;
      espectro[3].datoColorY[16] = 0.005D;
      espectro[3].datoColorL[17] = 570;
      espectro[3].datoColorY[17] = 0.025D;
      espectro[3].datoColorL[18] = 580;
      espectro[3].datoColorY[18] = 0.164D;
      espectro[3].datoColorL[19] = 590;
      espectro[3].datoColorY[19] = 0.423D;
      espectro[3].datoColorL[20] = 600;
      espectro[3].datoColorY[20] = 0.61D;
      espectro[3].datoColorL[21] = 610;
      espectro[3].datoColorY[21] = 0.706D;
      espectro[3].datoColorL[22] = 620;
      espectro[3].datoColorY[22] = 0.755D;
      espectro[3].datoColorL[23] = 630;
      espectro[3].datoColorY[23] = 0.781D;
      espectro[3].datoColorL[24] = 640;
      espectro[3].datoColorY[24] = 0.798D;
      espectro[3].datoColorL[25] = 650;
      espectro[3].datoColorY[25] = 0.815D;
      espectro[3].datoColorL[26] = 660;
      espectro[3].datoColorY[26] = 0.823D;
      espectro[3].datoColorL[27] = 670;
      espectro[3].datoColorY[27] = 0.829D;
      espectro[3].datoColorL[28] = 680;
      espectro[3].datoColorY[28] = 0.831D;
      espectro[3].datoColorL[29] = 690;
      espectro[3].datoColorY[29] = 0.839D;
      espectro[3].datoColorL[30] = 700;
      espectro[3].datoColorY[30] = 0.844D;
      espectro[4].letra = 'V';
      espectro[4].numDatosEspectro = 31;
      espectro[4].datoColorL[0] = 400;
      espectro[4].datoColorY[0] = 0.037D;
      espectro[4].datoColorL[1] = 410;
      espectro[4].datoColorY[1] = 0.045D;
      espectro[4].datoColorL[2] = 420;
      espectro[4].datoColorY[2] = 0.055D;
      espectro[4].datoColorL[3] = 430;
      espectro[4].datoColorY[3] = 0.068D;
      espectro[4].datoColorL[4] = 440;
      espectro[4].datoColorY[4] = 0.09D;
      espectro[4].datoColorL[5] = 450;
      espectro[4].datoColorY[5] = 0.131D;
      espectro[4].datoColorL[6] = 460;
      espectro[4].datoColorY[6] = 0.203D;
      espectro[4].datoColorL[7] = 470;
      espectro[4].datoColorY[7] = 0.31D;
      espectro[4].datoColorL[8] = 480;
      espectro[4].datoColorY[8] = 0.431D;
      espectro[4].datoColorL[9] = 490;
      espectro[4].datoColorY[9] = 0.52D;
      espectro[4].datoColorL[10] = 500;
      espectro[4].datoColorY[10] = 0.568D;
      espectro[4].datoColorL[11] = 510;
      espectro[4].datoColorY[11] = 0.586D;
      espectro[4].datoColorL[12] = 520;
      espectro[4].datoColorY[12] = 0.589D;
      espectro[4].datoColorL[13] = 530;
      espectro[4].datoColorY[13] = 0.581D;
      espectro[4].datoColorL[14] = 540;
      espectro[4].datoColorY[14] = 0.557D;
      espectro[4].datoColorL[15] = 550;
      espectro[4].datoColorY[15] = 0.518D;
      espectro[4].datoColorL[16] = 560;
      espectro[4].datoColorY[16] = 0.46D;
      espectro[4].datoColorL[17] = 570;
      espectro[4].datoColorY[17] = 0.381D;
      espectro[4].datoColorL[18] = 580;
      espectro[4].datoColorY[18] = 0.283D;
      espectro[4].datoColorL[19] = 590;
      espectro[4].datoColorY[19] = 0.173D;
      espectro[4].datoColorL[20] = 600;
      espectro[4].datoColorY[20] = 0.082D;
      espectro[4].datoColorL[21] = 610;
      espectro[4].datoColorY[21] = 0.042D;
      espectro[4].datoColorL[22] = 620;
      espectro[4].datoColorY[22] = 0.031D;
      espectro[4].datoColorL[23] = 630;
      espectro[4].datoColorY[23] = 0.029D;
      espectro[4].datoColorL[24] = 640;
      espectro[4].datoColorY[24] = 0.029D;
      espectro[4].datoColorL[25] = 650;
      espectro[4].datoColorY[25] = 0.031D;
      espectro[4].datoColorL[26] = 660;
      espectro[4].datoColorY[26] = 0.043D;
      espectro[4].datoColorL[27] = 670;
      espectro[4].datoColorY[27] = 0.072D;
      espectro[4].datoColorL[28] = 680;
      espectro[4].datoColorY[28] = 0.11D;
      espectro[4].datoColorL[29] = 690;
      espectro[4].datoColorY[29] = 0.149D;
      espectro[4].datoColorL[30] = 700;
      espectro[4].datoColorY[30] = 0.181D;
      espectro[5].letra = 'A';
      espectro[5].numDatosEspectro = 31;
      espectro[5].datoColorL[0] = 400;
      espectro[5].datoColorY[0] = 0.844D;
      espectro[5].datoColorL[1] = 410;
      espectro[5].datoColorY[1] = 0.839D;
      espectro[5].datoColorL[2] = 420;
      espectro[5].datoColorY[2] = 0.831D;
      espectro[5].datoColorL[3] = 430;
      espectro[5].datoColorY[3] = 0.829D;
      espectro[5].datoColorL[4] = 440;
      espectro[5].datoColorY[4] = 0.823D;
      espectro[5].datoColorL[5] = 450;
      espectro[5].datoColorY[5] = 0.815D;
      espectro[5].datoColorL[6] = 460;
      espectro[5].datoColorY[6] = 0.798D;
      espectro[5].datoColorL[7] = 470;
      espectro[5].datoColorY[7] = 0.781D;
      espectro[5].datoColorL[8] = 480;
      espectro[5].datoColorY[8] = 0.755D;
      espectro[5].datoColorL[9] = 490;
      espectro[5].datoColorY[9] = 0.706D;
      espectro[5].datoColorL[10] = 500;
      espectro[5].datoColorY[10] = 0.61D;
      espectro[5].datoColorL[11] = 510;
      espectro[5].datoColorY[11] = 0.423D;
      espectro[5].datoColorL[12] = 520;
      espectro[5].datoColorY[12] = 0.164D;
      espectro[5].datoColorL[13] = 530;
      espectro[5].datoColorY[13] = 0.025D;
      espectro[5].datoColorL[14] = 540;
      espectro[5].datoColorY[14] = 0.005D;
      espectro[5].datoColorL[15] = 550;
      espectro[5].datoColorY[15] = 0.004D;
      espectro[5].datoColorL[16] = 560;
      espectro[5].datoColorY[16] = 0.005D;
      espectro[5].datoColorL[17] = 570;
      espectro[5].datoColorY[17] = 0.005D;
      espectro[5].datoColorL[18] = 580;
      espectro[5].datoColorY[18] = 0.006D;
      espectro[5].datoColorL[19] = 590;
      espectro[5].datoColorY[19] = 0.009D;
      espectro[5].datoColorL[20] = 600;
      espectro[5].datoColorY[20] = 0.016D;
      espectro[5].datoColorL[21] = 610;
      espectro[5].datoColorY[21] = 0.028D;
      espectro[5].datoColorL[22] = 620;
      espectro[5].datoColorY[22] = 0.043D;
      espectro[5].datoColorL[23] = 630;
      espectro[5].datoColorY[23] = 0.055D;
      espectro[5].datoColorL[24] = 640;
      espectro[5].datoColorY[24] = 0.062D;
      espectro[5].datoColorL[25] = 650;
      espectro[5].datoColorY[25] = 0.063D;
      espectro[5].datoColorL[26] = 660;
      espectro[5].datoColorY[26] = 0.06D;
      espectro[5].datoColorL[27] = 670;
      espectro[5].datoColorY[27] = 0.061D;
      espectro[5].datoColorL[28] = 680;
      espectro[5].datoColorY[28] = 0.065D;
      espectro[5].datoColorL[29] = 690;
      espectro[5].datoColorY[29] = 0.075D;
      espectro[5].datoColorL[30] = 700;
      espectro[5].datoColorY[30] = 0.091D;
      var6 = 0.0D;
      double var8 = 1.0D;

      int var2;
      for(var2 = 0; var2 < 6; ++var2) {
         for(var1 = 0; var1 < 31; ++var1) {
            if (espectro[var2].datoColorY[var1] > var6) {
               var6 = espectro[var2].datoColorY[var1];
            }

            if (espectro[var2].datoColorY[var1] < var8) {
               var8 = espectro[var2].datoColorY[var1];
            }
         }
      }

      var8 -= 0.01D;
      double var10 = var6 - var8;

      for(var2 = 0; var2 < 6; ++var2) {
         for(var1 = 0; var1 < 31; ++var1) {
            espectro[var2].datoColorY[var1] = (espectro[var2].datoColorY[var1] - var8) / var10;
         }
      }

      for(var2 = 0; var2 < 3; ++var2) {
         colorProducto[var2] = new DatosEspectro();
         var1 = 0;

         for(int var3 = 0; var1 < 31; var3 += 10) {
            colorProducto[var2].datoColorL[var1] = 400 + var3;
            ++var1;
         }

         colorProducto[var2].numDatosEspectro = 31;
      }

      problemaR[0] = 99;
      problemaG[0] = 90;
      problemaB[0] = 11;
      problemaR[1] = 98;
      problemaG[1] = 80;
      problemaB[1] = 22;
      problemaR[2] = 97;
      problemaG[2] = 70;
      problemaB[2] = 33;
      problemaR[3] = 96;
      problemaG[3] = 60;
      problemaB[3] = 44;
      problemaR[4] = 95;
      problemaG[4] = 50;
      problemaB[4] = 55;
      problemaR[5] = 94;
      problemaG[5] = 40;
      problemaB[5] = 66;
      problemaR[6] = 93;
      problemaG[6] = 30;
      problemaB[6] = 74;
      problemaR[7] = 92;
      problemaG[7] = 20;
      problemaB[7] = 88;
      problemaR[8] = 91;
      problemaG[8] = 10;
      problemaB[8] = 99;
      problemaR[9] = 90;
      problemaG[9] = 11;
      problemaB[9] = 99;
      problemaR[10] = 80;
      problemaG[10] = 22;
      problemaB[10] = 98;
      problemaR[11] = 70;
      problemaG[11] = 33;
      problemaB[11] = 97;
      problemaR[12] = 60;
      problemaG[12] = 44;
      problemaB[12] = 96;
      problemaR[13] = 50;
      problemaG[13] = 55;
      problemaB[13] = 95;
      problemaR[14] = 40;
      problemaG[14] = 66;
      problemaB[14] = 94;
      problemaR[15] = 30;
      problemaG[15] = 77;
      problemaB[15] = 93;
      problemaR[16] = 20;
      problemaG[16] = 88;
      problemaB[16] = 92;
      problemaR[17] = 10;
      problemaG[17] = 99;
      problemaB[17] = 91;
      problemaR[18] = 11;
      problemaG[18] = 99;
      problemaB[18] = 90;
      problemaR[19] = 22;
      problemaG[19] = 98;
      problemaB[19] = 80;
      problemaR[20] = 33;
      problemaG[20] = 97;
      problemaB[20] = 70;
      problemaR[21] = 44;
      problemaG[21] = 96;
      problemaB[21] = 60;
      problemaR[22] = 55;
      problemaG[22] = 95;
      problemaB[22] = 50;
      problemaR[23] = 66;
      problemaG[23] = 94;
      problemaB[23] = 40;
      problemaR[24] = 77;
      problemaG[24] = 93;
      problemaB[24] = 30;
      problemaR[25] = 88;
      problemaG[25] = 92;
      problemaB[25] = 20;
      problemaR[26] = 99;
      problemaG[26] = 91;
      problemaB[26] = 10;
      problemaR[27] = 90;
      problemaG[27] = 5;
      problemaB[27] = 10;
      problemaR[28] = 50;
      problemaG[28] = 5;
      problemaB[28] = 50;
      problemaR[29] = 10;
      problemaG[29] = 5;
      problemaB[29] = 90;
      diagrama = new Diagrama();
      panelDiagrama.add(diagrama);
      JButton var12 = new JButton(textoMA[lang]);
      var12.setBounds(0, 0, 180, 25);
      var12.setFont(fuente2);
      var12.setForeground(Color.blue);
      var12.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Colores.this.aditiva_actionPerformed(var1);
         }
      });
      this.getContentPane().add(var12);
      JButton var13 = new JButton(textoMS[lang]);
      var13.setBounds(180, 0, 180, 25);
      var13.setFont(fuente2);
      var13.setForeground(Color.blue);
      var13.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Colores.this.sustractiva_actionPerformed(var1);
         }
      });
      this.getContentPane().add(var13);
      JButton var14 = new JButton(textoFI[lang]);
      var14.setBounds(360, 0, 180, 25);
      var14.setForeground(Color.blue);
      var14.setFont(fuente2);
      var14.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Colores.this.producto_actionPerformed(var1);
         }
      });
      this.getContentPane().add(var14);
      JButton var15 = new JButton(textoSA[lang]);
      var15.setBounds(540, 0, 102, 25);
      var15.setForeground(Color.blue);
      var15.setFont(fuente2);
      var15.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Colores.this.salida_actionPerformed(var1);
         }
      });
      this.getContentPane().add(var15);
      JButton var16 = new JButton(textoAD[lang]);
      var16.setBounds(642, 0, 102, 25);
      var16.setForeground(Color.blue);
      var16.setFont(fuente2);
      var16.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Colores.this.acerca_actionPerformed(var1);
         }
      });
      this.getContentPane().add(var16);
      sumaColores = new SumaColores();
      panelElegir1 = new PanelElegir1();
      restaColores = new RestaColores();
      panelElegir2 = new PanelElegir2();
      productoIF = new ProductoIF();
      this.repaint();
   }

   public void start() {
   }

   public void stop() {
   }

   public void destroy() {
   }

   public String getAppletInfo() {
      return "Applet Information";
   }

   public String[][] getParameterInfo() {
      return null;
   }

   public static void main(String[] var0) {
      try {
         String var1 = var0[0].toUpperCase().intern();
         if (var1 == "CA") {
            lang = 0;
         } else if (var1 == "ES") {
            lang = 1;
         } else if (var1 == "EN") {
            lang = 2;
         } else {
            lang = 0;
         }
      } catch (Exception var4) {
         lang = 0;
      }

      Colores var5 = new Colores();
      var5.isStandalone = true;
      JFrame var2 = new JFrame();
      var2.setDefaultCloseOperation(3);
      var2.setTitle(texto0[lang]);
      var2.getContentPane().add(var5, "Center");
      var5.jbInit();
      var5.start();
      var2.setSize(ancho, alto);
      Dimension var3 = Toolkit.getDefaultToolkit().getScreenSize();
      var2.setLocation((var3.width - var2.getSize().width) / 2, (var3.height - var2.getSize().height) / 2);
      var2.setVisible(true);
   }

   void aditiva_actionPerformed(ActionEvent var1) {
      try {
         panelBase.remove(sumaColores);
      } catch (Exception var11) {
      }

      try {
         panelBase.remove(panelElegir1);
      } catch (Exception var10) {
      }

      try {
         panelBase.remove(restaColores);
      } catch (Exception var9) {
      }

      try {
         panelBase.remove(panelElegir2);
      } catch (Exception var8) {
      }

      try {
         panelBase.remove(productoIF);
      } catch (Exception var7) {
      }

      this.repaint();
      numColorProb = (int)(Math.random() * 30.0D);
      boolean var2 = false;
      boolean var3 = false;
      boolean var4 = false;
      int var12 = problemaR[numColorProb] * 255 / 100;
      int var13 = problemaG[numColorProb] * 255 / 100;
      int var14 = problemaB[numColorProb] * 255 / 100;
      sumaColores.colorProblema.R = (double)var12;
      sumaColores.colorProblema.G = (double)var13;
      sumaColores.colorProblema.B = (double)var14;
      var12 = tablaMonitor[var12];
      var13 = tablaMonitor[var13];
      var14 = tablaMonitor[var14];
      Varios.RGBXYZ(sumaColores.colorProblema);
      double var5 = sumaColores.colorProblema.X + sumaColores.colorProblema.Y + sumaColores.colorProblema.Z;
      sumaColores.colorProblema.x = sumaColores.colorProblema.X / var5;
      sumaColores.colorProblema.y = sumaColores.colorProblema.Y / var5;
      SumaColores var10000 = sumaColores;
      SumaColores.sliderR.setValue(50);
      var10000 = sumaColores;
      SumaColores.sliderG.setValue(50);
      var10000 = sumaColores;
      SumaColores.sliderB.setValue(50);
      panelBase.add(sumaColores);
      sumaColores.grafic = diagrama.getGraphics();
      var10000 = sumaColores;
      SumaColores.fraseNP.setText("       Nº " + numColorProb);
      var10000 = sumaColores;
      SumaColores.cuadroP.setBackground(new Color(var12, var13, var14));
      this.repaint();
   }

   void sustractiva_actionPerformed(ActionEvent var1) {
      try {
         panelBase.remove(sumaColores);
      } catch (Exception var7) {
      }

      try {
         panelBase.remove(panelElegir1);
      } catch (Exception var6) {
      }

      try {
         panelBase.remove(restaColores);
      } catch (Exception var5) {
      }

      try {
         panelBase.remove(panelElegir2);
      } catch (Exception var4) {
      }

      try {
         panelBase.remove(productoIF);
      } catch (Exception var3) {
      }

      RestaColores var10000 = restaColores;
      RestaColores.sliderI.setValue(50);
      var10000 = restaColores;
      RestaColores.sliderM.setValue(50);
      var10000 = restaColores;
      RestaColores.sliderA.setValue(50);
      var10000 = restaColores;
      RestaColores.sliderC.setValue(50);
      panelBase.add(panelElegir1);
      this.repaint();
   }

   void producto_actionPerformed(ActionEvent var1) {
      try {
         panelBase.remove(sumaColores);
      } catch (Exception var7) {
      }

      try {
         panelBase.remove(panelElegir1);
      } catch (Exception var6) {
      }

      try {
         panelBase.remove(restaColores);
      } catch (Exception var5) {
      }

      try {
         panelBase.remove(panelElegir2);
      } catch (Exception var4) {
      }

      try {
         panelBase.remove(productoIF);
      } catch (Exception var3) {
      }

      panelBase.add(panelElegir2);
      this.repaint();
   }

   void acerca_actionPerformed(ActionEvent var1) {
      JFrame var2 = new JFrame();
      Object[] var3 = new Object[]{acerca_etiqueta[0][lang]};
      Object var4 = null;
      JOptionPane var5 = new JOptionPane(acerca_etiqueta[1][lang], 1, -1, (Icon)var4, var3);
      JDialog var6 = var5.createDialog(var2, textoAD[lang]);
      var6.setResizable(false);
      var6.show();
   }

   void salida_actionPerformed(ActionEvent var1) {
      System.exit(0);
   }

   static {
      color = Color.blue;
   }
}
