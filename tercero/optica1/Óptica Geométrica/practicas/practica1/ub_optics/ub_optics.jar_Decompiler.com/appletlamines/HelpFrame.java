package appletlamines;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class HelpFrame extends JFrame {
   private JPanel jPanel1 = new JPanel();
   private JScrollPane jScrollPane1 = new JScrollPane();
   private JTextPane jTextPane1 = new JTextPane();
   URL info_page;
   private BorderLayout borderLayout1 = new BorderLayout();

   public HelpFrame() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void carga_info(int lengua) {
      String s = null;

      try {
         if (lengua == 0) {
            s = "Doc_FilmCa.html";
         } else if (lengua == 2) {
            s = "Doc_FilmEn.html";
         } else {
            s = "Doc_FilmEs.html";
         }

         this.info_page = this.getClass().getResource(s);
      } catch (Exception var5) {
         System.err.println("Couldn't create help URL: " + s);
      }

      try {
         this.jTextPane1.setPage(this.info_page);
      } catch (IOException var4) {
         System.err.println("Attempted to read a bad URL: " + this.info_page);
      }

   }

   public boolean postEvent(Event evt) {
      return true;
   }

   public Font getFont() {
      Font F = new Font("SansSerif", 0, 14);
      return F;
   }

   private void jbInit() throws Exception {
      this.jPanel1.setFont(new Font("SansSerif", 0, 14));
      this.jPanel1.setMaximumSize(new Dimension(800, 600));
      this.jPanel1.setMinimumSize(new Dimension(800, 600));
      this.jPanel1.setPreferredSize(new Dimension(800, 600));
      this.jPanel1.setLayout(this.borderLayout1);
      this.jTextPane1.setBackground(new Color(204, 204, 204));
      this.jTextPane1.setFont(new Font("SansSerif", 0, 14));
      this.jTextPane1.setMaximumSize(new Dimension(1000000, 1000000));
      this.jTextPane1.setMinimumSize(new Dimension(1, 1));
      this.jTextPane1.setPreferredSize(new Dimension(700, 500));
      this.jTextPane1.setEditable(false);
      this.jScrollPane1.setFont(new Font("SansSerif", 0, 14));
      this.jScrollPane1.setMaximumSize(new Dimension(100000, 100000));
      this.jScrollPane1.setMinimumSize(new Dimension(1, 1));
      this.jScrollPane1.setPreferredSize(new Dimension(1, 1));
      this.addWindowListener(new WindowAdapter() {
         public void windowOpened(WindowEvent e) {
            HelpFrame.this.this_windowOpened(e);
         }
      });
      this.setFont(new Font("SansSerif", 0, 14));
      this.getContentPane().add(this.jPanel1, "Center");
      this.jPanel1.add(this.jScrollPane1, "Center");
      this.jScrollPane1.getViewport().add(this.jTextPane1, (Object)null);
   }

   void this_windowOpened(WindowEvent e) {
      this.carga_info(Lamines.lang);
   }
}
