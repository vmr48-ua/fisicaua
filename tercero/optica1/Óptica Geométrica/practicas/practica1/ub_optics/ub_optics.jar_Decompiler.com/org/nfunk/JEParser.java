package org.nfunk;

import java.util.Stack;
import org.nfunk.jep.JEP;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;
import org.nfunk.jep.function.PostfixMathCommandI;
import org.nfunk.jep.type.Complex;
import org.opensourcephysics.numerics.MathExpParser;
import org.opensourcephysics.numerics.ParserException;

public class JEParser extends MathExpParser {
   public static final boolean MAKE_COMPLEX = true;
   public double tol;
   JEP jep;
   String funcStr;
   String[] variables;
   boolean calcLimitValue;
   private static JEParser parser;

   public JEParser(String var1, String var2) throws ParserException {
      this(var1, var2, false);
   }

   public JEParser(String var1, String var2, String var3) throws ParserException {
      this(var1, var2, var3, false);
   }

   public JEParser(String var1, String var2, boolean var3) throws ParserException {
      this.tol = 1.0E-9D;
      this.jep = new JEP();
      this.funcStr = null;
      this.calcLimitValue = true;
      this.funcStr = var1;
      this.variables = new String[1];
      this.variables[0] = var2;
      this.jep.addStandardFunctions();
      this.jep.addStandardConstants();
      this.jep.addFunction("sqrt", new JEParser.SquareRoot());
      this.jep.addFunction("step", new JEParser.Step());
      if (var3) {
         this.jep.addComplex();
      }

      this.jep.addVariable(var2, 0.0D);
      this.jep.parseExpression(var1);
      if (this.jep.hasError()) {
         String var4 = "Error in function string: " + this.funcStr;
         var4 = var4 + '\n' + "Error: " + this.jep.getErrorInfo();
         throw new ParserException(var4);
      }
   }

   public JEParser(String var1, String var2, String var3, boolean var4) throws ParserException {
      this.tol = 1.0E-9D;
      this.jep = new JEP();
      this.funcStr = null;
      this.calcLimitValue = true;
      this.funcStr = var1;
      this.variables = new String[2];
      this.variables[0] = var2;
      this.variables[1] = var3;
      this.jep.addStandardFunctions();
      this.jep.addStandardConstants();
      this.jep.addFunction("sqrt", new JEParser.SquareRoot());
      this.jep.addFunction("step", new JEParser.Step());
      if (var4) {
         this.jep.addComplex();
      }

      this.jep.addVariable(var2, 0.0D);
      this.jep.addVariable(var3, 0.0D);
      this.jep.parseExpression(var1);
      if (this.jep.hasError()) {
         String var5 = "Error in function string: " + this.funcStr;
         var5 = var5 + '\n' + "Error: " + this.jep.getErrorInfo();
         throw new ParserException(var5);
      }
   }

   public JEParser(String var1, String[] var2, boolean var3) throws ParserException {
      this.tol = 1.0E-9D;
      this.jep = new JEP();
      this.funcStr = null;
      this.calcLimitValue = true;
      this.funcStr = var1;
      this.variables = new String[var2.length];
      this.jep.addStandardFunctions();
      this.jep.addStandardConstants();
      this.jep.addFunction("sqrt", new JEParser.SquareRoot());
      this.jep.addFunction("step", new JEParser.Step());
      if (var3) {
         this.jep.addComplex();
      }

      for(int var4 = 0; var4 < var2.length; ++var4) {
         this.variables[var4] = var2[var4];
         this.jep.addVariable(var2[var4], 0.0D);
      }

      this.jep.parseExpression(var1);
      if (this.jep.hasError()) {
         String var5 = "Error in function string: " + this.funcStr;
         var5 = var5 + '\n' + "Error: " + this.jep.getErrorInfo();
         throw new ParserException(var5);
      }
   }

   public JEParser(String var1, String[] var2) throws ParserException {
      this.tol = 1.0E-9D;
      this.jep = new JEP();
      this.funcStr = null;
      this.calcLimitValue = true;
      this.funcStr = var1;
      this.jep.addStandardFunctions();
      this.jep.addStandardConstants();
      this.jep.addFunction("sqrt", new JEParser.SquareRoot());
      this.jep.addFunction("step", new JEParser.Step());

      for(int var3 = 0; var3 < var2.length; ++var3) {
         this.jep.addVariable(var2[var3], 0.0D);
      }

      this.jep.parseExpression(var1);
      if (this.jep.hasError()) {
         String var4 = "Error in function string: " + this.funcStr;
         var4 = var4 + '\n' + "Error: " + this.jep.getErrorInfo();
         throw new ParserException(var4);
      }
   }

