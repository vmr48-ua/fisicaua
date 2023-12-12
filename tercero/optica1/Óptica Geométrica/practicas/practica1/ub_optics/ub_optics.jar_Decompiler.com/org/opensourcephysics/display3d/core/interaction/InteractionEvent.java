package org.opensourcephysics.display3d.core.interaction;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class InteractionEvent extends ActionEvent {
   public static final int MOUSE_PRESSED = 2000;
   public static final int MOUSE_DRAGGED = 2001;
   public static final int MOUSE_RELEASED = 2002;
   public static final int MOUSE_ENTERED = 2003;
   public static final int MOUSE_EXITED = 2004;
   public static final int MOUSE_MOVED = 2005;
   private Object info;
   private MouseEvent mouseEvent;

   public InteractionEvent(Object var1, int var2, String var3, Object var4, MouseEvent var5) {
      super(var1, var2, var3);
      this.info = var4;
      this.mouseEvent = var5;
   }

   public Object getInfo() {
      return this.info;
   }

   public MouseEvent getMouseEvent() {
      return this.mouseEvent;
   }
}
