package org.opensourcephysics.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControlElement;
import org.opensourcephysics.controls.XMLTreePanel;

public class LaunchSaver extends JDialog {
   private String prevTabSetName;
   private String prevTabSetBasePath;
   private boolean prevTabSetSelfContained;
   private Map prevNodeNames = new HashMap();
   private Map prevNodeSelfContains = new HashMap();
   private LaunchBuilder builder;
   private DefaultTreeModel treeModel;
   private JTree tree;
   private ArrayList treePaths = new ArrayList();
   private JTextField pathField;
   private JButton chooseButton;
   private JButton inspectButton;
   private LaunchSaver.Node root;
   private JScrollPane treeScroller;
   private boolean approved = false;
   private LaunchSaver.Editor editor = new LaunchSaver.Editor();
   private JDialog inspector;
   private boolean active;

   public LaunchSaver(LaunchBuilder var1) {
      super(var1.frame, true);
      this.createGUI();
      this.setBuilder(var1);
      if (FontSizer.getLevel() != 0) {
         FontSizer.setFonts(this.getContentPane(), FontSizer.getLevel());
      }

      this.pack();
      Dimension var2 = Toolkit.getDefaultToolkit().getScreenSize();
      int var3 = (var2.width - this.getBounds().width) / 2;
      int var4 = (var2.height - this.getBounds().height) / 2;
      this.setLocation(var3, var4);
   }

   public void setBuilder(LaunchBuilder var1) {
      this.builder = var1;
      this.createTree();
      this.refresh();
      this.approved = false;
   }

   public void setSelectedNode(LaunchSaver.Node var1) {
      if (var1 != null) {
         this.tree.setSelectionPath(new TreePath(var1.getPath()));
      }
   }

   public LaunchSaver.Node getSelectedNode() {
      TreePath var1 = this.tree.getSelectionPath();
      return var1 == null ? null : (LaunchSaver.Node)var1.getLastPathComponent();
   }

   public boolean isApproved() {
      return this.approved;
   }

   private void refresh() {
      this.inspectButton.setEnabled(this.getSelectedNode() != null);
      String var1 = null;
      if (!Launcher.tabSetBasePath.equals("")) {
         var1 = Launcher.tabSetBasePath;
      } else if (this.builder.jarBasePath != null) {
         var1 = this.builder.jarBasePath;
      } else {
         var1 = XML.forwardSlash(XML.getUserDirectory());
      }

      if (var1.indexOf("javaws") > -1) {
         var1 = XML.forwardSlash(System.getProperty("user.home", "."));
         Launcher.tabSetBasePath = var1;
      }

      this.pathField.setText(var1 + "/");
      this.pathField.setBackground(Color.white);
      this.treeModel.nodeChanged(this.root);
      this.tree.repaint();
   }

   private void inspectSelectedNode() {
      LaunchSaver.Node var1 = this.getSelectedNode();
      if (var1 == null) {
         var1 = this.root;
      }

      Object var2 = var1.node;
      if (var2 == null) {
         var2 = this.builder.new LaunchSet(this.builder, this.builder.tabSetName);
      }

      XMLControlElement var3 = new XMLControlElement(var2);
      XMLTreePanel var4 = new XMLTreePanel(var3, false);
      this.inspector.setContentPane(var4);
      String var5 = var1 != this.root ? var1.node.getFileName() : this.builder.tabSetName;
      var5 = XML.getResolvedPath(var5, Launcher.tabSetBasePath);
      this.inspector.setTitle(LaunchRes.getString("Inspector.Title.File") + " \"" + var5 + "\"");
      this.inspector.setVisible(true);
   }

   private void revert() {
      Launcher.tabSetBasePath = this.prevTabSetBasePath;
      this.builder.tabSetName = this.prevTabSetName;
      this.builder.selfContained = this.prevTabSetSelfContained;

      for(int var1 = 0; var1 < this.builder.tabbedPane.getTabCount(); ++var1) {
         LaunchNode var2 = this.builder.getTab(var1).getRootNode();
         var2.parentSelfContained = this.prevTabSetSelfContained;
      }

      Iterator var5 = this.prevNodeNames.keySet().iterator();

      while(var5.hasNext()) {
         LaunchSaver.Node var6 = (LaunchSaver.Node)var5.next();
         String var3 = (String)this.prevNodeNames.get(var6);
         var6.node.setFileName(var3);
         Boolean var4 = (Boolean)this.prevNodeSelfContains.get(var6);
         var6.node.setSelfContained(var4);
      }

   }

