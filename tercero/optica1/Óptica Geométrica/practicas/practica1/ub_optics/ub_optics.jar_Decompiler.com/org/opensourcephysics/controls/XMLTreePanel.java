package org.opensourcephysics.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;
import java.util.Iterator;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.opensourcephysics.tools.ArrayInspector;
import org.opensourcephysics.tools.ResourceLoader;

public class XMLTreePanel extends JPanel {
   protected JLabel label;
   protected JTextField input;
   protected JTextPane xmlPane;
   protected JTree tree;
   protected JScrollPane treeScroller;
   protected Icon valueIcon;
   protected Icon inspectIcon;
   protected Icon inspectFolderIcon;
   protected Icon folderIcon;
   protected XMLControl control;
   protected XMLProperty property;
   protected boolean editable;
   protected JPopupMenu popup;
   int maxStringLength;

   public XMLTreePanel(XMLControl var1) {
      this(var1, true);
   }

   public XMLTreePanel(XMLControl var1, boolean var2) {
      super(new BorderLayout());
      this.treeScroller = new JScrollPane();
      this.maxStringLength = 24;
      this.control = var1;
      this.editable = var2;
      this.createGUI();
   }

   public void refresh() {
      XMLTreeNode var1 = this.createTree(this.control);
      this.displayProperty(var1, this.editable);
   }

   public XMLControl getControl() {
      return this.control;
   }

   public XMLTreeNode setSelectedNode(String var1) {
      XMLTreeNode var2 = (XMLTreeNode)this.tree.getModel().getRoot();
      Enumeration var3 = var2.breadthFirstEnumeration();

      XMLTreeNode var4;
      XMLProperty var5;
      do {
         if (!var3.hasMoreElements()) {
            return null;
         }

         var4 = (XMLTreeNode)var3.nextElement();
         var5 = var4.getProperty();
      } while(!var5.getPropertyName().equals(var1));

      TreePath var6 = new TreePath(var4.getPath());
      this.tree.setSelectionPath(var6);
      this.tree.scrollPathToVisible(var6);
      this.showInspector(var4);
      return var4;
   }

   protected void displayProperty(XMLTreeNode var1, boolean var2) {
      this.input.setVisible(false);
      XMLProperty var3 = var1.getProperty();
      this.label.setText(var3.getPropertyType() + " " + var3.getPropertyName());
      if (!var3.getPropertyContent().isEmpty()) {
         Object var4 = var3.getPropertyContent().get(0);
         if (var4 instanceof String) {
            this.property = var3;
            String var5 = (String)var4;
            if (var5.indexOf("<![CDATA[") != -1) {
               var5 = var5.substring(var5.indexOf("<![CDATA[") + "<![CDATA[".length(), var5.length() - "]]>".length());
            }

            this.input.setText(var5);
            this.input.setEditable(var2);
            this.input.setVisible(true);
         }
      }

      String var6 = var3.toString();
      this.xmlPane.setText(this.getDisplay(var6));
      this.xmlPane.setCaretPosition(0);
   }

   protected String getDisplay(String var1) {
      String var2 = "";
      String var3 = "name=\"array\" type=\"string\">";
      String var4 = "</property>";

      for(int var6 = var1.indexOf(var3); var6 > 0; var6 = var1.indexOf(var3)) {
         var6 += var3.length();
         var2 = var2 + var1.substring(0, var6);
         var1 = var1.substring(var6);
         var6 = var1.indexOf(var4);
         String var5 = var1.substring(0, var6);
         var1 = var1.substring(var6, var1.length());
         if (var5.length() > this.maxStringLength) {
            var5 = var5.substring(0, this.maxStringLength - 3) + "...";
         }

         var2 = var2 + var5;
      }

      var2 = var2 + var1;
      return var2;
   }

