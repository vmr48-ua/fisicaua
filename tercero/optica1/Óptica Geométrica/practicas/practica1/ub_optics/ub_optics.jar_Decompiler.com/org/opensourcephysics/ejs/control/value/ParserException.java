package org.opensourcephysics.ejs.control.value;

public final class ParserException extends Exception {
   public static final int SYNTAX_ERROR = -1;
   private int errorcode;

   public ParserException(int var1) {
      this.errorcode = var1;
   }

   public ParserException(String var1) {
      super(var1);
      this.errorcode = -1;
   }

   public int getErrorCode() {
      return this.errorcode;
   }
}
