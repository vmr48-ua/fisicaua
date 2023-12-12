package polar;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import org.apache.commons.math.complex.Complex;
import org.apache.commons.math.complex.ComplexUtils;

public class PolarApplet extends Applet {
   static int idioma;
   String[][] etiqueta = new String[][]{{"Polarización de la luz y coeficientes de Fresnel", "Polarització de la llum i coeficients de Fresnel", "Light polarization and Fresnel coefficients"}, {"Polarización", "Polarització", "Polarization"}, {"Dieléctricos", "Dielèctrics", "Dielectrics"}, {"Conductores", "Conductors", "Conductors"}, {"Resumen teoría", "Resum teoria", "Theory summary"}, {"Salir", "Sortir", "Exit"}, {"Ayuda", "Ajuda", "Help"}};
   String[][] acerca_etiqueta = new String[][]{{"Acerca de", "En quant a", "About"}, {"Aceptar", "Acceptar", "OK"}, {"Applet de Polarización de la luz, versión 0.9\nGID Óptica Física y Fotónica\nUniversitat de Barcelona 2003\nLa utilización de este programa está bajo una licencia de Creative Commons - UB\nVer condiciones en http://creativecommons.org/licenses/by-nc-sa/2.0/\nCurso de Óptica en Java DOI: 10.1344/401.000000050\nApplet de Polarización de la luz DOI: 10.1344/203.000000101\nEste programa hace servir algunas de la funciones de la libreria commons-math 1.1\nhttp://www.apache.org/licenses/LICENSE-2.0", "Applet de Polarització de la llum, versió 0.9\nGID Òptica Física i Fotònica\nUniversitat de Barcelona 2003\nLa utilització d'aquest programa està sota una llicència de Creative Commons - UB\nVeure condicions a http://www.publicacions.ub.es/doi/licencia/resum-deriv.htm\nCurs d'Òptica en Java DOI: 10.1344/401.000000050\nApplet de Polarització de la llum DOI: 10.1344/203.000000101\nAquest programa fa servir algunes funcions de la llibreria commons-math 1.1\nhttp://www.apache.org/licenses/LICENSE-2.0", "Light polarization Applet, version 0.9\nGID Òptica Física i Fotònica\nUniversitat de Barcelona 2003\nThe use of this program is under a Creative Commons license\nSee conditions in http://creativecommons.org/licenses/by-nc-sa/2.0/\nJava Optics Course DOI: 10.1344/401.000000050\nLight polarization Applet DOI: 10.1344/203.000000101\nThis program uses the commons-math 1.1 library\nhttp://www.apache.org/licenses/LICENSE-2.0"}};
   String[][] P_etiqueta = new String[][]{{"Amplitud onda p", "Amplitud ona p", "p wave amplitude"}, {"Amplitud onda s", "Amplitud ona s", "s wave amplitude"}, {"Desfase", "Desfasament", "Phase retardance"}, {"Onda Incidente", "Ona Incident", "Incident Wave"}};
   String[][] elipse_etiqueta = new String[][]{{"Semieje mayor", "Semieix major", "Semimajor axis"}, {"Semieje menor", "Semieix menor", "Semiminor axis"}, {"Ángulo con eje p", "Angle amb eix p", "Angle with p axis"}, {"Parámetros de Stokes", "Paràmetres de Stokes", "Stokes parameters"}};
   String[][] F_etiqueta = new String[][]{{"n", "n", "n"}, {"n' ", "n' ", "n' "}, {"Ángulo de incidencia", "Angle d'incidència", "Incident Angle"}, {"Ángulo de refracción", "Angle de refracció", "Refraction Angle"}, {"Ángulo de Brewster", "Angle de Brewster", "Brewster Angle"}, {"Ángulo límite", "Angle límit", "Critical angle"}, {"Reflexión total", "Reflexió total", "Total internal reflection"}, {"Coeficientes de Fresnel", "Coeficients de Fresnel", "Fresnel coeficients"}, {"Polarización", "Polarització", "Polarization"}, {"angp", "angp", "angp"}, {"angs", "angs", "angs"}, {"Desfase total", "Desfasament total", "Total phase retardance"}};
   String[] PolIni_etiqueta = new String[]{"Ir a Polarización para cambiar el estado de polarización de la luz incidente", "Anar a Polarització per canviar l'estat de polarització de la llum incident", "Click on Polarization to change the polarization state of the incident light"};
   String[][] M_etiqueta = new String[][]{{"n' ", "n' ", "n' "}, {"k", "k", "k"}, {"Longitud de onda", "Longitud d'ona", "Wavelength"}, {"Onda Reflejada", "Ona Reflectida", "Reflected wave"}, {"Onda Transmitida", "Ona Transmesa", "Transmitted wave"}, {"(incidencia normal)", "(incidència normal)", "(normal incidence)"}, {"Prof. de penetración", "Prof. de penetració", "Skin depth"}, {"inf.", "inf.", "inf."}};
   boolean isStandalone = false;
   BorderLayout borderLayout = new BorderLayout();
   JPanel jPanel_Principal = new JPanel();
   JPanel jPanel_Salida = new JPanel();
   JButton jButton_Salir = new JButton();
   JButton jButton_Acerca = new JButton();
   FlowLayout flowLayout_Salida = new FlowLayout();
   JTabbedPane jTabbedPane = new JTabbedPane();
   BorderLayout borderLayout_Principal = new BorderLayout();
   JPanel jPanel_Polar = new JPanel();
   JPanel jPanel_Fresnel = new JPanel();
   BorderLayout borderLayout_F = new BorderLayout();
   JPanel jPanel_FCentro = new JPanel();
   BorderLayout borderLayout_FC = new BorderLayout();
   JPanel jPanel_Doc = new JPanel();
   JPanel jPanel_Metal = new JPanel();
   JPanel jPanel_PCentro = new JPanel();
   BorderLayout borderLayout_P = new BorderLayout();
   BorderLayout borderLayout_PC = new BorderLayout();
   JPanel jPanel_PCC = new JPanel();
   JPanel jPanel_PCEast = new JPanel();
   JPanel JPanel_PCSouth = new JPanel();
   PanelOndas ondas = new PanelOndas();
   BorderLayout borderLayout_PCC = new BorderLayout();
   JPanel jPanel_PCEC = new JPanel();
   BorderLayout borderLayout_PCE = new BorderLayout();
   FlowLayout flowLayout_PCEC = new FlowLayout();
   JLabel jLabel_PA2 = new JLabel();
   JLabel jLabel_PA1 = new JLabel();
   JLabel jLabel_PFase = new JLabel();
   JSlider jSlider_PA2 = new JSlider();
   JSlider jSlider_PFase = new JSlider();
   JSlider jSlider_PA1 = new JSlider();
   JPanel jPanel_PCEN = new JPanel();
   JPanel jPanel_PCES = new JPanel();
   JPanel jPanel_PCEW = new JPanel();
   JPanel jPanel_PCEE = new JPanel();
   BorderLayout borderLayout_PCS = new BorderLayout();
   BorderLayout borderLayout_D = new BorderLayout();
   BorderLayout borderLayout_M = new BorderLayout();
   JPanel jPanel_FCEast = new JPanel();
   JPanel jPanel_FCC = new JPanel();
   JPanel jPanel_FCS = new JPanel();
   JPanel jPanel_FCSW = new JPanel();
   JPanel jPanel_FCSE = new JPanel();
   JPanel jPanel_FCSS = new JPanel();
   JPanel jPanel_FCSN = new JPanel();
   JPanel jPanel_FCSC = new JPanel();
   BorderLayout borderLayout_FCS = new BorderLayout();
   FlowLayout flowLayout_FCSC = new FlowLayout();
   JLabel jLabel_FNi = new JLabel();
   JSlider jSlider_FNi = new JSlider();
   JLabel jLabel_FNr = new JLabel();
   JSlider jSlider_FNr = new JSlider();
   JLabel jLabel_FAngi = new JLabel();
   JSlider jSlider_FAngi = new JSlider();
   PanelDibFresnel dibFresnel = new PanelDibFresnel();
   BorderLayout borderLayout_FCC = new BorderLayout();
   BorderLayout borderLayout_FCE = new BorderLayout();
   JPanel jPanel_FCEN = new JPanel();
   JPanel jPanel_FCENW = new JPanel();
   JPanel jPanel_FCENE = new JPanel();
   JPanel jPanel_FCENS = new JPanel();
   JPanel jPanel_FCENN = new JPanel();
   BorderLayout borderLayout_FCEN = new BorderLayout();
   JLabel jLabel_FAngr = new JLabel();
   TitledBorder titledBorder1;
   JLabel jLabel_FAngB = new JLabel();
   JLabel jLabel_FAngL = new JLabel();
   JPanel jPanel_FCEC = new JPanel();
   BorderLayout borderLayout_FCEC = new BorderLayout();
   JPanel jPanelL_FPolar = new JPanel();
   JPanel jPanelR_FGrafico = new JPanel();
   JSplitPane jSplitPane;
   PanelGrafFresnel grafFresnel;
   PanelElipse elipsei;
   PanelElipse elipser;
   PanelElipse elipset;
   PanelGrafMetalT grafMetalT;
   JPanel jPanel_FCENC;
   JLabel jLabel_Fts;
   GridLayout gridLayout_FCoefic;
   JLabel jLabel_Ftp;
   JPanel jPanel_FCoefic;
   JLabel jLabel_Frs;
   JLabel jLabel_Frp;
   BorderLayout borderLayout_FCENC;
   BorderLayout borderLayoutR_FGrafico;
   JPanel jPanel_RFGS;
   JPanel jPanel_RFGE;
   JPanel jPanel_RFGW;
   JPanel jPanel_RFGN;
   JLabel jLabel_TitCoefFresnel;
   BorderLayout borderLayout_RFGN;
   JPanel jPanel_RFGNN;
   static boolean label_grafico = false;
   JPanel jPanel_FBoton;
   JButton jButton_FresPol;
   JPanel jPanel_elipse;
   PanelElipse elipse;
   BorderLayout borderLayout_jPanelelipse;
   JLabel jLabel_elipAng;
   JLabel jLabel_stokes4;
   JLabel jLabel_stokes3;
   GridLayout gridLayout_elipsecar;
   JLabel jLabel_stokes2;
   JLabel jLabel_stokes1;
   JPanel jPanel_elipsecar;
   JLabel jLabel_stokest;
   JLabel jLabel_elipB;
   JLabel jLabel_elipA;
   JPanel jPanel_PCCS;
   JPanel jPanel_PCCW;
   JPanel jPanel_PCCE;
   JPanel jPanel_PCCN;
   BorderLayout borderLayout_elipse;
   BorderLayout borderLayout_elipse1;
   BorderLayout borderLayout_elipse2;
   GridLayout gridLayout_FPolar;
   JPanel jPanel_FP11;
   JPanel jPanel_FP12;
   JPanel jPanel_FP21;
   JPanel jPanel_FP22;
   BorderLayout borderLayout_FP1;
   BorderLayout borderLayout_FP2;
   BorderLayout borderLayout_FP3;
   BorderLayout borderLayout_FP4;
   BorderLayout borderLayout_FP11;
   BorderLayout borderLayout_FP12;
   BorderLayout borderLayout_FP21;
   BorderLayout borderLayout_FP22;
   JPanel jPanel_FPolini;
   JPanel jPanel_FPoliniMssg;
   JLabel jLabel_FPoli;
   JLabel jLabel_FPolia1;
   JLabel jLabel_FPolia2;
   JLabel jLabel_FPolifas;
   FlowLayout flowLayout_FPolini;
   JTextPane jTextPane_FPoliMssg;
   FlowLayout flowLayout_FPoliMssg;
   JLabel jLabel_RefTotal;
   BorderLayout borderLayout_FBoton;
   JPanel jPanel_MCentro;
   BorderLayout borderLayout_MC;
   JPanel jPanel_MCC;
   BorderLayout borderLayout_MCC;
   PanelDibMetal dibMetal;
   JPanel jPanel_MCEast;
   BorderLayout borderLayout_MCE;
   JPanel jPanel_MCS;
   BorderLayout borderLayout_MCS;
   JPanel jPanel_MCSC;
   JPanel jPanel_MCSN;
   JPanel jPanel_MCSS;
   JPanel jPanel_MCSW;
   JPanel jPanel_MCSE;
   JLabel jLabel_MNi;
   JSlider jSlider_MNi;
   JLabel jLabel_MNr;
   JSlider jSlider_MNr;
   JLabel jLabel_MKr;
   JSlider jSlider_MKr;
   JLabel jLabel_MAngi;
   JSlider jSlider_MAngi;
   JLabel jLabel_MLonda;
   JSlider jSlider_MLonda;
   JPanel jPanel_MCEC;
   BorderLayout borderLayout_MCEC;
   JPanel jPanel_MCEW;
   JPanel jPanel_MCEE;
   JPanel jPanel_MRef;
   JPanel jPanel_MTrans;
   BorderLayout borderLayout_MRef;
   BorderLayout borderLayout_MTrans;
   JPanel jPanel_MRC;
   BorderLayout borderLayout_MRC;
   JPanel jPanel_MRef_elipses;
   GridLayout gridLayout_MRef_elipses;
   JPanel jPanel_MR_elipi;
   JPanel jPanel_MR_elipr;
   JPanel jPanel_MTC;
   BorderLayout borderLayout_MTC;
   BorderLayout borderLayout_MR_elipi;
   PanelElipse elipsei_MRef;
   PanelElipse elipser_MRef;
   BorderLayout borderLayout_MR_elipr;
   JPanel jPanel_MTCW;
   JPanel jPanel_MR_elipiW;
   JLabel jLabel_MPolifas;
   JLabel jLabel_MPolia2;
   FlowLayout flowLayout_MR_elipit;
   JLabel jLabel_MPolia1;
   JPanel jPanel_MR_elipit;
   JTextPane jTextPane_MPoliMssg;
   JLabel jLabel_MPoli;
   BorderLayout borderLayout_MR_elipiW;
   JPanel jPanel_MR_elipiWW;
   JPanel jPanel_MR_eliprW;
   JLabel jLabel_MPolrfas;
   JLabel jLabel_MPolrangs;
   JLabel jLabel_MPolr;
   JPanel jPanel_MR_eliprt;
   JLabel jLabel_MPolrangp;
   JLabel jLabel_MPolrp;
   JLabel jLabel_MPolrs;
   BorderLayout borderLayout_eliprW;
   JPanel jPanel_eliprWW;
   FlowLayout flowLayout_MR_eliprt;
   BorderLayout borderLayout_MTCW;
   JLabel jLabel_MTpiel;
   JPanel jPanel_MTt;
   JPanel jPanel_MTCWW;
   JTextPane jTextPane_MTrans;
   FlowLayout flowLayout_MTt;
   JLabel jLabel_MTT;
   JPanel jPanel_DN;
   JScrollPane jScrollPane_Doc;
   JTextPane jTextPane_Doc;

