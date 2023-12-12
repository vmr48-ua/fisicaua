package org.opensourcephysics.ejs.control.value;

import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

public final class ParserSuryono {
   private int var_count;
   private String[] var_name;
   private double[] var_value;
   private double[] number;
   private String function = "";
   private String postfix_code = "";
   private boolean valid = false;
   private int error;
   private boolean ISBOOLEAN = false;
   private boolean INRELATION = false;
   private int position;
   private int start;
   private int num;
   private char character;
   private int numberindex;
   private double[] refvalue = null;
   private static final String[] constname = new String[]{"Math.E", "Math.PI"};
   private static final double[] constvalue = new double[]{2.718281828459045D, 3.141592653589793D};
   private static final String[] funcnameNoParam = new String[]{"Math.random"};
   private static final String[] funcname = new String[]{"Math.abs", "Math.acos", "Math.asin", "Math.atan", "Math.ceil", "Math.cos", "Math.exp", "Math.floor", "Math.log", "Math.rint", "Math.round", "Math.sin", "Math.sqrt", "Math.tan", "Math.toDegrees", "Math.toRadians"};
   private static final String[] extfunc = new String[]{"Math.atan2", "Math.IEEEremainder", "Math.max", "Math.min", "Math.pow"};
   private static final int MAX_NUM = 200;
   private static final int NO_CONST;
   private static final int NO_FUNCSNOPARAM;
   private static final int NO_FUNCS;
   private static final int NO_EXT_FUNCS;
   private static final int STACK_SIZE = 50;
   private double[] stack = new double[50];
   private Hashtable references = null;
   private Vector refnames = null;
   public static final int NO_ERROR = 0;
   public static final int SYNTAX_ERROR = 1;
   public static final int PAREN_EXPECTED = 2;
   public static final int UNCOMPILED_FUNCTION = 3;
   public static final int EXPRESSION_EXPECTED = 4;
   public static final int UNKNOWN_IDENTIFIER = 5;
   public static final int OPERATOR_EXPECTED = 6;
   public static final int PAREN_NOT_MATCH = 7;
   public static final int CODE_DAMAGED = 8;
   public static final int STACK_OVERFLOW = 9;
   public static final int TOO_MANY_CONSTS = 10;
   public static final int COMMA_EXPECTED = 11;
   public static final int INVALID_OPERAND = 12;
   public static final int INVALID_OPERATOR = 13;
   public static final int NO_FUNC_DEFINITION = 14;
   public static final int REF_NAME_EXPECTED = 15;
   private static final int FUNC_OFFSET = 1000;
   private static final int EXT_FUNC_OFFSET;
   private static final int FUNCNOPARAM_OFFSET;
   private static final int VAR_OFFSET = 2000;
   private static final int REF_OFFSET = 3000;
   private static final char CONST_OFFSET = 'ý';
   private static final char NUMERIC = 'Ĭ';
   private static final char JUMP_CODE = '\u0001';
   private static final char LESS_THAN = '\u0002';
   private static final char GREATER_THAN = '\u0003';
   private static final char LESS_EQUAL = '\u0004';
   private static final char GREATER_EQUAL = '\u0005';
   private static final char NOT_EQUAL = '\u0006';
   private static final char EQUAL = '\u0007';
   private static final char IF_CODE = '\b';
   private static final char ENDIF = '\t';
   private static final char AND_CODE = '\n';
   private static final char OR_CODE = '\u000b';
   private static final char NOT_CODE = '\f';

   public static boolean isKeyword(String var0) {
      try {
         Double.parseDouble(var0);
         return true;
      } catch (Exception var2) {
         int var1;
         for(var1 = 0; var1 < NO_CONST; ++var1) {
            if (var0.equals(constname[var1])) {
               return true;
            }
         }

         for(var1 = 0; var1 < NO_FUNCS; ++var1) {
            if (var0.equals(funcname[var1])) {
               return true;
            }
         }

         for(var1 = 0; var1 < NO_EXT_FUNCS; ++var1) {
            if (var0.equals(extfunc[var1])) {
               return true;
            }
         }

         for(var1 = 0; var1 < NO_FUNCSNOPARAM; ++var1) {
            if (var0.equals(funcnameNoParam[var1])) {
               return true;
            }
         }

         return false;
      }
   }

