package org.opensourcephysics.ejs.control;

import org.opensourcephysics.ejs.control.value.BooleanValue;
import org.opensourcephysics.ejs.control.value.DoubleValue;
import org.opensourcephysics.ejs.control.value.IntegerValue;
import org.opensourcephysics.ejs.control.value.StringValue;
import org.opensourcephysics.ejs.control.value.Value;
import org.opensourcephysics.numerics.Util;

public class ParsedEjsControl extends EjsControl {
   public ParsedEjsControl(Object var1) {
      super(var1);
   }

   public double getDouble(String var1) {
      Value var2 = this.getValue(var1);
      if (var2 instanceof DoubleValue) {
         return super.getDouble(var1);
      } else if (var2 instanceof IntegerValue) {
         return (double)super.getInt(var1);
      } else {
         String var3 = super.getString(var1);

         try {
            return Double.parseDouble(var3);
         } catch (NumberFormatException var5) {
            return Util.evalMath(var3);
         }
      }
   }

   public Object getObject(String var1) {
      Value var2 = this.getValue(var1);
      if (var2 == null) {
         return null;
      } else if (var2 instanceof IntegerValue) {
         return new Integer(super.getInt(var1));
      } else if (var2 instanceof DoubleValue) {
         return new Double(super.getDouble(var1));
      } else if (var2 instanceof BooleanValue) {
         return new Boolean(super.getBoolean(var1));
      } else {
         return var2 instanceof StringValue ? super.getString(var1) : super.getObject(var1);
      }
   }

   public int getInt(String var1) {
      Value var2 = this.getValue(var1);
      if (var2 instanceof IntegerValue) {
         return super.getInt(var1);
      } else if (var2 instanceof DoubleValue) {
         return (int)super.getDouble(var1);
      } else {
         String var3 = super.getString(var1);

         try {
            return Integer.parseInt(var3);
         } catch (NumberFormatException var5) {
            return (int)Util.evalMath(var3);
         }
      }
   }
}
