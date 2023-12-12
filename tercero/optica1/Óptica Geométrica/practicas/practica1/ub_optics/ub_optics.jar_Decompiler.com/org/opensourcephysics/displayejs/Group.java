package org.opensourcephysics.displayejs;

import java.util.ArrayList;
import java.util.Iterator;
import org.opensourcephysics.display.DrawingPanel;

public class Group {
   protected double x = 0.0D;
   protected double y = 0.0D;
   protected double z = 0.0D;
   protected double sizex = 1.0D;
   protected double sizey = 1.0D;
   protected double sizez = 1.0D;
   protected ArrayList list = new ArrayList();

   void addElement(InteractiveElement var1) {
      if (!this.list.contains(var1)) {
         this.list.add(var1);
      }

   }

   boolean removeElement(InteractiveElement var1) {
      return this.list.remove(var1);
   }

   public void setX(double var1) {
      this.x = var1;
      this.reportChange();
   }

   public double getX() {
      return this.x;
   }

   public void setY(double var1) {
      this.y = var1;
      this.reportChange();
   }

   public double getY() {
      return this.y;
   }

   public void setZ(double var1) {
      this.z = var1;
      this.reportChange();
   }

   public double getZ() {
      return this.z;
   }

   public void setXY(double var1, double var3) {
      this.x = var1;
      this.y = var3;
      this.reportChange();
   }

   public void setXYZ(double var1, double var3, double var5) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
      this.reportChange();
   }

   public void setSizeX(double var1) {
      this.sizex = var1;
      this.reportChange();
   }

   public double getSizeX() {
      return this.sizex;
   }

   public void setSizeY(double var1) {
      this.sizey = var1;
      this.reportChange();
   }

   public double getSizeY() {
      return this.sizey;
   }

   public void setSizeZ(double var1) {
      this.sizez = var1;
      this.reportChange();
   }

   public double getSizeZ() {
      return this.sizez;
   }

   public void setSizeXY(double var1, double var3) {
      this.sizex = var1;
      this.sizey = var3;
      this.reportChange();
   }

   public void setSizeXYZ(double var1, double var3, double var5) {
      this.sizex = var1;
      this.sizey = var3;
      this.sizez = var5;
      this.reportChange();
   }

   public void setVisible(boolean var1) {
      Iterator var2 = this.list.iterator();

      while(var2.hasNext()) {
         ((InteractiveElement)var2.next()).setVisible(var1);
      }

   }

   public boolean isVisible() {
      Iterator var1 = this.list.iterator();

      do {
         if (!var1.hasNext()) {
            return false;
         }
      } while(!((InteractiveElement)var1.next()).isVisible());

      return true;
   }

   public void reportChange() {
      Iterator var1 = this.list.iterator();

      while(var1.hasNext()) {
         ((InteractiveElement)var1.next()).needsToProject((DrawingPanel)null);
      }

   }
}
