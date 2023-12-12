package org.nfunk.jep;

import java.io.IOException;
import java.io.PrintStream;

public class ParserTokenManager implements ParserConstants {
   public PrintStream debugStream;
   static final long[] jjbitVec0 = new long[]{-2L, -1L, -1L, -1L};
   static final long[] jjbitVec2 = new long[]{0L, 0L, -1L, -1L};
   static final long[] jjbitVec3 = new long[]{2301339413881290750L, -16384L, 4294967295L, 432345564227567616L};
   static final long[] jjbitVec4 = new long[]{0L, 0L, 0L, -36028797027352577L};
   static final long[] jjbitVec5 = new long[]{0L, -1L, -1L, -1L};
   static final long[] jjbitVec6 = new long[]{-1L, -1L, 65535L, 0L};
   static final long[] jjbitVec7 = new long[]{-1L, -1L, 0L, 0L};
   static final long[] jjbitVec8 = new long[]{70368744177663L, 0L, 0L, 0L};
   static final int[] jjnextStates = new int[]{13, 14, 15, 20, 21, 6, 7, 9, 3, 4, 18, 19, 22, 23};
   public static final String[] jjstrLiteralImages = new String[]{"", null, null, null, null, null, null, null, null, null, null, null, null, ">", "<", "==", "<=", ">=", "!=", "&&", "||", "+", "-", "*", "/", "%", "!", "^", "(", ")", ",", "[", "]"};
   public static final String[] lexStateNames = new String[]{"DEFAULT"};
   static final long[] jjtoToken = new long[]{8589928097L};
   static final long[] jjtoSkip = new long[]{30L};
   private JavaCharStream input_stream;
   private final int[] jjrounds;
   private final int[] jjstateSet;
   protected char curChar;
   int curLexState;
   int defaultLexState;
   int jjnewStateCnt;
   int jjround;
   int jjmatchedPos;
   int jjmatchedKind;

   public void setDebugStream(PrintStream var1) {
      this.debugStream = var1;
   }

   private final int jjStopStringLiteralDfa_0(int var1, long var2) {
      switch(var1) {
      default:
         return -1;
      }
   }

   private final int jjStartNfa_0(int var1, long var2) {
      return this.jjMoveNfa_0(this.jjStopStringLiteralDfa_0(var1, var2), var1 + 1);
   }

   private final int jjStopAtPos(int var1, int var2) {
      this.jjmatchedKind = var2;
      this.jjmatchedPos = var1;
      return var1 + 1;
   }

   private final int jjStartNfaWithStates_0(int var1, int var2, int var3) {
      this.jjmatchedKind = var2;
      this.jjmatchedPos = var1;

      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var5) {
         return var1 + 1;
      }

