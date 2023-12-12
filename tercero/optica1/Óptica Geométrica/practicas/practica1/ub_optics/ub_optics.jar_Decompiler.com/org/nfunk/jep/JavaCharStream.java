package org.nfunk.jep;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public final class JavaCharStream {
   public static final boolean staticFlag = false;
   public int bufpos;
   int bufsize;
   int available;
   int tokenBegin;
   private int[] bufline;
   private int[] bufcolumn;
   private int column;
   private int line;
   private boolean prevCharIsCR;
   private boolean prevCharIsLF;
   private Reader inputStream;
   private char[] nextCharBuf;
   private char[] buffer;
   private int maxNextCharInd;
   private int nextCharInd;
   private int inBuf;

   static final int hexval(char var0) throws IOException {
      switch(var0) {
      case '0':
         return 0;
      case '1':
         return 1;
      case '2':
         return 2;
      case '3':
         return 3;
      case '4':
         return 4;
      case '5':
         return 5;
      case '6':
         return 6;
      case '7':
         return 7;
      case '8':
         return 8;
      case '9':
         return 9;
      case ':':
      case ';':
      case '<':
      case '=':
      case '>':
      case '?':
      case '@':
      case 'G':
      case 'H':
      case 'I':
      case 'J':
      case 'K':
      case 'L':
      case 'M':
      case 'N':
      case 'O':
      case 'P':
      case 'Q':
      case 'R':
      case 'S':
      case 'T':
      case 'U':
      case 'V':
      case 'W':
      case 'X':
      case 'Y':
      case 'Z':
      case '[':
      case '\\':
      case ']':
      case '^':
      case '_':
      case '`':
      default:
         throw new IOException();
      case 'A':
      case 'a':
         return 10;
      case 'B':
      case 'b':
         return 11;
      case 'C':
      case 'c':
         return 12;
      case 'D':
      case 'd':
         return 13;
      case 'E':
      case 'e':
         return 14;
      case 'F':
      case 'f':
         return 15;
      }
   }

   private final void ExpandBuff(boolean var1) {
      char[] var2 = new char[this.bufsize + 2048];
      int[] var3 = new int[this.bufsize + 2048];
      int[] var4 = new int[this.bufsize + 2048];

      try {
         if (var1) {
            System.arraycopy(this.buffer, this.tokenBegin, var2, 0, this.bufsize - this.tokenBegin);
            System.arraycopy(this.buffer, 0, var2, this.bufsize - this.tokenBegin, this.bufpos);
            this.buffer = var2;
            System.arraycopy(this.bufline, this.tokenBegin, var3, 0, this.bufsize - this.tokenBegin);
            System.arraycopy(this.bufline, 0, var3, this.bufsize - this.tokenBegin, this.bufpos);
            this.bufline = var3;
            System.arraycopy(this.bufcolumn, this.tokenBegin, var4, 0, this.bufsize - this.tokenBegin);
            System.arraycopy(this.bufcolumn, 0, var4, this.bufsize - this.tokenBegin, this.bufpos);
            this.bufcolumn = var4;
            this.bufpos += this.bufsize - this.tokenBegin;
         } else {
            System.arraycopy(this.buffer, this.tokenBegin, var2, 0, this.bufsize - this.tokenBegin);
            this.buffer = var2;
            System.arraycopy(this.bufline, this.tokenBegin, var3, 0, this.bufsize - this.tokenBegin);
            this.bufline = var3;
            System.arraycopy(this.bufcolumn, this.tokenBegin, var4, 0, this.bufsize - this.tokenBegin);
            this.bufcolumn = var4;
            this.bufpos -= this.tokenBegin;
         }
      } catch (Throwable var6) {
         throw new Error(var6.getMessage());
      }

      this.available = this.bufsize += 2048;
      this.tokenBegin = 0;
   }

   private final void FillBuff() throws IOException {
      if (this.maxNextCharInd == 4096) {
         this.maxNextCharInd = this.nextCharInd = 0;
      }

      try {
         int var1;
         if ((var1 = this.inputStream.read(this.nextCharBuf, this.maxNextCharInd, 4096 - this.maxNextCharInd)) == -1) {
            this.inputStream.close();
            throw new IOException();
         } else {
            this.maxNextCharInd += var1;
         }
      } catch (IOException var3) {
         if (this.bufpos != 0) {
            --this.bufpos;
            this.backup(0);
         } else {
            this.bufline[this.bufpos] = this.line;
            this.bufcolumn[this.bufpos] = this.column;
         }

         throw var3;
      }
   }

   private final char ReadByte() throws IOException {
      if (++this.nextCharInd >= this.maxNextCharInd) {
         this.FillBuff();
      }

      return this.nextCharBuf[this.nextCharInd];
   }

   public final char BeginToken() throws IOException {
      if (this.inBuf > 0) {
         --this.inBuf;
         if (++this.bufpos == this.bufsize) {
            this.bufpos = 0;
         }

         this.tokenBegin = this.bufpos;
         return this.buffer[this.bufpos];
      } else {
         this.tokenBegin = 0;
         this.bufpos = -1;
         return this.readChar();
      }
   }

   private final void AdjustBuffSize() {
      if (this.available == this.bufsize) {
         if (this.tokenBegin > 2048) {
            this.bufpos = 0;
            this.available = this.tokenBegin;
         } else {
            this.ExpandBuff(false);
         }
      } else if (this.available > this.tokenBegin) {
         this.available = this.bufsize;
      } else if (this.tokenBegin - this.available < 2048) {
         this.ExpandBuff(true);
      } else {
         this.available = this.tokenBegin;
      }

   }

   private final void UpdateLineColumn(char var1) {
      ++this.column;
      if (this.prevCharIsLF) {
         this.prevCharIsLF = false;
         this.line += this.column = 1;
      } else if (this.prevCharIsCR) {
         this.prevCharIsCR = false;
         if (var1 == '\n') {
            this.prevCharIsLF = true;
         } else {
            this.line += this.column = 1;
         }
      }

      switch(var1) {
      case '\t':
         --this.column;
         this.column += 8 - (this.column & 7);
         break;
      case '\n':
         this.prevCharIsLF = true;
      case '\u000b':
      case '\f':
      default:
         break;
      case '\r':
         this.prevCharIsCR = true;
      }

      this.bufline[this.bufpos] = this.line;
      this.bufcolumn[this.bufpos] = this.column;
   }

   public final char readChar() throws IOException {
      if (this.inBuf > 0) {
         --this.inBuf;
         if (++this.bufpos == this.bufsize) {
            this.bufpos = 0;
         }

         return this.buffer[this.bufpos];
      } else {
         if (++this.bufpos == this.available) {
            this.AdjustBuffSize();
         }

         char var1;
         if ((this.buffer[this.bufpos] = var1 = this.ReadByte()) != '\\') {
            this.UpdateLineColumn(var1);
            return var1;
         } else {
            this.UpdateLineColumn(var1);
            int var2 = 1;

            while(true) {
               if (++this.bufpos == this.available) {
                  this.AdjustBuffSize();
               }

               label80: {
                  try {
                     if ((this.buffer[this.bufpos] = var1 = this.ReadByte()) == '\\') {
                        break label80;
                     }

                     this.UpdateLineColumn(var1);
                     if (var1 != 'u' || (var2 & 1) != 1) {
                        this.backup(var2);
                        return '\\';
                     }

                     if (--this.bufpos < 0) {
                        this.bufpos = this.bufsize - 1;
                     }
                  } catch (IOException var5) {
                     if (var2 > 1) {
                        this.backup(var2);
                     }

                     return '\\';
                  }

                  try {
                     while((var1 = this.ReadByte()) == 'u') {
                        ++this.column;
                     }

                     this.buffer[this.bufpos] = var1 = (char)(hexval(var1) << 12 | hexval(this.ReadByte()) << 8 | hexval(this.ReadByte()) << 4 | hexval(this.ReadByte()));
                     this.column += 4;
                  } catch (IOException var4) {
                     throw new Error("Invalid escape character at line " + this.line + " column " + this.column + ".");
                  }

                  if (var2 == 1) {
                     return var1;
                  }

                  this.backup(var2 - 1);
                  return '\\';
               }

               this.UpdateLineColumn(var1);
               ++var2;
            }
         }
      }
   }

   /** @deprecated */
   public final int getColumn() {
      return this.bufcolumn[this.bufpos];
   }

   /** @deprecated */
   public final int getLine() {
      return this.bufline[this.bufpos];
   }

   public final int getEndColumn() {
      return this.bufcolumn[this.bufpos];
   }

   public final int getEndLine() {
      return this.bufline[this.bufpos];
   }

   public final int getBeginColumn() {
      return this.bufcolumn[this.tokenBegin];
   }

   public final int getBeginLine() {
      return this.bufline[this.tokenBegin];
   }

   public final void backup(int var1) {
      this.inBuf += var1;
      if ((this.bufpos -= var1) < 0) {
         this.bufpos += this.bufsize;
      }

   }

   public JavaCharStream(Reader var1, int var2, int var3, int var4) {
      this.bufpos = -1;
      this.column = 0;
      this.line = 1;
      this.prevCharIsCR = false;
      this.prevCharIsLF = false;
      this.maxNextCharInd = 0;
      this.nextCharInd = -1;
      this.inBuf = 0;
      this.inputStream = var1;
      this.line = var2;
      this.column = var3 - 1;
      this.available = this.bufsize = var4;
      this.buffer = new char[var4];
      this.bufline = new int[var4];
      this.bufcolumn = new int[var4];
      this.nextCharBuf = new char[4096];
   }

   public JavaCharStream(Reader var1, int var2, int var3) {
      this((Reader)var1, var2, var3, 4096);
   }

   public JavaCharStream(Reader var1) {
      this((Reader)var1, 1, 1, 4096);
   }

   public void ReInit(Reader var1, int var2, int var3, int var4) {
      this.inputStream = var1;
      this.line = var2;
      this.column = var3 - 1;
      if (this.buffer == null || var4 != this.buffer.length) {
         this.available = this.bufsize = var4;
         this.buffer = new char[var4];
         this.bufline = new int[var4];
         this.bufcolumn = new int[var4];
         this.nextCharBuf = new char[4096];
      }

      this.prevCharIsLF = this.prevCharIsCR = false;
      this.tokenBegin = this.inBuf = this.maxNextCharInd = 0;
      this.nextCharInd = this.bufpos = -1;
   }

   public void ReInit(Reader var1, int var2, int var3) {
      this.ReInit((Reader)var1, var2, var3, 4096);
   }

   public void ReInit(Reader var1) {
      this.ReInit((Reader)var1, 1, 1, 4096);
   }

   public JavaCharStream(InputStream var1, int var2, int var3, int var4) {
      this((Reader)(new InputStreamReader(var1)), var2, var3, 4096);
   }

   public JavaCharStream(InputStream var1, int var2, int var3) {
      this((InputStream)var1, var2, var3, 4096);
   }

   public JavaCharStream(InputStream var1) {
      this((InputStream)var1, 1, 1, 4096);
   }

   public void ReInit(InputStream var1, int var2, int var3, int var4) {
      this.ReInit((Reader)(new InputStreamReader(var1)), var2, var3, 4096);
   }

   public void ReInit(InputStream var1, int var2, int var3) {
      this.ReInit((InputStream)var1, var2, var3, 4096);
   }

   public void ReInit(InputStream var1) {
      this.ReInit((InputStream)var1, 1, 1, 4096);
   }

   public final String GetImage() {
      return this.bufpos >= this.tokenBegin ? new String(this.buffer, this.tokenBegin, this.bufpos - this.tokenBegin + 1) : new String(this.buffer, this.tokenBegin, this.bufsize - this.tokenBegin) + new String(this.buffer, 0, this.bufpos + 1);
   }

   public final char[] GetSuffix(int var1) {
      char[] var2 = new char[var1];
      if (this.bufpos + 1 >= var1) {
         System.arraycopy(this.buffer, this.bufpos - var1 + 1, var2, 0, var1);
      } else {
         System.arraycopy(this.buffer, this.bufsize - (var1 - this.bufpos - 1), var2, 0, var1 - this.bufpos - 1);
         System.arraycopy(this.buffer, 0, var2, var1 - this.bufpos - 1, this.bufpos + 1);
      }

      return var2;
   }

   public void Done() {
      this.nextCharBuf = null;
      this.buffer = null;
      this.bufline = null;
      this.bufcolumn = null;
   }

   public void adjustBeginLineColumn(int var1, int var2) {
      int var3 = this.tokenBegin;
      int var4;
      if (this.bufpos >= this.tokenBegin) {
         var4 = this.bufpos - this.tokenBegin + this.inBuf + 1;
      } else {
         var4 = this.bufsize - this.tokenBegin + this.bufpos + 1 + this.inBuf;
      }

      int var5 = 0;
      int var6 = 0;
      boolean var7 = false;
      boolean var8 = false;

      int var9;
      int var10000;
      for(var9 = 0; var5 < var4; ++var5) {
         var10000 = this.bufline[var6 = var3 % this.bufsize];
         ++var3;
         int var10;
         if (var10000 != this.bufline[var10 = var3 % this.bufsize]) {
            break;
         }

         this.bufline[var6] = var1;
         int var11 = var9 + this.bufcolumn[var10] - this.bufcolumn[var6];
         this.bufcolumn[var6] = var2 + var9;
         var9 = var11;
      }

      if (var5 < var4) {
         this.bufline[var6] = var1++;
         this.bufcolumn[var6] = var2 + var9;

         while(var5++ < var4) {
            var10000 = this.bufline[var6 = var3 % this.bufsize];
            ++var3;
            if (var10000 != this.bufline[var3 % this.bufsize]) {
               this.bufline[var6] = var1++;
            } else {
               this.bufline[var6] = var1;
            }
         }
      }

      this.line = this.bufline[var6];
      this.column = this.bufcolumn[var6];
   }
}
