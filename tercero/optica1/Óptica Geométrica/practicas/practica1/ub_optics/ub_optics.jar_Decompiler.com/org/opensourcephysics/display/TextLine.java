package org.opensourcephysics.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.Stack;
import java.util.Vector;

public class TextLine {
   public static final int CENTER = 0;
   public static final int LEFT = 1;
   public static final int RIGHT = 2;
   public static final int SCIENTIFIC = 1;
   public static final int ALGEBRAIC = 2;
   static final int MINIMUM_SIZE = 6;
   protected double script_fraction;
   protected double sup_offset;
   protected double sub_offset;
   protected Font font;
   protected Color color;
   protected Color background;
   protected String text;
   protected String fontname;
   protected int fontsize;
   protected int fontstyle;
   protected int justification;
   protected int width;
   protected int ascent;
   protected int maxAscent;
   protected int descent;
   protected int maxDescent;
   protected int height;
   protected int leading;
   protected boolean parse;
   protected Graphics lg;
   protected Vector list;

   public TextLine() {
      this.script_fraction = 0.8D;
      this.sup_offset = 0.6D;
      this.sub_offset = 0.7D;
      this.font = null;
      this.color = null;
      this.background = null;
      this.text = null;
      this.fontname = "TimesRoman";
      this.fontsize = 0;
      this.fontstyle = 0;
      this.justification = 1;
      this.width = 0;
      this.ascent = 0;
      this.maxAscent = 0;
      this.descent = 0;
      this.maxDescent = 0;
      this.height = 0;
      this.leading = 0;
      this.parse = true;
      this.lg = null;
      this.list = new Vector(8, 4);
   }

   public TextLine(String var1) {
      this.script_fraction = 0.8D;
      this.sup_offset = 0.6D;
      this.sub_offset = 0.7D;
      this.font = null;
      this.color = null;
      this.background = null;
      this.text = null;
      this.fontname = "TimesRoman";
      this.fontsize = 0;
      this.fontstyle = 0;
      this.justification = 1;
      this.width = 0;
      this.ascent = 0;
      this.maxAscent = 0;
      this.descent = 0;
      this.maxDescent = 0;
      this.height = 0;
      this.leading = 0;
      this.parse = true;
      this.lg = null;
      this.list = new Vector(8, 4);
      this.text = GUIUtils.parseTeX(var1);
   }

   public TextLine(String var1, Font var2) {
      this(var1);
      this.font = var2;
      if (this.font != null) {
         this.fontname = var2.getName();
         this.fontstyle = var2.getStyle();
         this.fontsize = var2.getSize();
      }
   }

   public TextLine(String var1, Font var2, Color var3, int var4) {
      this(var1, var2);
      this.color = var3;
      this.justification = var4;
   }

   public TextLine(String var1, Color var2) {
      this(var1);
      this.color = var2;
   }

   public TextLine(Font var1, Color var2, int var3) {
      this.script_fraction = 0.8D;
      this.sup_offset = 0.6D;
      this.sub_offset = 0.7D;
      this.font = null;
      this.color = null;
      this.background = null;
      this.text = null;
      this.fontname = "TimesRoman";
      this.fontsize = 0;
      this.fontstyle = 0;
      this.justification = 1;
      this.width = 0;
      this.ascent = 0;
      this.maxAscent = 0;
      this.descent = 0;
      this.maxDescent = 0;
      this.height = 0;
      this.leading = 0;
      this.parse = true;
      this.lg = null;
      this.list = new Vector(8, 4);
      this.font = var1;
      this.color = var2;
      this.justification = var3;
      if (this.font != null) {
         this.fontname = var1.getName();
         this.fontstyle = var1.getStyle();
         this.fontsize = var1.getSize();
      }
   }

   public TextLine copyState() {
      return new TextLine(this.font, this.color, this.justification);
   }

   public void copyState(TextLine var1) {
      if (var1 != null) {
         this.font = var1.getFont();
         this.color = var1.getColor();
         this.justification = var1.getJustification();
         if (this.font != null) {
            this.fontname = this.font.getName();
            this.fontstyle = this.font.getStyle();
            this.fontsize = this.font.getSize();
            this.parse = true;
         }
      }
   }

   public void setFont(Font var1) {
      this.font = var1;
      this.fontname = var1.getName();
      this.fontstyle = var1.getStyle();
      this.fontsize = var1.getSize();
      this.parse = true;
   }

   public void setText(String var1) {
      this.text = GUIUtils.parseTeX(var1);
      this.parse = true;
   }

   public void setColor(Color var1) {
      this.color = var1;
   }

   public void setBackground(Color var1) {
      this.background = var1;
   }

   public void setJustification(int var1) {
      switch(var1) {
      case 0:
         this.justification = 0;
         break;
      case 1:
      default:
         this.justification = 1;
         break;
      case 2:
         this.justification = 2;
      }

   }

   public Font getFont() {
      return this.font;
   }

   public String getText() {
      return this.text;
   }

   public Color getColor() {
      return this.color;
   }

