package org.opensourcephysics.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.rmi.RemoteException;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import org.opensourcephysics.controls.Cryptic;
import org.opensourcephysics.controls.OSPLog;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLControlElement;
import org.opensourcephysics.controls.XMLTreePanel;
import org.opensourcephysics.display.OSPFrame;

public class EncryptionTool extends OSPFrame implements Tool {
   private static final String VERSION = " 1.0";
   private static Dimension dim = new Dimension(720, 500);
   private XMLTreePanel treePanel;
   private JPanel contentPane;
   private JobManager jobManager;
   private JTextField passwordField;
   private JCheckBox encryptedCheckBox;
   private JCheckBox previewCheckBox;
   private String fileName;
   private JMenuItem saveItem;
   private JMenuItem saveAsItem;
   private JLabel passwordLabel;
   private JMenu fileMenu;
   private JMenu helpMenu;
   private JMenuItem openItem;
   private JMenuItem exitItem;
   private JMenuItem logItem;
   private JMenuItem aboutItem;
   private Icon openIcon;
   private JButton openButton;
   private Icon saveIcon;
   private JButton saveButton;
   private static final EncryptionTool ENCRYPTION_TOOL = new EncryptionTool();
   // $FF: synthetic field
   static Class class$org$opensourcephysics$controls$Cryptic;

   public static EncryptionTool getTool() {
      return ENCRYPTION_TOOL;
   }

   public EncryptionTool() {
      this.contentPane = new JPanel(new BorderLayout());
      this.jobManager = new JobManager(this);
      String var1 = "EncryptionTool";
      this.setName(var1);
      this.createGUI();
      this.refreshGUI();
      Toolbox.addTool(var1, this);
   }

   public EncryptionTool(String var1) {
      this();
      this.open(var1);
   }

   public String open(String var1) {
      OSPLog.fine("opening " + var1);
      XMLControlElement var2 = new XMLControlElement();
      var2.setDecryptPolicy(5);
      var2.read(var1);
      if (var2.failedToRead()) {
         return null;
      } else {
         String var3 = var2.getPassword();
         if (var3 == null) {
            this.passwordField.setText((String)null);
            this.displayXML(var2);
            this.encryptedCheckBox.setEnabled(true);
         } else if (this.passwordField.getText().equals(var3)) {
            this.displayXML(this.decrypt(var2));
            this.encryptedCheckBox.setEnabled(true);
         } else {
            this.displayXML(var2);
            this.encryptedCheckBox.setEnabled(false);
         }

         this.fileName = var1;
         this.refreshGUI();
         return var1;
      }
   }

   public void send(Job var1, Tool var2) throws RemoteException {
      XMLControlElement var3 = new XMLControlElement();
      var3.setDecryptPolicy(5);
      var3.readXML(var1.getXML());
      if (!var3.failedToRead()) {
         String var4 = var3.getPassword();
         if (var4 == null) {
            this.passwordField.setText((String)null);
            this.displayXML(var3);
         } else if (this.passwordField.getText().equals(var4)) {
            this.displayXML(this.decrypt(var3));
         } else {
            this.displayXML(var3);
         }

         this.fileName = null;
         this.refreshGUI();
         this.jobManager.log(var1, var2);
      }
   }

