package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;

public class Modulus extends PostfixMathCommand {
   public Modulus() {
      super.numberOfParameters = 2;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      Object var3 = var1.pop();
      if (var3 instanceof Number && var2 instanceof Number) {
         double var4 = ((Number)var2).doubleValue();
         double var6 = ((Number)var3).doubleValue();
         double var8 = var6 % var4;
         var1.push(new Double(var8));
      } else {
         throw new ParseException("Invalid parameter type");
      }
   }
}
