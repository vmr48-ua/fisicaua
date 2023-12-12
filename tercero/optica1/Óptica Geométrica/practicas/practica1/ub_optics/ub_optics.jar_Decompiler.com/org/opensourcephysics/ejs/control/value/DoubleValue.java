package org.opensourcephysics.ejs.control.value;

public class DoubleValue extends Value {
   public double value;

   public DoubleValue(double var1) {
      this.value = var1;
   }

   public boolean getBoolean() {
      return this.value != 0.0D;
   }

   public int getInteger() {
      return (int)Math.round(this.value);
   }

   public double getDouble() {
      return this.value;
   }

   public String getString() {
      return String.valueOf(this.value);
   }

   public Object getObject() {
      return null;
   }
}
