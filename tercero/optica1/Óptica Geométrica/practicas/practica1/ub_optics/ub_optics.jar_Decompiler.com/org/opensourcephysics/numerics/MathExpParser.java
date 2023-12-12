package org.opensourcephysics.numerics;

public abstract class MathExpParser implements Function, MultiVarFunction {
   public static final int NO_ERROR = 0;
   public static final int SYNTAX_ERROR = 1;

   public abstract void setFunction(String var1) throws ParserException;

   public abstract void setFunction(String var1, String[] var2) throws ParserException;

   public abstract String getFunction();

   public static MathExpParser createParser() {
      return new SuryonoParser(0);
   }

   // $FF: synthetic method
   public abstract double evaluate(double[] var1);

   // $FF: synthetic method
   public abstract double evaluate(double var1);
}
