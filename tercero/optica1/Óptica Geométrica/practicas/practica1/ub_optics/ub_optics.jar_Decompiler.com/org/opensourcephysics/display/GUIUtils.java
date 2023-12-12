package org.opensourcephysics.display;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import org.jibble.epsgraphics.EpsGraphics2D;
import org.opensourcephysics.display3d.core.DrawingFrame3D;

public class GUIUtils {
   public static Map charMap = new HashMap();
   // $FF: synthetic field
   static Class class$org$opensourcephysics$display$DataTableFrame;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$display$DrawingFrame;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$display$OSPFrame;

   private GUIUtils() {
   }

   public static String parseTeX(String var0) {
      if (var0 == null) {
         return null;
      } else {
         String[] var1 = var0.split("\\$");
         boolean var2 = false;

         for(int var3 = 0; var3 < var1.length; ++var3) {
            if (var2) {
               String var4 = (String)charMap.get(var1[var3].trim());
               if (var4 != null) {
                  var1[var3] = var4;
               }
            }

            var2 = !var2;
         }

         String var5 = "";

         for(int var6 = 0; var6 < var1.length; ++var6) {
            var5 = var5 + var1[var6];
         }

         return var5;
      }
   }

   public static Component findInstance(Container var0, Class var1) {
      if (var0 != null && !var1.isInstance(var0)) {
         Component[] var2 = var0.getComponents();
         int var3 = 0;

         for(int var4 = var2.length; var3 < var4; ++var3) {
            if (var1.isInstance(var2[var3])) {
               return var2[var3];
            }

            if (var2[var3] instanceof Container) {
               Component var5 = findInstance((Container)var2[var3], var1);
               if (var1.isInstance(var5)) {
                  return var5;
               }
            }
         }

         return null;
      } else {
         return var0;
      }
   }

   public static void showDrawingAndTableFrames() {
      Frame[] var0 = Frame.getFrames();

      for(int var1 = 0; var1 < var0.length; ++var1) {
         if (var0[var1].isDisplayable() && (var0[var1].getName() == null || var0[var1].getName().indexOf("Tool") <= -1) && (class$org$opensourcephysics$display$OSPFrame == null ? (class$org$opensourcephysics$display$OSPFrame = class$("org.opensourcephysics.display.OSPFrame")) : class$org$opensourcephysics$display$OSPFrame).isInstance(var0[var1])) {
            if ((class$org$opensourcephysics$display$DataTableFrame == null ? (class$org$opensourcephysics$display$DataTableFrame = class$("org.opensourcephysics.display.DataTableFrame")) : class$org$opensourcephysics$display$DataTableFrame).isInstance(var0[var1])) {
               ((DataTableFrame)var0[var1]).refreshTable();
            }

            var0[var1].setVisible(true);
            ((OSPFrame)var0[var1]).invalidateImage();
            var0[var1].repaint();
            var0[var1].toFront();
         }
      }

      if (OSPFrame.applet != null) {
         OSPFrame.applet.getRootPane().repaint();
      }

   }

   public static void renderAnimatedFrames() {
      Frame[] var0 = Frame.getFrames();

      for(int var1 = 0; var1 < var0.length; ++var1) {
         if (var0[var1].isDisplayable() && (class$org$opensourcephysics$display$OSPFrame == null ? (class$org$opensourcephysics$display$OSPFrame = class$("org.opensourcephysics.display.OSPFrame")) : class$org$opensourcephysics$display$OSPFrame).isInstance(var0[var1]) && ((OSPFrame)var0[var1]).isAnimated()) {
            ((OSPFrame)var0[var1]).render();
         }
      }

      if (OSPFrame.applet != null && OSPFrame.applet instanceof Renderable) {
         ((Renderable)OSPFrame.applet).render();
      }

   }

   public static void repaintAnimatedFrames() {
      Frame[] var0 = Frame.getFrames();

      for(int var1 = 0; var1 < var0.length; ++var1) {
         if (var0[var1].isDisplayable() && (class$org$opensourcephysics$display$OSPFrame == null ? (class$org$opensourcephysics$display$OSPFrame = class$("org.opensourcephysics.display.OSPFrame")) : class$org$opensourcephysics$display$OSPFrame).isInstance(var0[var1]) && ((OSPFrame)var0[var1]).isAnimated()) {
            ((OSPFrame)var0[var1]).invalidateImage();
            ((OSPFrame)var0[var1]).repaint();
         }
      }

   }