   public Color getBackground() {
      return this.background;
   }

   public int getJustification() {
      return this.justification;
   }

   public FontMetrics getFM(Graphics var1) {
      if (var1 == null) {
         return null;
      } else {
         return this.font == null ? var1.getFontMetrics() : var1.getFontMetrics(this.font);
      }
   }

   public int charWidth(Graphics var1, char var2) {
      if (var1 == null) {
         return 0;
      } else {
         FontMetrics var3;
         if (this.font == null) {
            var3 = var1.getFontMetrics();
         } else {
            var3 = var1.getFontMetrics(this.font);
         }

         return var3.charWidth(var2);
      }
   }

   public Rectangle2D getStringBounds(Graphics var1) {
      this.parseText(var1);
      return new Float(0.0F, 0.0F, (float)this.width, (float)this.height);
   }

   public int getWidth(Graphics var1) {
      this.parseText(var1);
      return this.width;
   }

   public int getHeight(Graphics var1) {
      this.parseText(var1);
      return this.height;
   }

   public int getAscent(Graphics var1) {
      if (var1 == null) {
         return 0;
      } else {
         this.parseText(var1);
         return this.ascent;
      }
   }

   public int getMaxAscent(Graphics var1) {
      if (var1 == null) {
         return 0;
      } else {
         this.parseText(var1);
         return this.maxAscent;
      }
   }

   public int getDescent(Graphics var1) {
      if (var1 == null) {
         return 0;
      } else {
         this.parseText(var1);
         return this.descent;
      }
   }

   public int getMaxDescent(Graphics var1) {
      if (var1 == null) {
         return 0;
      } else {
         this.parseText(var1);
         return this.maxDescent;
      }
   }

   public int getLeading(Graphics var1) {
      if (var1 == null) {
         return 0;
      } else {
         this.parseText(var1);
         return this.leading;
      }
   }

   public void parseText(Graphics var1) {
      TextState var2 = new TextState();
      Stack var4 = new Stack();
      boolean var5 = false;
      if (this.lg != var1) {
         this.parse = true;
      }

      this.lg = var1;
      if (this.parse) {
         this.parse = false;
         this.width = 0;
         this.leading = 0;
         this.ascent = 0;
         this.descent = 0;
         this.height = 0;
         this.maxAscent = 0;
         this.maxDescent = 0;
         if (this.text != null && var1 != null) {
            this.list.removeAllElements();
            if (this.font == null) {
               var2.f = var1.getFont();
            } else {
               var2.f = this.font;
            }

            var4.push(var2);
            this.list.addElement(var2);

            for(int var6 = 0; var6 < this.text.length(); ++var6) {
               char var3 = this.text.charAt(var6);
               int var10;
               switch(var3) {
               case '$':
                  ++var6;
                  if (var6 < this.text.length()) {
                     var2.s.append(this.text.charAt(var6));
                  }
                  break;
               case '^':
                  var10 = var2.getWidth(var1);
                  if (!var2.isEmpty()) {
                     var2 = var2.copyState();
                     this.list.addElement(var2);
                  }

                  var2.f = this.getScriptFont(var2.f);
                  var2.x += var10;
                  var2.y -= (int)((double)var2.getAscent(var1) * this.sup_offset + 0.5D);
                  break;
               case '_':
                  var10 = var2.getWidth(var1);
                  if (!var2.isEmpty()) {
                     var2 = var2.copyState();
                     this.list.addElement(var2);
                  }

                  var2.f = this.getScriptFont(var2.f);
                  var2.x += var10;
                  var2.y += (int)((double)var2.getDescent(var1) * this.sub_offset + 0.5D);
                  break;
               case '{':
                  var10 = var2.getWidth(var1);
                  if (!var2.isEmpty()) {
                     var2 = var2.copyState();
                     this.list.addElement(var2);
                  }

                  var4.push(var2);
                  var2.x += var10;
                  break;
               case '}':
                  var10 = var2.x + var2.getWidth(var1);
                  var4.pop();
                  var2 = ((TextState)var4.peek()).copyState();
                  this.list.addElement(var2);
                  var2.x = var10;
                  break;
               default:
                  var2.s.append(var3);
               }
            }

            Vector var11;
            synchronized(this.list) {
               var11 = (Vector)this.list.clone();
            }

            for(int var7 = 0; var7 < var11.size(); ++var7) {
               var2 = (TextState)((TextState)var11.elementAt(var7));
               if (!var2.isEmpty()) {
                  this.width += var2.getWidth(var1);
                  this.ascent = Math.max(this.ascent, Math.abs(var2.y) + var2.getAscent(var1));
                  this.descent = Math.max(this.descent, Math.abs(var2.y) + var2.getDescent(var1));
                  this.leading = Math.max(this.leading, var2.getLeading(var1));
                  this.maxDescent = Math.max(this.maxDescent, Math.abs(var2.y) + var2.getMaxDescent(var1));
                  this.maxAscent = Math.max(this.maxAscent, Math.abs(var2.y) + var2.getMaxAscent(var1));
               }
            }

            this.height = this.ascent + this.descent + this.leading;
         }
      }
   }

