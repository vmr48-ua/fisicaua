package org.opensourcephysics.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEditSupport;
import org.opensourcephysics.controls.MessageFrame;
import org.opensourcephysics.controls.OSPLog;
import org.opensourcephysics.controls.OSPParameters;
import org.opensourcephysics.controls.Password;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLControlElement;
import org.opensourcephysics.controls.XMLTreePanel;
import org.opensourcephysics.display.OSPFrame;

public class Launcher {
   protected static String defaultFileName = "launcher_default";
   protected static String resourcesPath = "/org/opensourcephysics/resources/tools/";
   protected static String classPath;
   protected static String tabSetBasePath = "";
   protected static String version = "1.2";
   protected static String releaseDate = "July 2006";
   protected static JFileChooser chooser;
   protected static FileFilter xmlFileFilter;
   protected static FileFilter xsetFileFilter;
   protected static FileFilter launcherFileFilter;
   protected static int wInit = 480;
   protected static int hInit = 400;
   protected static JDialog splashDialog;
   protected static JLabel splashTitleLabel;
   protected static JLabel splashPathLabel;
   protected static Timer splashTimer;
   protected static boolean isWebStart = false;
   protected static float baseMenuFontSize;
   protected static Icon launchIcon;
   protected static Icon launchedIcon;
   protected static Icon singletonIcon;
   protected static Icon whiteFolderIcon;
   protected static Icon redFileIcon;
   protected static Icon greenFileIcon;
   protected static Icon magentaFileIcon;
   protected static Icon yellowFileIcon;
   protected static Icon whiteFileIcon;
   protected static Icon noFileIcon;
   protected static Icon ghostFileIcon;
   protected static Icon redFolderIcon;
   protected static Icon greenFolderIcon;
   protected static Icon yellowFolderIcon;
   protected static Icon linkIcon;
   protected static Icon htmlIcon;
   protected static Icon launchEmptyIcon;
   public static boolean singleAppMode = false;
   public static boolean singletonMode = false;
   private static boolean newVMAllowed = false;
   protected static Timer frameFinder;
   protected static ArrayList existingFrames = new ArrayList();
   public static LaunchNode activeNode;
   protected JDialog inspector;
   protected int divider;
   public JFrame frame;
   protected JPanel contentPane;
   protected JTabbedPane tabbedPane;
   protected JMenuItem singleAppItem;
   protected LaunchNode selectedNode;
   protected LaunchNode previousNode;
   protected String tabSetName;
   protected JEditorPane textPane;
   protected JScrollPane textScroller;
   protected boolean showText;
   protected JMenu fileMenu;
   protected JMenu displayMenu;
   protected JMenu helpMenu;
   protected JMenuItem openItem;
   protected JMenu openFromJarMenu;
   protected JMenuItem closeTabItem;
   protected JMenuItem closeAllItem;
   protected JMenuItem editItem;
   protected JMenuItem exitItem;
   protected JMenuItem inspectItem;
   protected JMenuItem hideItem;
   protected JMenuItem backItem;
   protected JMenu languageMenu;
   protected JMenuItem sizeUpItem;
   protected JMenuItem sizeDownItem;
   protected JMenuItem logItem;
   protected JMenuItem aboutItem;
   protected JMenu diagnosticMenu;
   protected Locale[] locales;
   protected JMenuItem[] languageItems;
   protected LaunchClassChooser classChooser;
   protected JPopupMenu popup;
   protected Set openPaths;
   protected Launcher spawner;
   protected boolean previewing;
   protected boolean editorEnabled;
   protected Set changedFiles;
   protected MouseListener tabListener;
   protected boolean newNodeSelected;
   protected boolean selfContained;
   protected String jarBasePath;
   protected String title;
   protected ArrayList tabs;
   protected UndoManager undoManager;
   protected UndoableEditSupport undoSupport;
   protected String password;
   protected boolean pwRequiredToLoad;
   protected Launcher.LinkEdit undoEdit;
   // $FF: synthetic field
   static Class array$Ljava$lang$String;
   // $FF: synthetic field
   static Class class$java$lang$Object;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$tools$LaunchNode;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$tools$Launcher;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$tools$Launcher$LaunchSet;

   public Launcher() {
      this(true);
   }

   public Launcher(boolean var1) {
      this.divider = 160;
      this.showText = true;
      this.popup = new JPopupMenu();
      this.openPaths = new HashSet();
      this.previewing = false;
      this.editorEnabled = false;
      this.changedFiles = new HashSet();
      this.newNodeSelected = false;
      this.selfContained = false;
      this.jarBasePath = null;
      this.tabs = new ArrayList();
      this.createGUI(var1);
      XML.setLoader(class$org$opensourcephysics$tools$Launcher$LaunchSet == null ? (class$org$opensourcephysics$tools$Launcher$LaunchSet = class$("org.opensourcephysics.tools.Launcher$LaunchSet")) : class$org$opensourcephysics$tools$Launcher$LaunchSet, new Launcher.LaunchSet());
      if (OSPFrame.applet == null) {
         Runnable var2 = new Runnable() {
            public void run() {
               try {
                  Process var1 = Runtime.getRuntime().exec("java");
                  Launcher.newVMAllowed = true;
                  var1.destroy();
               } catch (Exception var2) {
               }

            }
         };

         try {
            Thread var3 = new Thread(var2);
            var3.start();
            var3.join(5000L);
         } catch (InterruptedException var5) {
         }
      }

      if (FontSizer.getLevel() != 0) {
         this.setFontLevel(FontSizer.getLevel());
      } else {
         this.refreshStringResources();
         this.refreshGUI();
      }

      Dimension var6 = Toolkit.getDefaultToolkit().getScreenSize();
      int var7 = (var6.width - this.frame.getBounds().width) / 2;
      int var4 = (var6.height - this.frame.getBounds().height) / 2;
      this.frame.setLocation(var7, var4);
   }

   public Launcher(String var1) {
      this(var1, var1 == null || !var1.startsWith("<?xml"));
   }

   public Launcher(String var1, boolean var2) {
      this.divider = 160;
      this.showText = true;
      this.popup = new JPopupMenu();
      this.openPaths = new HashSet();
      this.previewing = false;
      this.editorEnabled = false;
      this.changedFiles = new HashSet();
      this.newNodeSelected = false;
      this.selfContained = false;
      this.jarBasePath = null;
      this.tabs = new ArrayList();
      this.createGUI(var2);
      XML.setLoader(class$org$opensourcephysics$tools$Launcher$LaunchSet == null ? (class$org$opensourcephysics$tools$Launcher$LaunchSet = class$("org.opensourcephysics.tools.Launcher$LaunchSet")) : class$org$opensourcephysics$tools$Launcher$LaunchSet, new Launcher.LaunchSet());
      String var3 = null;
      if (var1 == null) {
         if (ResourceLoader.launchJarName != null) {
            var1 = XML.stripExtension(ResourceLoader.launchJarName) + ".xset";
            var3 = this.open(var1);
         }

         if (var3 == null) {
            var1 = defaultFileName + ".xset";
            var3 = this.open(var1);
         }

         if (var3 == null) {
            var1 = defaultFileName + ".xml";
            this.open(var1);
         }
      } else {
         this.open(var1);
      }

      if (FontSizer.getLevel() != 0) {
         this.setFontLevel(FontSizer.getLevel());
      } else {
         this.refreshStringResources();
         this.refreshGUI();
      }

      Dimension var4 = Toolkit.getDefaultToolkit().getScreenSize();
      int var5 = (var4.width - this.frame.getBounds().width) / 2;
      int var6 = (var4.height - this.frame.getBounds().height) / 2;
      this.frame.setLocation(var5, var6);
   }

   public Dimension getSize() {
      return this.contentPane.getSize();
   }

   public void setSize(Dimension var1) {
      this.contentPane.setPreferredSize(var1);
      this.frame.pack();
   }

   public void setSize(int var1, int var2) {
      this.contentPane.setPreferredSize(new Dimension(var1, var2));
      this.frame.pack();
   }

   public int getDivider() {
      return this.divider;
   }

   public void setDivider(int var1) {
      this.divider = var1;
      this.refreshGUI();
   }

   public boolean isVisible() {
      return this.frame.isVisible();
   }

   public void setVisible(boolean var1) {
      this.frame.setVisible(var1);
   }

   public LaunchPanel getSelectedTab() {
      return (LaunchPanel)this.tabbedPane.getSelectedComponent();
   }

   public LaunchPanel setSelectedTab(String var1) {
      if (var1 == null) {
         return null;
      } else {
         for(int var2 = 0; var2 < this.tabbedPane.getTabCount(); ++var2) {
            LaunchPanel var3 = this.getTab(var2);
            if (var1.startsWith(var3.getRootNode().name)) {
               this.tabbedPane.setSelectedComponent(var3);
               return var3;
            }
         }

         return null;
      }
   }

   public LaunchNode getSelectedNode() {
      if (this.getSelectedTab() == null) {
         this.selectedNode = null;
      } else {
         this.selectedNode = this.getSelectedTab().getSelectedNode();
      }

      return this.selectedNode;
   }

   public LaunchNode setSelectedNode(String var1) {
      if (var1 == null) {
         return null;
      } else {
         var1 = XML.forwardSlash(var1);
         this.setSelectedTab(var1);
         LaunchPanel var2 = this.getSelectedTab();
         if (var2 == null) {
            return null;
         } else {
            Enumeration var3 = var2.getRootNode().breadthFirstEnumeration();

            LaunchNode var4;
            do {
               if (!var3.hasMoreElements()) {
                  return null;
               }

               var4 = (LaunchNode)var3.nextElement();
            } while(!var1.equals(var4.getPathString()));

            var2.setSelectedNode(var4);
            return var4;
         }
      }
   }

