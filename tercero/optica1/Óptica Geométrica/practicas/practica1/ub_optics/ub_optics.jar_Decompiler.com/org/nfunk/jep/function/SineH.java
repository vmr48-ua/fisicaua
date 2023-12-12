package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class SineH extends PostfixMathCommand {
   public SineH() {
      super.numberOfParameters = 1;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      var1.push(this.sinh(var2));
   }

   public Object sinh(Object var1) throws ParseException {
      if (var1 instanceof Number) {
         double var2 = ((Number)var1).doubleValue();
         return new Double((Math.exp(var2) - Math.exp(-var2)) / 2.0D);
      } else if (var1 instanceof Complex) {
         return ((Complex)var1).sinh();
      } else {
         throw new ParseException("Invalid parameter type");
      }
   }
}