   public static String[] getVariableList(String var0) {
      Vector var1 = getVariableList(var0, new Vector());
      return var1.size() < 1 ? new String[0] : (String[])((String[])var1.toArray(new String[1]));
   }

   public static Vector getVariableList(String var0, Vector var1) {
      StringTokenizer var2 = new StringTokenizer(var0, "() \t+-*/,<>=&|");

      while(var2.hasMoreTokens()) {
         String var3 = var2.nextToken();
         if (!isKeyword(var3) && !var1.contains(var3)) {
            var1.add(var3);
         }
      }

      return var1;
   }

   public ParserSuryono(int var1) {
      this.var_count = var1;
      this.references = new Hashtable();
      this.refnames = new Vector();
      this.var_name = new String[var1];
      this.var_value = new double[var1];
      this.number = new double[200];
   }

   public void defineVariable(int var1, String var2) {
      this.var_name[var1] = var2;
   }

   public void setVariable(int var1, double var2) {
      this.var_value[var1] = var2;
   }

   public void define(String var1) {
      this.function = var1;
      this.valid = false;
   }

   public void parse() {
      String var1 = new String(this.function);
      String var2 = new String(this.function);
      if (!this.valid) {
         this.num = 0;
         this.error = 0;
         this.references.clear();
         this.refnames.removeAllElements();

         int var3;
         while((var3 = var1.lastIndexOf(";")) != -1) {
            this.function = var1.substring(var3 + 1) + ')';
            var1 = var1.substring(0, var3++);
            String var4 = null;
            int var5 = this.function.indexOf(":");
            if (var5 == -1) {
               this.error = 14;

               for(this.position = 0; this.position < this.function.length() && this.function.charAt(this.position) == ' '; ++this.position) {
               }

               ++this.position;
            } else {
               var4 = this.function.substring(0, var5);
               this.function = this.function.substring(var5 + 1);
               var4 = var4.trim();
               if (var4.equals("")) {
                  this.error = 15;
                  this.position = 1;
               } else {
                  ++var5;
                  var3 += var5;
                  this.parseSubFunction();
               }
            }

            if (this.error != 0) {
               this.position += var3;
               break;
            }

            this.references.put(var4, this.postfix_code);
            this.refnames.addElement(var4);
         }

         if (this.error == 0) {
            this.function = var1 + ')';
            this.parseSubFunction();
         }

         this.function = var2;
         this.valid = this.error == 0;
      }
   }

   public double evaluate() {
      int var1 = this.refnames.size();
      if (!this.valid) {
         this.error = 3;
         return 0.0D;
      } else {
         this.error = 0;
         this.numberindex = 0;
         double var2;
         if (var1 != 0) {
            String var4 = this.postfix_code;
            this.refvalue = new double[var1];

            for(int var5 = 0; var5 < this.refnames.size(); ++var5) {
               String var6 = (String)this.refnames.elementAt(var5);
               this.postfix_code = (String)this.references.get(var6);
               var2 = this.evaluateSubFunction();
               if (this.error != 0) {
                  this.postfix_code = var4;
                  this.refvalue = null;
                  return var2;
               }

               this.refvalue[var5] = var2;
            }

            this.postfix_code = var4;
         }

         var2 = this.evaluateSubFunction();
         this.refvalue = null;
         if (Double.isNaN(var2)) {
            var2 = 0.0D;
         }

         return var2;
      }
   }

   public int getErrorCode() {
      return this.error;
   }

   public String getErrorString() {
      return toErrorString(this.error);
   }

   public int getErrorPosition() {
      return this.position;
   }