   public boolean isNull() {
      return this.text == null;
   }

   public void drawText(Graphics var1, int var2, int var3, int var4) {
      this.justification = var4;
      if (var1 != null) {
         this.drawText(var1, var2, var3);
      }
   }

   public void drawText(Graphics var1, int var2, int var3) {
      int var5 = var2;
      int var6 = var3;
      if (var1 != null && this.text != null) {
         Graphics var7 = var1.create();
         if (var7 != null) {
            this.parseText(var1);
            if (this.justification == 0) {
               var5 = var2 - this.width / 2;
            } else if (this.justification == 2) {
               var5 = var2 - this.width;
            }

            if (this.background != null) {
               var7.setColor(this.background);
               var7.fillRect(var5, var3 - this.ascent, this.width, this.height);
               var7.setColor(var1.getColor());
            }

            if (this.font != null) {
               var7.setFont(this.font);
            }

            if (this.color != null) {
               var7.setColor(this.color);
            }

            Vector var8;
            synchronized(this.list) {
               var8 = (Vector)this.list.clone();
            }

            for(int var9 = 0; var9 < var8.size(); ++var9) {
               TextState var4 = (TextState)((TextState)var8.elementAt(var9));
               if (var4.f != null) {
                  var7.setFont(var4.f);
               }

               if (var4.s != null) {
                  var7.drawString(var4.toString(), var4.x + var5, var4.y + var6);
               }
            }

            var7.dispose();
            var7 = null;
         }
      }
   }

   public String getFontName() {
      return this.fontname;
   }

   public int getFontStyle() {
      return this.fontstyle;
   }

   public int getFontSize() {
      return this.fontsize;
   }

   public void setFontName(String var1) {
      this.fontname = var1;
      this.rebuildFont();
   }

   public void setFontStyle(int var1) {
      this.fontstyle = var1;
      this.rebuildFont();
   }

   public void setFontSize(int var1) {
      this.fontsize = var1;
      this.rebuildFont();
   }

   private void rebuildFont() {
      this.parse = true;
      if (this.fontsize > 0 && this.fontname != null) {
         this.font = new Font(this.fontname, this.fontstyle, this.fontsize);
      } else {
         this.font = null;
      }

   }

   public Font getScriptFont(Font var1) {
      if (var1 == null) {
         return var1;
      } else {
         int var2 = var1.getSize();
         if (var2 <= 6) {
            return var1;
         } else {
            var2 = (int)((double)var1.getSize() * this.script_fraction + 0.5D);
            return var2 <= 6 ? var1 : new Font(var1.getName(), var1.getStyle(), var2);
         }
      }
   }

   public boolean parseDouble(double var1) {
      return this.parseDouble(var1, 7, 6, 2);
   }

   public boolean parseDouble(double var1, int var3) {
      return this.parseDouble(var1, var3 + 1, var3, 2);
   }

   public boolean parseDouble(double var1, int var3, int var4, int var5) {
      double var6 = var1;
      int var8 = var3 - var4;
      double var9 = 0.0D;
      StringBuffer var14 = new StringBuffer(var3 + 4);
      if (var8 < 0) {
         System.out.println("TextLine.parseDouble: Precision > significant figures!");
         return false;
      } else {
         if (var1 < 0.0D) {
            var6 = -var1;
            var14.append("-");
         }

         int var12;
         if (var1 == 0.0D) {
            var12 = 0;
         } else {
            var12 = (int)Math.floor(log10(var6));
         }

         int var11 = var12 - (var8 - 1);
         int var13;
         if (var11 < 0) {
            for(var13 = var11; var13 < 0; ++var13) {
               var6 *= 10.0D;
            }
         } else {
            for(var13 = 0; var13 < var11; ++var13) {
               var6 /= 10.0D;
            }
         }

         var8 = (int)var6;
         var14.append(var8);
         if (var4 > 0) {
            var14.append('.');
            var9 = var6 - (double)var8;

            for(var13 = 0; var13 < var4; ++var13) {
               var9 *= 10.0D;
               int var15 = (int)Math.round(var9);
               var14.append(var15);
               var9 -= (double)var15;
            }
         }

         if (var11 != 0) {
            if (var5 == 1) {
               var14.append('E');
               if (var11 < 0) {
                  var14.append('-');
               } else {
                  var14.append('+');
               }

               var11 = Math.abs(var11);
               if (var11 > 9) {
                  var14.append(var11);
               } else {
                  var14.append('0');
                  var14.append(var11);
               }
            } else {
               var14.append("x10{^");
               var14.append(var11);
               var14.append("}");
            }
         }

         this.setText(var14.toString());
         return true;
      }
   }

   public static double log10(double var0) throws ArithmeticException {
      if (var0 <= 0.0D) {
         throw new ArithmeticException("range exception");
      } else {
         return Math.log(var0) / 2.302585092994046D;
      }
   }
}
