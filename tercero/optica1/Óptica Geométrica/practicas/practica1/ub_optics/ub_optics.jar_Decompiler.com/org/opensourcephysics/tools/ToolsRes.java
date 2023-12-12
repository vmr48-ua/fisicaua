package org.opensourcephysics.tools;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ToolsRes {
   private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("org.opensourcephysics.resources.tools.tools");

   private ToolsRes() {
   }

   public static String getString(String var0) {
      try {
         return BUNDLE.getString(var0);
      } catch (MissingResourceException var2) {
         return '!' + var0 + '!';
      }
   }
}