   public static String toErrorString(int var0) {
      String var1 = "";
      switch(var0) {
      case 0:
         var1 = "no error";
         break;
      case 1:
         var1 = "syntax error";
         break;
      case 2:
         var1 = "parenthesis expected";
         break;
      case 3:
         var1 = "uncompiled function";
         break;
      case 4:
         var1 = "expression expected";
         break;
      case 5:
         var1 = "unknown identifier";
         break;
      case 6:
         var1 = "operator expected";
         break;
      case 7:
         var1 = "parentheses not match";
         break;
      case 8:
         var1 = "internal code damaged";
         break;
      case 9:
         var1 = "execution stack overflow";
         break;
      case 10:
         var1 = "too many constants";
         break;
      case 11:
         var1 = "comma expected";
         break;
      case 12:
         var1 = "invalid operand type";
         break;
      case 13:
         var1 = "invalid operator";
         break;
      case 14:
         var1 = "bad reference definition (: expected)";
         break;
      case 15:
         var1 = "reference name expected";
      }

      return var1;
   }

   private void skipSpaces() throws ParserException {
      try {
         while(this.function.charAt(this.position - 1) == ' ') {
            ++this.position;
         }

         this.character = this.function.charAt(this.position - 1);
      } catch (StringIndexOutOfBoundsException var2) {
         throw new ParserException(7);
      }
   }

   private void getNextCharacter() throws ParserException {
      ++this.position;

      try {
         this.character = this.function.charAt(this.position - 1);
      } catch (StringIndexOutOfBoundsException var2) {
         throw new ParserException(7);
      }
   }

   private void addCode(char var1) {
      this.postfix_code = this.postfix_code + var1;
   }

   private void scanNumber() throws ParserException {
      String var1 = "";
      if (this.num == 200) {
         throw new ParserException(10);
      } else {
         if (this.character != '.') {
            do {
               var1 = var1 + this.character;
               this.getNextCharacter();
            } while(this.character >= '0' && this.character <= '9');
         } else {
            var1 = var1 + '0';
         }

         if (this.character == '.') {
            do {
               var1 = var1 + this.character;
               this.getNextCharacter();
            } while(this.character >= '0' && this.character <= '9');
         }

         if (this.character == 'e' || this.character == 'E') {
            var1 = var1 + 'e';
            this.getNextCharacter();
            if (this.character == '+' || this.character == '-') {
               var1 = var1 + this.character;
               this.getNextCharacter();
            }

            while(this.character >= '0' && this.character <= '9') {
               var1 = var1 + this.character;
               this.getNextCharacter();
            }
         }

         double var2;
         try {
            var2 = Double.valueOf(var1);
         } catch (NumberFormatException var5) {
            this.position = this.start;
            throw new ParserException(1);
         }

         this.number[this.num++] = var2;
         this.addCode('Ĭ');
      }
   }

   private void scanNonNumeric() throws ParserException {
      String var1 = "";
      if (this.character != '*' && this.character != '/' && this.character != ')' && this.character != ',' && this.character != '<' && this.character != '>' && this.character != '=' && this.character != '&' && this.character != '|') {
         do {
            var1 = var1 + this.character;
            this.getNextCharacter();
         } while(this.character != ' ' && this.character != '+' && this.character != '-' && this.character != '*' && this.character != '/' && this.character != '(' && this.character != ')' && this.character != ',' && this.character != '<' && this.character != '>' && this.character != '=' && this.character != '&' && this.character != '|');

         int var2;
         for(var2 = 0; var2 < NO_CONST; ++var2) {
            if (var1.equals(constname[var2])) {
               this.addCode((char)(var2 + 253));
               return;
            }
         }

         for(var2 = 0; var2 < NO_FUNCS; ++var2) {
            if (var1.equals(funcname[var2])) {
               this.skipSpaces();
               if (this.character != '(') {
                  throw new ParserException(2);
               }

               this.scanAndParse();
               if (this.character != ')') {
                  throw new ParserException(2);
               }

               this.getNextCharacter();
               this.addCode((char)(var2 + 1000));
               return;
            }
         }

         for(var2 = 0; var2 < NO_EXT_FUNCS; ++var2) {
            if (var1.equals(extfunc[var2])) {
               this.skipSpaces();
               if (this.character != '(') {
                  throw new ParserException(2);
               }

               this.scanAndParse();
               if (this.character != ',') {
                  throw new ParserException(11);
               }

               String var3 = new String(this.postfix_code);
               this.postfix_code = "";
               this.scanAndParse();
               if (this.character != ')') {
                  throw new ParserException(2);
               }

               this.getNextCharacter();
               var3 = var3 + this.postfix_code;
               this.postfix_code = new String(var3);
               this.addCode((char)(var2 + EXT_FUNC_OFFSET));
               return;
            }
         }

         for(var2 = 0; var2 < NO_FUNCSNOPARAM; ++var2) {
            if (var1.equals(funcnameNoParam[var2])) {
               this.skipSpaces();
               if (this.character != '(') {
                  throw new ParserException(2);
               }

               this.skipSpaces();
               this.getNextCharacter();
               if (this.character != ')') {
                  throw new ParserException(2);
               }

               this.getNextCharacter();
               this.addCode((char)(var2 + FUNCNOPARAM_OFFSET));
               return;
            }
         }

         for(var2 = 0; var2 < this.var_count; ++var2) {
            if (var1.equals(this.var_name[var2])) {
               this.addCode((char)(var2 + 2000));
               return;
            }
         }

         var2 = this.refnames.indexOf(var1);
         if (var2 != -1) {
            this.addCode((char)(var2 + 3000));
         } else {
            this.position = this.start;
            throw new ParserException(5);
         }
      } else {
         throw new ParserException(1);
      }
   }

