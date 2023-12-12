package org.opensourcephysics.ejs.control.swing;

import java.awt.Component;
import java.util.ArrayList;
import org.opensourcephysics.displayejs.DrawingPanel3D;
import org.opensourcephysics.displayejs.InteractionEvent;
import org.opensourcephysics.displayejs.InteractionListener;
import org.opensourcephysics.displayejs.Point3D;
import org.opensourcephysics.ejs.control.value.DoubleValue;
import org.opensourcephysics.ejs.control.value.IntegerValue;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlDrawingPanel3DNew extends ControlDrawablesParent implements InteractionListener {
   private static final double TO_RAD = 0.017453292519943295D;
   private static final int[] posIndexes = new int[]{9, 10, 11};
   private static final int[] angleIndexes = new int[]{15, 16};
   private static final int ZOOM_INDEX = 17;
   private static final int[] panIndexes = new int[]{18, 19};
   protected DrawingPanel3D drawingPanel3D;
   private double minX;
   private double maxX;
   private double minY;
   private double maxY;
   private double minZ;
   private double maxZ;
   private DoubleValue zoomValue;
   private DoubleValue[] angleValues;
   private IntegerValue[] panValues;
   private DoubleValue[] posValues = new DoubleValue[]{new DoubleValue(0.0D), new DoubleValue(0.0D), new DoubleValue(0.0D)};
   private static ArrayList infoList = null;

   public ControlDrawingPanel3DNew(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof DrawingPanel3D) {
         this.drawingPanel3D = (DrawingPanel3D)var1;
      } else {
         this.drawingPanel3D = new DrawingPanel3D();
      }

      this.minX = this.drawingPanel3D.getXMin();
      this.maxX = this.drawingPanel3D.getXMax();
      this.minY = this.drawingPanel3D.getYMin();
      this.maxY = this.drawingPanel3D.getYMax();
      this.minZ = this.drawingPanel3D.getZMin();
      this.maxZ = this.drawingPanel3D.getZMax();
      this.angleValues = new DoubleValue[2];
      this.angleValues[0] = new DoubleValue(this.drawingPanel3D.getAlpha());
      this.angleValues[1] = new DoubleValue(this.drawingPanel3D.getBeta());
      this.zoomValue = new DoubleValue(this.drawingPanel3D.getZoom());
      this.panValues = new IntegerValue[2];
      this.panValues[0] = new IntegerValue(this.drawingPanel3D.getPan().x);
      this.panValues[1] = new IntegerValue(this.drawingPanel3D.getPan().y);
      this.drawingPanel3D.addListener(this);
      this.drawingPanel3D.render();
      return this.drawingPanel3D;
   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("autoscaleX");
         infoList.add("autoscaleY");
         infoList.add("autoscaleZ");
         infoList.add("minimumX");
         infoList.add("maximumX");
         infoList.add("minimumY");
         infoList.add("maximumY");
         infoList.add("minimumZ");
         infoList.add("maximumZ");
         infoList.add("x");
         infoList.add("y");
         infoList.add("z");
         infoList.add("pressaction");
         infoList.add("dragaction");
         infoList.add("action");
         infoList.add("alpha");
         infoList.add("beta");
         infoList.add("zoom");
         infoList.add("panx");
         infoList.add("pany");
         infoList.add("displayMode");
         infoList.add("decoration");
         infoList.add("square");
         infoList.add("cursorMode");
         infoList.add("showCoordinates");
         infoList.add("hideLines");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("autoscaleX")) {
         return "boolean";
      } else if (var1.equals("autoscaleY")) {
         return "boolean";
      } else if (var1.equals("autoscaleZ")) {
         return "boolean";
      } else if (var1.equals("minimumX")) {
         return "int|double";
      } else if (var1.equals("maximumX")) {
         return "int|double";
      } else if (var1.equals("minimumY")) {
         return "int|double";
      } else if (var1.equals("maximumY")) {
         return "int|double";
      } else if (var1.equals("minimumZ")) {
         return "int|double";
      } else if (var1.equals("maximumZ")) {
         return "int|double";
      } else if (var1.equals("x")) {
         return "int|double";
      } else if (var1.equals("y")) {
         return "int|double";
      } else if (var1.equals("z")) {
         return "int|double";
      } else if (var1.equals("action")) {
         return "Action CONSTANT";
      } else if (var1.equals("pressaction")) {
         return "Action CONSTANT";
      } else if (var1.equals("dragaction")) {
         return "Action CONSTANT";
      } else if (var1.equals("alpha")) {
         return "int|double BASIC";
      } else if (var1.equals("beta")) {
         return "int|double BASIC";
      } else if (var1.equals("zoom")) {
         return "int|double BASIC";
      } else if (var1.equals("panx")) {
         return "int|double BASIC HIDDEN";
      } else if (var1.equals("pany")) {
         return "int|double BASIC HIDDEN";
      } else if (var1.equals("displayMode")) {
         return "int|DisplayMode BASIC";
      } else if (var1.equals("decoration")) {
         return "int|Decoration BASIC";
      } else if (var1.equals("square")) {
         return "boolean BASIC";
      } else if (var1.equals("cursorMode")) {
         return "int|CursorMode BASIC";
      } else if (var1.equals("showCoordinates")) {
         return "boolean BASIC";
      } else {
         return var1.equals("hideLines") ? "boolean BASIC" : super.getPropertyInfo(var1);
      }
   }

   public Value parseConstant(String var1, String var2) {
      if (var2 == null) {
         return null;
      } else {
         if (var1.indexOf("DisplayMode") >= 0) {
            var2 = var2.trim().toLowerCase();
            if (var2.equals("perspective")) {
               return new IntegerValue(10);
            }

            if (var2.equals("no_perspective")) {
               return new IntegerValue(12);
            }

            if (var2.equals("planar_xy")) {
               return new IntegerValue(0);
            }

            if (var2.equals("planar_xz")) {
               return new IntegerValue(1);
            }

            if (var2.equals("planar_yz")) {
               return new IntegerValue(2);
            }
         }

         if (var1.indexOf("Decoration") >= 0) {
            var2 = var2.trim().toLowerCase();
            if (var2.equals("none")) {
               return new IntegerValue(0);
            }

            if (var2.equals("cube")) {
               return new IntegerValue(2);
            }

            if (var2.equals("axes")) {
               return new IntegerValue(1);
            }
         }

         if (var1.indexOf("CursorMode") >= 0) {
            var2 = var2.trim().toLowerCase();
            if (var2.equals("none")) {
               return new IntegerValue(0);
            }

            if (var2.equals("cube")) {
               return new IntegerValue(2);
            }

            if (var2.equals("crosshair")) {
               return new IntegerValue(3);
            }

            if (var2.equals("xyz")) {
               return new IntegerValue(1);
            }
         }

         return super.parseConstant(var1, var2);
      }
   }

   public void setValue(int var1, Value var2) {
      double var3;
      switch(var1) {
      case 0:
         this.drawingPanel3D.setAutoscaleX(var2.getBoolean());
         break;
      case 1:
         this.drawingPanel3D.setAutoscaleY(var2.getBoolean());
         break;
      case 2:
         this.drawingPanel3D.setAutoscaleZ(var2.getBoolean());
         break;
      case 3:
         if (var2.getDouble() != this.minX) {
            this.drawingPanel3D.setPreferredMinMaxX(this.minX = var2.getDouble(), this.maxX);
         }
         break;
      case 4:
         if (var2.getDouble() != this.maxX) {
            this.drawingPanel3D.setPreferredMinMaxX(this.minX, this.maxX = var2.getDouble());
         }
         break;
      case 5:
         if (var2.getDouble() != this.minY) {
            this.drawingPanel3D.setPreferredMinMaxY(this.minY = var2.getDouble(), this.maxY);
         }
         break;
      case 6:
         if (var2.getDouble() != this.maxY) {
            this.drawingPanel3D.setPreferredMinMaxY(this.minY, this.maxY = var2.getDouble());
         }
         break;
      case 7:
         if (var2.getDouble() != this.minZ) {
            this.drawingPanel3D.setPreferredMinMaxZ(this.minZ = var2.getDouble(), this.maxZ);
         }
         break;
      case 8:
         if (var2.getDouble() != this.maxZ) {
            this.drawingPanel3D.setPreferredMinMaxZ(this.minZ, this.maxZ = var2.getDouble());
         }
         break;
      case 9:
         this.posValues[0].value = var2.getDouble();
         break;
      case 10:
         this.posValues[1].value = var2.getDouble();
         break;
      case 11:
         this.posValues[2].value = var2.getDouble();
         break;
      case 12:
         this.removeAction(10, this.getProperty("pressaction"));
         this.addAction(10, var2.getString());
         break;
      case 13:
         this.removeAction(1, this.getProperty("dragaction"));
         this.addAction(1, var2.getString());
         break;
      case 14:
         this.removeAction(0, this.getProperty("action"));
         this.addAction(0, var2.getString());
         break;
      case 15:
         if (var2 instanceof IntegerValue) {
            var3 = (double)var2.getInteger() * 0.017453292519943295D;
         } else {
            var3 = var2.getDouble();
         }

         if (var3 != this.angleValues[0].value) {
            this.drawingPanel3D.setAlpha(this.angleValues[0].value = var3);
         }
         break;
      case 16:
         if (var2 instanceof IntegerValue) {
            var3 = (double)var2.getInteger() * 0.017453292519943295D;
         } else {
            var3 = var2.getDouble();
         }

         if (var3 != this.angleValues[1].value) {
            this.drawingPanel3D.setBeta(this.angleValues[1].value = var3);
         }
         break;
      case 17:
         if (var2.getDouble() != this.zoomValue.value) {
            this.drawingPanel3D.setZoom(this.zoomValue.value = var2.getDouble());
         }
         break;
      case 18:
         if (var2.getInteger() != this.panValues[0].value) {
            this.drawingPanel3D.setPan(this.panValues[0].value = var2.getInteger(), this.panValues[1].value);
         }
         break;
      case 19:
         if (var2.getInteger() != this.panValues[1].value) {
            this.drawingPanel3D.setPan(this.panValues[0].value, this.panValues[1].value = var2.getInteger());
         }
         break;
      case 20:
         if (var2.getInteger() != this.drawingPanel3D.getDisplayMode()) {
            this.drawingPanel3D.setDisplayMode(var2.getInteger());
         }
         break;
      case 21:
         if (var2.getInteger() != this.drawingPanel3D.getDecorationType()) {
            this.drawingPanel3D.setDecorationType(var2.getInteger());
         }
         break;
      case 22:
         if (var2.getBoolean() != this.drawingPanel3D.isSquareAspect()) {
            this.drawingPanel3D.setSquareAspect(var2.getBoolean());
         }
         break;
      case 23:
         if (var2.getInteger() != this.drawingPanel3D.getCursorMode()) {
            this.drawingPanel3D.setCursorMode(var2.getInteger());
         }
         break;
      case 24:
         this.drawingPanel3D.setShowCoordinates(var2.getBoolean());
         break;
      case 25:
         this.drawingPanel3D.setRemoveHiddenLines(var2.getBoolean());
         break;
      default:
         super.setValue(var1 - 26, var2);
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         this.drawingPanel3D.setAutoscaleX(false);
         break;
      case 1:
         this.drawingPanel3D.setAutoscaleY(false);
         break;
      case 2:
         this.drawingPanel3D.setAutoscaleZ(false);
         break;
      case 3:
         this.drawingPanel3D.setPreferredMinMaxX(this.minX = 0.0D, this.maxX);
         break;
      case 4:
         this.drawingPanel3D.setPreferredMinMaxX(this.minX, this.maxX = 1.0D);
         break;
      case 5:
         this.drawingPanel3D.setPreferredMinMaxY(this.minY = 0.0D, this.maxY);
         break;
      case 6:
         this.drawingPanel3D.setPreferredMinMaxY(this.minY, this.maxY = 1.0D);
         break;
      case 7:
         this.drawingPanel3D.setPreferredMinMaxZ(this.minZ = 0.0D, this.maxZ);
         break;
      case 8:
         this.drawingPanel3D.setPreferredMinMaxZ(this.minZ, this.maxZ = 1.0D);
         break;
      case 9:
         this.posValues[0].value = (this.minX + this.maxX) / 2.0D;
         break;
      case 10:
         this.posValues[1].value = (this.minY + this.maxY) / 2.0D;
         break;
      case 11:
         this.posValues[2].value = (this.minZ + this.maxZ) / 2.0D;
         break;
      case 12:
         this.removeAction(10, this.getProperty("pressaction"));
         break;
      case 13:
         this.removeAction(1, this.getProperty("dragaction"));
         break;
      case 14:
         this.removeAction(0, this.getProperty("action"));
         break;
      case 15:
         this.drawingPanel3D.setAlpha(this.angleValues[0].value = 0.0D);
         break;
      case 16:
         this.drawingPanel3D.setBeta(this.angleValues[1].value = 0.0D);
         break;
      case 17:
         this.drawingPanel3D.setZoom(this.zoomValue.value = 1.0D);
         break;
      case 18:
         this.drawingPanel3D.setPan(this.panValues[0].value = 0, this.panValues[1].value);
         break;
      case 19:
         this.drawingPanel3D.setPan(this.panValues[0].value, this.panValues[1].value = 0);
         break;
      case 20:
         this.drawingPanel3D.setDisplayMode(10);
         break;
      case 21:
         this.drawingPanel3D.setDecorationType(2);
         break;
      case 22:
         this.drawingPanel3D.setSquareAspect(true);
         break;
      case 23:
         this.drawingPanel3D.setCursorMode(3);
         break;
      case 24:
         this.drawingPanel3D.setShowCoordinates(true);
         break;
      case 25:
         this.drawingPanel3D.setRemoveHiddenLines(true);
         break;
      default:
         super.setDefaultValue(var1 - 26);
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
      case 6:
      case 7:
      case 8:
      case 12:
      case 13:
      case 14:
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
         return null;
      case 9:
         return this.posValues[0];
      case 10:
         return this.posValues[1];
      case 11:
         return this.posValues[2];
      case 15:
         return this.angleValues[0];
      case 16:
         return this.angleValues[1];
      case 17:
         return this.zoomValue;
      case 18:
         return this.panValues[0];
      case 19:
         return this.panValues[1];
      default:
         return super.getValue(var1 - 26);
      }
   }

   public void interactionPerformed(InteractionEvent var1) {
      if (var1.getTarget() != null) {
         if (var1.getTarget() instanceof Point3D) {
            Point3D var2 = (Point3D)var1.getTarget();
            switch(var1.getID()) {
            case 2000:
               this.invokeActions(10);
            case 2001:
               this.posValues[0].value = var2.x;
               this.posValues[1].value = var2.y;
               this.posValues[2].value = var2.z;
               this.variablesChanged(posIndexes, this.posValues);
               break;
            case 2002:
               this.invokeActions(0);
            }

         }
      } else {
         if (var1.getID() == 2001) {
            if (this.angleValues[0].value != this.drawingPanel3D.getAlpha() || this.angleValues[1].value != this.drawingPanel3D.getBeta()) {
               this.angleValues[0].value = this.drawingPanel3D.getAlpha();
               this.angleValues[1].value = this.drawingPanel3D.getBeta();
               this.variablesChanged(angleIndexes, this.angleValues);
            }

            if (this.zoomValue.value != this.drawingPanel3D.getZoom()) {
               this.zoomValue.value = this.drawingPanel3D.getZoom();
               this.variableChanged(17, this.zoomValue);
            }

            if (this.panValues[0].value != this.drawingPanel3D.getPan().x || this.panValues[1].value != this.drawingPanel3D.getPan().y) {
               this.panValues[0].value = this.drawingPanel3D.getPan().x;
               this.panValues[1].value = this.drawingPanel3D.getPan().y;
               this.variablesChanged(panIndexes, this.panValues);
            }
         }

      }
   }
}