   public static void clearDrawingFrameData(boolean var0) {
      Frame[] var1 = Frame.getFrames();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var1[var2].isDisplayable() && (class$org$opensourcephysics$display$OSPFrame == null ? (class$org$opensourcephysics$display$OSPFrame = class$("org.opensourcephysics.display.OSPFrame")) : class$org$opensourcephysics$display$OSPFrame).isInstance(var1[var2])) {
            OSPFrame var3 = (OSPFrame)var1[var2];
            if (var0 || var3.isAutoclear()) {
               var3.clearDataAndRepaint();
            }
         }
      }

   }

   public static void setAnimatedFrameIgnoreRepaint(boolean var0) {
      Frame[] var1 = Frame.getFrames();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var1[var2].isDisplayable() && (class$org$opensourcephysics$display$DrawingFrame == null ? (class$org$opensourcephysics$display$DrawingFrame = class$("org.opensourcephysics.display.DrawingFrame")) : class$org$opensourcephysics$display$DrawingFrame).isInstance(var1[var2]) && ((DrawingFrame)var1[var2]).isAnimated()) {
            DrawingPanel var3 = ((DrawingFrame)var1[var2]).getDrawingPanel();
            if (var3 != null) {
               var3.setIgnoreRepaint(var0);
            }
         }
      }

   }

   public static void enableMenubars(boolean var0) {
      Frame[] var1 = Frame.getFrames();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var1[var2].isDisplayable() && (var1[var2].getName() == null || var1[var2].getName().indexOf("Tool") <= -1) && ((class$org$opensourcephysics$display$DrawingFrame == null ? (class$org$opensourcephysics$display$DrawingFrame = class$("org.opensourcephysics.display.DrawingFrame")) : class$org$opensourcephysics$display$DrawingFrame).isInstance(var1[var2]) || var1[var2] instanceof DrawingFrame3D)) {
            JMenuBar var3 = ((JFrame)var1[var2]).getJMenuBar();
            if (var3 != null) {
               int var4 = 0;

               for(int var5 = var3.getMenuCount(); var4 < var5; ++var4) {
                  var3.getMenu(var4).setEnabled(var0);
               }
            }
         }
      }

   }

   public static void closeAndDisposeOSPFrames(Frame var0) {
      Frame[] var1 = Frame.getFrames();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var1[var2] != var0 && (class$org$opensourcephysics$display$OSPFrame == null ? (class$org$opensourcephysics$display$OSPFrame = class$("org.opensourcephysics.display.OSPFrame")) : class$org$opensourcephysics$display$OSPFrame).isInstance(var1[var2])) {
            ((OSPFrame)var1[var2]).setDefaultCloseOperation(2);
            ((OSPFrame)var1[var2]).setVisible(false);
            ((OSPFrame)var1[var2]).dispose();
         }
      }

   }

   public static Color randomColor() {
      return new Color((int)(Math.random() * 255.0D), (int)(Math.random() * 255.0D), (int)(Math.random() * 255.0D));
   }

   public static File showSaveDialog(Component var0) {
      return showSaveDialog(var0, "Save");
   }

   public static File showSaveDialog(Component var0, String var1) {
      JFileChooser var2 = OSPFrame.getChooser();
      if (var2 == null) {
         return null;
      } else {
         String var3 = var2.getDialogTitle();
         var2.setDialogTitle(var1);
         int var4 = var2.showSaveDialog(var0);
         var2.setDialogTitle(var3);
         if (var4 != 0) {
            return null;
         } else {
            OSPFrame.chooserDir = var2.getCurrentDirectory().toString();
            File var5 = var2.getSelectedFile();
            if (var5.exists()) {
               int var6 = JOptionPane.showConfirmDialog(var0, "A file named " + var5.getName() + " already exists.\nAre you sure you want to replace it?", "Warning", 1);
               if (var6 != 0) {
                  return null;
               }
            }

            return var5;
         }
      }
   }

   public static File showOpenDialog(Component var0) {
      JFileChooser var1 = OSPFrame.getChooser();
      int var2 = var1.showOpenDialog(var0);
      if (var2 != 0) {
         return null;
      } else {
         OSPFrame.chooserDir = var1.getCurrentDirectory().toString();
         File var3 = var1.getSelectedFile();
         return var3;
      }
   }

   public static void timingTest(Drawable var0) {
      DrawingPanel var1 = new DrawingPanel();
      DrawingFrame var2 = new DrawingFrame(var1);
      var2.setVisible(true);
      var1.addDrawable(var0);
      var1.scale();
      var1.setPixelScale();
      Graphics var3 = var1.getGraphics();
      if (var3 != null) {
         long var4 = System.currentTimeMillis();
         var0.draw(var1, var3);
         System.out.print("first drawing=" + (System.currentTimeMillis() - var4));
         var4 = System.currentTimeMillis();

         for(int var6 = 0; var6 < 5; ++var6) {
            var0.draw(var1, var3);
         }

         System.out.println("  avg time/drawing=" + (System.currentTimeMillis() - var4) / 5L);
         var3.dispose();
      }
   }

   public static void saveImage(JComponent var0, File var1, String var2) throws IOException {
      FileOutputStream var3 = null;

      try {
         var3 = new FileOutputStream(var1);
         if (var2.equals("eps")) {
            EpsGraphics2D var4 = new EpsGraphics2D("", var3, 0, 0, var0.getWidth(), var0.getHeight());
            var0.paint(var4);
            var4.scale(0.24D, 0.24D);
            var4.close();
         } else {
            BufferedImage var9 = new BufferedImage(var0.getWidth(), var0.getHeight(), 5);
            Graphics var5 = var9.getGraphics();
            var0.paint(var5);
            var5.dispose();
            ImageIO.write(var9, var2, var3);
            var3.close();
         }
      } finally {
         if (var3 != null) {
            var3.close();
         }

      }

   }

   public static void saveImage(JComponent var0, String var1, Component var2) {
      File var3 = showSaveDialog(var0, "Save Image");
      if (var3 != null) {
         try {
            saveImage(var0, var3, var1);
         } catch (IOException var5) {
            JOptionPane.showMessageDialog(var2, "An error occurred while saving the file " + var3.getName() + ".'");
         }

      }
   }

   static {
      charMap.put("\\Alpha", "Α");
      charMap.put("\\Beta", "Β");
      charMap.put("\\Gamma", "Γ");
      charMap.put("\\Delta", "Δ");
      charMap.put("\\Epsilon", "Ε");
      charMap.put("\\Zeta", "Ζ");
      charMap.put("\\Eta", "Η");
      charMap.put("\\Theta", "Θ");
      charMap.put("\\Pi", "Π");
      charMap.put("\\Rho", "Ρ");
      charMap.put("\\Sigma", "Σ");
      charMap.put("\\Tau", "Τ");
      charMap.put("\\Phi", "Φ");
      charMap.put("\\Chi", "Χ");
      charMap.put("\\Psi", "Ψ");
      charMap.put("\\Omega", "Ω");
      charMap.put("\\Xi", "Ξ");
      charMap.put("\\alpha", "α");
      charMap.put("\\beta", "β");
      charMap.put("\\gamma", "γ");
      charMap.put("\\delta", "δ");
      charMap.put("\\epsilon", "ε");
      charMap.put("\\zeta", "ζ");
      charMap.put("\\eta", "η");
      charMap.put("\\theta", "θ");
      charMap.put("\\iota", "ι");
      charMap.put("\\kappa", "κ");
      charMap.put("\\lamda", "λ");
      charMap.put("\\mu", "μ");
      charMap.put("\\micro", "μ");
      charMap.put("\\nu", "ν");
      charMap.put("\\xi", "ξ");
      charMap.put("\\pi", "π");
      charMap.put("\\rho", "ρ");
      charMap.put("\\sigma", "σ");
      charMap.put("\\tau", "τ");
      charMap.put("\\phi", "φ");
      charMap.put("\\chi", "χ");
      charMap.put("\\psi", "ψ");
      charMap.put("\\omega", "ω");
      charMap.put("\\degree", "°");
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
