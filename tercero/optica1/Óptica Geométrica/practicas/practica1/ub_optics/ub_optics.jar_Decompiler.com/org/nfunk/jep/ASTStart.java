package org.nfunk.jep;

public class ASTStart extends SimpleNode {
   public ASTStart(int var1) {
      super(var1);
   }

   public ASTStart(Parser var1, int var2) {
      super(var1, var2);
   }

   public Object jjtAccept(ParserVisitor var1, Object var2) {
      return var1.visit(this, var2);
   }
}
