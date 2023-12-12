package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class SquareRoot extends PostfixMathCommand {
   public SquareRoot() {
      super.numberOfParameters = 1;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      var1.push(this.sqrt(var2));
   }

   public Object sqrt(Object var1) throws ParseException {
      if (var1 instanceof Number) {
         double var2 = ((Number)var1).doubleValue();
         return var2 < 0.0D ? (new Complex(var2)).sqrt() : new Double(Math.sqrt(var2));
      } else if (var1 instanceof Complex) {
         return ((Complex)var1).sqrt();
      } else {
         throw new ParseException("Invalid parameter type");
      }
   }
}
