package org.opensourcephysics.controls;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.opensourcephysics.display.GUIUtils;
import org.opensourcephysics.display.OSPFrame;
import org.opensourcephysics.tools.FontSizer;

public abstract class ControlFrame extends OSPFrame implements Control {
   static final int MENU_SHORTCUT_KEY_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
   protected Object model;
   protected JMenu fileMenu;
   protected JMenu editMenu;
   protected JMenuItem readItem;
   protected JMenuItem clearItem;
   protected JMenuItem saveAsItem;
   protected JMenuItem copyItem;
   protected JMenuItem inspectItem;
   protected JMenuItem logToFileItem;
   protected JMenuItem sizeUpItem;
   protected JMenuItem sizeDownItem;
   protected OSPApplication ospApp;
   protected XMLControlElement xmlDefault;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$controls$OSPApplication;

   protected ControlFrame(String var1) {
      super(var1);
      this.createMenuBar();
      this.setName("controlFrame");
   }

   private void createMenuBar() {
      JMenuBar var1 = new JMenuBar();
      if (!OSPFrame.appletMode) {
         this.setJMenuBar(var1);
      }

      this.fileMenu = new JMenu("File");
      this.editMenu = new JMenu("Edit");
      var1.add(this.fileMenu);
      var1.add(this.editMenu);
      this.readItem = new JMenuItem("Load XML...");
      this.saveAsItem = new JMenuItem("Save XML...");
      this.inspectItem = new JMenuItem("Inspect XML...");
      this.clearItem = new JMenuItem("Clear XML");
      this.copyItem = new JMenuItem("Copy");
      this.fileMenu.add(this.readItem);
      this.fileMenu.add(this.saveAsItem);
      this.fileMenu.add(this.inspectItem);
      this.fileMenu.add(this.clearItem);
      this.editMenu.add(this.copyItem);
      this.copyItem.setAccelerator(KeyStroke.getKeyStroke(67, MENU_SHORTCUT_KEY_MASK));
      this.copyItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ControlFrame.this.copy();
         }
      });
      this.saveAsItem.setAccelerator(KeyStroke.getKeyStroke(83, MENU_SHORTCUT_KEY_MASK));
      this.saveAsItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ControlFrame.this.saveXML();
         }
      });
      this.inspectItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ControlFrame.this.inspectXML();
         }
      });
      this.readItem.setAccelerator(KeyStroke.getKeyStroke(76, MENU_SHORTCUT_KEY_MASK));
      this.readItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ControlFrame.this.loadXML((String)null);
         }
      });
      this.clearItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ControlFrame.this.xmlDefault = null;
         }
      });
      JMenu var2 = new JMenu();
      var2.setText("Display");
      var1.add(var2);
      this.sizeUpItem = new JMenuItem();
      this.sizeUpItem.setText("Increase Font Size");
      this.sizeUpItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            FontSizer.levelUp();
         }
      });
      var2.add(this.sizeUpItem);
      this.sizeDownItem = new JMenuItem();
      this.sizeDownItem.setText("Decrease Font Size");
      this.sizeDownItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            FontSizer.levelDown();
         }
      });
      var2.add(this.sizeDownItem);
      JMenu var3 = new JMenu("Help");
      var1.add(var3);
      JMenuItem var4 = new JMenuItem("About...");
      var4.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ControlFrame.showAboutDialog(ControlFrame.this);
         }
      });
      var3.add(var4);
      JMenuItem var5 = new JMenuItem("System...");
      var5.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ControlUtils.showSystemProperties(true);
         }
      });
      var3.add(var5);
      JMenuItem var6 = new JMenuItem("Display All Frames");
      var6.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            GUIUtils.showDrawingAndTableFrames();
         }
      });
      var3.add(var6);
      var3.addSeparator();
      JMenuItem var7 = new JMenuItem("Message Log...");
      var7.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            OSPLog.getOSPLog().setVisible(true);
         }
      });
      var3.add(var7);
      this.logToFileItem = new JCheckBoxMenuItem("Log to file.");
      this.logToFileItem.setSelected(false);
      this.logToFileItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            JCheckBoxMenuItem var2 = (JCheckBoxMenuItem)var1.getSource();
            OSPLog.getOSPLog().setLogToFile(var2.isSelected());
         }
      });
      var3.add(this.logToFileItem);
      this.validate();
   }

   public static void showAboutDialog(Component var0) {
      String var1 = "OSP Library " + ControlUtils.version + " released " + ControlUtils.releaseDate + "\n" + "Open Source Physics Project \n" + "www.opensourcephysics.org";
      JOptionPane.showMessageDialog(var0, var1, "About Open Source Physics", 1);
   }

   public void save() {
      ControlUtils.saveToFile(this, this);
   }

   public void readParameters() {
      ControlUtils.loadParameters(this, this);
   }

   public void copy() {
      Clipboard var1 = Toolkit.getDefaultToolkit().getSystemClipboard();
      StringSelection var2 = new StringSelection(this.toString());
      var1.setContents(var2, var2);
   }

   public void saveXML() {
      JFileChooser var1 = OSPFrame.getChooser();
      if (var1 != null) {
         String var2 = var1.getDialogTitle();
         var1.setDialogTitle("Save XML Data");
         int var3 = var1.showSaveDialog((Component)null);
         var1.setDialogTitle(var2);
         if (var3 == 0) {
            File var4 = var1.getSelectedFile();
            if (var4.exists()) {
               int var5 = JOptionPane.showConfirmDialog((Component)null, "Replace existing " + var4.getName() + "?", "Replace File", 1);
               if (var5 != 0) {
                  return;
               }
            }

            OSPFrame.chooserDir = var1.getCurrentDirectory().toString();
            String var8 = var4.getAbsolutePath();
            if (var8 == null || var8.trim().equals("")) {
               return;
            }

            int var6 = var8.toLowerCase().lastIndexOf(".xml");
            if (var6 != var8.length() - 4) {
               var8 = var8 + ".xml";
            }

            XMLControlElement var7 = new XMLControlElement(this.getOSPApp());
            var7.write(var8);
         }

      }
   }

   public void loadXML(String[] var1) {
      if (var1 != null) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            this.loadXML(var1[var2]);
         }
      }

   }

   public void loadXML(String var1) {
      if (var1 != null && !var1.trim().equals("")) {
         XMLControlElement var2 = new XMLControlElement(var1);
         if ((class$org$opensourcephysics$controls$OSPApplication == null ? (class$org$opensourcephysics$controls$OSPApplication = class$("org.opensourcephysics.controls.OSPApplication")) : class$org$opensourcephysics$controls$OSPApplication).isAssignableFrom(var2.getObjectClass())) {
            this.xmlDefault = var2;
            this.xmlDefault.loadObject(this.getOSPApp());
         } else {
            JOptionPane.showMessageDialog(this, "\"" + var1 + "\" is for " + var2.getObjectClass() + ".", "Incorrect XML Object Type", 2);
         }

      } else {
         this.loadXML();
      }
   }

   public void loadXML() {
      JFileChooser var1 = OSPFrame.getChooser();
      if (var1 != null) {
         String var2 = var1.getDialogTitle();
         var1.setDialogTitle("Load XML Data");
         int var3 = var1.showOpenDialog((Component)null);
         var1.setDialogTitle(var2);
         if (var3 == 0) {
            OSPFrame.chooserDir = var1.getCurrentDirectory().toString();
            String var4 = var1.getSelectedFile().getAbsolutePath();
            this.loadXML(var4);
         }

      }
   }

   public void inspectXML() {
      XMLControlElement var1 = new XMLControlElement(this.getOSPApp());
      XMLTreePanel var2 = new XMLTreePanel(var1);
      JDialog var3 = new JDialog((Frame)null, true);
      var3.setContentPane(var2);
      var3.setSize(new Dimension(600, 300));
      var3.setVisible(true);
   }

   protected OSPApplication getOSPApp() {
      if (this.ospApp == null) {
         this.ospApp = new OSPApplication(this, this.model);
      }

      return this.ospApp;
   }

   // $FF: synthetic method
   public abstract void calculationDone(String var1);

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   // $FF: synthetic method
   public abstract void clearMessages();

   // $FF: synthetic method
   public abstract void clearValues();

   // $FF: synthetic method
   public abstract boolean getBoolean(String var1);

   // $FF: synthetic method
   public abstract double getDouble(String var1);

   // $FF: synthetic method
   public abstract int getInt(String var1);

   // $FF: synthetic method
   public abstract Object getObject(String var1);

   // $FF: synthetic method
   public abstract Collection getPropertyNames();

   // $FF: synthetic method
   public abstract String getString(String var1);

   // $FF: synthetic method
   public abstract void print(String var1);

   // $FF: synthetic method
   public abstract void println(String var1);

   // $FF: synthetic method
   public abstract void println();

   // $FF: synthetic method
   public abstract void setLockValues(boolean var1);

   // $FF: synthetic method
   public abstract void setValue(String var1, Object var2);

   // $FF: synthetic method
   public abstract void setValue(String var1, double var2);

   // $FF: synthetic method
   public abstract void setValue(String var1, int var2);

   // $FF: synthetic method
   public abstract void setValue(String var1, boolean var2);
}
