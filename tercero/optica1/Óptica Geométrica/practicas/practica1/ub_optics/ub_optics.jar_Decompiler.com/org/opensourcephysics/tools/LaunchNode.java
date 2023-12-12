package org.opensourcephysics.tools;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import org.opensourcephysics.controls.ConsoleLevel;
import org.opensourcephysics.controls.OSPLog;
import org.opensourcephysics.controls.OSPParameters;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLControlElement;
import org.opensourcephysics.controls.XMLLoader;

public class LaunchNode extends DefaultMutableTreeNode {
   static final Level DEFAULT_LOG_LEVEL;
   Object launchObj;
   String classPath;
   String launchClassName;
   Class launchClass;
   String[] args = new String[]{""};
   boolean showLog = false;
   boolean clearLog = false;
   Level logLevel;
   boolean singleVM;
   boolean singleVMOff;
   boolean hiddenWhenRoot;
   boolean buttonView;
   boolean singleton;
   boolean singleApp;
   boolean singleAppOff;
   boolean webStart;
   boolean hiddenInLauncher;
   String name;
   String description;
   String tooltip;
   String xsetName;
   String author;
   String keywords;
   String comment;
   String urlName;
   URL url;
   private String fileName;
   Collection processes;
   Collection frames;
   Collection actions;
   Map threads;
   int launchCount;
   LaunchPanel launchPanel;
   boolean selfContained;
   boolean parentSelfContained;
   boolean previewing;
   boolean saveHiddenNodes;
   List jars;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$tools$LaunchNode;

   public LaunchNode(String var1) {
      this.logLevel = DEFAULT_LOG_LEVEL;
      this.singleVM = false;
      this.singleVMOff = false;
      this.hiddenWhenRoot = false;
      this.buttonView = false;
      this.singleton = false;
      this.singleApp = false;
      this.singleAppOff = false;
      this.webStart = false;
      this.hiddenInLauncher = false;
      this.name = "";
      this.description = "";
      this.tooltip = "";
      this.xsetName = "";
      this.author = "";
      this.keywords = "";
      this.comment = "";
      this.processes = new HashSet();
      this.frames = new HashSet();
      this.actions = new HashSet();
      this.threads = new HashMap();
      this.launchCount = 0;
      this.jars = new ArrayList();
      this.setUserObject(this);
      if (var1 != null) {
         this.name = var1;
      }

   }

   public void threadRunning(boolean var1) {
      this.launchCount += var1 ? 1 : -1;
      this.launchCount = Math.max(0, this.launchCount);
      if (this.launchPanel != null) {
         this.launchPanel.repaint();
      }

   }

   public void launch() {
      this.launch((LaunchPanel)null);
   }

   public void launch(LaunchPanel var1) {
      if (this.isLeaf()) {
         this.launchPanel = var1;
         OSPParameters.launchingInSingleVM = this.isSingleVM();
         Launcher.singleAppMode = this.isSingleApp();
         Launcher.classPath = this.getClassPath();
         if (this.isShowLog() && this.isSingleVM()) {
            OSPLog.setLevel(this.getLogLevel());
            OSPLog var2 = OSPLog.getOSPLog();
            if (this.isClearLog()) {
               var2.clear();
            }

            var2.setVisible(true);
         }

         this.setMinimumArgLength(1);
         String var5 = this.args[0];
         if (this.getLaunchClass() != null) {
            if (var5.equals("this")) {
               Object var3 = this.getLaunchObject();
               if (var3 != null) {
                  XMLControlElement var4 = new XMLControlElement(var3);
                  this.args[0] = var4.toXML();
               } else {
                  this.args[0] = "";
               }
            }

            if (this.args[0].equals("") && this.args.length == 1) {
               Launcher.launch(this.getLaunchClass(), (String[])null, this);
            } else {
               Launcher.launch(this.getLaunchClass(), this.args, this);
            }
         }

         this.args[0] = var5;
      }
   }

   public LaunchNode getOwner() {
      if (this.fileName != null) {
         return this;
      } else {
         return this.getParent() != null ? ((LaunchNode)this.getParent()).getOwner() : null;
      }
   }

