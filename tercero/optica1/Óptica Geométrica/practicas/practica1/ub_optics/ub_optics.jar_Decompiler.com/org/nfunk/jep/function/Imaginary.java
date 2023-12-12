package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class Imaginary extends PostfixMathCommand {
   public Imaginary() {
      super.numberOfParameters = 1;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      var1.push(this.im(var2));
   }

   public Number im(Object var1) throws ParseException {
      if (var1 instanceof Number) {
         return new Double(0.0D);
      } else if (var1 instanceof Complex) {
         return new Double(((Complex)var1).im());
      } else {
         throw new ParseException("Invalid parameter type");
      }
   }
}
