package org.opensourcephysics.ejs.control.value;

public class IntegerValue extends Value {
   public int value;

   public IntegerValue(int var1) {
      this.value = var1;
   }

   public boolean getBoolean() {
      return this.value != 0;
   }

   public int getInteger() {
      return this.value;
   }

   public double getDouble() {
      return (double)this.value;
   }

   public String getString() {
      return String.valueOf(this.value);
   }

   public Object getObject() {
      return null;
   }
}
