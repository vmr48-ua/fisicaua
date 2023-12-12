package difraccion;

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
import javax.swing.JToggleButton;

public class DifraccionApplet extends Applet {
   String[][] acerca_etiqueta = new String[][]{{"Acerca de", "En quant a", "About"}, {"Aceptar", "Acceptar", "OK"}, {"Javaoptics: Applet de Difracción de Fresnel y Fraunhofer v2.0a \n Grup d'Innovació Docent en Òptica Física i Fotònica \n Universitat de Barcelona, 2003 \n La utilización de este programa está bajo una licencia de Creative Commons \n Ver condiciones en http://creativecommons.org/license/by-nc-sa/2.0/ \n \n Curso de Óptica en Java DOI: 10.1344/401.000000050 \n Applet de Difracción de Fresnel y Fraunhofer DOI: 10.1344/203.000000094", "Javaoptics: Applet de Difracció de Fresnel i Fraunhofer v2.0a \n Grup d'Innovació Docent en Òptica Física i Fotònica \n Universitat de Barcelona, 2003 \n La utilització d'aquest programa està sota una llicència de Creative Commons \n Veure condicions a http://www.publicacions.ub.es/doi/licencia/resum-deriv.htm \n \n Curs d'Òptica en Java DOI: 10.1344/401.000000050 \n Applet de Difracció de Fresnel i Fraunhofer DOI: 10.1344/203.000000094", "Javaoptics: Fresnel and Fraunhofer Diffraction Applet v2.0a \n Grup d'Innovació Docent en Òptica Física i Fotònica \n Universitat de Barcelona, 2003 \n The use of this program is under a Creative Commons license \n See conditions in http://creativecommons.org/license/by-nc-sa/2.0/ \n \n Java Optics Course DOI: 10.1344/401.000000050 \n Fresnel and Fraunhofer Diffraction applet DOI: 10.1344/203.000000094"}};
   String[][] fraun_etiqueta = new String[][]{{"Parámetros del sistema:", "Paràmetres del sistema", "System parameters"}, {"Longitud de onda:", "Longitud d'ona:", "Wavelength:"}, {"Distancia focal:", "Distància focal:", "Focal distance:"}, {"Objeto:", "Objecte:", "Object:"}, {"Rectángulo", "Rectangle", "Rectangle"}, {"Círculo", "Cercle", "Circle"}, {"Rendija", "Escletxa", "Slit"}, {"Lado x:", "Costat x:", "X side:"}, {"Lado y:", "Costat y:", "Y side:"}, {"N objetos:", "N objectes:", "N objects:"}, {"Período objetos:", "Període objectes:", "Objects period:"}, {"Tamaño imagen objeto:", "Mida imatge objecte:", "Object image size:"}, {"Umbral:", "Llindar:", "Threshold:"}, {"Mostrar:", "Mostrar:", "Show:"}, {"Imagen", "Imatge", "Image"}, {"Perfil fila central", "Perfil fila central", "Central row profile"}, {"Amplitud", "Amplitud", "Amplitude"}, {"Intensidad", "Intensitat", "Intensity"}, {"Log Intensidad", "Log Intensitat", "Log Intensity"}, {"Tamaño imagen difracción:", "Mida imatge difracció:", "Diffraction image size:"}, {"Distancia 1º mínimo difracción:", "Distància 1º mínim difracció:", "Distance 1st diffraction min:"}, {"Distancia entre máximos:", "Distància entre màxims:", "Distance between maximums:"}, {"Distancia entre mínimos:", "Distància entre mínims:", "Distance between minimums:"}, {"Diámetro:", "Diàmetre", "Diameter:"}, {"Dist. 1º min difracción en x: ", "Dist. 1º min difracció en x: ", "Dist. 1st diffraction min in x: "}, {", en y: ", ", en y: ", ", in y: "}, {"Radio de Airy: ", "Radi d'Airy: ", "Airy's radius: "}, {"Distancia entre máximos: ", "Distància entre màxims: ", "Distance between maximums: "}, {"Distancia entre mínimos: ", "Distància entre mínims: ", "Distance between minimums: "}};
   String[] boton_salir = new String[]{"Salir", "Sortir", "Exit"};
   String[] info_etiqueta = new String[]{"Resumen Teoría", "Resum Teoria", "Theory Summary"};
   String[][] fres_etiqueta = new String[][]{{"Semiplano", "Semipla", "Semiplane"}, {"Dist. propagación:", "Dist. propagació:", "Propagation dist.:"}, {"Calcular difracción", "Calcular diffracció", "Compute diffraction"}};
   int long_onda;
   double dist_focal;
   int num_objetos = 0;
   int n_objetos;
   int tipo_objeto = 0;
   double dim_x;
   double dim_y;
   double fraun_periodo;
   double fraun_tam_img;
   int label_fraun_show;
   int label_fraun_tipoimg = 1;
   double fraun_umbral;
   boolean label_fraun_zoom;
   boolean label_fraun_activaimg = true;
   int tipo_objeto_fresnel = 0;
   int long_onda_fres;
   double dist_prop;
   double dim_x_fres;
   double dim_y_fres;
   boolean label_fres_calcula;
   int numpto_x = 256;
   int numpto_y = 256;
   int numpto_total;
   double[] matriz_fres_re;
   double[] matriz_fres_im;
   int label_fres_tipoimg;
   double fres_umbral;
   TransformadaFresnel transformadaFresnel;
   boolean label_fres_activaimg;
   DecimalFormatSymbols df_symb;
   DecimalFormat df;
   private boolean isStandalone;
   static int lang;
   BorderLayout borderLayout1;
   JPanel jPanel_Base;
   BorderLayout borderLayout2;
   JPanel jPanel_Norte;
   JPanel jPanel_Centro;
   JPanel jPanel_Sur;
   FlowLayout flowLayout1;
   BorderLayout borderLayout3;
   FlowLayout flowLayout2;
   JButton jButton_Salir;
   JButton jButton_Acercade;
   JTabbedPane jTabbedPane1;
   JPanel jPanel_Fresnel;
   BorderLayout borderLayout4;
   JPanel jPanel_Fraunhofer;
   BorderLayout borderLayout5;
   JPanel jPanel_Info;
   BorderLayout borderLayout6;
   JPanel jPanel_Fraun_w;
   BorderLayout borderLayout7;
   JPanel jPanel_Fraun_c;
   BorderLayout borderLayout8;
   JPanel jPanel_Fraun_w_n;
   BorderLayout borderLayout9;
   JPanel jPanel_Fraun_w_c;
   FlowLayout flowLayout3;
   JPanel jPanel_Fraun_w_n_n;
   BorderLayout borderLayout10;
   JPanel jPanel_Fraun_w_n_c;
   BorderLayout borderLayout11;
   JPanel jPanel_Fraun_w_n_s;
   BorderLayout borderLayout12;
   JPanel jPanel_Fraun_w_n_w;
   BorderLayout borderLayout13;
   JPanel jPanel_Fraun_w_n_e;
   BorderLayout borderLayout14;
   JPanel jPanel_Fraun_c_n;
   BorderLayout borderLayout15;
   JPanel jPanel_fraun_c_c;
   FlowLayout flowLayout4;
   JPanel jPanel_Fraun_c_n_n;
   BorderLayout borderLayout16;
   JPanel jPanel_Fraun_c_n_c;
   BorderLayout borderLayout17;
   JPanel jPanel_Fraun_c_n_s;
   BorderLayout borderLayout18;
   JPanel jPanel_Fraun_c_n_w;
   BorderLayout borderLayout19;
   JPanel jPanel_Fraun_c_n_e;
   BorderLayout borderLayout20;
   JPanel jPanel_fres_w;
   BorderLayout borderLayout21;
   JPanel jPanel_fres_c;
   BorderLayout borderLayout22;
   JPanel jPanel_fres_w_n;
   BorderLayout borderLayout23;
   JPanel jPanel_fres_w_c;
   JPanel jPanel_fres_w_n_n;
   BorderLayout borderLayout25;
   JPanel jPanel_fres_w_n_c;
   BorderLayout borderLayout26;
   JPanel jPanel_fres_w_n_s;
   BorderLayout borderLayout27;
   JPanel jPanel_fres_w_n_w;
   BorderLayout borderLayout28;
   JPanel jPanel_fres_w_n_e;
   BorderLayout borderLayout29;
   FlowLayout flowLayout5;
   JPanel jPanel_fres_c_n;
   BorderLayout borderLayout24;
   JPanel jPanel_fres_c_c;
   FlowLayout flowLayout6;
   JPanel jPanel_fres_c_n_n;
   BorderLayout borderLayout30;
   JPanel jPanel_fres_c_n_c;
   JPanel jPanel_fres_c_n_s;
   BorderLayout borderLayout31;
   BorderLayout borderLayout32;
   JPanel jPanel_fres_c_n_w;
   BorderLayout borderLayout33;
   JPanel jPanel_fres_c_n_e;
   BorderLayout borderLayout34;
   JLabel jLabel_fraun_param;
   JLabel jLabel_fraun_longOnda;
   JSlider jSlider_fraun_longOnda;
   JLabel jLabel_fraun_nlongOnda;
   JLabel jLabel_fraun_distFocal;
   JSlider jSlider_fraun_distFocal;
   JLabel jLabel_fraun_ndistFocal;
   JLabel jLabel_fraun_objeto;
   JRadioButton jRadioButton_fraun_rectang;
   JRadioButton jRadioButton_fraun_circ;
   JRadioButton jRadioButton_fraun_rend;
   ButtonGroup group_fraun_tipoobj;
   JPanel jPanel_fraun_dimObjeto;
   FlowLayout flowLayout7;
   JLabel jLabel_fraun_dimx;
   JSlider jSlider_fraun_dimx;
   JLabel jLabel_fraun_ndimx;
   JLabel jLabel_fraun_dimy;
   JSlider jSlider_fraun_dimy;
   JLabel jLabel_fraun_ndimy;
   JLabel jLabel_fraun_nObjetos;
   JSlider jSlider_fraun_nobjetos;
   JLabel jLabel_fraun_numobj;
   JLabel jLabel_fraun_periodo;
   JLabel jLabel_fraun_dimimag;
   PanelObjeto objetoFraunhofer;
   ImagenFraunhofer imagenFraun;
   PerfilFraunhofer perfilFraun;
   JSplitPane jSplitPane_Fraun;
   JLabel jLabel_fraun_umbral;
   JSlider jSlider_fraun_umbral;
   JLabel jLabel_fraun_numbral;
   JLabel jLabel_fraun_show;
   JRadioButton jRadioButton_fraun_showimg;
   JRadioButton jRadioButton_fraun_showperf;
   ButtonGroup group_fraun_show;
   JLabel jLabel_fraun_tipoimg;
   JRadioButton jRadioButton_fraun_tipoimg_amp;
   JRadioButton jRadioButton_fraun_tipoimg_int;
   JRadioButton jRadioButton_fraun_tipoimg_intlog;
   ButtonGroup group_fraun_tipoimg;
   JLabel jLabel_fraun_tamdiff;
   JLabel jLabel_fraun_distmindif;
   JLabel jLabel_fraun_distnmax;
   JLabel jLabel_fraun_distnmin;
   JToggleButton jToggleButton_fraun_zoom;
   JPanel jPanel_fraun_showimg;
   FlowLayout flowLayout8;
   JLabel jLabel_fres_param;
   JLabel jLabel_fres_longOnda;
   JSlider jSlider_fres_longOnda;
   JLabel jLabel_fres_nlongOnda;
   JLabel jLabel_fres_distProp;
   JSlider jSlider_fres_distProp;
   JLabel jLabel_fres_ndistProp;
   JLabel jLabel_fres_objeto;
   JLabel jLabel_fres_nada;
   JRadioButton jRadioButton_fres_rectang;
   JRadioButton jRadioButton_fres_circ;
   JRadioButton jRadioButton_fres_rend;
   JRadioButton jRadioButton_fres_semip;
   ButtonGroup group_fres_tipoobj;
   JPanel jPanel_fres_dimObjeto;
   FlowLayout flowLayoutx7;
   JLabel jLabel_fres_dimx;
   JSlider jSlider_fres_dimx;
   JLabel jLabel_fres_ndimx;
   JLabel jLabel_fres_dimy;
   JSlider jSlider_fres_dimy;
   JLabel jLabel_fres_ndimy;
   JButton jButton_fres_calcdif;
   PanelObjetoFresnel objetoFresnel;
   ImagenFresnel imagenFresnel;
   PerfilFresnel perfilFresnel;
   JSplitPane jSplitPane_fres;
   JLabel jLabel_fres_umbral;
   JSlider jSlider_fres_umbral;
   JLabel jLabel_fres_numbral;
   JLabel jLabel_fres_show;
   JRadioButton jRadioButton_fres_showimg;
   JRadioButton jRadioButton_fres_showperf;
   ButtonGroup group_fres_show;
   JLabel jLabel_fres_tipoimg;
   JRadioButton jRadioButton_fres_tipoimg_amp;
   JRadioButton jRadioButton_fres_tipoimg_int;
   JRadioButton jRadioButton_fres_tipoimg_intlog;
   ButtonGroup group_fres_tipoimg;
   JLabel jLabel_fres_dimimag;
   JLabel jLabel_fres_tamdiff;
   JPanel jPanel_fres_showimg;
   FlowLayout flowLayoutx8;
   static ImageIcon icon_joc = null;
   JLabel jLabel_fres_nada2;
   URL info_page;
   JScrollPane jScrollPane_teoria;
   JTextPane jTextPane_teoria;

