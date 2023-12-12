package org.opensourcephysics.numerics;

public final class ParsedFunction implements Function {
   private final String fStr;
   private final Function function;

   public ParsedFunction(String var1) throws ParserException {
      this(var1, "x");
   }

   public ParsedFunction(String var1, String var2) throws ParserException {
      this.fStr = var1;
      SuryonoParser var3 = null;
      var3 = new SuryonoParser(this.fStr, var2);
      this.function = var3;
   }

   public double evaluate(double var1) {
      return this.function.evaluate(var1);
   }

   public String toString() {
      return "f(x) = " + this.fStr;
   }
}
