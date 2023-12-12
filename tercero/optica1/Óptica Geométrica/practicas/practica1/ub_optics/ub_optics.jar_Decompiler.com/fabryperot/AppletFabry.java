package fabryperot;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AppletFabry extends JApplet {
   static FabryCalc fabry1 = new FabryCalc();
   static FabryCalc fabry2 = new FabryCalc();
   double lambdai = 380.0D;
   double lambdaf = 780.0D;
   double AOI_i = 0.0D;
   double AOI_f = 90.0D;
   double thick_max = 1.0E-6D;
   boolean ListVarMode = true;
   boolean EthalonMode = false;
   double[][] Spectrum;
   int numint = 50;
   int numR;
   int numT;
   private String[] gruix = new String[]{"Gruix         ", "Espesor       ", "Thickness     "};
   private String[] wleng1 = new String[]{"Long. d'ona, L", "Long. de onda, L", "Wavelength,  L"};
   private String[] wleng2 = new String[]{"Delta L ", "Delta L ", "Delta L "};
   private String[] angle = new String[]{"Angle d'inciden.", "Angulo de incid.", "Angle of incid."};
   private String[] textLabelMidaFont = new String[]{"Mida Font", "Tamaño Fuente", "Source Size"};
   private String[] textLabelFocalFont = new String[]{"Focal Lent 1", "Focal Lente 1", "Lens 1 Focal"};
   private String[] textLabelMidaPantalla = new String[]{"Mida Pantalla", "Tamaño Pantalla", "Screen Size"};
   private String[] textLabelFocalPantalla = new String[]{"Focal Lent 2", "Focal Lente 2", "Lens 2 Focal"};
   private String[] textTransiRefle = new String[]{"Transmissió i Reflexió", "Transmisión y Reflexión", "Transmission and Reflection"};
   private String[] textDifFase = new String[]{"Diferència de Fase", "Diferencia de Fase", "Phase Difference"};
   private String[] textExit = new String[]{"Sortir", "Salir", "Exit"};
   private String[] textAbout = new String[]{"Quant a", "Acerca de", "About"};
   private String[] textHelp = new String[]{"Resum de Teoria", "Resumen de Teoría", "Theory Summary"};
   private String[] textExperiment = new String[]{"Experimenta", "Experimenta", "Experiment"};
   private String[] textVarAngle = new String[]{"Variable: Angle           ", "Variable: Ángulo          ", "Variable: Angle           "};
   private String[] textVarLambda = new String[]{"Variable: distància a l'eix òptic", "Variable: distancia al eje óptico", "Variable: distance to the optical axis"};
   private String[] textCongigGraph = new String[]{"Mides i Focals", "Tamaños y Focales", "Focal Lengths and Sizes"};
   private String[] textXPantalla = new String[]{"Posició a la Pantalla", "Posición en la Pantalla", "Screen position"};
   private String[] textYGraph = new String[]{"I (U.A.)", "I (U.A.)", "I (A.U.)"};
   private String[] textAnglePantalla = new String[]{"Angle corresponent", "Ángulo correspondiente", "Corresponding angle:"};
   private String[] textOrdenada = new String[]{"I (U.A.)", "I (U.A.)", "I (A.U.)"};
   private String[] textWLengthIni = new String[]{"Longitud d'ona inicial (nm)", "Longitud de onda inicial (nm)", "Spectrum Initial  Wavelength (nm)"};
   private String[] textWLengthFin = new String[]{"Longitud d'ona final (nm)", "Longitud de onda final (nm)", "Spectrum Final Wavelength (nm)"};
   private String[] textTRIni = new String[]{"Valor de T i R inicial", "Valor de T y R inicial", "Initial T and R value"};
   private String[] textTRFin = new String[]{"Valor de T i R final", "Valor de T y R final", "Final T and R value"};
   private String[] textRange01 = new String[]{"Rang de R/T 0-1", "Rango de R/T 0-1", "R/T Range 0-1"};
   private String[] textAutoRange = new String[]{"Rang Automàtic", "Auto Rango", "Auto Range"};
   private String[] textAutoRangeR = new String[]{"Rang Automàtic R", "Auto Rango R", "Auto Range R"};
   private String[] textAutoRangeT = new String[]{"Rang Automàtic T", "Auto Rango T", "Auto Range T"};
   private String[] textGrafica = new String[]{"Intensitat", "Intensidad", "Intensity"};
   private String[] textTMaxMin = new String[]{"Màxims i Mínims de T", "Máximos y Mínimos de T", "T Maxima and Minima"};
   private static String[] rollo = new String[]{"Applet d'Interferòmetre de Fabry-Pérot\nGrup d'Innovació Docent en Òptica Física i Fotònica\nUniversitat de Barcelona, 2003\nLa utilització d'aquest programa està sota una llicència de Creative Commons\nVeure condicions a http://www.publicacions.ub.es/doi/licencia/resum-deriv.htm\n\nCurs d'Òptica en Java DOI: 10.1344/401.000000050\nApplet de Fibres Òptiques DOI: 10.1344/203.000000100", "Applet de Interferómetro de Fabry-Pérot\nGrup d'Innovació Docent en Òptica Física i Fotònica \nUniversitat de Barcelona, 2003\nLa utilización de este programa está sujeta a una licencia de Creative Commons\nVer condiciones en http://creativecommons.org/license/by-nc-sa2.0/\n\nCurso de Óptica en Java DOI: 10.1344/401.000000050\nApplet de Fibras Ópticas DOI: 10.1344/203.000000100", "Fabry-Pérot Interferometer Applet\n Grup d'Innovació Docent en Òptica Física i Fotònica\nUniversitat de Barcelona, 2003\nThe use of this program is under a Creative Commons license\nSee conditions in http://creativecommons.org/license/by-nc-sa2.0/\n\nJava Optics Course DOI: 10.1344/401.000000050\nOptical Fibers Applet DOI: 10.1344/203.000000100"};
   private String[] textShowResult = new String[]{"Mostra Resultat", "Mostrar Resultado", "Show Result"};
   private String[] textValorsTrob = new String[]{"Valors trobats: ", "Valores encontrados: ", "Found values: "};
   static String[] textTitol = new String[]{"Applet d'Interferòmetre de Fabry-Pérot", "Applet de Interferómetro de Fabry-Pérot", "Fabry-Pérot Interferometer Applet "};
   static String[] textImatge = new String[]{"Imatge", "Imagen", "Image"};
   static String[] textGrafic = new String[]{"Gràfica", "Grafica", "Graph"};
   static int lang = 0;
   private boolean isStandalone = false;
   String var0;
   private JPanel jPanel1 = new JPanel();
   private JButton jButtonExit = new JButton();
   private JButton jButtonHelp = new JButton();
   private Frame HelpExperiment = new Frame();
   int a = 0;
   private JPanel jPanel5 = new JPanel();
   private JPanel jPanel3 = new JPanel();
   private JPanel jPanel2 = new JPanel();
   private FlowLayout flowLayout1 = new FlowLayout();
   private SliderText sliderTextThickness = new SliderText();
   private JPanel jPanelRTvsLambda = new JPanel();
   private BorderLayout borderLayout1 = new BorderLayout();
   private JTabbedPane jTabbedPane1 = new JTabbedPane();
   private PanelFabry panelFabry2 = new PanelFabry();
   private PanelFabry panelFabry1 = new PanelFabry();
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
   private JTextArea jTextArea1 = new JTextArea();
   private JPanel jPanel20 = new JPanel();
   private JButton jButtonHelpExpOk = new JButton();
   private JButton jButton1 = new JButton();
   private JPanel jPanel6 = new JPanel();
   private JPanel jPanel7 = new JPanel();
   private SliderText sliderTextWL1 = new SliderText();
   private JPanel jPanel8 = new JPanel();
   private SliderText sliderTextWL2 = new SliderText();
   private CardLayout cardLayout2 = new CardLayout();
   private SliderText sliderTextR = new SliderText();
   private SliderText sliderTextAngle = new SliderText();
   private MidesFrame midesFrame1;
   private EthalonPanel ethalonPanel1;
   private JButton jButton2;
   private JLabel jLabel3;
   private JLabel jLabel4;
   private JLabel jLabel5;
   private HelpFrame helpFrame1;

   public String getParameter(String key, String def) {
      return this.isStandalone ? System.getProperty(key, def) : (this.getParameter(key) != null ? this.getParameter(key) : def);
   }

   public AppletFabry() {
      this.midesFrame1 = new MidesFrame(fabry1);
      this.ethalonPanel1 = new EthalonPanel();
      this.jButton2 = new JButton();
      this.jLabel3 = new JLabel();
      this.jLabel4 = new JLabel();
      this.jLabel5 = new JLabel();
      this.helpFrame1 = new HelpFrame();
      DecimalFormat df = new DecimalFormat("####");
      DecimalFormat df2 = new DecimalFormat("######");
      String S1 = df.format(0L);
      String S2 = df.format(fabry1.thick_max);
      this.sliderTextThickness = new SliderText(0.0D, fabry1.thick_max, fabry1.thick, this.gruix[lang], "mm", S1, S2);
      this.sliderTextThickness.setStep(25000);
      S1 = df2.format(this.lambdai);
      S2 = df2.format(this.lambdaf);
      this.sliderTextWL1 = new SliderText(this.lambdai, this.lambdaf, fabry1.lambda, this.wleng1[lang], "nm", S1, S2);
      this.sliderTextWL1.setStep(400000);
      this.sliderTextWL1.DF = new DecimalFormat("###.###");
      S1 = df.format(0.0D);
      S2 = df.format(1.0D);
      this.sliderTextWL2 = new SliderText(0.0D, 1.0D, 0.01D, this.wleng2[lang], "nm", "0", "1.0");
      this.sliderTextWL2.setStep(1000);
      this.sliderTextWL2.DF = new DecimalFormat("#.####");
      S1 = df.format(this.AOI_i);
      S2 = df.format(this.AOI_f);
      this.sliderTextAngle = new SliderText(this.AOI_i, this.AOI_f, 0.0D, this.angle[lang], "º", S1, S2);
      this.sliderTextAngle.setStep(90);
      this.sliderTextR = new SliderText(0.0D, 1.0D, fabry1.r, "r", "", "0", "1");
      this.sliderTextR.setStep(1000);
      this.sliderTextR.DF = new DecimalFormat(".####");
      this.HelpExperiment.setSize(440, 320);
   }

   public void init() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      this.jTabbedPane1.setTitleAt(0, this.textTransiRefle[lang]);
      this.jTabbedPane1.setTitleAt(1, this.textDifFase[lang]);
      this.jTabbedPane2.setTitleAt(0, this.textGrafica[lang]);
      this.jTabbedPane2.setTitleAt(1, this.textTMaxMin[lang]);
      this.graph2DPanel1.backColor = Color.blue;
      this.graph2DPanel1.axisColor = Color.white;
      this.SetupGrafica();
      this.sliderTextThickness.label_titol.setText(this.gruix[lang]);
      this.sliderTextAngle.label_titol.setText(this.angle[lang]);
      this.sliderTextWL1.label_titol.setText(this.wleng1[lang]);
      this.jButtonExit.setText(this.textExit[lang]);
      this.jButtonHelp.setText(this.textHelp[lang]);
      this.jRadioButtonAngle.setText(this.textVarAngle[lang]);
      this.jRadioButtonLambda.setText(this.textVarLambda[lang]);
      this.jButtonGraphConfig.setText(this.textCongigGraph[lang]);
      this.jLabel1.setText(this.textXPantalla[lang]);
      this.jLabel2.setText(this.textOrdenada[lang]);
      this.jLabel5.setText(this.textAnglePantalla[lang]);
      this.HelpExperiment.setTitle(this.textExperiment[lang]);
      this.jButton1.setText(this.textAbout[lang]);
      this.graph2DPanel1.TexteX = this.textXPantalla[lang] + " (mm)";
      this.graph2DPanel1.TexteY = this.textYGraph[lang];
      this.jButton2.setText(textImatge[lang]);
      this.midesFrame1.setTitle(this.textCongigGraph[lang]);
      this.midesFrame1.sliderTextSourceSize.label_titol.setText(this.textLabelMidaFont[lang]);
      this.midesFrame1.sliderTextSourceFocal.label_titol.setText(this.textLabelFocalFont[lang]);
      this.midesFrame1.sliderTextScreenSize.label_titol.setText(this.textLabelMidaPantalla[lang]);
      this.midesFrame1.sliderTextScreenFocal.label_titol.setText(this.textLabelFocalPantalla[lang]);
   }

   private void jbInit() throws Exception {
      this.jButtonExit.setActionCommand("jButtonExit");
      this.jButtonExit.setText("Sortir");
      this.jButtonExit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            AppletFabry.this.jButtonExit_actionPerformed(e);
         }
      });
      this.jButtonHelp.setText("Ajut");
      this.jButtonHelp.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            AppletFabry.this.jButtonHelp_actionPerformed(e);
         }
      });
      this.jPanel1.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel5.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanel5.setMaximumSize(new Dimension(380, 225));
      this.jPanel5.setMinimumSize(new Dimension(380, 225));
      this.jPanel5.setPreferredSize(new Dimension(380, 225));
      this.jPanel5.setLayout(this.flowLayout1);
      this.jPanel3.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel3.setMaximumSize(new Dimension(365, 530));
      this.jPanel3.setMinimumSize(new Dimension(365, 530));
      this.jPanel3.setPreferredSize(new Dimension(365, 530));
      this.jPanel2.setAlignmentX(0.0F);
      this.jPanel2.setAlignmentY(0.0F);
      this.jPanel2.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel2.setMaximumSize(new Dimension(385, 530));
      this.jPanel2.setMinimumSize(new Dimension(385, 530));
      this.jPanel2.setPreferredSize(new Dimension(385, 530));
      this.flowLayout1.setHgap(1);
      this.flowLayout1.setVgap(1);
      this.sliderTextThickness.setBorder(BorderFactory.createLineBorder(Color.black));
      this.sliderTextThickness.setMaximumSize(new Dimension(360, 48));
      this.sliderTextThickness.setMinimumSize(new Dimension(360, 48));
      this.sliderTextThickness.setPreferredSize(new Dimension(360, 48));
      this.jPanelRTvsLambda.setAlignmentX(0.0F);
      this.jPanelRTvsLambda.setAlignmentY(0.0F);
      this.jPanelRTvsLambda.setMaximumSize(new Dimension(750, 500));
      this.jPanelRTvsLambda.setMinimumSize(new Dimension(750, 500));
      this.jPanelRTvsLambda.setPreferredSize(new Dimension(750, 500));
      this.jPanelRTvsLambda.setLayout(this.borderLayout1);
      this.panelFabry1.setMaximumSize(new Dimension(360, 230));
      this.panelFabry1.setMinimumSize(new Dimension(360, 230));
      this.panelFabry1.setPreferredSize(new Dimension(360, 230));
      this.panelFabry1.setToolTipText("");
      this.panelFabry1.addComponentListener(new ComponentAdapter() {
         public void componentShown(ComponentEvent e) {
            AppletFabry.this.panelFabry1_componentShown(e);
         }
      });
      this.panelFabry2.setMaximumSize(new Dimension(360, 230));
      this.panelFabry2.setMinimumSize(new Dimension(360, 230));
      this.panelFabry2.setPreferredSize(new Dimension(360, 230));
      this.panelFabry2.addComponentListener(new ComponentAdapter() {
         public void componentShown(ComponentEvent e) {
            AppletFabry.this.panelFabry2_componentShown(e);
         }
      });
      this.jTabbedPane2.setMaximumSize(new Dimension(360, 490));
      this.jTabbedPane2.setMinimumSize(new Dimension(360, 490));
      this.jTabbedPane2.setPreferredSize(new Dimension(360, 490));
      this.jPanel11.addComponentListener(new ComponentAdapter() {
         public void componentShown(ComponentEvent e) {
            AppletFabry.this.jPanel11_componentShown(e);
         }
      });
      this.jScrollPane1.setName("");
      this.jScrollPane1.setMaximumSize(new Dimension(350, 160));
      this.jScrollPane1.setMinimumSize(new Dimension(350, 160));
      this.jScrollPane1.setPreferredSize(new Dimension(350, 160));
      this.jScrollPane2.setMaximumSize(new Dimension(350, 160));
      this.jScrollPane2.setMinimumSize(new Dimension(350, 160));
      this.jScrollPane2.setPreferredSize(new Dimension(350, 160));
      this.jPanel12.setName("");
      this.jPanel12.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel12.setMaximumSize(new Dimension(250, 70));
      this.jPanel12.setMinimumSize(new Dimension(250, 70));
      this.jPanel12.setPreferredSize(new Dimension(250, 70));
      this.jRadioButtonAngle.setMaximumSize(new Dimension(240, 25));
      this.jRadioButtonAngle.setMinimumSize(new Dimension(240, 25));
      this.jRadioButtonAngle.setPreferredSize(new Dimension(240, 25));
      this.jRadioButtonAngle.setText("Variable: Angle");
      this.jRadioButtonLambda.setMaximumSize(new Dimension(240, 25));
      this.jRadioButtonLambda.setMinimumSize(new Dimension(240, 25));
      this.jRadioButtonLambda.setPreferredSize(new Dimension(240, 25));
      this.jRadioButtonLambda.setContentAreaFilled(true);
      this.jRadioButtonLambda.setMnemonic('0');
      this.jRadioButtonLambda.setSelected(true);
      this.jRadioButtonLambda.setText("Variable: Position the Screen");
      this.jRadioButtonLambda.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            AppletFabry.this.jRadioButtonLambda_stateChanged(e);
         }
      });
      this.jButtonGraphConfig.setText("Configurar Gràfica");
      this.jButtonGraphConfig.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            AppletFabry.this.jButtonGraphConfig_actionPerformed(e);
         }
      });
      this.jLabel1.setMaximumSize(new Dimension(300, 17));
      this.jLabel1.setMinimumSize(new Dimension(300, 17));
      this.jLabel1.setPreferredSize(new Dimension(300, 17));
      this.jLabel1.setText("Posició a la pantalla: ");
      this.jLabel2.setMaximumSize(new Dimension(300, 17));
      this.jLabel2.setMinimumSize(new Dimension(300, 17));
      this.jLabel2.setPreferredSize(new Dimension(300, 17));
      this.jLabel2.setText("Ordenada: ");
      this.graph2DPanel1.setMaximumSize(new Dimension(350, 350));
      this.graph2DPanel1.setMinimumSize(new Dimension(350, 350));
      this.graph2DPanel1.setPreferredSize(new Dimension(350, 350));
      this.graph2DPanel1.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseMoved(MouseEvent e) {
            AppletFabry.this.graph2DPanel1_mouseMoved(e);
         }
      });
      this.jPanel13.setLayout(this.cardLayout1);
      this.HelpExperiment.setTitle("Experiment");
      this.HelpExperiment.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            AppletFabry.this.HelpExperiment_windowClosing(e);
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
            AppletFabry.this.jButtonHelpExpOk_actionPerformed(e);
         }
      });
      this.jButton1.setText("Quant a");
      this.jButton1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            AppletFabry.this.jButton1_actionPerformed(e);
         }
      });
      this.jPanel6.setMaximumSize(new Dimension(360, 50));
      this.jPanel6.setMinimumSize(new Dimension(360, 50));
      this.jPanel6.setPreferredSize(new Dimension(360, 50));
      this.jPanel6.setLayout(this.cardLayout2);
      this.sliderTextWL1.setPreferredSize(new Dimension(360, 48));
      this.sliderTextWL1.setMinimumSize(new Dimension(360, 48));
      this.sliderTextWL1.setMaximumSize(new Dimension(360, 48));
      this.sliderTextWL1.setAlignmentX(0.0F);
      this.sliderTextWL1.setAlignmentY(0.0F);
      this.sliderTextWL1.setBorder(BorderFactory.createLineBorder(Color.black));
      this.sliderTextWL1.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            AppletFabry.this.sliderTextWL1_stateChanged(e);
         }
      });
      this.sliderTextWL2.setAlignmentX(0.0F);
      this.sliderTextWL2.setAlignmentY(0.0F);
      this.sliderTextWL2.setBorder(BorderFactory.createLineBorder(Color.black));
      this.sliderTextWL2.setMaximumSize(new Dimension(360, 48));
      this.sliderTextWL2.setMinimumSize(new Dimension(360, 48));
      this.sliderTextWL2.setPreferredSize(new Dimension(360, 48));
      this.sliderTextWL2.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            AppletFabry.this.sliderTextWL2_stateChanged(e);
         }
      });
      this.jPanel7.setAlignmentX(0.0F);
      this.jPanel7.setAlignmentY(0.0F);
      this.jPanel7.setMaximumSize(new Dimension(360, 53));
      this.jPanel7.setMinimumSize(new Dimension(360, 53));
      this.jPanel7.setPreferredSize(new Dimension(360, 53));
      this.jPanel8.setAlignmentX(0.0F);
      this.jPanel8.setAlignmentY(0.0F);
      this.jPanel8.setMaximumSize(new Dimension(360, 53));
      this.jPanel8.setMinimumSize(new Dimension(360, 53));
      this.jPanel8.setPreferredSize(new Dimension(360, 53));
      this.sliderTextR.setMaximumSize(new Dimension(360, 48));
      this.sliderTextR.setBorder(BorderFactory.createLineBorder(Color.black));
      this.sliderTextR.setMinimumSize(new Dimension(360, 48));
      this.sliderTextR.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            AppletFabry.this.sliderTextR_stateChanged(e);
         }
      });
      this.sliderTextR.setPreferredSize(new Dimension(360, 48));
      this.sliderTextR.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            AppletFabry.this.sliderTextR_stateChanged(e);
         }
      });
      this.sliderTextAngle.setPreferredSize(new Dimension(360, 48));
      this.sliderTextAngle.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            AppletFabry.this.sliderTextAngle_stateChanged(e);
         }
      });
      this.sliderTextAngle.setMinimumSize(new Dimension(360, 48));
      this.sliderTextAngle.setAlignmentX(0.0F);
      this.sliderTextAngle.setAlignmentY(0.0F);
      this.sliderTextAngle.setBorder(BorderFactory.createLineBorder(Color.black));
      this.sliderTextAngle.setMaximumSize(new Dimension(360, 48));
      this.midesFrame1.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            AppletFabry.this.midesFrame1_stateChanged(e);
         }
      });
      this.ethalonPanel1.setMaximumSize(new Dimension(350, 350));
      this.ethalonPanel1.setMinimumSize(new Dimension(350, 350));
      this.ethalonPanel1.setPreferredSize(new Dimension(350, 350));
      this.ethalonPanel1.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseMoved(MouseEvent e) {
            AppletFabry.this.ethalonPanel1_mouseMoved(e);
         }
      });
      this.jButton2.setText("Imatge");
      this.jButton2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            AppletFabry.this.jButton2_actionPerformed(e);
         }
      });
      this.jLabel3.setHorizontalAlignment(11);
      this.jLabel3.setText("L1");
      this.jLabel4.setToolTipText("");
      this.jLabel4.setText("L2");
      this.jLabel5.setMaximumSize(new Dimension(300, 17));
      this.jLabel5.setMinimumSize(new Dimension(300, 17));
      this.jLabel5.setPreferredSize(new Dimension(300, 17));
      this.jLabel5.setText("Angle");
      this.jPanel4.setMinimumSize(new Dimension(1238, 370));
      this.jPanel4.setPreferredSize(new Dimension(1238, 370));
      this.jPanel1.add(this.jButton1, (Object)null);
      this.jPanel1.add(this.jButtonHelp, (Object)null);
      this.jPanel1.add(this.jButtonExit, (Object)null);
      this.getContentPane().add(this.jPanelRTvsLambda, "North");
      this.jTabbedPane2.add(this.jPanel4, "Gràfica");
      this.jPanel4.add(this.jPanel13, (Object)null);
      this.jPanel13.add(this.graph2DPanel1, "graph2DPanel1");
      this.jPanel13.add(this.ethalonPanel1, "ethalonPanel1");
      this.jPanel4.add(this.jLabel1, (Object)null);
      this.jPanel4.add(this.jLabel5, (Object)null);
      this.jPanel4.add(this.jLabel2, (Object)null);
      this.jPanel4.add(this.jButtonGraphConfig, (Object)null);
      this.jPanel4.add(this.jButton2, (Object)null);
      this.jTabbedPane2.add(this.jPanel11, "Maxims i mínims");
      this.jPanel11.add(this.jLabel3, (Object)null);
      this.jPanel11.add(this.jScrollPane1, (Object)null);
      this.jPanel11.add(this.jLabel4, (Object)null);
      this.jScrollPane1.getViewport().add(this.jList3, (Object)null);
      this.jPanel11.add(this.jScrollPane2, (Object)null);
      this.jPanel11.add(this.jPanel12, (Object)null);
      this.jPanel12.add(this.jRadioButtonLambda, (Object)null);
      this.jPanel12.add(this.jRadioButtonAngle, (Object)null);
      this.jScrollPane2.getViewport().add(this.jList2, (Object)null);
      this.getContentPane().add(this.jPanel1, "South");
      this.jPanelRTvsLambda.add(this.jPanel2, "West");
      this.jTabbedPane1.add(this.panelFabry1, "Transmissió i Reflexió");
      this.jTabbedPane1.add(this.panelFabry2, "Diferència de Fase");
      this.jPanel2.add(this.jTabbedPane1, (Object)null);
      this.jPanel2.add(this.jPanel5, (Object)null);
      this.jPanel5.add(this.sliderTextThickness, (Object)null);
      this.jPanel5.add(this.jPanel6, (Object)null);
      this.jPanel6.add(this.sliderTextR, "sliderTextR");
      this.jPanel6.add(this.sliderTextAngle, "sliderTextAngle");
      this.jPanel5.add(this.jPanel7, (Object)null);
      this.jPanel7.add(this.sliderTextWL1, (Object)null);
      this.jPanel5.add(this.jPanel8, (Object)null);
      this.jPanel8.add(this.sliderTextWL2, (Object)null);
      this.jPanelRTvsLambda.add(this.jPanel3, "Center");
      this.jPanel3.add(this.jTabbedPane2, (Object)null);
      this.sliderTextWL1.jPanelLabel.setPreferredSize(new Dimension(80, 30));
      this.sliderTextWL1.jPanelLabel.setMaximumSize(new Dimension(80, 30));
      this.sliderTextWL1.jPanelLabel.setMinimumSize(new Dimension(80, 30));
      this.sliderTextWL2.jPanelLabel.setPreferredSize(new Dimension(80, 30));
      this.sliderTextWL2.jPanelLabel.setMaximumSize(new Dimension(80, 30));
      this.sliderTextWL2.jPanelLabel.setMinimumSize(new Dimension(80, 30));
      this.sliderTextThickness.jPanelLabel.setPreferredSize(new Dimension(80, 30));
      this.sliderTextThickness.jPanelLabel.setMaximumSize(new Dimension(80, 30));
      this.sliderTextThickness.jPanelLabel.setMinimumSize(new Dimension(80, 30));
      this.sliderTextAngle.jPanelLabel.setPreferredSize(new Dimension(80, 30));
      this.sliderTextAngle.jPanelLabel.setMaximumSize(new Dimension(80, 30));
      this.sliderTextAngle.jPanelLabel.setMinimumSize(new Dimension(80, 30));
      this.sliderTextR.jPanelLabel.setPreferredSize(new Dimension(80, 30));
      this.sliderTextR.jPanelLabel.setMaximumSize(new Dimension(80, 30));
      this.sliderTextR.jPanelLabel.setMinimumSize(new Dimension(80, 30));
      this.sliderTextWL1.jPanelSlider.setPreferredSize(new Dimension(170, 40));
      this.sliderTextWL1.jPanelSlider.setMaximumSize(new Dimension(170, 40));
      this.sliderTextWL1.jPanelSlider.setMinimumSize(new Dimension(170, 40));
      this.sliderTextWL2.jPanelSlider.setPreferredSize(new Dimension(170, 40));
      this.sliderTextWL2.jPanelSlider.setMaximumSize(new Dimension(170, 40));
      this.sliderTextWL2.jPanelSlider.setMinimumSize(new Dimension(170, 40));
      this.sliderTextThickness.jPanelSlider.setPreferredSize(new Dimension(170, 40));
      this.sliderTextThickness.jPanelSlider.setMaximumSize(new Dimension(170, 40));
      this.sliderTextThickness.jPanelSlider.setMinimumSize(new Dimension(170, 40));
      this.sliderTextAngle.jPanelSlider.setPreferredSize(new Dimension(170, 40));
      this.sliderTextAngle.jPanelSlider.setMaximumSize(new Dimension(170, 40));
      this.sliderTextAngle.jPanelSlider.setMinimumSize(new Dimension(170, 40));
      this.sliderTextR.jPanelSlider.setPreferredSize(new Dimension(170, 40));
      this.sliderTextR.jPanelSlider.setMaximumSize(new Dimension(170, 40));
      this.sliderTextR.jPanelSlider.setMinimumSize(new Dimension(170, 40));
      this.sliderTextWL1.texte.setPreferredSize(new Dimension(45, 20));
      this.sliderTextWL1.texte.setMaximumSize(new Dimension(45, 20));
      this.sliderTextWL1.texte.setMinimumSize(new Dimension(45, 20));
      this.sliderTextWL2.texte.setPreferredSize(new Dimension(35, 20));
      this.sliderTextWL2.texte.setMaximumSize(new Dimension(35, 20));
      this.sliderTextWL2.texte.setMinimumSize(new Dimension(35, 20));
      this.sliderTextThickness.texte.setPreferredSize(new Dimension(35, 20));
      this.sliderTextThickness.texte.setMaximumSize(new Dimension(35, 20));
      this.sliderTextThickness.texte.setMinimumSize(new Dimension(35, 20));
      this.sliderTextAngle.texte.setPreferredSize(new Dimension(35, 20));
      this.sliderTextAngle.texte.setMaximumSize(new Dimension(35, 20));
      this.sliderTextAngle.texte.setMinimumSize(new Dimension(35, 20));
      this.sliderTextR.texte.setPreferredSize(new Dimension(35, 20));
      this.sliderTextR.texte.setMaximumSize(new Dimension(35, 20));
      this.sliderTextR.texte.setMinimumSize(new Dimension(35, 20));
      this.sliderTextWL1.slider.setPreferredSize(new Dimension(90, 30));
      this.sliderTextWL1.slider.setMaximumSize(new Dimension(90, 30));
      this.sliderTextWL1.slider.setMinimumSize(new Dimension(90, 30));
      this.sliderTextWL2.slider.setPreferredSize(new Dimension(90, 30));
      this.sliderTextWL2.slider.setMaximumSize(new Dimension(90, 30));
      this.sliderTextWL2.slider.setMinimumSize(new Dimension(90, 30));
      this.sliderTextThickness.slider.setPreferredSize(new Dimension(90, 30));
      this.sliderTextThickness.slider.setMaximumSize(new Dimension(90, 30));
      this.sliderTextThickness.slider.setMinimumSize(new Dimension(90, 30));
      this.sliderTextAngle.slider.setPreferredSize(new Dimension(90, 30));
      this.sliderTextAngle.slider.setMaximumSize(new Dimension(90, 30));
      this.sliderTextAngle.slider.setMinimumSize(new Dimension(90, 30));
      this.sliderTextR.slider.setPreferredSize(new Dimension(90, 30));
      this.sliderTextR.slider.setMaximumSize(new Dimension(90, 30));
      this.sliderTextR.slider.setMinimumSize(new Dimension(90, 30));
      this.sliderTextWL1.label_vmin.setPreferredSize(new Dimension(25, 30));
      this.sliderTextWL1.label_vmin.setMaximumSize(new Dimension(25, 30));
      this.sliderTextWL1.label_vmin.setMinimumSize(new Dimension(25, 30));
      this.sliderTextWL2.label_vmin.setPreferredSize(new Dimension(25, 30));
      this.sliderTextWL2.label_vmin.setMaximumSize(new Dimension(25, 30));
      this.sliderTextWL2.label_vmin.setMinimumSize(new Dimension(25, 30));
      this.sliderTextThickness.label_vmin.setPreferredSize(new Dimension(25, 30));
      this.sliderTextThickness.label_vmin.setMaximumSize(new Dimension(25, 30));
      this.sliderTextThickness.label_vmin.setMinimumSize(new Dimension(25, 30));
      this.sliderTextAngle.label_vmin.setPreferredSize(new Dimension(25, 30));
      this.sliderTextAngle.label_vmin.setMaximumSize(new Dimension(25, 30));
      this.sliderTextAngle.label_vmin.setMinimumSize(new Dimension(25, 30));
      this.sliderTextR.label_vmin.setPreferredSize(new Dimension(25, 30));
      this.sliderTextR.label_vmin.setMaximumSize(new Dimension(25, 30));
      this.sliderTextR.label_vmin.setMinimumSize(new Dimension(25, 30));
      this.sliderTextWL1.label_vmax.setPreferredSize(new Dimension(25, 30));
      this.sliderTextWL1.label_vmax.setMaximumSize(new Dimension(25, 30));
      this.sliderTextWL1.label_vmax.setMinimumSize(new Dimension(25, 30));
      this.sliderTextWL2.label_vmax.setPreferredSize(new Dimension(25, 30));
      this.sliderTextWL2.label_vmax.setMaximumSize(new Dimension(25, 30));
      this.sliderTextWL2.label_vmax.setMinimumSize(new Dimension(25, 30));
      this.sliderTextThickness.label_vmax.setPreferredSize(new Dimension(25, 30));
      this.sliderTextThickness.label_vmax.setMaximumSize(new Dimension(25, 30));
      this.sliderTextThickness.label_vmax.setMinimumSize(new Dimension(25, 30));
      this.sliderTextAngle.label_vmax.setPreferredSize(new Dimension(25, 30));
      this.sliderTextAngle.label_vmax.setMaximumSize(new Dimension(25, 30));
      this.sliderTextAngle.label_vmax.setMinimumSize(new Dimension(25, 30));
      this.sliderTextR.label_vmax.setPreferredSize(new Dimension(25, 30));
      this.sliderTextR.label_vmax.setMaximumSize(new Dimension(25, 30));
      this.sliderTextR.label_vmax.setMinimumSize(new Dimension(25, 30));
      this.sliderTextWL1.label_units.setPreferredSize(new Dimension(25, 30));
      this.sliderTextWL1.label_units.setMaximumSize(new Dimension(25, 30));
      this.sliderTextWL1.label_units.setMinimumSize(new Dimension(25, 30));
      this.sliderTextWL2.label_units.setPreferredSize(new Dimension(25, 30));
      this.sliderTextWL2.label_units.setMaximumSize(new Dimension(25, 30));
      this.sliderTextWL2.label_units.setMinimumSize(new Dimension(25, 30));
      this.sliderTextThickness.label_units.setPreferredSize(new Dimension(25, 30));
      this.sliderTextThickness.label_units.setMaximumSize(new Dimension(25, 30));
      this.sliderTextThickness.label_units.setMinimumSize(new Dimension(25, 30));
      this.sliderTextAngle.label_units.setPreferredSize(new Dimension(25, 30));
      this.sliderTextAngle.label_units.setMaximumSize(new Dimension(25, 30));
      this.sliderTextAngle.label_units.setMinimumSize(new Dimension(25, 30));
      this.sliderTextR.label_units.setPreferredSize(new Dimension(25, 30));
      this.sliderTextR.label_units.setMaximumSize(new Dimension(25, 30));
      this.sliderTextR.label_units.setMinimumSize(new Dimension(25, 30));
      this.sliderTextThickness.setDecimalFormat(new DecimalFormat("#.###"));
      this.setSize(new Dimension(750, 550));
      this.panelFabry1.mode = 0;
      this.panelFabry2.mode = 1;
      this.sliderTextThickness.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            AppletFabry.this.sliderTextThickness_stateChanged(e);
         }
      });
      this.buttonGroup1.add(this.jRadioButtonLambda);
      this.buttonGroup1.add(this.jRadioButtonAngle);
      this.HelpExperiment.add(this.jTextArea1, "Center");
      this.HelpExperiment.add(this.jPanel20, "South");
      this.jPanel20.add(this.jButtonHelpExpOk, (Object)null);
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
      try {
         String dato = args[0].toUpperCase().intern();
         if (dato == "CA") {
            lang = 0;
         } else if (dato == "ES") {
            lang = 1;
         } else if (dato == "EN") {
            lang = 2;
         } else {
            lang = 0;
         }
      } catch (Exception var4) {
         lang = 0;
      }

      AppletFabry applet = new AppletFabry();
      applet.isStandalone = true;
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(3);
      frame.getContentPane().add(applet, "Center");
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

   void jButtonHelp_actionPerformed(ActionEvent e) {
      this.helpFrame1.setSize(new Dimension(700, 500));
      this.helpFrame1.show();
   }

   public void UpdateMostraPanel() {
      fabry2.lambda = fabry1.lambda = this.sliderTextWL1.valcur;
      fabry2.thick = fabry1.thick = this.sliderTextThickness.valcur;
      fabry2.angle = fabry1.angle = this.sliderTextAngle.valcur / 180.0D * 3.141592653589793D;
      FabryCalc var10000 = fabry2;
      var10000.lambda += this.sliderTextWL2.valcur;
      this.panelFabry1.fabry1 = fabry1;
      this.panelFabry1.repaint();
      this.panelFabry2.fabry1 = fabry1;
      this.panelFabry2.fabry2 = fabry2;
      this.panelFabry2.repaint();
   }

   void sliderTextThickness_stateChanged(ChangeEvent e) {
      this.UpdateMostraPanel();
      this.UpdateMaxMinList(this.jList3, fabry1);
      this.UpdateMaxMinList(this.jList2, fabry2);
      this.UpdateGrafica();
   }

   void sliderTextWL1_stateChanged(ChangeEvent e) {
      this.UpdateMostraPanel();
      this.UpdateMaxMinList(this.jList3, fabry1);
      this.UpdateMaxMinList(this.jList2, fabry2);
      this.UpdateGrafica();
   }

   void sliderTextAngle_stateChanged(ChangeEvent e) {
      this.UpdateMostraPanel();
      this.UpdateMaxMinList(this.jList3, fabry1);
      this.UpdateMaxMinList(this.jList2, fabry2);
      this.UpdateGrafica();
   }

   void UpdateMaxMinList(JList Llista, FabryCalc mostra) {
      Vector Strings = new Vector();
      new String();
      DecimalFormat df1 = new DecimalFormat("##.##");
      DecimalFormat df2 = new DecimalFormat("##.####");
      double[] MaxPos = mostra.CalcMax();
      int kmin = mostra.kmin;
      int kmax = mostra.kmax;
      String Texte;
      int i;
      if (this.ListVarMode) {
         if (mostra.thick != 0.0D) {
            for(i = kmin; i <= kmax; ++i) {
               double pos = Math.tan(Math.acos((double)i * mostra.lambda * 1.0E-6D / (4.0D * mostra.thick))) * mostra.fScreen;
               Texte = "x = " + df1.format(pos) + " mm, m = " + i / 2 + ", ";
               if (i / 2 == (i + 1) / 2) {
                  Strings.add(Texte);
               } else {
                  Texte = Texte + " Min.";
               }
            }
         }
      } else {
         for(i = kmin; i <= kmax; ++i) {
            double ang = Math.acos((double)i * mostra.lambda * 1.0E-6D / (4.0D * mostra.thick)) / 3.141592653589793D * 180.0D;
            Texte = "θ = " + df2.format(ang) + "º, m = " + i / 2 + ", ";
            if (i / 2 == (i + 1) / 2) {
               Strings.add(Texte);
            } else {
               Texte = Texte + " Min.";
            }
         }
      }

      Llista.setListData(Strings);
   }

   void jPanel11_componentShown(ComponentEvent e) {
      this.UpdateMaxMinList(this.jList3, fabry1);
      this.UpdateMaxMinList(this.jList2, fabry2);
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
      this.numint = 200;
      double[][] IT = fabry1.getIT();
      this.numint = fabry1.numint;
      this.graph2DPanel1.resetData();
      this.graph2DPanel1.addData(IT[0], IT[1], this.numint + 1, 1, Color.red);
      this.graph2DPanel1.addData(IT[0], IT[1], this.numint + 1, 1, Color.white);
      this.graph2DPanel1.setLegend("L1", 0);
      this.graph2DPanel1.setLegend("L2", 1);
      this.graph2DPanel1.flag_legend = 5;
      this.graph2DPanel1.xmin = 0.0D;
      this.graph2DPanel1.xmax = fabry1.sizeScreen;
      this.graph2DPanel1.ymin = 0.0D;
      this.graph2DPanel1.ymax = 1.0D;
      this.graph2DPanel1.TexteX = "Position (mm)";
      this.graph2DPanel1.TexteY = "I";
      this.graph2DPanel1.repaint();
   }

   void UpdateGrafica() {
      this.numint = 200;
      double[][] IT = fabry1.getIT();
      this.numint = fabry1.numint;
      this.graph2DPanel1.xmax = fabry1.sizeScreen;
      this.graph2DPanel1.updateData(IT[0], IT[1], this.numint + 1, 0);
      IT = fabry2.getIT();
      this.numint = fabry2.numint;
      this.graph2DPanel1.updateData(IT[0], IT[1], this.numint + 1, 1);
      this.graph2DPanel1.repaint();
      this.ethalonPanel1.repaint();
   }

   void jRadioButtonLambda_stateChanged(ChangeEvent e) {
      this.ListVarMode = this.jRadioButtonLambda.isSelected();
      this.UpdateMaxMinList(this.jList3, fabry1);
      this.UpdateMaxMinList(this.jList2, fabry2);
   }

   void graph2DPanel1_mouseMoved(MouseEvent e) {
      double[] xyval = new double[2];
      DecimalFormat df = new DecimalFormat("##.##");
      if (!this.EthalonMode && this.graph2DPanel1.getXY(e.getX(), e.getY(), xyval)) {
         String texte = this.textXPantalla[lang] + ": " + this.graph2DPanel1.FormatX.format(xyval[0]) + " mm";
         this.jLabel1.setText(texte);
         texte = this.textOrdenada[lang] + ": " + this.graph2DPanel1.FormatX.format(xyval[1]);
         this.jLabel2.setText(texte);
         texte = this.textAnglePantalla[lang] + ": " + df.format(Math.atan(xyval[0] / fabry1.fScreen) * 180.0D / 3.141592653589793D) + "º";
         this.jLabel5.setText(texte);
      }

   }

   void jButtonGraphConfig_actionPerformed(ActionEvent e) {
      DecimalFormatSymbols DFS = new DecimalFormatSymbols();
      DFS.setDecimalSeparator('.');
      DecimalFormat DF = this.graph2DPanel1.FormatY;
      DF.setDecimalFormatSymbols(DFS);
      this.midesFrame1.setSize(new Dimension(350, 350));
      this.midesFrame1.show();
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
      JOptionPane hola = new JOptionPane(rollo[lang], 1, -1, icon_joc, options);
      JDialog dialog = hola.createDialog(f, this.textAbout[lang]);
      dialog.setResizable(false);
      dialog.show();
      this.repaint();
   }

   void sliderTextWL2_stateChanged(ChangeEvent e) {
      this.UpdateMostraPanel();
      this.UpdateMaxMinList(this.jList3, fabry1);
      this.UpdateMaxMinList(this.jList2, fabry2);
      this.UpdateGrafica();
   }

   void panelFabry1_componentShown(ComponentEvent e) {
      ((CardLayout)((CardLayout)this.jPanel6.getLayout())).show(this.jPanel6, "sliderTextR");
      this.jButtonGraphConfig.setEnabled(true);
   }

   void panelFabry2_componentShown(ComponentEvent e) {
      DecimalFormat df = new DecimalFormat("##.#");
      this.sliderTextAngle.valmax = Math.atan(fabry1.sizeScreen / fabry1.fScreen) / 3.141592653589793D * 180.0D;
      this.sliderTextAngle.label_vmax.setText(df.format(this.sliderTextAngle.valmax));
      this.sliderTextAngle.valcur = 0.0D;
      ((CardLayout)((CardLayout)this.jPanel6.getLayout())).show(this.jPanel6, "sliderTextAngle");
      this.midesFrame1.hide();
      this.jButtonGraphConfig.setEnabled(false);
   }

   void sliderTextR_stateChanged(ChangeEvent e) {
      fabry2.r = fabry1.r = this.sliderTextR.valcur;
      this.UpdateMostraPanel();
      this.UpdateMaxMinList(this.jList3, fabry1);
      this.UpdateMaxMinList(this.jList2, fabry2);
      this.UpdateGrafica();
   }

   void midesFrame1_stateChanged(ChangeEvent e) {
      fabry1.fScreen = this.midesFrame1.sliderTextScreenFocal.valcur;
      fabry1.fSource = this.midesFrame1.sliderTextSourceFocal.valcur;
      fabry1.sizeScreen = this.midesFrame1.sliderTextScreenSize.valcur;
      fabry1.sizeSource = this.midesFrame1.sliderTextSourceSize.valcur;
      fabry2.fScreen = this.midesFrame1.sliderTextScreenFocal.valcur;
      fabry2.fSource = this.midesFrame1.sliderTextSourceFocal.valcur;
      fabry2.sizeScreen = this.midesFrame1.sliderTextScreenSize.valcur;
      fabry2.sizeSource = this.midesFrame1.sliderTextSourceSize.valcur;
      this.panelFabry1.repaint();
      this.UpdateGrafica();
      this.UpdateMaxMinList(this.jList3, fabry1);
      this.UpdateMaxMinList(this.jList2, fabry2);
   }

   void jButton2_actionPerformed(ActionEvent e) {
      if (!this.EthalonMode) {
         ((CardLayout)this.jPanel13.getLayout()).show(this.jPanel13, "ethalonPanel1");
         this.jButton2.setText(textGrafic[lang]);
      } else {
         ((CardLayout)this.jPanel13.getLayout()).show(this.jPanel13, "graph2DPanel1");
         this.jButton2.setText(textImatge[lang]);
      }

      this.EthalonMode = !this.EthalonMode;
   }

   void ethalonPanel1_mouseMoved(MouseEvent e) {
      double[] xyval = new double[2];
      DecimalFormat df = new DecimalFormat("##.##");
      if (this.EthalonMode) {
         double xpos = (2.0D * (double)e.getX() / (double)this.ethalonPanel1.getWidth() - 1.0D) * fabry1.sizeScreen;
         double ypos = (2.0D * (double)e.getY() / (double)this.ethalonPanel1.getHeight() - 1.0D) * fabry1.sizeScreen;
         xpos = Math.sqrt(xpos * xpos + ypos * ypos);
         String texte = this.textXPantalla[lang] + ": " + this.graph2DPanel1.FormatX.format(xpos) + " mm";
         this.jLabel1.setText(texte);
         this.jLabel2.setText("");
         texte = this.textAnglePantalla[lang] + ": " + df.format(180.0D * Math.atan(xpos / fabry1.fScreen) / 3.141592653589793D) + "º";
         this.jLabel5.setText(texte);
      }

   }
}
