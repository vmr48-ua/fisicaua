package org.opensourcephysics.displayejs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Stroke;
import org.opensourcephysics.display.DrawingPanel;

public class Style {
   public static final int CENTERED = 0;
   public static final int NORTH = 1;
   public static final int SOUTH = 2;
   public static final int EAST = 3;
   public static final int WEST = 4;
   public static final int NORTH_EAST = 5;
   public static final int NORTH_WEST = 6;
   public static final int SOUTH_EAST = 7;
   public static final int SOUTH_WEST = 8;
   private Drawable3D drawable = null;
   int position = 0;
   double angle = 0.0D;
   double cosAngle = 1.0D;
   double sinAngle = 0.0D;
   Color edgeColor;
   Stroke edgeStroke;
   Paint fillPattern;
   Font font;
   Object displayObject;

   public Style(Drawable3D var1) {
      this.edgeColor = Color.black;
      this.edgeStroke = new BasicStroke();
      this.fillPattern = Color.blue;
      this.font = null;
      this.displayObject = null;
      this.drawable = var1;
   }

   public Style(Style var1) {
      this.edgeColor = Color.black;
      this.edgeStroke = new BasicStroke();
      this.fillPattern = Color.blue;
      this.font = null;
      this.displayObject = null;
      this.drawable = var1.drawable;
      this.position = var1.position;
      this.angle = var1.angle;
      this.cosAngle = var1.cosAngle;
      this.sinAngle = var1.sinAngle;
      this.edgeColor = var1.edgeColor;
      this.edgeStroke = var1.edgeStroke;
      this.fillPattern = var1.fillPattern;
      this.font = var1.font;
      this.displayObject = var1.displayObject;
   }

   public void copyFrom(Style var1) {
      this.drawable = var1.drawable;
      this.position = var1.position;
      this.angle = var1.angle;
      this.cosAngle = var1.cosAngle;
      this.sinAngle = var1.sinAngle;
      this.edgeColor = var1.edgeColor;
      this.edgeStroke = var1.edgeStroke;
      this.fillPattern = var1.fillPattern;
      this.font = var1.font;
      this.displayObject = var1.displayObject;
   }

   public void setPosition(int var1) {
      this.position = var1;
      this.drawable.needsToProject((DrawingPanel)null);
   }

   public int getPosition() {
      return this.position;
   }

   public void setAngle(double var1) {
      this.angle = var1;
      this.cosAngle = Math.cos(this.angle);
      this.sinAngle = Math.sin(this.angle);
      this.drawable.needsToProject((DrawingPanel)null);
   }

   public double getAngle() {
      return this.angle;
   }

   public void setEdgeColor(Color var1) {
      this.edgeColor = var1;
   }

   public Color getEdgeColor() {
      return this.edgeColor;
   }

   public void setEdgeStroke(Stroke var1) {
      this.edgeStroke = var1;
   }

   public Stroke getEdgeStroke() {
      return this.edgeStroke;
   }

   public void setFillPattern(Paint var1) {
      this.fillPattern = var1;
   }

   public Paint getFillPattern() {
      return this.fillPattern;
   }

   public void setFont(Font var1) {
      this.font = var1;
      this.drawable.needsToProject((DrawingPanel)null);
   }

   public Font getFont() {
      return this.font;
   }

   public void setDisplayObject(Object var1) {
      this.displayObject = var1;
      this.drawable.needsToProject((DrawingPanel)null);
   }

   public Object getDisplayObject() {
      return this.displayObject;
   }
}
