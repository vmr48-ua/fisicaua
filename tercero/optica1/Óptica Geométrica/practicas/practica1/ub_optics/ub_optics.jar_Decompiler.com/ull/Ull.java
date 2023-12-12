package ull;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Ull extends Applet {
   String[] titol = new String[]{"MODELITZACIÓ DE L'ULL", "MODELIZACIÓN DEL OJO", "EYE MODELIZATION"};
   String[][] etiqPane = new String[][]{{"Formació d'imatges", "Formación de imágenes", "Image formation"}, {"Traçat de raigs", "Trazado de rayos", "Ray tracing"}, {"Resum teoria", "Resumen teoría", "Theory summary"}};
   String[] sortir = new String[]{"Sortir", "Salir", "Exit"};
   String[] about = new String[]{"En quant a", "Acerca de", "About"};
   static String[] aceptar = new String[]{"Acceptar", "Aceptar", "OK"};
   String[] rollo = new String[]{"Applet de Modelització de l'ull v 0.1 \nGrup d'Innovació Docent en Òptica Física i Fotònica\n Universitat de Barcelona, 2003 \nLa utilització d'aquest programa està sota una llicència de Creative Commons \nVeure condicions en www.ub.edu/javaoptics", "Applet de Modelización del ojo v 0.1\nGrup d'Innovació Docent en Òptica Física i Fotònica\n Universitat de Barcelona, 2003 \nLa utilización de este programa está bajo una licencia de Creative Commons \nVer condiciones en www.ub.edu/javaoptics", "Eye modelization Applet v 0.1\nGrup d'Innovació Docent en Òptica Física i Fotònica\n Universitat de Barcelona, 2003 \nThe use of this program is under a Creative Commons license \nSee conditions in www.ub.edu/javaoptics"};
   String[][] labelSlider = new String[][]{{"Diòptries", "Dioptrías", "Dioptres"}, {"Miopia", "Miopía", "Myopia"}, {"Hipermetropia", "Hipermetropía", "Hyper myopia"}, {"astigmatisme", "astigmatismo", "astigmatism"}, {"Distància ull-objecte (m)", "Distancia ojo-objeto (m)", "Eye-object distance (m)"}};
   String[][] labelImagen = new String[][]{{"Imatge original", "Imagen original", "Original image"}, {"Imatge calculada", "Imagen calculada", "Calculated image"}, {"Imatge corregida"}, {"Imagen corregida"}, {"Rectified image"}};
   static String[] nombre = new String[]{"tirant.jpg", "quijote.jpg", "hamlet.jpg"};
   private boolean isStandalone = false;
   JPanel jPanelBase = new JPanel();
   BorderLayout borderLayout1 = new BorderLayout();
   BorderLayout borderLayout2 = new BorderLayout();
   JPanel jPanelNorte = new JPanel();
   JPanel jPanelCentro = new JPanel();
   JPanel jPanelSur = new JPanel();
   BorderLayout borderLayout3 = new BorderLayout();
   JTabbedPane jTabbedPaneCentro = new JTabbedPane();
   JPanel jPanelImatges = new JPanel();
   JPanel jPanelInfo = new JPanel();
   static int lang;
   JLabel jLabel1 = new JLabel();
   JButton jButton1 = new JButton();
   JButton jButton2 = new JButton();
   BorderLayout borderLayout4 = new BorderLayout();
   JPanel jPanelVista = new JPanel();
   static JPanel jPanelIm = new JPanel();
   JPanel jPanelCorr = new JPanel();
   static JPanel jPanelRes = new JPanel();
   FlowLayout layoutSur = new FlowLayout();
   static PanelImagen panIm;
   static PanelImagen panIm2;
   static PanelImagen panIm3;
   BorderLayout borderLayout5 = new BorderLayout();
   JPanel jPanelEsq = new JPanel();
   VentanaSliders ventana;
   BorderLayout borderLayout6 = new BorderLayout();
   JPanel jPanelEsqUll = new JPanel();
   JLabel jLabelIm0 = new JLabel();
   JLabel jLabelImCalc = new JLabel();
   JLabel jLabelImCorr = new JLabel();
   BorderLayout borderLayout7 = new BorderLayout();
   BorderLayout borderLayout8 = new BorderLayout();
   GridLayout gridLayout1 = new GridLayout();
   static EsqUll esqUll;
   BorderLayout borderLayout9 = new BorderLayout();
   static Image img;
   ImageIcon icon_joc;

   public String getParameter(String key, String def) {
      return this.isStandalone ? System.getProperty(key, def) : (this.getParameter(key) != null ? this.getParameter(key) : def);
   }

   public void init() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setLayout(this.borderLayout1);
      this.jPanelBase.setLayout(this.borderLayout2);
      this.jPanelCentro.setLayout(this.borderLayout3);
      this.jLabel1.setForeground(Color.gray);
      this.jLabel1.setHorizontalAlignment(0);
      this.jLabel1.setHorizontalTextPosition(0);
      this.jLabel1.setText(this.titol[lang]);
      this.jButton1.setMaximumSize(new Dimension(34, 10));
      this.jButton1.setMinimumSize(new Dimension(34, 10));
      this.jButton1.setPreferredSize(new Dimension(120, 17));
      this.jButton1.setMnemonic('0');
      this.jButton1.setText(this.about[lang]);
      this.jButton1.addActionListener(new Ull_jButton1_actionAdapter(this));
      this.jButton2.setMaximumSize(new Dimension(34, 10));
      this.jButton2.setMinimumSize(new Dimension(34, 10));
      this.jButton2.setPreferredSize(new Dimension(120, 17));
      this.jButton2.setHorizontalAlignment(0);
      this.jButton2.setHorizontalTextPosition(11);
      this.jButton2.setText(this.sortir[lang]);
      this.jButton2.addActionListener(new Ull_jButton2_actionAdapter(this));
      this.jPanelImatges.setLayout(this.borderLayout4);
      this.jPanelVista.setLayout(this.gridLayout1);
      this.jPanelCentro.setMinimumSize(new Dimension(775, 88));
      this.jPanelCentro.setPreferredSize(new Dimension(775, 88));
      jPanelIm.setMaximumSize(new Dimension(258, 265));
      jPanelIm.setMinimumSize(new Dimension(258, 265));
      jPanelIm.setPreferredSize(new Dimension(258, 265));
      jPanelIm.setLayout(this.borderLayout5);
      this.jPanelEsq.setMaximumSize(new Dimension(775, 190));
      this.jPanelEsq.setMinimumSize(new Dimension(775, 190));
      this.jPanelEsq.setPreferredSize(new Dimension(775, 190));
      this.jPanelEsq.setLayout(this.borderLayout6);
      this.jPanelEsq.setBackground(new Color(0, 0, 0));
      this.jPanelEsqUll.setBackground(Color.black);
      this.jPanelEsqUll.setMinimumSize(new Dimension(775, 190));
      this.jPanelEsqUll.setPreferredSize(new Dimension(775, 190));
      this.jPanelEsqUll.setLayout(this.borderLayout9);
      this.jPanelEsqUll.setBackground(new Color(0, 0, 0));
      esqUll = new EsqUll();
      this.jPanelEsqUll.add(esqUll, "Center");
      this.jLabelIm0.setHorizontalAlignment(0);
      this.jLabelIm0.setHorizontalTextPosition(0);
      this.jLabelIm0.setForeground(new Color(102, 102, 153));
      this.jLabelIm0.setVerticalTextPosition(0);
      this.jLabelIm0.setText(this.labelImagen[0][lang]);
      this.jLabelImCalc.setHorizontalAlignment(0);
      this.jLabelImCalc.setForeground(new Color(102, 102, 153));
      this.jLabelImCalc.setText(this.labelImagen[1][lang]);
      this.jLabelImCorr.setHorizontalAlignment(0);
      this.jLabelImCorr.setHorizontalTextPosition(11);
      this.jLabelImCorr.setForeground(new Color(102, 102, 153));
      this.jLabelImCorr.setText("PSF");
      jPanelRes.setLayout(this.borderLayout7);
      this.jPanelCorr.setLayout(this.borderLayout8);
      this.jPanelVista.setMaximumSize(new Dimension(775, 265));
      this.jPanelVista.setMinimumSize(new Dimension(775, 265));
      this.jPanelVista.setPreferredSize(new Dimension(775, 265));
      jPanelRes.setMaximumSize(new Dimension(258, 265));
      jPanelRes.setMinimumSize(new Dimension(258, 265));
      jPanelRes.setPreferredSize(new Dimension(258, 265));
      this.jPanelCorr.setMaximumSize(new Dimension(258, 265));
      this.jPanelCorr.setMinimumSize(new Dimension(258, 265));
      this.jPanelCorr.setPreferredSize(new Dimension(258, 265));
      this.add(this.jPanelBase, "Center");
      this.jPanelBase.add(this.jPanelNorte, "North");
      this.jPanelNorte.add(this.jLabel1, (Object)null);
      this.jPanelBase.add(this.jPanelCentro, "Center");
      this.layoutSur.setAlignment(2);
      this.jPanelSur.setLayout(this.layoutSur);
      this.jPanelBase.add(this.jPanelSur, "South");
      this.jPanelSur.add(this.jButton1, (Object)null);
      this.jPanelSur.add(this.jButton2, (Object)null);
      this.jPanelCentro.add(this.jTabbedPaneCentro, "Center");
      this.jTabbedPaneCentro.add(this.jPanelImatges, this.etiqPane[0][lang]);
      this.jPanelImatges.add(this.jPanelVista, "Center");
      String nom_imagen = "arbre.jpg";
      URL url_imagen = this.getClass().getResource(nom_imagen);
      System.out.println("La url es" + url_imagen.toString());
      img = Toolkit.getDefaultToolkit().createImage(url_imagen);
      panIm = new PanelImagen(img);
      jPanelIm.add(panIm, "Center");
      jPanelIm.add(this.jLabelIm0, "South");
      jPanelIm.repaint();
      panIm2 = new PanelImagen(img);
      jPanelRes.add(panIm2, "Center");
      jPanelRes.add(this.jLabelImCalc, "South");
      nom_imagen = "psf0.jpg";
      url_imagen = this.getClass().getResource(nom_imagen);
      System.out.println("La url es" + url_imagen.toString());
      img = Toolkit.getDefaultToolkit().createImage(url_imagen);
      panIm3 = new PanelImagen(img);
      this.jPanelCorr.add(panIm3, "Center");
      this.jPanelCorr.add(this.jLabelImCorr, "South");
      this.jPanelVista.add(jPanelIm, (Object)null);
      this.jPanelVista.add(jPanelRes, (Object)null);
      this.jPanelVista.add(this.jPanelCorr, (Object)null);
      this.jPanelImatges.add(this.jPanelEsq, "South");
      this.jPanelEsq.add(this.jPanelEsqUll, "Center");
      this.ventana = new VentanaSliders();
      this.repaint();
   }

   public void start() {
   }

   public void stop() {
   }

   public void destroy() {
   }

   public String getAppletInfo() {
      return "Información del applet";
   }

   public String[][] getParameterInfo() {
      return (String[][])null;
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

      Ull applet = new Ull();
      applet.isStandalone = true;
      Frame frame = new Frame();
      frame.setTitle("Eye applet");
      frame.add(applet, "Center");
      applet.init();
      applet.start();
      frame.setSize(775, 570);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
      frame.setVisible(true);
   }

   void jButton2_actionPerformed(ActionEvent e) {
      System.exit(0);
   }

   void jButton1_actionPerformed(ActionEvent e) {
      Frame f = new Frame();
      Object[] options = new Object[]{aceptar[lang]};
      ImageIcon icon_joc = null;

      try {
         String st_icon = "jocon.jpg";
         URL url_icon = this.getClass().getResource(st_icon);
         icon_joc = new ImageIcon(url_icon, "Ull");
      } catch (Exception var7) {
         System.out.println("No carga icono");
      }

      JOptionPane hola = new JOptionPane(this.rollo[lang], 1, -1, icon_joc, options);
      JDialog dialog = hola.createDialog(f, this.about[lang]);
      dialog.setVisible(true);
   }
}
