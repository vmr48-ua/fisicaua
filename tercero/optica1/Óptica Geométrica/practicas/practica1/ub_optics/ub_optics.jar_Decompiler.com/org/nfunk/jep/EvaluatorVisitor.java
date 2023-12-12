package org.nfunk.jep;

import java.util.Stack;
import java.util.Vector;
import org.nfunk.jep.function.PostfixMathCommandI;

public class EvaluatorVisitor implements ParserVisitor {
   private Stack stack = new Stack();
   private Vector errorList = null;
   private SymbolTable symTab = null;
   private boolean errorFlag;
   private static final boolean debug = false;

   private void addToErrorList(String var1) {
      if (this.errorList != null) {
         this.errorList.addElement(var1);
      }

   }

   public Object getValue(Node var1, Vector var2, SymbolTable var3) throws Exception {
      if (var1 == null) {
         throw new IllegalArgumentException("topNode parameter is null");
      } else {
         this.errorList = var2;
         this.symTab = var3;
         this.errorFlag = false;
         var1.jjtAccept(this, (Object)null);
         if (!this.errorFlag && this.stack.size() == 1) {
            return this.stack.pop();
         } else {
            throw new Exception("EvaluatorVisitor.getValue(): Error during evaluation");
         }
      }
   }

   public Object visit(SimpleNode var1, Object var2) {
      return var2;
   }

   public Object visit(ASTStart var1, Object var2) {
      return var2;
   }

   public Object visit(ASTFunNode var1, Object var2) {
      if (var1 == null) {
         return null;
      } else {
         var2 = var1.childrenAccept(this, var2);
         PostfixMathCommandI var3 = var1.getPFMC();
         if (var3 == null) {
            this.addToErrorList("No function class associated with " + var1.getName());
            return var2;
         } else {
            if (var3.getNumberOfParameters() == -1) {
               var3.setCurNumberOfParameters(var1.jjtGetNumChildren());
            }

            try {
               var3.run(this.stack);
            } catch (ParseException var5) {
               this.addToErrorList(var5.getMessage());
               this.errorFlag = true;
            }

            return var2;
         }
      }
   }

   public Object visit(ASTVarNode var1, Object var2) {
      String var3 = "Could not evaluate " + var1.getName() + ": ";
      if (this.symTab == null) {
         var3 = var3 + "the symbol table is null";
         this.addToErrorList(var3);
         return var2;
      } else {
         Object var4 = this.symTab.get(var1.getName());
         if (var4 == null) {
            var3 = var3 + "the variable was not found in the symbol table";
            this.addToErrorList(var3);
         } else {
            this.stack.push(var4);
         }

         return var2;
      }
   }

   public Object visit(ASTConstant var1, Object var2) {
      this.stack.push(var1.getValue());
      return var2;
   }
}
