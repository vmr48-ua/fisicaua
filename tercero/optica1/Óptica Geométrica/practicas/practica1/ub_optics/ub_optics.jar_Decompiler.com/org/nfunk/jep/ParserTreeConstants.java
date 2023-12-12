package org.nfunk.jep;

public interface ParserTreeConstants {
   int JJTSTART = 0;
   int JJTVOID = 1;
   int JJTFUNNODE = 2;
   int JJTVARNODE = 3;
   int JJTCONSTANT = 4;
   String[] jjtNodeName = new String[]{"Start", "void", "FunNode", "VarNode", "Constant"};
}
