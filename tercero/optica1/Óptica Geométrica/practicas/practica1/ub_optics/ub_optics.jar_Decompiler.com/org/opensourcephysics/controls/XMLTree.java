package org.opensourcephysics.controls;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

public class XMLTree {
   protected static Icon hiliteIcon;
   protected XMLTreeNode root;
   protected JTree tree;
   protected JScrollPane scroller;
   protected XMLControl control;
   protected List selectedProps = new ArrayList();
   protected Class hilite;
   // $FF: synthetic field
   static Class class$java$lang$Object;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$controls$XMLTree;

   public XMLTree(XMLControl var1) {
      this.hilite = class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object;
      this.control = var1;
      this.createGUI();
   }

   public JTree getTree() {
      return this.tree;
   }

   public List getSelectedProperties() {
      this.selectedProps.clear();
      TreePath[] var1 = this.tree.getSelectionPaths();
      if (var1 != null) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            XMLTreeNode var3 = (XMLTreeNode)var1[var2].getLastPathComponent();
            this.selectedProps.add(var3.getProperty());
         }
      }

      return this.selectedProps;
   }

   public JScrollPane getScrollPane() {
      return this.scroller;
   }

   public void setHighlightedClass(Class var1) {
      if (var1 != null) {
         this.hilite = var1;
         this.scroller.repaint();
      }

   }

   public Class getHighlightedClass() {
      return this.hilite;
   }

   public void selectHighlightedProperties() {
      Enumeration var1 = this.root.breadthFirstEnumeration();

      while(var1.hasMoreElements()) {
         XMLTreeNode var2 = (XMLTreeNode)var1.nextElement();
         XMLProperty var3 = var2.getProperty();
         Class var4 = var3.getPropertyClass();
         if (var4 != null && this.hilite.isAssignableFrom(var4)) {
            TreePath var5 = new TreePath(var2.getPath());
            this.tree.addSelectionPath(var5);
            this.tree.scrollPathToVisible(var5);
         }
      }

   }

   public void showHighlightedProperties() {
      Enumeration var1 = this.root.breadthFirstEnumeration();

      while(var1.hasMoreElements()) {
         XMLTreeNode var2 = (XMLTreeNode)var1.nextElement();
         XMLProperty var3 = var2.getProperty();
         Class var4 = var3.getPropertyClass();
         if (var4 != null && this.hilite.isAssignableFrom(var4)) {
            TreePath var5 = new TreePath(var2.getPath());
            this.tree.scrollPathToVisible(var5);
         }
      }

   }

   protected void createGUI() {
      String var1 = "/org/opensourcephysics/resources/controls/images/hilite.gif";
      hiliteIcon = new ImageIcon((class$org$opensourcephysics$controls$XMLTree == null ? (class$org$opensourcephysics$controls$XMLTree = class$("org.opensourcephysics.controls.XMLTree")) : class$org$opensourcephysics$controls$XMLTree).getResource(var1));
      this.root = new XMLTreeNode(this.control);
      this.tree = new JTree(this.root);
      this.tree.setCellRenderer(new XMLTree.HighlightRenderer());
      this.tree.getSelectionModel().setSelectionMode(4);
      this.scroller = new JScrollPane(this.tree);
      this.scroller.setPreferredSize(new Dimension(200, 200));
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   private class HighlightRenderer extends DefaultTreeCellRenderer {
      private HighlightRenderer() {
      }

      public Component getTreeCellRendererComponent(JTree var1, Object var2, boolean var3, boolean var4, boolean var5, int var6, boolean var7) {
         super.getTreeCellRendererComponent(var1, var2, var3, var4, var5, var6, var7);
         XMLTreeNode var8 = (XMLTreeNode)var2;
         XMLProperty var9 = var8.getProperty();
         Class var10 = var9.getPropertyClass();
         if (var10 != null && XMLTree.this.hilite.isAssignableFrom(var10)) {
            this.setIcon(XMLTree.hiliteIcon);
         }

         return this;
      }

      // $FF: synthetic method
      HighlightRenderer(Object var2) {
         this();
      }
   }
}
