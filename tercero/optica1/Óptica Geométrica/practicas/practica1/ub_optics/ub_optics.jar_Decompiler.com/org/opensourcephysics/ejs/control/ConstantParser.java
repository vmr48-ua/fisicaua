package org.opensourcephysics.ejs.control;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import org.opensourcephysics.ejs.control.value.BooleanValue;
import org.opensourcephysics.ejs.control.value.ObjectValue;
import org.opensourcephysics.ejs.control.value.Value;

public class ConstantParser {
   public static final Color NULL_COLOR = new Color(0, 0, 0, 0);
   private static Font defaultFont = new Font("Dialog", 12, 0);

   public static Value fontConstant(Font var0, String var1) {
      if (var1.indexOf(44) < 0) {
         return null;
      } else {
         if (var0 == null) {
            var0 = defaultFont;
         }

         int var2 = var0.getStyle();
         int var3 = var0.getSize();
         String var4 = null;
         StringTokenizer var5 = new StringTokenizer(var1, ",", true);
         if (var5.hasMoreTokens()) {
            var4 = var5.nextToken();
            if (var4.equals(",")) {
               var4 = var0.getName();
            } else if (var5.hasMoreTokens()) {
               var5.nextToken();
            }
         } else {
            var4 = var0.getName();
         }

         if (var5.hasMoreTokens()) {
            String var6 = var5.nextToken().toLowerCase();
            var2 = var0.getStyle();
            if (!var6.equals(",")) {
               if (var6.indexOf("plain") != -1) {
                  var2 = 0;
               }

               if (var6.indexOf("bold") != -1) {
                  var2 = 1;
               }

               if (var6.indexOf("italic") != -1) {
                  var2 |= 2;
               }

               if (var5.hasMoreTokens()) {
                  var5.nextToken();
               }
            }
         }

         if (var5.hasMoreTokens()) {
            try {
               var3 = Integer.parseInt(var5.nextToken());
            } catch (Exception var7) {
               var3 = var0.getSize();
            }
         }

         return new ObjectValue(new Font(var4, var2, var3));
      }
   }

   public static Value booleanConstant(String var0) {
      if (var0.equals("true")) {
         return new BooleanValue(true);
      } else {
         return var0.equals("false") ? new BooleanValue(false) : null;
      }
   }

   public static Value colorConstant(String var0) {
      if (var0.indexOf(44) >= 0) {
         try {
            StringTokenizer var1 = new StringTokenizer(var0, ":,");
            int var2 = Integer.parseInt(var1.nextToken());
            int var3 = Integer.parseInt(var1.nextToken());
            int var4 = Integer.parseInt(var1.nextToken());
            int var5;
            if (var1.hasMoreTokens()) {
               var5 = Integer.parseInt(var1.nextToken());
            } else {
               var5 = 255;
            }

            if (var2 < 0) {
               var2 = 0;
            } else if (var2 > 255) {
               var2 = 255;
            }

            if (var3 < 0) {
               var3 = 0;
            } else if (var3 > 255) {
               var3 = 255;
            }

            if (var4 < 0) {
               var4 = 0;
            } else if (var4 > 255) {
               var4 = 255;
            }

            if (var5 < 0) {
               var5 = 0;
            } else if (var5 > 255) {
               var5 = 255;
            }

            return new ObjectValue(new Color(var2, var3, var4, var5));
         } catch (Exception var6) {
            var6.printStackTrace();
            return null;
         }
      } else if (!var0.equals("null") && !var0.equals("none")) {
         if (!var0.equals("black") && !var0.equals("Color.black")) {
            if (!var0.equals("blue") && !var0.equals("Color.blue")) {
               if (!var0.equals("cyan") && !var0.equals("Color.cyan")) {
                  if (!var0.equals("darkGray") && !var0.equals("Color.darkGray")) {
                     if (!var0.equals("gray") && !var0.equals("Color.gray")) {
                        if (!var0.equals("green") && !var0.equals("Color.green")) {
                           if (!var0.equals("lightGray") && !var0.equals("Color.lightGray")) {
                              if (!var0.equals("magenta") && !var0.equals("Color.magenta")) {
                                 if (!var0.equals("orange") && !var0.equals("Color.orange")) {
                                    if (!var0.equals("pink") && !var0.equals("Color.pink")) {
                                       if (!var0.equals("red") && !var0.equals("Color.red")) {
                                          if (!var0.equals("white") && !var0.equals("Color.white")) {
                                             return !var0.equals("yellow") && !var0.equals("Color.yellow") ? null : new ObjectValue(Color.yellow);
                                          } else {
                                             return new ObjectValue(Color.white);
                                          }
                                       } else {
                                          return new ObjectValue(Color.red);
                                       }
                                    } else {
                                       return new ObjectValue(Color.pink);
                                    }
                                 } else {
                                    return new ObjectValue(Color.orange);
                                 }
                              } else {
                                 return new ObjectValue(Color.magenta);
                              }
                           } else {
                              return new ObjectValue(Color.lightGray);
                           }
                        } else {
                           return new ObjectValue(Color.green);
                        }
                     } else {
                        return new ObjectValue(Color.gray);
                     }
                  } else {
                     return new ObjectValue(Color.darkGray);
                  }
               } else {
                  return new ObjectValue(Color.cyan);
               }
            } else {
               return new ObjectValue(Color.blue);
            }
         } else {
            return new ObjectValue(Color.black);
         }
      } else {
         return new ObjectValue(NULL_COLOR);
      }
   }

   public static Value formatConstant(String var0) {
      if (var0.indexOf(";") == -1) {
         int var1 = var0.indexOf("0");
         int var2 = var0.indexOf("#");
         int var3 = -1;
         if (var1 > 0 && var2 > 0) {
            var3 = var1 < var2 ? var1 : var2;
         } else if (var1 > 0) {
            var3 = var1;
         } else if (var2 > 0) {
            var3 = var2;
         }

         if (var3 > 0) {
            var0 = var0 + ";" + var0.substring(0, var3) + "-" + var0.substring(var3);
         }
      }

      try {
         return new ObjectValue(new DecimalFormat(var0));
      } catch (IllegalArgumentException var4) {
         return null;
      }
   }

   public static Value rectangleConstant(String var0) {
      if (var0.indexOf(44) < 0) {
         return null;
      } else {
         try {
            StringTokenizer var1 = new StringTokenizer(var0, ",");
            int var2 = Integer.parseInt(var1.nextToken());
            int var3 = Integer.parseInt(var1.nextToken());
            int var4 = Integer.parseInt(var1.nextToken());
            int var5 = Integer.parseInt(var1.nextToken());
            return new ObjectValue(new Rectangle(var2, var3, var4, var5));
         } catch (Exception var6) {
            var6.printStackTrace();
            return null;
         }
      }
   }
}