   public LaunchNode getRootNode() {
      return this.getSelectedTab() == null ? null : this.getSelectedTab().getRootNode();
   }

   public LaunchPanel getTab(int var1) {
      return var1 >= this.tabbedPane.getTabCount() ? null : (LaunchPanel)this.tabbedPane.getComponentAt(var1);
   }

   public Container getContentPane() {
      return this.contentPane;
   }

   public String open(String[] var1) {
      if (var1 != null && var1.length != 0) {
         String var2 = this.open(var1[0]);
         if (var1.length > 1 && var1[1] != null) {
            this.setSelectedNode(var1[1]);
         }

         return var2;
      } else {
         return null;
      }
   }

   public String open(String var1) {
      if (var1 != null && !var1.equals("")) {
         ResourceLoader.addSearchPath(resourcesPath);
         ResourceLoader.addSearchPath(tabSetBasePath);
         String var3 = "";
         XMLControlElement var4 = new XMLControlElement();
         if (var1.startsWith("<?xml")) {
            var4.readXML(var1);
            if (var4.failedToRead()) {
               return null;
            }
         }

         if (var4.getObjectClassName().equals((class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object).getName())) {
            var3 = var4.read(var1);
         }

         String var5;
         if (var4.failedToRead()) {
            var5 = XML.stripExtension(ResourceLoader.launchJarName);
            if (!var1.startsWith(defaultFileName) && var5 != null && !var1.startsWith(var5)) {
               OSPLog.info(LaunchRes.getString("Log.Message.InvalidXML") + " " + var1);
               JOptionPane.showMessageDialog((Component)null, LaunchRes.getString("Dialog.InvalidXML.Message") + " \"" + var1 + "\"", LaunchRes.getString("Dialog.InvalidXML.Title"), 2);
            }

            return null;
         } else {
            OSPLog.fine(var1);
            var5 = XML.forwardSlash(var3);
            this.jarBasePath = null;
            String var6 = LaunchRes.getString("Splash.Label.Internal");
            int var7 = Math.max(var5.indexOf("jar!"), var5.indexOf("zip!"));
            String var8;
            if (var7 > -1) {
               var6 = XML.getName(var5.substring(0, var7 + 3));
               var8 = XML.getDirectoryPath(var5.substring(0, var7 + 3));
               this.jarBasePath = var8.equals("") ? XML.forwardSlash(System.getProperty("user.dir", "")) : var8;
               var5 = var5.substring(var7 + 5);
            }

            var8 = this.password;
            if (var4.getPassword() != null) {
               boolean var9 = var4.getBoolean("pw_required_by_launcher");
               if (this instanceof LaunchBuilder || var9) {
                  if (!Password.verify(var4.getPassword(), var5)) {
                     if (this.undoEdit != null) {
                        this.undoSupport.postEdit(new Launcher.LinkEdit(this.undoEdit));
                        this.undoEdit = null;
                     }

                     return null;
                  }

                  this.password = var4.getPassword();
               }
            }

            Class var14 = var4.getObjectClass();
            String var11;
            if ((class$org$opensourcephysics$tools$Launcher$LaunchSet == null ? (class$org$opensourcephysics$tools$Launcher$LaunchSet = class$("org.opensourcephysics.tools.Launcher$LaunchSet")) : class$org$opensourcephysics$tools$Launcher$LaunchSet).equals(var14)) {
               if (!var5.equals("") && splashDialog != null && splashDialog.isVisible()) {
                  Resource var15 = ResourceLoader.getResource(var1);
                  var11 = LaunchRes.getString("Log.Message.Loading") + ": ";
                  isWebStart = var15.getAbsolutePath().indexOf("javaws") > -1;
                  if (var15.getFile() != null) {
                     OSPLog.info(var11 + var15.getAbsolutePath());
                     splashDialog.getContentPane().setBackground(new Color(228, 228, 255));
                     splashPathLabel.setIcon(magentaFileIcon);
                     splashPathLabel.setText(var11 + var1);
                  } else {
                     var11 = LaunchRes.getString("Log.Message.LoadingFrom") + " " + var6 + ": " + var1;
                     boolean var18 = var6.equals(LaunchRes.getString("Splash.Label.Internal")) || var6.equals(ResourceLoader.launchJarName);
                     if (var18) {
                        splashDialog.getContentPane().setBackground(new Color(255, 255, 204));
                        splashPathLabel.setIcon(greenFileIcon);
                     } else {
                        splashDialog.getContentPane().setBackground(new Color(228, 228, 255));
                        splashPathLabel.setIcon(magentaFileIcon);
                     }

                     splashPathLabel.setText(var11);
                     var11 = LaunchRes.getString("Log.Message.Loading") + ": ";
                     OSPLog.info(var11 + var15.getAbsolutePath());
                  }
               }

               if (!this.removeAllTabs()) {
                  this.password = var8;
                  if (this.undoEdit != null) {
                     this.undoSupport.postEdit(new Launcher.LinkEdit(this.undoEdit));
                     this.undoEdit = null;
                  }

                  return null;
               } else {
                  this.tabSetName = XML.getName(var5);
                  if (!var5.equals("")) {
                     if (this.jarBasePath != null) {
                        tabSetBasePath = "";
                     } else {
                        tabSetBasePath = XML.getDirectoryPath(var5);
                     }
                  }

                  OSPLog.finest(LaunchRes.getString("Log.Message.Loading") + ": " + var5);
                  Launcher.LaunchSet var16 = new Launcher.LaunchSet(this, this.tabSetName);
                  var4.loadObject(var16);
                  if (var16.failedToLoad) {
                     if (this.tabSetName.equals(XML.getName(var5))) {
                        this.tabSetName = null;
                     }

                     var5 = null;
                  }

                  this.changedFiles.clear();
                  OSPLog.fine("returning " + var5);
                  this.undoEdit = null;
                  return var5;
               }
            } else if (!(class$org$opensourcephysics$tools$LaunchNode == null ? (class$org$opensourcephysics$tools$LaunchNode = class$("org.opensourcephysics.tools.LaunchNode")) : class$org$opensourcephysics$tools$LaunchNode).equals(var14)) {
               OSPLog.info(LaunchRes.getString("Log.Message.NotLauncherFile"));
               JOptionPane.showMessageDialog((Component)null, LaunchRes.getString("Dialog.NotLauncherFile.Message") + " \"" + var1 + "\"", LaunchRes.getString("Dialog.NotLauncherFile.Title"), 2);
               return null;
            } else {
               OSPLog.finest(LaunchRes.getString("Log.Message.Loading") + ": " + var1);
               LaunchNode var10 = new LaunchNode(LaunchRes.getString("NewNode.Name"));
               var10.setFileName(XML.getPathRelativeTo(var5, tabSetBasePath));
               var4.loadObject(var10);
               var11 = getDisplayName(var5);

               LaunchNode var13;
               for(int var12 = 0; var12 < this.tabbedPane.getComponentCount(); ++var12) {
                  if (this.tabbedPane.getTitleAt(var12).equals(var11)) {
                     var13 = ((LaunchPanel)this.tabbedPane.getComponent(var12)).getRootNode();
                     if (var13.matches(var10)) {
                        this.tabbedPane.setSelectedIndex(var12);
                        return null;
                     }
                  }
               }

               if (this.tabSetName == null) {
                  this.tabSetName = LaunchRes.getString("Tabset.Name.New");
                  this.title = null;
                  tabSetBasePath = XML.getDirectoryPath(var5);
                  this.editorEnabled = false;
               }

               this.addTab(var10);
               Enumeration var17 = var10.breadthFirstEnumeration();

               while(var17.hasMoreElements()) {
                  var13 = (LaunchNode)var17.nextElement();
                  var13.setLaunchClass(var13.launchClassName);
               }

               return var5;
            }
         }
      } else {
         return null;
      }
   }

