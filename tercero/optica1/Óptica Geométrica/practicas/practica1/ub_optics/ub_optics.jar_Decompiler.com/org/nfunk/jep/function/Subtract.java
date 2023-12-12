package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class Subtract extends PostfixMathCommand {
   public Subtract() {
      super.numberOfParameters = 2;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      Object var3 = var1.pop();
      var1.push(this.sub(var3, var2));
   }

   public Object sub(Object var1, Object var2) throws ParseException {
      if (var1 instanceof Number) {
         if (var2 instanceof Number) {
            return this.sub((Number)var1, (Number)var2);
         }

         if (var2 instanceof Complex) {
            return this.sub((Number)var1, (Complex)var2);
         }
      } else if (var1 instanceof Complex) {
         if (var2 instanceof Number) {
            return this.sub((Complex)var1, (Number)var2);
         }

         if (var2 instanceof Complex) {
            return this.sub((Complex)var1, (Complex)var2);
         }
      }

      throw new ParseException("Invalid parameter type");
   }

   public Double sub(Number var1, Number var2) {
      return new Double(var1.doubleValue() - var2.doubleValue());
   }

   public Complex sub(Complex var1, Complex var2) {
      return new Complex(var1.re() - var2.re(), var1.im() - var2.im());
   }

   public Complex sub(Complex var1, Number var2) {
      return new Complex(var1.re() - var2.doubleValue(), var1.im());
   }

   public Complex sub(Number var1, Complex var2) {
      return new Complex(var1.doubleValue() - var2.re(), -var2.im());
   }
}
