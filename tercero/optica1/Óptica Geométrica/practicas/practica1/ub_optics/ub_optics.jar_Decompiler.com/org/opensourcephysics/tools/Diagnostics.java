package org.opensourcephysics.tools;

import java.awt.Component;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import javax.swing.JOptionPane;

public class Diagnostics {
   static final String NEWLINE = System.getProperty("line.separator", "\n");

   public static void aboutJava() {
      String var0 = System.getProperty("java.vm.version", "unknown version");
      String var1 = System.getProperty("java.vendor");
      String var2 = "Java version " + var0 + NEWLINE + var1;
      JOptionPane.showMessageDialog((Component)null, var2, "About Java", 1);
   }

   public static void aboutQTJava() {
      String var0 = System.getProperty("java.ext.dirs");
      String var1 = System.getProperty("file.separator", "/");
      File var2 = new File(var0 + var1 + "QTJava.zip");
      if (var2.exists()) {
         boolean var3 = false;

         try {
            Class var4 = Class.forName("quicktime.util.QTBuild");
            Method var5 = var4.getMethod("info", (Class[])null);
            String var6 = (String)var5.invoke((Object)null, (Object[])null);
            var6 = var6.substring(var6.indexOf(":") + 1, var6.indexOf("]"));
            String var7 = "QTJava version " + var6;
            JOptionPane.showMessageDialog((Component)null, var7, "About QuickTime for Java", 1);
         } catch (Exception var8) {
            var3 = true;
         } catch (Error var9) {
            var3 = true;
         }

         if (var3) {
            JOptionPane.showMessageDialog((Component)null, "QuickTime for Java is installed but not operating correctly.", "About QuickTime for Java", 2);
         }
      } else {
         JOptionPane.showMessageDialog((Component)null, "QuickTime for Java was not found in " + var0 + "." + NEWLINE + "The installer can be downloaded from http://www.apple.com/quicktime/download/standalone.html.", "About QuickTime for Java", 2);
      }

   }

   public static void aboutJava3D() {
      String var0 = System.getProperty("java.ext.dirs");
      String var1 = System.getProperty("file.separator", "/");
      File var2 = new File(var0 + var1 + "j3dcore.jar");
      if (var2.exists()) {
         boolean var3 = false;

         try {
            Class var4 = Class.forName("javax.media.j3d.VirtualUniverse");
            Method var5 = var4.getMethod("getProperties", (Class[])null);
            Map var6 = (Map)var5.invoke((Object)null, (Object[])null);
            String var7 = (String)var6.get("j3d.version");
            String var8 = (String)var6.get("j3d.vendor");
            String var9 = "Java 3D version " + var7 + NEWLINE + var8;
            JOptionPane.showMessageDialog((Component)null, var9, "About Java 3D", 1);
         } catch (Exception var10) {
            var3 = true;
         } catch (Error var11) {
            var3 = true;
         }

         if (var3) {
            JOptionPane.showMessageDialog((Component)null, "Java 3D is installed but not operating correctly.", "About Java 3D", 2);
         }
      } else {
         JOptionPane.showMessageDialog((Component)null, "Java 3D was not found in " + var0 + "." + NEWLINE + "The installer can be downloaded from https://java3d.dev.java.net/binary-builds.html.", "About Java 3D", 2);
      }

   }

   public static void aboutJOGL() {
      String var0 = System.getProperty("java.ext.dirs");
      String var1 = System.getProperty("file.separator", "/");
      File var2 = new File(var0 + var1 + "jogl.jar");
      if (var2.exists()) {
         boolean var3 = false;

         try {
            Class var4 = Class.forName("javax.media.opengl.glu.GLU");
            Field var5 = var4.getField("versionString");
            String var6 = (String)var5.get((Object)null);
            String var7 = "JOGL version " + var6;
            JOptionPane.showMessageDialog((Component)null, var7, "About JOGL", 1);
         } catch (Exception var8) {
            var3 = true;
         } catch (Error var9) {
            var3 = true;
         }

         if (var3) {
            JOptionPane.showMessageDialog((Component)null, "JOGL is installed but not operating correctly.", "About JOGL", 2);
         }
      } else {
         JOptionPane.showMessageDialog((Component)null, "JOGL was not found in " + var0 + "." + NEWLINE + "The required jar file \"jogl.jar\" can be downloaded from https://jogl.dev.java.net/.", "About JOGL", 2);
      }

   }
}
