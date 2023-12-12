package org.opensourcephysics.tools;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JMenu;
import javax.swing.border.TitledBorder;
import javax.swing.event.SwingPropertyChangeSupport;

public class FontSizer {
   static Object levelObj = new FontSizer();
   static PropertyChangeSupport support;
   static int level;
   static double levelFactor;
   static Map fontMap;

   private FontSizer() {
   }

   public static void setLevel(int var0) {
      level = Math.abs(var0);
      support.firePropertyChange("level", (Object)null, new Integer(level));
   }

   public static int getLevel() {
      return level;
   }

   public static void levelUp() {
      ++level;
      support.firePropertyChange("level", (Object)null, new Integer(level));
   }

   public static void levelDown() {
      --level;
      level = Math.max(level, 0);
      support.firePropertyChange("level", (Object)null, new Integer(level));
   }

   public static void setFonts(Object var0, int var1) {
      double var2 = getFactor(var1);
      if (var0 instanceof Container) {
         setFontFactor((Container)var0, var2);
      } else if (var0 instanceof TitledBorder) {
         setFontFactor((TitledBorder)var0, var2);
      } else if (var0 instanceof Component) {
         setFontFactor((Component)var0, var2);
      }

   }

   public static Font getResizedFont(Font var0, int var1) {
      return getResizedFont(var0, getFactor(var1));
   }

   public static Font getResizedFont(Font var0, double var1) {
      if (var0 == null) {
         return null;
      } else {
         Font var3 = (Font)fontMap.get(var0);
         if (var3 == null) {
            var3 = var0;
            fontMap.put(var0, var0);
         }

         float var4 = (float)((double)var3.getSize() * var1);
         var0 = var3.deriveFont(var4);
         fontMap.put(var0, var3);
         return var0;
      }
   }

   public static double getFactor(int var0) {
      double var1 = 1.0D;

      for(int var3 = 0; var3 < var0; ++var3) {
         var1 *= levelFactor;
      }

      return var1;
   }

   public static void addPropertyChangeListener(String var0, PropertyChangeListener var1) {
      if (var0.equals("level")) {
         support.addPropertyChangeListener(var0, var1);
      }

   }

   public static void removePropertyChangeListener(String var0, PropertyChangeListener var1) {
      support.removePropertyChangeListener(var0, var1);
   }

   private static void setFontFactor(Container var0, double var1) {
      Font var3 = getResizedFont(var0.getFont(), var1);
      if (var0 instanceof JMenu) {
         setMenuFont((JMenu)var0, var3);
      } else {
         var0.setFont(var3);

         for(int var4 = 0; var4 < var0.getComponentCount(); ++var4) {
            Component var5 = var0.getComponent(var4);
            if (var5 instanceof Container) {
               setFontFactor((Container)var5, var1);
            } else {
               setFontFactor(var5, var1);
            }
         }
      }

      if (var0 != null) {
         var0.repaint();
      }

   }

   private static void setFontFactor(Component var0, double var1) {
      Font var3 = getResizedFont(var0.getFont(), var1);
      var0.setFont(var3);
   }

   private static void setFontFactor(TitledBorder var0, double var1) {
      Font var3 = getResizedFont(var0.getTitleFont(), var1);
      var0.setTitleFont(var3);
   }

   private static void setMenuFont(JMenu var0, Font var1) {
      var0.setFont(var1);

      for(int var2 = 0; var2 < var0.getMenuComponentCount(); ++var2) {
         var0.getMenuComponent(var2).setFont(var1);
         if (var0.getMenuComponent(var2) instanceof JMenu) {
            setMenuFont((JMenu)var0.getMenuComponent(var2), var1);
         }
      }

   }

   static {
      support = new SwingPropertyChangeSupport(levelObj);
      levelFactor = 1.35D;
      fontMap = new HashMap();
   }
}