      return this.jjMoveNfa_0(var3, var1 + 1);
   }

   private final int jjMoveStringLiteralDfa0_0() {
      switch(this.curChar) {
      case '!':
         this.jjmatchedKind = 26;
         return this.jjMoveStringLiteralDfa1_0(262144L);
      case '%':
         return this.jjStopAtPos(0, 25);
      case '&':
         return this.jjMoveStringLiteralDfa1_0(524288L);
      case '(':
         return this.jjStopAtPos(0, 28);
      case ')':
         return this.jjStopAtPos(0, 29);
      case '*':
         return this.jjStopAtPos(0, 23);
      case '+':
         return this.jjStopAtPos(0, 21);
      case ',':
         return this.jjStopAtPos(0, 30);
      case '-':
         return this.jjStopAtPos(0, 22);
      case '/':
         return this.jjStopAtPos(0, 24);
      case '<':
         this.jjmatchedKind = 14;
         return this.jjMoveStringLiteralDfa1_0(65536L);
      case '=':
         return this.jjMoveStringLiteralDfa1_0(32768L);
      case '>':
         this.jjmatchedKind = 13;
         return this.jjMoveStringLiteralDfa1_0(131072L);
      case '[':
         return this.jjStopAtPos(0, 31);
      case ']':
         return this.jjStopAtPos(0, 32);
      case '^':
         return this.jjStopAtPos(0, 27);
      case '|':
         return this.jjMoveStringLiteralDfa1_0(1048576L);
      default:
         return this.jjMoveNfa_0(0, 0);
      }
   }

   private final int jjMoveStringLiteralDfa1_0(long var1) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         this.jjStopStringLiteralDfa_0(0, var1);
         return 1;
      }

      switch(this.curChar) {
      case '&':
         if ((var1 & 524288L) != 0L) {
            return this.jjStopAtPos(1, 19);
         }
         break;
      case '=':
         if ((var1 & 32768L) != 0L) {
            return this.jjStopAtPos(1, 15);
         }

         if ((var1 & 65536L) != 0L) {
            return this.jjStopAtPos(1, 16);
         }

         if ((var1 & 131072L) != 0L) {
            return this.jjStopAtPos(1, 17);
         }

         if ((var1 & 262144L) != 0L) {
            return this.jjStopAtPos(1, 18);
         }
         break;
      case '|':
         if ((var1 & 1048576L) != 0L) {
            return this.jjStopAtPos(1, 20);
         }
      }

      return this.jjStartNfa_0(0, var1);
   }

   private final void jjCheckNAdd(int var1) {
      if (this.jjrounds[var1] != this.jjround) {
         this.jjstateSet[this.jjnewStateCnt++] = var1;
         this.jjrounds[var1] = this.jjround;
      }

   }

   private final void jjAddStates(int var1, int var2) {
      do {
         this.jjstateSet[this.jjnewStateCnt++] = jjnextStates[var1];
      } while(var1++ != var2);

   }

   private final void jjCheckNAddTwoStates(int var1, int var2) {
      this.jjCheckNAdd(var1);
      this.jjCheckNAdd(var2);
   }

   private final void jjCheckNAddStates(int var1, int var2) {
      do {
         this.jjCheckNAdd(jjnextStates[var1]);
      } while(var1++ != var2);

   }

   private final void jjCheckNAddStates(int var1) {
      this.jjCheckNAdd(jjnextStates[var1]);
      this.jjCheckNAdd(jjnextStates[var1 + 1]);
   }

   private final int jjMoveNfa_0(int var1, int var2) {
      int var3 = 0;
      this.jjnewStateCnt = 24;
      int var4 = 1;
      this.jjstateSet[0] = var1;
      int var5 = Integer.MAX_VALUE;

      while(true) {
         if (++this.jjround == Integer.MAX_VALUE) {
            this.ReInitRounds();
         }

         long var14;
         if (this.curChar < '@') {
            var14 = 1L << this.curChar;

            do {
               --var4;
               switch(this.jjstateSet[var4]) {
               case 0:
                  if ((287948901175001088L & var14) != 0L) {
                     if (var5 > 5) {
                        var5 = 5;
                     }

                     this.jjCheckNAddStates(0, 4);
                  } else if (this.curChar == '$') {
                     if (var5 > 10) {
                        var5 = 10;
                     }

                     this.jjCheckNAdd(11);
                  } else if (this.curChar == '"') {
                     this.jjCheckNAddStates(5, 7);
                  } else if (this.curChar == '.') {
                     this.jjCheckNAdd(1);
                  }
                  break;
               case 1:
                  if ((287948901175001088L & var14) != 0L) {
                     if (var5 > 7) {
                        var5 = 7;
                     }

                     this.jjCheckNAddTwoStates(1, 2);
                  }
               case 2:
               case 7:
               case 17:
               case 21:
               default:
                  break;
               case 3:
                  if ((43980465111040L & var14) != 0L) {
                     this.jjCheckNAdd(4);
                  }
                  break;
               case 4:
                  if ((287948901175001088L & var14) != 0L) {
                     if (var5 > 7) {
                        var5 = 7;
                     }

                     this.jjCheckNAdd(4);
                  }
                  break;
               case 5:
                  if (this.curChar == '"') {
                     this.jjCheckNAddStates(5, 7);
                  }
                  break;
               case 6:
                  if ((-17179878401L & var14) != 0L) {
                     this.jjCheckNAddStates(5, 7);
                  }
                  break;
               case 8:
                  if ((566935683072L & var14) != 0L) {
                     this.jjCheckNAddStates(5, 7);
                  }
                  break;
               case 9:
                  if (this.curChar == '"' && var5 > 9) {
                     var5 = 9;
                  }
                  break;
               case 10:
                  if (this.curChar == '$') {
                     if (var5 > 10) {
                        var5 = 10;
                     }

                     this.jjCheckNAdd(11);
                  }
                  break;
               case 11:
                  if ((287948969894477824L & var14) != 0L) {
                     if (var5 > 10) {
                        var5 = 10;
                     }

                     this.jjCheckNAdd(11);
                  }
                  break;
               case 12:
                  if ((287948901175001088L & var14) != 0L) {
                     if (var5 > 5) {
                        var5 = 5;
                     }

                     this.jjCheckNAddStates(0, 4);
                  }
                  break;
               case 13:
                  if ((287948901175001088L & var14) != 0L) {
                     if (var5 > 5) {
                        var5 = 5;
                     }

                     this.jjCheckNAdd(13);
                  }
                  break;
               case 14:
                  if ((287948901175001088L & var14) != 0L) {
                     this.jjCheckNAddTwoStates(14, 15);
                  }
                  break;
               case 15:
                  if (this.curChar == '.') {
                     if (var5 > 7) {
                        var5 = 7;
                     }

                     this.jjCheckNAddTwoStates(16, 17);
                  }
                  break;
               case 16:
                  if ((287948901175001088L & var14) != 0L) {
                     if (var5 > 7) {
                        var5 = 7;
                     }

                     this.jjCheckNAddTwoStates(16, 17);
                  }
                  break;
               case 18:
                  if ((43980465111040L & var14) != 0L) {
                     this.jjCheckNAdd(19);
                  }
                  break;
               case 19:
                  if ((287948901175001088L & var14) != 0L) {
                     if (var5 > 7) {
                        var5 = 7;
                     }

                     this.jjCheckNAdd(19);
                  }
                  break;
               case 20:
                  if ((287948901175001088L & var14) != 0L) {
                     this.jjCheckNAddTwoStates(20, 21);
                  }
                  break;
               case 22:
                  if ((43980465111040L & var14) != 0L) {
                     this.jjCheckNAdd(23);
                  }
                  break;
               case 23:
                  if ((287948901175001088L & var14) != 0L) {
                     if (var5 > 7) {
                        var5 = 7;
                     }

                     this.jjCheckNAdd(23);
                  }
               }
            } while(var4 != var3);
         } else if (this.curChar < 128) {
            var14 = 1L << (this.curChar & 63);

            do {
               --var4;
               switch(this.jjstateSet[var4]) {
               case 0:
               case 11:
                  if ((576460745995190270L & var14) != 0L) {
                     if (var5 > 10) {
                        var5 = 10;
                     }

                     this.jjCheckNAdd(11);
                  }
               case 1:
               case 3:
               case 4:
               case 5:
               case 9:
               case 10:
               case 12:
               case 13:
               case 14:
               case 15:
               case 16:
               case 18:
               case 19:
               case 20:
               default:
                  break;
               case 2:
                  if ((137438953504L & var14) != 0L) {
                     this.jjAddStates(8, 9);
                  }
                  break;
               case 6:
                  if ((-268435457L & var14) != 0L) {
                     this.jjCheckNAddStates(5, 7);
                  }
                  break;
               case 7:
                  if (this.curChar == '\\') {
                     this.jjstateSet[this.jjnewStateCnt++] = 8;
                  }
                  break;
               case 8:
                  if ((5700160604602368L & var14) != 0L) {
                     this.jjCheckNAddStates(5, 7);
                  }
                  break;
               case 17:
                  if ((137438953504L & var14) != 0L) {
                     this.jjAddStates(10, 11);
                  }
                  break;
               case 21:
                  if ((137438953504L & var14) != 0L) {
                     this.jjAddStates(12, 13);
                  }
               }
            } while(var4 != var3);
         } else {
            int var6 = this.curChar >> 8;
            int var7 = var6 >> 6;
            long var8 = 1L << (var6 & 63);
            int var10 = (this.curChar & 255) >> 6;
            long var11 = 1L << (this.curChar & 63);

            do {
               --var4;
               switch(this.jjstateSet[var4]) {
               case 0:
               case 11:
                  if (jjCanMove_1(var6, var7, var10, var8, var11)) {
                     if (var5 > 10) {
                        var5 = 10;
                     }

                     this.jjCheckNAdd(11);
                  }
                  break;
               case 6:
                  if (jjCanMove_0(var6, var7, var10, var8, var11)) {
                     this.jjAddStates(5, 7);
                  }
               }
            } while(var4 != var3);
         }

         if (var5 != Integer.MAX_VALUE) {
            this.jjmatchedKind = var5;
            this.jjmatchedPos = var2;
            var5 = Integer.MAX_VALUE;
         }

         ++var2;
         if ((var4 = this.jjnewStateCnt) == (var3 = 24 - (this.jjnewStateCnt = var3))) {
            return var2;
         }

         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var13) {
            return var2;
         }
      }
   }

   private static final boolean jjCanMove_0(int var0, int var1, int var2, long var3, long var5) {
      switch(var0) {
      case 0:
         return (jjbitVec2[var2] & var5) != 0L;
      default:
         return (jjbitVec0[var1] & var3) != 0L;
      }
   }

   private static final boolean jjCanMove_1(int var0, int var1, int var2, long var3, long var5) {
      switch(var0) {
      case 0:
         return (jjbitVec4[var2] & var5) != 0L;
      case 48:
         return (jjbitVec5[var2] & var5) != 0L;
      case 49:
         return (jjbitVec6[var2] & var5) != 0L;
      case 51:
         return (jjbitVec7[var2] & var5) != 0L;
      case 61:
         return (jjbitVec8[var2] & var5) != 0L;
      default:
         return (jjbitVec3[var1] & var3) != 0L;
      }
   }

   public ParserTokenManager(JavaCharStream var1) {
      this.debugStream = System.out;
      this.jjrounds = new int[24];
      this.jjstateSet = new int[48];
      this.curLexState = 0;
      this.defaultLexState = 0;
      this.input_stream = var1;
   }

   public ParserTokenManager(JavaCharStream var1, int var2) {
      this(var1);
      this.SwitchTo(var2);
   }

   public void ReInit(JavaCharStream var1) {
      this.jjmatchedPos = this.jjnewStateCnt = 0;
      this.curLexState = this.defaultLexState;
      this.input_stream = var1;
      this.ReInitRounds();
   }

   private final void ReInitRounds() {
      this.jjround = -2147483647;

      for(int var1 = 24; var1-- > 0; this.jjrounds[var1] = Integer.MIN_VALUE) {
      }

   }

   public void ReInit(JavaCharStream var1, int var2) {
      this.ReInit(var1);
      this.SwitchTo(var2);
   }

   public void SwitchTo(int var1) {
      if (var1 < 1 && var1 >= 0) {
         this.curLexState = var1;
      } else {
         throw new TokenMgrError("Error: Ignoring invalid lexical state : " + var1 + ". State unchanged.", 2);
      }
   }

   private final Token jjFillToken() {
      Token var1 = Token.newToken(this.jjmatchedKind);
      var1.kind = this.jjmatchedKind;
      String var2 = jjstrLiteralImages[this.jjmatchedKind];
      var1.image = var2 == null ? this.input_stream.GetImage() : var2;
      var1.beginLine = this.input_stream.getBeginLine();
      var1.beginColumn = this.input_stream.getBeginColumn();
      var1.endLine = this.input_stream.getEndLine();
      var1.endColumn = this.input_stream.getEndColumn();
      return var1;
   }

   public final Token getNextToken() {
      boolean var2 = false;

      while(true) {
         Token var1;
         try {
            this.curChar = this.input_stream.BeginToken();
         } catch (IOException var8) {
            this.jjmatchedKind = 0;
            var1 = this.jjFillToken();
            return var1;
         }

         try {
            this.input_stream.backup(0);

            while(this.curChar <= ' ' && (4294977024L & 1L << this.curChar) != 0L) {
               this.curChar = this.input_stream.BeginToken();
            }
         } catch (IOException var10) {
            continue;
         }

         this.jjmatchedKind = Integer.MAX_VALUE;
         this.jjmatchedPos = 0;
         int var11 = this.jjMoveStringLiteralDfa0_0();
         if (this.jjmatchedKind == Integer.MAX_VALUE) {
            int var3 = this.input_stream.getEndLine();
            int var4 = this.input_stream.getEndColumn();
            String var5 = null;
            boolean var6 = false;

            try {
               this.input_stream.readChar();
               this.input_stream.backup(1);
            } catch (IOException var9) {
               var6 = true;
               var5 = var11 <= 1 ? "" : this.input_stream.GetImage();
               if (this.curChar != '\n' && this.curChar != '\r') {
                  ++var4;
               } else {
                  ++var3;
                  var4 = 0;
               }
            }

            if (!var6) {
               this.input_stream.backup(1);
               var5 = var11 <= 1 ? "" : this.input_stream.GetImage();
            }

            throw new TokenMgrError(var6, this.curLexState, var3, var4, var5, this.curChar, 0);
         }

         if (this.jjmatchedPos + 1 < var11) {
            this.input_stream.backup(var11 - this.jjmatchedPos - 1);
         }

         if ((jjtoToken[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 63)) != 0L) {
            var1 = this.jjFillToken();
            return var1;
         }
      }
   }
}
