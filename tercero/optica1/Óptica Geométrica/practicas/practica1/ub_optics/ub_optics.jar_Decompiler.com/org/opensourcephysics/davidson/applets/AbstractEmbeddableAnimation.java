package org.opensourcephysics.davidson.applets;

import org.opensourcephysics.controls.AbstractAnimation;
import org.opensourcephysics.controls.Control;

public abstract class AbstractEmbeddableAnimation extends AbstractAnimation implements Embeddable {
   protected ObjectManager objectManager = new ObjectManager();
   protected double timeMax = 3.4028234663852886E38D;
   protected String timeMsg = "Done";

   public Control getControl() {
      return super.control;
   }

   public ObjectManager getManager() {
      return this.objectManager;
   }

   public void setControl(Control var1) {
      this.stopAnimation();
      super.control = var1;
      if (var1 != null) {
         this.resetAnimation();
      }
   }

   public void setMaxTime(double var1, String var3) {
      this.timeMax = var1;
      this.timeMsg = var3;
   }

   public synchronized void startStop() {
      if (super.animationThread == null) {
         this.startAnimation();
      } else {
         this.stopAnimation();
      }

   }
}
