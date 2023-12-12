package org.opensourcephysics.ejs.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import javax.swing.ImageIcon;

public class Utils {
   private static Hashtable cacheImages = new Hashtable();
   private static byte[] enormous = new byte[100000];

   public static boolean fileExists(String var0, String var1) {
      if (var1 == null) {
         return false;
      } else if (cacheImages.get(var1) != null) {
         return true;
      } else {
         if (var0 != null) {
            if (var0.startsWith("file:")) {
               var0 = "file:///" + var0.substring(6);
            }

            if (!var0.endsWith("/")) {
               var0 = var0 + "/";
            }
         }

         int var2 = var1.indexOf(43);
         if (var2 >= 0) {
            return fileExistsInJar(var0, var1.substring(0, var2), var1.substring(var2 + 1));
         } else if (var0 == null) {
            File var6 = new File(var1);
            return var6.exists();
         } else {
            try {
               URL var3 = new URL(var0 + var1);
               InputStream var4 = var3.openStream();
               var4.close();
               return true;
            } catch (Exception var5) {
               return false;
            }
         }
      }
   }

   public static boolean fileExistsInJar(String var0, String var1, String var2) {
      if (var2 != null && var1 != null) {
         Object var3 = null;

         try {
            if (var0 == null) {
               var3 = new FileInputStream(var1);
            } else {
               URL var4 = new URL(var0 + var1);
               var3 = var4.openStream();
            }

            JarInputStream var7 = new JarInputStream((InputStream)var3);

            JarEntry var5;
            do {
               var5 = var7.getNextJarEntry();
               if (var5 == null) {
                  return false;
               }
            } while(var5.isDirectory() || !var5.getName().equals(var2));

            return true;
         } catch (Exception var6) {
            return false;
         }
      } else {
         return false;
      }
   }

   public static ImageIcon icon(String var0, String var1) {
      return icon(var0, var1, true);
   }

   public static ImageIcon icon(String var0, String var1, boolean var2) {
      if (var1 == null) {
         return null;
      } else {
         ImageIcon var3 = (ImageIcon)cacheImages.get(var1);
         if (var3 != null) {
            return var3;
         } else {
            if (var0 != null) {
               if (var0.startsWith("file:")) {
                  var0 = "file:///" + var0.substring(6);
               }

               if (!var0.endsWith("/")) {
                  var0 = var0 + "/";
               }
            }

            int var4 = var1.indexOf(43);
            if (var4 >= 0) {
               var3 = iconJar(var0, var1.substring(0, var4), var1.substring(var4 + 1), var2);
            } else if (var0 == null) {
               File var5 = new File(var1);
               if (var5.exists()) {
                  var3 = new ImageIcon(var1);
               }
            } else {
               try {
                  URL var7 = new URL(var0 + var1);
                  var3 = new ImageIcon(var7);
               } catch (Exception var6) {
                  if (var2) {
                     var6.printStackTrace();
                  }

                  var3 = null;
               }
            }

            if (var3 != null && var3.getIconHeight() > 0) {
               cacheImages.put(var1, var3);
            } else if (var2) {
               System.out.println("Unable to load image " + var1);
            }

            return var3;
         }
      }
   }

   public static ImageIcon iconJar(String var0, String var1, String var2, boolean var3) {
      if (var2 != null && var1 != null) {
         ImageIcon var4 = null;
         Object var5 = null;

         try {
            if (var0 == null) {
               var5 = new FileInputStream(var1);
            } else {
               URL var6 = new URL(var0 + var1);
               var5 = var6.openStream();
            }

            JarInputStream var15 = new JarInputStream((InputStream)var5);
            boolean var7 = false;
            byte[] var8 = null;

            label60:
            while(true) {
               JarEntry var9;
               do {
                  do {
                     if (var7) {
                        break label60;
                     }

                     var9 = var15.getNextJarEntry();
                     if (var9 == null) {
                        break label60;
                     }
                  } while(var9.isDirectory());
               } while(!var9.getName().equals(var2));

               long var10 = (long)((int)var9.getSize());
               int var12 = 0;

               for(int var13 = 0; var13 >= 0; var12 += var13) {
                  var13 = var15.read(enormous, var12, 255);
                  if (var13 == -1) {
                     break;
                  }
               }

               var10 = (long)var12;
               var8 = new byte[(int)var10];
               System.arraycopy(enormous, 0, var8, 0, (int)var10);
               var7 = true;
            }

            var4 = new ImageIcon(var8);
         } catch (Exception var14) {
            if (var3) {
               var14.printStackTrace();
            }

            var4 = null;
         }

         return var4;
      } else {
         return null;
      }
   }
}
