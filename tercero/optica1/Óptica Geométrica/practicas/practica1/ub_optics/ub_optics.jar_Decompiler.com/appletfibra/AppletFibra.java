package appletfibra;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

public class AppletFibra extends Applet {
   String S;
   DecimalFormat DF1 = new DecimalFormat("##.##");
   LinkedList Llista1 = new LinkedList();
   LinkedList Llista2 = new LinkedList();
   static int count = 0;
   static DataFibra Fibra1 = new DataFibra();
   static DataFibra Fibra2 = new DataFibra();
   static DataFibra FibraM = new DataFibra();
   double[] F1x = new double[5];
   double[] F1z = new double[5];
   double[] F2x = new double[5];
   double[] F2z = new double[5];
   double[] C1x = new double[91];
   double[] C1y = new double[91];
   double[] C2x = new double[91];
   double[] C2y = new double[91];
   double[][] ModePerfx = new double[2][128];
   double[][] ModePerfy = new double[2][128];
   double[][] ModePerfn = new double[2][128];
   int lockcombos = 0;
   private boolean isStandalone = false;
   LinkedList RB = new LinkedList();
   static int lang = 0;
   Color[] colors;
   JTabbedPane jTabbedPane1 = new JTabbedPane();
   BorderLayout borderLayout1 = new BorderLayout();
   JPanel jPanelRaig = new JPanel();
   JPanel jPanelModes = new JPanel();
   JPanel jPanelHelp = new JPanel();
   static String[] textTitol = new String[]{"Applet de Fibres Òptiques", "Applet de Fibras Ópticas", "Optical Fibers Applet "};
   private static String[] rollo = new String[]{"Applet de Fibres Òptiques\nGrup d'Innovació Docent en Òptica Física i Fotònica\nUniversitat de Barcelona, 2003\nLa utilització d'aquest programa està sota una llicència de Creative Commons\nVeure condicions a http://www.publicacions.ub.es/doi/licencia/resum-deriv.htm\n\nCurs d'Òptica en Java DOI: 10.1344/401.000000050\nApplet de Fibres Òptiques DOI: 10.1344/???.0000000??", "Applet de Fibras Ópticas\nGrup d'Innovació Docent en Òptica Física i Fotònica \nUniversitat de Barcelona, 2003\nLa utilización de este programa está sujeta a una licencia de Creative Commons\nVer condiciones en http://creativecommons.org/license/by-nc-sa2.0/\n\nCurso de Óptica en Java DOI: 10.1344/401.000000050\nApplet de Fibras Ópticas DOI: 10.1344/???.000000???", "Optical Fibers Applet\n Grup d'Innovació Docent en Òptica Física i Fotònica\nUniversitat de Barcelona, 2003\nThe use of this program is under a Creative Commons license\nSee conditions in http://creativecommons.org/license/by-nc-sa2.0/\n\nJava Optics Course DOI: 10.1344/401.000000050\nOptical Fibers Applet DOI: 10.1344/???.000000???"};
   private static String[] textRadiCore = new String[]{"Radi Nucli", "R Nucleo", "Core Radius"};
   private static String[] textSLFiberRadius = new String[]{"Radi", "Radio", "Radius"};
   private static String[] textSLLength = new String[]{"Longitud", "Longitud", "Length"};
   private static String[] textExit = new String[]{"Sortir", "Salida", "Exit"};
   private static String[] textAbout = new String[]{"Quant a", "Acerca de ", "About"};
   private static String[] textFiber1 = new String[]{"Fibra 1", "Fibra 1", "Fiber 1"};
   private static String[] textFiber2 = new String[]{"Fibra 2", "Fibra 2", "Fiber 2"};
   private static String[] textAfegirRaig = new String[]{"Afegir Raig", "Añadir Rayo", "Add Ray"};
   private static String[] textEsborrarRaig = new String[]{"Esborrar Raig", "Borrar Rayo", "Erase Ray"};
   private static String[] textEditPropFibra = new String[]{"Editar Propietats Fibra", "Editar Props Fibra", "Edit Fiber Properties"};
   private static String[] textRayTracing = new String[]{"Traçat de Raigs", "Trazado de Rayos", "Ray Tracing"};
   private static String[] textModes = new String[]{"Modes", "Modos", "Modes"};
   private static String[] textPerfil = new String[]{"Perfil", "Perfil", "Profile"};
   private static String[] textMap = new String[]{"Mapa", "Mapa", "Map"};
   private static String[] textLlistaModes = new String[]{"Llista de Modes", "Lista de Modos", "Mode List"};
   private static String[] textTeoria = new String[]{"Resum de Teoria", "Resumen de Teoria", "Theory Summary"};
   private static String[] textGraphSideX = new String[]{"Longitud (e-6m)", "Longitud (e-6m)", "Length (e-6m)"};
   private static String[] textRCore = new String[]{"; R nucli = ", "; R nucleo = ", "; R core = "};
   static String[] textGraphProfileX = new String[]{"Radi (e-6m)", "Radio (e-6m)", "Radius (e-6m)"};
   private static String[] textGraphProfileY = new String[]{"I (U. A.)", "I (U. A.)", "I (A. U.)"};
   static String[] textTitleConfig = new String[]{"Configurar Fibra", "Configurar Fibra", "Configure Fiber"};
   static String[] textFiberRadius = new String[]{"Radi Fibra", "R Fibra", "Fiber Radius"};
   static String[] textCoreIndex = new String[]{"Index Nucli", "Índice Núcleo", "Core Index"};
   static String[] textCladIndex = new String[]{"Index Clad", "Índice Clad", "Clad Index"};
   static String[] textExponent = new String[]{"Exponent", "Exponente", "Exponent"};
   static String[] textLength = new String[]{"Longitud", "Longitud", "Length"};
   JPanel jPanel1 = new JPanel();
   JPanel jPanel2 = new JPanel();
   JButton jButtonExit = new JButton();
   JButton jButtonAbout = new JButton();
   JPanel jPanelFibra1 = new JPanel();
   JPanel jPanelFibra2 = new JPanel();
   BorderLayout borderLayout2 = new BorderLayout();
   JPanel jPanelRaigControl = new JPanel();
   BorderLayout borderLayout3 = new BorderLayout();
   JPanel jPanelModeControl = new JPanel();
   JPanel jPanelModeContainer = new JPanel();
   JPanel jPanelMode1 = new JPanel();
   BorderLayout borderLayout4 = new BorderLayout();
   JPanel jPanelMode2 = new JPanel();
   JPanel jPanelRaigSliders = new JPanel();
   JPanel jPanelRaigButtons = new JPanel();
   JPanel jPanelFibraSelector = new JPanel();
   JRadioButton jRadioButton1 = new JRadioButton();
   JRadioButton jRadioButton2 = new JRadioButton();
   BorderLayout borderLayout5 = new BorderLayout();
   ButtonGroup buttonGroup1 = new ButtonGroup();
   JPanel jPanelFibraBotons = new JPanel();
   JButton jButtonAfegir = new JButton();
   JButton jButtonEsborrar = new JButton();
   JButton jButtonEditar = new JButton();
   SliderText sliderTextHeight = new SliderText();
   SliderText sliderTextTheta = new SliderText();
   SliderText sliderTextPhi = new SliderText();
   FlowLayout flowLayout1 = new FlowLayout();
   JPopupMenu jPopupMenu1 = new JPopupMenu();
   JMenuItem jMenuItem1 = new JMenuItem();
   JMenuItem jMenuItem2 = new JMenuItem();
   Border border2;
   Border border3;
   Border border1;
   JPanel jPanelRaigs = new JPanel();
   ButtonGroup buttonGroup2 = new ButtonGroup();
   JRadioButton jRadioButtonR1 = new JRadioButton();
   JRadioButton jRadioButtonR2 = new JRadioButton();
   JRadioButton jRadioButtonR3 = new JRadioButton();
   JRadioButton jRadioButtonR4 = new JRadioButton();
   JRadioButton jRadioButtonR5 = new JRadioButton();
   FibraFrame fibraFrame1 = new FibraFrame();
   SliderText sliderTextLambda = new SliderText();
   SliderText sliderTextRCore = new SliderText();
   SliderText sliderTextN1 = new SliderText();
   SliderText sliderTextDeltaN = new SliderText();
   BorderLayout borderLayout6 = new BorderLayout();
   BorderLayout borderLayout7 = new BorderLayout();
   JTabbedPane jTabbedPaneMode1 = new JTabbedPane();
   JPanel jPanelMode1Graph = new JPanel();
   JPanel jPanelMode1Image = new JPanel();
   JPanel jPanelMode1List = new JPanel();
   JPanel jPanelMode2List = new JPanel();
   JPanel jPanelMode2Image = new JPanel();
   JTabbedPane jTabbedPaneMode2 = new JTabbedPane();
   JPanel jPanelMode2Graph = new JPanel();
   Graph2DPanel graph2DPanelTop1 = new Graph2DPanel();
   Graph2DPanel graph2DPanelTop2 = new Graph2DPanel();
   Graph2DPanel graph2DPanelSide1 = new Graph2DPanel();
   Graph2DPanel graph2DPanelSide2 = new Graph2DPanel();
   BorderLayout borderLayout8 = new BorderLayout();
   BorderLayout borderLayout9 = new BorderLayout();
   JList jListModes1 = new JList();
   JScrollPane jScrollPane1 = new JScrollPane();
   Graph2DPanel graph2DPanel1 = new Graph2DPanel();
   BorderLayout borderLayout10 = new BorderLayout();
   JScrollPane jScrollPane2 = new JScrollPane();
   JList jListModes2 = new JList();
   JComboBox jComboBoxMode1 = new JComboBox();
   JComboBox jComboBoxMode2 = new JComboBox();
   FieldPanel fieldPanel1 = new FieldPanel();
   BorderLayout borderLayout11 = new BorderLayout();
   JPanel jPanelSliders = new JPanel();
   JPanel jPanelVinfo = new JPanel();
   JLabel jLabelVinfo = new JLabel();
   BorderLayout borderLayout12 = new BorderLayout();
   Graph2DPanel graph2DPanel2 = new Graph2DPanel();
   BorderLayout borderLayout13 = new BorderLayout();
   FieldPanel fieldPanel2 = new FieldPanel();
   BorderLayout borderLayout14 = new BorderLayout();

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

