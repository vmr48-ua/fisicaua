package org.opensourcephysics.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import org.opensourcephysics.controls.OSPLog;
import org.opensourcephysics.controls.XMLControlElement;
import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.display.OSPFrame;
import org.opensourcephysics.display2d.GridData;

public class ExportTool implements Tool, PropertyChangeListener {
   static ExportTool TOOL;
   static JFileChooser fc;
   static String exportExtension = "txt";
   static Hashtable formats = new Hashtable();
   JCheckBox[] checkBoxes;
   String exportName = "default";
   // $FF: synthetic field
   static Class class$org$opensourcephysics$display$Dataset;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$display2d$GridData;

   public ExportTool() {
      fc.addPropertyChangeListener(this);
   }

   public void propertyChange(PropertyChangeEvent var1) {
      FileFilter var2 = fc.getFileFilter();
      if (var2 != null) {
         ExportFormat var3 = (ExportFormat)formats.get(var2.getDescription());
         if (var3 != null && !exportExtension.equals(var3.extension())) {
            exportExtension = var3.extension();
            fc.setSelectedFile(new File(this.exportName + '.' + exportExtension));
         }
      }
   }

   void buildAccessory(List var1) {
      this.checkBoxes = new JCheckBox[var1.size()];
      JPanel var2 = new JPanel(new GridLayout(0, 1));

      for(int var3 = 0; var3 < var1.size(); ++var3) {
         String var4 = "Unknown" + var3;
         Color var5 = Color.BLACK;
         Object var6 = var1.get(var3);
         if (var6 instanceof Dataset) {
            Dataset var7 = (Dataset)var6;
            var4 = "Dataset" + var3;
            var5 = var7.getFillColor();
         } else if (var6 instanceof GridData) {
            var4 = "GridData" + var3;
         }

         this.checkBoxes[var3] = new JCheckBox(var4);
         this.checkBoxes[var3].setSelected(true);
         this.checkBoxes[var3].setForeground(var5);
         this.checkBoxes[var3].setBackground(Color.WHITE);
         var2.add(this.checkBoxes[var3]);
      }

      JScrollPane var8 = new JScrollPane(var2);
      var8.getViewport().setBackground(Color.WHITE);
      JPanel var9 = new JPanel(new BorderLayout());
      if (var1.size() == 0) {
         var9.add(new JLabel("No Data"), "North");
      } else {
         var9.add(new JLabel("Exportable Data"), "North");
      }

      var9.add(var8, "Center");
      var9.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
      fc.setAccessory(var9);
   }

   static void setChooserFormats() {
      fc.resetChoosableFileFilters();
      fc.setAcceptAllFileFilterUsed(false);
      Enumeration var0 = formats.keys();

      while(var0.hasMoreElements()) {
         final String var1 = (String)var0.nextElement();
         fc.addChoosableFileFilter(new FileFilter() {
            public boolean accept(File var1x) {
               return true;
            }

            public String getDescription() {
               return var1;
            }
         });
      }

   }

   List getDataObjects(XMLControlElement var1) {
      List var2 = var1.getObjects(class$org$opensourcephysics$display$Dataset == null ? (class$org$opensourcephysics$display$Dataset = class$("org.opensourcephysics.display.Dataset")) : class$org$opensourcephysics$display$Dataset);
      var2.addAll(var1.getObjects(class$org$opensourcephysics$display2d$GridData == null ? (class$org$opensourcephysics$display2d$GridData = class$("org.opensourcephysics.display2d.GridData")) : class$org$opensourcephysics$display2d$GridData));
      return var2;
   }

   List filterDataObjects(List var1) {
      Vector var2 = new Vector();

      for(int var3 = 0; var3 < var1.size(); ++var3) {
         if (this.checkBoxes[var3].isSelected()) {
            var2.add(var1.get(var3));
         }
      }

      return var2;
   }

   public static void registerFormat(ExportFormat var0) {
      formats.put(var0.description(), var0);
   }

   public void send(Job var1, Tool var2) throws RemoteException {
      XMLControlElement var3 = new XMLControlElement();

      try {
         var3.readXML(var1.getXML());
      } catch (RemoteException var8) {
      }

      OSPLog.finest(var3.toXML());
      List var4 = this.getDataObjects(var3);
      this.buildAccessory(var4);
      fc.setSelectedFile(new File(this.exportName + '.' + exportExtension));
      int var5 = fc.showSaveDialog((Component)null);
      if (var5 == 0) {
         File var6 = fc.getSelectedFile();
         if (var6.exists()) {
            int var7 = JOptionPane.showConfirmDialog((Component)null, "Replace existing " + var6.getName() + "?", "Replace File", 1);
            if (var7 != 0) {
               return;
            }
         }

         String var9 = fc.getFileFilter().getDescription();
         ((ExportFormat)formats.get(var9)).export(var6, this.filterDataObjects(var4));
         if (var6.getName().endsWith(exportExtension)) {
            this.exportName = var6.getName().substring(0, var6.getName().length() - 1 - exportExtension.length());
         }
      }

   }

   public static ExportTool getTool() {
      if (TOOL == null) {
         TOOL = new ExportTool();
         Toolbox.addTool("ExportTool", TOOL);
      }

      return TOOL;
   }

   static {
      registerFormat(new ExportGnuplotFormat());
      registerFormat(new ExportXMLFormat());
      Object var0 = UIManager.put("FileChooser.filesOfTypeLabelText", "File Format:");
      fc = new JFileChooser(OSPFrame.chooserDir);
      UIManager.put("FileChooser.filesOfTypeLabelText", var0);
      fc.setDialogType(1);
      fc.setDialogTitle("Export Data");
      fc.setApproveButtonText("Export");
      setChooserFormats();
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
