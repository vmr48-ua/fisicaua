package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class ArcSineH extends PostfixMathCommand {
   public ArcSineH() {
      super.numberOfParameters = 1;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      var1.push(this.asinh(var2));
   }

   public Object asinh(Object var1) throws ParseException {
      if (var1 instanceof Number) {
         Complex var2 = new Complex(((Number)var1).doubleValue(), 0.0D);
         return var2.asinh();
      } else if (var1 instanceof Complex) {
         return ((Complex)var1).asinh();
      } else {
         throw new ParseException("Invalid parameter type");
      }
   }
}
