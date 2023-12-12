package org.opensourcephysics.ejs.control;

import java.lang.reflect.Method;
import org.opensourcephysics.ejs.control.value.BooleanValue;
import org.opensourcephysics.ejs.control.value.DoubleValue;
import org.opensourcephysics.ejs.control.value.IntegerValue;
import org.opensourcephysics.ejs.control.value.StringValue;
import org.opensourcephysics.ejs.control.value.Value;

public class MethodWithOneParameter {
   private String methodName;
   private int methodType;
   private Object targetObject = null;
   private Object[] parameterList = new Object[0];
   private Method methodToCall;
   private MethodWithOneParameter secondMethod = null;
   private Value returnValue = null;

   MethodWithOneParameter(int var1, Object var2, String var3, String var4, MethodWithOneParameter var5, Object var6) {
      Class[] var7 = new Class[0];
      Object var8 = null;
      Class var9 = null;
      this.methodName = var3;
      this.methodType = var1;
      this.targetObject = var2;
      this.secondMethod = var5;
      String[] var10 = splitMethodName(var3.trim());
      if (var10[2].equals("#CONTROL#") && var6 != null) {
         var8 = var6;
         var9 = var6.getClass();
      } else {
         Value var11 = Value.parseConstant(var10[2], false);
         if (var11 == null) {
            var8 = null;
         } else if (var11 instanceof StringValue) {
            var8 = var11.getString();
            var9 = var3.getClass();
         } else if (var11 instanceof BooleanValue) {
            var8 = new Boolean(var11.getBoolean());
            var9 = Boolean.TYPE;
         } else if (var11 instanceof DoubleValue) {
            var8 = new Double(var11.getDouble());
            var9 = Double.TYPE;
         } else if (var11 instanceof IntegerValue) {
            var8 = new Integer(var11.getInteger());
            var9 = Integer.TYPE;
         }
      }

      if (var8 != null) {
         var7 = new Class[]{var9};
         this.parameterList = new Object[1];
         this.parameterList[0] = var8;
      }

      this.methodToCall = resolveMethod(this.targetObject, var10[1], var7);
      if (this.methodToCall == null) {
         System.err.println(this.getClass().getName() + " : Error! Unable to find a suitable method " + this.methodName + " in class " + this.targetObject.getClass().getName());
      }

      if (var4 == null) {
         this.returnValue = null;
      } else {
         var4 = var4.trim().toLowerCase();
         if (var4.equals("double")) {
            this.returnValue = new DoubleValue(0.0D);
         } else if (var4.equals("int")) {
            this.returnValue = new IntegerValue(0);
         } else if (var4.equals("string")) {
            this.returnValue = new StringValue("");
         } else if (var4.equals("boolean")) {
            this.returnValue = new BooleanValue(false);
         } else {
            this.returnValue = null;
         }
      }

   }

   public Value invoke(int var1, Object var2) {
      if (this.methodType != var1) {
         return null;
      } else {
         try {
            if (this.returnValue == null) {
               this.methodToCall.invoke(this.targetObject, this.parameterList);
            } else if (this.returnValue instanceof DoubleValue) {
               ((DoubleValue)this.returnValue).value = (Double)this.methodToCall.invoke(this.targetObject, this.parameterList);
            } else if (this.returnValue instanceof IntegerValue) {
               ((IntegerValue)this.returnValue).value = (Integer)this.methodToCall.invoke(this.targetObject, this.parameterList);
            } else if (this.returnValue instanceof BooleanValue) {
               ((BooleanValue)this.returnValue).value = (Boolean)this.methodToCall.invoke(this.targetObject, this.parameterList);
            } else if (this.returnValue instanceof StringValue) {
               ((StringValue)this.returnValue).value = this.methodToCall.invoke(this.targetObject, this.parameterList).toString();
            }

            if (this.secondMethod != null) {
               this.secondMethod.invoke(var1, var2);
            }
         } catch (Exception var4) {
            var4.printStackTrace(System.err);
            return null;
         }

         return this.returnValue;
      }
   }

   public boolean equals(int var1, Object var2, String var3) {
      if (this.methodType != var1) {
         return false;
      } else {
         return this.targetObject != var2 ? false : this.methodName.equals(var3);
      }
   }

   public String toString() {
      return this.methodName;
   }

   public static Method resolveMethod(Object var0, String var1, Class[] var2) {
      Method[] var3 = var0.getClass().getMethods();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         if (var3[var4].getName().equals(var1)) {
            Class[] var5 = var3[var4].getParameterTypes();
            if (var5.length == var2.length) {
               boolean var6 = true;

               for(int var7 = 0; var7 < var5.length; ++var7) {
                  if (!var5[var7].isAssignableFrom(var2[var7])) {
                     var6 = false;
                     break;
                  }
               }

               if (var6) {
                  return var3[var4];
               }
            }
         }
      }

      return null;
   }

   public static String[] splitMethodName(String var0) {
      String[] var1 = new String[3];
      String var2 = var0;
      int var3 = var0.indexOf(46);
      int var4 = var0.indexOf(40);
      if (var3 <= 0 || var4 >= 0 && var4 <= var3) {
         var1[0] = null;
      } else {
         var1[0] = var0.substring(0, var3);
         var2 = var0.substring(var3 + 1);
      }

      var3 = var2.indexOf("(");
      if (var3 <= 0) {
         var1[1] = var2;
         var1[2] = null;
      } else {
         var1[1] = var2.substring(0, var3).trim();
         var2 = var2.substring(var3);
         var4 = var2.lastIndexOf(41);
         if (var4 < 0) {
            System.err.println(" : Error! Incorrect method description " + var0);
            return null;
         }

         var1[2] = var2.substring(1, var4).trim();
      }

      return var1;
   }
}
