package org.opensourcephysics.ejs.control.swing;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import org.opensourcephysics.display.Interactive;
import org.opensourcephysics.display.InteractiveMouseHandler;
import org.opensourcephysics.display.InteractivePanel;
import org.opensourcephysics.displayejs.InteractionEvent;
import org.opensourcephysics.displayejs.InteractionTarget;
import org.opensourcephysics.displayejs.InteractiveElement;
import org.opensourcephysics.displayejs.Point3D;
import org.opensourcephysics.ejs.control.value.DoubleValue;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlDrawingPanel extends ControlDrawablesParent implements InteractiveMouseHandler {
   private static final int[] posIndex = new int[]{6, 7};
   protected InteractivePanel drawingPanel;
   private double minX;
   private double maxX;
   private double minY;
   private double maxY;
   private Rectangle myGutters = null;
   private DoubleValue[] posValues = new DoubleValue[]{new DoubleValue(0.0D), new DoubleValue(0.0D)};
   private static ArrayList infoList = null;
   private InteractionTarget targetHit = null;

   public ControlDrawingPanel(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof InteractivePanel) {
         this.drawingPanel = (InteractivePanel)var1;
      } else {
         this.drawingPanel = new InteractivePanel();
         this.drawingPanel.enableInspector(false);
         this.drawingPanel.setSquareAspect(false);
      }

      this.drawingPanel.removeOptionController();
      this.minX = this.drawingPanel.getXMin();
      this.maxX = this.drawingPanel.getXMax();
      this.minY = this.drawingPanel.getYMin();
      this.maxY = this.drawingPanel.getYMax();
      this.drawingPanel.render();
      this.drawingPanel.setInteractiveMouseHandler(this);
      return this.drawingPanel;
   }

   protected int[] getPosIndex() {
      return posIndex;
   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("autoscaleX");
         infoList.add("autoscaleY");
         infoList.add("minimumX");
         infoList.add("maximumX");
         infoList.add("minimumY");
         infoList.add("maximumY");
         infoList.add("x");
         infoList.add("y");
         infoList.add("pressaction");
         infoList.add("dragaction");
         infoList.add("action");
         infoList.add("square");
         infoList.add("showCoordinates");
         infoList.add("gutters");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("autoscaleX")) {
         return "boolean";
      } else if (var1.equals("autoscaleY")) {
         return "boolean";
      } else if (var1.equals("minimumX")) {
         return "int|double";
      } else if (var1.equals("maximumX")) {
         return "int|double";
      } else if (var1.equals("minimumY")) {
         return "int|double";
      } else if (var1.equals("maximumY")) {
         return "int|double";
      } else if (var1.equals("x")) {
         return "int|double";
      } else if (var1.equals("y")) {
         return "int|double";
      } else if (var1.equals("action")) {
         return "Action CONSTANT";
      } else if (var1.equals("pressaction")) {
         return "Action CONSTANT";
      } else if (var1.equals("dragaction")) {
         return "Action CONSTANT";
      } else if (var1.equals("square")) {
         return "boolean BASIC";
      } else if (var1.equals("showCoordinates")) {
         return "boolean BASIC";
      } else {
         return var1.equals("gutters") ? "Margins|Object BASIC" : super.getPropertyInfo(var1);
      }
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         this.drawingPanel.setAutoscaleX(var2.getBoolean());
         break;
      case 1:
         this.drawingPanel.setAutoscaleY(var2.getBoolean());
         break;
      case 2:
         if (var2.getDouble() != this.minX) {
            this.drawingPanel.setPreferredMinMaxX(this.minX = var2.getDouble(), this.maxX);
         }
         break;
      case 3:
         if (var2.getDouble() != this.maxX) {
            this.drawingPanel.setPreferredMinMaxX(this.minX, this.maxX = var2.getDouble());
         }
         break;
      case 4:
         if (var2.getDouble() != this.minY) {
            this.drawingPanel.setPreferredMinMaxY(this.minY = var2.getDouble(), this.maxY);
         }
         break;
      case 5:
         if (var2.getDouble() != this.maxY) {
            this.drawingPanel.setPreferredMinMaxY(this.minY, this.maxY = var2.getDouble());
         }
         break;
      case 6:
         this.posValues[0].value = var2.getDouble();
         break;
      case 7:
         this.posValues[1].value = var2.getDouble();
         break;
      case 8:
         this.removeAction(10, this.getProperty("pressaction"));
         this.addAction(10, var2.getString());
         break;
      case 9:
         this.removeAction(1, this.getProperty("dragaction"));
         this.addAction(1, var2.getString());
         break;
      case 10:
         this.removeAction(0, this.getProperty("action"));
         this.addAction(0, var2.getString());
         break;
      case 11:
         this.drawingPanel.setSquareAspect(var2.getBoolean());
         break;
      case 12:
         this.drawingPanel.setShowCoordinates(var2.getBoolean());
         break;
      case 13:
         if (var2.getObject() instanceof Rectangle) {
            Rectangle var3 = (Rectangle)var2.getObject();
            if (var3 != this.myGutters) {
               this.drawingPanel.setGutters(var3.x, var3.y, var3.width, var3.height);
               this.myGutters = var3;
            }
         }
         break;
      default:
         super.setValue(var1 - 14, var2);
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         this.drawingPanel.setAutoscaleX(false);
         break;
      case 1:
         this.drawingPanel.setAutoscaleY(false);
         break;
      case 2:
         this.drawingPanel.setPreferredMinMaxX(this.minX = 0.0D, this.maxX);
         break;
      case 3:
         this.drawingPanel.setPreferredMinMaxX(this.minX, this.maxX = 1.0D);
         break;
      case 4:
         this.drawingPanel.setPreferredMinMaxY(this.minY = 0.0D, this.maxY);
         break;
      case 5:
         this.drawingPanel.setPreferredMinMaxY(this.minY, this.maxY = 1.0D);
         break;
      case 6:
         this.posValues[0].value = (this.minX + this.maxX) / 2.0D;
         break;
      case 7:
         this.posValues[1].value = (this.minY + this.maxY) / 2.0D;
         break;
      case 8:
         this.removeAction(10, this.getProperty("pressaction"));
         break;
      case 9:
         this.removeAction(1, this.getProperty("dragaction"));
         break;
      case 10:
         this.removeAction(0, this.getProperty("action"));
         break;
      case 11:
         this.drawingPanel.setSquareAspect(false);
         break;
      case 12:
         this.drawingPanel.setShowCoordinates(true);
         break;
      case 13:
         this.drawingPanel.setGutters(0, 0, 0, 0);
         this.myGutters = null;
         break;
      default:
         super.setDefaultValue(var1 - 14);
      }

   }

   public Value getValue(int var1) {
      switch(var1) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
         return null;
      case 6:
         return this.posValues[0];
      case 7:
         return this.posValues[1];
      default:
         return super.getValue(var1 - 14);
      }
   }

   public ControlDrawable getSelectedDrawable() {
      if (this.targetHit != null && this.targetHit.getSource() instanceof InteractiveElement) {
         Object var1 = ((InteractiveElement)this.targetHit.getSource()).getDataObject();
         if (var1 instanceof ControlDrawable) {
            return (ControlDrawable)var1;
         }
      }

      return null;
   }

   public void handleMouseAction(InteractivePanel var1, MouseEvent var2) {
      switch(var1.getMouseAction()) {
      case 1:
         Interactive var3 = var1.getInteractive();
         if (var3 instanceof InteractionTarget) {
            this.targetHit = (InteractionTarget)var3;
            this.targetHit.getSource().invokeActions(new InteractionEvent(this.targetHit.getSource(), 2000, (String)null, this.targetHit));
         } else {
            this.targetHit = null;
            this.mousePressed(var1.getMouseX(), var1.getMouseY());
         }
         break;
      case 2:
         if (this.targetHit != null) {
            this.targetHit.getSource().invokeActions(new InteractionEvent(this.targetHit.getSource(), 2002, (String)null, this.targetHit));
         } else {
            this.mouseReleased(var1.getMouseX(), var1.getMouseY());
         }
      case 6:
         this.targetHit = null;
         break;
      case 3:
         if (this.targetHit != null) {
            Point3D var4 = new Point3D(var1.getMouseX(), var1.getMouseY(), 0.0D);
            this.targetHit.updateHotspot(var1, var4);
            this.targetHit.getSource().invokeActions(new InteractionEvent(this.targetHit.getSource(), 2001, (String)null, this.targetHit));
            var1.repaint();
         } else {
            this.mouseDragged(var1.getMouseX(), var1.getMouseY());
         }
      case 4:
      case 5:
      default:
         break;
      case 7:
         if (var1.getInteractive() != null) {
            var1.setMouseCursor(Cursor.getPredefinedCursor(12));
         } else {
            var1.setMouseCursor(Cursor.getPredefinedCursor(1));
         }
      }

   }

   public void mousePressed(double var1, double var3) {
      this.invokeActions(10);
      this.mouseDragged(var1, var3);
   }

   public void mouseDragged(double var1, double var3) {
      this.posValues[0].value = var1;
      this.posValues[1].value = var3;
      this.variablesChanged(this.getPosIndex(), this.posValues);
   }

   public void mouseReleased(double var1, double var3) {
      this.invokeActions(0);
   }
}
