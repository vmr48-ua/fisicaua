package org.opensourcephysics.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import org.opensourcephysics.display.OSPFrame;

public class OSPLog extends JFrame {
   private static OSPLog OSPLOG;
   private static JFileChooser chooser;
   protected static Style black;
   protected static Style red;
   protected static Style blue;
   protected static Style green;
   protected static Style magenta;
   protected static Style gray;
   protected static final Color DARK_GREEN = new Color(0, 128, 0);
   protected static final Color DARK_BLUE = new Color(0, 0, 128);
   protected static final Color DARK_RED = new Color(128, 0, 0);
   public static final Level[] levels;
   private static Level defaultLevel;
   private Logger logger;
   private Handler fileHandler;
   private Handler logHandler;
   private JTextPane textPane;
   private String logFileName;
   private String tempFileName;
   private JPanel logPanel;
   private JPopupMenu popup;
   private ButtonGroup popupGroup;
   private ButtonGroup menubarGroup;
   private String pkgName;
   private String bundleName;
   private JMenuItem logToFileItem;
   private boolean hasPermission;
   private static LogRecord[] messageStorage;
   private static int messageIndex;
   static String eol;
   static String tmpdir;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$controls$ConsoleLevel;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$display$OSPFrame;

   public static OSPLog getOSPLog() {
      if (OSPLOG == null) {
         OSPLOG = new OSPLog("org.opensourcephysics", (String)null);
         if (!OSPFrame.appletMode && OSPFrame.applet == null) {
            try {
               System.setOut(new PrintStream(new LoggerOutputStream(ConsoleLevel.OUT_CONSOLE, System.out)));
               System.setErr(new PrintStream(new LoggerOutputStream(ConsoleLevel.ERR_CONSOLE, System.err)));
            } catch (SecurityException var1) {
            }
         }

         setLevel(defaultLevel);
      }

      return OSPLOG;
   }

   public static boolean isLogVisible() {
      if ((OSPFrame.appletMode || OSPFrame.applet != null) && MessageFrame.APPLET_MESSAGEFRAME != null) {
         return MessageFrame.APPLET_MESSAGEFRAME.isVisible();
      } else {
         return OSPLOG != null ? OSPLOG.isVisible() : false;
      }
   }

   public void setVisible(boolean var1) {
      if (!OSPFrame.appletMode && OSPFrame.applet == null) {
         super.setVisible(var1);
      } else {
         MessageFrame.showLog(var1);
      }

   }

   public boolean isVisible(boolean var1) {
      return !OSPFrame.appletMode && OSPFrame.applet == null ? super.isVisible() : MessageFrame.isLogVisible();
   }

   public static void showLog() {
      if (!OSPFrame.appletMode && OSPFrame.applet == null) {
         getOSPLog().setVisible(true);
         Logger var0 = OSPLOG.getLogger();
         int var1 = 0;

         for(int var2 = messageStorage.length; var1 < var2; ++var1) {
            LogRecord var3 = messageStorage[(var1 + messageIndex) % var2];
            if (var3 != null) {
               var0.log(var3);
            }
         }

         messageIndex = 0;
      } else {
         MessageFrame.showLog(true);
      }

   }

   public static void setLevel(Level var0) {
      if (!OSPFrame.appletMode && OSPFrame.applet == null) {
         getOSPLog().getLogger().setLevel(var0);

         for(int var1 = 0; var1 < 2; ++var1) {
            Enumeration var2 = getOSPLog().menubarGroup.getElements();
            if (var1 == 1) {
               var2 = getOSPLog().popupGroup.getElements();
            }

            while(var2.hasMoreElements()) {
               JMenuItem var3 = (JMenuItem)var2.nextElement();
               if (getOSPLog().getLogger().getLevel().toString().equals(var3.getActionCommand())) {
                  var3.setSelected(true);
                  break;
               }
            }
         }
      } else {
         MessageFrame.setLevel(var0);
      }

   }

   public static Level parseLevel(String var0) {
      for(int var1 = 0; var1 < levels.length; ++var1) {
         if (levels[var1].getName().equals(var0)) {
            return levels[var1];
         }
      }

      return null;
   }

   public static void severe(String var0) {
      if (!OSPFrame.appletMode && OSPFrame.applet == null) {
         log(Level.SEVERE, var0);
      } else {
         MessageFrame.severe(var0);
      }

   }

   public static void warning(String var0) {
      if (!OSPFrame.appletMode && OSPFrame.applet == null) {
         log(Level.WARNING, var0);
      } else {
         MessageFrame.warning(var0);
      }

   }

   public static void info(String var0) {
      if (!OSPFrame.appletMode && OSPFrame.applet == null) {
         log(Level.INFO, var0);
      } else {
         MessageFrame.info(var0);
      }

   }