   protected boolean addTab(LaunchNode var1) {
      final LaunchPanel var2 = new LaunchPanel(var1, this);
      var2.textPane.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent var1) {
            if (Launcher.splashDialog != null) {
               Launcher.splashDialog.dispose();
            }

            var2.textPane.removeMouseListener(this);
         }
      });
      this.tabs.add(var2);
      if (var1.isHiddenInLauncher() && !(this instanceof LaunchBuilder)) {
         return false;
      } else {
         var2.tree.setCellRenderer(new Launcher.LaunchRenderer());
         var2.tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent var1) {
               Launcher.this.newNodeSelected = true;
               Launcher.this.refreshGUI();
            }
         });
         var2.tree.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent var1) {
               if (Launcher.splashDialog != null) {
                  Launcher.splashDialog.dispose();
               }

               var2.tree.removeMouseListener(this);
            }
         });
         var2.tree.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent var1) {
               Launcher.this.popup.removeAll();
               Launcher.this.handleMousePressed(var1, var2);
            }
         });
         this.tabbedPane.addTab(var1.toString(), var2);
         this.tabbedPane.setSelectedComponent(var2);
         var2.setSelectedNode(var1);
         if (!var1.tooltip.equals("")) {
            this.tabbedPane.setToolTipTextAt(this.tabbedPane.getSelectedIndex(), var1.tooltip);
         }

         var2.dataPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent var1) {
               Launcher.this.divider = var2.splitPane.getDividerLocation();
            }
         });
         return true;
      }
   }

   protected void showButtonView(final LaunchNode var1) {
      String var2 = LaunchPanel.defaultType == null ? "text" : LaunchPanel.defaultType;
      if (var1.url != null) {
         try {
            if (var1.url.getContent() != null) {
               this.textPane.setPage(var1.url);
            }
         } catch (IOException var8) {
            OSPLog.finest(LaunchRes.getString("Log.Message.BadURL") + " " + var1.url);
            if (this.showText) {
               this.textPane.setContentType(var2);
               this.textPane.setText(var1.description);
            }
         }
      } else if (this.showText) {
         this.textPane.setContentType(var2);
         this.textPane.setText(var1.description);
      }

      this.contentPane.removeAll();
      this.contentPane.add(this.textScroller, "Center");
      JPanel var3 = new JPanel();
      this.contentPane.add(var3, "South");
      AbstractAction var4 = new AbstractAction() {
         public void actionPerformed(ActionEvent var1x) {
            int var2 = Integer.parseInt(var1x.getActionCommand());
            LaunchNode var3 = (LaunchNode)var1.getChildAt(var2);
            if (var3.getLaunchClass() == null) {
               if (Launcher.this.tabSetName != null) {
                  Launcher.LinkEdit var4 = Launcher.this.new LinkEdit(var3.args);
                  if (Launcher.this.open(var3.args) != null) {
                     Launcher.this.undoSupport.postEdit(var4);
                     Launcher.this.refreshGUI();
                  }
               } else if (Launcher.this.open(var3.args) != null) {
                  Launcher.this.refreshGUI();
               }
            } else {
               var3.launch();
            }

         }
      };

      for(int var5 = 0; var5 < var1.getChildCount(); ++var5) {
         LaunchNode var6 = (LaunchNode)var1.getChildAt(var5);
         JButton var7 = new JButton(var6.name);
         var7.addActionListener(var4);
         var7.setActionCommand(String.valueOf(var5));
         var7.setToolTipText(var6.tooltip);
         var3.add(var7);
      }

      this.frame.validate();
      this.refreshGUI();
      this.frame.repaint();
   }

   protected void showTabbedPaneView() {
      if (this.contentPane.getComponent(0) != this.tabbedPane) {
         this.contentPane.removeAll();
         this.contentPane.add(this.tabbedPane, "Center");
         this.frame.validate();
         this.refreshGUI();
         this.frame.repaint();
      }
   }

   protected String open() {
      getXMLChooser().setFileFilter(launcherFileFilter);
      int var1 = getXMLChooser().showOpenDialog((Component)null);
      if (var1 == 0) {
         File var2 = getXMLChooser().getSelectedFile();
         String var3 = XML.forwardSlash(var2.getAbsolutePath());
         OSPFrame.chooserDir = XML.getDirectoryPath(var3);
         return this.open(var3);
      } else {
         return null;
      }
   }

   protected boolean removeSelectedTab() {
      int var1 = this.tabbedPane.getSelectedIndex();
      if (var1 < 0) {
         return false;
      } else {
         Launcher.LinkEdit var2 = null;
         if (this.tabSetName != null && (new File(this.tabSetName)).exists()) {
            String[] var3 = new String[]{" "};
            var2 = new Launcher.LinkEdit(var3);
         }

         this.tabs.remove(this.getTab(var1));
         this.tabbedPane.removeTabAt(var1);
         this.previousNode = this.selectedNode;
         this.newNodeSelected = true;
         if (this.tabbedPane.getTabCount() == 0) {
            this.tabSetName = null;
            this.title = null;
            this.editorEnabled = false;
            if (var2 != null) {
               this.undoSupport.postEdit(var2);
            }
         }

         this.refreshGUI();
         return true;
      }
   }

   protected boolean removeAllTabs() {
      int var1 = this.tabbedPane.getTabCount();
      if (var1 == 0) {
         return true;
      } else {
         int var2;
         for(var2 = var1 - 1; var2 >= 0; --var2) {
            this.tabbedPane.removeTabAt(var2);
         }

         if (this.tabbedPane.getTabCount() == 0) {
            this.tabSetName = null;
            this.title = null;
            this.editorEnabled = false;
            this.password = null;
         } else {
            this.previousNode = this.selectedNode;
            this.newNodeSelected = true;
         }

         this.refreshGUI();
         this.tabs.clear();

         for(var2 = 0; var2 < this.tabbedPane.getTabCount(); ++var2) {
            this.tabs.add(this.tabbedPane.getComponentAt(var2));
         }

         return this.tabbedPane.getTabCount() == 0;
      }
   }

   protected void refreshStringResources() {
      this.fileMenu.setText(LaunchRes.getString("Menu.File"));
      this.displayMenu.setText(LaunchRes.getString("Menu.Display"));
      this.openItem.setText(LaunchRes.getString("Menu.File.Open"));
      this.closeTabItem.setText(LaunchRes.getString("Menu.File.CloseTab"));
      this.closeAllItem.setText(LaunchRes.getString("Menu.File.CloseAll"));
      this.editItem.setText(LaunchRes.getString("Menu.File.Edit"));
      this.backItem.setText(LaunchRes.getString("Menu.File.Back"));
      this.helpMenu.setText(LaunchRes.getString("Menu.Help"));
      this.logItem.setText(LaunchRes.getString("Menu.Help.MessageLog"));
      this.inspectItem.setText(LaunchRes.getString("Menu.Help.Inspect"));
      this.aboutItem.setText(LaunchRes.getString("Menu.Help.About"));
      this.diagnosticMenu.setText(LaunchRes.getString("Menu.Help.Diagnostics"));
      if (this.exitItem != null) {
         this.exitItem.setText(LaunchRes.getString("Menu.File.Exit"));
      }

      this.languageMenu.setText(LaunchRes.getString("Menu.Display.Language"));
      this.sizeUpItem.setText(LaunchRes.getString("Menu.Display.IncreaseFontSize"));
      this.sizeDownItem.setText(LaunchRes.getString("Menu.Display.DecreaseFontSize"));
      if (this.openFromJarMenu != null) {
         this.openFromJarMenu.setText(LaunchRes.getString("Menu.File.OpenFromJar"));
      }

      for(int var1 = 0; var1 < this.locales.length; ++var1) {
         if (this.locales[var1].getLanguage().equals(LaunchRes.locale.getLanguage())) {
            this.languageItems[var1].setSelected(true);
         }
      }

   }

   protected void refreshGUI() {
      String var1 = this.title == null ? this.tabSetName : this.title;
      if (var1 == null) {
         var1 = LaunchRes.getString("Frame.Title");
      } else {
         var1 = LaunchRes.getString("Frame.Title") + ": " + var1;
      }

      LaunchPanel var2 = this.getSelectedTab();
      if (var2 != null) {
         this.getSelectedTab().splitPane.setDividerLocation(this.divider);
      }

      this.fileMenu.removeAll();
      if (this.undoManager.canUndo()) {
         this.fileMenu.add(this.backItem);
         this.fileMenu.addSeparator();
      }

      if (OSPFrame.applet == null && (var2 == null || !var2.getRootNode().isButtonView())) {
         this.fileMenu.add(this.openItem);
      }

      if (this.openFromJarMenu != null) {
         this.fileMenu.add(this.openFromJarMenu);
      }

      if (OSPFrame.applet != null) {
         this.fileMenu.add(this.hideItem);
      } else {
         if (var2 != null) {
            if (this.fileMenu.getItemCount() > 0) {
               this.fileMenu.addSeparator();
            }

            boolean var3 = true;
            if (this.getClass() == (class$org$opensourcephysics$tools$Launcher == null ? (class$org$opensourcephysics$tools$Launcher = class$("org.opensourcephysics.tools.Launcher")) : class$org$opensourcephysics$tools$Launcher)) {
               if (var2.getRootNode().isButtonView()) {
                  var3 = false;
                  var1 = LaunchRes.getString("Frame.Title") + ": " + var2.getRootNode().name;
               } else if (this.tabbedPane.getTabCount() == 1) {
                  var3 = false;
               }
            }

            if (var3) {
               this.fileMenu.add(this.closeTabItem);
               this.closeAllItem.setText(LaunchRes.getString("Menu.File.CloseAll"));
            } else {
               this.closeAllItem.setText(LaunchRes.getString("MenuItem.Close"));
            }

            this.fileMenu.add(this.closeAllItem);
            if (this.editorEnabled) {
               this.fileMenu.addSeparator();
               this.fileMenu.add(this.editItem);
            }
         }

         this.fileMenu.addSeparator();
         if (this.exitItem != null) {
            this.fileMenu.add(this.exitItem);
         }

         this.inspectItem.setEnabled(this.password == null || this instanceof LaunchBuilder);
         this.sizeDownItem.setEnabled((float)this.fileMenu.getFont().getSize() > baseMenuFontSize);
         this.frame.setTitle(var1);
      }
   }

   private void appletGUI() {
      this.hideItem = new JMenuItem(LaunchRes.getString("Menu.File.Hide"));
      this.hideItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Launcher.this.exit();
         }
      });
   }

   protected void createGUI(boolean var1) {
      this.appletGUI();
      Frame[] var2 = Frame.getFrames();
      int var3 = 0;

      int var4;
      for(var4 = var2.length; var3 < var4; ++var3) {
         existingFrames.add(var2[var3]);
      }

      OSPLog.getOSPLog();
      this.undoManager = new UndoManager();
      this.undoSupport = new UndoableEditSupport();
      this.undoSupport.addUndoableEditListener(this.undoManager);
      this.frame = new Launcher.LauncherFrame();
      existingFrames.add(this.frame);
      if (var1 && OSPFrame.applet == null) {
         this.splash();
      }

      this.inspector = new JDialog(this.frame, false);
      this.inspector.setSize(new Dimension(600, 300));
      Dimension var23 = Toolkit.getDefaultToolkit().getScreenSize();
      var4 = (var23.width - this.inspector.getBounds().width) / 2;
      int var5 = (var23.height - this.inspector.getBounds().height) / 2;
      this.inspector.setLocation(var4, var5);
      this.contentPane = new JPanel(new BorderLayout());
      this.contentPane.setPreferredSize(new Dimension(wInit, hInit));
      this.frame.setContentPane(this.contentPane);
      this.frame.setDefaultCloseOperation(1);
      String var6 = "/org/opensourcephysics/resources/tools/images/launch.gif";
      launchIcon = ResourceLoader.getIcon(var6);
      var6 = "/org/opensourcephysics/resources/tools/images/launched.gif";
      launchedIcon = ResourceLoader.getIcon(var6);
      var6 = "/org/opensourcephysics/resources/tools/images/singleton.gif";
      singletonIcon = ResourceLoader.getIcon(var6);
      var6 = "/org/opensourcephysics/resources/tools/images/nofile.gif";
      noFileIcon = ResourceLoader.getIcon(var6);
      var6 = "/org/opensourcephysics/resources/tools/images/greenfile.gif";
      greenFileIcon = ResourceLoader.getIcon(var6);
      var6 = "/org/opensourcephysics/resources/tools/images/magentafile.gif";
      magentaFileIcon = ResourceLoader.getIcon(var6);
      var6 = "/org/opensourcephysics/resources/tools/images/link.gif";
      linkIcon = ResourceLoader.getIcon(var6);
      var6 = "/org/opensourcephysics/resources/tools/images/html.gif";
      htmlIcon = ResourceLoader.getIcon(var6);
      var6 = "/org/opensourcephysics/resources/tools/images/launchempty.gif";
      launchEmptyIcon = ResourceLoader.getIcon(var6);
      this.textPane = new JTextPane();
      this.textPane.setEditable(false);
      this.textPane.addHyperlinkListener(new HyperlinkListener() {
         public void hyperlinkUpdate(HyperlinkEvent var1) {
            if (var1.getEventType() == EventType.ACTIVATED) {
               try {
                  Launcher.this.textPane.setPage(var1.getURL());
               } catch (IOException var3) {
               }
            }

         }
      });
      this.textScroller = new JScrollPane(this.textPane);
      this.tabbedPane = new JTabbedPane(3);
      this.contentPane.add(this.tabbedPane, "Center");
      this.tabbedPane.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent var1) {
            LaunchNode var2 = Launcher.this.getRootNode();
            if (var2 != null && var2.isButtonView() && Launcher.this.getClass() == (Launcher.class$org$opensourcephysics$tools$Launcher == null ? (Launcher.class$org$opensourcephysics$tools$Launcher = Launcher.class$("org.opensourcephysics.tools.Launcher")) : Launcher.class$org$opensourcephysics$tools$Launcher)) {
               Launcher.this.showButtonView(var2);
            } else {
               Launcher.this.showTabbedPaneView();
            }

            Launcher.this.previousNode = Launcher.this.selectedNode;
            Launcher.this.newNodeSelected = true;
            Launcher.this.refreshGUI();
         }
      });
      this.tabListener = new MouseAdapter() {
         public void mousePressed(MouseEvent var1) {
            if (Launcher.this.contentPane.getTopLevelAncestor() == Launcher.this.frame) {
               if (var1.isPopupTrigger() || var1.getButton() == 3 || var1.isControlDown() && System.getProperty("os.name", "").indexOf("Mac") > -1) {
                  JPopupMenu var2 = new JPopupMenu();
                  JMenuItem var3 = new JMenuItem(LaunchRes.getString("MenuItem.Close"));
                  var3.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent var1) {
                        Launcher.this.removeSelectedTab();
                     }
                  });
                  var2.add(var3);
                  var2.show(Launcher.this.tabbedPane, var1.getX(), var1.getY() + 8);
               }

            }
         }
      };
      this.tabbedPane.addMouseListener(this.tabListener);
      JMenuBar var7 = new JMenuBar();
      this.fileMenu = new JMenu();
      this.fileMenu.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent var1) {
            if (Launcher.splashDialog != null) {
               Launcher.splashDialog.dispose();
            }

            Launcher.this.fileMenu.removeMouseListener(this);
         }
      });
      var7.add(this.fileMenu);
      this.openItem = new JMenuItem();
      int var8 = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
      this.openItem.setAccelerator(KeyStroke.getKeyStroke(79, var8));
      this.openItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            String[] var2 = new String[]{" "};
            if (Launcher.this.tabSetName != null && (new File(Launcher.this.tabSetName)).exists()) {
               Launcher.LinkEdit var3 = Launcher.this.new LinkEdit(var2);
               String var4 = Launcher.this.open();
               if (var4 != null) {
                  var3.args[0] = var4;
                  Launcher.this.undoSupport.postEdit(var3);
                  Launcher.this.refreshGUI();
               }
            } else {
               Launcher.this.open();
               Launcher.this.refreshGUI();
            }

         }
      });
      this.closeTabItem = new JMenuItem();
      this.closeTabItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Launcher.this.removeSelectedTab();
         }
      });
      this.closeAllItem = new JMenuItem();
      this.closeAllItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Launcher.LinkEdit var2 = null;
            if (Launcher.this.tabSetName != null && (new File(Launcher.this.tabSetName)).exists()) {
               String[] var3 = new String[]{" "};
               var2 = Launcher.this.new LinkEdit(var3);
            }

            if (Launcher.this.removeAllTabs() && var2 != null) {
               Launcher.this.undoSupport.postEdit(var2);
            }

            Launcher.this.refreshGUI();
         }
      });
      this.editItem = new JMenuItem();
      this.editItem.setAccelerator(KeyStroke.getKeyStroke(69, var8));
      this.editItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            if (Launcher.this.password == null || Launcher.this.pwRequiredToLoad || Password.verify(Launcher.this.password, Launcher.this.tabSetName)) {
               if (Launcher.this.previewing) {
                  Launcher.this.previewing = false;
                  if (Launcher.this.spawner != null) {
                     LaunchNode var2 = Launcher.this.getSelectedNode();
                     if (var2 != null) {
                        Launcher.this.spawner.setSelectedNode(var2.getPathString());
                     }
                  }

                  Launcher.this.exit();
               } else {
                  Launcher.LaunchSet var7 = Launcher.this.new LaunchSet(Launcher.this, Launcher.this.tabSetName);
                  XMLControlElement var3 = new XMLControlElement(var7);
                  var3.setPassword((String)null);
                  LaunchBuilder var4 = new LaunchBuilder(var3.toXML());
                  LaunchNode var5 = Launcher.this.getSelectedNode();
                  if (var5 != null) {
                     var4.setSelectedNode(var5.getPathString());
                  }

                  var4.spawner = Launcher.this;
                  var4.tabSetName = Launcher.this.tabSetName;
                  var4.password = Launcher.this.password;
                  var4.jarBasePath = Launcher.this.jarBasePath;
                  Point var6 = Launcher.this.frame.getLocation();
                  var4.frame.setLocation(var6.x + 24, var6.y + 24);
                  var4.frame.setVisible(true);
                  var4.frame.setDefaultCloseOperation(0);
               }

            }
         }
      });
      this.backItem = new JMenuItem();
      this.backItem.setAccelerator(KeyStroke.getKeyStroke(37, var8));
      this.backItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Launcher.this.undoManager.undo();
         }
      });
      this.displayMenu = new JMenu();
      var7.add(this.displayMenu);
      LaunchRes.addPropertyChangeListener("locale", new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent var1) {
            Launcher.this.refreshStringResources();
            Launcher.this.refreshGUI();
         }
      });
      this.languageMenu = new JMenu();
      this.locales = new Locale[]{Locale.ENGLISH, new Locale("es"), Locale.TAIWAN};
      AbstractAction var9 = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            String var2 = var1.getActionCommand();
            OSPLog.finest("setting language to " + var2);

            for(int var3 = 0; var3 < Launcher.this.locales.length; ++var3) {
               if (var2.equals(Launcher.this.locales[var3].getDisplayName())) {
                  LaunchRes.setLocale(Launcher.this.locales[var3]);
                  return;
               }
            }

         }
      };
      ButtonGroup var10 = new ButtonGroup();
      this.languageItems = new JMenuItem[this.locales.length];

      for(int var11 = 0; var11 < this.locales.length; ++var11) {
         this.languageItems[var11] = new JRadioButtonMenuItem(this.locales[var11].getDisplayName(this.locales[var11]));
         this.languageItems[var11].setActionCommand(this.locales[var11].getDisplayName());
         this.languageItems[var11].addActionListener(var9);
         this.languageMenu.add(this.languageItems[var11]);
         var10.add(this.languageItems[var11]);
      }

      this.displayMenu.add(this.languageMenu);
      this.displayMenu.addSeparator();
      FontSizer.addPropertyChangeListener("level", new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent var1) {
            int var2 = (Integer)var1.getNewValue();
            Launcher.this.setFontLevel(var2);
         }
      });
      this.sizeUpItem = new JMenuItem();
      this.sizeUpItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            FontSizer.levelUp();
         }
      });
      this.displayMenu.add(this.sizeUpItem);
      this.sizeDownItem = new JMenuItem();
      this.sizeDownItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            FontSizer.levelDown();
         }
      });
      this.displayMenu.add(this.sizeDownItem);
      this.helpMenu = new JMenu();
      this.helpMenu.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent var1) {
            if (Launcher.splashDialog != null) {
               Launcher.splashDialog.dispose();
            }

            Launcher.this.helpMenu.removeMouseListener(this);
         }
      });
      var7.add(this.helpMenu);
      this.logItem = new JMenuItem();
      this.logItem.setAccelerator(KeyStroke.getKeyStroke(76, var8));
      this.logItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            if (OSPFrame.applet == null) {
               OSPLog var2 = OSPLog.getOSPLog();
               if (var2.getLocation().x == 0 && var2.getLocation().y == 0) {
                  Point var3 = Launcher.this.frame.getLocation();
                  var2.setLocation(var3.x + 28, var3.y + 28);
               }
            }

            OSPLog.showLog();
         }
      });
      this.helpMenu.add(this.logItem);
      this.inspectItem = new JMenuItem();
      this.helpMenu.add(this.inspectItem);
      this.inspectItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Launcher.LaunchSet var2 = Launcher.this.new LaunchSet(Launcher.this, Launcher.this.tabSetName);
            var2.showHiddenNodes = Launcher.this instanceof LaunchBuilder;
            XMLControlElement var3 = new XMLControlElement(var2);
            XMLTreePanel var4 = new XMLTreePanel(var3, false);
            Launcher.this.inspector.setContentPane(var4);
            Launcher.this.inspector.setTitle(LaunchRes.getString("Inspector.Title.TabSet") + " \"" + Launcher.getDisplayName(Launcher.this.tabSetName) + "\"");
            Launcher.this.inspector.setVisible(true);
         }
      });
      this.diagnosticMenu = new JMenu();
      this.helpMenu.add(this.diagnosticMenu);
      JMenuItem var24 = new JMenuItem("Java VM");
      var24.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Diagnostics.aboutJava();
         }
      });
      this.diagnosticMenu.add(var24);
      JMenuItem var12 = new JMenuItem("QuickTime");
      var12.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Diagnostics.aboutQTJava();
         }
      });
      this.diagnosticMenu.add(var12);
      JMenuItem var13 = new JMenuItem("Java 3D");
      var13.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Diagnostics.aboutJava3D();
         }
      });
      this.diagnosticMenu.add(var13);
      JMenuItem var14 = new JMenuItem("JOGL");
      var14.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Diagnostics.aboutJOGL();
         }
      });
      this.diagnosticMenu.add(var14);
      this.helpMenu.addSeparator();
      this.aboutItem = new JMenuItem();
      this.aboutItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Launcher.this.showAboutDialog();
         }
      });
      this.helpMenu.add(this.aboutItem);
      if (OSPFrame.applet == null) {
         this.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent var1) {
               Launcher.this.exit();
            }
         });
         this.fileMenu.addSeparator();
         this.exitItem = new JMenuItem();
         this.exitItem.setAccelerator(KeyStroke.getKeyStroke(81, var8));
         this.exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               Launcher.this.exit();
            }
         });
      }

      if (ResourceLoader.launchJarPath != null) {
         JarFile var15 = null;

         try {
            if (OSPFrame.applet == null) {
               var15 = new JarFile(ResourceLoader.launchJarPath);
            } else {
               URL var16;
               if (ResourceLoader.launchJarPath.startsWith("http:")) {
                  var16 = new URL("jar:" + ResourceLoader.launchJarPath + "!/");
               } else {
                  var16 = new URL("jar:file:/" + ResourceLoader.launchJarPath + "!/");
               }

               JarURLConnection var17 = (JarURLConnection)var16.openConnection();
               var15 = var17.getJarFile();
            }
         } catch (IOException var21) {
            OSPLog.warning(var21.getClass().getName() + ": " + var21.getMessage());
         } catch (SecurityException var22) {
            OSPLog.warning(var22.getClass().getName() + ": " + var22.getMessage());
         }

         if (var15 != null) {
            AbstractAction var25 = new AbstractAction() {
               public void actionPerformed(ActionEvent var1) {
                  String var2 = ((JMenuItem)var1.getSource()).getText();
                  var2 = ResourceLoader.launchJarName + "!/" + var2;
                  if (Launcher.this.tabSetName != null) {
                     Launcher.LinkEdit var3 = Launcher.this.new LinkEdit(new String[]{var2});
                     if (Launcher.this.open(var2) != null) {
                        Launcher.this.undoSupport.postEdit(var3);
                        Launcher.this.refreshGUI();
                     }
                  } else {
                     Launcher.this.open(var2);
                     Launcher.this.refreshGUI();
                  }

               }
            };
            Enumeration var26 = var15.entries();

            while(var26.hasMoreElements()) {
               JarEntry var18 = (JarEntry)var26.nextElement();
               String var19 = var18.getName();
               if (var19.endsWith(".xset") && !var19.startsWith(resourcesPath.substring(1)) && !var19.startsWith(defaultFileName)) {
                  if (this.openFromJarMenu == null) {
                     this.openFromJarMenu = new JMenu();
                  }

                  JMenuItem var20 = new JMenuItem(var19);
                  var20.addActionListener(var25);
                  this.openFromJarMenu.add(var20);
               }
            }
         }
      }

      baseMenuFontSize = (float)this.fileMenu.getFont().getSize();
      this.frame.setJMenuBar(var7);
      this.frame.pack();
   }

   protected void setFontLevel(int var1) {
      FontSizer.setFonts(this.frame.getJMenuBar(), var1);
      FontSizer.setFonts(this.frame.getContentPane(), var1);
      this.refreshStringResources();
      LaunchPanel var2 = this.getSelectedTab();
      if (var2 != null) {
         Enumeration var3 = var2.getRootNode().breadthFirstEnumeration();

         while(var3.hasMoreElements()) {
            LaunchNode var4 = (LaunchNode)var3.nextElement();
            var2.treeModel.nodeChanged(var4);
         }
      }

      this.refreshGUI();
   }

   protected Set getOpenPaths() {
      this.openPaths.clear();
      this.openPaths.add(this.tabSetName);

      for(int var1 = 0; var1 < this.tabbedPane.getTabCount(); ++var1) {
         LaunchPanel var2 = (LaunchPanel)this.tabbedPane.getComponentAt(var1);
         LaunchNode[] var3 = var2.getRootNode().getAllOwnedNodes();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            this.openPaths.add(var3[var4].getFileName());
         }

         this.openPaths.add(var2.getRootNode().getFileName());
      }

      return this.openPaths;
   }

   protected void showAboutDialog() {
      String var1 = XML.NEW_LINE;
      String var2 = "Launcher " + version + "   " + releaseDate + var1 + "Open Source Physics Project" + var1 + "www.opensourcephysics.org";
      JOptionPane.showMessageDialog(this.frame, var2, LaunchRes.getString("Help.About.Title") + " Launcher", 1);
   }

   protected boolean isLink(LaunchNode var1) {
      if (var1 != null && var1.isLeaf() && (var1.launchClassName == null || var1.launchClassName.equals(""))) {
         return !var1.args[0].equals("") || var1.args.length > 1 && !var1.args[1].equals("") || var1.args.length > 2 && !var1.args[2].equals("");
      } else {
         return false;
      }
   }

   protected boolean isLaunchable(LaunchNode var1) {
      return var1 != null && var1.isLeaf() ? isLaunchable(var1.getLaunchClass()) : false;
   }

   protected static boolean isLaunchable(Class var0) {
      if (var0 == null) {
         return false;
      } else {
         try {
            var0.getMethod("main", array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            return true;
         } catch (NoSuchMethodException var2) {
            return false;
         }
      }
   }

   protected void handleMousePressed(MouseEvent var1, final LaunchPanel var2) {
      LaunchNode var3 = this.getSelectedNode();
      if (!var1.isPopupTrigger() && var1.getButton() != 3 && (!var1.isControlDown() || System.getProperty("os.name", "").indexOf("Mac") <= -1)) {
         if (var1.getClickCount() == 2 && this.isLaunchable(var3)) {
            if (var3.launchCount == 0) {
               var3.launch(var2);
            } else if (!var3.isSingleton() && (!var3.isSingleVM() || !var3.isSingleApp())) {
               int var11 = JOptionPane.showConfirmDialog(this.frame, LaunchRes.getString("Dialog.Relaunch.Message"), LaunchRes.getString("Dialog.Relaunch.Title"), 0);
               if (var11 == 0) {
                  var3.launch(var2);
               }
            } else {
               JOptionPane.showMessageDialog(this.frame, LaunchRes.getString("Dialog.Singleton.Message") + " \"" + var3.toString() + "\"", LaunchRes.getString("Dialog.Singleton.Title"), 1);
            }

            if (var3.launchPanel != null) {
               var3.launchPanel.repaint();
            }
         } else if (var1.getClickCount() == 2 && this.isLink(var3)) {
            if (this.tabSetName != null) {
               Launcher.LinkEdit var10 = new Launcher.LinkEdit(var3.args);
               if (this.open(var3.args) != null) {
                  this.undoSupport.postEdit(var10);
                  this.refreshGUI();
               } else {
                  this.open(var3.args);
                  this.refreshGUI();
               }
            }
         } else if (var1.getClickCount() == 2 && var3.getLaunchClass() == null && var3.launchClassName != null && !var3.launchClassName.equals("")) {
            String[] var9 = LaunchClassChooser.parsePath(var3.getClassPath(), true);
            String var12 = "";

            for(int var13 = 0; var13 < var9.length; ++var13) {
               if (!var12.equals("")) {
                  if (var9[var13].equals(ResourceLoader.launchJarName)) {
                     continue;
                  }

                  var12 = var12 + ", ";
               }

               var12 = var12 + var9[var13];
            }

            JOptionPane.showMessageDialog(this.frame, LaunchRes.getString("Dialog.ClassNotFound.Message1") + var3.launchClassName + XML.NEW_LINE + LaunchRes.getString("Dialog.ClassNotFound.Message2") + var12, LaunchRes.getString("Dialog.ClassNotFound.Title"), 2);
         }
      } else {
         TreePath var4 = var2.tree.getPathForLocation(var1.getX(), var1.getY());
         if (var4 == null) {
            return;
         }

         var2.tree.setSelectionPath(var4);
         final LaunchNode var5 = this.getSelectedNode();
         if (var5 == null) {
            return;
         }

         JMenuItem var6 = new JMenuItem(LaunchRes.getString("MenuItem.Inspect"));
         var6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               Launcher.this.inspectXML(var5);
            }
         });
         var6.setEnabled(this.password == null || this instanceof LaunchBuilder);
         this.popup.add(var6);
         if (var5.getLaunchClass() != null) {
            JMenuItem var7;
            if (var5.launchCount == 0) {
               this.popup.addSeparator();
               var7 = new JMenuItem(LaunchRes.getString("MenuItem.Launch"));
               this.popup.add(var7);
               var7.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent var1) {
                     var5.launch(var2);
                  }
               });
            } else {
               this.popup.addSeparator();
               var7 = new JMenuItem(LaunchRes.getString("MenuItem.Terminate"));
               this.popup.add(var7);
               var7.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent var1) {
                     var5.terminateAll();
                  }
               });
               if (var5.launchCount > 1) {
                  var7.setText(LaunchRes.getString("MenuItem.TerminateAll"));
               }

               if (!var5.isSingleton()) {
                  JMenuItem var8 = new JMenuItem(LaunchRes.getString("MenuItem.Relaunch"));
                  this.popup.add(var8);
                  var8.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent var1) {
                        var5.launch(var2);
                     }
                  });
               }
            }
         }

         if (this.getClass().equals(class$org$opensourcephysics$tools$Launcher == null ? (class$org$opensourcephysics$tools$Launcher = class$("org.opensourcephysics.tools.Launcher")) : class$org$opensourcephysics$tools$Launcher)) {
            this.popup.show(var2, var1.getX() + 4, var1.getY() + 12);
         }
      }

   }

   private void exitCurrentApps() {
      Frame[] var1 = Frame.getFrames();
      int var2 = 0;

      for(int var3 = var1.length; var2 < var3; ++var2) {
         if (!existingFrames.contains(var1[var2]) && !(var1[var2] instanceof Launcher.LauncherFrame)) {
            WindowListener[] var4 = var1[var2].getWindowListeners();

            for(int var5 = 0; var5 < var4.length; ++var5) {
               var4[var5].windowClosing((WindowEvent)null);
            }

            var1[var2].dispose();
         }
      }

   }

   private void splash() {
      short var1 = 360;
      byte var2 = 120;
      if (splashDialog == null) {
         splashDialog = new JDialog(this.frame, false);
         splashDialog.setUndecorated(true);
         Color var3 = new Color(128, 0, 0);
         JPanel var4 = new JPanel(new BorderLayout());
         var4.setBackground(new Color(255, 255, 255));
         var4.setPreferredSize(new Dimension(var1, var2));
         var4.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent var1) {
               Launcher.splashDialog.dispose();
            }
         });
         this.frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent var1) {
               Launcher.splashDialog.dispose();
               Launcher.this.frame.removeMouseListener(this);
            }
         });
         Border var5 = BorderFactory.createEtchedBorder();
         var4.setBorder(var5);
         splashDialog.setContentPane(var4);
         Box var6 = Box.createVerticalBox();
         JLabel var7 = new JLabel("OSP Launcher");
         if (this instanceof LaunchBuilder) {
            var7.setText("OSP Launch Builder");
         }

         Font var8 = var7.getFont().deriveFont(1);
         var7.setFont(var8.deriveFont(20.0F));
         var7.setForeground(var3);
         var7.setAlignmentX(0.5F);
         var7.setHorizontalAlignment(0);
         JLabel var9 = new JLabel("By Douglas Brown");
         var9.setFont(var8.deriveFont(10.0F));
         var9.setHorizontalAlignment(0);
         var9.setAlignmentX(0.5F);
         splashPathLabel = new JLabel(" ") {
            public void setText(String var1) {
               byte var2 = 80;
               if (var1 != null && var1.length() > var2) {
                  var1 = var1.substring(0, var2 - 4) + "...";
               }

               super.setText(var1);
            }
         };
         var8 = var8.deriveFont(0);
         splashPathLabel.setFont(var8.deriveFont(12.0F));
         splashPathLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 2));
         splashPathLabel.setHorizontalAlignment(0);
         var6.add(Box.createGlue());
         var6.add(var7);
         var6.add(var9);
         var6.add(Box.createGlue());
         var4.add(var6, "Center");
         var4.add(splashPathLabel, "South");
         splashDialog.pack();
         splashTimer = new Timer(4000, new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               if (Launcher.this.frame.isShowing()) {
                  Launcher.splashDialog.dispose();
                  Launcher.splashTimer.stop();
               }

            }
         });
      }

      Dimension var10 = Toolkit.getDefaultToolkit().getScreenSize();
      int var11 = var10.width / 2;
      int var12 = var10.height / 2;
      splashDialog.setLocation(var11 - var1 / 2, var12 - var2 / 2);
      splashDialog.setVisible(true);
      splashTimer.start();
   }

   protected void exit() {
      if (!this.terminateApps()) {
         final int var1 = this.frame.getDefaultCloseOperation();
         this.frame.setDefaultCloseOperation(0);
         Runnable var2 = new Runnable() {
            public void run() {
               Launcher.this.frame.setDefaultCloseOperation(var1);
            }
         };
         SwingUtilities.invokeLater(var2);
      } else {
         if (OSPFrame.applet == null && this.frame.getDefaultCloseOperation() == 1) {
            System.exit(0);
         } else {
            this.exitCurrentApps();
            this.frame.setVisible(false);
         }

      }
   }

   protected boolean terminateApps() {
      if (this.frame.getDefaultCloseOperation() == 1) {
         boolean var1 = false;
         Frame[] var2 = Frame.getFrames();
         int var3 = 0;

         int var5;
         for(int var4 = var2.length; var3 < var4; ++var3) {
            if (!var1 && var2[var3].isVisible() && !(var2[var3] instanceof Launcher.LauncherFrame) && !(var2[var3] instanceof OSPLog) && !(var2[var3] instanceof EncryptionTool) && !existingFrames.contains(var2[var3])) {
               var5 = JOptionPane.showConfirmDialog(this.frame, LaunchRes.getString("Dialog.Terminate.Message") + XML.NEW_LINE + LaunchRes.getString("Dialog.Terminate.Question"), LaunchRes.getString("Dialog.Terminate.Title"), 0);
               if (var5 != 0) {
                  return false;
               }

               var1 = true;
            }
         }

         var1 = false;
         boolean var11 = false;
         Component[] var12 = this.tabbedPane.getComponents();

         label77:
         for(var5 = 0; var5 < var12.length; ++var5) {
            LaunchPanel var6 = (LaunchPanel)var12[var5];
            Enumeration var7 = var6.getRootNode().breadthFirstEnumeration();

            while(true) {
               LaunchNode var8;
               do {
                  if (!var7.hasMoreElements()) {
                     continue label77;
                  }

                  var8 = (LaunchNode)var7.nextElement();
               } while(var8.processes.isEmpty());

               if (!var1 && !var11) {
                  int var9 = JOptionPane.showConfirmDialog(this.frame, LaunchRes.getString("Dialog.TerminateSeparateVM.Message") + XML.NEW_LINE + LaunchRes.getString("Dialog.TerminateSeparateVM.Question"), LaunchRes.getString("Dialog.TerminateSeparateVM.Title"), 0);
                  var1 = var9 == 0;
                  var11 = !var1;
               }

               if (!var1) {
                  return false;
               }

               Iterator var13 = var8.processes.iterator();

               while(var13.hasNext()) {
                  Process var10 = (Process)var13.next();
                  var13.remove();
                  var10.destroy();
               }
            }
         }
      }

      return true;
   }

   protected Icon getFileIcon(LaunchNode var1) {
      if (var1.getFileName().length() == 0) {
         return null;
      } else {
         File var2 = var1.getFile();
         Resource var3 = var1.getResource();
         boolean var4 = this.changedFiles.contains(var1.getFileName());
         if (var4) {
            return yellowFileIcon;
         } else if (var3 != null && !var1.isParentSelfContained()) {
            if (var2 != null && var2.canWrite()) {
               return whiteFileIcon;
            } else {
               return var2 == null ? magentaFileIcon : redFileIcon;
            }
         } else {
            return ghostFileIcon;
         }
      }
   }

   public static void launch(Class var0) {
      launch(var0, (String[])null, (LaunchNode)null);
   }

   public static void launch(Class var0, String[] var1) {
      launch(var0, var1, (LaunchNode)null);
   }

   public static void launch(final Class var0, final String[] var1, final LaunchNode var2) {
      if (var0 == null) {
         OSPLog.info(LaunchRes.getString("Log.Message.NoClass"));
         JOptionPane.showMessageDialog((Component)null, LaunchRes.getString("Dialog.NoLaunchClass.Message"), LaunchRes.getString("Dialog.NoLaunchClass.Title"), 2);
      } else {
         String var3 = LaunchRes.getString("Log.Message.Launching") + " " + var0 + ", args ";
         if (var1 == null) {
            var3 = var3 + var1;
         } else {
            var3 = var3 + "{";

            for(int var4 = 0; var4 < var1.length; ++var4) {
               var3 = var3 + var1[var4];
               if (var4 < var1.length - 1) {
                  var3 = var3 + ", ";
               }
            }

            var3 = var3 + "}";
         }

         OSPLog.fine(var3);
         int var6;
         String var12;
         if (!OSPParameters.launchingInSingleVM && newVMAllowed) {
            OSPLog.finer(LaunchRes.getString("Log.Message.LaunchSeparateVM"));
            final Vector var11 = new Vector();
            var11.add("java");
            if (classPath != null && !classPath.equals("")) {
               var12 = getDefaultJar();
               if (var12 != null && classPath.indexOf(var12) == -1) {
                  classPath = classPath + ";" + var12;
               }

               for(var6 = classPath.indexOf(":"); var6 != -1; var6 = classPath.indexOf(":")) {
                  classPath = classPath.substring(0, var6) + ";" + classPath.substring(var6 + 1);
               }

               char var17 = System.getProperty("path.separator").charAt(0);
               classPath = classPath.replace(';', var17);
               var11.add("-classpath");
               var11.add(classPath);
            }

            var11.add(var0.getName());
            if (var1 != null) {
               for(int var13 = 0; var13 < var1.length && var1[var13] != null; ++var13) {
                  var11.add(var1[var13]);
               }
            }

            Runnable var14 = new Runnable() {
               public void run() {
                  OSPLog.finer(LaunchRes.getString("Log.Message.Command") + " " + var11.toString());
                  String[] var1 = (String[])((String[])var11.toArray(new String[0]));

                  try {
                     Process var2x = Runtime.getRuntime().exec(var1);
                     if (var2 != null) {
                        var2.processes.add(var2x);
                     }

                     var2x.waitFor();
                     if (var2 != null) {
                        var2.threadRunning(false);
                        var2.processes.remove(var2x);
                     }
                  } catch (Exception var3) {
                     OSPLog.info(var3.toString());
                     if (var2 != null) {
                        var2.threadRunning(false);
                     }
                  }

               }
            };
            if (var2 != null) {
               var2.threadRunning(true);
            }

            (new Thread(var14)).start();
         } else {
            OSPParameters.launchingInSingleVM = true;
            OSPLog.finer(LaunchRes.getString("Log.Message.LaunchCurrentVM"));
            final Frame[] var10 = Frame.getFrames();
            if (singleAppMode) {
               OSPLog.finer(LaunchRes.getString("Log.Message.LaunchSingleApp"));
               boolean var5 = OSPLog.isLogVisible();
               var6 = 0;

               for(int var7 = var10.length; var6 < var7; ++var6) {
                  if (!existingFrames.contains(var10[var6]) && !(var10[var6] instanceof Launcher.LauncherFrame)) {
                     WindowListener[] var8 = var10[var6].getWindowListeners();

                     for(int var9 = 0; var9 < var8.length; ++var9) {
                        var8[var9].windowClosing((WindowEvent)null);
                     }

                     var10[var6].dispose();
                  }
               }

               if (var5) {
                  OSPLog.showLog();
               }
            }

            if (var2 != null) {
               var12 = var2.getClassPath();
               XML.setClassLoader(LaunchClassChooser.getClassLoader(var12));
            }

            final Runnable var15 = new Runnable() {
               public void run() {
                  Launcher.activeNode = var2;

                  try {
                     Method var1x = var0.getMethod("main", Launcher.array$Ljava$lang$String == null ? (Launcher.array$Ljava$lang$String = Launcher.class$("[Ljava.lang.String;")) : Launcher.array$Ljava$lang$String);
                     var1x.invoke(var0, var1);
                  } catch (NoSuchMethodException var2x) {
                  } catch (InvocationTargetException var3) {
                  } catch (IllegalAccessException var4) {
                  }

                  var2.threads.remove(this);
                  Launcher.activeNode = null;
                  if (Launcher.frameFinder != null) {
                     Launcher.findFramesFor(var2, var10, this);
                     if (Launcher.frameFinder != null) {
                        Launcher.frameFinder.stop();
                        Launcher.frameFinder = null;
                     }
                  }

               }
            };
            if (frameFinder != null) {
               frameFinder.stop();
            }

            frameFinder = new Timer(1000, new ActionListener() {
               public void actionPerformed(ActionEvent var1) {
                  Launcher.findFramesFor(var2, var10, var15);
               }
            });
            Thread var16 = new Thread(var15);
            var16.setDaemon(true);
            var2.threads.put(var15, var16);
            var16.start();
            frameFinder.start();
         }
      }
   }

   public static void main(String[] var0) {
      Launcher var1 = new Launcher();
      if (var0 != null && var0.length > 0) {
         var1.open(var0);
      } else {
         String var2 = null;
         if (ResourceLoader.launchJarName != null) {
            var2 = var1.open(XML.stripExtension(ResourceLoader.launchJarName) + ".xset");
         }

         if (var2 == null) {
            var2 = var1.open(defaultFileName + ".xset");
         }

         if (var2 == null) {
            var1.open(defaultFileName + ".xml");
         }
      }

      var1.refreshGUI();
      Dimension var5 = Toolkit.getDefaultToolkit().getScreenSize();
      int var3 = (var5.width - var1.frame.getBounds().width) / 2;
      int var4 = (var5.height - var1.frame.getBounds().height) / 2;
      var1.frame.setLocation(var3, var4);
      ((Launcher.LauncherFrame)var1.frame).setKeepHidden(false);
      var1.frame.setVisible(true);
   }

   protected LaunchClassChooser getClassChooser() {
      if (this.classChooser == null) {
         this.classChooser = new LaunchClassChooser(this.contentPane);
      }

      return this.classChooser;
   }

   protected static JFileChooser getXMLChooser() {
      if (chooser != null) {
         return chooser;
      } else {
         chooser = new JFileChooser(new File(OSPFrame.chooserDir));
         launcherFileFilter = new FileFilter() {
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

                  return var2 != null && (var2.equals("xset") || var2.equals("xml") || var2.equals("zip"));
               }
            }

            public String getDescription() {
               return LaunchRes.getString("FileChooser.LauncherFilter.Description");
            }
         };
         xmlFileFilter = new FileFilter() {
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

                  return var2 != null && var2.equals("xml");
               }
            }

            public String getDescription() {
               return LaunchRes.getString("FileChooser.XMLFilter.Description");
            }
         };
         xsetFileFilter = new FileFilter() {
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

                  return var2 != null && var2.equals("xset");
               }
            }

            public String getDescription() {
               return LaunchRes.getString("FileChooser.XSETFilter.Description");
            }
         };
         chooser.addChoosableFileFilter(xmlFileFilter);
         chooser.addChoosableFileFilter(xsetFileFilter);
         chooser.addChoosableFileFilter(launcherFileFilter);
         return chooser;
      }
   }

   protected static String getDisplayName(String var0) {
      var0 = XML.getName(var0);
      int var1 = var0.lastIndexOf(".");
      return var1 != -1 ? var0.substring(0, var1) : var0;
   }

   protected static String getDefaultJar() {
      URL var0 = ClassLoader.getSystemResource(defaultFileName + ".xset");
      if (var0 == null) {
         var0 = ClassLoader.getSystemResource(defaultFileName + ".xml");
      }

      if (var0 == null) {
         return null;
      } else {
         String var1 = var0.getPath();
         int var2 = var1.indexOf("/" + defaultFileName);
         if (var2 == -1) {
            return null;
         } else {
            var1 = var1.substring(0, var2);
            var2 = var1.lastIndexOf("!");
            return var2 == -1 ? null : var1.substring(var1.lastIndexOf("/") + 1, var2);
         }
      }
   }

   private void inspectXML(LaunchNode var1) {
      XMLControlElement var2 = new XMLControlElement(var1);
      XMLTreePanel var3 = new XMLTreePanel(var2, false);
      this.inspector.setContentPane(var3);
      this.inspector.setTitle(LaunchRes.getString("Inspector.Title.Node") + " \"" + var1.name + "\"");
      this.inspector.setVisible(true);
   }

   private static void findFramesFor(LaunchNode var0, Frame[] var1, Runnable var2) {
      Frame[] var3 = Frame.getFrames();
      ArrayList var4 = new ArrayList();

      int var5;
      for(var5 = 0; var5 < var3.length; ++var5) {
         if (var3[var5] instanceof JFrame) {
            JFrame var6 = (JFrame)var3[var5];
            if (var6.getDefaultCloseOperation() == 0 || var6 instanceof MessageFrame || var6 instanceof Launcher.LauncherFrame) {
               continue;
            }
         }

         if (var3[var5].getClass().getName().indexOf("SharedOwnerFrame") <= -1 && var3[var5].getClass().getName().indexOf("QTFrame") <= -1) {
            var4.add(var3[var5]);
         }
      }

      for(var5 = 0; var5 < var1.length; ++var5) {
         var4.remove(var1[var5]);
      }

      if (!var4.isEmpty()) {
         var3 = (Frame[])((Frame[])var4.toArray(new Frame[0]));
         var4.clear();
         Launcher.FrameCloser var8 = new Launcher.FrameCloser(var0, var4, var2);

         for(int var9 = 0; var9 < var3.length; ++var9) {
            if (var3[var9] instanceof JFrame) {
               JFrame var7 = (JFrame)var3[var9];
               if (var7 instanceof OSPFrame && ((OSPFrame)var7).wishesToExit() || var7.getDefaultCloseOperation() == 3) {
                  if (var7.getDefaultCloseOperation() == 3) {
                     var7.setDefaultCloseOperation(2);
                  }

                  var7.addWindowListener(var8);
               }

               var4.add(var7);
            }
         }

         if (var0 != null) {
            var0.frames.addAll(var4);
            ++var0.launchCount;
            if (frameFinder != null) {
               frameFinder.stop();
               frameFinder = null;
            }
         }

         if (var0.launchPanel != null) {
            var0.launchPanel.repaint();
         }

      }
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

   static class FrameCloser extends WindowAdapter {
      LaunchNode node;
      Collection frames;
      Runnable runner;

      FrameCloser(LaunchNode var1, Collection var2, Runnable var3) {
         this.frames = var2;
         this.node = var1;
         this.runner = var3;
      }

      public void windowClosing(WindowEvent var1) {
         Iterator var2 = this.frames.iterator();

         while(var2.hasNext()) {
            Frame var3 = (Frame)var2.next();
            var3.removeWindowListener(this);
            var3.dispose();
         }

         if (this.node != null) {
            Thread var4 = (Thread)this.node.threads.get(this.runner);
            if (var4 != null) {
               var4.interrupt();
               this.node.threads.put(this.runner, (Object)null);
            }

            this.node.frames.removeAll(this.frames);
            this.node.launchCount = Math.max(0, --this.node.launchCount);
            if (this.node.launchPanel != null) {
               this.node.launchPanel.repaint();
            }
         }

      }
   }

   protected class LinkEdit extends AbstractUndoableEdit {
      String[] args = new String[2];
      String[] prev = new String[2];

      public LinkEdit(String[] var2) {
         this.args[0] = var2[0];
         this.args[1] = var2.length < 2 ? "" : var2[1];
         String var3 = Launcher.this.tabSetName;
         if (!var3.startsWith(Launcher.defaultFileName) && Launcher.tabSetBasePath.equals("")) {
            var3 = ResourceLoader.launchJarName + "!/" + var3;
         }

         this.prev[0] = this.args[0].equals("") ? "" : var3;
         if (Launcher.this.getSelectedNode() != null) {
            this.prev[1] = Launcher.this.getSelectedNode().getPathString();
         } else {
            this.prev[1] = Launcher.this.getSelectedTab() == null ? "" : Launcher.this.getSelectedTab().getRootNode().name;
         }

      }

      public LinkEdit(Launcher.LinkEdit var2) {
         this.args = var2.args;
         this.prev = var2.prev;
      }

      public void undo() throws CannotUndoException {
         Launcher.this.undoEdit = this;
         Launcher.this.open(this.prev);
         Launcher.this.refreshGUI();
      }

      public void redo() throws CannotUndoException {
         Launcher.this.open(this.args);
         Launcher.this.refreshGUI();
      }

      public String getPresentationName() {
         return "Link";
      }
   }

   public class LaunchSet implements XML.ObjectLoader {
      private Launcher launcher;
      private String name;
      boolean failedToLoad = false;
      public boolean showHiddenNodes = true;

      public LaunchSet() {
         this.launcher = Launcher.this;
      }

      protected LaunchSet(Launcher var2, String var3) {
         this.launcher = var2;
         this.name = XML.getName(XML.forwardSlash(var3));
      }

      public void saveObject(XMLControl var1, Object var2) {
         Launcher.LaunchSet var3 = (Launcher.LaunchSet)var2;
         Launcher var4 = var3.launcher;
         var1.setValue("classpath", Launcher.classPath);
         var1.setValue("title", var4.title);
         Dimension var5 = var4.contentPane.getSize();
         var1.setValue("width", var5.width);
         var1.setValue("height", var5.height);
         var1.setValue("divider", var4.divider);
         ArrayList var6 = new ArrayList();

         for(int var7 = 0; var7 < var4.tabs.size(); ++var7) {
            LaunchNode var8 = ((LaunchPanel)var4.tabs.get(var7)).getRootNode();
            if (!var8.isHiddenInLauncher() || var3.showHiddenNodes) {
               var8.parentSelfContained = false;
               var8.previewing = false;
               var8.saveHiddenNodes = var3.showHiddenNodes;
               if (var4.selfContained) {
                  var8.setSelfContained(false);
                  var8.parentSelfContained = true;
                  var6.add(var8);
               } else if (var4.previewing) {
                  var8.previewing = true;
                  var6.add(var8);
               } else if (var8.getFileName() != null && !var8.getFileName().equals("")) {
                  var6.add(var8.getFileName());
               } else {
                  var6.add(var8);
               }
            }
         }

         var1.setValue("launch_nodes", var6);
         if (var4.editorEnabled) {
            var1.setValue("editor_enabled", true);
         }

         boolean var9 = var4.password != null && !var4.password.equals("");
         if (var9 && Launcher.this.pwRequiredToLoad) {
            var1.setValue("pw_required_by_launcher", true);
         }

         var1.setValue("xml_password", var4.password);
      }

      public Object createObject(XMLControl var1) {
         return null;
      }

      public Object loadObject(XMLControl var1, Object var2) {
         Launcher.LaunchSet var3 = (Launcher.LaunchSet)var2;
         Launcher var4 = var3.launcher;
         if (var1.getPropertyNames().contains("launchset")) {
            String var12 = var4.open(var1.getString("launchset"));
            var3.failedToLoad = var12 == null;
            return var2;
         } else {
            if (var1.getPropertyNames().contains("classpath")) {
               Launcher.classPath = var1.getString("classpath");
            }

            Collection var5 = (Collection)var1.getObject("launch_nodes");
            if (var5 != null && !var5.isEmpty()) {
               int var6 = var4.tabbedPane.getSelectedIndex();
               Iterator var7 = var5.iterator();
               boolean var8 = false;
               LaunchNode var9 = null;

               label94:
               while(true) {
                  while(true) {
                     Object var10;
                     do {
                        if (!var7.hasNext()) {
                           if (var9 != null) {
                              for(int var14 = 0; var14 < Launcher.this.tabbedPane.getTabCount(); ++var14) {
                                 if (Launcher.this.getTab(var14).getRootNode() == var9) {
                                    Launcher.this.tabbedPane.setSelectedIndex(var14);
                                 }
                              }
                           } else if (var8) {
                              var4.tabbedPane.setSelectedIndex(var6 + 1);
                           }
                           break label94;
                        }

                        var10 = var7.next();
                     } while(var3.name != null && var3.name.equals(var10));

                     if (var10 instanceof String) {
                        String var15 = XML.getResolvedPath((String)var10, Launcher.tabSetBasePath);
                        if (var4.open(var15) != null) {
                           var8 = true;
                        }
                     } else if (var10 instanceof LaunchNode) {
                        LaunchNode var11 = (LaunchNode)var10;
                        var8 = var4.addTab(var11) || var8;
                        if (var4.getClass() == (Launcher.class$org$opensourcephysics$tools$Launcher == null ? (Launcher.class$org$opensourcephysics$tools$Launcher = Launcher.class$("org.opensourcephysics.tools.Launcher")) : Launcher.class$org$opensourcephysics$tools$Launcher) && var11.isButtonView() && var9 == null) {
                           var9 = var11;
                        }
                     }
                  }
               }
            }

            var4.title = var1.getString("title");
            var4.editorEnabled = var1.getBoolean("editor_enabled");
            var4.password = var1.getString("xml_password");
            var4.pwRequiredToLoad = var1.getBoolean("pw_required_by_launcher");
            if (var1.getPropertyNames().contains("width") && var1.getPropertyNames().contains("height")) {
               Dimension var13 = Toolkit.getDefaultToolkit().getScreenSize();
               var13.width = Math.min(8 * var13.width / 10, var1.getInt("width"));
               var13.height = Math.min(8 * var13.height / 10, var1.getInt("height"));
               var4.contentPane.setPreferredSize(var13);
               var4.frame.pack();
            }

            if (var1.getPropertyNames().contains("divider")) {
               var4.divider = var1.getInt("divider");
               var4.refreshGUI();
            }

            return var2;
         }
      }
   }

   private class LaunchRenderer extends DefaultTreeCellRenderer {
      private LaunchRenderer() {
      }

      public Component getTreeCellRendererComponent(JTree var1, Object var2, boolean var3, boolean var4, boolean var5, int var6, boolean var7) {
         super.getTreeCellRendererComponent(var1, var2, var3, var4, var5, var6, var7);
         LaunchNode var8 = (LaunchNode)var2;
         this.setToolTipText(var8.tooltip.equals("") ? null : var8.tooltip);
         if (var8.getFileName() != null && Launcher.this instanceof LaunchBuilder) {
            this.setToolTipText(LaunchRes.getString("ToolTip.FileName") + " \"" + var8.getFileName() + "\"");
            this.setIcon(Launcher.this.getFileIcon(var8));
         } else if (var8.launchCount > 0) {
            if (var8.isSingleton()) {
               this.setIcon(Launcher.singletonIcon);
            } else if (var8.isSingleVM() && var8.isSingleApp()) {
               this.setIcon(Launcher.singletonIcon);
            } else {
               this.setIcon(Launcher.launchedIcon);
            }
         } else if (Launcher.this.isLaunchable(var8)) {
            this.setIcon(Launcher.launchIcon);
         } else if (Launcher.this.isLink(var8)) {
            this.setIcon(Launcher.linkIcon);
         } else if (var8.isLeaf()) {
            if (var8.getLaunchClass() == null && var8.launchClassName != null && !var8.launchClassName.equals("")) {
               this.setIcon(Launcher.launchEmptyIcon);
            } else if (var8.url != null) {
               this.setIcon(Launcher.htmlIcon);
            } else {
               this.setIcon(Launcher.noFileIcon);
            }
         }

         return this;
      }

      // $FF: synthetic method
      LaunchRenderer(Object var2) {
         this();
      }
   }

   private class LauncherFrame extends OSPFrame {
      LaunchNode node;

      public LauncherFrame() {
         this.setName("LauncherTool");
      }

      public LaunchNode getLaunchNode() {
         return this.node;
      }
   }
}
