package org.nfunk.jep;

public class ASTConstant extends SimpleNode {
   private Object value;

   public ASTConstant(int var1) {
      super(var1);
   }

   public ASTConstant(Parser var1, int var2) {
      super(var1, var2);
   }

   public void setValue(Object var1) {
      this.value = var1;
   }

   public Object getValue() {
      return this.value;
   }

   public Object jjtAccept(ParserVisitor var1, Object var2) {
      return var1.visit(this, var2);
   }

   public String toString() {
      return "Constant: " + this.value;
   }
}
