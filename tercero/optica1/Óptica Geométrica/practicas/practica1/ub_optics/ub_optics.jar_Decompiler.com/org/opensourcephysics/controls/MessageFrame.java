package org.opensourcephysics.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class MessageFrame extends JFrame {
   static final Color DARK_GREEN = new Color(0, 128, 0);
   static final Color DARK_BLUE = new Color(0, 0, 128);
   static final Color DARK_RED = new Color(128, 0, 0);
   static Style black;
   static Style red;
   static Style blue;
   static Style green;
   static Style magenta;
   static Style gray;
   static MessageFrame APPLET_MESSAGEFRAME;
   static int levelOSP;
   private static int OFF;
   private static int SEVERE;
   private static int WARNING;
   private static int INFO;
   private static int ERR;
   private static int OUT;
   private static int CONFIG;
   private static int FINE;
   private static int FINER;
   private static int FINEST;
   private static int ALL;
   private JTextPane textPane;
   private static ArrayList buttonList;

   private MessageFrame() {
      this.setTitle("Message Log");
      JPanel var1 = new JPanel(new BorderLayout());
      var1.setPreferredSize(new Dimension(480, 240));
      this.setContentPane(var1);
      this.textPane = new JTextPane();
      var1.setPreferredSize(new Dimension(200, 300));
      var1.setMinimumSize(new Dimension(100, 100));
      this.textPane.setEditable(false);
      this.textPane.setAutoscrolls(true);
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
      JScrollPane var2 = new JScrollPane(this.textPane);
      var2.setWheelScrollingEnabled(true);
      var1.add(var2, "Center");
      this.pack();
   }

   public static void showLog(boolean var0) {
      if (APPLET_MESSAGEFRAME == null || !APPLET_MESSAGEFRAME.isDisplayable()) {
         createAppletMessageFrame();
      }

      APPLET_MESSAGEFRAME.setVisible(var0);
   }

   private static void createAppletMessageFrame() {
      APPLET_MESSAGEFRAME = new MessageFrame();
      APPLET_MESSAGEFRAME.setSize(300, 200);
      APPLET_MESSAGEFRAME.setDefaultCloseOperation(1);
      JMenuBar var0 = new JMenuBar();
      APPLET_MESSAGEFRAME.setJMenuBar(var0);
      JMenu var1 = new JMenu("Edit");
      var0.add(var1);
      JMenuItem var2 = new JMenuItem("Clear");
      var1.add(var2);
      var2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            MessageFrame.APPLET_MESSAGEFRAME.textPane.setText("");
         }
      });
      JMenu var3 = new JMenu("Level");
      var0.add(var3);
      ButtonGroup var4 = new ButtonGroup();

      for(int var5 = 0; var5 < OSPLog.levels.length; ++var5) {
         JRadioButtonMenuItem var6 = new JRadioButtonMenuItem(OSPLog.levels[var5].getName());
         buttonList.add(var6);
         var3.add(var6, 0);
         var4.add(var6);
         if (levelOSP == OSPLog.levels[var5].intValue()) {
            var6.setSelected(true);
         }

         var6.setActionCommand(OSPLog.levels[var5].getName());
         var6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               MessageFrame.setLevel(Level.parse(var1.getActionCommand()));
            }
         });
      }

   }

   public static boolean isLogVisible() {
      return APPLET_MESSAGEFRAME == null ? false : APPLET_MESSAGEFRAME.isVisible();
   }

   public static void clear() {
      if (APPLET_MESSAGEFRAME != null) {
         APPLET_MESSAGEFRAME.textPane.setText("");
      }

   }

   public static void setLevel(Level var0) {
      levelOSP = var0.intValue();
      int var1 = 0;

      for(int var2 = Math.min(OSPLog.levels.length, buttonList.size()); var1 < var2; ++var1) {
         if (levelOSP == OSPLog.levels[var1].intValue()) {
            ((JRadioButtonMenuItem)buttonList.get(var1)).setSelected(true);
         }
      }

   }

   public static void severe(String var0) {
      if (levelOSP <= SEVERE) {
         appletLog(var0, red);
      }

   }

   public static void warning(String var0) {
      if (levelOSP <= WARNING) {
         appletLog(var0, red);
      }

   }

   public static void info(String var0) {
      if (levelOSP <= INFO) {
         appletLog(var0, black);
      }

   }

   public static void config(String var0) {
      if (levelOSP <= CONFIG) {
         appletLog(var0, green);
      }

   }

   public static void fine(String var0) {
      if (levelOSP <= FINE) {
         appletLog(var0, blue);
      }

   }

   public static void finer(String var0) {
      if (levelOSP <= FINER) {
         appletLog(var0, blue);
      }

   }

   public static void finest(String var0) {
      if (levelOSP <= FINEST) {
         appletLog(var0, blue);
      }

   }

   private static void appletLog(final String var0, final Style var1) {
      if (APPLET_MESSAGEFRAME == null || !APPLET_MESSAGEFRAME.isDisplayable()) {
         createAppletMessageFrame();
      }

      Runnable var2 = new Runnable() {
         public synchronized void run() {
            try {
               Document var1x = MessageFrame.APPLET_MESSAGEFRAME.textPane.getDocument();
               var1x.insertString(var1x.getLength(), var0 + '\n', var1);
               Rectangle var2 = MessageFrame.APPLET_MESSAGEFRAME.textPane.getBounds();
               var2.y = var2.height;
               MessageFrame.APPLET_MESSAGEFRAME.textPane.scrollRectToVisible(var2);
            } catch (BadLocationException var3) {
               System.err.println(var3);
            }

         }
      };
      if (SwingUtilities.isEventDispatchThread()) {
         var2.run();
      } else {
         SwingUtilities.invokeLater(var2);
      }

   }

   static {
      levelOSP = Level.CONFIG.intValue();
      OFF = Level.OFF.intValue();
      SEVERE = Level.SEVERE.intValue();
      WARNING = Level.WARNING.intValue();
      INFO = Level.INFO.intValue();
      ERR = ConsoleLevel.ERR_CONSOLE.intValue();
      OUT = ConsoleLevel.OUT_CONSOLE.intValue();
      CONFIG = Level.CONFIG.intValue();
      FINE = Level.FINE.intValue();
      FINER = Level.FINER.intValue();
      FINEST = Level.FINEST.intValue();
      ALL = Level.ALL.intValue();
      buttonList = new ArrayList();
   }
}
