package optfourier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import local.imagenes.OptImagen;

public class OptFou extends JApplet {
   boolean isStandalone = false;
   Holog g;
   static int ult_n;
   static int idiom;
   FrameInfo m;
   static boolean hab;
   static int num = 20;
   static boolean[] salvado;
   JPanel jPanelCentral;
   BorderLayout borderLayout1;
   JPanel jPanelN;
   JPanel jPanelS;
   JPanel jPanelO;
   JPanel jPanelC;
   JPanel jPanelE;
   JButton jButton1;
   static JButton jButton2;
   static Vector vect;
   String[] about = new String[]{"En quant a", "Acerca de", "About"};
   String[] rollo = new String[]{"Applet d'Òptica de Fourier\nGrup d'Innovació docent en Òptica Física i Fotònica\nUniversitat de Barcelona, 2003\nLa utilització d'aquest programa està sota una llicència Creative Commons - UB\nVeure condicions en http://www.publicacions.ub.es/doi/licencia/resum-deriv.htm\nCurs d'Òptica en Java DOI: 10.1344/401.000000050\nApplet d'Òptica de Fourier    DOI: 10.1344/203.000000096", "Applet de Óptica de Fourier\nGrup d'Innovació docent en Òptica Física i Fotònica\nUniversitat de Barcelona, 2003\nLa utilización de este programa está bajo una licencia de Creative Commons \nVer condiciones en http://creativecommons.org/licenses/by-nc-sa/2.0/\nCurso de Óptica en Java DOI: 10.1344/401.000000050\nApplet de Óptica de Fourier    DOI: 10.1344/203.000000096", "Fourier Optics Applet\nGrup d'Innovació docent en Òptica Física i Fotònica\nUniversitat de Barcelona, 2003\nThe use of this program is under a Creative Commons-UB license\nSee conditions in http://creativecommons.org/licenses/by-nc-sa/2.0/\nJava Optics Course DOI: 10.1344/401.000000050\nFourier Optics Applet    DOI: 10.1344/203.000000096"};
   String[] exit = new String[]{"Sortir", "Salir", "Exit"};
   static String[] aceptar = new String[]{"Acceptar", "Aceptar", "OK"};
   static String[][] etiqueta = new String[][]{{"Arxius", "Archivos", "Files"}, {"Transf. Fourier", "Transf. Fourier", "Fourier Transf."}, {"Correlació", "Correlación", "Correlation"}, {"Holografia 2D", "Holografia 2D", "2D Holography"}, {"Ajuda", "Ayuda", "Help"}, {"Carregar Imatge BMP/RAW/GIF/JPEG", "Cargar Imagen BMP/RAW/GIF/JPEG", "Open BMP/RAW/GIF/JPEG File"}, {"Salvar Arxiu BMP/RAW", "Salvar Archivo BMP/RAW", "Save BMP/RAW File"}, {"Sortir", "Salir", "Exit"}, {"Amplitud TF", "Amplitud TF", "FT Amplitude"}, {"Gamma Amplitud TF", "Gamma Amplitud TF", "FT Gamma Amplitude"}, {"Intensitat Log TF", "Intensidad Log TF", "FT Log Intensity"}, {"Fase TF", "Fase TF", "FT Phase"}, {"Filtre Adaptat", "Filtro Adaptado", "Adapted Filter"}, {"Filtre de Fase", "Filtro de Fase", "Phase Filter"}, {"Filtre Invers", "Filtro Inverso", "Inverse Filter"}, {"Convolució", "Convolución", "Convolution"}, {"Joint", "Joint", "Joint"}, {"Joint Binari", "Joint Binario", "Binary Joint"}, {"Codificació Burckhardt", "Codificación Burckhardt", "Burckhardt Codification"}, {"Visualització", "Visualización", "View"}, {"Zoom", "Zoom", "Zoom"}, {"De Disc", "De Disco", "From Disk"}, {"D'Applet", "De Applet", "From Applet"}, {"Lectura de fila", "Lectura de fila", "Row scan"}, {"Lectura de columna", "Lectura de columna", "Column scan"}, {"Informació", "Información", "Information"}, {"Historial", "Historial", "History"}, {"Teoria", "Teoría", "Theory"}};
   static String[] salvat = new String[]{"  Desada en disc", "  Guardada en disco", "  Saved on disk"};
   static String[] titol0 = new String[]{"Imatge ", "Imagen ", "Image "};
   static String[] si = new String[]{" Sí", " Sí", " Yes"};
   static String[] no = new String[]{" No", " No", " No"};
   JMenuBar jMenuBar1;
   JMenu jMenu3;
   JMenu jMenu4;
   JMenu jMenu5;
   JMenu jMenu6;
   JMenu jMenu1;
   JMenu jMenu8;
   static JMenuItem jMenuItem1;
   static JMenuItem jMenuItem2;
   static JMenuItem jMenuItem3;
   static JMenuItem jMenuItem4;
   static JMenuItem jMenuItem5;
   static JMenuItem jMenuItem6;
   static JMenuItem jMenuItem7;
   static JMenuItem jMenuItem8;
   static JMenuItem jMenuItem9;
   static JMenuItem jMenuItem10;
   static JMenuItem jMenuItem11;
   static JMenuItem jMenuItem12;
   static JMenuItem jMenuItem13;
   static JMenuItem jMenuItem14;
   static JMenuItem jMenuItem15;
   static JMenuItem jMenuItem16;
   static JMenuItem jMenuItem17;
   static JMenuItem jMenuItem18;
   static JMenuItem jMenuItem19;
   static JPanel jPanelTexto;
   BorderLayout borderLayout2;
   BorderLayout borderLayout3;
   JPanel jPanel2;
   BorderLayout borderLayout5;
   JPanel jPanel3;
   FlowLayout flowLayout1;
   JMenu jMenu7;
   BorderLayout borderLayout6;
   JPanel jPanel5;
   JPanel jPanel6;
   JPanel jPanel7;
   static JComboBox jCombo;
   FlowLayout flowLayout2;
   BorderLayout borderLayout7;
   JPanel jPanel4;
   BorderLayout borderLayout4;
   JPanel jPanel1;
   JPanel jPanel8;
   static JLabel jLabelImagen;
   static JLabel save;
   static JTextField jTexto;
   static JTextField jTextoDim;
   static JTextField jTextoTime;
   static String[] texto;
   static String[] textoDim;
   static String[] textoTime;
   static ImageIcon icon_joc = null;
   static ImageIcon save_joc = null;

