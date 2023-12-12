package org.opensourcephysics.display;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

public class InteractivePanel extends DrawingPanel implements InteractiveMouseHandler {
   public static final int MOUSE_PRESSED = 1;
   public static final int MOUSE_RELEASED = 2;
   public static final int MOUSE_DRAGGED = 3;
   public static final int MOUSE_CLICKED = 4;
   public static final int MOUSE_ENTERED = 5;
   public static final int MOUSE_EXITED = 6;
   public static final int MOUSE_MOVED = 7;
   protected boolean containsInteractive;
   protected int mouseAction;
   protected MouseEvent mouseEvent;
   protected InteractiveMouseHandler interactive;
   private Interactive iaDragable;
   private Selectable iaSelectable;

   public InteractivePanel(InteractiveMouseHandler var1) {
      this();
      this.interactive = var1;
   }

   public InteractivePanel() {
      this.containsInteractive = false;
      this.mouseAction = 0;
      this.mouseEvent = null;
      this.interactive = null;
      this.iaDragable = null;
      this.iaSelectable = null;
      this.removeMouseListener(super.mouseController);
      this.removeMouseMotionListener(super.mouseController);
      super.mouseController = new InteractivePanel.IADMouseController();
      this.addMouseListener(super.mouseController);
      this.addMouseMotionListener(super.mouseController);
      this.interactive = this;
   }

   public synchronized void addDrawable(Drawable var1) {
      super.addDrawable(var1);
      if (var1 instanceof Interactive) {
         this.containsInteractive = true;
      }

   }

   public synchronized void clear() {
      super.clear();
      this.containsInteractive = false;
   }

   public void setInteractiveMouseHandler(InteractiveMouseHandler var1) {
      this.interactive = var1;
   }

   public void handleMouseAction(InteractivePanel var1, MouseEvent var2) {
      switch(var1.getMouseAction()) {
      case 3:
         if (this.iaDragable == null) {
            return;
         }

         double var4 = var1.getMouseX();
         double var6 = var1.getMouseY();
         if (var2.getX() < 1 + super.leftGutter) {
            var4 = var1.pixToX(1 + super.leftGutter);
         }

         if (var2.getX() > var1.getWidth() - 1 - super.rightGutter) {
            var4 = var1.pixToX(var1.getWidth() - 1 - super.rightGutter);
         }

         if (var2.getY() < 1 + super.topGutter) {
            var6 = var1.pixToY(1 + super.topGutter);
         }

         if (var2.getY() > var1.getHeight() - 1 - super.bottomGutter) {
            var6 = var1.pixToY(var1.getHeight() - 1 - super.bottomGutter);
         }

         this.iaDragable.setXY(var4, var6);
         super.validImage = false;
         if (!this.getIgnoreRepaint()) {
            var1.repaint();
         }
         break;
      case 4:
         Interactive var3 = this.getInteractive();
         if (var1.getMouseClickCount() < 2 || var3 == null || !(var3 instanceof Selectable)) {
            return;
         }

         if (this.iaSelectable != null && this.iaSelectable != var3) {
            this.iaSelectable.setSelected(false);
         }

         this.iaSelectable = (Selectable)var3;
         this.iaSelectable.toggleSelected();
         super.validImage = false;
         if (!this.getIgnoreRepaint()) {
            var1.repaint();
         }
      }

   }

   public Interactive getInteractive() {
      if (!this.containsInteractive) {
         return null;
      } else if (this.iaDragable != null) {
         return this.iaDragable;
      } else if (this.iaSelectable != null && this.iaSelectable.isSelected()) {
         Interactive var5 = this.iaSelectable.findInteractive(this, this.mouseEvent.getX(), this.mouseEvent.getY());
         return var5;
      } else {
         Object[] var1 = super.drawableList.toArray();

         for(int var2 = var1.length - 1; var2 >= 0; --var2) {
            Object var3 = var1[var2];
            if (var3 instanceof Interactive) {
               Interactive var4 = ((Interactive)var3).findInteractive(this, this.mouseEvent.getX(), this.mouseEvent.getY());
               if (var4 != null) {
                  return var4;
               }
            }
         }

         return null;
      }
   }

   public void setShowCoordinates(boolean var1) {
      super.showCoordinates = var1;
   }

   public int getMouseButton() {
      switch(this.mouseEvent.getModifiers()) {
      case 4:
         return 3;
      case 8:
         return 2;
      case 16:
         return 1;
      default:
         return 0;
      }
   }

   public int getMouseClickCount() {
      return this.mouseEvent.getClickCount();
   }

   public int getMouseAction() {
      return this.mouseAction;
   }

   public int getMouseIntX() {
      return this.mouseEvent.getX();
   }

   public int getMouseIntY() {
      return this.mouseEvent.getY();
   }