   public String getParameter(String key, String def) {
      return this.isStandalone ? System.getProperty(key, def) : (this.getParameter(key) != null ? this.getParameter(key) : def);
   }

   public PolarApplet() {
      this.jSplitPane = new JSplitPane(1, this.jPanelL_FPolar, this.jPanelR_FGrafico);
      this.grafFresnel = new PanelGrafFresnel();
      this.elipsei = new PanelElipse();
      this.elipser = new PanelElipse();
      this.elipset = new PanelElipse();
      this.grafMetalT = new PanelGrafMetalT();
      this.jPanel_FCENC = new JPanel();
      this.jLabel_Fts = new JLabel();
      this.gridLayout_FCoefic = new GridLayout();
      this.jLabel_Ftp = new JLabel();
      this.jPanel_FCoefic = new JPanel();
      this.jLabel_Frs = new JLabel();
      this.jLabel_Frp = new JLabel();
      this.borderLayout_FCENC = new BorderLayout();
      this.borderLayoutR_FGrafico = new BorderLayout();
      this.jPanel_RFGS = new JPanel();
      this.jPanel_RFGE = new JPanel();
      this.jPanel_RFGW = new JPanel();
      this.jPanel_RFGN = new JPanel();
      this.jLabel_TitCoefFresnel = new JLabel();
      this.borderLayout_RFGN = new BorderLayout();
      this.jPanel_RFGNN = new JPanel();
      this.jPanel_FBoton = new JPanel();
      this.jButton_FresPol = new JButton();
      this.jPanel_elipse = new JPanel();
      this.elipse = new PanelElipse();
      this.borderLayout_jPanelelipse = new BorderLayout();
      this.jLabel_elipAng = new JLabel();
      this.jLabel_stokes4 = new JLabel();
      this.jLabel_stokes3 = new JLabel();
      this.gridLayout_elipsecar = new GridLayout();
      this.jLabel_stokes2 = new JLabel();
      this.jLabel_stokes1 = new JLabel();
      this.jPanel_elipsecar = new JPanel();
      this.jLabel_stokest = new JLabel();
      this.jLabel_elipB = new JLabel();
      this.jLabel_elipA = new JLabel();
      this.jPanel_PCCS = new JPanel();
      this.jPanel_PCCW = new JPanel();
      this.jPanel_PCCE = new JPanel();
      this.jPanel_PCCN = new JPanel();
      this.borderLayout_elipse = new BorderLayout();
      this.borderLayout_elipse1 = new BorderLayout();
      this.borderLayout_elipse2 = new BorderLayout();
      this.gridLayout_FPolar = new GridLayout();
      this.jPanel_FP11 = new JPanel();
      this.jPanel_FP12 = new JPanel();
      this.jPanel_FP21 = new JPanel();
      this.jPanel_FP22 = new JPanel();
      this.borderLayout_FP1 = new BorderLayout();
      this.borderLayout_FP2 = new BorderLayout();
      this.borderLayout_FP3 = new BorderLayout();
      this.borderLayout_FP4 = new BorderLayout();
      this.borderLayout_FP11 = new BorderLayout();
      this.borderLayout_FP12 = new BorderLayout();
      this.borderLayout_FP21 = new BorderLayout();
      this.borderLayout_FP22 = new BorderLayout();
      this.jPanel_FPolini = new JPanel();
      this.jPanel_FPoliniMssg = new JPanel();
      this.jLabel_FPoli = new JLabel();
      this.jLabel_FPolia1 = new JLabel();
      this.jLabel_FPolia2 = new JLabel();
      this.jLabel_FPolifas = new JLabel();
      this.flowLayout_FPolini = new FlowLayout();
      this.jTextPane_FPoliMssg = new JTextPane();
      this.flowLayout_FPoliMssg = new FlowLayout();
      this.jLabel_RefTotal = new JLabel();
      this.borderLayout_FBoton = new BorderLayout();
      this.jPanel_MCentro = new JPanel();
      this.borderLayout_MC = new BorderLayout();
      this.jPanel_MCC = new JPanel();
      this.borderLayout_MCC = new BorderLayout();
      this.dibMetal = new PanelDibMetal();
      this.jPanel_MCEast = new JPanel();
      this.borderLayout_MCE = new BorderLayout();
      this.jPanel_MCS = new JPanel();
      this.borderLayout_MCS = new BorderLayout();
      this.jPanel_MCSC = new JPanel();
      this.jPanel_MCSN = new JPanel();
      this.jPanel_MCSS = new JPanel();
      this.jPanel_MCSW = new JPanel();
      this.jPanel_MCSE = new JPanel();
      this.jLabel_MNi = new JLabel();
      this.jSlider_MNi = new JSlider();
      this.jLabel_MNr = new JLabel();
      this.jSlider_MNr = new JSlider();
      this.jLabel_MKr = new JLabel();
      this.jSlider_MKr = new JSlider();
      this.jLabel_MAngi = new JLabel();
      this.jSlider_MAngi = new JSlider();
      this.jLabel_MLonda = new JLabel();
      this.jSlider_MLonda = new JSlider();
      this.jPanel_MCEC = new JPanel();
      this.borderLayout_MCEC = new BorderLayout();
      this.jPanel_MCEW = new JPanel();
      this.jPanel_MCEE = new JPanel();
      this.jPanel_MRef = new JPanel();
      this.jPanel_MTrans = new JPanel();
      this.borderLayout_MRef = new BorderLayout();
      this.borderLayout_MTrans = new BorderLayout();
      this.jPanel_MRC = new JPanel();
      this.borderLayout_MRC = new BorderLayout();
      this.jPanel_MRef_elipses = new JPanel();
      this.gridLayout_MRef_elipses = new GridLayout();
      this.jPanel_MR_elipi = new JPanel();
      this.jPanel_MR_elipr = new JPanel();
      this.jPanel_MTC = new JPanel();
      this.borderLayout_MTC = new BorderLayout();
      this.borderLayout_MR_elipi = new BorderLayout();
      this.elipsei_MRef = new PanelElipse();
      this.elipser_MRef = new PanelElipse();
      this.borderLayout_MR_elipr = new BorderLayout();
      this.jPanel_MTCW = new JPanel();
      this.jPanel_MR_elipiW = new JPanel();
      this.jLabel_MPolifas = new JLabel();
      this.jLabel_MPolia2 = new JLabel();
      this.flowLayout_MR_elipit = new FlowLayout();
      this.jLabel_MPolia1 = new JLabel();
      this.jPanel_MR_elipit = new JPanel();
      this.jTextPane_MPoliMssg = new JTextPane();
      this.jLabel_MPoli = new JLabel();
      this.borderLayout_MR_elipiW = new BorderLayout();
      this.jPanel_MR_elipiWW = new JPanel();
      this.jPanel_MR_eliprW = new JPanel();
      this.jLabel_MPolrfas = new JLabel();
      this.jLabel_MPolrangs = new JLabel();
      this.jLabel_MPolr = new JLabel();
      this.jPanel_MR_eliprt = new JPanel();
      this.jLabel_MPolrangp = new JLabel();
      this.jLabel_MPolrp = new JLabel();
      this.jLabel_MPolrs = new JLabel();
      this.borderLayout_eliprW = new BorderLayout();
      this.jPanel_eliprWW = new JPanel();
      this.flowLayout_MR_eliprt = new FlowLayout();
      this.borderLayout_MTCW = new BorderLayout();
      this.jLabel_MTpiel = new JLabel();
      this.jPanel_MTt = new JPanel();
      this.jPanel_MTCWW = new JPanel();
      this.jTextPane_MTrans = new JTextPane();
      this.flowLayout_MTt = new FlowLayout();
      this.jLabel_MTT = new JLabel();
      this.jPanel_DN = new JPanel();
      this.jScrollPane_Doc = new JScrollPane();
      this.jTextPane_Doc = new JTextPane();
   }

