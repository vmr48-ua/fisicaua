package org.opensourcephysics.davidson.nbody;

import java.awt.Color;
import java.awt.Graphics;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLLoader;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.InteractiveCircle;

public class OrbitParticle extends InteractiveCircle {
   double x;
   double y;
   double vx;
   double vy;
   double m;
   int r = 5;

   public OrbitParticle(double var1, double var3, double var5, double var7, double var9) {
      this.x = var1;
      this.y = var5;
      this.vx = var3;
      this.vy = var7;
      this.m = var9;
      this.r = super.pixRadius;
      super.color = new Color(255, 0, 0, 156);
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      super.setXY(this.x, this.y);
      super.pixRadius = (int)Math.max(1.0D, Math.sqrt(this.m) * (double)this.r);
      super.draw(var1, var2);
   }

   public static XML.ObjectLoader getLoader() {
      return new OrbitParticle.OrbitParticleLoader();
   }

   public void setXY(double var1, double var3) {
      this.x = var1;
      this.y = var3;
      super.setXY(var1, var3);
   }

   private static class OrbitParticleLoader extends XMLLoader {
      private OrbitParticleLoader() {
      }

      public void saveObject(XMLControl var1, Object var2) {
         OrbitParticle var3 = (OrbitParticle)var2;
         var1.setValue("x", var3.x);
         var1.setValue("vx", var3.vx);
         var1.setValue("y", var3.y);
         var1.setValue("vy", var3.vy);
         var1.setValue("m", var3.m);
         var1.setValue("interaction_enabled", var3.isEnabled());
      }

      public Object createObject(XMLControl var1) {
         return new OrbitParticle(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
      }

      public Object loadObject(XMLControl var1, Object var2) {
         OrbitParticle var3 = (OrbitParticle)var2;
         var3.x = var1.getDouble("x");
         var3.vx = var1.getDouble("vx");
         var3.y = var1.getDouble("y");
         var3.vy = var1.getDouble("vy");
         var3.m = var1.getDouble("m");
         var3.setEnabled(var1.getBoolean("interaction_enabled"));
         return var2;
      }

      // $FF: synthetic method
      OrbitParticleLoader(Object var1) {
         this();
      }
   }
}
