package optfourier;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class FrameTeoria extends JFrame {
   ImageIcon icon_joc = null;
   String[] teoria = new String[]{" Resum teoria", " Resumen teoría", " Theory summary"};
   JTextPane jTextPane_info = new JTextPane();
   JScrollPane jScrollPane1 = new JScrollPane();
   JPanel jPanel1 = new JPanel();
   JButton jButton1 = new JButton();
   static String[] cierra = new String[]{"Tancar", "Cerrar", "Close"};

   public FrameTeoria() {
      try {
         String st_icon = "jocon.jpg";
         URL url_icon = this.getClass().getResource(st_icon);
         this.icon_joc = new ImageIcon(url_icon, "");
      } catch (Exception var4) {
         System.out.println("No carga icono");
      }

      if (this.icon_joc != null) {
         this.setIconImage(this.icon_joc.getImage());
      }

      this.setSize(new Dimension(750, 550));
      this.setTitle(this.teoria[OptFou.idiom]);
      this.setResizable(false);
      this.jTextPane_info.setMargin(new Insets(10, 10, 10, 10));

      try {
         this.jbInit();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.jPanel1.setPreferredSize(new Dimension(10, 30));
      this.jButton1.setPreferredSize(new Dimension(100, 17));
      this.jButton1.setText(cierra[OptFou.idiom]);
      this.jButton1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            FrameTeoria.this.jButton1_actionPerformed(e);
         }
      });
      this.jScrollPane1.setAutoscrolls(true);
      this.jScrollPane1.setBorder(BorderFactory.createEtchedBorder());
      this.jScrollPane1.setPreferredSize(new Dimension(24, 40));
      this.jTextPane_info.setBackground(new Color(204, 204, 204));
      System.out.println(this.jTextPane_info.getMargin().toString());
      this.jTextPane_info.setBorder(BorderFactory.createEtchedBorder());
      this.jTextPane_info.setMinimumSize(new Dimension(20, 37));
      this.jTextPane_info.setPreferredSize(new Dimension(20, 37));
      this.jTextPane_info.setEditable(false);
      this.carga_info();
      this.getContentPane().add(this.jScrollPane1, "Center");
      this.getContentPane().add(this.jPanel1, "South");
      this.jPanel1.add(this.jButton1, (Object)null);
      this.jScrollPane1.getViewport().add(this.jTextPane_info, (Object)null);
   }

   private void carga_info() {
      String s = null;
      URL info_page = null;

      try {
         if (OptFou.idiom == 1) {
            s = "DocA_YoungEs.htm";
         } else if (OptFou.idiom == 2) {
            s = "DocA_YoungEn.htm";
         } else {
            s = "DocA_YoungCa.htm";
         }

         info_page = this.getClass().getResource(s);
      } catch (Exception var5) {
         System.err.println("Couldn't create help URL: " + s);
      }

      try {
         this.jTextPane_info.setPage(info_page);
      } catch (IOException var4) {
         System.err.println("Attempted to read a bad URL: " + info_page);
      }

   }

   void jButton1_actionPerformed(ActionEvent e) {
      this.setVisible(false);
   }
}