   public String getParameter(String key, String def) {
      return this.isStandalone ? System.getProperty(key, def) : (this.getParameter(key) != null ? this.getParameter(key) : def);
   }

   public DifraccionApplet() {
      this.numpto_total = this.numpto_x * this.numpto_y;
      this.matriz_fres_re = new double[this.numpto_total];
      this.matriz_fres_im = new double[this.numpto_total];
      this.label_fres_tipoimg = 1;
      this.transformadaFresnel = new TransformadaFresnel();
      this.label_fres_activaimg = true;
      this.df_symb = new DecimalFormatSymbols();
      this.df = new DecimalFormat("#.###", this.df_symb);
      this.isStandalone = false;
      this.borderLayout1 = new BorderLayout();
      this.jPanel_Base = new JPanel();
      this.borderLayout2 = new BorderLayout();
      this.jPanel_Norte = new JPanel();
      this.jPanel_Centro = new JPanel();
      this.jPanel_Sur = new JPanel();
      this.flowLayout1 = new FlowLayout();
      this.borderLayout3 = new BorderLayout();
      this.flowLayout2 = new FlowLayout();
      this.jButton_Salir = new JButton();
      this.jButton_Acercade = new JButton();
      this.jTabbedPane1 = new JTabbedPane();
      this.jPanel_Fresnel = new JPanel();
      this.borderLayout4 = new BorderLayout();
      this.jPanel_Fraunhofer = new JPanel();
      this.borderLayout5 = new BorderLayout();
      this.jPanel_Info = new JPanel();
      this.borderLayout6 = new BorderLayout();
      this.jPanel_Fraun_w = new JPanel();
      this.borderLayout7 = new BorderLayout();
      this.jPanel_Fraun_c = new JPanel();
      this.borderLayout8 = new BorderLayout();
      this.jPanel_Fraun_w_n = new JPanel();
      this.borderLayout9 = new BorderLayout();
      this.jPanel_Fraun_w_c = new JPanel();
      this.flowLayout3 = new FlowLayout();
      this.jPanel_Fraun_w_n_n = new JPanel();
      this.borderLayout10 = new BorderLayout();
      this.jPanel_Fraun_w_n_c = new JPanel();
      this.borderLayout11 = new BorderLayout();
      this.jPanel_Fraun_w_n_s = new JPanel();
      this.borderLayout12 = new BorderLayout();
      this.jPanel_Fraun_w_n_w = new JPanel();
      this.borderLayout13 = new BorderLayout();
      this.jPanel_Fraun_w_n_e = new JPanel();
      this.borderLayout14 = new BorderLayout();
      this.jPanel_Fraun_c_n = new JPanel();
      this.borderLayout15 = new BorderLayout();
      this.jPanel_fraun_c_c = new JPanel();
      this.flowLayout4 = new FlowLayout();
      this.jPanel_Fraun_c_n_n = new JPanel();
      this.borderLayout16 = new BorderLayout();
      this.jPanel_Fraun_c_n_c = new JPanel();
      this.borderLayout17 = new BorderLayout();
      this.jPanel_Fraun_c_n_s = new JPanel();
      this.borderLayout18 = new BorderLayout();
      this.jPanel_Fraun_c_n_w = new JPanel();
      this.borderLayout19 = new BorderLayout();
      this.jPanel_Fraun_c_n_e = new JPanel();
      this.borderLayout20 = new BorderLayout();
      this.jPanel_fres_w = new JPanel();
      this.borderLayout21 = new BorderLayout();
      this.jPanel_fres_c = new JPanel();
      this.borderLayout22 = new BorderLayout();
      this.jPanel_fres_w_n = new JPanel();
      this.borderLayout23 = new BorderLayout();
      this.jPanel_fres_w_c = new JPanel();
      this.jPanel_fres_w_n_n = new JPanel();
      this.borderLayout25 = new BorderLayout();
      this.jPanel_fres_w_n_c = new JPanel();
      this.borderLayout26 = new BorderLayout();
      this.jPanel_fres_w_n_s = new JPanel();
      this.borderLayout27 = new BorderLayout();
      this.jPanel_fres_w_n_w = new JPanel();
      this.borderLayout28 = new BorderLayout();
      this.jPanel_fres_w_n_e = new JPanel();
      this.borderLayout29 = new BorderLayout();
      this.flowLayout5 = new FlowLayout();
      this.jPanel_fres_c_n = new JPanel();
      this.borderLayout24 = new BorderLayout();
      this.jPanel_fres_c_c = new JPanel();
      this.flowLayout6 = new FlowLayout();
      this.jPanel_fres_c_n_n = new JPanel();
      this.borderLayout30 = new BorderLayout();
      this.jPanel_fres_c_n_c = new JPanel();
      this.jPanel_fres_c_n_s = new JPanel();
      this.borderLayout31 = new BorderLayout();
      this.borderLayout32 = new BorderLayout();
      this.jPanel_fres_c_n_w = new JPanel();
      this.borderLayout33 = new BorderLayout();
      this.jPanel_fres_c_n_e = new JPanel();
      this.borderLayout34 = new BorderLayout();
      this.jLabel_fraun_param = new JLabel();
      this.jLabel_fraun_longOnda = new JLabel();
      this.jSlider_fraun_longOnda = new JSlider(0, 400, 700, 633);
      this.jLabel_fraun_nlongOnda = new JLabel();
      this.jLabel_fraun_distFocal = new JLabel();
      this.jSlider_fraun_distFocal = new JSlider(0, 100, 1000, 1000);
      this.jLabel_fraun_ndistFocal = new JLabel();
      this.jLabel_fraun_objeto = new JLabel();
      this.jRadioButton_fraun_rectang = new JRadioButton();
      this.jRadioButton_fraun_circ = new JRadioButton();
      this.jRadioButton_fraun_rend = new JRadioButton();
      this.group_fraun_tipoobj = new ButtonGroup();
      this.jPanel_fraun_dimObjeto = new JPanel();
      this.flowLayout7 = new FlowLayout();
      this.jLabel_fraun_dimx = new JLabel();
      this.jSlider_fraun_dimx = new JSlider(0, 5, 50, 5);
      this.jLabel_fraun_ndimx = new JLabel();
      this.jLabel_fraun_dimy = new JLabel();
      this.jSlider_fraun_dimy = new JSlider(0, 5, 50, 5);
      this.jLabel_fraun_ndimy = new JLabel();
      this.jLabel_fraun_nObjetos = new JLabel();
      this.jSlider_fraun_nobjetos = new JSlider(0, 1, 10, 1);
      this.jLabel_fraun_numobj = new JLabel();
      this.jLabel_fraun_periodo = new JLabel();
      this.jLabel_fraun_dimimag = new JLabel();
      this.objetoFraunhofer = new PanelObjeto();
      this.imagenFraun = new ImagenFraunhofer();
      this.perfilFraun = new PerfilFraunhofer();
      this.jSplitPane_Fraun = new JSplitPane(1, this.perfilFraun, this.imagenFraun);
      this.jLabel_fraun_umbral = new JLabel();
      this.jSlider_fraun_umbral = new JSlider(0, 1, 100, 100);
      this.jLabel_fraun_numbral = new JLabel();
      this.jLabel_fraun_show = new JLabel();
      this.jRadioButton_fraun_showimg = new JRadioButton();
      this.jRadioButton_fraun_showperf = new JRadioButton();
      this.group_fraun_show = new ButtonGroup();
      this.jLabel_fraun_tipoimg = new JLabel();
      this.jRadioButton_fraun_tipoimg_amp = new JRadioButton();
      this.jRadioButton_fraun_tipoimg_int = new JRadioButton();
      this.jRadioButton_fraun_tipoimg_intlog = new JRadioButton();
      this.group_fraun_tipoimg = new ButtonGroup();
      this.jLabel_fraun_tamdiff = new JLabel();
      this.jLabel_fraun_distmindif = new JLabel();
      this.jLabel_fraun_distnmax = new JLabel();
      this.jLabel_fraun_distnmin = new JLabel();
      this.jToggleButton_fraun_zoom = new JToggleButton("Zoom", false);
      this.jPanel_fraun_showimg = new JPanel();
      this.flowLayout8 = new FlowLayout();
      this.jLabel_fres_param = new JLabel();
      this.jLabel_fres_longOnda = new JLabel();
      this.jSlider_fres_longOnda = new JSlider(0, 400, 700, 633);
      this.jLabel_fres_nlongOnda = new JLabel();
      this.jLabel_fres_distProp = new JLabel();
      this.jSlider_fres_distProp = new JSlider(0, 100, 1000, 100);
      this.jLabel_fres_ndistProp = new JLabel();
      this.jLabel_fres_objeto = new JLabel();
      this.jLabel_fres_nada = new JLabel();
      this.jRadioButton_fres_rectang = new JRadioButton();
      this.jRadioButton_fres_circ = new JRadioButton();
      this.jRadioButton_fres_rend = new JRadioButton();
      this.jRadioButton_fres_semip = new JRadioButton();
      this.group_fres_tipoobj = new ButtonGroup();
      this.jPanel_fres_dimObjeto = new JPanel();
      this.flowLayoutx7 = new FlowLayout();
      this.jLabel_fres_dimx = new JLabel();
      this.jSlider_fres_dimx = new JSlider(0, 5, 50, 5);
      this.jLabel_fres_ndimx = new JLabel();
      this.jLabel_fres_dimy = new JLabel();
      this.jSlider_fres_dimy = new JSlider(0, 5, 50, 5);
      this.jLabel_fres_ndimy = new JLabel();
      this.jButton_fres_calcdif = new JButton();
      this.objetoFresnel = new PanelObjetoFresnel();
      this.imagenFresnel = new ImagenFresnel();
      this.perfilFresnel = new PerfilFresnel();
      this.jSplitPane_fres = new JSplitPane(1, this.perfilFresnel, this.imagenFresnel);
      this.jLabel_fres_umbral = new JLabel();
      this.jSlider_fres_umbral = new JSlider(0, 1, 100, 100);
      this.jLabel_fres_numbral = new JLabel();
      this.jLabel_fres_show = new JLabel();
      this.jRadioButton_fres_showimg = new JRadioButton();
      this.jRadioButton_fres_showperf = new JRadioButton();
      this.group_fres_show = new ButtonGroup();
      this.jLabel_fres_tipoimg = new JLabel();
      this.jRadioButton_fres_tipoimg_amp = new JRadioButton();
      this.jRadioButton_fres_tipoimg_int = new JRadioButton();
      this.jRadioButton_fres_tipoimg_intlog = new JRadioButton();
      this.group_fres_tipoimg = new ButtonGroup();
      this.jLabel_fres_dimimag = new JLabel();
      this.jLabel_fres_tamdiff = new JLabel();
      this.jPanel_fres_showimg = new JPanel();
      this.flowLayoutx8 = new FlowLayout();
      this.jLabel_fres_nada2 = new JLabel();
      this.jScrollPane_teoria = new JScrollPane();
      this.jTextPane_teoria = new JTextPane();

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

      this.actuaParametrosFraunhofer();
      this.actuaParametrosFresnel();
      this.calculaDifraccionFresnel();
      this.actuaImagenFresnel();
      this.carga_info(lang);
   }

