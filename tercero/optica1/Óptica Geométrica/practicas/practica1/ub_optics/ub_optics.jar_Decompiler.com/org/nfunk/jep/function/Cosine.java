package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class Cosine extends PostfixMathCommand {
   public Cosine() {
      super.numberOfParameters = 1;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      var1.push(this.cos(var2));
   }

   public Object cos(Object var1) throws ParseException {
      if (var1 instanceof Number) {
         return new Double(Math.cos(((Number)var1).doubleValue()));
      } else if (var1 instanceof Complex) {
         return ((Complex)var1).cos();
      } else {
         throw new ParseException("Invalid parameter type");
      }
   }
}
