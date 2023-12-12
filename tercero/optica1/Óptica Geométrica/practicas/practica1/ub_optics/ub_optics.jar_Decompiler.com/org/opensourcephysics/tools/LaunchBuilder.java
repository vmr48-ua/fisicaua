package org.opensourcephysics.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.TreePath;
import org.opensourcephysics.controls.OSPLog;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControlElement;
import org.opensourcephysics.display.OSPFrame;

public class LaunchBuilder extends Launcher {
   static final Color RED = new Color(255, 102, 102);
   static JFileChooser fileChooser;
   static FileFilter jarFileFilter;
   static FileFilter htmlFileFilter;
   static FileFilter allFileFilter;
   static int maxArgs = 4;
   Action newTabSetAction;
   Action changeAction;
   Action newTabAction;
   Action addAction;
   Action cutAction;
   Action copyAction;
   Action pasteAction;
   Action importAction;
   Action saveAsAction;
   Action saveAction;
   Action saveAllAction;
   Action saveSetAsAction;
   Action moveUpAction;
   Action moveDownAction;
   Action openJarAction;
   Action searchJarAction;
   Action openArgAction;
   Action openURLAction;
   Action openTabAction;
   FocusListener focusListener;
   KeyListener keyListener;
   JTabbedPane editorTabs;
   JPanel displayPanel;
   JPanel launchPanel;
   JPanel authorPanel;
   ArrayList labels;
   JTextField titleField;
   JLabel titleLabel;
   JTextField passwordEditor;
   JLabel passwordLabel;
   JTextField nameField;
   JLabel nameLabel;
   JTextField tooltipField;
   JLabel tooltipLabel;
   TitledBorder htmlTitle;
   JTextField classField;
   JLabel classLabel;
   JTextField argField;
   JLabel argLabel;
   JSpinner argSpinner;
   JTextField jarField;
   JLabel jarLabel;
   JTextField urlField;
   JLabel urlLabel;
   JPanel urlPanel;
   JTextPane descriptionPane;
   JScrollPane descriptionScroller;
   TitledBorder descriptionTitle;
   JSplitPane displaySplitPane;
   JTextField authorField;
   JLabel authorLabel;
   JTextField keywordField;
   JLabel keywordLabel;
   JTextPane commentPane;
   JScrollPane commentScroller;
   TitledBorder commentTitle;
   TitledBorder optionsTitle;
   TitledBorder securityTitle;
   JCheckBox editorEnabledCheckBox;
   JCheckBox encryptCheckBox;
   JCheckBox onEditCheckBox;
   JCheckBox onLoadCheckBox;
   JCheckBox hideRootCheckBox;
   JCheckBox hiddenCheckBox;
   JCheckBox buttonViewCheckBox;
   JCheckBox singleVMCheckBox;
   JCheckBox showLogCheckBox;
   JCheckBox clearLogCheckBox;
   JCheckBox singletonCheckBox;
   JCheckBox singleAppCheckBox;
   JComboBox levelDropDown;
   String previousClassPath;
   JButton newTabButton;
   JButton addButton;
   JButton cutButton;
   JButton copyButton;
   JButton pasteButton;
   JButton moveUpButton;
   JButton moveDownButton;
   JMenuItem newItem;
   JMenuItem previewItem;
   JMenuItem saveNodeItem;
   JMenuItem saveNodeAsItem;
   JMenuItem saveSetAsItem;
   JMenuItem saveAllItem;
   JMenuItem importItem;
   JMenuItem openTabItem;
   JMenu toolsMenu;
   JMenuItem encryptionToolItem;
   JToolBar toolbar;
   LaunchSaver saver = new LaunchSaver(this);
   // $FF: synthetic field
   static Class class$org$opensourcephysics$tools$LaunchNode;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$tools$Launcher;

   public LaunchBuilder() {
   }

   public LaunchBuilder(String var1) {
      super(var1);
   }

   public static void main(String[] var0) {
      String var1 = null;
      if (var0 != null && var0.length != 0) {
         var1 = var0[0];
      }

      LaunchBuilder var2 = new LaunchBuilder(var1);
      var2.frame.setVisible(true);
   }

   public String save(LaunchNode var1, String var2) {
      if (var1 == null) {
         return null;
      } else if (var2 != null && !var2.trim().equals("")) {
         if (XML.getExtension(var2) == null) {
            while(var2.endsWith(".")) {
               var2 = var2.substring(0, var2.length() - 1);
            }

            var2 = var2 + ".xml";
         }

         if (!this.saveOwnedNodes(var1)) {
            return null;
         } else {
            OSPLog.fine(var2);
            File var3 = new File(XML.getResolvedPath(var2, Launcher.tabSetBasePath));
            String var4 = XML.forwardSlash(var3.getAbsolutePath());
            String var5 = XML.getDirectoryPath(var4);
            XML.createFolders(var5);
            XMLControlElement var6 = new XMLControlElement(var1);
            var6.write(var4);
            if (!var6.canWrite) {
               OSPLog.info(LaunchRes.getString("Dialog.SaveFailed.Message") + " " + var4);
               JOptionPane.showMessageDialog((Component)null, LaunchRes.getString("Dialog.SaveFailed.Message") + " " + var2, LaunchRes.getString("Dialog.SaveFailed.Title"), 2);
               return null;
            } else {
               var1.setFileName(var2);
               super.changedFiles.remove(var1.getFileName());
               return var2;
            }
         }
      } else {
         return this.saveAs(var1);
      }
   }

   public String saveAs(LaunchNode var1) {
      Launcher.getXMLChooser().setFileFilter(Launcher.xmlFileFilter);
      String var2;
      if (var1.getFileName() != null) {
         var2 = XML.getResolvedPath(var1.getFileName(), Launcher.tabSetBasePath);
         Launcher.getXMLChooser().setSelectedFile(new File(var2));
      } else {
         var2 = var1.name;
         if (var2.equals(LaunchRes.getString("NewNode.Name")) || var2.equals(LaunchRes.getString("NewTab.Name"))) {
            var2 = LaunchRes.getString("NewFile.Name");
         }

         String var3 = XML.getResolvedPath(var2 + ".xml", Launcher.tabSetBasePath);
         Launcher.getXMLChooser().setSelectedFile(new File(var3));
      }

      int var11 = Launcher.getXMLChooser().showDialog((Component)null, LaunchRes.getString("FileChooser.SaveAs.Title"));
      if (var11 == 0) {
         File var12 = Launcher.getXMLChooser().getSelectedFile();
         String var4 = XML.forwardSlash(var12.getParent());
         XML.createFolders(var4);
         String var5;
         if (var12.exists()) {
            var5 = XML.forwardSlash(var12.getAbsolutePath());
            var5 = XML.getPathRelativeTo(var5, Launcher.tabSetBasePath);
            if (this.getOpenPaths().contains(var5)) {
               JOptionPane.showMessageDialog(super.frame, LaunchRes.getString("Dialog.DuplicateFileName.Message") + " \"" + var5 + "\"", LaunchRes.getString("Dialog.DuplicateFileName.Title"), 2);
               return null;
            }

            int var6 = JOptionPane.showConfirmDialog(super.frame, LaunchRes.getString("Dialog.ReplaceFile.Message") + " " + var12.getName() + XML.NEW_LINE + LaunchRes.getString("Dialog.ReplaceFile.Question"), LaunchRes.getString("Dialog.ReplaceFile.Title"), 0);
            if (var6 != 0) {
               return null;
            }
         }

         var4 = XML.forwardSlash(var12.getAbsolutePath());
         var5 = XML.getPathRelativeTo(var4, Launcher.tabSetBasePath);
         OSPFrame.chooserDir = XML.getDirectoryPath(var4);
         Map var13 = this.getClones(var1);
         var4 = this.save(var1, var5);
         if (var4 != null) {
            LaunchPanel var8;
            if (var1.isRoot()) {
               for(int var7 = 0; var7 < super.tabbedPane.getTabCount(); ++var7) {
                  var8 = (LaunchPanel)super.tabbedPane.getComponentAt(var7);
                  if (var8.getRootNode() == var1) {
                     super.tabbedPane.setTitleAt(var7, var1.toString());
                     break;
                  }
               }
            }

            Iterator var14 = var13.keySet().iterator();

            while(var14.hasNext()) {
               var8 = (LaunchPanel)var14.next();
               LaunchNode var9 = (LaunchNode)var13.get(var8);
               var9.setFileName(var1.getFileName());
               if (var9 == var8.getRootNode()) {
                  int var10 = super.tabbedPane.indexOfComponent(var8);
                  super.tabbedPane.setTitleAt(var10, var1.toString());
               }
            }

            if (super.tabSetName != null) {
               super.changedFiles.add(super.tabSetName);
            }
         }

         return var4;
      } else {
         return null;
      }
   }

   public boolean saveOwnedNodes(LaunchNode var1) {
      if (var1 == null) {
         return false;
      } else if (var1.isSelfContained()) {
         return true;
      } else {
         LaunchNode[] var2 = var1.getChildOwnedNodes();

         for(int var3 = 0; var3 < var2.length; ++var3) {
            if (var2[var3].getChildOwnedNodes().length > 1 && !this.saveOwnedNodes(var2[var3])) {
               return false;
            }

            if (this.save(var2[var3], var2[var3].getFileName()) == null) {
               return false;
            }
         }

         return true;
      }
   }

   protected String saveTabSetAs() {
      this.saver.setBuilder(this);
      this.saver.setVisible(true);
      return !this.saver.isApproved() ? null : this.saveTabSet();
   }

   protected String saveTabSet() {
      if (super.tabSetName.trim().equals("")) {
         return this.saveTabSetAs();
      } else if (!this.isTabSetWritable()) {
         return this.saveTabSetAs();
      } else if (!super.selfContained && !this.saveTabs()) {
         return null;
      } else {
         String var1 = XML.getResolvedPath(super.tabSetName, Launcher.tabSetBasePath);
         OSPLog.fine(var1);
         File var2 = new File(var1);
         var1 = XML.forwardSlash(var2.getAbsolutePath());
         String var3 = XML.getDirectoryPath(var1);
         XML.createFolders(var3);
         Launcher.LaunchSet var4 = new Launcher.LaunchSet(this, super.tabSetName);
         XMLControlElement var5 = new XMLControlElement(var4);
         if (var5.write(var1) == null) {
            return null;
         } else {
            super.changedFiles.clear();
            super.jarBasePath = null;
            if (super.spawner != null) {
               super.spawner.open(var1);
               super.spawner.refreshGUI();
            }

            return var1;
         }
      }
   }

