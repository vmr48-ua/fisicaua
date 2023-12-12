package young;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

public class YoungApplet extends Applet {
   String[] titulo = new String[]{"INTERFERENCIAS DE YOUNG. COHERENCIA ESPACIAL", "INTERFERÈNCIES DE YOUNG. COHERÈNCIA ESPACIAL", "YOUNG'S INTERFERENCES. SPATIAL COHERENCE"};
   String[][] etiqueta = new String[][]{{"Experimento Interferencias de Young", "Experiment Interferències de Young", "Young's Interferences Experiment"}, {"Onda plana", "Ona plana", "Plane wave"}, {"Coherencia espacial", "Coherència espaial", "Spatial coherence"}, {"  ", "  ", "  "}, {"Salir", "Sortir", "Exit"}, {"Archivo", "Arxiu", "File"}, {"Ayuda", "Ajuda", "Help"}, {"Acerca de...", "En quant a...", "About..."}};
   String[][] label_etiqueta = new String[][]{{"Parametros del sistema", "Paràmetres del sistema", "System parameters"}, {"Distancia entre rendijas", "Distància entre escletxes", "Distance between slits"}, {"Distancia entre planos", "Distància entre plans", "Distance between planes"}, {"Longitud de onda", "Longitud d'ona", "Wavelength"}, {"Indice de refracción", "Índex de refracció", "Refraction index"}, {"Número de rendijas", "Nombre d'escletxes", "Number of slits"}, {"Información parametros del sistema", "Informació paràmetres del sistema", "System parameters information"}, {"Distancia entre máximos", "Distància entre màxims", "Distance between maximums"}, {"Distancia entre mínimos", "Distància entre mínims", "Distance between minimums"}, {"Anchura zona de visión", "Amplada zona de visió", "Display area width"}, {"(en el vacío) ", "(al buit) ", "(in vacuum) "}};
   String[][] boton_etiqueta = new String[][]{{"Gráfico", "Gràfic", "Graphic"}, {"Imagen", "Imatge", "Image"}, {"Intensidad logaritmica on", "Intensitat logarítmica on", "Logarithmic intensity on"}, {"Intensidad lineal on", "Intensitat lineal on", "Linear intensity on"}};
   String[][] label_coherencia = new String[][]{{"Tipo de fuente", "Tipus de font", "Source type"}, {"1 puntual", "1 puntual", "1 point"}, {"2 puntuales", "2 puntuals", "2 point"}, {"1 extensa", "1 extensa", "1 extended"}, {"Distancia al centro", "Distància al centre", "Distance to center"}, {"Distancia entre fuentes", "Distància entre fonts", "Distance between sources"}, {"Dimensión de la fuente", "Dimensió de la font", "Source width"}, {"Posición fuente puntual", "Posició font puntual", "Point source position"}, {"Posición 2ª fuente puntual", "Posició 2ª font puntual", "2nd point source position"}, {" A la fuente", " A la font", " To the source"}, {"Factor de visibilidad", "Factor de visibilitat", "Visibility factor"}};
   String[][] acerca_etiqueta = new String[][]{{"Acerca de", "En quant a", "About"}, {"Aceptar", "Acceptar", "OK"}, {"Applet de Interferencias de Young versión 0.8 \n Grup d'Innovació Docent en Òptica Física i Fotònica \n Universitat de Barcelona, 2003 \n La utilización de este programa está bajo una licencia de Creative Commons \n Ver condiciones en http://creativecommons.org/license/by-nc-sa/2.0/ \n \n Curso de Óptica en Java DOI: 10.1344/401.000000050 \n Applet de Interferencias de Young DOI: 10.1344/203.000000093", "Applet de Interferències de Young versió 0.8 \n Grup d'Innovació Docent en Òptica Física i Fotònica \n Universitat de Barcelona, 2003 \n La utilització d'aquest programa està sota una llicència de Creative Commons \n Veure condicions a http://www.publicacions.ub.es/doi/licencia/resum-deriv.htm\n \n Curs d'Òptica en Java DOI: 10.1344/401.000000050 \n Applet de Interferències de Young DOI: 10.1344/203.000000093", "Young's Interferences Applet version 0.8 \n Grup d'Innovació Docent en Òptica Física i Fotònica \n Universitat de Barcelona, 2003 \n The use of this program is under a Creative Commons license \n See conditions in http://creativecommons.org/license/by-nc-sa/2.0/ \n \n Java Optics Course DOI: 10.1344/401.000000050 \n Young's Interferences Applet DOI: 10.1344/203.000000093"}};
   String[] info_etiqueta = new String[]{"Resumen teoría", "Resum teoria", "Theory summary"};
   int lambda;
   int n_rendijas;
   double d_planos = 3.0D;
   double d_holes;
   double n_refraccion;
   double dist_max;
   double dist_min;
   double zona_vis;
   double d_fuente;
   int tipo_fuente = 0;
   double factor_vis;
   DecimalFormatSymbols df_symb = new DecimalFormatSymbols();
   DecimalFormat df;
   boolean isStandalone;
   static int idioma;
   BorderLayout borderLayout1;
   JPanel jPanelBase;
   BorderLayout borderLayout2;
   JPanel jPanelNorte;
   JPanel jPanelCentro;
   JPanel jPanelSur;
   JTabbedPane jTabbedPane1;
   BorderLayout borderLayout3;
   JPanel jPanel1;
   JPanel jPanel2;
   BorderLayout borderLayout5;
   JPanel jPanel1a;
   JPanel jPanel1b;
   FlowLayout flowLayout1;
   JButton jButtonSalir;
   BorderLayout borderLayout6;
   JPanel jPanel1a_norte;
   JPanel jPanel1a_centro;
   BorderLayout borderLayout7;
   JPanel jPanel1a_n_n;
   JPanel jPanel1a_n_w;
   JPanel jPanel1a_n_e;
   JPanel jPanel1a_n_c;
   FlowLayout flowLayout2;
   JLabel jLabel1a_c_1;
   JLabel jLabel1a_c_2;
   JSlider jSlider_1a_dRendijas;
   JLabel jLabel1a_c_3;
   JSlider jSlider_1a_dPlanos;
   JLabel jLabel1a_c_4;
   JSlider jSlider_1a_longOnda;
   JLabel jLabel1a_c_5;
   JSlider jSlider_1a_nRefrac;
   JLabel jLabel1a_c_6;
   JSlider jSlider_1a_nRendijas;
   BorderLayout borderLayout8;
   JPanel jPanel1a_norte1;
   JPanel jPanel1a_centro1;
   FlowLayout flowLayout3;
   BorderLayout borderLayout9;
   JPanel jPanel1b_norte;
   JPanel jPanel1b_centro;
   BorderLayout borderLayout10;
   JPanel jPanel1b_n_n;
   JPanel jPanel1b_n_w;
   JPanel jPanel1b_n_e;
   JPanel jPanel1b_n_c;
   FlowLayout flowLayout4;
   JLabel jLabel1b_c_1;
   BorderLayout borderLayout11;
   JPanel jPanel2a;
   JPanel jPanel2b;
   JPanel jPanel2a_norte;
   BorderLayout borderLayout12;
   JPanel jPanel2a_centro;
   BorderLayout borderLayout13;
   JPanel jPanel2a_n_n;
   JPanel jPanel2a_n_w;
   JPanel jPanel2a_n_e;
   JPanel jPanel2a_n_c;
   JPanel jPanel2b_norte;
   BorderLayout borderLayout14;
   BorderLayout borderLayout15;
   JPanel jPanel2b_centro;
   JPanel jPanel2b_n_n;
   JPanel jPanel2b_n_w;
   JPanel jPanel2b_n_e;
   JPanel jPanel2b_n_c;
   JLabel jLabel1b_c_5;
   YoungEsquema esquema;
   JLabel jLabel1b_c_6;
   BorderLayout borderLayout16;
   JLabel jLabel1b_c_4;
   JLabel jLabel1b_c_7;
   YoungFranjas franjas;
   YoungGrafico grafico;
   JSplitPane jSplitPane1;
   JButton jButton1b_grafico;
   boolean label_grafico;
   JLabel jLabel1b_c_3;
   JButton jButton1b_intensidad;
   boolean label_int_log;
   JLabel jLabel1b_c_8;
   JLabel jLabel1b_c_9;
   FlowLayout flowLayout5;
   JLabel jLabel1b_c_2;
   FlowLayout flowLayout6;
   JLabel jLabel2a_c_1;
   JLabel jLabel2a_c_2;
   JRadioButton jRadioButton_1puntual;
   JRadioButton jRadioButton_2puntual;
   JRadioButton jRadioButton_1extensa;
   ButtonGroup group_tipo_fuente;
   JLabel jLabel2a_c_3;
   JSlider jSlider_2a_dFuente;
   JLabel jLabel2a_c_4;
   JSlider jSlider_2a_dRendijas;
   JLabel jLabel2a_c_5;
   JSlider jSlider_2a_dPlanos;
   JLabel jLabel2a_c_6;
   JSlider jSlider_2a_lOnda;
   JLabel jLabel2a_c_nRefrac;
   JSlider jSlider_2a_nRefrac;
   JButton jButton_acercade;
   CoherenciaEsquema esquemaCoherencia;
   CoherenciaFranjas franjasCoherencia;
   CoherenciaGrafico graficoCoherencia;
   JSplitPane jSplitPane2;
   JButton jButton2b_grafico;
   boolean label_grafico2;
   BorderLayout borderLayout4;
   JLabel jLabel2b_c_1;
   JLabel jLabel2b_c_2;
   JLabel jLabel2b_c_3;
   JLabel jLabel2b_c_4;
   JLabel jLabel2b_c_5;
   JLabel jLabel2b_c_6;
   JLabel jLabel2b_c_7;
   JLabel jLabel2b_c_8;
   JLabel jLabel2b_c_9;
   JLabel jLabel_titulo;
   URL info_page;
   private JPanel jPanel_teoria;
   private BorderLayout borderLayout19;
   private JScrollPane jScrollPane_teoria;
   private JTextPane jTextPane_teoria;

