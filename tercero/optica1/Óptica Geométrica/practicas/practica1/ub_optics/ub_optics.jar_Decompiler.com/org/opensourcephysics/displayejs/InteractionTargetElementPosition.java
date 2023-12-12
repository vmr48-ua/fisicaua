package org.opensourcephysics.displayejs;

import java.awt.Graphics;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Interactive;

public class InteractionTargetElementPosition implements InteractionTarget {
   protected InteractiveElement element;

   InteractionTargetElementPosition(InteractiveElement var1) {
      this.element = var1;
   }

   public InteractionSource getSource() {
      return this.element;
   }

   public Point3D getHotspot(DrawingPanel var1) {
      return this.element.getGroup() == null ? new Point3D(this.element.getX(), this.element.getY(), this.element.getZ()) : new Point3D(this.element.getGroup().getX() + this.element.getX() * this.element.getGroup().getSizeX(), this.element.getGroup().getY() + this.element.getY() * this.element.getGroup().getSizeY(), this.element.getGroup().getZ() + this.element.getZ() * this.element.getGroup().getSizeZ());
   }

   public void updateHotspot(DrawingPanel var1, Point3D var2) {
      if (this.element.getGroup() == null) {
         this.element.setXYZ(var2.x, var2.y, var2.z);
      } else if (this.element.isGroupEnabled(0)) {
         this.element.getGroup().setXYZ(var2.x - this.element.getX() * this.element.getGroup().getSizeX(), var2.y - this.element.getY() * this.element.getGroup().getSizeY(), var2.z - this.element.getZ() * this.element.getGroup().getSizeZ());
      } else {
         double var3 = this.element.getGroup().getSizeX();
         double var5 = this.element.getGroup().getSizeY();
         double var7 = this.element.getGroup().getSizeZ();
         if (var3 == 0.0D) {
            var3 = this.element.getX();
         } else {
            var3 = (var2.x - this.element.getGroup().getX()) / var3;
         }

         if (var5 == 0.0D) {
            var5 = this.element.getY();
         } else {
            var5 = (var2.y - this.element.getGroup().getY()) / var5;
         }

         if (var7 == 0.0D) {
            var7 = this.element.getZ();
         } else {
            var7 = (var2.z - this.element.getGroup().getZ()) / var7;
         }

         this.element.setXYZ(var3, var5, var7);
      }

   }

   public Interactive findInteractive(DrawingPanel var1, int var2, int var3) {
      return this.element;
   }

   public void setEnabled(boolean var1) {
      this.element.setEnabled(0, var1);
   }

   public boolean isEnabled() {
      return this.element.isEnabled(0);
   }

   public void setXY(double var1, double var3) {
      this.element.setXY(var1, var3);
   }

   public void setX(double var1) {
      this.element.setX(var1);
   }

   public void setY(double var1) {
      this.element.setY(var1);
   }

   public double getX() {
      return this.element.getX();
   }

   public double getY() {
      return this.element.getY();
   }

   public boolean isMeasured() {
      return true;
   }

   public double getXMin() {
      return this.element.getXMin();
   }

   public double getXMax() {
      return this.element.getXMax();
   }

   public double getYMin() {
      return this.element.getYMin();
   }

   public double getYMax() {
      return this.element.getYMax();
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      this.element.draw(var1, var2);
   }
}
