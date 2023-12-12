package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class Comparative extends PostfixMathCommand {
   int id;
   double tolerance;

   public Comparative(int var1) {
      this.id = var1;
      super.numberOfParameters = 2;
      this.tolerance = 1.0E-6D;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      Object var3 = var1.pop();
      if (var3 instanceof Number && var2 instanceof Number) {
         double var9 = ((Number)var3).doubleValue();
         double var6 = ((Number)var2).doubleValue();
         int var8;
         switch(this.id) {
         case 0:
            var8 = var9 < var6 ? 1 : 0;
            break;
         case 1:
            var8 = var9 > var6 ? 1 : 0;
            break;
         case 2:
            var8 = var9 <= var6 ? 1 : 0;
            break;
         case 3:
            var8 = var9 >= var6 ? 1 : 0;
            break;
         case 4:
            var8 = var9 != var6 ? 1 : 0;
            break;
         case 5:
            var8 = var9 == var6 ? 1 : 0;
            break;
         default:
            throw new ParseException("Unknown relational operator");
         }

         var1.push(new Double((double)var8));
      } else {
         int var4;
         if (var3 instanceof Complex && var2 instanceof Complex) {
            switch(this.id) {
            case 4:
               var4 = ((Complex)var3).equals((Complex)var2, this.tolerance) ? 0 : 1;
               break;
            case 5:
               var4 = ((Complex)var3).equals((Complex)var2, this.tolerance) ? 1 : 0;
               break;
            default:
               throw new ParseException("Relational operator type error");
            }

            var1.push(new Double((double)var4));
         } else {
            if (!(var3 instanceof String) || !(var2 instanceof String)) {
               throw new ParseException("Invalid parameter type");
            }

            switch(this.id) {
            case 4:
               var4 = ((String)var3).equals((String)var2) ? 0 : 1;
               break;
            case 5:
               var4 = ((String)var3).equals((String)var2) ? 1 : 0;
               break;
            default:
               throw new ParseException("Relational operator type error");
            }

            var1.push(new Double((double)var4));
         }
      }

   }
}