   public String getParameter(String key, String def) {
      return this.isStandalone ? System.getProperty(key, def) : (this.getParameter(key) != null ? this.getParameter(key) : def);
   }

   public YoungApplet() {
      this.df = new DecimalFormat("#.###", this.df_symb);
      this.isStandalone = false;
      this.borderLayout1 = new BorderLayout();
      this.jPanelBase = new JPanel();
      this.borderLayout2 = new BorderLayout();
      this.jPanelNorte = new JPanel();
      this.jPanelCentro = new JPanel();
      this.jPanelSur = new JPanel();
      this.jTabbedPane1 = new JTabbedPane();
      this.borderLayout3 = new BorderLayout();
      this.jPanel1 = new JPanel();
      this.jPanel2 = new JPanel();
      this.borderLayout5 = new BorderLayout();
      this.jPanel1a = new JPanel();
      this.jPanel1b = new JPanel();
      this.flowLayout1 = new FlowLayout();
      this.jButtonSalir = new JButton();
      this.borderLayout6 = new BorderLayout();
      this.jPanel1a_norte = new JPanel();
      this.jPanel1a_centro = new JPanel();
      this.borderLayout7 = new BorderLayout();
      this.jPanel1a_n_n = new JPanel();
      this.jPanel1a_n_w = new JPanel();
      this.jPanel1a_n_e = new JPanel();
      this.jPanel1a_n_c = new JPanel();
      this.flowLayout2 = new FlowLayout();
      this.jLabel1a_c_1 = new JLabel();
      this.jLabel1a_c_2 = new JLabel();
      this.jSlider_1a_dRendijas = new JSlider(0, 10, 60, 30);
      this.jLabel1a_c_3 = new JLabel();
      this.jSlider_1a_dPlanos = new JSlider(0, 10, 60, 30);
      this.jLabel1a_c_4 = new JLabel();
      this.jSlider_1a_longOnda = new JSlider(0, 380, 780, 590);
      this.jLabel1a_c_5 = new JLabel();
      this.jSlider_1a_nRefrac = new JSlider(0, 100, 200, 100);
      this.jLabel1a_c_6 = new JLabel();
      this.jSlider_1a_nRendijas = new JSlider(0, 2, 10, 2);
      this.borderLayout8 = new BorderLayout();
      this.jPanel1a_norte1 = new JPanel();
      this.jPanel1a_centro1 = new JPanel();
      this.flowLayout3 = new FlowLayout();
      this.borderLayout9 = new BorderLayout();
      this.jPanel1b_norte = new JPanel();
      this.jPanel1b_centro = new JPanel();
      this.borderLayout10 = new BorderLayout();
      this.jPanel1b_n_n = new JPanel();
      this.jPanel1b_n_w = new JPanel();
      this.jPanel1b_n_e = new JPanel();
      this.jPanel1b_n_c = new JPanel();
      this.flowLayout4 = new FlowLayout();
      this.jLabel1b_c_1 = new JLabel();
      this.borderLayout11 = new BorderLayout();
      this.jPanel2a = new JPanel();
      this.jPanel2b = new JPanel();
      this.jPanel2a_norte = new JPanel();
      this.borderLayout12 = new BorderLayout();
      this.jPanel2a_centro = new JPanel();
      this.borderLayout13 = new BorderLayout();
      this.jPanel2a_n_n = new JPanel();
      this.jPanel2a_n_w = new JPanel();
      this.jPanel2a_n_e = new JPanel();
      this.jPanel2a_n_c = new JPanel();
      this.jPanel2b_norte = new JPanel();
      this.borderLayout14 = new BorderLayout();
      this.borderLayout15 = new BorderLayout();
      this.jPanel2b_centro = new JPanel();
      this.jPanel2b_n_n = new JPanel();
      this.jPanel2b_n_w = new JPanel();
      this.jPanel2b_n_e = new JPanel();
      this.jPanel2b_n_c = new JPanel();
      this.jLabel1b_c_5 = new JLabel();
      this.esquema = new YoungEsquema();
      this.jLabel1b_c_6 = new JLabel();
      this.borderLayout16 = new BorderLayout();
      this.jLabel1b_c_4 = new JLabel();
      this.jLabel1b_c_7 = new JLabel();
      this.franjas = new YoungFranjas();
      this.grafico = new YoungGrafico();
      this.jSplitPane1 = new JSplitPane(1, this.grafico, this.franjas);
      this.jButton1b_grafico = new JButton();
      this.label_grafico = false;
      this.jLabel1b_c_3 = new JLabel();
      this.jButton1b_intensidad = new JButton();
      this.label_int_log = false;
      this.jLabel1b_c_8 = new JLabel();
      this.jLabel1b_c_9 = new JLabel();
      this.flowLayout5 = new FlowLayout();
      this.jLabel1b_c_2 = new JLabel();
      this.flowLayout6 = new FlowLayout();
      this.jLabel2a_c_1 = new JLabel();
      this.jLabel2a_c_2 = new JLabel();
      this.jRadioButton_1puntual = new JRadioButton();
      this.jRadioButton_2puntual = new JRadioButton();
      this.jRadioButton_1extensa = new JRadioButton();
      this.group_tipo_fuente = new ButtonGroup();
      this.jLabel2a_c_3 = new JLabel();
      this.jSlider_2a_dFuente = new JSlider(0, 0, 60, 30);
      this.jLabel2a_c_4 = new JLabel();
      this.jSlider_2a_dRendijas = new JSlider(0, 10, 60, 30);
      this.jLabel2a_c_5 = new JLabel();
      this.jSlider_2a_dPlanos = new JSlider(0, 10, 60, 30);
      this.jLabel2a_c_6 = new JLabel();
      this.jSlider_2a_lOnda = new JSlider(0, 380, 780, 590);
      this.jLabel2a_c_nRefrac = new JLabel();
      this.jSlider_2a_nRefrac = new JSlider(0, 100, 200, 100);
      this.jButton_acercade = new JButton();
      this.esquemaCoherencia = new CoherenciaEsquema();
      this.franjasCoherencia = new CoherenciaFranjas();
      this.graficoCoherencia = new CoherenciaGrafico();
      this.jSplitPane2 = new JSplitPane(1, this.graficoCoherencia, this.franjasCoherencia);
      this.jButton2b_grafico = new JButton();
      this.label_grafico2 = false;
      this.borderLayout4 = new BorderLayout();
      this.jLabel2b_c_1 = new JLabel();
      this.jLabel2b_c_2 = new JLabel();
      this.jLabel2b_c_3 = new JLabel();
      this.jLabel2b_c_4 = new JLabel();
      this.jLabel2b_c_5 = new JLabel();
      this.jLabel2b_c_6 = new JLabel();
      this.jLabel2b_c_7 = new JLabel();
      this.jLabel2b_c_8 = new JLabel();
      this.jLabel2b_c_9 = new JLabel();
      this.jLabel_titulo = new JLabel();
      this.jPanel_teoria = new JPanel();
      this.borderLayout19 = new BorderLayout();
      this.jScrollPane_teoria = new JScrollPane();
      this.jTextPane_teoria = new JTextPane();
   }

