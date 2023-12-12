package org.opensourcephysics.ejs.control.value;

import java.util.StringTokenizer;

public abstract class Value {
   public abstract boolean getBoolean();

   public abstract int getInteger();

   public abstract double getDouble();

   public abstract String getString();

   public abstract Object getObject();

   public void copyValue(Value var1) {
      if (this instanceof DoubleValue) {
         ((DoubleValue)this).value = var1.getDouble();
      } else if (this instanceof IntegerValue) {
         ((IntegerValue)this).value = var1.getInteger();
      } else if (this instanceof BooleanValue) {
         ((BooleanValue)this).value = var1.getBoolean();
      } else if (this instanceof StringValue) {
         ((StringValue)this).value = var1.getString();
      } else if (this instanceof ObjectValue) {
         ((ObjectValue)this).value = var1.getObject();
      }

   }

   public Value cloneValue() {
      if (this instanceof DoubleValue) {
         return new DoubleValue(this.getDouble());
      } else if (this instanceof IntegerValue) {
         return new IntegerValue(this.getInteger());
      } else if (this instanceof BooleanValue) {
         return new BooleanValue(this.getBoolean());
      } else if (this instanceof StringValue) {
         return new StringValue(this.getString());
      } else {
         return this instanceof ObjectValue ? new ObjectValue(this.getObject()) : null;
      }
   }

   public String toString() {
      return this.getString();
   }

   public static Value parseConstantOrArray(String var0, boolean var1) {
      StringTokenizer var2 = new StringTokenizer(var0, ",");
      int var3 = var2.countTokens();
      if (var3 <= 1) {
         return parseConstant(var0, var1);
      } else {
         Value[] var4 = new Value[var3];
         boolean var5 = false;
         boolean var6 = false;
         boolean var7 = false;

         for(int var8 = 0; var8 < var3; ++var8) {
            var4[var8] = parseConstant(var2.nextToken(), var1);
            if (var4[var8] == null) {
               return parseConstant(var0, var1);
            }

            if (var4[var8] instanceof DoubleValue) {
               var5 = true;
            } else if (var4[var8] instanceof IntegerValue) {
               var6 = true;
            } else if (var4[var8] instanceof BooleanValue) {
               var7 = true;
            }
         }

         int var9;
         if (var5) {
            double[] var12 = new double[var3];

            for(var9 = 0; var9 < var3; ++var9) {
               var12[var9] = var4[var9].getDouble();
            }

            return new ObjectValue(var12);
         } else if (var6) {
            int[] var11 = new int[var3];

            for(var9 = 0; var9 < var3; ++var9) {
               var11[var9] = var4[var9].getInteger();
            }

            return new ObjectValue(var11);
         } else if (!var7) {
            return parseConstant(var0, var1);
         } else {
            boolean[] var10 = new boolean[var3];

            for(var9 = 0; var9 < var3; ++var9) {
               var10[var9] = var4[var9].getBoolean();
            }

            return new ObjectValue(var10);
         }
      }
   }

   public static String removeScapes(String var0) {
      String var1 = "";
      int var2 = var0.length();

      for(int var3 = 0; var3 < var2; ++var3) {
         char var4 = var0.charAt(var3);
         if (var4 == '\\') {
            if (var3 == var2 - 1) {
               return var1 + var4;
            }

            ++var3;
            var4 = var0.charAt(var3);
         }

         var1 = var1 + var4;
      }

      return var1;
   }

   public static Value parseConstant(String var0, boolean var1) {
      var0 = var0.trim();
      if (var0.length() <= 0) {
         return null;
      } else if (var0.startsWith("\"")) {
         if (var0.length() <= 1) {
            return null;
         } else {
            return !var0.endsWith("\"") ? null : new StringValue(removeScapes(var0.substring(1, var0.length() - 1)));
         }
      } else if (var0.startsWith("'")) {
         return !var0.endsWith("'") ? null : new StringValue(removeScapes(var0.substring(1, var0.length() - 1)));
      } else if (var0.equals("true")) {
         return new BooleanValue(true);
      } else if (var0.equals("false")) {
         return new BooleanValue(false);
      } else if (var0.indexOf(46) >= 0) {
         try {
            double var6 = Double.parseDouble(var0);
            return new DoubleValue(var6);
         } catch (Exception var5) {
            if (!var1) {
               System.err.println("Value : Error 2! Incorrect input to parse " + var0);
            }

            return null;
         }
      } else {
         try {
            int var2 = Integer.parseInt(var0);
            return new IntegerValue(var2);
         } catch (Exception var4) {
            return null;
         }
      }
   }
}
