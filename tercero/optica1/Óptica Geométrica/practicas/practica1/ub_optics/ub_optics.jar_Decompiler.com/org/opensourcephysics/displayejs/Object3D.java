package org.opensourcephysics.displayejs;

public class Object3D {
   public Drawable3D drawable3D = null;
   public int index = -1;
   public double distance = Double.NaN;

   public Object3D(Drawable3D var1, int var2) {
      this.drawable3D = var1;
      this.index = var2;
      this.distance = Double.NaN;
   }
}
