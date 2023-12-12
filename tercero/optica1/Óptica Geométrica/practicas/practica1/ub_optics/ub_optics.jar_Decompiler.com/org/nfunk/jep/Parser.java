package org.nfunk.jep;

import java.io.InputStream;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Vector;
import org.nfunk.jep.function.Add;
import org.nfunk.jep.function.Comparative;
import org.nfunk.jep.function.Divide;
import org.nfunk.jep.function.Logical;
import org.nfunk.jep.function.Modulus;
import org.nfunk.jep.function.Multiply;
import org.nfunk.jep.function.Not;
import org.nfunk.jep.function.PostfixMathCommandI;
import org.nfunk.jep.function.Power;
import org.nfunk.jep.function.Subtract;
import org.nfunk.jep.function.UMinus;

public class Parser implements ParserTreeConstants, ParserConstants {
   protected JJTParserState jjtree = new JJTParserState();
   private JEP jep;
   public ParserTokenManager token_source;
   JavaCharStream jj_input_stream;
   public Token token;
   public Token jj_nt;
   private int jj_ntk;
   private Token jj_scanpos;
   private Token jj_lastpos;
   private int jj_la;
   public boolean lookingAhead = false;
   private boolean jj_semLA;
   private int jj_gen;
   private final int[] jj_la1 = new int[19];
   private final int[] jj_la1_0 = new int[]{1, 1048576, 524288, 294912, 294912, 221184, 221184, 6291456, 6291456, 58720256, 73400320, 134217728, 1024, -2147482976, 268435456, 1073741824, -2147482976, 1073741824, 160};
   private final int[] jj_la1_1 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
   private final Parser.JJCalls[] jj_2_rtns = new Parser.JJCalls[6];
   private boolean jj_rescan = false;
   private int jj_gc = 0;
   private Vector jj_expentries = new Vector();
   private int[] jj_expentry;
   private int jj_kind = -1;
   private int[] jj_lasttokens = new int[100];
   private int jj_endpos;

   public Node parseStream(Reader var1, JEP var2) throws ParseException {
      this.ReInit(var1);
      this.jep = var2;
      return this.Start().jjtGetChild(0);
   }

   private void addToErrorList(String var1) {
      this.jep.errorList.addElement(var1);
   }

   private String replaceEscape(String var1) {
      int var2 = var1.length();
      int var3 = 0;
      String var5 = "tnrbf\\\"'";
      String var6 = "\t\n\r\b\f\\\"'";

      int var4;
      StringBuffer var7;
      for(var7 = new StringBuffer(); (var4 = var1.indexOf(92, var3)) != -1; var3 = var4 + 2) {
         var7.append(var1.substring(var3, var4));
         if (var4 + 1 == var2) {
            break;
         }

         char var8 = var1.charAt(var4 + 1);
         int var9 = var5.indexOf(var8);
         if (var9 == -1) {
            var7.append('\\');
            var7.append(var8);
         } else {
            var7.append(var6.charAt(var9));
         }
      }

      if (var3 < var2) {
         var7.append(var1.substring(var3));
      }

      return var7.toString();
   }

   public final ASTStart Start() throws ParseException {
      ASTStart var1 = new ASTStart(0);
      boolean var2 = true;
      this.jjtree.openNodeScope(var1);

      ASTStart var3;
      try {
         if (!this.jj_2_1(1)) {
            switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 0:
               this.jj_consume_token(0);
               this.jjtree.closeNodeScope(var1, true);
               var2 = false;
               throw new ParseException("No expression entered");
            default:
               this.jj_la1[0] = this.jj_gen;
               this.jj_consume_token(-1);
               throw new ParseException();
            }
         }

         this.Expression();
         this.jj_consume_token(0);
         this.jjtree.closeNodeScope(var1, true);
         var2 = false;
         var3 = var1;
      } catch (Throwable var7) {
         if (var2) {
            this.jjtree.clearNodeScope(var1);
            var2 = false;
         } else {
            this.jjtree.popNode();
         }

         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         }

         if (var7 instanceof ParseException) {
            throw (ParseException)var7;
         }