      AppletFibra applet = new AppletFibra();
      applet.isStandalone = true;
      JFrame frame = new JFrame();
      applet.init();
      applet.start();
      frame.setDefaultCloseOperation(3);
      frame.getContentPane().add(applet, "Center");
      frame.setSize(750, 570);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
      frame.setTitle(textTitol[lang]);
      frame.setVisible(true);
   }

   public String getParameter(String key, String def) {
      return this.isStandalone ? System.getProperty(key, def) : (this.getParameter(key) != null ? this.getParameter(key) : def);
   }

   public AppletFibra() {
      Fibra1.radi = 5.0D;
      Fibra2.radi = 5.0D;
      FibraM.radi = 5.0D;
      Fibra1.n1 = 1.52D;
      Fibra1.n2 = 1.51D;
      Fibra2.n1 = 1.53D;
      Fibra2.n2 = 1.52D;
      FibraM.n1 = 1.52D;
      FibraM.n2 = 1.51D;
      Fibra1.p = 0.0D;
      Fibra2.p = 0.0D;
      FibraM.p = 0.0D;
      Fibra1.L = 1.0D;
      Fibra2.L = 1.0D;
      Fibra1.lambda = Fibra2.lambda = FibraM.lambda = 633.0D;
      this.RB.add(this.jRadioButtonR1);
      this.RB.add(this.jRadioButtonR2);
      this.RB.add(this.jRadioButtonR3);
      this.RB.add(this.jRadioButtonR4);
      this.RB.add(this.jRadioButtonR5);
      DecimalFormat df = new DecimalFormat("####");
      String S1 = df.format(0L);
      String S2 = df.format(Fibra1.radi);
      this.sliderTextHeight = new SliderText(0.0D, Fibra1.radi, 0.0D, "h", "e-6m", S1, S2);
      S1 = df.format(0L);
      S2 = df.format(90L);
      this.sliderTextTheta = new SliderText(0.0D, 90.0D, 0.0D, "Theta", "º", S1, S2);
      S1 = df.format(0L);
      S2 = df.format(360L);
      this.sliderTextTheta.setStep(900);
      this.sliderTextPhi = new SliderText(0.0D, 360.0D, 0.0D, "Phi", "º", S1, S2);
      this.sliderTextLambda = new SliderText(300.0D, 700.0D, FibraM.lambda, "Lambda", "nm", "300", "700");
      this.sliderTextRCore = new SliderText(0.0D, 50.0D, FibraM.radi, textRadiCore[lang], "e-6m", "0", "50");
      this.sliderTextN1 = new SliderText(1.0D, 3.0D, FibraM.n1, "n1", "", "1", "3");
      this.sliderTextDeltaN = new SliderText(0.0D, 0.5D, FibraM.n1 - FibraM.n2, "Delta n", "", "0", "0.5");
      this.sliderTextLambda.setStep(400);
      this.sliderTextRCore.setStep(490);
      this.sliderTextN1.setStep(200);
      this.sliderTextDeltaN.setStep(500);
      this.sliderTextHeight.setStep(500);
      this.sliderTextTheta.setStep(900);
      this.sliderTextPhi.setStep(3600);
      this.sliderTextDeltaN.setDecimalFormat(new DecimalFormat(".###"));
      this.sliderTextHeight.jPanelLabel.setPreferredSize(new Dimension(40, 25));
      this.sliderTextTheta.jPanelLabel.setPreferredSize(new Dimension(40, 25));
      this.sliderTextPhi.jPanelLabel.setPreferredSize(new Dimension(40, 25));
      this.sliderTextHeight.label_vmax.setPreferredSize(new Dimension(20, 25));
      this.sliderTextTheta.label_vmax.setPreferredSize(new Dimension(20, 25));
      this.sliderTextPhi.label_vmax.setPreferredSize(new Dimension(20, 25));
      this.sliderTextHeight.slider.setPreferredSize(new Dimension(150, 25));
      this.sliderTextTheta.slider.setPreferredSize(new Dimension(150, 25));
      this.sliderTextPhi.slider.setPreferredSize(new Dimension(150, 25));
      this.sliderTextHeight.jPanelSlider.setPreferredSize(new Dimension(200, 35));
      this.sliderTextTheta.jPanelSlider.setPreferredSize(new Dimension(200, 35));
      this.sliderTextPhi.jPanelSlider.setPreferredSize(new Dimension(200, 35));
      this.fibraFrame1.sliderTextFiberRadius.label_titol.setText(textSLFiberRadius[lang]);
      this.fibraFrame1.sliderTextFiberRadius.setRange(1.0D, 50.0D);
      this.fibraFrame1.sliderTextFiberRadius.setValcur(Fibra1.radi);
      this.fibraFrame1.sliderTextFiberRadius.setStep(490);
      this.fibraFrame1.sliderTextFiberRadius.label_units.setText("e-6m");
      this.fibraFrame1.sliderTextCoreIndex.label_titol.setText("n1");
      this.fibraFrame1.sliderTextCoreIndex.setRange(1.0D, 2.5D);
      this.fibraFrame1.sliderTextCoreIndex.setValcur(Fibra1.n1);
      this.fibraFrame1.sliderTextCoreIndex.setStep(150);
      this.fibraFrame1.sliderTextCoreIndex.label_units.setText("");
      this.fibraFrame1.sliderTextCladdingIndex.label_titol.setText("n2");
      this.fibraFrame1.sliderTextCladdingIndex.setRange(1.0D, 2.5D);
      this.fibraFrame1.sliderTextCladdingIndex.setValcur(Fibra1.n2);
      this.fibraFrame1.sliderTextCladdingIndex.setStep(150);
      this.fibraFrame1.sliderTextCladdingIndex.label_units.setText("");
      this.fibraFrame1.sliderTextExponent.label_titol.setText("p");
      this.fibraFrame1.sliderTextExponent.setRange(0.0D, 10.0D);
      this.fibraFrame1.sliderTextExponent.setValcur(Fibra1.p);
      this.fibraFrame1.sliderTextExponent.setStep(100);
      this.fibraFrame1.sliderTextExponent.label_units.setText("");
      this.fibraFrame1.sliderTextLength.label_titol.setText(textSLLength[lang]);
      this.fibraFrame1.sliderTextLength.setRange(0.0D, 5.0D);
      this.fibraFrame1.sliderTextLength.setValcur(Fibra1.L);
      this.fibraFrame1.sliderTextLength.setStep(500);
      this.fibraFrame1.sliderTextLength.label_units.setText("mm");
      this.fibraFrame1.sliderTextFiberRadius.label_titol.setPreferredSize(new Dimension(35, 30));
      this.fibraFrame1.sliderTextFiberRadius.jPanelSlider.setMinimumSize(new Dimension(205, 40));
      this.fibraFrame1.sliderTextFiberRadius.jPanelSlider.setPreferredSize(new Dimension(205, 40));
      this.fibraFrame1.sliderTextFiberRadius.slider.setPreferredSize(new Dimension(140, 30));
      this.fibraFrame1.sliderTextFiberRadius.label_vmin.setPreferredSize(new Dimension(20, 30));
      this.fibraFrame1.sliderTextFiberRadius.label_vmax.setPreferredSize(new Dimension(20, 30));
      this.fibraFrame1.sliderTextCoreIndex.label_titol.setPreferredSize(new Dimension(35, 30));
      this.fibraFrame1.sliderTextCoreIndex.jPanelSlider.setMinimumSize(new Dimension(205, 40));
      this.fibraFrame1.sliderTextCoreIndex.jPanelSlider.setPreferredSize(new Dimension(205, 40));
      this.fibraFrame1.sliderTextCoreIndex.slider.setPreferredSize(new Dimension(140, 30));
      this.fibraFrame1.sliderTextCoreIndex.label_vmin.setPreferredSize(new Dimension(20, 30));
      this.fibraFrame1.sliderTextCoreIndex.label_vmax.setPreferredSize(new Dimension(20, 30));
      this.fibraFrame1.sliderTextCladdingIndex.label_titol.setPreferredSize(new Dimension(35, 30));
      this.fibraFrame1.sliderTextCladdingIndex.jPanelSlider.setMinimumSize(new Dimension(205, 40));
      this.fibraFrame1.sliderTextCladdingIndex.jPanelSlider.setPreferredSize(new Dimension(205, 40));
      this.fibraFrame1.sliderTextCladdingIndex.slider.setPreferredSize(new Dimension(140, 30));
      this.fibraFrame1.sliderTextCladdingIndex.label_vmin.setPreferredSize(new Dimension(20, 30));
      this.fibraFrame1.sliderTextCladdingIndex.label_vmax.setPreferredSize(new Dimension(20, 30));
      this.fibraFrame1.sliderTextExponent.label_titol.setPreferredSize(new Dimension(35, 30));
      this.fibraFrame1.sliderTextExponent.jPanelSlider.setMinimumSize(new Dimension(205, 40));
      this.fibraFrame1.sliderTextExponent.jPanelSlider.setPreferredSize(new Dimension(205, 40));
      this.fibraFrame1.sliderTextExponent.slider.setPreferredSize(new Dimension(140, 30));
      this.fibraFrame1.sliderTextExponent.label_vmin.setPreferredSize(new Dimension(20, 30));
      this.fibraFrame1.sliderTextExponent.label_vmax.setPreferredSize(new Dimension(20, 30));
      this.fibraFrame1.sliderTextLength.label_titol.setPreferredSize(new Dimension(45, 30));
      this.fibraFrame1.sliderTextLength.jPanelSlider.setMinimumSize(new Dimension(185, 40));
      this.fibraFrame1.sliderTextLength.jPanelSlider.setPreferredSize(new Dimension(185, 40));
      this.fibraFrame1.sliderTextLength.slider.setPreferredSize(new Dimension(120, 30));
      this.fibraFrame1.sliderTextLength.label_vmin.setPreferredSize(new Dimension(20, 30));
      this.fibraFrame1.sliderTextLength.label_vmax.setPreferredSize(new Dimension(20, 30));
      this.sliderTextRCore.jPanelLabel.setPreferredSize(new Dimension(55, 26));
      this.sliderTextRCore.jPanelSlider.setPreferredSize(new Dimension(160, 40));
      this.sliderTextRCore.slider.setPreferredSize(new Dimension(110, 30));
      this.sliderTextLambda.jPanelLabel.setPreferredSize(new Dimension(52, 26));
      this.sliderTextLambda.jPanelSlider.setPreferredSize(new Dimension(165, 40));
      this.sliderTextLambda.slider.setPreferredSize(new Dimension(110, 30));
      this.sliderTextN1.jPanelLabel.setPreferredSize(new Dimension(55, 26));
      this.sliderTextN1.jPanelSlider.setPreferredSize(new Dimension(160, 40));
      this.sliderTextN1.slider.setPreferredSize(new Dimension(110, 30));
      this.sliderTextDeltaN.jPanelLabel.setPreferredSize(new Dimension(55, 26));
      this.sliderTextDeltaN.jPanelSlider.setPreferredSize(new Dimension(160, 40));
      this.sliderTextDeltaN.slider.setPreferredSize(new Dimension(110, 30));
      this.colors = new Color[5];
      this.colors[0] = Color.blue;
      this.colors[1] = Color.cyan;
      this.colors[2] = Color.orange;
      this.colors[3] = Color.green;
      this.colors[4] = Color.magenta;
   }

   public void init() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      this.fibraFrame1.SetTexts();
   }

   private void jbInit() throws Exception {
      this.border2 = BorderFactory.createEtchedBorder(Color.red, new Color(148, 145, 140));
      this.border3 = BorderFactory.createEtchedBorder(Color.red, new Color(148, 145, 140));
      this.border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
      this.setForeground(Color.black);
      this.addComponentListener(new AppletFibra_this_componentAdapter(this));
      this.setLayout(this.borderLayout1);
      this.jButtonExit.setText("Sortir");
      this.jButtonExit.addActionListener(new AppletFibra_jButtonExit_actionAdapter(this));
      this.jButtonAbout.setToolTipText("");
      this.jButtonAbout.setText("Quant a");
      this.jButtonAbout.addActionListener(new AppletFibra_jButtonAbout_actionAdapter(this));
      this.jPanel1.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel2.setBorder(BorderFactory.createEtchedBorder());
      this.jTabbedPane1.setBorder(BorderFactory.createEtchedBorder());
      this.jTabbedPane1.setPreferredSize(new Dimension(714, 490));
      this.jTabbedPane1.addMouseListener(new AppletFibra_jTabbedPane1_mouseAdapter(this));
      this.jPanelFibra1.setBorder(this.border2);
      this.jPanelFibra1.setPreferredSize(new Dimension(715, 150));
      this.jPanelFibra1.addMouseListener(new AppletFibra_jPanelFibra1_mouseAdapter(this));
      this.jPanelFibra1.setLayout(this.borderLayout8);
      this.jPanelFibra2.setBorder(this.border1);
      this.jPanelFibra2.setPreferredSize(new Dimension(715, 150));
      this.jPanelFibra2.addMouseListener(new AppletFibra_jPanelFibra2_mouseAdapter(this));
      this.jPanelFibra2.setLayout(this.borderLayout9);
      this.jPanelRaig.setLayout(this.borderLayout2);
      this.jPanelRaigControl.setBorder(BorderFactory.createEtchedBorder());
      this.jPanelRaigControl.setPreferredSize(new Dimension(715, 160));
      this.jPanelModes.setLayout(this.borderLayout3);
      this.jPanelModeControl.setBorder(BorderFactory.createEtchedBorder());
      this.jPanelModeControl.setDebugGraphicsOptions(0);
      this.jPanelModeControl.setPreferredSize(new Dimension(710, 120));
      this.jPanelModeControl.setLayout(this.borderLayout12);
      this.jPanelModeContainer.setBorder(BorderFactory.createEtchedBorder());
      this.jPanelModeContainer.setPreferredSize(new Dimension(710, 330));
      this.jPanelModeContainer.setLayout(this.borderLayout4);
      this.jPanelMode1.setBorder(BorderFactory.createEtchedBorder());
      this.jPanelMode1.setPreferredSize(new Dimension(357, 325));
      this.jPanelMode1.setLayout(this.borderLayout6);
      this.jPanelMode2.setPreferredSize(new Dimension(357, 320));
      this.jPanelMode2.setLayout(this.borderLayout7);
      this.jPanelMode2.setBorder(BorderFactory.createEtchedBorder());
      this.jPanelRaigSliders.setBorder(BorderFactory.createEtchedBorder());
      this.jPanelRaigSliders.setPreferredSize(new Dimension(345, 150));
      this.jPanelRaigSliders.setLayout(this.flowLayout1);
      this.jPanelRaigButtons.setBorder(BorderFactory.createEtchedBorder());
      this.jPanelRaigButtons.setMinimumSize(new Dimension(10, 10));
      this.jPanelRaigButtons.setPreferredSize(new Dimension(345, 150));
      this.jPanelRaigButtons.setLayout(this.borderLayout5);
      this.jRadioButton1.setSelected(true);
      this.jRadioButton1.setText("Fibra 1");
      this.jRadioButton1.addChangeListener(new AppletFibra_jRadioButton1_changeAdapter(this));
      this.jRadioButton2.setText("Fibra 2");
      this.jRadioButton2.addActionListener(new AppletFibra_jRadioButton2_actionAdapter(this));
      this.jPanelRaig.setPreferredSize(new Dimension(715, 460));
      this.jButtonAfegir.setMinimumSize(new Dimension(135, 25));
      this.jButtonAfegir.setPreferredSize(new Dimension(135, 25));
      this.jButtonAfegir.setActionCommand("Afegir Raig");
      this.jButtonAfegir.setText("Afegir Raig");
      this.jButtonAfegir.addActionListener(new AppletFibra_jButtonAfegir_actionAdapter(this));
      this.jPanelFibraBotons.setBorder(BorderFactory.createEtchedBorder());
      this.jPanelFibraBotons.setPreferredSize(new Dimension(150, 43));
      this.jButtonEsborrar.setMinimumSize(new Dimension(135, 25));
      this.jButtonEsborrar.setPreferredSize(new Dimension(135, 25));
      this.jButtonEsborrar.setText("Esborrar Raig");
      this.jButtonEsborrar.addActionListener(new AppletFibra_jButtonEsborrar_actionAdapter(this));
      this.jButtonEditar.setMargin(new Insets(2, 3, 2, 3));
      this.jButtonEditar.setText("Editar Fibra");
      this.jButtonEditar.addActionListener(new AppletFibra_jButtonEditar_actionAdapter(this));
      this.sliderTextPhi.setBorder(BorderFactory.createLineBorder(Color.black));
      this.sliderTextPhi.setPreferredSize(new Dimension(330, 47));
      this.sliderTextPhi.addChangeListener(new AppletFibra_sliderTextPhi_changeAdapter(this));
      this.sliderTextTheta.setBorder(BorderFactory.createLineBorder(Color.black));
      this.sliderTextTheta.setPreferredSize(new Dimension(330, 47));
      this.sliderTextTheta.addChangeListener(new AppletFibra_sliderTextTheta_changeAdapter(this));
      this.sliderTextHeight.setBorder(BorderFactory.createLineBorder(Color.black));
      this.sliderTextHeight.setPreferredSize(new Dimension(330, 47));
      this.sliderTextHeight.addChangeListener(new AppletFibra_sliderTextHeight_changeAdapter(this));
      this.flowLayout1.setHgap(1);
      this.flowLayout1.setVgap(1);
      this.jMenuItem1.setText("Afegir Raig");
      this.jMenuItem1.addActionListener(new AppletFibra_jMenuItem1_actionAdapter(this));
      this.jMenuItem2.setText("Esborrar Raig");
      this.jMenuItem2.addActionListener(new AppletFibra_jMenuItem2_actionAdapter(this));
      this.jRadioButtonR1.setPreferredSize(new Dimension(185, 22));
      this.jRadioButtonR1.setHorizontalAlignment(2);
      this.jRadioButtonR1.setHorizontalTextPosition(11);
      this.jRadioButtonR1.setSelected(true);
      this.jRadioButtonR1.setText("");
      this.jRadioButtonR1.addMouseListener(new AppletFibra_jRadioButtonR1_mouseAdapter(this));
      this.jRadioButtonR2.setPreferredSize(new Dimension(185, 22));
      this.jRadioButtonR2.setText("");
      this.jRadioButtonR2.addMouseListener(new AppletFibra_jRadioButtonR2_mouseAdapter(this));
      this.jRadioButtonR3.setPreferredSize(new Dimension(185, 22));
      this.jRadioButtonR3.setText("");
      this.jRadioButtonR3.addMouseListener(new AppletFibra_jRadioButtonR3_mouseAdapter(this));
      this.jRadioButtonR4.setPreferredSize(new Dimension(185, 22));
      this.jRadioButtonR4.setText("");
      this.jRadioButtonR4.addMouseListener(new AppletFibra_jRadioButtonR4_mouseAdapter(this));
      this.jRadioButtonR5.setPreferredSize(new Dimension(185, 22));
      this.jRadioButtonR5.setText("");
      this.jRadioButtonR5.addMouseListener(new AppletFibra_jRadioButtonR5_mouseAdapter(this));
      this.jPanelRaigs.setBorder(BorderFactory.createEtchedBorder());
      this.jPanelRaigs.setPreferredSize(new Dimension(35, 33));
      this.jPopupMenu1.setActionMap((ActionMap)null);
      this.jPopupMenu1.setInvoker(this.jPanelRaigs);
      this.fibraFrame1.addChangeListener(new AppletFibra_fibraFrame1_changeAdapter(this));
      this.jPanelModes.setPreferredSize(new Dimension(715, 450));
      this.sliderTextLambda.setPreferredSize(new Dimension(300, 50));
      this.sliderTextLambda.addChangeListener(new AppletFibra_sliderTextLambda_changeAdapter(this));
      this.sliderTextRCore.setPreferredSize(new Dimension(300, 50));
      this.sliderTextRCore.addChangeListener(new AppletFibra_sliderTextRCore_changeAdapter(this));
      this.sliderTextN1.setPreferredSize(new Dimension(300, 50));
      this.sliderTextN1.addChangeListener(new AppletFibra_sliderTextN1_changeAdapter(this));
      this.sliderTextDeltaN.setPreferredSize(new Dimension(300, 50));
      this.sliderTextDeltaN.addChangeListener(new AppletFibra_sliderTextDeltaN_changeAdapter(this));
      this.graph2DPanelTop1.setBorder((Border)null);
      this.graph2DPanelTop1.setPreferredSize(new Dimension(140, 140));
      this.graph2DPanelTop2.setPreferredSize(new Dimension(140, 140));
      this.graph2DPanelTop2.setBorder(BorderFactory.createEtchedBorder());
      this.graph2DPanelSide1.setPreferredSize(new Dimension(365, 140));
      this.graph2DPanelSide2.setBorder(BorderFactory.createEtchedBorder());
      this.graph2DPanelSide2.setPreferredSize(new Dimension(365, 140));
      this.jListModes1.setDebugGraphicsOptions(0);
      this.jListModes1.setSelectionMode(0);
      this.jListModes1.addListSelectionListener(new AppletFibra_jListModes1_listSelectionAdapter(this));
      this.jPanelMode1List.setMinimumSize(new Dimension(10, 10));
      this.jPanelMode1List.setPreferredSize(new Dimension(10, 10));
      this.jScrollPane1.setHorizontalScrollBarPolicy(30);
      this.jScrollPane1.setPreferredSize(new Dimension(340, 260));
      this.jPanelMode1Graph.setLayout(this.borderLayout10);
      this.jScrollPane2.setPreferredSize(new Dimension(330, 260));
      this.jScrollPane2.setHorizontalScrollBarPolicy(30);
      this.jPanelMode2List.setPreferredSize(new Dimension(10, 10));
      this.jListModes2.addListSelectionListener(new AppletFibra_jListModes2_listSelectionAdapter(this));
      this.jComboBoxMode1.setPreferredSize(new Dimension(300, 24));
      this.jComboBoxMode1.addActionListener(new AppletFibra_jComboBoxMode1_actionAdapter(this));
      this.jComboBoxMode2.setPreferredSize(new Dimension(300, 24));
      this.jComboBoxMode2.addActionListener(new AppletFibra_jComboBoxMode2_actionAdapter(this));
      this.jPanelMode1Image.setLayout(this.borderLayout11);
      this.graph2DPanel1.addComponentListener(new AppletFibra_graph2DPanel1_componentAdapter(this));
      this.fieldPanel1.addComponentListener(new AppletFibra_fieldPanel1_componentAdapter(this));
      this.jPanelSliders.setPreferredSize(new Dimension(610, 110));
      this.jPanelVinfo.setPreferredSize(new Dimension(90, 110));
      this.jLabelVinfo.setPreferredSize(new Dimension(80, 100));
      this.jLabelVinfo.setHorizontalAlignment(0);
      this.jLabelVinfo.setText("V = ");
      this.jLabelVinfo.addPropertyChangeListener(new AppletFibra_jLabelVinfo_propertyChangeAdapter(this));
      this.jPanelMode2Graph.setLayout(this.borderLayout13);
      this.fieldPanel2.addComponentListener(new AppletFibra_fieldPanel2_componentAdapter(this));
      this.jPanelMode2Image.setLayout(this.borderLayout14);
      this.fieldPanel2.addComponentListener(new AppletFibra_fieldPanel2_componentAdapter(this));
      this.jPanelRaigs.add(this.jRadioButtonR1, (Object)null);
      this.jPanelRaigs.add(this.jRadioButtonR2, (Object)null);
      this.jPanelRaigs.add(this.jRadioButtonR3, (Object)null);
      this.jPanelRaigs.add(this.jRadioButtonR4, (Object)null);
      this.jPanelRaigs.add(this.jRadioButtonR5, (Object)null);
      this.jPanelRaigControl.add(this.jPanelRaigButtons, (Object)null);
      this.jPanelRaigControl.add(this.jPanelRaigSliders, (Object)null);
      this.jTabbedPane1.add(this.jPanelRaig, "Traçat de Raigs");
      this.jTabbedPane1.add(this.jPanelModes, "Modes");
      this.jPanelFibra1.add(this.graph2DPanelTop1, "West");
      this.jPanelFibra1.add(this.graph2DPanelSide1, "Center");
      this.jPanelRaig.add(this.jPanelFibra2, "Center");
      this.jPanelRaig.add(this.jPanelFibra1, "North");
      this.add(this.jPanel2, "South");
      this.jPanel2.add(this.jButtonAbout, (Object)null);
      this.jPanel2.add(this.jButtonExit, (Object)null);
      this.add(this.jPanel1, "Center");
      this.jPanel1.add(this.jTabbedPane1, (Object)null);
      this.jPanelModes.add(this.jPanelModeControl, "South");
      this.jPanelModeControl.add(this.jPanelSliders, "West");
      this.jPanelSliders.add(this.sliderTextLambda, (Object)null);
      this.jPanelSliders.add(this.sliderTextRCore, (Object)null);
      this.jPanelSliders.add(this.sliderTextN1, (Object)null);
      this.jPanelSliders.add(this.sliderTextDeltaN, (Object)null);
      this.jPanelModeControl.add(this.jPanelVinfo, "East");
      this.jPanelVinfo.add(this.jLabelVinfo, (Object)null);
      this.jPanelModes.add(this.jPanelModeContainer, "North");
      this.jPanelModeContainer.add(this.jPanelMode1, "West");
      this.jPanelModeContainer.add(this.jPanelMode2, "Center");
      this.jPanelFibraSelector.add(this.jRadioButton1, (Object)null);
      this.jPanelFibraSelector.add(this.jRadioButton2, (Object)null);
      this.jPanelFibraBotons.add(this.jButtonAfegir, (Object)null);
      this.jPanelFibraBotons.add(this.jButtonEsborrar, (Object)null);
      this.jPanelFibraBotons.add(this.jButtonEditar, (Object)null);
      this.jPanelRaigButtons.add(this.jPanelRaigs, "Center");
      this.buttonGroup1.add(this.jRadioButton1);
      this.buttonGroup1.add(this.jRadioButton2);
      this.jPanelFibraBotons.add(this.jPanelFibraSelector, (Object)null);
      this.jPanelRaigButtons.add(this.jPanelFibraBotons, "West");
      this.jPanelRaigSliders.add(this.sliderTextHeight, (Object)null);
      this.jPanelRaigSliders.add(this.sliderTextTheta, (Object)null);
      this.jPanelRaigSliders.add(this.sliderTextPhi, (Object)null);
      this.jPopupMenu1.add(this.jMenuItem1);
      this.jPopupMenu1.add(this.jMenuItem2);
      this.buttonGroup2.add(this.jRadioButtonR1);
      this.buttonGroup2.add(this.jRadioButtonR2);
      this.buttonGroup2.add(this.jRadioButtonR3);
      this.buttonGroup2.add(this.jRadioButtonR4);
      this.buttonGroup2.add(this.jRadioButtonR5);
      this.jPanelMode1.add(this.jTabbedPaneMode1, "Center");
      this.jTabbedPaneMode1.add(this.jPanelMode1Graph, "Perfil");
      this.jPanelMode1Graph.add(this.graph2DPanel1, "Center");
      this.jTabbedPaneMode1.add(this.jPanelMode1Image, "Mapa");
      this.jPanelMode1Image.add(this.fieldPanel1, "Center");
      this.jTabbedPaneMode1.add(this.jPanelMode1List, "Llista de Modes");
      this.jPanelMode1List.add(this.jScrollPane1, (Object)null);
      this.jPanelMode1.add(this.jComboBoxMode1, "South");
      this.jScrollPane1.getViewport().add(this.jListModes1, (Object)null);
      this.jTabbedPaneMode2.add(this.jPanelMode2Graph, "Perfil");
      this.jTabbedPaneMode2.add(this.jPanelMode2Image, "Mapa");
      this.jPanelMode2Image.add(this.fieldPanel2, "Center");
      this.jTabbedPaneMode2.add(this.jPanelMode2List, "Llista de Modes");
      this.jPanelMode2List.add(this.jScrollPane2, (Object)null);
      this.jPanelMode2Graph.add(this.graph2DPanel2, "Center");
      this.jScrollPane2.getViewport().add(this.jListModes2, (Object)null);
      this.jTabbedPane1.add(this.jPanelHelp, "Resum de Teoria");
      this.jPanelMode2.add(this.jTabbedPaneMode2, "Center");
      this.jPanelFibra2.add(this.graph2DPanelTop2, "West");
      this.jPanelFibra2.add(this.graph2DPanelSide2, "Center");
      this.jPanelRaig.add(this.jPanelRaigControl, "South");
      this.jPanelMode2.add(this.jComboBoxMode2, "South");
   }

   public void start() {
      this.SetLimitsFibres();
      this.UpdateLlista();
      this.UpdateSliders();
      this.UpdateModes();
      this.UpdateRaig();
      this.jButtonExit.setText(textExit[lang]);
      this.jButtonAbout.setText(textAbout[lang]);
      this.jRadioButton1.setText(textFiber1[lang]);
      this.jRadioButton2.setText(textFiber2[lang]);
      this.jButtonAfegir.setText(textAfegirRaig[lang]);
      this.jButtonEsborrar.setText(textEsborrarRaig[lang]);
      this.jButtonEditar.setText(textEditPropFibra[lang]);
      this.jMenuItem1.setText(textAfegirRaig[lang]);
      this.jMenuItem2.setText(textEsborrarRaig[lang]);
      this.jTabbedPaneMode1.setTitleAt(0, textPerfil[lang]);
      this.jTabbedPaneMode1.setTitleAt(1, textMap[lang]);
      this.jTabbedPaneMode1.setTitleAt(2, textLlistaModes[lang]);
      this.jTabbedPaneMode2.setTitleAt(0, textPerfil[lang]);
      this.jTabbedPaneMode2.setTitleAt(1, textMap[lang]);
      this.jTabbedPaneMode2.setTitleAt(2, textLlistaModes[lang]);
      this.jTabbedPane1.setTitleAt(0, textRayTracing[lang]);
      this.jTabbedPane1.setTitleAt(1, textModes[lang]);
      this.jTabbedPane1.setTitleAt(2, textTeoria[lang]);
      this.fibraFrame1.sliderTextFiberRadius.label_titol.setText(textSLFiberRadius[lang]);
      this.fibraFrame1.sliderTextLength.label_titol.setText(textSLLength[lang]);
      this.sliderTextRCore.label_titol.setText(textRadiCore[lang]);
   }

   public void stop() {
   }

   public void destroy() {
   }

   public String getAppletInfo() {
      return "Applet Information";
   }

   public String[][] getParameterInfo() {
      String[][] pinfo = new String[][]{{"lang", "String", ""}};
      return pinfo;
   }

   void jButtonExit_actionPerformed(ActionEvent e) {
      try {
         System.exit(0);
      } catch (Exception var3) {
         System.out.println("Error al sortir");
      }

   }

   void jButtonAfegir_actionPerformed(ActionEvent e) {
      DataRaig F = new DataRaig();
      F.h = 0.0D;
      F.fi = 0.0D;
      F.theta = 0.0D;
      F.ID = count++;
      LinkedList L = this.SelectList();
      if (L.size() < 5) {
         L.add(F);
      }

      this.UpdateLlista();
      ((JRadioButton)this.RB.get(L.size() - 1)).setSelected(true);
      this.UpdateSliders();
   }

   LinkedList SelectList() {
      return this.jRadioButton1.isSelected() ? this.Llista1 : this.Llista2;
   }

   void UpdateLlista() {
      new String();
      LinkedList L = this.SelectList();

      int i;
      for(i = 0; i < 5; ++i) {
         ((JRadioButton)this.RB.get(i)).setEnabled(false);
         ((JRadioButton)this.RB.get(i)).setSelected(false);
         ((JRadioButton)this.RB.get(i)).setText("");
         ((JRadioButton)this.RB.get(i)).setBorderPainted(false);
      }

      for(i = 0; i < L.size(); ++i) {
         DataRaig F = (DataRaig)L.get(i);
         String Text = "h = " + this.DF1.format(F.h) + ", θ = " + this.DF1.format(F.theta) + ", φ = " + this.DF1.format(F.fi);
         ((JRadioButton)this.RB.get(i)).setText(Text);
         ((JRadioButton)this.RB.get(i)).setBorder(BorderFactory.createLineBorder(this.colors[i]));
         ((JRadioButton)this.RB.get(i)).setBorderPainted(true);
         ((JRadioButton)this.RB.get(i)).setEnabled(true);
      }

      this.UpdateRaig();
   }

   void UpdateFibraFrame() {
      if (this.fibraFrame1.isVisible()) {
         DataFibra DF = this.SelectDataFibra();
         this.fibraFrame1.DF = DF;
         this.fibraFrame1.sliderTextFiberRadius.setValcur(DF.radi);
         this.fibraFrame1.sliderTextCoreIndex.setValcur(DF.n1);
         this.fibraFrame1.sliderTextCladdingIndex.setValcur(DF.n2);
         this.fibraFrame1.sliderTextExponent.setValcur(DF.p);
      }
   }

   void jRadioButton1_stateChanged(ChangeEvent e) {
      this.UpdateLlista();
      this.UpdateFibraFrame();
      if (this.jRadioButton1.isSelected()) {
         this.jPanelFibra1.setBorder(this.border2);
         this.jPanelFibra2.setBorder(this.border1);
      } else {
         this.jPanelFibra1.setBorder(this.border1);
         this.jPanelFibra2.setBorder(this.border2);
      }

   }

   int RBButtonSelected() {
      int i;
      for(i = 0; i < 5 && !((JRadioButton)this.RB.get(i)).isSelected(); ++i) {
      }

      return i;
   }

   void jButtonEsborrar_actionPerformed(ActionEvent e) {
      int index = false;
      LinkedList L = this.SelectList();
      int index = this.RBButtonSelected();
      if (index <= 4) {
         if (L.size() > 0) {
            L.remove(index);
         }

         this.UpdateLlista();
         if (index > 0) {
            ((JRadioButton)this.RB.get(index - 1)).setSelected(true);
         } else if (index == 0) {
            ((JRadioButton)this.RB.get(index)).setSelected(true);
         }

         this.UpdateSliders();
      }
   }

   void UpdateRaig() {
      int index = false;
      new DataRaig();
      new DataFibra();
      this.graph2DPanelSide1.resetData();
      this.graph2DPanelSide2.resetData();
      this.graph2DPanelTop1.resetData();
      this.graph2DPanelTop2.resetData();
      this.SetLimitsFibres();
      this.graph2DPanelSide1.addData(this.F1z, this.F1x, 5, 1, Color.RED);
      this.graph2DPanelSide2.addData(this.F2z, this.F2x, 5, 1, Color.RED);
      this.graph2DPanelTop1.addData(this.C1x, this.C1y, 91, 1, Color.RED);
      this.graph2DPanelTop2.addData(this.C2x, this.C2y, 91, 1, Color.RED);
      this.graph2DPanelSide1.xmax = Fibra1.L * 1000.0D * 1.05D;
      this.graph2DPanelSide1.xmin = -Fibra1.L * 1000.0D * 0.05D;
      this.graph2DPanelSide1.ymax = Fibra1.radi * 1.1D;
      this.graph2DPanelSide1.ymin = -Fibra1.radi * 1.1D;
      this.graph2DPanelTop1.ymax = Fibra1.radi * 1.1D;
      this.graph2DPanelTop1.ymin = -Fibra1.radi * 1.1D;
      this.graph2DPanelTop1.xmax = Fibra1.radi * 1.1D;
      this.graph2DPanelTop1.xmin = -Fibra1.radi * 1.1D;
      this.graph2DPanelSide1.TexteX = textGraphSideX[lang];
      this.graph2DPanelSide1.TexteY = "x (e-6m)";
      this.graph2DPanelTop1.TexteX = "x (e-6m)";
      this.graph2DPanelTop1.TexteY = "y (e-6m)";
      this.graph2DPanelSide1.flag_splimits = true;
      this.graph2DPanelSide1.xminlabel = 0.0D;
      this.graph2DPanelSide1.xmaxlabel = Fibra1.L * 1000.0D;
      this.graph2DPanelSide1.yminlabel = -Fibra1.radi;
      this.graph2DPanelSide1.ymaxlabel = Fibra1.radi;
      this.graph2DPanelTop1.flag_splimits = true;
      this.graph2DPanelTop1.xminlabel = -Fibra1.radi;
      this.graph2DPanelTop1.xmaxlabel = Fibra1.radi;
      this.graph2DPanelTop1.yminlabel = -Fibra1.radi;
      this.graph2DPanelTop1.ymaxlabel = Fibra1.radi;
      this.graph2DPanelSide1.TexteX = textGraphSideX[lang];
      this.graph2DPanelSide1.TexteY = "x (e-6m)";
      this.graph2DPanelTop1.TexteX = "x (e-6m)";
      this.graph2DPanelTop1.TexteY = "y (e-6m)";
      Graph2DPanel var10001 = this.graph2DPanelTop1;
      this.graph2DPanelSide1.flag_extra_text = 5;
      this.graph2DPanelSide1.TexteExtra = "n1 = " + this.DF1.format(Fibra1.n1) + "; n2 = " + this.DF1.format(Fibra1.n2) + "; p =" + this.DF1.format(Fibra1.p) + textRCore[lang] + this.DF1.format(Fibra1.radi) + "e-6m; L = " + this.DF1.format(Fibra1.L * 1000.0D) + "e-6m";

      DataRaig DR;
      DataFibra DF;
      int index;
      for(index = 0; index < this.Llista1.size(); ++index) {
         DR = (DataRaig)this.Llista1.get(index);
         DF = Fibra1;
         DF.RayTrace(DR);
         this.graph2DPanelSide1.addData(DR.z, DR.x, DR.N, 1, this.colors[index]);
         this.graph2DPanelTop1.addData(DR.x, DR.y, DR.N, 1, this.colors[index]);
      }

      this.graph2DPanelSide1.repaint();
      this.graph2DPanelTop1.repaint();
      this.graph2DPanelSide2.xmax = Fibra2.L * 1000.0D * 1.05D;
      this.graph2DPanelSide2.xmin = -Fibra2.L * 1000.0D * 0.05D;
      this.graph2DPanelSide2.ymax = Fibra2.radi * 1.1D;
      this.graph2DPanelSide2.ymin = -Fibra2.radi * 1.1D;
      this.graph2DPanelTop2.ymax = Fibra2.radi * 1.1D;
      this.graph2DPanelTop2.ymin = -Fibra2.radi * 1.1D;
      this.graph2DPanelTop2.xmax = Fibra2.radi * 1.1D;
      this.graph2DPanelTop2.xmin = -Fibra2.radi * 1.1D;
      this.graph2DPanelSide2.flag_splimits = true;
      this.graph2DPanelSide2.xminlabel = 0.0D;
      this.graph2DPanelSide2.xmaxlabel = Fibra2.L * 1000.0D;
      this.graph2DPanelSide2.yminlabel = -Fibra2.radi;
      this.graph2DPanelSide2.ymaxlabel = Fibra2.radi;
      this.graph2DPanelTop2.flag_splimits = true;
      this.graph2DPanelTop2.xminlabel = -Fibra2.radi;
      this.graph2DPanelTop2.xmaxlabel = Fibra2.radi;
      this.graph2DPanelTop2.yminlabel = -Fibra2.radi;
      this.graph2DPanelTop2.ymaxlabel = Fibra2.radi;
      this.graph2DPanelSide2.TexteX = textGraphSideX[lang];
      this.graph2DPanelSide2.TexteY = "x (e-6m)";
      this.graph2DPanelTop2.TexteX = "x (e-6m)";
      this.graph2DPanelTop2.TexteY = "y (e-6m)";
      this.graph2DPanelSide2.flag_extra_text = 5;
      this.graph2DPanelSide2.TexteExtra = "n1 = " + this.DF1.format(Fibra2.n1) + "; n2 = " + this.DF1.format(Fibra2.n2) + "; p =" + this.DF1.format(Fibra2.p) + textRCore[lang] + this.DF1.format(Fibra2.radi) + "e-6m; L = " + this.DF1.format(Fibra2.L * 1000.0D) + "e-6m";

      for(index = 0; index < this.Llista2.size(); ++index) {
         DR = (DataRaig)this.Llista2.get(index);
         DF = Fibra2;
         DF.RayTrace(DR);
         this.graph2DPanelSide2.addData(DR.z, DR.x, DR.N, 1, this.colors[index]);
         this.graph2DPanelTop2.addData(DR.x, DR.y, DR.N, 1, this.colors[index]);
      }

      this.graph2DPanelSide2.repaint();
      this.graph2DPanelTop2.repaint();
   }

   void sliderTextHeight_stateChanged(ChangeEvent e) {
      LinkedList L = this.SelectList();
      new DataRaig();
      int index = this.RBButtonSelected();
      if (index < L.size()) {
         DataRaig DR = (DataRaig)L.get(index);
         DR.h = this.sliderTextHeight.valcur;
         this.UpdateLlista();
      }

   }

   void jRadioButtonR1_mousePressed(MouseEvent e) {
      if (e.getButton() == 3) {
         this.jRadioButton1.setSelected(true);
         this.jPopupMenu1.show(this.jRadioButtonR1, e.getX(), e.getY());
      }

   }

   void jRadioButtonR2_mouseClicked(MouseEvent e) {
      this.UpdateSliders();
   }

   void jRadioButtonR3_mouseClicked(MouseEvent e) {
      this.UpdateSliders();
   }

   void jRadioButtonR4_mouseClicked(MouseEvent e) {
      this.UpdateSliders();
   }

   void jRadioButtonR5_mouseClicked(MouseEvent e) {
      this.UpdateSliders();
   }

   void jRadioButtonR1_mouseClicked(MouseEvent e) {
      this.UpdateSliders();
   }

   void UpdateSliders() {
      LinkedList L = this.SelectList();
      if (L.size() == 0) {
         this.sliderTextHeight.setEnabled(false);
         this.sliderTextTheta.setEnabled(false);
         this.sliderTextPhi.setEnabled(false);
      } else {
         this.sliderTextHeight.setEnabled(true);
         this.sliderTextTheta.setEnabled(true);
         this.sliderTextPhi.setEnabled(true);
         int index = this.RBButtonSelected();
         if (index < L.size()) {
            DataRaig DR = (DataRaig)L.get(index);
            DataFibra DFi = this.SelectDataFibra();
            this.sliderTextHeight.setValmax(DFi.radi);
            this.sliderTextHeight.setValcur(DR.h);
            this.sliderTextTheta.setValcur(DR.theta);
            this.sliderTextPhi.setValcur(DR.fi);
         }

      }
   }

   void jRadioButtonR2_mousePressed(MouseEvent e) {
      if (e.getButton() == 3) {
         this.jRadioButtonR2.setSelected(true);
         this.jPopupMenu1.show(this.jRadioButtonR2, e.getX(), e.getY());
      }

   }

   void jRadioButtonR3_mousePressed(MouseEvent e) {
      if (e.getButton() == 3) {
         this.jRadioButtonR3.setSelected(true);
         this.jPopupMenu1.show(this.jRadioButtonR3, e.getX(), e.getY());
      }

   }

   void jRadioButtonR4_mousePressed(MouseEvent e) {
      if (e.getButton() == 3) {
         this.jRadioButtonR4.setSelected(true);
         this.jPopupMenu1.show(this.jRadioButtonR4, e.getX(), e.getY());
      }

   }

   void jRadioButtonR5_mousePressed(MouseEvent e) {
      if (e.getButton() == 3) {
         this.jRadioButtonR5.setSelected(true);
         this.jPopupMenu1.show(this.jRadioButtonR5, e.getX(), e.getY());
      }

   }

   void sliderTextTheta_stateChanged(ChangeEvent e) {
      LinkedList L = this.SelectList();
      new DataRaig();
      int index = this.RBButtonSelected();
      if (index < L.size()) {
         DataRaig DR = (DataRaig)L.get(index);
         DR.theta = this.sliderTextTheta.valcur;
         this.UpdateLlista();
      }

   }

   void sliderTextPhi_stateChanged(ChangeEvent e) {
      LinkedList L = this.SelectList();
      new DataRaig();
      int index = this.RBButtonSelected();
      if (index < L.size()) {
         DataRaig DR = (DataRaig)L.get(index);
         DR.fi = this.sliderTextPhi.valcur;
         this.UpdateLlista();
      }

   }

   void jButtonEditar_actionPerformed(ActionEvent e) {
      DataFibra DF = this.SelectDataFibra();
      this.fibraFrame1.sliderTextFiberRadius.setValcur(DF.radi);
      this.fibraFrame1.sliderTextCoreIndex.setValcur(DF.n1);
      this.fibraFrame1.sliderTextCladdingIndex.setValcur(DF.n2);
      this.fibraFrame1.sliderTextExponent.setValcur(DF.p);
      this.fibraFrame1.setSize(420, 400);
      this.fibraFrame1.DF = DF;
      this.fibraFrame1.show();
   }

   DataFibra SelectDataFibra() {
      return this.jRadioButton1.isSelected() ? Fibra1 : Fibra2;
   }

   void fibraFrame1_stateChanged(ChangeEvent e) {
      DataFibra DF = this.SelectDataFibra();
      DF.radi = this.fibraFrame1.sliderTextFiberRadius.valcur;
      DF.n1 = this.fibraFrame1.sliderTextCoreIndex.valcur;
      DF.n2 = this.fibraFrame1.sliderTextCladdingIndex.valcur;
      DF.p = this.fibraFrame1.sliderTextExponent.valcur;
      if (DF.p == 0.0D) {
         DF.tipus = 's';
      } else {
         DF.tipus = 'g';
      }

      DF.CreaLams();
      if (this.fibraFrame1.sliderTextLength.valcur == 0.0D) {
         this.fibraFrame1.sliderTextLength.setValcur(0.01D);
      }

      DF.L = this.fibraFrame1.sliderTextLength.valcur;
      this.UpdateSliders();
      this.UpdateRaig();
   }

   void UpdateModes() {
      DataMode dm = new DataMode();
      int num_nummod = 6;
      int old_selected_index1 = this.jListModes1.getSelectedIndex();
      int old_selected_index2 = this.jListModes2.getSelectedIndex();
      int[][] nummod = new int[10][1];
      if (FibraM.n1 <= FibraM.n2) {
         num_nummod = 0;
      } else if (FibraM.radi == 0.0D) {
         num_nummod = 0;
      } else {
         dm.CalcNumModes(FibraM.radi * 1000.0D, FibraM.lambda, FibraM.n1, FibraM.n2, 0, nummod, num_nummod);
      }

      Vector Strings = new Vector();
      new String();
      this.lockcombos = 1;
      this.jComboBoxMode1.removeAllItems();
      this.jComboBoxMode2.removeAllItems();

      for(int l = 0; l < num_nummod; ++l) {
         for(int m = 0; m < nummod[l][0]; ++m) {
            String Texte = "l = " + l + " , m = " + m;
            Strings.add(Texte);
            this.jComboBoxMode1.addItem(Texte);
            this.jComboBoxMode2.addItem(Texte);
         }
      }

      this.lockcombos = 0;
      this.jListModes1.setListData(Strings);
      this.jListModes2.setListData(Strings);
      int sizelist = this.jListModes1.getModel().getSize();
      int selindex1 = old_selected_index1;
      int selindex2 = old_selected_index2;
      if (sizelist != 0) {
         if (sizelist == 1) {
            selindex2 = 0;
            selindex1 = 0;
         }

         if (selindex1 == -1) {
            selindex1 = 0;
         }

         if (selindex2 == -1) {
            if (selindex1 < sizelist - 1) {
               selindex2 = selindex1 + 1;
            } else {
               selindex2 = selindex1 - 1;
            }
         }

         this.jListModes1.setSelectedIndex(0);
         this.jComboBoxMode1.setSelectedIndex(0);
         this.jListModes2.setSelectedIndex(0);
         this.jComboBoxMode2.setSelectedIndex(0);
         this.UpdateModeLabels();
      }
   }

   void UpdateModeLabels() {
      int selindex1 = this.jListModes1.getSelectedIndex();
      int selindex2 = this.jListModes2.getSelectedIndex();
      if (selindex1 > 0) {
         this.jComboBoxMode1.setSelectedIndex(selindex1);
      }

      if (selindex2 > 0) {
         this.jComboBoxMode2.setSelectedIndex(selindex2);
      }

      this.UpdateGrafica();
   }

   void SetupGrafica() {
      this.graph2DPanel1.resetData();
      this.graph2DPanel1.flag_legend = 5;
      this.graph2DPanel1.xmin = 0.0D;
      this.graph2DPanel1.ymin = 0.0D;
      this.graph2DPanel1.ymax = 1.0D;
      this.graph2DPanel1.TexteX = textGraphProfileX[lang];
      this.graph2DPanel1.TexteY = "I";
      this.graph2DPanel1.repaint();
      this.graph2DPanel2.resetData();
      this.graph2DPanel2.flag_legend = 5;
      this.graph2DPanel2.xmin = 0.0D;
      this.graph2DPanel2.ymin = 0.0D;
      this.graph2DPanel2.ymax = 1.0D;
      this.graph2DPanel2.TexteX = textGraphProfileX[lang];
      this.graph2DPanel2.TexteY = "I";
      this.graph2DPanel2.repaint();
   }

   void UpdateGrafica() {
      int num_nummod = 6;
      int[][] nummod = new int[10][1];
      DataMode dm = new DataMode();
      boolean num_nummod;
      if (FibraM.n1 <= FibraM.n2) {
         num_nummod = false;
      } else if (FibraM.radi == 0.0D) {
         num_nummod = false;
      } else {
         double[][] pModes = dm.CalcNumModes(FibraM.radi * 1000.0D, FibraM.lambda, FibraM.n1, FibraM.n2, 0, nummod, num_nummod);
         new String();
         double V = 6.283185307179586D / FibraM.lambda * FibraM.radi * 1000.0D * Math.sqrt(FibraM.n1 * FibraM.n1 - FibraM.n2 * FibraM.n2);
         String S = "V = " + this.DF1.format(V);
         this.jLabelVinfo.setText(S);
         int selindex1 = this.jListModes1.getSelectedIndex();
         if (selindex1 != -1) {
            String Selection1 = (String)this.jListModes1.getSelectedValue();
            String[] Sels1 = Selection1.split(" ");
            int l1 = Integer.parseInt(Sels1[2]);
            int m1 = Integer.parseInt(Sels1[6]);
            selindex1 = this.jListModes2.getSelectedIndex();
            if (selindex1 != -1) {
               String Selection2 = (String)this.jListModes2.getSelectedValue();
               String[] Sels2 = Selection2.split(" ");
               int l2 = Integer.parseInt(Sels2[2]);
               int m2 = Integer.parseInt(Sels2[6]);
               double X = pModes[l1][m1];
               double[] valsx = new double[40];
               double[] valsy = new double[40];
               double[] valsn = new double[40];
               dm.CalcPerfilMode(l1, FibraM.radi * 1000.0D, FibraM.lambda, X, Math.sqrt(V * V - X * X), valsx, valsy, valsn, 40);
               this.graph2DPanel1.resetData();
               this.graph2DPanel1.addData(valsx, valsy, 40, 1, Color.red);
               this.graph2DPanel1.addData(valsx, valsn, 40, 1, Color.green);
               this.graph2DPanel1.xmin = 0.0D;
               this.graph2DPanel1.xmax = FibraM.radi * 1.2D;
               this.graph2DPanel1.ymin = 0.0D;
               this.graph2DPanel1.ymax = 1.0D;
               this.graph2DPanel1.TexteX = textGraphProfileX[lang];
               this.graph2DPanel1.TexteY = textGraphProfileY[lang];
               this.graph2DPanel1.repaint();
               this.fieldPanel1.SetData(valsx, valsy, l1, m1);
               this.fieldPanel1.repaint();
               X = pModes[l2][m2];
               valsx = new double[40];
               valsy = new double[40];
               valsn = new double[40];
               dm.CalcPerfilMode(l2, FibraM.radi * 1000.0D, FibraM.lambda, X, Math.sqrt(V * V - X * X), valsx, valsy, valsn, 40);
               this.graph2DPanel2.resetData();
               this.graph2DPanel2.addData(valsx, valsy, 40, 1, Color.red);
               this.graph2DPanel2.addData(valsx, valsn, 40, 1, Color.green);
               this.graph2DPanel2.xmin = 0.0D;
               this.graph2DPanel2.xmax = FibraM.radi * 1.2D;
               this.graph2DPanel2.ymin = 0.0D;
               this.graph2DPanel2.ymax = 1.0D;
               this.graph2DPanel2.TexteX = textGraphProfileX[lang];
               this.graph2DPanel2.TexteY = textGraphProfileY[lang];
               this.graph2DPanel2.repaint();
               this.fieldPanel2.SetData(valsx, valsy, l2, m2);
               this.fieldPanel2.repaint();
            }
         }
      }
   }

   void sliderTextLambda_stateChanged(ChangeEvent e) {
      FibraM.lambda = this.sliderTextLambda.valcur;
      this.UpdateModes();
      this.UpdateGrafica();
   }

   void sliderTextRCore_stateChanged(ChangeEvent e) {
      FibraM.radi = this.sliderTextRCore.valcur;
      this.UpdateModes();
      this.UpdateGrafica();
   }

   void sliderTextN1_stateChanged(ChangeEvent e) {
      FibraM.n1 = this.sliderTextN1.valcur;
      FibraM.n2 = FibraM.n1 - this.sliderTextDeltaN.valcur;
      this.UpdateModes();
      this.UpdateGrafica();
   }

   void sliderTextDeltaN_stateChanged(ChangeEvent e) {
      FibraM.n2 = FibraM.n1 - this.sliderTextDeltaN.valcur;
      this.UpdateModes();
      this.UpdateGrafica();
   }

   void jRadioButton2_actionPerformed(ActionEvent e) {
   }

   void SetLimitsFibres() {
      this.F1z[0] = this.F1z[3] = this.F1z[4] = 0.0D;
      this.F1z[1] = this.F1z[2] = Fibra1.L * 1000.0D;
      this.F1x[0] = this.F1x[1] = this.F1x[4] = -Fibra1.radi;
      this.F1x[2] = this.F1x[3] = Fibra1.radi;
      this.F2z[0] = this.F2z[3] = this.F2z[4] = 0.0D;
      this.F2z[1] = this.F2z[2] = Fibra2.L * 1000.0D;
      this.F2x[0] = this.F2x[1] = this.F2x[4] = -Fibra2.radi;
      this.F2x[2] = this.F2x[3] = Fibra2.radi;

      for(int i = 0; i <= 90; ++i) {
         this.C1x[i] = Fibra1.radi * Math.cos((double)i * 4.0D / 180.0D * 3.141592653589793D);
         this.C1y[i] = Fibra1.radi * Math.sin((double)i * 4.0D / 180.0D * 3.141592653589793D);
         this.C2x[i] = Fibra2.radi * Math.cos((double)i * 4.0D / 180.0D * 3.141592653589793D);
         this.C2y[i] = Fibra2.radi * Math.sin((double)i * 4.0D / 180.0D * 3.141592653589793D);
      }

   }

   void jListModes1_valueChanged(ListSelectionEvent e) {
      this.UpdateModeLabels();
   }

   void jListModes2_valueChanged(ListSelectionEvent e) {
      this.UpdateModeLabels();
   }

   void jComboBoxMode1_actionPerformed(ActionEvent e) {
      if (this.lockcombos != 1) {
         int selindex = this.jComboBoxMode1.getSelectedIndex();
         int sizelist = this.jListModes1.getModel().getSize();
         if (selindex < sizelist) {
            this.jListModes1.setSelectedIndex(selindex);
         }

         this.UpdateGrafica();
      }
   }

   void jPanelFibra1_mouseClicked(MouseEvent e) {
      int clics = e.getClickCount();
      this.jRadioButton1.setSelected(true);
      if (clics > 1) {
         DataFibra DF = this.SelectDataFibra();
         this.fibraFrame1.sliderTextFiberRadius.setValcur(DF.radi);
         this.fibraFrame1.sliderTextCoreIndex.setValcur(DF.n1);
         this.fibraFrame1.sliderTextCladdingIndex.setValcur(DF.n2);
         this.fibraFrame1.sliderTextExponent.setValcur(DF.p);
         this.fibraFrame1.DF = DF;
         this.fibraFrame1.setSize(420, 400);
         this.fibraFrame1.show();
      }

   }

   void jPanelFibra2_mouseClicked(MouseEvent e) {
      int clics = e.getClickCount();
      this.jRadioButton2.setSelected(true);
      if (clics > 1) {
         DataFibra DF = this.SelectDataFibra();
         this.fibraFrame1.sliderTextFiberRadius.setValcur(DF.radi);
         this.fibraFrame1.sliderTextCoreIndex.setValcur(DF.n1);
         this.fibraFrame1.sliderTextCladdingIndex.setValcur(DF.n2);
         this.fibraFrame1.sliderTextExponent.setValcur(DF.p);
         this.fibraFrame1.DF = DF;
         this.fibraFrame1.setSize(420, 400);
         this.fibraFrame1.show();
      }

   }

   void graph2DPanel1_componentShown(ComponentEvent e) {
      this.UpdateGrafica();
   }

   void fieldPanel1_componentShown(ComponentEvent e) {
      this.UpdateGrafica();
   }

   void jLabelVinfo_propertyChange(PropertyChangeEvent e) {
      new String();
      double V = 6.283185307179586D / FibraM.lambda * FibraM.radi * 1000.0D * Math.sqrt(FibraM.n1 * FibraM.n1 - FibraM.n2 * FibraM.n2);
      String S = "V = " + this.DF1.format(V);
      this.jLabelVinfo.setText(S);
   }

   void fieldPanel2_componentShown(ComponentEvent e) {
      this.UpdateGrafica();
   }

   void jComboBoxMode2_actionPerformed(ActionEvent e) {
      if (this.lockcombos != 1) {
         int selindex = this.jComboBoxMode2.getSelectedIndex();
         int sizelist = this.jListModes2.getModel().getSize();
         if (selindex < sizelist) {
            this.jListModes2.setSelectedIndex(selindex);
         }

         this.UpdateGrafica();
      }
   }

   void jTabbedPane1_mouseClicked(MouseEvent e) {
   }

   void jMenuItem1_actionPerformed(ActionEvent e) {
      this.jButtonAfegir_actionPerformed(e);
   }

   void jMenuItem2_actionPerformed(ActionEvent e) {
      this.jButtonEsborrar_actionPerformed(e);
   }

   void this_componentShown(ComponentEvent e) {
   }

   void jButtonAbout_actionPerformed(ActionEvent e) {
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
      JDialog dialog = hola.createDialog(f, textAbout[lang]);
      dialog.setResizable(false);
      dialog.show();
      this.repaint();
   }

   public class TextLlista {
      String Text;
   }
}
