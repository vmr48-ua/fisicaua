package org.opensourcephysics.display.axes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.DrawableTextLine;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.PlottingPanel;

public class CustomAxes implements DrawableAxes {
   Color gridColor;
   Color interiorColor;
   DrawableTextLine titleLine;
   Font titleFont;
   boolean visible;
   ArrayList drawableList;

   public CustomAxes(PlottingPanel var1) {
      this.gridColor = Color.lightGray;
      this.interiorColor = Color.white;
      this.titleLine = new DrawableTextLine("", 0.0D, 0.0D);
      this.titleFont = new Font("Dialog", 1, 14);
      this.visible = true;
      this.drawableList = new ArrayList();
      if (var1 != null) {
         var1.setGutters(25, 25, 25, 25);
         this.titleLine.setJustification(0);
         this.titleLine.setFont(this.titleFont);
         var1.setCoordinateStringBuilder(CoordinateStringBuilder.createCartesian());
         var1.setAxes(this);
      }
   }

   public void setXLabel(String var1, String var2) {
   }

   public void setYLabel(String var1, String var2) {
   }

   public String getXLabel() {
      return "";
   }

   public String getYLabel() {
      return "";
   }

   public String getTitle() {
      return this.titleLine.getText();
   }

   public void setTitle(String var1, String var2) {
      this.titleLine.setText(var1);
      if (var2 != null && !var2.equals("")) {
         this.titleLine.setFont(Font.decode(var2));
      }
   }

   public void setXLog(boolean var1) {
   }

   public void setYLog(boolean var1) {
   }

   public void setVisible(boolean var1) {
      this.visible = var1;
   }

   public void setInteriorBackground(Color var1) {
      this.interiorColor = var1;
   }

   public void setShowMajorXGrid(boolean var1) {
   }

   public void setShowMinorXGrid(boolean var1) {
   }

   public void setShowMajorYGrid(boolean var1) {
   }

   public void setShowMinorYGrid(boolean var1) {
   }

   public void resizeFonts(double var1, DrawingPanel var3) {
   }

   public synchronized void addDrawable(Drawable var1) {
      if (var1 != null && !this.drawableList.contains(var1)) {
         this.drawableList.add(var1);
      }

   }

   public void draw(DrawingPanel var1, Graphics var2) {
      if (this.visible) {
         if (this.interiorColor != var1.getBackground()) {
            var2.setColor(this.interiorColor);
            int var3 = var1.getLeftGutter() + var1.getRightGutter();
            int var4 = var1.getTopGutter() + var1.getLeftGutter();
            var2.fillRect(var1.getLeftGutter(), var1.getTopGutter(), var1.getWidth() - var3, var1.getHeight() - var4);
            var2.setColor(this.gridColor);
            var2.drawRect(var1.getLeftGutter(), var1.getTopGutter(), var1.getWidth() - var3, var1.getHeight() - var4);
         }

         Iterator var5 = this.drawableList.iterator();

         while(var5.hasNext()) {
            Drawable var6 = (Drawable)var5.next();
            var6.draw(var1, var2);
         }

         this.titleLine.setX((var1.getXMax() + var1.getXMin()) / 2.0D);
         if (var1.getTopGutter() > 20) {
            this.titleLine.setY(var1.getYMax() + 5.0D / var1.getYPixPerUnit());
         } else {
            this.titleLine.setY(var1.getYMax() - 25.0D / var1.getYPixPerUnit());
         }

         this.titleLine.draw(var1, var2);
      }
   }
}
