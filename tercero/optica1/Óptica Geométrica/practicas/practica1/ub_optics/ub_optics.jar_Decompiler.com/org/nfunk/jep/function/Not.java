package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;

public class Not extends PostfixMathCommand {
   public Not() {
      super.numberOfParameters = 1;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      if (var2 instanceof Number) {
         int var3 = ((Number)var2).doubleValue() == 0.0D ? 1 : 0;
         var1.push(new Double((double)var3));
      } else {
         throw new ParseException("Invalid parameter type");
      }
   }
}
