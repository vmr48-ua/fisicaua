package org.nfunk.jep;

import java.io.StringReader;
import java.util.Vector;
import org.nfunk.jep.function.Abs;
import org.nfunk.jep.function.Angle;
import org.nfunk.jep.function.ArcCosine;
import org.nfunk.jep.function.ArcCosineH;
import org.nfunk.jep.function.ArcSine;
import org.nfunk.jep.function.ArcSineH;
import org.nfunk.jep.function.ArcTanH;
import org.nfunk.jep.function.ArcTangent;
import org.nfunk.jep.function.Cosine;
import org.nfunk.jep.function.CosineH;
import org.nfunk.jep.function.Imaginary;
import org.nfunk.jep.function.Logarithm;
import org.nfunk.jep.function.Modulus;
import org.nfunk.jep.function.NaturalLogarithm;
import org.nfunk.jep.function.PostfixMathCommandI;
import org.nfunk.jep.function.Random;
import org.nfunk.jep.function.Real;
import org.nfunk.jep.function.Sine;
import org.nfunk.jep.function.SineH;
import org.nfunk.jep.function.SquareRoot;
import org.nfunk.jep.function.Sum;
import org.nfunk.jep.function.TanH;
import org.nfunk.jep.function.Tangent;
import org.nfunk.jep.type.Complex;
import org.nfunk.jep.type.DoubleNumberFactory;
import org.nfunk.jep.type.NumberFactory;

public class JEP {
   private static final boolean debug = false;
   private boolean traverse;
   protected boolean allowUndeclared;
   protected boolean implicitMul;
   protected SymbolTable symTab;
   protected FunctionTable funTab;
   protected Vector errorList;
   private Parser parser;
   private Node topNode = null;
   private EvaluatorVisitor ev;
   private NumberFactory numberFactory;

   public JEP() {
      this.traverse = false;
      this.allowUndeclared = false;
      this.implicitMul = false;
      this.numberFactory = new DoubleNumberFactory();
      this.initSymTab();
      this.initFunTab();
      this.errorList = new Vector();
      this.ev = new EvaluatorVisitor();
      this.parser = new Parser(new StringReader(""));
      this.parseExpression("");
   }

   public JEP(boolean var1, boolean var2, boolean var3, NumberFactory var4) {
      this.traverse = var1;
      this.allowUndeclared = var2;
      this.implicitMul = var3;
      if (var4 == null) {
         this.numberFactory = new DoubleNumberFactory();
      } else {
         this.numberFactory = var4;
      }

      this.initSymTab();
      this.initFunTab();
      this.errorList = new Vector();
      this.ev = new EvaluatorVisitor();
      this.parser = new Parser(new StringReader(""));
      this.parseExpression("");
   }

   public void initSymTab() {
      this.symTab = new SymbolTable();
   }

   public void initFunTab() {
      this.funTab = new FunctionTable();
   }

   public void addStandardFunctions() {
      this.funTab.put("sin", new Sine());
      this.funTab.put("cos", new Cosine());
      this.funTab.put("tan", new Tangent());
      this.funTab.put("asin", new ArcSine());
      this.funTab.put("acos", new ArcCosine());
      this.funTab.put("atan", new ArcTangent());
      this.funTab.put("sinh", new SineH());
      this.funTab.put("cosh", new CosineH());
      this.funTab.put("tanh", new TanH());
      this.funTab.put("asinh", new ArcSineH());
      this.funTab.put("acosh", new ArcCosineH());
      this.funTab.put("atanh", new ArcTanH());
      this.funTab.put("log", new Logarithm());
      this.funTab.put("ln", new NaturalLogarithm());
      this.funTab.put("sqrt", new SquareRoot());
      this.funTab.put("angle", new Angle());
      this.funTab.put("abs", new Abs());
      this.funTab.put("mod", new Modulus());
      this.funTab.put("sum", new Sum());
      this.funTab.put("rand", new Random());
   }

   public void addStandardConstants() {
      this.symTab.put("pi", new Double(3.141592653589793D));
      this.symTab.put("e", new Double(2.718281828459045D));
   }

   public void addComplex() {
      this.symTab.put("i", new Complex(0.0D, 1.0D));
      this.funTab.put("re", new Real());
      this.funTab.put("im", new Imaginary());
   }

   public void addFunction(String var1, PostfixMathCommandI var2) {
      this.funTab.put(var1, var2);
   }

   public Double addVariable(String var1, double var2) {
      Double var4 = new Double(var2);
      this.symTab.put(var1, var4);
      return var4;
   }

   public Complex addComplexVariable(String var1, double var2, double var4) {
      Complex var6 = new Complex(var2, var4);
      this.symTab.put(var1, var6);
      return var6;
   }

   public void addVariableAsObject(String var1, Object var2) {
      this.symTab.put(var1, var2);
   }

   public Object removeVariable(String var1) {
      return this.symTab.remove(var1);
   }

   public Object removeFunction(String var1) {
      return this.funTab.remove(var1);
   }

   public void setTraverse(boolean var1) {
      this.traverse = var1;
   }

   public void setImplicitMul(boolean var1) {
      this.implicitMul = var1;
   }

   public void setAllowUndeclared(boolean var1) {
      this.allowUndeclared = var1;
   }

   public void parseExpression(String var1) {
      StringReader var2 = new StringReader(var1);

      try {
         this.errorList.removeAllElements();
         this.topNode = this.parser.parseStream(var2, this);
      } catch (Throwable var4) {
         this.topNode = null;
         if (var4 instanceof ParseException) {
            this.errorList.addElement(((ParseException)var4).getErrorInfo());
         } else {
            this.errorList.addElement("Syntax error");
         }
      }

      if (this.traverse && !this.hasError()) {
         ParserDumpVisitor var3 = new ParserDumpVisitor();
         this.topNode.jjtAccept(var3, (Object)null);
      }

   }

   public double getValue() {
      Object var1 = this.getValueAsObject();
      if (var1 == null) {
         return 0.0D;
      } else if (var1 instanceof Number) {
         return ((Number)var1).doubleValue();
      } else {
         return var1 instanceof Complex ? ((Complex)var1).re() : 0.0D;
      }
   }

   public Complex getComplexValue() {
      Object var1 = this.getValueAsObject();
      if (var1 == null) {
         return null;
      } else if (var1 instanceof Number) {
         return new Complex(((Number)var1).doubleValue(), 0.0D);
      } else {
         return var1 instanceof Complex ? (Complex)var1 : null;
      }
   }

   public Object getValueAsObject() {
      if (this.topNode != null && !this.hasError()) {
         try {
            Object var1 = this.ev.getValue(this.topNode, this.errorList, this.symTab);
            return var1;
         } catch (Exception var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   public boolean hasError() {
      return !this.errorList.isEmpty();
   }

   public String getErrorInfo() {
      if (!this.hasError()) {
         return null;
      } else {
         String var1 = "";

         for(int var2 = 0; var2 < this.errorList.size(); ++var2) {
            var1 = var1 + this.errorList.elementAt(var2) + "\n";
         }

         return var1;
      }
   }

   public Node getTopNode() {
      return this.topNode;
   }

   public SymbolTable getSymbolTable() {
      return this.symTab;
   }

   public NumberFactory getNumberFactory() {
      return this.numberFactory;
   }
}