   private void setTabSetBasePath(String var1) {
      var1 = XML.forwardSlash(var1);

      boolean var2;
      for(var2 = false; var1.startsWith("/"); var1 = var1.substring(1)) {
         var2 = true;
      }

      if (var2) {
         var1 = "/" + var1;
      }

      while(var1.endsWith("/")) {
         var1 = var1.substring(0, var1.length() - 1);
      }

      if (!var1.startsWith("/") && var1.indexOf(":") == -1) {
         String var3 = XML.forwardSlash(XML.getUserDirectory());
         if (this.builder.jarBasePath != null) {
            var3 = this.builder.jarBasePath;
         }

         if (!var1.equals("")) {
            var3 = var3 + "/";
         }

         var1 = var3 + var1;
      }

      Launcher.tabSetBasePath = var1;
      this.refresh();
   }

   private String setTabSetName(String var1) {
      String var2 = XML.getName(var1);
      if (!"xset".equals(XML.getExtension(var2))) {
         while(true) {
            if (var2.indexOf(".") <= -1) {
               var2 = var2 + ".xset";
               break;
            }

            var2 = var2.substring(0, var2.length() - 1);
         }
      }

      this.builder.tabSetName = var2;
      this.refresh();
      return var2;
   }

   private void createGUI() {
      this.inspector = new JDialog(this, false);
      this.inspector.setSize(new Dimension(600, 300));
      Dimension var1 = Toolkit.getDefaultToolkit().getScreenSize();
      int var2 = (var1.width - this.inspector.getBounds().width) / 2;
      int var3 = (var1.height - this.inspector.getBounds().height) / 2;
      this.inspector.setLocation(var2, var3);
      this.setTitle(LaunchRes.getString("Saver.Title"));
      JPanel var4 = new JPanel(new BorderLayout());
      var4.setPreferredSize(new Dimension(600, 300));
      this.setContentPane(var4);
      JPanel var5 = new JPanel(new GridLayout(1, 4));
      var4.add(var5, "North");
      JLabel var6 = new JLabel(LaunchRes.getString("Saver.Legend.New"), Launcher.greenFileIcon, 0);
      var6.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
      var5.add(var6);
      var6 = new JLabel(LaunchRes.getString("Saver.Legend.Replace"), Launcher.yellowFileIcon, 0);
      var6.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
      var5.add(var6);
      var6 = new JLabel(LaunchRes.getString("Saver.Legend.ReadOnly"), Launcher.redFileIcon, 0);
      var6.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
      var5.add(var6);
      var6 = new JLabel(LaunchRes.getString("Saver.Legend.SelfContained"), Launcher.whiteFolderIcon, 0);
      var6.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
      var5.add(var6);
      var5.setBorder(BorderFactory.createLoweredBevelBorder());
      JPanel var7 = new JPanel(new BorderLayout());
      var4.add(var7, "South");
      JToolBar var8 = new JToolBar();
      var8.setFloatable(false);
      var7.add(var8, "North");
      var6 = new JLabel(LaunchRes.getString("Saver.Label.Base"));
      var6.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 4));
      var8.add(var6);
      this.pathField = new JTextField();
      this.pathField.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent var1) {
            if (var1.getKeyCode() == 10) {
               LaunchSaver.this.setTabSetBasePath(LaunchSaver.this.pathField.getText());
            } else {
               LaunchSaver.this.pathField.setBackground(Color.yellow);
            }

         }
      });
      this.pathField.addFocusListener(new FocusAdapter() {
         public void focusLost(FocusEvent var1) {
            LaunchSaver.this.setTabSetBasePath(LaunchSaver.this.pathField.getText());
         }
      });
      var8.add(this.pathField);
      this.chooseButton = new JButton(LaunchRes.getString("Saver.Button.Choose"));
      this.chooseButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            LaunchSaver.this.chooseTabSet();
         }
      });
      this.inspectButton = new JButton(LaunchRes.getString("MenuItem.Inspect"));
      this.inspectButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            LaunchSaver.this.inspectSelectedNode();
         }
      });
      JToolBar var9 = new JToolBar();
      var9.setFloatable(false);
      var9.add(Box.createHorizontalGlue());
      var9.add(Box.createHorizontalGlue());
      var9.add(Box.createHorizontalGlue());
      JPanel var10 = new JPanel(new GridLayout(1, 4));
      var9.add(var10);
      var10.add(this.inspectButton);
      var10.add(this.chooseButton);
      var7.add(var9, "South");
      JButton var11 = new JButton(LaunchRes.getString("Saver.Button.Save"));
      var10.add(var11);
      var11.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            LaunchSaver.this.approved = true;
            LaunchSaver.this.setVisible(false);
         }
      });
      JButton var12 = new JButton(LaunchRes.getString("Saver.Button.Cancel"));
      var10.add(var12);
      var12.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            LaunchSaver.this.revert();
            LaunchSaver.this.setVisible(false);
         }
      });
      this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent var1) {
            if (!LaunchSaver.this.isApproved()) {
               LaunchSaver.this.revert();
            }

         }
      });
   }

   private void createTree() {
      this.active = false;
      this.treePaths.clear();
      this.prevNodeNames.clear();
      this.prevNodeSelfContains.clear();
      this.prevTabSetName = this.builder.tabSetName;
      this.prevTabSetBasePath = Launcher.tabSetBasePath;
      this.prevTabSetSelfContained = this.builder.selfContained;
      this.root = new LaunchSaver.Node();
      this.treePaths.add(new TreePath(this.root.getPath()));
      int var1 = this.builder.tabbedPane.getTabCount();

      int var5;
      for(int var2 = 0; var2 < var1; ++var2) {
         LaunchSaver.Node var3 = new LaunchSaver.Node(this.builder.getTab(var2).getRootNode());
         if (var3.node.getFileName() != null) {
            this.root.add(var3);
            this.addChildren(var3);
         } else {
            LaunchNode[] var4 = var3.node.getChildOwnedNodes();

            for(var5 = 0; var5 < var4.length; ++var5) {
               LaunchSaver.Node var6 = new LaunchSaver.Node(var4[var5]);
               this.root.add(var6);
               this.addChildren(var6);
            }
         }
      }

      this.treeModel = new DefaultTreeModel(this.root);
      this.tree = new JTree(this.treeModel);
      this.tree.setCellRenderer(new LaunchSaver.Renderer());
      this.tree.setCellEditor(this.editor);
      this.tree.setEditable(true);
      this.tree.setShowsRootHandles(true);
      this.tree.getSelectionModel().setSelectionMode(1);
      this.tree.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent var1) {
            TreePath var2 = LaunchSaver.this.tree.getPathForLocation(var1.getX(), var1.getY());
            if (var2 == null) {
               LaunchSaver.this.editor.stopCellEditing();
            }

         }
      });
      this.tree.addTreeWillExpandListener(new LaunchSaver.ExpansionListener());
      this.tree.addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent var1) {
            if (LaunchSaver.this.inspector.isVisible()) {
               LaunchSaver.this.inspectSelectedNode();
            }

         }
      });
      FontRenderContext var9 = new FontRenderContext((AffineTransform)null, false, false);
      Font var10 = this.editor.field.getFont();
      Rectangle2D var11 = var10.getStringBounds("A", var9);
      var5 = (int)var11.getHeight();
      this.tree.setRowHeight(var5 + 3);
      this.tree.setBorder(BorderFactory.createEmptyBorder(4, 0, 2, 0));
      if (this.treeScroller != null) {
         this.getContentPane().remove(this.treeScroller);
      }

      this.treeScroller = new JScrollPane(this.tree);
      this.getContentPane().add(this.treeScroller, "Center");
      this.setSelectedNode(this.root);
      ListIterator var12 = this.treePaths.listIterator();

      TreePath var7;
      while(var12.hasNext()) {
         var7 = (TreePath)var12.next();
         this.tree.expandPath(var7);
      }

      while(var12.hasPrevious()) {
         var7 = (TreePath)var12.previous();
         LaunchSaver.Node var8 = (LaunchSaver.Node)var7.getLastPathComponent();
         if (this.isFolder(var8)) {
            this.tree.collapsePath(var7);
         }
      }

      this.active = true;
   }

   private void addChildren(LaunchSaver.Node var1) {
      this.prevNodeNames.put(var1, var1.node.getFileName());
      this.prevNodeSelfContains.put(var1, new Boolean(var1.node.selfContained));
      this.treePaths.add(new TreePath(var1.getPath()));
      LaunchNode[] var2 = var1.node.getChildOwnedNodes();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         LaunchSaver.Node var4 = new LaunchSaver.Node(var2[var3]);
         var1.add(var4);
         this.addChildren(var4);
      }

   }

   private boolean isFolder(LaunchSaver.Node var1) {
      if (var1 == this.root) {
         return this.builder.selfContained;
      } else {
         return var1.node.selfContained && !var1.isLeaf();
      }
   }

   private void chooseTabSet() {
      String var1 = XML.getResolvedPath(this.builder.tabSetName, Launcher.tabSetBasePath);
      JFileChooser var2 = Launcher.getXMLChooser();
      var2.setSelectedFile(new File(var1));
      var2.setFileFilter(Launcher.xsetFileFilter);
      int var3 = var2.showDialog(this, LaunchRes.getString("Saver.FileChooser.Title"));
      if (var3 == 0) {
         File var4 = var2.getSelectedFile();
         var1 = var4.getAbsolutePath();
         this.setTabSetName(var1);
         this.setTabSetBasePath(XML.getDirectoryPath(var1));
      }

      this.refresh();
   }

   private Icon getIcon(LaunchSaver.Node var1) {
      File var2;
      if (var1 == this.root) {
         String var3 = XML.getResolvedPath(this.builder.tabSetName, Launcher.tabSetBasePath);
         Resource var4 = ResourceLoader.getResource(var3);
         var2 = var4 == null ? null : var4.getFile();
      } else {
         var2 = var1.node.getFile();
      }

      if (var2 == null) {
         return this.isFolder(var1) ? Launcher.greenFolderIcon : Launcher.greenFileIcon;
      } else if (var2.canWrite()) {
         return this.isFolder(var1) ? Launcher.yellowFolderIcon : Launcher.yellowFileIcon;
      } else {
         return this.isFolder(var1) ? Launcher.redFolderIcon : Launcher.redFileIcon;
      }
   }

   private class ExpansionListener implements TreeWillExpandListener {
      private ExpansionListener() {
      }

      public void treeWillExpand(TreeExpansionEvent var1) {
         TreePath var2 = var1.getPath();
         this.set(var2, false);
      }

      public void treeWillCollapse(TreeExpansionEvent var1) {
         TreePath var2 = var1.getPath();
         this.set(var2, true);
      }

      private void set(TreePath var1, boolean var2) {
         if (LaunchSaver.this.active) {
            LaunchSaver.Node var3 = (LaunchSaver.Node)var1.getLastPathComponent();
            if (var3 == LaunchSaver.this.root) {
               LaunchSaver.this.builder.selfContained = var2;

               for(int var4 = 0; var4 < LaunchSaver.this.builder.tabbedPane.getTabCount(); ++var4) {
                  LaunchNode var5 = LaunchSaver.this.builder.getTab(var4).getRootNode();
                  var5.parentSelfContained = var2;
               }
            } else {
               var3.node.setSelfContained(var2);
            }

            if (LaunchSaver.this.inspector.isVisible()) {
               LaunchSaver.this.inspectSelectedNode();
            }
         }

      }

      // $FF: synthetic method
      ExpansionListener(Object var2) {
         this();
      }
   }

   private class Editor extends AbstractCellEditor implements TreeCellEditor {
      JPanel panel = new JPanel(new BorderLayout());
      JTextField field = new JTextField();
      JLabel label = new JLabel();

      Editor() {
         this.panel.add(this.label, "West");
         this.panel.add(this.field, "Center");
         this.panel.setOpaque(false);
         this.label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
         this.field.setBorder(BorderFactory.createLineBorder(Color.black));
         this.field.setEditable(true);
         this.field.setColumns(30);
         this.field.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               Editor.this.stopCellEditing();
            }
         });
         this.field.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent var1) {
               if (var1.getKeyCode() == 10) {
                  Editor.this.stopCellEditing();
               } else {
                  Editor.this.field.setBackground(Color.yellow);
               }

            }
         });
      }

      public Component getTreeCellEditorComponent(JTree var1, Object var2, boolean var3, boolean var4, boolean var5, int var6) {
         LaunchSaver.Node var7 = (LaunchSaver.Node)var2;
         String var8 = var7 != LaunchSaver.this.root ? var7.node.getFileName() : LaunchSaver.this.builder.tabSetName;
         this.field.setText(var8);
         this.label.setIcon(LaunchSaver.this.getIcon(var7));
         this.label.setText(var7.toString());
         if (FontSizer.getLevel() != 0) {
            FontSizer.setFonts(this.panel, FontSizer.getLevel());
         }

         return this.panel;
      }

      public boolean isCellEditable(EventObject var1) {
         return var1 instanceof MouseEvent;
      }

      public Object getCellEditorValue() {
         this.field.setBackground(Color.white);
         LaunchSaver.Node var1 = LaunchSaver.this.getSelectedNode();
         String var2 = this.field.getText();
         String var3;
         if (var1.node != null) {
            var3 = var1.node.getFileName();
         } else {
            var3 = LaunchSaver.this.builder.tabSetName;
         }

         if (LaunchSaver.this.builder.getOpenPaths().contains(var2) && !var3.equals(var2)) {
            JOptionPane.showMessageDialog(LaunchSaver.this, LaunchRes.getString("Dialog.DuplicateFileName.Message") + " \"" + var2 + "\"", LaunchRes.getString("Dialog.DuplicateFileName.Title"), 2);
            LaunchSaver.this.refresh();
            return null;
         } else {
            if (var1.node != null) {
               var1.node.setFileName(var2);
            } else {
               var2 = LaunchSaver.this.setTabSetName(var2);
            }

            return var2;
         }
      }
   }

   class Renderer implements TreeCellRenderer {
      JPanel panel = new JPanel(new BorderLayout());
      JLabel label = new JLabel();
      JTextField field = new JTextField();

      public Renderer() {
         this.panel.add(this.label, "West");
         this.panel.add(this.field, "Center");
         this.panel.setOpaque(false);
         this.label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
         this.field.setBorder((Border)null);
         this.field.setBackground(this.field.getSelectionColor());
      }

      public Component getTreeCellRendererComponent(JTree var1, Object var2, boolean var3, boolean var4, boolean var5, int var6, boolean var7) {
         LaunchSaver.Node var8 = (LaunchSaver.Node)var2;
         String var9 = var8 != LaunchSaver.this.root ? var8.node.getFileName() : LaunchSaver.this.builder.tabSetName;
         this.field.setText(var9);
         this.label.setIcon(LaunchSaver.this.getIcon(var8));
         this.label.setText(var8.toString());
         if (FontSizer.getLevel() != 0) {
            FontSizer.setFonts(this.panel, FontSizer.getLevel());
         }

         this.field.setOpaque(var3);
         return this.panel;
      }
   }

   private class Node extends DefaultMutableTreeNode {
      private LaunchNode node;

      Node() {
      }

      Node(LaunchNode var2) {
         this.node = var2;
      }

      public String toString() {
         if (this.node == null) {
            return LaunchRes.getString("Saver.Tree.TabSet") + ":";
         } else {
            return this.node.isRoot() ? LaunchRes.getString("Saver.Tree.Tab") + " \"" + this.node.name + "\":" : LaunchRes.getString("Saver.Tree.Node") + " \"" + this.node.name + "\":";
         }
      }
   }
}
