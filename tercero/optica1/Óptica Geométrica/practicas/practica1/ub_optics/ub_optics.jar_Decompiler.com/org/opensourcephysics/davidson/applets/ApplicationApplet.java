package org.opensourcephysics.davidson.applets;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.opensourcephysics.controls.MessageFrame;
import org.opensourcephysics.controls.OSPLog;
import org.opensourcephysics.display.OSPFrame;
import org.opensourcephysics.display.TextFrame;
import org.opensourcephysics.tools.Resource;
import org.opensourcephysics.tools.ResourceLoader;

public class ApplicationApplet extends JApplet {
   JFrame mainFrame = null;
   JButton showFramesButton = new JButton("Show");
   String targetClassName;
   ArrayList newFrames = new ArrayList();
   ArrayList existingFrames = new ArrayList();
   Class target;
   String[] args = null;
   boolean singleApp = false;
   String targetError = null;
   // $FF: synthetic field
   static Class array$Ljava$lang$String;

   public String getParameter(String var1, String var2) {
      return this.getParameter(var1) != null ? this.getParameter(var1) : var2;
   }

   public void init() {
      super.init();
      OSPFrame.applet = this;
      OSPFrame.appletMode = false;
      if (this.getParameter("showLog", "false").toLowerCase().trim().equals("true")) {
         OSPLog.showLog();
      }

      String var1 = this.getParameter("xmldata", (String)null);
      var1 = this.getParameter("args[0]", var1);
      if (var1 != null) {
         this.args = new String[1];
      }

      String var2 = this.getParameter("args[1]", (String)null);
      String var3 = this.getParameter("args[2]", (String)null);
      if (var3 != null) {
         this.args = new String[3];
         this.args[0] = var1;
         this.args[1] = var2;
         this.args[2] = var3;
      } else if (var2 != null) {
         this.args = new String[2];
         this.args[0] = var1;
         this.args[1] = var2;
      } else if (var1 != null) {
         this.args = new String[1];
         this.args[0] = var1;
      }

      this.targetClassName = this.getParameter("target", (String)null);
      if (this.targetClassName == null) {
         this.targetClassName = this.getParameter("app", (String)null);
      }

      if (this.targetClassName == null) {
         this.readManifest();
      }

      String var4 = this.getParameter("title", (String)null);
      this.singleApp = this.getParameter("singleapp", "false").trim().equalsIgnoreCase("true");
      if (var4 == null) {
         String[] var5 = this.targetClassName.split("[.]");
         this.showFramesButton.setText(var5[var5.length - 1]);
      } else {
         this.showFramesButton.setText(var4);
      }

      this.getRootPane().getContentPane().add(this.showFramesButton, "Center");
      this.showFramesButton.addActionListener(new ApplicationApplet.DisplayBtnListener());
   }

