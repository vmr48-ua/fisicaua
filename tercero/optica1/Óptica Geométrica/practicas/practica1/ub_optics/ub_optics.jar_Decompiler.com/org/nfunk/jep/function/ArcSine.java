package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class ArcSine extends PostfixMathCommand {
   public ArcSine() {
      super.numberOfParameters = 1;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      var1.push(this.asin(var2));
   }

   public Object asin(Object var1) throws ParseException {
      if (var1 instanceof Number) {
         return new Double(Math.asin(((Number)var1).doubleValue()));
      } else if (var1 instanceof Complex) {
         return ((Complex)var1).asin();
      } else {
         throw new ParseException("Invalid parameter type");
      }
   }
}
