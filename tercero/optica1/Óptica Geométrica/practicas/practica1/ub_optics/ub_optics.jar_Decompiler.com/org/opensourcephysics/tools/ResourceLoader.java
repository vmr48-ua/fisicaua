package org.opensourcephysics.tools;

import java.applet.AudioClip;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import org.opensourcephysics.controls.OSPLog;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.display.OSPFrame;

public class ResourceLoader {
   protected static ArrayList searchPaths = new ArrayList();
   protected static int maxPaths = 20;
   protected static Hashtable resources = new Hashtable();
   protected static boolean cacheEnabled = false;
   protected static Map zipLoaders = new TreeMap();
   protected static URLClassLoader xsetZipLoader;
   protected static String launchJarName;
   protected static String launchJarPath;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$tools$Resource;

   private ResourceLoader() {
   }

   public static Resource getResource(String var0) {
      return getResource(var0, class$org$opensourcephysics$tools$Resource == null ? (class$org$opensourcephysics$tools$Resource = class$("org.opensourcephysics.tools.Resource")) : class$org$opensourcephysics$tools$Resource);
   }

   public static Resource getResource(String var0, Class var1) {
      if (var0 != null && !var0.equals("")) {
         Resource var2 = findResource(var0, var1);
         if (var2 != null) {
            return var2;
         } else {
            StringBuffer var3 = new StringBuffer("Not found: " + var0);
            var3.append(" [searched " + var0);
            Iterator var4 = searchPaths.iterator();

            while(var4.hasNext()) {
               String var5 = getPath((String)var4.next(), var0);
               var2 = findResource(var5, var1);
               if (var2 != null) {
                  return var2;
               }

               var3.append(";" + var5);
            }

            var3.append("]");
            OSPLog.fine(var3.toString());
            return null;
         }
      } else {
         return null;
      }
   }

   public static Resource getResource(String var0, String var1) {
      return getResource(var0, var1, class$org$opensourcephysics$tools$Resource == null ? (class$org$opensourcephysics$tools$Resource = class$("org.opensourcephysics.tools.Resource")) : class$org$opensourcephysics$tools$Resource);
   }

   public static Resource getResource(String var0, String var1, Class var2) {
      if (var0 == null) {
         return getResource(var1, var2);
      } else {
         String var3 = getPath(var0, var1);
         Resource var4 = findResource(var3, var2);
         if (var4 != null) {
            return var4;
         } else if (!var0.startsWith("/") && var0.indexOf(":/") <= -1) {
            StringBuffer var5 = new StringBuffer("Not found: " + var3);
            var5.append(" [searched " + var3);
            if (OSPFrame.applet != null) {
               String var6 = OSPFrame.applet.getDocumentBase().toExternalForm();
               var6 = XML.getDirectoryPath(var6) + "/";
               var3 = getPath(getPath(var6, var0), var1);
               var4 = findResource(var3, var2);
               if (var4 != null) {
                  return var4;
               }

               var5.append(";" + var3);
               String var7 = OSPFrame.applet.getCodeBase().toExternalForm();
               if (!var7.equals(var6)) {
                  var3 = getPath(getPath(var7, var0), var1);
                  var4 = findResource(var3, var2);
                  if (var4 != null) {
                     return var4;
                  }

                  var5.append(";" + var3);
               }
            }

            Iterator var8 = searchPaths.iterator();

            while(var8.hasNext()) {
               var3 = getPath(getPath((String)var8.next(), var0), var1);
               var4 = findResource(var3, var2);
               if (var4 != null) {
                  return var4;
               }

               var5.append(";" + var3);
            }

            var5.append("]");
            OSPLog.fine(var5.toString());
            return null;
         } else {
            return null;
         }
      }
   }

   public static void addSearchPath(String var0) {
      if (var0 != null && !var0.equals("") && maxPaths >= 1) {
         synchronized(searchPaths) {
            if (searchPaths.contains(var0)) {
               searchPaths.remove(var0);
            } else {
               OSPLog.fine("Added path: " + var0);
            }

            searchPaths.add(0, var0);

            while(searchPaths.size() > Math.max(maxPaths, 0)) {
               var0 = (String)searchPaths.get(searchPaths.size() - 1);
               OSPLog.fine("Removed path: " + var0);
               searchPaths.remove(var0);
            }

         }
      }
   }

   public static void removeSearchPath(String var0) {
      if (var0 != null && !var0.equals("")) {
         synchronized(searchPaths) {
            if (searchPaths.contains(var0)) {
               OSPLog.fine("Removed path: " + var0);
               searchPaths.remove(var0);
            }

         }
      }
   }

