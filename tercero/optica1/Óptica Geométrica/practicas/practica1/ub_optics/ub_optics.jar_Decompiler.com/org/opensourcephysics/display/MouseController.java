package org.opensourcephysics.display;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class MouseController implements MouseListener, MouseMotionListener {
   // $FF: synthetic method
   public abstract void mouseClicked(MouseEvent var1);

   // $FF: synthetic method
   public abstract void mouseDragged(MouseEvent var1);

   // $FF: synthetic method
   public abstract void mouseEntered(MouseEvent var1);

   // $FF: synthetic method
   public abstract void mouseExited(MouseEvent var1);

   // $FF: synthetic method
   public abstract void mouseMoved(MouseEvent var1);

   // $FF: synthetic method
   public abstract void mousePressed(MouseEvent var1);

   // $FF: synthetic method
   public abstract void mouseReleased(MouseEvent var1);
}
