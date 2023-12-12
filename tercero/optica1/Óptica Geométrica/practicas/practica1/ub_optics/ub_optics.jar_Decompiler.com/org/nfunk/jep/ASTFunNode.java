package org.nfunk.jep;

import org.nfunk.jep.function.PostfixMathCommandI;

public class ASTFunNode extends SimpleNode {
   private PostfixMathCommandI pfmc;
   private String name;

   public ASTFunNode(int var1) {
      super(var1);
   }

   public ASTFunNode(Parser var1, int var2) {
      super(var1, var2);
   }

   public Object jjtAccept(ParserVisitor var1, Object var2) {
      return var1.visit(this, var2);
   }

   public void setFunction(String var1, PostfixMathCommandI var2) {
      this.name = var1;
      this.pfmc = var2;
   }

   public String toString() {
      return "Function \"" + this.name + "\"";
   }

   public PostfixMathCommandI getPFMC() {
      return this.pfmc;
   }

   public String getName() {
      return this.name;
   }
}
