package org.opensourcephysics.displayejs;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Interactive;

public class ElementSet extends AbstractInteractionSource implements Interactive, Drawable3D, Measurable3D {
   protected int numElements = 0;
   protected Class classType;
   protected InteractiveElement[] elements = null;
   protected ArrayList list3D = new ArrayList();
   protected Object3D[] minimalObjects = new Object3D[1];

   public ElementSet(int var1, Class var2) {
      if (var1 < 1) {
         System.out.println("ElementSet error: An element set must contain at least one element!");
         var1 = 1;
      }

      this.classType = var2;
      this.setNumberOfElements(var1);
   }

   public int getNumberOfElements() {
      return this.numElements;
   }

   public synchronized void setNumberOfElements(int var1) {
      if (var1 != this.numElements && var1 >= 1) {
         InteractiveElement[] var2 = this.elements;
         this.elements = new InteractiveElement[var1];

         int var3;
         try {
            var3 = 0;

            while(true) {
               if (var3 >= var1) {
                  this.numElements = var1;
                  break;
               }

               this.elements[var3] = (InteractiveElement)this.classType.newInstance();
               this.elements[var3].setSet(this, var3);
               ++var3;
            }
         } catch (Exception var5) {
            System.out.println("Error: ElementSet requires a class that extends InteractiveElement!");
            var5.printStackTrace();
            this.numElements = 0;
            return;
         }

         if (var2 != null) {
            var3 = 0;

            int var4;
            for(var4 = Math.min(this.elements.length, var2.length); var3 < var4; ++var3) {
               this.elements[var3].copyFrom(var2[var3]);
            }

            if (this.elements.length > var2.length) {
               var3 = var2.length;

               for(var4 = this.elements.length; var3 < var4; ++var3) {
                  this.elements[var3].copyFrom(var2[0]);
               }
            }

            var2 = null;
         }
      }
   }

   public InteractiveElement elementAt(int var1) {
      return this.elements[var1];
   }

   public void setXs(double[] var1) {
      int var2 = this.numElements;
      if (var2 > var1.length) {
         var2 = var1.length;
      }

      for(int var3 = 0; var3 < var2; ++var3) {
         this.elements[var3].setX(var1[var3]);
      }

   }

   public double[] getXs() {
      double[] var1 = new double[this.numElements];

      for(int var2 = 0; var2 < this.numElements; ++var2) {
         var1[var2] = this.elements[var2].getX();
      }

      return var1;
   }

   public void setYs(double[] var1) {
      int var2 = this.numElements;
      if (var2 > var1.length) {
         var2 = var1.length;
      }

      for(int var3 = 0; var3 < var2; ++var3) {
         this.elements[var3].setY(var1[var3]);
      }

   }

   public double[] getYs() {
      double[] var1 = new double[this.numElements];

      for(int var2 = 0; var2 < this.numElements; ++var2) {
         var1[var2] = this.elements[var2].getY();
      }

      return var1;
   }

   public void setZs(double[] var1) {
      int var2 = this.numElements;
      if (var2 > var1.length) {
         var2 = var1.length;
      }

      for(int var3 = 0; var3 < var2; ++var3) {
         this.elements[var3].setZ(var1[var3]);
      }

   }

   public double[] getZs() {
      double[] var1 = new double[this.numElements];

      for(int var2 = 0; var2 < this.numElements; ++var2) {
         var1[var2] = this.elements[var2].getZ();
      }

      return var1;
   }

   public void setXYZs(double[] var1, double[] var2, double[] var3) {
      int var4 = this.numElements;
      if (var4 > var1.length) {
         var4 = var1.length;
      }

      if (var4 > var2.length) {
         var4 = var2.length;
      }

      if (var4 > var3.length) {
         var4 = var3.length;
      }

      for(int var5 = 0; var5 < var4; ++var5) {
         this.elements[var5].setXYZ(var1[var5], var2[var5], var3[var5]);
      }

   }

