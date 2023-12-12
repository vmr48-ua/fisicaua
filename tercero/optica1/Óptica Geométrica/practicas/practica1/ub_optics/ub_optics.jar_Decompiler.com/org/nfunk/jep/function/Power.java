package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class Power extends PostfixMathCommand {
   public Power() {
      super.numberOfParameters = 2;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      Object var3 = var1.pop();
      var1.push(this.power(var3, var2));
   }

   public Object power(Object var1, Object var2) throws ParseException {
      if (var1 instanceof Number) {
         if (var2 instanceof Number) {
            return this.power((Number)var1, (Number)var2);
         }

         if (var2 instanceof Complex) {
            return this.power((Number)var1, (Complex)var2);
         }
      } else if (var1 instanceof Complex) {
         if (var2 instanceof Number) {
            return this.power((Complex)var1, (Number)var2);
         }

         if (var2 instanceof Complex) {
            return this.power((Complex)var1, (Complex)var2);
         }
      }

      throw new ParseException("Invalid parameter type");
   }

   public Object power(Number var1, Number var2) {
      if (var1.doubleValue() < 0.0D && var2.doubleValue() != (double)var2.intValue()) {
         Complex var3 = new Complex(var1.doubleValue(), 0.0D);
         return var3.power(var2.doubleValue());
      } else {
         return new Double(Math.pow(var1.doubleValue(), var2.doubleValue()));
      }
   }

   public Object power(Complex var1, Complex var2) {
      Complex var3 = var1.power(var2);
      return var3.im() == 0.0D ? new Double(var3.re()) : var3;
   }

   public Object power(Complex var1, Number var2) {
      Complex var3 = var1.power(var2.doubleValue());
      return var3.im() == 0.0D ? new Double(var3.re()) : var3;
   }

   public Object power(Number var1, Complex var2) {
      Complex var3 = new Complex(var1.doubleValue(), 0.0D);
      Complex var4 = var3.power(var2);
      return var4.im() == 0.0D ? new Double(var4.re()) : var4;
   }
}
