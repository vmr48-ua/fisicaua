package dispersion;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;

public class DispersionApplet extends Applet {
   private boolean isStandalone = false;
   static int lang;
   static ImageIcon icon_joc = null;
   String[][] etiqueta_acerca = new String[][]{{"Acerca de", "En quant a", "About"}, {"Aceptar", "Acceptar", "OK"}, {"Javaoptics: Applet de dispersión de la luz v1.0 \n Grup d'Innovació Docent en Òptica Física i Fotònica \n Universitat de Barcelona, 2005 \n La utilización de este programa está bajo una licencia de Creative Commons \n Ver condiciones en http://creativecommons.org/licenses/by-nc-sa/2.1/es/deed.es \n \n Curso de Óptica en Java DOI: 10.1344/401.000000050 \n Applet de dispersión de la luz DOI:", "Javaoptics: Applet de dispersió de la llum v1.0 \n Grup d'Innovació Docent en Òptica Física i Fotònica \n Universitat de Barcelona, 2005 \n La utilització d'aquest programa està sota una llicència de Creative Commons \n Veure condicions a http://www.publicacions.ub.es/doi/licencia/resum-deriv.htm \n \n Curs d'Òptica en Java DOI: 10.1344/401.000000050 \n Applet de dispersió de la llum DOI:", "Javaoptics: Light dispersion Applet v1.0 \n Grup d'Innovació Docent en Òptica Física i Fotònica \n Universitat de Barcelona, 2005 \n The use of this program is under a Creative Commons license \n See conditions in http://creativecommons.org/licenses/by-nc-sa/2.1/es/deed.en \n \n Java Optics Course DOI: 10.1344/401.000000050 \n Light dispersion applet DOI:"}};
   String[] etiqueta_boton_salir = new String[]{"Salir", "Sortir", "Exit"};
   String[] info_etiqueta = new String[]{"Resumen Teoría", "Resum Teoria", "Theory Summary"};
   String[] escoger = new String[]{"Elección Libre", "Elecció lliure", "Free Selection"};
   String[] silice = new String[]{"Sílice fundido", "Sílice fos", "Fused Silica"};
   String[] tipo_luz = new String[]{"Luz blanca", "Llum blanca", "White light"};
   String[][] etiquetas_prisma = new String[][]{{"Ángulo de incidencia:", "Angle d'incidència:", "Angle of incidence:"}, {"Ángulo del prisma:", "Angle del prisma:", "Angle of the prism:"}, {"Ángulo de desviación mínima", "Angle de mínima desviació", "Angle of minimum deviation"}, {"Índice de refracción", "Índex de refracció", "Refraction index"}, {"Vidrio", "Vidre", "Glass"}, {"Composición espectral", "Composició espectral", "Spectral composition"}, {"Longitudes de onda:", "Longituts d'ona:", "Wave lengths:"}, {"Ángulo desviación", "Angle desviació", "Deviation angle"}, {"Dispersión en un prisma", "Dispersió en un prisma", "Dispersion in a prism"}, {"Ángulo de desviación", "Angle de desviació", "Angle of deviation"}, {"no es posible", "no és posible", "it's no possible"}};
   String[][] etiquetas_arcoiris = new String[][]{{"Arco iris", "Arc de Sant Martí", "Rainbow"}, {"Parámetro de impacto", "Paràmetre d'impacte", "Impact parameter"}, {"Longitud de onda", "Longitud d'ona", "Wave lenght"}, {"Ángulo de desviación arco primario:", "Angle de desviació arc primari:", "Deviation angle of primary rainbow:"}, {"Ángulo de desviación arco secundario:", "Angle de desviació arc secundari:", "Deviation angle of secondary rainbow:"}, {"Parám. impacto desv. mín. del arco primario:", "Paràm. impacte desv. mín. del arc primari:", "Impact param. minimum dev. ang. primary rainbow:"}, {"Parám. impacto desv. mín. del arco secundario:", "Paràm. impacte desv. mín. del arc secundari:", "Impact param. minimum dev. ang. secondary rainbow:"}, {"Ángulo desviación mínima arco primario:", "Angle desviació mínima arc primari:", "Minimum deviation angle of primary rainbow:"}, {"Ángulo desviación mínima arco secundario:", "Angle desviació mínima arc secundari:", "Minimum deviation angle of secondary rainbow:"}, {"Ángulo de entrada:", "Angle d'entrada:", "Angle in:"}, {"Ángulo de salida:", "Angle de sortida:", "Angle out:"}};
   double[][] indices_vidrios = new double[][]{{0.0D, 0.0D, 0.0D}, {1.447445818D, 4010.422126D, -7.18180972E7D}, {1.503832281D, 4679.833919D, -7.009154527E7D}, {1.557176473D, 5357.545919D, -2.328710996E7D}, {1.595867948D, 7882.891778D, 1.594766114E8D}, {1.700233962D, 5961.602015D, 2.976417754E8D}};
   double[][] espectro_luz = new double[][]{{0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}, {400.0D, 450.0D, 500.0D, 550.0D, 600.0D, 650.0D, 700.0D}, {404.7D, 407.8D, 435.8D, 491.6D, 546.1D, 576.9D, 579.0D}, {479.9D, 508.6D, 533.7D, 537.8D, 609.9D, 643.8D, 643.8D}, {472.2D, 481.0D, 491.1D, 589.4D, 602.1D, 621.4D, 636.2D}};
   DecimalFormatSymbols df_symb = new DecimalFormatSymbols();
   DecimalFormat df;
   DecimalFormat df2;
   int[] long_onda_prisma;
   double[] indices_prisma;
   double[] angdesv_prisma;
   double[] ange2prima_prisma;
   double ang_prisma;
   double ang_in_prisma;
   double n0_prisma;
   double nA_prisma;
   double nB_prisma;
   boolean label_prisma_zoom;
   int long_onda_rainbow;
   double param_imp_rainbow;
   JPanel panel_Base;
   BorderLayout borderLayout1;
   BorderLayout borderLayout2;
   JPanel panel_Norte;
   JPanel panel_Centro;
   JPanel panel_Sur;
   FlowLayout flowLayout1;
   JButton boton_salir;
   JButton boton_acercade;
   BorderLayout borderLayout3;
   JTabbedPane tabbedPane;
   JPanel panel_prisma;
   BorderLayout borderLayout4;
   JPanel panel_prisma_w;
   JPanel panel_prisma_c;
   JPanel panel_p_wn;
   BorderLayout borderLayout5;
   JPanel panel_p_wc;
   BorderLayout borderLayout6;
   JPanel panel_p_cn;
   JPanel panel_p_cc;
   BorderLayout borderLayout7;
   JPanel panel_p_wnn;
   JPanel panel_p_wnw;
   JPanel panel_p_wns;
   JPanel panel_p_wne;
   JPanel panel_p_wnc;
   FlowLayout flowLayout2;
   JLabel label_p_angp;
   JSlider slider_p_angp;
   JLabel label_p_nangp;
   JLabel label_p_angin;
   JSlider slider_p_angin;
   JLabel label_p_nangin;
   JLabel label_p_angdesvmin;
   JLabel label_p_indc;
   JComboBox comboBox_p_material;
   JLabel label_p_indc_n0;
   JSlider slider_p_indc_n0;
   JLabel label_p_indc_vn0;
   JLabel label_p_indc_A;
   JSlider slider_p_indc_A;
   JLabel label_p_indc_vA;
   JLabel label_p_indc_B;
   JSlider slider_p_indc_B;
   JLabel label_p_indc_vB;
   JLabel label_p_indc_nd;
   JLabel label_p_indc_nF;
   JLabel label_p_indc_nC;
   JLabel label_p_abbe;
   JLabel label_p_tipovidrio;
   BorderLayout borderLayout8;
   JPanel panel_p_ccn;
   JPanel panel_p_ccc;
   JPanel panel_p_ccs;
   JLabel label_p_compespec;
   JComboBox comboBox_p_espec;
   FlowLayout flowLayout3;
   FlowLayout flowLayout4;
   JLabel[] label_p_longondas;
   JSlider[] slider_p_longondas;
   JLabel[] label_p_vlongondas;
   JLabel[] label_p_angdesvs;
   JLabel label_p_longonda;
   JLabel label_p_angdesv;
   DibujoPrisma dibujoprisma;
   BorderLayout borderLayout9;
   JPanel panel_p_wncn;
   JPanel panel_p_wncc;
   BorderLayout borderLayout10;
   DibujoLineasPrisma dibujolineasprisma;
   BorderLayout borderLayout11;
   JLabel label_p_nada;
   JToggleButton toggleButton_p_zoom;
   JTabbedPane tabbedPane_p_graficos;
   BorderLayout borderLayout12;
   JPanel panel_p_indice;
   JPanel panel_p_delta;
   BorderLayout borderLayout13;
   JPanel panel_p_indice_w;
   JPanel panel_p_indice_n;
   JPanel panel_p_indice_s;
   JPanel panel_p_indice_e;
   JPanel panel_p_indice_c;
   BorderLayout borderLayout14;
   IndicePrisma indiceprisma;
   BorderLayout borderLayout15;
   JPanel panel_p_delta_n;
   JPanel panel_p_delta_w;
   JPanel panel_p_delta_e;
   JPanel panel_p_delta_s;
   JPanel panel_p_delta_c;
   BorderLayout borderLayout16;
   GraficoAnguloDesviacion graficoangulodesviacion;
   JPanel panel_rainbow;
   BorderLayout borderLayout17;
   JPanel panel_rainbow_w;
   JPanel panel_rainbow_c;
   BorderLayout borderLayout18;
   JPanel panel_r_wn;
   BorderLayout borderLayout19;
   JPanel panel_r_wc;
   JPanel panel_r_wnn;
   JPanel panel_r_wnw;
   JPanel panel_r_wns;
   JPanel panel_r_wne;
   JPanel panel_r_wnc;
   BorderLayout borderLayout20;
   FlowLayout flowLayout5;
   JLabel label_r_paramimp;
   JSlider slider_r_paramimp;
   JLabel label_r_nparamimp;
   JLabel label_r_longonda;
   JSlider slider_r_longonda;
   JLabel label_r_nlongonda;
   DibujoGota dibujogota;
   BorderLayout borderLayout21;
   JPanel panel_r_cn;
   JPanel panel_r_cc;
   BorderLayout borderLayout22;
   JPanel panel_r_cnn;
   JPanel panel_r_cnw;
   JPanel panel_r_cne;
   JPanel panel_r_cns;
   JPanel panel_r_cnc;
   DibujoArcoIris dibujoarco;
   BorderLayout borderLayout23;
   BorderLayout borderLayout24;
   JPanel panel_r_ccn;
   JPanel panel_r_ccs;
   JPanel panel_r_ccw;
   JPanel panel_r_cce;
   JPanel panel_r_ccc;
   GraficoDesviacionArcoIris graficodesvarcoiris;
   BorderLayout borderLayout25;
   JLabel label_r_angdesv1;
   JLabel label_r_angdesv1_val;
   JLabel label_r_angdesv2;
   JLabel label_r_angdesv2_val;
   JLabel label_r_parimpmin1;
   JLabel label_r_parimpmin1_val;
   JLabel label_r_parimpmin2;
   JLabel label_r_parimpmin2_val;
   JLabel label_r_angdesvmin1;
   JLabel label_r_angdesvmin1_val;
   JLabel label_r_angdesvmin2;
   JLabel label_r_angdesvmin2_val;
   JLabel label_r_angin;
   JLabel label_r_angin_val;
   JLabel label_r_angout;
   JLabel label_r_angout_val;
   JPanel jPanel_info;
   BorderLayout borderLayout26;
   JScrollPane jScrollPane_info;
   JTextPane jTextPane_info;
   URL info_page;