   public void refreshGUI() {
      String var1 = ToolsRes.getString("EncryptionTool.Title");
      if (this.fileName != null) {
         var1 = var1 + ": " + this.fileName;
      }

      this.setTitle(var1);
      this.openButton.setToolTipText(ToolsRes.getString("EncryptionTool.Button.Open.ToolTip"));
      this.saveButton.setToolTipText(ToolsRes.getString("EncryptionTool.Button.Save.ToolTip"));
      this.passwordLabel.setText(ToolsRes.getString("EncryptionTool.Label.Password"));
      this.passwordField.setToolTipText(ToolsRes.getString("EncryptionTool.PasswordField.ToolTip"));
      this.encryptedCheckBox.setText(ToolsRes.getString("EncryptionTool.CheckBox.Encrypted"));
      this.encryptedCheckBox.setToolTipText(ToolsRes.getString("EncryptionTool.CheckBox.Encrypted.ToolTip"));
      this.previewCheckBox.setText(ToolsRes.getString("EncryptionTool.CheckBox.Preview"));
      this.previewCheckBox.setToolTipText(ToolsRes.getString("EncryptionTool.CheckBox.Preview.ToolTip"));
      this.fileMenu.setText(ToolsRes.getString("EncryptionTool.Menu.File"));
      this.openItem.setText(ToolsRes.getString("EncryptionTool.MenuItem.Open"));
      this.saveItem.setText(ToolsRes.getString("EncryptionTool.MenuItem.Save"));
      this.saveAsItem.setText(ToolsRes.getString("EncryptionTool.MenuItem.SaveAs"));
      this.exitItem.setText(ToolsRes.getString("EncryptionTool.MenuItem.Exit"));
      this.helpMenu.setText(ToolsRes.getString("EncryptionTool.Menu.Help"));
      this.logItem.setText(ToolsRes.getString("EncryptionTool.MenuItem.Log"));
      this.aboutItem.setText(ToolsRes.getString("EncryptionTool.MenuItem.About"));
      this.saveButton.setEnabled(this.encryptedCheckBox.isEnabled());
      this.saveItem.setEnabled(this.encryptedCheckBox.isEnabled());
      this.saveAsItem.setEnabled(this.encryptedCheckBox.isEnabled());
      XMLControlElement var2 = this.getCurrentControl();
      this.encryptedCheckBox.setSelected(var2 != null && var2.getPassword() != null);
      this.passwordLabel.setEnabled(this.encryptedCheckBox.isSelected());
      this.passwordField.setEnabled(this.encryptedCheckBox.isSelected());
      this.previewCheckBox.setEnabled(this.encryptedCheckBox.isEnabled() && this.encryptedCheckBox.isSelected());
      this.previewCheckBox.setSelected(var2 != null && var2.getObjectClass() == (class$org$opensourcephysics$controls$Cryptic == null ? (class$org$opensourcephysics$controls$Cryptic = class$("org.opensourcephysics.controls.Cryptic")) : class$org$opensourcephysics$controls$Cryptic));
   }

   public static void main(String[] var0) {
      EncryptionTool var1 = getTool();
      var1.setDefaultCloseOperation(3);
      var1.open("Untitled.xset");
      var1.setVisible(true);
   }

   private XMLControlElement getCurrentControl() {
      if (this.treePanel == null) {
         return null;
      } else {
         XMLControl var1 = this.treePanel.getControl();
         return var1 instanceof XMLControlElement ? (XMLControlElement)var1 : null;
      }
   }

   private void displayXML(XMLControlElement var1) {
      if (this.treePanel != null) {
         this.contentPane.remove(this.treePanel);
      }

      this.treePanel = new XMLTreePanel(var1, false);
      this.contentPane.add(this.treePanel, "Center");
      this.validate();
      this.refreshGUI();
   }

   private void setPassword(String var1) {
      XMLControlElement var2 = this.getCurrentControl();
      if (var2 != null) {
         String var3 = var2.getPassword();
         if (!this.encryptedCheckBox.isEnabled()) {
            boolean var4 = var1.equals(var3);
            if (var4) {
               this.displayXML(this.decrypt(var2));
               this.encryptedCheckBox.setEnabled(true);
            } else {
               Toolkit.getDefaultToolkit().beep();
            }
         } else if (var2.getObjectClass() == (class$org$opensourcephysics$controls$Cryptic == null ? (class$org$opensourcephysics$controls$Cryptic = class$("org.opensourcephysics.controls.Cryptic")) : class$org$opensourcephysics$controls$Cryptic)) {
            XMLControlElement var5 = this.decrypt(var2);
            var5.setPassword(var1);
            var5 = this.encrypt(var5);
            var2.setValue("cryptic", var5.getString("cryptic"));
            this.treePanel.refresh();
         } else {
            if (var1.equals("") && !this.encryptedCheckBox.isSelected()) {
               var1 = null;
            }

            var2.setPassword(var1);
            this.treePanel.refresh();
         }

         this.refreshGUI();
      }
   }

   private XMLControlElement encrypt(XMLControlElement var1) {
      if (var1.getObjectClass() == (class$org$opensourcephysics$controls$Cryptic == null ? (class$org$opensourcephysics$controls$Cryptic = class$("org.opensourcephysics.controls.Cryptic")) : class$org$opensourcephysics$controls$Cryptic)) {
         return var1;
      } else {
         String var2 = var1.toXML();
         Cryptic var3 = new Cryptic(var2);
         XMLControlElement var4 = new XMLControlElement(var3);
         var4.setPassword(var1.getPassword());
         return var4;
      }
   }