   public LaunchNode[] getAllOwnedNodes() {
      ArrayList var1 = new ArrayList();
      Enumeration var2 = this.breadthFirstEnumeration();

      while(var2.hasMoreElements()) {
         LaunchNode var3 = (LaunchNode)var2.nextElement();
         if (var3.fileName != null && var3 != this) {
            var1.add(var3);
         }
      }

      return (LaunchNode[])((LaunchNode[])var1.toArray(new LaunchNode[0]));
   }

   public LaunchNode[] getChildOwnedNodes() {
      ArrayList var1 = new ArrayList();
      LaunchNode[] var2 = this.getAllOwnedNodes();
      LaunchNode var3 = this.getOwner();

      for(int var4 = 0; var4 < var2.length; ++var4) {
         LaunchNode var5 = ((LaunchNode)var2[var4].getParent()).getOwner();
         if (var5 == var3) {
            var1.add(var2[var4]);
         }
      }

      return (LaunchNode[])((LaunchNode[])var1.toArray(new LaunchNode[0]));
   }

   public String toString() {
      if (this.name != null && !this.name.equals("")) {
         return this.name;
      } else if (this.launchClassName != null) {
         return XML.getExtension(this.launchClassName);
      } else if (!this.args[0].equals("")) {
         String var1 = this.args[0];
         var1 = XML.getName(var1);
         return XML.stripExtension(var1);
      } else {
         return "";
      }
   }