   public String getParameter(String key, String def) {
      return this.isStandalone ? System.getProperty(key, def) : (this.getParameter(key) != null ? this.getParameter(key) : def);
   }

   public DispersionApplet() {
      this.df = new DecimalFormat("#.####", this.df_symb);
      this.df2 = new DecimalFormat("#.##", this.df_symb);
      this.long_onda_prisma = new int[7];
      this.indices_prisma = new double[7];
      this.angdesv_prisma = new double[7];
      this.ange2prima_prisma = new double[7];
      this.panel_Base = new JPanel();
      this.borderLayout1 = new BorderLayout();
      this.borderLayout2 = new BorderLayout();
      this.panel_Norte = new JPanel();
      this.panel_Centro = new JPanel();
      this.panel_Sur = new JPanel();
      this.flowLayout1 = new FlowLayout();
      this.boton_salir = new JButton();
      this.boton_acercade = new JButton();
      this.borderLayout3 = new BorderLayout();
      this.tabbedPane = new JTabbedPane();
      this.panel_prisma = new JPanel();
      this.borderLayout4 = new BorderLayout();
      this.panel_prisma_w = new JPanel();
      this.panel_prisma_c = new JPanel();
      this.panel_p_wn = new JPanel();
      this.borderLayout5 = new BorderLayout();
      this.panel_p_wc = new JPanel();
      this.borderLayout6 = new BorderLayout();
      this.panel_p_cn = new JPanel();
      this.panel_p_cc = new JPanel();
      this.borderLayout7 = new BorderLayout();
      this.panel_p_wnn = new JPanel();
      this.panel_p_wnw = new JPanel();
      this.panel_p_wns = new JPanel();
      this.panel_p_wne = new JPanel();
      this.panel_p_wnc = new JPanel();
      this.flowLayout2 = new FlowLayout();
      this.label_p_angp = new JLabel();
      this.slider_p_angp = new JSlider();
      this.label_p_nangp = new JLabel();
      this.label_p_angin = new JLabel();
      this.slider_p_angin = new JSlider();
      this.label_p_nangin = new JLabel();
      this.label_p_angdesvmin = new JLabel();
      this.label_p_indc = new JLabel();
      this.label_p_indc_n0 = new JLabel();
      this.slider_p_indc_n0 = new JSlider(0, 1300, 1750, 1500);
      this.label_p_indc_vn0 = new JLabel();
      this.label_p_indc_A = new JLabel();
      this.slider_p_indc_A = new JSlider(0, 0, 2000, 1000);
      this.label_p_indc_vA = new JLabel();
      this.label_p_indc_B = new JLabel();
      this.slider_p_indc_B = new JSlider(0, 0, 20000, 10000);
      this.label_p_indc_vB = new JLabel();
      this.label_p_indc_nd = new JLabel();
      this.label_p_indc_nF = new JLabel();
      this.label_p_indc_nC = new JLabel();
      this.label_p_abbe = new JLabel();
      this.label_p_tipovidrio = new JLabel();
      this.borderLayout8 = new BorderLayout();
      this.panel_p_ccn = new JPanel();
      this.panel_p_ccc = new JPanel();
      this.panel_p_ccs = new JPanel();
      this.label_p_compespec = new JLabel();
      this.flowLayout3 = new FlowLayout();
      this.flowLayout4 = new FlowLayout();
      this.label_p_longondas = new JLabel[7];
      this.slider_p_longondas = new JSlider[7];
      this.label_p_vlongondas = new JLabel[7];
      this.label_p_angdesvs = new JLabel[7];
      this.label_p_longonda = new JLabel();
      this.label_p_angdesv = new JLabel();
      this.dibujoprisma = new DibujoPrisma();
      this.borderLayout9 = new BorderLayout();
      this.panel_p_wncn = new JPanel();
      this.panel_p_wncc = new JPanel();
      this.borderLayout10 = new BorderLayout();
      this.dibujolineasprisma = new DibujoLineasPrisma();
      this.borderLayout11 = new BorderLayout();
      this.label_p_nada = new JLabel();
      this.toggleButton_p_zoom = new JToggleButton();
      this.tabbedPane_p_graficos = new JTabbedPane();
      this.borderLayout12 = new BorderLayout();
      this.panel_p_indice = new JPanel();
      this.panel_p_delta = new JPanel();
      this.borderLayout13 = new BorderLayout();
      this.panel_p_indice_w = new JPanel();
      this.panel_p_indice_n = new JPanel();
      this.panel_p_indice_s = new JPanel();
      this.panel_p_indice_e = new JPanel();
      this.panel_p_indice_c = new JPanel();
      this.borderLayout14 = new BorderLayout();
      this.indiceprisma = new IndicePrisma();
      this.borderLayout15 = new BorderLayout();
      this.panel_p_delta_n = new JPanel();
      this.panel_p_delta_w = new JPanel();
      this.panel_p_delta_e = new JPanel();
      this.panel_p_delta_s = new JPanel();
      this.panel_p_delta_c = new JPanel();
      this.borderLayout16 = new BorderLayout();
      this.graficoangulodesviacion = new GraficoAnguloDesviacion();
      this.panel_rainbow = new JPanel();
      this.borderLayout17 = new BorderLayout();
      this.panel_rainbow_w = new JPanel();
      this.panel_rainbow_c = new JPanel();
      this.borderLayout18 = new BorderLayout();
      this.panel_r_wn = new JPanel();
      this.borderLayout19 = new BorderLayout();
      this.panel_r_wc = new JPanel();
      this.panel_r_wnn = new JPanel();
      this.panel_r_wnw = new JPanel();
      this.panel_r_wns = new JPanel();
      this.panel_r_wne = new JPanel();
      this.panel_r_wnc = new JPanel();
      this.borderLayout20 = new BorderLayout();
      this.flowLayout5 = new FlowLayout();
      this.label_r_paramimp = new JLabel();
      this.slider_r_paramimp = new JSlider();
      this.label_r_nparamimp = new JLabel();
      this.label_r_longonda = new JLabel();
      this.slider_r_longonda = new JSlider();
      this.label_r_nlongonda = new JLabel();
      this.dibujogota = new DibujoGota();
      this.borderLayout21 = new BorderLayout();
      this.panel_r_cn = new JPanel();
      this.panel_r_cc = new JPanel();
      this.borderLayout22 = new BorderLayout();
      this.panel_r_cnn = new JPanel();
      this.panel_r_cnw = new JPanel();
      this.panel_r_cne = new JPanel();
      this.panel_r_cns = new JPanel();
      this.panel_r_cnc = new JPanel();
      this.dibujoarco = new DibujoArcoIris();
      this.borderLayout23 = new BorderLayout();
      this.borderLayout24 = new BorderLayout();
      this.panel_r_ccn = new JPanel();
      this.panel_r_ccs = new JPanel();
      this.panel_r_ccw = new JPanel();
      this.panel_r_cce = new JPanel();
      this.panel_r_ccc = new JPanel();
      this.graficodesvarcoiris = new GraficoDesviacionArcoIris();
      this.borderLayout25 = new BorderLayout();
      this.label_r_angdesv1 = new JLabel();
      this.label_r_angdesv1_val = new JLabel();
      this.label_r_angdesv2 = new JLabel();
      this.label_r_angdesv2_val = new JLabel();
      this.label_r_parimpmin1 = new JLabel();
      this.label_r_parimpmin1_val = new JLabel();
      this.label_r_parimpmin2 = new JLabel();
      this.label_r_parimpmin2_val = new JLabel();
      this.label_r_angdesvmin1 = new JLabel();
      this.label_r_angdesvmin1_val = new JLabel();
      this.label_r_angdesvmin2 = new JLabel();
      this.label_r_angdesvmin2_val = new JLabel();
      this.label_r_angin = new JLabel();
      this.label_r_angin_val = new JLabel();
      this.label_r_angout = new JLabel();
      this.label_r_angout_val = new JLabel();
      this.jPanel_info = new JPanel();
      this.borderLayout26 = new BorderLayout();
      this.jScrollPane_info = new JScrollPane();
      this.jTextPane_info = new JTextPane();

      try {
         String st_icon = "jocon.jpg";
         URL url_icon = this.getClass().getResource(st_icon);
         icon_joc = new ImageIcon(url_icon, "Difracción");
      } catch (Exception var3) {
         System.out.println("No carga icono");
      }

   }

