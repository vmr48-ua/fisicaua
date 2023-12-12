package org.nfunk.jep.function;

import java.util.Stack;
import java.util.Vector;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.type.Complex;

public class Divide extends PostfixMathCommand {
   public Divide() {
      super.numberOfParameters = 2;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      Object var2 = var1.pop();
      Object var3 = var1.pop();
      var1.push(this.div(var3, var2));
   }

   public Object div(Object var1, Object var2) throws ParseException {
      if (var1 instanceof Number) {
         if (var2 instanceof Number) {
            return this.div((Number)var1, (Number)var2);
         }

         if (var2 instanceof Complex) {
            return this.div((Number)var1, (Complex)var2);
         }

         if (var2 instanceof Vector) {
            return this.div((Number)var1, (Vector)var2);
         }
      } else if (var1 instanceof Complex) {
         if (var2 instanceof Number) {
            return this.div((Complex)var1, (Number)var2);
         }

         if (var2 instanceof Complex) {
            return this.div((Complex)var1, (Complex)var2);
         }

         if (var2 instanceof Vector) {
            return this.div((Complex)var1, (Vector)var2);
         }
      } else if (var1 instanceof Vector) {
         if (var2 instanceof Number) {
            return this.div((Vector)var1, (Number)var2);
         }

         if (var2 instanceof Complex) {
            return this.div((Vector)var1, (Complex)var2);
         }
      }

      throw new ParseException("Invalid parameter type");
   }

   public Double div(Number var1, Number var2) {
      return new Double(var1.doubleValue() / var2.doubleValue());
   }

   public Complex div(Complex var1, Complex var2) {
      return var1.div(var2);
   }

   public Complex div(Number var1, Complex var2) {
      Complex var3 = new Complex(var1.doubleValue(), 0.0D);
      return var3.div(var2);
   }

   public Complex div(Complex var1, Number var2) {
      return new Complex(var1.re() / var2.doubleValue(), var1.im() / var2.doubleValue());
   }

   public Vector div(Vector var1, Number var2) {
      Vector var3 = new Vector();

      for(int var4 = 0; var4 < var1.size(); ++var4) {
         var3.addElement(this.div((Number)var1.elementAt(var4), var2));
      }

      return var3;
   }

   public Vector div(Number var1, Vector var2) {
      Vector var3 = new Vector();

      for(int var4 = 0; var4 < var2.size(); ++var4) {
         var3.addElement(this.div(var1, (Number)var2.elementAt(var4)));
      }

      return var3;
   }

   public Vector div(Vector var1, Complex var2) {
      Vector var3 = new Vector();

      for(int var4 = 0; var4 < var1.size(); ++var4) {
         var3.addElement(this.div((Number)var1.elementAt(var4), var2));
      }

      return var3;
   }

   public Vector div(Complex var1, Vector var2) {
      Vector var3 = new Vector();

      for(int var4 = 0; var4 < var2.size(); ++var4) {
         var3.addElement(this.div(var1, (Number)var2.elementAt(var4)));
      }

      return var3;
   }
}
