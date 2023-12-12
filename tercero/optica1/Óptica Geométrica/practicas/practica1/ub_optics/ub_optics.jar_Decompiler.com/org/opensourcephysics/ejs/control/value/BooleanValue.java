package org.opensourcephysics.ejs.control.value;

public class BooleanValue extends Value {
   public boolean value;

   public BooleanValue(boolean var1) {
      this.value = var1;
   }

   public boolean getBoolean() {
      return this.value;
   }

   public int getInteger() {
      return this.value ? 1 : 0;
   }

   public double getDouble() {
      return this.value ? 1.0D : 0.0D;
   }

   public String getString() {
      return this.value ? "true" : "false";
   }

   public Object getObject() {
      return null;
   }
}