   public void init() {
      try {
         this.jbInit();
         this.actuaSliderP();
         this.actuaSliderF();
         this.actuaSliderM();
         this.carga_info();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.titledBorder1 = new TitledBorder("");
      this.setLayout(this.borderLayout);
      this.jPanel_Salida.setMaximumSize(new Dimension(10, 27));
      this.jPanel_Salida.setMinimumSize(new Dimension(10, 27));
      this.jPanel_Salida.setPreferredSize(new Dimension(10, 27));
      this.jPanel_Salida.setLayout(this.flowLayout_Salida);
      this.jButton_Salir.setPreferredSize(new Dimension(100, 20));
      this.jButton_Salir.setText(this.etiqueta[5][idioma]);
      this.jButton_Salir.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            PolarApplet.this.jButton_Salir_actionPerformed(e);
         }
      });
      Color color_default = new Color(102, 102, 153);
      Color color_rp = new Color(50, 180, 140);
      Color color_rs = new Color(0, 50, 0);
      Color color_tp = new Color(0, 100, 255);
      Color color_ts = new Color(0, 0, 150);
      Color color_RefTotal = new Color(0, 180, 0);
      Color color_angB = new Color(180, 0, 180);
      Color color_angL = new Color(180, 0, 0);
      this.flowLayout_Salida.setAlignment(2);
      this.jPanel_Principal.setLayout(this.borderLayout_Principal);
      this.jPanel_Polar.setLayout(this.borderLayout_P);
      this.JPanel_PCSouth.setBackground(Color.black);
      this.JPanel_PCSouth.setBorder(BorderFactory.createEtchedBorder());
      this.JPanel_PCSouth.setMinimumSize(new Dimension(12, 200));
      this.JPanel_PCSouth.setPreferredSize(new Dimension(10, 200));
      this.JPanel_PCSouth.setLayout(this.borderLayout_PCS);
      this.jPanel_PCentro.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_PCentro.setLayout(this.borderLayout_PC);
      this.jPanel_Principal.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_PCC.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_PCC.setLayout(this.borderLayout_PCC);
      this.jPanel_PCEast.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_PCEast.setMinimumSize(new Dimension(325, 14));
      this.jPanel_PCEast.setPreferredSize(new Dimension(250, 14));
      this.jPanel_PCEast.setLayout(this.borderLayout_PCE);
      this.jPanel_PCEC.setLayout(this.flowLayout_PCEC);
      this.jLabel_PA2.setPreferredSize(new Dimension(200, 30));
      this.jLabel_PA2.setForeground(color_default);
      this.jLabel_PA2.setText(this.P_etiqueta[1][idioma]);
      this.jLabel_PA1.setPreferredSize(new Dimension(200, 30));
      this.jLabel_PA1.setForeground(color_default);
      this.jLabel_PA1.setText(this.P_etiqueta[0][idioma]);
      this.jLabel_PFase.setPreferredSize(new Dimension(200, 30));
      this.jLabel_PFase.setForeground(color_default);
      this.jLabel_PFase.setText(this.P_etiqueta[2][idioma]);
      this.jSlider_PA2.setMajorTickSpacing(50);
      this.jSlider_PA2.setMinorTickSpacing(10);
      this.jSlider_PA2.setPaintTicks(true);
      this.jSlider_PA2.setPreferredSize(new Dimension(180, 30));
      this.jSlider_PA2.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            PolarApplet.this.jSlider_PA2_mouseDragged(e);
         }
      });
      this.jSlider_PA2.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            PolarApplet.this.jSlider_PA2_mouseClicked(e);
         }
      });
      this.jSlider_PA2.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            PolarApplet.this.jSlider_PA2_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            PolarApplet.this.jSlider_PA2_keyTyped(e);
         }
      });
      this.jSlider_PFase.setMajorTickSpacing(90);
      this.jSlider_PFase.setMaximum(360);
      this.jSlider_PFase.setMinorTickSpacing(15);
      this.jSlider_PFase.setPaintTicks(true);
      this.jSlider_PFase.setPreferredSize(new Dimension(180, 30));
      this.jSlider_PFase.addKeyListener(new KeyAdapter() {
         public void keyTyped(KeyEvent e) {
            PolarApplet.this.jSlider_PFase_keyTyped(e);
         }

         public void keyPressed(KeyEvent e) {
            PolarApplet.this.jSlider_PFase_keyPressed(e);
         }
      });
      this.jSlider_PFase.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            PolarApplet.this.jSlider_PFase_mouseDragged(e);
         }
      });
      this.jSlider_PFase.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            PolarApplet.this.jSlider_PFase_mouseClicked(e);
         }
      });
      this.jSlider_PA1.setMajorTickSpacing(50);
      this.jSlider_PA1.setMinorTickSpacing(10);
      this.jSlider_PA1.setPaintTicks(true);
      this.jSlider_PA1.setPreferredSize(new Dimension(180, 30));
      this.jSlider_PA1.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            PolarApplet.this.jSlider_PA1_mouseDragged(e);
         }
      });
      this.jSlider_PA1.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            PolarApplet.this.jSlider_PA1_mouseClicked(e);
         }
      });
      this.jSlider_PA1.addKeyListener(new KeyAdapter() {
         public void keyTyped(KeyEvent e) {
            PolarApplet.this.jSlider_PA1_keyTyped(e);
         }

         public void keyPressed(KeyEvent e) {
            PolarApplet.this.jSlider_PA1_keyPressed(e);
         }
      });
      this.jSlider_PA1.setValue(50);
      this.jSlider_PA2.setValue(50);
      this.jSlider_PFase.setValue(90);
      this.ondas.setBackground(Color.black);
      this.jButton_Acerca.setPreferredSize(new Dimension(100, 20));
      this.jButton_Acerca.setActionCommand("");
      this.jButton_Acerca.setText(this.acerca_etiqueta[0][idioma]);
      this.jButton_Acerca.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            PolarApplet.this.jButton_Acerca_actionPerformed(e);
         }
      });
      this.jPanel_Fresnel.setLayout(this.borderLayout_F);
      this.jPanel_FCentro.setLayout(this.borderLayout_FC);
      this.jPanel_Doc.setLayout(this.borderLayout_D);
      this.jPanel_Metal.setLayout(this.borderLayout_M);
      this.jPanel_FCEast.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_FCEast.setPreferredSize(new Dimension(430, 300));
      this.jPanel_FCEast.setLayout(this.borderLayout_FCE);
      this.jPanel_FCS.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_FCS.setMinimumSize(new Dimension(310, 61));
      this.jPanel_FCS.setPreferredSize(new Dimension(10, 270));
      this.jPanel_FCS.setLayout(this.borderLayout_FCS);
      this.jPanel_FCC.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_FCC.setLayout(this.borderLayout_FCC);
      this.jPanel_FCSC.setLayout(this.flowLayout_FCSC);
      this.jLabel_FNi.setForeground(color_default);
      this.jLabel_FNi.setText(this.F_etiqueta[0][idioma]);
      this.jLabel_FNi.setPreferredSize(new Dimension(240, 17));
      this.jSlider_FNi.setMajorTickSpacing(50);
      this.jSlider_FNi.setMaximum(250);
      this.jSlider_FNi.setMinimum(100);
      this.jSlider_FNi.setMinorTickSpacing(10);
      this.jSlider_FNi.setPaintTicks(true);
      this.jSlider_FNi.setPreferredSize(new Dimension(220, 27));
      this.jSlider_FNi.setValue(100);
      this.jLabel_FNr.setForeground(color_default);
      this.jLabel_FNr.setText(this.F_etiqueta[1][idioma]);
      this.jLabel_FNr.setPreferredSize(new Dimension(240, 17));
      this.jSlider_FNr.setMajorTickSpacing(50);
      this.jSlider_FNr.setMaximum(250);
      this.jSlider_FNr.setMinimum(100);
      this.jSlider_FNr.setValue(175);
      this.jSlider_FNr.setMinorTickSpacing(10);
      this.jSlider_FNr.setPaintTicks(true);
      this.jSlider_FNr.setPreferredSize(new Dimension(220, 27));
      this.jLabel_FAngi.setForeground(color_default);
      this.jLabel_FAngi.setText(this.F_etiqueta[2][idioma]);
      this.jLabel_FAngi.setPreferredSize(new Dimension(240, 17));
      this.jSlider_FAngi.setMajorTickSpacing(300);
      this.jSlider_FAngi.setMaximum(900);
      this.jSlider_FAngi.setMinorTickSpacing(50);
      this.jSlider_FAngi.setPaintTicks(true);
      this.jSlider_FAngi.setPreferredSize(new Dimension(220, 27));
      this.jSlider_FAngi.setValue(450);
      this.jSlider_FNi.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            PolarApplet.this.jSlider_FNi_mouseClicked(e);
         }
      });
      this.jSlider_FNi.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            PolarApplet.this.jSlider_FNi_mouseDragged(e);
         }
      });
      this.jSlider_FNi.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            PolarApplet.this.jSlider_FNi_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            PolarApplet.this.jSlider_FNi_keyTyped(e);
         }
      });
      this.jSlider_FNr.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            PolarApplet.this.jSlider_FNr_mouseDragged(e);
         }
      });
      this.jSlider_FNr.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            PolarApplet.this.jSlider_FNr_mouseClicked(e);
         }
      });
      this.jSlider_FNr.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            PolarApplet.this.jSlider_FNr_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            PolarApplet.this.jSlider_FNr_keyTyped(e);
         }
      });
      this.jSlider_FAngi.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            PolarApplet.this.jSlider_FAngi_mouseDragged(e);
         }
      });
      this.jSlider_FAngi.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            PolarApplet.this.jSlider_FAngi_mouseClicked(e);
         }
      });
      this.jSlider_FAngi.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            PolarApplet.this.jSlider_FAngi_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            PolarApplet.this.jSlider_FAngi_keyTyped(e);
         }
      });
      this.jSlider_MNi.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            PolarApplet.this.jSlider_MNi_mouseClicked(e);
         }
      });
      this.jSlider_MNi.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            PolarApplet.this.jSlider_MNi_mouseDragged(e);
         }
      });
      this.jSlider_MNi.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            PolarApplet.this.jSlider_MNi_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            PolarApplet.this.jSlider_MNi_keyTyped(e);
         }
      });
      this.jSlider_MNr.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            PolarApplet.this.jSlider_MNr_mouseClicked(e);
         }
      });
      this.jSlider_MNr.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            PolarApplet.this.jSlider_MNr_mouseDragged(e);
         }
      });
      this.jSlider_MNr.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            PolarApplet.this.jSlider_MNr_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            PolarApplet.this.jSlider_MNr_keyTyped(e);
         }
      });
      this.jSlider_MKr.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            PolarApplet.this.jSlider_MKr_mouseDragged(e);
         }
      });
      this.jSlider_MKr.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            PolarApplet.this.jSlider_MKr_mouseClicked(e);
         }
      });
      this.jSlider_MKr.addKeyListener(new KeyAdapter() {
         public void keyTyped(KeyEvent e) {
            PolarApplet.this.jSlider_MKr_keyTyped(e);
         }

         public void keyPressed(KeyEvent e) {
            PolarApplet.this.jSlider_MKr_keyPressed(e);
         }
      });
      this.jSlider_MAngi.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            PolarApplet.this.jSlider_MAngi_mouseClicked(e);
         }
      });
      this.jSlider_MAngi.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            PolarApplet.this.jSlider_MAngi_mouseDragged(e);
         }
      });
      this.jSlider_MAngi.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            PolarApplet.this.jSlider_MAngi_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            PolarApplet.this.jSlider_MAngi_keyTyped(e);
         }
      });
      this.jSlider_MLonda.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            PolarApplet.this.jSlider_MLonda_mouseClicked(e);
         }
      });
      this.jSlider_MLonda.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            PolarApplet.this.jSlider_MLonda_mouseDragged(e);
         }
      });
      this.jSlider_MLonda.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            PolarApplet.this.jSlider_MLonda_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            PolarApplet.this.jSlider_MLonda_keyTyped(e);
         }
      });
      this.dibFresnel.setBackground(Color.black);
      this.jPanel_FCEN.setLayout(this.borderLayout_FCEN);
      this.jPanel_FCENW.setPreferredSize(new Dimension(20, 10));
      this.jPanel_FCSS.setPreferredSize(new Dimension(100, 80));
      this.jLabel_FAngr.setPreferredSize(new Dimension(240, 17));
      this.jLabel_FAngr.setForeground(color_default);
      this.jLabel_FAngr.setText(this.F_etiqueta[3][idioma]);
      this.jLabel_FAngB.setPreferredSize(new Dimension(240, 17));
      this.jLabel_FAngB.setText(this.F_etiqueta[4][idioma]);
      this.jLabel_FAngB.setForeground(color_angB);
      this.jLabel_FAngL.setForeground(color_angL);
      this.jLabel_FAngL.setEnabled(false);
      this.jLabel_FAngL.setPreferredSize(new Dimension(240, 17));
      this.jLabel_FAngL.setText(this.F_etiqueta[5][idioma]);
      this.jPanel_FCEC.setLayout(this.borderLayout_FCEC);
      this.jPanelL_FPolar.setLayout(this.gridLayout_FPolar);
      this.jLabel_Fts.setText("ts");
      this.gridLayout_FCoefic.setColumns(3);
      this.gridLayout_FCoefic.setRows(2);
      this.jLabel_Ftp.setText("tp");
      this.jPanel_FCoefic.setPreferredSize(new Dimension(100, 80));
      this.jPanel_FCoefic.setLayout(this.gridLayout_FCoefic);
      this.jLabel_Frs.setText("rs");
      this.jLabel_Frp.setText("rp");
      this.jLabel_Frp.setForeground(color_rp);
      this.jLabel_Frp.setPreferredSize(new Dimension(12, 17));
      this.jLabel_Frs.setForeground(color_rs);
      this.jLabel_Ftp.setForeground(color_tp);
      this.jLabel_Fts.setForeground(color_ts);
      this.jPanel_FCENC.setLayout(this.borderLayout_FCENC);
      this.jPanelR_FGrafico.setLayout(this.borderLayoutR_FGrafico);
      this.grafFresnel.setBorder(BorderFactory.createEtchedBorder());
      this.jButton_FresPol.setMaximumSize(new Dimension(120, 20));
      this.jButton_FresPol.setMinimumSize(new Dimension(65, 1));
      this.jButton_FresPol.setPreferredSize(new Dimension(180, 20));
      this.jButton_FresPol.setText(this.F_etiqueta[8][idioma]);
      this.jButton_FresPol.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            PolarApplet.this.jButton_FresPol_actionPerformed(e);
         }
      });
      this.jPanel_FBoton.setPreferredSize(new Dimension(200, 30));
      this.jPanel_FBoton.setLayout(this.borderLayout_FBoton);
      this.elipse.setPreferredSize(new Dimension(300, 10));
      this.elipse.setToolTipText("");
      this.elipse.setLayout(this.borderLayout_elipse);
      this.jPanel_elipse.setLayout(this.borderLayout_jPanelelipse);
      this.jLabel_elipAng.setForeground(color_default);
      this.jLabel_elipAng.setText(this.elipse_etiqueta[2][idioma]);
      this.jLabel_stokes4.setFont(new Font("Dialog", 1, 11));
      this.jLabel_stokes4.setForeground(color_default);
      this.jLabel_stokes4.setText("S");
      this.jLabel_stokes3.setFont(new Font("Dialog", 1, 11));
      this.jLabel_stokes3.setForeground(color_default);
      this.jLabel_stokes3.setText("C");
      this.gridLayout_elipsecar.setColumns(1);
      this.gridLayout_elipsecar.setRows(8);
      this.jLabel_stokes2.setFont(new Font("Dialog", 1, 11));
      this.jLabel_stokes2.setForeground(color_default);
      this.jLabel_stokes2.setText("M");
      this.jLabel_stokes1.setFont(new Font("Dialog", 1, 11));
      this.jLabel_stokes1.setForeground(color_default);
      this.jLabel_stokes1.setText("I");
      this.jPanel_elipsecar.setLayout(this.gridLayout_elipsecar);
      this.jPanel_elipsecar.setPreferredSize(new Dimension(100, 200));
      this.jLabel_stokest.setForeground(color_default);
      this.jLabel_stokest.setText(this.elipse_etiqueta[3][idioma] + ":");
      this.jLabel_elipB.setForeground(color_default);
      this.jLabel_elipB.setText(this.elipse_etiqueta[1][idioma]);
      this.jLabel_elipA.setPreferredSize(new Dimension(41, 5));
      this.jLabel_elipA.setForeground(color_default);
      this.jLabel_elipA.setText(this.elipse_etiqueta[0][idioma]);
      this.jPanel_PCCS.setPreferredSize(new Dimension(2, 2));
      this.jPanel_PCCW.setPreferredSize(new Dimension(2, 2));
      this.jPanel_PCCE.setPreferredSize(new Dimension(2, 2));
      this.jPanel_PCCN.setPreferredSize(new Dimension(2, 2));
      this.jPanel_FP11.setLayout(this.borderLayout_FP11);
      this.jPanel_FP11.setBorder(BorderFactory.createRaisedBevelBorder());
      this.jPanel_FP12.setLayout(this.borderLayout_FP12);
      this.jPanel_FP12.setBorder(BorderFactory.createRaisedBevelBorder());
      this.jPanel_FP21.setLayout(this.borderLayout_FP21);
      this.jPanel_FP22.setLayout(this.borderLayout_FP22);
      this.jPanel_FP22.setBorder(BorderFactory.createRaisedBevelBorder());
      this.gridLayout_FPolar.setColumns(2);
      this.gridLayout_FPolar.setRows(2);
      this.jLabel_FPoli.setPreferredSize(new Dimension(150, 25));
      this.jLabel_FPoli.setHorizontalAlignment(2);
      this.jLabel_FPoli.setHorizontalTextPosition(2);
      this.jLabel_FPoli.setForeground(color_default);
      this.jLabel_FPoli.setText(this.P_etiqueta[3][idioma] + ":");
      this.jLabel_FPolia1.setFont(new Font("Dialog", 0, 12));
      this.jLabel_FPolia1.setPreferredSize(new Dimension(150, 17));
      this.jLabel_FPolia1.setForeground(color_default);
      this.jLabel_FPolia1.setText("  " + this.P_etiqueta[0][idioma]);
      this.jLabel_FPolia2.setFont(new Font("Dialog", 0, 12));
      this.jLabel_FPolia2.setPreferredSize(new Dimension(150, 17));
      this.jLabel_FPolia2.setForeground(color_default);
      this.jLabel_FPolia2.setText("  " + this.P_etiqueta[1][idioma]);
      this.jLabel_FPolifas.setFont(new Font("Dialog", 0, 12));
      this.jLabel_FPolifas.setPreferredSize(new Dimension(150, 17));
      this.jLabel_FPolifas.setForeground(color_default);
      this.jLabel_FPolifas.setText("  " + this.P_etiqueta[2][idioma]);
      this.jPanel_FPolini.setLayout(this.flowLayout_FPolini);
      this.jPanel_FPolini.setPreferredSize(new Dimension(625, 100));
      this.jPanel_FPolini.setPreferredSize(new Dimension(625, 120));
      this.jTextPane_FPoliMssg.setBackground(new Color(204, 204, 204));
      this.jTextPane_FPoliMssg.setFont(new Font("Dialog", 1, 11));
      this.jTextPane_FPoliMssg.setForeground(Color.darkGray);
      this.jTextPane_FPoliMssg.setPreferredSize(new Dimension(200, 100));
      this.jTextPane_FPoliMssg.setDisabledTextColor(Color.darkGray);
      this.jTextPane_FPoliMssg.setEditable(false);
      this.jTextPane_FPoliMssg.setText(this.PolIni_etiqueta[idioma]);
      this.jPanel_FPoliniMssg.setLayout(this.flowLayout_FPoliMssg);
      this.jPanel_FPoliniMssg.setPreferredSize(new Dimension(17, 70));
      this.jPanel_FPoliniMssg.setPreferredSize(new Dimension(250, 70));
      this.jLabel_RefTotal.setPreferredSize(new Dimension(180, 40));
      this.jLabel_RefTotal.setForeground(color_RefTotal);
      this.jPanel_MCentro.setLayout(this.borderLayout_MC);
      this.jPanel_MCC.setLayout(this.borderLayout_MCC);
      this.jPanel_MCC.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_MCEast.setLayout(this.borderLayout_MCE);
      this.jPanel_MCEast.setPreferredSize(new Dimension(450, 300));
      this.jPanel_MCS.setLayout(this.borderLayout_MCS);
      this.jPanel_MCS.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_MCS.setPreferredSize(new Dimension(10, 300));
      this.jLabel_MNi.setForeground(color_default);
      this.jLabel_MNi.setText(this.F_etiqueta[0][idioma]);
      this.jLabel_MNi.setPreferredSize(new Dimension(240, 17));
      this.jSlider_MNi.setMajorTickSpacing(50);
      this.jSlider_MNi.setMinimum(100);
      this.jSlider_MNi.setMaximum(250);
      this.jSlider_MNi.setMinorTickSpacing(10);
      this.jSlider_MNi.setPaintTicks(true);
      this.jSlider_MNi.setPreferredSize(new Dimension(220, 27));
      this.jLabel_MNr.setForeground(color_default);
      this.jLabel_MNr.setText(this.M_etiqueta[0][idioma]);
      this.jLabel_MNr.setPreferredSize(new Dimension(240, 17));
      this.jSlider_MNr.setMajorTickSpacing(100);
      this.jSlider_MNr.setMaximum(350);
      this.jSlider_MNr.setMinorTickSpacing(20);
      this.jSlider_MNr.setPaintTicks(true);
      this.jSlider_MNr.setPreferredSize(new Dimension(220, 27));
      this.jSlider_MNr.setValue(20);
      this.jLabel_MKr.setForeground(color_default);
      this.jLabel_MKr.setText(this.M_etiqueta[1][idioma]);
      this.jLabel_MKr.setPreferredSize(new Dimension(240, 17));
      this.jSlider_MKr.setMajorTickSpacing(100);
      this.jSlider_MKr.setMinorTickSpacing(50);
      this.jSlider_MKr.setPaintTicks(true);
      this.jSlider_MKr.setPreferredSize(new Dimension(220, 27));
      this.jSlider_MKr.setMinimum(0);
      this.jSlider_MKr.setMaximum(500);
      this.jSlider_MKr.setValue(344);
      this.jLabel_MAngi.setForeground(color_default);
      this.jLabel_MAngi.setText(this.F_etiqueta[2][idioma]);
      this.jLabel_MAngi.setPreferredSize(new Dimension(240, 17));
      this.jSlider_MAngi.setMajorTickSpacing(300);
      this.jSlider_MAngi.setMaximum(900);
      this.jSlider_MAngi.setMinorTickSpacing(50);
      this.jSlider_MAngi.setPaintTicks(true);
      this.jSlider_MAngi.setPreferredSize(new Dimension(220, 27));
      this.jSlider_MAngi.setValue(450);
      this.jLabel_MLonda.setForeground(color_default);
      this.jLabel_MLonda.setText(this.M_etiqueta[2][idioma]);
      this.jLabel_MLonda.setPreferredSize(new Dimension(240, 17));
      this.jSlider_MLonda.setMajorTickSpacing(100);
      this.jSlider_MLonda.setMinimum(400);
      this.jSlider_MLonda.setMaximum(700);
      this.jSlider_MLonda.setMinorTickSpacing(25);
      this.jSlider_MLonda.setPaintTicks(true);
      this.jSlider_MLonda.setPreferredSize(new Dimension(220, 27));
      this.jSlider_MLonda.setValue(589);
      this.dibMetal.setBackground(Color.black);
      this.jPanel_MCEC.setLayout(this.borderLayout_MCEC);
      this.jPanel_MRef.setLayout(this.borderLayout_MRef);
      this.jPanel_MTrans.setLayout(this.borderLayout_MTrans);
      this.jPanel_MTrans.setPreferredSize(new Dimension(100, 175));
      this.jPanel_MRC.setLayout(this.borderLayout_MRC);
      this.jPanel_MCEW.setPreferredSize(new Dimension(5, 10));
      this.jPanel_MCEE.setPreferredSize(new Dimension(5, 10));
      this.jPanel_MRef_elipses.setLayout(this.gridLayout_MRef_elipses);
      this.gridLayout_MRef_elipses.setColumns(1);
      this.gridLayout_MRef_elipses.setRows(2);
      this.jPanel_MTC.setLayout(this.borderLayout_MTC);
      this.jPanel_MTC.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_MR_elipi.setLayout(this.borderLayout_MR_elipi);
      this.jPanel_MR_elipi.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_MR_elipr.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_MR_elipr.setLayout(this.borderLayout_MR_elipr);
      this.elipsei_MRef.setPreferredSize(new Dimension(140, 140));
      this.elipser_MRef.setPreferredSize(new Dimension(140, 140));
      this.jLabel_MPolifas.setFont(new Font("Dialog", 0, 12));
      this.jLabel_MPolifas.setPreferredSize(new Dimension(150, 17));
      this.jLabel_MPolifas.setForeground(color_default);
      this.jLabel_MPolifas.setText("  " + this.P_etiqueta[2][idioma]);
      this.jLabel_MPolia2.setFont(new Font("Dialog", 0, 12));
      this.jLabel_MPolia2.setPreferredSize(new Dimension(150, 17));
      this.jLabel_MPolia2.setForeground(color_default);
      this.jLabel_MPolia2.setText("  " + this.P_etiqueta[1][idioma]);
      this.flowLayout_MR_elipit.setAlignment(0);
      this.jLabel_MPolia1.setFont(new Font("Dialog", 0, 12));
      this.jLabel_MPolia1.setPreferredSize(new Dimension(150, 17));
      this.jLabel_MPolia1.setForeground(color_default);
      this.jLabel_MPolia1.setText("  " + this.P_etiqueta[0][idioma]);
      this.jPanel_MR_elipit.setLayout(this.flowLayout_MR_elipit);
      this.jTextPane_MPoliMssg.setBackground(new Color(204, 204, 204));
      this.jTextPane_MPoliMssg.setFont(new Font("Dialog", 1, 11));
      this.jTextPane_MPoliMssg.setForeground(Color.darkGray);
      this.jTextPane_MPoliMssg.setPreferredSize(new Dimension(250, 40));
      this.jTextPane_MPoliMssg.setDisabledTextColor(Color.darkGray);
      this.jTextPane_MPoliMssg.setEditable(false);
      this.jTextPane_MPoliMssg.setText(this.PolIni_etiqueta[idioma]);
      this.jLabel_MPoli.setPreferredSize(new Dimension(200, 25));
      this.jLabel_MPoli.setHorizontalAlignment(2);
      this.jLabel_MPoli.setHorizontalTextPosition(2);
      this.jLabel_MPoli.setForeground(color_default);
      this.jLabel_MPoli.setText(this.P_etiqueta[3][idioma] + ":");
      this.jPanel_MR_elipiW.setLayout(this.borderLayout_MR_elipiW);
      this.jPanel_MR_elipiW.setPreferredSize(new Dimension(270, 50));
      this.jLabel_MPolrfas.setFont(new Font("Dialog", 0, 12));
      this.jLabel_MPolrfas.setPreferredSize(new Dimension(200, 40));
      this.jLabel_MPolrfas.setForeground(color_default);
      this.jLabel_MPolrfas.setText("  " + this.F_etiqueta[11][idioma]);
      this.jLabel_MPolrangs.setFont(new Font("Dialog", 0, 12));
      this.jLabel_MPolrangs.setPreferredSize(new Dimension(90, 25));
      this.jLabel_MPolrangs.setForeground(color_default);
      this.jLabel_MPolrangs.setText("  " + this.F_etiqueta[10][idioma]);
      this.jLabel_MPolr.setPreferredSize(new Dimension(200, 25));
      this.jLabel_MPolr.setHorizontalAlignment(2);
      this.jLabel_MPolr.setHorizontalTextPosition(2);
      this.jLabel_MPolr.setForeground(color_default);
      this.jLabel_MPolr.setText(this.M_etiqueta[3][idioma] + ":");
      this.jPanel_MR_eliprt.setPreferredSize(new Dimension(200, 60));
      this.jPanel_MR_eliprt.setLayout(this.flowLayout_MR_eliprt);
      this.jLabel_MPolrp.setFont(new Font("Dialog", 0, 12));
      this.jLabel_MPolrp.setPreferredSize(new Dimension(80, 25));
      this.jLabel_MPolrp.setForeground(color_default);
      this.jLabel_MPolrp.setText("  rp");
      this.jLabel_MPolrs.setFont(new Font("Dialog", 0, 12));
      this.jLabel_MPolrs.setPreferredSize(new Dimension(80, 25));
      this.jLabel_MPolrs.setForeground(color_default);
      this.jLabel_MPolrs.setText("  rs");
      this.jLabel_MPolrangp.setFont(new Font("Dialog", 0, 12));
      this.jLabel_MPolrangp.setPreferredSize(new Dimension(90, 25));
      this.jLabel_MPolrangp.setForeground(color_default);
      this.jLabel_MPolrangp.setText("  " + this.F_etiqueta[9][idioma]);
      this.jPanel_MR_eliprW.setLayout(this.borderLayout_eliprW);
      this.jPanel_MR_eliprW.setPreferredSize(new Dimension(270, 50));
      this.flowLayout_MR_eliprt.setAlignment(0);
      this.jPanel_MTCW.setLayout(this.borderLayout_MTCW);
      this.jPanel_MTCW.setPreferredSize(new Dimension(200, 100));
      this.jLabel_MTpiel.setFont(new Font("Dialog", 0, 12));
      this.jLabel_MTpiel.setPreferredSize(new Dimension(170, 25));
      this.jLabel_MTpiel.setForeground(color_default);
      this.jLabel_MTpiel.setText(" " + this.M_etiqueta[6][idioma]);
      this.jTextPane_MTrans.setBackground(new Color(204, 204, 204));
      this.jTextPane_MTrans.setFont(new Font("Dialog", 1, 12));
      this.jTextPane_MTrans.setForeground(new Color(102, 102, 153));
      this.jTextPane_MTrans.setPreferredSize(new Dimension(200, 40));
      this.jTextPane_MTrans.setText(this.M_etiqueta[4][idioma] + "\n" + this.M_etiqueta[5][idioma] + ":");
      this.jPanel_MTt.setLayout(this.flowLayout_MTt);
      this.flowLayout_MTt.setAlignment(0);
      this.jLabel_MTT.setFont(new Font("Dialog", 0, 12));
      this.jLabel_MTT.setPreferredSize(new Dimension(150, 25));
      this.jLabel_MTT.setForeground(color_default);
      this.jLabel_MTT.setText(" T");
      this.jTextPane_Doc.setBackground(new Color(204, 204, 204));
      this.jTextPane_Doc.setEditable(false);
      this.jTextPane_Doc.setMargin(new Insets(10, 10, 10, 10));
      this.jPanel_FPolini.add(this.jLabel_FPoli, (Object)null);
      this.jPanel_FPolini.add(this.jLabel_FPolia1, (Object)null);
      this.jPanel_FPolini.add(this.jLabel_FPolia2, (Object)null);
      this.jPanel_FPolini.add(this.jLabel_FPolifas, (Object)null);
      this.add(this.jPanel_Principal, "Center");
      this.jPanel_Principal.add(this.jTabbedPane, "Center");
      this.jTabbedPane.add(this.jPanel_Polar, this.etiqueta[1][idioma]);
      this.jPanel_Polar.add(this.jPanel_PCentro, "Center");
      this.jPanel_PCentro.add(this.jPanel_PCC, "Center");
      this.jPanel_Polar.add(this.JPanel_PCSouth, "South");
      this.JPanel_PCSouth.add(this.ondas, "Center");
      this.add(this.jPanel_Salida, "South");
      this.jPanel_Salida.add(this.jButton_Acerca, (Object)null);
      this.jPanel_Salida.add(this.jButton_Salir, (Object)null);
      this.jPanel_PCentro.add(this.jPanel_PCEast, "East");
      this.jPanel_PCEast.add(this.jPanel_PCEC, "Center");
      this.jPanel_PCEC.add(this.jLabel_PA1, (Object)null);
      this.jPanel_PCEC.add(this.jSlider_PA1, (Object)null);
      this.jPanel_PCEC.add(this.jLabel_PA2, (Object)null);
      this.jPanel_PCEC.add(this.jSlider_PA2, (Object)null);
      this.jPanel_PCEC.add(this.jLabel_PFase, (Object)null);
      this.jPanel_PCEC.add(this.jSlider_PFase, (Object)null);
      this.jPanel_PCEast.add(this.jPanel_PCEN, "North");
      this.jPanel_PCEast.add(this.jPanel_PCES, "South");
      this.jPanel_PCEast.add(this.jPanel_PCEW, "West");
      this.jPanel_PCEast.add(this.jPanel_PCEE, "East");
      this.jTabbedPane.add(this.jPanel_Fresnel, this.etiqueta[2][idioma]);
      this.jPanel_Fresnel.add(this.jPanel_FCentro, "Center");
      this.jPanel_FCentro.add(this.jPanel_FCC, "Center");
      this.jPanel_FCC.add(this.dibFresnel, "Center");
      this.jPanel_Fresnel.add(this.jPanel_FCEast, "East");
      this.jTabbedPane.add(this.jPanel_Metal, this.etiqueta[3][idioma]);
      this.jTabbedPane.add(this.jPanel_Doc, this.etiqueta[4][idioma]);
      this.jPanel_Doc.add(this.jScrollPane_Doc, "Center");
      this.jPanel_FCentro.add(this.jPanel_FCS, "South");
      this.jPanel_FCS.add(this.jPanel_FCSC, "Center");
      this.jPanel_FCS.add(this.jPanel_FCSN, "North");
      this.jPanel_FCS.add(this.jPanel_FCSS, "South");
      this.jPanel_FCSS.add(this.jLabel_FAngr, (Object)null);
      this.jPanel_FCSS.add(this.jLabel_FAngB, (Object)null);
      this.jPanel_FCSS.add(this.jLabel_FAngL, (Object)null);
      this.jPanel_FCS.add(this.jPanel_FCSE, "East");
      this.jPanel_FCS.add(this.jPanel_FCSW, "West");
      this.jPanel_FCSC.add(this.jLabel_FNi, (Object)null);
      this.jPanel_FCSC.add(this.jSlider_FNi, (Object)null);
      this.jPanel_FCSC.add(this.jLabel_FNr, (Object)null);
      this.jPanel_FCSC.add(this.jSlider_FNr, (Object)null);
      this.jPanel_FCSC.add(this.jLabel_FAngi, (Object)null);
      this.jPanel_FCSC.add(this.jSlider_FAngi, (Object)null);
      this.jPanel_FCEast.add(this.jPanel_FCEN, "North");
      this.jPanel_FCEast.add(this.jPanel_FCEC, "Center");
      this.jPanel_FCEN.add(this.jPanel_FCENN, "North");
      this.jPanel_FCEN.add(this.jPanel_FCENS, "South");
      this.jPanel_FCEN.add(this.jPanel_FCENE, "East");
      this.jPanel_FCEN.add(this.jPanel_FCENW, "West");
      this.jPanel_FCEC.add(this.jSplitPane, "Center");
      this.jSplitPane.add(this.jPanelL_FPolar, "left");
      this.jSplitPane.add(this.jPanelR_FGrafico, "right");
      this.jSplitPane.setContinuousLayout(true);
      this.jSplitPane.setDividerSize(0);
      this.jSplitPane.setOneTouchExpandable(false);
      this.jSplitPane.setDividerLocation(0);
      this.jPanel_FCEN.add(this.jPanel_FCENC, "Center");
      this.jPanel_FCoefic.add(this.jLabel_Frp, (Object)null);
      this.jPanel_FCoefic.add(this.jLabel_Ftp, (Object)null);
      this.jPanel_FCoefic.add(this.jLabel_Frs, (Object)null);
      this.jPanel_FCoefic.add(this.jLabel_Fts, (Object)null);
      this.jPanel_FCENC.add(this.jPanel_FCoefic, "Center");
      this.jPanel_FCENC.add(this.jPanel_FBoton, "East");
      this.jPanelR_FGrafico.add(this.jPanel_RFGS, "South");
      this.jPanelR_FGrafico.add(this.jPanel_RFGE, "East");
      this.jPanelR_FGrafico.add(this.jPanel_RFGW, "West");
      this.jPanelR_FGrafico.add(this.jLabel_TitCoefFresnel, "North");
      this.jPanelR_FGrafico.add(this.grafFresnel, "Center");
      this.jLabel_TitCoefFresnel.setPreferredSize(new Dimension(10, 25));
      this.jLabel_TitCoefFresnel.setHorizontalAlignment(0);
      this.jLabel_TitCoefFresnel.setHorizontalTextPosition(0);
      this.jLabel_TitCoefFresnel.setFont(new Font("Dialog", 1, 12));
      this.jLabel_TitCoefFresnel.setForeground(Color.darkGray);
      this.jLabel_TitCoefFresnel.setText(this.F_etiqueta[7][idioma]);
      this.jLabel_TitCoefFresnel.setVerticalAlignment(3);
      this.jLabel_TitCoefFresnel.setVerticalTextPosition(3);
      this.jPanel_RFGN.setLayout(this.borderLayout_RFGN);
      this.jPanel_FBoton.add(this.jLabel_RefTotal, "Center");
      this.jPanel_FBoton.add(this.jButton_FresPol, "South");
      this.jPanel_PCC.add(this.jPanel_elipse, "Center");
      this.jPanel_elipse.add(this.elipse, "West");
      this.jPanel_elipsecar.add(this.jLabel_elipA, (Object)null);
      this.jPanel_elipsecar.add(this.jLabel_elipB, (Object)null);
      this.jPanel_elipsecar.add(this.jLabel_elipAng, (Object)null);
      this.jPanel_elipsecar.add(this.jLabel_stokest, (Object)null);
      this.jPanel_elipsecar.add(this.jLabel_stokes1, (Object)null);
      this.jPanel_elipsecar.add(this.jLabel_stokes2, (Object)null);
      this.jPanel_elipsecar.add(this.jLabel_stokes3, (Object)null);
      this.jPanel_elipsecar.add(this.jLabel_stokes4, (Object)null);
      this.jPanel_PCC.add(this.jPanel_PCCS, "South");
      this.jPanel_PCC.add(this.jPanel_PCCW, "West");
      this.jPanel_PCC.add(this.jPanel_PCCE, "East");
      this.jPanel_PCC.add(this.jPanel_PCCN, "North");
      this.jPanel_elipse.add(this.jPanel_elipsecar, "Center");
      this.jPanelL_FPolar.add(this.jPanel_FP11, (Object)null);
      this.jPanelL_FPolar.add(this.jPanel_FP12, (Object)null);
      this.jPanelL_FPolar.add(this.jPanel_FP21, (Object)null);
      this.jPanel_FP21.add(this.jPanel_FPolini, "North");
      this.jPanel_FP21.add(this.jPanel_FPoliniMssg, "Center");
      this.jPanelL_FPolar.add(this.jPanel_FP22, (Object)null);
      this.jPanel_FP11.add(this.elipsei, "Center");
      this.jPanel_FP12.add(this.elipser, "Center");
      this.jPanel_FP22.add(this.elipset, "Center");
      this.jPanel_FPoliniMssg.add(this.jTextPane_FPoliMssg, (Object)null);
      this.jPanel_Metal.add(this.jPanel_MCentro, "Center");
      this.jPanel_MCentro.add(this.jPanel_MCC, "Center");
      this.jPanel_MCC.add(this.dibMetal, "Center");
      this.jPanel_MCentro.add(this.jPanel_MCS, "South");
      this.jPanel_MCS.add(this.jPanel_MCSC, "Center");
      this.jPanel_Metal.add(this.jPanel_MCEast, "East");
      this.jPanel_MCEast.add(this.jPanel_MCEC, "Center");
      this.jPanel_MCEC.add(this.jPanel_MRef, "Center");
      this.jPanel_MCEC.add(this.jPanel_MTrans, "South");
      this.jPanel_MTrans.add(this.jPanel_MTC, "Center");
      this.jPanel_MTC.add(this.jPanel_MTCW, "West");
      this.jPanel_MCS.add(this.jPanel_MCSN, "North");
      this.jPanel_MCS.add(this.jPanel_MCSS, "South");
      this.jPanel_MCS.add(this.jPanel_MCSW, "West");
      this.jPanel_MCS.add(this.jPanel_MCSE, "East");
      this.jPanel_MCSC.add(this.jLabel_MNi, (Object)null);
      this.jPanel_MCSC.add(this.jSlider_MNi, (Object)null);
      this.jPanel_MCSC.add(this.jLabel_MNr, (Object)null);
      this.jPanel_MCSC.add(this.jSlider_MNr, (Object)null);
      this.jPanel_MCSC.add(this.jLabel_MKr, (Object)null);
      this.jPanel_MCSC.add(this.jSlider_MKr, (Object)null);
      this.jPanel_MCSC.add(this.jLabel_MAngi, (Object)null);
      this.jPanel_MCSC.add(this.jSlider_MAngi, (Object)null);
      this.jPanel_MCSC.add(this.jLabel_MLonda, (Object)null);
      this.jPanel_MCSC.add(this.jSlider_MLonda, (Object)null);
      this.jPanel_MCEast.add(this.jPanel_MCEW, "West");
      this.jPanel_MCEast.add(this.jPanel_MCEE, "East");
      this.jPanel_MRef.add(this.jPanel_MRC, "Center");
      this.jPanel_MRC.add(this.jPanel_MRef_elipses, "Center");
      this.jPanel_MRef_elipses.add(this.jPanel_MR_elipi, (Object)null);
      this.jPanel_MRef_elipses.add(this.jPanel_MR_elipr, (Object)null);
      this.jPanel_MR_elipi.add(this.elipsei_MRef, "Center");
      this.jPanel_MR_elipi.add(this.jPanel_MR_elipiW, "West");
      this.jPanel_MR_elipr.add(this.elipser_MRef, "Center");
      this.jPanel_MR_elipr.add(this.jPanel_MR_eliprW, "West");
      this.jPanel_MR_elipit.add(this.jLabel_MPoli, (Object)null);
      this.jPanel_MR_elipit.add(this.jLabel_MPolia1, (Object)null);
      this.jPanel_MR_elipit.add(this.jLabel_MPolia2, (Object)null);
      this.jPanel_MR_elipit.add(this.jLabel_MPolifas, (Object)null);
      this.jPanel_MR_elipit.add(this.jTextPane_MPoliMssg, (Object)null);
      this.jPanel_MR_elipiW.add(this.jPanel_MR_elipit, "Center");
      this.jPanel_MR_elipiW.add(this.jPanel_MR_elipiWW, "West");
      this.jPanel_MR_eliprt.add(this.jLabel_MPolr, (Object)null);
      this.jPanel_MR_eliprt.add(this.jLabel_MPolrp, (Object)null);
      this.jPanel_MR_eliprt.add(this.jLabel_MPolrangp, (Object)null);
      this.jPanel_MR_eliprt.add(this.jLabel_MPolrs, (Object)null);
      this.jPanel_MR_eliprt.add(this.jLabel_MPolrangs, (Object)null);
      this.jPanel_MR_eliprt.add(this.jLabel_MPolrfas, (Object)null);
      this.jPanel_MR_eliprW.add(this.jPanel_MR_eliprt, "Center");
      this.jPanel_MR_eliprW.add(this.jPanel_eliprWW, "West");
      this.jPanel_MTCW.add(this.jPanel_MTt, "Center");
      this.jPanel_MTt.add(this.jTextPane_MTrans, (Object)null);
      this.jPanel_MTt.add(this.jLabel_MTT, (Object)null);
      this.jPanel_MTt.add(this.jLabel_MTpiel, (Object)null);
      this.jPanel_MTCW.add(this.jPanel_MTCWW, "West");
      this.jPanel_MTC.add(this.grafMetalT, "Center");
      this.jScrollPane_Doc.getViewport().add(this.jTextPane_Doc, (Object)null);
   }

   public String getAppletInfo() {
      return "Applet Information";
   }

   public String[][] getParameterInfo() {
      String[][] pinfo = new String[][]{{"idioma", "int", ""}};
      return pinfo;
   }

   public static void main(String[] args) {
      PolarApplet applet = new PolarApplet();
      applet.isStandalone = true;

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
      frame.setResizable(false);
      frame.setTitle("Polar & Fresnel coef. Applet");
      frame.add(applet, "Center");

      try {
         String st_icon = "jocon.jpg";
         URL url_icon = frame.getClass().getResource(st_icon);
         ImageIcon icon = new ImageIcon(url_icon);
         frame.setIconImage(icon.getImage());
      } catch (Exception var6) {
         System.out.println("No carga icono");
      }

      applet.init();
      applet.start();
      frame.setSize(750, 570);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
      frame.setVisible(true);
   }

   void jButton_Salir_actionPerformed(ActionEvent e) {
      System.exit(0);
   }

   void jSlider_PA1_mouseClicked(MouseEvent e) {
      this.actuaSliderP();
      this.actuaSliderF();
   }

   void jSlider_PA1_keyTyped(KeyEvent e) {
      this.actuaSliderP();
      this.actuaSliderF();
      this.actuaSliderM();
   }

   void jSlider_PA1_keyPressed(KeyEvent e) {
      this.actuaSliderP();
      this.actuaSliderF();
      this.actuaSliderM();
   }

   void jSlider_PA1_mouseDragged(MouseEvent e) {
      this.actuaSliderP();
      this.actuaSliderF();
      this.actuaSliderM();
   }

   void jSlider_PA2_mouseClicked(MouseEvent e) {
      this.actuaSliderP();
      this.actuaSliderF();
      this.actuaSliderM();
   }

   void jSlider_PA2_keyTyped(KeyEvent e) {
      this.actuaSliderP();
      this.actuaSliderF();
      this.actuaSliderM();
   }

   void jSlider_PA2_keyPressed(KeyEvent e) {
      this.actuaSliderP();
      this.actuaSliderF();
      this.actuaSliderM();
   }

   void jSlider_PA2_mouseDragged(MouseEvent e) {
      this.actuaSliderP();
      this.actuaSliderF();
      this.actuaSliderM();
   }

   void jSlider_PFase_mouseClicked(MouseEvent e) {
      this.actuaSliderP();
      this.actuaSliderF();
      this.actuaSliderM();
   }

   void jSlider_PFase_mouseDragged(MouseEvent e) {
      this.actuaSliderP();
      this.actuaSliderF();
      this.actuaSliderM();
   }

   void jSlider_PFase_keyPressed(KeyEvent e) {
      this.actuaSliderP();
      this.actuaSliderF();
      this.actuaSliderM();
   }

   void jSlider_PFase_keyTyped(KeyEvent e) {
      this.actuaSliderP();
      this.actuaSliderF();
      this.actuaSliderM();
   }

   void jSlider_FNi_keyPressed(KeyEvent e) {
      this.actuaSliderF();
   }

   void jSlider_FNi_keyTyped(KeyEvent e) {
      this.actuaSliderF();
   }

   void jSlider_FNi_mouseDragged(MouseEvent e) {
      this.actuaSliderF();
   }

   void jSlider_FNi_mouseClicked(MouseEvent e) {
      this.actuaSliderF();
   }

   void jSlider_FNr_keyPressed(KeyEvent e) {
      this.actuaSliderF();
   }

   void jSlider_FNr_keyTyped(KeyEvent e) {
      this.actuaSliderF();
   }

   void jSlider_FNr_mouseClicked(MouseEvent e) {
      this.actuaSliderF();
   }

   void jSlider_FNr_mouseDragged(MouseEvent e) {
      this.actuaSliderF();
   }

   void jSlider_FAngi_keyPressed(KeyEvent e) {
      this.actuaSliderF();
   }

   void jSlider_FAngi_keyTyped(KeyEvent e) {
      this.actuaSliderF();
   }

   void jSlider_FAngi_mouseClicked(MouseEvent e) {
      this.actuaSliderF();
   }

   void jSlider_FAngi_mouseDragged(MouseEvent e) {
      this.actuaSliderF();
   }

   void jSlider_MNi_keyPressed(KeyEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MNi_keyTyped(KeyEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MNi_mouseDragged(MouseEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MNi_mouseClicked(MouseEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MNr_keyPressed(KeyEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MNr_keyTyped(KeyEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MNr_mouseClicked(MouseEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MNr_mouseDragged(MouseEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MKr_keyPressed(KeyEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MKr_keyTyped(KeyEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MKr_mouseClicked(MouseEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MKr_mouseDragged(MouseEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MAngi_keyPressed(KeyEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MAngi_keyTyped(KeyEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MAngi_mouseClicked(MouseEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MAngi_mouseDragged(MouseEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MLonda_keyPressed(KeyEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MLonda_keyTyped(KeyEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MLonda_mouseClicked(MouseEvent e) {
      this.actuaSliderM();
   }

   void jSlider_MLonda_mouseDragged(MouseEvent e) {
      this.actuaSliderM();
   }

   void jButton_Acerca_actionPerformed(ActionEvent e) {
      Frame f = new Frame();
      Object[] options = new Object[]{this.acerca_etiqueta[1][idioma]};
      ImageIcon icon_joc = null;

      try {
         String st_icon = "jocon.jpg";
         URL url_icon = this.getClass().getResource(st_icon);
         icon_joc = new ImageIcon(url_icon);
      } catch (Exception var7) {
         System.out.println("No carga icono");
      }

      JOptionPane option = new JOptionPane(this.acerca_etiqueta[2][idioma], 1, -1, icon_joc, options);
      JDialog dialog = option.createDialog(f, this.acerca_etiqueta[0][idioma]);
      dialog.setResizable(false);
      dialog.setVisible(true);
   }

   void jButton_FresPol_actionPerformed(ActionEvent e) {
      if (!label_grafico) {
         this.jButton_FresPol.setText(this.F_etiqueta[7][idioma]);
         this.jSplitPane.setDividerLocation(1.0D);
         label_grafico = true;
      } else {
         this.jButton_FresPol.setText(this.F_etiqueta[8][idioma]);
         this.jSplitPane.setDividerLocation(0);
         label_grafico = false;
      }

   }

   public void actuaSliderP() {
      DecimalFormatSymbols df_symb = new DecimalFormatSymbols();
      DecimalFormat df = new DecimalFormat("#.##", df_symb);
      DecimalFormat df1 = new DecimalFormat("#.#", df_symb);
      char pto = '.';
      df_symb.setDecimalSeparator(pto);
      df.setDecimalFormatSymbols(df_symb);
      df1.setDecimalFormatSymbols(df_symb);
      int v_a1 = this.jSlider_PA1.getValue();
      double vd_a1 = (double)v_a1 / 100.0D;
      this.jLabel_PA1.setText(this.P_etiqueta[0][idioma] + ": " + df.format(vd_a1));
      int v_a2 = this.jSlider_PA2.getValue();
      double vd_a2 = (double)v_a2 / 100.0D;
      this.jLabel_PA2.setText(this.P_etiqueta[1][idioma] + ": " + df.format(vd_a2));
      int v_fase = this.jSlider_PFase.getValue();
      double vd_fase = (double)v_fase;
      vd_fase = Math.IEEEremainder(vd_fase, 360.0D);
      this.jLabel_PFase.setText(this.P_etiqueta[2][idioma] + ": " + df.format(vd_fase) + " º");
      double fasrad = Math.toRadians(vd_fase);
      double elip_ang = Math.atan2(2.0D * vd_a1 * vd_a2 * Math.cos(fasrad), vd_a1 * vd_a1 - vd_a2 * vd_a2) / 2.0D;
      int elip_angi = (int)Math.toDegrees(elip_ang);
      String sa = this.elipse_etiqueta[0][idioma] + ": ";
      String sb = this.elipse_etiqueta[1][idioma] + ": ";
      double stokes_M;
      if (v_fase != 0 && v_fase != 180 && v_fase != 360 && vd_a1 != 0.0D && vd_a2 != 0.0D) {
         double elip_ejea = Math.abs(Math.sin(fasrad)) / Math.sqrt(Math.cos(elip_ang) * Math.cos(elip_ang) / vd_a1 / vd_a1 + Math.sin(elip_ang) * Math.sin(elip_ang) / vd_a2 / vd_a2 - Math.sin(2.0D * elip_ang) * Math.cos(fasrad) / (vd_a1 * vd_a2));
         stokes_M = elip_ang - 1.5707963267948966D;
         double elip_ejeb = Math.abs(Math.sin(fasrad)) / Math.sqrt(Math.cos(stokes_M) * Math.cos(stokes_M) / vd_a1 / vd_a1 + Math.sin(stokes_M) * Math.sin(stokes_M) / vd_a2 / vd_a2 - Math.sin(2.0D * stokes_M) * Math.cos(fasrad) / (vd_a1 * vd_a2));
         sa = sa + df.format(elip_ejea);
         sb = sb + df.format(elip_ejeb);
      } else if (vd_a1 != 0.0D && vd_a2 != 0.0D) {
         if (v_fase != 0 || v_fase != 180 || v_fase != 360) {
            sa = sa + df.format(Math.sqrt(vd_a1 * vd_a1 + vd_a2 * vd_a2));
            sb = sb + df.format(0.0D);
         }
      } else {
         sa = sa + df.format(Math.max(vd_a1, vd_a2));
         sb = sb + df.format(Math.min(vd_a1, vd_a2));
      }

      this.jLabel_elipA.setText(sa);
      this.jLabel_elipB.setText(sb);
      this.jLabel_elipAng.setText(this.elipse_etiqueta[2][idioma] + ": " + df.format((long)elip_angi) + " º");
      stokes_M = (vd_a1 * vd_a1 - vd_a2 * vd_a2) / (vd_a1 * vd_a1 + vd_a2 * vd_a2);
      double stokes_C = 2.0D * vd_a1 * vd_a2 * Math.cos(fasrad) / (vd_a1 * vd_a1 + vd_a2 * vd_a2);
      double stokes_S = 2.0D * vd_a1 * vd_a2 * Math.sin(fasrad) / (vd_a1 * vd_a1 + vd_a2 * vd_a2);
      this.jLabel_stokes1.setText("I: " + df.format(1.0D));
      this.jLabel_stokes2.setText("M: " + df.format(stokes_M));
      this.jLabel_stokes3.setText("C: " + df.format(stokes_C));
      this.jLabel_stokes4.setText("S: " + df.format(stokes_S));
      this.elipse.putAtributos(vd_a1, vd_a2, vd_fase, Color.red, idioma, false);
      this.ondas.putAtributos(vd_a1, vd_a2, vd_fase);
      this.jPanel_elipse.repaint();
      this.JPanel_PCSouth.repaint();
      this.elipsei.putAtributos(vd_a1, vd_a2, vd_fase, Color.red, idioma, false);
      this.elipsei_MRef.putAtributos(vd_a1, vd_a2, vd_fase, Color.red, idioma, false);
   }

   public void actuaSliderF() {
      DecimalFormatSymbols df_symb = new DecimalFormatSymbols();
      DecimalFormat df = new DecimalFormat("#.##", df_symb);
      DecimalFormat df1 = new DecimalFormat("#.#", df_symb);
      char pto = '.';
      df_symb.setDecimalSeparator(pto);
      df.setDecimalFormatSymbols(df_symb);
      df1.setDecimalFormatSymbols(df_symb);
      int v_ni = this.jSlider_FNi.getValue();
      double vd_ni = (double)v_ni / 100.0D;
      this.jLabel_FNi.setText(this.F_etiqueta[0][idioma] + ": " + df.format(vd_ni));
      int v_nr = this.jSlider_FNr.getValue();
      double vd_nr = (double)v_nr / 100.0D;
      this.jLabel_FNr.setText(this.F_etiqueta[1][idioma] + ": " + df.format(vd_nr));
      int v_angi = this.jSlider_FAngi.getValue();
      double vd_angi = Math.toRadians((double)v_angi / 10.0D);
      double vd_angr = Math.asin(vd_ni * Math.sin(vd_angi) / vd_nr);
      Color color_rp = new Color(50, 180, 140);
      Color color_rs = new Color(0, 50, 0);
      Color color_tp = new Color(0, 100, 255);
      Color color_ts = new Color(0, 0, 150);
      Color color_RefTotal = new Color(0, 180, 0);
      Color color_default = new Color(102, 102, 153);
      this.jLabel_Ftp.setForeground(color_tp);
      this.jLabel_Fts.setForeground(color_ts);
      this.jLabel_FAngr.setForeground(color_RefTotal);
      this.jLabel_FAngr.setForeground(color_default);
      double rp;
      double rs;
      double tp;
      double ts;
      if (vd_angi == 0.0D) {
         rp = (vd_ni - vd_nr) / (vd_ni + vd_nr);
         rs = rp;
         tp = 2.0D * vd_ni / (vd_ni + vd_nr);
         ts = tp;
      } else {
         rp = Math.tan(vd_angr - vd_angi) / Math.tan(vd_angr + vd_angi);
         rs = Math.sin(vd_angr - vd_angi) / Math.sin(vd_angr + vd_angi);
         ts = 2.0D * Math.sin(vd_angr) * Math.cos(vd_angi) / Math.sin(vd_angr + vd_angi);
         tp = ts / Math.cos(vd_angr - vd_angi);
      }

      this.jLabel_FAngi.setText(this.F_etiqueta[2][idioma] + ": " + df1.format(Math.toDegrees(vd_angi)) + " º");
      double vd_angB = Math.atan(vd_nr / vd_ni);
      boolean ref_total = false;
      double angp = 0.0D;
      double angs = 0.0D;
      double vd_a1;
      if (vd_ni > vd_nr) {
         this.jLabel_FAngL.setEnabled(true);
         double vd_angL = Math.asin(vd_nr / vd_ni);
         this.jLabel_FAngL.setText(this.F_etiqueta[5][idioma] + ": " + df1.format(Math.toDegrees(vd_angL)) + " º");
         if (vd_angi >= vd_angL) {
            ref_total = true;
            rp = -1.0D;
            rs = 1.0D;
            double N = vd_nr / vd_ni;
            double sin2_angi = Math.sin(vd_angi) * Math.sin(vd_angi);
            vd_a1 = Math.sqrt(sin2_angi - N * N);
            double cos_angi = Math.sqrt(1.0D - sin2_angi);
            angp = 2.0D * Math.toDegrees(Math.atan2(vd_a1, N * N * cos_angi));
            angp = Math.IEEEremainder(angp, 360.0D);
            angs = 2.0D * Math.toDegrees(Math.atan2(vd_a1, cos_angi));
            angs = Math.IEEEremainder(angs, 360.0D);
            this.jLabel_FAngr.setForeground(color_RefTotal);
            this.jLabel_FAngr.setText(this.F_etiqueta[6][idioma] + " !!");
            this.jLabel_Ftp.setForeground(color_rp);
            this.jLabel_Fts.setForeground(color_rs);
            this.jLabel_Ftp.setText(this.F_etiqueta[9][idioma] + ": " + df1.format(angp) + " º");
            this.jLabel_Fts.setText(this.F_etiqueta[10][idioma] + ": " + df1.format(angs) + " º");
         } else {
            ref_total = false;
            this.jLabel_FAngr.setText(this.F_etiqueta[3][idioma] + ": " + df1.format(Math.toDegrees(vd_angr)) + " º");
            this.jLabel_Ftp.setText("tp: " + df.format(tp));
            this.jLabel_Fts.setText("ts: " + df.format(ts));
         }
      } else {
         ref_total = false;
         this.jLabel_FAngL.setEnabled(false);
         this.jLabel_FAngL.setText(this.F_etiqueta[5][idioma] + ": ");
         this.jLabel_FAngr.setText(this.F_etiqueta[3][idioma] + ": " + df1.format(Math.toDegrees(vd_angr)) + " º");
         this.jLabel_Ftp.setText("tp: " + df.format(tp));
         this.jLabel_Fts.setText("ts: " + df.format(ts));
      }

      this.jLabel_Frp.setText("rp: " + df.format(rp));
      this.jLabel_Frs.setText("rs: " + df.format(rs));
      this.jLabel_FAngB.setText(this.F_etiqueta[4][idioma] + ": " + df1.format(Math.toDegrees(vd_angB)) + " º");
      int sign_rp = (int)(Math.abs(rp) / rp);
      int sign_rs = (int)(Math.abs(rs) / rs);
      boolean brewster = false;
      int angi_1dec = Math.round((float)Math.toDegrees(vd_angi) * 10.0F);
      int angB_1dec = Math.round((float)Math.toDegrees(vd_angB) * 10.0F);
      if (angi_1dec == angB_1dec) {
         brewster = true;
      }

      this.dibFresnel.putAtributos(vd_ni, vd_nr, Math.toDegrees(vd_angi), Math.toDegrees(vd_angr), sign_rp, sign_rs, ref_total, brewster);
      this.jPanel_FCC.repaint();
      this.grafFresnel.putAtributos(vd_ni, vd_nr, Math.toDegrees(vd_angi), rp, rs, tp, ts, ref_total);
      this.jPanelR_FGrafico.repaint();
      int v_a1 = this.jSlider_PA1.getValue();
      vd_a1 = (double)v_a1 / 100.0D;
      this.jLabel_FPolia1.setText("  " + this.P_etiqueta[0][idioma] + ": " + df.format(vd_a1));
      int v_a2 = this.jSlider_PA2.getValue();
      double vd_a2 = (double)v_a2 / 100.0D;
      this.jLabel_FPolia2.setText("  " + this.P_etiqueta[1][idioma] + ": " + df.format(vd_a2));
      int v_fase = this.jSlider_PFase.getValue();
      double vd_fase = (double)v_fase;
      vd_fase = Math.IEEEremainder(vd_fase, 360.0D);
      this.jLabel_FPolifas.setText("  " + this.P_etiqueta[2][idioma] + ": " + df.format(vd_fase) + " º");
      if (ref_total) {
         vd_fase += angs - angp - 180.0D;
         vd_fase = Math.IEEEremainder(vd_fase, 360.0D);
         this.jLabel_FAngr.setText(this.F_etiqueta[6][idioma] + " !!");
         if (label_grafico) {
            this.jLabel_RefTotal.setText(this.F_etiqueta[11][idioma] + ": " + df1.format(vd_fase) + " º");
         } else {
            this.jLabel_RefTotal.setText("              ");
         }

         vd_fase += 180.0D;
      } else {
         this.jLabel_RefTotal.setText("              ");
      }

      this.elipser.putAtributos(-vd_a1 * rp, vd_a2 * rs, vd_fase, Color.green, idioma, false);
      Color color_trans = new Color(0, 150, 255);
      if (ref_total) {
         color_trans = Color.black;
      }

      this.elipset.putAtributos(vd_a1 * tp, vd_a2 * ts, vd_fase, color_trans, idioma, ref_total);
      this.jPanelL_FPolar.repaint();
   }

   public void actuaSliderM() {
      DecimalFormatSymbols df_symb = new DecimalFormatSymbols();
      DecimalFormat df = new DecimalFormat("#.##", df_symb);
      DecimalFormat df1 = new DecimalFormat("#.#", df_symb);
      DecimalFormat df0 = new DecimalFormat("#", df_symb);
      char pto = '.';
      df_symb.setDecimalSeparator(pto);
      df.setDecimalFormatSymbols(df_symb);
      df1.setDecimalFormatSymbols(df_symb);
      int v_ni = this.jSlider_MNi.getValue();
      double vd_ni = (double)v_ni / 100.0D;
      this.jLabel_MNi.setText(this.F_etiqueta[0][idioma] + ": " + df.format(vd_ni));
      int v_kr = this.jSlider_MKr.getValue();
      double vd_kr = (double)v_kr / 100.0D;
      this.jLabel_MKr.setText(this.M_etiqueta[1][idioma] + ": " + df.format(vd_kr));
      int v_nr = this.jSlider_MNr.getValue();
      if (v_nr < 10) {
         v_nr = 10;
      }

      if (v_nr < 100 && vd_kr == 0.0D) {
         v_nr = 100;
      }

      double vd_nr = (double)v_nr / 100.0D;
      this.jLabel_MNr.setText(this.M_etiqueta[0][idioma] + ": " + df.format(vd_nr));
      int v_angi = this.jSlider_MAngi.getValue();
      double vd_angi = (double)v_angi / 10.0D;
      this.jLabel_MAngi.setText(this.F_etiqueta[2][idioma] + ": " + df.format(vd_angi) + " º");
      this.dibMetal.putAtributos(vd_ni, vd_angi);
      vd_angi = Math.toRadians(vd_angi);
      this.jPanel_MCC.repaint();
      new Complex(0.0D, 0.0D);
      new Complex(0.0D, 0.0D);
      new Complex(0.0D, 0.0D);
      new Complex(0.0D, 0.0D);
      new Complex(0.0D, 0.0D);
      new Complex(0.0D, 0.0D);
      Complex cn = new Complex(vd_nr, -vd_kr);
      Complex cRp;
      Complex cRs;
      Complex cRpnum;
      Complex cRpden;
      double rp;
      double angp;
      double angs;
      if (vd_angi == 0.0D) {
         cRpnum = new Complex(vd_ni - vd_nr, vd_kr);
         cRpden = new Complex(vd_ni + vd_nr, -vd_kr);
         cRp = cRpnum.divide(cRpden);
         cRs = cRp;
      } else {
         rp = Math.cos(vd_angi);
         angp = Math.sin(vd_angi);
         new Complex(0.0D, 0.0D);
         Complex c1 = new Complex(-vd_ni * vd_ni * angp * angp, 0.0D);
         Complex c = cn.multiply(cn);
         c = c.add(c1);
         c = ComplexUtils.sqrt(c);
         angs = c.getReal();
         double b = -c.getImaginary();
         double prod = (vd_nr * vd_nr - vd_kr * vd_kr) * rp;
         cRpnum = new Complex(-(prod - angs * vd_ni), 2.0D * vd_nr * vd_kr * rp - b * vd_ni);
         cRpden = new Complex(prod + angs * vd_ni, -(2.0D * vd_nr * vd_kr * rp + b * vd_ni));
         cRp = cRpnum.divide(cRpden);
         Complex cRsnum = new Complex(vd_ni * rp - angs, b);
         Complex cRsden = new Complex(vd_ni * rp + angs, -b);
         cRs = cRsnum.divide(cRsden);
      }

      rp = cRp.abs();
      angp = Math.atan2(cRp.getImaginary(), cRp.getReal());
      angp = Math.toDegrees(angp);
      angp = Math.IEEEremainder(angp, 360.0D);
      double rs = cRs.abs();
      angs = Math.atan2(cRs.getImaginary(), cRs.getReal());
      angs = Math.toDegrees(angs);
      angs = Math.IEEEremainder(angs, 360.0D);
      int v_a1 = this.jSlider_PA1.getValue();
      double vd_a1 = (double)v_a1 / 100.0D;
      this.jLabel_MPolia1.setText("  " + this.P_etiqueta[0][idioma] + ": " + df.format(vd_a1));
      int v_a2 = this.jSlider_PA2.getValue();
      double vd_a2 = (double)v_a2 / 100.0D;
      this.jLabel_MPolia2.setText("  " + this.P_etiqueta[1][idioma] + ": " + df.format(vd_a2));
      int v_fase = this.jSlider_PFase.getValue();
      double vd_fase = (double)v_fase;
      vd_fase = Math.IEEEremainder(vd_fase, 360.0D);
      this.jLabel_MPolifas.setText("  " + this.P_etiqueta[2][idioma] + ": " + df.format(vd_fase) + " º");
      vd_fase += angs - angp;
      vd_fase = Math.IEEEremainder(vd_fase, 360.0D);
      this.elipser_MRef.putAtributos(-vd_a1 * rp, vd_a2 * rs, vd_fase, Color.green, idioma, false);
      this.jLabel_MPolrp.setText("  rp: " + df.format(rp));
      this.jLabel_MPolrs.setText("  rs: " + df.format(rs));
      this.jLabel_MPolrangp.setText("  " + this.F_etiqueta[9][idioma] + ": " + df1.format(angp) + " º");
      this.jLabel_MPolrangs.setText("  " + this.F_etiqueta[10][idioma] + ": " + df1.format(angs) + " º");
      this.jLabel_MPolrfas.setText("  " + this.F_etiqueta[11][idioma] + ": " + df1.format(vd_fase) + " º");
      this.jPanel_MRef.repaint();
      int v_lambda = this.jSlider_MLonda.getValue();
      this.jLabel_MLonda.setText(this.M_etiqueta[2][idioma] + ": " + v_lambda + " nm");
      double T = (vd_ni - vd_nr) * (vd_ni - vd_nr) + vd_kr * vd_kr;
      T /= (vd_ni + vd_nr) * (vd_ni + vd_nr) + vd_kr * vd_kr;
      T = 1.0D - T;
      this.jLabel_MTT.setText(" T: " + df.format(T));
      if (vd_kr != 0.0D) {
         double piel = (double)(v_lambda / 2) / 3.141592653589793D / vd_kr;
         this.jLabel_MTpiel.setText(" " + this.M_etiqueta[6][idioma] + ": " + df0.format(piel) + " nm");
      } else {
         this.jLabel_MTpiel.setText(" " + this.M_etiqueta[6][idioma] + ": " + this.M_etiqueta[7][idioma]);
      }

      this.grafMetalT.putAtributos(vd_kr, (double)v_lambda);
      this.jPanel_MTC.repaint();
   }

   private void carga_info() {
      String s = null;
      URL info_page = null;

      try {
         if (idioma == 1) {
            s = "DocA_PolarCa.html";
         } else if (idioma == 2) {
            s = "DocA_PolarEn.html";
         } else {
            s = "DocA_PolarEs.html";
         }

         info_page = this.getClass().getResource(s);
      } catch (Exception var5) {
         System.err.println("Couldn't create help URL: " + s);
      }

      try {
         this.jTextPane_Doc.setPage(info_page);
      } catch (IOException var4) {
         System.err.println("Attempted to read a bad URL: " + info_page);
      }

   }
}
