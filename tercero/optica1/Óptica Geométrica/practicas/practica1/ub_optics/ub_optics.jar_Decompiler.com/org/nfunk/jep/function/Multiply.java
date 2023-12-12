package org.nfunk.jep.function;

import java.util.Stack;
import java.util.Vector;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class Multiply extends PostfixMathCommand {
   public Multiply() {
      super.numberOfParameters = -1;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();

      for(int var4 = 1; var4 < super.curNumberOfParameters; ++var4) {
         Object var3 = var1.pop();
         var2 = this.mul(var2, var3);
      }

      var1.push(var2);
   }

   public Object mul(Object var1, Object var2) throws ParseException {
      if (var1 instanceof Number) {
         if (var2 instanceof Number) {
            return this.mul((Number)var1, (Number)var2);
         }

         if (var2 instanceof Complex) {
            return this.mul((Complex)var2, (Number)var1);
         }

         if (var2 instanceof Vector) {
            return this.mul((Vector)var2, (Number)var1);
         }
      } else if (var1 instanceof Complex) {
         if (var2 instanceof Number) {
            return this.mul((Complex)var1, (Number)var2);
         }

         if (var2 instanceof Complex) {
            return this.mul((Complex)var1, (Complex)var2);
         }

         if (var2 instanceof Vector) {
            return this.mul((Vector)var2, (Complex)var1);
         }
      } else if (var1 instanceof Vector) {
         if (var2 instanceof Number) {
            return this.mul((Vector)var1, (Number)var2);
         }

         if (var2 instanceof Complex) {
            return this.mul((Vector)var1, (Complex)var2);
         }
      }

      throw new ParseException("Invalid parameter type");
   }

   public Double mul(Number var1, Number var2) {
      return new Double(var1.doubleValue() * var2.doubleValue());
   }

   public Complex mul(Complex var1, Complex var2) {
      return var1.mul(var2);
   }

   public Complex mul(Complex var1, Number var2) {
      return var1.mul(var2.doubleValue());
   }

   public Vector mul(Vector var1, Number var2) {
      Vector var3 = new Vector();

      for(int var4 = 0; var4 < var1.size(); ++var4) {
         var3.addElement(this.mul((Number)var1.elementAt(var4), var2));
      }

      return var3;
   }

   public Vector mul(Vector var1, Complex var2) {
      Vector var3 = new Vector();

      for(int var4 = 0; var4 < var1.size(); ++var4) {
         var3.addElement(this.mul(var2, (Number)var1.elementAt(var4)));
      }

      return var3;
   }
}
