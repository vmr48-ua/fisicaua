package org.opensourcephysics.tools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.opensourcephysics.controls.OSPLog;

public class LaunchPanel extends JPanel {
   protected static String defaultType = "text";
   protected JTree tree;
   protected DefaultTreeModel treeModel;
   protected JSplitPane splitPane;
   protected JPanel dataPanel;
   protected JEditorPane textPane;
   protected JScrollPane textScroller;
   protected boolean showText = true;
   protected boolean showAllNodes;
   protected Map visibleNodeMap = new HashMap();

   public LaunchPanel(LaunchNode var1, Launcher var2) {
      this.showAllNodes = var2 instanceof LaunchBuilder;
      this.createGUI();
      this.createTree(var1);
      this.setSelectedNode(var1);
   }

   public void setSelectedNode(LaunchNode var1) {
      if (var1 != null) {
         this.tree.setSelectionPath(new TreePath(var1.getPath()));
      }
   }

   public LaunchNode getSelectedNode() {
      TreePath var1 = this.tree.getSelectionPath();
      return var1 == null ? null : (LaunchNode)var1.getLastPathComponent();
   }

   public LaunchNode getRootNode() {
      return (LaunchNode)this.treeModel.getRoot();
   }

   protected LaunchNode getClone(LaunchNode var1) {
      if (var1.getFileName() == null) {
         return null;
      } else {
         Enumeration var2 = this.getRootNode().breadthFirstEnumeration();

         LaunchNode var3;
         do {
            if (!var2.hasMoreElements()) {
               return null;
            }

            var3 = (LaunchNode)var2.nextElement();
         } while(!var1.getFileName().equals(var3.getFileName()));

         return var3;
      }
   }

   protected void createGUI() {
      this.setPreferredSize(new Dimension(400, 200));
      this.setLayout(new BorderLayout());
      this.splitPane = new JSplitPane();
      this.add(this.splitPane, "Center");
      this.dataPanel = new JPanel(new BorderLayout());
      this.splitPane.setRightComponent(this.dataPanel);
      this.textPane = new JTextPane();
      this.textPane.setEditable(false);
      this.textPane.addHyperlinkListener(new HyperlinkListener() {
         public void hyperlinkUpdate(HyperlinkEvent var1) {
            if (var1.getEventType() == EventType.ACTIVATED) {
               try {
                  LaunchPanel.this.textPane.setPage(var1.getURL());
               } catch (IOException var3) {
               }
            }

         }
      });
      this.textScroller = new JScrollPane(this.textPane);
      this.dataPanel.add(this.textScroller, "Center");
      this.splitPane.setDividerLocation(160);
   }

   protected void createTree(LaunchNode var1) {
      if (!this.showAllNodes) {
         LaunchPanel.VisibleNode var2 = new LaunchPanel.VisibleNode(var1);
         this.visibleNodeMap.put(var1, var2);
         this.addVisibleNodes(var2);
      }

      this.treeModel = new LaunchPanel.LaunchTreeModel(var1);
      this.tree = new JTree(this.treeModel);
      this.tree.setToolTipText("");
      this.tree.setRootVisible(!var1.hiddenWhenRoot);
      this.tree.getSelectionModel().setSelectionMode(1);
      this.tree.addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent var1) {
            LaunchNode var2 = LaunchPanel.this.getSelectedNode();
            OSPLog.finer(LaunchRes.getString("Log.Message.NodeSelected") + " " + var2);
            if (var2 != null) {
               if (var2.url != null) {
                  try {
                     if (var2.url.getContent() != null) {
                        LaunchPanel.this.textPane.setPage(var2.url);
                     }
                  } catch (IOException var4) {
                     OSPLog.finest(LaunchRes.getString("Log.Message.BadURL") + " " + var2.url);
                     if (LaunchPanel.this.showText) {
                        LaunchPanel.this.textPane.setContentType(LaunchPanel.defaultType);
                        LaunchPanel.this.textPane.setText(var2.description);
                     }
                  }
               } else if (LaunchPanel.this.showText) {
                  LaunchPanel.this.textPane.setContentType(LaunchPanel.defaultType);
                  LaunchPanel.this.textPane.setText(var2.description);
               }
            }

         }
      });
      JScrollPane var3 = new JScrollPane(this.tree);
      this.splitPane.setLeftComponent(var3);
   }

   private void addVisibleNodes(LaunchPanel.VisibleNode var1) {
      int var2 = var1.node.getChildCount();

      for(int var3 = 0; var3 < var2; ++var3) {
         LaunchNode var4 = (LaunchNode)var1.node.getChildAt(var3);
         if (!var4.isHiddenInLauncher()) {
            LaunchPanel.VisibleNode var5 = new LaunchPanel.VisibleNode(var4);
            this.visibleNodeMap.put(var4, var5);
            var1.add(var5);
            this.addVisibleNodes(var5);
         }
      }

   }

   private class VisibleNode extends DefaultMutableTreeNode {
      LaunchNode node;

      VisibleNode(LaunchNode var2) {
         this.node = var2;
      }
   }

   class LaunchTreeModel extends DefaultTreeModel {
      LaunchTreeModel(LaunchNode var2) {
         super(var2);
      }

      public Object getChild(Object var1, int var2) {
         if (LaunchPanel.this.showAllNodes) {
            return super.getChild(var1, var2);
         } else {
            LaunchPanel.VisibleNode var3 = (LaunchPanel.VisibleNode)LaunchPanel.this.visibleNodeMap.get(var1);
            if (var3 != null) {
               LaunchPanel.VisibleNode var4 = (LaunchPanel.VisibleNode)var3.getChildAt(var2);
               if (var4 != null) {
                  return var4.node;
               }
            }

            return null;
         }
      }

      public int getChildCount(Object var1) {
         if (LaunchPanel.this.showAllNodes) {
            return super.getChildCount(var1);
         } else {
            LaunchPanel.VisibleNode var2 = (LaunchPanel.VisibleNode)LaunchPanel.this.visibleNodeMap.get(var1);
            return var2 != null ? var2.getChildCount() : 0;
         }
      }

      public int getIndexOfChild(Object var1, Object var2) {
         if (LaunchPanel.this.showAllNodes) {
            return super.getIndexOfChild(var1, var2);
         } else {
            LaunchPanel.VisibleNode var3 = (LaunchPanel.VisibleNode)LaunchPanel.this.visibleNodeMap.get(var1);
            LaunchPanel.VisibleNode var4 = (LaunchPanel.VisibleNode)LaunchPanel.this.visibleNodeMap.get(var2);
            return var3 != null && var4 != null ? var3.getIndex(var4) : -1;
         }
      }
   }
}
