package optfourier;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FrameInfo extends JFrame {
   String[] titol = new String[]{"RELACIÓ D'OPERACIONS REALITZADES:", "RELACIÓN DE OPERACIONES REALIZADAS:", "LIST OF DONE OPERATIONS:"};
   JScrollPane jScrollPane1 = new JScrollPane();
   static JTextArea info;
   JPanel jPanel1 = new JPanel();
   JButton jButton1 = new JButton();
   static String[] cierra = new String[]{"Tancar", "Cerrar", "Close"};
   ImageIcon icon_joc = null;

   public FrameInfo() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   void abre() {
      this.setSize(400, 300);
      this.setVisible(true);
   }

   private void jbInit() throws Exception {
      try {
         String st_icon = "jocon.jpg";
         URL url_icon = this.getClass().getResource(st_icon);
         this.icon_joc = new ImageIcon(url_icon);
      } catch (Exception var3) {
         System.out.println("No carga icono");
      }

      this.setIconImage(this.icon_joc.getImage());
      this.setTitle(OptFou.etiqueta[26][OptFou.idiom]);
      info = new JTextArea(this.titol[OptFou.idiom] + "\n\n", 1, 1);
      info.setSize(new Dimension(400, 300));
      info.setBackground(new Color(0, 0, 0));
      info.setForeground(new Color(204, 204, 204));
      info.setEditable(false);
      info.setFont(new Font("Dialog", 1, 14));
      this.jPanel1.setMinimumSize(new Dimension(30, 10));
      this.jButton1.setMaximumSize(new Dimension(100, 17));
      this.jButton1.setPreferredSize(new Dimension(100, 17));
      this.jButton1.setText(cierra[OptFou.idiom]);
      this.jButton1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            FrameInfo.this.jButton1_actionPerformed(e);
         }
      });
      this.getContentPane().add(this.jScrollPane1, "Center");
      this.getContentPane().add(this.jPanel1, "South");
      this.jPanel1.add(this.jButton1, (Object)null);
      this.jScrollPane1.getViewport().add(info, (Object)null);
   }

   protected void processWindowEvent(WindowEvent e) {
      if (e.getID() == 201) {
      }

      super.processWindowEvent(e);
   }

   void jButton1_actionPerformed(ActionEvent e) {
      this.setVisible(false);
   }
}
