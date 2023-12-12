package org.opensourcephysics.ejs.control.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.StringTokenizer;
import javax.swing.BoxLayout;
import org.opensourcephysics.ejs.control.value.IntegerValue;
import org.opensourcephysics.ejs.control.value.ObjectValue;
import org.opensourcephysics.ejs.control.value.StringValue;
import org.opensourcephysics.ejs.control.value.Value;

public class ConstantParser extends org.opensourcephysics.ejs.control.ConstantParser {
   public static Value pointConstant(String var0) {
      var0 = var0.trim().toLowerCase();
      if ("center".equals(var0)) {
         Dimension var5 = Toolkit.getDefaultToolkit().getScreenSize();
         return new ObjectValue(new Point(var5.width / 2, var5.height / 2));
      } else if (var0.indexOf(44) < 0) {
         return null;
      } else {
         try {
            StringTokenizer var1 = new StringTokenizer(var0, ",");
            int var2 = Integer.parseInt(var1.nextToken());
            int var3 = Integer.parseInt(var1.nextToken());
            return new ObjectValue(new Point(var2, var3));
         } catch (Exception var4) {
            var4.printStackTrace();
            return null;
         }
      }
   }

   public static Value dimensionConstant(String var0) {
      var0 = var0.trim().toLowerCase();
      if ("pack".equals(var0)) {
         return new StringValue("pack");
      } else if (var0.indexOf(44) < 0) {
         return null;
      } else {
         try {
            StringTokenizer var1 = new StringTokenizer(var0, ",");
            int var2 = Integer.parseInt(var1.nextToken());
            int var3 = Integer.parseInt(var1.nextToken());
            return new ObjectValue(new Dimension(var2, var3));
         } catch (Exception var4) {
            var4.printStackTrace();
            return null;
         }
      }
   }

   public static Value placementConstant(String var0) {
      var0 = var0.trim().toLowerCase();
      if (var0.equals("bottom")) {
         return new IntegerValue(3);
      } else if (var0.equals("left")) {
         return new IntegerValue(2);
      } else if (var0.equals("right")) {
         return new IntegerValue(4);
      } else {
         return var0.equals("top") ? new IntegerValue(1) : null;
      }
   }

   public static Value layoutConstant(Container var0, String var1) {
      var1 = var1.trim().toLowerCase();
      StringTokenizer var2 = new StringTokenizer(var1, ":,");
      String var3 = var2.nextToken();
      int var6;
      int var7;
      if (var3.equals("flow")) {
         if (var2.hasMoreTokens()) {
            try {
               String var12 = var2.nextToken();
               byte var11;
               if (var12.equals("left")) {
                  var11 = 0;
               } else if (var12.equals("right")) {
                  var11 = 2;
               } else {
                  var11 = 1;
               }

               if (var2.hasMoreTokens()) {
                  var6 = Integer.parseInt(var2.nextToken());
                  var7 = Integer.parseInt(var2.nextToken());
                  return new ObjectValue(new FlowLayout(var11, var6, var7));
               }

               return new ObjectValue(new FlowLayout(var11));
            } catch (Exception var8) {
               var8.printStackTrace();
            }
         }

         return new ObjectValue(new FlowLayout());
      } else {
         int var4;
         int var5;
         if (var3.equals("grid")) {
            if (var2.hasMoreTokens()) {
               try {
                  var4 = Integer.parseInt(var2.nextToken());
                  var5 = Integer.parseInt(var2.nextToken());
                  if (var2.hasMoreTokens()) {
                     var6 = Integer.parseInt(var2.nextToken());
                     var7 = Integer.parseInt(var2.nextToken());
                     return new ObjectValue(new GridLayout(var4, var5, var6, var7));
                  }

                  return new ObjectValue(new GridLayout(var4, var5));
               } catch (Exception var9) {
                  var9.printStackTrace();
               }
            }

            return new ObjectValue(new GridLayout());
         } else if (!var3.equals("border")) {
            if (var3.equals("hbox")) {
               return new ObjectValue(new BoxLayout(var0, 0));
            } else {
               return var3.equals("vbox") ? new ObjectValue(new BoxLayout(var0, 1)) : null;
            }
         } else {
            if (var2.hasMoreTokens()) {
               try {
                  var4 = Integer.parseInt(var2.nextToken());
                  var5 = Integer.parseInt(var2.nextToken());
                  return new ObjectValue(new BorderLayout(var4, var5));
               } catch (Exception var10) {
                  var10.printStackTrace();
               }
            }

            return new ObjectValue(new BorderLayout());
         }
      }
   }

   public static Value constraintsConstant(String var0) {
      var0 = var0.trim().toLowerCase();
      if (var0.equals("north")) {
         return new StringValue("North");
      } else if (var0.equals("south")) {
         return new StringValue("South");
      } else if (var0.equals("east")) {
         return new StringValue("East");
      } else if (var0.equals("west")) {
         return new StringValue("West");
      } else {
         return var0.equals("center") ? new StringValue("Center") : new StringValue("Center");
      }
   }

   public static Value orientationConstant(String var0) {
      var0 = var0.trim().toLowerCase();
      return var0.equals("vertical") ? new IntegerValue(1) : new IntegerValue(0);
   }

   public static Value alignmentConstant(String var0) {
      var0 = var0.trim().toLowerCase();
      if (var0.indexOf("top") != -1) {
         return new IntegerValue(1);
      } else if (var0.indexOf("center") != -1) {
         return new IntegerValue(0);
      } else if (var0.indexOf("bottom") != -1) {
         return new IntegerValue(3);
      } else if (var0.indexOf("left") != -1) {
         return new IntegerValue(2);
      } else if (var0.indexOf("right") != -1) {
         return new IntegerValue(4);
      } else if (var0.indexOf("leading") != -1) {
         return new IntegerValue(10);
      } else {
         return var0.indexOf("trailing") != -1 ? new IntegerValue(11) : new IntegerValue(0);
      }
   }
}
