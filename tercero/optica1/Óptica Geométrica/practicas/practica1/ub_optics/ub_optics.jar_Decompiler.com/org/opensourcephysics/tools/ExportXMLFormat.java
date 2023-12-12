package org.opensourcephysics.tools;

import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import org.opensourcephysics.controls.XMLControlElement;

public class ExportXMLFormat implements ExportFormat {
   public String description() {
      return "XML";
   }

   public String extension() {
      return "xml";
   }

   public void export(File var1, List var2) {
      FileWriter var3 = null;

      try {
         var3 = new FileWriter(var1);
      } catch (IOException var7) {
         JOptionPane.showMessageDialog((Component)null, "An error occurred while saving your file. Please try again.", "Error", 0);
         return;
      }

      PrintWriter var4 = new PrintWriter(var3);
      Iterator var5 = var2.iterator();

      while(var5.hasNext()) {
         XMLControlElement var6 = new XMLControlElement(var5.next());
         var4.print(var6.toXML());
         var4.println();
      }

      var4.close();
   }
}
