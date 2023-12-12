package org.opensourcephysics.ejs.control;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.RootPaneContainer;
import javax.swing.event.SwingPropertyChangeSupport;
import org.opensourcephysics.controls.ControlFrame;
import org.opensourcephysics.controls.ControlUtils;
import org.opensourcephysics.controls.OSPApplication;
import org.opensourcephysics.controls.OSPLog;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControlElement;
import org.opensourcephysics.controls.XMLTreePanel;
import org.opensourcephysics.display.OSPFrame;
import org.opensourcephysics.tools.FontSizer;
import org.opensourcephysics.tools.LaunchNode;

public class EjsControlFrame extends ParsedEjsControl implements RootPaneContainer {
   static final int MENU_SHORTCUT_KEY_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
   OSPFrame frame;
   JFrame messageFrame;
   TextArea messageArea;
   Object model;
   JMenuBar menuBar;
   protected XMLControlElement xmlDefault;
   protected PropertyChangeSupport support;
   protected OSPApplication app;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$controls$OSPApplication;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$tools$LaunchNode;

   public EjsControlFrame(Object var1) {
      this(var1, "name=controlFrame;title=Control Frame;location=400,0;layout=border;exit=false; visible=true");
   }

   public EjsControlFrame(Object var1, String var2) {
      super(var1);
      this.frame = new OSPFrame() {
         public void render() {
            EjsControlFrame.this.render();
         }

         public void clearData() {
            EjsControlFrame.this.clearData();
         }

         public void clearDataAndRepaint() {
            EjsControlFrame.this.clearDataAndRepaint();
         }
      };
      this.messageFrame = new JFrame("Messages");
      this.messageArea = new TextArea(20, 20);
      this.menuBar = new JMenuBar();
      this.support = new SwingPropertyChangeSupport(this);
      this.model = var1;
      this.frame.setName("controlFrame");
      this.addObject(this.frame, "Frame", var2);
      this.frame.setJMenuBar(this.menuBar);
      JMenu var3 = new JMenu("File");
      this.menuBar.add(var3);
      JMenuItem var4 = new JMenuItem("Read");
      JMenuItem var5 = new JMenuItem("Save As...");
      JMenuItem var6 = new JMenuItem("Inspect");
      var3.add(var4);
      var3.add(var5);
      var3.add(var6);
      var4.setAccelerator(KeyStroke.getKeyStroke(82, MENU_SHORTCUT_KEY_MASK));
      var4.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            EjsControlFrame.this.loadXML((String)null);
            EjsControlFrame.this.support.firePropertyChange("xmlDefault", (Object)null, EjsControlFrame.this.xmlDefault);
            EjsControlFrame.this.frame.repaint();
         }
      });
      var5.setAccelerator(KeyStroke.getKeyStroke(83, MENU_SHORTCUT_KEY_MASK));
      var5.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            EjsControlFrame.this.saveXML();
         }
      });
      var6.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            EjsControlFrame.this.inspectXML();
         }
      });
      JMenu var7 = new JMenu();
      var7.setText("Display");
      this.menuBar.add(var7);
      JMenuItem var8 = new JMenuItem();
      var8.setText("Increase Font Size");
      var8.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            FontSizer.levelUp();
         }
      });
      var7.add(var8);
      JMenuItem var9 = new JMenuItem();
      var9.setText("Decrease Font Size");
      var9.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            FontSizer.levelDown();
         }
      });
      var7.add(var9);
      JMenu var10 = new JMenu("Help");
      this.menuBar.add(var10);
      JMenuItem var11 = new JMenuItem("About...");
      var11.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ControlFrame.showAboutDialog(EjsControlFrame.this.getFrame());
         }
      });
      var10.add(var11);
      JMenuItem var12 = new JMenuItem("System...");
      var12.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ControlUtils.showSystemProperties(true);
         }
      });
      var10.add(var12);
      var10.addSeparator();
      JCheckBoxMenuItem var13 = new JCheckBoxMenuItem("Log to file.");
      var13.setSelected(false);
      var13.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            JCheckBoxMenuItem var2 = (JCheckBoxMenuItem)var1.getSource();
            OSPLog.getOSPLog().setLogToFile(var2.isSelected());
         }
      });
      var10.add(var13);
      JMenuItem var14 = new JMenuItem("Message Log...");
      var14.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            OSPLog.getOSPLog().setVisible(true);
         }
      });
      var10.add(var14);
      this.menuBar.add(var10);
      if (OSPFrame.appletMode) {
         this.frame.setDefaultCloseOperation(1);
      }

      this.messageFrame.getContentPane().add(this.messageArea);
      this.messageFrame.setSize(300, 175);
   }

   public void addPropertyChangeListener(PropertyChangeListener var1) {
      this.support.addPropertyChangeListener(var1);
   }

   public void removePropertyChangeListener(PropertyChangeListener var1) {
      this.support.removePropertyChangeListener(var1);
   }

   public void println(String var1) {
      if (var1 != null) {
         this.messageArea.append(var1 + "\n");
         this.messageFrame.setVisible(true);
      }
   }

   public void println() {
      this.messageArea.append("\n");
      this.messageFrame.setVisible(true);
   }

   public void calculationDone(String var1) {
      if (var1 != null && !var1.trim().equals("")) {
         super.calculationDone(var1);
      }
   }

   public void print(String var1) {
      if (var1 != null) {
         this.messageArea.append(var1);
         this.messageFrame.setVisible(true);
      }
   }

   public void clearMessages() {
      this.messageArea.setText("");
   }

   public OSPFrame getFrame() {
      return this.frame;
   }

   public void render() {
   }

   public void clearData() {
   }

   public void clearDataAndRepaint() {
   }

   public Container getTopLevelAncestor() {
      return this.frame;
   }

   public JRootPane getRootPane() {
      return this.frame.getRootPane();
   }

   public Container getContentPane() {
      return this.frame.getContentPane();
   }

   public void setContentPane(Container var1) {
      this.frame.setContentPane(var1);
   }

   public JLayeredPane getLayeredPane() {
      return this.frame.getLayeredPane();
   }

   public void setLayeredPane(JLayeredPane var1) {
      this.frame.setLayeredPane(var1);
   }

   public Component getGlassPane() {
      return this.frame.getGlassPane();
   }

   public void setGlassPane(Component var1) {
      this.frame.setGlassPane(var1);
   }

   public void parseXMLMenu(String var1) {
      if (this.menuBar != null) {
         XMLControlElement var2 = new XMLControlElement(var1);
         if (var2.failedToRead()) {
            OSPLog.info("Tools menu not found: " + var1);
         } else {
            Class var3 = var2.getObjectClass();
            if (var3 != null && (class$org$opensourcephysics$tools$LaunchNode == null ? (class$org$opensourcephysics$tools$LaunchNode = class$("org.opensourcephysics.tools.LaunchNode")) : class$org$opensourcephysics$tools$LaunchNode).isAssignableFrom(var3)) {
               LaunchNode var4 = (LaunchNode)var2.loadObject((Object)null);
               String var5 = var4.toString();
               JMenu var6 = null;

               for(int var7 = 0; var7 < this.menuBar.getMenuCount(); ++var7) {
                  JMenu var8 = this.menuBar.getMenu(var7);
                  if (var8.getText().equals(var5)) {
                     var6 = var8;
                     break;
                  }
               }

               if (var6 == null) {
                  var6 = new JMenu(var5);
                  this.menuBar.add(var6);
                  this.menuBar.validate();
               }

               var4.setLaunchObject(this.model);
               var4.addMenuItemsTo(var6);
               OSPLog.finest("Tools menu loaded: " + var1);
            }
         }

      }
   }

   public void saveXML() {
      JFileChooser var1 = OSPFrame.getChooser();
      int var2 = var1.showSaveDialog((Component)null);
      if (var2 == 0) {
         File var3 = var1.getSelectedFile();
         if (var3.exists()) {
            int var4 = JOptionPane.showConfirmDialog((Component)null, "Replace existing " + var3.getName() + "?", "Replace File", 1);
            if (var4 != 0) {
               return;
            }
         }

         OSPFrame.chooserDir = var1.getCurrentDirectory().toString();
         String var7 = XML.getRelativePath(var3.getAbsolutePath());
         if (var7 == null || var7.trim().equals("")) {
            return;
         }

         int var5 = var7.toLowerCase().lastIndexOf(".xml");
         if (var5 != var7.length() - 4) {
            var7 = var7 + ".xml";
         }

         XMLControlElement var6 = new XMLControlElement(this.getApp());
         var6.write(var7);
      }

   }

   private OSPApplication getApp() {
      if (this.app == null) {
         this.app = new OSPApplication(this, this.model);
      }

      return this.app;
   }

   public void loadDefaultXML() {
      if (this.xmlDefault != null) {
         this.xmlDefault.loadObject(this.getApp());
      }

   }

   public void loadXML(String var1) {
      if (var1 != null && !var1.trim().equals("")) {
         XMLControlElement var2 = new XMLControlElement(var1);
         if ((class$org$opensourcephysics$controls$OSPApplication == null ? (class$org$opensourcephysics$controls$OSPApplication = class$("org.opensourcephysics.controls.OSPApplication")) : class$org$opensourcephysics$controls$OSPApplication).isAssignableFrom(var2.getObjectClass())) {
            this.xmlDefault = var2;
            var2.loadObject(this.getApp());
         } else {
            JOptionPane.showMessageDialog(this.frame, "\"" + var1 + "\" is for " + var2.getObjectClass() + ".", "Incorrect XML Object Type", 2);
         }

      } else {
         this.loadXML();
      }
   }

   public void loadXML() {
      JFileChooser var1 = OSPFrame.getChooser();
      int var2 = var1.showOpenDialog((Component)null);
      if (var2 == 0) {
         String var3 = var1.getSelectedFile().getAbsolutePath();
         this.loadXML(XML.getRelativePath(var3));
      }

   }

   public void inspectXML() {
      XMLControlElement var1 = new XMLControlElement(this.getApp());
      XMLTreePanel var2 = new XMLTreePanel(var1);
      JDialog var3 = new JDialog((Frame)null, true);
      var3.setContentPane(var2);
      var3.setSize(new Dimension(600, 300));
      var3.setVisible(true);
   }

   public void loadXML(String[] var1) {
      if (var1 != null) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            this.loadXML(var1[var2]);
         }
      }

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
