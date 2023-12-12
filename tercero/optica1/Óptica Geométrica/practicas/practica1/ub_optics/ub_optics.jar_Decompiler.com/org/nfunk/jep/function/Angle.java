package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;

public class Angle extends PostfixMathCommand {
   public Angle() {
      super.numberOfParameters = 2;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      Object var3 = var1.pop();
      if (var3 instanceof Number && var2 instanceof Number) {
         double var4 = ((Number)var3).doubleValue();
         double var6 = ((Number)var2).doubleValue();
         var1.push(new Double(Math.atan2(var4, var6)));
      } else {
         throw new ParseException("Invalid parameter type");
      }
   }
}
