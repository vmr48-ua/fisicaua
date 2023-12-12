package org.opensourcephysics.tools;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.swing.JApplet;
import org.opensourcephysics.controls.OSPLog;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.display.OSPFrame;

class LaunchableClassMap extends TreeMap {
   ClassLoader classLoader;
   String[] jarNames;
   boolean allLoaded = false;

   LaunchableClassMap(String[] var1) {
      this.jarNames = var1;
      ArrayList var2 = new ArrayList();

      for(int var3 = 0; var3 < var1.length; ++var3) {
         try {
            var2.add(new URL("file:" + var1[var3]));
         } catch (MalformedURLException var5) {
            OSPLog.info(var5 + " " + var1[var3]);
         }
      }

      this.classLoader = URLClassLoader.newInstance((URL[])((URL[])var2.toArray(new URL[0])));
   }

   Class smartLoadClass(String var1) throws ClassNotFoundException {
      try {
         return this.classLoader.loadClass(var1);
      } catch (ClassNotFoundException var3) {
         return this.getClass().getClassLoader().loadClass(var1);
      }
   }

   void loadAllLaunchables() {
      if (!this.allLoaded) {
         JApplet var1 = OSPFrame.applet;

         label72:
         for(int var2 = 0; var2 < this.jarNames.length; ++var2) {
            JarFile var3 = null;

            try {
               if (var1 == null) {
                  var3 = new JarFile(this.jarNames[var2]);
               } else {
                  String var4 = XML.getResolvedPath(this.jarNames[var2], var1.getCodeBase().toExternalForm());
                  URL var5 = new URL("jar:" + var4 + "!/");
                  JarURLConnection var6 = (JarURLConnection)var5.openConnection();
                  var3 = var6.getJarFile();
               }
            } catch (IOException var11) {
               OSPLog.info(var11.getClass().getName() + ": " + var11.getMessage());
            } catch (SecurityException var12) {
               OSPLog.info(var12.getClass().getName() + ": " + var12.getMessage());
            }

            if (var3 != null) {
               Enumeration var13 = var3.entries();

               while(true) {
                  String var15;
                  do {
                     do {
                        if (!var13.hasMoreElements()) {
                           continue label72;
                        }

                        JarEntry var14 = (JarEntry)var13.nextElement();
                        var15 = var14.getName();
                     } while(!var15.endsWith(".class"));
                  } while(var15.indexOf("$") != -1);

                  var15 = var15.substring(0, var15.indexOf(".class"));

                  for(int var7 = var15.indexOf("/"); var7 != -1; var7 = var15.indexOf("/")) {
                     var15 = var15.substring(0, var7) + "." + var15.substring(var7 + 1);
                  }

                  if (this.get(var15) == null) {
                     try {
                        Class var8 = this.smartLoadClass(var15);
                        if (Launcher.isLaunchable(var8)) {
                           this.put(var15, var8);
                        }
                     } catch (ClassNotFoundException var9) {
                     } catch (NoClassDefFoundError var10) {
                        OSPLog.info(var10.toString());
                     }
                  }
               }
            }
         }

         this.allLoaded = true;
      }
   }

   boolean includesJar(String var1) {
      for(int var2 = 0; var2 < this.jarNames.length; ++var2) {
         if (this.jarNames[var2].equals(var1)) {
            return true;
         }
      }

      return false;
   }

   Class getClass(String var1) {
      Class var2 = (Class)this.get(var1);
      if (var2 == null && !this.allLoaded) {
         try {
            var2 = this.smartLoadClass(var1);
            if (Launcher.isLaunchable(var2)) {
               return var2;
            }
         } catch (ClassNotFoundException var4) {
         } catch (NoClassDefFoundError var5) {
            OSPLog.info(var5.toString());
         }

         return null;
      } else {
         return var2;
      }
   }
}