   private XMLControlElement decrypt(XMLControlElement var1) {
      if (var1.getObjectClass() != (class$org$opensourcephysics$controls$Cryptic == null ? (class$org$opensourcephysics$controls$Cryptic = class$("org.opensourcephysics.controls.Cryptic")) : class$org$opensourcephysics$controls$Cryptic)) {
         return var1;
      } else {
         Cryptic var2 = (Cryptic)var1.loadObject((Object)null);
         String var3 = var2.decrypt();
         XMLControlElement var4 = new XMLControlElement(var3);
         return var4;
      }
   }

   private String open() {
      int var1 = OSPFrame.getChooser().showOpenDialog(this);
      if (var1 == 0) {
         OSPFrame.chooserDir = OSPFrame.getChooser().getCurrentDirectory().toString();
         String var2 = OSPFrame.getChooser().getSelectedFile().getAbsolutePath();
         var2 = XML.getRelativePath(var2);
         return this.open(var2);
      } else {
         return null;
      }
   }

   private String save(String var1) {
      if (var1 != null && !var1.equals("")) {
         if (this.passwordField.getBackground() == Color.yellow) {
            this.passwordField.setBackground(Color.white);
            this.setPassword(this.passwordField.getText());
         }

         XMLControlElement var2 = this.getCurrentControl();
         if (var2 == null) {
            return null;
         } else {
            if (var2.getObjectClass() == (class$org$opensourcephysics$controls$Cryptic == null ? (class$org$opensourcephysics$controls$Cryptic = class$("org.opensourcephysics.controls.Cryptic")) : class$org$opensourcephysics$controls$Cryptic)) {
               var2 = this.decrypt(var2);
            }

            if (var2.write(var1) == null) {
               return null;
            } else {
               this.fileName = var1;
               this.refreshGUI();
               return var1;
            }
         }
      } else {
         return null;
      }
   }

   private String saveAs() {
      int var1 = OSPFrame.getChooser().showSaveDialog(this);
      if (var1 == 0) {
         OSPFrame.chooserDir = OSPFrame.getChooser().getCurrentDirectory().toString();
         File var2 = OSPFrame.getChooser().getSelectedFile();
         if (var2.exists()) {
            int var3 = JOptionPane.showConfirmDialog((Component)null, ToolsRes.getString("EncryptionTool.Dialog.ReplaceFile.Message") + var2.getName() + "?", ToolsRes.getString("EncryptionTool.Dialog.ReplaceFile.Title"), 1);
            if (var3 != 0) {
               return null;
            }
         }

         String var4 = var2.getAbsolutePath();
         return var4 != null && !var4.trim().equals("") ? this.save(XML.getRelativePath(var4)) : null;
      } else {
         return null;
      }
   }

