package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class TanH extends PostfixMathCommand {
   public TanH() {
      super.numberOfParameters = 1;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      var1.push(this.tanh(var2));
   }

   public Object tanh(Object var1) throws ParseException {
      if (var1 instanceof Number) {
         double var2 = ((Number)var1).doubleValue();
         return new Double((Math.exp(var2) - Math.exp(-var2)) / (Math.pow(2.718281828459045D, var2) + Math.pow(2.718281828459045D, -var2)));
      } else if (var1 instanceof Complex) {
         return ((Complex)var1).tanh();
      } else {
         throw new ParseException("Invalid parameter type");
      }
   }
}
