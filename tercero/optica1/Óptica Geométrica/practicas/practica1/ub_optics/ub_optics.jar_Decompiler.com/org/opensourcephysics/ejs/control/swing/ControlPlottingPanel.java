package org.opensourcephysics.ejs.control.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import org.opensourcephysics.display.Interactive;
import org.opensourcephysics.display.InteractiveMouseHandler;
import org.opensourcephysics.display.InteractivePanel;
import org.opensourcephysics.display.PlottingPanel;
import org.opensourcephysics.display.axes.AxisFactory;
import org.opensourcephysics.display.axes.CartesianAxes;
import org.opensourcephysics.display.axes.CartesianType1;
import org.opensourcephysics.display.axes.CartesianType2;
import org.opensourcephysics.display.axes.CartesianType3;
import org.opensourcephysics.display.axes.DrawableAxes;
import org.opensourcephysics.display.axes.PolarAxes;
import org.opensourcephysics.display.axes.PolarType1;
import org.opensourcephysics.display.axes.PolarType2;
import org.opensourcephysics.displayejs.InteractionEvent;
import org.opensourcephysics.displayejs.InteractionTarget;
import org.opensourcephysics.displayejs.Point3D;
import org.opensourcephysics.ejs.control.ControlElement;
import org.opensourcephysics.ejs.control.value.DoubleValue;
import org.opensourcephysics.ejs.control.value.IntegerValue;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlPlottingPanel extends ControlDrawablesParent implements InteractiveMouseHandler {
   protected static final int ADDEDBYPLOTTINGPANEL = 28;
   static final int FONT = 36;
   private static final int[] posIndex = new int[]{18, 19};
   protected PlottingPanel plottingPanel;
   private String title;
   private String titleFontname;
   private double minX;
   private double maxX;
   private double minY;
   private double maxY;
   private Rectangle myGutters;
   private DrawableAxes axes;
   private boolean axisGridX;
   private boolean axisGridY;
   private boolean xaxisLog;
   private boolean yaxisLog;
   private int axesType;
   private double xaxisPos;
   private double yaxisPos;
   private double deltaR;
   private double deltaTheta;
   private String xLabel;
   private String yLabel;
   private String labelFontname;
   private DoubleValue[] posValues = new DoubleValue[]{new DoubleValue(0.0D), new DoubleValue(0.0D)};
   private static ArrayList infoList = null;
   private InteractionTarget targetHit = null;

   public ControlPlottingPanel(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof PlottingPanel) {
         this.plottingPanel = (PlottingPanel)var1;
      } else {
         this.plottingPanel = new PlottingPanel("", "", "");
         this.plottingPanel.enableInspector(false);
         this.plottingPanel.setSquareAspect(false);
         this.plottingPanel.setAutoscaleX(true);
         this.plottingPanel.setAutoscaleY(true);
      }

      this.plottingPanel.removeOptionController();
      this.axes = this.plottingPanel.getAxes();
      this.axes.setVisible(true);
      this.axes.setShowMajorXGrid(this.axisGridX = true);
      this.axes.setShowMajorYGrid(this.axisGridY = true);
      this.xaxisLog = this.yaxisLog = false;
      this.deltaR = 1.0D;
      this.deltaTheta = 0.39269908169872414D;
      if (this.axes instanceof CartesianAxes) {
         this.xaxisLog = ((CartesianAxes)this.axes).isXLog();
         this.yaxisLog = ((CartesianAxes)this.axes).isYLog();
         if (this.axes instanceof CartesianType1) {
            this.axesType = 1;
         } else if (this.axes instanceof CartesianType2) {
            this.axesType = 2;
         } else if (this.axes instanceof CartesianType3) {
            this.axesType = 3;
         } else {
            this.axesType = 1;
         }
      } else if (this.axes instanceof PolarAxes) {
         this.deltaR = ((PolarAxes)this.axes).getDeltaR();
         this.deltaTheta = ((PolarAxes)this.axes).getDeltaTheta();
         if (this.axes instanceof PolarType1) {
            this.axesType = 4;
         } else if (this.axes instanceof PolarType2) {
            this.axesType = 5;
         } else {
            this.axesType = 4;
         }
      } else {
         this.axesType = 0;
      }

      this.minX = this.plottingPanel.getXMin();
      this.maxX = this.plottingPanel.getXMax();
      this.minY = this.plottingPanel.getYMin();
      this.maxY = this.plottingPanel.getYMax();
      this.plottingPanel.setInteractiveMouseHandler(this);
      return this.plottingPanel;
   }

   protected int[] getPosIndex() {
      return posIndex;
   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("title");
         infoList.add("titleFont");
         infoList.add("axesType");
         infoList.add("titleX");
         infoList.add("titleY");
         infoList.add("xaxisType");
         infoList.add("yaxisType");
         infoList.add("deltaR");
         infoList.add("deltaTheta");
         infoList.add("interiorBackground");
         infoList.add("majorTicksX");
         infoList.add("majorTicksY");
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
         infoList.add("xaxisPos");
         infoList.add("yaxisPos");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("title")) {
         return "String";
      } else if (var1.equals("titleFont")) {
         return "Font|Object BASIC";
      } else if (var1.equals("axesType")) {
         return "int|AxesType BASIC";
      } else if (var1.equals("titleX")) {
         return "String";
      } else if (var1.equals("titleY")) {
         return "String";
      } else if (var1.equals("xaxisType")) {
         return "int|CartesianAxisType BASIC";
      } else if (var1.equals("yaxisType")) {
         return "int|CartesianAxisType BASIC";
      } else if (var1.equals("deltaR")) {
         return "int|double BASIC";
      } else if (var1.equals("deltaTheta")) {
         return "int|double BASIC";
      } else if (var1.equals("interiorBackground")) {
         return "Color|Object BASIC";
      } else if (var1.equals("majorTicksX")) {
         return "boolean BASIC";
      } else if (var1.equals("majorTicksY")) {
         return "boolean BASIC";
      } else if (var1.equals("autoscaleX")) {
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
      } else if (var1.equals("gutters")) {
         return "Margins|Object BASIC";
      } else if (var1.equals("xaxisPos")) {
         return "int|double BASIC";
      } else {
         return var1.equals("yaxisPos") ? "int|double BASIC" : super.getPropertyInfo(var1);
      }
   }

   public Value parseConstant(String var1, String var2) {
      if (var2 == null) {
         return null;
      } else {
         if (var1.indexOf("CartesianAxisType") >= 0) {
            var2 = var2.trim().toLowerCase();
            if (var2.equals("linear")) {
               return new IntegerValue(0);
            }

            if (var2.equals("log10")) {
               return new IntegerValue(1);
            }
         }

         if (var1.indexOf("AxesType") >= 0) {
            var2 = var2.trim().toLowerCase();
            if (var2.equals("cartesian1")) {
               return new IntegerValue(1);
            }

            if (var2.equals("cartesian2")) {
               return new IntegerValue(2);
            }

            if (var2.equals("cartesian3")) {
               return new IntegerValue(3);
            }

            if (var2.equals("polar1")) {
               return new IntegerValue(4);
            }

            if (var2.equals("polar2")) {
               return new IntegerValue(5);
            }
         }

         return super.parseConstant(var1, var2);
      }
   }

   public ControlElement setProperty(String var1, String var2) {
      var1 = var1.trim();
      if (var1.equals("xaxis")) {
         return super.setProperty("xaxisPos", var2);
      } else {
         return var1.equals("yaxis") ? super.setProperty("yaxisPos", var2) : super.setProperty(var1, var2);
      }
   }

   public void setValue(int var1, Value var2) {
      Font var3;
      switch(var1) {
      case 0:
         this.plottingPanel.setTitle(this.title = var2.getString(), this.titleFontname);
         break;
      case 1:
         if (var2.getObject() instanceof Font) {
            var3 = (Font)var2.getObject();
            this.titleFontname = var3.getFamily();
            if (var3.isPlain()) {
               this.titleFontname = this.titleFontname + "-PLAIN";
            } else if (var3.isItalic()) {
               if (var3.isBold()) {
                  this.titleFontname = this.titleFontname + "-BOLDITALIC";
               } else {
                  this.titleFontname = this.titleFontname + "-ITALIC";
               }
            } else if (var3.isBold()) {
               this.titleFontname = this.titleFontname + "-BOLD";
            }

            this.titleFontname = this.titleFontname + "-" + var3.getSize();
            if (this.title != null) {
               this.axes.setTitle(this.title, this.titleFontname);
            }
         }
         break;
      case 2:
         if (this.axesType != var2.getInteger()) {
            switch(this.axesType = var2.getInteger()) {
            case 1:
            default:
               this.axes = AxisFactory.createAxesType1(this.plottingPanel);
               ((CartesianType1)this.axes).setXLog(this.xaxisLog);
               ((CartesianType1)this.axes).setYLog(this.yaxisLog);
               break;
            case 2:
               this.axes = AxisFactory.createAxesType2(this.plottingPanel);
               ((CartesianType2)this.axes).setXLog(this.xaxisLog);
               ((CartesianType2)this.axes).setYLog(this.yaxisLog);
               break;
            case 3:
               this.axes = AxisFactory.createAxesType3(this.plottingPanel);
               ((CartesianType3)this.axes).setXLog(this.xaxisLog);
               ((CartesianType3)this.axes).setYLog(this.yaxisLog);
               break;
            case 4:
               this.axes = new PolarType1(this.plottingPanel);
               ((PolarAxes)this.axes).setDeltaR(this.deltaR);
               ((PolarAxes)this.axes).setDeltaTheta(this.deltaTheta);
               break;
            case 5:
               this.axes = new PolarType2(this.plottingPanel);
               ((PolarAxes)this.axes).setDeltaR(this.deltaR);
               ((PolarAxes)this.axes).setDeltaTheta(this.deltaTheta);
            }

            if (this.xLabel != null) {
               this.axes.setXLabel(this.xLabel, this.labelFontname);
            }

            if (this.yLabel != null) {
               this.axes.setYLabel(this.yLabel, this.labelFontname);
            }

            if (this.title != null) {
               this.axes.setTitle(this.title, this.titleFontname);
            }

            this.axes.setShowMajorXGrid(this.axisGridX);
            this.axes.setShowMajorYGrid(this.axisGridY);
            if (this.axes instanceof CartesianAxes) {
               ((CartesianAxes)this.axes).setX(this.xaxisPos);
               ((CartesianAxes)this.axes).setY(this.yaxisPos);
            }

            this.plottingPanel.setAxes(this.axes);
         }
         break;
      case 3:
         this.plottingPanel.setXLabel(this.xLabel = var2.getString(), this.labelFontname);
         break;
      case 4:
         this.plottingPanel.setYLabel(this.yLabel = var2.getString(), this.labelFontname);
         break;
      case 5:
         if (this.xaxisLog && var2.getInteger() != 1) {
            this.plottingPanel.setLogScale(this.xaxisLog = false, this.yaxisLog);
         } else if (!this.xaxisLog && var2.getInteger() == 1) {
            this.plottingPanel.setLogScale(this.xaxisLog = true, this.yaxisLog);
         }
         break;
      case 6:
         if (this.yaxisLog && var2.getInteger() != 1) {
            this.plottingPanel.setLogScale(this.xaxisLog, this.yaxisLog = false);
         } else if (!this.yaxisLog && var2.getInteger() == 1) {
            this.plottingPanel.setLogScale(this.xaxisLog, this.yaxisLog = true);
         }
         break;
      case 7:
         if (var2.getDouble() != this.deltaR) {
            this.deltaR = var2.getDouble();
            if (this.axes instanceof PolarAxes) {
               ((PolarAxes)this.axes).setDeltaR(this.deltaR);
            }
         }
         break;
      case 8:
         if (var2.getDouble() != this.deltaTheta) {
            this.deltaTheta = var2.getDouble();
            if (this.axes instanceof PolarAxes) {
               ((PolarAxes)this.axes).setDeltaTheta(this.deltaTheta);
            }
         }
         break;
      case 9:
         if (var2.getObject() instanceof Color) {
            this.axes.setInteriorBackground((Color)var2.getObject());
         }
         break;
      case 10:
         this.axes.setShowMajorXGrid(var2.getBoolean());
         break;
      case 11:
         this.axes.setShowMajorYGrid(var2.getBoolean());
         break;
      case 12:
         this.plottingPanel.setAutoscaleX(var2.getBoolean());
         break;
      case 13:
         this.plottingPanel.setAutoscaleY(var2.getBoolean());
         break;
      case 14:
         if (var2.getDouble() != this.minX) {
            this.plottingPanel.setPreferredMinMaxX(this.minX = var2.getDouble(), this.maxX);
         }
         break;
      case 15:
         if (var2.getDouble() != this.maxX) {
            this.plottingPanel.setPreferredMinMaxX(this.minX, this.maxX = var2.getDouble());
         }
         break;
      case 16:
         if (var2.getDouble() != this.minY) {
            this.plottingPanel.setPreferredMinMaxY(this.minY = var2.getDouble(), this.maxY);
         }
         break;
      case 17:
         if (var2.getDouble() != this.maxY) {
            this.plottingPanel.setPreferredMinMaxY(this.minY, this.maxY = var2.getDouble());
         }
         break;
      case 18:
         this.posValues[0].value = var2.getDouble();
         break;
      case 19:
         this.posValues[1].value = var2.getDouble();
         break;
      case 20:
         this.removeAction(10, this.getProperty("pressaction"));
         this.addAction(10, var2.getString());
         break;
      case 21:
         this.removeAction(1, this.getProperty("dragaction"));
         this.addAction(1, var2.getString());
         break;
      case 22:
         this.removeAction(0, this.getProperty("action"));
         this.addAction(0, var2.getString());
         break;
      case 23:
         this.plottingPanel.setSquareAspect(var2.getBoolean());
         break;
      case 24:
         this.plottingPanel.setShowCoordinates(var2.getBoolean());
         break;
      case 25:
         if (var2.getObject() instanceof Rectangle) {
            Rectangle var4 = (Rectangle)var2.getObject();
            if (var4 != this.myGutters) {
               this.plottingPanel.setGutters(var4.x, var4.y, var4.width, var4.height);
               this.myGutters = var4;
            }
         }
         break;
      case 26:
         if (this.xaxisPos != var2.getDouble() && this.axes instanceof CartesianAxes) {
            ((CartesianAxes)this.axes).setX(this.xaxisPos = var2.getDouble());
         }
         break;
      case 27:
         if (this.yaxisPos != var2.getDouble() && this.axes instanceof CartesianAxes) {
            ((CartesianAxes)this.axes).setY(this.yaxisPos = var2.getDouble());
         }
         break;
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      default:
         super.setValue(var1 - 28, var2);
         break;
      case 36:
         if (var2.getObject() instanceof Font) {
            var3 = (Font)var2.getObject();
            this.labelFontname = var3.getFamily();
            if (var3.isPlain()) {
               this.labelFontname = this.labelFontname + "-PLAIN";
            } else if (var3.isItalic()) {
               if (var3.isBold()) {
                  this.labelFontname = this.labelFontname + "-BOLDITALIC";
               } else {
                  this.labelFontname = this.labelFontname + "-ITALIC";
               }
            } else if (var3.isBold()) {
               this.labelFontname = this.labelFontname + "-BOLD";
            }

            this.labelFontname = this.labelFontname + "-" + var3.getSize();
            if (this.xLabel != null) {
               this.axes.setXLabel(this.xLabel, this.labelFontname);
            }

            if (this.yLabel != null) {
               this.axes.setYLabel(this.yLabel, this.labelFontname);
            }
         }

         super.setValue(8, var2);
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         this.plottingPanel.setTitle(this.title = "", this.titleFontname);
         break;
      case 1:
         this.titleFontname = "Helvetica-BOLD-14";
         if (this.title != null) {
            this.axes.setTitle(this.title, this.titleFontname);
         }
         break;
      case 2:
         this.axesType = 1;
         this.axes = AxisFactory.createAxesType1(this.plottingPanel);
         ((CartesianType1)this.axes).setXLog(this.xaxisLog);
         ((CartesianType1)this.axes).setYLog(this.yaxisLog);
         if (this.xLabel != null) {
            this.axes.setXLabel(this.xLabel, this.labelFontname);
         }

         if (this.yLabel != null) {
            this.axes.setYLabel(this.yLabel, this.labelFontname);
         }

         if (this.title != null) {
            this.axes.setTitle(this.title, this.titleFontname);
         }

         this.axes.setShowMajorXGrid(this.axisGridX);
         this.axes.setShowMajorYGrid(this.axisGridY);
         break;
      case 3:
         this.plottingPanel.setXLabel(this.xLabel = "", this.labelFontname);
         break;
      case 4:
         this.plottingPanel.setYLabel(this.yLabel = "", this.labelFontname);
         break;
      case 5:
         this.plottingPanel.setLogScale(this.xaxisLog = false, this.yaxisLog);
         break;
      case 6:
         this.plottingPanel.setLogScale(this.xaxisLog, this.yaxisLog = false);
         break;
      case 7:
         this.deltaR = 1.0D;
         if (this.axes instanceof PolarAxes) {
            ((PolarAxes)this.axes).setDeltaR(this.deltaR);
         }
         break;
      case 8:
         this.deltaTheta = 0.39269908169872414D;
         if (this.axes instanceof PolarAxes) {
            ((PolarAxes)this.axes).setDeltaTheta(this.deltaTheta);
         }
         break;
      case 9:
         this.axes.setInteriorBackground(Color.white);
         break;
      case 10:
         this.axes.setShowMajorXGrid(true);
         break;
      case 11:
         this.axes.setShowMajorYGrid(true);
         break;
      case 12:
         this.plottingPanel.setAutoscaleX(false);
         break;
      case 13:
         this.plottingPanel.setAutoscaleY(false);
         break;
      case 14:
         this.plottingPanel.setPreferredMinMaxX(this.minX = 0.0D, this.maxX);
         break;
      case 15:
         this.plottingPanel.setPreferredMinMaxX(this.minX, this.maxX = 1.0D);
         break;
      case 16:
         this.plottingPanel.setPreferredMinMaxY(this.minY = 0.0D, this.maxY);
         break;
      case 17:
         this.plottingPanel.setPreferredMinMaxY(this.minY, this.maxY = 1.0D);
         break;
      case 18:
         this.posValues[0].value = (this.minX + this.maxX) / 2.0D;
         break;
      case 19:
         this.posValues[1].value = (this.minY + this.maxY) / 2.0D;
         break;
      case 20:
         this.removeAction(10, this.getProperty("pressaction"));
         break;
      case 21:
         this.removeAction(1, this.getProperty("dragaction"));
         break;
      case 22:
         this.removeAction(0, this.getProperty("action"));
         break;
      case 23:
         this.plottingPanel.setSquareAspect(false);
         break;
      case 24:
         this.plottingPanel.setShowCoordinates(true);
         break;
      case 25:
         this.plottingPanel.setGutters(0, 0, 0, 0);
         this.myGutters = null;
         break;
      case 26:
         if (this.axes instanceof CartesianAxes) {
            ((CartesianAxes)this.axes).setX(this.xaxisPos = Double.NaN);
         }
         break;
      case 27:
         if (this.axes instanceof CartesianAxes) {
            ((CartesianAxes)this.axes).setY(this.yaxisPos = Double.NaN);
         }
         break;
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      default:
         super.setDefaultValue(var1 - 28);
         break;
      case 36:
         this.labelFontname = "Helvetica-PLAIN-12";
         if (this.xLabel != null) {
            this.axes.setXLabel(this.xLabel, this.labelFontname);
         }

         if (this.yLabel != null) {
            this.axes.setYLabel(this.yLabel, this.labelFontname);
         }

         super.setDefaultValue(8);
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
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      case 17:
         return null;
      case 18:
         return this.posValues[0];
      case 19:
         return this.posValues[1];
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      case 26:
      case 27:
         return null;
      default:
         return super.getValue(var1 - 28);
      }
   }

   public ControlDrawable getSelectedDrawable() {
      return this.targetHit != null && this.targetHit.getSource() instanceof ControlDrawable ? (ControlDrawable)this.targetHit.getSource() : null;
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
