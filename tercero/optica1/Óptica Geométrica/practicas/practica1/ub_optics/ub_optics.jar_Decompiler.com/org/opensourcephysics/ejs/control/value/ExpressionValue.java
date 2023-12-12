package org.opensourcephysics.ejs.control.value;

import java.util.StringTokenizer;
import org.opensourcephysics.ejs.control.GroupControl;

public class ExpressionValue extends Value {
   private String expression;
   private GroupControl group;
   private ParserSuryono parser;
   private String[] vars;
   private boolean isArray;
   private ParserSuryono[] arrayParser;
   private String[][] arrayVars;
   private double[] arrayValues;

   public ExpressionValue(String var1, GroupControl var2) {
      this.group = var2;
      this.expression = new String(var1.trim());
      this.processExpression();
   }

   public boolean getBoolean() {
      return this.getDouble() != 0.0D;
   }

   public int getInteger() {
      return (int)this.getDouble();
   }

   public double getDouble() {
      int var1 = 0;

      for(int var2 = this.vars.length; var1 < var2; ++var1) {
         this.parser.setVariable(var1, this.group.getDouble(this.vars[var1]));
      }

      return this.parser.evaluate();
   }

   public String getString() {
      return String.valueOf(this.getDouble());
   }

   public Object getObject() {
      if (!this.isArray) {
         return null;
      } else {
         int var1 = 0;

         for(int var2 = this.arrayVars.length; var1 < var2; ++var1) {
            int var3 = 0;

            for(int var4 = this.arrayVars[var1].length; var3 < var4; ++var3) {
               this.arrayParser[var1].setVariable(var3, this.group.getDouble(this.arrayVars[var1][var3]));
            }

            this.arrayValues[var1] = this.arrayParser[var1].evaluate();
         }

         return this.arrayValues;
      }
   }

   public void setExpression(String var1) {
      this.expression = new String(var1.trim());
      this.processExpression();
   }

   public void copyValue(Value var1) {
      if (var1 instanceof ExpressionValue) {
         this.expression = new String(((ExpressionValue)var1).expression);
      } else {
         this.expression = new String(var1.getString());
      }

      this.processExpression();
   }

   public Value cloneValue() {
      return new ExpressionValue(this.expression, this.group);
   }

   private void processExpression() {
      if (this.expression.startsWith("{") && this.expression.endsWith("}")) {
         String var8 = this.expression.substring(1, this.expression.length() - 1);
         StringTokenizer var9 = new StringTokenizer(var8, ",");
         int var3 = var9.countTokens();
         this.arrayParser = new ParserSuryono[var3];
         this.arrayVars = new String[var3][];
         this.arrayValues = new double[var3];
         this.isArray = true;

         for(int var4 = 0; var9.hasMoreTokens(); ++var4) {
            String var5 = var9.nextToken();
            this.arrayVars[var4] = ParserSuryono.getVariableList(var5);
            this.arrayParser[var4] = new ParserSuryono(this.arrayVars[var4].length);
            int var6 = 0;

            for(int var7 = this.arrayVars[var4].length; var6 < var7; ++var6) {
               this.arrayParser[var4].defineVariable(var6, this.arrayVars[var4][var6]);
            }

            this.arrayParser[var4].define(var5);
            this.arrayParser[var4].parse();
         }
      } else {
         this.vars = ParserSuryono.getVariableList(this.expression);
         this.parser = new ParserSuryono(this.vars.length);
         int var1 = 0;

         for(int var2 = this.vars.length; var1 < var2; ++var1) {
            this.parser.defineVariable(var1, this.vars[var1]);
         }

         this.parser.define(this.expression);
         this.parser.parse();
         this.isArray = false;
      }

   }
}