   private void jbInit() throws Exception {
      this.setLayout(this.borderLayout1);
      this.jPanel_Base.setLayout(this.borderLayout2);
      this.jPanel_Base.setMinimumSize(new Dimension(750, 550));
      this.jPanel_Base.setPreferredSize(new Dimension(750, 550));
      this.jPanel_Norte.setPreferredSize(new Dimension(750, 10));
      this.jPanel_Norte.setLayout(this.flowLayout1);
      this.jPanel_Sur.setMinimumSize(new Dimension(750, 25));
      this.jPanel_Sur.setPreferredSize(new Dimension(750, 25));
      this.jPanel_Sur.setLayout(this.flowLayout2);
      this.jPanel_Centro.setPreferredSize(new Dimension(750, 500));
      this.jPanel_Centro.setLayout(this.borderLayout3);
      this.flowLayout2.setAlignment(2);
      this.jButton_Salir.setMaximumSize(new Dimension(110, 20));
      this.jButton_Salir.setMinimumSize(new Dimension(110, 20));
      this.jButton_Salir.setPreferredSize(new Dimension(110, 20));
      this.jButton_Salir.setText(this.boton_salir[lang]);
      this.jButton_Salir.addActionListener(new DifraccionApplet_jButton_Salir_actionAdapter(this));
      this.jButton_Acercade.setForeground(Color.darkGray);
      this.jButton_Acercade.setMaximumSize(new Dimension(110, 20));
      this.jButton_Acercade.setMinimumSize(new Dimension(110, 20));
      this.jButton_Acercade.setPreferredSize(new Dimension(110, 20));
      this.jButton_Acercade.setText(this.acerca_etiqueta[0][lang]);
      this.jButton_Acercade.addActionListener(new DifraccionApplet_jButton_Acercade_actionAdapter(this));
      this.jTabbedPane1.setPreferredSize(new Dimension(750, 500));
      this.jPanel_Fresnel.setPreferredSize(new Dimension(750, 465));
      this.jPanel_Fresnel.setLayout(this.borderLayout4);
      this.jPanel_Fraunhofer.setMinimumSize(new Dimension(10, 10));
      this.jPanel_Fraunhofer.setPreferredSize(new Dimension(750, 465));
      this.jPanel_Fraunhofer.setLayout(this.borderLayout5);
      this.jPanel_Info.setLayout(this.borderLayout6);
      this.jPanel_Info.setPreferredSize(new Dimension(750, 465));
      this.jPanel_Fraun_w.setLayout(this.borderLayout7);
      this.jPanel_Fraun_w.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_Fraun_w.setPreferredSize(new Dimension(375, 465));
      this.jPanel_Fraun_c.setLayout(this.borderLayout8);
      this.jPanel_Fraun_c.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_Fraun_c.setPreferredSize(new Dimension(375, 465));
      this.jPanel_Fraun_w_n.setLayout(this.borderLayout9);
      this.jPanel_Fraun_w_n.setBackground(Color.black);
      this.jPanel_Fraun_w_n.setPreferredSize(new Dimension(375, 265));
      this.jPanel_Fraun_w_c.setLayout(this.flowLayout3);
      this.jPanel_Fraun_w_c.setPreferredSize(new Dimension(375, 210));
      this.jPanel_Fraun_w_n_n.setLayout(this.borderLayout10);
      this.jPanel_Fraun_w_n_c.setBackground(Color.black);
      this.jPanel_Fraun_w_n_c.setPreferredSize(new Dimension(256, 256));
      this.jPanel_Fraun_w_n_c.setLayout(this.borderLayout11);
      this.jPanel_Fraun_w_n_s.setLayout(this.borderLayout12);
      this.jPanel_Fraun_w_n_s.setPreferredSize(new Dimension(375, 5));
      this.jPanel_Fraun_w_n_n.setPreferredSize(new Dimension(375, 4));
      this.jPanel_Fraun_w_n_w.setLayout(this.borderLayout13);
      this.jPanel_Fraun_w_n_w.setPreferredSize(new Dimension(60, 256));
      this.jPanel_Fraun_w_n_e.setLayout(this.borderLayout14);
      this.jPanel_Fraun_w_n_e.setPreferredSize(new Dimension(59, 256));
      this.jPanel_Fraun_c_n.setPreferredSize(new Dimension(375, 265));
      this.jPanel_Fraun_c_n.setLayout(this.borderLayout15);
      this.jPanel_fraun_c_c.setLayout(this.flowLayout4);
      this.jPanel_fraun_c_c.setPreferredSize(new Dimension(375, 210));
      this.jPanel_Fraun_c_n_n.setLayout(this.borderLayout16);
      this.jPanel_Fraun_c_n_n.setPreferredSize(new Dimension(375, 5));
      this.jPanel_Fraun_c_n_c.setLayout(this.borderLayout17);
      this.jPanel_Fraun_c_n_c.setBackground(Color.black);
      this.jPanel_Fraun_c_n_c.setPreferredSize(new Dimension(256, 256));
      this.jPanel_Fraun_c_n_s.setLayout(this.borderLayout18);
      this.jPanel_Fraun_c_n_s.setPreferredSize(new Dimension(375, 4));
      this.jPanel_Fraun_c_n_w.setLayout(this.borderLayout19);
      this.jPanel_Fraun_c_n_w.setPreferredSize(new Dimension(59, 256));
      this.jPanel_Fraun_c_n_e.setLayout(this.borderLayout20);
      this.jPanel_Fraun_c_n_e.setPreferredSize(new Dimension(60, 256));
      this.jPanel_fres_w.setLayout(this.borderLayout21);
      this.jPanel_fres_w.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_fres_w.setPreferredSize(new Dimension(375, 465));
      this.jPanel_fres_c.setLayout(this.borderLayout22);
      this.jPanel_fres_c.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_fres_c.setPreferredSize(new Dimension(375, 465));
      this.jPanel_fres_w_n.setLayout(this.borderLayout23);
      this.jPanel_fres_w_n.setPreferredSize(new Dimension(375, 265));
      this.jPanel_fres_w_n.setBackground(Color.black);
      this.jPanel_fres_w_c.setLayout(this.flowLayout5);
      this.jPanel_fres_w_c.setPreferredSize(new Dimension(375, 210));
      this.jPanel_fres_w_n_n.setLayout(this.borderLayout25);
      this.jPanel_fres_w_n_n.setPreferredSize(new Dimension(375, 5));
      this.jPanel_fres_w_n_c.setLayout(this.borderLayout26);
      this.jPanel_fres_w_n_c.setPreferredSize(new Dimension(256, 256));
      this.jPanel_fres_w_n_c.setBackground(Color.black);
      this.jPanel_fres_w_n_s.setLayout(this.borderLayout27);
      this.jPanel_fres_w_n_s.setPreferredSize(new Dimension(375, 4));
      this.jPanel_fres_w_n_w.setLayout(this.borderLayout28);
      this.jPanel_fres_w_n_w.setPreferredSize(new Dimension(60, 256));
      this.jPanel_fres_w_n_e.setLayout(this.borderLayout29);
      this.jPanel_fres_w_n_e.setPreferredSize(new Dimension(59, 256));
      this.jPanel_fres_c_n.setLayout(this.borderLayout24);
      this.jPanel_fres_c_n.setPreferredSize(new Dimension(375, 265));
      this.jPanel_fres_c_c.setLayout(this.flowLayout6);
      this.jPanel_fres_c_c.setPreferredSize(new Dimension(375, 210));
      this.jPanel_fres_c_n_n.setLayout(this.borderLayout30);
      this.jPanel_fres_c_n_n.setPreferredSize(new Dimension(375, 5));
      this.jPanel_fres_c_n_c.setBackground(Color.black);
      this.jPanel_fres_c_n_c.setPreferredSize(new Dimension(256, 256));
      this.jPanel_fres_c_n_c.setBackground(Color.black);
      this.jPanel_fres_c_n_c.setLayout(this.borderLayout32);
      this.jPanel_fres_c_n_s.setLayout(this.borderLayout31);
      this.jPanel_fres_c_n_s.setPreferredSize(new Dimension(375, 4));
      this.jPanel_fres_c_n_w.setLayout(this.borderLayout33);
      this.jPanel_fres_c_n_w.setPreferredSize(new Dimension(59, 256));
      this.jPanel_fres_c_n_e.setLayout(this.borderLayout34);
      this.jPanel_fres_c_n_e.setPreferredSize(new Dimension(60, 256));
      this.jLabel_fraun_param.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_param.setPreferredSize(new Dimension(365, 16));
      this.jLabel_fraun_param.setHorizontalAlignment(0);
      this.jLabel_fraun_param.setText(this.fraun_etiqueta[0][lang]);
      this.jLabel_fraun_longOnda.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_longOnda.setMinimumSize(new Dimension(120, 16));
      this.jLabel_fraun_longOnda.setPreferredSize(new Dimension(120, 16));
      this.jLabel_fraun_longOnda.setText(this.fraun_etiqueta[1][lang]);
      this.jSlider_fraun_longOnda.setMaximum(700);
      this.jSlider_fraun_longOnda.setMinimum(400);
      this.jSlider_fraun_longOnda.setPreferredSize(new Dimension(140, 16));
      this.jSlider_fraun_longOnda.addMouseMotionListener(new DifraccionApplet_jSlider_fraun_longOnda_mouseMotionAdapter(this));
      this.jSlider_fraun_longOnda.addMouseListener(new DifraccionApplet_jSlider_fraun_longOnda_mouseAdapter(this));
      this.jSlider_fraun_longOnda.addKeyListener(new DifraccionApplet_jSlider_fraun_longOnda_keyAdapter(this));
      this.jLabel_fraun_nlongOnda.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_nlongOnda.setMinimumSize(new Dimension(50, 16));
      this.jLabel_fraun_nlongOnda.setPreferredSize(new Dimension(60, 16));
      this.jLabel_fraun_nlongOnda.setText("400 nm");
      this.jLabel_fraun_distFocal.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_distFocal.setMinimumSize(new Dimension(120, 16));
      this.jLabel_fraun_distFocal.setPreferredSize(new Dimension(120, 16));
      this.jLabel_fraun_distFocal.setText(this.fraun_etiqueta[2][lang]);
      this.jSlider_fraun_distFocal.setMaximum(1000);
      this.jSlider_fraun_distFocal.setMinimum(500);
      this.jSlider_fraun_distFocal.setPreferredSize(new Dimension(140, 16));
      this.jSlider_fraun_distFocal.addMouseMotionListener(new DifraccionApplet_jSlider_fraun_distFocal_mouseMotionAdapter(this));
      this.jSlider_fraun_distFocal.addMouseListener(new DifraccionApplet_jSlider_fraun_distFocal_mouseAdapter(this));
      this.jSlider_fraun_distFocal.addKeyListener(new DifraccionApplet_jSlider_fraun_distFocal_keyAdapter(this));
      this.jLabel_fraun_ndistFocal.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_ndistFocal.setMinimumSize(new Dimension(50, 16));
      this.jLabel_fraun_ndistFocal.setPreferredSize(new Dimension(60, 16));
      this.jLabel_fraun_ndistFocal.setText("100 mm");
      this.jLabel_fraun_objeto.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_objeto.setMinimumSize(new Dimension(80, 16));
      this.jLabel_fraun_objeto.setPreferredSize(new Dimension(80, 16));
      this.jLabel_fraun_objeto.setText(this.fraun_etiqueta[3][lang]);
      this.jRadioButton_fraun_rectang.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fraun_rectang.setText(this.fraun_etiqueta[4][lang]);
      this.jRadioButton_fraun_rectang.setSelected(true);
      this.jRadioButton_fraun_rectang.addActionListener(new DifraccionApplet_jRadioButton_fraun_rectang_actionAdapter(this));
      this.jRadioButton_fraun_circ.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fraun_circ.setPreferredSize(new Dimension(75, 24));
      this.jRadioButton_fraun_circ.setText(this.fraun_etiqueta[5][lang]);
      this.jRadioButton_fraun_circ.addActionListener(new DifraccionApplet_jRadioButton_fraun_circ_actionAdapter(this));
      this.jRadioButton_fraun_rend.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fraun_rend.setMinimumSize(new Dimension(75, 24));
      this.jRadioButton_fraun_rend.setPreferredSize(new Dimension(75, 24));
      this.jRadioButton_fraun_rend.setText(this.fraun_etiqueta[6][lang]);
      this.jRadioButton_fraun_rend.addActionListener(new DifraccionApplet_jRadioButton_fraun_rend_actionAdapter(this));
      this.jPanel_fraun_dimObjeto.setPreferredSize(new Dimension(375, 50));
      this.jPanel_fraun_dimObjeto.setLayout(this.flowLayout7);
      this.jLabel_fraun_dimx.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_dimx.setMinimumSize(new Dimension(120, 16));
      this.jLabel_fraun_dimx.setPreferredSize(new Dimension(120, 16));
      this.jLabel_fraun_dimx.setText(this.fraun_etiqueta[7][lang]);
      this.jSlider_fraun_dimx.setMaximum(30);
      this.jSlider_fraun_dimx.setMinimum(5);
      this.jSlider_fraun_dimx.setPreferredSize(new Dimension(150, 16));
      this.jSlider_fraun_dimx.addMouseMotionListener(new DifraccionApplet_jSlider_fraun_dimx_mouseMotionAdapter(this));
      this.jSlider_fraun_dimx.addMouseListener(new DifraccionApplet_jSlider_fraun_dimx_mouseAdapter(this));
      this.jSlider_fraun_dimx.addKeyListener(new DifraccionApplet_jSlider_fraun_dimx_keyAdapter(this));
      this.jLabel_fraun_ndimx.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_ndimx.setMinimumSize(new Dimension(50, 16));
      this.jLabel_fraun_ndimx.setPreferredSize(new Dimension(50, 16));
      this.jLabel_fraun_ndimx.setText("0.5 mm");
      this.jLabel_fraun_dimy.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_dimy.setMinimumSize(new Dimension(120, 16));
      this.jLabel_fraun_dimy.setPreferredSize(new Dimension(120, 16));
      this.jLabel_fraun_dimy.setText(this.fraun_etiqueta[8][lang]);
      this.jSlider_fraun_dimy.setMaximum(30);
      this.jSlider_fraun_dimy.setMinimum(5);
      this.jSlider_fraun_dimy.setPreferredSize(new Dimension(150, 16));
      this.jSlider_fraun_dimy.addMouseMotionListener(new DifraccionApplet_jSlider_fraun_dimy_mouseMotionAdapter(this));
      this.jSlider_fraun_dimy.addMouseListener(new DifraccionApplet_jSlider_fraun_dimy_mouseAdapter(this));
      this.jSlider_fraun_dimy.addKeyListener(new DifraccionApplet_jSlider_fraun_dimy_keyAdapter(this));
      this.jLabel_fraun_ndimy.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_ndimy.setPreferredSize(new Dimension(50, 16));
      this.jLabel_fraun_ndimy.setText("0.5 mm");
      this.jLabel_fraun_nObjetos.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_nObjetos.setMinimumSize(new Dimension(120, 16));
      this.jLabel_fraun_nObjetos.setPreferredSize(new Dimension(120, 16));
      this.jLabel_fraun_nObjetos.setText(this.fraun_etiqueta[9][lang]);
      this.jSlider_fraun_nobjetos.setMaximum(10);
      this.jSlider_fraun_nobjetos.setMinimum(1);
      this.jSlider_fraun_nobjetos.setPreferredSize(new Dimension(150, 16));
      this.jSlider_fraun_nobjetos.addKeyListener(new DifraccionApplet_jSlider_fraun_nobjetos_keyAdapter(this));
      this.jSlider_fraun_nobjetos.addMouseMotionListener(new DifraccionApplet_jSlider_fraun_nobjetos_mouseMotionAdapter(this));
      this.jSlider_fraun_nobjetos.addMouseListener(new DifraccionApplet_jSlider_fraun_nobjetos_mouseAdapter(this));
      this.jLabel_fraun_numobj.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_numobj.setMaximumSize(new Dimension(50, 16));
      this.jLabel_fraun_numobj.setMinimumSize(new Dimension(50, 16));
      this.jLabel_fraun_numobj.setPreferredSize(new Dimension(50, 16));
      this.jLabel_fraun_numobj.setText("1");
      this.jLabel_fraun_periodo.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_periodo.setMaximumSize(new Dimension(375, 16));
      this.jLabel_fraun_periodo.setMinimumSize(new Dimension(100, 16));
      this.jLabel_fraun_periodo.setPreferredSize(new Dimension(300, 16));
      this.jLabel_fraun_periodo.setText(this.fraun_etiqueta[10][lang] + " xx mm");
      this.jLabel_fraun_dimimag.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_dimimag.setMaximumSize(new Dimension(375, 16));
      this.jLabel_fraun_dimimag.setMinimumSize(new Dimension(300, 16));
      this.jLabel_fraun_dimimag.setPreferredSize(new Dimension(300, 16));
      this.jLabel_fraun_dimimag.setText(this.fraun_etiqueta[11][lang] + " xx mm");
      this.objetoFraunhofer.setBackground(Color.black);
      this.jSplitPane_Fraun.setBackground(Color.black);
      this.jSplitPane_Fraun.setContinuousLayout(true);
      this.jSplitPane_Fraun.setOneTouchExpandable(false);
      this.jSplitPane_Fraun.setDividerLocation(0);
      this.jSplitPane_Fraun.setDividerSize(0);
      this.jLabel_fraun_umbral.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_umbral.setMinimumSize(new Dimension(80, 16));
      this.jLabel_fraun_umbral.setPreferredSize(new Dimension(80, 16));
      this.jLabel_fraun_umbral.setText(this.fraun_etiqueta[12][lang]);
      this.jSlider_fraun_umbral.setPreferredSize(new Dimension(100, 16));
      this.jSlider_fraun_umbral.addKeyListener(new DifraccionApplet_jSlider_fraun_umbral_keyAdapter(this));
      this.jSlider_fraun_umbral.addMouseMotionListener(new DifraccionApplet_jSlider_fraun_umbral_mouseMotionAdapter(this));
      this.jSlider_fraun_umbral.addMouseListener(new DifraccionApplet_jSlider_fraun_umbral_mouseAdapter(this));
      this.jLabel_fraun_numbral.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_numbral.setMinimumSize(new Dimension(50, 16));
      this.jLabel_fraun_numbral.setPreferredSize(new Dimension(50, 16));
      this.jLabel_fraun_numbral.setText("100 %");
      this.jLabel_fraun_show.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_show.setMinimumSize(new Dimension(55, 16));
      this.jLabel_fraun_show.setPreferredSize(new Dimension(55, 16));
      this.jLabel_fraun_show.setText(this.fraun_etiqueta[13][lang]);
      this.jRadioButton_fraun_showimg.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fraun_showimg.setMaximumSize(new Dimension(95, 24));
      this.jRadioButton_fraun_showimg.setMinimumSize(new Dimension(75, 24));
      this.jRadioButton_fraun_showimg.setPreferredSize(new Dimension(75, 24));
      this.jRadioButton_fraun_showimg.setText(this.fraun_etiqueta[14][lang]);
      this.jRadioButton_fraun_showimg.setSelected(true);
      this.jRadioButton_fraun_showimg.addActionListener(new DifraccionApplet_jRadioButton_fraun_showimg_actionAdapter(this));
      this.jRadioButton_fraun_showperf.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fraun_showperf.setMinimumSize(new Dimension(130, 24));
      this.jRadioButton_fraun_showperf.setPreferredSize(new Dimension(130, 24));
      this.jRadioButton_fraun_showperf.setText(this.fraun_etiqueta[15][lang]);
      this.jRadioButton_fraun_showperf.addActionListener(new DifraccionApplet_jRadioButton_fraun_showperf_actionAdapter(this));
      this.jLabel_fraun_tipoimg.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_tipoimg.setMaximumSize(new Dimension(75, 16));
      this.jLabel_fraun_tipoimg.setMinimumSize(new Dimension(75, 16));
      this.jLabel_fraun_tipoimg.setPreferredSize(new Dimension(50, 16));
      this.jLabel_fraun_tipoimg.setText(this.fraun_etiqueta[14][lang] + ":");
      this.jRadioButton_fraun_tipoimg_amp.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fraun_tipoimg_amp.setText(this.fraun_etiqueta[16][lang]);
      this.jRadioButton_fraun_tipoimg_amp.addActionListener(new DifraccionApplet_jRadioButton_fraun_tipoimg_amp_actionAdapter(this));
      this.jRadioButton_fraun_tipoimg_int.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fraun_tipoimg_int.setText(this.fraun_etiqueta[17][lang]);
      this.jRadioButton_fraun_tipoimg_int.setSelected(true);
      this.jRadioButton_fraun_tipoimg_int.addActionListener(new DifraccionApplet_jRadioButton_fraun_tipoimg_int_actionAdapter(this));
      this.jRadioButton_fraun_tipoimg_intlog.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fraun_tipoimg_intlog.setText(this.fraun_etiqueta[18][lang]);
      this.jRadioButton_fraun_tipoimg_intlog.addActionListener(new DifraccionApplet_jRadioButton_fraun_tipoimg_intlog_actionAdapter(this));
      this.jLabel_fraun_tamdiff.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_tamdiff.setMinimumSize(new Dimension(300, 16));
      this.jLabel_fraun_tamdiff.setPreferredSize(new Dimension(300, 16));
      this.jLabel_fraun_tamdiff.setText(this.fraun_etiqueta[19][lang] + " xx mm");
      this.jLabel_fraun_distmindif.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_distmindif.setMinimumSize(new Dimension(300, 16));
      this.jLabel_fraun_distmindif.setPreferredSize(new Dimension(300, 16));
      this.jLabel_fraun_distmindif.setText(this.fraun_etiqueta[20][lang] + " xx mm");
      this.jLabel_fraun_distnmax.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_distnmax.setMinimumSize(new Dimension(300, 16));
      this.jLabel_fraun_distnmax.setPreferredSize(new Dimension(300, 16));
      this.jLabel_fraun_distnmax.setText(this.fraun_etiqueta[21][lang] + " xx mm");
      this.jLabel_fraun_distnmin.setForeground(new Color(102, 102, 153));
      this.jLabel_fraun_distnmin.setMinimumSize(new Dimension(300, 16));
      this.jLabel_fraun_distnmin.setPreferredSize(new Dimension(300, 16));
      this.jLabel_fraun_distnmin.setText(this.fraun_etiqueta[22][lang] + " xx mm");
      this.jToggleButton_fraun_zoom.setForeground(new Color(102, 102, 153));
      this.jToggleButton_fraun_zoom.setMinimumSize(new Dimension(65, 15));
      this.jToggleButton_fraun_zoom.setPreferredSize(new Dimension(70, 15));
      this.jToggleButton_fraun_zoom.setText("Zoom");
      this.jToggleButton_fraun_zoom.addActionListener(new DifraccionApplet_jToggleButton_fraun_zoom_actionAdapter(this));
      this.jPanel_fraun_showimg.setLayout(this.flowLayout8);
      this.jPanel_fraun_showimg.setMinimumSize(new Dimension(375, 30));
      this.jPanel_fraun_showimg.setPreferredSize(new Dimension(375, 30));
      this.jLabel_fres_param.setForeground(new Color(102, 102, 153));
      this.jLabel_fres_param.setPreferredSize(new Dimension(365, 16));
      this.jLabel_fres_param.setHorizontalAlignment(0);
      this.jLabel_fres_param.setText(this.fraun_etiqueta[0][lang]);
      this.jLabel_fres_longOnda.setForeground(new Color(102, 102, 153));
      this.jLabel_fres_longOnda.setMinimumSize(new Dimension(120, 16));
      this.jLabel_fres_longOnda.setPreferredSize(new Dimension(120, 16));
      this.jLabel_fres_longOnda.setText(this.fraun_etiqueta[1][lang]);
      this.jSlider_fres_longOnda.setMaximum(700);
      this.jSlider_fres_longOnda.setMinimum(400);
      this.jSlider_fres_longOnda.setPreferredSize(new Dimension(140, 16));
      this.jSlider_fres_longOnda.addMouseMotionListener(new DifraccionApplet_jSlider_fres_longOnda_mouseMotionAdapter(this));
      this.jSlider_fres_longOnda.addMouseListener(new DifraccionApplet_jSlider_fres_longOnda_mouseAdapter(this));
      this.jSlider_fres_longOnda.addKeyListener(new DifraccionApplet_jSlider_fres_longOnda_keyAdapter(this));
      this.jLabel_fres_nlongOnda.setForeground(new Color(102, 102, 153));
      this.jLabel_fres_nlongOnda.setMinimumSize(new Dimension(50, 16));
      this.jLabel_fres_nlongOnda.setPreferredSize(new Dimension(60, 16));
      this.jLabel_fres_nlongOnda.setText("400 nm");
      this.jLabel_fres_distProp.setForeground(new Color(102, 102, 153));
      this.jLabel_fres_distProp.setMinimumSize(new Dimension(120, 16));
      this.jLabel_fres_distProp.setPreferredSize(new Dimension(120, 16));
      this.jLabel_fres_distProp.setText(this.fres_etiqueta[1][lang]);
      this.jSlider_fres_distProp.setMaximum(1000);
      this.jSlider_fres_distProp.setMinimum(100);
      this.jSlider_fres_distProp.setPreferredSize(new Dimension(140, 16));
      this.jSlider_fres_distProp.addMouseMotionListener(new DifraccionApplet_jSlider_fres_distProp_mouseMotionAdapter(this));
      this.jSlider_fres_distProp.addMouseListener(new DifraccionApplet_jSlider_fres_distProp_mouseAdapter(this));
      this.jSlider_fres_distProp.addKeyListener(new DifraccionApplet_jSlider_fres_distProp_keyAdapter(this));
      this.jLabel_fres_ndistProp.setForeground(new Color(102, 102, 153));
      this.jLabel_fres_ndistProp.setMinimumSize(new Dimension(50, 16));
      this.jLabel_fres_ndistProp.setPreferredSize(new Dimension(60, 16));
      this.jLabel_fres_ndistProp.setText("100 mm");
      this.jLabel_fres_objeto.setForeground(new Color(102, 102, 153));
      this.jLabel_fres_objeto.setMinimumSize(new Dimension(80, 16));
      this.jLabel_fres_objeto.setPreferredSize(new Dimension(80, 16));
      this.jLabel_fres_objeto.setText(this.fraun_etiqueta[3][lang]);
      this.jRadioButton_fres_rectang.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fres_rectang.setPreferredSize(new Dimension(120, 24));
      this.jRadioButton_fres_rectang.setText(this.fraun_etiqueta[4][lang]);
      this.jRadioButton_fres_rectang.setSelected(true);
      this.jRadioButton_fres_rectang.addActionListener(new DifraccionApplet_jRadioButton_fres_rectang_actionAdapter(this));
      this.jRadioButton_fres_circ.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fres_circ.setPreferredSize(new Dimension(120, 24));
      this.jRadioButton_fres_circ.setText(this.fraun_etiqueta[5][lang]);
      this.jRadioButton_fres_circ.addActionListener(new DifraccionApplet_jRadioButton_fres_circ_actionAdapter(this));
      this.jRadioButton_fres_rend.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fres_rend.setPreferredSize(new Dimension(120, 24));
      this.jRadioButton_fres_rend.setText(this.fraun_etiqueta[6][lang]);
      this.jRadioButton_fres_rend.addActionListener(new DifraccionApplet_jRadioButton_fres_rend_actionAdapter(this));
      this.jRadioButton_fres_semip.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fres_semip.setMinimumSize(new Dimension(75, 24));
      this.jRadioButton_fres_semip.setPreferredSize(new Dimension(120, 24));
      this.jRadioButton_fres_semip.setText(this.fres_etiqueta[0][lang]);
      this.jRadioButton_fres_semip.addActionListener(new DifraccionApplet_jRadioButton_fres_semip_actionAdapter(this));
      this.jLabel_fres_nada.setPreferredSize(new Dimension(80, 0));
      this.jLabel_fres_nada.setText(" ");
      this.jPanel_fres_dimObjeto.setPreferredSize(new Dimension(375, 50));
      this.jPanel_fres_dimObjeto.setLayout(this.flowLayoutx7);
      this.jLabel_fres_dimx.setForeground(new Color(102, 102, 153));
      this.jLabel_fres_dimx.setMinimumSize(new Dimension(120, 16));
      this.jLabel_fres_dimx.setPreferredSize(new Dimension(120, 16));
      this.jLabel_fres_dimx.setText(this.fraun_etiqueta[7][lang]);
      this.jSlider_fres_dimx.setMaximum(30);
      this.jSlider_fres_dimx.setMinimum(5);
      this.jSlider_fres_dimx.setPreferredSize(new Dimension(150, 16));
      this.jSlider_fres_dimx.addMouseMotionListener(new DifraccionApplet_jSlider_fres_dimx_mouseMotionAdapter(this));
      this.jSlider_fres_dimx.addMouseListener(new DifraccionApplet_jSlider_fres_dimx_mouseAdapter(this));
      this.jSlider_fres_dimx.addKeyListener(new DifraccionApplet_jSlider_fres_dimx_keyAdapter(this));
      this.jLabel_fres_ndimx.setForeground(new Color(102, 102, 153));
      this.jLabel_fres_ndimx.setMinimumSize(new Dimension(50, 16));
      this.jLabel_fres_ndimx.setPreferredSize(new Dimension(50, 16));
      this.jLabel_fres_ndimx.setText("0.5 mm");
      this.jLabel_fres_dimy.setForeground(new Color(102, 102, 153));
      this.jLabel_fres_dimy.setMinimumSize(new Dimension(120, 16));
      this.jLabel_fres_dimy.setPreferredSize(new Dimension(120, 16));
      this.jLabel_fres_dimy.setText(this.fraun_etiqueta[8][lang]);
      this.jSlider_fres_dimy.setMaximum(30);
      this.jSlider_fres_dimy.setMinimum(5);
      this.jSlider_fres_dimy.setPreferredSize(new Dimension(150, 16));
      this.jSlider_fres_dimy.addMouseMotionListener(new DifraccionApplet_jSlider_fres_dimy_mouseMotionAdapter(this));
      this.jSlider_fres_dimy.addMouseListener(new DifraccionApplet_jSlider_fres_dimy_mouseAdapter(this));
      this.jSlider_fres_dimy.addKeyListener(new DifraccionApplet_jSlider_fres_dimy_keyAdapter(this));
      this.jLabel_fres_ndimy.setForeground(new Color(102, 102, 153));
      this.jLabel_fres_ndimy.setPreferredSize(new Dimension(50, 16));
      this.jLabel_fres_ndimy.setText("0.5 mm");
      this.jButton_fres_calcdif.setPreferredSize(new Dimension(150, 26));
      this.jButton_fres_calcdif.setText(this.fres_etiqueta[2][lang]);
      this.jButton_fres_calcdif.addActionListener(new DifraccionApplet_jButton_fres_calcdif_actionAdapter(this));
      this.jSplitPane_fres.setBackground(Color.black);
      this.jSplitPane_fres.setContinuousLayout(true);
      this.jSplitPane_fres.setOneTouchExpandable(false);
      this.jSplitPane_fres.setDividerLocation(0);
      this.jSplitPane_fres.setDividerSize(0);
      this.objetoFresnel.setBackground(Color.black);
      this.jLabel_fres_dimimag.setForeground(new Color(102, 102, 153));
      this.jLabel_fres_dimimag.setMaximumSize(new Dimension(375, 16));
      this.jLabel_fres_dimimag.setMinimumSize(new Dimension(300, 16));
      this.jLabel_fres_dimimag.setPreferredSize(new Dimension(300, 16));
      this.jLabel_fres_dimimag.setText(this.fraun_etiqueta[11][lang] + " xx mm");
      this.jLabel_fres_umbral.setForeground(new Color(102, 102, 153));
      this.jLabel_fres_umbral.setMinimumSize(new Dimension(80, 16));
      this.jLabel_fres_umbral.setPreferredSize(new Dimension(80, 16));
      this.jLabel_fres_umbral.setText(this.fraun_etiqueta[12][lang]);
      this.jSlider_fres_umbral.setPreferredSize(new Dimension(100, 16));
      this.jSlider_fres_umbral.addKeyListener(new DifraccionApplet_jSlider_fres_umbral_keyAdapter(this));
      this.jSlider_fres_umbral.addMouseMotionListener(new DifraccionApplet_jSlider_fres_umbral_mouseMotionAdapter(this));
      this.jSlider_fres_umbral.addMouseListener(new DifraccionApplet_jSlider_fres_umbral_mouseAdapter(this));
      this.jLabel_fres_numbral.setForeground(new Color(102, 102, 153));
      this.jLabel_fres_numbral.setMinimumSize(new Dimension(50, 16));
      this.jLabel_fres_numbral.setPreferredSize(new Dimension(50, 16));
      this.jLabel_fres_numbral.setText("100 %");
      this.jLabel_fres_show.setForeground(new Color(102, 102, 153));
      this.jLabel_fres_show.setMinimumSize(new Dimension(75, 16));
      this.jLabel_fres_show.setPreferredSize(new Dimension(75, 16));
      this.jLabel_fres_show.setText(this.fraun_etiqueta[13][lang]);
      this.jRadioButton_fres_showimg.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fres_showimg.setMaximumSize(new Dimension(95, 24));
      this.jRadioButton_fres_showimg.setMinimumSize(new Dimension(75, 24));
      this.jRadioButton_fres_showimg.setPreferredSize(new Dimension(75, 24));
      this.jRadioButton_fres_showimg.setText(this.fraun_etiqueta[14][lang]);
      this.jRadioButton_fres_showimg.setSelected(true);
      this.jRadioButton_fres_showimg.addActionListener(new DifraccionApplet_jRadioButton_fres_showimg_actionAdapter(this));
      this.jRadioButton_fres_showperf.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fres_showperf.setMinimumSize(new Dimension(130, 24));
      this.jRadioButton_fres_showperf.setPreferredSize(new Dimension(130, 24));
      this.jRadioButton_fres_showperf.setText(this.fraun_etiqueta[15][lang]);
      this.jRadioButton_fres_showperf.addActionListener(new DifraccionApplet_jRadioButton_fres_showperf_actionAdapter(this));
      this.jLabel_fres_tipoimg.setForeground(new Color(102, 102, 153));
      this.jLabel_fres_tipoimg.setMaximumSize(new Dimension(75, 16));
      this.jLabel_fres_tipoimg.setMinimumSize(new Dimension(50, 16));
      this.jLabel_fres_tipoimg.setPreferredSize(new Dimension(50, 16));
      this.jLabel_fres_tipoimg.setText(this.fraun_etiqueta[14][lang] + ":");
      this.jRadioButton_fres_tipoimg_amp.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fres_tipoimg_amp.setText(this.fraun_etiqueta[16][lang]);
      this.jRadioButton_fres_tipoimg_amp.addActionListener(new DifraccionApplet_jRadioButton_fres_tipoimg_amp_actionAdapter(this));
      this.jRadioButton_fres_tipoimg_int.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fres_tipoimg_int.setText(this.fraun_etiqueta[17][lang]);
      this.jRadioButton_fres_tipoimg_int.setSelected(true);
      this.jRadioButton_fres_tipoimg_int.addActionListener(new DifraccionApplet_jRadioButton_fres_tipoimg_int_actionAdapter(this));
      this.jRadioButton_fres_tipoimg_intlog.setForeground(new Color(102, 102, 153));
      this.jRadioButton_fres_tipoimg_intlog.setText(this.fraun_etiqueta[18][lang]);
      this.jRadioButton_fres_tipoimg_intlog.addActionListener(new DifraccionApplet_jRadioButton_fres_tipoimg_intlog_actionAdapter(this));
      this.jLabel_fres_tamdiff.setForeground(new Color(102, 102, 153));
      this.jLabel_fres_tamdiff.setMinimumSize(new Dimension(300, 16));
      this.jLabel_fres_tamdiff.setPreferredSize(new Dimension(300, 16));
      this.jLabel_fres_tamdiff.setText(this.fraun_etiqueta[19][lang] + " xx mm");
      this.jPanel_fres_showimg.setLayout(this.flowLayoutx8);
      this.jPanel_fres_showimg.setMinimumSize(new Dimension(375, 30));
      this.jPanel_fres_showimg.setPreferredSize(new Dimension(375, 30));
      this.jLabel_fres_nada2.setPreferredSize(new Dimension(350, 16));
      this.jLabel_fres_nada2.setText(" ");
      this.jTextPane_teoria.setBackground(new Color(204, 204, 204));
      this.jTextPane_teoria.setText("");
      this.add(this.jPanel_Base, "Center");
      this.jPanel_Base.add(this.jPanel_Norte, "North");
      this.jPanel_Base.add(this.jPanel_Centro, "Center");
      this.jPanel_Centro.add(this.jTabbedPane1, "Center");
      this.jTabbedPane1.add(this.jPanel_Fresnel, "Fresnel");
      this.jPanel_Fresnel.add(this.jPanel_fres_w, "West");
      this.jTabbedPane1.add(this.jPanel_Fraunhofer, "Fraunhofer");
      this.jPanel_Fraunhofer.add(this.jPanel_Fraun_w, "West");
      this.jPanel_Fraun_w.add(this.jPanel_Fraun_w_n, "North");
      this.jTabbedPane1.add(this.jPanel_Info, this.info_etiqueta[lang]);
      this.jPanel_Info.add(this.jScrollPane_teoria, "Center");
      this.jPanel_Base.add(this.jPanel_Sur, "South");
      this.jPanel_Sur.add(this.jButton_Acercade, (Object)null);
      this.jPanel_Sur.add(this.jButton_Salir, (Object)null);
      this.jPanel_Fraunhofer.add(this.jPanel_Fraun_c, "Center");
      this.jPanel_Fraun_c.add(this.jPanel_Fraun_c_n, "North");
      this.jPanel_Fraun_w.add(this.jPanel_Fraun_w_c, "Center");
      this.jPanel_Fraun_w_c.add(this.jLabel_fraun_param, (Object)null);
      this.jPanel_Fraun_w_n.add(this.jPanel_Fraun_w_n_n, "South");
      this.jPanel_Fraun_w_n.add(this.jPanel_Fraun_w_n_c, "Center");
      this.jPanel_Fraun_w_n.add(this.jPanel_Fraun_w_n_s, "North");
      this.jPanel_Fraun_w_n.add(this.jPanel_Fraun_w_n_w, "West");
      this.jPanel_Fraun_w_n.add(this.jPanel_Fraun_w_n_e, "East");
      this.jPanel_Fraun_c.add(this.jPanel_fraun_c_c, "Center");
      this.jPanel_Fraun_c_n.add(this.jPanel_Fraun_c_n_n, "North");
      this.jPanel_Fraun_c_n.add(this.jPanel_Fraun_c_n_c, "Center");
      this.jPanel_Fraun_c_n_c.add(this.jSplitPane_Fraun, "Center");
      this.jPanel_Fraun_c_n.add(this.jPanel_Fraun_c_n_s, "South");
      this.jPanel_Fraun_c_n.add(this.jPanel_Fraun_c_n_w, "West");
      this.jPanel_Fraun_c_n.add(this.jPanel_Fraun_c_n_e, "East");
      this.jPanel_Fresnel.add(this.jPanel_fres_c, "Center");
      this.jPanel_fres_w.add(this.jPanel_fres_w_n, "North");
      this.jPanel_fres_w.add(this.jPanel_fres_w_c, "Center");
      this.jPanel_fres_w_n.add(this.jPanel_fres_w_n_n, "North");
      this.jPanel_fres_w_n.add(this.jPanel_fres_w_n_c, "Center");
      this.jPanel_fres_w_n.add(this.jPanel_fres_w_n_s, "South");
      this.jPanel_fres_w_n.add(this.jPanel_fres_w_n_w, "West");
      this.jPanel_fres_w_n.add(this.jPanel_fres_w_n_e, "East");
      this.jPanel_fres_c.add(this.jPanel_fres_c_n, "North");
      this.jPanel_fres_c.add(this.jPanel_fres_c_c, "Center");
      this.jPanel_fres_c_n.add(this.jPanel_fres_c_n_n, "North");
      this.jPanel_fres_c_n.add(this.jPanel_fres_c_n_c, "Center");
      this.jPanel_fres_c_n.add(this.jPanel_fres_c_n_s, "South");
      this.jPanel_fres_c_n.add(this.jPanel_fres_c_n_w, "West");
      this.jPanel_fres_c_n.add(this.jPanel_fres_c_n_e, "East");
      this.jPanel_Fraun_w_c.add(this.jLabel_fraun_longOnda, (Object)null);
      this.jPanel_Fraun_w_c.add(this.jSlider_fraun_longOnda, (Object)null);
      this.jPanel_Fraun_w_c.add(this.jLabel_fraun_nlongOnda, (Object)null);
      this.jPanel_Fraun_w_c.add(this.jLabel_fraun_distFocal, (Object)null);
      this.jPanel_Fraun_w_c.add(this.jSlider_fraun_distFocal, (Object)null);
      this.jPanel_Fraun_w_c.add(this.jLabel_fraun_ndistFocal, (Object)null);
      this.jPanel_Fraun_w_c.add(this.jLabel_fraun_objeto, (Object)null);
      this.jPanel_Fraun_w_c.add(this.jRadioButton_fraun_rectang, (Object)null);
      this.jPanel_Fraun_w_c.add(this.jRadioButton_fraun_circ, (Object)null);
      this.jPanel_Fraun_w_c.add(this.jRadioButton_fraun_rend, (Object)null);
      this.group_fraun_tipoobj.add(this.jRadioButton_fraun_rectang);
      this.group_fraun_tipoobj.add(this.jRadioButton_fraun_circ);
      this.group_fraun_tipoobj.add(this.jRadioButton_fraun_rend);
      this.jPanel_Fraun_w_c.add(this.jPanel_fraun_dimObjeto, (Object)null);
      this.jPanel_fraun_dimObjeto.add(this.jLabel_fraun_dimx, (Object)null);
      this.jPanel_fraun_dimObjeto.add(this.jSlider_fraun_dimx, (Object)null);
      this.jPanel_fraun_dimObjeto.add(this.jLabel_fraun_ndimx, (Object)null);
      this.jPanel_fraun_dimObjeto.add(this.jLabel_fraun_dimy, (Object)null);
      this.jPanel_fraun_dimObjeto.add(this.jSlider_fraun_dimy, (Object)null);
      this.jPanel_fraun_dimObjeto.add(this.jLabel_fraun_ndimy, (Object)null);
      this.jPanel_Fraun_w_c.add(this.jLabel_fraun_nObjetos, (Object)null);
      this.jPanel_Fraun_w_c.add(this.jSlider_fraun_nobjetos, (Object)null);
      this.jPanel_Fraun_w_c.add(this.jLabel_fraun_numobj, (Object)null);
      this.jPanel_fraun_c_c.add(this.jLabel_fraun_periodo, (Object)null);
      this.jPanel_fraun_c_c.add(this.jLabel_fraun_dimimag, (Object)null);
      this.jPanel_fraun_c_c.add(this.jLabel_fraun_tamdiff, (Object)null);
      this.jPanel_fraun_c_c.add(this.jLabel_fraun_distmindif, (Object)null);
      this.jPanel_fraun_c_c.add(this.jLabel_fraun_distnmax, (Object)null);
      this.jPanel_fraun_c_c.add(this.jLabel_fraun_distnmin, (Object)null);
      this.jPanel_fraun_c_c.add(this.jPanel_fraun_showimg, (Object)null);
      this.jPanel_fraun_showimg.add(this.jLabel_fraun_show, (Object)null);
      this.jPanel_Fraun_w_n_c.add(this.objetoFraunhofer, (Object)null);
      this.jPanel_fraun_showimg.add(this.jRadioButton_fraun_showimg, (Object)null);
      this.jPanel_fraun_showimg.add(this.jRadioButton_fraun_showperf, (Object)null);
      this.jPanel_fraun_showimg.add(this.jToggleButton_fraun_zoom, (Object)null);
      this.group_fraun_show.add(this.jRadioButton_fraun_showimg);
      this.group_fraun_show.add(this.jRadioButton_fraun_showperf);
      this.jPanel_fraun_c_c.add(this.jLabel_fraun_tipoimg, (Object)null);
      this.jPanel_fraun_c_c.add(this.jRadioButton_fraun_tipoimg_amp, (Object)null);
      this.jPanel_fraun_c_c.add(this.jRadioButton_fraun_tipoimg_int, (Object)null);
      this.jPanel_fraun_c_c.add(this.jRadioButton_fraun_tipoimg_intlog, (Object)null);
      this.group_fraun_tipoimg.add(this.jRadioButton_fraun_tipoimg_amp);
      this.group_fraun_tipoimg.add(this.jRadioButton_fraun_tipoimg_int);
      this.group_fraun_tipoimg.add(this.jRadioButton_fraun_tipoimg_intlog);
      this.jPanel_fraun_c_c.add(this.jLabel_fraun_umbral, (Object)null);
      this.jPanel_fraun_c_c.add(this.jSlider_fraun_umbral, (Object)null);
      this.jPanel_fraun_c_c.add(this.jLabel_fraun_numbral, (Object)null);
      this.jPanel_fres_w_c.add(this.jLabel_fres_param, (Object)null);
      this.jPanel_fres_w_c.add(this.jLabel_fres_longOnda, (Object)null);
      this.jPanel_fres_w_c.add(this.jSlider_fres_longOnda, (Object)null);
      this.jPanel_fres_w_c.add(this.jLabel_fres_nlongOnda, (Object)null);
      this.jPanel_fres_w_c.add(this.jLabel_fres_distProp, (Object)null);
      this.jPanel_fres_w_c.add(this.jSlider_fres_distProp, (Object)null);
      this.jPanel_fres_w_c.add(this.jLabel_fres_ndistProp, (Object)null);
      this.jPanel_fres_w_c.add(this.jLabel_fres_objeto, (Object)null);
      this.jPanel_fres_w_c.add(this.jRadioButton_fres_rectang, (Object)null);
      this.jPanel_fres_w_c.add(this.jRadioButton_fres_circ, (Object)null);
      this.jPanel_fres_w_c.add(this.jLabel_fres_nada, (Object)null);
      this.jPanel_fres_w_c.add(this.jRadioButton_fres_rend, (Object)null);
      this.jPanel_fres_w_c.add(this.jRadioButton_fres_semip, (Object)null);
      this.group_fres_tipoobj.add(this.jRadioButton_fres_rectang);
      this.group_fres_tipoobj.add(this.jRadioButton_fres_circ);
      this.group_fres_tipoobj.add(this.jRadioButton_fres_rend);
      this.group_fres_tipoobj.add(this.jRadioButton_fres_semip);
      this.jPanel_fres_w_c.add(this.jPanel_fres_dimObjeto, (Object)null);
      this.jPanel_fres_dimObjeto.add(this.jLabel_fres_dimx, (Object)null);
      this.jPanel_fres_dimObjeto.add(this.jSlider_fres_dimx, (Object)null);
      this.jPanel_fres_dimObjeto.add(this.jLabel_fres_ndimx, (Object)null);
      this.jPanel_fres_dimObjeto.add(this.jLabel_fres_dimy, (Object)null);
      this.jPanel_fres_dimObjeto.add(this.jSlider_fres_dimy, (Object)null);
      this.jPanel_fres_dimObjeto.add(this.jLabel_fres_ndimy, (Object)null);
      this.jPanel_fres_w_c.add(this.jButton_fres_calcdif, (Object)null);
      this.jPanel_fres_w_n_c.add(this.objetoFresnel, (Object)null);
      this.jPanel_fres_c_n_c.add(this.jSplitPane_fres, "Center");
      this.jPanel_fres_c_c.add(this.jLabel_fres_nada2, (Object)null);
      this.jPanel_fres_c_c.add(this.jLabel_fres_dimimag, (Object)null);
      this.jPanel_fres_c_c.add(this.jLabel_fres_tamdiff, (Object)null);
      this.jPanel_fres_c_c.add(this.jPanel_fres_showimg, (Object)null);
      this.jPanel_fres_showimg.add(this.jLabel_fres_show, (Object)null);
      this.jPanel_fres_showimg.add(this.jRadioButton_fres_showimg, (Object)null);
      this.jPanel_fres_showimg.add(this.jRadioButton_fres_showperf, (Object)null);
      this.group_fres_show.add(this.jRadioButton_fres_showimg);
      this.group_fres_show.add(this.jRadioButton_fres_showperf);
      this.jPanel_fres_c_c.add(this.jLabel_fres_tipoimg, (Object)null);
      this.jPanel_fres_c_c.add(this.jRadioButton_fres_tipoimg_amp, (Object)null);
      this.jPanel_fres_c_c.add(this.jRadioButton_fres_tipoimg_int, (Object)null);
      this.jPanel_fres_c_c.add(this.jRadioButton_fres_tipoimg_intlog, (Object)null);
      this.group_fres_tipoimg.add(this.jRadioButton_fres_tipoimg_amp);
      this.group_fres_tipoimg.add(this.jRadioButton_fres_tipoimg_int);
      this.group_fres_tipoimg.add(this.jRadioButton_fres_tipoimg_intlog);
      this.jPanel_fres_c_c.add(this.jLabel_fres_umbral, (Object)null);
      this.jPanel_fres_c_c.add(this.jSlider_fres_umbral, (Object)null);
      this.jPanel_fres_c_c.add(this.jLabel_fres_numbral, (Object)null);
      this.jScrollPane_teoria.getViewport().add(this.jTextPane_teoria, (Object)null);
      this.jSplitPane_Fraun.setDividerLocation(0);
      this.jSplitPane_fres.setDividerLocation(0);
   }