   public double getMouseX() {
      return this.pixToX(this.mouseEvent.getX());
   }

   public double getMouseY() {
      return this.pixToY(this.mouseEvent.getY());
   }

   public void saveMouseEvent(int var1, MouseEvent var2) {
      this.mouseAction = var1;
      this.mouseEvent = var2;
   }

   protected class IADMouseController extends MouseController {
      protected DecimalFormat scientificFormat = new DecimalFormat("0.###E0");
      protected DecimalFormat decimalFormat = new DecimalFormat("0.00");

      public void mousePressed(MouseEvent var1) {
         InteractivePanel.this.mouseEvent = var1;
         InteractivePanel.this.mouseAction = 1;
         if (InteractivePanel.this.interactive != null) {
            InteractivePanel.this.interactive.handleMouseAction(InteractivePanel.this, var1);
            InteractivePanel.this.iaDragable = null;
            InteractivePanel.this.iaDragable = InteractivePanel.this.getInteractive();
            if (InteractivePanel.this.iaDragable != null) {
               if (InteractivePanel.this.iaDragable instanceof Selectable) {
                  InteractivePanel.this.setMouseCursor(((Selectable)InteractivePanel.this.iaDragable).getPreferredCursor());
               } else {
                  InteractivePanel.this.setMouseCursor(Cursor.getPredefinedCursor(12));
               }
            }
         }

         if (InteractivePanel.super.showCoordinates) {
            String var2 = InteractivePanel.super.coordinateStrBuilder.getCoordinateString(InteractivePanel.this, var1);
            InteractivePanel.super.blMessageBox.setText(var2);
         }

      }

      public void mouseReleased(MouseEvent var1) {
         InteractivePanel.this.mouseEvent = var1;
         InteractivePanel.this.mouseAction = 2;
         if (InteractivePanel.this.interactive != null) {
            InteractivePanel.this.interactive.handleMouseAction(InteractivePanel.this, var1);
         }

         InteractivePanel.this.iaDragable = null;
         if (InteractivePanel.super.showCoordinates) {
            InteractivePanel.super.blMessageBox.setText((String)null);
         }

         InteractivePanel.this.setMouseCursor(Cursor.getPredefinedCursor(1));
      }

      public void mouseEntered(MouseEvent var1) {
         if (InteractivePanel.super.showCoordinates) {
            InteractivePanel.this.setMouseCursor(Cursor.getPredefinedCursor(1));
         }

         InteractivePanel.this.mouseEvent = var1;
         InteractivePanel.this.mouseAction = 5;
         if (InteractivePanel.this.interactive != null) {
            InteractivePanel.this.interactive.handleMouseAction(InteractivePanel.this, var1);
         }

      }

      public void mouseExited(MouseEvent var1) {
         InteractivePanel.this.setMouseCursor(Cursor.getPredefinedCursor(0));
         InteractivePanel.this.mouseEvent = var1;
         InteractivePanel.this.mouseAction = 6;
         if (InteractivePanel.this.interactive != null) {
            InteractivePanel.this.interactive.handleMouseAction(InteractivePanel.this, var1);
         }

      }

      public void mouseClicked(MouseEvent var1) {
         InteractivePanel.this.mouseEvent = var1;
         InteractivePanel.this.mouseAction = 4;
         if (InteractivePanel.this.interactive != null) {
            InteractivePanel.this.interactive.handleMouseAction(InteractivePanel.this, var1);
         }
      }

      public void mouseDragged(MouseEvent var1) {
         InteractivePanel.this.mouseEvent = var1;
         InteractivePanel.this.mouseAction = 3;
         if (InteractivePanel.this.interactive != null) {
            InteractivePanel.this.interactive.handleMouseAction(InteractivePanel.this, var1);
         }

         if (InteractivePanel.super.showCoordinates) {
            String var2 = InteractivePanel.super.coordinateStrBuilder.getCoordinateString(InteractivePanel.this, var1);
            InteractivePanel.super.blMessageBox.setText(var2);
         }

      }

      public void mouseMoved(MouseEvent var1) {
         InteractivePanel.this.mouseEvent = var1;
         InteractivePanel.this.mouseAction = 7;
         InteractivePanel.this.iaDragable = null;
         if (InteractivePanel.this.interactive != null) {
            InteractivePanel.this.interactive.handleMouseAction(InteractivePanel.this, var1);
            Interactive var2 = InteractivePanel.this.getInteractive();
            if (var2 == null) {
               InteractivePanel.this.setMouseCursor(Cursor.getPredefinedCursor(1));
            } else if (var2 instanceof Selectable) {
               InteractivePanel.this.setMouseCursor(((Selectable)var2).getPreferredCursor());
            } else {
               InteractivePanel.this.setMouseCursor(Cursor.getPredefinedCursor(12));
            }
         }

      }
   }
}
