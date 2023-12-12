package org.opensourcephysics.tools;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelEvent;
import org.opensourcephysics.controls.OSPLog;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLControlElement;
import org.opensourcephysics.controls.XMLTree;
import org.opensourcephysics.controls.XMLTreeChooser;
import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.display.HighlightableDataset;
import org.opensourcephysics.display.OSPFrame;

public class DatasetTool extends OSPFrame implements Tool {
   protected static JFileChooser chooser;
   protected static OSPLog log = OSPLog.getOSPLog();
   protected static Dimension dim = new Dimension(720, 500);
   protected JTabbedPane tabbedPane;
   protected boolean useChooser;
   protected JPanel contentPane;
   protected PropertyChangeSupport support;
   protected XMLControl control;
   protected JobManager jobManager;
   protected JMenu addMenu;
   protected JMenu subtractMenu;
   protected JMenu multiplyMenu;
   protected JMenu divideMenu;
   static final DatasetTool DATASET_TOOL = new DatasetTool();
   // $FF: synthetic field
   static Class class$java$lang$Object;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$display$Dataset;

   public static DatasetTool getTool() {
      return DATASET_TOOL;
   }

   public DatasetTool() {
      super(ToolsRes.getString("Frame.Title"));
      this.useChooser = true;
      this.contentPane = new JPanel(new BorderLayout());
      this.control = new XMLControlElement();
      this.jobManager = new JobManager(this);
      String var1 = "DatasetTool";
      this.setName(var1);
      this.createGUI();
      Toolbox.addTool(var1, this);
   }

   public DatasetTool(String var1) {
      this();
      this.open(var1);
   }

   public DatasetTool(Dataset var1) {
      this();
      this.addTab(var1);
   }

   public DatasetTool(XMLControl var1) {
      this();
      this.loadDatasets(var1);
   }

   public String open(String var1) {
      OSPLog.fine("opening " + var1);
      XMLControlElement var2 = new XMLControlElement(var1);
      if (!this.loadDatasets(var2).isEmpty()) {
         return var1;
      } else {
         OSPLog.finest("no datasets found");
         return null;
      }
   }

   public void send(Job var1, Tool var2) throws RemoteException {
      XMLControlElement var3 = new XMLControlElement(var1.getXML());
      if (!var3.failedToRead() && var3.getObjectClass() != (class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object)) {
         this.jobManager.log(var1, var2);
         Collection var4 = this.loadDatasets(var3);
         Iterator var5 = var4.iterator();

         while(var5.hasNext()) {
            this.jobManager.associate(var1, var5.next());
         }

      }
   }

   public void setUseChooser(boolean var1) {
      this.useChooser = var1;
   }

   public boolean isUseChooser() {
      return this.useChooser;
   }

   public Collection loadDatasets(XMLControl var1) {
      return this.loadDatasets(var1, this.useChooser);
   }

   public Collection loadDatasets(XMLControl var1, boolean var2) {
      HashSet var4 = new HashSet();
      List var3;
      if (var2) {
         XMLTreeChooser var5 = new XMLTreeChooser(ToolsRes.getString("Chooser.Title"), ToolsRes.getString("Chooser.Label"), this);
         var3 = var5.choose(var1, class$org$opensourcephysics$display$Dataset == null ? (class$org$opensourcephysics$display$Dataset = class$("org.opensourcephysics.display.Dataset")) : class$org$opensourcephysics$display$Dataset);
      } else {
         XMLTree var9 = new XMLTree(var1);
         var9.setHighlightedClass(class$org$opensourcephysics$display$Dataset == null ? (class$org$opensourcephysics$display$Dataset = class$("org.opensourcephysics.display.Dataset")) : class$org$opensourcephysics$display$Dataset);
         var9.selectHighlightedProperties();
         var3 = var9.getSelectedProperties();
         if (var3.isEmpty()) {
            JOptionPane.showMessageDialog((Component)null, ToolsRes.getString("Dialog.NoDatasets.Message"));
         }
      }

      if (!var3.isEmpty()) {
         Iterator var10 = var3.iterator();

         while(var10.hasNext()) {
            XMLControl var6 = (XMLControl)var10.next();
            Dataset var7 = null;
            if (var6 instanceof XMLControlElement) {
               XMLControlElement var8 = (XMLControlElement)var6;
               var7 = (Dataset)var8.loadObject((Object)null, true, true);
            } else {
               var7 = (Dataset)var6.loadObject((Object)null);
            }

            this.addTab(var7);
            var4.add(var7);
         }
      }

      return var4;
   }

