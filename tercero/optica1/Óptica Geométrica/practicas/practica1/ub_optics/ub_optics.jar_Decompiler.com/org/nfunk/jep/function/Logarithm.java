package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class Logarithm extends PostfixMathCommand {
   public Logarithm() {
      super.numberOfParameters = 1;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      var1.push(this.log(var2));
   }

   public Object log(Object var1) throws ParseException {
      Complex var2;
      if (var1 instanceof Number) {
         var2 = new Complex(((Number)var1).doubleValue());
         Complex var3 = new Complex(Math.log(10.0D), 0.0D);
         return var2.log().div(var3);
      } else if (var1 instanceof Complex) {
         var2 = new Complex(Math.log(10.0D), 0.0D);
         return ((Complex)var1).log().div(var2);
      } else {
         throw new ParseException("Invalid parameter type");
      }
   }
}