   public static void config(String var0) {
      if (!OSPFrame.appletMode && OSPFrame.applet == null) {
         log(Level.CONFIG, var0);
      } else {
         MessageFrame.config(var0);
      }

   }

   public static void fine(String var0) {
      if (!OSPFrame.appletMode && OSPFrame.applet == null) {
         log(Level.FINE, var0);
      } else {
         MessageFrame.fine(var0);
      }

   }

   public static void clearLog() {
      messageIndex = 0;
      if (!OSPFrame.appletMode && OSPFrame.applet == null) {
         OSPLOG.clear();
      } else {
         MessageFrame.clear();
      }

   }

   public static void finer(String var0) {
      if (!OSPFrame.appletMode && OSPFrame.applet == null) {
         log(Level.FINER, var0);
      } else {
         MessageFrame.finer(var0);
      }

   }

   public static void finest(String var0) {
      if (!OSPFrame.appletMode && OSPFrame.applet == null) {
         log(Level.FINEST, var0);
      } else {
         MessageFrame.finest(var0);
      }

   }

   public OSPLog(Package var1) {
      this((String)var1.getName(), (String)null);
   }

   public OSPLog(Package var1, String var2) {
      this(var1.getName(), var2);
   }

   public OSPLog(Class var1) {
      this((Class)var1, (String)null);
   }

   public OSPLog(Class var1, String var2) {
      this(var1.getPackage().getName(), var2);
   }

   public JPanel getLogPanel() {
      return this.logPanel;
   }

   public void clear() {
      this.textPane.setText((String)null);
   }

   public String saveLog(String var1) {
      if (var1 != null && !var1.trim().equals("")) {
         try {
            BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
            var2.write(this.textPane.getText());
            var2.flush();
            var2.close();
            return var1;
         } catch (IOException var3) {
            return null;
         }
      } else {
         return this.saveLogAs();
      }
   }

   public String saveLogAs() {
      int var1 = getChooser().showSaveDialog((Component)null);
      if (var1 == 0) {
         File var2 = getChooser().getSelectedFile();
         if (var2.exists()) {
            int var3 = JOptionPane.showConfirmDialog(this, "Replace existing " + var2.getName() + "?", "Replace File", 1);
            if (var3 != 0) {
               return null;
            }
         }

         String var4 = XML.getRelativePath(var2.getAbsolutePath());
         return this.saveLog(var4);
      } else {
         return null;
      }
   }

   public String saveXML(String var1) {
      if (!OSPFrame.appletMode && OSPFrame.applet == null) {
         if (var1 != null && !var1.trim().equals("")) {
            String var2 = this.read(this.tempFileName);
            Handler var3 = this.getFileHandler();
            String var4 = var3.getFormatter().getTail(var3);
            var2 = var2 + var4;

            try {
               BufferedWriter var5 = new BufferedWriter(new FileWriter(var1));
               var5.write(var2);
               var5.flush();
               var5.close();
               return var1;
            } catch (IOException var6) {
               return null;
            }
         } else {
            return this.saveXMLAs();
         }
      } else {
         this.logger.log(Level.FINE, "Cannot save XML file when running as an applet.");
         return null;
      }
   }

   public String saveXMLAs() {
      int var1 = getChooser().showSaveDialog((Component)null);
      if (var1 == 0) {
         File var2 = getChooser().getSelectedFile();
         if (var2.exists()) {
            int var3 = JOptionPane.showConfirmDialog(this, "Replace existing " + var2.getName() + "?", "Replace File", 1);
            if (var3 != 0) {
               return null;
            }
         }

         this.logFileName = XML.getRelativePath(var2.getAbsolutePath());
         return this.saveXML(this.logFileName);
      } else {
         return null;
      }
   }

   public String open() {
      int var1 = getChooser().showOpenDialog((Component)null);
      if (var1 == 0) {
         File var2 = getChooser().getSelectedFile();
         String var3 = XML.getRelativePath(var2.getAbsolutePath());
         return this.open(var3);
      } else {
         return null;
      }
   }

   public String open(String var1) {
      this.textPane.setText(this.read(var1));
      return var1;
   }

   public Logger getLogger() {
      return this.logger;
   }

   public void setLogToFile(boolean var1) {
      if (!OSPFrame.appletMode && OSPFrame.applet == null) {
         if (var1) {
            this.logToFileItem.setSelected(true);
            this.logger.addHandler(this.getFileHandler());
         } else {
            this.logToFileItem.setSelected(false);
            this.logger.removeHandler(this.fileHandler);
         }

      } else {
         this.logger.log(Level.FINE, "Cannot log to file when running as an applet.");
      }
   }

