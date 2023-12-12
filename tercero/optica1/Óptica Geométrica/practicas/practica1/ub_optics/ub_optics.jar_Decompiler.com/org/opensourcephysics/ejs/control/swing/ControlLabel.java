package org.opensourcephysics.ejs.control.swing;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JLabel;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlLabel extends ControlSwingElement {
   protected JLabel label;
   private String imageFile = null;
   private static ArrayList infoList = null;

   public ControlLabel(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof JLabel) {
         this.label = (JLabel)var1;
      } else {
         this.label = new JLabel();
      }

      return this.label;
   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("text");
         infoList.add("image");
         infoList.add("alignment");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("text")) {
         return "String NotTrimmed";
      } else if (var1.equals("image")) {
         return "File|String";
      } else {
         return var1.equals("alignment") ? "Alignment|int" : super.getPropertyInfo(var1);
      }
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         this.label.setText(var2.getString());
         break;
      case 1:
         if (var2.getString().equals(this.imageFile)) {
            return;
         }

         this.label.setIcon(this.getIcon(this.imageFile = var2.getString()));
         break;
      case 2:
         this.label.setHorizontalAlignment(var2.getInteger());
         break;
      default:
         super.setValue(var1 - 3, var2);
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         this.label.setText("");
         break;
      case 1:
         this.label.setIcon((Icon)null);
         this.imageFile = null;
         break;
      case 2:
         this.label.setHorizontalAlignment(0);
         break;
      default:
         super.setDefaultValue(var1 - 3);
      }

   }

   public Value getValue(int var1) {
      switch(var1) {
      case 0:
      case 1:
      case 2:
         return null;
      default:
         return super.getValue(var1 - 3);
      }
   }
}
