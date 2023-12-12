package difraccion;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

public class Dispersion extends Applet {
   private boolean isStandalone = false;
   int lang;

   public String getParameter(String key, String def) {
      return this.isStandalone ? System.getProperty(key, def) : (this.getParameter(key) != null ? this.getParameter(key) : def);
   }

   public void init() {
      try {
         this.lang = Integer.parseInt(this.getParameter("idioma", "0"));
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

   public String getAppletInfo() {
      return "Información del applet";
   }

   public String[][] getParameterInfo() {
      String[][] pinfo = new String[][]{{"idioma", "int", ""}};
      return pinfo;
   }

   public static void main(String[] args) {
      Dispersion applet = new Dispersion();
      applet.isStandalone = true;
      Frame frame = new Frame();
      frame.setTitle("Marco del applet");
      frame.add(applet, "Center");
      applet.init();
      applet.start();
      frame.setSize(750, 570);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
      frame.setVisible(true);
   }
}
