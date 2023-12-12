package org.opensourcephysics.controls;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ControlsRes {
   static ResourceBundle res = ResourceBundle.getBundle("org.opensourcephysics.resources.controls.controls_res");
   public static final String ANIMATION_NEW;
   public static final String ANIMATION_INIT;
   public static final String ANIMATION_STEP;
   public static final String ANIMATION_RESET;
   public static final String ANIMATION_START;
   public static final String ANIMATION_STOP;
   public static final String ANIMATION_RESET_TIP;
   public static final String ANIMATION_INIT_TIP;
   public static final String ANIMATION_START_TIP;
   public static final String ANIMATION_STOP_TIP;
   public static final String ANIMATION_NEW_TIP;
   public static final String ANIMATION_STEP_TIP;
   public static final String CALCULATION_CALC;
   public static final String CALCULATION_RESET;
   public static final String CALCULATION_CALC_TIP;
   public static final String CALCULATION_RESET_TIP;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$controls$ControlsRes;

   private ControlsRes() {
   }

   private static String getString(ResourceBundle var0, String var1) {
      try {
         return var0.getString(var1);
      } catch (MissingResourceException var3) {
         return '|' + var1 + '|';
      }
   }

   static String getString(String var0) {
      try {
         return res.getString(var0);
      } catch (MissingResourceException var2) {
         return "!" + var0 + "!";
      }
   }

   static {
      ResourceBundle var1 = ResourceBundle.getBundle("org.opensourcephysics.resources.controls.controls_res", Locale.getDefault(), (class$org$opensourcephysics$controls$ControlsRes == null ? (class$org$opensourcephysics$controls$ControlsRes = class$("org.opensourcephysics.controls.ControlsRes")) : class$org$opensourcephysics$controls$ControlsRes).getClassLoader());
      ANIMATION_NEW = getString(var1, "ANIMATION_NEW");
      ANIMATION_INIT = getString(var1, "ANIMATION_INIT");
      ANIMATION_STEP = getString(var1, "ANIMATION_STEP");
      ANIMATION_RESET = getString(var1, "ANIMATION_RESET");
      ANIMATION_START = getString(var1, "ANIMATION_START");
      ANIMATION_STOP = getString(var1, "ANIMATION_STOP");
      ANIMATION_RESET_TIP = getString(var1, "ANIMATION_RESET_TIP");
      ANIMATION_INIT_TIP = getString(var1, "ANIMATION_INIT_TIP");
      ANIMATION_START_TIP = getString(var1, "ANIMATION_START_TIP");
      ANIMATION_STOP_TIP = getString(var1, "ANIMATION_STOP_TIP");
      ANIMATION_NEW_TIP = getString(var1, "ANIMATION_NEW_TIP");
      ANIMATION_STEP_TIP = getString(var1, "ANIMATION_STEP_TIP");
      CALCULATION_CALC = getString(var1, "CALCULATION_CALC");
      CALCULATION_RESET = getString(var1, "CALCULATION_RESET");
      CALCULATION_CALC_TIP = getString(var1, "CALCULATION_CALC_TIP");
      CALCULATION_RESET_TIP = getString(var1, "CALCULATION_RESET_TIP");
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