   public static void setCacheEnabled(boolean var0) {
      cacheEnabled = var0;
   }

   public static boolean isCacheEnabled() {
      return cacheEnabled;
   }

   public static InputStream openInputStream(String var0) {
      Resource var1 = getResource(var0);
      return var1 == null ? null : var1.openInputStream();
   }

   public static Reader openReader(String var0) {
      Resource var1 = getResource(var0);
      return var1 == null ? null : var1.openReader();
   }

   public static String getString(String var0) {
      Resource var1 = getResource(var0);
      return var1 == null ? null : var1.getString();
   }

   public static ImageIcon getIcon(String var0) {
      Resource var1 = getResource(var0);
      return var1 == null ? null : var1.getIcon();
   }

   public static Image getImage(String var0) {
      Resource var1 = getResource(var0);
      return var1 == null ? null : var1.getImage();
   }

   public static BufferedImage getBufferedImage(String var0) {
      Resource var1 = getResource(var0);
      return var1 == null ? null : var1.getBufferedImage();
   }

   public static AudioClip getAudioClip(String var0) {
      Resource var1 = getResource(var0);
      return var1 == null ? null : var1.getAudioClip();
   }

   public static String getLaunchJarPath() {
      return launchJarPath;
   }

   private static Resource createFileResource(String var0) {
      if (OSPFrame.applet != null) {
         return null;
      } else if (var0.indexOf(".zip") <= -1 && var0.indexOf(".jar") <= -1) {
         File var1 = new File(var0);
         if (var1.exists() && var1.canRead()) {
            Resource var2 = new Resource(var1);
            if (var0.endsWith("xset")) {
               xsetZipLoader = null;
            }

            OSPLog.fine("File: " + XML.forwardSlash(var2.getAbsolutePath()));
            return var2;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static Resource createURLResource(String var0) {
      if (var0.indexOf(".zip") <= -1 && var0.indexOf(".jar") <= -1) {
         Resource var1 = null;
         URL var2;
         if (var0.indexOf(":/") > -1) {
            try {
               var2 = new URL(var0);
               var1 = createResource(var2);
            } catch (Exception var8) {
            }
         } else if (OSPFrame.applet != null && !var0.startsWith("/")) {
            var2 = OSPFrame.applet.getDocumentBase();

            URL var3;
            try {
               var3 = new URL(var2, var0);
               var1 = createResource(var3);
            } catch (Exception var7) {
            }

            if (var1 == null) {
               var3 = OSPFrame.applet.getCodeBase();
               String var4 = XML.getDirectoryPath(var2.toExternalForm()) + "/";
               if (!var3.toExternalForm().equals(var4)) {
                  try {
                     URL var5 = new URL(var3, var0);
                     var1 = createResource(var5);
                  } catch (Exception var6) {
                  }
               }
            }
         }

         if (var1 != null) {
            if (var0.endsWith(".xset")) {
               xsetZipLoader = null;
            }

            OSPLog.fine("URL: " + XML.forwardSlash(var1.getAbsolutePath()));
         }

         return var1;
      } else {
         return null;
      }
   }

   private static Resource createZipResource(String var0) {
      String var1 = null;
      String var2 = var0;
      int var3 = var0.indexOf("zip!/");
      if (var3 == -1) {
         var3 = var0.indexOf("jar!/");
      }

      if (var3 > -1) {
         var1 = var0.substring(0, var3 + 3);
         var2 = var0.substring(var3 + 5);
      }

      if (var1 == null) {
         if (!var0.endsWith(".zip") && !var0.endsWith(".jar")) {
            if (var0.endsWith(".xset")) {
               var1 = var0.substring(0, var0.length() - 4) + "zip";
            }
         } else {
            String var4 = XML.stripExtension(XML.getName(var0));
            var1 = var0;
            var2 = var4 + ".xset";
         }
      }

      URLClassLoader var11 = null;
      URL var5 = null;
      URL[] var6;
      URL var7;
      if (var1 != null) {
         var11 = (URLClassLoader)zipLoaders.get(var1);
         if (var11 != null) {
            var5 = var11.findResource(var2);
         } else {
            try {
               var6 = new URL[]{new URL("file", (String)null, var1)};
               var11 = new URLClassLoader(var6);
               var5 = var11.findResource(var2);
               if (var5 == null) {
                  var7 = (class$org$opensourcephysics$tools$Resource == null ? (class$org$opensourcephysics$tools$Resource = class$("org.opensourcephysics.tools.Resource")) : class$org$opensourcephysics$tools$Resource).getResource("/" + var1);
                  if (var7 != null) {
                     var6 = new URL[]{var7};
                     var11 = new URLClassLoader(var6);
                     var5 = var11.findResource(var2);
                  }
               }

               if (var5 != null) {
                  zipLoaders.put(var1, var11);
               }
            } catch (MalformedURLException var9) {
            }
         }
      }

      if (var5 == null && xsetZipLoader != null) {
         var5 = xsetZipLoader.findResource(var2);
      }

      if (var5 == null && launchJarPath != null) {
         var11 = (URLClassLoader)zipLoaders.get(launchJarPath);
         if (var11 != null) {
            var5 = var11.findResource(var2);
         } else {
            try {
               var6 = new URL[]{new URL("file", (String)null, launchJarPath)};
               var11 = new URLClassLoader(var6);
               var5 = var11.findResource(var2);
               if (var5 == null) {
                  var7 = (class$org$opensourcephysics$tools$Resource == null ? (class$org$opensourcephysics$tools$Resource = class$("org.opensourcephysics.tools.Resource")) : class$org$opensourcephysics$tools$Resource).getResource("/" + launchJarPath);
                  if (var7 != null) {
                     var6 = new URL[]{var7};
                     var11 = new URLClassLoader(var6);
                     var5 = var11.findResource(var2);
                  }
               }

               if (var5 != null) {
                  zipLoaders.put(launchJarPath, var11);
               }
            } catch (MalformedURLException var8) {
            }
         }
      }

      if (var5 != null) {
         try {
            Resource var12 = createResource(var5);
            if (var12 != null && var12.getAbsolutePath().indexOf(var0) != -1) {
               if (var2.endsWith("xset")) {
                  xsetZipLoader = var11;
               }

               OSPLog.fine("Zip: " + XML.forwardSlash(var12.getAbsolutePath()));
               return var12;
            }

            return null;
         } catch (IOException var10) {
         }
      }

      return null;
   }

   private static Resource createClassResource(String var0, Class var1) {
      if (var0.indexOf(":/") != -1) {
         return null;
      } else {
         int var3 = var0.indexOf("jar!/");
         if (var3 != -1) {
            var0 = var0.substring(var3 + 5);
         }

         Resource var4 = null;

         URL var5;
         try {
            var5 = var1.getResource("/" + var0);
            var4 = createResource(var5);
         } catch (Exception var8) {
         }

         if (var4 == null) {
            try {
               var5 = var1.getResource(var0);
               var4 = createResource(var5);
            } catch (Exception var7) {
            }
         }

         if (var4 != null) {
            String var9 = XML.forwardSlash(var4.getAbsolutePath());
            if (var9.indexOf("/jre") > -1 && var9.indexOf("/lib") > -1) {
               return null;
            }

            if (var9.indexOf(var0) == -1) {
               return null;
            }

            if (var0.endsWith("xset")) {
               xsetZipLoader = null;
            }

            OSPLog.fine("Class resource: " + var9);
            if (launchJarName == null) {
               int var6 = var9.indexOf(".jar!");
               if (var6 > -1) {
                  var9 = var9.substring(0, var6 + 4);
                  launchJarName = var9.substring(var9.lastIndexOf("/") + 1);
                  launchJarPath = var9;
               }
            }
         }

         return var4;
      }
   }

   private static Resource createResource(URL var0) throws IOException {
      if (var0 == null) {
         return null;
      } else {
         InputStream var1 = var0.openStream();
         if (var1.read() == -1) {
            return null;
         } else {
            var1.close();
            return new Resource(var0);
         }
      }
   }

   private static Resource findResource(String var0, Class var1) {
      Resource var2 = null;
      if (cacheEnabled) {
         var2 = (Resource)resources.get(var0);
         if (var2 != null) {
            OSPLog.finest("Found in cache: " + var0);
            return var2;
         }
      }

      if ((var2 = createFileResource(var0)) == null && (var2 = createURLResource(var0)) == null && (var2 = createZipResource(var0)) == null && (var2 = createClassResource(var0, var1)) == null) {
         return null;
      } else {
         if (cacheEnabled) {
            resources.put(var0, var2);
         }

         return var2;
      }
   }

   private static String getPath(String var0, String var1) {
      if (var0 == null) {
         var0 = "";
      }

      if (var0.endsWith(".jar") || var0.endsWith(".zip")) {
         var0 = var0 + "!";
      }

      String var2 = XML.getResolvedPath(var1, var0);
      if (System.getProperty("os.name").indexOf("Mac") > -1 && var2.startsWith("file:/") && !var2.startsWith("file:///")) {
         for(var2 = var2.substring(6); var2.startsWith("/"); var2 = var2.substring(1)) {
         }

         var2 = "file:///" + var2;
      }

      return var2;
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
