package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;

public class Logical extends PostfixMathCommand {
   int id;

   public Logical(int var1) {
      this.id = var1;
      super.numberOfParameters = 2;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      Object var3 = var1.pop();
      if (var3 instanceof Number && var2 instanceof Number) {
         double var4 = ((Number)var3).doubleValue();
         double var6 = ((Number)var2).doubleValue();
         int var8;
         switch(this.id) {
         case 0:
            var8 = var4 != 0.0D && var6 != 0.0D ? 1 : 0;
            break;
         case 1:
            var8 = var4 == 0.0D && var6 == 0.0D ? 0 : 1;
            break;
         default:
            var8 = 0;
         }

         var1.push(new Double((double)var8));
      } else {
         throw new ParseException("Invalid parameter type");
      }
   }
}