         throw (Error)var7;
      } finally {
         if (var2) {
            this.jjtree.closeNodeScope(var1, true);
         }

      }

      return var3;
   }

   public final void Expression() throws ParseException {
      this.OrExpression();
   }

   public final void OrExpression() throws ParseException {
      this.AndExpression();

      while(true) {
         switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 20:
            ASTFunNode var1 = new ASTFunNode(2);
            boolean var2 = true;
            this.jjtree.openNodeScope(var1);

            try {
               this.jj_consume_token(20);
               this.AndExpression();
               this.jjtree.closeNodeScope(var1, 2);
               var2 = false;
               var1.setFunction(ParserConstants.tokenImage[20], new Logical(1));
               break;
            } catch (Throwable var7) {
               if (var2) {
                  this.jjtree.clearNodeScope(var1);
                  var2 = false;
               } else {
                  this.jjtree.popNode();
               }

               if (var7 instanceof RuntimeException) {
                  throw (RuntimeException)var7;
               }

               if (var7 instanceof ParseException) {
                  throw (ParseException)var7;
               }

               throw (Error)var7;
            } finally {
               if (var2) {
                  this.jjtree.closeNodeScope(var1, 2);
               }

            }
         default:
            this.jj_la1[1] = this.jj_gen;
            return;
         }
      }
   }

   public final void AndExpression() throws ParseException {
      this.EqualExpression();

      while(true) {
         switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 19:
            ASTFunNode var1 = new ASTFunNode(2);
            boolean var2 = true;
            this.jjtree.openNodeScope(var1);

            try {
               this.jj_consume_token(19);
               this.EqualExpression();
               this.jjtree.closeNodeScope(var1, 2);
               var2 = false;
               var1.setFunction(ParserConstants.tokenImage[19], new Logical(0));
               break;
            } catch (Throwable var7) {
               if (var2) {
                  this.jjtree.clearNodeScope(var1);
                  var2 = false;
               } else {
                  this.jjtree.popNode();
               }

               if (var7 instanceof RuntimeException) {
                  throw (RuntimeException)var7;
               }

               if (var7 instanceof ParseException) {
                  throw (ParseException)var7;
               }

               throw (Error)var7;
            } finally {
               if (var2) {
                  this.jjtree.closeNodeScope(var1, 2);
               }

            }
         default:
            this.jj_la1[2] = this.jj_gen;
            return;
         }
      }
   }

   public final void EqualExpression() throws ParseException {
      this.RelationalExpression();

      while(true) {
         switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 15:
         case 18:
            switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 15:
               ASTFunNode var3 = new ASTFunNode(2);
               boolean var4 = true;
               this.jjtree.openNodeScope(var3);

               try {
                  this.jj_consume_token(15);
                  this.RelationalExpression();
                  this.jjtree.closeNodeScope(var3, 2);
                  var4 = false;
                  var3.setFunction(ParserConstants.tokenImage[15], new Comparative(5));
                  continue;
               } catch (Throwable var17) {
                  if (var4) {
                     this.jjtree.clearNodeScope(var3);
                     var4 = false;
                  } else {
                     this.jjtree.popNode();
                  }

                  if (var17 instanceof RuntimeException) {
                     throw (RuntimeException)var17;
                  }

                  if (var17 instanceof ParseException) {
                     throw (ParseException)var17;
                  }

                  throw (Error)var17;
               } finally {
                  if (var4) {
                     this.jjtree.closeNodeScope(var3, 2);
                  }

               }
            case 18:
               ASTFunNode var1 = new ASTFunNode(2);
               boolean var2 = true;
               this.jjtree.openNodeScope(var1);

               try {
                  this.jj_consume_token(18);
                  this.RelationalExpression();
                  this.jjtree.closeNodeScope(var1, 2);
                  var2 = false;
                  var1.setFunction(ParserConstants.tokenImage[18], new Comparative(4));
                  continue;
               } catch (Throwable var15) {
                  if (var2) {
                     this.jjtree.clearNodeScope(var1);
                     var2 = false;
                  } else {
                     this.jjtree.popNode();
                  }

                  if (var15 instanceof RuntimeException) {
                     throw (RuntimeException)var15;
                  }

                  if (var15 instanceof ParseException) {
                     throw (ParseException)var15;
                  }

                  throw (Error)var15;
               } finally {
                  if (var2) {
                     this.jjtree.closeNodeScope(var1, 2);
                  }

               }
            default:
               this.jj_la1[4] = this.jj_gen;
               this.jj_consume_token(-1);
               throw new ParseException();
            }
         default:
            this.jj_la1[3] = this.jj_gen;
            return;
         }
      }
   }

   public final void RelationalExpression() throws ParseException {
      this.AdditiveExpression();

      while(true) {
         switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 13:
         case 14:
         case 16:
         case 17:
            switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 13:
               ASTFunNode var3 = new ASTFunNode(2);
               boolean var4 = true;
               this.jjtree.openNodeScope(var3);

               try {
                  this.jj_consume_token(13);
                  this.AdditiveExpression();
                  this.jjtree.closeNodeScope(var3, 2);
                  var4 = false;
                  var3.setFunction(ParserConstants.tokenImage[13], new Comparative(1));
                  continue;
               } catch (Throwable var45) {
                  if (var4) {
                     this.jjtree.clearNodeScope(var3);
                     var4 = false;
                  } else {
                     this.jjtree.popNode();
                  }

                  if (var45 instanceof RuntimeException) {
                     throw (RuntimeException)var45;
                  }

                  if (var45 instanceof ParseException) {
                     throw (ParseException)var45;
                  }

                  throw (Error)var45;
               } finally {
                  if (var4) {
                     this.jjtree.closeNodeScope(var3, 2);
                  }

               }
            case 14:
               ASTFunNode var1 = new ASTFunNode(2);
               boolean var2 = true;
               this.jjtree.openNodeScope(var1);

               try {
                  this.jj_consume_token(14);
                  this.AdditiveExpression();
                  this.jjtree.closeNodeScope(var1, 2);
                  var2 = false;
                  var1.setFunction(ParserConstants.tokenImage[14], new Comparative(0));
                  continue;
               } catch (Throwable var47) {
                  if (var2) {
                     this.jjtree.clearNodeScope(var1);
                     var2 = false;
                  } else {
                     this.jjtree.popNode();
                  }

                  if (var47 instanceof RuntimeException) {
                     throw (RuntimeException)var47;
                  }

                  if (var47 instanceof ParseException) {
                     throw (ParseException)var47;
                  }

                  throw (Error)var47;
               } finally {
                  if (var2) {
                     this.jjtree.closeNodeScope(var1, 2);
                  }

               }
            case 15:
            default:
               this.jj_la1[6] = this.jj_gen;
               this.jj_consume_token(-1);
               throw new ParseException();
            case 16:
               ASTFunNode var5 = new ASTFunNode(2);
               boolean var6 = true;
               this.jjtree.openNodeScope(var5);

               try {
                  this.jj_consume_token(16);
                  this.AdditiveExpression();
                  this.jjtree.closeNodeScope(var5, 2);
                  var6 = false;
                  var5.setFunction(ParserConstants.tokenImage[16], new Comparative(2));
                  continue;
               } catch (Throwable var43) {
                  if (var6) {
                     this.jjtree.clearNodeScope(var5);
                     var6 = false;
                  } else {
                     this.jjtree.popNode();
                  }

                  if (var43 instanceof RuntimeException) {
                     throw (RuntimeException)var43;
                  }

                  if (var43 instanceof ParseException) {
                     throw (ParseException)var43;
                  }

                  throw (Error)var43;
               } finally {
                  if (var6) {
                     this.jjtree.closeNodeScope(var5, 2);
                  }

               }
            case 17:
               ASTFunNode var7 = new ASTFunNode(2);
               boolean var8 = true;
               this.jjtree.openNodeScope(var7);

               try {
                  this.jj_consume_token(17);
                  this.AdditiveExpression();
                  this.jjtree.closeNodeScope(var7, 2);
                  var8 = false;
                  var7.setFunction(ParserConstants.tokenImage[17], new Comparative(3));
                  continue;
               } catch (Throwable var49) {
                  if (var8) {
                     this.jjtree.clearNodeScope(var7);
                     var8 = false;
                  } else {
                     this.jjtree.popNode();
                  }

                  if (var49 instanceof RuntimeException) {
                     throw (RuntimeException)var49;
                  }

                  if (var49 instanceof ParseException) {
                     throw (ParseException)var49;
                  }

                  throw (Error)var49;
               } finally {
                  if (var8) {
                     this.jjtree.closeNodeScope(var7, 2);
                  }

               }
            }
         case 15:
         default:
            this.jj_la1[5] = this.jj_gen;
            return;
         }
      }
   }

   public final void AdditiveExpression() throws ParseException {
      this.MultiplicativeExpression();

      while(true) {
         switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 21:
         case 22:
            switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 21:
               ASTFunNode var1 = new ASTFunNode(2);
               boolean var2 = true;
               this.jjtree.openNodeScope(var1);

               try {
                  this.jj_consume_token(21);
                  this.MultiplicativeExpression();
                  this.jjtree.closeNodeScope(var1, 2);
                  var2 = false;
                  var1.setFunction(ParserConstants.tokenImage[21], new Add());
                  continue;
               } catch (Throwable var15) {
                  if (var2) {
                     this.jjtree.clearNodeScope(var1);
                     var2 = false;
                  } else {
                     this.jjtree.popNode();
                  }

                  if (var15 instanceof RuntimeException) {
                     throw (RuntimeException)var15;
                  }

                  if (var15 instanceof ParseException) {
                     throw (ParseException)var15;
                  }

                  throw (Error)var15;
               } finally {
                  if (var2) {
                     this.jjtree.closeNodeScope(var1, 2);
                  }

               }
            case 22:
               ASTFunNode var3 = new ASTFunNode(2);
               boolean var4 = true;
               this.jjtree.openNodeScope(var3);

               try {
                  this.jj_consume_token(22);
                  this.MultiplicativeExpression();
                  this.jjtree.closeNodeScope(var3, 2);
                  var4 = false;
                  var3.setFunction(ParserConstants.tokenImage[22], new Subtract());
                  continue;
               } catch (Throwable var17) {
                  if (var4) {
                     this.jjtree.clearNodeScope(var3);
                     var4 = false;
                  } else {
                     this.jjtree.popNode();
                  }

                  if (var17 instanceof RuntimeException) {
                     throw (RuntimeException)var17;
                  }

                  if (var17 instanceof ParseException) {
                     throw (ParseException)var17;
                  }

                  throw (Error)var17;
               } finally {
                  if (var4) {
                     this.jjtree.closeNodeScope(var3, 2);
                  }

               }
            default:
               this.jj_la1[8] = this.jj_gen;
               this.jj_consume_token(-1);
               throw new ParseException();
            }
         default:
            this.jj_la1[7] = this.jj_gen;
            return;
         }
      }
   }

   public final void MultiplicativeExpression() throws ParseException {
      this.UnaryExpression();

      while(this.jj_2_2(1)) {
         ASTFunNode var1;
         boolean var2;
         if (this.jj_2_3(1)) {
            var1 = new ASTFunNode(2);
            var2 = true;
            this.jjtree.openNodeScope(var1);

            try {
               this.PowerExpression();
               this.jjtree.closeNodeScope(var1, 2);
               var2 = false;
               if (!this.jep.implicitMul) {
                  throw new ParseException("Syntax Error (implicit multiplication not enabled)");
               }

               var1.setFunction(ParserConstants.tokenImage[23], new Multiply());
            } catch (Throwable var43) {
               if (var2) {
                  this.jjtree.clearNodeScope(var1);
                  var2 = false;
               } else {
                  this.jjtree.popNode();
               }

               if (var43 instanceof RuntimeException) {
                  throw (RuntimeException)var43;
               }

               if (var43 instanceof ParseException) {
                  throw (ParseException)var43;
               }

               throw (Error)var43;
            } finally {
               if (var2) {
                  this.jjtree.closeNodeScope(var1, 2);
               }

            }
         } else {
            switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 23:
               var1 = new ASTFunNode(2);
               var2 = true;
               this.jjtree.openNodeScope(var1);

               try {
                  this.jj_consume_token(23);
                  this.UnaryExpression();
                  this.jjtree.closeNodeScope(var1, 2);
                  var2 = false;
                  var1.setFunction(ParserConstants.tokenImage[23], new Multiply());
                  break;
               } catch (Throwable var45) {
                  if (var2) {
                     this.jjtree.clearNodeScope(var1);
                     var2 = false;
                  } else {
                     this.jjtree.popNode();
                  }

                  if (var45 instanceof RuntimeException) {
                     throw (RuntimeException)var45;
                  }

                  if (var45 instanceof ParseException) {
                     throw (ParseException)var45;
                  }

                  throw (Error)var45;
               } finally {
                  if (var2) {
                     this.jjtree.closeNodeScope(var1, 2);
                  }

               }
            case 24:
               ASTFunNode var3 = new ASTFunNode(2);
               boolean var4 = true;
               this.jjtree.openNodeScope(var3);

               try {
                  this.jj_consume_token(24);
                  this.UnaryExpression();
                  this.jjtree.closeNodeScope(var3, 2);
                  var4 = false;
                  var3.setFunction(ParserConstants.tokenImage[24], new Divide());
                  break;
               } catch (Throwable var41) {
                  if (var4) {
                     this.jjtree.clearNodeScope(var3);
                     var4 = false;
                  } else {
                     this.jjtree.popNode();
                  }

                  if (var41 instanceof RuntimeException) {
                     throw (RuntimeException)var41;
                  }

                  if (var41 instanceof ParseException) {
                     throw (ParseException)var41;
                  }

                  throw (Error)var41;
               } finally {
                  if (var4) {
                     this.jjtree.closeNodeScope(var3, 2);
                  }

               }
            case 25:
               ASTFunNode var5 = new ASTFunNode(2);
               boolean var6 = true;
               this.jjtree.openNodeScope(var5);

               try {
                  this.jj_consume_token(25);
                  this.UnaryExpression();
                  this.jjtree.closeNodeScope(var5, 2);
                  var6 = false;
                  var5.setFunction(ParserConstants.tokenImage[25], new Modulus());
                  break;
               } catch (Throwable var47) {
                  if (var6) {
                     this.jjtree.clearNodeScope(var5);
                     var6 = false;
                  } else {
                     this.jjtree.popNode();
                  }

                  if (var47 instanceof RuntimeException) {
                     throw (RuntimeException)var47;
                  }

                  if (var47 instanceof ParseException) {
                     throw (ParseException)var47;
                  }

                  throw (Error)var47;
               } finally {
                  if (var6) {
                     this.jjtree.closeNodeScope(var5, 2);
                  }

               }
            default:
               this.jj_la1[9] = this.jj_gen;
               this.jj_consume_token(-1);
               throw new ParseException();
            }
         }
      }

   }

   public final void UnaryExpression() throws ParseException {
      switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
      case 21:
         this.jj_consume_token(21);
         this.UnaryExpression();
         break;
      case 22:
         ASTFunNode var1 = new ASTFunNode(2);
         boolean var2 = true;
         this.jjtree.openNodeScope(var1);

         try {
            this.jj_consume_token(22);
            this.UnaryExpression();
            this.jjtree.closeNodeScope(var1, 1);
            var2 = false;
            var1.setFunction(ParserConstants.tokenImage[22], new UMinus());
            break;
         } catch (Throwable var15) {
            if (var2) {
               this.jjtree.clearNodeScope(var1);
               var2 = false;
            } else {
               this.jjtree.popNode();
            }

            if (var15 instanceof RuntimeException) {
               throw (RuntimeException)var15;
            }

            if (var15 instanceof ParseException) {
               throw (ParseException)var15;
            }

            throw (Error)var15;
         } finally {
            if (var2) {
               this.jjtree.closeNodeScope(var1, 1);
            }

         }
      case 26:
         ASTFunNode var3 = new ASTFunNode(2);
         boolean var4 = true;
         this.jjtree.openNodeScope(var3);

         try {
            this.jj_consume_token(26);
            this.UnaryExpression();
            this.jjtree.closeNodeScope(var3, 1);
            var4 = false;
            var3.setFunction(ParserConstants.tokenImage[26], new Not());
            break;
         } catch (Throwable var17) {
            if (var4) {
               this.jjtree.clearNodeScope(var3);
               var4 = false;
            } else {
               this.jjtree.popNode();
            }

            if (var17 instanceof RuntimeException) {
               throw (RuntimeException)var17;
            }

            if (var17 instanceof ParseException) {
               throw (ParseException)var17;
            }

            throw (Error)var17;
         } finally {
            if (var4) {
               this.jjtree.closeNodeScope(var3, 1);
            }

         }
      default:
         this.jj_la1[10] = this.jj_gen;
         if (!this.jj_2_4(1)) {
            this.jj_consume_token(-1);
            throw new ParseException();
         }

         this.PowerExpression();
      }

   }

   public final void PowerExpression() throws ParseException {
      this.UnaryExpressionNotPlusMinus();
      switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
      case 27:
         ASTFunNode var1 = new ASTFunNode(2);
         boolean var2 = true;
         this.jjtree.openNodeScope(var1);

         try {
            this.jj_consume_token(27);
            this.UnaryExpression();
            this.jjtree.closeNodeScope(var1, 2);
            var2 = false;
            var1.setFunction(ParserConstants.tokenImage[27], new Power());
            break;
         } catch (Throwable var7) {
            if (var2) {
               this.jjtree.clearNodeScope(var1);
               var2 = false;
            } else {
               this.jjtree.popNode();
            }

            if (var7 instanceof RuntimeException) {
               throw (RuntimeException)var7;
            }

            if (var7 instanceof ParseException) {
               throw (ParseException)var7;
            }

            throw (Error)var7;
         } finally {
            if (var2) {
               this.jjtree.closeNodeScope(var1, 2);
            }

         }
      default:
         this.jj_la1[11] = this.jj_gen;
      }

   }

   public final void UnaryExpressionNotPlusMinus() throws ParseException {
      switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
      case 5:
      case 7:
      case 9:
      case 31:
         this.AnyConstant();
         break;
      default:
         this.jj_la1[13] = this.jj_gen;
         if (this.jj_2_5(1)) {
            if (this.getToken(1).kind == 10 && this.jep.funTab.containsKey(this.getToken(1).image)) {
               this.Function();
            } else {
               switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
               case 10:
                  this.Variable();
                  break;
               default:
                  this.jj_la1[12] = this.jj_gen;
                  this.jj_consume_token(-1);
                  throw new ParseException();
               }
            }
         } else {
            switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 28:
               this.jj_consume_token(28);
               this.Expression();
               this.jj_consume_token(29);
               break;
            default:
               this.jj_la1[14] = this.jj_gen;
               this.jj_consume_token(-1);
               throw new ParseException();
            }
         }
      }

   }

   public final void Variable() throws ParseException {
      String var1 = "";
      ASTVarNode var2 = new ASTVarNode(3);
      boolean var3 = true;
      this.jjtree.openNodeScope(var2);

      try {
         var1 = this.Identifier();
         this.jjtree.closeNodeScope(var2, true);
         var3 = false;
         if (this.jep.symTab.containsKey(var1)) {
            var2.setName(var1);
         } else if (this.jep.allowUndeclared) {
            this.jep.symTab.put(var1, new Double(0.0D));
            var2.setName(var1);
         } else {
            this.addToErrorList("Unrecognized symbol \"" + var1 + "\"");
         }
      } catch (Throwable var8) {
         if (var3) {
            this.jjtree.clearNodeScope(var2);
            var3 = false;
         } else {
            this.jjtree.popNode();
         }

         if (var8 instanceof RuntimeException) {
            throw (RuntimeException)var8;
         }

         if (var8 instanceof ParseException) {
            throw (ParseException)var8;
         }

         throw (Error)var8;
      } finally {
         if (var3) {
            this.jjtree.closeNodeScope(var2, true);
         }

      }

   }

   public final void Function() throws ParseException {
      int var1 = 0;
      String var2 = "";
      ASTFunNode var3 = new ASTFunNode(2);
      boolean var4 = true;
      this.jjtree.openNodeScope(var3);

      try {
         var2 = this.Identifier();
         if (this.jep.funTab.containsKey(var2)) {
            var1 = ((PostfixMathCommandI)this.jep.funTab.get(var2)).getNumberOfParameters();
            var3.setFunction(var2, (PostfixMathCommandI)this.jep.funTab.get(var2));
         } else {
            this.addToErrorList("!!! Unrecognized function \"" + var2 + "\"");
         }

         this.jj_consume_token(28);
         this.ArgumentList(var1, var2);
         this.jj_consume_token(29);
      } catch (Throwable var9) {
         if (var4) {
            this.jjtree.clearNodeScope(var3);
            var4 = false;
         } else {
            this.jjtree.popNode();
         }

         if (var9 instanceof RuntimeException) {
            throw (RuntimeException)var9;
         }

         if (var9 instanceof ParseException) {
            throw (ParseException)var9;
         }

         throw (Error)var9;
      } finally {
         if (var4) {
            this.jjtree.closeNodeScope(var3, true);
         }

      }

   }

   public final void ArgumentList(int var1, String var2) throws ParseException {
      int var3 = 0;
      String var4 = "";
      if (this.jj_2_6(1)) {
         this.Expression();
         ++var3;

         label30:
         while(true) {
            switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 30:
               this.jj_consume_token(30);
               this.Expression();
               ++var3;
               break;
            default:
               this.jj_la1[15] = this.jj_gen;
               break label30;
            }
         }
      }

      if (var1 != var3 && var1 != -1) {
         var4 = "Function \"" + var2 + "\" requires " + var1 + " parameter";
         if (var1 != 1) {
            var4 = var4 + "s";
         }

         this.addToErrorList(var4);
      }

   }

   public final String Identifier() throws ParseException {
      Token var1 = this.jj_consume_token(10);
      return var1.image;
   }

   public final void AnyConstant() throws ParseException {
      ASTConstant var1 = new ASTConstant(4);
      boolean var2 = true;
      this.jjtree.openNodeScope(var1);

      try {
         switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 5:
         case 7:
            Object var11 = this.RealConstant();
            this.jjtree.closeNodeScope(var1, true);
            var2 = false;
            var1.setValue(var11);
            break;
         case 9:
            Token var3 = this.jj_consume_token(9);
            this.jjtree.closeNodeScope(var1, true);
            var2 = false;
            String var5 = var3.image.substring(1, var3.image.length() - 1);
            var5 = this.replaceEscape(var5);
            var1.setValue(var5);
            break;
         case 31:
            Vector var4 = this.Array();
            this.jjtree.closeNodeScope(var1, true);
            var2 = false;
            var1.setValue(var4);
            break;
         default:
            this.jj_la1[16] = this.jj_gen;
            this.jj_consume_token(-1);
            throw new ParseException();
         }
      } catch (Throwable var9) {
         if (var2) {
            this.jjtree.clearNodeScope(var1);
            var2 = false;
         } else {
            this.jjtree.popNode();
         }

         if (var9 instanceof RuntimeException) {
            throw (RuntimeException)var9;
         }

         if (var9 instanceof ParseException) {
            throw (ParseException)var9;
         }

         throw (Error)var9;
      } finally {
         if (var2) {
            this.jjtree.closeNodeScope(var1, true);
         }

      }

   }

   public final Vector Array() throws ParseException {
      Vector var2 = new Vector();
      this.jj_consume_token(31);
      Object var1 = this.RealConstant();
      var2.addElement(var1);

      while(true) {
         switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 30:
            this.jj_consume_token(30);
            var1 = this.RealConstant();
            var2.addElement(var1);
            break;
         default:
            this.jj_la1[17] = this.jj_gen;
            this.jj_consume_token(32);
            return var2;
         }
      }
   }

   public final Object RealConstant() throws ParseException {
      Token var1;
      switch(this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
      case 5:
         var1 = this.jj_consume_token(5);
         break;
      case 7:
         var1 = this.jj_consume_token(7);
         break;
      default:
         this.jj_la1[18] = this.jj_gen;
         this.jj_consume_token(-1);
         throw new ParseException();
      }

      Object var2;
      try {
         Double var3 = new Double(var1.image);
         var2 = this.jep.getNumberFactory().createNumber(var3);
      } catch (Exception var4) {
         var2 = null;
         this.addToErrorList("Can't parse \"" + var1.image + "\"");
      }

      return var2;
   }

   private final boolean jj_2_1(int var1) {
      this.jj_la = var1;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean var2 = !this.jj_3_1();
      this.jj_save(0, var1);
      return var2;
   }

   private final boolean jj_2_2(int var1) {
      this.jj_la = var1;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean var2 = !this.jj_3_2();
      this.jj_save(1, var1);
      return var2;
   }

   private final boolean jj_2_3(int var1) {
      this.jj_la = var1;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean var2 = !this.jj_3_3();
      this.jj_save(2, var1);
      return var2;
   }

   private final boolean jj_2_4(int var1) {
      this.jj_la = var1;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean var2 = !this.jj_3_4();
      this.jj_save(3, var1);
      return var2;
   }

   private final boolean jj_2_5(int var1) {
      this.jj_la = var1;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean var2 = !this.jj_3_5();
      this.jj_save(4, var1);
      return var2;
   }

   private final boolean jj_2_6(int var1) {
      this.jj_la = var1;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean var2 = !this.jj_3_6();
      this.jj_save(5, var1);
      return var2;
   }

   private final boolean jj_3R_15() {
      if (this.jj_3R_19()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_33() {
      if (this.jj_3R_36()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_22() {
      if (this.jj_scan_token(28)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_25() {
      Token var1 = this.jj_scanpos;
      if (this.jj_3R_27()) {
         this.jj_scanpos = var1;
         if (this.jj_3R_28()) {
            this.jj_scanpos = var1;
            if (this.jj_3R_29()) {
               return true;
            }

            if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      return false;
   }

   private final boolean jj_3R_27() {
      if (this.jj_scan_token(9)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_16() {
      if (this.jj_3R_20()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_14() {
      if (this.jj_3R_18()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3_5() {
      Token var1 = this.jj_scanpos;
      this.lookingAhead = true;
      this.jj_semLA = this.getToken(1).kind == 10 && this.jep.funTab.containsKey(this.getToken(1).image);
      this.lookingAhead = false;
      if (this.jj_semLA && !this.jj_3R_14()) {
         if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }
      } else {
         this.jj_scanpos = var1;
         if (this.jj_3R_15()) {
            return true;
         }

         if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }
      }

      return false;
   }

   private final boolean jj_3R_17() {
      Token var1 = this.jj_scanpos;
      if (this.jj_3R_21()) {
         this.jj_scanpos = var1;
         if (this.jj_3_5()) {
            this.jj_scanpos = var1;
            if (this.jj_3R_22()) {
               return true;
            }

            if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      return false;
   }

   private final boolean jj_3R_21() {
      if (this.jj_3R_25()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_9() {
      if (this.jj_3R_16()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_23() {
      if (this.jj_scan_token(10)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_30() {
      if (this.jj_3R_33()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3_1() {
      if (this.jj_3R_9()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_13() {
      if (this.jj_3R_17()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3_6() {
      if (this.jj_3R_9()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3_4() {
      if (this.jj_3R_13()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_35() {
      if (this.jj_scan_token(7)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_34() {
      if (this.jj_scan_token(5)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_31() {
      Token var1 = this.jj_scanpos;
      if (this.jj_3R_34()) {
         this.jj_scanpos = var1;
         if (this.jj_3R_35()) {
            return true;
         }

         if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      return false;
   }

   private final boolean jj_3R_39() {
      if (this.jj_scan_token(26)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_26() {
      if (this.jj_3R_30()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_38() {
      if (this.jj_scan_token(22)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_36() {
      Token var1 = this.jj_scanpos;
      if (this.jj_3R_37()) {
         this.jj_scanpos = var1;
         if (this.jj_3R_38()) {
            this.jj_scanpos = var1;
            if (this.jj_3R_39()) {
               this.jj_scanpos = var1;
               if (this.jj_3_4()) {
                  return true;
               }

               if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                  return false;
               }
            } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      return false;
   }

   private final boolean jj_3R_37() {
      if (this.jj_scan_token(21)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_18() {
      if (this.jj_3R_23()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_12() {
      if (this.jj_scan_token(25)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_24() {
      if (this.jj_3R_26()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_32() {
      if (this.jj_scan_token(31)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_11() {
      if (this.jj_scan_token(24)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_10() {
      if (this.jj_scan_token(23)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_20() {
      if (this.jj_3R_24()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_29() {
      if (this.jj_3R_32()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_19() {
      if (this.jj_3R_23()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_28() {
      if (this.jj_3R_31()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3_2() {
      Token var1 = this.jj_scanpos;
      if (this.jj_3_3()) {
         this.jj_scanpos = var1;
         if (this.jj_3R_10()) {
            this.jj_scanpos = var1;
            if (this.jj_3R_11()) {
               this.jj_scanpos = var1;
               if (this.jj_3R_12()) {
                  return true;
               }

               if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
                  return false;
               }
            } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
               return false;
            }
         } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            return false;
         }
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      return false;
   }

   private final boolean jj_3_3() {
      if (this.jj_3R_13()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   public Parser(InputStream var1) {
      this.jj_input_stream = new JavaCharStream(var1, 1, 1);
      this.token_source = new ParserTokenManager(this.jj_input_stream);
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      int var2;
      for(var2 = 0; var2 < 19; ++var2) {
         this.jj_la1[var2] = -1;
      }

      for(var2 = 0; var2 < this.jj_2_rtns.length; ++var2) {
         this.jj_2_rtns[var2] = new Parser.JJCalls();
      }

   }

   public void ReInit(InputStream var1) {
      this.jj_input_stream.ReInit((InputStream)var1, 1, 1);
      this.token_source.ReInit(this.jj_input_stream);
      this.token = new Token();
      this.jj_ntk = -1;
      this.jjtree.reset();
      this.jj_gen = 0;

      int var2;
      for(var2 = 0; var2 < 19; ++var2) {
         this.jj_la1[var2] = -1;
      }

      for(var2 = 0; var2 < this.jj_2_rtns.length; ++var2) {
         this.jj_2_rtns[var2] = new Parser.JJCalls();
      }

   }

   public Parser(Reader var1) {
      this.jj_input_stream = new JavaCharStream(var1, 1, 1);
      this.token_source = new ParserTokenManager(this.jj_input_stream);
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      int var2;
      for(var2 = 0; var2 < 19; ++var2) {
         this.jj_la1[var2] = -1;
      }

      for(var2 = 0; var2 < this.jj_2_rtns.length; ++var2) {
         this.jj_2_rtns[var2] = new Parser.JJCalls();
      }

   }

   public void ReInit(Reader var1) {
      this.jj_input_stream.ReInit((Reader)var1, 1, 1);
      this.token_source.ReInit(this.jj_input_stream);
      this.token = new Token();
      this.jj_ntk = -1;
      this.jjtree.reset();
      this.jj_gen = 0;

      int var2;
      for(var2 = 0; var2 < 19; ++var2) {
         this.jj_la1[var2] = -1;
      }

      for(var2 = 0; var2 < this.jj_2_rtns.length; ++var2) {
         this.jj_2_rtns[var2] = new Parser.JJCalls();
      }

   }

   public Parser(ParserTokenManager var1) {
      this.token_source = var1;
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      int var2;
      for(var2 = 0; var2 < 19; ++var2) {
         this.jj_la1[var2] = -1;
      }

      for(var2 = 0; var2 < this.jj_2_rtns.length; ++var2) {
         this.jj_2_rtns[var2] = new Parser.JJCalls();
      }

   }

   public void ReInit(ParserTokenManager var1) {
      this.token_source = var1;
      this.token = new Token();
      this.jj_ntk = -1;
      this.jjtree.reset();
      this.jj_gen = 0;

      int var2;
      for(var2 = 0; var2 < 19; ++var2) {
         this.jj_la1[var2] = -1;
      }

      for(var2 = 0; var2 < this.jj_2_rtns.length; ++var2) {
         this.jj_2_rtns[var2] = new Parser.JJCalls();
      }

   }

   private final Token jj_consume_token(int var1) throws ParseException {
      Token var2;
      if ((var2 = this.token).next != null) {
         this.token = this.token.next;
      } else {
         this.token = this.token.next = this.token_source.getNextToken();
      }

      this.jj_ntk = -1;
      if (this.token.kind != var1) {
         this.token = var2;
         this.jj_kind = var1;
         throw this.generateParseException();
      } else {
         ++this.jj_gen;
         if (++this.jj_gc > 100) {
            this.jj_gc = 0;

            for(int var3 = 0; var3 < this.jj_2_rtns.length; ++var3) {
               for(Parser.JJCalls var4 = this.jj_2_rtns[var3]; var4 != null; var4 = var4.next) {
                  if (var4.gen < this.jj_gen) {
                     var4.first = null;
                  }
               }
            }
         }

         return this.token;
      }
   }

   private final boolean jj_scan_token(int var1) {
      if (this.jj_scanpos == this.jj_lastpos) {
         --this.jj_la;
         if (this.jj_scanpos.next == null) {
            this.jj_lastpos = this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken();
         } else {
            this.jj_lastpos = this.jj_scanpos = this.jj_scanpos.next;
         }
      } else {
         this.jj_scanpos = this.jj_scanpos.next;
      }

      if (this.jj_rescan) {
         int var2 = 0;

         Token var3;
         for(var3 = this.token; var3 != null && var3 != this.jj_scanpos; var3 = var3.next) {
            ++var2;
         }

         if (var3 != null) {
            this.jj_add_error_token(var1, var2);
         }
      }

      return this.jj_scanpos.kind != var1;
   }

   public final Token getNextToken() {
      if (this.token.next != null) {
         this.token = this.token.next;
      } else {
         this.token = this.token.next = this.token_source.getNextToken();
      }

      this.jj_ntk = -1;
      ++this.jj_gen;
      return this.token;
   }

   public final Token getToken(int var1) {
      Token var2 = this.lookingAhead ? this.jj_scanpos : this.token;

      for(int var3 = 0; var3 < var1; ++var3) {
         if (var2.next != null) {
            var2 = var2.next;
         } else {
            var2 = var2.next = this.token_source.getNextToken();
         }
      }

      return var2;
   }

   private final int jj_ntk() {
      return (this.jj_nt = this.token.next) == null ? (this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind) : (this.jj_ntk = this.jj_nt.kind);
   }

   private void jj_add_error_token(int var1, int var2) {
      if (var2 < 100) {
         if (var2 == this.jj_endpos + 1) {
            this.jj_lasttokens[this.jj_endpos++] = var1;
         } else if (this.jj_endpos != 0) {
            this.jj_expentry = new int[this.jj_endpos];

            for(int var3 = 0; var3 < this.jj_endpos; ++var3) {
               this.jj_expentry[var3] = this.jj_lasttokens[var3];
            }

            boolean var7 = false;
            Enumeration var4 = this.jj_expentries.elements();

            label48:
            do {
               int[] var5;
               do {
                  if (!var4.hasMoreElements()) {
                     break label48;
                  }

                  var5 = (int[])((int[])var4.nextElement());
               } while(var5.length != this.jj_expentry.length);

               var7 = true;

               for(int var6 = 0; var6 < this.jj_expentry.length; ++var6) {
                  if (var5[var6] != this.jj_expentry[var6]) {
                     var7 = false;
                     break;
                  }
               }
            } while(!var7);

            if (!var7) {
               this.jj_expentries.addElement(this.jj_expentry);
            }

            if (var2 != 0) {
               this.jj_lasttokens[(this.jj_endpos = var2) - 1] = var1;
            }
         }

      }
   }

   public final ParseException generateParseException() {
      this.jj_expentries.removeAllElements();
      boolean[] var1 = new boolean[33];

      int var2;
      for(var2 = 0; var2 < 33; ++var2) {
         var1[var2] = false;
      }

      if (this.jj_kind >= 0) {
         var1[this.jj_kind] = true;
         this.jj_kind = -1;
      }

      int var3;
      for(var2 = 0; var2 < 19; ++var2) {
         if (this.jj_la1[var2] == this.jj_gen) {
            for(var3 = 0; var3 < 32; ++var3) {
               if ((this.jj_la1_0[var2] & 1 << var3) != 0) {
                  var1[var3] = true;
               }

               if ((this.jj_la1_1[var2] & 1 << var3) != 0) {
                  var1[32 + var3] = true;
               }
            }
         }
      }

      for(var2 = 0; var2 < 33; ++var2) {
         if (var1[var2]) {
            this.jj_expentry = new int[1];
            this.jj_expentry[0] = var2;
            this.jj_expentries.addElement(this.jj_expentry);
         }
      }

      this.jj_endpos = 0;
      this.jj_rescan_token();
      this.jj_add_error_token(0, 0);
      int[][] var4 = new int[this.jj_expentries.size()][];

      for(var3 = 0; var3 < this.jj_expentries.size(); ++var3) {
         var4[var3] = (int[])((int[])this.jj_expentries.elementAt(var3));
      }

      return new ParseException(this.token, var4, ParserConstants.tokenImage);
   }

   public final void enable_tracing() {
   }

   public final void disable_tracing() {
   }

   private final void jj_rescan_token() {
      this.jj_rescan = true;

      for(int var1 = 0; var1 < 6; ++var1) {
         Parser.JJCalls var2 = this.jj_2_rtns[var1];

         do {
            if (var2.gen > this.jj_gen) {
               this.jj_la = var2.arg;
               this.jj_lastpos = this.jj_scanpos = var2.first;
               switch(var1) {
               case 0:
                  this.jj_3_1();
                  break;
               case 1:
                  this.jj_3_2();
                  break;
               case 2:
                  this.jj_3_3();
                  break;
               case 3:
                  this.jj_3_4();
                  break;
               case 4:
                  this.jj_3_5();
                  break;
               case 5:
                  this.jj_3_6();
               }
            }

            var2 = var2.next;
         } while(var2 != null);
      }

      this.jj_rescan = false;
   }

   private final void jj_save(int var1, int var2) {
      Parser.JJCalls var3;
      for(var3 = this.jj_2_rtns[var1]; var3.gen > this.jj_gen; var3 = var3.next) {
         if (var3.next == null) {
            var3 = var3.next = new Parser.JJCalls();
            break;
         }
      }

      var3.gen = this.jj_gen + var2 - this.jj_la;
      var3.first = this.token;
      var3.arg = var2;
   }

   static final class JJCalls {
      int gen;
      Token first;
      int arg;
      Parser.JJCalls next;
   }
}
