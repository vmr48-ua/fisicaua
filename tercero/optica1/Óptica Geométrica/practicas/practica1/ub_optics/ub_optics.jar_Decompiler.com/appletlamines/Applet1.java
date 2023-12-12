package appletlamines;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

public class Applet1 extends Applet {
   private boolean isStandalone = false;
   String var0;

   public String getParameter(String key, String def) {
      return this.isStandalone ? System.getProperty(key, def) : (this.getParameter(key) != null ? this.getParameter(key) : def);
   }

   public void init() {
      try {
         this.var0 = this.getParameter("param0", "");
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
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
      String[][] pinfo = new String[][]{{"param0", "String", "Idioma"}};
      return pinfo;
   }

   public static void main(String[] args) {
      Applet1 applet = new Applet1();
      applet.isStandalone = true;
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
      frame.setTitle("Applet Frame");
      frame.add(applet, "Center");
      applet.init();
      applet.start();
      frame.setSize(750, 570);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
      frame.setVisible(true);
   }
}
