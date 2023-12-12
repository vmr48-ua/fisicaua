package org.opensourcephysics.display;

import java.awt.Font;
import java.awt.Graphics;

class TextState {
   Font f = null;
   StringBuffer s = null;
   int x = 0;
   int y = 0;

   public TextState() {
      this.s = new StringBuffer();
   }

   public TextState copyAll() {
      TextState var1 = this.copyState();
      if (this.s.length() == 0) {
         return var1;
      } else {
         for(int var2 = 0; var2 < this.s.length(); ++var2) {
            var1.s.append(this.s.charAt(var2));
         }

         return var1;
      }
   }

   public TextState copyState() {
      TextState var1 = new TextState();
      var1.f = this.f;
      var1.x = this.x;
      var1.y = this.y;
      return var1;
   }

   public String toString() {
      return this.s.toString();
   }

   public boolean isEmpty() {
      return this.s.length() == 0;
   }

   public int getWidth(Graphics var1) {
      return var1 != null && this.f != null && this.s.length() != 0 ? var1.getFontMetrics(this.f).stringWidth(this.s.toString()) : 0;
   }

   public int getHeight(Graphics var1) {
      return var1 != null && this.f != null ? var1.getFontMetrics(this.f).getHeight() : 0;
   }

   public int getAscent(Graphics var1) {
      return var1 != null && this.f != null ? var1.getFontMetrics(this.f).getAscent() : 0;
   }

   public int getDescent(Graphics var1) {
      return var1 != null && this.f != null ? var1.getFontMetrics(this.f).getDescent() : 0;
   }

   public int getMaxAscent(Graphics var1) {
      return var1 != null && this.f != null ? var1.getFontMetrics(this.f).getMaxAscent() : 0;
   }

   public int getMaxDescent(Graphics var1) {
      return var1 != null && this.f != null ? var1.getFontMetrics(this.f).getMaxDescent() : 0;
   }

   public int getLeading(Graphics var1) {
      return var1 != null && this.f != null ? var1.getFontMetrics(this.f).getLeading() : 0;
   }
}
