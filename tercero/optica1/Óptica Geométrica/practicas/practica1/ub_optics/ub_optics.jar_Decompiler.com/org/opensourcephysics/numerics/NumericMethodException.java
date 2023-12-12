package org.opensourcephysics.numerics;

public final class NumericMethodException extends RuntimeException {
   public double error_value;
   public int error_code;

   public NumericMethodException() {
   }

   public NumericMethodException(String var1) {
      super(var1);
   }

   public NumericMethodException(String var1, int var2, double var3) {
      super(var1);
      this.error_code = var2;
      this.error_value = var3;
   }

   public String getMessage() {
      return super.getMessage() + "\n error code=" + this.error_code + "  error value=" + this.error_value;
   }
}