   public void init() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      this.actuaParametros();
      this.actuaParametrosCoherencia();
      this.carga_info(idioma);
   }

   private void jbInit() throws Exception {
      this.setLayout(this.borderLayout1);
      this.jPanelBase.setLayout(this.borderLayout2);
      this.jPanelCentro.setLayout(this.borderLayout3);
      this.jPanelNorte.setMinimumSize(new Dimension(10, 20));
      this.jPanelNorte.setPreferredSize(new Dimension(750, 25));
      this.jPanelNorte.setLayout(this.flowLayout5);
      this.jPanelCentro.setPreferredSize(new Dimension(750, 500));
      this.jPanelSur.setPreferredSize(new Dimension(750, 25));
      this.jPanelSur.setLayout(this.flowLayout1);
      this.jPanelBase.setPreferredSize(new Dimension(750, 550));
      this.jTabbedPane1.setPreferredSize(new Dimension(750, 500));
      this.jPanel1.setLayout(this.borderLayout5);
      this.jPanel1.setPreferredSize(new Dimension(750, 450));
      this.jPanel1a.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel1a.setPreferredSize(new Dimension(375, 450));
      this.jPanel1a.setLayout(this.borderLayout6);
      this.jPanel1b.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel1b.setPreferredSize(new Dimension(375, 450));
      this.jPanel1b.setLayout(this.borderLayout9);
      this.flowLayout1.setAlignment(2);
      this.jButtonSalir.setMinimumSize(new Dimension(81, 20));
      this.jButtonSalir.setPreferredSize(new Dimension(81, 20));
      this.jButtonSalir.setText(this.etiqueta[4][idioma]);
      this.jButtonSalir.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            YoungApplet.this.jButtonSalir_actionPerformed(e);
         }
      });
      this.jPanel1a_norte.setPreferredSize(new Dimension(375, 250));
      this.jPanel1a_norte.setLayout(this.borderLayout7);
      this.jPanel1a_centro.setPreferredSize(new Dimension(375, 200));
      this.jPanel1a_centro.setLayout(this.flowLayout2);
      this.jPanel1a_n_c.setBackground(Color.darkGray);
      this.jLabel1a_c_1.setPreferredSize(new Dimension(375, 17));
      this.jLabel1a_c_1.setHorizontalAlignment(0);
      this.jLabel1a_c_1.setHorizontalTextPosition(0);
      this.jLabel1a_c_1.setForeground(new Color(102, 102, 153));
      this.jLabel1a_c_1.setText(this.label_etiqueta[0][idioma]);
      this.jLabel1a_c_2.setPreferredSize(new Dimension(150, 17));
      this.jLabel1a_c_2.setForeground(new Color(102, 102, 153));
      this.jLabel1a_c_2.setText(this.label_etiqueta[1][idioma]);
      this.jLabel1a_c_3.setPreferredSize(new Dimension(150, 17));
      this.jLabel1a_c_3.setForeground(new Color(102, 102, 153));
      this.jLabel1a_c_3.setText(this.label_etiqueta[2][idioma]);
      this.jLabel1a_c_4.setPreferredSize(new Dimension(150, 17));
      this.jLabel1a_c_4.setForeground(new Color(102, 102, 153));
      this.jLabel1a_c_4.setText(this.label_etiqueta[3][idioma]);
      this.jLabel1a_c_5.setPreferredSize(new Dimension(150, 17));
      this.jLabel1a_c_5.setForeground(new Color(102, 102, 153));
      this.jLabel1a_c_5.setText(this.label_etiqueta[4][idioma]);
      this.jLabel1a_c_6.setPreferredSize(new Dimension(150, 17));
      this.jLabel1a_c_6.setForeground(new Color(102, 102, 153));
      this.jLabel1a_c_6.setText(this.label_etiqueta[5][idioma]);
      this.jPanel1a_norte1.setLayout(this.borderLayout8);
      this.jPanel1a_norte1.setPreferredSize(new Dimension(375, 250));
      this.jPanel1a_centro1.setLayout(this.flowLayout3);
      this.jPanel1a_centro1.setPreferredSize(new Dimension(375, 200));
      this.jPanel1b_norte.setPreferredSize(new Dimension(375, 250));
      this.jPanel1b_norte.setLayout(this.borderLayout10);
      this.jPanel1b_centro.setPreferredSize(new Dimension(375, 10));
      this.jPanel1b_centro.setLayout(this.flowLayout4);
      this.jPanel1b_n_c.setBackground(Color.black);
      this.jPanel1b_n_c.setLayout(this.borderLayout16);
      this.jLabel1b_c_1.setPreferredSize(new Dimension(375, 15));
      this.jLabel1b_c_1.setHorizontalAlignment(0);
      this.jLabel1b_c_1.setForeground(new Color(102, 102, 153));
      this.jLabel1b_c_1.setText(this.label_etiqueta[6][idioma]);
      this.jPanel2.setLayout(this.borderLayout11);
      this.jPanel2a.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel2a.setPreferredSize(new Dimension(375, 450));
      this.jPanel2a.setLayout(this.borderLayout12);
      this.jPanel2b.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel2b.setPreferredSize(new Dimension(375, 450));
      this.jPanel2b.setLayout(this.borderLayout14);
      this.jPanel2a_norte.setPreferredSize(new Dimension(375, 250));
      this.jPanel2a_norte.setLayout(this.borderLayout13);
      this.jPanel2a_centro.setPreferredSize(new Dimension(375, 200));
      this.jPanel2a_centro.setLayout(this.flowLayout6);
      this.jPanel2a_n_c.setBackground(Color.darkGray);
      this.jPanel2b_norte.setPreferredSize(new Dimension(375, 250));
      this.jPanel2b_norte.setLayout(this.borderLayout15);
      this.jPanel2b_centro.setPreferredSize(new Dimension(375, 200));
      this.jPanel2b_n_c.setBackground(Color.black);
      this.jPanel2b_n_c.setLayout(this.borderLayout4);
      this.jSlider_1a_longOnda.setMajorTickSpacing(80);
      this.jSlider_1a_longOnda.setMaximum(700);
      this.jSlider_1a_longOnda.setMinimum(400);
      this.jSlider_1a_longOnda.setMinorTickSpacing(20);
      this.jSlider_1a_longOnda.createStandardLabels(42);
      this.jSlider_1a_longOnda.setPaintTicks(true);
      this.jSlider_1a_longOnda.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            YoungApplet.this.jSlider_1a_longOnda_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            YoungApplet.this.jSlider_1a_longOnda_keyTyped(e);
         }
      });
      this.jSlider_1a_longOnda.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            YoungApplet.this.jSlider_1a_longOnda_mouseDragged(e);
         }
      });
      this.jSlider_1a_longOnda.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            YoungApplet.this.jSlider_1a_longOnda_mouseClicked(e);
         }
      });
      this.jSlider_1a_dPlanos.setMajorTickSpacing(10);
      this.jSlider_1a_dPlanos.setMaximum(60);
      this.jSlider_1a_dPlanos.setMinimum(10);
      this.jSlider_1a_dPlanos.setMinorTickSpacing(2);
      this.jSlider_1a_dPlanos.setPaintTicks(true);
      this.jSlider_1a_dPlanos.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            YoungApplet.this.jSlider_1a_dPlanos_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            YoungApplet.this.jSlider_1a_dPlanos_keyTyped(e);
         }
      });
      this.jSlider_1a_dPlanos.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            YoungApplet.this.jSlider_1a_dPlanos_mouseClicked(e);
         }
      });
      this.jSlider_1a_dPlanos.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            YoungApplet.this.jSlider_1a_dPlanos_mouseDragged(e);
         }
      });
      this.jSlider_1a_dRendijas.setMajorTickSpacing(10);
      this.jSlider_1a_dRendijas.setMaximum(60);
      this.jSlider_1a_dRendijas.setMinimum(10);
      this.jSlider_1a_dRendijas.setMinorTickSpacing(2);
      this.jSlider_1a_dRendijas.setPaintTicks(true);
      this.jSlider_1a_dRendijas.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            YoungApplet.this.jSlider_1a_dRendijas_mouseClicked(e);
         }
      });
      this.jSlider_1a_dRendijas.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            YoungApplet.this.jSlider_1a_dRendijas_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            YoungApplet.this.jSlider_1a_dRendijas_keyTyped(e);
         }
      });
      this.jSlider_1a_dRendijas.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            YoungApplet.this.jSlider_1a_dRendijas_mouseDragged(e);
         }
      });
      this.jSlider_1a_nRefrac.setMajorTickSpacing(25);
      this.jSlider_1a_nRefrac.setMaximum(200);
      this.jSlider_1a_nRefrac.setMinimum(100);
      this.jSlider_1a_nRefrac.setMinorTickSpacing(5);
      this.jSlider_1a_nRefrac.setPaintTicks(true);
      this.jSlider_1a_nRefrac.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            YoungApplet.this.jSlider_1a_nRefrac_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            YoungApplet.this.jSlider_1a_nRefrac_keyTyped(e);
         }
      });
      this.jSlider_1a_nRefrac.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            YoungApplet.this.jSlider_1a_nRefrac_mouseClicked(e);
         }
      });
      this.jSlider_1a_nRefrac.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            YoungApplet.this.jSlider_1a_nRefrac_mouseDragged(e);
         }
      });
      this.jSlider_1a_nRendijas.setMajorTickSpacing(1);
      this.jSlider_1a_nRendijas.setMaximum(10);
      this.jSlider_1a_nRendijas.setMinimum(2);
      this.jSlider_1a_nRendijas.setMinorTickSpacing(1);
      this.jSlider_1a_nRendijas.setPaintTicks(true);
      this.jSlider_1a_nRendijas.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            YoungApplet.this.jSlider_1a_nRendijas_mouseDragged(e);
         }
      });
      this.jSlider_1a_nRendijas.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            YoungApplet.this.jSlider_1a_nRendijas_mouseClicked(e);
         }
      });
      this.jSlider_1a_nRendijas.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            YoungApplet.this.jSlider_1a_nRendijas_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            YoungApplet.this.jSlider_1a_nRendijas_keyTyped(e);
         }
      });
      this.jLabel1b_c_5.setPreferredSize(new Dimension(300, 15));
      this.jLabel1b_c_5.setForeground(new Color(102, 102, 153));
      this.jLabel1b_c_5.setText(this.label_etiqueta[3][idioma] + " " + this.label_etiqueta[10][idioma] + ": " + this.lambda + " nm");
      this.jLabel1b_c_6.setPreferredSize(new Dimension(300, 15));
      this.jLabel1b_c_6.setForeground(new Color(102, 102, 153));
      this.jLabel1b_c_6.setText(this.label_etiqueta[2][idioma] + ": " + this.d_planos + " m");
      this.jLabel1b_c_4.setPreferredSize(new Dimension(300, 15));
      this.jLabel1b_c_4.setForeground(new Color(102, 102, 153));
      this.jLabel1b_c_4.setText(this.label_etiqueta[1][idioma] + ": " + this.d_holes + " mm");
      this.jLabel1b_c_7.setPreferredSize(new Dimension(300, 15));
      this.jLabel1b_c_7.setForeground(new Color(102, 102, 153));
      this.jLabel1b_c_7.setText(this.label_etiqueta[4][idioma] + ": " + this.n_refraccion);
      this.jSplitPane1.setBackground(Color.black);
      this.jSplitPane1.setContinuousLayout(true);
      this.jSplitPane1.setDividerSize(0);
      this.jSplitPane1.setOneTouchExpandable(false);
      this.jSplitPane1.setDividerLocation(0);
      this.jButton1b_grafico.setPreferredSize(new Dimension(100, 20));
      this.jButton1b_grafico.setText(this.boton_etiqueta[0][idioma]);
      this.jButton1b_grafico.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            YoungApplet.this.jButton1b_grafico_actionPerformed(e);
         }
      });
      this.jLabel1b_c_3.setPreferredSize(new Dimension(300, 15));
      this.jLabel1b_c_3.setForeground(new Color(102, 102, 153));
      this.jLabel1b_c_3.setText(this.label_etiqueta[5][idioma] + ": " + this.n_rendijas);
      this.jButton1b_intensidad.setPreferredSize(new Dimension(190, 20));
      this.jButton1b_intensidad.setText(this.boton_etiqueta[3][idioma]);
      this.jButton1b_intensidad.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            YoungApplet.this.jButton1b_intensidad_actionPerformed(e);
         }
      });
      this.jLabel1b_c_8.setPreferredSize(new Dimension(300, 15));
      this.jLabel1b_c_8.setForeground(new Color(102, 102, 153));
      this.jLabel1b_c_8.setText(this.label_etiqueta[7][idioma] + ": " + this.dist_max + " mm");
      this.jLabel1b_c_9.setMinimumSize(new Dimension(190, 17));
      this.jLabel1b_c_9.setPreferredSize(new Dimension(300, 15));
      this.jLabel1b_c_9.setForeground(new Color(102, 102, 153));
      this.jLabel1b_c_9.setText(this.label_etiqueta[8][idioma] + ": " + this.dist_min + " mm");
      this.flowLayout5.setAlignment(2);
      this.jLabel1b_c_2.setForeground(Color.darkGray);
      this.jLabel1b_c_2.setPreferredSize(new Dimension(300, 15));
      this.jLabel1b_c_2.setText(this.label_etiqueta[9][idioma] + ": " + this.zona_vis + " mm");
      this.jLabel2a_c_1.setPreferredSize(new Dimension(375, 17));
      this.jLabel2a_c_1.setHorizontalAlignment(0);
      this.jLabel2a_c_1.setHorizontalTextPosition(0);
      this.jLabel2a_c_1.setForeground(new Color(102, 102, 153));
      this.jLabel2a_c_1.setText(this.label_etiqueta[0][idioma]);
      this.jLabel2a_c_2.setPreferredSize(new Dimension(100, 17));
      this.jLabel2a_c_2.setForeground(new Color(102, 102, 153));
      this.jLabel2a_c_2.setText(this.label_coherencia[0][idioma] + ":");
      this.jRadioButton_1puntual.setForeground(new Color(102, 102, 153));
      this.jRadioButton_1puntual.setPreferredSize(new Dimension(77, 17));
      this.jRadioButton_1puntual.setSelected(true);
      this.jRadioButton_1puntual.setText(this.label_coherencia[1][idioma]);
      this.jRadioButton_1puntual.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            YoungApplet.this.jRadioButton_1puntual_actionPerformed(e);
         }
      });
      this.jRadioButton_2puntual.setForeground(new Color(102, 102, 153));
      this.jRadioButton_2puntual.setPreferredSize(new Dimension(84, 17));
      this.jRadioButton_2puntual.setText(this.label_coherencia[2][idioma]);
      this.jRadioButton_2puntual.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            YoungApplet.this.jRadioButton_2puntual_actionPerformed(e);
         }
      });
      this.jRadioButton_1extensa.setForeground(new Color(102, 102, 153));
      this.jRadioButton_1extensa.setPreferredSize(new Dimension(81, 17));
      this.jRadioButton_1extensa.setText(this.label_coherencia[3][idioma]);
      this.jRadioButton_1extensa.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            YoungApplet.this.jRadioButton_1extensa_actionPerformed(e);
         }
      });
      this.jLabel2a_c_3.setPreferredSize(new Dimension(150, 17));
      this.jLabel2a_c_3.setForeground(new Color(102, 102, 153));
      this.jLabel2a_c_3.setText(this.label_coherencia[4][idioma]);
      this.jSlider_2a_dFuente.setMajorTickSpacing(10);
      this.jSlider_2a_dFuente.setMaximum(60);
      this.jSlider_2a_dFuente.setMinorTickSpacing(2);
      this.jSlider_2a_dFuente.setPaintTicks(true);
      this.jSlider_2a_dFuente.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            YoungApplet.this.jSlider_2a_dFuente_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            YoungApplet.this.jSlider_2a_dFuente_keyTyped(e);
         }
      });
      this.jSlider_2a_dFuente.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            YoungApplet.this.jSlider_2a_dFuente_mouseClicked(e);
         }
      });
      this.jSlider_2a_dFuente.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            YoungApplet.this.jSlider_2a_dFuente_mouseDragged(e);
         }
      });
      this.jLabel2a_c_4.setPreferredSize(new Dimension(150, 17));
      this.jLabel2a_c_4.setForeground(new Color(102, 102, 153));
      this.jLabel2a_c_4.setText(this.label_etiqueta[1][idioma]);
      this.jLabel2a_c_5.setPreferredSize(new Dimension(150, 17));
      this.jLabel2a_c_5.setForeground(new Color(102, 102, 153));
      this.jLabel2a_c_5.setText(this.label_etiqueta[2][idioma]);
      this.jSlider_2a_dRendijas.setMajorTickSpacing(10);
      this.jSlider_2a_dRendijas.setMaximum(60);
      this.jSlider_2a_dRendijas.setMinimum(10);
      this.jSlider_2a_dRendijas.setMinorTickSpacing(2);
      this.jSlider_2a_dRendijas.setPaintTicks(true);
      this.jSlider_2a_dRendijas.setPreferredSize(new Dimension(200, 27));
      this.jSlider_2a_dRendijas.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            YoungApplet.this.jSlider_2a_dRendijas_mouseClicked(e);
         }
      });
      this.jSlider_2a_dRendijas.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            YoungApplet.this.jSlider_2a_dRendijas_mouseDragged(e);
         }
      });
      this.jSlider_2a_dRendijas.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            YoungApplet.this.jSlider_2a_dRendijas_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            YoungApplet.this.jSlider_2a_dRendijas_keyTyped(e);
         }
      });
      this.jSlider_2a_dPlanos.setMajorTickSpacing(10);
      this.jSlider_2a_dPlanos.setMaximum(60);
      this.jSlider_2a_dPlanos.setMinimum(10);
      this.jSlider_2a_dPlanos.setMinorTickSpacing(2);
      this.jSlider_2a_dPlanos.setPaintTicks(true);
      this.jSlider_2a_dPlanos.setPreferredSize(new Dimension(200, 27));
      this.jSlider_2a_dPlanos.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            YoungApplet.this.jSlider_2a_dPlanos_mouseDragged(e);
         }
      });
      this.jSlider_2a_dPlanos.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            YoungApplet.this.jSlider_2a_dPlanos_mouseClicked(e);
         }
      });
      this.jSlider_2a_dPlanos.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            YoungApplet.this.jSlider_2a_dPlanos_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            YoungApplet.this.jSlider_2a_dPlanos_keyTyped(e);
         }
      });
      this.jLabel2a_c_6.setMaximumSize(new Dimension(150, 17));
      this.jLabel2a_c_6.setMinimumSize(new Dimension(150, 17));
      this.jLabel2a_c_6.setPreferredSize(new Dimension(150, 17));
      this.jLabel2a_c_6.setForeground(new Color(102, 102, 153));
      this.jLabel2a_c_6.setText(this.label_etiqueta[3][idioma]);
      this.jSlider_2a_lOnda.setMajorTickSpacing(80);
      this.jSlider_2a_lOnda.setMaximum(700);
      this.jSlider_2a_lOnda.setMinimum(400);
      this.jSlider_2a_lOnda.setMinorTickSpacing(20);
      this.jSlider_2a_lOnda.setPaintTicks(true);
      this.jSlider_2a_lOnda.setPreferredSize(new Dimension(200, 27));
      this.jSlider_2a_lOnda.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            YoungApplet.this.jSlider_2a_lOnda_mouseDragged(e);
         }
      });
      this.jSlider_2a_lOnda.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            YoungApplet.this.jSlider_2a_lOnda_mouseClicked(e);
         }
      });
      this.jSlider_2a_lOnda.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            YoungApplet.this.jSlider_2a_lOnda_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            YoungApplet.this.jSlider_2a_lOnda_keyTyped(e);
         }
      });
      this.jLabel2a_c_nRefrac.setPreferredSize(new Dimension(150, 17));
      this.jLabel2a_c_nRefrac.setForeground(new Color(102, 102, 153));
      this.jLabel2a_c_nRefrac.setText(this.label_etiqueta[4][idioma]);
      this.jSlider_2a_nRefrac.setMajorTickSpacing(25);
      this.jSlider_2a_nRefrac.setMaximum(200);
      this.jSlider_2a_nRefrac.setMinimum(100);
      this.jSlider_2a_nRefrac.setMinorTickSpacing(5);
      this.jSlider_2a_nRefrac.setPaintTicks(true);
      this.jSlider_2a_nRefrac.setPreferredSize(new Dimension(200, 27));
      this.jSlider_2a_nRefrac.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            YoungApplet.this.jSlider_2a_nRefrac_mouseDragged(e);
         }
      });
      this.jSlider_2a_nRefrac.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            YoungApplet.this.jSlider_2a_nRefrac_mouseClicked(e);
         }
      });
      this.jSlider_2a_nRefrac.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            YoungApplet.this.jSlider_2a_nRefrac_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            YoungApplet.this.jSlider_2a_nRefrac_keyTyped(e);
         }
      });
      this.jButton_acercade.setForeground(Color.darkGray);
      this.jButton_acercade.setPreferredSize(new Dimension(120, 20));
      this.jButton_acercade.setText(this.acerca_etiqueta[0][idioma]);
      this.jButton_acercade.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            YoungApplet.this.jButton_acercade_actionPerformed(e);
         }
      });
      this.jLabel2b_c_1.setPreferredSize(new Dimension(375, 15));
      this.jLabel2b_c_1.setHorizontalAlignment(0);
      this.jLabel2b_c_1.setForeground(new Color(102, 102, 153));
      this.jLabel2b_c_1.setText(this.label_etiqueta[6][idioma]);
      this.jLabel2b_c_2.setForeground(Color.darkGray);
      this.jLabel2b_c_2.setPreferredSize(new Dimension(300, 15));
      this.jLabel2b_c_2.setText(this.label_etiqueta[9][idioma] + ": " + this.zona_vis + " mm");
      this.jLabel2b_c_3.setPreferredSize(new Dimension(300, 15));
      this.jLabel2b_c_3.setForeground(new Color(102, 102, 153));
      if (this.tipo_fuente == 0) {
         this.jLabel2b_c_3.setText(this.label_coherencia[7][idioma] + ": " + this.d_fuente + " mm");
      }

      if (this.tipo_fuente == 1) {
         this.jLabel2b_c_3.setText(this.label_coherencia[8][idioma] + ": " + this.d_fuente + " mm");
      }

      if (this.tipo_fuente == 2) {
         this.jLabel2b_c_3.setText(this.label_coherencia[6][idioma] + ": " + this.d_fuente + " mm");
      }

      this.jLabel2b_c_4.setPreferredSize(new Dimension(300, 15));
      this.jLabel2b_c_4.setForeground(new Color(102, 102, 153));
      this.jLabel2b_c_4.setText(this.label_etiqueta[1][idioma] + ": " + this.d_holes + " mm");
      this.jLabel2b_c_5.setPreferredSize(new Dimension(300, 15));
      this.jLabel2b_c_5.setForeground(new Color(102, 102, 153));
      this.jLabel2b_c_5.setText(this.label_etiqueta[3][idioma] + " " + this.label_etiqueta[10][idioma] + ": " + this.lambda + " nm");
      this.jLabel2b_c_6.setPreferredSize(new Dimension(300, 15));
      this.jLabel2b_c_6.setForeground(new Color(102, 102, 153));
      this.jLabel2b_c_6.setText(this.label_etiqueta[2][idioma] + ": " + this.d_planos + " m" + this.label_coherencia[9][idioma] + ": " + (7.0D - this.d_planos) + " m");
      this.jLabel2b_c_7.setPreferredSize(new Dimension(300, 15));
      this.jLabel2b_c_7.setForeground(new Color(102, 102, 153));
      this.jLabel2b_c_7.setText(this.label_etiqueta[4][idioma] + ": " + this.n_refraccion);
      this.jLabel2b_c_8.setPreferredSize(new Dimension(300, 15));
      this.jLabel2b_c_8.setForeground(new Color(102, 102, 153));
      this.jLabel2b_c_8.setText(this.label_etiqueta[7][idioma] + ": " + this.dist_max + " mm");
      this.jLabel2b_c_9.setForeground(new Color(64, 64, 255));
      this.jLabel2b_c_9.setMinimumSize(new Dimension(190, 17));
      this.jLabel2b_c_9.setPreferredSize(new Dimension(300, 15));
      this.jLabel2b_c_9.setText(this.label_coherencia[10][idioma] + ": " + this.factor_vis);
      this.jButton2b_grafico.setPreferredSize(new Dimension(100, 20));
      this.jButton2b_grafico.setText(this.boton_etiqueta[0][idioma]);
      this.jButton2b_grafico.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            YoungApplet.this.jButton2b_grafico_actionPerformed(e);
         }
      });
      this.jLabel_titulo.setForeground(Color.gray);
      this.jLabel_titulo.setPreferredSize(new Dimension(400, 17));
      this.jLabel_titulo.setHorizontalAlignment(2);
      this.jLabel_titulo.setHorizontalTextPosition(2);
      this.jLabel_titulo.setText(this.titulo[idioma]);
      this.jTextPane_teoria.setEditable(false);
      this.jPanel_teoria.setLayout(this.borderLayout19);
      this.jTextPane_teoria.setBackground(new Color(204, 204, 204));
      this.jTextPane_teoria.setEditable(false);
      this.jTextPane_teoria.setMargin(new Insets(10, 10, 10, 10));
      this.jSplitPane2.setBackground(Color.black);
      this.add(this.jPanelBase, "Center");
      this.jPanelBase.add(this.jPanelNorte, "North");
      this.jPanelNorte.add(this.jLabel_titulo, (Object)null);
      this.jPanelNorte.add(this.jButton_acercade, (Object)null);
      this.jPanelBase.add(this.jPanelCentro, "Center");
      this.jPanelCentro.add(this.jTabbedPane1, "Center");
      this.jPanelBase.add(this.jPanelSur, "South");
      this.jPanelSur.add(this.jButtonSalir, (Object)null);
      this.jTabbedPane1.add(this.jPanel1, this.etiqueta[1][idioma]);
      this.jPanel1.add(this.jPanel1a, "West");
      this.jPanel1.add(this.jPanel1b, "Center");
      this.jPanel1b.add(this.jPanel1b_norte, "North");
      this.jTabbedPane1.add(this.jPanel2, this.etiqueta[2][idioma]);
      this.jPanel2.add(this.jPanel2a, "West");
      this.jPanel1a.add(this.jPanel1a_norte, "North");
      this.jPanel1a.add(this.jPanel1a_centro, "Center");
      this.jPanel1a_centro.add(this.jLabel1a_c_1, (Object)null);
      this.jPanel1a_centro.add(this.jLabel1a_c_6, (Object)null);
      this.jPanel1a_centro.add(this.jSlider_1a_nRendijas, (Object)null);
      this.jPanel1a_centro.add(this.jLabel1a_c_2, (Object)null);
      this.jPanel1a_centro.add(this.jSlider_1a_dRendijas, (Object)null);
      this.jPanel1a_norte.add(this.jPanel1a_n_n, "North");
      this.jPanel1a_norte.add(this.jPanel1a_n_w, "West");
      this.jPanel1a_norte.add(this.jPanel1a_n_e, "East");
      this.jPanel1a_norte.add(this.jPanel1a_n_c, "Center");
      this.jPanel1a_centro.add(this.jLabel1a_c_3, (Object)null);
      this.jPanel1a_centro.add(this.jSlider_1a_dPlanos, (Object)null);
      this.jPanel1a_centro.add(this.jLabel1a_c_4, (Object)null);
      this.jPanel1a_centro.add(this.jSlider_1a_longOnda, (Object)null);
      this.jPanel1a_centro.add(this.jLabel1a_c_5, (Object)null);
      this.jPanel1a_centro.add(this.jSlider_1a_nRefrac, (Object)null);
      this.jPanel1b.add(this.jPanel1b_centro, "Center");
      this.jPanel1b_centro.add(this.jLabel1b_c_1, (Object)null);
      this.jPanel1b_centro.add(this.jLabel1b_c_2, (Object)null);
      this.jPanel1b_centro.add(this.jLabel1b_c_3, (Object)null);
      this.jPanel1b_centro.add(this.jLabel1b_c_4, (Object)null);
      this.jPanel1b_centro.add(this.jLabel1b_c_5, (Object)null);
      this.jPanel1b_centro.add(this.jLabel1b_c_6, (Object)null);
      this.jPanel1b_centro.add(this.jLabel1b_c_7, (Object)null);
      this.jPanel1b_centro.add(this.jLabel1b_c_8, (Object)null);
      this.jPanel1b_centro.add(this.jLabel1b_c_9, (Object)null);
      this.jPanel1b_centro.add(this.jButton1b_intensidad, (Object)null);
      this.jPanel1b_centro.add(this.jButton1b_grafico, (Object)null);
      this.jPanel1b_norte.add(this.jPanel1b_n_n, "North");
      this.jPanel1b_norte.add(this.jPanel1b_n_w, "West");
      this.jPanel1b_norte.add(this.jPanel1b_n_e, "East");
      this.jPanel1b_norte.add(this.jPanel1b_n_c, "Center");
      this.jPanel1b_n_c.add(this.jSplitPane1, "Center");
      this.jPanel2.add(this.jPanel2b, "Center");
      this.jPanel2a.add(this.jPanel2a_norte, "North");
      this.jPanel2a.add(this.jPanel2a_centro, "Center");
      this.jPanel2a_norte.add(this.jPanel2a_n_n, "North");
      this.jPanel2a_norte.add(this.jPanel2a_n_w, "West");
      this.jPanel2a_norte.add(this.jPanel2a_n_e, "East");
      this.jPanel2a_norte.add(this.jPanel2a_n_c, "Center");
      this.jPanel2b.add(this.jPanel2b_norte, "North");
      this.jPanel2b.add(this.jPanel2b_centro, "Center");
      this.jPanel2b_norte.add(this.jPanel2b_n_n, "North");
      this.jPanel2b_norte.add(this.jPanel2b_n_w, "West");
      this.jPanel2b_norte.add(this.jPanel2b_n_e, "East");
      this.jPanel2b_norte.add(this.jPanel2b_n_c, "Center");
      this.jSplitPane2.setContinuousLayout(true);
      this.jSplitPane2.setDividerSize(0);
      this.jSplitPane2.setOneTouchExpandable(false);
      this.jSplitPane2.setDividerLocation(0);
      this.jPanel2b_n_c.add(this.jSplitPane2, "Center");
      this.jPanel1a_n_c.add(this.esquema, "Center");
      this.jPanel2a_centro.add(this.jLabel2a_c_1, (Object)null);
      this.jPanel2a_centro.add(this.jLabel2a_c_2, (Object)null);
      this.jPanel2a_centro.add(this.jRadioButton_1puntual, (Object)null);
      this.jPanel2a_centro.add(this.jRadioButton_2puntual, (Object)null);
      this.jPanel2a_centro.add(this.jRadioButton_1extensa, (Object)null);
      this.jPanel2a_centro.add(this.jLabel2a_c_3, (Object)null);
      this.jPanel2a_centro.add(this.jSlider_2a_dFuente, (Object)null);
      this.jPanel2a_centro.add(this.jLabel2a_c_4, (Object)null);
      this.group_tipo_fuente.add(this.jRadioButton_1puntual);
      this.group_tipo_fuente.add(this.jRadioButton_2puntual);
      this.group_tipo_fuente.add(this.jRadioButton_1extensa);
      this.jPanel2a_centro.add(this.jSlider_2a_dRendijas, (Object)null);
      this.jPanel2a_centro.add(this.jLabel2a_c_5, (Object)null);
      this.jPanel2a_centro.add(this.jSlider_2a_dPlanos, (Object)null);
      this.jPanel2a_centro.add(this.jLabel2a_c_6, (Object)null);
      this.jPanel2a_centro.add(this.jSlider_2a_lOnda, (Object)null);
      this.jPanel2a_centro.add(this.jLabel2a_c_nRefrac, (Object)null);
      this.jPanel2a_centro.add(this.jSlider_2a_nRefrac, (Object)null);
      this.jPanel2a_n_c.add(this.esquemaCoherencia, "Center");
      this.jPanel2b_centro.add(this.jLabel2b_c_1, (Object)null);
      this.jPanel2b_centro.add(this.jLabel2b_c_2, (Object)null);
      this.jPanel2b_centro.add(this.jLabel2b_c_3, (Object)null);
      this.jPanel2b_centro.add(this.jLabel2b_c_4, (Object)null);
      this.jPanel2b_centro.add(this.jLabel2b_c_5, (Object)null);
      this.jPanel2b_centro.add(this.jLabel2b_c_6, (Object)null);
      this.jPanel2b_centro.add(this.jLabel2b_c_7, (Object)null);
      this.jPanel2b_centro.add(this.jLabel2b_c_8, (Object)null);
      this.jPanel2b_centro.add(this.jLabel2b_c_9, (Object)null);
      this.jPanel2b_centro.add(this.jButton2b_grafico, (Object)null);
      this.jTabbedPane1.add(this.jPanel_teoria, this.info_etiqueta[idioma]);
      this.jPanel_teoria.add(this.jScrollPane_teoria, "Center");
      this.jScrollPane_teoria.getViewport().add(this.jTextPane_teoria, (Object)null);
   }

   public String getAppletInfo() {
      return "Applet Information";
   }

   public String[][] getParameterInfo() {
      String[][] pinfo = new String[][]{{"idioma", "int", ""}};
      return pinfo;
   }

   public static void main(String[] args) {
      try {
         String dato = args[0].toUpperCase().intern();
         if (dato == "CA") {
            idioma = 1;
         } else if (dato == "ES") {
            idioma = 0;
         } else if (dato == "EN") {
            idioma = 2;
         } else {
            idioma = 0;
         }
      } catch (Exception var7) {
         idioma = 0;
      }

      YoungApplet applet = new YoungApplet();
      applet.isStandalone = true;
      Frame frame = new Frame() {
         protected void processWindowEvent(WindowEvent e) {
            super.processWindowEvent(e);
            if (e.getID() == 201) {
               System.exit(0);
            }

         }

         public synchronized void setTitle(String title) {
            super.setTitle(title);
            this.enableEvents(64L);
         }
      };
      frame.setTitle("Young Applet");
      frame.setResizable(false);
      frame.add(applet, "Center");

      try {
         String st_icon = "jocon.jpg";
         URL url_icon = frame.getClass().getResource(st_icon);
         ImageIcon icon = new ImageIcon(url_icon, "Interferencias de Young");
         frame.setIconImage(icon.getImage());
      } catch (Exception var6) {
         System.out.println("No carga icono");
      }

      applet.init();
      applet.start();
      frame.setSize(750, 570);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation((d.width - frame.getSize().width) / 4, (d.height - frame.getSize().height) / 4);
      frame.setVisible(true);
   }

   void jButtonSalir_actionPerformed(ActionEvent e) {
      try {
         System.exit(0);
      } catch (Exception var3) {
         System.out.println("Error al salir");
      }

   }

   void jSlider_1a_longOnda_mouseClicked(MouseEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_longOnda_mouseDragged(MouseEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_dPlanos_mouseDragged(MouseEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_dPlanos_mouseClicked(MouseEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_dPlanos_keyPressed(KeyEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_nRefrac_mouseDragged(MouseEvent e) {
      this.actuaParametros();
   }

   void actuaParametros() {
      this.d_holes = (double)this.jSlider_1a_dRendijas.getValue() / 10.0D;
      this.d_planos = (double)this.jSlider_1a_dPlanos.getValue() / 10.0D;
      this.lambda = this.jSlider_1a_longOnda.getValue();
      this.n_refraccion = (double)this.jSlider_1a_nRefrac.getValue() / 100.0D;
      int colorfondo = 50 + (int)((double)this.jSlider_1a_nRefrac.getValue() * 0.2D);
      this.n_rendijas = this.jSlider_1a_nRendijas.getValue();
      this.jPanel1a_n_c.setBackground(new Color(colorfondo, colorfondo, colorfondo));
      this.esquema.putAtributos(this.n_rendijas, this.lambda, this.d_holes, this.d_planos);
      this.franjas.putAtributos(this.n_rendijas, this.lambda, this.d_holes, this.d_planos, this.n_refraccion, this.label_int_log);
      this.grafico.putAtributos(this.n_rendijas, this.lambda, this.d_holes, this.d_planos, this.n_refraccion);
      this.jPanel1a_norte.repaint();
      this.jPanel1b_norte.repaint();
      this.jLabel1b_c_5.setText(this.label_etiqueta[3][idioma] + " " + this.label_etiqueta[10][idioma] + ": " + this.lambda + " nm");
      this.jLabel1b_c_6.setText(this.label_etiqueta[2][idioma] + ": " + this.d_planos + " m");
      this.jLabel1b_c_4.setText(this.label_etiqueta[1][idioma] + ": " + this.d_holes + " mm");
      this.jLabel1b_c_7.setText(this.label_etiqueta[4][idioma] + ": " + this.n_refraccion);
      this.jLabel1b_c_3.setText(this.label_etiqueta[5][idioma] + ": " + this.n_rendijas);
      char pto = '.';
      this.df_symb.setDecimalSeparator(pto);
      this.df.setDecimalFormatSymbols(this.df_symb);
      this.dist_max = (double)this.lambda * this.d_planos * 0.001D / (this.n_refraccion * this.d_holes);
      this.jLabel1b_c_8.setText(this.label_etiqueta[7][idioma] + ": " + this.df.format(this.dist_max) + " mm");
      if (this.n_rendijas == 2) {
         this.dist_min = this.dist_max;
      } else {
         this.dist_min = this.dist_max / (double)this.n_rendijas;
      }

      this.jLabel1b_c_9.setText(this.label_etiqueta[8][idioma] + ": " + this.df.format(this.dist_min) + " mm");
      if (this.dist_max >= 0.5D) {
         this.zona_vis = 5.0D;
      }

      if (this.dist_max < 0.5D) {
         this.zona_vis = 2.0D;
      }

      if (this.dist_max <= 0.1D && this.dist_min >= 0.05D) {
         this.zona_vis = 1.0D;
      }

      if (this.dist_max <= 0.1D && this.dist_min < 0.05D) {
         this.zona_vis = 0.5D;
      }

      if (this.dist_max <= 0.1D && this.dist_min < 0.02D) {
         this.zona_vis = 0.25D;
      }

      this.jLabel1b_c_2.setText(this.label_etiqueta[9][idioma] + ": " + this.zona_vis + " mm");
   }

   void jSlider_1a_dRendijas_keyPressed(KeyEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_dRendijas_mouseClicked(MouseEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_dRendijas_mouseDragged(MouseEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_longOnda_keyPressed(KeyEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_nRefrac_mouseClicked(MouseEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_nRefrac_keyPressed(KeyEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_dRendijas_keyTyped(KeyEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_dPlanos_keyTyped(KeyEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_longOnda_keyTyped(KeyEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_nRefrac_keyTyped(KeyEvent e) {
      this.actuaParametros();
   }

   void jButton1b_grafico_actionPerformed(ActionEvent e) {
      if (!this.label_grafico) {
         this.jButton1b_grafico.setText(this.boton_etiqueta[1][idioma]);
         this.jSplitPane1.setDividerLocation(1.0D);
         this.label_grafico = true;
      } else {
         this.jButton1b_grafico.setText(this.boton_etiqueta[0][idioma]);
         this.jSplitPane1.setDividerLocation(0);
         this.label_grafico = false;
      }

   }

   void jSlider_1a_nRendijas_keyPressed(KeyEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_nRendijas_keyTyped(KeyEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_nRendijas_mouseClicked(MouseEvent e) {
      this.actuaParametros();
   }

   void jSlider_1a_nRendijas_mouseDragged(MouseEvent e) {
      this.actuaParametros();
   }

   void jButton1b_intensidad_actionPerformed(ActionEvent e) {
      if (!this.label_int_log) {
         this.jButton1b_intensidad.setText(this.boton_etiqueta[2][idioma]);
         this.label_int_log = true;
         this.actuaParametros();
      } else {
         this.jButton1b_intensidad.setText(this.boton_etiqueta[3][idioma]);
         this.label_int_log = false;
         this.actuaParametros();
      }

   }

   void jButton_acercade_actionPerformed(ActionEvent e) {
      Frame f = new Frame();
      Object[] options = new Object[]{this.acerca_etiqueta[1][idioma]};
      ImageIcon icon_joc = null;

      try {
         String st_icon = "jocon.jpg";
         URL url_icon = this.getClass().getResource(st_icon);
         icon_joc = new ImageIcon(url_icon, "Interferencias de Young");
      } catch (Exception var7) {
         System.out.println("No carga icono");
      }

      JOptionPane option = new JOptionPane(this.acerca_etiqueta[2][idioma], 1, -1, icon_joc, options);
      JDialog dialog = option.createDialog(f, this.acerca_etiqueta[0][idioma]);
      dialog.setResizable(false);
      dialog.show();
   }

   void jRadioButton_1puntual_actionPerformed(ActionEvent e) {
      this.tipo_fuente = 0;
      this.jLabel2a_c_3.setText(this.label_coherencia[4][idioma]);
      this.actuaParametrosCoherencia();
   }

   void jRadioButton_2puntual_actionPerformed(ActionEvent e) {
      this.tipo_fuente = 1;
      this.jLabel2a_c_3.setText(this.label_coherencia[5][idioma]);
      this.actuaParametrosCoherencia();
   }

   void jRadioButton_1extensa_actionPerformed(ActionEvent e) {
      this.tipo_fuente = 2;
      this.jLabel2a_c_3.setText(this.label_coherencia[6][idioma]);
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_dFuente_mouseDragged(MouseEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_dFuente_mouseClicked(MouseEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_dFuente_keyPressed(KeyEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_dFuente_keyTyped(KeyEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_dRendijas_keyPressed(KeyEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_dRendijas_keyTyped(KeyEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_dRendijas_mouseDragged(MouseEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_dRendijas_mouseClicked(MouseEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_dPlanos_keyPressed(KeyEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_dPlanos_keyTyped(KeyEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_dPlanos_mouseClicked(MouseEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_dPlanos_mouseDragged(MouseEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_lOnda_keyPressed(KeyEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_lOnda_keyTyped(KeyEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_lOnda_mouseClicked(MouseEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_lOnda_mouseDragged(MouseEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_nRefrac_keyPressed(KeyEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_nRefrac_keyTyped(KeyEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_nRefrac_mouseClicked(MouseEvent e) {
      this.actuaParametrosCoherencia();
   }

   void jSlider_2a_nRefrac_mouseDragged(MouseEvent e) {
      this.actuaParametrosCoherencia();
   }

   void actuaParametrosCoherencia() {
      char pto = '.';
      this.df_symb.setDecimalSeparator(pto);
      this.df.setDecimalFormatSymbols(this.df_symb);
      if (this.tipo_fuente == 2) {
         this.d_fuente = (double)this.jSlider_2a_dFuente.getValue() / 10.0D;
      } else {
         this.d_fuente = ((double)this.jSlider_2a_dFuente.getValue() - 30.0D) / 10.0D;
      }

      this.d_holes = (double)this.jSlider_2a_dRendijas.getValue() / 10.0D;
      this.d_planos = (double)this.jSlider_2a_dPlanos.getValue() / 10.0D;
      this.lambda = this.jSlider_2a_lOnda.getValue();
      this.n_refraccion = (double)this.jSlider_2a_nRefrac.getValue() / 100.0D;
      int colorfondo = 50 + (int)((double)this.jSlider_2a_nRefrac.getValue() * 0.2D);
      this.jPanel2a_n_c.setBackground(new Color(colorfondo, colorfondo, colorfondo));
      this.esquemaCoherencia.putAtributos(this.tipo_fuente, this.d_fuente, this.lambda, this.d_holes, this.d_planos);
      this.franjasCoherencia.putAtributos(this.tipo_fuente, this.d_fuente, this.lambda, this.d_holes, this.d_planos, this.n_refraccion);
      this.graficoCoherencia.putAtributos(this.tipo_fuente, this.d_fuente, this.lambda, this.d_holes, this.d_planos, this.n_refraccion);
      this.jPanel2a_norte.repaint();
      this.jPanel2b_norte.repaint();
      this.jLabel2b_c_5.setText(this.label_etiqueta[3][idioma] + " " + this.label_etiqueta[10][idioma] + ": " + this.lambda + " nm");
      this.jLabel2b_c_6.setText(this.label_etiqueta[2][idioma] + ": " + this.d_planos + " m." + this.label_coherencia[9][idioma] + ": " + this.df.format(7.0D - this.d_planos) + " m");
      this.jLabel2b_c_4.setText(this.label_etiqueta[1][idioma] + ": " + this.d_holes + " mm");
      this.jLabel2b_c_7.setText(this.label_etiqueta[4][idioma] + ": " + this.n_refraccion);
      if (this.tipo_fuente == 0) {
         this.jLabel2b_c_3.setText(this.label_coherencia[7][idioma] + ": " + this.d_fuente + " mm");
      }

      if (this.tipo_fuente == 1) {
         this.jLabel2b_c_3.setText(this.label_coherencia[8][idioma] + ": " + this.d_fuente + " mm");
      }

      if (this.tipo_fuente == 2) {
         this.jLabel2b_c_3.setText(this.label_coherencia[6][idioma] + ": " + this.d_fuente + " mm");
      }

      this.dist_max = (double)this.lambda * this.d_planos * 0.001D / (this.n_refraccion * this.d_holes);
      this.jLabel2b_c_8.setText(this.label_etiqueta[7][idioma] + ": " + this.df.format(this.dist_max) + " mm");
      this.dist_min = this.dist_max;
      if (this.dist_max >= 0.5D) {
         this.zona_vis = 5.0D;
      }

      if (this.dist_max < 0.5D) {
         this.zona_vis = 2.0D;
      }

      if (this.dist_max <= 0.1D && this.dist_min >= 0.05D) {
         this.zona_vis = 1.0D;
      }

      if (this.dist_max <= 0.1D && this.dist_min < 0.05D) {
         this.zona_vis = 0.5D;
      }

      if (this.dist_max <= 0.1D && this.dist_min < 0.02D) {
         this.zona_vis = 0.25D;
      }

      this.jLabel2b_c_2.setText(this.label_etiqueta[9][idioma] + ": " + this.zona_vis + " mm");
      if (this.tipo_fuente == 0) {
         this.factor_vis = 1.0D;
      }

      double termino1;
      if (this.tipo_fuente == 1) {
         termino1 = 3.141592653589793D * this.d_fuente * this.d_holes * this.n_refraccion / ((double)this.lambda * 0.001D * (7.0D - this.d_planos));
         this.factor_vis = Math.cos(termino1) * Math.cos(termino1);
      }

      if (this.tipo_fuente == 2) {
         if (this.d_fuente == 0.0D) {
            this.factor_vis = 1.0D;
         } else {
            termino1 = (double)this.lambda * 0.001D * (7.0D - this.d_planos) / (3.141592653589793D * this.d_holes * this.d_fuente * this.n_refraccion);
            double termino2 = 3.141592653589793D * this.d_fuente * this.d_holes * this.n_refraccion / ((double)this.lambda * 0.001D * (7.0D - this.d_planos));
            this.factor_vis = Math.abs(termino1 * Math.sin(termino2));
         }
      }

      this.jLabel2b_c_9.setText(this.label_coherencia[10][idioma] + ": " + this.df.format(this.factor_vis));
   }

   void jButton2b_grafico_actionPerformed(ActionEvent e) {
      if (!this.label_grafico2) {
         this.jButton2b_grafico.setText(this.boton_etiqueta[1][idioma]);
         this.jSplitPane2.setDividerLocation(1.0D);
         this.label_grafico2 = true;
      } else {
         this.jButton2b_grafico.setText(this.boton_etiqueta[0][idioma]);
         this.jSplitPane2.setDividerLocation(0);
         this.label_grafico2 = false;
      }

   }

   private void carga_info(int lengua) {
      String s = null;

      try {
         if (lengua == 1) {
            s = "DocA_YoungCa.htm";
         } else if (lengua == 2) {
            s = "DocA_YoungEn.htm";
         } else {
            s = "DocA_YoungEs.htm";
         }

         this.info_page = this.getClass().getResource(s);
      } catch (Exception var5) {
         System.err.println("Couldn't create help URL: " + s);
      }

      try {
         this.jTextPane_teoria.setPage(this.info_page);
      } catch (IOException var4) {
         System.err.println("Attempted to read a bad URL: " + this.info_page);
      }

   }
}