   public String getParameter(String key, String def) {
      return this.isStandalone ? System.getProperty(key, def) : (this.getParameter(key) != null ? this.getParameter(key) : def);
   }

   public OptFou() {
      String save_icon;
      URL url_save;
      try {
         save_icon = "jocon.jpg";
         url_save = this.getClass().getResource(save_icon);
         icon_joc = new ImageIcon(url_save, "Òptica de Fourier");
      } catch (Exception var4) {
         System.out.println("No carga icono");
      }

      try {
         save_icon = "disk.jpg";
         url_save = this.getClass().getResource(save_icon);
         save_joc = new ImageIcon(url_save, "Òptica de Fourier");
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

   }

   private void jbInit() throws Exception {
      ult_n = 0;
      this.g = new Holog();
      num = 20;
      salvado = new boolean[num];
      this.jPanelCentral = new JPanel();
      this.borderLayout1 = new BorderLayout();
      this.jPanelN = new JPanel();
      this.jPanelS = new JPanel();
      this.jPanelE = new JPanel();
      this.jPanelO = new JPanel();
      this.jPanelC = new JPanel();
      this.jButton1 = new JButton();
      jButton2 = new JButton();
      vect = new Vector();
      this.jMenuBar1 = new JMenuBar();
      this.jMenu3 = new JMenu();
      this.jMenu4 = new JMenu();
      this.jMenu5 = new JMenu();
      this.jMenu6 = new JMenu();
      this.jMenu8 = new JMenu();
      this.jMenu1 = new JMenu();
      jMenuItem1 = new JMenuItem();
      jMenuItem2 = new JMenuItem();
      jMenuItem3 = new JMenuItem();
      jMenuItem4 = new JMenuItem();
      jMenuItem5 = new JMenuItem();
      jMenuItem6 = new JMenuItem();
      jMenuItem7 = new JMenuItem();
      jMenuItem8 = new JMenuItem();
      jMenuItem9 = new JMenuItem();
      jMenuItem10 = new JMenuItem();
      jMenuItem11 = new JMenuItem();
      jMenuItem12 = new JMenuItem();
      jMenuItem13 = new JMenuItem();
      jMenuItem14 = new JMenuItem();
      jMenuItem15 = new JMenuItem();
      jMenuItem16 = new JMenuItem();
      jMenuItem17 = new JMenuItem();
      jMenuItem18 = new JMenuItem();
      jMenuItem19 = new JMenuItem();
      jPanelTexto = new JPanel();
      this.borderLayout2 = new BorderLayout();
      this.borderLayout3 = new BorderLayout();
      this.borderLayout5 = new BorderLayout();
      this.borderLayout6 = new BorderLayout();
      this.jPanel2 = new JPanel();
      this.jPanel3 = new JPanel();
      this.flowLayout1 = new FlowLayout();
      this.jMenu7 = new JMenu();
      this.jPanel5 = new JPanel();
      this.jPanel6 = new JPanel();
      this.jPanel7 = new JPanel();
      jCombo = new JComboBox(vect);
      this.flowLayout2 = new FlowLayout();
      this.borderLayout7 = new BorderLayout();
      this.jPanel4 = new JPanel();
      this.borderLayout4 = new BorderLayout();
      this.jPanel1 = new JPanel();
      this.jPanel8 = new JPanel();
      jLabelImagen = new JLabel();
      save = new JLabel();
      jTexto = new JTextField();
      jTextoDim = new JTextField();
      jTextoTime = new JTextField();
      texto = new String[num];
      textoDim = new String[num];
      textoTime = new String[num];
      icon_joc = null;
      save_joc = null;
      this.m = new FrameInfo();
      this.setSize(new Dimension(500, 225));
      this.jPanelCentral.setLayout(this.borderLayout1);
      this.jButton1.setPreferredSize(new Dimension(120, 17));
      this.jButton1.setText(this.about[idiom]);
      this.jButton1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jButton1_actionPerformed(e);
         }
      });
      jButton2.setPreferredSize(new Dimension(90, 17));
      jButton2.setText(this.exit[idiom]);
      jButton2.setVisible(false);
      jButton2.setEnabled(false);
      jButton2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jButton2_actionPerformed(e);
         }
      });

      for(int i = 0; i < num; ++i) {
         texto[i] = "";
         textoDim[i] = "";
         textoTime[i] = "";
      }

      this.jMenu1.setText(etiqueta[0][idiom]);
      this.jMenu6.setText(etiqueta[1][idiom]);
      this.jMenu5.setText(etiqueta[2][idiom]);
      this.jMenu4.setText(etiqueta[3][idiom]);
      this.jMenu3.setText(etiqueta[19][idiom]);
      this.jMenu7.setText(etiqueta[5][idiom]);
      this.jMenu8.setText(etiqueta[25][idiom]);
      jMenuItem1.setText(etiqueta[21][idiom]);
      jMenuItem17.setText(etiqueta[22][idiom]);
      jMenuItem2.setText(etiqueta[6][idiom]);
      jMenuItem3.setText(etiqueta[8][idiom]);
      jMenuItem4.setText(etiqueta[9][idiom]);
      jMenuItem5.setText(etiqueta[10][idiom]);
      jMenuItem6.setText(etiqueta[11][idiom]);
      jMenuItem7.setText(etiqueta[12][idiom]);
      jMenuItem8.setText(etiqueta[13][idiom]);
      jMenuItem9.setText(etiqueta[14][idiom]);
      jMenuItem10.setText(etiqueta[15][idiom]);
      jMenuItem11.setText(etiqueta[16][idiom]);
      jMenuItem12.setText(etiqueta[17][idiom]);
      jMenuItem13.setText(etiqueta[18][idiom]);
      jMenuItem14.setText(etiqueta[20][idiom]);
      jMenuItem15.setText(etiqueta[23][idiom]);
      jMenuItem16.setText(etiqueta[24][idiom]);
      jMenuItem18.setText(etiqueta[26][idiom]);
      jMenuItem19.setText(etiqueta[27][idiom]);
      jMenuItem2.setEnabled(false);
      jMenuItem3.setEnabled(false);
      jMenuItem4.setEnabled(false);
      jMenuItem5.setEnabled(false);
      jMenuItem6.setEnabled(false);
      jMenuItem7.setEnabled(false);
      jMenuItem8.setEnabled(false);
      jMenuItem9.setEnabled(false);
      jMenuItem10.setEnabled(false);
      jMenuItem11.setEnabled(false);
      jMenuItem12.setEnabled(false);
      jMenuItem13.setEnabled(false);
      jMenuItem14.setEnabled(false);
      jMenuItem15.setEnabled(false);
      jMenuItem16.setEnabled(false);
      jMenuItem19.setEnabled(false);
      jCombo.setEnabled(false);
      jCombo.setMaximumSize(new Dimension(130, 26));
      jCombo.setMinimumSize(new Dimension(130, 26));
      jCombo.setPreferredSize(new Dimension(130, 26));
      jCombo.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jCombo_actionPerformed(e);
         }
      });
      jMenuItem1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem1_actionPerformed(e);
         }
      });
      jMenuItem2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem2_actionPerformed(e);
         }
      });
      jMenuItem3.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem3_actionPerformed(e);
         }
      });
      jMenuItem4.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem4_actionPerformed(e);
         }
      });
      jMenuItem5.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem5_actionPerformed(e);
         }
      });
      jMenuItem6.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem6_actionPerformed(e);
         }
      });
      jMenuItem7.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem7_actionPerformed(e);
         }
      });
      jMenuItem8.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem8_actionPerformed(e);
         }
      });
      jMenuItem9.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem9_actionPerformed(e);
         }
      });
      jMenuItem10.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem10_actionPerformed(e);
         }
      });
      jMenuItem11.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem11_actionPerformed(e);
         }
      });
      jMenuItem12.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem12_actionPerformed(e);
         }
      });
      jMenuItem13.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem13_actionPerformed(e);
         }
      });
      jMenuItem14.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem14_actionPerformed(e);
         }
      });
      jMenuItem15.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem15_actionPerformed(e);
         }
      });
      jMenuItem16.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem16_actionPerformed(e);
         }
      });
      jMenuItem17.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem17_actionPerformed(e);
         }
      });
      jMenuItem18.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem18_actionPerformed(e);
         }
      });
      jMenuItem19.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptFou.this.jMenuItem19_actionPerformed(e);
         }
      });
      this.jPanelS.setLayout(this.borderLayout5);
      this.jPanelC.setLayout(this.borderLayout7);
      this.jPanelN.setLayout(this.borderLayout3);
      this.jPanelN.setMinimumSize(new Dimension(10, 20));
      this.jPanelN.setPreferredSize(new Dimension(10, 20));
      jPanelTexto.setLayout(this.borderLayout4);
      this.jPanelS.setMinimumSize(new Dimension(169, 70));
      this.jPanelS.setPreferredSize(new Dimension(225, 70));
      this.jPanel2.setLayout(this.flowLayout1);
      this.flowLayout1.setAlignment(2);
      this.jPanel3.setPreferredSize(new Dimension(140, 40));
      this.jPanel3.setLayout(this.borderLayout6);
      this.jPanel7.setLayout(this.flowLayout2);
      this.flowLayout2.setAlignment(0);
      jPanelTexto.setBackground(Color.black);
      this.jPanel8.setBackground(Color.black);
      this.jPanel8.setPreferredSize(new Dimension(10, 50));
      this.jPanel1.setBackground(Color.black);
      this.jPanel1.setPreferredSize(new Dimension(10, 50));
      jLabelImagen.setBackground(Color.black);
      jLabelImagen.setFont(new Font("Dialog", 1, 14));
      jLabelImagen.setForeground(new Color(204, 204, 255));
      jLabelImagen.setPreferredSize(new Dimension(200, 50));
      jLabelImagen.setHorizontalAlignment(0);
      jLabelImagen.setText(titol0[idiom]);
      save.setForeground(UIManager.getColor("DesktopIcon.background"));
      save.setPreferredSize(new Dimension(150, 50));
      save.setHorizontalAlignment(0);
      jTexto.setEditable(false);
      jTexto.setBackground(Color.black);
      jTexto.setFont(new Font("Dialog", 1, 12));
      jTexto.setForeground(new Color(204, 204, 204));
      jTexto.setPreferredSize(new Dimension(250, 21));
      jTexto.setText("");
      jTextoDim.setEditable(false);
      jTextoDim.setBackground(Color.black);
      jTextoDim.setFont(new Font("Dialog", 1, 12));
      jTextoDim.setForeground(new Color(204, 204, 204));
      jTextoDim.setPreferredSize(new Dimension(100, 21));
      jTextoDim.setText("");
      jTextoDim.setHorizontalAlignment(4);
      jTextoTime.setEditable(false);
      jTextoTime.setBackground(Color.black);
      jTextoTime.setFont(new Font("Dialog", 1, 12));
      jTextoTime.setForeground(new Color(204, 204, 204));
      jTextoTime.setPreferredSize(new Dimension(100, 21));
      jTextoTime.setText("");
      jTextoTime.setHorizontalAlignment(4);
      this.getContentPane().add(this.jPanelCentral, "Center");
      this.jPanelCentral.add(this.jPanelN, "North");
      this.jPanelCentral.add(this.jPanelS, "South");
      this.jPanelCentral.add(this.jPanelO, "West");
      this.jPanelCentral.add(this.jPanelC, "Center");
      this.jPanelCentral.add(this.jPanelE, "East");
      this.jPanelS.add(this.jPanel2, "South");
      this.jPanel2.add(this.jButton1, (Object)null);
      this.jPanel2.add(jButton2, (Object)null);
      this.jPanelS.add(this.jPanel3, "North");
      this.jPanel3.add(this.jPanel5, "East");
      this.jPanel3.add(this.jPanel6, "West");
      this.jPanel3.add(this.jPanel7, "Center");
      this.jPanel7.add(jCombo, (Object)null);
      this.jPanelN.add(this.jMenuBar1, "Center");
      this.jMenu1.add(this.jMenu7);
      this.jMenu7.add(jMenuItem1);
      this.jMenu7.add(jMenuItem17);
      this.jMenu1.add(jMenuItem2);
      this.jMenu6.add(jMenuItem3);
      this.jMenu6.add(jMenuItem4);
      this.jMenu6.add(jMenuItem5);
      this.jMenu6.add(jMenuItem6);
      this.jMenu5.add(jMenuItem7);
      this.jMenu5.add(jMenuItem8);
      this.jMenu5.add(jMenuItem9);
      this.jMenu5.add(jMenuItem10);
      this.jMenu5.add(jMenuItem11);
      this.jMenu5.add(jMenuItem12);
      this.jMenu4.add(jMenuItem13);
      this.jMenu3.add(jMenuItem14);
      this.jMenu3.add(jMenuItem15);
      this.jMenu3.add(jMenuItem16);
      this.jMenu8.add(jMenuItem18);
      this.jMenu8.add(jMenuItem19);
      this.jMenuBar1.add(this.jMenu1);
      this.jMenuBar1.add(this.jMenu6);
      this.jMenuBar1.add(this.jMenu5);
      this.jMenuBar1.add(this.jMenu4);
      this.jMenuBar1.add(this.jMenu3);
      this.jMenuBar1.add(this.jMenu8);
      this.jPanelC.add(jPanelTexto, "Center");
      jPanelTexto.add(this.jPanel1, "Center");
      jPanelTexto.add(this.jPanel8, "North");
      this.jPanel8.add(jLabelImagen, (Object)null);
      this.jPanelC.add(this.jPanel4, "North");
      this.jPanel8.add(save, (Object)null);
      this.jPanel1.add(jTexto, (Object)null);
      this.jPanel1.add(jTextoDim, (Object)null);
      this.jPanel1.add(jTextoTime, (Object)null);
   }

   public void start() {
   }

   public void stop() {
   }

   public void destroy() {
      for(int i = 0; i < Holog.maxNumImagen + 1; ++i) {
         Holog.controlImagen[i] = false;
      }

      Holog.imagen = new OptImagen[Holog.maxNumImagen + 1];
      Holog.nmin = 0;
      Holog.numImagen = 0;
      Holog.numOperacio = 0;
      jTexto.setText("");
      jTextoDim.setText("");
      jTextoTime.setText("");
      hab = false;
   }

   public String getAppletInfo() {
      return "Applet Information";
   }

   public String[][] getParameterInfo() {
      String[][] pinfo = new String[][]{{"lang", "int", ""}};
      return pinfo;
   }

   public static void main(String[] args) {
      try {
         String dato = args[0].toUpperCase().intern();
         if (dato == "CA") {
            idiom = 0;
         } else if (dato == "ES") {
            idiom = 1;
         } else if (dato == "EN") {
            idiom = 2;
         } else {
            idiom = 0;
         }
      } catch (Exception var4) {
         idiom = 0;
      }

      OptFou applet = new OptFou();
      applet.isStandalone = true;
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(3);
      frame.setTitle("Òptica de Fourier");
      frame.getContentPane().add(applet, "Center");
      if (icon_joc != null) {
         frame.setIconImage(icon_joc.getImage());
      }

      applet.init();
      applet.start();
      frame.setSize(500, 225);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
      frame.setVisible(true);
      frame.setResizable(false);
      jButton2.setVisible(true);
      jButton2.setEnabled(true);
   }

   void jButton1_actionPerformed(ActionEvent e) {
      Frame f = new Frame();
      Object[] options = new Object[]{aceptar[idiom]};
      ImageIcon icon_joc = null;

      try {
         String st_icon = "jocon.jpg";
         URL url_icon = this.getClass().getResource(st_icon);
         icon_joc = new ImageIcon(url_icon, "Òptica de Fourier");
      } catch (Exception var7) {
         System.out.println("No carga icono");
      }

      JOptionPane hola = new JOptionPane(this.rollo[idiom], 1, -1, (Icon)null, options);
      hola.setIcon(icon_joc);
      JDialog dialog = hola.createDialog(f, this.about[idiom]);
      dialog.setResizable(false);
      dialog.setVisible(true);
      this.repaint();
   }

   void jButton2_actionPerformed(ActionEvent e) {
      try {
         System.exit(0);
      } catch (Exception var3) {
         System.out.println("Error al salir");
      }

   }

   static void habilita() {
      jMenuItem2.setEnabled(true);
      jMenuItem3.setEnabled(true);
      jMenuItem4.setEnabled(true);
      jMenuItem5.setEnabled(true);
      jMenuItem6.setEnabled(true);
      jMenuItem13.setEnabled(true);
      jMenuItem14.setEnabled(true);
      jMenuItem7.setEnabled(true);
      jMenuItem8.setEnabled(true);
      jMenuItem9.setEnabled(true);
      jMenuItem10.setEnabled(true);
      jMenuItem11.setEnabled(true);
      jMenuItem12.setEnabled(true);
      jMenuItem15.setEnabled(true);
      jMenuItem16.setEnabled(true);
      jCombo.setEnabled(true);
      hab = true;
   }

   void jCombo_actionPerformed(ActionEvent e) {
      int n = jCombo.getSelectedIndex();

      try {
         Holog.imagen[ult_n].setVisible(false);
         Holog.imagen[n].setVisible(true);
         if (salvado[n]) {
            save.setText(salvat[idiom]);
            save.setIcon(save_joc);
         } else {
            save.setText("");
            save.setIcon((Icon)null);
         }

         jTexto.setText(" " + texto[n]);
         jTextoDim.setText(textoDim[n]);
         jTextoTime.setText(textoTime[n]);
         jLabelImagen.setText(titol0[idiom] + " " + n);
      } catch (Exception var4) {
         System.out.println("No existen");
      }

      ult_n = n;
   }

   void jMenuItem1_actionPerformed(ActionEvent e) {
      Holog var10000 = this.g;
      Holog.carregar();
   }

   void jMenuItem2_actionPerformed(ActionEvent e) {
      this.g.salvar();
   }

   void jMenuItem3_actionPerformed(ActionEvent e) {
      this.g.tf(etiqueta[8][idiom]);
   }

   void jMenuItem4_actionPerformed(ActionEvent e) {
      this.g.tf(etiqueta[9][idiom]);
   }

   void jMenuItem5_actionPerformed(ActionEvent e) {
      this.g.tf(etiqueta[10][idiom]);
   }

   void jMenuItem6_actionPerformed(ActionEvent e) {
      this.g.tf(etiqueta[11][idiom]);
   }

   void jMenuItem7_actionPerformed(ActionEvent e) {
      this.g.filtres(etiqueta[12][idiom]);
   }

   void jMenuItem8_actionPerformed(ActionEvent e) {
      this.g.filtres(etiqueta[13][idiom]);
   }

   void jMenuItem9_actionPerformed(ActionEvent e) {
      this.g.filtres(etiqueta[14][idiom]);
   }

   void jMenuItem10_actionPerformed(ActionEvent e) {
      this.g.filtres(etiqueta[15][idiom]);
   }

   void jMenuItem11_actionPerformed(ActionEvent e) {
      this.g.joint(etiqueta[16][idiom]);
   }

   void jMenuItem12_actionPerformed(ActionEvent e) {
      this.g.joint(etiqueta[17][idiom]);
   }

   void jMenuItem13_actionPerformed(ActionEvent e) {
      this.g.codBurck();
   }

   void jMenuItem14_actionPerformed(ActionEvent e) {
      this.g.zoomea();
   }

   void jMenuItem17_actionPerformed(ActionEvent e) {
      Selector sel = new Selector();
      sel.setVisible(true);
   }

   void jMenuItem18_actionPerformed(ActionEvent e) {
      this.m.abre();
   }

   void jMenuItem19_actionPerformed(ActionEvent e) {
      FrameTeoria ft = new FrameTeoria();
      ft.setVisible(true);
   }

   void jMenuItem15_actionPerformed(ActionEvent e) {
      String[] preguntas = new String[]{titol0[idiom] + " n. ?"};
      String[] respuestas = new String[]{"0"};
      JFrame f = new JFrame();
      if (icon_joc != null) {
         f.setIconImage(icon_joc.getImage());
      }

      if (Dialogos.optPreguntas(f, preguntas, respuestas, idiom)) {
         int resp;
         try {
            resp = Integer.valueOf(respuestas[0]);
         } catch (NumberFormatException var10) {
            Holog.errorNumber(1);
            return;
         }

         if (!Holog.controlImagen[resp]) {
            Frame fr = new Frame();
            Object[] options = new Object[]{aceptar[idiom]};
            JOptionPane hola = new JOptionPane(Holog.existe[1][idiom], 0, -1, (Icon)null, options);
            JDialog dialog = hola.createDialog(fr, "");
            dialog.setVisible(true);
         } else {
            jCombo.setSelectedIndex(resp);
            Visualitzacio v = new Visualitzacio(resp);
            if (icon_joc != null) {
               v.setIconImage(icon_joc.getImage());
            }

            v.setTitle(etiqueta[23][idiom]);
            System.out.println(resp);
            Visualitzacio.setTipo(0);
            v.setVisible(true);
            jMenuItem15.setEnabled(false);
            jMenuItem16.setEnabled(false);
         }
      }

   }

   void jMenuItem16_actionPerformed(ActionEvent e) {
      String[] preguntas = new String[]{titol0[idiom] + " n. ?"};
      String[] respuestas = new String[]{"0"};
      JFrame f = new JFrame();
      if (icon_joc != null) {
         f.setIconImage(icon_joc.getImage());
      }

      if (Dialogos.optPreguntas(f, preguntas, respuestas, idiom)) {
         int resp;
         try {
            resp = Integer.valueOf(respuestas[0]);
         } catch (NumberFormatException var10) {
            Holog.errorNumber(1);
            return;
         }

         if (!Holog.controlImagen[resp]) {
            Frame fr = new Frame();
            Object[] options = new Object[]{aceptar[idiom]};
            JOptionPane hola = new JOptionPane(Holog.existe[1][idiom], 0, -1, (Icon)null, options);
            JDialog dialog = hola.createDialog(fr, "");
            dialog.setVisible(true);
         } else {
            jCombo.setSelectedIndex(resp);
            Visualitzacio v = new Visualitzacio(resp);
            if (icon_joc != null) {
               v.setIconImage(icon_joc.getImage());
            }

            v.setTitle(etiqueta[24][idiom]);
            Visualitzacio.setTipo(1);
            v.setVisible(true);
            jMenuItem15.setEnabled(false);
            jMenuItem16.setEnabled(false);
         }
      }

   }
}
