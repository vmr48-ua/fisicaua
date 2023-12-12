package org.opensourcephysics.display;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import org.opensourcephysics.controls.OSPLog;
import org.opensourcephysics.controls.OSPParameters;
import org.opensourcephysics.controls.XMLControlElement;
import org.opensourcephysics.tools.FontSizer;
import org.opensourcephysics.tools.LaunchNode;
import org.opensourcephysics.tools.Resource;
import org.opensourcephysics.tools.ResourceLoader;

public class OSPFrame extends JFrame implements Printable {
   public static String chooserDir;
   static int topx = 10;
   static int topy = 100;
   protected boolean animated;
   protected boolean autoclear;
   public static boolean appletMode = false;
   private volatile boolean wishesToExit;
   public static JApplet applet;
   public ThreadGroup constructorThreadGroup;
   protected boolean keepHidden;
   protected BufferStrategy strategy;
   private static JFileChooser chooser;
   protected JPanel buttonPanel;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$tools$LaunchNode;

   public OSPFrame(String var1) {
      super(GUIUtils.parseTeX(var1));
      this.animated = false;
      this.autoclear = false;
      this.wishesToExit = false;
      this.constructorThreadGroup = Thread.currentThread().getThreadGroup();
      this.keepHidden = false;
      this.buttonPanel = new JPanel();
      if (appletMode) {
         this.keepHidden = true;
      }

      this.buttonPanel.setVisible(false);
      this.setLocation(topx, topy);
      Dimension var2 = Toolkit.getDefaultToolkit().getScreenSize();
      topx = Math.min(topx + 20, (int)var2.getWidth() - 100);
      topy = Math.min(topy + 20, (int)var2.getHeight() - 100);
      this.setDefaultCloseOperation(1);
      this.setFontLevel(FontSizer.getLevel());
      FontSizer.addPropertyChangeListener("level", new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent var1) {
            int var2 = (Integer)var1.getNewValue();
            OSPFrame.this.setFontLevel(var2);
         }
      });
   }

   public OSPFrame() {
      this("Open Source Physics");
   }

   public OSPFrame(Container var1) {
      this();
      this.setContentPane(var1);
   }

   public void setTitle(String var1) {
      super.setTitle(GUIUtils.parseTeX(var1));
   }

   public boolean isIconified() {
      return (this.getExtendedState() & 1) == 1;
   }

   public void invalidateImage() {
   }

   protected void setFontLevel(int var1) {
      FontSizer.setFonts(this.getJMenuBar(), var1);
      FontSizer.setFonts(this.getContentPane(), var1);
   }

   public boolean isAnimated() {
      return this.animated;
   }

   public void setAnimated(boolean var1) {
      this.animated = var1;
   }

   public boolean isAutoclear() {
      return this.autoclear;
   }

   public void setAutoclear(boolean var1) {
      this.autoclear = var1;
   }

   public void clearData() {
   }

   public void clearDataAndRepaint() {
   }

   public void setSize(int var1, int var2) {
      super.setSize(var1, var2);
      this.validate();
   }

   /** @deprecated */
   public void show() {
      if (!this.keepHidden) {
         super.show();
      }

   }

   public void dispose() {
      this.keepHidden = true;
      super.dispose();
   }

   public void setVisible(boolean var1) {
      if (!this.keepHidden) {
         boolean var2 = !this.isVisible() && this.animated;
         super.setVisible(var1);
         if (var2) {
            this.render();
         }
      }

   }

   public void setKeepHidden(boolean var1) {
      this.keepHidden = var1;
      if (this.keepHidden) {
         super.setVisible(false);
      }

   }

   public boolean isKeepHidden() {
      return this.keepHidden;
   }

   public ThreadGroup getConstructorThreadGroup() {
      return this.constructorThreadGroup;
   }

   public void createBufferStrategy() {
      this.createBufferStrategy(2);
      this.strategy = this.getBufferStrategy();
   }

   public void bufferStrategyShow() {
      if (this.strategy == null) {
         this.createBufferStrategy();
      }

      if (!this.isIconified() && this.isShowing()) {
         Graphics var1 = this.strategy.getDrawGraphics();
         this.paintComponents(var1);
         var1.dispose();
         this.strategy.show();
      }
   }

   public void render() {
   }

   public JMenu getMenu(String var1) {
      JMenuBar var2 = this.getJMenuBar();
      if (var2 == null) {
         return null;
      } else {
         var1 = var1.trim();
         JMenu var3 = null;

         for(int var4 = 0; var4 < var2.getMenuCount(); ++var4) {
            JMenu var5 = var2.getMenu(var4);
            if (var5.getText().trim().equals(var1)) {
               var3 = var5;
               break;
            }
         }

         return var3;
      }
   }

   public JMenu removeMenu(String var1) {
      JMenuBar var2 = this.getJMenuBar();
      if (var2 == null) {
         return null;
      } else {
         var1 = var1.trim();
         JMenu var3 = null;

         for(int var4 = 0; var4 < var2.getMenuCount(); ++var4) {
            JMenu var5 = var2.getMenu(var4);
            if (var5.getText().trim().equals(var1)) {
               var3 = var5;
               var2.remove(var4);
               break;
            }
         }

         return var3;
      }
   }

   public JMenuItem removeMenuItem(String var1, String var2) {
      JMenu var3 = this.getMenu(var1);
      if (var3 == null) {
         return null;
      } else {
         var2 = var2.trim();
         JMenuItem var4 = null;

         for(int var5 = 0; var5 < var3.getItemCount(); ++var5) {
            JMenuItem var6 = var3.getItem(var5);
            if (var6.getText().trim().equals(var2)) {
               var4 = var6;
               var3.remove(var5);
               break;
            }
         }

         return var4;
      }
   }

   public void parseXMLMenu(String var1) {
      this.parseXMLMenu(var1, (Class)null);
   }

   public void parseXMLMenu(String var1, Class var2) {
      XMLControlElement var3 = null;
      if (var2 != null) {
         Resource var4 = ResourceLoader.getResource(var1, var2);
         if (var4 != null) {
            var3 = new XMLControlElement(var4.getString());
         }
      }

      if (var3 == null) {
         var3 = new XMLControlElement(var1);
      }

      if (var3.failedToRead()) {
         OSPLog.info("Menu not found: " + var1);
      } else {
         var2 = var3.getObjectClass();
         if (var2 != null && (class$org$opensourcephysics$tools$LaunchNode == null ? (class$org$opensourcephysics$tools$LaunchNode = class$("org.opensourcephysics.tools.LaunchNode")) : class$org$opensourcephysics$tools$LaunchNode).isAssignableFrom(var2)) {
            LaunchNode var8 = (LaunchNode)var3.loadObject((Object)null);
            JMenuBar var5 = this.getJMenuBar();
            if (var5 == null) {
               return;
            }

            String var6 = var8.toString();
            JMenu var7 = this.getMenu(var6);
            if (var7 == null) {
               var7 = new JMenu(var6);
               var5.add(var7);
               var5.validate();
            }

            var8.addMenuItemsTo(var7);
            OSPLog.finest("Menu loaded: " + var1);
         }
      }

   }

   public static JFileChooser getChooser() {
      if (chooser != null) {
         return chooser;
      } else {
         chooser = chooserDir == null ? new JFileChooser() : new JFileChooser(new File(chooserDir));
         FileFilter var0 = chooser.getFileFilter();
         FileFilter var1 = new FileFilter() {
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
               return "XML files";
            }
         };
         FileFilter var2 = new FileFilter() {
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

                  return var2 != null && var2.equals("txt");
               }
            }

            public String getDescription() {
               return "TXT files";
            }
         };
         chooser.addChoosableFileFilter(var1);
         chooser.addChoosableFileFilter(var2);
         chooser.setFileFilter(var0);
         return chooser;
      }
   }

   public int print(Graphics var1, PageFormat var2, int var3) throws PrinterException {
      if (var3 >= 1) {
         return 1;
      } else if (var1 == null) {
         return 1;
      } else {
         Graphics2D var4 = (Graphics2D)var1;
         double var5 = var2.getImageableWidth() / (double)this.getWidth();
         double var7 = var2.getImageableHeight() / (double)this.getHeight();
         double var9 = Math.min(var5, var7);
         var4.translate((int)var2.getImageableX(), (int)var2.getImageableY());
         var4.scale(var9, var9);
         this.paintAll(var4);
         return 0;
      }
   }

   public JButton addButton(String var1, String var2, String var3, final Object var4) {
      JButton var5 = new JButton(var2);
      var5.setToolTipText(var3);
      Class[] var6 = new Class[0];

      try {
         final Method var7 = var4.getClass().getMethod(var1, var6);
         var5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               Object[] var2 = new Object[0];

               try {
                  var7.invoke(var4, var2);
               } catch (IllegalAccessException var4x) {
                  System.err.println(var4x);
               } catch (InvocationTargetException var5) {
                  System.err.println(var5);
               }

            }
         });
         this.buttonPanel.setVisible(true);
         this.buttonPanel.add(var5);
         this.validate();
         this.pack();
      } catch (NoSuchMethodException var8) {
         System.err.println("Error adding custom button " + var2 + ". The method " + var1 + "() does not exist.");
      }

      return var5;
   }

   public void setDefaultCloseOperation(int var1) {
      if (var1 == 3 && OSPParameters.launchingInSingleVM) {
         var1 = 2;
         this.wishesToExit = true;
      }

      super.setDefaultCloseOperation(var1);
   }

   public boolean wishesToExit() {
      return this.wishesToExit;
   }

   static {
      JFrame.setDefaultLookAndFeelDecorated(true);
      JDialog.setDefaultLookAndFeelDecorated(true);

      try {
         chooserDir = System.getProperty("user.dir", (String)null);
      } catch (SecurityException var1) {
         chooserDir = null;
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
