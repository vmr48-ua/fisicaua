package org.opensourcephysics.tools;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.event.SwingPropertyChangeSupport;

class LaunchRes {
   static Locale locale = Locale.getDefault();
   static ResourceBundle res;
   static Object resObj;
   static PropertyChangeSupport support;

   private LaunchRes() {
   }

   static String getString(String var0) {
      try {
         return res.getString(var0);
      } catch (MissingResourceException var2) {
         return "!" + var0 + "!";
      }
   }

   public static void setLocale(Locale var0) {
      if (locale != var0) {
         Locale var1 = locale;
         locale = var0;
         res = ResourceBundle.getBundle("org.opensourcephysics.resources.tools.launcher", locale);
         support.firePropertyChange("locale", var1, locale);
      }
   }

   public static void addPropertyChangeListener(String var0, PropertyChangeListener var1) {
      if (var0.equals("locale")) {
         support.addPropertyChangeListener(var0, var1);
      }

   }

   public static void removePropertyChangeListener(String var0, PropertyChangeListener var1) {
      support.removePropertyChangeListener(var0, var1);
   }

   static {
      res = ResourceBundle.getBundle("org.opensourcephysics.resources.tools.launcher", locale);
      resObj = new LaunchRes();
      support = new SwingPropertyChangeSupport(resObj);
   }
}
