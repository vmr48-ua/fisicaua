package interfero;

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
import java.io.IOException;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class Interferometros extends JApplet {
   String[][] etiqueta = new String[][]{{"Parámetros del sistema", "Paràmetres del sistema", "System parameters"}, {"Longitud de onda ", "Longitud d'ona ", "Wavelength "}, {"Distancia espejos ", "Distància miralls ", "Mirrors distance "}, {"Información parámetros del sistema", "Informació paràmetres sistema", "System parameters information"}, {"Índice de refracción", "Índex de refracció", "Refraction index"}, {"Gráfico", "Gràfic", "Graphic"}, {"Imagen", "Imatge", "Image"}, {"Anchura zona de visión ", "Amplada zona de visió ", "Display area width "}, {"Tipo de fuente", "Tipus de font", "Source type"}, {"(en el vacío) ", "(al buit) ", "(in vacuum) "}};
   String[] about = new String[]{"Acerca de", "En quant a", "About"};
   String[] aceptar = new String[]{"Aceptar", "Acceptar", "OK"};
   String[] rollo = new String[]{"Applet de Interferómetro de Michelson\nGrup d'Innovació docent en Òptica Física i Fotònica\nUniversitat de Barcelona, 2003\nLa utilización de este programa está bajo una licencia de Creative Commons \nVer condiciones en http://creativecommons.org/licenses/by-nc-sa/2.0/\nCurso de Óptica en Java DOI: 10.1344/401.000000050\nApplet de Interferómetro de Michelson    DOI: 10.1344/203.000000095", "Applet de Interferòmetre de Michelson\nGrup d'Innovació docent en Òptica Física i Fotònica\nUniversitat de Barcelona, 2003\nLa utilització d'aquest programa està sota una llicència Creative Commons - UB\nVeure condicions en http://www.publicacions.ub.es/doi/licencia/resum-deriv.htm\nCurs d'Òptica en Java DOI: 10.1344/401.000000050\nApplet de Interferòmetre de Michelson    DOI: 10.1344/203.000000095", "Michelson Interferometer Applet\nGrup d'Innovació docent en Òptica Física i Fotònica\nUniversitat de Barcelona, 2003\nThe use of this program is under a Creative Commons-UB license\nSee conditions in http://creativecommons.org/licenses/by-nc-sa/2.0/\nJava Optics Course DOI: 10.1344/401.000000050\nMichelson Interferometer Applet    DOI: 10.1344/203.000000095"};
   String[] exit = new String[]{"Salir", "Sortir", "Exit"};
   static String[] titol = new String[]{"Applet de Interferómetro de Michelson", "Applet d'Interferòmetre de Michelson", "Michelson Interferometer Applet"};
   String[][] radioText = new String[][]{{"Extensa", "Extensa", "Extended"}, {"Puntual (Twyman)", "Puntual (Twyman)", "Point (Twyman)  "}};
   String[][] esquemaText = new String[][]{{"Esquema", "Esquema", "Diagram"}, {"Montaje", "Muntatge", "Setup"}};
   String[] titolTabbed = new String[]{"Interferómetro de Michelson", "Interferòmetre de Michelson", "Michelson interferometer"};
   String[] focal = new String[]{"Focal lente:", "Focal lent:", "Lens focal length:"};
   String[] teoria = new String[]{"Resumen teoría", "Resum teoria", "Theory summary"};
   boolean isStandalone = false;
   static int lang;
   JPanel jPanelBase = new JPanel();
   JPanel jPanelNorte = new JPanel();
   BorderLayout borderLayout1 = new BorderLayout();
   JPanel jPanelCentro = new JPanel();
   JPanel jPanelSur = new JPanel();
   JButton jButtonSalir = new JButton();
   JTabbedPane jTabbedPane1 = new JTabbedPane();
   JPanel jPanelMichelson = new JPanel();
   JPanel jPanelMach = new JPanel();
   BorderLayout borderLayout2 = new BorderLayout();
   BorderLayout borderLayout3 = new BorderLayout();
   BorderLayout borderLayout4 = new BorderLayout();
   JPanel jPanel_m1_w = new JPanel();
   JPanel jPanel_m1_c = new JPanel();
   TitledBorder titledBorder1;
   JPanel jPanel_m1_w_n = new JPanel();
   BorderLayout borderLayout5 = new BorderLayout();
   BorderLayout borderLayout6 = new BorderLayout();
   JPanel jPanel_m1_w_c = new JPanel();
   JPanel jPanel_m1_w_n_n = new JPanel();
   JPanel jPanel_m1_w_n_w = new JPanel();
   JPanel jPanel_m1_w_n_e = new JPanel();
   JPanel jPanel_m1_w_n_c = new JPanel();
   BorderLayout borderLayout7 = new BorderLayout();
   JPanel jPanel_m1_c_n = new JPanel();
   BorderLayout borderLayout8 = new BorderLayout();
   JPanel jPanel_m1_c_c = new JPanel();
   JPanel jPanel_m1_c_n_n = new JPanel();
   JPanel jPanel_m1_c_n_w = new JPanel();
   JPanel jPanel_m1_c_n_e = new JPanel();
   JPanel jPanel_m1_c_n_c = new JPanel();
   JButton grafic = new JButton();
   Esq_Michelson esqMichelson = new Esq_Michelson();
   Graf_Michelson grafMichelson = new Graf_Michelson();
   Img_Michelson imgMichelson = new Img_Michelson();
   Esq_Conceptual esqConceptual = new Esq_Conceptual();
   FlowLayout flowLayout2 = new FlowLayout();
   JLabel jLabel1 = new JLabel();
   JLabel jLabel2 = new JLabel();
   static JSlider jSlider_l = new JSlider();
   JLabel jLabel3 = new JLabel();
   JSlider jSlider_d = new JSlider();
   JLabel jLabel4 = new JLabel();
   JLabel jLabel5 = new JLabel();
   FlowLayout flowLayout3 = new FlowLayout();
   JLabel jLabel6 = new JLabel();
   JLabel jLabel7 = new JLabel();
   JSlider jSlider_n = new JSlider();
   JLabel jLabel8 = new JLabel();
   JLabel jLabel9 = new JLabel();
   JPanel jPanel_m1_c_n_s = new JPanel();
   BorderLayout borderLayout9 = new BorderLayout();
   JSplitPane jSplitPane1;
   JSplitPane sp_esquema;
   int lambda;
   double n;
   double d;
   static double finestra = 1.25D;
   JLabel jLabel10;
   JSlider jSlider_angle;
   JButton jButton1;
   FlowLayout flowLayout1;
   JLabel jLabel11;
   JRadioButton jRadioButton1;
   JRadioButton jRadioButton2;
   ButtonGroup grupo;
   static boolean tipoFuente = false;
   JButton esquema;
   JPanel jPanel1;
   JLabel jLabel12;
   JSlider jSliderFocal;
   JPanel jPanel_info;
   JScrollPane jScrollPane1;
   BorderLayout borderLayout10;
   JTextPane jTextPane_info;
   static ImageIcon icon_joc = null;

   public String getParameter(String key, String def) {
      return this.isStandalone ? System.getProperty(key, def) : (this.getParameter(key) != null ? this.getParameter(key) : def);
   }

   public Interferometros() {
      this.jSplitPane1 = new JSplitPane(1, this.grafMichelson, this.imgMichelson);
      this.sp_esquema = new JSplitPane(1, this.esqConceptual, this.esqMichelson);
      this.jLabel10 = new JLabel();
      this.jSlider_angle = new JSlider();
      this.jButton1 = new JButton();
      this.flowLayout1 = new FlowLayout();
      this.jLabel11 = new JLabel();
      this.jRadioButton1 = new JRadioButton();
      this.jRadioButton2 = new JRadioButton();
      this.grupo = new ButtonGroup();
      this.esquema = new JButton();
      this.jPanel1 = new JPanel();
      this.jLabel12 = new JLabel();
      this.jSliderFocal = new JSlider();
      this.jPanel_info = new JPanel();
      this.jScrollPane1 = new JScrollPane();
      this.borderLayout10 = new BorderLayout();
      this.jTextPane_info = new JTextPane();

      try {
         String st_icon = "jocon.jpg";
         URL url_icon = this.getClass().getResource(st_icon);
         icon_joc = new ImageIcon(url_icon, "Interferencias de Young");
      } catch (Exception var3) {
         System.out.println("No carga icono");
      }

   }

   public void init() {
      this.carga_info(lang);

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.titledBorder1 = new TitledBorder("");
      this.setSize(new Dimension(750, 550));
      this.jPanelBase.setLayout(this.borderLayout1);
      this.jScrollPane1.setAutoscrolls(true);
      this.jButtonSalir.setMaximumSize(new Dimension(61, 127));
      this.jButtonSalir.setPreferredSize(new Dimension(90, 17));
      this.jButtonSalir.setText(this.exit[lang]);
      this.jButtonSalir.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Interferometros.this.jButtonSalir_actionPerformed(e);
         }
      });
      this.jPanelSur.setLayout(this.flowLayout1);
      this.jPanelCentro.setLayout(this.borderLayout2);
      this.jPanelMichelson.setLayout(this.borderLayout3);
      this.jPanelMach.setLayout(this.borderLayout4);
      this.jPanel_m1_w.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_m1_w.setPreferredSize(new Dimension(375, 450));
      this.jPanel_m1_w.setLayout(this.borderLayout5);
      this.jPanel_m1_c.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_m1_c.setMaximumSize(new Dimension(379, 369));
      this.jPanel_m1_c.setMinimumSize(new Dimension(379, 369));
      this.jPanel_m1_c.setLayout(this.borderLayout7);
      this.jPanel_m1_w_n.setLayout(this.borderLayout6);
      this.jPanel_m1_w_n.setPreferredSize(new Dimension(280, 250));
      this.jPanel_m1_w_n_c.setBackground(Color.darkGray);
      this.jPanel_m1_w_n_c.setMinimumSize(new Dimension(360, 350));
      this.jPanel_m1_c_n.setLayout(this.borderLayout8);
      this.jPanel_m1_c_n.setPreferredSize(new Dimension(375, 280));
      this.jPanel_m1_c_n_c.setBackground(Color.black);
      this.jPanel_m1_c_n_c.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel_m1_c_n_c.setPreferredSize(new Dimension(230, 230));
      this.jPanel_m1_c_n_c.setLayout(this.borderLayout9);
      this.jPanel_m1_w_c.setLayout(this.flowLayout2);
      this.jPanel_m1_w_c.setPreferredSize(new Dimension(1417, 150));
      this.jLabel1.setForeground(new Color(102, 102, 153));
      this.jLabel1.setPreferredSize(new Dimension(375, 17));
      this.jLabel1.setHorizontalAlignment(0);
      this.jLabel1.setText(this.etiqueta[0][lang]);
      this.jLabel2.setPreferredSize(new Dimension(134, 17));
      this.jLabel2.setForeground(new Color(102, 102, 153));
      this.jLabel2.setText(this.etiqueta[1][lang]);
      this.jLabel3.setPreferredSize(new Dimension(134, 17));
      this.jLabel3.setText(this.etiqueta[2][lang]);
      this.jLabel3.setForeground(new Color(102, 102, 153));
      jSlider_l.setMaximum(700);
      jSlider_l.setMinimum(380);
      jSlider_l.setMinorTickSpacing(20);
      jSlider_l.setPaintTicks(true);
      jSlider_l.setValue(633);
      jSlider_l.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            Interferometros.this.jSlider_l_mouseDragged(e);
         }
      });
      jSlider_l.addMouseListener(new MouseAdapter() {
         public void mouseDragged(MouseEvent e) {
            Interferometros.this.jSlider_l_mouseDragged(e);
         }

         public void mouseClicked(MouseEvent e) {
            Interferometros.this.jSlider_l_mouseClicked(e);
         }
      });
      jSlider_l.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            Interferometros.this.jSlider_l_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            Interferometros.this.jSlider_l_keyTyped(e);
         }
      });
      this.jSlider_d.setMaximum(600);
      this.jSlider_d.setMinimum(0);
      this.jSlider_d.setMinorTickSpacing(40);
      this.jSlider_d.setPaintTicks(true);
      this.jSlider_d.setValue(0);
      this.jSlider_d.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            Interferometros.this.jSlider_d_mouseDragged(e);
         }
      });
      this.jSlider_d.addMouseListener(new MouseAdapter() {
         public void mouseDragged(MouseEvent e) {
            Interferometros.this.jSlider_d_mouseDragged(e);
         }

         public void mouseClicked(MouseEvent e) {
            Interferometros.this.jSlider_d_mouseClicked(e);
         }
      });
      this.jSlider_d.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            Interferometros.this.jSlider_d_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            Interferometros.this.jSlider_d_keyTyped(e);
         }
      });
      this.jSlider_n.setMajorTickSpacing(25);
      this.jSlider_n.setMaximum(200);
      this.jSlider_n.setMinimum(100);
      this.jSlider_n.setMinorTickSpacing(5);
      this.jSlider_n.setPaintTicks(true);
      this.jSlider_n.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            Interferometros.this.jSlider_n_mouseDragged(e);
         }
      });
      this.jSlider_n.addMouseListener(new MouseAdapter() {
         public void mouseDragged(MouseEvent e) {
            Interferometros.this.jSlider_n_mouseDragged(e);
         }

         public void mouseClicked(MouseEvent e) {
            Interferometros.this.jSlider_n_mouseClicked(e);
         }
      });
      this.jSlider_n.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            Interferometros.this.jSlider_n_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            Interferometros.this.jSlider_n_keyTyped(e);
         }
      });
      this.jSlider_angle.setMajorTickSpacing(10);
      this.jSlider_angle.setMaximum(50);
      this.jSlider_angle.setMinimum(0);
      this.jSlider_angle.setMinorTickSpacing(2);
      this.jSlider_angle.setPaintTicks(true);
      this.jSlider_angle.setValue(0);
      this.jSlider_angle.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            Interferometros.this.jSlider_angle_mouseDragged(e);
         }
      });
      this.jSlider_angle.addMouseListener(new MouseAdapter() {
         public void mouseDragged(MouseEvent e) {
            Interferometros.this.jSlider_angle_mouseDragged(e);
         }

         public void mouseClicked(MouseEvent e) {
            Interferometros.this.jSlider_angle_mouseClicked(e);
         }
      });
      this.jSlider_angle.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            Interferometros.this.jSlider_angle_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            Interferometros.this.jSlider_angle_keyTyped(e);
         }
      });
      this.jSliderFocal.setMajorTickSpacing(10);
      this.jSliderFocal.setMaximum(100);
      this.jSliderFocal.setMinimum(50);
      this.jSliderFocal.setMinorTickSpacing(5);
      this.jSliderFocal.setPaintTicks(true);
      this.jSliderFocal.setValue(50);
      this.jSliderFocal.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
            Interferometros.this.jSliderFocal_mouseDragged(e);
         }
      });
      this.jSliderFocal.addMouseListener(new MouseAdapter() {
         public void mouseDragged(MouseEvent e) {
            Interferometros.this.jSliderFocal_mouseDragged(e);
         }

         public void mouseClicked(MouseEvent e) {
            Interferometros.this.jSliderFocal_mouseClicked(e);
         }
      });
      this.jSliderFocal.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            Interferometros.this.jSliderFocal_keyPressed(e);
         }

         public void keyTyped(KeyEvent e) {
            Interferometros.this.jSliderFocal_keyTyped(e);
         }
      });
      this.jSplitPane1.setBorder((Border)null);
      this.jSplitPane1.setContinuousLayout(true);
      this.jSplitPane1.setDividerSize(0);
      this.jSplitPane1.setOneTouchExpandable(false);
      this.jSplitPane1.setDividerLocation(1.0D);
      this.sp_esquema.setBorder((Border)null);
      this.sp_esquema.setMinimumSize(new Dimension(350, 350));
      this.sp_esquema.setPreferredSize(new Dimension(350, 310));
      this.sp_esquema.setContinuousLayout(true);
      this.sp_esquema.setDividerSize(0);
      this.sp_esquema.setOneTouchExpandable(false);
      this.sp_esquema.setDividerLocation(1.0D);
      this.jLabel4.setPreferredSize(new Dimension(300, 17));
      this.jLabel4.setHorizontalAlignment(0);
      this.jLabel4.setText(this.etiqueta[3][lang]);
      this.jLabel4.setForeground(new Color(102, 102, 153));
      this.jLabel5.setPreferredSize(new Dimension(300, 17));
      this.jLabel5.setText(this.etiqueta[1][lang] + " " + this.etiqueta[9][lang] + ": 633 nm");
      this.jLabel5.setForeground(new Color(102, 102, 153));
      this.jPanel_m1_c_c.setLayout(this.flowLayout3);
      this.jLabel6.setPreferredSize(new Dimension(300, 17));
      this.jLabel6.setText(this.etiqueta[2][lang] + ": 0 mm");
      this.jLabel6.setForeground(new Color(102, 102, 153));
      this.jLabel7.setPreferredSize(new Dimension(134, 17));
      this.jLabel7.setText(this.etiqueta[4][lang]);
      this.jLabel7.setForeground(new Color(102, 102, 153));
      this.jLabel8.setPreferredSize(new Dimension(300, 17));
      this.jLabel8.setText(this.etiqueta[4][lang] + " : 1");
      this.jLabel8.setForeground(new Color(102, 102, 153));
      this.jLabel9.setText(this.etiqueta[7][lang] + " : " + finestra + " mm");
      this.jLabel9.setForeground(new Color(102, 102, 153));
      this.jPanel_m1_c_n_w.setPreferredSize(new Dimension(63, 10));
      this.jPanel_m1_c_n_e.setPreferredSize(new Dimension(60, 10));
      this.jPanel_m1_c_n_e.setPreferredSize(new Dimension(62, 10));
      this.jPanel_m1_c_n_s.setPreferredSize(new Dimension(10, 100));
      this.jPanel_m1_c_n_s.setPreferredSize(new Dimension(10, 20));
      this.esqMichelson.setMinimumSize(new Dimension(350, 350));
      this.esqMichelson.setPreferredSize(new Dimension(350, 300));
      this.grafic.setPreferredSize(new Dimension(100, 27));
      this.grafic.setText(this.etiqueta[5][lang]);
      this.grafic.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Interferometros.this.grafic_actionPerformed(e);
         }
      });
      this.jLabel9.setPreferredSize(new Dimension(300, 17));
      this.jLabel10.setPreferredSize(new Dimension(134, 17));
      this.jLabel10.setForeground(new Color(102, 102, 153));
      this.jLabel10.setText(this.etiqueta[8][lang]);
      this.jButton1.setPreferredSize(new Dimension(120, 17));
      this.jButton1.setHorizontalTextPosition(0);
      this.jButton1.setText(this.about[lang]);
      this.jButton1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Interferometros.this.jButton1_actionPerformed(e);
         }
      });
      this.jPanelSur.setPreferredSize(new Dimension(210, 27));
      this.flowLayout1.setAlignment(2);
      this.jLabel11.setMaximumSize(new Dimension(300, 17));
      this.jLabel11.setPreferredSize(new Dimension(300, 27));
      this.jLabel11.setForeground(new Color(102, 102, 153));
      this.jLabel11.setText(this.etiqueta[8][lang] + " : " + "0.0º");
      this.jRadioButton1.setText(this.radioText[0][lang]);
      this.jRadioButton1.setForeground(new Color(102, 102, 153));
      this.jRadioButton1.setSelected(true);
      this.jRadioButton1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Interferometros.this.jRadioButton1_actionPerformed(e);
         }
      });
      this.jRadioButton2.setText(this.radioText[1][lang]);
      this.jRadioButton2.setForeground(new Color(102, 102, 153));
      this.esquema.setMaximumSize(new Dimension(35, 11));
      this.esquema.setMinimumSize(new Dimension(35, 11));
      this.esquema.setPreferredSize(new Dimension(100, 27));
      this.esquema.setText(this.esquemaText[0][lang]);
      this.esquema.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Interferometros.this.esquema_actionPerformed(e);
         }
      });
      this.jPanel1.setPreferredSize(new Dimension(300, 10));
      this.jPanel_m1_c_n_n.setPreferredSize(new Dimension(10, 30));
      this.jLabel12.setPreferredSize(new Dimension(195, 27));
      this.jLabel12.setText(this.focal[lang]);
      this.jLabel12.setForeground(new Color(102, 102, 153));
      this.jSliderFocal.setPreferredSize(new Dimension(100, 27));
      this.jPanel_info.setLayout(this.borderLayout10);
      this.jTextPane_info.setBackground(new Color(204, 204, 204));
      this.jTextPane_info.setEditable(false);
      this.jTextPane_info.setMargin(new Insets(10, 10, 10, 10));
      this.jPanelCentro.setMinimumSize(new Dimension(768, 429));
      this.grupo.add(this.jRadioButton1);
      this.grupo.add(this.jRadioButton2);
      this.jRadioButton2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Interferometros.this.jRadioButton2_actionPerformed(e);
         }
      });
      this.getContentPane().add(this.jPanelBase, "Center");
      this.jPanelBase.add(this.jPanelNorte, "North");
      this.jPanelBase.add(this.jPanelCentro, "Center");
      this.jPanelCentro.add(this.jTabbedPane1, "Center");
      this.jTabbedPane1.add(this.jPanelMichelson, this.titolTabbed[lang]);
      this.jPanelMichelson.add(this.jPanel_m1_w, "West");
      this.jPanel_m1_w.add(this.jPanel_m1_w_n, "Center");
      this.jPanelBase.add(this.jPanelSur, "South");
      this.jPanelSur.add(this.jButton1, (Object)null);
      this.jPanelSur.add(this.jButtonSalir, (Object)null);
      this.jPanelMichelson.add(this.jPanel_m1_c, "Center");
      this.jPanel_m1_c_n_c.add(this.jSplitPane1, "Center");
      this.jPanel_m1_c.add(this.jPanel_m1_c_n, "North");
      this.jPanel_m1_w.add(this.jPanel_m1_w_c, "South");
      this.jPanel_m1_w_c.add(this.jLabel1, (Object)null);
      this.jPanel_m1_w_c.add(this.jLabel2, (Object)null);
      this.jPanel_m1_w_c.add(jSlider_l, (Object)null);
      this.jPanel_m1_w_c.add(this.jLabel3, (Object)null);
      this.jPanel_m1_w_c.add(this.jSlider_d, (Object)null);
      this.jPanel_m1_w_c.add(this.jLabel7, (Object)null);
      this.jPanel_m1_w_c.add(this.jSlider_n, (Object)null);
      this.jPanel_m1_w_c.add(this.jLabel10, (Object)null);
      this.jPanel_m1_w_c.add(this.jRadioButton1, (Object)null);
      this.jPanel_m1_w_c.add(this.jRadioButton2, (Object)null);
      this.jPanel_m1_w_n.add(this.jPanel_m1_w_n_n, "North");
      this.jPanel_m1_w_n.add(this.jPanel_m1_w_n_w, "West");
      this.jPanel_m1_w_n.add(this.jPanel_m1_w_n_e, "East");
      this.jPanel_m1_w_n.add(this.jPanel_m1_w_n_c, "Center");
      this.jPanel_m1_w_n_c.add(this.sp_esquema, "Center");
      this.jPanel_m1_c.add(this.jPanel_m1_c_c, "Center");
      this.jPanel_m1_c_c.add(this.jLabel4, (Object)null);
      this.jPanel_m1_c_c.add(this.jLabel5, (Object)null);
      this.jPanel_m1_c_c.add(this.jLabel6, (Object)null);
      this.jPanel_m1_c_c.add(this.jLabel8, (Object)null);
      this.jPanel_m1_c_c.add(this.jLabel9, (Object)null);
      this.jPanel_m1_c_c.add(this.jLabel12, (Object)null);
      this.jPanel_m1_c_c.add(this.jSliderFocal, (Object)null);
      this.jPanel_m1_c_c.add(this.jPanel1, (Object)null);
      this.jPanel_m1_c_c.add(this.esquema, (Object)null);
      this.jPanel_m1_c_c.add(this.grafic, (Object)null);
      this.jTabbedPane1.add(this.jPanel_info, "jPanel_info");
      this.jTabbedPane1.add(this.jPanel_info, this.teoria[lang]);
      this.jPanel_info.add(this.jScrollPane1, "Center");
      this.jScrollPane1.getViewport().add(this.jTextPane_info, (Object)null);
      this.jPanel_m1_c_n.add(this.jPanel_m1_c_n_n, "North");
      this.jPanel_m1_c_n.add(this.jPanel_m1_c_n_w, "West");
      this.jPanel_m1_c_n.add(this.jPanel_m1_c_n_e, "East");
      this.jPanel_m1_c_n.add(this.jPanel_m1_c_n_c, "Center");
      this.jPanel_m1_c_n.add(this.jPanel_m1_c_n_s, "South");
      this.jPanel_m1_w_n.repaint();
      this.actualizaSlider();
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

      Interferometros applet = new Interferometros();
      applet.isStandalone = true;
      JFrame frame = new JFrame();
      if (icon_joc != null) {
         frame.setIconImage(icon_joc.getImage());
      }

      frame.setDefaultCloseOperation(3);
      frame.setTitle(titol[lang]);
      frame.setResizable(false);
      frame.getContentPane().add(applet, "Center");
      applet.init();
      applet.start();
      frame.setSize(750, 570);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
      frame.setVisible(true);
   }

   void jButtonSalir_actionPerformed(ActionEvent e) {
      try {
         System.exit(0);
      } catch (Exception var3) {
         System.out.println("Error al salir");
      }

   }

   void jSlider_l_mouseDragged(MouseEvent e) {
      this.actualizaSlider();
   }

   void jSlider_l_mouseClicked(MouseEvent e) {
      this.actualizaSlider();
   }

   void jSlider_l_keyPressed(KeyEvent e) {
      this.actualizaSlider();
   }

   void jSlider_l_keyTyped(KeyEvent e) {
      this.actualizaSlider();
   }

   void jSlider_d_mouseDragged(MouseEvent e) {
      this.actualizaSlider();
   }

   void jSlider_d_mouseClicked(MouseEvent e) {
      this.actualizaSlider();
   }

   void jSlider_d_keyPressed(KeyEvent e) {
      this.actualizaSlider();
   }

   void jSlider_d_keyTyped(KeyEvent e) {
      this.actualizaSlider();
   }

   void jSlider_n_mouseDragged(MouseEvent e) {
      this.actualizaSlider();
   }

   void jSlider_n_mouseClicked(MouseEvent e) {
      this.actualizaSlider();
   }

   void jSlider_n_keyPressed(KeyEvent e) {
      this.actualizaSlider();
   }

   void jSlider_n_keyTyped(KeyEvent e) {
      this.actualizaSlider();
   }

   void jSlider_angle_mouseDragged(MouseEvent e) {
      this.actualizaSlider();
   }

   void jSlider_angle_mouseClicked(MouseEvent e) {
      this.actualizaSlider();
   }

   void jSlider_angle_keyPressed(KeyEvent e) {
      this.actualizaSlider();
   }

   void jSlider_angle_keyTyped(KeyEvent e) {
      this.actualizaSlider();
   }

   void jSliderFocal_mouseDragged(MouseEvent e) {
      this.actualizaSlider();
   }

   void jSliderFocal_mouseClicked(MouseEvent e) {
      this.actualizaSlider();
   }

   void jSliderFocal_keyPressed(KeyEvent e) {
      this.actualizaSlider();
   }

   void jSliderFocal_keyTyped(KeyEvent e) {
      this.actualizaSlider();
   }

   void actualizaSlider() {
      this.lambda = jSlider_l.getValue();
      this.n = (double)this.jSlider_n.getValue() / 100.0D;
      this.d = (double)this.jSlider_d.getValue() / 100.0D;
      int colorfondo = 50 + (int)((double)this.jSlider_n.getValue() * 0.2D);
      double a = (double)this.jSlider_angle.getValue() / 10.0D;
      this.esqMichelson.putAtributos(this.lambda, this.d, this.n);
      this.jPanel_m1_w_n_c.setBackground(new Color(colorfondo, colorfondo, colorfondo));
      this.jPanel_m1_w_n.repaint();
      this.jLabel5.setText(this.etiqueta[1][lang] + " " + this.etiqueta[9][lang] + ": " + jSlider_l.getValue() + " nm");
      this.jLabel6.setText(this.etiqueta[2][lang] + ": " + (double)this.jSlider_d.getValue() / 100.0D + " mm");
      this.jLabel8.setText(this.etiqueta[4][lang] + " : " + (double)this.jSlider_n.getValue() / 100.0D);
      this.jLabel11.setText(this.etiqueta[8][lang] + " : " + (double)this.jSlider_angle.getValue() / 10.0D + "º");
      this.imgMichelson.putAtributos((double)this.lambda, this.d, this.n);
      this.grafMichelson.putAtributos(this.lambda, this.d, this.n);
      this.esqConceptual.putAtributos(this.lambda, this.d, this.n);
      this.jLabel12.setText(this.focal[lang] + " " + this.jSliderFocal.getValue() + " mm");
      this.jLabel9.setText(this.etiqueta[7][lang] + " : " + finestra * (double)this.jSliderFocal.getValue() / 50.0D + " mm");
      this.jPanel_m1_c_n.repaint();
      this.jPanel_m1_w_n.repaint();
   }

   void grafic_actionPerformed(ActionEvent e) {
      if (this.grafic.getText().equals(this.etiqueta[5][lang])) {
         this.grafic.setText(this.etiqueta[6][lang]);
         this.jSplitPane1.setDividerLocation(1.0D);
         this.jPanel_m1_c_n.repaint();
      } else {
         this.grafic.setText(this.etiqueta[5][lang]);
         this.jSplitPane1.setDividerLocation(0.0D);
         this.jPanel_m1_c_n.repaint();
      }

   }

   void jButton1_actionPerformed(ActionEvent e) {
      Frame f = new Frame();
      Object[] options = new Object[]{this.aceptar[lang]};
      ImageIcon icon_joc = null;

      try {
         String st_icon = "jocon.jpg";
         URL url_icon = this.getClass().getResource(st_icon);
         icon_joc = new ImageIcon(url_icon, "Interferencias de Young");
      } catch (Exception var7) {
         System.out.println("No carga icono");
      }

      JOptionPane hola = new JOptionPane(this.rollo[lang], 1, -1, icon_joc, options);
      JDialog dialog = hola.createDialog(f, this.about[lang]);
      dialog.show();
      this.repaint();
   }

   void jRadioButton1_actionPerformed(ActionEvent e) {
      tipoFuente = false;
      this.actualizaSlider();
   }

   void jRadioButton2_actionPerformed(ActionEvent e) {
      tipoFuente = true;
      this.actualizaSlider();
   }

   void esquema_actionPerformed(ActionEvent e) {
      if (this.esquema.getText().equals(this.esquemaText[1][lang])) {
         this.esquema.setText(this.esquemaText[0][lang]);
         this.sp_esquema.setDividerLocation(0.0D);
         this.jPanel_m1_w_n.repaint();
      } else {
         this.esquema.setText(this.esquemaText[1][lang]);
         this.sp_esquema.setDividerLocation(1.0D);
         this.jPanel_m1_w_n.repaint();
      }

   }

   private void carga_info(int lengua) {
      this.jTextPane_info.setEditable(false);
      String s = null;
      URL info_page = null;

      try {
         if (lengua == 1) {
            s = "DocA_MichelCa.htm";
         } else if (lengua == 2) {
            s = "DocA_MichelEn.htm";
         } else {
            s = "DocA_MichelEs.htm";
         }

         info_page = this.getClass().getResource(s);
      } catch (Exception var6) {
         System.err.println("Couldn't create help URL: " + s);
      }

      try {
         this.jTextPane_info.setPage(info_page);
      } catch (IOException var5) {
         System.err.println("Attempted to read a bad URL: " + info_page);
      }

   }
}