   private boolean getIdentifier() throws ParserException {
      boolean var1 = false;
      this.getNextCharacter();
      this.skipSpaces();
      if (this.character == '!') {
         this.getNextCharacter();
         this.skipSpaces();
         if (this.character != '(') {
            throw new ParserException(2);
         } else {
            this.scanAndParse();
            if (this.character != ')') {
               throw new ParserException(2);
            } else if (!this.ISBOOLEAN) {
               throw new ParserException(12);
            } else {
               this.addCode('\f');
               this.getNextCharacter();
               return false;
            }
         }
      } else {
         this.ISBOOLEAN = false;

         while(this.character == '+' || this.character == '-') {
            if (this.character == '-') {
               var1 = !var1;
            }

            this.getNextCharacter();
            this.skipSpaces();
         }

         this.start = this.position;
         if ((this.character < '0' || this.character > '9') && this.character != '.') {
            if (this.character == '(') {
               this.scanAndParse();
               this.getNextCharacter();
            } else {
               this.scanNonNumeric();
            }
         } else {
            this.scanNumber();
         }

         this.skipSpaces();
         return var1;
      }
   }

   private void arithmeticLevel2() throws ParserException {
      if (this.ISBOOLEAN) {
         throw new ParserException(12);
      } else {
         do {
            char var2 = this.character;
            boolean var1 = this.getIdentifier();
            if (this.ISBOOLEAN) {
               throw new ParserException(12);
            }

            if (var1) {
               this.addCode('_');
            }

            this.addCode(var2);
         } while(this.character == '*' || this.character == '/');

      }
   }

   private void arithmeticLevel1() throws ParserException {
      if (this.ISBOOLEAN) {
         throw new ParserException(12);
      } else {
         do {
            char var2 = this.character;
            boolean var1 = this.getIdentifier();
            if (this.ISBOOLEAN) {
               throw new ParserException(12);
            }

            if (this.character == '*' || this.character == '/') {
               if (var1) {
                  this.addCode('_');
               }

               this.arithmeticLevel2();
            }

            this.addCode(var2);
         } while(this.character == '+' || this.character == '-');

      }
   }

   private void relationLevel() throws ParserException {
      char var1 = 0;
      if (this.INRELATION) {
         throw new ParserException(13);
      } else {
         this.INRELATION = true;
         if (this.ISBOOLEAN) {
            throw new ParserException(12);
         } else {
            switch(this.character) {
            case '<':
               var1 = 2;
               this.getNextCharacter();
               if (this.character == '>') {
                  var1 = 6;
               } else if (this.character == '=') {
                  var1 = 4;
               } else {
                  --this.position;
               }
               break;
            case '=':
               var1 = 7;
               break;
            case '>':
               var1 = 3;
               this.getNextCharacter();
               if (this.character == '=') {
                  var1 = 5;
               } else {
                  --this.position;
               }
            }

            this.scanAndParse();
            this.INRELATION = false;
            if (this.ISBOOLEAN) {
               throw new ParserException(12);
            } else {
               this.addCode(var1);
               this.ISBOOLEAN = true;
            }
         }
      }
   }

