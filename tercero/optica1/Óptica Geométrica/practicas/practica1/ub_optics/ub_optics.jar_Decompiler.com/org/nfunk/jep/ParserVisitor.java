package org.nfunk.jep;

public interface ParserVisitor {
   Object visit(SimpleNode var1, Object var2);

   Object visit(ASTStart var1, Object var2);

   Object visit(ASTFunNode var1, Object var2);

   Object visit(ASTVarNode var1, Object var2);

   Object visit(ASTConstant var1, Object var2);
}
