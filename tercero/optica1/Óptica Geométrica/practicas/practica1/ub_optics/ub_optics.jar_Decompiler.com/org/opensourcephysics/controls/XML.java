package org.opensourcephysics.controls;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class XML {
   public static String NEW_LINE = "/n";
   public static final String CDATA_PRE = "<![CDATA[";
   public static final String CDATA_POST = "]]>";
   public static final int INDENT = 4;
   private static Map loaders = new HashMap();
   private static XML.ObjectLoader defaultLoader;
   private static String dtdName;
   private static String dtd;
   private static String defaultName = "osp10.dtd";
   private static ClassLoader classLoader;
   // $FF: synthetic field
   static Class class$java$awt$Color;
   // $FF: synthetic field
   static Class class$java$lang$Boolean;
   // $FF: synthetic field
   static Class class$java$lang$Double;
   // $FF: synthetic field
   static Class class$java$lang$Integer;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$controls$XML;

   private XML() {
   }

   public static void setLoader(Class var0, XML.ObjectLoader var1) {
      loaders.put(var0, var1);
   }

   public static XML.ObjectLoader getLoader(Class var0) {
      XML.ObjectLoader var1 = (XML.ObjectLoader)loaders.get(var0);
      if (var1 == null) {
         try {
            Method var2 = var0.getMethod("getLoader", (Class[])null);
            if (var2 != null && Modifier.isStatic(var2.getModifiers())) {
               var1 = (XML.ObjectLoader)var2.invoke((Object)null, (Object[])null);
               if (var1 != null) {
                  setLoader(var0, var1);
               }
            }
         } catch (IllegalAccessException var3) {
         } catch (IllegalArgumentException var4) {
         } catch (InvocationTargetException var5) {
         } catch (NoSuchMethodException var6) {
         } catch (SecurityException var7) {
         }
      }

      if (var1 == null) {
         if (defaultLoader == null) {
            defaultLoader = new XMLLoader();
         }

         var1 = defaultLoader;
      }

      return var1;
   }

   public static void setDefaultLoader(XML.ObjectLoader var0) {
      defaultLoader = var0;
   }

   public static String getDataType(Object var0) {
      if (var0 == null) {
         return null;
      } else if (var0 instanceof String) {
         return "string";
      } else if (var0 instanceof Collection) {
         return "collection";
      } else if (!var0.getClass().isArray()) {
         if (var0 instanceof Double) {
            return "double";
         } else {
            return var0 instanceof Integer ? "int" : "object";
         }
      } else {
         Class var1;
         for(var1 = var0.getClass().getComponentType(); var1.isArray(); var1 = var1.getComponentType()) {
         }

         String var2 = var1.getName();
         return var2.indexOf(".") == -1 && "intdoubleboolean".indexOf(var2) == -1 ? null : "array";
      }
   }

   public static String[] getDataTypes() {
      return new String[]{"object", "array", "collection", "string", "int", "double", "boolean"};
   }

   public static boolean requiresCDATA(String var0) {
      return var0.indexOf("\"") != -1 || var0.indexOf("<") != -1 || var0.indexOf(">") != -1 || var0.indexOf("&") != -1 || var0.indexOf("'") != -1;
   }

   public static String getDTD(String var0) {
      if (dtdName != var0) {
         dtdName = defaultName;

         try {
            String var1 = "/org/opensourcephysics/resources/controls/doctypes/";
            URL var2 = (class$org$opensourcephysics$controls$XML == null ? (class$org$opensourcephysics$controls$XML = class$("org.opensourcephysics.controls.XML")) : class$org$opensourcephysics$controls$XML).getResource(var1 + var0);
            if (var2 == null) {
               return dtd;
            }

            Object var3 = var2.getContent();
            if (var3 instanceof InputStream) {
               BufferedReader var4 = new BufferedReader(new InputStreamReader((InputStream)var3));
               StringBuffer var5 = new StringBuffer(0);

               String var6;
               while((var6 = var4.readLine()) != null) {
                  var5.append(var6 + NEW_LINE);
               }

               dtd = var5.toString();
               dtdName = var0;
            }
         } catch (IOException var7) {
         }
      }

      return dtd;
   }

   public static void setClassLoader(ClassLoader var0) {
      classLoader = var0;
   }

   public static ClassLoader getClassLoader() {
      return classLoader;
   }

   public static String forwardSlash(String var0) {
      if (var0 == null) {
         return "";
      } else {
         for(int var1 = var0.indexOf("\\"); var1 != -1; var1 = var0.indexOf("\\")) {
            var0 = var0.substring(0, var1) + "/" + var0.substring(var1 + 1);
         }

         return var0;
      }
   }

   public static String getName(String var0) {
      if (var0 == null) {
         return "";
      } else {
         int var1 = var0.lastIndexOf("/");
         if (var1 == -1) {
            var1 = var0.lastIndexOf("\\");
         }

         return var1 != -1 ? var0.substring(var1 + 1) : var0;
      }
   }

   public static String getExtension(String var0) {
      if (var0 == null) {
         return null;
      } else {
         int var1 = var0.lastIndexOf(46);
         return var1 > 0 && var1 < var0.length() - 1 ? var0.substring(var1 + 1) : null;
      }
   }

   public static String getSimpleClassName(Class var0) {
      String var1 = var0.getName();
      int var2 = var1.indexOf(";");
      if (var2 > -1) {
         var1 = var1.substring(0, var2);
      }

      while(var1.startsWith("[")) {
         var1 = var1.substring(1);
         var1 = var1 + "[]";
      }

      String var3 = getExtension(var1);
      if (var3 != null) {
         var1 = var3;
      }

      var2 = var1.indexOf("[");
      if (var2 > -1) {
         String var4 = var1.substring(0, var2);
         if (var4.equals("I")) {
            var4 = "int";
         } else if (var4.equals("D")) {
            var4 = "double";
         } else if (var4.equals("Z")) {
            var4 = "boolean";
         }

         var1 = var4 + var1.substring(var2);
      }

      return var1;
   }

   public static String stripExtension(String var0) {
      if (var0 == null) {
         return null;
      } else {
         int var1 = var0.lastIndexOf(46);
         return var1 > 0 && var1 < var0.length() - 1 ? var0.substring(0, var1) : var0;
      }
   }

   public static String getPathRelativeTo(String var0, String var1) {
      if (var1 == null || var1.equals("")) {
         var1 = getUserDirectory();
      }

      var0 = forwardSlash(var0);
      var1 = forwardSlash(var1);
      if (!var0.startsWith("/") && var0.indexOf(":") == -1) {
         return var0;
      } else if (!var1.startsWith("/") && var1.indexOf(":") == -1) {
         return var0;
      } else {
         int var2 = var0.indexOf("jar!");
         if (var2 > -1) {
            var0 = var0.substring(var2 + 5);
            return var0;
         } else {
            String var3 = "";
            if (var1.endsWith("/")) {
               var1 = var1.substring(0, var1.length() - 1);
            }

            for(int var4 = 0; var4 < 6; ++var4) {
               if (var4 > 0) {
                  int var5 = var1.lastIndexOf("/");
                  if (var5 != -1) {
                     var1 = var1.substring(0, var5);
                     var3 = var3 + "../";
                  } else {
                     if (var1.equals("")) {
                        break;
                     }

                     var1 = "";
                     var3 = var3 + "../";
                  }
               }

               if (!var1.equals("") && var0.startsWith(var1)) {
                  String var7 = var0.substring(var1.length());
                  int var6 = var7.indexOf("/");
                  if (var6 == 0) {
                     var7 = var7.substring(1);
                  }

                  var3 = var3 + var7;
                  return var3;
               }
            }

            return var0;
         }
      }
   }

   public static String getRelativePath(String var0) {
      return getPathRelativeTo(var0, getUserDirectory());
   }

   public static String getUserDirectory() {
      String var0 = System.getProperty("user.dir", ".");
      return var0;
   }

   public static String getDirectoryPath(String var0) {
      if (var0 == null) {
         return "";
      } else {
         var0 = forwardSlash(var0);
         int var1 = var0.lastIndexOf("/");
         return var1 != -1 ? var0.substring(0, var1) : "";
      }
   }

   public static String getResolvedPath(String var0, String var1) {
      var0 = forwardSlash(var0);
      if (!var0.startsWith("/") && var0.indexOf(":/") == -1) {
         for(var1 = forwardSlash(var1); var0.startsWith("../") && !var1.equals(""); var1 = var1.substring(0, var1.lastIndexOf("/"))) {
            if (var1.indexOf("/") == -1) {
               var1 = "/" + var1;
            }

            var0 = var0.substring(3);
         }

         if (var1.equals("")) {
            return var0;
         } else {
            return var1.endsWith("/") ? var1 + var0 : var1 + "/" + var0;
         }
      } else {
         return var0;
      }
   }

   public static void createFolders(String var0) {
      File var1 = new File(var0);

      ArrayList var2;
      for(var2 = new ArrayList(); !var1.exists(); var1 = new File(var0)) {
         var2.add(0, var1);
         int var3 = var0.lastIndexOf("/");
         if (var3 == -1) {
            break;
         }

         var0 = var0.substring(0, var3);
      }

      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         var1 = (File)var4.next();
         var1.mkdir();
      }

   }

   static {
      try {
         NEW_LINE = System.getProperty("line.separator", "/n");
      } catch (SecurityException var1) {
      }

      setLoader(class$java$awt$Color == null ? (class$java$awt$Color = class$("java.awt.Color")) : class$java$awt$Color, new XML.ObjectLoader() {
         public void saveObject(XMLControl var1, Object var2) {
            Color var3 = (Color)var2;
            var1.setValue("red", var3.getRed());
            var1.setValue("green", var3.getGreen());
            var1.setValue("blue", var3.getBlue());
            var1.setValue("alpha", var3.getAlpha());
         }

         public Object createObject(XMLControl var1) {
            int var2 = var1.getInt("red");
            int var3 = var1.getInt("green");
            int var4 = var1.getInt("blue");
            int var5 = var1.getInt("alpha");
            return new Color(var2, var3, var4, var5);
         }

         public Object loadObject(XMLControl var1, Object var2) {
            int var3 = var1.getInt("red");
            int var4 = var1.getInt("green");
            int var5 = var1.getInt("blue");
            int var6 = var1.getInt("alpha");
            return new Color(var3, var4, var5, var6);
         }
      });
      setLoader(class$java$lang$Double == null ? (class$java$lang$Double = class$("java.lang.Double")) : class$java$lang$Double, new XML.ObjectLoader() {
         public void saveObject(XMLControl var1, Object var2) {
            Double var3 = (Double)var2;
            var1.setValue("value", var3);
         }

         public Object createObject(XMLControl var1) {
            double var2 = var1.getDouble("value");
            return new Double(var2);
         }

         public Object loadObject(XMLControl var1, Object var2) {
            Double var3 = (Double)var2;
            double var4 = var1.getDouble("value");
            return var3 == var4 ? var3 : new Double(var4);
         }
      });
      setLoader(class$java$lang$Integer == null ? (class$java$lang$Integer = class$("java.lang.Integer")) : class$java$lang$Integer, new XML.ObjectLoader() {
         public void saveObject(XMLControl var1, Object var2) {
            Integer var3 = (Integer)var2;
            var1.setValue("value", var3);
         }

         public Object createObject(XMLControl var1) {
            int var2 = var1.getInt("value");
            return new Integer(var2);
         }

         public Object loadObject(XMLControl var1, Object var2) {
            Integer var3 = (Integer)var2;
            int var4 = var1.getInt("value");
            return var3 == var4 ? var3 : new Integer(var4);
         }
      });
      setLoader(class$java$lang$Boolean == null ? (class$java$lang$Boolean = class$("java.lang.Boolean")) : class$java$lang$Boolean, new XML.ObjectLoader() {
         public void saveObject(XMLControl var1, Object var2) {
            Boolean var3 = (Boolean)var2;
            var1.setValue("value", var3);
         }

         public Object createObject(XMLControl var1) {
            boolean var2 = var1.getBoolean("value");
            return new Boolean(var2);
         }

         public Object loadObject(XMLControl var1, Object var2) {
            Boolean var3 = (Boolean)var2;
            boolean var4 = var1.getBoolean("value");
            return var3 == var4 ? var3 : new Boolean(var4);
         }
      });
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public interface ObjectLoader {
      void saveObject(XMLControl var1, Object var2);

      Object createObject(XMLControl var1);

      Object loadObject(XMLControl var1, Object var2);
   }
}