   public void setFunction(String var1) throws ParserException {
      this.funcStr = var1;
      this.jep.parseExpression(this.funcStr);
      if (this.jep.hasError()) {
         String var2 = "Error in function string: " + this.funcStr;
         var2 = var2 + '\n' + "Error: " + this.jep.getErrorInfo();
         throw new ParserException(var2);
      }
   }

   public void setToZero() {
      this.funcStr = "0";
      this.jep.parseExpression(this.funcStr);
   }

   public void setFunction(String var1, String[] var2) throws ParserException {
      this.funcStr = var1;
      this.variables = new String[var2.length];
      this.jep.initSymTab();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         this.variables[var3] = var2[var3];
         this.jep.addVariable(this.variables[var3], 0.0D);
      }

      this.jep.parseExpression(this.funcStr);
      if (this.jep.hasError()) {
         String var4 = "Error in function string: " + this.funcStr;
         var4 = var4 + '\n' + "Error: " + this.jep.getErrorInfo();
         throw new ParserException(var4);
      }
   }

   public String getFunction() {
      return this.funcStr;
   }

   public boolean hasError() {
      return this.jep.hasError();
   }

   public double evaluate(double var1) {
      this.jep.addVariable(this.variables[0], var1);
      double var3 = this.jep.getValue();
      if (Double.isNaN(var3) && this.calcLimitValue) {
         double var5 = var1 * 1.0E-5D + this.tol;
         this.jep.addVariable(this.variables[0], var1 + var5);
         double var7 = this.jep.getValue();
         this.jep.addVariable(this.variables[0], var1 - var5);
         double var9 = this.jep.getValue();
         return (var7 + var9) / 2.0D;
      } else {
         return var3;
      }
   }

   public double evaluate(double var1, double var3) {
      if (this.variables.length < 1) {
         System.out.println("JEParser Error: Only one variable has been defined.");
         return 0.0D;
      } else {
         this.jep.addVariable(this.variables[0], var1);
         this.jep.addVariable(this.variables[1], var3);
         return this.jep.getValue();
      }
   }

   public double evaluate(double[] var1) {
      if (this.variables.length < var1.length) {
         System.out.println("JEParser Error: incorrect number of variables.");
         return 0.0D;
      } else {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            this.jep.addVariable(this.variables[var2], var1[var2]);
         }

         return this.jep.getValue();
      }
   }

   public Complex evaluateComplex(double var1) {
      this.jep.addVariable(this.variables[0], var1);
      return this.jep.getComplexValue();
   }

   public Complex evaluateComplex(double var1, double var3) {
      this.jep.addVariable(this.variables[0], var1);
      this.jep.addVariable(this.variables[1], var3);
      return this.jep.getComplexValue();
   }

   public Complex evaluateComplex(double var1, double var3, double var5) {
      this.jep.addVariable(this.variables[0], var1);
      this.jep.addVariable(this.variables[1], var3);
      this.jep.addVariable(this.variables[2], var5);
      return this.jep.getComplexValue();
   }

   public static synchronized Complex evalMath(String var0) {
      try {
         JEParser var1 = getJEParser();
         var1.setFunction(var0);
         return var1.evaluateComplex(0.0D);
      } catch (ParserException var2) {
         return new Complex(Double.NaN, Double.NaN);
      }
   }

   private static synchronized JEParser getJEParser() {
      if (parser == null) {
         try {
            parser = new JEParser("0", "", true);
         } catch (ParserException var1) {
         }
      }

      return parser;
   }

   public class Step extends PostfixMathCommand implements PostfixMathCommandI {
      public Step() {
         super.numberOfParameters = 1;
      }

      public void run(Stack var1) throws ParseException {
         this.checkStack(var1);
         Object var2 = var1.pop();
         if (var2 instanceof Double) {
            if ((Double)var2 >= 0.0D) {
               var1.push(new Double(1.0D));
            } else {
               var1.push(new Double(0.0D));
            }

         } else {
            throw new ParseException("Invalid parameter type");
         }
      }
   }

   public class SquareRoot extends PostfixMathCommand implements PostfixMathCommandI {
      public SquareRoot() {
         super.numberOfParameters = 1;
      }

      public void run(Stack var1) throws ParseException {
         this.checkStack(var1);
         Object var2 = var1.pop();
         if (var2 instanceof Double) {
            double var3 = (Double)var2;
            if (var3 >= 0.0D) {
               var1.push(new Double(Math.sqrt(var3)));
            } else {
               var1.push(new Complex(0.0D, Math.sqrt(-var3)));
            }
         } else {
            if (!(var2 instanceof Complex)) {
               throw new ParseException("Invalid parameter type");
            }

            var1.push(((Complex)var2).sqrt());
         }

      }
   }
}
