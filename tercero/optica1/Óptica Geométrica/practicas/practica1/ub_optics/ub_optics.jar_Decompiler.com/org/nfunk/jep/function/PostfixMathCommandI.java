package org.nfunk.jep.function;

import java.util.Stack;
import org.nfunk.jep.ParseException;

public interface PostfixMathCommandI {
   void run(Stack var1) throws ParseException;

   int getNumberOfParameters();

   void setCurNumberOfParameters(int var1);
}