   protected boolean saveTabs() {
      Component[] var1 = super.tabbedPane.getComponents();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         LaunchPanel var3 = (LaunchPanel)var1[var2];
         LaunchNode var4 = var3.getRootNode();
         if (var4.getFileName() != null && !var4.getFileName().equals("")) {
            this.save(var4, XML.getResolvedPath(var4.getFileName(), Launcher.tabSetBasePath));
         }
      }

      return true;
   }

   protected void refreshSelectedNode() {
      this.refreshNode(this.getSelectedNode());
   }

   protected void refreshNode(LaunchNode var1) {
      boolean var2 = false;
      if (var1 != null) {
         LaunchNode var3;
         if (var1.isSingleVM() != this.singleVMCheckBox.isSelected()) {
            OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodeVM"));
            var3 = (LaunchNode)var1.getParent();
            if (var3 != null && var3.isSingleVM()) {
               var1.singleVM = false;
               var1.singleVMOff = !this.singleVMCheckBox.isSelected();
            } else {
               var1.singleVM = this.singleVMCheckBox.isSelected();
               var1.singleVMOff = false;
            }

            if (var1.isSingleVM()) {
               this.showLogCheckBox.setSelected(var1.showLog);
               this.clearLogCheckBox.setSelected(var1.clearLog);
               this.singleAppCheckBox.setSelected(var1.isSingleApp());
            } else {
               this.singletonCheckBox.setSelected(var1.singleton);
            }

            var2 = true;
         }

         if (var1.isSingleVM() && var1.showLog != this.showLogCheckBox.isSelected()) {
            OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodeShowLog"));
            var1.showLog = this.showLogCheckBox.isSelected();
            var2 = true;
         }

         if (var1.isSingleVM() && var1.isShowLog() && var1.clearLog != this.clearLogCheckBox.isSelected()) {
            OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodeClearLog"));
            var1.clearLog = this.clearLogCheckBox.isSelected();
            var2 = true;
         }

         if (var1.isSingleVM() && var1.isSingleApp() != this.singleAppCheckBox.isSelected()) {
            OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodeSingleApp"));
            var3 = (LaunchNode)var1.getParent();
            if (var3 != null && var3.isSingleApp()) {
               var1.singleApp = false;
               var1.singleAppOff = !this.singleAppCheckBox.isSelected();
            } else {
               var1.singleApp = this.singleAppCheckBox.isSelected();
               var1.singleAppOff = false;
            }

            var2 = true;
         }

         if (var1.singleton != this.singletonCheckBox.isSelected()) {
            OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodeSingleton"));
            var1.singleton = this.singletonCheckBox.isSelected();
            var2 = true;
         }

         if (var1.hiddenInLauncher != this.hiddenCheckBox.isSelected()) {
            OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodeHidden"));
            var1.hiddenInLauncher = this.hiddenCheckBox.isSelected();
            var2 = true;
         }

         if (var1.isButtonView() != this.buttonViewCheckBox.isSelected()) {
            OSPLog.finest(LaunchRes.getString("Log.Message.ChangeButtonView"));
            var1.setButtonView(this.buttonViewCheckBox.isSelected());
            var2 = true;
         }

         if (!var1.name.equals(this.nameField.getText())) {
            OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodeName"));
            var1.name = this.nameField.getText();
            var2 = true;
         }

         if (!var1.tooltip.equals(this.tooltipField.getText())) {
            OSPLog.finest(LaunchRes.getString("Log.Message.ChangeTooltip"));
            var1.tooltip = this.tooltipField.getText();
            var2 = true;
         }

         if (!var1.description.equals(this.descriptionPane.getText())) {
            OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodeDesc"));
            var1.description = this.descriptionPane.getText();
            var2 = true;
         }

         int var11 = (Integer)this.argSpinner.getValue();
         String var4 = this.argField.getText();
         if (!var4.equals("")) {
            var1.setMinimumArgLength(var11 + 1);
         }

         if (var1.args.length > var11 && !var4.equals(var1.args[var11])) {
            OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodeArgs") + " " + var11);
            var1.args[var11] = var4;
            if (var4.equals("")) {
               var1.setMinimumArgLength(1);
            }

            var2 = true;
         }

         String var5 = this.jarField.getText();
         if (var5.equals("") && var1.classPath != null || !var5.equals("") && !var5.equals(var1.classPath)) {
            OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodePath"));
            var1.setClassPath(var5.equals("") ? null : var5);
            var2 = true;
         }

         String var6 = this.urlField.getText();
         if (var6.equals("")) {
            var6 = null;
         }

         if (var1.urlName != null && !var1.urlName.equals(var6) || var6 != null && !var6.equals(var1.urlName)) {
            var1.setURL(var6);
            var2 = true;
         }

         String var7 = this.classField.getText();
         if (var7.equals("")) {
            if (var1.launchClassName != null) {
               var1.launchClassName = null;
               var1.launchClass = null;
               OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodeLaunchClass"));
               var2 = true;
            }
         } else if (!var7.equals(var1.launchClassName) || !var7.equals("") && var1.getLaunchClass() == null) {
            boolean var8 = var1.setLaunchClass(var7);
            if (var8) {
               OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodeLaunchClass"));
               var2 = true;
            }
         }

         if (!var1.getAuthor().equals(this.authorField.getText())) {
            OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodeAuthor"));
            var1.author = this.authorField.getText();
            var2 = true;
         }

         if (!var1.keywords.equals(this.keywordField.getText())) {
            OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodeKeywords"));
            var1.keywords = this.keywordField.getText();
            var2 = true;
         }

         if (!var1.comment.equals(this.commentPane.getText())) {
            OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodeComment"));
            var1.comment = this.commentPane.getText();
            var2 = true;
         }

         LaunchNode var12 = (LaunchNode)var1.getRoot();
         if (var12 != null) {
            boolean var9 = this.hideRootCheckBox.isSelected();
            if (var9 != var12.hiddenWhenRoot) {
               var12.hiddenWhenRoot = var9;
               OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodeRootHidden"));
               var2 = true;
            }

            boolean var10 = this.editorEnabledCheckBox.isSelected();
            if (var10 != super.editorEnabled) {
               super.editorEnabled = var10;
               OSPLog.finest(LaunchRes.getString("Log.Message.ChangeNodeEditorEnabled"));
               if (super.tabSetName != null) {
                  super.changedFiles.add(super.tabSetName);
               }

               this.refreshGUI();
            }
         }

         if (var2) {
            OSPLog.fine(LaunchRes.getString("Log.Message.ChangeNode") + " \"" + var1.toString() + "\"");
            LaunchPanel var13 = this.getSelectedTab();
            if (var13 != null) {
               var13.treeModel.nodeChanged(var1);
            }

            if (var1.getOwner() != null) {
               super.changedFiles.add(var1.getOwner().getFileName());
            } else {
               super.changedFiles.add(super.tabSetName);
            }

            this.refreshClones(var1);
            this.refreshGUI();
         }
      }

   }

   protected boolean addTab(LaunchNode var1) {
      OSPLog.finest(var1.toString());
      boolean var2 = super.addTab(var1);
      if (var2) {
         LaunchPanel var3 = this.getSelectedTab();
         var3.showText = false;
         var3.textPane.setText((String)null);
         if (super.tabSetName == null) {
            super.tabSetName = LaunchRes.getString("Tabset.Name.New");
         }

         super.changedFiles.add(super.tabSetName);
         this.refreshGUI();
      }

      return var2;
   }

   protected boolean removeSelectedTab() {
      boolean var2;
      if (super.tabbedPane.getTabCount() == 1) {
         Launcher.LinkEdit var3 = null;
         if (super.tabSetName != null && (new File(super.tabSetName)).exists()) {
            String[] var4 = new String[]{" "};
            var3 = new Launcher.LinkEdit(var4);
         }

         var2 = this.removeAllTabs();
         if (var2 && var3 != null) {
            super.undoSupport.postEdit(var3);
         }

         return var2;
      } else {
         LaunchPanel var1 = (LaunchPanel)super.tabbedPane.getSelectedComponent();
         if (var1 != null && !this.saveChanges(var1)) {
            return false;
         } else {
            var2 = super.removeSelectedTab();
            if (super.tabSetName != null) {
               super.changedFiles.add(super.tabSetName);
               this.refreshGUI();
            }

            return var2;
         }
      }
   }

   protected boolean saveChanges(LaunchPanel var1) {
      LaunchNode var2 = var1.getRootNode();
      int var3 = super.tabbedPane.indexOfComponent(var1);
      String var4 = var3 > -1 ? super.tabbedPane.getTitleAt(var3) : Launcher.getDisplayName(var2.getFileName());
      boolean var5 = super.changedFiles.contains(var2.getFileName());
      LaunchNode[] var6 = var2.getAllOwnedNodes();

      int var7;
      for(var7 = 0; var7 < var6.length; ++var7) {
         var5 = var5 || super.changedFiles.contains(var6[var7].getFileName());
      }

      if (var5) {
         var7 = JOptionPane.showConfirmDialog(super.frame, LaunchRes.getString("Dialog.SaveChanges.Tab.Message") + " \"" + var4 + "\"" + XML.NEW_LINE + LaunchRes.getString("Dialog.SaveChanges.Question"), LaunchRes.getString("Dialog.SaveChanges.Title"), 1);
         if (var7 == 2) {
            return false;
         }

         if (var7 == 0) {
            this.save(var2, var2.getFileName());
         }
      }

      return true;
   }

   protected boolean removeAllTabs() {
      return !this.saveAllChanges() ? false : super.removeAllTabs();
   }

   protected boolean saveAllChanges() {
      if (!super.changedFiles.isEmpty() && super.tabbedPane.getTabCount() > 0) {
         String var1 = LaunchRes.getString("Dialog.SaveChanges.Tabset.Message");
         int var2 = JOptionPane.showConfirmDialog(super.frame, var1 + "\"" + super.tabSetName + "\"" + XML.NEW_LINE + LaunchRes.getString("Dialog.SaveChanges.Question"), LaunchRes.getString("Dialog.SaveChanges.Title"), 1);
         if (var2 == 2) {
            return false;
         }

         if (var2 == 0) {
            if (!super.tabSetName.equals(LaunchRes.getString("Tabset.Name.New")) && this.saveAllItem.isEnabled()) {
               this.saveTabSet();
            } else {
               this.saveTabSetAs();
            }
         }
      }

      return true;
   }

   protected void refreshStringResources() {
      super.refreshStringResources();
      this.saver = new LaunchSaver(this);
      this.editorTabs.setTitleAt(0, LaunchRes.getString("Tab.Display"));
      this.editorTabs.setTitleAt(1, LaunchRes.getString("Tab.Launch"));
      this.editorTabs.setTitleAt(2, LaunchRes.getString("Tab.Author"));
      this.htmlTitle.setTitle(LaunchRes.getString("Label.HTML"));
      this.commentTitle.setTitle(LaunchRes.getString("Label.Comments"));
      this.descriptionTitle.setTitle(LaunchRes.getString("Label.Description"));
      this.optionsTitle.setTitle(LaunchRes.getString("Label.Options"));
      this.hiddenCheckBox.setText(LaunchRes.getString("Checkbox.Hidden"));
      this.buttonViewCheckBox.setText(LaunchRes.getString("Checkbox.ButtonView"));
      this.nameLabel.setText(LaunchRes.getString("Label.Name"));
      this.tooltipLabel.setText(LaunchRes.getString("Label.Tooltip"));
      this.urlLabel.setText(LaunchRes.getString("Label.URL"));
      this.jarLabel.setText(LaunchRes.getString("Label.Jar"));
      this.classLabel.setText(LaunchRes.getString("Label.Class"));
      this.argLabel.setText(LaunchRes.getString("Label.Args"));
      this.singleVMCheckBox.setText(LaunchRes.getString("Checkbox.SingleVM"));
      this.showLogCheckBox.setText(LaunchRes.getString("Checkbox.ShowLog"));
      this.clearLogCheckBox.setText(LaunchRes.getString("Checkbox.ClearLog"));
      this.singletonCheckBox.setText(LaunchRes.getString("Checkbox.Singleton"));
      this.singleAppCheckBox.setText(LaunchRes.getString("Checkbox.SingleApp"));
      this.authorLabel.setText(LaunchRes.getString("Label.Author"));
      this.keywordLabel.setText(LaunchRes.getString("Label.Keywords"));
      this.securityTitle.setTitle(LaunchRes.getString("Label.Security"));
      this.editorEnabledCheckBox.setText(LaunchRes.getString("Checkbox.EditorEnabled"));
      this.encryptCheckBox.setText(LaunchRes.getString("Checkbox.Encrypted"));
      this.passwordLabel.setText(LaunchRes.getString("Label.Password"));
      this.onLoadCheckBox.setText(LaunchRes.getString("Checkbox.PWLoad"));
      this.titleLabel.setText(LaunchRes.getString("Label.Title"));
      this.hideRootCheckBox.setText(LaunchRes.getString("Checkbox.HideRoot"));
      this.previewItem.setText(LaunchRes.getString("Menu.File.Preview"));
      this.encryptionToolItem.setText(LaunchRes.getString("MenuItem.EncryptionTool"));
      this.newItem.setText(LaunchRes.getString("Menu.File.New"));
      this.importItem.setText(LaunchRes.getString("Action.Import"));
      this.saveNodeItem.setText(LaunchRes.getString("Action.SaveNode"));
      this.saveNodeAsItem.setText(LaunchRes.getString("Action.SaveNodeAs"));
      this.saveAllItem.setText(LaunchRes.getString("Action.SaveAll"));
      this.openTabItem.setText(LaunchRes.getString("Action.OpenTab"));
      this.saveSetAsItem.setText(LaunchRes.getString("Action.SaveSetAs"));
      this.toolsMenu.setText(LaunchRes.getString("Menu.Tools"));
      this.newTabButton.setText(LaunchRes.getString("Action.New"));
      this.addButton.setText(LaunchRes.getString("Action.Add"));
      this.cutButton.setText(LaunchRes.getString("Action.Cut"));
      this.copyButton.setText(LaunchRes.getString("Action.Copy"));
      this.pasteButton.setText(LaunchRes.getString("Action.Paste"));
      this.moveUpButton.setText(LaunchRes.getString("Action.Up"));
      this.moveDownButton.setText(LaunchRes.getString("Action.Down"));
      this.labels.clear();
      this.labels.add(this.nameLabel);
      this.labels.add(this.tooltipLabel);
      this.labels.add(this.urlLabel);
      FontRenderContext var1 = new FontRenderContext((AffineTransform)null, false, false);
      Font var2 = this.nameLabel.getFont();
      int var3 = 0;

      Rectangle2D var6;
      for(Iterator var4 = this.labels.iterator(); var4.hasNext(); var3 = Math.max(var3, (int)var6.getWidth() + 1)) {
         JLabel var5 = (JLabel)var4.next();
         var6 = var2.getStringBounds(var5.getText() + " ", var1);
      }

      Dimension var8 = new Dimension(var3, 20);
      Iterator var9 = this.labels.iterator();

      JLabel var10;
      while(var9.hasNext()) {
         var10 = (JLabel)var9.next();
         var10.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
         var10.setPreferredSize(var8);
         var10.setHorizontalAlignment(11);
      }

      this.labels.clear();
      this.labels.add(this.jarLabel);
      this.labels.add(this.classLabel);
      this.labels.add(this.argLabel);
      var3 = 0;

      Rectangle2D var7;
      for(var9 = this.labels.iterator(); var9.hasNext(); var3 = Math.max(var3, (int)var7.getWidth() + 1)) {
         var10 = (JLabel)var9.next();
         var7 = var2.getStringBounds(var10.getText() + " ", var1);
      }

      var8 = new Dimension(var3, 20);
      var9 = this.labels.iterator();

      while(var9.hasNext()) {
         var10 = (JLabel)var9.next();
         var10.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
         var10.setPreferredSize(var8);
         var10.setHorizontalAlignment(11);
      }

      this.labels.clear();
      this.labels.add(this.authorLabel);
      this.labels.add(this.keywordLabel);
      var3 = 0;

      for(var9 = this.labels.iterator(); var9.hasNext(); var3 = Math.max(var3, (int)var7.getWidth() + 1)) {
         var10 = (JLabel)var9.next();
         var7 = var2.getStringBounds(var10.getText() + " ", var1);
      }

      var8 = new Dimension(var3, 20);
      var9 = this.labels.iterator();

      while(var9.hasNext()) {
         var10 = (JLabel)var9.next();
         var10.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
         var10.setPreferredSize(var8);
         var10.setHorizontalAlignment(11);
      }

   }

   protected void refreshGUI() {
      if (super.previousNode != null) {
         LaunchNode var1 = super.previousNode;
         super.previousNode = null;
         this.refreshNode(var1);
      }

      if (super.newNodeSelected) {
         this.argSpinner.setValue(new Integer(0));
         super.newNodeSelected = false;
      }

      this.titleField.setText(super.title);
      this.titleField.setBackground(Color.white);
      super.refreshGUI();
      String var13 = super.frame.getTitle();
      if (super.title != null) {
         if (!super.changedFiles.isEmpty()) {
            var13 = var13 + " [" + super.tabSetName + "*]";
         } else {
            var13 = var13 + " [" + super.tabSetName + "]";
         }
      } else if (!super.changedFiles.isEmpty()) {
         if (super.tabbedPane.getTabCount() == 0) {
            super.changedFiles.clear();
         } else {
            var13 = var13 + "*";
         }
      }

      super.frame.setTitle(var13);
      LaunchNode var2 = this.getSelectedNode();
      if (var2 != null) {
         for(int var3 = 0; var3 < super.tabbedPane.getTabCount(); ++var3) {
            LaunchNode var4 = this.getTab(var3).getRootNode();
            super.tabbedPane.setTitleAt(var3, var4.toString());
         }

         this.hiddenCheckBox.setSelected(var2.isHiddenInLauncher());
         boolean var14 = var2.getParent() != null && ((LaunchNode)var2.getParent()).isHiddenInLauncher();
         this.hiddenCheckBox.setEnabled(!var14);
         this.nameField.setText(var2.toString());
         this.nameField.setBackground(Color.white);
         this.tooltipField.setText(var2.tooltip);
         this.tooltipField.setBackground(Color.white);
         this.descriptionPane.setText(var2.description);
         this.descriptionPane.setBackground(Color.white);
         this.urlField.setText(var2.urlName);
         this.urlField.setBackground(var2.url == null && var2.urlName != null ? RED : Color.white);
         JEditorPane var16 = this.getSelectedTab().textPane;
         if (var2.url != null && var16.getPage() != var2.url) {
            try {
               if (var2.url.getContent() != null) {
                  var16.setPage(var2.url);
               }
            } catch (IOException var12) {
               var16.setText((String)null);
            }
         } else if (var2.url == null) {
            if (LaunchPanel.defaultType != null) {
               var16.setContentType(LaunchPanel.defaultType);
            }

            var16.setText((String)null);
         }

         String var5 = var2.getClassPath();
         if (!var5.equals(this.previousClassPath)) {
            boolean var6 = this.getClassChooser().setPath(var5);
            this.searchJarAction.setEnabled(var6);
         }

         this.previousClassPath = var2.getClassPath();
         this.jarField.setText(var2.classPath);
         this.jarField.setBackground(var2.classPath != null && !this.getClassChooser().isLoaded(var2.classPath) ? RED : Color.white);
         this.classField.setText(var2.launchClassName);
         this.classField.setBackground(var2.getLaunchClass() == null && var2.launchClassName != null ? RED : Color.white);
         int var20 = (Integer)this.argSpinner.getValue();
         if (var2.args.length > var20) {
            this.argField.setText(var2.args[var20]);
         } else {
            this.argField.setText("");
         }

         boolean var7 = this.argField.getText().endsWith(".xml");
         Resource var8 = null;
         if (var7) {
            var8 = ResourceLoader.getResource(this.argField.getText());
            this.argField.setBackground(var8 == null ? RED : Color.white);
         } else {
            this.argField.setBackground(Color.white);
         }

         LaunchNode var9 = (LaunchNode)var2.getParent();
         this.singletonCheckBox.setEnabled(var9 == null || !var9.isSingleton());
         this.singletonCheckBox.setSelected(var2.isSingleton());
         this.singleVMCheckBox.setSelected(var2.isSingleVM());
         if (!var2.isSingleVM()) {
            this.showLogCheckBox.setEnabled(false);
            this.showLogCheckBox.setSelected(false);
            this.clearLogCheckBox.setEnabled(false);
            this.clearLogCheckBox.setSelected(false);
            this.singleAppCheckBox.setEnabled(false);
            this.singleAppCheckBox.setSelected(false);
         } else {
            this.showLogCheckBox.setEnabled(var9 == null || !var9.isShowLog());
            this.showLogCheckBox.setSelected(var2.isShowLog());
            this.clearLogCheckBox.setEnabled(var9 == null || !var9.isClearLog());
            this.clearLogCheckBox.setSelected(var2.isClearLog());
            this.singleAppCheckBox.setEnabled(true);
            this.singleAppCheckBox.setSelected(var2.isSingleApp());
         }

         this.levelDropDown.setVisible(var2.isShowLog());
         this.clearLogCheckBox.setVisible(var2.isShowLog());
         boolean var10;
         if (this.levelDropDown.isVisible()) {
            var10 = var9 == null || !var9.isShowLog();
            this.levelDropDown.setEnabled(false);
            this.levelDropDown.removeAllItems();
            int var11 = 0;

            while(true) {
               if (var11 >= OSPLog.levels.length) {
                  this.levelDropDown.setSelectedItem(var2.getLogLevel());
                  this.levelDropDown.setEnabled(true);
                  break;
               }

               if (var10 || OSPLog.levels[var11].intValue() <= var9.getLogLevel().intValue()) {
                  this.levelDropDown.addItem(OSPLog.levels[var11]);
               }

               ++var11;
            }
         }

         this.authorField.setText(var2.getAuthor());
         this.authorField.setBackground(Color.white);
         this.keywordField.setText(var2.keywords);
         this.keywordField.setBackground(Color.white);
         this.commentPane.setText(var2.comment);
         this.commentPane.setBackground(Color.white);
         var10 = super.password != null && !super.password.equals("");
         this.onLoadCheckBox.setEnabled(var10);
         this.onLoadCheckBox.setSelected(var10 && super.pwRequiredToLoad);
         this.encryptCheckBox.setSelected(super.password != null);
         this.passwordEditor.setEnabled(this.encryptCheckBox.isSelected());
         this.passwordLabel.setEnabled(this.encryptCheckBox.isSelected());
         this.passwordEditor.setText(super.password);
         this.passwordEditor.setBackground(Color.white);
      }

      super.fileMenu.removeAll();
      super.fileMenu.add(this.newItem);
      if (super.undoManager.canUndo()) {
         super.fileMenu.add(super.backItem);
      }

      super.fileMenu.addSeparator();
      super.fileMenu.add(super.openItem);
      if (super.openFromJarMenu != null) {
         super.fileMenu.add(super.openFromJarMenu);
      }

      LaunchPanel var15 = this.getSelectedTab();
      boolean var17 = super.jarBasePath != null && !super.jarBasePath.equals("");
      this.saveAllItem.setEnabled(!var17 && this.isTabSetWritable());
      if (var15 != null) {
         super.fileMenu.add(this.importItem);
         super.fileMenu.addSeparator();
         super.fileMenu.add(super.closeTabItem);
         super.fileMenu.add(super.closeAllItem);
         super.fileMenu.addSeparator();
         super.fileMenu.add(this.previewItem);
         super.fileMenu.addSeparator();
         super.fileMenu.add(this.saveAllItem);
         super.fileMenu.add(this.saveSetAsItem);
         super.frame.getContentPane().add(this.toolbar, "North");
         var15.dataPanel.add(this.editorTabs, "Center");
         var15.textScroller.setBorder(this.htmlTitle);
         this.displaySplitPane.setTopComponent(var15.textScroller);
         if (this.getRootNode().getChildCount() == 0) {
            this.getRootNode().hiddenWhenRoot = false;
            this.hideRootCheckBox.setEnabled(false);
         } else {
            this.hideRootCheckBox.setEnabled(var2 == null || !var2.isButtonView());
         }

         boolean var18 = !this.getRootNode().hiddenWhenRoot;
         this.hideRootCheckBox.setSelected(!var18);
         var15.tree.setRootVisible(var18);
         if (this.getSelectedNode() == null && !var18) {
            var15.setSelectedNode((LaunchNode)this.getRootNode().getChildAt(0));
         }

         this.buttonViewCheckBox.setSelected(var2 != null && var2.isButtonView());
         this.buttonViewCheckBox.setEnabled(var18);
         this.editorEnabledCheckBox.setSelected(super.editorEnabled);
      } else {
         super.frame.getContentPane().remove(this.toolbar);
      }

      if (super.exitItem != null) {
         super.fileMenu.addSeparator();
         super.fileMenu.add(super.exitItem);
      }

      for(int var19 = 0; var19 < super.tabbedPane.getTabCount(); ++var19) {
         LaunchNode var21 = this.getTab(var19).getRootNode();
         if (var21.getFileName() != null) {
            super.tabbedPane.setIconAt(var19, this.getFileIcon(var21));
            super.tabbedPane.setToolTipTextAt(var19, LaunchRes.getString("ToolTip.FileName") + " \"" + var21.getFileName() + "\"");
         } else {
            super.tabbedPane.setIconAt(var19, (Icon)null);
            super.tabbedPane.setToolTipTextAt(var19, (String)null);
         }
      }

   }

   protected void createGUI(boolean var1) {
      Launcher.wInit = 600;
      Launcher.hInit = 540;
      this.labels = new ArrayList();
      super.createGUI(var1);
      super.frame.addWindowListener(new WindowAdapter() {
         public void windowOpened(WindowEvent var1) {
            if (LaunchBuilder.this.getSelectedNode() != null) {
               LaunchBuilder.this.getSelectedTab().textPane.setContentType(LaunchPanel.defaultType);
               LaunchBuilder.this.getSelectedTab().textPane.setText((String)null);
               LaunchBuilder.this.refreshGUI();
            }

         }
      });
      super.tabbedPane.addComponentListener(new ComponentAdapter() {
         public void componentResized(ComponentEvent var1) {
            LaunchBuilder.this.refreshGUI();
         }
      });
      String var2 = "/org/opensourcephysics/resources/tools/images/whitefile.gif";
      Launcher.whiteFileIcon = ResourceLoader.getIcon(var2);
      var2 = "/org/opensourcephysics/resources/tools/images/ghostfile.gif";
      Launcher.ghostFileIcon = ResourceLoader.getIcon(var2);
      var2 = "/org/opensourcephysics/resources/tools/images/redfile.gif";
      Launcher.redFileIcon = ResourceLoader.getIcon(var2);
      var2 = "/org/opensourcephysics/resources/tools/images/yellowfile.gif";
      Launcher.yellowFileIcon = ResourceLoader.getIcon(var2);
      var2 = "/org/opensourcephysics/resources/tools/images/whitefolder.gif";
      Launcher.whiteFolderIcon = ResourceLoader.getIcon(var2);
      var2 = "/org/opensourcephysics/resources/tools/images/redfolder.gif";
      Launcher.redFolderIcon = ResourceLoader.getIcon(var2);
      var2 = "/org/opensourcephysics/resources/tools/images/greenfolder.gif";
      Launcher.greenFolderIcon = ResourceLoader.getIcon(var2);
      var2 = "/org/opensourcephysics/resources/tools/images/yellowfolder.gif";
      Launcher.yellowFolderIcon = ResourceLoader.getIcon(var2);
      this.createActions();
      this.titleField = new JTextField();
      this.titleField.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent var1) {
            if (var1.getKeyCode() == 10) {
               String var2 = LaunchBuilder.this.titleField.getText();
               if (var2.equals("")) {
                  var2 = null;
               }

               if (var2 != LaunchBuilder.super.title) {
                  LaunchBuilder.super.changedFiles.add(LaunchBuilder.super.tabSetName);
               }

               LaunchBuilder.super.title = var2;
               LaunchBuilder.this.refreshGUI();
            } else {
               LaunchBuilder.this.titleField.setBackground(Color.yellow);
            }

         }
      });
      this.titleField.addFocusListener(new FocusAdapter() {
         public void focusLost(FocusEvent var1) {
            String var2 = LaunchBuilder.this.titleField.getText();
            if (var2.equals("")) {
               var2 = null;
            }

            if (var2 != LaunchBuilder.super.title) {
               LaunchBuilder.super.changedFiles.add(LaunchBuilder.super.tabSetName);
            }

            LaunchBuilder.super.title = var2;
            LaunchBuilder.this.refreshGUI();
         }
      });
      this.nameField = new JTextField();
      this.nameField.addKeyListener(this.keyListener);
      this.nameField.addFocusListener(this.focusListener);
      this.tooltipField = new JTextField();
      this.tooltipField.addKeyListener(this.keyListener);
      this.tooltipField.addFocusListener(this.focusListener);
      this.htmlTitle = BorderFactory.createTitledBorder(LaunchRes.getString("Label.HTML"));
      this.classField = new JTextField();
      this.classField.addKeyListener(this.keyListener);
      this.classField.addFocusListener(this.focusListener);
      this.argField = new JTextField();
      this.argField.addKeyListener(this.keyListener);
      this.argField.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent var1) {
            if (var1.isPopupTrigger() || var1.getButton() == 3 || var1.isControlDown() && System.getProperty("os.name", "").indexOf("Mac") > -1) {
               XMLControlElement var2 = new XMLControlElement();
               if (var2.read(LaunchBuilder.this.argField.getText()) != null) {
                  JPopupMenu var3 = new JPopupMenu();
                  JMenuItem var4 = new JMenuItem(LaunchRes.getString("MenuItem.EncryptionTool"));
                  var4.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent var1) {
                        EncryptionTool var2 = EncryptionTool.getTool();
                        var2.open(LaunchBuilder.this.argField.getText());
                        var2.setVisible(true);
                     }
                  });
                  var3.add(var4);
                  var3.show(LaunchBuilder.this.argField, var1.getX(), var1.getY() + 8);
               }
            }

         }
      });
      this.argField.addFocusListener(this.focusListener);
      this.jarField = new JTextField();
      this.jarField.addKeyListener(this.keyListener);
      this.jarField.addFocusListener(this.focusListener);
      this.urlField = new JTextField();
      this.urlField.addKeyListener(this.keyListener);
      this.urlField.addFocusListener(this.focusListener);
      this.keywordField = new JTextField();
      this.keywordField.addKeyListener(this.keyListener);
      this.keywordField.addFocusListener(this.focusListener);
      this.authorField = new JTextField();
      this.authorField.addKeyListener(this.keyListener);
      this.authorField.addFocusListener(this.focusListener);
      this.commentPane = new JTextPane();
      this.commentPane.addKeyListener(this.keyListener);
      this.commentPane.addFocusListener(this.focusListener);
      this.commentScroller = new JScrollPane(this.commentPane);
      this.commentTitle = BorderFactory.createTitledBorder(LaunchRes.getString("Label.Comments"));
      this.commentScroller.setBorder(this.commentTitle);
      this.descriptionPane = new JTextPane();
      this.descriptionPane.addKeyListener(this.keyListener);
      this.descriptionPane.addFocusListener(this.focusListener);
      this.descriptionScroller = new JScrollPane(this.descriptionPane);
      this.descriptionTitle = BorderFactory.createTitledBorder(LaunchRes.getString("Label.Description"));
      this.descriptionScroller.setBorder(this.descriptionTitle);
      this.displaySplitPane = new JSplitPane(0) {
         public void setDividerLocation(int var1) {
            super.setDividerLocation(var1);
            double var2 = (double)this.getDividerLocation();
            var2 /= (double)(this.getHeight() - this.getDividerSize());
            this.setName("" + var2);
         }

         public void setTopComponent(Component var1) {
            int var2 = this.getLastDividerLocation();
            String var3 = this.getName();
            super.setTopComponent(var1);
            if (var3 != null) {
               double var4 = Double.parseDouble(var3);
               var4 = Math.max(0.0D, var4);
               var4 = Math.min(1.0D, var4);
               this.setDividerLocation(var4);
               this.setLastDividerLocation(var2);
               Runnable var6 = new Runnable() {
                  public void run() {
                     JViewport var1 = LaunchBuilder.this.descriptionScroller.getViewport();
                     var1.setViewPosition(new Point(0, 0));
                  }
               };
               SwingUtilities.invokeLater(var6);
            }

         }
      };
      this.displaySplitPane.setBottomComponent(this.descriptionScroller);
      this.displaySplitPane.setOneTouchExpandable(true);
      this.displaySplitPane.setResizeWeight(0.5D);
      this.hiddenCheckBox = new JCheckBox();
      this.hiddenCheckBox.addActionListener(this.changeAction);
      this.hiddenCheckBox.setContentAreaFilled(false);
      this.hiddenCheckBox.setAlignmentX(0.0F);
      this.buttonViewCheckBox = new JCheckBox();
      this.buttonViewCheckBox.addActionListener(this.changeAction);
      this.buttonViewCheckBox.setContentAreaFilled(false);
      this.buttonViewCheckBox.setAlignmentX(0.0F);
      JPanel var3 = new JPanel(new BorderLayout());
      JToolBar var4 = new JToolBar();
      var4.setFloatable(false);
      this.nameLabel = new JLabel();
      this.labels.add(this.nameLabel);
      var4.add(this.nameLabel);
      var4.add(this.nameField);
      var4.add(this.hiddenCheckBox);
      var3.add(var4, "North");
      JPanel var5 = new JPanel(new BorderLayout());
      var3.add(var5, "Center");
      JToolBar var6 = new JToolBar();
      var6.setFloatable(false);
      this.tooltipLabel = new JLabel();
      this.labels.add(this.tooltipLabel);
      var6.add(this.tooltipLabel);
      var6.add(this.tooltipField);
      var5.add(var6, "North");
      this.urlPanel = new JPanel(new BorderLayout());
      var5.add(this.urlPanel, "Center");
      JToolBar var7 = new JToolBar();
      var7.setFloatable(false);
      this.urlLabel = new JLabel();
      this.labels.add(this.urlLabel);
      var7.add(this.urlLabel);
      var7.add(this.urlField);
      var7.add(this.openURLAction);
      this.urlPanel.add(var7, "North");
      this.urlPanel.add(this.displaySplitPane, "Center");
      JPanel var8 = new JPanel(new BorderLayout());
      JToolBar var9 = new JToolBar();
      var9.setFloatable(false);
      this.jarLabel = new JLabel();
      this.labels.add(this.jarLabel);
      var9.add(this.jarLabel);
      var9.add(this.jarField);
      var9.add(this.openJarAction);
      var8.add(var9, "North");
      JPanel var10 = new JPanel(new BorderLayout());
      var8.add(var10, "Center");
      JToolBar var11 = new JToolBar();
      var11.setFloatable(false);
      this.classLabel = new JLabel();
      this.labels.add(this.classLabel);
      var11.add(this.classLabel);
      var11.add(this.classField);
      var11.add(this.searchJarAction);
      var10.add(var11, "North");
      JPanel var12 = new JPanel(new BorderLayout());
      var10.add(var12, "Center");
      JToolBar var13 = new JToolBar();
      var13.setFloatable(false);
      this.argLabel = new JLabel();
      this.labels.add(this.argLabel);
      var13.add(this.argLabel);
      SpinnerNumberModel var14 = new SpinnerNumberModel(0, 0, maxArgs - 1, 1);
      this.argSpinner = new JSpinner(var14);
      NumberEditor var15 = new NumberEditor(this.argSpinner);
      this.argSpinner.setEditor(var15);
      this.argSpinner.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent var1) {
            if (LaunchBuilder.this.argField.getBackground() == Color.yellow) {
               LaunchBuilder.this.refreshSelectedNode();
            } else {
               LaunchBuilder.this.refreshGUI();
            }

         }
      });
      var13.add(this.argSpinner);
      var13.add(this.argField);
      var13.add(this.openArgAction);
      var12.add(var13, "North");
      JPanel var16 = new JPanel(new BorderLayout());
      var12.add(var16, "Center");
      JToolBar var17 = new JToolBar();
      var17.setFloatable(false);
      this.singleVMCheckBox = new JCheckBox();
      this.singleVMCheckBox.addActionListener(this.changeAction);
      this.singleVMCheckBox.setContentAreaFilled(false);
      this.singleVMCheckBox.setAlignmentX(0.0F);
      this.showLogCheckBox = new JCheckBox();
      this.showLogCheckBox.addActionListener(this.changeAction);
      this.showLogCheckBox.setContentAreaFilled(false);
      this.showLogCheckBox.setAlignmentX(0.0F);
      this.clearLogCheckBox = new JCheckBox();
      this.clearLogCheckBox.addActionListener(this.changeAction);
      this.clearLogCheckBox.setContentAreaFilled(false);
      this.clearLogCheckBox.setAlignmentX(0.0F);
      this.singletonCheckBox = new JCheckBox();
      this.singletonCheckBox.addActionListener(this.changeAction);
      this.singletonCheckBox.setContentAreaFilled(false);
      this.singletonCheckBox.setAlignmentX(0.0F);
      this.singleAppCheckBox = new JCheckBox();
      this.singleAppCheckBox.addActionListener(this.changeAction);
      this.singleAppCheckBox.setContentAreaFilled(false);
      this.singleAppCheckBox.setAlignmentX(0.0F);
      this.levelDropDown = new JComboBox(OSPLog.levels);
      this.levelDropDown.setMaximumSize(this.levelDropDown.getMinimumSize());
      this.levelDropDown.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            if (LaunchBuilder.this.levelDropDown.isEnabled()) {
               LaunchNode var2 = LaunchBuilder.this.getSelectedNode();
               if (var2 != null) {
                  var2.setLogLevel((Level)LaunchBuilder.this.levelDropDown.getSelectedItem());
               }
            }

         }
      });
      Box var18 = Box.createVerticalBox();
      JToolBar var19 = new JToolBar();
      var19.setFloatable(false);
      var19.setAlignmentX(0.0F);
      var19.add(this.singletonCheckBox);
      var19.add(Box.createHorizontalGlue());
      var18.add(var19);
      var19 = new JToolBar();
      var19.setFloatable(false);
      var19.setAlignmentX(0.0F);
      var19.add(this.singleVMCheckBox);
      var19.add(Box.createHorizontalGlue());
      var18.add(var19);
      var19 = new JToolBar();
      var19.setFloatable(false);
      var19.setAlignmentX(0.0F);
      var19.add(this.singleAppCheckBox);
      var19.add(Box.createHorizontalGlue());
      var18.add(var19);
      var19 = new JToolBar();
      var19.setFloatable(false);
      var19.add(this.showLogCheckBox);
      var19.add(Box.createHorizontalStrut(4));
      var19.add(this.levelDropDown);
      var19.add(Box.createHorizontalStrut(4));
      var19.add(this.clearLogCheckBox);
      var19.add(Box.createHorizontalGlue());
      var19.setAlignmentX(0.0F);
      var18.add(var19);
      this.optionsTitle = BorderFactory.createTitledBorder(LaunchRes.getString("Label.Options"));
      Border var20 = BorderFactory.createLoweredBevelBorder();
      var17.setBorder(BorderFactory.createCompoundBorder(var20, this.optionsTitle));
      var17.add(var18);
      var16.add(var17, "North");
      JPanel var21 = new JPanel(new BorderLayout());
      JToolBar var22 = new JToolBar();
      var21.add(var22, "North");
      var22.setFloatable(false);
      this.authorLabel = new JLabel();
      this.labels.add(this.authorLabel);
      var22.add(this.authorLabel);
      var22.add(this.authorField);
      JPanel var23 = new JPanel(new BorderLayout());
      var21.add(var23, "Center");
      JToolBar var24 = new JToolBar();
      var23.add(var24, "North");
      var24.setFloatable(false);
      this.keywordLabel = new JLabel();
      this.labels.add(this.keywordLabel);
      var24.add(this.keywordLabel);
      var24.add(this.keywordField);
      JPanel var25 = new JPanel(new BorderLayout());
      var23.add(var25, "Center");
      JToolBar var26 = new JToolBar();
      var26.setFloatable(false);
      var25.add(var26, "North");
      this.securityTitle = BorderFactory.createTitledBorder(LaunchRes.getString("Label.Security"));
      var26.setBorder(BorderFactory.createCompoundBorder(var20, this.securityTitle));
      Box var27 = Box.createVerticalBox();
      var26.add(var27);
      var19 = new JToolBar();
      var19.setFloatable(false);
      var19.setAlignmentX(0.0F);
      this.editorEnabledCheckBox = new JCheckBox();
      this.editorEnabledCheckBox.addActionListener(this.changeAction);
      this.editorEnabledCheckBox.setContentAreaFilled(false);
      this.editorEnabledCheckBox.setAlignmentX(0.0F);
      var19.add(this.editorEnabledCheckBox);
      var19.add(Box.createHorizontalGlue());
      var27.add(var19);
      var19 = new JToolBar();
      var19.setFloatable(false);
      var19.setAlignmentX(0.0F);
      this.encryptCheckBox = new JCheckBox();
      this.encryptCheckBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            if (LaunchBuilder.this.encryptCheckBox.isSelected() && LaunchBuilder.super.password == null) {
               LaunchBuilder.super.password = "";
            } else if (!LaunchBuilder.this.encryptCheckBox.isSelected()) {
               LaunchBuilder.super.password = null;
            }

            OSPLog.finest(LaunchRes.getString("Log.Message.ChangeEncrypted"));
            if (LaunchBuilder.super.tabSetName != null) {
               LaunchBuilder.super.changedFiles.add(LaunchBuilder.super.tabSetName);
            }

            LaunchBuilder.this.refreshGUI();
         }
      });
      this.encryptCheckBox.setContentAreaFilled(false);
      var19.add(this.encryptCheckBox);
      var19.add(Box.createHorizontalGlue());
      var27.add(var19);
      var19 = new JToolBar();
      var19.setFloatable(false);
      var19.setAlignmentX(0.0F);
      this.passwordLabel = new JLabel();
      this.passwordLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 2));
      var19.add(this.passwordLabel);
      this.passwordEditor = new JTextField();
      this.passwordEditor.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent var1) {
            if (var1.getKeyCode() == 10) {
               String var2 = LaunchBuilder.this.passwordEditor.getText();
               if (var2.equals("") && (!LaunchBuilder.this.encryptCheckBox.isSelected() || !LaunchBuilder.this.encryptCheckBox.isEnabled())) {
                  var2 = null;
               }

               if (var2 != LaunchBuilder.super.password) {
                  LaunchBuilder.super.changedFiles.add(LaunchBuilder.super.tabSetName);
               }

               LaunchBuilder.super.password = var2;
               LaunchBuilder.this.refreshGUI();
            } else {
               LaunchBuilder.this.passwordEditor.setBackground(Color.yellow);
            }

         }
      });
      var19.add(this.passwordEditor);
      var19.add(Box.createHorizontalGlue());
      this.onLoadCheckBox = new JCheckBox();
      this.onLoadCheckBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            LaunchBuilder.super.pwRequiredToLoad = LaunchBuilder.this.onLoadCheckBox.isSelected();
            OSPLog.finest(LaunchRes.getString("Log.Message.ChangePWRequirement"));
            if (LaunchBuilder.super.tabSetName != null) {
               LaunchBuilder.super.changedFiles.add(LaunchBuilder.super.tabSetName);
            }

            LaunchBuilder.this.refreshGUI();
         }
      });
      this.onLoadCheckBox.setContentAreaFilled(false);
      var19.add(this.onLoadCheckBox);
      var27.add(var19);
      var25.add(this.commentScroller, "Center");
      this.editorTabs = new JTabbedPane(1);
      this.editorTabs.addTab(LaunchRes.getString("Tab.Display"), var3);
      this.editorTabs.addTab(LaunchRes.getString("Tab.Launch"), var8);
      this.editorTabs.addTab(LaunchRes.getString("Tab.Author"), var21);
      this.toolbar = new JToolBar();
      this.toolbar.setFloatable(false);
      this.toolbar.setRollover(true);
      this.toolbar.setBorder(BorderFactory.createLineBorder(Color.gray));
      super.frame.getContentPane().add(this.toolbar, "North");
      this.newTabButton = new JButton(this.newTabAction);
      this.toolbar.add(this.newTabButton);
      this.addButton = new JButton(this.addAction);
      this.toolbar.add(this.addButton);
      this.cutButton = new JButton(this.cutAction);
      this.toolbar.add(this.cutButton);
      this.copyButton = new JButton(this.copyAction);
      this.toolbar.add(this.copyButton);
      this.pasteButton = new JButton(this.pasteAction);
      this.toolbar.add(this.pasteButton);
      this.moveUpButton = new JButton(this.moveUpAction);
      this.toolbar.add(this.moveUpButton);
      this.moveDownButton = new JButton(this.moveDownAction);
      this.toolbar.add(this.moveDownButton);
      this.titleLabel = new JLabel(LaunchRes.getString("Label.Title"));
      this.titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 2));
      this.toolbar.add(this.titleLabel);
      this.toolbar.add(this.titleField);
      this.toolbar.add(this.buttonViewCheckBox);
      this.hideRootCheckBox = new JCheckBox();
      this.hideRootCheckBox.addActionListener(this.changeAction);
      this.hideRootCheckBox.setContentAreaFilled(false);
      this.toolbar.add(this.hideRootCheckBox);
      int var28 = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
      this.newItem = new JMenuItem(this.newTabSetAction);
      this.newItem.setAccelerator(KeyStroke.getKeyStroke(78, var28));
      this.previewItem = new JMenuItem();
      this.previewItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            String var2 = Launcher.tabSetBasePath;
            LaunchBuilder.super.previewing = true;
            Launcher.LaunchSet var3 = LaunchBuilder.this.new LaunchSet(LaunchBuilder.this, LaunchBuilder.super.tabSetName);
            XMLControlElement var4 = new XMLControlElement(var3);
            var4.setValue("filename", LaunchBuilder.super.tabSetName);
            Launcher var5 = new Launcher(var4.toXML());
            Point var6 = LaunchBuilder.super.frame.getLocation();
            var5.frame.setLocation(var6.x + 24, var6.y + 24);
            var5.frame.setVisible(true);
            var5.frame.setDefaultCloseOperation(2);
            Launcher.tabSetBasePath = var2;
            LaunchBuilder.super.previewing = false;
            var5.password = LaunchBuilder.super.password;
            var5.previewing = true;
            var5.spawner = LaunchBuilder.this;
            var5.refreshGUI();
         }
      });
      this.importItem = new JMenuItem(this.importAction);
      this.saveNodeItem = new JMenuItem(this.saveAction);
      this.saveNodeAsItem = new JMenuItem(this.saveAsAction);
      this.saveAllItem = new JMenuItem(this.saveAllAction);
      this.openTabItem = new JMenuItem(this.openTabAction);
      this.saveAllItem.setAccelerator(KeyStroke.getKeyStroke(83, var28));
      this.saveSetAsItem = new JMenuItem(this.saveSetAsAction);
      this.toolsMenu = new JMenu();
      super.frame.getJMenuBar().add(this.toolsMenu, 2);
      this.encryptionToolItem = new JMenuItem();
      this.toolsMenu.add(this.encryptionToolItem);
      this.encryptionToolItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            EncryptionTool.getTool().setVisible(true);
         }
      });
      super.tabbedPane.removeMouseListener(super.tabListener);
      super.tabListener = new MouseAdapter() {
         public void mousePressed(MouseEvent var1) {
            if (LaunchBuilder.super.contentPane.getTopLevelAncestor() == LaunchBuilder.super.frame) {
               if (var1.isPopupTrigger() || var1.getButton() == 3 || var1.isControlDown() && System.getProperty("os.name", "").indexOf("Mac") > -1) {
                  JPopupMenu var2 = new JPopupMenu();
                  JMenuItem var3 = new JMenuItem(LaunchRes.getString("MenuItem.Close"));
                  var3.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent var1) {
                        LaunchBuilder.this.removeSelectedTab();
                     }
                  });
                  var2.add(var3);
                  var2.addSeparator();
                  var3 = new JMenuItem(LaunchRes.getString("Menu.File.SaveAs"));
                  var3.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent var1) {
                        LaunchNode var2 = LaunchBuilder.this.getSelectedTab().getRootNode();
                        if (LaunchBuilder.this.saveAs(var2) != null) {
                           int var3 = LaunchBuilder.this.tabbedPane.getSelectedIndex();
                           LaunchBuilder.this.tabbedPane.setTitleAt(var3, var2.toString());
                        }

                        LaunchBuilder.this.refreshGUI();
                     }
                  });
                  var2.add(var3);
                  final int var4 = LaunchBuilder.super.tabbedPane.getSelectedIndex();
                  if (var4 > 0 || var4 < LaunchBuilder.super.tabbedPane.getTabCount() - 1) {
                     var2.addSeparator();
                  }

                  if (var4 < LaunchBuilder.super.tabbedPane.getTabCount() - 1) {
                     var3 = new JMenuItem(LaunchRes.getString("Popup.MenuItem.MoveUp"));
                     var3.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent var1) {
                           LaunchPanel var2 = LaunchBuilder.this.getSelectedTab();
                           LaunchNode var3 = var2.getRootNode();
                           LaunchBuilder.this.removeSelectedTab();
                           LaunchBuilder.this.tabbedPane.insertTab(Launcher.getDisplayName(var3.getFileName()), (Icon)null, var2, (String)null, var4 + 1);
                           LaunchBuilder.this.tabbedPane.setSelectedComponent(var2);
                           int var10001 = var4 + 1;
                           LaunchBuilder.this.tabs.add(var10001, var2);
                        }
                     });
                     var2.add(var3);
                  }

                  if (var4 > 0) {
                     var3 = new JMenuItem(LaunchRes.getString("Popup.MenuItem.MoveDown"));
                     var3.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent var1) {
                           LaunchPanel var2 = LaunchBuilder.this.getSelectedTab();
                           LaunchNode var3 = var2.getRootNode();
                           LaunchBuilder.this.removeSelectedTab();
                           LaunchBuilder.this.tabbedPane.insertTab(Launcher.getDisplayName(var3.getFileName()), (Icon)null, var2, (String)null, var4 - 1);
                           LaunchBuilder.this.tabbedPane.setSelectedComponent(var2);
                           int var10001 = var4 - 1;
                           LaunchBuilder.this.tabs.add(var10001, var2);
                        }
                     });
                     var2.add(var3);
                  }

                  var2.show(LaunchBuilder.super.tabbedPane, var1.getX(), var1.getY() + 8);
               }

            }
         }
      };
      super.tabbedPane.addMouseListener(super.tabListener);
      super.frame.pack();
      this.displaySplitPane.setDividerLocation(0.7D);
   }

   protected void setFontLevel(int var1) {
      final int var2 = this.displaySplitPane.getLastDividerLocation();
      final String var3 = this.displaySplitPane.getName();
      FontSizer.setFonts(this.htmlTitle, var1);
      FontSizer.setFonts(this.commentTitle, var1);
      FontSizer.setFonts(this.descriptionTitle, var1);
      FontSizer.setFonts(this.optionsTitle, var1);
      FontSizer.setFonts(this.securityTitle, var1);
      super.setFontLevel(var1);
      if (var3 != null) {
         Runnable var4 = new Runnable() {
            public void run() {
               double var1 = Double.parseDouble(var3);
               var1 = Math.max(0.0D, var1);
               var1 = Math.min(1.0D, var1);
               LaunchBuilder.this.displaySplitPane.setDividerLocation(var1);
               LaunchBuilder.this.displaySplitPane.setLastDividerLocation(var2);
            }
         };
         SwingUtilities.invokeLater(var4);
      }

   }

   protected void createActions() {
      String var1 = "/org/opensourcephysics/resources/tools/images/open.gif";
      ImageIcon var2 = new ImageIcon((class$org$opensourcephysics$tools$Launcher == null ? (class$org$opensourcephysics$tools$Launcher = class$("org.opensourcephysics.tools.Launcher")) : class$org$opensourcephysics$tools$Launcher).getResource(var1));
      this.openJarAction = new AbstractAction((String)null, var2) {
         public void actionPerformed(ActionEvent var1) {
            JFileChooser var2 = LaunchBuilder.getJARChooser();
            int var3 = var2.showOpenDialog((Component)null);
            if (var3 == 0) {
               File var4 = var2.getSelectedFile();
               String var5 = XML.getRelativePath(var4.getPath());
               String var6 = LaunchBuilder.this.jarField.getText();
               if (var6.indexOf(var5) > -1) {
                  var5 = null;
               }

               if (!var6.equals("")) {
                  var6 = var6 + ";";
               }

               if (var5 != null) {
                  LaunchBuilder.this.jarField.setText(var6 + var5);
               }

               OSPFrame.chooserDir = XML.getDirectoryPath(var4.getPath());
               LaunchBuilder.this.searchJarAction.setEnabled(true);
               LaunchBuilder.this.refreshSelectedNode();
            }

         }
      };
      this.searchJarAction = new AbstractAction((String)null, var2) {
         public void actionPerformed(ActionEvent var1) {
            LaunchNode var2 = LaunchBuilder.this.getSelectedNode();
            if (var2 != null && LaunchBuilder.this.getClassChooser().chooseClassFor(var2)) {
               if (var2.getOwner() != null) {
                  LaunchBuilder.super.changedFiles.add(var2.getOwner().getFileName());
               } else {
                  LaunchBuilder.super.changedFiles.add(LaunchBuilder.super.tabSetName);
               }

               LaunchBuilder.this.refreshClones(var2);
               LaunchBuilder.this.refreshGUI();
            }

         }
      };
      this.searchJarAction.setEnabled(false);
      this.openArgAction = new AbstractAction((String)null, var2) {
         public void actionPerformed(ActionEvent var1) {
            JFileChooser var2 = LaunchBuilder.getFileChooser();
            int var3 = var2.showOpenDialog((Component)null);
            if (var3 == 0) {
               File var4 = var2.getSelectedFile();
               LaunchBuilder.this.argField.setText(XML.getRelativePath(var4.getPath()));
               OSPFrame.chooserDir = XML.getDirectoryPath(var4.getPath());
               LaunchBuilder.this.refreshSelectedNode();
            }

         }
      };
      this.openURLAction = new AbstractAction((String)null, var2) {
         public void actionPerformed(ActionEvent var1) {
            JFileChooser var2 = LaunchBuilder.getHTMLChooser();
            int var3 = var2.showOpenDialog((Component)null);
            if (var3 == 0) {
               File var4 = var2.getSelectedFile();
               LaunchBuilder.this.urlField.setText(XML.getRelativePath(var4.getPath()));
               OSPFrame.chooserDir = XML.getDirectoryPath(var4.getPath());
               LaunchBuilder.this.refreshSelectedNode();
            }

         }
      };
      this.openTabAction = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            LaunchNode var2 = LaunchBuilder.this.getSelectedNode();
            String var3 = var2.toString();

            for(int var4 = 0; var4 < LaunchBuilder.super.tabbedPane.getComponentCount(); ++var4) {
               if (LaunchBuilder.super.tabbedPane.getTitleAt(var4).equals(var3)) {
                  LaunchBuilder.super.tabbedPane.setSelectedIndex(var4);
                  return;
               }
            }

            XMLControlElement var7 = new XMLControlElement(var2);
            XMLControlElement var5 = new XMLControlElement(var7);
            LaunchNode var6 = (LaunchNode)var5.loadObject((Object)null);
            var6.setFileName(var2.getFileName());
            LaunchBuilder.this.addTab(var6);
            LaunchBuilder.this.refreshGUI();
         }
      };
      this.changeAction = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            LaunchBuilder.this.refreshSelectedNode();
         }
      };
      this.newTabSetAction = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            Launcher.LinkEdit var2 = null;
            if (LaunchBuilder.super.tabSetName != null && (new File(LaunchBuilder.super.tabSetName)).exists()) {
               String[] var3 = new String[]{" "};
               var2 = LaunchBuilder.this.new LinkEdit(var3);
            }

            if (LaunchBuilder.this.removeAllTabs()) {
               if (var2 != null) {
                  LaunchBuilder.super.undoSupport.postEdit(var2);
               }

               LaunchNode var4 = new LaunchNode(LaunchRes.getString("NewTab.Name"));
               LaunchBuilder.this.addTab(var4);
            }

         }
      };
      this.addAction = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            LaunchNode var2 = new LaunchNode(LaunchRes.getString("NewNode.Name"));
            LaunchBuilder.this.addChildToSelectedNode(var2);
         }
      };
      this.newTabAction = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            LaunchNode var2 = new LaunchNode(LaunchRes.getString("NewTab.Name"));
            LaunchBuilder.this.addTab(var2);
         }
      };
      this.cutAction = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            LaunchBuilder.this.copyAction.actionPerformed((ActionEvent)null);
            LaunchBuilder.this.removeSelectedNode();
         }
      };
      this.copyAction = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            LaunchNode var2 = LaunchBuilder.this.getSelectedNode();
            if (var2 != null) {
               XMLControlElement var3 = new XMLControlElement(var2);
               var3.setValue("filename", var2.getFileName());
               StringSelection var4 = new StringSelection(var3.toXML());
               Clipboard var5 = Toolkit.getDefaultToolkit().getSystemClipboard();
               var5.setContents(var4, var4);
            }

         }
      };
      this.pasteAction = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            try {
               Clipboard var2 = Toolkit.getDefaultToolkit().getSystemClipboard();
               Transferable var3 = var2.getContents((Object)null);
               String var4 = (String)var3.getTransferData(DataFlavor.stringFlavor);
               if (var4 != null) {
                  XMLControlElement var5 = new XMLControlElement();
                  var5.readXML(var4);
                  if (var5.getObjectClass() == (LaunchBuilder.class$org$opensourcephysics$tools$LaunchNode == null ? (LaunchBuilder.class$org$opensourcephysics$tools$LaunchNode = LaunchBuilder.class$("org.opensourcephysics.tools.LaunchNode")) : LaunchBuilder.class$org$opensourcephysics$tools$LaunchNode)) {
                     String var6 = var5.getString("filename");
                     LaunchNode var7 = (LaunchNode)var5.loadObject((Object)null);
                     var7.setFileName(var6);
                     LaunchBuilder.this.addChildToSelectedNode(var7);
                  }
               }
            } catch (UnsupportedFlavorException var8) {
            } catch (IOException var9) {
            } catch (HeadlessException var10) {
            }

         }
      };
      this.importAction = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            Launcher.getXMLChooser().setFileFilter(Launcher.xmlFileFilter);
            int var2 = Launcher.getXMLChooser().showOpenDialog((Component)null);
            if (var2 == 0) {
               File var3 = Launcher.getXMLChooser().getSelectedFile();
               String var4 = var3.getAbsolutePath();
               OSPFrame.chooserDir = XML.getDirectoryPath(var3.getPath());
               XMLControlElement var5 = new XMLControlElement(var4);
               if (var5.failedToRead()) {
                  OSPLog.info(LaunchRes.getString("Log.Message.InvalidXML") + " " + var4);
                  JOptionPane.showMessageDialog((Component)null, LaunchRes.getString("Dialog.InvalidXML.Message") + " \"" + XML.getName(var4) + "\"", LaunchRes.getString("Dialog.InvalidXML.Title"), 2);
                  return;
               }

               if (var5.getObjectClass() == (LaunchBuilder.class$org$opensourcephysics$tools$LaunchNode == null ? (LaunchBuilder.class$org$opensourcephysics$tools$LaunchNode = LaunchBuilder.class$("org.opensourcephysics.tools.LaunchNode")) : LaunchBuilder.class$org$opensourcephysics$tools$LaunchNode)) {
                  LaunchNode var6 = (LaunchNode)var5.loadObject((Object)null);
                  var6.setFileName(var4);
                  LaunchBuilder.this.addChildToSelectedNode(var6);
               } else {
                  OSPLog.info(LaunchRes.getString("Log.Message.NotLauncherFile") + " " + var4);
                  JOptionPane.showMessageDialog((Component)null, LaunchRes.getString("Dialog.NotLauncherFile.Message") + " \"" + XML.getName(var4) + "\"", LaunchRes.getString("Dialog.NotLauncherFile.Title"), 2);
               }
            }

         }
      };
      this.saveAction = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            LaunchNode var2 = LaunchBuilder.this.getSelectedNode();
            if (var2.getFileName() != null) {
               LaunchBuilder.this.save(var2, var2.getFileName());
               LaunchBuilder.this.refreshGUI();
            }

         }
      };
      this.saveAsAction = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            LaunchNode var2 = LaunchBuilder.this.getSelectedNode();
            LaunchNode var3 = (LaunchNode)var2.getParent();
            String var4 = LaunchBuilder.this.saveAs(var2);
            if (var4 != null) {
               LaunchBuilder.super.selfContained = false;

               LaunchNode var6;
               for(Enumeration var5 = var2.pathFromAncestorEnumeration(var2.getRoot()); var5.hasMoreElements(); var6.parentSelfContained = false) {
                  var6 = (LaunchNode)var5.nextElement();
                  var6.setSelfContained(false);
               }

               if (var3 != null) {
                  if (var3.getOwner() != null) {
                     LaunchBuilder.super.changedFiles.add(var3.getOwner().getFileName());
                  }

                  LaunchBuilder.this.refreshClones(var3);
               }
            }

            LaunchBuilder.this.refreshGUI();
         }
      };
      this.saveAllAction = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            if (LaunchBuilder.super.tabSetName.equals(LaunchRes.getString("Tabset.Name.New"))) {
               LaunchBuilder.this.saveTabSetAs();
            } else {
               LaunchBuilder.this.saveTabSet();
            }

            LaunchBuilder.this.refreshGUI();
         }
      };
      this.saveSetAsAction = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            LaunchBuilder.this.saveTabSetAs();
            LaunchBuilder.this.refreshGUI();
         }
      };
      this.moveUpAction = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            LaunchNode var2 = LaunchBuilder.this.getSelectedNode();
            if (var2 != null) {
               LaunchNode var3 = (LaunchNode)var2.getParent();
               if (var3 != null) {
                  int var4 = var3.getIndex(var2);
                  if (var4 > 0) {
                     LaunchBuilder.this.getSelectedTab().treeModel.removeNodeFromParent(var2);
                     LaunchBuilder.this.getSelectedTab().treeModel.insertNodeInto(var2, var3, var4 - 1);
                     LaunchBuilder.this.getSelectedTab().setSelectedNode(var2);
                     if (var3.getOwner() != null) {
                        LaunchBuilder.super.changedFiles.add(var3.getOwner().getFileName());
                     } else {
                        LaunchBuilder.super.changedFiles.add(LaunchBuilder.super.tabSetName);
                     }

                     LaunchBuilder.this.refreshGUI();
                  }

               }
            }
         }
      };
      this.moveDownAction = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            LaunchNode var2 = LaunchBuilder.this.getSelectedNode();
            if (var2 != null) {
               LaunchNode var3 = (LaunchNode)var2.getParent();
               if (var3 != null) {
                  int var4 = var3.getIndex(var2);
                  int var5 = var3.getChildCount();
                  if (var4 < var5 - 1) {
                     LaunchBuilder.this.getSelectedTab().treeModel.removeNodeFromParent(var2);
                     LaunchBuilder.this.getSelectedTab().treeModel.insertNodeInto(var2, var3, var4 + 1);
                     LaunchBuilder.this.getSelectedTab().setSelectedNode(var2);
                     if (var3.getOwner() != null) {
                        LaunchBuilder.super.changedFiles.add(var3.getOwner().getFileName());
                     } else {
                        LaunchBuilder.super.changedFiles.add(LaunchBuilder.super.tabSetName);
                     }

                     LaunchBuilder.this.refreshGUI();
                  }

               }
            }
         }
      };
      this.focusListener = new FocusAdapter() {
         public void focusLost(FocusEvent var1) {
            LaunchBuilder.this.refreshSelectedNode();
            LaunchBuilder.this.refreshGUI();
         }
      };
      this.keyListener = new KeyAdapter() {
         public void keyPressed(KeyEvent var1) {
            JComponent var2 = (JComponent)var1.getSource();
            if (var1.getKeyCode() != 10 || (var2 == LaunchBuilder.this.descriptionPane || var2 == LaunchBuilder.this.commentPane) && !var1.isControlDown() && !var1.isShiftDown()) {
               var2.setBackground(Color.yellow);
            } else {
               LaunchBuilder.this.refreshSelectedNode();
               LaunchBuilder.this.refreshGUI();
            }

         }
      };
   }

   protected void removeSelectedNode() {
      LaunchNode var1 = this.getSelectedNode();
      if (var1 != null && var1.getParent() != null) {
         LaunchNode var2 = (LaunchNode)var1.getParent();
         this.getSelectedTab().treeModel.removeNodeFromParent(var1);
         this.getSelectedTab().setSelectedNode(var2);
         if (var2.getOwner() != null) {
            super.changedFiles.add(var2.getOwner().getFileName());
         } else {
            super.changedFiles.add(super.tabSetName);
         }

         this.refreshClones(var2);
         this.refreshGUI();
      } else {
         Toolkit.getDefaultToolkit().beep();
      }
   }

   protected void addChildToSelectedNode(LaunchNode var1) {
      LaunchNode var2 = this.getSelectedNode();
      if (var2 != null && var1 != null) {
         LaunchNode[] var3 = var1.getAllOwnedNodes();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            LaunchNode var5 = this.getSelectedTab().getClone(var3[var4]);
            if (var5 != null) {
               this.getSelectedTab().setSelectedNode(var5);
               JOptionPane.showMessageDialog(super.frame, LaunchRes.getString("Dialog.DuplicateNode.Message") + " \"" + var5 + "\"", LaunchRes.getString("Dialog.DuplicateNode.Title"), 2);
               return;
            }
         }

         this.getSelectedTab().treeModel.insertNodeInto(var1, var2, var2.getChildCount());
         this.getSelectedTab().tree.scrollPathToVisible(new TreePath(var1.getPath()));
         var1.setLaunchClass(var1.launchClassName);
         if (var2.getOwner() != null) {
            super.changedFiles.add(var2.getOwner().getFileName());
         } else {
            super.changedFiles.add(super.tabSetName);
         }

         this.refreshClones(var2);
         this.refreshGUI();
      }

   }

   protected void refreshClones(LaunchNode var1) {
      Map var2 = this.getClones(var1);
      this.replaceClones(var1, var2);
   }

   protected void replaceClones(LaunchNode var1, Map var2) {
      if (!var2.isEmpty()) {
         XMLControlElement var3 = new XMLControlElement(var1.getOwner());
         Iterator var4 = var2.keySet().iterator();

         while(var4.hasNext()) {
            LaunchPanel var5 = (LaunchPanel)var4.next();
            LaunchNode var6 = (LaunchNode)var2.get(var5);
            LaunchNode var7 = (LaunchNode)var6.getParent();
            boolean var8 = var5.tree.isExpanded(new TreePath(var6.getPath()));
            if (var7 != null) {
               int var9 = var7.getIndex(var6);
               var5.treeModel.removeNodeFromParent(var6);
               var6 = (LaunchNode)(new XMLControlElement(var3)).loadObject((Object)null);
               var6.setFileName(var1.getFileName());
               var5.treeModel.insertNodeInto(var6, var7, var9);
            } else {
               var6 = (LaunchNode)(new XMLControlElement(var3)).loadObject((Object)null);
               var6.setFileName(var1.getFileName());
               var5.treeModel.setRoot(var6);
            }

            if (var8) {
               var5.tree.expandPath(new TreePath(var6.getPath()));
            }
         }

      }
   }

   protected Map getClones(LaunchNode var1) {
      HashMap var2 = new HashMap();
      var1 = var1.getOwner();
      if (var1 == null) {
         return var2;
      } else {
         Component[] var3 = super.tabbedPane.getComponents();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            LaunchPanel var5 = (LaunchPanel)var3[var4];
            LaunchNode var6 = var5.getClone(var1);
            if (var6 != null && var6 != var1) {
               var2.put(var5, var6);
            }
         }

         return var2;
      }
   }

   protected void showAboutDialog() {
      String var1 = XML.NEW_LINE;
      String var2 = "LaunchBuilder " + Launcher.version + "   " + Launcher.releaseDate + var1 + "Open Source Physics Project" + var1 + "www.opensourcephysics.org";
      JOptionPane.showMessageDialog(super.frame, var2, LaunchRes.getString("Help.About.Title") + " LaunchBuilder", 1);
   }

   protected static JFileChooser getJARChooser() {
      getFileChooser().setFileFilter(jarFileFilter);
      return fileChooser;
   }

   protected static JFileChooser getHTMLChooser() {
      getFileChooser().setFileFilter(htmlFileFilter);
      return fileChooser;
   }

   protected static JFileChooser getFileChooser() {
      if (fileChooser == null) {
         fileChooser = new JFileChooser(new File(OSPFrame.chooserDir));
         allFileFilter = fileChooser.getFileFilter();
         jarFileFilter = new FileFilter() {
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

                  return var2 != null && var2.equals("jar");
               }
            }

            public String getDescription() {
               return LaunchRes.getString("FileChooser.JarFilter.Description");
            }
         };
         htmlFileFilter = new FileFilter() {
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

                  return var2 != null && (var2.equals("htm") || var2.equals("html"));
               }
            }

            public String getDescription() {
               return LaunchRes.getString("FileChooser.HTMLFilter.Description");
            }
         };
      }

      fileChooser.removeChoosableFileFilter(jarFileFilter);
      fileChooser.removeChoosableFileFilter(htmlFileFilter);
      fileChooser.setFileFilter(allFileFilter);
      return fileChooser;
   }

   protected void handleMousePressed(MouseEvent var1, LaunchPanel var2) {
      super.handleMousePressed(var1, var2);
      if (var1.isPopupTrigger() || var1.getButton() == 3 || var1.isControlDown() && System.getProperty("os.name", "").indexOf("Mac") > -1) {
         TreePath var3 = var2.tree.getPathForLocation(var1.getX(), var1.getY());
         if (var3 == null) {
            return;
         }

         LaunchNode var4 = this.getSelectedNode();
         if (var4 == null) {
            return;
         }

         String var5 = var4.getFileName();
         if (var5 != null && super.changedFiles.contains(var5)) {
            if (super.popup.getComponentCount() != 0) {
               super.popup.addSeparator();
            }

            super.popup.add(this.saveNodeItem);
         }

         if (super.popup.getComponentCount() != 0) {
            super.popup.addSeparator();
         }

         super.popup.add(this.saveNodeAsItem);
         if (!var4.isRoot()) {
            super.popup.addSeparator();
            this.openTabItem.setText(LaunchRes.getString("Action.OpenTab"));
            super.popup.add(this.openTabItem);
         }

         super.popup.show(var2, var1.getX() + 4, var1.getY() + 12);
      }

   }

   protected void exit() {
      if (!this.saveAllChanges()) {
         final int var1 = super.frame.getDefaultCloseOperation();
         super.frame.setDefaultCloseOperation(0);
         Runnable var2 = new Runnable() {
            public void run() {
               LaunchBuilder.super.frame.setDefaultCloseOperation(var1);
            }
         };
         SwingUtilities.invokeLater(var2);
      } else {
         super.exit();
      }
   }

   protected boolean isTabSetWritable() {
      String var1 = XML.getResolvedPath(super.tabSetName, Launcher.tabSetBasePath);
      Resource var2 = ResourceLoader.getResource(var1);
      File var3 = var2 == null ? null : var2.getFile();
      boolean var4 = var3 == null ? true : var3.canWrite();
      if (!super.selfContained) {
         for(int var5 = 0; var5 < super.tabbedPane.getTabCount(); ++var5) {
            LaunchNode var6 = this.getTab(var5).getRootNode();
            var4 = var4 && this.isNodeWritable(var6);
         }
      }

      return var4;
   }

   protected boolean isNodeWritable(LaunchNode var1) {
      File var2 = var1.getFile();
      boolean var3 = var2 == null ? true : var2.canWrite();
      if (!var1.isSelfContained()) {
         LaunchNode[] var4 = var1.getChildOwnedNodes();

         for(int var5 = 0; var5 < var4.length; ++var5) {
            var3 = var3 && this.isNodeWritable(var4[var5]);
         }
      }

      return var3;
   }

   static {
      JFrame.setDefaultLookAndFeelDecorated(true);
      JDialog.setDefaultLookAndFeelDecorated(true);
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