   protected void createGUI() {
      String var1 = "/org/opensourcephysics/resources/controls/images/inspect.gif";
      this.inspectIcon = ResourceLoader.getIcon(var1);
      var1 = "/org/opensourcephysics/resources/controls/images/value.gif";
      this.valueIcon = ResourceLoader.getIcon(var1);
      var1 = "/org/opensourcephysics/resources/controls/images/folder.gif";
      this.folderIcon = ResourceLoader.getIcon(var1);
      var1 = "/org/opensourcephysics/resources/controls/images/inspectfolder.gif";
      this.inspectFolderIcon = ResourceLoader.getIcon(var1);
      this.popup = new JPopupMenu();
      JMenuItem var2 = new JMenuItem("Inspect...");
      this.popup.add(var2);
      var2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            XMLTreeNode var2 = (XMLTreeNode)XMLTreePanel.this.tree.getLastSelectedPathComponent();
            if (var2 != null) {
               XMLTreePanel.this.showInspector(var2);
            }

         }
      });
      XMLTreeNode var3 = this.createTree(this.control);
      JToolBar var4 = new JToolBar();
      var4.setFloatable(false);
      this.label = new JLabel();
      var4.add(this.label);
      this.input = new JTextField(20);
      this.input.setVisible(false);
      this.input.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            XMLTreePanel.this.property.setValue(XMLTreePanel.this.input.getText());
            Object var2 = XMLTreePanel.this.control.loadObject((Object)null);
            if (var2 instanceof Component) {
               ((Component)var2).repaint();
            }

            XMLTreePanel.this.input.setText((String)XMLTreePanel.this.property.getPropertyContent().get(0));
            XMLTreePanel.this.input.selectAll();
            XMLTreeNode var3 = (XMLTreeNode)XMLTreePanel.this.tree.getLastSelectedPathComponent();
            if (var3 != null) {
               XMLTreePanel.this.displayProperty(var3, XMLTreePanel.this.editable);
            }

         }
      });
      this.input.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent var1) {
            if (XMLTreePanel.this.editable) {
               JComponent var2 = (JComponent)var1.getSource();
               if (var1.getKeyCode() == 10) {
                  var2.setBackground(Color.white);
               } else {
                  var2.setBackground(Color.yellow);
               }

            }
         }
      });
      this.input.addFocusListener(new FocusAdapter() {
         public void focusLost(FocusEvent var1) {
            JComponent var2 = (JComponent)var1.getSource();
            var2.setBackground(Color.white);
         }
      });
      var4.add(this.input);
      this.xmlPane = new JTextPane();
      this.xmlPane.setPreferredSize(new Dimension(360, 200));
      this.xmlPane.setEditable(false);
      JScrollPane var5 = new JScrollPane(this.xmlPane);
      JPanel var6 = new JPanel(new BorderLayout());
      var6.add(var4, "North");
      var6.add(var5, "Center");
      JSplitPane var7 = new JSplitPane(1, this.treeScroller, var6);
      this.add(var7, "Center");
      this.treeScroller.setPreferredSize(new Dimension(140, 200));
      this.displayProperty(var3, this.editable);
   }

   private XMLTreeNode createTree(XMLControl var1) {
      XMLTreeNode var2 = new XMLTreeNode(var1);
      this.tree = new JTree(var2);
      this.tree.setCellRenderer(new XMLTreePanel.XMLRenderer());
      this.tree.getSelectionModel().setSelectionMode(1);
      this.tree.addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent var1) {
            XMLTreeNode var2 = (XMLTreeNode)XMLTreePanel.this.tree.getLastSelectedPathComponent();
            if (var2 != null) {
               XMLTreePanel.this.displayProperty(var2, XMLTreePanel.this.editable);
            }

         }
      });
      this.tree.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent var1) {
            if (var1.isPopupTrigger() || var1.getButton() == 3 || var1.isControlDown() && System.getProperty("os.name").indexOf("Mac") > -1) {
               TreePath var2 = XMLTreePanel.this.tree.getPathForLocation(var1.getX(), var1.getY());
               if (var2 == null) {
                  return;
               }

               XMLTreePanel.this.tree.setSelectionPath(var2);
               XMLTreeNode var3 = (XMLTreeNode)XMLTreePanel.this.tree.getLastSelectedPathComponent();
               if (var3.isInspectable()) {
                  XMLTreePanel.this.popup.show(XMLTreePanel.this.tree, var1.getX(), var1.getY() + 8);
               }
            }

         }
      });
      this.treeScroller.setViewportView(this.tree);
      return var2;
   }

   private void showInspector(XMLTreeNode var1) {
      if (var1 != null) {
         if (var1.getProperty().getPropertyType().equals("array")) {
            XMLProperty var2 = var1.getProperty();
            ArrayInspector var3 = ArrayInspector.getInspector(var2);
            if (var3 != null) {
               final String var4 = var2.getPropertyName();

               XMLProperty var5;
               for(var5 = var2.getParentProperty(); !(var5 instanceof XMLControl); var5 = var5.getParentProperty()) {
                  var4 = var5.getPropertyName();
               }

               final XMLControl var6 = (XMLControl)var5;
               final Object var8 = var3.getArray();
               final XMLTreeNode var9 = (XMLTreeNode)var1.getParent();
               var3.setEditable(this.editable);
               var3.addPropertyChangeListener(new PropertyChangeListener() {
                  public void propertyChange(PropertyChangeEvent var1) {
                     if (var1.getPropertyName().equals("cell")) {
                        var6.setValue(var4, var8);
                        XMLTreePanel.this.control.loadObject((Object)null);
                        Iterator var2 = var6.getPropertyContent().iterator();

                        while(var2.hasNext()) {
                           XMLProperty var3 = (XMLProperty)var2.next();
                           if (var3.getPropertyName().equals(var4)) {
                              for(int var4x = 0; var4x < var9.getChildCount(); ++var4x) {
                                 XMLTreeNode var5 = (XMLTreeNode)var9.getChildAt(var4x);
                                 if (var5.getProperty().getPropertyName().equals(var4)) {
                                    XMLTreeNode var6x = new XMLTreeNode(var3);
                                    TreeModel var7 = XMLTreePanel.this.tree.getModel();
                                    if (var7 instanceof DefaultTreeModel) {
                                       DefaultTreeModel var8x = (DefaultTreeModel)var7;
                                       var8x.removeNodeFromParent(var5);
                                       var8x.insertNodeInto(var6x, var9, var4x);
                                       TreePath var9x = new TreePath(var6x.getPath());
                                       XMLTreePanel.this.tree.setSelectionPath(var9x);
                                    }

                                    return;
                                 }
                              }

                              return;
                           }
                        }
                     }

                  }
               });
               Container var10 = this.getTopLevelAncestor();
               Point var11 = var10.getLocationOnScreen();
               var3.setLocation(var11.x + 30, var11.y + 30);
               var3.setVisible(true);
            }
         }

      }
   }

   private class XMLRenderer extends DefaultTreeCellRenderer {
      private XMLRenderer() {
      }

      public Component getTreeCellRendererComponent(JTree var1, Object var2, boolean var3, boolean var4, boolean var5, int var6, boolean var7) {
         super.getTreeCellRendererComponent(var1, var2, var3, var4, var5, var6, var7);
         XMLTreeNode var8 = (XMLTreeNode)var2;
         if (var8.isLeaf()) {
            if (var8.isInspectable()) {
               this.setIcon(XMLTreePanel.this.inspectIcon);
            } else {
               this.setIcon(XMLTreePanel.this.valueIcon);
            }
         } else if (var8.isInspectable()) {
            this.setIcon(XMLTreePanel.this.inspectFolderIcon);
         } else {
            this.setIcon(XMLTreePanel.this.folderIcon);
         }

         return this;
      }

      // $FF: synthetic method
      XMLRenderer(Object var2) {
         this();
      }
   }
}
