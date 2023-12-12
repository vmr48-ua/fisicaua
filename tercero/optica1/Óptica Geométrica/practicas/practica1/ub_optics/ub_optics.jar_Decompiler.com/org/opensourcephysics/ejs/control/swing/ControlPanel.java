package org.opensourcephysics.ejs.control.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlPanel extends ControlContainer {
   protected JPanel panel;
   private LayoutManager myLayout = null;
   private Rectangle myBorder = null;
   private static ArrayList infoList = null;

   public ControlPanel(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof JPanel) {
         this.panel = (JPanel)var1;
      } else {
         this.panel = new JPanel();
      }

      return this.panel;
   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("layout");
         infoList.add("border");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("layout")) {
         return "Layout|Object";
      } else {
         return var1.equals("border") ? "Margins|Object" : super.getPropertyInfo(var1);
      }
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         if (var2.getObject() instanceof LayoutManager) {
            LayoutManager var4 = (LayoutManager)var2.getObject();
            if (var4 != this.myLayout) {
               this.getContainer().setLayout(this.myLayout = var4);
               this.panel.validate();
            }
         }
         break;
      case 1:
         if (var2.getObject() instanceof Rectangle) {
            Rectangle var3 = (Rectangle)var2.getObject();
            if (var3 != this.myBorder) {
               this.panel.setBorder(new EmptyBorder(var3.x, var3.y, var3.width, var3.height));
               this.myBorder = var3;
            }
         }
         break;
      default:
         super.setValue(var1 - 2, var2);
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         this.getContainer().setLayout(this.myLayout = new BorderLayout());
         this.panel.validate();
         break;
      case 1:
         this.panel.setBorder((Border)null);
         this.myBorder = null;
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
