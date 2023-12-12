package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class Add extends PostfixMathCommand {
   public Add() {
      super.numberOfParameters = -1;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();

      for(int var4 = 1; var4 < super.curNumberOfParameters; ++var4) {
         Object var3 = var1.pop();
         var2 = this.add(var3, var2);
      }

      var1.push(var2);
   }

   public Object add(Object var1, Object var2) throws ParseException {
      if (var1 instanceof Number) {
         if (var2 instanceof Number) {
            return this.add((Number)var1, (Number)var2);
         }

         if (var2 instanceof Complex) {
            return this.add((Complex)var2, (Number)var1);
         }
      } else if (var1 instanceof Complex) {
         if (var2 instanceof Number) {
            return this.add((Complex)var1, (Number)var2);
         }

         if (var2 instanceof Complex) {
            return this.add((Complex)var1, (Complex)var2);
         }
      } else if (var1 instanceof String && var2 instanceof String) {
         return (String)var1 + (String)var2;
      }

      throw new ParseException("Invalid parameter type");
   }

   public Double add(Number var1, Number var2) {
      return new Double(var1.doubleValue() + var2.doubleValue());
   }

   public Complex add(Complex var1, Complex var2) {
      return new Complex(var1.re() + var2.re(), var1.im() + var2.im());
   }

   public Complex add(Complex var1, Number var2) {
      return new Complex(var1.re() + var2.doubleValue(), var1.im());
   }
}