   private void booleanLevel() throws ParserException {
      if (!this.ISBOOLEAN) {
         throw new ParserException(12);
      } else {
         char var1 = this.character;
         this.scanAndParse();
         if (!this.ISBOOLEAN) {
            throw new ParserException(12);
         } else {
            switch(var1) {
            case '&':
               this.addCode('\n');
               break;
            case '|':
               this.addCode('\u000b');
            }

         }
      }
   }

   private void scanAndParse() throws ParserException {
      boolean var1 = this.getIdentifier();
      if (var1) {
         this.addCode('_');
      }

      while(true) {
         switch(this.character) {
         case '&':
         case '|':
            this.booleanLevel();
            break;
         case ')':
         case ',':
            return;
         case '*':
         case '/':
            this.arithmeticLevel2();
            break;
         case '+':
         case '-':
            this.arithmeticLevel1();
            break;
         case '<':
         case '=':
         case '>':
            this.relationLevel();
            break;
         default:
            throw new ParserException(6);
         }
      }
   }

   private void parseSubFunction() {
      this.position = 0;
      this.postfix_code = "";
      this.INRELATION = false;
      this.ISBOOLEAN = false;

      try {
         this.scanAndParse();
      } catch (ParserException var2) {
         this.error = var2.getErrorCode();
         if (this.error == 1 && this.postfix_code == "") {
            this.error = 4;
         }
      }

      if (this.error == 0 && this.position != this.function.length()) {
         this.error = 7;
      }

   }

   private double builtInFunction(int var1, double var2) {
      switch(var1) {
      case 0:
         return Math.abs(var2);
      case 1:
         return Math.acos(var2);
      case 2:
         return Math.asin(var2);
      case 3:
         return Math.atan(var2);
      case 4:
         return Math.ceil(var2);
      case 5:
         return Math.cos(var2);
      case 6:
         return Math.exp(var2);
      case 7:
         return Math.floor(var2);
      case 8:
         return Math.log(var2);
      case 9:
         return Math.rint(var2);
      case 10:
         return (double)Math.round(var2);
      case 11:
         return Math.sin(var2);
      case 12:
         return Math.sqrt(var2);
      case 13:
         return Math.tan(var2);
      case 14:
         return Math.toDegrees(var2);
      case 15:
         return Math.toRadians(var2);
      default:
         this.error = 8;
         return Double.NaN;
      }
   }

   private double builtInFunctionNoParam(int var1) {
      switch(var1) {
      case 0:
         return Math.random();
      default:
         this.error = 8;
         return Double.NaN;
      }
   }

   private double builtInExtFunction(int var1, double var2, double var4) {
      switch(var1) {
      case 0:
         return Math.atan2(var2, var4);
      case 1:
         return Math.IEEEremainder(var2, var4);
      case 2:
         return Math.max(var2, var4);
      case 3:
         return Math.min(var2, var4);
      case 4:
         return Math.pow(var2, var4);
      default:
         this.error = 8;
         return Double.NaN;
      }
   }