   public void init() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      this.actua_parametros_prisma();
      this.actua_parametros_rainbow();
      this.carga_info(lang);
   }

   private void jbInit() throws Exception {
      String[] lista_vidrios = new String[]{this.escoger[lang], this.silice[lang], "BK 7", "BaK 1", "F2", "LaK 10"};
      this.comboBox_p_material = new JComboBox(lista_vidrios);
      String[] lista_espectros = new String[]{this.escoger[lang], this.tipo_luz[lang], "Hg", "Cd", "Zn"};
      this.comboBox_p_espec = new JComboBox(lista_espectros);

      for(int i = 0; i < 7; ++i) {
         this.label_p_longondas[i] = new JLabel();
         this.slider_p_longondas[i] = new JSlider();
         this.label_p_vlongondas[i] = new JLabel();
         this.label_p_angdesvs[i] = new JLabel();
      }

      this.setLayout(this.borderLayout1);
      this.panel_Base.setLayout(this.borderLayout2);
      this.panel_Norte.setPreferredSize(new Dimension(10, 5));
      this.panel_Sur.setPreferredSize(new Dimension(750, 25));
      this.panel_Sur.setLayout(this.flowLayout1);
      this.boton_salir.setPreferredSize(new Dimension(110, 20));
      this.boton_salir.setText(this.etiqueta_boton_salir[lang]);
      this.boton_salir.addActionListener(new DispersionApplet_boton_salir_actionAdapter(this));
      this.flowLayout1.setAlignment(2);
      this.boton_acercade.setPreferredSize(new Dimension(110, 20));
      this.boton_acercade.setText(this.etiqueta_acerca[0][lang]);
      this.boton_acercade.addActionListener(new DispersionApplet_boton_acercade_actionAdapter(this));
      this.panel_Centro.setLayout(this.borderLayout3);
      this.panel_prisma.setLayout(this.borderLayout4);
      this.panel_prisma_w.setPreferredSize(new Dimension(375, 550));
      this.panel_prisma_w.setLayout(this.borderLayout5);
      this.panel_p_wn.setPreferredSize(new Dimension(375, 256));
      this.panel_p_wn.setLayout(this.borderLayout7);
      this.panel_prisma_c.setLayout(this.borderLayout6);
      this.panel_p_cn.setPreferredSize(new Dimension(375, 256));
      this.panel_p_cn.setLayout(this.borderLayout12);
      this.panel_p_wnn.setPreferredSize(new Dimension(375, 4));
      this.panel_p_wnw.setPreferredSize(new Dimension(5, 248));
      this.panel_p_wns.setPreferredSize(new Dimension(375, 4));
      this.panel_p_wne.setPreferredSize(new Dimension(5, 248));
      this.panel_p_wnc.setBackground(Color.black);
      this.panel_p_wnc.setLayout(this.borderLayout9);
      this.panel_p_wc.setLayout(this.flowLayout2);
      this.label_p_angp.setForeground(new Color(102, 102, 153));
      this.label_p_angp.setMaximumSize(new Dimension(120, 16));
      this.label_p_angp.setMinimumSize(new Dimension(120, 16));
      this.label_p_angp.setPreferredSize(new Dimension(120, 16));
      this.label_p_angp.setToolTipText("");
      this.label_p_angp.setText(this.etiquetas_prisma[1][lang]);
      this.slider_p_angp.setMaximum(80);
      this.slider_p_angp.setMinimum(20);
      this.slider_p_angp.setPreferredSize(new Dimension(150, 16));
      this.slider_p_angp.addMouseMotionListener(new DispersionApplet_slider_p_angp_mouseMotionAdapter(this));
      this.slider_p_angp.addMouseListener(new DispersionApplet_slider_p_angp_mouseAdapter(this));
      this.slider_p_angp.addKeyListener(new DispersionApplet_slider_p_angp_keyAdapter(this));
      this.label_p_nangp.setForeground(new Color(102, 102, 153));
      this.label_p_nangp.setMinimumSize(new Dimension(50, 16));
      this.label_p_nangp.setPreferredSize(new Dimension(50, 16));
      this.label_p_nangp.setText("xxº");
      this.label_p_angin.setForeground(new Color(102, 102, 153));
      this.label_p_angin.setText(this.etiquetas_prisma[0][lang]);
      this.slider_p_angin.setMaximum(89);
      this.slider_p_angin.setPaintTrack(true);
      this.slider_p_angin.setPreferredSize(new Dimension(150, 16));
      this.slider_p_angin.addMouseMotionListener(new DispersionApplet_slider_p_angin_mouseMotionAdapter(this));
      this.slider_p_angin.addMouseListener(new DispersionApplet_slider_p_angin_mouseAdapter(this));
      this.slider_p_angin.addKeyListener(new DispersionApplet_slider_p_angin_keyAdapter(this));
      this.label_p_nangin.setForeground(new Color(102, 102, 153));
      this.label_p_nangin.setMinimumSize(new Dimension(50, 16));
      this.label_p_nangin.setPreferredSize(new Dimension(50, 16));
      this.label_p_nangin.setText("xxº");
      this.label_p_angdesvmin.setForeground(new Color(102, 102, 153));
      this.label_p_angdesvmin.setPreferredSize(new Dimension(350, 16));
      this.label_p_angdesvmin.setHorizontalAlignment(0);
      this.label_p_angdesvmin.setText(this.etiquetas_prisma[2][lang] + ": xxº");
      this.label_p_indc.setForeground(new Color(102, 102, 153));
      this.label_p_indc.setPreferredSize(new Dimension(150, 16));
      this.label_p_indc.setText(this.etiquetas_prisma[3][lang] + ":");
      this.comboBox_p_material.setMinimumSize(new Dimension(108, 20));
      this.comboBox_p_material.setPreferredSize(new Dimension(150, 20));
      this.comboBox_p_material.addActionListener(new DispersionApplet_comboBox_p_material_actionAdapter(this));
      this.label_p_indc_n0.setForeground(new Color(102, 102, 153));
      this.label_p_indc_n0.setMinimumSize(new Dimension(11, 16));
      this.label_p_indc_n0.setPreferredSize(new Dimension(70, 16));
      this.label_p_indc_n0.setText("n0:");
      this.slider_p_indc_n0.setMaximum(1750);
      this.slider_p_indc_n0.setMinimum(1300);
      this.slider_p_indc_n0.setPreferredSize(new Dimension(150, 16));
      this.slider_p_indc_n0.addKeyListener(new DispersionApplet_slider_p_indc_n0_keyAdapter(this));
      this.slider_p_indc_n0.addMouseMotionListener(new DispersionApplet_slider_p_indc_n0_mouseMotionAdapter(this));
      this.slider_p_indc_n0.addMouseListener(new DispersionApplet_slider_p_indc_n0_mouseAdapter(this));
      this.label_p_indc_vn0.setForeground(new Color(102, 102, 153));
      this.label_p_indc_vn0.setPreferredSize(new Dimension(80, 16));
      this.label_p_indc_vn0.setText("x.xx");
      this.label_p_indc_A.setForeground(new Color(102, 102, 153));
      this.label_p_indc_A.setPreferredSize(new Dimension(70, 16));
      this.label_p_indc_A.setText("A:");
      this.slider_p_indc_A.setMaximum(2000);
      this.slider_p_indc_A.setPreferredSize(new Dimension(150, 16));
      this.slider_p_indc_A.addMouseMotionListener(new DispersionApplet_slider_p_indc_A_mouseMotionAdapter(this));
      this.slider_p_indc_A.addMouseListener(new DispersionApplet_slider_p_indc_A_mouseAdapter(this));
      this.slider_p_indc_A.addKeyListener(new DispersionApplet_slider_p_indc_A_keyAdapter(this));
      this.label_p_indc_vA.setForeground(new Color(102, 102, 153));
      this.label_p_indc_vA.setPreferredSize(new Dimension(80, 16));
      this.label_p_indc_vA.setText("x.xxx");
      this.label_p_indc_B.setForeground(new Color(102, 102, 153));
      this.label_p_indc_B.setPreferredSize(new Dimension(70, 16));
      this.label_p_indc_B.setText("B:");
      this.slider_p_indc_B.setMaximum(20000);
      this.slider_p_indc_B.setPreferredSize(new Dimension(150, 16));
      this.slider_p_indc_B.addMouseMotionListener(new DispersionApplet_slider_p_indc_B_mouseMotionAdapter(this));
      this.slider_p_indc_B.addMouseListener(new DispersionApplet_slider_p_indc_B_mouseAdapter(this));
      this.slider_p_indc_B.addKeyListener(new DispersionApplet_slider_p_indc_B_keyAdapter(this));
      this.label_p_indc_vB.setForeground(new Color(102, 102, 153));
      this.label_p_indc_vB.setPreferredSize(new Dimension(80, 16));
      this.label_p_indc_vB.setText("x.xxx");
      this.label_p_indc_nd.setForeground(new Color(102, 102, 153));
      this.label_p_indc_nd.setPreferredSize(new Dimension(100, 16));
      this.label_p_indc_nd.setText("nd = x.xxx");
      this.label_p_indc_nF.setForeground(new Color(102, 102, 153));
      this.label_p_indc_nF.setPreferredSize(new Dimension(100, 16));
      this.label_p_indc_nF.setText("nF = x.xxx");
      this.label_p_indc_nC.setForeground(new Color(102, 102, 153));
      this.label_p_indc_nC.setPreferredSize(new Dimension(100, 16));
      this.label_p_indc_nC.setText("nC = x.xxx");
      this.label_p_abbe.setForeground(new Color(102, 102, 153));
      this.label_p_abbe.setPreferredSize(new Dimension(100, 16));
      this.label_p_abbe.setText("nº Abbe = xx.x");
      this.label_p_tipovidrio.setForeground(new Color(102, 102, 153));
      this.label_p_tipovidrio.setPreferredSize(new Dimension(150, 16));
      this.label_p_tipovidrio.setText(this.etiquetas_prisma[4][lang] + ": flint");
      this.panel_p_cc.setLayout(this.borderLayout8);
      this.panel_p_ccn.setPreferredSize(new Dimension(375, 30));
      this.panel_p_ccn.setLayout(this.flowLayout3);
      this.panel_p_ccs.setPreferredSize(new Dimension(375, 10));
      this.label_p_compespec.setForeground(new Color(102, 102, 153));
      this.label_p_compespec.setPreferredSize(new Dimension(150, 16));
      this.label_p_compespec.setText(this.etiquetas_prisma[5][lang]);
      this.comboBox_p_espec.setMinimumSize(new Dimension(108, 20));
      this.comboBox_p_espec.setPreferredSize(new Dimension(150, 20));
      this.comboBox_p_espec.addActionListener(new DispersionApplet_comboBox_p_espec_actionAdapter(this));
      this.panel_p_ccc.setLayout(this.flowLayout4);
      this.label_p_longondas[0].setForeground(new Color(102, 102, 153));
      this.label_p_longondas[0].setPreferredSize(new Dimension(25, 16));
      this.label_p_longondas[0].setText("1:");
      this.slider_p_longondas[0].setMaximum(700);
      this.slider_p_longondas[0].setMinimum(400);
      this.slider_p_longondas[0].setPreferredSize(new Dimension(150, 16));
      this.slider_p_longondas[0].addMouseMotionListener(new DispersionApplet_slider_p_longonda1_mouseMotionAdapter(this));
      this.slider_p_longondas[0].addMouseListener(new DispersionApplet_slider_p_longonda1_mouseAdapter(this));
      this.slider_p_longondas[0].addKeyListener(new DispersionApplet_slider_p_longonda1_keyAdapter(this));
      this.label_p_vlongondas[0].setForeground(new Color(102, 102, 153));
      this.label_p_vlongondas[0].setPreferredSize(new Dimension(75, 16));
      this.label_p_vlongondas[0].setText("xxx.x nm");
      this.label_p_angdesvs[0].setForeground(new Color(102, 102, 153));
      this.label_p_angdesvs[0].setPreferredSize(new Dimension(75, 16));
      this.label_p_angdesvs[0].setHorizontalAlignment(4);
      this.label_p_angdesvs[0].setText("xx.xº");
      this.label_p_longonda.setForeground(new Color(102, 102, 153));
      this.label_p_longonda.setPreferredSize(new Dimension(175, 16));
      this.label_p_longonda.setText(this.etiquetas_prisma[6][lang]);
      this.label_p_angdesv.setForeground(new Color(102, 102, 153));
      this.label_p_angdesv.setPreferredSize(new Dimension(175, 16));
      this.label_p_angdesv.setHorizontalAlignment(4);
      this.label_p_angdesv.setText(this.etiquetas_prisma[7][lang]);
      this.label_p_longondas[1].setForeground(new Color(102, 102, 153));
      this.label_p_longondas[1].setPreferredSize(new Dimension(25, 16));
      this.label_p_longondas[1].setText("2:");
      this.slider_p_longondas[1].setMaximum(700);
      this.slider_p_longondas[1].setMinimum(400);
      this.slider_p_longondas[1].setPreferredSize(new Dimension(150, 16));
      this.slider_p_longondas[1].addMouseMotionListener(new DispersionApplet_slider_p_longonda2_mouseMotionAdapter(this));
      this.slider_p_longondas[1].addMouseListener(new DispersionApplet_slider_p_longonda2_mouseAdapter(this));
      this.slider_p_longondas[1].addKeyListener(new DispersionApplet_slider_p_longonda2_keyAdapter(this));
      this.label_p_vlongondas[1].setForeground(new Color(102, 102, 153));
      this.label_p_vlongondas[1].setPreferredSize(new Dimension(75, 16));
      this.label_p_vlongondas[1].setText("xxx.x nm");
      this.label_p_angdesvs[1].setForeground(new Color(102, 102, 153));
      this.label_p_angdesvs[1].setPreferredSize(new Dimension(75, 16));
      this.label_p_angdesvs[1].setHorizontalAlignment(4);
      this.label_p_angdesvs[1].setText("xx.xº");
      this.label_p_longondas[2].setForeground(new Color(102, 102, 153));
      this.label_p_longondas[2].setPreferredSize(new Dimension(25, 16));
      this.label_p_longondas[2].setText("3:");
      this.slider_p_longondas[2].setMaximum(700);
      this.slider_p_longondas[2].setMinimum(400);
      this.slider_p_longondas[2].setPreferredSize(new Dimension(150, 16));
      this.slider_p_longondas[2].addMouseMotionListener(new DispersionApplet_slider_p_longonda3_mouseMotionAdapter(this));
      this.slider_p_longondas[2].addMouseListener(new DispersionApplet_slider_p_longonda3_mouseAdapter(this));
      this.slider_p_longondas[2].addKeyListener(new DispersionApplet_slider_p_longonda3_keyAdapter(this));
      this.label_p_vlongondas[2].setForeground(new Color(102, 102, 153));
      this.label_p_vlongondas[2].setPreferredSize(new Dimension(75, 16));
      this.label_p_vlongondas[2].setText("xxx.x nm");
      this.label_p_angdesvs[2].setForeground(new Color(102, 102, 153));
      this.label_p_angdesvs[2].setPreferredSize(new Dimension(75, 16));
      this.label_p_angdesvs[2].setHorizontalAlignment(4);
      this.label_p_angdesvs[2].setText("xx.xº");
      this.label_p_longondas[3].setForeground(new Color(102, 102, 153));
      this.label_p_longondas[3].setPreferredSize(new Dimension(25, 16));
      this.label_p_longondas[3].setText("4:");
      this.slider_p_longondas[3].setMaximum(700);
      this.slider_p_longondas[3].setMinimum(400);
      this.slider_p_longondas[3].setPreferredSize(new Dimension(150, 16));
      this.slider_p_longondas[3].addMouseMotionListener(new DispersionApplet_slider_p_longonda4_mouseMotionAdapter(this));
      this.slider_p_longondas[3].addMouseListener(new DispersionApplet_slider_p_longonda4_mouseAdapter(this));
      this.slider_p_longondas[3].addKeyListener(new DispersionApplet_slider_p_longonda4_keyAdapter(this));
      this.label_p_vlongondas[3].setForeground(new Color(102, 102, 153));
      this.label_p_vlongondas[3].setPreferredSize(new Dimension(75, 16));
      this.label_p_vlongondas[3].setText("xxx.x nm");
      this.label_p_angdesvs[3].setForeground(new Color(102, 102, 153));
      this.label_p_angdesvs[3].setPreferredSize(new Dimension(75, 16));
      this.label_p_angdesvs[3].setHorizontalAlignment(4);
      this.label_p_angdesvs[3].setText("xx.xº");
      this.label_p_longondas[4].setForeground(new Color(102, 102, 153));
      this.label_p_longondas[4].setPreferredSize(new Dimension(25, 16));
      this.label_p_longondas[4].setText("5:");
      this.slider_p_longondas[4].setMaximum(700);
      this.slider_p_longondas[4].setMinimum(400);
      this.slider_p_longondas[4].setPreferredSize(new Dimension(150, 16));
      this.slider_p_longondas[4].addMouseMotionListener(new DispersionApplet_slider_p_longonda5_mouseMotionAdapter(this));
      this.slider_p_longondas[4].addMouseListener(new DispersionApplet_slider_p_longonda5_mouseAdapter(this));
      this.slider_p_longondas[4].addKeyListener(new DispersionApplet_slider_p_longonda5_keyAdapter(this));
      this.label_p_vlongondas[4].setForeground(new Color(102, 102, 153));
      this.label_p_vlongondas[4].setPreferredSize(new Dimension(75, 16));
      this.label_p_vlongondas[4].setText("xxx.x nm");
      this.label_p_angdesvs[4].setForeground(new Color(102, 102, 153));
      this.label_p_angdesvs[4].setPreferredSize(new Dimension(75, 16));
      this.label_p_angdesvs[4].setHorizontalAlignment(4);
      this.label_p_angdesvs[4].setText("xx.xº");
      this.label_p_longondas[5].setForeground(new Color(102, 102, 153));
      this.label_p_longondas[5].setPreferredSize(new Dimension(25, 16));
      this.label_p_longondas[5].setText("6:");
      this.slider_p_longondas[5].setMaximum(700);
      this.slider_p_longondas[5].setMinimum(400);
      this.slider_p_longondas[5].setPreferredSize(new Dimension(150, 16));
      this.slider_p_longondas[5].addMouseMotionListener(new DispersionApplet_slider_p_longonda6_mouseMotionAdapter(this));
      this.slider_p_longondas[5].addMouseListener(new DispersionApplet_slider_p_longonda6_mouseAdapter(this));
      this.slider_p_longondas[5].addKeyListener(new DispersionApplet_slider_p_longonda6_keyAdapter(this));
      this.label_p_vlongondas[5].setForeground(new Color(102, 102, 153));
      this.label_p_vlongondas[5].setPreferredSize(new Dimension(75, 16));
      this.label_p_vlongondas[5].setRequestFocusEnabled(true);
      this.label_p_vlongondas[5].setText("xxx.x nm");
      this.label_p_angdesvs[5].setForeground(new Color(102, 102, 153));
      this.label_p_angdesvs[5].setPreferredSize(new Dimension(75, 16));
      this.label_p_angdesvs[5].setHorizontalAlignment(4);
      this.label_p_angdesvs[5].setText("xx.xº");
      this.label_p_longondas[6].setForeground(new Color(102, 102, 153));
      this.label_p_longondas[6].setPreferredSize(new Dimension(25, 16));
      this.label_p_longondas[6].setText("7:");
      this.slider_p_longondas[6].setMaximum(700);
      this.slider_p_longondas[6].setMinimum(400);
      this.slider_p_longondas[6].setPreferredSize(new Dimension(150, 16));
      this.slider_p_longondas[6].addMouseMotionListener(new DispersionApplet_slider_p_longonda7_mouseMotionAdapter(this));
      this.slider_p_longondas[6].addMouseListener(new DispersionApplet_slider_p_longonda7_mouseAdapter(this));
      this.slider_p_longondas[6].addKeyListener(new DispersionApplet_slider_p_longonda7_keyAdapter(this));
      this.label_p_vlongondas[6].setForeground(new Color(102, 102, 153));
      this.label_p_vlongondas[6].setPreferredSize(new Dimension(75, 16));
      this.label_p_vlongondas[6].setHorizontalAlignment(10);
      this.label_p_vlongondas[6].setText("xxx.x nm");
      this.label_p_angdesvs[6].setForeground(new Color(102, 102, 153));
      this.label_p_angdesvs[6].setPreferredSize(new Dimension(75, 16));
      this.label_p_angdesvs[6].setHorizontalAlignment(4);
      this.label_p_angdesvs[6].setText("xx.xº");
      this.panel_p_wncn.setBackground(Color.black);
      this.panel_p_wncn.setPreferredSize(new Dimension(355, 200));
      this.panel_p_wncn.setLayout(this.borderLayout10);
      this.panel_p_wncc.setBackground(Color.darkGray);
      this.panel_p_wncc.setLayout(this.borderLayout11);
      this.dibujoprisma.setPreferredSize(new Dimension(355, 200));
      this.label_p_nada.setMinimumSize(new Dimension(250, 16));
      this.label_p_nada.setPreferredSize(new Dimension(240, 16));
      this.label_p_nada.setText(" ");
      this.toggleButton_p_zoom.setForeground(new Color(102, 102, 153));
      this.toggleButton_p_zoom.setMinimumSize(new Dimension(66, 20));
      this.toggleButton_p_zoom.setPreferredSize(new Dimension(66, 20));
      this.toggleButton_p_zoom.setText("Zoom");
      this.toggleButton_p_zoom.addActionListener(new DispersionApplet_toggleButton_p_zoom_actionAdapter(this));
      this.panel_p_indice.setLayout(this.borderLayout13);
      this.panel_p_indice_w.setMinimumSize(new Dimension(5, 10));
      this.panel_p_indice_w.setPreferredSize(new Dimension(5, 10));
      this.panel_p_indice_n.setMinimumSize(new Dimension(10, 5));
      this.panel_p_indice_n.setPreferredSize(new Dimension(10, 5));
      this.panel_p_indice_e.setMinimumSize(new Dimension(5, 10));
      this.panel_p_indice_e.setPreferredSize(new Dimension(5, 10));
      this.panel_p_indice_s.setMinimumSize(new Dimension(10, 5));
      this.panel_p_indice_s.setPreferredSize(new Dimension(10, 5));
      this.panel_p_indice_c.setLayout(this.borderLayout14);
      this.panel_p_indice_c.setBackground(Color.white);
      this.panel_p_indice.setBackground(Color.black);
      this.panel_p_delta.setLayout(this.borderLayout15);
      this.panel_p_delta_n.setPreferredSize(new Dimension(10, 5));
      this.panel_p_delta_w.setPreferredSize(new Dimension(5, 10));
      this.panel_p_delta_e.setPreferredSize(new Dimension(5, 10));
      this.panel_p_delta_s.setPreferredSize(new Dimension(10, 5));
      this.panel_p_delta.setBackground(Color.black);
      this.panel_p_delta_c.setLayout(this.borderLayout16);
      this.panel_rainbow.setLayout(this.borderLayout17);
      this.panel_rainbow_w.setPreferredSize(new Dimension(375, 550));
      this.panel_rainbow_w.setLayout(this.borderLayout18);
      this.panel_rainbow_c.setPreferredSize(new Dimension(375, 337));
      this.panel_rainbow_c.setLayout(this.borderLayout20);
      this.panel_r_wn.setLayout(this.borderLayout19);
      this.panel_r_wn.setBackground(Color.black);
      this.panel_r_wn.setPreferredSize(new Dimension(375, 256));
      this.panel_r_wnn.setPreferredSize(new Dimension(375, 4));
      this.panel_r_wnw.setPreferredSize(new Dimension(5, 248));
      this.panel_r_wns.setPreferredSize(new Dimension(375, 4));
      this.panel_r_wne.setPreferredSize(new Dimension(5, 248));
      this.panel_r_wnc.setBackground(Color.black);
      this.panel_r_wnc.setPreferredSize(new Dimension(365, 248));
      this.panel_r_wnc.setLayout(this.borderLayout21);
      this.panel_r_wc.setLayout(this.flowLayout5);
      this.label_r_paramimp.setForeground(new Color(102, 102, 153));
      this.label_r_paramimp.setMinimumSize(new Dimension(120, 16));
      this.label_r_paramimp.setPreferredSize(new Dimension(150, 16));
      this.label_r_paramimp.setText(this.etiquetas_arcoiris[1][lang] + ":");
      this.slider_r_paramimp.setMinimum(1);
      this.slider_r_paramimp.setMinimumSize(new Dimension(36, 16));
      this.slider_r_paramimp.setPreferredSize(new Dimension(150, 16));
      this.slider_r_paramimp.addMouseMotionListener(new DispersionApplet_slider_r_paramimp_mouseMotionAdapter(this));
      this.slider_r_paramimp.addMouseListener(new DispersionApplet_slider_r_paramimp_mouseAdapter(this));
      this.slider_r_paramimp.addKeyListener(new DispersionApplet_slider_r_paramimp_keyAdapter(this));
      this.label_r_nparamimp.setForeground(new Color(102, 102, 153));
      this.label_r_nparamimp.setMinimumSize(new Dimension(50, 16));
      this.label_r_nparamimp.setPreferredSize(new Dimension(50, 16));
      this.label_r_nparamimp.setText("x.xx");
      this.label_r_longonda.setForeground(new Color(102, 102, 153));
      this.label_r_longonda.setMinimumSize(new Dimension(120, 16));
      this.label_r_longonda.setPreferredSize(new Dimension(150, 16));
      this.label_r_longonda.setText(this.etiquetas_arcoiris[2][lang] + ":");
      this.slider_r_longonda.setMaximum(700);
      this.slider_r_longonda.setMinimum(400);
      this.slider_r_longonda.setPreferredSize(new Dimension(150, 16));
      this.slider_r_longonda.addMouseMotionListener(new DispersionApplet_slider_r_longonda_mouseMotionAdapter(this));
      this.slider_r_longonda.addMouseListener(new DispersionApplet_slider_r_longonda_mouseAdapter(this));
      this.slider_r_longonda.addKeyListener(new DispersionApplet_slider_r_longonda_keyAdapter(this));
      this.label_r_nlongonda.setForeground(new Color(102, 102, 153));
      this.label_r_nlongonda.setMinimumSize(new Dimension(50, 16));
      this.label_r_nlongonda.setPreferredSize(new Dimension(50, 16));
      this.label_r_nlongonda.setText("xxx nm");
      this.panel_r_cn.setBackground(new Color(100, 180, 200));
      this.panel_r_cn.setPreferredSize(new Dimension(375, 256));
      this.panel_r_cn.setLayout(this.borderLayout22);
      this.panel_r_cnn.setPreferredSize(new Dimension(375, 4));
      this.panel_r_cnw.setPreferredSize(new Dimension(5, 248));
      this.panel_r_cne.setPreferredSize(new Dimension(5, 248));
      this.panel_r_cns.setPreferredSize(new Dimension(375, 4));
      this.panel_r_cnc.setBackground(new Color(100, 180, 200));
      this.panel_r_cnc.setPreferredSize(new Dimension(365, 248));
      this.panel_r_cnc.setLayout(this.borderLayout23);
      this.panel_r_cc.setLayout(this.borderLayout24);
      this.panel_r_ccn.setPreferredSize(new Dimension(375, 4));
      this.panel_r_ccs.setPreferredSize(new Dimension(375, 4));
      this.panel_r_ccw.setPreferredSize(new Dimension(5, 248));
      this.panel_r_cce.setPreferredSize(new Dimension(5, 248));
      this.panel_r_ccc.setPreferredSize(new Dimension(365, 248));
      this.panel_r_ccc.setLayout(this.borderLayout25);
      this.panel_r_cc.setBackground(Color.black);
      this.label_r_angdesv1.setForeground(new Color(102, 102, 153));
      this.label_r_angdesv1.setPreferredSize(new Dimension(275, 16));
      this.label_r_angdesv1.setText(this.etiquetas_arcoiris[3][lang]);
      this.label_r_angdesv1_val.setForeground(new Color(102, 102, 153));
      this.label_r_angdesv1_val.setMinimumSize(new Dimension(50, 16));
      this.label_r_angdesv1_val.setPreferredSize(new Dimension(50, 16));
      this.label_r_angdesv1_val.setHorizontalTextPosition(2);
      this.label_r_angdesv1_val.setText("xxº");
      this.label_r_angdesv2.setForeground(new Color(102, 102, 153));
      this.label_r_angdesv2.setPreferredSize(new Dimension(275, 16));
      this.label_r_angdesv2.setText(this.etiquetas_arcoiris[4][lang]);
      this.label_r_angdesv2_val.setForeground(new Color(102, 102, 153));
      this.label_r_angdesv2_val.setPreferredSize(new Dimension(50, 16));
      this.label_r_angdesv2_val.setHorizontalTextPosition(2);
      this.label_r_angdesv2_val.setText("xxº");
      this.label_r_parimpmin1.setForeground(new Color(102, 102, 153));
      this.label_r_parimpmin1.setPreferredSize(new Dimension(275, 16));
      this.label_r_parimpmin1.setText(this.etiquetas_arcoiris[5][lang]);
      this.label_r_parimpmin1_val.setForeground(new Color(102, 102, 153));
      this.label_r_parimpmin1_val.setOpaque(false);
      this.label_r_parimpmin1_val.setPreferredSize(new Dimension(50, 16));
      this.label_r_parimpmin1_val.setHorizontalAlignment(10);
      this.label_r_parimpmin1_val.setHorizontalTextPosition(2);
      this.label_r_parimpmin1_val.setText("xx");
      this.label_r_parimpmin2.setForeground(new Color(102, 102, 153));
      this.label_r_parimpmin2.setMinimumSize(new Dimension(275, 16));
      this.label_r_parimpmin2.setPreferredSize(new Dimension(275, 16));
      this.label_r_parimpmin2.setText(this.etiquetas_arcoiris[6][lang]);
      this.label_r_parimpmin2_val.setForeground(new Color(102, 102, 153));
      this.label_r_parimpmin2_val.setPreferredSize(new Dimension(50, 16));
      this.label_r_parimpmin2_val.setHorizontalTextPosition(2);
      this.label_r_parimpmin2_val.setText("xx");
      this.label_r_angdesvmin1.setForeground(new Color(102, 102, 153));
      this.label_r_angdesvmin1.setPreferredSize(new Dimension(275, 16));
      this.label_r_angdesvmin1.setText(this.etiquetas_arcoiris[7][lang]);
      this.label_r_angdesvmin1_val.setForeground(new Color(102, 102, 153));
      this.label_r_angdesvmin1_val.setPreferredSize(new Dimension(50, 16));
      this.label_r_angdesvmin1_val.setText("xxº");
      this.label_r_angdesvmin2.setForeground(new Color(102, 102, 153));
      this.label_r_angdesvmin2.setPreferredSize(new Dimension(275, 16));
      this.label_r_angdesvmin2.setText(this.etiquetas_arcoiris[8][lang]);
      this.label_r_angdesvmin2_val.setForeground(new Color(102, 102, 153));
      this.label_r_angdesvmin2_val.setPreferredSize(new Dimension(50, 16));
      this.label_r_angdesvmin2_val.setText("xxº");
      this.label_r_angin.setForeground(new Color(102, 102, 153));
      this.label_r_angin.setPreferredSize(new Dimension(275, 16));
      this.label_r_angin.setText(this.etiquetas_arcoiris[9][lang]);
      this.label_r_angin_val.setForeground(new Color(102, 102, 153));
      this.label_r_angin_val.setPreferredSize(new Dimension(50, 16));
      this.label_r_angin_val.setRequestFocusEnabled(true);
      this.label_r_angin_val.setText("xxº");
      this.label_r_angout.setForeground(new Color(102, 102, 153));
      this.label_r_angout.setPreferredSize(new Dimension(275, 16));
      this.label_r_angout.setText(this.etiquetas_arcoiris[10][lang]);
      this.label_r_angout_val.setForeground(new Color(102, 102, 153));
      this.label_r_angout_val.setPreferredSize(new Dimension(50, 16));
      this.label_r_angout_val.setText("xxº");
      this.jPanel_info.setLayout(this.borderLayout26);
      this.jTextPane_info.setBackground(new Color(204, 204, 204));
      this.jTextPane_info.setText(this.info_etiqueta[lang]);
      this.panel_p_ccc.add(this.label_p_longonda, (Object)null);
      this.panel_p_ccc.add(this.label_p_angdesv, (Object)null);
      this.panel_Sur.add(this.boton_acercade, (Object)null);
      this.add(this.panel_Base, "Center");
      this.panel_Base.add(this.panel_Norte, "North");
      this.panel_Base.add(this.panel_Centro, "Center");
      this.panel_Centro.add(this.tabbedPane, "Center");
      this.panel_Base.add(this.panel_Sur, "South");
      this.panel_Sur.add(this.boton_salir, (Object)null);
      this.tabbedPane.add(this.panel_prisma, this.etiquetas_prisma[8][lang]);
      this.panel_prisma.add(this.panel_prisma_w, "West");
      this.panel_prisma.add(this.panel_prisma_c, "Center");
      this.panel_prisma_w.add(this.panel_p_wn, "North");
      this.panel_p_wn.add(this.panel_p_wnn, "North");
      this.panel_prisma_w.add(this.panel_p_wc, "Center");
      this.panel_p_wc.add(this.label_p_nada, (Object)null);
      this.panel_p_wc.add(this.toggleButton_p_zoom, (Object)null);
      this.panel_p_wc.add(this.label_p_angp, (Object)null);
      this.panel_prisma_c.add(this.panel_p_cn, "North");
      this.panel_p_cn.add(this.tabbedPane_p_graficos, "Center");
      this.tabbedPane_p_graficos.add(this.panel_p_indice, this.etiquetas_prisma[3][lang]);
      this.panel_p_indice.add(this.panel_p_indice_w, "West");
      this.tabbedPane_p_graficos.add(this.panel_p_delta, this.etiquetas_prisma[9][lang]);
      this.panel_p_delta.add(this.panel_p_delta_n, "North");
      this.panel_prisma_c.add(this.panel_p_cc, "Center");
      this.panel_p_cc.add(this.panel_p_ccn, "North");
      this.panel_p_wn.add(this.panel_p_wnw, "West");
      this.panel_p_wn.add(this.panel_p_wns, "South");
      this.panel_p_wn.add(this.panel_p_wne, "East");
      this.panel_p_wn.add(this.panel_p_wnc, "Center");
      this.panel_p_wnc.add(this.panel_p_wncn, "North");
      this.panel_p_wc.add(this.slider_p_angp, (Object)null);
      this.panel_p_wc.add(this.label_p_nangp, (Object)null);
      this.panel_p_wc.add(this.label_p_angin, (Object)null);
      this.panel_p_wc.add(this.slider_p_angin, (Object)null);
      this.panel_p_wc.add(this.label_p_nangin, (Object)null);
      this.panel_p_wc.add(this.label_p_angdesvmin, (Object)null);
      this.panel_p_wc.add(this.label_p_indc, (Object)null);
      this.panel_p_wc.add(this.comboBox_p_material, (Object)null);
      this.panel_p_wc.add(this.label_p_indc_n0, (Object)null);
      this.panel_p_wc.add(this.slider_p_indc_n0, (Object)null);
      this.panel_p_wc.add(this.label_p_indc_vn0, (Object)null);
      this.panel_p_wc.add(this.label_p_indc_A, (Object)null);
      this.panel_p_wc.add(this.slider_p_indc_A, (Object)null);
      this.panel_p_wc.add(this.label_p_indc_vA, (Object)null);
      this.panel_p_wc.add(this.label_p_indc_B, (Object)null);
      this.panel_p_wc.add(this.slider_p_indc_B, (Object)null);
      this.panel_p_wc.add(this.label_p_indc_vB, (Object)null);
      this.panel_p_wc.add(this.label_p_indc_nd, (Object)null);
      this.panel_p_wc.add(this.label_p_indc_nF, (Object)null);
      this.panel_p_wc.add(this.label_p_indc_nC, (Object)null);
      this.panel_p_wc.add(this.label_p_abbe, (Object)null);
      this.panel_p_wc.add(this.label_p_tipovidrio, (Object)null);
      this.panel_p_cc.add(this.panel_p_ccc, "Center");
      this.panel_p_cc.add(this.panel_p_ccs, "South");
      this.tabbedPane.add(this.panel_rainbow, this.etiquetas_arcoiris[0][lang]);
      this.panel_p_ccn.add(this.label_p_compespec, (Object)null);
      this.panel_p_ccn.add(this.comboBox_p_espec, (Object)null);
      this.panel_p_ccc.add(this.label_p_longondas[0], (Object)null);
      this.panel_p_ccc.add(this.slider_p_longondas[0], (Object)null);
      this.panel_p_ccc.add(this.label_p_vlongondas[0], (Object)null);
      this.panel_p_ccc.add(this.label_p_angdesvs[0], (Object)null);
      this.panel_p_ccc.add(this.label_p_longondas[1], (Object)null);
      this.panel_p_ccc.add(this.slider_p_longondas[1], (Object)null);
      this.panel_p_ccc.add(this.label_p_vlongondas[1], (Object)null);
      this.panel_p_ccc.add(this.label_p_angdesvs[1], (Object)null);
      this.panel_p_ccc.add(this.label_p_longondas[2], (Object)null);
      this.panel_p_ccc.add(this.slider_p_longondas[2], (Object)null);
      this.panel_p_ccc.add(this.label_p_vlongondas[2], (Object)null);
      this.panel_p_ccc.add(this.label_p_angdesvs[2], (Object)null);
      this.panel_p_ccc.add(this.label_p_longondas[3], (Object)null);
      this.panel_p_ccc.add(this.slider_p_longondas[3], (Object)null);
      this.panel_p_ccc.add(this.label_p_vlongondas[3], (Object)null);
      this.panel_p_ccc.add(this.label_p_angdesvs[3], (Object)null);
      this.panel_p_ccc.add(this.label_p_longondas[4], (Object)null);
      this.panel_p_ccc.add(this.slider_p_longondas[4], (Object)null);
      this.panel_p_ccc.add(this.label_p_vlongondas[4], (Object)null);
      this.panel_p_ccc.add(this.label_p_angdesvs[4], (Object)null);
      this.panel_p_ccc.add(this.label_p_longondas[5], (Object)null);
      this.panel_p_ccc.add(this.slider_p_longondas[5], (Object)null);
      this.panel_p_ccc.add(this.label_p_vlongondas[5], (Object)null);
      this.panel_p_ccc.add(this.label_p_angdesvs[5], (Object)null);
      this.panel_p_ccc.add(this.label_p_longondas[6], (Object)null);
      this.panel_p_ccc.add(this.slider_p_longondas[6], (Object)null);
      this.panel_p_ccc.add(this.label_p_vlongondas[6], (Object)null);
      this.panel_p_ccc.add(this.label_p_angdesvs[6], (Object)null);
      this.panel_p_wnc.add(this.panel_p_wncc, "Center");
      this.panel_p_wncn.add(this.dibujoprisma, "North");
      this.panel_p_wncc.add(this.dibujolineasprisma, "Center");
      this.panel_p_indice.add(this.panel_p_indice_n, "North");
      this.panel_p_indice.add(this.panel_p_indice_s, "South");
      this.panel_p_indice.add(this.panel_p_indice_e, "East");
      this.panel_p_indice.add(this.panel_p_indice_c, "Center");
      this.panel_p_indice_c.add(this.indiceprisma, "Center");
      this.panel_p_delta.add(this.panel_p_delta_w, "West");
      this.panel_p_delta.add(this.panel_p_delta_e, "East");
      this.panel_p_delta.add(this.panel_p_delta_s, "South");
      this.panel_p_delta.add(this.panel_p_delta_c, "Center");
      this.panel_p_delta_c.add(this.graficoangulodesviacion, "Center");
      this.panel_rainbow.add(this.panel_rainbow_w, "West");
      this.panel_rainbow.add(this.panel_rainbow_c, "Center");
      this.panel_rainbow_c.add(this.panel_r_cn, "North");
      this.panel_rainbow_w.add(this.panel_r_wn, "North");
      this.panel_rainbow_w.add(this.panel_r_wc, "Center");
      this.panel_r_wn.add(this.panel_r_wnn, "North");
      this.panel_r_wn.add(this.panel_r_wnw, "West");
      this.panel_r_wn.add(this.panel_r_wns, "South");
      this.panel_r_wn.add(this.panel_r_wne, "East");
      this.panel_r_wn.add(this.panel_r_wnc, "Center");
      this.panel_r_wc.add(this.label_r_paramimp, (Object)null);
      this.panel_r_wc.add(this.slider_r_paramimp, (Object)null);
      this.panel_r_wc.add(this.label_r_nparamimp, (Object)null);
      this.panel_r_wc.add(this.label_r_longonda, (Object)null);
      this.panel_r_wc.add(this.slider_r_longonda, (Object)null);
      this.panel_r_wc.add(this.label_r_nlongonda, (Object)null);
      this.panel_r_wc.add(this.label_r_angin, (Object)null);
      this.panel_r_wc.add(this.label_r_angin_val, (Object)null);
      this.panel_r_wc.add(this.label_r_angout, (Object)null);
      this.panel_r_wc.add(this.label_r_angout_val, (Object)null);
      this.panel_r_wc.add(this.label_r_angdesv1, (Object)null);
      this.panel_r_wnc.add(this.dibujogota, "Center");
      this.panel_rainbow_c.add(this.panel_r_cc, "Center");
      this.panel_r_cc.add(this.panel_r_ccn, "North");
      this.panel_r_cn.add(this.panel_r_cnn, "North");
      this.panel_r_cn.add(this.panel_r_cnw, "West");
      this.panel_r_cn.add(this.panel_r_cne, "East");
      this.panel_r_cn.add(this.panel_r_cns, "South");
      this.panel_r_cn.add(this.panel_r_cnc, "Center");
      this.panel_r_cnc.add(this.dibujoarco, "Center");
      this.panel_r_cc.add(this.panel_r_ccs, "South");
      this.panel_r_cc.add(this.panel_r_ccw, "West");
      this.panel_r_cc.add(this.panel_r_cce, "East");
      this.panel_r_cc.add(this.panel_r_ccc, "Center");
      this.panel_r_ccc.add(this.graficodesvarcoiris, "Center");
      this.tabbedPane.add(this.jPanel_info, this.info_etiqueta[lang]);
      this.panel_r_wc.add(this.label_r_angdesv1_val, (Object)null);
      this.panel_r_wc.add(this.label_r_angdesv2, (Object)null);
      this.panel_r_wc.add(this.label_r_angdesv2_val, (Object)null);
      this.panel_r_wc.add(this.label_r_parimpmin1, (Object)null);
      this.panel_r_wc.add(this.label_r_parimpmin1_val, (Object)null);
      this.panel_r_wc.add(this.label_r_parimpmin2, (Object)null);
      this.panel_r_wc.add(this.label_r_parimpmin2_val, (Object)null);
      this.panel_r_wc.add(this.label_r_angdesvmin1, (Object)null);
      this.panel_r_wc.add(this.label_r_angdesvmin1_val, (Object)null);
      this.panel_r_wc.add(this.label_r_angdesvmin2, (Object)null);
      this.panel_r_wc.add(this.label_r_angdesvmin2_val, (Object)null);
      this.jPanel_info.add(this.jScrollPane_info, "Center");
      this.jScrollPane_info.getViewport().add(this.jTextPane_info, (Object)null);
   }

   public String getAppletInfo() {
      return "Información del applet";
   }

   public String[][] getParameterInfo() {
      String[][] pinfo = new String[][]{{"idioma", "int", ""}};
      return pinfo;
   }

   public static void main(String[] args) {
      try {
         String dato = args[0].toUpperCase().intern();
         if (dato == "CA") {
            lang = 1;
         } else if (dato == "ES") {
            lang = 0;
         } else if (dato == "EN") {
            lang = 2;
         } else {
            lang = 0;
         }
      } catch (Exception var4) {
         lang = 0;
      }

      DispersionApplet applet = new DispersionApplet();
      applet.isStandalone = true;
      Frame frame = new Frame();
      frame.setTitle("Applet dispersion");
      frame.setResizable(false);
      frame.add(applet, "Center");
      if (icon_joc != null) {
         frame.setIconImage(icon_joc.getImage());
      }

      applet.init();
      applet.start();
      frame.setSize(750, 570);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
      frame.setVisible(true);
   }

   void boton_salir_actionPerformed(ActionEvent e) {
      try {
         System.exit(0);
      } catch (Exception var3) {
         System.out.println("Error al salir");
      }

   }

   void boton_acercade_actionPerformed(ActionEvent e) {
      Frame f = new Frame();
      Object[] options = new Object[]{this.etiqueta_acerca[1][lang]};
      ImageIcon icon_joc = null;

      try {
         String st_icon = "jocon.jpg";
         URL url_icon = this.getClass().getResource(st_icon);
         icon_joc = new ImageIcon(url_icon, "Dispersion");
      } catch (Exception var7) {
         System.out.println("No carga icono");
      }

      JOptionPane option = new JOptionPane(this.etiqueta_acerca[2][lang], 1, -1, icon_joc, options);
      JDialog dialog = option.createDialog(f, this.etiqueta_acerca[0][lang]);
      dialog.setResizable(false);
      dialog.show();
   }

   void actua_parametros_prisma() {
      char pto = '.';
      this.df_symb.setDecimalSeparator(pto);
      this.df.setDecimalFormatSymbols(this.df_symb);
      this.df2.setDecimalFormatSymbols(this.df_symb);
      this.ang_prisma = (double)this.slider_p_angp.getValue() * 0.017453292519943295D;
      this.ang_in_prisma = (double)this.slider_p_angin.getValue() * 0.017453292519943295D;
      this.label_p_nangp.setText(this.slider_p_angp.getValue() + "º");
      this.label_p_nangin.setText(this.slider_p_angin.getValue() + "º");
      int tipo_espectro = this.comboBox_p_espec.getSelectedIndex();
      int i;
      if (tipo_espectro == 0) {
         for(i = 0; i < 7; ++i) {
            this.slider_p_longondas[i].setEnabled(true);
            this.long_onda_prisma[i] = this.slider_p_longondas[i].getValue();
            this.label_p_vlongondas[i].setText(this.long_onda_prisma[i] + " nm");
         }
      } else {
         for(i = 0; i < 7; ++i) {
            this.slider_p_longondas[i].setEnabled(false);
            this.long_onda_prisma[i] = (int)this.espectro_luz[tipo_espectro][i];
            this.label_p_vlongondas[i].setText(this.long_onda_prisma[i] + " nm");
            this.slider_p_longondas[i].setValue((int)this.espectro_luz[tipo_espectro][i]);
         }
      }

      int tipo_vidrio = this.comboBox_p_material.getSelectedIndex();
      if (tipo_vidrio == 0) {
         this.slider_p_indc_n0.setEnabled(true);
         this.slider_p_indc_A.setEnabled(true);
         this.slider_p_indc_B.setEnabled(true);
         this.n0_prisma = (double)this.slider_p_indc_n0.getValue() / 1000.0D;
         this.label_p_indc_vn0.setText(this.df.format(this.n0_prisma));
         this.nA_prisma = (double)this.slider_p_indc_A.getValue() * 10.0D;
         this.label_p_indc_vA.setText(this.df.format(this.nA_prisma));
         this.nB_prisma = ((double)this.slider_p_indc_B.getValue() - 10000.0D) * 10000.0D;
         this.label_p_indc_vB.setText(this.df.format(this.nB_prisma));
      } else {
         this.slider_p_indc_n0.setEnabled(false);
         this.slider_p_indc_A.setEnabled(false);
         this.slider_p_indc_B.setEnabled(false);
         this.n0_prisma = this.indices_vidrios[tipo_vidrio][0];
         this.label_p_indc_vn0.setText(this.df.format(this.n0_prisma));
         this.nA_prisma = this.indices_vidrios[tipo_vidrio][1];
         this.label_p_indc_vA.setText(this.df.format(this.nA_prisma));
         this.nB_prisma = this.indices_vidrios[tipo_vidrio][2];
         this.label_p_indc_vB.setText(this.df.format(this.nB_prisma));
         this.slider_p_indc_n0.setValue((int)(this.n0_prisma * 1000.0D));
         this.slider_p_indc_A.setValue((int)(this.nA_prisma / 10.0D));
         this.slider_p_indc_B.setValue((int)(this.nB_prisma / 1000.0D + 10000.0D));
      }

      double nd = this.n0_prisma + this.nA_prisma / 345273.76D + this.nB_prisma / 1.1921396934453761E11D;
      double nf = this.n0_prisma + this.nA_prisma / 236293.21000000002D + this.nB_prisma / 5.583448109210411E10D;
      double nc = this.n0_prisma + this.nA_prisma / 430729.68999999994D + this.nB_prisma / 1.8552806584749603E11D;
      double nmax = this.n0_prisma + this.nA_prisma / 160000.0D + this.nB_prisma / 2.56E10D;
      double nmin = this.n0_prisma + this.nA_prisma / 490000.0D + this.nB_prisma / 2.401E11D;
      double lambda_extrem = 0.0D;
      if (this.nA_prisma != 0.0D) {
         lambda_extrem = Math.sqrt(-2.0D * this.nB_prisma / this.nA_prisma);
      }

      if (Double.isNaN(lambda_extrem)) {
         lambda_extrem = 0.0D;
      }

      double nabbe = (nd - 1.0D) / (nf - nc);
      this.label_p_indc_nd.setText("nd = " + this.df.format(nd));
      this.label_p_indc_nC.setText("nC = " + this.df.format(nc));
      this.label_p_indc_nF.setText("nF = " + this.df.format(nf));
      if (nf > nd && nd > nc && nf > nc && nc < nmax && nmin < nf && nmax > nmin && lambda_extrem < 400.0D) {
         this.label_p_abbe.setText("nº Abbe: " + this.df2.format(nabbe));
         this.label_p_tipovidrio.setForeground(new Color(102, 102, 153));
         if (nabbe > 50.0D) {
            this.label_p_tipovidrio.setText(this.etiquetas_prisma[4][lang] + ": " + "crown");
         } else {
            this.label_p_tipovidrio.setText(this.etiquetas_prisma[4][lang] + ": " + "flint");
         }
      } else {
         this.label_p_abbe.setText("nº Abbe: --");
         this.label_p_tipovidrio.setForeground(Color.red);
         this.label_p_tipovidrio.setText(this.etiquetas_prisma[4][lang] + ": " + this.etiquetas_prisma[10][lang]);
      }

      double angdesvmin = (2.0D * Math.asin(nd * Math.sin(this.ang_prisma / 2.0D)) - this.ang_prisma) * 57.29577951308232D;
      if (Double.isNaN(angdesvmin)) {
         this.label_p_angdesvmin.setForeground(Color.red);
         this.label_p_angdesvmin.setText(this.etiquetas_prisma[2][lang] + " (nd): > a.l.");
      } else {
         this.label_p_angdesvmin.setForeground(new Color(102, 102, 153));
         this.label_p_angdesvmin.setText(this.etiquetas_prisma[2][lang] + " (nd): " + this.df.format(angdesvmin) + "º");
      }

      double sinalpha = Math.sin(this.ang_prisma);
      double cosalpha = Math.cos(this.ang_prisma);
      double sinepsilon1 = Math.sin(this.ang_in_prisma);
      double cosepsilon1 = Math.cos(this.ang_in_prisma);

      int i;
      for(i = 0; i < 7; ++i) {
         double longonda2 = (double)this.long_onda_prisma[i] * (double)this.long_onda_prisma[i];
         this.indices_prisma[i] = this.n0_prisma + this.nA_prisma / longonda2 + this.nB_prisma / (longonda2 * longonda2);
         double ang_limite = Math.asin(1.0D / this.indices_prisma[i]);
         double epsilon1prima = Math.asin(sinepsilon1 / this.indices_prisma[i]);
         double epsilon2 = this.ang_prisma - epsilon1prima;
         double sinepsilon2 = Math.sin(epsilon2);
         double epsilon2prima;
         if (epsilon2 < ang_limite) {
            double sinepsilon2prima = this.indices_prisma[i] * sinepsilon2;
            epsilon2prima = Math.asin(sinepsilon2prima);
            if (Double.isNaN(epsilon2prima)) {
               this.ange2prima_prisma[i] = 3.141592653589793D;
            } else {
               this.ange2prima_prisma[i] = epsilon2prima;
            }
         } else {
            epsilon2prima = 3.141592653589793D;
            this.ange2prima_prisma[i] = epsilon2prima;
         }

         this.angdesv_prisma[i] = this.ang_in_prisma + this.ange2prima_prisma[i] - this.ang_prisma;
      }

      for(i = 0; i < 7; ++i) {
         if (this.ange2prima_prisma[i] != 3.141592653589793D) {
            this.label_p_angdesvs[i].setForeground(new Color(102, 102, 153));
            this.label_p_angdesvs[i].setText(this.df.format(this.angdesv_prisma[i] * 57.29577951308232D));
         } else {
            this.label_p_angdesvs[i].setForeground(Color.red);
            this.label_p_angdesvs[i].setText("> a.l.");
         }
      }

      if (this.toggleButton_p_zoom.isSelected()) {
         this.label_prisma_zoom = true;
      } else {
         this.label_prisma_zoom = false;
      }

      this.dibujoprisma.putAtributos(this.ang_prisma, this.ang_in_prisma, this.long_onda_prisma, this.indices_prisma, this.angdesv_prisma, this.ange2prima_prisma);
      this.dibujolineasprisma.putAtributos(this.ang_prisma, this.ang_in_prisma, this.long_onda_prisma, this.indices_prisma, this.angdesv_prisma, this.label_prisma_zoom, this.ange2prima_prisma);
      this.panel_p_wnc.repaint();
      if (nf > nd && nd > nc && nf > nc && nc > nmin && nf < nmax && nmax > nmin && lambda_extrem < 400.0D) {
         this.indiceprisma.putAtributos(this.n0_prisma, this.nA_prisma, this.nB_prisma, lang);
      } else {
         this.indiceprisma.putAtributos(0.0D, 0.0D, 0.0D, lang);
      }

      this.panel_p_indice.repaint();
      this.graficoangulodesviacion.putAtributos(this.ang_prisma, this.long_onda_prisma, this.indices_prisma, nd, lang);
      this.panel_p_delta.repaint();
   }

   void actua_parametros_rainbow() {
      char pto = '.';
      this.df_symb.setDecimalSeparator(pto);
      this.df2.setDecimalFormatSymbols(this.df_symb);
      this.param_imp_rainbow = (double)this.slider_r_paramimp.getValue() / 100.0D;
      this.long_onda_rainbow = this.slider_r_longonda.getValue();
      this.label_r_nparamimp.setText(this.df2.format(this.param_imp_rainbow));
      this.label_r_nlongonda.setText(this.long_onda_rainbow + " nm");
      this.dibujogota.putAtributos(this.param_imp_rainbow, this.long_onda_rainbow, lang);
      this.panel_r_wn.repaint();
      this.dibujoarco.putAtributos(this.long_onda_rainbow, lang);
      this.panel_r_cn.repaint();
      this.graficodesvarcoiris.putAtributos(this.param_imp_rainbow, this.long_onda_rainbow, lang);
      this.panel_r_cc.repaint();
      double n0 = 1.323585015D;
      double nA = 3878.664771D;
      double nB = -1.132388308E8D;
      double lambda2 = (double)this.long_onda_rainbow * (double)this.long_onda_rainbow;
      double lambda4 = lambda2 * lambda2;
      double n = n0 + nA / lambda2 + nB / lambda4;
      double alpha = Math.asin(this.param_imp_rainbow);
      double beta = Math.asin(this.param_imp_rainbow / n);
      double desv_p = (3.141592653589793D + 2.0D * alpha - 4.0D * beta) * 57.29577951308232D;
      double desv_s = (-2.0D * alpha + 6.0D * beta) * 57.29577951308232D;
      this.label_r_angin_val.setText(this.df2.format(alpha * 57.29577951308232D) + "º");
      this.label_r_angout_val.setText(this.df2.format(beta * 57.29577951308232D) + "º");
      this.label_r_angdesv1_val.setText(this.df2.format(desv_p) + "º");
      this.label_r_angdesv2_val.setText(this.df2.format(desv_s) + "º");
      double b_min_primary = Math.sqrt((4.0D - n * n) / 3.0D);
      double b_min_secondary = Math.sqrt((9.0D - n * n) / 8.0D);
      double alpha_min_primary = Math.asin(b_min_primary);
      double alpha_min_secondary = Math.asin(b_min_secondary);
      double beta_min_primary = Math.asin(b_min_primary / n);
      double beta_min_secondary = Math.asin(b_min_secondary / n);
      double desv_min_p = (3.141592653589793D + 2.0D * alpha_min_primary - 4.0D * beta_min_primary) * 57.29577951308232D;
      double desv_min_s = (-2.0D * alpha_min_secondary + 6.0D * beta_min_secondary) * 57.29577951308232D;
      this.label_r_parimpmin1_val.setText(this.df.format(b_min_primary));
      this.label_r_parimpmin2_val.setText(this.df.format(b_min_secondary));
      this.label_r_angdesvmin1_val.setText(this.df2.format(desv_min_p) + "º");
      this.label_r_angdesvmin2_val.setText(this.df2.format(desv_min_s) + "º");
   }

   private void carga_info(int lengua) {
      String s = null;

      try {
         if (lengua == 1) {
            s = "DocA_DispersionCa.htm";
         } else if (lengua == 2) {
            s = "DocA_DispersionEn.htm";
         } else {
            s = "DocA_DispersionEs.htm";
         }

         this.info_page = this.getClass().getResource(s);
      } catch (Exception var5) {
         System.err.println("Couldn't create help URL: " + s);
      }

      try {
         this.jTextPane_info.setPage(this.info_page);
      } catch (IOException var4) {
         System.err.println("Attempted to read a bad URL: " + this.info_page);
      }

   }

   void slider_p_longonda1_keyPressed(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda1_keyTyped(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda1_mouseClicked(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda1_mouseDragged(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda2_keyPressed(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda2_keyTyped(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda2_mouseClicked(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda2_mouseDragged(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda3_keyPressed(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda3_keyTyped(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda3_mouseClicked(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda3_mouseDragged(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda4_keyPressed(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda4_keyTyped(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda4_mouseClicked(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda4_mouseDragged(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda5_keyPressed(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda5_keyTyped(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda5_mouseClicked(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda5_mouseDragged(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda6_keyPressed(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda6_keyTyped(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda6_mouseClicked(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda6_mouseDragged(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda7_keyPressed(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda7_keyTyped(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda7_mouseClicked(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_longonda7_mouseDragged(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_angp_keyPressed(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_angp_keyTyped(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_angp_mouseClicked(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_angp_mouseDragged(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_angin_keyTyped(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_angin_keyPressed(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_angin_mouseClicked(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_angin_mouseDragged(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_indc_n0_mouseClicked(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_indc_n0_mouseDragged(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_indc_n0_keyPressed(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_indc_n0_keyTyped(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_indc_A_keyPressed(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_indc_A_keyTyped(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_indc_A_mouseClicked(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_indc_A_mouseDragged(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_indc_B_keyPressed(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_indc_B_keyTyped(KeyEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_indc_B_mouseClicked(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_p_indc_B_mouseDragged(MouseEvent e) {
      this.actua_parametros_prisma();
   }

   void toggleButton_p_zoom_actionPerformed(ActionEvent e) {
      this.actua_parametros_prisma();
   }

   void comboBox_p_material_actionPerformed(ActionEvent e) {
      this.actua_parametros_prisma();
   }

   void comboBox_p_espec_actionPerformed(ActionEvent e) {
      this.actua_parametros_prisma();
   }

   void slider_r_paramimp_keyPressed(KeyEvent e) {
      this.actua_parametros_rainbow();
   }

   void slider_r_paramimp_keyReleased(KeyEvent e) {
      this.actua_parametros_rainbow();
   }

   void slider_r_paramimp_mouseClicked(MouseEvent e) {
      this.actua_parametros_rainbow();
   }

   void slider_r_paramimp_mouseDragged(MouseEvent e) {
      this.actua_parametros_rainbow();
   }

   void slider_r_longonda_keyPressed(KeyEvent e) {
      this.actua_parametros_rainbow();
   }

   void slider_r_longonda_keyTyped(KeyEvent e) {
      this.actua_parametros_rainbow();
   }

   void slider_r_longonda_mouseClicked(MouseEvent e) {
      this.actua_parametros_rainbow();
   }

   void slider_r_longonda_mouseDragged(MouseEvent e) {
      this.actua_parametros_rainbow();
   }
}
