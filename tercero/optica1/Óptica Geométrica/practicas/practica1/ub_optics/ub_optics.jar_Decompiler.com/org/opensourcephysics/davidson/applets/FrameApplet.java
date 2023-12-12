package org.opensourcephysics.davidson.applets;

import java.awt.Container;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.swing.JApplet;
import javax.swing.JFrame;
import org.opensourcephysics.controls.OSPLog;
import org.opensourcephysics.display.GUIUtils;
import org.opensourcephysics.display.OSPFrame;
import org.opensourcephysics.display.Renderable;
import org.opensourcephysics.display.TextFrame;

public class FrameApplet extends JApplet implements Renderable {
   JFrame mainFrame = null;
   String targetClassName;
   String contentName;
   ArrayList newFrames = new ArrayList();
   ArrayList existingFrames = new ArrayList();
   Class target;
   String[] args = null;
   Renderable renderPanel;
   boolean singleFrame = true;
   // $FF: synthetic field
   static Class array$Ljava$lang$String;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$display$Renderable;

   public String getParameter(String var1, String var2) {
      return this.getParameter(var1) != null ? this.getParameter(var1) : var2;
   }

   public void init() {
      super.init();
      OSPFrame.applet = this;
      OSPFrame.appletMode = true;
      if (this.getParameter("showLog", "false").toLowerCase().trim().equals("true")) {
         OSPLog.showLog();
      }

      String var1 = this.getParameter("xmldata", (String)null);
      if (var1 != null) {
         this.args = new String[1];
         this.args[0] = var1;
      }

      this.targetClassName = this.getParameter("target", (String)null);
      if (this.targetClassName == null) {
         this.targetClassName = this.getParameter("app", (String)null);
      }

      this.contentName = this.getParameter("content", (String)null);
      this.singleFrame = !this.getParameter("singleframe", "true").trim().equalsIgnoreCase("false");
   }

   public void start() {
      if (this.mainFrame == null) {
         this.createTarget();
         int var2;
         int var3;
         if (this.contentName != null) {
            Frame[] var1 = Frame.getFrames();
            var2 = 0;

            for(var3 = var1.length; var2 < var3; ++var2) {
               if (var1[var2] instanceof JFrame && var1[var2].getName().equalsIgnoreCase(this.contentName)) {
                  this.mainFrame = (JFrame)var1[var2];
                  break;
               }
            }
         }

         if (this.mainFrame == null) {
            System.out.println("Main frame not found.");
         } else {
            this.removeWindowListeners(this.mainFrame);
            this.mainFrame.setVisible(false);
            if (!this.mainFrame.isActive()) {
               this.mainFrame.dispose();
            }

            Container var4 = this.mainFrame.getContentPane();
            if (this.mainFrame instanceof OSPFrame && ((OSPFrame)this.mainFrame).isAnimated()) {
               this.renderPanel = (Renderable)GUIUtils.findInstance(var4, class$org$opensourcephysics$display$Renderable == null ? (class$org$opensourcephysics$display$Renderable = class$("org.opensourcephysics.display.Renderable")) : class$org$opensourcephysics$display$Renderable);
            }

            this.getRootPane().setContentPane(var4);
            this.getRootPane().requestFocus();
            if (!this.singleFrame) {
               OSPFrame.appletMode = false;
               var2 = 0;

               for(var3 = this.newFrames.size(); var2 < var3; ++var2) {
                  if (this.newFrames.get(var2) instanceof OSPFrame) {
                     ((OSPFrame)this.newFrames.get(var2)).setKeepHidden(false);
                  }
               }

               GUIUtils.showDrawingAndTableFrames();
            }

         }
      }
   }

   private void removeWindowListeners(Window var1) {
      WindowListener[] var2 = var1.getWindowListeners();
      int var3 = 0;

      for(int var4 = var2.length; var3 < var4; ++var3) {
         this.mainFrame.removeWindowListener(var2[var3]);
      }

   }

   public BufferedImage render() {
      return this.renderPanel != null ? this.renderPanel.render() : null;
   }

   public Image render(Image var1) {
      if (this.renderPanel != null) {
         this.renderPanel.render(var1);
      }

      return var1;
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

   private Class createTarget() {
      Class var1 = null;
      ClassLoader var2 = this.getClass().getClassLoader();

      try {
         var1 = var2.loadClass(this.targetClassName);
      } catch (ClassNotFoundException var8) {
         System.err.println("Class not found: " + this.targetClassName);
         return null;
      }

      if (!isLaunchable(var1)) {
         System.err.println("Main method not found in " + this.targetClassName);
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
         } catch (InvocationTargetException var10) {
            System.err.println(var10);
         } catch (IllegalAccessException var11) {
            System.err.println(var11);
         }

         if (this.newFrames.size() > 0 && this.mainFrame == null && this.newFrames.get(0) instanceof JFrame) {
            this.mainFrame = (JFrame)this.newFrames.get(0);
         }

         return var1;
      }
   }

   private void disposeOwnedFrames() {
      Frame[] var1 = Frame.getFrames();
      int var2 = 0;

      for(int var3 = var1.length; var2 < var3; ++var2) {
         if (var1[var2] instanceof JFrame && ((JFrame)var1[var2]).getDefaultCloseOperation() == 3) {
            ((JFrame)var1[var2]).setDefaultCloseOperation(2);
         }

         if (!this.existingFrames.contains(var1[var2])) {
            var1[var2].setVisible(false);
            this.removeWindowListeners(var1[var2]);
            var1[var2].dispose();
         }
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
