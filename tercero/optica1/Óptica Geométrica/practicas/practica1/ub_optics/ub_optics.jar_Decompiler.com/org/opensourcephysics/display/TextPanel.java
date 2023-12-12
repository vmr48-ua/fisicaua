package org.opensourcephysics.display;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class TextPanel extends JPanel {
   protected static Dimension ZEROSIZE = new Dimension(0, 0);
   protected String text;
   protected Font font;
   protected String fontname;
   protected int fontsize;
   protected int fontstyle;
   protected Color textColor;
   protected Color backgroundColor;
   protected Dimension dim;

   public TextPanel() {
      this.text = "";
      this.fontname = "TimesRoman";
      this.fontsize = 14;
      this.fontstyle = 0;
      this.textColor = Color.black;
      this.backgroundColor = Color.yellow;
      this.dim = ZEROSIZE;
      this.setBackground(this.backgroundColor);
      this.font = new Font(this.fontname, this.fontstyle, this.fontsize);
   }

   public TextPanel(String var1) {
      this();
      this.setText(var1);
   }

   public void setText(String var1) {
      var1 = GUIUtils.parseTeX(var1);
      if (this.text != var1) {
         this.text = var1;
         if (this.text == null) {
            this.text = "";
         }

         Container var2 = this.getParent();
         if (var2 != null) {
            if (var2.getLayout() instanceof OSPLayout) {
               ((OSPLayout)var2.getLayout()).quickLayout(var2, this);
               this.repaint();
            } else {
               var2.validate();
            }

         }
      }
   }

   public Dimension getPreferredSize() {
      Container var1 = this.getParent();
      String var2 = this.text;
      if (var1 != null && !var2.equals("")) {
         Graphics2D var3 = (Graphics2D)var1.getGraphics();
         if (var3 == null) {
            return ZEROSIZE;
         } else {
            Font var4 = var3.getFont();
            var3.setFont(this.font);
            FontMetrics var5 = var3.getFontMetrics();
            int var6 = var5.getAscent() + 4;
            int var7 = var5.stringWidth(var2) + 6;
            var3.setFont(var4);
            return new Dimension(var7, var6);
         }
      } else {
         return ZEROSIZE;
      }
   }

   public void paint(Graphics var1) {
      String var2 = this.text;
      if (!this.dim.equals(this.getPreferredSize())) {
         this.dim = this.getPreferredSize();
         this.setSize(this.dim);
      }

      if (!var2.equals("") && this.isVisible()) {
         Graphics2D var3 = (Graphics2D)var1;
         super.paintComponent(var1);
         int var4 = this.getWidth();
         int var5 = this.getHeight();
         var3.setColor(this.textColor);
         Font var6 = var3.getFont();
         var3.setFont(this.font);
         var3.drawString(var2, 3, var5 - 4);
         var3.setFont(var6);
         var3.setColor(Color.black);
         var3.drawRect(0, 0, var4 - 1, var5 - 1);
      }
   }
}
