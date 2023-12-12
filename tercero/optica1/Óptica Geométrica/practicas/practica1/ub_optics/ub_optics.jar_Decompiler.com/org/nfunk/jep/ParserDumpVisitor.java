package org.nfunk.jep;

public class ParserDumpVisitor implements ParserVisitor {
   private int indent = 0;

   private String indentString() {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = 0; var2 < this.indent; ++var2) {
         var1.append("  ");
      }

      return var1.toString();
   }

   public Object visit(SimpleNode var1, Object var2) {
      System.out.println(this.indentString() + var1 + ": acceptor not unimplemented in subclass?");
      ++this.indent;
      var2 = var1.childrenAccept(this, var2);
      --this.indent;
      return var2;
   }

   public Object visit(ASTStart var1, Object var2) {
      System.out.println(this.indentString() + var1);
      ++this.indent;
      var2 = var1.childrenAccept(this, var2);
      --this.indent;
      return var2;
   }

   public Object visit(ASTFunNode var1, Object var2) {
      System.out.println(this.indentString() + var1);
      ++this.indent;
      var2 = var1.childrenAccept(this, var2);
      --this.indent;
      return var2;
   }

   public Object visit(ASTVarNode var1, Object var2) {
      System.out.println(this.indentString() + var1);
      ++this.indent;
      var2 = var1.childrenAccept(this, var2);
      --this.indent;
      return var2;
   }

   public Object visit(ASTConstant var1, Object var2) {
      System.out.println(this.indentString() + var1);
      ++this.indent;
      var2 = var1.childrenAccept(this, var2);
      --this.indent;
      return var2;
   }
}
