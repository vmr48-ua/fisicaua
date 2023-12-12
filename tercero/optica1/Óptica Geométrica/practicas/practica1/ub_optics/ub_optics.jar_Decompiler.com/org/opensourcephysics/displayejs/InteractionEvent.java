package org.opensourcephysics.displayejs;

import java.awt.event.ActionEvent;

public class InteractionEvent extends ActionEvent {
   public static final int MOUSE_PRESSED = 2000;
   public static final int MOUSE_DRAGGED = 2001;
   public static final int MOUSE_RELEASED = 2002;
   private Object target;

   public InteractionEvent(Object var1, int var2, String var3, Object var4) {
      super(var1, var2, var3);
      this.target = var4;
   }

   public Object getTarget() {
      return this.target;
   }
}
