package org.opensourcephysics.displayejs;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Interactive;

public class InteractiveText extends AbstractInteractiveElement {
   private double a1;
   private double b1;
   private double[] coordinates;
   private double[] pixelOrigin;
   private Object3D[] objects;

   public InteractiveText() {
      this("");
   }

   public InteractiveText(String var1) {
      this.a1 = 0.0D;
      this.b1 = 0.0D;
      this.coordinates = new double[3];
      this.pixelOrigin = new double[3];
      this.objects = new Object3D[]{new Object3D(this, 0)};
      super.style.setFont(new Font("Dialog", 0, 18));
      super.style.setDisplayObject(var1);
      super.sizeEnabled = false;
   }

   public Interactive findInteractive(DrawingPanel var1, int var2, int var3) {
      if (!super.visible) {
         return null;
      } else {
         if (super.hasChanged || var1 != super.panelWithValidProjection) {
            this.projectPoints(var1);
         }

         return super.positionEnabled && Math.abs(this.pixelOrigin[0] - (double)var2) < 5.0D && Math.abs(this.pixelOrigin[1] - (double)var3) < 5.0D ? new InteractionTargetElementPosition(this) : null;
      }
   }

   public Object3D[] getObjects3D(DrawingPanel3D var1) {
      if (!super.visible) {
         return null;
      } else {
         if (super.hasChanged || var1 != super.panelWithValidProjection) {
            this.projectPoints(var1);
         }

         return this.objects;
      }
   }

   public void draw(DrawingPanel3D var1, Graphics2D var2, int var3) {
      Color var4 = var1.projectColor(super.style.edgeColor, this.objects[0].distance);
      Object var5 = super.style.fillPattern;
      if (var5 instanceof Color) {
         var5 = var1.projectColor((Color)var5, this.objects[0].distance);
      }

      this.drawIt(var2, var4, (Paint)var5);
   }

   public void drawQuickly(DrawingPanel3D var1, Graphics2D var2) {
      if (super.visible) {
         if (super.hasChanged || var1 != super.panelWithValidProjection) {
            this.projectPoints(var1);
         }

         this.drawIt(var2, super.style.edgeColor, super.style.fillPattern);
      }
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      if (super.visible) {
         this.projectPoints(var1);
         this.drawIt((Graphics2D)var2, super.style.edgeColor, super.style.fillPattern);
      }
   }

   protected void projectPoints(DrawingPanel var1) {
      if (super.group != null) {
         this.coordinates[0] = super.group.x + super.x * super.group.sizex;
         this.coordinates[1] = super.group.y + super.y * super.group.sizey;
         this.coordinates[2] = super.group.z + super.z * super.group.sizez;
      } else {
         this.coordinates[0] = super.x;
         this.coordinates[1] = super.y;
         this.coordinates[2] = super.z;
      }

      var1.project(this.coordinates, this.pixelOrigin);
      this.objects[0].distance = this.pixelOrigin[2];
      this.a1 = this.pixelOrigin[0];
      this.b1 = this.pixelOrigin[1];
      if (super.style.displayObject != null) {
         FontMetrics var2;
         if (super.style.font != null) {
            var2 = var1.getFontMetrics(super.style.font);
         } else {
            var2 = var1.getFontMetrics(var1.getFont());
         }

         int var3 = var2.stringWidth(super.style.displayObject.toString());
         int var4 = var2.getHeight() / 2;
         switch(super.style.position) {
         case 0:
         default:
            this.a1 -= (double)var3 / 2.0D;
            this.b1 += (double)var4 / 2.0D;
            break;
         case 1:
            this.a1 -= (double)var3 / 2.0D;
            this.b1 += (double)var4;
            break;
         case 2:
            this.a1 -= (double)var3 / 2.0D;
            break;
         case 3:
            this.a1 -= (double)var3;
            this.b1 += (double)var4 / 2.0D;
            break;
         case 4:
            this.b1 += (double)var4 / 2.0D;
            break;
         case 5:
            this.a1 -= (double)var3;
            this.b1 += (double)var4;
            break;
         case 6:
            this.b1 += (double)var4;
            break;
         case 7:
            this.a1 -= (double)var3;
         case 8:
         }
      }

      super.hasChanged = false;
      super.panelWithValidProjection = var1;
   }

   private void drawIt(Graphics2D var1, Color var2, Paint var3) {
      if (super.style.displayObject != null) {
         if (super.style.font != null) {
            var1.setFont(super.style.font);
         }

         var1.setColor(var2);
         var1.drawString(super.style.displayObject.toString(), (int)this.a1, (int)this.b1);
      }
   }
}