   public void setSizeXs(double[] var1) {
      int var2 = this.numElements;
      if (var2 > var1.length) {
         var2 = var1.length;
      }

      for(int var3 = 0; var3 < var2; ++var3) {
         this.elements[var3].setSizeX(var1[var3]);
      }

   }

   public double[] getSizeXs() {
      double[] var1 = new double[this.numElements];

      for(int var2 = 0; var2 < this.numElements; ++var2) {
         var1[var2] = this.elements[var2].getSizeX();
      }

      return var1;
   }

   public void setSizeYs(double[] var1) {
      int var2 = this.numElements;
      if (var2 > var1.length) {
         var2 = var1.length;
      }

      for(int var3 = 0; var3 < var2; ++var3) {
         this.elements[var3].setSizeY(var1[var3]);
      }

   }

   public double[] getSizeYs() {
      double[] var1 = new double[this.numElements];

      for(int var2 = 0; var2 < this.numElements; ++var2) {
         var1[var2] = this.elements[var2].getSizeY();
      }

      return var1;
   }

   public void setSizeZs(double[] var1) {
      int var2 = this.numElements;
      if (var2 > var1.length) {
         var2 = var1.length;
      }

      for(int var3 = 0; var3 < var2; ++var3) {
         this.elements[var3].setSizeZ(var1[var3]);
      }

   }

   public double[] getSizeZs() {
      double[] var1 = new double[this.numElements];

      for(int var2 = 0; var2 < this.numElements; ++var2) {
         var1[var2] = this.elements[var2].getSizeZ();
      }

      return var1;
   }

   public void setSizeXYZs(double[] var1, double[] var2, double[] var3) {
      int var4 = this.numElements;
      if (var4 > var1.length) {
         var4 = var1.length;
      }

      if (var4 > var2.length) {
         var4 = var2.length;
      }

      if (var4 > var3.length) {
         var4 = var3.length;
      }

      for(int var5 = 0; var5 < var4; ++var5) {
         this.elements[var5].setSizeXYZ(var1[var5], var2[var5], var3[var5]);
      }

   }

   public void setVisible(boolean var1) {
      for(int var2 = 0; var2 < this.numElements; ++var2) {
         this.elements[var2].setVisible(var1);
      }

   }

   public boolean isVisible() {
      for(int var1 = 0; var1 < this.numElements; ++var1) {
         if (this.elements[var1].isVisible()) {
            return true;
         }
      }

      return false;
   }

   public void setVisibles(boolean[] var1) {
      int var2 = this.numElements;
      if (var2 > var1.length) {
         var2 = var1.length;
      }

      for(int var3 = 0; var3 < var2; ++var3) {
         this.elements[var3].setVisible(var1[var3]);
      }

   }

   public void setEnabled(boolean var1) {
      for(int var2 = 0; var2 < this.numElements; ++var2) {
         this.elements[var2].setEnabled(var1);
      }

   }

   public boolean isEnabled() {
      for(int var1 = 0; var1 < this.numElements; ++var1) {
         if (this.elements[var1].isEnabled()) {
            return true;
         }
      }

      return false;
   }

   public void setEnabled(int var1, boolean var2) {
      for(int var3 = 0; var3 < this.numElements; ++var3) {
         this.elements[var3].setEnabled(var1, var2);
      }

   }

   public boolean isEnabled(int var1) {
      for(int var2 = 0; var2 < this.numElements; ++var2) {
         if (this.elements[var2].isEnabled(var1)) {
            return true;
         }
      }

      return false;
   }

   public void setEnableds(boolean[] var1) {
      int var2 = this.numElements;
      if (var2 > var1.length) {
         var2 = var1.length;
      }

      for(int var3 = 0; var3 < var2; ++var3) {
         this.elements[var3].setEnabled(var1[var3]);
      }

   }

