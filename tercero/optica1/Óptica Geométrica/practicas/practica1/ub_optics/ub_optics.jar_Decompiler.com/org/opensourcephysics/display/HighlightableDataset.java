package org.opensourcephysics.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLLoader;

public class HighlightableDataset extends Dataset {
   boolean[] highlighted = new boolean[1];
   boolean[] previous;
   Color highlightColor = new Color(255, 255, 0, 128);
   Shape highlightShape;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$display$Dataset;

   public HighlightableDataset() {
   }

   public HighlightableDataset(Color var1) {
      super(var1);
   }

   public HighlightableDataset(Color var1, Color var2, boolean var3) {
      super(var1, var2, var3);
   }

   public void append(double var1, double var3) {
      super.append(var1, var3);
      this.adjustCapacity(super.xpoints.length);
   }

   public void append(double[] var1, double[] var2) {
      super.append(var1, var2);
      this.adjustCapacity(super.xpoints.length);
   }

   public void clear() {
      super.clear();
      this.previous = this.highlighted;
      this.highlighted = new boolean[super.xpoints.length];
   }

   public void restoreHighlights() {
      if (this.previous != null && this.previous.length == this.highlighted.length) {
         this.highlighted = this.previous;
      }

   }

   public void clearHighlights() {
      for(int var1 = 0; var1 < this.highlighted.length; ++var1) {
         this.highlighted[var1] = false;
      }

   }

   public void setHighlighted(int var1, boolean var2) {
      if (var1 >= this.highlighted.length) {
         this.adjustCapacity(var1 + 1);
      }

      this.highlighted[var1] = var2;
   }

   public void setHighlighted(int var1, int var2, boolean var3) {
      if (var2 >= this.highlighted.length) {
         this.adjustCapacity(var2 + 1);
      }

      var1 = Math.max(var1, 0);

      for(int var4 = var1; var4 <= var2; ++var4) {
         this.highlighted[var4] = var3;
      }

   }

   public boolean isHighlighted(int var1) {
      if (var1 >= this.highlighted.length) {
         this.adjustCapacity(var1 + 1);
      }

      return this.highlighted[var1];
   }

   public void setHighlightColor(Color var1) {
      this.highlightColor = new Color(var1.getRed(), var1.getGreen(), var1.getBlue(), 128);
   }

   protected void moveDatum(int var1) {
      super.moveDatum(var1);
   }

   private synchronized void adjustCapacity(int var1) {
      int var2 = Math.max(super.xpoints.length, var1);
      if (this.highlighted.length != var2) {
         boolean[] var3 = this.highlighted;
         this.highlighted = new boolean[var2];
         int var4 = Math.min(var3.length, var2);
         System.arraycopy(var3, 0, this.highlighted, 0, var4);
      }
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      super.draw(var1, var2);
      Graphics2D var3 = (Graphics2D)var2;
      int var4 = this.getMarkerSize() + 4;
      int var5 = 2 * var4;
      Shape var6 = var3.getClip();
      var3.setClip(var1.leftGutter - var4 - 1, var1.topGutter - var4 - 1, var1.getWidth() - var1.leftGutter - var1.rightGutter + 2 + 2 * var4, var1.getHeight() - var1.bottomGutter - var1.topGutter + 2 + 2 * var4);
      Rectangle var7 = var1.getViewRect();
      if (var7 != null) {
         var3.clipRect(var7.x, var7.y, var7.x + var7.width, var7.y + var7.height);
      }

      for(int var8 = 0; var8 < super.index; ++var8) {
         if (this.isHighlighted(var8) && !Double.isNaN(super.ypoints[var8])) {
            double var9 = (double)var1.xToPix(super.xpoints[var8]);
            double var11 = (double)var1.yToPix(super.ypoints[var8]);
            java.awt.geom.Rectangle2D.Double var13 = new java.awt.geom.Rectangle2D.Double(var9 - (double)var4, var11 - (double)var4, (double)var5, (double)var5);
            var3.setColor(this.highlightColor);
            var3.fill(var13);
         }
      }

      var3.setClip(var6);
   }

   public static XML.ObjectLoader getLoader() {
      return new HighlightableDataset.Loader();
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   private static class Loader extends XMLLoader {
      private Loader() {
      }

      public void saveObject(XMLControl var1, Object var2) {
         XML.getLoader(HighlightableDataset.class$org$opensourcephysics$display$Dataset == null ? (HighlightableDataset.class$org$opensourcephysics$display$Dataset = HighlightableDataset.class$("org.opensourcephysics.display.Dataset")) : HighlightableDataset.class$org$opensourcephysics$display$Dataset).saveObject(var1, var2);
         HighlightableDataset var3 = (HighlightableDataset)var2;
         var1.setValue("highlighted", var3.highlighted);
      }

      public Object createObject(XMLControl var1) {
         return new HighlightableDataset();
      }

      public Object loadObject(XMLControl var1, Object var2) {
         XML.getLoader(HighlightableDataset.class$org$opensourcephysics$display$Dataset == null ? (HighlightableDataset.class$org$opensourcephysics$display$Dataset = HighlightableDataset.class$("org.opensourcephysics.display.Dataset")) : HighlightableDataset.class$org$opensourcephysics$display$Dataset).loadObject(var1, var2);
         HighlightableDataset var3 = (HighlightableDataset)var2;
         boolean[] var4 = (boolean[])((boolean[])var1.getObject("highlighted"));
         if (var4 != null) {
            var3.highlighted = var4;
         }

         return var3;
      }

      // $FF: synthetic method
      Loader(Object var1) {
         this();
      }
   }
}
