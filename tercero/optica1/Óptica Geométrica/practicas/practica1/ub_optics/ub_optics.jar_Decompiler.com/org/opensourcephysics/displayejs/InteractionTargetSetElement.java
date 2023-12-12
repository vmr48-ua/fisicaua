package org.opensourcephysics.displayejs;

import java.awt.Graphics;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Interactive;

public class InteractionTargetSetElement implements InteractionTarget {
   private ElementSet set;
   private int index;
   private InteractionTarget targetHit;

   InteractionTargetSetElement(ElementSet var1, int var2, InteractionTarget var3) {
      this.set = var1;
      this.index = var2;
      this.targetHit = var3;
   }

   public InteractionSource getSource() {
      return this.set;
   }

   public Point3D getHotspot(DrawingPanel var1) {
      return this.targetHit.getHotspot(var1);
   }

   public void updateHotspot(DrawingPanel var1, Point3D var2) {
      this.targetHit.updateHotspot(var1, var2);
   }

   public int getElementIndex() {
      return this.index;
   }

   public InteractionTarget getElementTarget() {
      return this.targetHit;
   }

   public Interactive findInteractive(DrawingPanel var1, int var2, int var3) {
      return null;
   }

   public void setEnabled(boolean var1) {
   }

   public boolean isEnabled() {
      return true;
   }

   public void setXY(double var1, double var3) {
   }

   public void setX(double var1) {
   }

   public void setY(double var1) {
   }

   public double getX() {
      return Double.NaN;
   }

   public double getY() {
      return Double.NaN;
   }

   public boolean isMeasured() {
      return true;
   }

   public double getXMin() {
      return Double.NaN;
   }

   public double getXMax() {
      return Double.NaN;
   }

   public double getYMin() {
      return Double.NaN;
   }

   public double getYMax() {
      return Double.NaN;
   }

   public void draw(DrawingPanel var1, Graphics var2) {
   }
}
