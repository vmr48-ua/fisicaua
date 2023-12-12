package org.opensourcephysics.controls;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.Properties;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import org.opensourcephysics.display.OSPFrame;
import org.opensourcephysics.display.TextFrame;

public class ControlUtils {
   static String version = "1.0.2";
   static String releaseDate = "May 1, 2006";
   static DecimalFormat format2 = new DecimalFormat("#0.00");
   static DecimalFormat format3 = new DecimalFormat("#0.000");
   static DecimalFormat format4 = new DecimalFormat("#0.0000");
   static DecimalFormat format_E2 = new DecimalFormat("0.00E0");
   static DecimalFormat format_E3 = new DecimalFormat("0.000E0");
   static DecimalFormat format_E4 = new DecimalFormat("0.0000E0");
   protected static JFileChooser chooser;
   private static TextFrame sysPropFrame;

   public static String f2(double var0) {
      return format2.format(var0);
   }

   public static String f3(double var0) {
      return format3.format(var0);
   }

   public static String e2(double var0) {
      return format_E2.format(var0);
   }

   public static String e3(double var0) {
      return format_E3.format(var0);
   }

   public static String e4(double var0) {
      return format_E4.format(var0);
   }

   public static String f4(double var0) {
      return format4.format(var0);
   }

   private ControlUtils() {
   }

   public static JFrame showSystemProperties(boolean var0) {
      if (sysPropFrame == null) {
         sysPropFrame = new TextFrame((String)null, (Class)null);
         sysPropFrame.getTextPane().setText(getSystemProperties());
         sysPropFrame.setSize(300, 400);
      }

      sysPropFrame.setVisible(var0);
      return sysPropFrame;
   }

   public static String getSystemProperties() {
      StringBuffer var0 = new StringBuffer();

      try {
         Properties var1 = System.getProperties();
         int var6 = 0;
         Enumeration var7 = var1.propertyNames();

         while(var7.hasMoreElements()) {
            String var4 = (String)var7.nextElement();
            ++var6;
            var0.append(var6 + ". " + var4 + " = " + var1.getProperty(var4) + "\n");
         }
      } catch (Exception var5) {
         String[] var2 = new String[]{"java.version", "java.vendor", "java.class.version", "os.name", "os.arch", "os.version", "file.separator", "path.separator", "line.separator"};

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var0.append(var3 + ". " + var2[var3] + " = " + System.getProperty(var2[var3]) + "\n");
         }
      }

      return var0.toString();
   }

   public static void loadParameters(Control var0, Component var1) {
      JFileChooser var2 = new JFileChooser(new File(OSPFrame.chooserDir));
      int var3 = var2.showOpenDialog(var1);
      if (var3 == 0) {
         try {
            BufferedReader var4 = new BufferedReader(new FileReader(var2.getSelectedFile()));
            readFile(var0, var4);
            var4.close();
         } catch (Exception var5) {
            System.err.println(var5.getMessage());
         }
      }

   }

   public static void saveToFile(Object var0, Component var1) {
      JFileChooser var2 = new JFileChooser(new File(OSPFrame.chooserDir));
      int var3 = var2.showSaveDialog(var1);
      if (var3 == 0) {
         File var4 = var2.getSelectedFile();
         if (var4.exists()) {
            int var5 = JOptionPane.showConfirmDialog(var1, "A file named " + var4.getName() + " already exists. Are you sure you want to replace it?", "Warning", 1);
            if (var5 != 0) {
               return;
            }
         }

         try {
            FileWriter var8 = new FileWriter(var4);
            PrintWriter var6 = new PrintWriter(var8);
            var6.print(var0.toString());
            var6.close();
         } catch (IOException var7) {
            JOptionPane.showMessageDialog(var1, "An error occurred while saving your file. Please try again.", "Error", 0);
         }

      }
   }

   public static void saveXML(Object var0) {
      int var1 = getXMLFileChooser().showSaveDialog((Component)null);
      if (var1 == 0) {
         File var2 = chooser.getSelectedFile();
         if (var2.exists()) {
            int var3 = JOptionPane.showConfirmDialog((Component)null, "Replace existing " + var2.getName() + "?", "Replace File", 1);
            if (var3 != 0) {
               return;
            }
         }

         String var6 = XML.getRelativePath(var2.getAbsolutePath());
         if (var6 == null || var6.trim().equals("")) {
            return;
         }

         int var4 = var6.toLowerCase().lastIndexOf(".xml");
         if (var4 != var6.length() - 4) {
            var6 = var6 + ".xml";
         }

         XMLControlElement var5 = new XMLControlElement(var0);
         var5.write(var6);
      }

   }

   public static JFileChooser getXMLFileChooser() {
      if (chooser != null) {
         return chooser;
      } else {
         chooser = new JFileChooser(new File(OSPFrame.chooserDir));
         FileFilter var0 = new FileFilter() {
            public boolean accept(File var1) {
               if (var1 == null) {
                  return false;
               } else if (var1.isDirectory()) {
                  return true;
               } else {
                  String var2 = null;
                  String var3 = var1.getName();
                  int var4 = var3.lastIndexOf(46);
                  if (var4 > 0 && var4 < var3.length() - 1) {
                     var2 = var3.substring(var4 + 1).toLowerCase();
                  }

                  return var2 != null && (var2.equals("xml") || var2.equals("osp"));
               }
            }

            public String getDescription() {
               return "XML files";
            }
         };
         chooser.addChoosableFileFilter(var0);
         return chooser;
      }
   }

   private static void readFile(Control var0, BufferedReader var1) throws IOException {
      for(String var2 = var1.readLine(); var2 != null; var2 = var1.readLine()) {
         String var3 = parseParameter(var2);
         if (var3 != null && !var3.equals("")) {
            var0.setValue(var3, parseValue(var2));
         }
      }

   }

   private static String parseParameter(String var0) {
      int var1 = var0.indexOf(61);
      return var1 < 1 ? null : var0.substring(0, var1).trim();
   }

   private static String parseValue(String var0) {
      int var1 = var0.indexOf(61);
      return var1 >= var0.length() - 1 ? "" : var0.substring(var1 + 1).trim();
   }
}