   private double evaluateSubFunction() {
      int var1 = -1;
      int var2 = 0;
      int var5 = this.postfix_code.length();

      label142:
      while(true) {
         char var4;
         try {
            if (var2 == var5) {
               return this.stack[0];
            }

            var4 = this.postfix_code.charAt(var2++);
         } catch (StringIndexOutOfBoundsException var9) {
            return this.stack[0];
         }

         try {
            double[] var10000;
            int var3;
            switch(var4) {
            case '\u0001':
               var3 = var2 + this.postfix_code.charAt(var2++);

               while(true) {
                  if (var2 >= var3) {
                     continue label142;
                  }

                  if (this.postfix_code.charAt(var2++) == 300) {
                     ++this.numberindex;
                  }
               }
            case '\u0002':
               --var1;
               this.stack[var1] = this.stack[var1] < this.stack[var1 + 1] ? 1.0D : 0.0D;
               break;
            case '\u0003':
               --var1;
               this.stack[var1] = this.stack[var1] > this.stack[var1 + 1] ? 1.0D : 0.0D;
               break;
            case '\u0004':
               --var1;
               this.stack[var1] = this.stack[var1] <= this.stack[var1 + 1] ? 1.0D : 0.0D;
               break;
            case '\u0005':
               --var1;
               this.stack[var1] = this.stack[var1] >= this.stack[var1 + 1] ? 1.0D : 0.0D;
               break;
            case '\u0006':
               --var1;
               this.stack[var1] = this.stack[var1] != this.stack[var1 + 1] ? 1.0D : 0.0D;
               break;
            case '\u0007':
               --var1;
               this.stack[var1] = this.stack[var1] == this.stack[var1 + 1] ? 1.0D : 0.0D;
               break;
            case '\b':
               if (this.stack[var1--] == 0.0D) {
                  var3 = var2 + this.postfix_code.charAt(var2++);

                  while(var2 < var3) {
                     if (this.postfix_code.charAt(var2++) == 300) {
                        ++this.numberindex;
                     }
                  }
               } else {
                  ++var2;
               }
            case '\t':
               break;
            case '\n':
               --var1;
               if (this.stack[var1] != 0.0D && this.stack[var1 + 1] != 0.0D) {
                  this.stack[var1] = 1.0D;
                  break;
               }

               this.stack[var1] = 0.0D;
               break;
            case '\u000b':
               --var1;
               if (this.stack[var1] == 0.0D && this.stack[var1 + 1] == 0.0D) {
                  this.stack[var1] = 0.0D;
                  break;
               }

               this.stack[var1] = 1.0D;
               break;
            case '\f':
               this.stack[var1] = this.stack[var1] == 0.0D ? 1.0D : 0.0D;
               break;
            case '*':
               var10000 = this.stack;
               var10000[var1 - 1] *= this.stack[var1];
               --var1;
               break;
            case '+':
               var10000 = this.stack;
               var10000[var1 - 1] += this.stack[var1];
               --var1;
               break;
            case '-':
               var10000 = this.stack;
               var10000[var1 - 1] -= this.stack[var1];
               --var1;
               break;
            case '/':
               if (this.stack[var1] != 0.0D) {
                  var10000 = this.stack;
                  var10000[var1 - 1] /= this.stack[var1];
               } else {
                  var10000 = this.stack;
                  var10000[var1 - 1] /= 1.0E-128D;
               }

               --var1;
               break;
            case '_':
               this.stack[var1] = -this.stack[var1];
               break;
            case 'Ĭ':
               ++var1;
               this.stack[var1] = this.number[this.numberindex++];
               break;
            default:
               if (var4 >= 3000) {
                  ++var1;
                  this.stack[var1] = this.refvalue[var4 - 3000];
               } else if (var4 >= 2000) {
                  ++var1;
                  this.stack[var1] = this.var_value[var4 - 2000];
               } else if (var4 >= FUNCNOPARAM_OFFSET) {
                  ++var1;
                  this.stack[var1] = this.builtInFunctionNoParam(var4 - FUNCNOPARAM_OFFSET);
               } else if (var4 >= EXT_FUNC_OFFSET) {
                  this.stack[var1 - 1] = this.builtInExtFunction(var4 - EXT_FUNC_OFFSET, this.stack[var1 - 1], this.stack[var1]);
                  --var1;
               } else if (var4 >= 1000) {
                  this.stack[var1] = this.builtInFunction(var4 - 1000, this.stack[var1]);
               } else {
                  if (var4 < 253) {
                     this.error = 8;
                     return Double.NaN;
                  }

                  ++var1;
                  this.stack[var1] = constvalue[var4 - 253];
               }
            }
         } catch (ArrayIndexOutOfBoundsException var7) {
            this.error = 9;
            return Double.NaN;
         } catch (NullPointerException var8) {
            this.error = 8;
            return Double.NaN;
         }
      }
   }

   static {
      NO_CONST = constname.length;
      NO_FUNCSNOPARAM = funcnameNoParam.length;
      NO_FUNCS = funcname.length;
      NO_EXT_FUNCS = extfunc.length;
      EXT_FUNC_OFFSET = 1000 + NO_FUNCS;
      FUNCNOPARAM_OFFSET = EXT_FUNC_OFFSET + NO_EXT_FUNCS;
   }
}