   public void setEnableds(int var1, boolean[] var2) {
      int var3 = this.numElements;
      if (var3 > var2.length) {
         var3 = var2.length;
      }

      for(int var4 = 0; var4 < var3; ++var4) {
         this.elements[var4].setEnabled(var1, var2[var4]);
      }

   }

   public String toXML() {
      return this.toString();
   }

   public Interactive findInteractive(DrawingPanel var1, int var2, int var3) {
      for(int var4 = 0; var4 < this.numElements; ++var4) {
         Interactive var5 = this.elements[var4].findInteractive(var1, var2, var3);
         if (var5 != null) {
            return new InteractionTargetSetElement(this, var4, (InteractionTarget)var5);
         }
      }

      return null;
   }

   public void needsToProject(DrawingPanel var1) {
      for(int var2 = 0; var2 < this.numElements; ++var2) {
         this.elements[var2].needsToProject(var1);
      }

   }

   public Object3D[] getObjects3D(DrawingPanel3D var1) {
      if (this.numElements <= 0) {
         return null;
      } else {
         this.list3D.clear();

         for(int var2 = 0; var2 < this.numElements; ++var2) {
            Object3D[] var3 = this.elements[var2].getObjects3D(var1);
            if (var3 != null) {
               for(int var4 = 0; var4 < var3.length; ++var4) {
                  this.list3D.add(var3[var4]);
               }
            }
         }

         if (this.list3D.size() == 0) {
            return null;
         } else {
            return (Object3D[])((Object3D[])this.list3D.toArray(this.minimalObjects));
         }
      }
   }

   public void draw(DrawingPanel3D var1, Graphics2D var2, int var3) {
      System.out.println("ElementSet draw (i): I should not be called!");
   }

   public void drawQuickly(DrawingPanel3D var1, Graphics2D var2) {
      for(int var3 = this.numElements - 1; var3 >= 0; --var3) {
         this.elements[var3].drawQuickly(var1, var2);
      }

   }

   public void draw(DrawingPanel var1, Graphics var2) {
      for(int var3 = this.numElements - 1; var3 >= 0; --var3) {
         this.elements[var3].draw(var1, var2);
      }

   }

   public boolean isMeasured() {
      return this.numElements > 0;
   }

   public double getXMin() {
      double var1 = Double.MAX_VALUE;

      for(int var5 = 0; var5 < this.numElements; ++var5) {
         double var3;
         if ((var3 = this.elements[var5].getXMin()) < var1) {
            var1 = var3;
         }
      }

      return var1;
   }

   public double getXMax() {
      double var1 = -1.7976931348623157E308D;

      for(int var5 = 0; var5 < this.numElements; ++var5) {
         double var3;
         if ((var3 = this.elements[var5].getXMax()) > var1) {
            var1 = var3;
         }
      }

      return var1;
   }

   public double getYMin() {
      double var1 = Double.MAX_VALUE;

      for(int var5 = 0; var5 < this.numElements; ++var5) {
         double var3;
         if ((var3 = this.elements[var5].getYMin()) < var1) {
            var1 = var3;
         }
      }

      return var1;
   }

   public double getYMax() {
      double var1 = -1.7976931348623157E308D;

      for(int var5 = 0; var5 < this.numElements; ++var5) {
         double var3;
         if ((var3 = this.elements[var5].getYMax()) > var1) {
            var1 = var3;
         }
      }

      return var1;
   }

   public double getZMin() {
      double var1 = Double.MAX_VALUE;

      for(int var5 = 0; var5 < this.numElements; ++var5) {
         double var3;
         if ((var3 = this.elements[var5].getZMin()) < var1) {
            var1 = var3;
         }
      }

      return var1;
   }

   public double getZMax() {
      double var1 = -1.7976931348623157E308D;

      for(int var5 = 0; var5 < this.numElements; ++var5) {
         double var3;
         if ((var3 = this.elements[var5].getZMax()) > var1) {
            var1 = var3;
         }
      }

      return var1;
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
}
