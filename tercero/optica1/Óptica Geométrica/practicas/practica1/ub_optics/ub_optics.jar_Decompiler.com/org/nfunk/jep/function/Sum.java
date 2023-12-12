package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;

public class Sum extends PostfixMathCommand {
   public Sum() {
      super.numberOfParameters = -1;
   }

   public void run(Stack var1) throws ParseException {
      if (null == var1) {
         throw new ParseException("Stack argument null");
      } else {
         Object var2 = null;
         double var3 = 0.0D;

         for(int var5 = 0; var5 < super.curNumberOfParameters; ++var5) {
            var2 = var1.pop();
            if (!(var2 instanceof Number)) {
               throw new ParseException("Invalid parameter type");
            }

            var3 += ((Number)var2).doubleValue();
         }

         var1.push(new Double(var3));
      }
   }
}