   protected void createGUI() {
      this.logPanel = new JPanel(new BorderLayout());
      this.logPanel.setPreferredSize(new Dimension(480, 240));
      this.setContentPane(this.logPanel);
      this.textPane = new JTextPane();
      this.textPane.setEditable(false);
      this.textPane.setAutoscrolls(true);
      JScrollPane var1 = new JScrollPane(this.textPane);
      var1.setWheelScrollingEnabled(true);
      this.logPanel.add(var1, "Center");
      black = StyleContext.getDefaultStyleContext().getStyle("default");
      red = this.textPane.addStyle("red", black);
      StyleConstants.setForeground(red, DARK_RED);
      blue = this.textPane.addStyle("blue", black);
      StyleConstants.setForeground(blue, DARK_BLUE);
      green = this.textPane.addStyle("green", black);
      StyleConstants.setForeground(green, DARK_GREEN);
      magenta = this.textPane.addStyle("magenta", black);
      StyleConstants.setForeground(magenta, Color.MAGENTA);
      gray = this.textPane.addStyle("gray", black);
      StyleConstants.setForeground(gray, Color.GRAY);
      this.createLogger();
      this.createMenus();
      this.pack();
      this.textPane.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent var1) {
            if ((var1.isPopupTrigger() || var1.getButton() == 3 || var1.isControlDown() && System.getProperty("os.name").indexOf("Mac") > -1) && OSPLog.this.popup != null) {
               OSPLog.this.popup.show(OSPLog.this.textPane, var1.getX(), var1.getY() + 8);
            }

         }
      });
   }

   protected Logger createLogger() {
      if (this.bundleName != null) {
         try {
            this.logger = Logger.getLogger(this.pkgName, this.bundleName);
         } catch (Exception var3) {
            this.logger = Logger.getLogger(this.pkgName);
         }
      } else {
         this.logger = Logger.getLogger(this.pkgName);
      }

      try {
         this.logger.setLevel(defaultLevel);
         this.logHandler = new OSPLogHandler(this.textPane);
         this.logHandler.setFormatter(new ConsoleFormatter());
         this.logHandler.setLevel(Level.ALL);
         (class$org$opensourcephysics$display$OSPFrame == null ? (class$org$opensourcephysics$display$OSPFrame = class$("org.opensourcephysics.display.OSPFrame")) : class$org$opensourcephysics$display$OSPFrame).getClass();
         this.logger.setUseParentHandlers(false);
         this.logger.addHandler(this.logHandler);
      } catch (SecurityException var2) {
         this.hasPermission = false;
      }

      return this.logger;
   }

   protected synchronized Handler getFileHandler() {
      if (this.fileHandler != null) {
         return this.fileHandler;
      } else {
         try {
            int var1 = this.pkgName.lastIndexOf(".");
            if (var1 > -1) {
               this.pkgName = this.pkgName.substring(var1 + 1);
            }

            this.tempFileName = tmpdir + this.pkgName + ".log";
            this.fileHandler = new FileHandler(this.tempFileName);
            this.fileHandler.setFormatter(new XMLFormatter());
            this.fileHandler.setLevel(Level.ALL);
            this.logger.addHandler(this.fileHandler);
            this.logger.log(Level.FINE, "Logging to file enabled. File = " + this.tempFileName);
         } catch (IOException var2) {
         } catch (SecurityException var3) {
         }

         return this.fileHandler;
      }
   }

   protected void createMenus() {
      if (this.hasPermission) {
         this.popup = new JPopupMenu();
         JMenu var1 = new JMenu("Level");
         this.popup.add(var1);
         this.popupGroup = new ButtonGroup();

         for(int var2 = 0; var2 < levels.length; ++var2) {
            JRadioButtonMenuItem var3 = new JRadioButtonMenuItem(levels[var2].getName());
            var1.add(var3, 0);
            this.popupGroup.add(var3);
            if (this.logger.getLevel().toString().equals(levels[var2])) {
               var3.setSelected(true);
            }

            var3.setActionCommand(levels[var2].getName());
            var3.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent var1) {
                  OSPLog.this.logger.setLevel(Level.parse(var1.getActionCommand()));
                  Enumeration var2 = OSPLog.this.menubarGroup.getElements();

                  while(var2.hasMoreElements()) {
                     JMenuItem var3 = (JMenuItem)var2.nextElement();
                     if (OSPLog.this.logger.getLevel().toString().equals(var3.getActionCommand())) {
                        var3.setSelected(true);
                        break;
                     }
                  }

               }
            });
         }

         this.popup.addSeparator();
         AbstractAction var8 = new AbstractAction("Open...") {
            public void actionPerformed(ActionEvent var1) {
               OSPLog.this.open();
            }
         };
         var8.setEnabled(!OSPFrame.appletMode && OSPFrame.applet == null);
         this.popup.add(var8);
         AbstractAction var9 = new AbstractAction("Save As...") {
            public void actionPerformed(ActionEvent var1) {
               OSPLog.this.saveLogAs();
            }
         };
         var9.setEnabled(!OSPFrame.appletMode && OSPFrame.applet == null);
         this.popup.add(var9);
         this.popup.addSeparator();
         AbstractAction var4 = new AbstractAction("Clear") {
            public void actionPerformed(ActionEvent var1) {
               OSPLog.this.clear();
            }
         };
         this.popup.add(var4);
         JMenuBar var5 = new JMenuBar();
         this.setJMenuBar(var5);
         var1 = new JMenu("File");
         var5.add(var1);
         var1.add(var8);
         var1.add(var9);
         var1 = new JMenu("Edit");
         var5.add(var1);
         var1.add(var4);
         var1 = new JMenu("Level");
         var5.add(var1);
         this.menubarGroup = new ButtonGroup();

         for(int var6 = 0; var6 < levels.length; ++var6) {
            JRadioButtonMenuItem var7 = new JRadioButtonMenuItem(levels[var6].getName());
            var1.add(var7, 0);
            this.menubarGroup.add(var7);
            if (this.logger.getLevel().toString().equals(levels[var6])) {
               var7.setSelected(true);
            }

            var7.setActionCommand(levels[var6].getName());
            var7.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent var1) {
                  OSPLog.this.logger.setLevel(Level.parse(var1.getActionCommand()));
                  Enumeration var2 = OSPLog.this.popupGroup.getElements();

                  while(var2.hasMoreElements()) {
                     JMenuItem var3 = (JMenuItem)var2.nextElement();
                     if (OSPLog.this.logger.getLevel().toString().equals(var3.getActionCommand())) {
                        var3.setSelected(true);
                        break;
                     }
                  }

               }
            });
         }

         JMenu var10 = new JMenu("Options");
         var5.add(var10);
         this.logToFileItem = new JCheckBoxMenuItem("Log to file.");
         this.logToFileItem.setSelected(false);
         this.logToFileItem.setEnabled(!OSPFrame.appletMode && OSPFrame.applet == null);
         this.logToFileItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               JCheckBoxMenuItem var2 = (JCheckBoxMenuItem)var1.getSource();
               OSPLog.this.setLogToFile(var2.isSelected());
            }
         });
         var10.add(this.logToFileItem);
      }
   }

   protected static JFileChooser getChooser() {
      if (chooser == null) {
         chooser = new JFileChooser(new File(OSPFrame.chooserDir));
      }

      return chooser;
   }

   protected String read(String var1) {
      File var2 = new File(var1);
      StringBuffer var3 = null;

      try {
         BufferedReader var4 = new BufferedReader(new FileReader(var2));
         var3 = new StringBuffer();

         for(String var5 = var4.readLine(); var5 != null; var5 = var4.readLine()) {
            var3.append(var5 + XML.NEW_LINE);
         }

         var4.close();
      } catch (IOException var6) {
         this.logger.warning(var6.toString());
      }

      return var3.toString();
   }

   private OSPLog(String var1, String var2) {
      super("Message Log");
      this.hasPermission = true;
      this.setName("LogTool");
      this.bundleName = var2;
      this.pkgName = var1;
      (class$org$opensourcephysics$controls$ConsoleLevel == null ? (class$org$opensourcephysics$controls$ConsoleLevel = class$("org.opensourcephysics.controls.ConsoleLevel")) : class$org$opensourcephysics$controls$ConsoleLevel).getName();
      this.createGUI();
      this.setDefaultCloseOperation(1);
   }

   private static void log(Level var0, String var1) {
      LogRecord var2 = new LogRecord(var0, var1);
      StackTraceElement[] var3 = (new Throwable()).getStackTrace();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         StackTraceElement var5 = var3[var4];
         String var6 = var5.getClassName();
         if (!var6.equals("org.opensourcephysics.controls.OSPLog")) {
            var2.setSourceClassName(var6);
            var2.setSourceMethodName(var5.getMethodName());
            break;
         }
      }

      if (OSPLOG != null) {
         OSPLOG.getLogger().log(var2);
      } else {
         messageStorage[messageIndex] = var2;
         ++messageIndex;
         messageIndex %= messageStorage.length;
      }

   }

   static {
      levels = new Level[]{Level.OFF, Level.SEVERE, Level.WARNING, Level.INFO, ConsoleLevel.ERR_CONSOLE, ConsoleLevel.OUT_CONSOLE, Level.CONFIG, Level.FINE, Level.FINER, Level.FINEST, Level.ALL};
      defaultLevel = ConsoleLevel.OUT_CONSOLE;
      messageStorage = new LogRecord[128];
      messageIndex = 0;
      eol = "\n";
      tmpdir = ".";

      try {
         eol = System.getProperty("line.separator", eol);
         tmpdir = System.getProperty("java.io.tmpdir", tmpdir);
      } catch (SecurityException var1) {
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
