package org.opensourcephysics.display.dialogs;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DialogsRes {
   public static final String AUTOSCALE_AUTOSCALE;
   public static final String AUTOSCALE_AUTO;
   public static final String AUTOSCALE_OK;
   public static final String AUTOSCALE_ZOOM_WARNING;
   public static final String SCALE_SCALE;
   public static final String SCALE_MIN;
   public static final String SCALE_MAX;
   public static final String SCALE_AUTO;
   public static final String SCALE_CANCEL;
   public static final String SCALE_OK;
   public static final String LOG_SCALE;
   public static final String LOG_X;
   public static final String LOG_Y;
   public static final String LOG_OK;
   public static final String LOG_WARNING;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$display$dialogs$DialogsRes;

   private DialogsRes() {
   }

   private static String getString(ResourceBundle var0, String var1) {
      try {
         return var0.getString(var1);
      } catch (MissingResourceException var3) {
         return '|' + var1 + '|';
      }
   }

   static {
      ResourceBundle var1 = ResourceBundle.getBundle("org.opensourcephysics.resources.display.dialogs_res", Locale.getDefault(), (class$org$opensourcephysics$display$dialogs$DialogsRes == null ? (class$org$opensourcephysics$display$dialogs$DialogsRes = class$("org.opensourcephysics.display.dialogs.DialogsRes")) : class$org$opensourcephysics$display$dialogs$DialogsRes).getClassLoader());
      AUTOSCALE_AUTOSCALE = getString(var1, "AUTOSCALE_AUTOSCALE");
      AUTOSCALE_AUTO = getString(var1, "AUTOSCALE_AUTO");
      AUTOSCALE_OK = getString(var1, "AUTOSCALE_OK");
      AUTOSCALE_ZOOM_WARNING = getString(var1, "AUTOSCALE_ZOOM_WARNING");
      SCALE_SCALE = getString(var1, "SCALE_SCALE");
      SCALE_MIN = getString(var1, "SCALE_MIN");
      SCALE_MAX = getString(var1, "SCALE_MAX");
      SCALE_AUTO = getString(var1, "SCALE_AUTO");
      SCALE_CANCEL = getString(var1, "SCALE_CANCEL");
      SCALE_OK = getString(var1, "SCALE_OK");
      LOG_SCALE = getString(var1, "LOG_SCALE");
      LOG_X = getString(var1, "LOG_X");
      LOG_Y = getString(var1, "LOG_Y");
      LOG_OK = getString(var1, "LOG_OK");
      LOG_WARNING = getString(var1, "LOG_WARNING");
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