   public Collection getDatasets() {
      HashSet var1 = new HashSet();

      for(int var2 = 0; var2 < this.tabbedPane.getTabCount(); ++var2) {
         DatasetTab var3 = (DatasetTab)this.tabbedPane.getComponentAt(var2);
         var1.add(var3.getDataset());
      }

      return var1;
   }

   public Container getContentPane() {
      return this.contentPane;
   }

   public Dataset getSelectedDataset() {
      DatasetTab var1 = this.getSelectedTab();
      return var1 != null ? var1.getDataset() : null;
   }

   protected boolean addTab(Dataset var1) {
      int var2 = this.getTabIndex(var1);
      if (var2 >= 0) {
         this.tabbedPane.setSelectedIndex(var2);
         double[] var6 = var1.getXPoints();
         double[] var7 = var1.getYPoints();
         DatasetTab var5 = this.getSelectedTab();
         var5.original.setName(var1.getName());
         var5.dataset.clear();
         var5.dataset.append(var6, var7);
         var5.dataset.setName(var1.getName());
         var5.dataTable.tableChanged((TableModelEvent)null);
         var5.refresh();
         this.refreshTabTitles();
         return false;
      } else {
         final DatasetTab var3 = new DatasetTab(var1);
         var3.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent var1) {
               if (!var3.fitCheckBox.isSelected()) {
                  var3.splitPanes[1].setDividerLocation(1.0D);
               }

               if (!var3.statsCheckBox.isSelected()) {
                  var3.splitPanes[2].setDividerLocation(0);
               }

            }
         });
         String var4 = var1.getName() + " (" + var1.getColumnName(0) + ", " + var1.getColumnName(1) + ")";
         OSPLog.finer("adding tab " + var4);
         this.tabbedPane.addTab(var4, var3);
         this.tabbedPane.setSelectedComponent(var3);
         var3.dataTable.getColumnModel().addColumnModelListener(new TableColumnModelListener() {
            public void columnAdded(TableColumnModelEvent var1) {
            }

            public void columnRemoved(TableColumnModelEvent var1) {
            }

            public void columnSelectionChanged(ListSelectionEvent var1) {
            }

            public void columnMarginChanged(ChangeEvent var1) {
            }

            public void columnMoved(TableColumnModelEvent var1) {
               DatasetTool.this.refreshTabTitles();
            }
         });
         this.validate();
         var3.init();
         var3.refresh();
         this.refreshTabTitles();
         return true;
      }
   }

   protected DatasetTab getSelectedTab() {
      return (DatasetTab)this.tabbedPane.getSelectedComponent();
   }

   protected String open() {
      int var1 = OSPFrame.getChooser().showOpenDialog((Component)null);
      if (var1 == 0) {
         OSPFrame.chooserDir = OSPFrame.getChooser().getCurrentDirectory().toString();
         String var2 = OSPFrame.getChooser().getSelectedFile().getAbsolutePath();
         var2 = XML.getRelativePath(var2);
         return this.open(var2);
      } else {
         return null;
      }
   }

   protected int getTabIndex(Dataset var1) {
      for(int var2 = 0; var2 < this.tabbedPane.getTabCount(); ++var2) {
         DatasetTab var3 = (DatasetTab)this.tabbedPane.getComponentAt(var2);
         Dataset var4 = var3.getDataset();
         if (var4 == var1 || var4.getID() == var1.getID()) {
            return var2;
         }
      }

      return -1;
   }

   protected Dataset getDataset(int var1) {
      if (var1 >= 0 && var1 < this.tabbedPane.getTabCount()) {
         DatasetTab var2 = (DatasetTab)this.tabbedPane.getComponentAt(var1);
         return var2.getDataset();
      } else {
         return null;
      }
   }

   public void updateData(Dataset var1) {
      int var2 = this.tabbedPane.getTabCount();

      for(int var3 = 0; var3 < var2; ++var3) {
         Dataset var4 = ((DatasetTab)this.tabbedPane.getComponentAt(var3)).getDataset();
         if (var4 == var1) {
            ((DatasetTab)this.tabbedPane.getComponentAt(var3)).updateData();
         }
      }

   }

   protected void removeTab(int var1) {
      if (var1 >= 0 && var1 < this.tabbedPane.getTabCount()) {
         String var2 = this.tabbedPane.getTitleAt(var1);
         OSPLog.finer("removing tab " + var2);
         this.tabbedPane.removeTabAt(var1);
         this.refreshTabTitles();
      }

   }

   protected void removeAllButTab(int var1) {
      for(int var2 = this.tabbedPane.getTabCount() - 1; var2 >= 0; --var2) {
         if (var2 != var1) {
            String var3 = this.tabbedPane.getTitleAt(var2);
            OSPLog.finer("removing tab " + var3);
            this.tabbedPane.removeTabAt(var2);
         }
      }

      this.refreshTabTitles();
   }

   protected void removeAllTabs() {
      for(int var1 = this.tabbedPane.getTabCount() - 1; var1 >= 0; --var1) {
         String var2 = this.tabbedPane.getTitleAt(var1);
         OSPLog.finer("removing tab " + var2);
         this.tabbedPane.removeTabAt(var1);
      }

   }

   protected void refreshTabTitles() {
      String[] var1 = new String[this.tabbedPane.getTabCount()];
      String[] var2 = new String[this.tabbedPane.getTabCount()];
      boolean var3 = true;

      int var4;
      for(var4 = 0; var4 < var1.length; ++var4) {
         DatasetTab var5 = (DatasetTab)this.tabbedPane.getComponentAt(var4);
         HighlightableDataset var6 = var5.displayData;
         var1[var4] = "(" + var6.getColumnName(0) + ", " + var6.getColumnName(1) + ")";
         var2[var4] = var6.getName();
         if (var2[var4] == null || var2[var4].equals("")) {
            var2[var4] = ToolsRes.getString("Dataset.Name.Default");
         }

         var3 = var3 && var2[var4].equals(var2[0]);
      }

      for(var4 = 0; var4 < var1.length; ++var4) {
         if (!var3 || var2[var4] != ToolsRes.getString("Dataset.Name.Default")) {
            var1[var4] = var2[var4] + " " + var1[var4];
         }
      }

      for(var4 = 0; var4 < var1.length; ++var4) {
         this.tabbedPane.setTitleAt(var4, var1[var4]);
      }

   }

   protected void createGUI() {
      this.contentPane.setPreferredSize(dim);
      this.setContentPane(this.contentPane);
      this.setDefaultCloseOperation(1);
      this.addComponentListener(new ComponentAdapter() {
         public void componentResized(ComponentEvent var1) {
            DatasetTab var2 = DatasetTool.this.getSelectedTab();
            if (var2 != null) {
               if (!var2.fitCheckBox.isSelected()) {
                  var2.splitPanes[1].setDividerLocation(1.0D);
               }

               if (!var2.statsCheckBox.isSelected()) {
                  var2.splitPanes[2].setDividerLocation(0);
               }

            }
         }
      });
      this.tabbedPane = new JTabbedPane(1);
      this.contentPane.add(this.tabbedPane, "Center");
      this.tabbedPane.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent var1) {
            if (var1.isPopupTrigger() || var1.getButton() == 3 || var1.isControlDown() && System.getProperty("os.name", "").indexOf("Mac") > -1) {
               final int var2 = DatasetTool.this.tabbedPane.getSelectedIndex();
               DatasetTab var3 = (DatasetTab)DatasetTool.this.tabbedPane.getComponentAt(var2);
               final HighlightableDataset var4 = var3.displayData;
               final String var5 = var4.getColumnName(0) + ", " + var4.getColumnName(1);
               JMenuItem var6 = new JMenuItem(ToolsRes.getString("MenuItem.Close"));
               JPopupMenu var7 = new JPopupMenu();
               var7.add(var6);
               var6.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent var1) {
                     DatasetTool.this.removeTab(var2);
                  }
               });
               var6 = new JMenuItem(ToolsRes.getString("MenuItem.CloseOthers"));
               var7.add(var6);
               var6.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent var1) {
                     DatasetTool.this.removeAllButTab(var2);
                  }
               });
               var6 = new JMenuItem(ToolsRes.getString("MenuItem.CloseAll"));
               var7.add(var6);
               var6.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent var1) {
                     DatasetTool.this.removeAllTabs();
                  }
               });
               ArrayList var8 = new ArrayList();
               final String var9 = ToolsRes.getString("Dataset.Name.Default");

               for(int var10 = 0; var10 < DatasetTool.this.tabbedPane.getTabCount(); ++var10) {
                  if (var10 != var2) {
                     var3 = (DatasetTab)DatasetTool.this.tabbedPane.getComponentAt(var10);
                     HighlightableDataset var11 = var3.displayData;
                     if (var11.getColumnName(0).equals(var4.getColumnName(0))) {
                        var8.add(String.valueOf(var10));
                     }
                  }
               }

               if (!var8.isEmpty()) {
                  AbstractAction var14 = new AbstractAction() {
                     public void actionPerformed(ActionEvent var1) {
                        int var2 = Integer.parseInt(var1.getActionCommand());
                        DatasetTab var3 = (DatasetTab)DatasetTool.this.tabbedPane.getComponentAt(var2);
                        HighlightableDataset var4x = var3.displayData;
                        String var5x = var4.getName();
                        String var6 = var4x.getName();
                        String var7 = var4.getColumnName(1);
                        String var8 = var4x.getColumnName(1);
                        if (var5x == null || var5x.equals("")) {
                           var5x = var9;
                        }

                        if (var6 == null || var6.equals("")) {
                           var6 = var9;
                        }

                        if (var5x.indexOf(" - ") > -1 || var5x.indexOf(" + ") > -1 || var5x.indexOf(" * ") > -1 || var5x.indexOf(" / ") > -1) {
                           var5x = "(" + var5x + ")";
                        }

                        if (var7.indexOf("-") > -1 || var7.indexOf("+") > -1 || var7.indexOf("*") > -1 || var7.indexOf("/") > -1) {
                           var7 = "(" + var7 + ")";
                        }

                        if (var6.indexOf(" - ") > -1 || var6.indexOf(" + ") > -1 || var6.indexOf(" * ") > -1 || var6.indexOf(" / ") > -1) {
                           var6 = "(" + var6 + ")";
                        }

                        if (var8.indexOf("-") > -1 || var8.indexOf("+") > -1 || var8.indexOf("*") > -1 || var8.indexOf("/") > -1) {
                           var8 = "(" + var8 + ")";
                        }

                        String var9x = "";
                        JMenuItem var10 = (JMenuItem)var1.getSource();
                        if (var10.getParent() == DatasetTool.this.addMenu.getPopupMenu()) {
                           var9x = "+";
                        }

                        if (var10.getParent() == DatasetTool.this.subtractMenu.getPopupMenu()) {
                           var9x = "-";
                        } else if (var10.getParent() == DatasetTool.this.multiplyMenu.getPopupMenu()) {
                           var9x = "*";
                        } else if (var10.getParent() == DatasetTool.this.divideMenu.getPopupMenu()) {
                           var9x = "/";
                        }

                        String var11 = var5x;
                        if (!var5x.equals(var6)) {
                           var11 = var5x + " " + var9x + " " + var6;
                        }

                        String var12 = var7 + var9x + var8;

                        for(int var13 = 0; var13 < DatasetTool.this.tabbedPane.getTabCount(); ++var13) {
                           var3 = (DatasetTab)DatasetTool.this.tabbedPane.getComponentAt(var13);
                           if (var11.equals(var3.dataset.getName()) && var5.equals(var3.dataset.getColumnName(0) + ", " + var12)) {
                              DatasetTool.this.tabbedPane.setSelectedIndex(var13);
                              DatasetTool.this.getSelectedTab().dataTable.tableChanged((TableModelEvent)null);
                              DatasetTool.this.getSelectedTab().refresh();
                              DatasetTool.this.refreshTabTitles();
                              return;
                           }
                        }

                        double[] var20 = var4.getXPoints();
                        double[] var14 = var4.getYPoints();
                        double[] var15 = var4x.getXPoints();
                        double[] var16 = var4x.getYPoints();
                        int var17 = Math.min(var14.length, var16.length);
                        Dataset var18 = new Dataset();

                        for(int var19 = 0; var19 < var17; ++var19) {
                           if (var20[var19] == var15[var19]) {
                              if (var9x.equals("+")) {
                                 var18.append(var20[var19], var14[var19] + var16[var19]);
                              } else if (var9x.equals("-")) {
                                 var18.append(var20[var19], var14[var19] - var16[var19]);
                              } else if (var9x.equals("*")) {
                                 var18.append(var20[var19], var14[var19] * var16[var19]);
                              } else if (var9x.equals("/")) {
                                 var18.append(var20[var19], var14[var19] / var16[var19]);
                              }
                           }
                        }

                        if (var18.getRowCount() > 0) {
                           if (!var11.equals(var9)) {
                              var18.setName(var11);
                           }

                           var18.setXYColumnNames(var4.getColumnName(0), var7 + var9x + var8);
                           var18.setMarkerShape(var4.getMarkerShape());
                           var18.setMarkerSize(var4.getMarkerSize());
                           var18.setConnected(var4.isConnected());
                           var18.setLineColor(var4.getLineColor());
                           var18.setMarkerColor(var4.getFillColor(), var4.getEdgeColor());
                           DatasetTool.this.addTab(var18);
                        } else {
                           String var21 = ToolsRes.getString("Dialog.AddFailed.Title");
                           if (var10.getParent() == DatasetTool.this.subtractMenu.getPopupMenu()) {
                              var21 = ToolsRes.getString("Dialog.SubtractFailed.Title");
                           } else if (var10.getParent() == DatasetTool.this.divideMenu.getPopupMenu()) {
                              var21 = ToolsRes.getString("Dialog.DivideFailed.Title");
                           }

                           JOptionPane.showMessageDialog(DatasetTool.this.tabbedPane, ToolsRes.getString("Dialog.OperationFailed.Message"), var21, 2);
                        }

                     }
                  };
                  DatasetTool.this.addMenu = new JMenu(ToolsRes.getString("Menu.Add"));
                  DatasetTool.this.subtractMenu = new JMenu(ToolsRes.getString("Menu.Subtract"));
                  DatasetTool.this.multiplyMenu = new JMenu(ToolsRes.getString("Menu.MultiplyBy"));
                  DatasetTool.this.divideMenu = new JMenu(ToolsRes.getString("Menu.DivideBy"));
                  var7.addSeparator();
                  var7.add(DatasetTool.this.addMenu);
                  var7.add(DatasetTool.this.subtractMenu);
                  var7.add(DatasetTool.this.multiplyMenu);
                  var7.add(DatasetTool.this.divideMenu);
                  Iterator var15 = var8.iterator();

                  while(var15.hasNext()) {
                     String var12 = (String)var15.next();
                     int var13 = Integer.parseInt(var12);
                     var6 = new JMenuItem(DatasetTool.this.tabbedPane.getTitleAt(var13));
                     var6.setActionCommand(var12);
                     var6.addActionListener(var14);
                     DatasetTool.this.addMenu.add(var6);
                     var6 = new JMenuItem(DatasetTool.this.tabbedPane.getTitleAt(var13));
                     var6.setActionCommand(var12);
                     var6.addActionListener(var14);
                     DatasetTool.this.subtractMenu.add(var6);
                     var6 = new JMenuItem(DatasetTool.this.tabbedPane.getTitleAt(var13));
                     var6.setActionCommand(var12);
                     var6.addActionListener(var14);
                     DatasetTool.this.multiplyMenu.add(var6);
                     var6 = new JMenuItem(DatasetTool.this.tabbedPane.getTitleAt(var13));
                     var6.setActionCommand(var12);
                     var6.addActionListener(var14);
                     DatasetTool.this.divideMenu.add(var6);
                  }
               }

               var7.show(DatasetTool.this.tabbedPane, var1.getX(), var1.getY() + 8);
            }

         }
      });
      int var1 = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
      JMenuBar var2 = new JMenuBar();
      JMenu var3 = new JMenu(ToolsRes.getString("Menu.File"));
      var2.add(var3);
      JMenuItem var4 = new JMenuItem(ToolsRes.getString("MenuItem.Open"));
      var4.setAccelerator(KeyStroke.getKeyStroke(79, var1));
      var4.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DatasetTool.this.open();
         }
      });
      var3.add(var4);
      JMenuItem var5 = new JMenuItem(ToolsRes.getString("MenuItem.Close"));
      var5.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            int var2 = DatasetTool.this.tabbedPane.getSelectedIndex();
            DatasetTool.this.removeTab(var2);
         }
      });
      var3.add(var5);
      JMenuItem var6 = new JMenuItem(ToolsRes.getString("MenuItem.CloseAll"));
      var6.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DatasetTool.this.removeAllTabs();
         }
      });
      var3.add(var6);
      var3.addSeparator();
      JMenuItem var7 = new JMenuItem(ToolsRes.getString("MenuItem.Exit"));
      var7.setAccelerator(KeyStroke.getKeyStroke(81, var1));
      var7.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DatasetTool.this.removeAllTabs();
            System.exit(0);
         }
      });
      var3.add(var7);
      JMenu var8 = new JMenu(ToolsRes.getString("Menu.Edit"));
      var2.add(var8);
      AbstractAction var9 = new AbstractAction(ToolsRes.getString("MenuItem.Copy")) {
         public void actionPerformed(ActionEvent var1) {
            Dataset var2 = DatasetTool.this.getSelectedDataset();
            if (var2 != null) {
               int var3 = DatasetTool.this.tabbedPane.getSelectedIndex();
               String var4 = DatasetTool.this.tabbedPane.getTitleAt(var3);
               OSPLog.finest("copying " + var4);
               XMLControlElement var5 = new XMLControlElement(var2);
               StringSelection var6 = new StringSelection(var5.toXML());
               Clipboard var7 = Toolkit.getDefaultToolkit().getSystemClipboard();
               var7.setContents(var6, var6);
            }
         }
      };
      JMenuItem var10 = new JMenuItem(var9);
      var10.setAccelerator(KeyStroke.getKeyStroke(67, var1));
      var8.add(var10);
      AbstractAction var11 = new AbstractAction(ToolsRes.getString("MenuItem.Paste")) {
         public void actionPerformed(ActionEvent var1) {
            try {
               Clipboard var2 = Toolkit.getDefaultToolkit().getSystemClipboard();
               Transferable var3 = var2.getContents((Object)null);
               String var4 = (String)var3.getTransferData(DataFlavor.stringFlavor);
               if (var4 != null) {
                  XMLControlElement var5 = new XMLControlElement();
                  var5.readXML(var4);
                  if (!var5.failedToRead()) {
                     OSPLog.finest("pasting");
                  }

                  if (DatasetTool.this.loadDatasets(var5).isEmpty()) {
                     OSPLog.finest("no datasets found");
                  }
               }
            } catch (UnsupportedFlavorException var6) {
            } catch (IOException var7) {
            } catch (HeadlessException var8) {
            }

         }
      };
      JMenuItem var12 = new JMenuItem(var11);
      var12.setAccelerator(KeyStroke.getKeyStroke(86, var1));
      var8.add(var12);
      JMenu var13 = new JMenu(ToolsRes.getString("Menu.Help"));
      var2.add(var13);
      JMenuItem var14 = new JMenuItem(ToolsRes.getString("MenuItem.Log"));
      var14.setAccelerator(KeyStroke.getKeyStroke(76, var1));
      var14.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            if (DatasetTool.log.getLocation().x == 0 && DatasetTool.log.getLocation().y == 0) {
               Point var2 = DatasetTool.this.getLocation();
               DatasetTool.log.setLocation(var2.x + 28, var2.y + 28);
            }

            DatasetTool.log.setVisible(true);
         }
      });
      var13.add(var14);
      var13.addSeparator();
      JMenuItem var15 = new JMenuItem(ToolsRes.getString("MenuItem.About"));
      var15.setAccelerator(KeyStroke.getKeyStroke(65, var1));
      var15.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DatasetTool.this.showAboutDialog();
         }
      });
      var13.add(var15);
      this.setJMenuBar(var2);
      this.pack();
      Dimension var16 = Toolkit.getDefaultToolkit().getScreenSize();
      int var17 = (var16.width - this.getBounds().width) / 2;
      int var18 = (var16.height - this.getBounds().height) / 2;
      this.setLocation(var17, var18);
   }

   protected void showAboutDialog() {
      String var1 = this.getName() + " 1.0  Jan 2006\n" + "Open Source Physics Project\n" + "www.opensourcephysics.org";
      JOptionPane.showMessageDialog(this, var1, ToolsRes.getString("Dialog.About.Title") + " " + this.getName(), 1);
   }

   public static void main(String[] var0) {
      DatasetTool var1 = getTool();
      var1.setVisible(true);
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
