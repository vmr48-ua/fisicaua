package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class ArcCosineH extends PostfixMathCommand {
   public ArcCosineH() {
      super.numberOfParameters = 1;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      var1.push(this.acosh(var2));
   }

   public Object acosh(Object var1) throws ParseException {
      if (var1 instanceof Number) {
         Complex var2 = new Complex(((Number)var1).doubleValue(), 0.0D);
         return var2.acosh();
      } else if (var1 instanceof Complex) {
         return ((Complex)var1).acosh();
      } else {
         throw new ParseException("Invalid parameter type");
      }
   }
}
