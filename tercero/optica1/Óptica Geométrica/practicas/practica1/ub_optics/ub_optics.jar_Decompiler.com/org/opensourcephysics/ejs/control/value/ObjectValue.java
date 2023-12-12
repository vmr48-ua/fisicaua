package org.opensourcephysics.ejs.control.value;

public class ObjectValue extends Value {
   public Object value;

   public ObjectValue(Object var1) {
      this.value = var1;
   }

   public boolean getBoolean() {
      return this.value.toString().equals("true");
   }

   public int getInteger() {
      return (int)Math.round(this.getDouble());
   }

   public double getDouble() {
      try {
         return Double.valueOf(this.value.toString());
      } catch (NumberFormatException var2) {
         return 0.0D;
      }
   }

   public String getString() {
      return this.value.toString();
   }

   public Object getObject() {
      return this.value;
   }
}
