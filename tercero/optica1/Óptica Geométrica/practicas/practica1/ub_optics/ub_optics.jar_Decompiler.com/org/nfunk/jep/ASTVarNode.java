package org.nfunk.jep;

public class ASTVarNode extends SimpleNode {
   private String varName;

   public ASTVarNode(int var1) {
      super(var1);
      this.varName = "";
   }

   public ASTVarNode(Parser var1, int var2) {
      super(var1, var2);
   }

   public Object jjtAccept(ParserVisitor var1, Object var2) {
      return var1.visit(this, var2);
   }

   public void setName(String var1) {
      this.varName = var1;
   }

   public String getName() {
      return this.varName;
   }

   public String toString() {
      String var1 = "Variable: \"" + this.getName() + "\"";
      return var1;
   }
}
