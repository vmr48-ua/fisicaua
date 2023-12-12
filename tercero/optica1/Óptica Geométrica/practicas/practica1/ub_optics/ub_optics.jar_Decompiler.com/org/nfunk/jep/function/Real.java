package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class Real extends PostfixMathCommand {
   public Real() {
      super.numberOfParameters = 1;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      var1.push(this.re(var2));
   }

   public Number re(Object var1) throws ParseException {
      if (var1 instanceof Number) {
         return (Number)var1;
      } else if (var1 instanceof Complex) {
         return new Double(((Complex)var1).re());
      } else {
         throw new ParseException("Invalid parameter type");
      }
   }
}
