package ull;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class VentanaSliders extends Frame {
   JPanel jPanelBase = new JPanel();
   BorderLayout borderLayout1 = new BorderLayout();
   JPanel jPanelBoton = new JPanel();
   JPanel jPanelSliders = new JPanel();
   JButton jButton1 = new JButton();
   String[] calcula = new String[]{"Calcular", "Calcular", "Calculate"};
   String[][] labelSlider = new String[][]{{"Diòptries", "Dioptrías", "Dioptres"}, {"Miopia", "Miopía", "Myopia"}, {"Hipermetropia", "Hipermetropía", "Hyper myopia"}, {"Astigmatisme", "Astigmatismo", "Astigmatism"}, {"Distància", "Distancia", "Eye-object"}, {"ull-objecte", "ojo-objeto", "distance"}, {"lent", "lente", "lens"}};
   String[] nom_click = new String[]{"click_ca.gif", "click_es.gif", "click_en.gif"};
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   JSlider jSliderAstX = new JSlider();
   JTextField jTextField2 = new JTextField();
   JSlider jSliderAstY = new JSlider();
   JLabel jLabel3 = new JLabel();
   JSlider jSliderZ = new JSlider();
   JLabel jLabel4 = new JLabel();
   JTextField jTextField3 = new JTextField();
   JTextField jTextField4 = new JTextField();
   static String[] inf = new String[]{"Infinit", "Infinito", "Infinite"};
   Hashtable tabla = new Hashtable();
   Hashtable tabla10 = new Hashtable();
   JLabel labelIcon;
   JPanel jPanel1 = new JPanel();
   JPanel jPanel2 = new JPanel();
   JPanel jPanel3 = new JPanel();
   JLabel jLabelX = new JLabel();
   JLabel jLabelY = new JLabel();
   JLabel jLabelXb = new JLabel();
   JLabel jLabelYb = new JLabel();
   double valor;
   int valorint;
   Conv conv = new Conv();
   Calculo calc = new Calculo();
   String[] error = new String[]{"Els camps han de ser números", "Los campos han de ser números", "Numbers are required in the fields"};
   ImageIcon icon_joc;
   Image img;
   static int numImagen = 0;
   JButton reset = new JButton();
   double astx;
   double asty;
   double miopia;
   double distz;
   static boolean ast = false;
   JRadioButton jRadioButtonAmet = new JRadioButton();
   JRadioButton jRadioButtonAst = new JRadioButton();
   ButtonGroup bg = new ButtonGroup();
   JLabel jLabel1 = new JLabel();
   JLabel jLabel2 = new JLabel();
   JButton correccio = new JButton();
   String[] correct = new String[]{"Corregir", "Corregir", "Correct"};
   JSlider correccioX = new JSlider();
   JSlider correccioY = new JSlider();
   boolean lents = false;
   JTextField jTextField5 = new JTextField();
   JTextField jTextField6 = new JTextField();
   JLabel labelLent = new JLabel();
   JLabel labelcorX = new JLabel();
   JLabel labelcorY = new JLabel();
   JLabel jLabel5 = new JLabel();
   JLabel jLabel6 = new JLabel();

   public VentanaSliders() throws HeadlessException {
      this.setSize(500, 325);

      try {
         try {
            String st_icon = "jocon.jpg";
            URL url_icon = this.getClass().getResource(st_icon);
            this.icon_joc = new ImageIcon(url_icon, "Ull");
         } catch (Exception var3) {
            System.out.println("No carga icono");
         }

         this.jbInit();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public void jbInit() {
      this.lents = false;
      this.setSize(500, 325);
      this.setResizable(false);
      this.setIconImage(this.icon_joc.getImage());
      this.jPanelSliders.setLayout(this.gridBagLayout1);
      this.jPanelBase.setMaximumSize(new Dimension(500, 325));
      this.jPanelBase.setMinimumSize(new Dimension(500, 325));
      this.jPanelBase.setPreferredSize(new Dimension(500, 325));
      this.jPanelBase.setLayout(this.borderLayout1);
      this.jPanelBoton.setMinimumSize(new Dimension(500, 30));
      this.jPanelBoton.setPreferredSize(new Dimension(500, 30));
      this.jPanelSliders.setMaximumSize(new Dimension(500, 275));
      this.jPanelSliders.setMinimumSize(new Dimension(500, 275));
      this.jPanelSliders.setPreferredSize(new Dimension(500, 275));
      this.jButton1.setAlignmentY(0.0F);
      this.jButton1.setHorizontalTextPosition(0);
      this.jButton1.setMargin(new Insets(0, 14, 0, 14));
      this.jButton1.setText(this.calcula[Ull.lang]);
      this.jButton1.addActionListener(new VentanaSliders_jButton1_actionAdapter(this));
      this.jTextField2.setMinimumSize(new Dimension(50, 20));
      this.jTextField2.setPreferredSize(new Dimension(50, 20));
      this.jTextField2.setText("0.0");
      this.jTextField2.setHorizontalAlignment(4);
      this.jTextField2.addKeyListener(new VentanaSliders_jTextField2_keyAdapter(this));
      this.jTextField3.setMinimumSize(new Dimension(50, 20));
      this.jTextField3.setPreferredSize(new Dimension(50, 20));
      this.jTextField3.setText("0.0");
      this.jTextField3.setHorizontalAlignment(4);
      this.jTextField3.addKeyListener(new VentanaSliders_jTextField3_keyAdapter(this));
      this.jTextField4.setMinimumSize(new Dimension(50, 20));
      this.jTextField4.setOpaque(true);
      this.jTextField4.setPreferredSize(new Dimension(50, 20));
      this.jTextField4.setText(inf[Ull.lang]);
      this.jTextField4.setHorizontalAlignment(4);
      this.jTextField4.addKeyListener(new VentanaSliders_jTextField4_keyAdapter(this));
      this.jTextField5.setMinimumSize(new Dimension(50, 20));
      this.jTextField5.setOpaque(true);
      this.jTextField5.setPreferredSize(new Dimension(50, 20));
      this.jTextField5.setText("0.0");
      this.jTextField5.setHorizontalAlignment(4);
      this.jTextField5.setEnabled(false);
      this.jTextField6.setMinimumSize(new Dimension(50, 20));
      this.jTextField6.setOpaque(true);
      this.jTextField6.setPreferredSize(new Dimension(50, 20));
      this.jTextField6.setText("0.0");
      this.jTextField6.setHorizontalAlignment(4);
      this.jTextField6.setEnabled(false);
      this.jLabel3.setMaximumSize(new Dimension(90, 20));
      this.jLabel3.setMinimumSize(new Dimension(90, 20));
      this.jLabel3.setOpaque(false);
      this.jLabel3.setPreferredSize(new Dimension(90, 20));
      this.jLabel3.setText(this.labelSlider[0][Ull.lang]);
      this.jLabel4.setMaximumSize(new Dimension(90, 20));
      this.jLabel4.setMinimumSize(new Dimension(90, 20));
      this.jLabel4.setPreferredSize(new Dimension(90, 20));
      this.jLabel4.setText(this.labelSlider[4][Ull.lang]);
      this.tabla10.put(new Integer(-1000), this.etiqTabla("-10"));
      this.tabla10.put(new Integer(-800), this.etiqTabla("-8"));
      this.tabla10.put(new Integer(-600), this.etiqTabla("-6"));
      this.tabla10.put(new Integer(-400), this.etiqTabla("-4"));
      this.tabla10.put(new Integer(-200), this.etiqTabla("-2"));
      this.tabla10.put(new Integer(0), this.etiqTabla("0"));
      this.tabla10.put(new Integer(200), this.etiqTabla("2"));
      this.tabla10.put(new Integer(400), this.etiqTabla("4"));
      this.tabla10.put(new Integer(600), this.etiqTabla("6"));
      this.tabla10.put(new Integer(800), this.etiqTabla("8"));
      this.tabla10.put(new Integer(1000), this.etiqTabla("10"));
      this.jSliderAstX.setMinimumSize(new Dimension(250, 35));
      this.jSliderAstX.setPreferredSize(new Dimension(250, 35));
      this.jSliderAstX.addMouseMotionListener(new VentanaSliders_jSliderAstX_mouseMotionAdapter(this));
      this.jSliderAstX.addMouseListener(new VentanaSliders_jSliderAstX_mouseAdapter(this));
      this.jSliderAstX.addKeyListener(new VentanaSliders_jSliderAstX_keyAdapter(this));
      this.jSliderAstX.setLabelTable(this.tabla10);
      this.jSliderAstX.setPaintLabels(true);
      this.jSliderAstY.setMinimumSize(new Dimension(250, 35));
      this.jSliderAstY.setPreferredSize(new Dimension(250, 35));
      this.jSliderAstY.addMouseMotionListener(new VentanaSliders_jSliderAstY_mouseMotionAdapter(this));
      this.jSliderAstY.addMouseListener(new VentanaSliders_jSliderAstY_mouseAdapter(this));
      this.jSliderAstY.addKeyListener(new VentanaSliders_jSliderAstY_keyAdapter(this));
      this.jSliderAstY.setLabelTable(this.tabla10);
      this.jSliderAstY.setPaintLabels(true);
      this.jSliderZ.setMinimumSize(new Dimension(250, 35));
      this.jSliderZ.setPreferredSize(new Dimension(250, 35));
      this.jSliderZ.addMouseMotionListener(new VentanaSliders_jSliderZ_mouseMotionAdapter(this));
      this.jSliderZ.addMouseListener(new VentanaSliders_jSliderZ_mouseAdapter(this));
      this.jSliderZ.addKeyListener(new VentanaSliders_jSliderZ_keyAdapter(this));
      this.jSliderZ.setPaintTicks(true);
      this.tabla.put(new Integer(0), this.etiqTabla("5"));
      this.tabla.put(new Integer(100), this.etiqTabla("25"));
      this.tabla.put(new Integer(200), this.etiqTabla("70"));
      this.tabla.put(new Integer(300), this.etiqTabla("500"));
      this.jSliderZ.setLabelTable(this.tabla);
      this.jSliderZ.setPaintLabels(true);
      this.correccioY.setMinimumSize(new Dimension(250, 35));
      this.correccioY.setPreferredSize(new Dimension(250, 35));
      this.correccioY.setLabelTable(this.tabla10);
      this.correccioY.setPaintLabels(true);
      this.correccioY.setEnabled(false);
      this.correccioX.setMinimumSize(new Dimension(250, 35));
      this.correccioX.setPreferredSize(new Dimension(250, 35));
      this.correccioX.setEnabled(false);
      this.correccioX.setLabelTable(this.tabla10);
      this.correccioX.setPaintLabels(true);
      this.jPanel1.setMinimumSize(new Dimension(25, 10));
      this.jPanel1.setPreferredSize(new Dimension(25, 10));
      this.jPanel3.setMaximumSize(new Dimension(32767, 32767));
      this.jPanel3.setMinimumSize(new Dimension(25, 10));
      this.jPanel3.setPreferredSize(new Dimension(25, 10));
      this.jLabelX.setText("X");
      this.jLabelY.setText("Y");
      this.jLabelXb.setText("X");
      this.jLabelYb.setText("Y");
      this.reset.setAlignmentY(0.0F);
      this.reset.setHorizontalTextPosition(0);
      this.reset.setMargin(new Insets(0, 14, 0, 14));
      this.reset.setText("Reset");
      this.correccio.setAlignmentY(0.0F);
      this.correccio.setHorizontalTextPosition(0);
      this.correccio.setMargin(new Insets(0, 14, 0, 14));
      this.correccio.setText(this.correct[Ull.lang]);
      this.correccio.addActionListener(new VentanaSliders_correccio_actionAdapter(this));
      this.jRadioButtonAmet.addActionListener(new VentanaSliders_jRadioButtonAmet_actionAdapter(this));
      this.jRadioButtonAst.addActionListener(new VentanaSliders_jRadioButtonAst_actionAdapter(this));
      this.jRadioButtonAmet.setMaximumSize(new Dimension(125, 21));
      this.jRadioButtonAmet.setMinimumSize(new Dimension(125, 21));
      this.jRadioButtonAmet.setPreferredSize(new Dimension(125, 21));
      this.jRadioButtonAst.setMaximumSize(new Dimension(125, 21));
      this.jRadioButtonAst.setMinimumSize(new Dimension(125, 21));
      this.jRadioButtonAst.setPreferredSize(new Dimension(125, 21));
      this.jLabel1.setMaximumSize(new Dimension(90, 20));
      this.jLabel1.setMinimumSize(new Dimension(90, 20));
      this.jLabel1.setPreferredSize(new Dimension(90, 20));
      this.jLabel1.setText(this.labelSlider[5][Ull.lang]);
      this.jLabel2.setText("(cm)");
      this.jLabel5.setText(this.labelSlider[0][Ull.lang]);
      this.jLabel6.setText(this.labelSlider[6][Ull.lang]);
      this.add(this.jPanelBase, "Center");
      this.jPanelBase.add(this.jPanelBoton, "South");
      this.jPanelBoton.add(this.jButton1, (Object)null);
      this.jPanelBoton.add(this.reset, (Object)null);
      this.jPanelBoton.add(this.correccio, (Object)null);
      this.jPanelBase.add(this.jPanelSliders, "East");
      this.jSliderAstX.setMajorTickSpacing(200);
      this.jSliderAstX.setMaximum(1000);
      this.jSliderAstX.setMinimum(-1000);
      this.jSliderAstX.setValue(0);
      this.jSliderAstX.setPaintTicks(true);
      this.jSliderAstY.setMajorTickSpacing(200);
      this.jSliderAstY.setPaintTicks(true);
      this.jSliderAstY.setValue(0);
      this.jSliderAstY.setMaximum(1000);
      this.jSliderAstY.setMinimum(-1000);
      this.jSliderZ.setPaintTicks(true);
      this.jSliderZ.setMaximum(301);
      this.jSliderZ.setMinimum(0);
      this.jSliderZ.setValue(301);
      this.jSliderZ.setMajorTickSpacing(25);
      this.correccioX.setMajorTickSpacing(200);
      this.correccioX.setMaximum(1000);
      this.correccioX.setMinimum(-1000);
      this.correccioX.setValue(0);
      this.correccioX.setPaintTicks(true);
      this.correccioY.setMajorTickSpacing(200);
      this.correccioY.setMaximum(1000);
      this.correccioY.setMinimum(-1000);
      this.correccioY.setValue(0);
      this.correccioY.setPaintTicks(true);
      ImageIcon icon_inf = null;

      try {
         String st_icon = "inf2.gif";
         URL url_icon = this.getClass().getResource(st_icon);
         icon_inf = new ImageIcon(url_icon, "Inf");
         this.labelIcon = new JLabel(icon_inf);
      } catch (Exception var4) {
         System.out.println("No carga icono");
         this.labelIcon = new JLabel(inf[Ull.lang]);
      }

      this.jRadioButtonAmet.setText(this.labelSlider[1][Ull.lang]);
      this.jRadioButtonAmet.setForeground(new Color(102, 102, 153));
      this.jRadioButtonAst.setText(this.labelSlider[3][Ull.lang]);
      this.jRadioButtonAst.setForeground(new Color(102, 102, 153));
      this.bg.add(this.jRadioButtonAmet);
      this.bg.add(this.jRadioButtonAst);
      this.jRadioButtonAmet.setSelected(true);
      this.jSliderAstX.setEnabled(false);
      this.jTextField2.setEnabled(false);
      this.jPanelSliders.add(this.jSliderAstX, new GridBagConstraints(0, 3, 2, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 25, 11));
      this.jPanelSliders.add(this.jTextField2, new GridBagConstraints(3, 3, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jSliderAstY, new GridBagConstraints(0, 2, 2, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 25, 11));
      this.jPanelSliders.add(this.jLabel3, new GridBagConstraints(4, 2, 1, 2, 0.0D, 0.0D, 10, 0, new Insets(0, 10, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jSliderZ, new GridBagConstraints(0, 4, 2, 3, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 25, 11));
      this.jPanelSliders.add(this.jLabel4, new GridBagConstraints(4, 5, 1, 1, 0.0D, 0.0D, 11, 0, new Insets(0, 10, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jTextField3, new GridBagConstraints(3, 2, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jTextField4, new GridBagConstraints(3, 4, 1, 3, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.jPanelSliders.add(this.labelIcon, new GridBagConstraints(2, 5, 1, 3, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jPanel1, new GridBagConstraints(2, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jPanel2, new GridBagConstraints(2, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jPanel3, new GridBagConstraints(4, 3, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jLabelX, new GridBagConstraints(4, 3, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jLabelY, new GridBagConstraints(4, 2, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jRadioButtonAmet, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 10, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jRadioButtonAst, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jLabel1, new GridBagConstraints(4, 5, 1, 1, 0.0D, 0.0D, 15, 0, new Insets(20, 10, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jLabel2, new GridBagConstraints(4, 5, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.jPanelSliders.add(this.correccioY, new GridBagConstraints(0, 7, 2, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 25, 11));
      this.jPanelSliders.add(this.correccioX, new GridBagConstraints(0, 9, 2, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 25, 11));
      this.jPanelSliders.add(this.jTextField5, new GridBagConstraints(3, 7, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jTextField6, new GridBagConstraints(3, 9, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jLabelXb, new GridBagConstraints(4, 9, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jLabelYb, new GridBagConstraints(4, 7, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jLabel5, new GridBagConstraints(4, 7, 1, 3, 0.0D, 0.0D, 17, 0, new Insets(-20, 10, 0, 0), 0, 0));
      this.jPanelSliders.add(this.jLabel6, new GridBagConstraints(4, 7, 1, 3, 0.0D, 0.0D, 17, 0, new Insets(20, 10, 0, 0), 0, 0));
      this.setVisible(true);
   }

   void jSliderAstX_keyTyped(KeyEvent e) {
      this.mueveSliderAstX();
   }

   void jSliderAstX_mouseClicked(MouseEvent e) {
      this.mueveSliderAstX();
   }

   void jSliderAstX_mouseDragged(MouseEvent e) {
      this.mueveSliderAstX();
   }

   void jSliderAstX_keyReleased(KeyEvent e) {
      this.mueveSliderAstX();
   }

   void jSliderAstY_keyTyped(KeyEvent e) {
      this.mueveSliderAstY();
   }

   void jSliderAstY_mouseClicked(MouseEvent e) {
      this.mueveSliderAstY();
   }

   void jSliderAstY_mouseDragged(MouseEvent e) {
      this.mueveSliderAstY();
   }

   void jSliderAstY_keyReleased(KeyEvent e) {
      this.mueveSliderAstY();
   }

   void jSliderZ_keyReleased(KeyEvent e) {
      this.ponTextoEnSliderZ();
   }

   void jSliderZ_keyTyped(KeyEvent e) {
      this.ponTextoEnSliderZ();
   }

   void jSliderZ_mouseClicked(MouseEvent e) {
      this.ponTextoEnSliderZ();
   }

   void jSliderZ_mouseDragged(MouseEvent e) {
      this.ponTextoEnSliderZ();
   }

   void jTextField2_keyTyped(KeyEvent e) {
      if (e.getKeyChar() == '\n') {
         try {
            this.valor = Double.valueOf(this.jTextField2.getText());
            if (this.valor >= -1000.0D && this.valor <= 1000.0D) {
               this.valorint = (int)(this.valor * 100.0D);
               this.jSliderAstX.setValue(this.valorint);
            }

            if (this.valor > 1000.0D) {
               this.jSliderAstX.setValue(1000);
            }

            if (this.valor < -1000.0D) {
               this.jSliderAstX.setValue(-1000);
            }

            this.mueveSliderAstX();
         } catch (Exception var3) {
            this.jSliderAstX.setValue(0);
            this.mueveSliderAstX();
         }
      }

   }

   void jTextField3_keyTyped(KeyEvent e) {
      if (e.getKeyChar() == '\n') {
         try {
            this.valor = Double.valueOf(this.jTextField3.getText());
            if (this.valor >= -1000.0D && this.valor <= 1000.0D) {
               this.valorint = (int)(this.valor * 100.0D);
               this.jSliderAstY.setValue(this.valorint);
            }

            if (this.valor > 1000.0D) {
               this.jSliderAstY.setValue(1000);
            }

            if (this.valor < -1000.0D) {
               this.jSliderAstY.setValue(-1000);
            }

            this.mueveSliderAstY();
         } catch (Exception var3) {
            this.jSliderAstY.setValue(0);
            this.mueveSliderAstY();
         }
      }

   }

   void jTextField4_keyTyped(KeyEvent e) {
      if (e.getKeyChar() == '\n') {
         this.ponSliderDondeTextoZ();
      }

   }

   void mueveSliderAstX() {
      this.jTextField2.setText(Double.toString((double)this.jSliderAstX.getValue() / 100.0D));
      ast = true;
      URL url_imagen = this.getClass().getResource(this.nom_click[Ull.lang]);
      this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
      Ull.panIm2.setImage(this.img);

      try {
         this.astx = Double.valueOf(this.jTextField2.getText());
         this.asty = Double.valueOf(this.jTextField3.getText());
         if (this.jTextField4.getText().equals(inf[Ull.lang])) {
            this.distz = 501.0D;
            numImagen = 2;
         } else {
            this.distz = Double.valueOf(this.jTextField4.getText());
         }

         this.calc.calcula(this.astx, this.asty, this.distz);
         Ull.esqUll.repaint();
      } catch (Exception var8) {
         try {
            String st_icon = "jocon.jpg";
            URL url_icon = this.getClass().getResource(st_icon);
            this.icon_joc = new ImageIcon(url_icon, "Ull");
         } catch (Exception var7) {
            System.out.println("No carga icono");
         }

         Frame fr = new Frame();
         fr.setIconImage(this.icon_joc.getImage());
         Object[] options = new Object[]{Ull.aceptar[Ull.lang]};
         JOptionPane hola = new JOptionPane(this.error[Ull.lang], 0, -1, (Icon)null, options);
         JDialog dialog = hola.createDialog(fr, "");
         dialog.setVisible(true);
         var8.printStackTrace();
      }

   }

   void mueveSliderAstY() {
      this.jTextField3.setText(Double.toString((double)this.jSliderAstY.getValue() / 100.0D));
      URL url_imagen = this.getClass().getResource(this.nom_click[Ull.lang]);
      this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
      Ull.panIm2.setImage(this.img);

      try {
         if (this.jRadioButtonAst.isSelected()) {
            ast = true;
            this.astx = Double.valueOf(this.jTextField2.getText());
            this.asty = Double.valueOf(this.jTextField3.getText());
            if (this.jTextField4.getText().equals(inf[Ull.lang])) {
               this.distz = 501.0D;
               this.calc.calcula(this.astx, this.asty, this.distz);
               Ull.esqUll.repaint();
               numImagen = 2;
            } else {
               this.distz = Double.valueOf(this.jTextField4.getText());
               this.calc.calcula(this.astx, this.asty, this.distz);
               Ull.esqUll.repaint();
            }
         } else {
            this.anulaAst();
            this.miopia = Double.valueOf(this.jTextField3.getText());
            if (this.miopia <= 0.0D) {
               this.jRadioButtonAmet.setText(this.labelSlider[1][Ull.lang]);
            } else {
               this.jRadioButtonAmet.setText(this.labelSlider[2][Ull.lang]);
            }

            if (this.jTextField4.getText().equals(inf[Ull.lang])) {
               this.distz = 501.0D;
               this.calc.calcula(this.miopia, this.distz);
               Ull.esqUll.repaint();
               numImagen = 2;
            } else {
               this.distz = Double.valueOf(this.jTextField4.getText());
            }

            this.calc.calcula(this.miopia, this.distz);
            Ull.esqUll.repaint();
         }
      } catch (Exception var8) {
         try {
            String st_icon = "jocon.jpg";
            URL url_icon = this.getClass().getResource(st_icon);
            this.icon_joc = new ImageIcon(url_icon, "Ull");
         } catch (Exception var7) {
            System.out.println("No carga icono");
         }

         Frame fr = new Frame();
         fr.setIconImage(this.icon_joc.getImage());
         Object[] options = new Object[]{Ull.aceptar[Ull.lang]};
         JOptionPane hola = new JOptionPane(this.error[Ull.lang], 0, -1, (Icon)null, options);
         JDialog dialog = hola.createDialog(fr, "");
         dialog.setVisible(true);
         var8.printStackTrace();
      }

   }

   void jButton1_actionPerformed(ActionEvent e) {
      try {
         this.miopia = Double.valueOf(this.jTextField3.getText());
         this.astx = Double.valueOf(this.jTextField2.getText());
         this.asty = Double.valueOf(this.jTextField3.getText());
         if (this.jTextField4.getText().equals(inf[Ull.lang])) {
            this.distz = 501.0D;
            numImagen = 2;
         } else {
            this.distz = Double.valueOf(this.jTextField4.getText());
         }

         if (!ast) {
            this.calc.calcula(this.miopia, this.distz);
            this.conv.convoluciona();
         } else {
            this.calc.calcula(this.astx, this.asty, this.distz);
            this.conv.convoluciona();
         }

         Ull.esqUll.repaint();
      } catch (Exception var8) {
         try {
            String st_icon = "jocon.jpg";
            URL url_icon = this.getClass().getResource(st_icon);
            this.icon_joc = new ImageIcon(url_icon, "Ull");
         } catch (Exception var7) {
            System.out.println("No carga icono");
         }

         Frame fr = new Frame();
         fr.setIconImage(this.icon_joc.getImage());
         Object[] options = new Object[]{Ull.aceptar[Ull.lang]};
         JOptionPane hola = new JOptionPane(this.error[Ull.lang], 0, -1, (Icon)null, options);
         JDialog dialog = hola.createDialog(fr, "");
         dialog.setVisible(true);
         var8.printStackTrace();
      }

   }

   void ponTextoEnSliderZ() {
      double valor = 0.0D;
      String nom_imagen;
      URL url_imagen;
      if (this.jSliderZ.getValue() <= 100) {
         valor = 5.0D + (double)this.jSliderZ.getValue() * 0.2D;
         valor *= 10.0D;
         this.valorint = (int)Math.round(valor);
         valor = (double)this.valorint / 10.0D;
         this.jTextField4.setText(Double.toString(valor));
         nom_imagen = Ull.nombre[Ull.lang];
         url_imagen = this.getClass().getResource(nom_imagen);
         this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
         Ull.panIm.setImage(this.img);
         url_imagen = this.getClass().getResource(this.nom_click[Ull.lang]);
         this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
         Ull.panIm2.setImage(this.img);
         numImagen = 0;
      }

      if (this.jSliderZ.getValue() >= 200) {
         valor = 4.3D * (double)this.jSliderZ.getValue() - 790.0D;
         valor *= 10.0D;
         this.valorint = (int)Math.round(valor);
         valor = (double)this.valorint / 10.0D;
         this.jTextField4.setText(Double.toString(valor));
         nom_imagen = "arbre.jpg";
         url_imagen = this.getClass().getResource(nom_imagen);
         this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
         Ull.panIm.setImage(this.img);
         url_imagen = this.getClass().getResource(this.nom_click[Ull.lang]);
         this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
         Ull.panIm2.setImage(this.img);
         numImagen = 2;
      }

      if (this.jSliderZ.getValue() > 100 && this.jSliderZ.getValue() < 200) {
         valor = 0.45D * (double)this.jSliderZ.getValue() - 20.0D;
         valor *= 10.0D;
         this.valorint = (int)Math.round(valor);
         valor = (double)this.valorint / 10.0D;
         this.jTextField4.setText(Double.toString(valor));
         nom_imagen = "monitor.jpg";
         url_imagen = this.getClass().getResource(nom_imagen);
         this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
         Ull.panIm.setImage(this.img);
         url_imagen = this.getClass().getResource(this.nom_click[Ull.lang]);
         this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
         Ull.panIm2.setImage(this.img);
         numImagen = 1;
      }

      if (this.jSliderZ.getValue() == 301) {
         this.jTextField4.setText(inf[Ull.lang]);
         nom_imagen = "arbre.jpg";
         url_imagen = this.getClass().getResource(nom_imagen);
         this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
         Ull.panIm.setImage(this.img);
         url_imagen = this.getClass().getResource(this.nom_click[Ull.lang]);
         this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
         Ull.panIm2.setImage(this.img);
         numImagen = 2;
      }

      try {
         this.miopia = Double.valueOf(this.jTextField3.getText());
         this.astx = Double.valueOf(this.jTextField2.getText());
         this.asty = Double.valueOf(this.jTextField3.getText());
         if (this.jTextField4.getText().equals(inf[Ull.lang])) {
            this.distz = 501.0D;
            numImagen = 2;
         } else {
            this.distz = Double.valueOf(this.jTextField4.getText());
         }

         if (!ast) {
            this.calc.calcula(this.miopia, this.distz);
         } else {
            this.calc.calcula(this.astx, this.asty, this.distz);
         }

         Ull.esqUll.repaint();
      } catch (Exception var9) {
         try {
            String st_icon = "jocon.jpg";
            URL url_icon = this.getClass().getResource(st_icon);
            this.icon_joc = new ImageIcon(url_icon, "Ull");
         } catch (Exception var8) {
            System.out.println("No carga icono");
         }

         Frame fr = new Frame();
         fr.setIconImage(this.icon_joc.getImage());
         Object[] options = new Object[]{Ull.aceptar[Ull.lang]};
         JOptionPane hola = new JOptionPane(this.error[Ull.lang], 0, -1, (Icon)null, options);
         JDialog dialog = hola.createDialog(fr, "");
         dialog.setVisible(true);
         var9.printStackTrace();
      }

   }

   void ponSliderDondeTextoZ() {
      try {
         if (this.jTextField4.getText().equalsIgnoreCase(inf[Ull.lang])) {
            this.jSliderZ.setValue(301);
         } else {
            this.valor = Double.valueOf(this.jTextField4.getText());
            String textinf = this.jTextField4.getText();
            String nom_imagen;
            URL url_imagen;
            if (this.valor < 5.0D) {
               this.jSliderZ.setValue(0);
               nom_imagen = Ull.nombre[Ull.lang];
               url_imagen = this.getClass().getResource(nom_imagen);
               this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
               Ull.panIm.setImage(this.img);
               url_imagen = this.getClass().getResource(this.nom_click[Ull.lang]);
               this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
               Ull.panIm2.setImage(this.img);
               numImagen = 0;
            }

            if (this.valor >= 5.0D && this.valor <= 25.0D) {
               this.valorint = (int)(this.valor * 5.0D - 25.0D);
               this.jSliderZ.setValue(this.valorint);
               nom_imagen = Ull.nombre[Ull.lang];
               url_imagen = this.getClass().getResource(nom_imagen);
               this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
               Ull.panIm.setImage(this.img);
               url_imagen = this.getClass().getResource(this.nom_click[Ull.lang]);
               this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
               Ull.panIm2.setImage(this.img);
               numImagen = 0;
            }

            if (this.valor > 25.0D && this.valor <= 70.0D) {
               this.valorint = (int)((20.0D * this.valor + 400.0D) / 9.0D);
               this.jSliderZ.setValue(this.valorint);
               nom_imagen = "monitor.jpg";
               url_imagen = this.getClass().getResource(nom_imagen);
               this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
               Ull.panIm.setImage(this.img);
               url_imagen = this.getClass().getResource(this.nom_click[Ull.lang]);
               this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
               Ull.panIm2.setImage(this.img);
               numImagen = 1;
            }

            if (this.valor > 70.0D && this.valor <= 500.0D) {
               this.valorint = (int)((this.valor * 10.0D + 7900.0D) / 43.0D);
               this.jSliderZ.setValue(this.valorint);
               nom_imagen = "arbre.jpg";
               url_imagen = this.getClass().getResource(nom_imagen);
               this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
               Ull.panIm.setImage(this.img);
               url_imagen = this.getClass().getResource(this.nom_click[Ull.lang]);
               this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
               Ull.panIm2.setImage(this.img);
               numImagen = 2;
            }

            if (this.valor > 500.0D) {
               this.jSliderZ.setValue(301);
               this.jTextField4.setText(inf[Ull.lang]);
               nom_imagen = "arbre.jpg";
               url_imagen = this.getClass().getResource(nom_imagen);
               this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
               Ull.panIm.setImage(this.img);
               url_imagen = this.getClass().getResource(this.nom_click[Ull.lang]);
               this.img = Toolkit.getDefaultToolkit().createImage(url_imagen);
               Ull.panIm2.setImage(this.img);
               numImagen = 2;
            }

            try {
               this.miopia = Double.valueOf(this.jTextField3.getText());
               this.astx = Double.valueOf(this.jTextField2.getText());
               this.asty = Double.valueOf(this.jTextField3.getText());
               if (this.jTextField4.getText().equals(inf[Ull.lang])) {
                  this.distz = 501.0D;
                  numImagen = 2;
               } else {
                  this.distz = Double.valueOf(this.jTextField4.getText());
               }

               if (!ast) {
                  this.calc.calcula(this.miopia, this.distz);
               } else {
                  this.calc.calcula(this.astx, this.asty, this.distz);
               }

               Ull.esqUll.repaint();
            } catch (Exception var8) {
               try {
                  String st_icon = "jocon.jpg";
                  URL url_icon = this.getClass().getResource(st_icon);
                  this.icon_joc = new ImageIcon(url_icon, "Ull");
               } catch (Exception var7) {
                  System.out.println("No carga icono");
               }

               Frame fr = new Frame();
               fr.setIconImage(this.icon_joc.getImage());
               Object[] options = new Object[]{Ull.aceptar[Ull.lang]};
               JOptionPane hola = new JOptionPane(this.error[Ull.lang], 0, -1, (Icon)null, options);
               JDialog dialog = hola.createDialog(fr, "");
               dialog.setVisible(true);
               var8.printStackTrace();
            }

            Ull.esqUll.repaint();
         }
      } catch (Exception var9) {
         this.jSliderZ.setValue(0);
      }

   }

   void anulaAst() {
      this.jTextField2.setText("0.0");
      this.jSliderAstX.setValue(0);
      this.jSliderAstX.setEnabled(false);
      this.jTextField2.setEnabled(false);
   }

   void reset_actionPerformed(ActionEvent e) {
      this.resetea();
      this.jRadioButtonAmet.setSelected(true);
   }

   void resetea() {
      ast = false;
      this.jSliderZ.setValue(301);
      this.jSliderAstX.setValue(0);
      this.jSliderAstY.setValue(0);
      this.jTextField2.setText("0.0");
      this.jTextField3.setText("0.0");
      this.jTextField4.setText(inf[Ull.lang]);
      this.jSliderAstX.setEnabled(false);
      this.jSliderZ.setEnabled(true);
      this.jTextField2.setEnabled(false);
      this.jRadioButtonAmet.setText(this.labelSlider[1][Ull.lang]);
      String url_string = "arbre.jpg";

      BufferedImage imagen;
      URL url_imagen;
      try {
         url_imagen = this.getClass().getResource(url_string);
         imagen = ImageIO.read(url_imagen);
         Ull.panIm.setImage(imagen);
         Ull.panIm.repaint();
         Ull.panIm2.setImage(imagen);
         Ull.panIm2.repaint();
      } catch (Exception var5) {
         System.err.println("Algo pasa con la imagen");
      }

      url_string = "psf0.jpg";

      try {
         url_imagen = this.getClass().getResource(url_string);
         imagen = ImageIO.read(url_imagen);
         Ull.panIm3.setImage(imagen);
         Ull.panIm3.repaint();
      } catch (Exception var4) {
         System.err.println("Algo pasa con la imagen");
      }

      this.calc.calcula(0.0D, 501.0D);
      Ull.esqUll.repaint();
   }

   JLabel etiqTabla(String num) {
      JLabel labelTabla = new JLabel(num);
      Font fuente = new Font("Dialog", 1, 10);
      labelTabla.setFont(fuente);
      return labelTabla;
   }

   void jRadioButtonAmet_actionPerformed(ActionEvent e) {
      this.resetea();
      ast = false;
      this.anulaAst();
   }

   void jRadioButtonAst_actionPerformed(ActionEvent e) {
      this.resetea();
      ast = true;
      this.distz = 501.0D;
      this.astx = 0.0D;
      this.asty = 0.0D;
      this.calc.calcula(this.astx, this.asty, this.distz);
      Ull.esqUll.repaint();
      this.jSliderAstX.setEnabled(true);
      this.jTextField2.setEnabled(true);
   }

   void correccio_actionPerformed(ActionEvent e) {
      if (!this.lents) {
         this.correccioY.setEnabled(true);
         this.jTextField5.setEnabled(true);
         if (ast) {
            this.correccioX.setEnabled(true);
            this.jTextField6.setEnabled(true);
         }

         this.lents = true;
      } else if (this.lents) {
         this.correccioX.setEnabled(false);
         this.correccioY.setEnabled(false);
         this.jTextField5.setEnabled(false);
         this.jTextField6.setEnabled(false);
         this.lents = false;
      }

   }
}
