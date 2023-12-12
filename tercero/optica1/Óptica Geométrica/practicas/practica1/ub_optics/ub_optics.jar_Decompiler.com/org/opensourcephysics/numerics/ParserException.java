package org.opensourcephysics.numerics;

public final class ParserException extends Exception {
   private int errorcode;

   public ParserException(int var1) {
      this.errorcode = var1;
   }

   public ParserException(String var1) {
      super(var1);
      this.errorcode = 1;
   }

   public int getErrorCode() {
      return this.errorcode;
   }
}
