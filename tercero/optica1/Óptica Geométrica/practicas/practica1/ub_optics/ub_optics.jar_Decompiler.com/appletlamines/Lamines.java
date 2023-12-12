package appletlamines;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Lamines extends JApplet {
   Mostra mostra1 = new Mostra();
   Mostra mostra2 = new Mostra();
   double lambdai = 4.0E-7D;
   double lambdaf = 8.0E-7D;
   double AOI_i = 0.0D;
   double AOI_f = 90.0D;
   double thick_max = 1.0E-6D;
   boolean LambdaMode = true;
   boolean ExcerMode = false;
   boolean ConfigMode = false;
   double[][] Spectrum;
   int numint = 50;
   int numR;
   int numT;
   private String[] gruix = new String[]{"Gruix         ", "Espesor       ", "Thickness     "};
   private String[] wleng = new String[]{"Longitud d'ona", "Long. de onda ", "Wavelength    "};
   private String[] angle = new String[]{"Angle d'incidència", "Angulo de incid.", "Angle of incidence"};
   private String[] textButtonN = new String[]{"Canviar n", "Cambiar n", "Change n"};
   private String[] textButtonK = new String[]{"Canviar k", "Cambiar k", "Change k"};
   private String[] textTransiRefle = new String[]{"Transmissió i Reflexió", "Transmisión y Reflexión", "Transmission and Reflection"};
   private String[] textDifFase = new String[]{"Diferència de Fase", "Diferencia de Fase", "Phase Difference"};
   private String[] textExperimentHelp = new String[]{"Aquest exercici simula una situació experimental en què es\nté una mostra composta d'un substrat amb una làmina prima\nde cares plano-paral·leles de gruix i índex desconeguts.\nAl laboratori es mesuren els espectres de reflectància i\ntransmitància en funció de la longitud d'ona ('R exp' i\n'T exp' a la gràfica).\n\nEn aquest exercici cal trobar els valors de gruix, A i B\n(que es canvien amb el botó 'Canviar n') que fan que les\ncorbes experimentals siguin iguals a les teòriques ('R' i\n'T' a la gràfica)", "Este ejercicio simula una situación experimental en la que\nse tiene una muestra compuesta por un substrato con una\nlámina delgada de caras plano-paralelas de espesor e índice\ndesconocidos. En el laboratorio se han medido los espectros\nde reflectancia y transmitancia en función de la longitud\nde onda ('R exp' y 'T exp' en la gráfica)\n\nEn este ejercicio deben encontrarse los valores de espesor,\nA y B (que se cambian con el botón 'Cambiar n') que\nhacen que las curvas experimetales sean iguales a las\nteóricas ('R' y 'T' en la gráfica)", "This exercise simulates a experimental situation where there\nis a sample composed of a substrate and a thin-film with\nplano-parallel sides, and of unknown thickness and refractive\nindex. In the laboratory the reflectance and transmittance\nspectra as a function of wavelength have been measured\n('R exp' and 'T exp' in the graph).\n\nIn this exercise you have to find what values of thickness\nA and B (that can be changed with the button 'Change n')\nproduce theoretical curves ('R' and 'T' in the graph) that\nfit the experiemtal ones."};
   private String[] textExit = new String[]{"Sortir", "Salir", "Exit"};
   private String[] textAbout = new String[]{"Quant a", "Acerca de", "About"};
   private String[] textHelp = new String[]{"Ajut", "Ayuda", "Help"};
   private String[] textExperiment = new String[]{"Experimenta", "Experimenta", "Experiment"};
   private String[] textVarAngle = new String[]{"Variable: Angle           ", "Variable: Ángulo          ", "Variable: Angle           "};
   private String[] textVarLambda = new String[]{"Variable: Longitud d'ona  ", "Variable: Longitud de onda", "Variable: Wavelength      "};
   private String[] textCongigGraph = new String[]{"Configurar Gràfica", "Configurar Grafica", "Configure Graph"};
   private String[] textWLength = new String[]{"Longitud d'ona", "Longitud de onda", "Wavelength"};
   private String[] textOrdenada = new String[]{"R/T", "R/T", "R/T"};
   private String[] textWLengthIni = new String[]{"Longitud d'ona inicial (nm)", "Longitud de onda inicial (nm)", "Spectrum Initial  Wavelength (nm)"};
   private String[] textWLengthFin = new String[]{"Longitud d'ona final (nm)", "Longitud de onda final (nm)", "Spectrum Final Wavelength (nm)"};
   private String[] textTRIni = new String[]{"Valor de T i R inicial", "Valor de T y R inicial", "Initial T and R value"};
   private String[] textTRFin = new String[]{"Valor de T i R final", "Valor de T y R final", "Final T and R value"};
   private String[] textRange01 = new String[]{"Rang de R/T 0-1", "Rango de R/T 0-1", "R/T Range 0-1"};
   private String[] textAutoRange = new String[]{"Rang Automàtic", "Auto Rango", "Auto Range"};
   private String[] textAutoRangeR = new String[]{"Rang Automàtic R", "Auto Rango R", "Auto Range R"};
   private String[] textAutoRangeT = new String[]{"Rang Automàtic T", "Auto Rango T", "Auto Range T"};
   private String[] textGrafica = new String[]{"Espectre", "Espectro", "Spectrum"};
   private String[] textTMaxMin = new String[]{"Màxims i Mínims de T", "Máximos y Mínimos de T", "T Maxima and Minima"};
   private String[] NCFTitle = new String[]{"Paràmetres de l'índex de refracció n", "Parámetros del índice de refracción n", "Refractive index n parameters"};
   private String[] KCFTitle = new String[]{"Paràmetres del coeficient d'extinció k", "Parámetros del coeficiente de extinción k", "Extinction coefficient k parameters"};
   private String[] rollo = new String[]{"Applet de Làmina de Cares Plano-paral·leles\n Grup d'Innovació Docent en Òptica Física i Fotònica \n Universitat de Barcelona, 2003 \n La utilització d'aquest programa està sota una llicència de Creative Commons \n Veure condicions en http://creativecommons.org/license/by-nc-sa/2.0/ \n\n Curs d’Òptica en Java DOI: 10.1344/401.000000050 \n Applet de Làmines de Cares Plano-paral·leles DOI: 10.1344/203.000000100 \n Aquest programa utiliza la librería commons-math 1.1 \n http://www.apache.org/licenses/LICENSE-2.0", "Applet de Lámina de Caras Plano-paralelas\n Grup d'Innovació Docent en Òptica Física i Fotònica \n Universitat de Barcelona, 2003 \n La utilización de este programa está bajo una licencia de Creative Commons \n Ver condiciones en http://creativecommons.org/license/by-nc-sa/2.0/ \n\n Curso de Óptica en Java DOI: 10.1344/401.000000050 \n Applet de Lámina de Caras Plano-paralelas DOI: 10.1344/203.000000100 \n Este programa utiliza la librería commons-math 1.1 \n http://www.apache.org/licenses/LICENSE-2.0", "Thin-film Applet\n Grup d'Innovació Docent en Òptica Física i Fotònica \n Universitat de Barcelona, 2003 \n The use of this program is under a Creative Commons license \n See conditions in http://creativecommons.org/license/by-nc-sa/2.0/ \n\n Java Optics Course DOI: 10.1344/401.000000050 \n\n Thin-film Applet DOI: 10.1344/203.000000100 \n This program uses the commons-math 1.1 library \n http://www.apache.org/licenses/LICENSE-2.0"};
   private String[] textShowResult = new String[]{"Mostra Resultat", "Mostrar Resultado", "Show Result"};
   private String[] textValorsTrob = new String[]{"Valors trobats: ", "Valores encontrados: ", "Found values: "};
   private String[] textMostraProb = new String[]{"Mostra problema: \n", "Muestra problema: \n", "Problem sample: \n"};
   private String[] textGruixResult = new String[]{"Gruix: ", "Espesor: ", "Thickness: "};
   private String[] textSubstratVidre = new String[]{"Substrat: Vidre", "Substrato: Vidrio", "Substrate: Glass"};
   private String[] textSubstratAire = new String[]{"Substrat: Aire", "Substrato: Aire", "Substrate: Air"};
   static String[] textTitol = new String[]{"Applet de Làmines plano-paral·leles", "Applet de Láminas plano-paralelas", "Thin Films Applet "};
   static int lang = 0;
   private boolean isStandalone = false;
   String var0;
   private JPanel jPanel1 = new JPanel();
   private JButton jButtonExit = new JButton();
   private JButton jButtonHelp = new JButton();
   private JButton jButtonExperiment = new JButton();
   private NCauchyFrame NCF = new NCauchyFrame();
   private KCauchyFrame KCF = new KCauchyFrame();
   private Frame HelpExperiment = new Frame();
   private HelpFrame helpFrame1 = new HelpFrame();
   int a = 0;
   private JPanel jPanel9 = new JPanel();
   private JPanel jPanel8 = new JPanel();
   private JPanel jPanel7 = new JPanel();
   private JPanel jPanel6 = new JPanel();
   private JPanel jPanel5 = new JPanel();
   private JPanel jPanel3 = new JPanel();
   private JPanel jPanel2 = new JPanel();
   private JLabel jLabelN = new JLabel();
   private SliderText sliderTextWavelength = new SliderText();
   private JLabel jLabelK = new JLabel();
   private FlowLayout flowLayout4 = new FlowLayout();
   private FlowLayout flowLayout3 = new FlowLayout();
   private JPanel jPanel10 = new JPanel();
   private FlowLayout flowLayout2 = new FlowLayout();
   private FlowLayout flowLayout1 = new FlowLayout();
   private SliderText sliderTextThickness = new SliderText();
   private JButton jButtonN = new JButton();
   private JButton jButtonK = new JButton();
   private SliderText sliderTextAngle = new SliderText();
   private JPanel jPanelRTvsLambda = new JPanel();
   private BorderLayout borderLayout1 = new BorderLayout();
   private JTabbedPane jTabbedPane1 = new JTabbedPane();
   private MostraPanel mostraPanel2 = new MostraPanel();
   private MostraPanel mostraPanel1 = new MostraPanel();
   private JTabbedPane jTabbedPane2 = new JTabbedPane();
   private JPanel jPanel4 = new JPanel();
   private JPanel jPanel11 = new JPanel();
   private JScrollPane jScrollPane2 = new JScrollPane();
   private JScrollPane jScrollPane1 = new JScrollPane();
   private JList jList2 = new JList();
   private JPanel jPanel12 = new JPanel();
   private ButtonGroup buttonGroup1 = new ButtonGroup();
   private JRadioButton jRadioButtonAngle = new JRadioButton();
   private JRadioButton jRadioButtonLambda = new JRadioButton();
   private JList jList3 = new JList();
   private JButton jButtonGraphConfig = new JButton();
   private JLabel jLabel1 = new JLabel();
   private JLabel jLabel2 = new JLabel();
   private JPanel jPanel13 = new JPanel();
   private Graph2DPanel graph2DPanel1 = new Graph2DPanel();
   private CardLayout cardLayout1 = new CardLayout();
   private JPanel jPanel14 = new JPanel();
   private JPanel jPanel15 = new JPanel();
   private JPanel jPanel16 = new JPanel();
   private JPanel jPanel17 = new JPanel();
   private JPanel jPanel18 = new JPanel();
   private JPanel jPanel19 = new JPanel();
   private JTextField jTextFieldLambdaIni = new JTextField();
   private JLabel jLabel3 = new JLabel();
   private FlowLayout flowLayout5 = new FlowLayout();
   private JTextField jTextFieldLambdaFin = new JTextField();
   private JLabel jLabel4 = new JLabel();
   private JTextField jTextFieldTIni = new JTextField();
   private JLabel jLabel5 = new JLabel();
   private JTextField jTextFieldTFin = new JTextField();
   private JLabel jLabel6 = new JLabel();
   private JButton jButton4 = new JButton();
   private JButton jButton5 = new JButton();
   private JButton jButton6 = new JButton();
   private JButton jButton7 = new JButton();
   private JTextArea jTextArea1 = new JTextArea();
   private JPanel jPanel20 = new JPanel();
   private JButton jButtonHelpExpOk = new JButton();
   private JButton jButton1 = new JButton();
   private JPanel jPanel21 = new JPanel();
   private JRadioButton jRadioButton2 = new JRadioButton();
   private JRadioButton jRadioButton1 = new JRadioButton();
   private ButtonGroup buttonGroup2 = new ButtonGroup();

   public String getParameter(String key, String def) {
      return this.isStandalone ? System.getProperty(key, def) : (this.getParameter(key) != null ? this.getParameter(key) : def);
   }

   public Lamines() {
      DecimalFormat df = new DecimalFormat("####");
      String S1 = df.format(0L);
      String S2 = df.format(this.mostra1.thick_max * 1.0E9D);
      this.sliderTextThickness = new SliderText(0.0D, this.mostra1.thick_max * 1.0E9D, this.mostra1.thick * 1.0E9D, this.gruix[lang], "nm", S1, S2);
      S1 = df.format(this.lambdai * 1.0E9D);
      S2 = df.format(this.lambdaf * 1.0E9D);
      this.sliderTextWavelength = new SliderText(this.lambdai * 1.0E9D, this.lambdaf * 1.0E9D, 633.0D, this.wleng[lang], "nm", S1, S2);
      S1 = df.format(this.AOI_i);
      S2 = df.format(this.AOI_f);
      this.sliderTextAngle = new SliderText(this.AOI_i, this.AOI_f, 0.0D, this.angle[lang], "º", S1, S2);
      this.sliderTextAngle.setStep(90);
      this.NCF.setSize(320, 515);
      this.KCF.setSize(320, 515);
      this.HelpExperiment.setSize(440, 320);
   }

   public void init() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      this.jButtonN.setText(this.textButtonN[lang]);
      this.jButtonK.setText(this.textButtonK[lang]);
      this.jTabbedPane1.setTitleAt(0, this.textTransiRefle[lang]);
      this.jTabbedPane1.setTitleAt(1, this.textDifFase[lang]);
      this.jTabbedPane2.setTitleAt(0, this.textGrafica[lang]);
      this.jTabbedPane2.setTitleAt(1, this.textTMaxMin[lang]);
      this.jRadioButton2.setText(this.textSubstratVidre[lang]);
      this.jRadioButton1.setText(this.textSubstratAire[lang]);
      this.graph2DPanel1.backColor = Color.blue;
      this.graph2DPanel1.axisColor = Color.white;
      this.UpdateNKParameters();
      this.UpdateMaxMinList(this.jList3, this.mostra1);
      this.SetupGrafica();
      this.sliderTextThickness.label_titol.setText(this.gruix[lang]);
      this.sliderTextAngle.label_titol.setText(this.angle[lang]);
      this.sliderTextWavelength.label_titol.setText(this.wleng[lang]);
      this.jButtonExit.setText(this.textExit[lang]);
      this.jButtonHelp.setText(this.textHelp[lang]);
      this.jButtonExperiment.setText(this.textExperiment[lang]);
      this.jRadioButtonAngle.setText(this.textVarAngle[lang]);
      this.jRadioButtonLambda.setText(this.textVarLambda[lang]);
      this.jButtonGraphConfig.setText(this.textCongigGraph[lang]);
      this.jLabel1.setText(this.textWLength[lang]);
      this.jLabel2.setText(this.textOrdenada[lang]);
      this.jLabel3.setText(this.textWLengthIni[lang]);
      this.jLabel4.setText(this.textWLengthFin[lang]);
      this.jLabel5.setText(this.textTRIni[lang]);
      this.jLabel6.setText(this.textTRFin[lang]);
      this.jButton4.setText(this.textRange01[lang]);
      this.jButton5.setText(this.textAutoRange[lang]);
      this.jButton6.setText(this.textAutoRangeR[lang]);
      this.jButton7.setText(this.textAutoRangeT[lang]);
      this.HelpExperiment.setTitle(this.textExperiment[lang]);
      this.jButton1.setText(this.textAbout[lang]);
      this.NCF.setTitle(this.NCFTitle[lang]);
      this.KCF.setTitle(this.KCFTitle[lang]);
      this.NCF.SetXAxis(this.textWLength[lang] + " (nm)");
      this.KCF.SetXAxis(this.textWLength[lang] + " (nm)");
      this.graph2DPanel1.TexteX = this.textWLength[lang] + " (nm)";
   }

   private void jbInit() throws Exception {
      this.jButtonExit.setActionCommand("jButtonExit");
      this.jButtonExit.setMnemonic('1');
      this.jButtonExit.setText("Sortir");
      this.jButtonExit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Lamines.this.jButtonExit_actionPerformed(e);
         }
      });
      this.jButtonHelp.setText("Ajut");
      this.jButtonHelp.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Lamines.this.jButtonHelp_actionPerformed(e);
         }
      });
      this.jButtonExperiment.setText("Experimenta");
      this.jButtonExperiment.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Lamines.this.jButtonExperiment_actionPerformed(e);
         }
      });
      this.jPanel1.setBorder(BorderFactory.createEtchedBorder());
      this.KCF.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            Lamines.this.KCF_stateChanged(e);
         }
      });
      this.NCF.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            Lamines.this.NCF_stateChanged(e);
         }
      });
      this.NCF.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            Lamines.this.NCF_stateChanged(e);
         }
      });
      this.jPanel9.setMinimumSize(new Dimension(260, 27));
      this.jPanel9.setPreferredSize(new Dimension(260, 27));
      this.jPanel8.setLayout(this.flowLayout3);
      this.jPanel8.setMaximumSize(new Dimension(360, 32767));
      this.jPanel8.setMinimumSize(new Dimension(360, 30));
      this.jPanel8.setPreferredSize(new Dimension(360, 30));
      this.jPanel7.setLayout(this.flowLayout4);
      this.jPanel7.setMaximumSize(new Dimension(180, 32767));
      this.jPanel7.setMinimumSize(new Dimension(360, 29));
      this.jPanel7.setPreferredSize(new Dimension(360, 29));
      this.jPanel6.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanel6.setMaximumSize(new Dimension(365, 100));
      this.jPanel6.setMinimumSize(new Dimension(365, 100));
      this.jPanel6.setPreferredSize(new Dimension(365, 100));
      this.jPanel6.setLayout(this.flowLayout2);
      this.jPanel5.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanel5.setMaximumSize(new Dimension(365, 150));
      this.jPanel5.setMinimumSize(new Dimension(365, 150));
      this.jPanel5.setPreferredSize(new Dimension(365, 150));
      this.jPanel5.setLayout(this.flowLayout1);
      this.jPanel3.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel3.setMaximumSize(new Dimension(365, 530));
      this.jPanel3.setMinimumSize(new Dimension(365, 530));
      this.jPanel3.setPreferredSize(new Dimension(365, 530));
      this.jPanel2.setAlignmentX(0.0F);
      this.jPanel2.setAlignmentY(0.0F);
      this.jPanel2.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel2.setMaximumSize(new Dimension(375, 530));
      this.jPanel2.setMinimumSize(new Dimension(375, 530));
      this.jPanel2.setPreferredSize(new Dimension(375, 530));
      this.jPanel21.setMaximumSize(new Dimension(360, 30));
      this.jPanel21.setMinimumSize(new Dimension(360, 30));
      this.jPanel21.setPreferredSize(new Dimension(360, 30));
      this.jLabelN.setText("A = 1, B = 100 nm^2, C = 10000 nm^4");
      this.sliderTextWavelength.setPreferredSize(new Dimension(360, 48));
      this.sliderTextWavelength.setMinimumSize(new Dimension(360, 48));
      this.sliderTextWavelength.setMaximumSize(new Dimension(360, 48));
      this.sliderTextWavelength.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jLabelK.setText("k0 = 0.001, D = 100 nm");
      this.flowLayout4.setAlignment(0);
      this.flowLayout4.setHgap(1);
      this.flowLayout4.setVgap(1);
      this.flowLayout3.setAlignment(0);
      this.flowLayout3.setHgap(1);
      this.flowLayout3.setVgap(1);
      this.jPanel10.setMinimumSize(new Dimension(260, 27));
      this.jPanel10.setPreferredSize(new Dimension(260, 27));
      this.flowLayout2.setVgap(1);
      this.flowLayout1.setHgap(1);
      this.flowLayout1.setVgap(1);
      this.sliderTextThickness.setBorder(BorderFactory.createLineBorder(Color.black));
      this.sliderTextThickness.setMaximumSize(new Dimension(360, 48));
      this.sliderTextThickness.setMinimumSize(new Dimension(360, 48));
      this.sliderTextThickness.setPreferredSize(new Dimension(360, 48));
      this.jButtonN.setMaximumSize(new Dimension(95, 27));
      this.jButtonN.setMinimumSize(new Dimension(95, 27));
      this.jButtonN.setPreferredSize(new Dimension(95, 27));
      this.jButtonN.setMnemonic('0');
      this.jButtonN.setText("Canviar n");
      this.jButtonN.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Lamines.this.jButtonN_actionPerformed(e);
         }
      });
      this.jButtonK.setMaximumSize(new Dimension(95, 27));
      this.jButtonK.setMinimumSize(new Dimension(95, 27));
      this.jButtonK.setPreferredSize(new Dimension(95, 27));
      this.jButtonK.setText("Canviar k");
      this.jButtonK.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Lamines.this.jButtonK_actionPerformed(e);
         }
      });
      this.sliderTextAngle.setPreferredSize(new Dimension(360, 48));
      this.sliderTextAngle.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            Lamines.this.sliderTextAngle_stateChanged(e);
         }
      });
      this.sliderTextAngle.setMinimumSize(new Dimension(360, 48));
      this.sliderTextAngle.setMaximumSize(new Dimension(360, 48));
      this.sliderTextAngle.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanelRTvsLambda.setAlignmentX(0.0F);
      this.jPanelRTvsLambda.setAlignmentY(0.0F);
      this.jPanelRTvsLambda.setMaximumSize(new Dimension(750, 500));
      this.jPanelRTvsLambda.setMinimumSize(new Dimension(750, 500));
      this.jPanelRTvsLambda.setPreferredSize(new Dimension(750, 500));
      this.jPanelRTvsLambda.setLayout(this.borderLayout1);
      this.mostraPanel1.setMaximumSize(new Dimension(360, 200));
      this.mostraPanel1.setMinimumSize(new Dimension(360, 200));
      this.mostraPanel1.setPreferredSize(new Dimension(360, 200));
      this.mostraPanel1.setToolTipText("");
      this.mostraPanel2.setMaximumSize(new Dimension(360, 200));
      this.mostraPanel2.setMinimumSize(new Dimension(360, 200));
      this.mostraPanel2.setPreferredSize(new Dimension(360, 200));
      this.jTabbedPane2.setMaximumSize(new Dimension(360, 480));
      this.jTabbedPane2.setMinimumSize(new Dimension(360, 480));
      this.jTabbedPane2.setPreferredSize(new Dimension(360, 480));
      this.jPanel11.addComponentListener(new ComponentAdapter() {
         public void componentShown(ComponentEvent e) {
            Lamines.this.jPanel11_componentShown(e);
         }
      });
      this.jScrollPane1.setName("");
      this.jScrollPane1.setMaximumSize(new Dimension(350, 360));
      this.jScrollPane1.setMinimumSize(new Dimension(350, 360));
      this.jScrollPane1.setPreferredSize(new Dimension(350, 360));
      this.jScrollPane2.setVisible(false);
      this.jScrollPane2.setMaximumSize(new Dimension(350, 180));
      this.jScrollPane2.setMinimumSize(new Dimension(350, 180));
      this.jScrollPane2.setPreferredSize(new Dimension(350, 180));
      this.jPanel12.setName("");
      this.jPanel12.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel12.setMaximumSize(new Dimension(200, 70));
      this.jPanel12.setMinimumSize(new Dimension(200, 70));
      this.jPanel12.setPreferredSize(new Dimension(200, 70));
      this.jRadioButtonAngle.setMaximumSize(new Dimension(180, 25));
      this.jRadioButtonAngle.setMinimumSize(new Dimension(180, 25));
      this.jRadioButtonAngle.setPreferredSize(new Dimension(180, 25));
      this.jRadioButtonAngle.setText("Variable: Angle");
      this.jRadioButtonLambda.setMaximumSize(new Dimension(180, 25));
      this.jRadioButtonLambda.setMinimumSize(new Dimension(180, 25));
      this.jRadioButtonLambda.setPreferredSize(new Dimension(180, 25));
      this.jRadioButtonLambda.setSelected(true);
      this.jRadioButtonLambda.setText("Variable: Wavelength");
      this.jRadioButtonLambda.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            Lamines.this.jRadioButtonLambda_stateChanged(e);
         }
      });
      this.jButtonGraphConfig.setText("Configurar Gràfica");
      this.jButtonGraphConfig.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Lamines.this.jButtonGraphConfig_actionPerformed(e);
         }
      });
      this.jLabel1.setMaximumSize(new Dimension(300, 17));
      this.jLabel1.setMinimumSize(new Dimension(300, 17));
      this.jLabel1.setPreferredSize(new Dimension(300, 17));
      this.jLabel1.setText("Longitud d'ona: ");
      this.jLabel2.setMaximumSize(new Dimension(300, 17));
      this.jLabel2.setMinimumSize(new Dimension(300, 17));
      this.jLabel2.setPreferredSize(new Dimension(300, 17));
      this.jLabel2.setText("Ordenada: ");
      this.graph2DPanel1.setMaximumSize(new Dimension(350, 300));
      this.graph2DPanel1.setMinimumSize(new Dimension(350, 300));
      this.graph2DPanel1.setPreferredSize(new Dimension(350, 300));
      this.graph2DPanel1.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseMoved(MouseEvent e) {
            Lamines.this.graph2DPanel1_mouseMoved(e);
         }
      });
      this.jPanel13.setLayout(this.cardLayout1);
      this.jPanel15.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanel15.setMaximumSize(new Dimension(340, 110));
      this.jPanel15.setMinimumSize(new Dimension(340, 110));
      this.jPanel15.setPreferredSize(new Dimension(340, 110));
      this.jPanel16.setPreferredSize(new Dimension(340, 40));
      this.jPanel16.setMinimumSize(new Dimension(340, 40));
      this.jPanel16.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanel16.setMaximumSize(new Dimension(340, 40));
      this.jPanel17.setPreferredSize(new Dimension(340, 40));
      this.jPanel17.setMinimumSize(new Dimension(340, 40));
      this.jPanel17.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanel17.setMaximumSize(new Dimension(340, 40));
      this.jPanel14.setMaximumSize(new Dimension(350, 300));
      this.jPanel14.setMinimumSize(new Dimension(350, 300));
      this.jPanel14.setPreferredSize(new Dimension(350, 300));
      this.jPanel18.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanel18.setMaximumSize(new Dimension(340, 40));
      this.jPanel18.setMinimumSize(new Dimension(340, 40));
      this.jPanel18.setPreferredSize(new Dimension(340, 40));
      this.jPanel19.setPreferredSize(new Dimension(340, 40));
      this.jPanel19.setLayout(this.flowLayout5);
      this.jPanel19.setMinimumSize(new Dimension(340, 40));
      this.jPanel19.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanel19.setMaximumSize(new Dimension(340, 40));
      this.jLabel3.setMaximumSize(new Dimension(180, 17));
      this.jLabel3.setMinimumSize(new Dimension(180, 17));
      this.jLabel3.setPreferredSize(new Dimension(180, 17));
      this.jLabel3.setText("Spectrum Initial Wavelength");
      this.jTextFieldLambdaFin.setToolTipText("");
      this.jTextFieldLambdaFin.setColumns(5);
      this.jLabel4.setMaximumSize(new Dimension(180, 17));
      this.jLabel4.setMinimumSize(new Dimension(180, 17));
      this.jLabel4.setPreferredSize(new Dimension(180, 17));
      this.jLabel4.setToolTipText("");
      this.jLabel4.setText("Spectrum Final Wavelength");
      this.jTextFieldTIni.setColumns(5);
      this.jLabel5.setMaximumSize(new Dimension(180, 17));
      this.jLabel5.setMinimumSize(new Dimension(180, 17));
      this.jLabel5.setPreferredSize(new Dimension(180, 17));
      this.jLabel5.setText("Initial T and R value");
      this.jLabel6.setMaximumSize(new Dimension(180, 17));
      this.jLabel6.setMinimumSize(new Dimension(180, 17));
      this.jLabel6.setPreferredSize(new Dimension(180, 17));
      this.jLabel6.setText("Final T and R Value");
      this.jButton4.setMaximumSize(new Dimension(140, 27));
      this.jButton4.setMinimumSize(new Dimension(140, 27));
      this.jButton4.setPreferredSize(new Dimension(140, 27));
      this.jButton4.setText("Range 0-1");
      this.jButton4.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Lamines.this.jButton4_actionPerformed(e);
         }
      });
      this.jButton5.setMaximumSize(new Dimension(140, 27));
      this.jButton5.setMinimumSize(new Dimension(140, 27));
      this.jButton5.setPreferredSize(new Dimension(140, 27));
      this.jButton5.setText("Auto Range");
      this.jButton5.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Lamines.this.jButton5_actionPerformed(e);
         }
      });
      this.jButton6.setMaximumSize(new Dimension(140, 27));
      this.jButton6.setMinimumSize(new Dimension(140, 27));
      this.jButton6.setPreferredSize(new Dimension(140, 27));
      this.jButton6.setText("Auto Range R");
      this.jButton6.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Lamines.this.jButton6_actionPerformed(e);
         }
      });
      this.jButton7.setMaximumSize(new Dimension(140, 27));
      this.jButton7.setMinimumSize(new Dimension(140, 27));
      this.jButton7.setPreferredSize(new Dimension(140, 27));
      this.jButton7.setText("Auto Range T");
      this.jButton7.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Lamines.this.jButton7_actionPerformed(e);
         }
      });
      this.jTextFieldLambdaIni.setColumns(5);
      this.jTextFieldTFin.setColumns(5);
      this.HelpExperiment.setTitle("Experiment");
      this.HelpExperiment.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            Lamines.this.HelpExperiment_windowClosing(e);
         }
      });
      this.jTextArea1.setMaximumSize(new Dimension(600, 300));
      this.jTextArea1.setMinimumSize(new Dimension(600, 300));
      this.jTextArea1.setPreferredSize(new Dimension(600, 300));
      this.jTextArea1.setCaretPosition(0);
      this.jTextArea1.setEditable(false);
      this.jTextArea1.setText("jTextArea1");
      this.jTextArea1.setColumns(62);
      this.jTextArea1.setLineWrap(true);
      this.jButtonHelpExpOk.setMnemonic('0');
      this.jButtonHelpExpOk.setText("Ok");
      this.jButtonHelpExpOk.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Lamines.this.jButtonHelpExpOk_actionPerformed(e);
         }
      });
      this.jButton1.setText("Quant a");
      this.jButton1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Lamines.this.jButton1_actionPerformed(e);
         }
      });
      this.jRadioButton2.setPreferredSize(new Dimension(120, 25));
      this.jRadioButton2.setHorizontalAlignment(0);
      this.jRadioButton2.setSelected(true);
      this.jRadioButton2.setText("Substrat: Vidre");
      this.jRadioButton2.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            Lamines.this.jRadioButton2_stateChanged(e);
         }
      });
      this.jRadioButton1.setPreferredSize(new Dimension(120, 25));
      this.jRadioButton1.setHorizontalAlignment(0);
      this.jRadioButton1.setText("Substrat: Aire");
      this.jPanel1.add(this.jButtonExperiment, (Object)null);
      this.jPanel1.add(this.jButtonHelp, (Object)null);
      this.jPanel1.add(this.jButton1, (Object)null);
      this.jPanel1.add(this.jButtonExit, (Object)null);
      this.getContentPane().add(this.jPanelRTvsLambda, "North");
      this.jTabbedPane2.add(this.jPanel4, "Gràfica");
      this.jPanel4.add(this.jPanel13, (Object)null);
      this.jPanel13.add(this.graph2DPanel1, "graph2DPanel1");
      this.jPanel13.add(this.jPanel14, "jPanel14");
      this.jPanel14.add(this.jPanel19, (Object)null);
      this.jPanel19.add(this.jLabel3, (Object)null);
      this.jPanel19.add(this.jTextFieldLambdaIni, (Object)null);
      this.jPanel14.add(this.jPanel18, (Object)null);
      this.jPanel18.add(this.jLabel4, (Object)null);
      this.jPanel18.add(this.jTextFieldLambdaFin, (Object)null);
      this.jPanel14.add(this.jPanel17, (Object)null);
      this.jPanel17.add(this.jLabel5, (Object)null);
      this.jPanel17.add(this.jTextFieldTIni, (Object)null);
      this.jPanel14.add(this.jPanel16, (Object)null);
      this.jPanel16.add(this.jLabel6, (Object)null);
      this.jPanel16.add(this.jTextFieldTFin, (Object)null);
      this.jPanel14.add(this.jPanel15, (Object)null);
      this.jPanel15.add(this.jButton4, (Object)null);
      this.jPanel15.add(this.jButton5, (Object)null);
      this.jPanel15.add(this.jButton6, (Object)null);
      this.jPanel15.add(this.jButton7, (Object)null);
      this.jPanel4.add(this.jLabel1, (Object)null);
      this.jPanel4.add(this.jLabel2, (Object)null);
      this.jPanel4.add(this.jButtonGraphConfig, (Object)null);
      this.jTabbedPane2.add(this.jPanel11, "Maxims i mínims");
      this.jPanel11.add(this.jScrollPane1, (Object)null);
      this.jScrollPane1.getViewport().add(this.jList3, (Object)null);
      this.jPanel11.add(this.jScrollPane2, (Object)null);
      this.jPanel11.add(this.jPanel12, (Object)null);
      this.jPanel12.add(this.jRadioButtonLambda, (Object)null);
      this.jPanel12.add(this.jRadioButtonAngle, (Object)null);
      this.jScrollPane2.getViewport().add(this.jList2, (Object)null);
      this.getContentPane().add(this.jPanel1, "South");
      this.jPanelRTvsLambda.add(this.jPanel2, "West");
      this.jTabbedPane1.add(this.mostraPanel1, "Transmissió i Reflexió");
      this.jTabbedPane1.add(this.mostraPanel2, "Diferència de Fase");
      this.jPanel2.add(this.jTabbedPane1, (Object)null);
      this.jPanel2.add(this.jPanel5, (Object)null);
      this.jPanel5.add(this.sliderTextThickness, (Object)null);
      this.jPanel5.add(this.sliderTextWavelength, (Object)null);
      this.jPanel5.add(this.sliderTextAngle, (Object)null);
      this.jPanel2.add(this.jPanel6, (Object)null);
      this.jPanel8.add(this.jPanel10, (Object)null);
      this.jPanel10.add(this.jLabelK, (Object)null);
      this.jPanel8.add(this.jButtonK, (Object)null);
      this.jPanelRTvsLambda.add(this.jPanel3, "Center");
      this.jPanel3.add(this.jTabbedPane2, (Object)null);
      this.jPanel7.add(this.jPanel9, (Object)null);
      this.jPanel9.add(this.jLabelN, (Object)null);
      this.jPanel7.add(this.jButtonN, (Object)null);
      this.jPanel21.add(this.jRadioButton2, (Object)null);
      this.jPanel21.add(this.jRadioButton1, (Object)null);
      this.jPanel6.add(this.jPanel7, (Object)null);
      this.jPanel6.add(this.jPanel8, (Object)null);
      this.jPanel6.add(this.jPanel21, (Object)null);
      this.sliderTextWavelength.jPanelSlider.setPreferredSize(new Dimension(180, 40));
      this.sliderTextWavelength.jPanelSlider.setMaximumSize(new Dimension(180, 40));
      this.sliderTextWavelength.jPanelSlider.setMinimumSize(new Dimension(180, 40));
      this.sliderTextThickness.jPanelSlider.setPreferredSize(new Dimension(180, 40));
      this.sliderTextThickness.jPanelSlider.setMaximumSize(new Dimension(180, 40));
      this.sliderTextThickness.jPanelSlider.setMinimumSize(new Dimension(180, 40));
      this.sliderTextAngle.jPanelSlider.setPreferredSize(new Dimension(180, 40));
      this.sliderTextAngle.jPanelSlider.setMaximumSize(new Dimension(180, 40));
      this.sliderTextAngle.jPanelSlider.setMinimumSize(new Dimension(180, 40));
      this.sliderTextWavelength.texte.setPreferredSize(new Dimension(30, 20));
      this.sliderTextWavelength.texte.setMaximumSize(new Dimension(30, 20));
      this.sliderTextWavelength.texte.setMinimumSize(new Dimension(30, 20));
      this.sliderTextThickness.texte.setPreferredSize(new Dimension(30, 20));
      this.sliderTextThickness.texte.setMaximumSize(new Dimension(30, 20));
      this.sliderTextThickness.texte.setMinimumSize(new Dimension(30, 20));
      this.sliderTextAngle.texte.setPreferredSize(new Dimension(30, 20));
      this.sliderTextAngle.texte.setMaximumSize(new Dimension(30, 20));
      this.sliderTextAngle.texte.setMinimumSize(new Dimension(30, 20));
      this.sliderTextWavelength.texte.setColumns(4);
      this.sliderTextThickness.texte.setColumns(4);
      this.sliderTextAngle.texte.setColumns(4);
      this.sliderTextWavelength.slider.setPreferredSize(new Dimension(105, 30));
      this.sliderTextWavelength.slider.setMaximumSize(new Dimension(105, 30));
      this.sliderTextWavelength.slider.setMinimumSize(new Dimension(105, 30));
      this.sliderTextThickness.slider.setPreferredSize(new Dimension(105, 30));
      this.sliderTextThickness.slider.setMaximumSize(new Dimension(105, 30));
      this.sliderTextThickness.slider.setMinimumSize(new Dimension(105, 30));
      this.sliderTextAngle.slider.setPreferredSize(new Dimension(105, 30));
      this.sliderTextAngle.slider.setMaximumSize(new Dimension(105, 30));
      this.sliderTextAngle.slider.setMinimumSize(new Dimension(105, 30));
      this.sliderTextWavelength.jPanelLabel.setPreferredSize(new Dimension(90, 35));
      this.sliderTextWavelength.jPanelLabel.setMaximumSize(new Dimension(90, 35));
      this.sliderTextWavelength.jPanelLabel.setMinimumSize(new Dimension(90, 35));
      this.sliderTextThickness.jPanelLabel.setPreferredSize(new Dimension(90, 35));
      this.sliderTextThickness.jPanelLabel.setMaximumSize(new Dimension(90, 35));
      this.sliderTextThickness.jPanelLabel.setMinimumSize(new Dimension(90, 35));
      this.sliderTextAngle.jPanelLabel.setPreferredSize(new Dimension(90, 35));
      this.sliderTextAngle.jPanelLabel.setMaximumSize(new Dimension(90, 35));
      this.sliderTextAngle.jPanelLabel.setMinimumSize(new Dimension(90, 35));
      this.sliderTextWavelength.label_vmin.setPreferredSize(new Dimension(25, 30));
      this.sliderTextWavelength.label_vmin.setMaximumSize(new Dimension(25, 30));
      this.sliderTextWavelength.label_vmin.setMinimumSize(new Dimension(25, 30));
      this.sliderTextThickness.label_vmin.setPreferredSize(new Dimension(25, 30));
      this.sliderTextThickness.label_vmin.setMaximumSize(new Dimension(25, 30));
      this.sliderTextThickness.label_vmin.setMinimumSize(new Dimension(25, 30));
      this.sliderTextAngle.label_vmin.setPreferredSize(new Dimension(25, 30));
      this.sliderTextAngle.label_vmin.setMaximumSize(new Dimension(25, 30));
      this.sliderTextAngle.label_vmin.setMinimumSize(new Dimension(25, 30));
      this.sliderTextWavelength.label_vmax.setPreferredSize(new Dimension(35, 30));
      this.sliderTextWavelength.label_vmax.setMaximumSize(new Dimension(35, 30));
      this.sliderTextWavelength.label_vmax.setMinimumSize(new Dimension(35, 30));
      this.sliderTextThickness.label_vmax.setPreferredSize(new Dimension(35, 30));
      this.sliderTextThickness.label_vmax.setMaximumSize(new Dimension(35, 30));
      this.sliderTextThickness.label_vmax.setMinimumSize(new Dimension(35, 30));
      this.sliderTextAngle.label_vmax.setPreferredSize(new Dimension(35, 30));
      this.sliderTextAngle.label_vmax.setMaximumSize(new Dimension(35, 30));
      this.sliderTextAngle.label_vmax.setMinimumSize(new Dimension(35, 30));
      this.sliderTextWavelength.label_units.setPreferredSize(new Dimension(20, 30));
      this.sliderTextWavelength.label_units.setMaximumSize(new Dimension(20, 30));
      this.sliderTextWavelength.label_units.setMinimumSize(new Dimension(20, 30));
      this.sliderTextThickness.label_units.setPreferredSize(new Dimension(20, 30));
      this.sliderTextThickness.label_units.setMaximumSize(new Dimension(20, 30));
      this.sliderTextThickness.label_units.setMinimumSize(new Dimension(20, 30));
      this.sliderTextAngle.label_units.setPreferredSize(new Dimension(20, 30));
      this.sliderTextAngle.label_units.setMaximumSize(new Dimension(20, 30));
      this.sliderTextAngle.label_units.setMinimumSize(new Dimension(20, 30));
      this.setSize(new Dimension(750, 550));
      this.mostraPanel1.mode = 0;
      this.mostraPanel2.mode = 1;
      this.sliderTextWavelength.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            Lamines.this.sliderTextWavelength_stateChanged(e);
         }
      });
      this.sliderTextThickness.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            Lamines.this.sliderTextThickness_stateChanged(e);
         }
      });
      this.sliderTextThickness.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            Lamines.this.sliderTextThickness_stateChanged(e);
         }
      });
      this.sliderTextAngle.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            Lamines.this.sliderTextAngle_stateChanged(e);
         }
      });
      this.buttonGroup1.add(this.jRadioButtonLambda);
      this.buttonGroup1.add(this.jRadioButtonAngle);
      this.HelpExperiment.add(this.jTextArea1, "Center");
      this.HelpExperiment.add(this.jPanel20, "South");
      this.jPanel20.add(this.jButtonHelpExpOk, (Object)null);
      this.buttonGroup2.add(this.jRadioButton2);
      this.buttonGroup2.add(this.jRadioButton1);
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
      String[][] pinfo = new String[][]{{"param0", "String", "Idioma"}};
      return pinfo;
   }

   public static void main(String[] args) {
      Lamines applet = new Lamines();
      applet.isStandalone = true;
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(3);
      frame.getContentPane().add(applet, "Center");

      try {
         String dato = args[0].toUpperCase().intern();
         if (dato == "CA") {
            lang = 0;
         } else if (dato == "ES") {
            lang = 1;
         } else if (dato == "EN") {
            lang = 2;
         }
      } catch (Exception var4) {
         lang = 0;
      }

      applet.init();
      applet.start();
      frame.setSize(750, 570);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
      frame.setTitle(textTitol[lang]);
      frame.setVisible(true);
   }

   void jButtonExit_actionPerformed(ActionEvent e) {
      try {
         System.exit(0);
      } catch (Exception var3) {
         System.out.println("Error al sortir");
      }

   }

   void KCF_stateChanged(ChangeEvent e) {
      this.mostra1.k0 = this.KCF.k0;
      this.mostra1.D = this.KCF.D;
      this.UpdateNKParameters();
   }

   void jButtonHelp_actionPerformed(ActionEvent e) {
      this.helpFrame1.setSize(new Dimension(700, 500));
      this.helpFrame1.show();
   }

   void NCF_stateChanged(ChangeEvent e) {
      this.mostra1.A = this.NCF.A;
      this.mostra1.B = this.NCF.B;
      this.mostra1.C = this.NCF.C;
      this.UpdateNKParameters();
   }

   public void UpdateMostraPanel() {
      this.mostra1.lambda = this.sliderTextWavelength.valcur / 1.0E9D;
      this.mostra1.thick = this.sliderTextThickness.valcur / 1.0E9D;
      this.mostra1.angle = this.sliderTextAngle.valcur / 180.0D * 3.141592653589793D;
      this.mostraPanel1.mostra1 = this.mostra1;
      this.mostraPanel1.repaint();
      this.mostraPanel2.mostra1 = this.mostra1;
      this.mostraPanel2.repaint();
   }

   void sliderTextThickness_stateChanged(ChangeEvent e) {
      this.UpdateMostraPanel();
      this.UpdateMaxMinList(this.jList3, this.mostra1);
      this.UpdateGrafica();
   }

   void sliderTextWavelength_stateChanged(ChangeEvent e) {
      this.UpdateMostraPanel();
      this.UpdateMaxMinList(this.jList3, this.mostra1);
      this.UpdateGrafica();
   }

   void sliderTextAngle_stateChanged(ChangeEvent e) {
      this.UpdateMostraPanel();
      this.UpdateMaxMinList(this.jList3, this.mostra1);
      this.UpdateGrafica();
   }

   void jButtonN_actionPerformed(ActionEvent e) {
      this.NCF.UpdateValues(this.mostra1.A, this.mostra1.B, this.mostra1.C);
      this.NCF.setVisible(true);
   }

   void jButtonK_actionPerformed(ActionEvent e) {
      this.KCF.UpdateValues(this.mostra1.k0, this.mostra1.D);
      this.KCF.setVisible(true);
   }

   void UpdateNKParameters() {
      DecimalFormat Format = new DecimalFormat("#.###E0");
      String S = "n -> A=" + Format.format(this.mostra1.A) + "   B=" + Format.format(this.mostra1.B) + "   C=" + Format.format(this.mostra1.C);
      this.jLabelN.setText(S);
      S = "k -> k0=" + Format.format(this.mostra1.k0);
      Format.applyPattern("####");
      S = S + "   D=" + Format.format(this.mostra1.D);
      this.jLabelK.setText(S);
      this.UpdateMostraPanel();
      this.UpdateMaxMinList(this.jList3, this.mostra1);
      this.UpdateGrafica();
   }

   void UpdateMaxMinList(JList Llista, Mostra mostra) {
      Vector Strings = new Vector();
      new String();
      String Texte;
      int i;
      int kmin;
      int kmax;
      double nl;
      if (this.LambdaMode) {
         if (mostra.thick != 0.0D) {
            nl = mostra.n(this.lambdaf);
            mostra.ns(this.lambdaf);
            double theta = Math.asin(1.0D / nl * Math.sin(mostra.angle));
            kmin = (int)Math.ceil(4.0D * nl * mostra.thick * Math.cos(theta) / this.lambdaf);
            nl = mostra.n(this.lambdai);
            theta = Math.asin(1.0D / nl * Math.sin(mostra.angle));
            kmax = (int)Math.floor(4.0D * nl * mostra.thick * Math.cos(theta) / this.lambdai);

            for(i = kmin; i <= kmax; ++i) {
               double aux_lam = this.AjLambda(4.0D * mostra.thick * Math.cos(theta) / (double)i, mostra) * 1.0E9D;
               Texte = "λ = " + (int)aux_lam + " nm, k = " + i + ", ";
               if (i / 2 == (i + 1) / 2) {
                  if (mostra.n(aux_lam) > mostra.ns(aux_lam)) {
                     Texte = Texte + " Max.";
                  } else {
                     Texte = Texte + " Min.";
                  }
               } else if (mostra.n(aux_lam) > mostra.ns(aux_lam)) {
                  Texte = Texte + " Min.";
               } else {
                  Texte = Texte + " Max.";
               }

               Strings.add(Texte);
            }
         }
      } else {
         nl = mostra.n(mostra.lambda);
         Math.asin(1.0D / nl * Math.sin(mostra.angle));
         double theta_max = Math.asin(1.0D / nl);
         kmin = (int)Math.ceil(4.0D * nl * mostra.thick * Math.cos(theta_max) / mostra.lambda);
         kmax = (int)Math.floor(4.0D * nl * mostra.thick / mostra.lambda);

         for(i = kmin; i <= kmax; ++i) {
            Texte = "θ = " + (int)(Math.acos(mostra.lambda * (double)i / (4.0D * nl * mostra.thick)) / 3.141592653589793D * 180.0D) + "º, k = " + i + ", ";
            if (i / 2 == (i + 1) / 2) {
               if (mostra.n(mostra.lambda) > mostra.ns(mostra.lambda)) {
                  Texte = Texte + " Max.";
               } else {
                  Texte = Texte + " Min.";
               }
            } else if (mostra.n(mostra.lambda) > mostra.ns(mostra.lambda)) {
               Texte = Texte + " Min.";
            } else {
               Texte = Texte + " Max.";
            }

            Strings.add(Texte);
         }
      }

      Llista.setListData(Strings);
   }

   double AjLambda(double K, Mostra mostra) {
      double a = this.lambdai;
      double c = this.lambdaf;

      double b;
      double d1;
      double d2;
      do {
         b = (a + c) / 2.0D;
         d1 = (K * mostra.n(b) - b) * (K * mostra.n(a) - a);
         d2 = (K * mostra.n(c) - c) * (K * mostra.n(b) - b);
         if (d1 < 0.0D) {
            c = b;
         } else {
            if (!(d2 < 0.0D)) {
               break;
            }

            a = b;
         }
      } while(d1 != 0.0D && d2 != 0.0D && Math.abs(a - c) / b > 0.001D);

      return b;
   }

   void jPanel11_componentShown(ComponentEvent e) {
      this.UpdateMaxMinList(this.jList3, this.mostra1);
   }

   int CalcNumInt(Mostra mostra) {
      if (mostra.thick != 0.0D) {
         double nl = mostra.n(this.lambdaf);
         mostra.ns(this.lambdaf);
         double theta = Math.asin(1.0D / nl * Math.sin(mostra.angle));
         int kmin = (int)Math.ceil(4.0D * nl * mostra.thick * Math.cos(theta) / this.lambdaf);
         nl = mostra.n(this.lambdai);
         theta = Math.asin(1.0D / nl * Math.sin(mostra.angle));
         int kmax = (int)Math.floor(4.0D * nl * mostra.thick * Math.cos(theta) / this.lambdai);
         return (kmax - kmin + 1) * 10 + 20;
      } else {
         return 50;
      }
   }

   void SetLambdas(double[] Lambda, int numint) {
      double li = 1.0D / this.lambdai;
      double lf = 1.0D / this.lambdaf;

      for(int i = 0; i <= numint; ++i) {
         double l = li + (lf - li) / (double)numint * (double)i;
         Lambda[i] = 1.0E9D / l;
      }

   }

   void SetupGrafica() {
      double[] Lambda = new double[this.numint + 1];
      this.SetLambdas(Lambda, this.numint);
      double[][] RT = this.mostra1.getSpectrumRT(this.lambdai, this.lambdaf, this.numint);
      this.graph2DPanel1.resetData();
      this.graph2DPanel1.addData(Lambda, RT[0], this.numint + 1, 1, Color.red);
      this.graph2DPanel1.addData(Lambda, RT[1], this.numint + 1, 1, Color.green);
      this.graph2DPanel1.setLegend("R", 0);
      this.graph2DPanel1.setLegend("T", 1);
      this.graph2DPanel1.flag_legend = 5;
      this.graph2DPanel1.xmin = this.lambdai * 1.0E9D;
      this.graph2DPanel1.xmax = this.lambdaf * 1.0E9D;
      this.graph2DPanel1.ymin = 0.0D;
      this.graph2DPanel1.ymax = 1.0D;
      this.graph2DPanel1.TexteX = "Longitud d'ona (nm)";
      this.graph2DPanel1.TexteY = "R, T";
      this.graph2DPanel1.repaint();
   }

   void UpdateGrafica() {
      this.numint = this.CalcNumInt(this.mostra1);
      double[] Lambda = new double[this.numint + 1];
      this.SetLambdas(Lambda, this.numint);
      double[][] RT = this.mostra1.getSpectrumRT(this.lambdai, this.lambdaf, this.numint);
      this.graph2DPanel1.xmin = this.lambdai * 1.0E9D;
      this.graph2DPanel1.xmax = this.lambdaf * 1.0E9D;
      this.graph2DPanel1.updateData(Lambda, RT[0], this.numint + 1, 0);
      this.graph2DPanel1.updateData(Lambda, RT[1], this.numint + 1, 1);
      if (this.ExcerMode) {
         RT = this.mostra2.getSpectrumRT(this.lambdai, this.lambdaf, this.numint);
         this.graph2DPanel1.updateData(Lambda, RT[0], this.numint + 1, 2);
         this.graph2DPanel1.updateData(Lambda, RT[1], this.numint + 1, 3);
      }

      this.graph2DPanel1.repaint();
   }

   void jRadioButtonLambda_stateChanged(ChangeEvent e) {
      this.LambdaMode = this.jRadioButtonLambda.isSelected();
      this.UpdateMaxMinList(this.jList3, this.mostra1);
   }

   void jButtonExperiment_actionPerformed(ActionEvent e) {
      this.ExcerMode = !this.ExcerMode;
      if (this.ExcerMode) {
         this.jScrollPane1.setPreferredSize(new Dimension(350, 180));
         this.jScrollPane1.setSize(350, 180);
         this.jScrollPane2.setSize(350, 180);
         this.jScrollPane2.setVisible(true);
         this.jTextArea1.setText(this.textExperimentHelp[lang]);
         this.jTextArea1.setEditable(false);
         this.HelpExperiment.setVisible(true);
         this.mostra2.A = Math.random() * 1.0D + 1.4D;
         this.mostra2.B = Math.random() * 3000.0D + 22000.0D;
         this.mostra2.C = 0.0D;
         this.mostra1.C = 0.0D;
         this.mostra2.D = 100.0D;
         this.mostra2.k0 = 0.0D;
         this.mostra2.As = this.mostra1.As;
         this.mostra2.Bs = this.mostra1.Bs;
         this.mostra2.Cs = this.mostra1.Cs;
         this.mostra2.Ds = 100.0D;
         this.mostra2.k0s = 0.0D;
         this.mostra2.angle = this.mostra1.angle = 0.0D;
         this.mostra2.thick = (400.0D + Math.random() * 100.0D) * 1.0E-9D;
         this.sliderTextAngle.setValcur(0.0D);
         this.sliderTextAngle.setEnabled(false);
         this.KCF.setSlidersEnabled(false);
         this.NCF.SetSlidersEnabled(false);
         this.NCF.ZeroCSlider();
         this.jButtonExperiment.setText(this.textShowResult[lang]);
         this.UpdateMaxMinList(this.jList2, this.mostra2);
         int numint2 = this.CalcNumInt(this.mostra2);
         double[] Lambda = new double[numint2 + 1];
         this.SetLambdas(Lambda, numint2);
         double[][] RT = this.mostra2.getSpectrumRT(this.lambdai, this.lambdaf, numint2);
         this.graph2DPanel1.addData(Lambda, RT[0], numint2 + 1, 1, Color.cyan);
         this.graph2DPanel1.addData(Lambda, RT[1], numint2 + 1, 1, Color.yellow);
         this.graph2DPanel1.setLegend("R exp", 2);
         this.graph2DPanel1.setLegend("T exp", 3);
         this.graph2DPanel1.repaint();
      } else {
         this.jScrollPane1.setPreferredSize(new Dimension(350, 360));
         this.jScrollPane1.setSize(350, 360);
         this.jScrollPane2.setSize(350, 0);
         this.jScrollPane2.setVisible(false);
         DecimalFormat df = new DecimalFormat("#.###");
         this.jTextArea1.setText("");
         new String();
         String Mess = this.textMostraProb[lang];
         Mess = Mess + this.textGruixResult[lang] + " = " + df.format(this.mostra2.thick * 1.0E9D) + " nm\n";
         Mess = Mess + "A     = " + df.format(this.mostra2.A) + "\n";
         Mess = Mess + "B     = " + df.format(this.mostra2.B) + "\n\n";
         Mess = Mess + this.textValorsTrob[lang] + "\n";
         Mess = Mess + this.textGruixResult[lang] + " = " + df.format(this.mostra1.thick * 1.0E9D) + " nm\n";
         Mess = Mess + "A     = " + Double.toString(this.mostra1.A) + "\n";
         Mess = Mess + "B     = " + Double.toString(this.mostra1.B) + "\n\n";
         this.jTextArea1.setText(Mess);
         this.HelpExperiment.setVisible(true);
         this.jButtonExperiment.setText(this.textExperiment[lang]);
         this.SetupGrafica();
         this.sliderTextAngle.setEnabled(true);
         this.KCF.setSlidersEnabled(true);
         this.NCF.SetSlidersEnabled(true);
      }

      this.jPanel11.validate();
      this.jPanel11.repaint();
   }

   void graph2DPanel1_mouseMoved(MouseEvent e) {
      double[] xyval = new double[2];
      if (this.graph2DPanel1.getXY(e.getX(), e.getY(), xyval)) {
         String texte = this.textWLength[lang] + ": " + this.graph2DPanel1.FormatX.format(xyval[0]) + " nm";
         this.jLabel1.setText(texte);
         texte = this.textOrdenada[lang] + ": " + this.graph2DPanel1.FormatX.format(xyval[1]);
         this.jLabel2.setText(texte);
      }

   }

   void jButtonGraphConfig_actionPerformed(ActionEvent e) {
      DecimalFormatSymbols DFS = new DecimalFormatSymbols();
      DFS.setDecimalSeparator('.');
      DecimalFormat DF = this.graph2DPanel1.FormatY;
      DF.setDecimalFormatSymbols(DFS);
      this.ConfigMode = !this.ConfigMode;
      if (this.ConfigMode) {
         this.jTextFieldLambdaIni.setText(this.graph2DPanel1.FormatX.format(this.lambdai * 1.0E9D));
         this.jTextFieldLambdaFin.setText(this.graph2DPanel1.FormatX.format(this.lambdaf * 1.0E9D));
         this.jTextFieldTIni.setText(DF.format(this.graph2DPanel1.ymin));
         this.jTextFieldTFin.setText(DF.format(this.graph2DPanel1.ymax));
         ((CardLayout)this.jPanel13.getLayout()).show(this.jPanel13, "jPanel14");
         this.jButtonGraphConfig.setText("Ok");
         this.jLabel1.setVisible(false);
         this.jLabel2.setVisible(false);
      } else {
         double auxmin = Double.parseDouble(this.jTextFieldLambdaIni.getText());
         double auxmax = Double.parseDouble(this.jTextFieldLambdaFin.getText());
         if (auxmax >= auxmin + 1.0D) {
            this.graph2DPanel1.xmin = auxmin;
            this.graph2DPanel1.xmax = auxmax;
            this.lambdai = auxmin / 1.0E9D;
            this.lambdaf = auxmax / 1.0E9D;
            this.sliderTextWavelength.setRange(auxmin, auxmax);
         }

         auxmin = Double.parseDouble(this.jTextFieldTIni.getText());
         auxmax = Double.parseDouble(this.jTextFieldTFin.getText());
         if (auxmax > auxmin) {
            this.graph2DPanel1.ymin = auxmin;
            this.graph2DPanel1.ymax = auxmax;
         }

         ((CardLayout)this.jPanel13.getLayout()).show(this.jPanel13, "graph2DPanel1");
         this.jButtonGraphConfig.setText(this.textCongigGraph[lang]);
         this.jLabel1.setVisible(true);
         this.jLabel2.setVisible(true);
         this.UpdateGrafica();
      }

   }

   void jButton4_actionPerformed(ActionEvent e) {
      this.graph2DPanel1.ymin = 0.0D;
      this.graph2DPanel1.ymax = 1.0D;
      this.ConfigMode = false;
      ((CardLayout)this.jPanel13.getLayout()).show(this.jPanel13, "graph2DPanel1");
      this.jButtonGraphConfig.setText(this.textCongigGraph[lang]);
      this.jLabel1.setVisible(true);
      this.jLabel2.setVisible(true);
   }

   void jButton5_actionPerformed(ActionEvent e) {
      this.graph2DPanel1.LimitsY();
      this.ConfigMode = false;
      ((CardLayout)this.jPanel13.getLayout()).show(this.jPanel13, "graph2DPanel1");
      this.jButtonGraphConfig.setText(this.textCongigGraph[lang]);
      this.jLabel1.setVisible(true);
      this.jLabel2.setVisible(true);
   }

   void jButton6_actionPerformed(ActionEvent e) {
      this.graph2DPanel1.LimitsY(0);
      this.ConfigMode = false;
      ((CardLayout)this.jPanel13.getLayout()).show(this.jPanel13, "graph2DPanel1");
      this.jButtonGraphConfig.setText(this.textCongigGraph[lang]);
      this.jLabel1.setVisible(true);
      this.jLabel2.setVisible(true);
   }

   void jButton7_actionPerformed(ActionEvent e) {
      this.graph2DPanel1.LimitsY(1);
      this.ConfigMode = false;
      ((CardLayout)this.jPanel13.getLayout()).show(this.jPanel13, "graph2DPanel1");
      this.jButtonGraphConfig.setText(this.textCongigGraph[lang]);
      this.jLabel1.setVisible(true);
      this.jLabel2.setVisible(true);
   }

   void HelpExperiment_windowClosing(WindowEvent e) {
      this.HelpExperiment.setVisible(false);
   }

   void jButtonHelpExpOk_actionPerformed(ActionEvent e) {
      this.HelpExperiment.setVisible(false);
   }

   void jButton1_actionPerformed(ActionEvent e) {
      ImageIcon icon_joc = null;

      try {
         String st_icon = "jocon.jpg";
         URL url_icon = this.getClass().getResource(st_icon);
         icon_joc = new ImageIcon(url_icon, "Interferencias de Young");
      } catch (Exception var7) {
         System.out.println("No carga icono");
      }

      Frame f = new Frame();
      Object[] options = new Object[]{"Ok"};
      JOptionPane hola = new JOptionPane(this.rollo[lang], 1, -1, icon_joc, options);
      JDialog dialog = hola.createDialog(f, this.textAbout[lang]);
      dialog.setResizable(false);
      dialog.show();
      this.repaint();
   }

   void jRadioButton2_stateChanged(ChangeEvent e) {
      this.mostra1.UpdateSubstrate(this.jRadioButton2.isSelected());
      this.repaint();
   }
}
