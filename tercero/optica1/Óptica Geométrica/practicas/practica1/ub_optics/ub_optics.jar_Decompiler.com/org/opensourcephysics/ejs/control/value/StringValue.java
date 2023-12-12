package org.opensourcephysics.ejs.control.value;

public class StringValue extends Value {
   public String value;

   public StringValue(String var1) {
      this.value = var1;
   }

   public boolean getBoolean() {
      return this.value.equals("true");
   }

   public int getInteger() {
      return (int)Math.round(this.getDouble());
   }

   public double getDouble() {
      try {
         return Double.valueOf(this.value);
      } catch (NumberFormatException var2) {
         return 0.0D;
      }
   }

   public String getString() {
      return this.value;
   }

   public Object getObject() {
      return null;
   }
}
