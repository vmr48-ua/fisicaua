package org.opensourcephysics.ejs.control.swing;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTabbedPane;
import org.opensourcephysics.ejs.control.ControlElement;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlTabbedPanel extends ControlContainer {
   protected JTabbedPane tabbedpanel;
   private static ArrayList infoList = null;

   public ControlTabbedPanel(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof JTabbedPane) {
         this.tabbedpanel = (JTabbedPane)var1;
      } else {
         this.tabbedpanel = new JTabbedPane(1);
      }

      return this.tabbedpanel;
   }

   public void add(ControlElement var1) {
      String var2 = var1.getProperty("name");
      if (var2 != null) {
         this.tabbedpanel.add(var1.getComponent(), var2);
      } else {
         this.tabbedpanel.add(var1.getComponent(), "   ");
      }

      if (var1 instanceof ControlRadioButton) {
         super.radioButtons.add(var1);
         ((ControlRadioButton)var1).setParent(this);
      }

   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("placement");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      return var1.equals("placement") ? "Placement|int" : super.getPropertyInfo(var1);
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         if (this.tabbedpanel.getTabPlacement() != var2.getInteger()) {
            this.tabbedpanel.setTabPlacement(var2.getInteger());
         }
         break;
      default:
         super.setValue(var1 - 1, var2);
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         this.tabbedpanel.setTabPlacement(1);
         break;
      default:
         super.setDefaultValue(var1 - 1);
      }

   }

   public Value getValue(int var1) {
      switch(var1) {
      case 0:
         return null;
      default:
         return super.getValue(var1 - 1);
      }
   }
}
