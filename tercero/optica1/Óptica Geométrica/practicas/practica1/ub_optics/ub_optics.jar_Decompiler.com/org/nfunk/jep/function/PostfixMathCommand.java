package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;

public class PostfixMathCommand implements PostfixMathCommandI {
   protected int numberOfParameters = 0;
   protected int curNumberOfParameters = 0;

   protected void checkStack(Stack var1) throws ParseException {
      if (null == var1) {
         throw new ParseException("Stack argument null");
      }
   }

   public int getNumberOfParameters() {
      return this.numberOfParameters;
   }

   public void setCurNumberOfParameters(int var1) {
      this.curNumberOfParameters = var1;
   }

   public void run(Stack var1) throws ParseException {
      throw new ParseException("run() method of PostfixMathCommand called");
   }
}