   public String getAppletInfo() {
      return "Información del applet";
   }

   public String[][] getParameterInfo() {
      String[][] pinfo = new String[][]{{"lengua", "int", ""}};
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

      DifraccionApplet applet = new DifraccionApplet();
      applet.isStandalone = true;
      Frame frame = new Frame();
      frame.setTitle("Applet Difracción");
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

   void jButton_Salir_actionPerformed(ActionEvent e) {
      try {
         System.exit(0);
      } catch (Exception var3) {
         System.out.println("Error al salir");
      }

   }

   void jButton_Acercade_actionPerformed(ActionEvent e) {
      Frame f = new Frame();
      Object[] options = new Object[]{this.acerca_etiqueta[1][lang]};
      ImageIcon icon_joc = null;

      try {
         String st_icon = "jocon.jpg";
         URL url_icon = this.getClass().getResource(st_icon);
         icon_joc = new ImageIcon(url_icon, "Difracción");
      } catch (Exception var7) {
         System.out.println("No carga icono");
      }

      JOptionPane option = new JOptionPane(this.acerca_etiqueta[2][lang], 1, -1, icon_joc, options);
      JDialog dialog = option.createDialog(f, this.acerca_etiqueta[0][lang]);
      dialog.setResizable(false);
      dialog.show();
   }

   void actuaParametrosFraunhofer() {
      double delta_pix = 6.0D;
      double dim_x_max = 3.0D;
      double dist_separa = 0.0D;
      double fraun_tam_dif = 0.0D;
      double fraun_mindif = 0.0D;
      double fraun_mindif_y = 0.0D;
      double fraun_distnmax = 0.0D;
      double fraun_distnmin = 0.0D;
      if (this.tipo_objeto == 0) {
         this.jLabel_fraun_dimx.setText(this.fraun_etiqueta[7][lang]);
         this.jLabel_fraun_dimy.setVisible(true);
         this.jSlider_fraun_dimy.setVisible(true);
         this.jLabel_fraun_ndimy.setVisible(true);
      } else if (this.tipo_objeto == 1) {
         this.jLabel_fraun_dimx.setText(this.fraun_etiqueta[23][lang]);
         this.jLabel_fraun_dimy.setVisible(false);
         this.jSlider_fraun_dimy.setVisible(false);
         this.jLabel_fraun_ndimy.setVisible(false);
      } else {
         this.jLabel_fraun_dimx.setText(this.fraun_etiqueta[7][lang]);
         this.jLabel_fraun_dimy.setVisible(false);
         this.jSlider_fraun_dimy.setVisible(false);
         this.jLabel_fraun_ndimy.setVisible(false);
      }

      this.long_onda = this.jSlider_fraun_longOnda.getValue();
      this.jLabel_fraun_nlongOnda.setText(this.long_onda + " nm");
      this.n_objetos = this.jSlider_fraun_nobjetos.getValue();
      this.jLabel_fraun_numobj.setText(this.n_objetos + "");
      this.dist_focal = (double)this.jSlider_fraun_distFocal.getValue();
      this.jLabel_fraun_ndistFocal.setText((int)this.dist_focal + " mm");
      this.dim_x = (double)this.jSlider_fraun_dimx.getValue() / 10.0D;
      this.jLabel_fraun_ndimx.setText(this.dim_x + " mm");
      this.dim_y = (double)this.jSlider_fraun_dimy.getValue() / 10.0D;
      this.jLabel_fraun_ndimy.setText(this.dim_y + " mm");
      this.objetoFraunhofer.putAtributos(this.tipo_objeto, this.dim_x, this.dim_y, this.n_objetos, this.long_onda);
      this.jPanel_Fraun_w_n.repaint();
      char pto = '.';
      this.df_symb.setDecimalSeparator(pto);
      this.df.setDecimalFormatSymbols(this.df_symb);
      if (this.n_objetos < 5) {
         dist_separa = 2.0D * dim_x_max;
      } else if (this.n_objetos > 4 && this.n_objetos < 9) {
         dist_separa = dim_x_max / 2.0D;
      } else if (this.n_objetos > 8 && this.n_objetos < 11) {
         dist_separa = dim_x_max / 4.0D;
      }

      double semi_periodo = 0.0D;
      if (this.n_objetos == 1) {
         if (this.tipo_objeto == 0) {
            if (this.dim_x > this.dim_y) {
               semi_periodo = (double)this.long_onda * 1.0E-6D * this.dist_focal / (2.0D * this.dim_x);
            } else {
               semi_periodo = (double)this.long_onda * 1.0E-6D * this.dist_focal / (2.0D * this.dim_y);
            }
         } else if (this.tipo_objeto == 1) {
            semi_periodo = (double)this.long_onda * 1.0E-6D * this.dist_focal * 1.22D / (8.0D * this.dim_x);
         } else {
            semi_periodo = (double)this.long_onda * 1.0E-6D * this.dist_focal / (2.0D * this.dim_x);
         }
      } else {
         semi_periodo = (double)this.long_onda * 1.0E-6D * this.dist_focal / (2.0D * (double)this.n_objetos * (dim_x_max + dist_separa));
      }

      double dim_Lx = 256.0D;
      if (semi_periodo * dim_Lx > 7.5D) {
         fraun_tam_dif = 5.0D;
      } else if (semi_periodo * dim_Lx <= 7.5D && semi_periodo * dim_Lx > 5.0D) {
         fraun_tam_dif = 3.0D;
      } else if (semi_periodo * dim_Lx <= 5.0D && semi_periodo * dim_Lx > 2.0D) {
         fraun_tam_dif = 2.5D;
      } else if (semi_periodo * dim_Lx <= 2.0D && semi_periodo * dim_Lx > 1.0D) {
         fraun_tam_dif = 0.5D;
      } else {
         fraun_tam_dif = 0.25D;
      }

      this.fraun_periodo = dist_separa + dim_x_max;
      this.fraun_tam_img = 256.0D / delta_pix;
      if (this.n_objetos > 1) {
         this.jLabel_fraun_periodo.setText(this.fraun_etiqueta[10][lang] + " " + this.fraun_periodo + " mm");
         this.jLabel_fraun_periodo.setEnabled(true);
      } else {
         this.jLabel_fraun_periodo.setText(this.fraun_etiqueta[10][lang] + " " + "-- mm");
         this.jLabel_fraun_periodo.setEnabled(false);
      }

      this.jLabel_fraun_dimimag.setText(this.fraun_etiqueta[11][lang] + " " + this.df.format(this.fraun_tam_img) + " X " + this.df.format(this.fraun_tam_img) + " mm^2");
      this.jLabel_fraun_tamdiff.setText(this.fraun_etiqueta[19][lang] + " " + this.df.format(fraun_tam_dif) + " X " + this.df.format(fraun_tam_dif) + " mm^2");
      if (this.tipo_objeto == 0) {
         fraun_mindif = (double)this.long_onda * 1.0E-6D * this.dist_focal / this.dim_x;
         fraun_mindif_y = (double)this.long_onda * 1.0E-6D * this.dist_focal / this.dim_y;
         this.jLabel_fraun_distmindif.setText(this.fraun_etiqueta[24][lang] + this.df.format(fraun_mindif) + this.fraun_etiqueta[25][lang] + this.df.format(fraun_mindif_y) + " mm");
      } else if (this.tipo_objeto == 1) {
         fraun_mindif = 1.22D * (double)this.long_onda * 1.0E-6D * this.dist_focal / this.dim_x;
         this.jLabel_fraun_distmindif.setText(this.fraun_etiqueta[26][lang] + this.df.format(fraun_mindif) + " mm");
      } else {
         fraun_mindif = (double)this.long_onda * 1.0E-6D * this.dist_focal / this.dim_x;
         this.jLabel_fraun_distmindif.setText(this.fraun_etiqueta[20][lang] + " " + this.df.format(fraun_mindif) + " mm");
      }

      if (this.n_objetos > 1) {
         fraun_distnmax = (double)this.long_onda * 1.0E-6D * this.dist_focal / this.fraun_periodo;
         if (this.n_objetos == 2) {
            fraun_distnmin = fraun_distnmax;
         } else {
            fraun_distnmin = (double)this.long_onda * 1.0E-6D * this.dist_focal / ((double)this.n_objetos * this.fraun_periodo);
         }

         this.jLabel_fraun_distnmax.setText(this.fraun_etiqueta[27][lang] + this.df.format(fraun_distnmax) + " mm");
         this.jLabel_fraun_distnmax.setEnabled(true);
         this.jLabel_fraun_distnmin.setText(this.fraun_etiqueta[28][lang] + this.df.format(fraun_distnmin) + " mm");
         this.jLabel_fraun_distnmin.setEnabled(true);
      } else {
         this.jLabel_fraun_distnmax.setText(this.fraun_etiqueta[27][lang] + "-- mm");
         this.jLabel_fraun_distnmax.setEnabled(false);
         this.jLabel_fraun_distnmin.setText(this.fraun_etiqueta[28][lang] + "-- mm");
         this.jLabel_fraun_distnmin.setEnabled(false);
      }

      if (!this.label_fraun_activaimg) {
         if (fraun_tam_dif < 2.0D * fraun_mindif) {
            this.jToggleButton_fraun_zoom.setEnabled(false);
            this.label_fraun_zoom = false;
         } else {
            this.jToggleButton_fraun_zoom.setEnabled(true);
            if (this.jToggleButton_fraun_zoom.isSelected()) {
               this.label_fraun_zoom = true;
               this.jLabel_fraun_tamdiff.setText(this.fraun_etiqueta[19][lang] + " " + this.df.format(2.0D * fraun_mindif) + " mm");
            } else {
               this.label_fraun_zoom = false;
            }
         }
      } else {
         this.jToggleButton_fraun_zoom.setEnabled(false);
         this.label_fraun_zoom = false;
      }

      if (this.label_fraun_activaimg) {
         this.jRadioButton_fraun_tipoimg_amp.setEnabled(true);
         this.jRadioButton_fraun_tipoimg_int.setEnabled(true);
         this.jRadioButton_fraun_tipoimg_intlog.setEnabled(true);
         if (this.label_fraun_tipoimg == 0) {
            this.jRadioButton_fraun_tipoimg_amp.setSelected(true);
            this.jRadioButton_fraun_tipoimg_int.setSelected(false);
            this.jRadioButton_fraun_tipoimg_intlog.setSelected(false);
            this.jSlider_fraun_umbral.setEnabled(true);
            this.fraun_umbral = (double)this.jSlider_fraun_umbral.getValue() / 100.0D;
         } else if (this.label_fraun_tipoimg == 1) {
            this.jRadioButton_fraun_tipoimg_amp.setSelected(false);
            this.jRadioButton_fraun_tipoimg_int.setSelected(true);
            this.jRadioButton_fraun_tipoimg_intlog.setSelected(false);
            this.jSlider_fraun_umbral.setEnabled(true);
            this.fraun_umbral = (double)this.jSlider_fraun_umbral.getValue() / 100.0D;
         } else {
            this.jRadioButton_fraun_tipoimg_amp.setSelected(false);
            this.jRadioButton_fraun_tipoimg_int.setSelected(false);
            this.jRadioButton_fraun_tipoimg_intlog.setSelected(true);
            this.jSlider_fraun_umbral.setEnabled(false);
            this.jSlider_fraun_umbral.setValue(100);
            this.fraun_umbral = 1.0D;
         }
      } else {
         this.jRadioButton_fraun_tipoimg_amp.setEnabled(false);
         this.jRadioButton_fraun_tipoimg_int.setEnabled(false);
         this.jRadioButton_fraun_tipoimg_intlog.setEnabled(false);
         this.jRadioButton_fraun_tipoimg_amp.setSelected(true);
         this.jSlider_fraun_umbral.setEnabled(false);
         this.jSlider_fraun_umbral.setValue(100);
         this.fraun_umbral = 1.0D;
      }

      this.jLabel_fraun_numbral.setText(this.jSlider_fraun_umbral.getValue() + " %");
      this.imagenFraun.putAtributos(this.tipo_objeto, this.dim_x, this.dim_y, this.n_objetos, this.long_onda, this.dist_focal, this.label_fraun_tipoimg, this.fraun_umbral);
      this.perfilFraun.putAtributos(this.tipo_objeto, this.dim_x, this.dim_y, this.n_objetos, this.long_onda, this.dist_focal, this.label_fraun_zoom);
      this.jPanel_Fraun_c_n.repaint();
   }

   void actuaParametrosFresnel() {
      if (this.tipo_objeto_fresnel == 0) {
         this.jLabel_fres_dimx.setVisible(true);
         this.jSlider_fres_dimx.setVisible(true);
         this.jLabel_fres_ndimx.setVisible(true);
         this.jLabel_fres_dimx.setText(this.fraun_etiqueta[7][lang]);
         this.jLabel_fres_dimy.setVisible(true);
         this.jSlider_fres_dimy.setVisible(true);
         this.jLabel_fres_ndimy.setVisible(true);
      } else if (this.tipo_objeto_fresnel == 1) {
         this.jLabel_fres_dimx.setVisible(true);
         this.jSlider_fres_dimx.setVisible(true);
         this.jLabel_fres_ndimx.setVisible(true);
         this.jLabel_fres_dimx.setText(this.fraun_etiqueta[23][lang]);
         this.jLabel_fres_dimy.setVisible(false);
         this.jSlider_fres_dimy.setVisible(false);
         this.jLabel_fres_ndimy.setVisible(false);
      } else if (this.tipo_objeto_fresnel == 3) {
         this.jLabel_fres_dimx.setVisible(true);
         this.jSlider_fres_dimx.setVisible(true);
         this.jLabel_fres_ndimx.setVisible(true);
         this.jLabel_fres_dimx.setText(this.fraun_etiqueta[7][lang]);
         this.jLabel_fres_dimy.setVisible(false);
         this.jSlider_fres_dimy.setVisible(false);
         this.jLabel_fres_ndimy.setVisible(false);
      } else {
         this.jLabel_fres_dimx.setVisible(false);
         this.jSlider_fres_dimx.setVisible(false);
         this.jLabel_fres_ndimx.setVisible(false);
         this.jLabel_fres_dimy.setVisible(false);
         this.jSlider_fres_dimy.setVisible(false);
         this.jLabel_fres_ndimy.setVisible(false);
      }

      this.long_onda_fres = this.jSlider_fres_longOnda.getValue();
      this.jLabel_fres_nlongOnda.setText(this.long_onda_fres + " nm");
      this.dist_prop = (double)this.jSlider_fres_distProp.getValue();
      this.jLabel_fres_ndistProp.setText((int)this.dist_prop + " mm");
      this.dim_x_fres = (double)this.jSlider_fres_dimx.getValue() / 10.0D;
      this.jLabel_fres_ndimx.setText(this.dim_x_fres + " mm");
      this.dim_y_fres = (double)this.jSlider_fres_dimy.getValue() / 10.0D;
      this.jLabel_fres_ndimy.setText(this.dim_y_fres + " mm");
      this.objetoFresnel.putAtributos(this.tipo_objeto_fresnel, this.dim_x_fres, this.dim_y_fres, this.long_onda_fres);
      this.jPanel_fres_w_n.repaint();
      this.label_fres_calcula = false;
      this.jButton_fres_calcdif.setForeground(Color.red);
      char pto = '.';
      this.df_symb.setDecimalSeparator(pto);
      this.df.setDecimalFormatSymbols(this.df_symb);
      double delta_pix = 42.0D;
      double fres_tam_img = 256.0D / delta_pix;
      double fres_tam_dif = 256.0D / delta_pix;
      this.jLabel_fres_dimimag.setText(this.fraun_etiqueta[11][lang] + " " + this.df.format(fres_tam_img) + " X " + this.df.format(fres_tam_img) + " mm^2");
      this.jLabel_fres_tamdiff.setText(this.fraun_etiqueta[19][lang] + " " + this.df.format(fres_tam_dif) + " X " + this.df.format(fres_tam_dif) + " mm^2");
   }

   void calculaDifraccionFresnel() {
      double delta_pix = 42.0D;
      double pix_x = 1.0D / delta_pix;
      double pix_y = 1.0D / delta_pix;
      this.label_fres_calcula = true;
      this.jButton_fres_calcdif.setForeground(Color.black);

      int i;
      for(i = 0; i < this.numpto_total; ++i) {
         this.matriz_fres_re[i] = 0.0D;
         this.matriz_fres_im[i] = 0.0D;
      }

      int j;
      if (this.tipo_objeto_fresnel == 0) {
         int x_inicio = (int)((double)this.numpto_x * 0.5D - this.dim_x_fres * delta_pix * 0.5D);
         int x_final = x_inicio + (int)(this.dim_x_fres * delta_pix);
         int y_inicio = (int)((double)this.numpto_y * 0.5D - this.dim_y_fres * delta_pix * 0.5D);
         int y_final = y_inicio + (int)(this.dim_y_fres * delta_pix);

         for(i = y_inicio; i < y_final; ++i) {
            for(j = x_inicio; j < x_final; ++j) {
               this.matriz_fres_re[j + i * this.numpto_x] = 1.0D;
            }
         }
      } else if (this.tipo_objeto_fresnel == 1) {
         for(i = 0; i < this.numpto_y; ++i) {
            for(j = 0; j < this.numpto_x; ++j) {
               double x = ((double)j - (double)this.numpto_x / 2.0D) * pix_x;
               double y = ((double)i - (double)this.numpto_y / 2.0D) * pix_y;
               double radio = 2.0D * Math.sqrt(x * x + y * y);
               if (radio < this.dim_x_fres) {
                  this.matriz_fres_re[j + i * this.numpto_x] = 1.0D;
               }
            }
         }
      } else if (this.tipo_objeto_fresnel == 3) {
         i = (int)(this.dim_x_fres * delta_pix);
         j = (int)((double)(this.numpto_x / 2) - this.dim_x_fres * delta_pix * 0.5D);

         for(int i = 0; i < this.numpto_y; ++i) {
            for(int j = j; j < j + i; ++j) {
               this.matriz_fres_re[j + i * this.numpto_x] = 1.0D;
            }
         }
      } else {
         for(i = 0; i < this.numpto_y; ++i) {
            for(j = 0; j < (int)((double)this.numpto_x * 0.5D); ++j) {
               this.matriz_fres_re[j + i * this.numpto_x] = 1.0D;
            }
         }
      }

      if (this.tipo_objeto_fresnel == 2) {
         this.transformadaFresnel.transformadaFresnelSemiplano(this.dist_prop, this.numpto_x, this.numpto_y, this.matriz_fres_re, pix_x, pix_y, (double)this.long_onda_fres);
      } else {
         this.transformadaFresnel.transformadaFresnel(this.dist_prop, this.numpto_x, this.numpto_y, this.matriz_fres_re, this.matriz_fres_im, pix_x, pix_y, (double)this.long_onda_fres);

         for(i = 0; i < this.numpto_total; ++i) {
            this.matriz_fres_re[i] = Math.sqrt(this.matriz_fres_re[i] * this.matriz_fres_re[i] + this.matriz_fres_im[i] * this.matriz_fres_im[i]);
         }
      }

      this.actuaImagenFresnel();
   }

   void actuaImagenFresnel() {
      if (this.label_fres_activaimg) {
         this.jRadioButton_fres_tipoimg_amp.setEnabled(true);
         this.jRadioButton_fres_tipoimg_int.setEnabled(true);
         this.jRadioButton_fres_tipoimg_intlog.setEnabled(true);
         if (this.label_fres_tipoimg == 0) {
            this.jRadioButton_fres_tipoimg_amp.setSelected(true);
            this.jRadioButton_fres_tipoimg_int.setSelected(false);
            this.jRadioButton_fres_tipoimg_intlog.setSelected(false);
            this.jSlider_fres_umbral.setEnabled(true);
            this.fres_umbral = (double)this.jSlider_fres_umbral.getValue() / 100.0D;
         } else if (this.label_fres_tipoimg == 1) {
            this.jRadioButton_fres_tipoimg_amp.setSelected(false);
            this.jRadioButton_fres_tipoimg_int.setSelected(true);
            this.jRadioButton_fres_tipoimg_intlog.setSelected(false);
            this.jSlider_fres_umbral.setEnabled(true);
            this.fres_umbral = (double)this.jSlider_fres_umbral.getValue() / 100.0D;
         } else {
            this.jRadioButton_fres_tipoimg_amp.setSelected(false);
            this.jRadioButton_fres_tipoimg_int.setSelected(false);
            this.jRadioButton_fres_tipoimg_intlog.setSelected(true);
            this.jSlider_fres_umbral.setEnabled(false);
            this.jSlider_fres_umbral.setValue(100);
            this.fres_umbral = 1.0D;
         }
      } else {
         this.jRadioButton_fres_tipoimg_amp.setEnabled(false);
         this.jRadioButton_fres_tipoimg_int.setEnabled(false);
         this.jRadioButton_fres_tipoimg_intlog.setEnabled(false);
         this.jRadioButton_fres_tipoimg_amp.setSelected(true);
         this.jSlider_fres_umbral.setEnabled(false);
         this.jSlider_fres_umbral.setValue(100);
         this.fres_umbral = 1.0D;
      }

      this.jLabel_fres_numbral.setText(this.jSlider_fres_umbral.getValue() + " %");
      this.imagenFresnel.putAtributos(this.dim_x, this.dim_y, this.matriz_fres_re, this.long_onda_fres, this.label_fres_tipoimg, this.fres_umbral);
      this.perfilFresnel.putAtributos(this.numpto_x, this.numpto_y, this.matriz_fres_re, this.long_onda_fres);
      this.jPanel_fres_c_n.repaint();
      this.jPanel_fres_c_n.repaint();
   }

   private void carga_info(int lengua) {
      String s = null;

      try {
         if (lengua == 1) {
            s = "DocA_DifracCa.htm";
         } else if (lengua == 2) {
            s = "DocA_DifracEn.htm";
         } else {
            s = "DocA_DifracEs.htm";
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

   void jRadioButton_fraun_rectang_actionPerformed(ActionEvent e) {
      this.tipo_objeto = 0;
      this.actuaParametrosFraunhofer();
   }

   void jRadioButton_fraun_circ_actionPerformed(ActionEvent e) {
      this.tipo_objeto = 1;
      this.actuaParametrosFraunhofer();
   }

   void jRadioButton_fraun_rend_actionPerformed(ActionEvent e) {
      this.tipo_objeto = 2;
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_longOnda_keyPressed(KeyEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_longOnda_keyTyped(KeyEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_longOnda_mouseClicked(MouseEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_longOnda_mouseDragged(MouseEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jRadioButton_fraun_1obj_actionPerformed(ActionEvent e) {
      this.num_objetos = 0;
      this.actuaParametrosFraunhofer();
   }

   void jRadioButton_fraun_nobj_actionPerformed(ActionEvent e) {
      this.num_objetos = 1;
      this.actuaParametrosFraunhofer();
   }

   void jRadioButton_fraun_red_actionPerformed(ActionEvent e) {
      this.num_objetos = 2;
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_nobjetos_mouseClicked(MouseEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_nobjetos_mouseDragged(MouseEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_nobjetos_keyPressed(KeyEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_nobjetos_keyTyped(KeyEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_distFocal_keyPressed(KeyEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_distFocal_keyTyped(KeyEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_distFocal_mouseClicked(MouseEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_distFocal_mouseDragged(MouseEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_dimx_keyPressed(KeyEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_dimx_keyTyped(KeyEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_dimx_mouseClicked(MouseEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_dimx_mouseDragged(MouseEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_dimy_keyPressed(KeyEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_dimy_keyTyped(KeyEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_dimy_mouseClicked(MouseEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_dimy_mouseDragged(MouseEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_umbral_mouseClicked(MouseEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_umbral_mouseDragged(MouseEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_umbral_keyPressed(KeyEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jSlider_fraun_umbral_keyTyped(KeyEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jRadioButton_fraun_showimg_actionPerformed(ActionEvent e) {
      this.jSplitPane_Fraun.setDividerLocation(0);
      this.label_fraun_activaimg = true;
      this.actuaParametrosFraunhofer();
   }

   void jRadioButton_fraun_showperf_actionPerformed(ActionEvent e) {
      this.jSplitPane_Fraun.setDividerLocation(1.0D);
      this.label_fraun_activaimg = false;
      this.actuaParametrosFraunhofer();
   }

   void jRadioButton_fraun_tipoimg_amp_actionPerformed(ActionEvent e) {
      this.label_fraun_tipoimg = 0;
      this.actuaParametrosFraunhofer();
   }

   void jRadioButton_fraun_tipoimg_int_actionPerformed(ActionEvent e) {
      this.label_fraun_tipoimg = 1;
      this.actuaParametrosFraunhofer();
   }

   void jRadioButton_fraun_tipoimg_intlog_actionPerformed(ActionEvent e) {
      this.label_fraun_tipoimg = 2;
      this.actuaParametrosFraunhofer();
   }

   void jToggleButton_fraun_zoom_actionPerformed(ActionEvent e) {
      this.actuaParametrosFraunhofer();
   }

   void jButton_fres_calcdif_actionPerformed(ActionEvent e) {
      this.calculaDifraccionFresnel();
   }

   void jSlider_fres_longOnda_keyPressed(KeyEvent e) {
      this.actuaParametrosFresnel();
   }

   void jSlider_fres_longOnda_keyTyped(KeyEvent e) {
      this.actuaParametrosFresnel();
   }

   void jSlider_fres_longOnda_mouseClicked(MouseEvent e) {
      this.actuaParametrosFresnel();
   }

   void jSlider_fres_longOnda_mouseDragged(MouseEvent e) {
      this.actuaParametrosFresnel();
   }

   void jRadioButton_fres_rectang_actionPerformed(ActionEvent e) {
      this.tipo_objeto_fresnel = 0;
      this.actuaParametrosFresnel();
   }

   void jRadioButton_fres_circ_actionPerformed(ActionEvent e) {
      this.tipo_objeto_fresnel = 1;
      this.actuaParametrosFresnel();
   }

   void jRadioButton_fres_semip_actionPerformed(ActionEvent e) {
      this.tipo_objeto_fresnel = 2;
      this.actuaParametrosFresnel();
   }

   void jRadioButton_fres_rend_actionPerformed(ActionEvent e) {
      this.tipo_objeto_fresnel = 3;
      this.actuaParametrosFresnel();
   }

   void jSlider_fres_distProp_keyPressed(KeyEvent e) {
      this.actuaParametrosFresnel();
   }

   void jSlider_fres_distProp_keyTyped(KeyEvent e) {
      this.actuaParametrosFresnel();
   }

   void jSlider_fres_distProp_mouseClicked(MouseEvent e) {
      this.actuaParametrosFresnel();
   }

   void jSlider_fres_distProp_mouseDragged(MouseEvent e) {
      this.actuaParametrosFresnel();
   }

   void jSlider_fres_dimx_keyPressed(KeyEvent e) {
      this.actuaParametrosFresnel();
   }

   void jSlider_fres_dimx_keyTyped(KeyEvent e) {
      this.actuaParametrosFresnel();
   }

   void jSlider_fres_dimx_mouseClicked(MouseEvent e) {
      this.actuaParametrosFresnel();
   }

   void jSlider_fres_dimx_mouseDragged(MouseEvent e) {
      this.actuaParametrosFresnel();
   }

   void jSlider_fres_dimy_keyPressed(KeyEvent e) {
      this.actuaParametrosFresnel();
   }

   void jSlider_fres_dimy_keyTyped(KeyEvent e) {
      this.actuaParametrosFresnel();
   }

   void jSlider_fres_dimy_mouseClicked(MouseEvent e) {
      this.actuaParametrosFresnel();
   }

   void jSlider_fres_dimy_mouseDragged(MouseEvent e) {
      this.actuaParametrosFresnel();
   }

   void jSlider_fres_umbral_mouseClicked(MouseEvent e) {
      this.actuaImagenFresnel();
   }

   void jSlider_fres_umbral_mouseDragged(MouseEvent e) {
      this.actuaImagenFresnel();
   }

   void jSlider_fres_umbral_keyPressed(KeyEvent e) {
      this.actuaImagenFresnel();
   }

   void jSlider_fres_umbral_keyTyped(KeyEvent e) {
      this.actuaImagenFresnel();
   }

   void jRadioButton_fres_showimg_actionPerformed(ActionEvent e) {
      this.jSplitPane_fres.setDividerLocation(0);
      this.label_fres_activaimg = true;
      this.actuaImagenFresnel();
   }

   void jRadioButton_fres_showperf_actionPerformed(ActionEvent e) {
      this.jSplitPane_fres.setDividerLocation(1.0D);
      this.label_fres_activaimg = false;
      this.actuaImagenFresnel();
   }

   void jRadioButton_fres_tipoimg_amp_actionPerformed(ActionEvent e) {
      this.label_fres_tipoimg = 0;
      this.actuaImagenFresnel();
   }

   void jRadioButton_fres_tipoimg_int_actionPerformed(ActionEvent e) {
      this.label_fres_tipoimg = 1;
      this.actuaImagenFresnel();
   }

   void jRadioButton_fres_tipoimg_intlog_actionPerformed(ActionEvent e) {
      this.label_fres_tipoimg = 2;
      this.actuaImagenFresnel();
   }
}