   public String getID() {
      return String.valueOf(this.hashCode());
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setDescription(String var1) {
      this.description = var1;
   }

   public void setArgs(String[] var1) {
      if (var1 != null && var1.length > 0 && var1[0] != null) {
         this.args = var1;
      }

   }

   public String getClassPath() {
      String var1 = "";
      if (this.classPath != null) {
         var1 = var1 + this.classPath;
      }

      LaunchNode var2 = this;

      while(!var2.isRoot()) {
         var2 = (LaunchNode)var2.getParent();
         if (var2.classPath != null) {
            if (!var1.equals("")) {
               var1 = var1 + ";";
            }

            var1 = var1 + var2.classPath;
         }
      }

      if (!var1.equals("")) {
         this.jars.clear();
         String var3 = var1;
         int var4 = var1.indexOf(";");
         if (var4 == -1) {
            var4 = var1.indexOf(":");
         }

         if (var4 != -1) {
            var3 = var1.substring(0, var4);
            var1 = var1.substring(var4 + 1);
         } else {
            var1 = "";
         }

         while(!var3.equals("")) {
            var3 = var3.trim();
            if (!this.jars.contains(var3)) {
               this.jars.add(var3);
            }

            var4 = var1.indexOf(";");
            if (var4 == -1) {
               var4 = var1.indexOf(":");
            }

            if (var4 == -1) {
               var3 = var1;
               var1 = "";
            } else {
               var3 = var1.substring(0, var4);
               var1 = var1.substring(var4 + 1);
            }
         }

         for(Iterator var5 = this.jars.iterator(); var5.hasNext(); var1 = var1 + var5.next()) {
            if (!var1.equals("")) {
               var1 = var1 + ";";
            }
         }
      }

      if (ResourceLoader.launchJarName != null && var1.indexOf(ResourceLoader.launchJarName) == -1) {
         if (!var1.equals("")) {
            var1 = var1 + ";";
         }

         var1 = var1 + ResourceLoader.launchJarName;
      }

      return var1;
   }

   public void setClassPath(String var1) {
      if (var1 != null && !var1.equals("")) {
         while(var1.startsWith(":") || var1.startsWith(";")) {
            var1 = var1.substring(1);
         }

         while(var1.endsWith(":") || var1.endsWith(";")) {
            var1 = var1.substring(0, var1.length() - 1);
         }

         String var2 = var1;
         int var3 = var1.indexOf(";;");
         if (var3 == -1) {
            var3 = var1.indexOf("::");
         }

         if (var3 == -1) {
            var3 = var1.indexOf(":;");
         }

         if (var3 == -1) {
            var3 = var1.indexOf(";:");
         }

         while(var3 > -1) {
            var1 = var1.substring(0, var3 + 1) + var2.substring(var3 + 2);
            var2 = var1;
            var3 = var1.indexOf(";;");
            if (var3 == -1) {
               var3 = var1.indexOf("::");
            }

            if (var3 == -1) {
               var3 = var1.indexOf(":;");
            }

            if (var3 == -1) {
               var3 = var1.indexOf(";:");
            }
         }

         this.classPath = var1;
      } else {
         this.classPath = null;
      }
   }

   public boolean setLaunchClass(String var1) {
      if (var1 == null) {
         return false;
      } else if (this.launchClassName == var1 && this.launchClass != null) {
         return false;
      } else {
         OSPLog.finest(LaunchRes.getString("Log.Message.SetLaunchClass") + " " + var1);
         this.launchClassName = var1;
         this.launchClass = LaunchClassChooser.getClass(this.getClassPath(), var1);
         return this.launchClass != null;
      }
   }

   public Class getLaunchClass() {
      if (this.launchClass == null && this.launchClassName != null && !this.launchClassName.equals("")) {
         this.setLaunchClass(this.launchClassName);
      }

      return this.launchClass;
   }

   public Object getLaunchObject() {
      if (this.launchObj != null) {
         return this.launchObj;
      } else if (this.isRoot()) {
         return null;
      } else {
         LaunchNode var1 = (LaunchNode)this.getParent();
         return var1.getLaunchObject();
      }
   }

   public void setLaunchObject(Object var1) {
      this.launchObj = var1;
   }

   public URL setURL(String var1) {
      if (var1 != null && !var1.equals("")) {
         this.urlName = var1;
         OSPLog.finest(LaunchRes.getString("Log.Message.SetURLPath") + " " + var1);
         Resource var2 = ResourceLoader.getResource(var1);
         if (var2 != null && var2.getURL() != null) {
            try {
               return this.setURL(var2.getURL());
            } catch (Exception var4) {
               OSPLog.info(LaunchRes.getString("Log.Message.NotFound") + " " + var1);
            }
         }

         this.url = null;
         return null;
      } else {
         this.urlName = null;
         this.url = null;
         return null;
      }
   }

   public String getFileName() {
      return this.fileName;
   }

   public String getPathString() {
      TreeNode[] var1 = this.getPath();
      LaunchNode var2 = (LaunchNode)var1[0];
      StringBuffer var3 = new StringBuffer(var2.name);

      for(int var4 = 1; var4 < var1.length; ++var4) {
         var2 = (LaunchNode)var1[var4];
         var3.append("/" + var2.name);
      }

      return var3.toString();
   }

   public String setFileName(String var1) {
      if (var1 == null) {
         this.fileName = null;
      } else {
         this.fileName = XML.getPathRelativeTo(var1, Launcher.tabSetBasePath);
      }

      return this.fileName;
   }

   public boolean isParentSelfContained() {
      if (this.parentSelfContained) {
         return true;
      } else if (this.isRoot()) {
         return false;
      } else {
         LaunchNode var1 = (LaunchNode)this.getParent();
         return var1.isSelfContained();
      }
   }

   public boolean isSelfContained() {
      return this.selfContained || this.isParentSelfContained();
   }

   public boolean isPreviewing() {
      if (this.previewing) {
         return true;
      } else if (this.isRoot()) {
         return false;
      } else {
         LaunchNode var1 = (LaunchNode)this.getParent();
         return var1.isPreviewing();
      }
   }

   public boolean isSavingHiddenNodes() {
      if (this.saveHiddenNodes) {
         return true;
      } else if (this.isRoot()) {
         return false;
      } else {
         LaunchNode var1 = (LaunchNode)this.getParent();
         return var1.isSavingHiddenNodes();
      }
   }

   public void setSelfContained(boolean var1) {
      this.selfContained = var1;
   }

   public boolean isSingleVM() {
      if (!this.singleVM && !this.webStart) {
         if (this.isRoot()) {
            return false;
         } else {
            LaunchNode var1 = (LaunchNode)this.getParent();
            return this.singleVMOff ? false : var1.isSingleVM();
         }
      } else {
         return true;
      }
   }

   public void setSingleVM(boolean var1) {
      this.singleVM = var1;
   }

   public boolean isShowLog() {
      if (this.showLog) {
         return true;
      } else if (this.isRoot()) {
         return false;
      } else {
         LaunchNode var1 = (LaunchNode)this.getParent();
         return var1.isShowLog();
      }
   }

   public void setShowLog(boolean var1) {
      this.showLog = var1;
   }

   public boolean isClearLog() {
      if (this.clearLog) {
         return true;
      } else if (this.isRoot()) {
         return false;
      } else {
         LaunchNode var1 = (LaunchNode)this.getParent();
         return var1.isClearLog();
      }
   }

   public void setClearLog(boolean var1) {
      this.clearLog = var1;
   }

   public Level getLogLevel() {
      if (this.isRoot()) {
         return this.logLevel;
      } else {
         LaunchNode var1 = (LaunchNode)this.getParent();
         if (var1.isShowLog()) {
            Level var2 = var1.getLogLevel();
            if (var2.intValue() < this.logLevel.intValue()) {
               return var2;
            }
         }

         return this.logLevel;
      }
   }

   public void setLogLevel(Level var1) {
      if (var1 != null) {
         this.logLevel = var1;
      }

   }

   public boolean isSingleApp() {
      if (this.singleApp) {
         return true;
      } else if (this.isRoot()) {
         return false;
      } else {
         LaunchNode var1 = (LaunchNode)this.getParent();
         return this.singleAppOff ? false : var1.isSingleApp();
      }
   }

   public void setSingleApp(boolean var1) {
      this.singleApp = var1;
   }

   public void setHiddenWhenRoot(boolean var1) {
      this.hiddenWhenRoot = var1;
   }

   public boolean isButtonView() {
      if (this.buttonView) {
         return true;
      } else if (this.isRoot()) {
         return false;
      } else {
         LaunchNode var1 = (LaunchNode)this.getParent();
         return var1.isButtonView();
      }
   }

   public void setButtonView(boolean var1) {
      LaunchNode var2 = (LaunchNode)this.getRoot();
      var2.buttonView = var1;
   }

   public boolean isSingleton() {
      if (this.singleton) {
         return true;
      } else if (this.isRoot()) {
         return false;
      } else {
         LaunchNode var1 = (LaunchNode)this.getParent();
         return var1.isSingleton();
      }
   }

   public void setSingleton(boolean var1) {
      this.singleton = var1;
   }

   public boolean isHiddenInLauncher() {
      if (this.hiddenInLauncher) {
         return true;
      } else if (this.isRoot()) {
         return false;
      } else {
         LaunchNode var1 = (LaunchNode)this.getParent();
         return var1.isHiddenInLauncher();
      }
   }

   public void setHiddenInLauncher(boolean var1) {
      this.hiddenInLauncher = var1;
   }

   public String getAuthor() {
      if (!this.author.equals("")) {
         return this.author;
      } else if (this.isRoot()) {
         return "";
      } else {
         LaunchNode var1 = (LaunchNode)this.getParent();
         return var1.getAuthor();
      }
   }

   public Resource getResource() {
      if (this.fileName == null) {
         return null;
      } else {
         String var1 = XML.getResolvedPath(this.fileName, Launcher.tabSetBasePath);
         return ResourceLoader.getResource(var1);
      }
   }

   public boolean exists() {
      return this.getResource() != null;
   }

   public File getFile() {
      return this.exists() ? this.getResource().getFile() : null;
   }

   public boolean matches(LaunchNode var1) {
      if (var1 == null) {
         return false;
      } else {
         boolean var2 = this.showLog == var1.showLog && this.clearLog == var1.clearLog && this.singleton == var1.singleton && this.singleVM == var1.singleVM && this.hiddenWhenRoot == var1.hiddenWhenRoot && this.name.equals(var1.name) && this.description.equals(var1.description) && this.args[0].equals(var1.args[0]) && (this.fileName == null && var1.fileName == null || this.fileName != null && this.fileName.equals(var1.fileName)) && (this.getLaunchClass() == null && var1.getLaunchClass() == null || this.getLaunchClass() != null && this.getLaunchClass().equals(var1.getLaunchClass())) && (this.classPath == null && var1.classPath == null || this.classPath != null && this.classPath.equals(var1.classPath));
         return var2;
      }
   }

   public LaunchNode getChildNode(String var1) {
      Enumeration var2 = this.breadthFirstEnumeration();

      LaunchNode var3;
      do {
         if (!var2.hasMoreElements()) {
            return null;
         }

         var3 = (LaunchNode)var2.nextElement();
      } while(!var1.equals(var3.fileName));

      return var3;
   }

   public void addMenuItemsTo(JComponent var1) {
      Enumeration var2 = this.children();

      while(var2.hasMoreElements()) {
         LaunchNode var3 = (LaunchNode)var2.nextElement();
         if (var3.isLeaf()) {
            JMenuItem var4 = new JMenuItem(var3.toString());
            var1.add(var4);
            var4.setToolTipText(var3.tooltip);
            var4.setActionCommand(var3.getID());
            var4.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent var1) {
                  String var2 = var1.getActionCommand();
                  LaunchNode var3 = (LaunchNode)LaunchNode.this.getRoot();
                  Enumeration var4 = var3.postorderEnumeration();

                  while(var4.hasMoreElements()) {
                     LaunchNode var5 = (LaunchNode)var4.nextElement();
                     if (var5.getID().equals(var2)) {
                        var5.launch();
                        break;
                     }
                  }

               }
            });
         } else {
            JMenu var5 = new JMenu(var3.toString());
            var1.add(var5);
            var3.addMenuItemsTo(var5);
         }
      }

   }

   public void addTerminateAction(Action var1) {
      this.actions.add(var1);
      ++this.launchCount;
   }

   public void removeTerminateAction(Action var1) {
      this.actions.remove(var1);
      this.launchCount = Math.max(0, --this.launchCount);
   }

   public void terminate(Action var1) {
      if (this.actions.contains(var1)) {
         this.removeTerminateAction(var1);
         if (this.launchPanel != null) {
            this.launchPanel.repaint();
         }
      }

   }

   public void terminateAll() {
      Iterator var1 = this.processes.iterator();

      while(var1.hasNext()) {
         Process var2 = (Process)var1.next();
         var2.destroy();
      }

      var1 = this.frames.iterator();

      while(var1.hasNext()) {
         Frame var6 = (Frame)var1.next();
         WindowListener[] var3 = var6.getWindowListeners();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            if (var3[var4] instanceof Launcher.FrameCloser) {
               var6.removeWindowListener(var3[var4]);
            }
         }

         var6.dispose();
      }

      var1 = this.threads.values().iterator();

      while(var1.hasNext()) {
         Thread var7 = (Thread)var1.next();
         if (var7 != null) {
            var7.interrupt();
         }
      }

      HashSet var5 = new HashSet(this.actions);
      Iterator var8 = var5.iterator();

      while(var8.hasNext()) {
         Action var9 = (Action)var8.next();
         if (var9 != null) {
            var9.actionPerformed((ActionEvent)null);
         }
      }

      this.processes.clear();
      this.frames.clear();
      this.threads.clear();
      this.actions.clear();
      this.launchCount = 0;
   }

   public static XML.ObjectLoader getLoader() {
      return new LaunchNode.Loader();
   }

   private URL setURL(URL var1) throws Exception {
      InputStream var2 = var1.openStream();
      var2.close();
      OSPLog.finer(LaunchRes.getString("Log.Message.URL") + " " + var1);
      this.url = var1;
      return var1;
   }

   protected void setMinimumArgLength(int var1) {
      var1 = Math.max(var1, 1);
      if (var1 != this.args.length) {
         String[] var2;
         int var3;
         if (var1 > this.args.length) {
            var2 = new String[var1];

            for(var3 = 0; var3 < var1; ++var3) {
               if (var3 < this.args.length) {
                  var2[var3] = this.args[var3];
               } else {
                  var2[var3] = "";
               }
            }

            this.setArgs(var2);
         } else {
            while(this.args.length > var1 && this.args[this.args.length - 1].equals("")) {
               var2 = new String[this.args.length - 1];

               for(var3 = 0; var3 < var2.length; ++var3) {
                  var2[var3] = this.args[var3];
               }

               this.setArgs(var2);
            }
         }

      }
   }

   protected void removeThread(Runnable var1) {
      this.threads.remove(var1);
   }

   static {
      DEFAULT_LOG_LEVEL = ConsoleLevel.OUT_CONSOLE;
   }

   // $FF: synthetic method
   static Vector access$100(LaunchNode var0) {
      return var0.children;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   private static class Loader extends XMLLoader {
      private Loader() {
      }

      public void saveObject(XMLControl var1, Object var2) {
         LaunchNode var3 = (LaunchNode)var2;
         var3.setMinimumArgLength(1);
         if (!var3.name.equals("")) {
            var1.setValue("name", var3.name);
         }

         if (!var3.description.equals("")) {
            var1.setValue("description", var3.description);
         }

         if (!var3.tooltip.equals("")) {
            var1.setValue("tooltip", var3.tooltip);
         }

         if (!var3.xsetName.equals("")) {
            var1.setValue("launchset", var3.xsetName);
         }

         if (var3.urlName != null) {
            var1.setValue("url", var3.urlName);
         }

         if (var3.getLaunchClass() != null) {
            var1.setValue("launch_class", var3.getLaunchClass().getName());
         } else if (var3.launchClassName != null) {
            var1.setValue("launch_class", var3.launchClassName);
         }

         if (!var3.args[0].equals("") || var3.args.length > 1) {
            var1.setValue("launch_args", var3.args);
         }

         if (var3.classPath != null && !var3.classPath.equals("")) {
            var1.setValue("classpath", var3.classPath);
         }

         if (var3.hiddenWhenRoot) {
            var1.setValue("root_hidden", true);
         }

         if (var3.buttonView) {
            var1.setValue("button_view", true);
         }

         if (var3.singleton) {
            var1.setValue("singleton", true);
         }

         if (var3.singleVM) {
            var1.setValue("single_vm", true);
         }

         if (var3.singleVMOff) {
            var1.setValue("single_vm_off", true);
         }

         if (var3.showLog) {
            var1.setValue("show_log", true);
         }

         if (var3.logLevel != LaunchNode.DEFAULT_LOG_LEVEL) {
            var1.setValue("log_level", var3.logLevel.getName());
         }

         if (var3.clearLog) {
            var1.setValue("clear_log", true);
         }

         if (var3.singleApp) {
            var1.setValue("single_app", true);
         }

         if (var3.singleAppOff) {
            var1.setValue("single_app_off", true);
         }

         if (var3.hiddenInLauncher) {
            var1.setValue("hidden_in_launcher", true);
         }

         if (!var3.author.equals("")) {
            var1.setValue("author", var3.author);
         }

         if (!var3.keywords.equals("")) {
            var1.setValue("keywords", var3.keywords);
         }

         if (!var3.comment.equals("")) {
            var1.setValue("comment", var3.comment);
         }

         if (LaunchNode.access$100(var3) != null) {
            ArrayList var4 = new ArrayList();
            Enumeration var5 = var3.children();
            boolean var6 = var3.isSavingHiddenNodes();

            while(true) {
               while(true) {
                  LaunchNode var7;
                  do {
                     if (!var5.hasMoreElements()) {
                        if (var4.size() > 0) {
                           var1.setValue("child_nodes", var4);
                        }

                        return;
                     }

                     var7 = (LaunchNode)var5.nextElement();
                  } while(!var6 && var7.isHiddenInLauncher());

                  if (var3.isPreviewing()) {
                     var4.add(var7);
                  } else if (var7.fileName != null && !var3.isSelfContained()) {
                     var4.add(var7.fileName);
                  } else {
                     var7.fileName = null;
                     var7.setSelfContained(false);
                     var4.add(var7);
                  }
               }
            }
         }
      }

      public Object createObject(XMLControl var1) {
         String var2 = var1.getString("name");
         return new LaunchNode(var2);
      }

      public Object loadObject(XMLControl var1, Object var2) {
         LaunchNode var3 = (LaunchNode)var2;
         String var4 = var1.getString("name");
         if (var4 != null) {
            var3.name = var4;
         }

         String var5 = var1.getString("description");
         if (var5 != null) {
            var3.description = var5;
         }

         String var6 = var1.getString("tooltip");
         if (var6 != null) {
            var3.tooltip = var6;
         }

         String var7 = var1.getString("launchset");
         if (var7 != null) {
            var3.xsetName = var7;
         }

         var3.setURL(var1.getString("url"));
         var3.setClassPath(var1.getString("classpath"));
         String var8 = var1.getString("launch_class");
         if (var8 != null) {
            var3.launchClassName = var8;
         }

         String[] var9 = (String[])((String[])var1.getObject("launch_args"));
         if (var9 != null) {
            var3.setArgs(var9);
         }

         var3.hiddenWhenRoot = var1.getBoolean("root_hidden");
         var3.buttonView = var1.getBoolean("button_view");
         var3.singleton = var1.getBoolean("singleton");
         var3.singleVM = var1.getBoolean("single_vm");
         var3.singleVMOff = var1.getBoolean("single_vm_off");
         var3.showLog = var1.getBoolean("show_log");
         var3.clearLog = var1.getBoolean("clear_log");
         var3.singleApp = var1.getBoolean("single_app");
         var3.singleAppOff = var1.getBoolean("single_app_off");
         var3.hiddenInLauncher = var1.getBoolean("hidden_in_launcher");
         Level var10 = OSPLog.parseLevel(var1.getString("log_level"));
         if (var10 != null) {
            var3.logLevel = var10;
         }

         String var11 = var1.getString("author");
         if (var11 != null) {
            var3.author = var11;
         }

         String var12 = var1.getString("keywords");
         if (var12 != null) {
            var3.keywords = var12;
         }

         String var13 = var1.getString("comment");
         if (var13 != null) {
            var3.comment = var13;
         }

         var4 = var1.getString("filename");
         if (var4 != null) {
            var3.setFileName(var4);
         }

         ArrayList var14 = (ArrayList)var1.getObject("child_nodes");
         if (var14 != null) {
            var3.removeAllChildren();
            Iterator var15 = var14.iterator();

            while(var15.hasNext()) {
               Object var16 = var15.next();
               if (var16 instanceof LaunchNode) {
                  LaunchNode var17 = (LaunchNode)var16;
                  var3.add(var17);
                  var17.setLaunchClass(var17.launchClassName);
               } else if (var16 instanceof String) {
                  String var24 = (String)var16;
                  String var18 = XML.getResolvedPath(var24, Launcher.tabSetBasePath);
                  ResourceLoader.addSearchPath(Launcher.resourcesPath);
                  ResourceLoader.addSearchPath(Launcher.tabSetBasePath);
                  XMLControlElement var19 = new XMLControlElement();
                  String var20 = var19.read(var18);
                  if (var19.failedToRead()) {
                     JOptionPane.showMessageDialog((Component)null, LaunchRes.getString("Dialog.InvalidXML.Message") + " \"" + var24 + "\"", LaunchRes.getString("Dialog.InvalidXML.Title"), 2);
                  }

                  LaunchNode var21 = (LaunchNode)var3.getRoot();
                  if (var21.getChildNode(var24) == null && !var24.equals(var21.fileName)) {
                     Class var22 = var19.getObjectClass();
                     if ((LaunchNode.class$org$opensourcephysics$tools$LaunchNode == null ? (LaunchNode.class$org$opensourcephysics$tools$LaunchNode = LaunchNode.class$("org.opensourcephysics.tools.LaunchNode")) : LaunchNode.class$org$opensourcephysics$tools$LaunchNode).isAssignableFrom(var22)) {
                        LaunchNode var23 = new LaunchNode(LaunchRes.getString("NewNode.Name"));
                        var23.setFileName(var24);
                        OSPLog.finest(LaunchRes.getString("Log.Message.Loading") + ": " + var20);
                        var3.add(var23);
                        var19.loadObject(var23);
                     }
                  }
               }
            }
         }

         return var3;
      }

      // $FF: synthetic method
      Loader(Object var1) {
         this();
      }
   }
}
