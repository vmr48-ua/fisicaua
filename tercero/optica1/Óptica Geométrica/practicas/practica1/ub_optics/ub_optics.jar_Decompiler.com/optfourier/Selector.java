package optfourier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class Selector extends JFrame {
   String[][] data = new String[][]{{"Quadrat", "Escletxa Vertical", "Escletxa Horitzontal", "Cercle", "Escena"}, {"Cuadrado", "Rendija Vertical", "Rendija Horizontal", "Círculo", "Escena"}, {"Square", "Vertical Slot", "Horizontal Slot", "Circle", "Scene"}};
   BorderLayout borderLayout1 = new BorderLayout();
   JPanel jPanel1 = new JPanel();
   JPanel jPanel2 = new JPanel();
   JButton jButton1 = new JButton();
   JButton jButton2 = new JButton();
   static String nombre;
   BorderLayout borderLayout2 = new BorderLayout();
   JPanel jPanel3 = new JPanel();
   JLabel jLabel1 = new JLabel();
   FlowLayout flowLayout1 = new FlowLayout();
   JPanel jPanel7 = new JPanel();
   JList list;
   JScrollPane scrollPane;
   String[] carga;
   String[] sel;
   TitledBorder titledBorder1;
   SimpleDateFormat hms;
   BorderLayout borderLayout3;
   JPanel jPanel4;
   JPanel jPanel5;
   JPanel jPanel6;
   static ImageIcon icon_joc = null;

   public Selector() {
      this.list = new JList(this.data[OptFou.idiom]);
      this.scrollPane = new JScrollPane(this.list);
      this.carga = new String[]{"Selecciona imatge", "Selecciona imagen", "Choose File"};
      this.sel = new String[]{"Selector", "Selector", "File Chooser"};
      this.hms = new SimpleDateFormat("HH:mm:ss");
      this.borderLayout3 = new BorderLayout();
      this.jPanel4 = new JPanel();
      this.jPanel5 = new JPanel();
      this.jPanel6 = new JPanel();
      this.setSize(250, 300);

      try {
         String st_icon = "jocon.jpg";
         URL url_icon = this.getClass().getResource(st_icon);
         icon_joc = new ImageIcon(url_icon, "Òptica de Fourier");
      } catch (Exception var4) {
         System.out.println("No carga icono");
      }

      if (icon_joc != null) {
         this.setIconImage(icon_joc.getImage());
      }

      try {
         this.jbInit();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.titledBorder1 = new TitledBorder("");
      this.getContentPane().setLayout(this.borderLayout1);
      this.setTitle(this.sel[OptFou.idiom]);
      this.jButton1.setMaximumSize(new Dimension(73, 27));
      this.jButton1.setMinimumSize(new Dimension(73, 27));
      this.jButton1.setPreferredSize(new Dimension(73, 27));
      this.jButton1.setText("OK");
      this.jButton1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Selector.this.jButton1_actionPerformed(e);
         }
      });
      this.jButton2.setText("Cancel");
      this.jButton2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Selector.this.jButton2_actionPerformed(e);
         }
      });
      this.jPanel1.setLayout(this.borderLayout2);
      this.jPanel3.setPreferredSize(new Dimension(10, 50));
      this.jPanel3.setBackground(new Color(204, 204, 204));
      this.jPanel3.setLayout(this.flowLayout1);
      this.jLabel1.setFont(new Font("Dialog", 1, 16));
      this.jLabel1.setForeground(new Color(0, 0, 0));
      this.jLabel1.setHorizontalAlignment(0);
      this.jLabel1.setHorizontalTextPosition(0);
      this.jLabel1.setText("jLabel1");
      this.flowLayout1.setVgap(15);
      this.setResizable(false);
      this.list.setBackground(new Color(204, 204, 204));
      this.list.setOpaque(false);
      this.list.setSelectedIndex(0);
      this.list.setSelectionMode(0);
      this.jPanel7.setBackground(new Color(204, 204, 204));
      this.jPanel7.setLayout(this.borderLayout3);
      this.jPanel2.setBackground(new Color(204, 204, 204));
      this.jPanel2.setPreferredSize(new Dimension(161, 50));
      this.jPanel6.setBackground(new Color(204, 204, 204));
      this.jPanel6.setPreferredSize(new Dimension(25, 10));
      this.jPanel5.setBackground(new Color(204, 204, 204));
      this.jPanel5.setPreferredSize(new Dimension(25, 10));
      this.jPanel4.setBackground(new Color(204, 204, 204));
      this.list.setBackground(Color.white);
      this.list.setForeground(Color.black);
      this.scrollPane.setHorizontalScrollBarPolicy(31);
      this.scrollPane.setViewportBorder(BorderFactory.createEtchedBorder());
      this.scrollPane.getViewport().setBackground(Color.white);
      this.scrollPane.setForeground(UIManager.getColor("CheckBox.background"));
      this.getContentPane().add(this.jPanel1, "Center");
      this.jPanel1.add(this.jPanel3, "North");
      this.jPanel3.add(this.jLabel1, (Object)null);
      this.jPanel1.add(this.jPanel7, "Center");
      this.jPanel7.add(this.scrollPane, "Center");
      this.jPanel7.add(this.jPanel4, "South");
      this.jPanel7.add(this.jPanel5, "West");
      this.jPanel7.add(this.jPanel6, "East");
      this.getContentPane().add(this.jPanel2, "South");
      this.jPanel2.add(this.jButton1, (Object)null);
      this.jPanel2.add(this.jButton2, (Object)null);
      this.jLabel1.setText(this.carga[OptFou.idiom]);
   }

   void jButton1_actionPerformed(ActionEvent e) {
      nombre = this.list.getSelectedValue().toString();
      Holog.generar(nombre);
      this.setVisible(false);
   }

   void jButton2_actionPerformed(ActionEvent e) {
      this.setVisible(false);
   }
}