   private void createGUI() {
      this.contentPane.setPreferredSize(dim);
      this.setContentPane(this.contentPane);
      this.setDefaultCloseOperation(1);
      JToolBar var1 = new JToolBar();
      var1.setFloatable(false);
      this.contentPane.add(var1, "North");
      String var2 = "/org/opensourcephysics/resources/tools/images/open.gif";
      this.openIcon = ResourceLoader.getIcon(var2);
      this.openButton = new JButton(this.openIcon);
      this.openButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            EncryptionTool.this.open();
         }
      });
      var1.add(this.openButton);
      var2 = "/org/opensourcephysics/resources/tools/images/save.gif";
      this.saveIcon = ResourceLoader.getIcon(var2);
      this.saveButton = new JButton(this.saveIcon);
      this.saveButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            EncryptionTool.this.save(EncryptionTool.this.fileName);
         }
      });
      var1.add(this.saveButton);
      var1.addSeparator();
      this.passwordLabel = new JLabel();
      this.passwordLabel.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
      this.passwordField = new JTextField(20);
      this.passwordField.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent var1) {
            if (var1.getKeyCode() == 10) {
               EncryptionTool.this.passwordField.setBackground(Color.white);
               EncryptionTool.this.setPassword(EncryptionTool.this.passwordField.getText());
            } else if (var1.getKeyChar() != '\uffff') {
               EncryptionTool.this.passwordField.setBackground(Color.yellow);
            }

         }
      });
      this.passwordField.addFocusListener(new FocusAdapter() {
         public void focusLost(FocusEvent var1) {
            if (EncryptionTool.this.passwordField.getBackground() == Color.yellow) {
               EncryptionTool.this.passwordField.setBackground(Color.white);
               EncryptionTool.this.setPassword(EncryptionTool.this.passwordField.getText());
            }

         }
      });
      var1.add(this.passwordLabel);
      var1.add(this.passwordField);
      this.encryptedCheckBox = new JCheckBox("");
      this.encryptedCheckBox.setEnabled(false);
      this.encryptedCheckBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            if (EncryptionTool.this.encryptedCheckBox.isSelected()) {
               EncryptionTool.this.setPassword(EncryptionTool.this.passwordField.getText());
            } else {
               XMLControlElement var2 = EncryptionTool.this.getCurrentControl();
               if (var2.getObjectClass() == (EncryptionTool.class$org$opensourcephysics$controls$Cryptic == null ? (EncryptionTool.class$org$opensourcephysics$controls$Cryptic = EncryptionTool.class$("org.opensourcephysics.controls.Cryptic")) : EncryptionTool.class$org$opensourcephysics$controls$Cryptic)) {
                  var2 = EncryptionTool.this.decrypt(var2);
                  var2.setPassword((String)null);
                  EncryptionTool.this.displayXML(var2);
               }

               EncryptionTool.this.setPassword("");
            }

         }
      });
      this.encryptedCheckBox.setContentAreaFilled(false);
      var1.add(this.encryptedCheckBox);
      this.previewCheckBox = new JCheckBox("");
      this.previewCheckBox.setOpaque(false);
      this.previewCheckBox.setEnabled(false);
      this.previewCheckBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            XMLControlElement var2 = EncryptionTool.this.getCurrentControl();
            if (EncryptionTool.this.previewCheckBox.isSelected()) {
               EncryptionTool.this.displayXML(EncryptionTool.this.encrypt(var2));
            } else {
               EncryptionTool.this.displayXML(EncryptionTool.this.decrypt(var2));
            }

         }
      });
      var1.add(this.previewCheckBox);
      int var3 = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
      JMenuBar var4 = new JMenuBar();
      this.fileMenu = new JMenu();
      var4.add(this.fileMenu);
      this.openItem = new JMenuItem();
      this.openItem.setAccelerator(KeyStroke.getKeyStroke(79, var3));
      this.openItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            EncryptionTool.this.open();
         }
      });
      this.fileMenu.add(this.openItem);
      this.fileMenu.addSeparator();
      this.saveItem = new JMenuItem();
      this.saveItem.setAccelerator(KeyStroke.getKeyStroke(83, var3));
      this.saveItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            EncryptionTool.this.save(EncryptionTool.this.fileName);
         }
      });
      this.saveItem.setEnabled(false);
      this.fileMenu.add(this.saveItem);
      this.saveAsItem = new JMenuItem();
      this.saveAsItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            EncryptionTool.this.saveAs();
         }
      });
      this.saveAsItem.setEnabled(false);
      this.fileMenu.add(this.saveAsItem);
      this.exitItem = new JMenuItem(ToolsRes.getString("MenuItem.Exit"));
      this.exitItem.setAccelerator(KeyStroke.getKeyStroke(81, var3));
      this.exitItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            System.exit(0);
         }
      });
      this.fileMenu.addSeparator();
      this.fileMenu.add(this.exitItem);
      this.helpMenu = new JMenu();
      var4.add(this.helpMenu);
      this.logItem = new JMenuItem();
      this.logItem.setAccelerator(KeyStroke.getKeyStroke(76, var3));
      this.logItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            OSPLog var2 = OSPLog.getOSPLog();
            if (var2.getLocation().x == 0 && var2.getLocation().y == 0) {
               Point var3 = EncryptionTool.this.getLocation();
               var2.setLocation(var3.x + 28, var3.y + 28);
            }

            OSPLog.showLog();
         }
      });
      this.helpMenu.add(this.logItem);
      this.helpMenu.addSeparator();
      this.aboutItem = new JMenuItem();
      this.aboutItem.setAccelerator(KeyStroke.getKeyStroke(65, var3));
      this.aboutItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            String var2 = ToolsRes.getString("EncryptionTool.About.ToolName");
            String var3 = var2 + " 1.0" + XML.NEW_LINE + ToolsRes.getString("EncryptionTool.About.OSPName") + XML.NEW_LINE + "www.opensourcephysics.org";
            JOptionPane.showMessageDialog(EncryptionTool.this, var3, ToolsRes.getString("EncryptionTool.About.Title"), 1);
         }
      });
      this.helpMenu.add(this.aboutItem);
      this.setJMenuBar(var4);
      this.pack();
      Dimension var5 = Toolkit.getDefaultToolkit().getScreenSize();
      int var6 = (var5.width - this.getBounds().width) / 2;
      int var7 = (var5.height - this.getBounds().height) / 2;
      this.setLocation(var6, var7);
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
