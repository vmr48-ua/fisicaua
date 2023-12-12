package org.opensourcephysics.ejs.control.swing;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.opensourcephysics.ejs.control.value.DoubleValue;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlSlider extends ControlSwingElement {
   private static final int RESOLUTION = 100000;
   private static final int VARIABLE = 0;
   protected JSlider slider;
   private DoubleValue internalValue;
   private boolean recalculate = true;
   private boolean defaultValueSet;
   private int ticks = 0;
   private double defaultValue;
   private double scale;
   private double minimum = 0.0D;
   private double maximum = 1.0D;
   private TitledBorder titledBorder;
   private EtchedBorder etchedBorder;
   private DecimalFormat format = null;
   private DecimalFormat ticksFormat = null;
   private static ArrayList infoList = null;
   private boolean adjusted = false;

   public ControlSlider(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof JSlider) {
         this.slider = (JSlider)var1;
      } else {
         this.slider = new JSlider();
         this.slider.setPaintLabels(false);
         this.slider.setPaintTicks(false);
         this.slider.setPaintTrack(true);
      }

      this.slider.setMinimum(0);
      this.slider.setMaximum(100000);
      this.slider.setValue(0);
      this.etchedBorder = new EtchedBorder(1);
      this.titledBorder = new TitledBorder(this.etchedBorder, "");
      this.titledBorder.setTitleJustification(2);
      this.slider.setBorder(this.etchedBorder);
      this.defaultValue = 0.0D;
      this.defaultValueSet = false;
      this.internalValue = new DoubleValue(this.defaultValue);
      this.minimum = 0.0D;
      this.maximum = 1.0D;
      this.scale = 100000.0D * (this.maximum - this.minimum);
      this.setMaximum(this.maximum);
      this.internalValue.value = this.minimum + (double)this.slider.getValue() / this.scale;
      this.slider.addChangeListener(new ControlSlider.MyChangeListener());
      this.slider.addMouseListener(new ControlSlider.MyMouseListener());
      return this.slider;
   }

   private void setTheValue(double var1) {
      this.internalValue.value = var1;
      this.recalculate = false;
      this.slider.setValue((int)((this.internalValue.value - this.minimum) * this.scale));
      this.recalculate = true;
      if (this.format != null) {
         this.titledBorder.setTitle(this.format.format(this.internalValue.value));
         this.slider.repaint();
      }

   }

   public void reset() {
      if (this.defaultValueSet) {
         this.setTheValue(this.defaultValue);
         this.variableChanged(0, this.internalValue);
      }

   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("variable");
         infoList.add("value");
         infoList.add("minimum");
         infoList.add("maximum");
         infoList.add("pressaction");
         infoList.add("dragaction");
         infoList.add("action");
         infoList.add("format");
         infoList.add("ticks");
         infoList.add("ticksFormat");
         infoList.add("closest");
         infoList.add("orientation");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("variable")) {
         return "int|double";
      } else if (var1.equals("value")) {
         return "int|double";
      } else if (var1.equals("minimum")) {
         return "int|double";
      } else if (var1.equals("maximum")) {
         return "int|double";
      } else if (var1.equals("pressaction")) {
         return "Action CONSTANT";
      } else if (var1.equals("dragaction")) {
         return "Action CONSTANT";
      } else if (var1.equals("action")) {
         return "Action CONSTANT";
      } else if (var1.equals("format")) {
         return "Format|Object";
      } else if (var1.equals("ticks")) {
         return "int    BASIC";
      } else if (var1.equals("ticksFormat")) {
         return "Format|Object BASIC";
      } else if (var1.equals("closest")) {
         return "boolean BASIC";
      } else if (var1.equals("orientation")) {
         return "Orientation|int BASIC";
      } else {
         return var1.equals("enabled") ? "boolean" : super.getPropertyInfo(var1);
      }
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         if (this.internalValue.value != var2.getDouble()) {
            this.setTheValue(var2.getDouble());
         }
         break;
      case 1:
         this.defaultValueSet = true;
         this.defaultValue = var2.getDouble();
         this.setActive(false);
         this.reset();
         this.setActive(true);
         break;
      case 2:
         this.setMinimum(var2.getDouble());
         break;
      case 3:
         this.setMaximum(var2.getDouble());
         break;
      case 4:
         this.removeAction(10, this.getProperty("pressaction"));
         this.addAction(10, var2.getString());
         break;
      case 5:
         this.removeAction(1, this.getProperty("dragaction"));
         this.addAction(1, var2.getString());
         break;
      case 6:
         this.removeAction(0, this.getProperty("action"));
         this.addAction(0, var2.getString());
         break;
      case 7:
         if (var2.getObject() instanceof DecimalFormat) {
            if (this.format == (DecimalFormat)var2.getObject()) {
               return;
            }

            this.format = (DecimalFormat)var2.getObject();
            this.titledBorder.setTitle(this.format.format(this.internalValue.value));
            this.slider.setBorder(this.titledBorder);
         }
         break;
      case 8:
         if (var2.getInteger() != this.ticks) {
            this.ticks = var2.getInteger();
            this.setTicks();
         }
         break;
      case 9:
         if (var2.getObject() instanceof DecimalFormat) {
            if (this.ticksFormat == (DecimalFormat)var2.getObject()) {
               return;
            }

            this.ticksFormat = (DecimalFormat)var2.getObject();
            this.slider.setPaintLabels(true);
            this.setTicks();
         }
         break;
      case 10:
         this.slider.setSnapToTicks(var2.getBoolean());
         break;
      case 11:
         if (this.slider.getOrientation() != var2.getInteger()) {
            this.slider.setOrientation(var2.getInteger());
         }
         break;
      default:
         super.setValue(var1 - 12, var2);
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         break;
      case 1:
         this.defaultValueSet = false;
         break;
      case 2:
         this.setMinimum(0.0D);
         break;
      case 3:
         this.setMaximum(1.0D);
         break;
      case 4:
         this.removeAction(10, this.getProperty("pressaction"));
         break;
      case 5:
         this.removeAction(1, this.getProperty("dragaction"));
         break;
      case 6:
         this.removeAction(0, this.getProperty("action"));
         break;
      case 7:
         this.format = null;
         this.slider.setBorder(this.etchedBorder);
         break;
      case 8:
         this.ticks = 0;
         this.setTicks();
         break;
      case 9:
         this.ticksFormat = null;
         this.slider.setPaintLabels(false);
         this.setTicks();
         break;
      case 10:
         this.slider.setSnapToTicks(false);
         break;
      case 11:
         this.slider.setOrientation(0);
         break;
      default:
         super.setDefaultValue(var1 - 12);
      }

   }

   public Value getValue(int var1) {
      switch(var1) {
      case 0:
         return this.internalValue;
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
         return null;
      default:
         return super.getValue(var1 - 12);
      }
   }

   private void setTicks() {
      if (this.ticks < 2) {
         this.slider.setPaintTicks(false);
      } else {
         int var1 = 100000 / (this.ticks - 1);
         this.slider.setMinorTickSpacing(var1);
         this.slider.setMajorTickSpacing(2 * var1);
         this.slider.setPaintTicks(true);
         if (this.ticksFormat != null) {
            Hashtable var2 = new Hashtable();

            for(int var3 = 0; var3 <= 100000; var3 += 2 * var1) {
               var2.put(new Integer(var3), new JLabel(this.ticksFormat.format(this.minimum + (double)var3 / this.scale)));
            }

            this.slider.setLabelTable(var2);
         }

      }
   }

   private void setMinimum(double var1) {
      if (var1 != this.minimum) {
         this.minimum = var1;
         if (this.minimum >= this.maximum) {
            this.maximum = this.minimum + 1.0D;
         }

         this.scale = 100000.0D / (this.maximum - this.minimum);
         this.setTicks();
         this.setTheValue(this.internalValue.value);
      }
   }

   private void setMaximum(double var1) {
      if (var1 != this.maximum) {
         this.maximum = var1;
         if (this.minimum >= this.maximum) {
            this.minimum = this.maximum - 1.0D;
         }

         this.scale = 100000.0D / (this.maximum - this.minimum);
         this.setTicks();
         this.setTheValue(this.internalValue.value);
      }
   }

   private class MyMouseListener extends MouseAdapter {
      private MyMouseListener() {
      }

      public void mousePressed(MouseEvent var1) {
         ControlSlider.this.invokeActions(10);
      }

      public void mouseReleased(MouseEvent var1) {
         ControlSlider.this.invokeActions(0);
      }

      // $FF: synthetic method
      MyMouseListener(Object var2) {
         this();
      }
   }

   private class MyChangeListener implements ChangeListener {
      private MyChangeListener() {
      }

      public void stateChanged(ChangeEvent var1) {
         if (ControlSlider.this.recalculate) {
            double var2 = ControlSlider.this.minimum + (double)ControlSlider.this.slider.getValue() / ControlSlider.this.scale;
            ControlSlider.this.internalValue.value = var2;
            if (ControlSlider.this.format != null) {
               ControlSlider.this.titledBorder.setTitle(ControlSlider.this.format.format(ControlSlider.this.internalValue.value));
               ControlSlider.this.slider.repaint();
            }

            ControlSlider.this.variableChanged(0, ControlSlider.this.internalValue);
         }

      }

      // $FF: synthetic method
      MyChangeListener(Object var2) {
         this();
      }
   }
}
