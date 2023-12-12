package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class NaturalLogarithm extends PostfixMathCommand {
   public NaturalLogarithm() {
      super.numberOfParameters = 1;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      var1.push(this.ln(var2));
   }

   public Object ln(Object var1) throws ParseException {
      if (var1 instanceof Number) {
         Complex var2 = new Complex(((Number)var1).doubleValue());
         return var2.log();
      } else if (var1 instanceof Complex) {
         return ((Complex)var1).log();
      } else {
         throw new ParseException("Invalid parameter type");
      }
   }
}
