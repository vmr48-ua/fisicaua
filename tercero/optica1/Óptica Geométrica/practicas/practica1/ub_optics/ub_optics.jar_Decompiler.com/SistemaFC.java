import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.JPanel;

public class SistemaFC extends JPanel {
   static SistemaFC sistema;
   static PanelBaseFC panelBase;
   AnteproyectoFC anteproyecto;
   ExactoFC exacto;
   static int lang = 0;
   static String strTeoria = null;
   static DibujoEscalaHFC canvasEscalaH;
   static DibujoEscalaVFC canvasEscalaV;
   static int ancho;
   static int alto;
   static double factorFO = 2.8D;
   static int ultimoHecho = 0;
   static int[] existeObjeto = new int[]{0, 0};
   static int[] real = new int[]{1, 1};
   static double[] valZObj = new double[]{-1600.0D, -1600.0D};
   static double[] zObjeto = new double[]{-1600.0D, -1600.0D};
   static double[] valYObj = new double[]{0.0D, 0.0D};
   static double[] campo = new double[]{0.0D, 0.0D};
   static double[] campoDib = new double[]{0.0D, 0.0D};
   static int[] tipoE = new int[8];
   static int[] existe = new int[8];
   static double[] valZE = new double[8];
   static double[] valPE = new double[8];
   static double[] valFE = new double[8];
   static double[] valDibPE = new double[8];
   static double[] valDE = new double[8];
   static double[] curv = new double[8];
   static double[] radio = new double[8];
   static double[] radioDib = new double[8];
   static double[] espesor = new double[8];
   static double[] altura = new double[8];
   static double[] dist = new double[8];
   static String[][] text = new String[][]{{"TRAÇAT DE RAIGS", "TRAZADO DE RAYOS", "RAY TRACING"}, {"Avantprojecte", "Anteproyecto", "Preliminary sketch"}, {"Càlcul exacte", "Cálculo exacto", "Exact calculation"}, {"Llegir un fitxer", "Leer un fichero", "Read file"}, {"Enregistrar", "Grabar un fichero", "Save to file"}, {"Resum de teoria", "Resumen de teoría", "Theory summary"}, {"Sortida", "Salida", "Exit"}, {"TEORIA", "TEORIA", "THEORY"}};

   public SistemaFC() {
      lang = Sistema.lang;
      ancho = Sistema.ancho;
      alto = Sistema.altoDibujo;
      strTeoria = Sistema.teoria[lang];
      this.setSize(ancho, alto);
      this.setLayout((LayoutManager)null);
      panelBase = new PanelBaseFC();
      this.add(panelBase);
      this.anteproyecto = new AnteproyectoFC();
      this.exacto = new ExactoFC();
      canvasEscalaV = new DibujoEscalaVFC();
      canvasEscalaV.setBackground(Color.black);
      canvasEscalaV.setBounds(0, 0, 30, 210);
      this.add(canvasEscalaV);
      canvasEscalaV.repaint();
      canvasEscalaH = new DibujoEscalaHFC();
      canvasEscalaH.setBackground(Color.black);
      canvasEscalaH.setBounds(0, 210, 800, 20);
      this.add(canvasEscalaH);
      canvasEscalaH.repaint();
      this.repaint();
   }
}
