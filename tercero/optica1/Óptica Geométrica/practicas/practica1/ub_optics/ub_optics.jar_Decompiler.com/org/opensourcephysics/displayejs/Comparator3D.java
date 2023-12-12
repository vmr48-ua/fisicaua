package org.opensourcephysics.displayejs;

import java.util.Comparator;

class Comparator3D implements Comparator {
   public int compare(Object var1, Object var2) {
      try {
         if (((Object3D)var1).distance > ((Object3D)var2).distance) {
            return -1;
         } else {
            return ((Object3D)var1).distance < ((Object3D)var2).distance ? 1 : 0;
         }
      } catch (Exception var4) {
         return 0;
      }
   }
}
