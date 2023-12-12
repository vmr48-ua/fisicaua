package org.opensourcephysics.ejs.control.swing;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JSplitPane;
import org.opensourcephysics.ejs.control.ControlElement;
import org.opensourcephysics.ejs.control.value.IntegerValue;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlSplitPanel extends ControlContainer {
   protected JSplitPane splitpanel;
   private boolean hasOne = false;
   private static ArrayList infoList = null;

   public ControlSplitPanel(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof JSplitPane) {
         this.splitpanel = (JSplitPane)var1;
      } else {
         this.splitpanel = new JSplitPane();
         this.splitpanel.setOneTouchExpandable(true);
         this.splitpanel.setDividerLocation(-1);
      }

      return this.splitpanel;
   }

   public void reset() {
      this.splitpanel.setDividerLocation(-1);
   }

   public void add(ControlElement var1) {
      if (this.hasOne) {
         this.splitpanel.setBottomComponent(var1.getComponent());
         this.splitpanel.setDividerLocation(-1);
      } else {
         this.splitpanel.setTopComponent(var1.getComponent());
         this.splitpanel.setDividerLocation(-1);
         this.hasOne = true;
      }

      if (var1 instanceof ControlRadioButton) {
         super.radioButtons.add(var1);
         ((ControlRadioButton)var1).setParent(this);
      }

   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("orientation");
         infoList.add("expandable");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("orientation")) {
         return "Orientation|int";
      } else {
         return var1.equals("expandable") ? "boolean" : super.getPropertyInfo(var1);
      }
   }

   public Value parseConstant(String var1, String var2) {
      if (var2 == null) {
         return null;
      } else {
         if (var1.indexOf("Orientation") >= 0) {
            var2 = var2.trim().toLowerCase();
            if (var2.equals("vertical")) {
               return new IntegerValue(0);
            }

            if (var2.equals("horizontal")) {
               return new IntegerValue(1);
            }
         }

         return super.parseConstant(var1, var2);
      }
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         if (this.splitpanel.getOrientation() != var2.getInteger()) {
            this.splitpanel.setOrientation(var2.getInteger());
         }
         break;
      case 1:
         this.splitpanel.setOneTouchExpandable(var2.getBoolean());
         break;
      default:
         super.setValue(var1 - 2, var2);
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         this.splitpanel.setOrientation(1);
         break;
      case 1:
         this.splitpanel.setOneTouchExpandable(true);
         break;
      default:
         super.setDefaultValue(var1 - 2);
      }

   }

   public Value getValue(int var1) {
      switch(var1) {
      case 0:
      case 1:
         return null;
      default:
         return super.getValue(var1 - 2);
      }
   }
}