   static boolean isLaunchable(Class var0) {
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

   public void destroy() {
      this.disposeOwnedFrames();
      this.target = null;
      this.mainFrame = null;
      super.destroy();
   }

   private void readManifest() {
      String var1 = this.getParameter("archive", (String)null);
      var1 = var1.split(";")[0];
      OSPLog.finer("archive applet tag=" + var1);
      if (var1 != null) {
         String var2 = "META-INF/MANIFEST.MF";
         Resource var3 = ResourceLoader.getResource(var1, var2);
         if (var3 == null) {
            OSPLog.fine("manifest not found in=" + var1);
         } else {
            String var4 = var3.getString();
            String[] var5 = var4.split("\n");
            int var6 = 0;

            for(int var7 = Math.min(10, var5.length); var6 < var7; ++var6) {
               int var8 = var5[var6].indexOf("Main-Class:");
               if (var8 >= 0) {
                  this.targetClassName = var5[var6].substring("Main-Class:".length() + var8).trim();
                  OSPLog.fine("target class in manifest=" + this.targetClassName);
                  return;
               }
            }

         }
      }
   }

   private Class createTarget() {
      Class var1 = null;
      this.targetError = null;
      ClassLoader var2 = this.getClass().getClassLoader();

      try {
         var1 = var2.loadClass(this.targetClassName);
      } catch (ClassNotFoundException var8) {
         System.err.println("Class not found: " + this.targetClassName);
         this.targetError = "Class not found: " + this.targetClassName;
         return null;
      }

      if (!isLaunchable(var1)) {
         System.err.println("Main method not found in " + this.targetClassName);
         this.targetError = "Main method not found in " + this.targetClassName;
         return null;
      } else {
         Frame[] var3 = Frame.getFrames();
         this.existingFrames.clear();
         int var4 = 0;

         for(int var5 = var3.length; var4 < var5; ++var4) {
            this.existingFrames.add(var3[var4]);
         }

         String var12 = this.getParameter("htmldata", (String)null);
         if (var12 != null) {
            TextFrame var13 = new TextFrame(var12, var1);
            var13.setVisible(true);
         }

         try {
            Method var14 = var1.getMethod("main", array$Ljava$lang$String == null ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String);
            var14.invoke(var1, this.args);
            var3 = Frame.getFrames();
            int var6 = 0;

            for(int var7 = var3.length; var6 < var7; ++var6) {
               if (var3[var6] instanceof JFrame && ((JFrame)var3[var6]).getDefaultCloseOperation() == 3) {
                  ((JFrame)var3[var6]).setDefaultCloseOperation(1);
                  if (this.mainFrame == null) {
                     this.mainFrame = (JFrame)var3[var6];
                  }
               }

               if (!this.existingFrames.contains(var3[var6])) {
                  this.newFrames.add(var3[var6]);
               }
            }
         } catch (NoSuchMethodException var9) {
            System.err.println(var9);
            this.targetError = var9.toString();
         } catch (InvocationTargetException var10) {
            System.err.println(var10);
            this.targetError = var10.toString();
         } catch (IllegalAccessException var11) {
            System.err.println(var11);
            this.targetError = var11.toString();
         }

         return var1;
      }
   }

   private void disposeOwnedFrames() {
      Frame[] var1 = Frame.getFrames();
      int var2 = 0;

      for(int var3 = var1.length; var2 < var3; ++var2) {
         if (!var1[var2].getClass().getName().startsWith("sun.plugin")) {
            if (var1[var2] instanceof JFrame && ((JFrame)var1[var2]).getDefaultCloseOperation() == 3) {
               ((JFrame)var1[var2]).setDefaultCloseOperation(2);
            }

            if (!this.existingFrames.contains(var1[var2])) {
               var1[var2].setVisible(false);
               var1[var2].dispose();
            }
         }
      }

      this.newFrames.clear();
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   private class DisplayBtnListener implements ActionListener {
      private DisplayBtnListener() {
      }

      public void actionPerformed(ActionEvent var1) {
         if (ApplicationApplet.this.singleApp && OSPFrame.applet != ApplicationApplet.this) {
            ApplicationApplet.this.disposeOwnedFrames();
            ApplicationApplet.this.target = null;
            ApplicationApplet.this.mainFrame = null;
            OSPFrame.applet = ApplicationApplet.this;
         }

         if (ApplicationApplet.this.target == null) {
            ApplicationApplet.this.target = ApplicationApplet.this.createTarget();
            if (ApplicationApplet.this.targetError != null) {
               ApplicationApplet.this.target = null;
            }
         } else {
            Iterator var2 = ApplicationApplet.this.newFrames.iterator();

            while(var2.hasNext()) {
               Frame var3 = (Frame)var2.next();
               if (var3.isDisplayable() && !(var3 instanceof OSPLog) && !(var3 instanceof MessageFrame)) {
                  var3.setVisible(true);
               }
            }
         }

         if (ApplicationApplet.this.mainFrame != null) {
            ApplicationApplet.this.mainFrame.setState(0);
            ApplicationApplet.this.mainFrame.setVisible(true);
            ApplicationApplet.this.mainFrame.toFront();
         }

      }

      // $FF: synthetic method
      DisplayBtnListener(Object var2) {
         this();
      }
   }
}
