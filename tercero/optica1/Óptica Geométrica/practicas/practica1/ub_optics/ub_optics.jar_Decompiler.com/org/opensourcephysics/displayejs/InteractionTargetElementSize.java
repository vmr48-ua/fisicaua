package org.opensourcephysics.displayejs;

import java.awt.Graphics;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Interactive;

public class InteractionTargetElementSize implements InteractionTarget {
   protected InteractiveElement element;

   InteractionTargetElementSize(InteractiveElement var1) {
      this.element = var1;
   }

   public InteractionSource getSource() {
      return this.element;
   }

   public Point3D getHotspot(DrawingPanel var1) {
      return this.element.getGroup() == null ? new Point3D(this.element.getX() + this.element.getSizeX(), this.element.getY() + this.element.getSizeY(), this.element.getZ() + this.element.getSizeZ()) : new Point3D(this.element.getGroup().getX() + (this.element.getX() + this.element.getSizeX()) * this.element.getGroup().getSizeX(), this.element.getGroup().getY() + (this.element.getY() + this.element.getSizeY()) * this.element.getGroup().getSizeY(), this.element.getGroup().getZ() + (this.element.getZ() + this.element.getSizeZ()) * this.element.getGroup().getSizeZ());
   }

   public void updateHotspot(DrawingPanel var1, Point3D var2) {
      if (this.element.getGroup() == null) {
         this.element.setSizeXYZ(var2.x - this.element.getX(), var2.y - this.element.getY(), var2.z - this.element.getZ());
      } else {
         double var3;
         double var5;
         double var7;
         if (this.element.isGroupEnabled(1)) {
            var3 = this.element.getX() + this.element.getSizeX();
            var5 = this.element.getY() + this.element.getSizeY();
            var7 = this.element.getZ() + this.element.getSizeZ();
            if (var3 == 0.0D) {
               var3 = this.element.getGroup().getSizeX();
            } else {
               var3 = (var2.x - this.element.getGroup().getX()) / var3;
            }

            if (var5 == 0.0D) {
               var5 = this.element.getGroup().getSizeY();
            } else {
               var5 = (var2.y - this.element.getGroup().getY()) / var5;
            }

            if (var7 == 0.0D) {
               var7 = this.element.getGroup().getSizeZ();
            } else {
               var7 = (var2.z - this.element.getGroup().getZ()) / var7;
            }

            this.element.getGroup().setSizeXYZ(var3, var5, var7);
         } else {
            var3 = this.element.getGroup().getSizeX();
            var5 = this.element.getGroup().getSizeY();
            var7 = this.element.getGroup().getSizeZ();
            if (var3 == 0.0D) {
               var3 = this.element.getSizeX();
            } else {
               var3 = (var2.x - this.element.getGroup().getX()) / var3 - this.element.getX();
            }

            if (var5 == 0.0D) {
               var5 = this.element.getSizeY();
            } else {
               var5 = (var2.y - this.element.getGroup().getY()) / var5 - this.element.getY();
            }

            if (var7 == 0.0D) {
               var7 = this.element.getSizeZ();
            } else {
               var7 = (var2.z - this.element.getGroup().getZ()) / var7 - this.element.getZ();
            }

            this.element.setSizeXYZ(var3, var5, var7);
         }
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
