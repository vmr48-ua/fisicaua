package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;

public class Random extends PostfixMathCommand {
   public Random() {
      super.numberOfParameters = 0;
   }

   public void run(Stack var1) throws ParseException {
      this.checkStack(var1);
      var1.push(new Double(Math.random()));
   }
}
