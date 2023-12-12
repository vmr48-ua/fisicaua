package org.opensourcephysics.ejs.control.swing;

import java.awt.Component;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JProgressBar;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlBar extends ControlSwingElement {
   private static final int RESOLUTION = 100000;
   protected JProgressBar bar;
   private double scale;
   private double minimum = 0.0D;
   private double maximum = 1.0D;
   private double variable = 0.0D;
   private DecimalFormat format;
   private static ArrayList infoList = null;

   public ControlBar(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof JProgressBar) {
         this.bar = (JProgressBar)var1;
      } else {
         this.bar = new JProgressBar(0);
         this.bar.setBorderPainted(true);
         this.bar.setStringPainted(false);
      }

      this.bar.setMinimum(0);
      this.bar.setMaximum(100000);
      this.minimum = 0.0D;
      this.maximum = 1.0D;
      this.variable = (double)this.bar.getValue();
      this.scale = 100000.0D * (this.maximum - this.minimum);
      this.format = null;
      this.bar.setValue((int)((this.variable - this.minimum) * this.scale));
      return this.bar;
   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("variable");
         infoList.add("minimum");
         infoList.add("maximum");
         infoList.add("format");
         infoList.add("orientation");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("variable")) {
         return "int|double";
      } else if (var1.equals("minimum")) {
         return "int|double";
      } else if (var1.equals("maximum")) {
         return "int|double";
      } else if (var1.equals("format")) {
         return "Format|Object";
      } else {
         return var1.equals("orientation") ? "Orientation|int" : super.getPropertyInfo(var1);
      }
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         this.setValue(var2.getDouble());
         break;
      case 1:
         this.setMinimum(var2.getDouble());
         break;
      case 2:
         this.setMaximum(var2.getDouble());
         break;
      case 3:
         DecimalFormat var3;
         if (var2.getObject() instanceof DecimalFormat) {
            var3 = (DecimalFormat)var2.getObject();
         } else {
            var3 = null;
         }

         if (this.format == var3) {
            return;
         }

         this.format = var3;
         if (this.format != null) {
            this.bar.setString(this.format.format(this.variable));
            this.bar.setStringPainted(true);
         } else {
            this.bar.setStringPainted(false);
         }
         break;
      case 4:
         if (this.bar.getOrientation() != var2.getInteger()) {
            this.bar.setOrientation(var2.getInteger());
         }
         break;
      default:
         super.setValue(var1 - 5, var2);
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         break;
      case 1:
         this.setMinimum(0.0D);
         break;
      case 2:
         this.setMaximum(1.0D);
         break;
      case 3:
         this.format = null;
         this.bar.setStringPainted(false);
         break;
      case 4:
         this.bar.setOrientation(0);
         break;
      default:
         super.setDefaultValue(var1 - 5);
      }

   }

   public Value getValue(int var1) {
      switch(var1) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
         return null;
      default:
         return super.getValue(var1 - 5);
      }
   }

   private void setValue(double var1) {
      if (var1 != this.variable) {
         this.variable = var1;
         this.bar.setValue((int)((this.variable - this.minimum) * this.scale));
         if (this.format != null) {
            this.bar.setString(this.format.format(this.variable));
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
         this.bar.setValue((int)((this.variable - this.minimum) * this.scale));
         if (this.format != null) {
            this.bar.setString(this.format.format(this.variable));
         }

      }
   }

   private void setMaximum(double var1) {
      if (var1 != this.maximum) {
         this.maximum = var1;
         if (this.minimum >= this.maximum) {
            this.minimum = this.maximum - 1.0D;
         }

         this.scale = 100000.0D / (this.maximum - this.minimum);
         this.bar.setValue((int)((this.variable - this.minimum) * this.scale));
         if (this.format != null) {
            this.bar.setString(this.format.format(this.variable));
         }

      }
   }
}
