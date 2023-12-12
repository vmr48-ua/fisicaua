package org.opensourcephysics.ejs.control.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JButton;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlButton extends ControlSwingElement {
   protected JButton button;
   private String imageFile = null;
   private static ArrayList infoList = null;

   public ControlButton(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof JButton) {
         this.button = (JButton)var1;
      } else {
         this.button = new JButton();
      }

      this.button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ControlButton.this.invokeActions();
         }
      });
      return this.button;
   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("text");
         infoList.add("image");
         infoList.add("alignment");
         infoList.add("action");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("text")) {
         return "String NotTrimmed";
      } else if (var1.equals("image")) {
         return "File|String";
      } else if (var1.equals("alignment")) {
         return "Alignment|int";
      } else if (var1.equals("action")) {
         return "Action CONSTANT";
      } else {
         return var1.equals("enabled") ? "boolean" : super.getPropertyInfo(var1);
      }
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         this.button.setText(var2.getString());
         break;
      case 1:
         if (var2.getString().equals(this.imageFile)) {
            return;
         }

         this.button.setIcon(this.getIcon(this.imageFile = var2.getString()));
         break;
      case 2:
         this.button.setHorizontalAlignment(var2.getInteger());
         break;
      case 3:
         this.removeAction(0, this.getProperty("action"));
         this.addAction(0, var2.getString());
         break;
      default:
         super.setValue(var1 - 4, var2);
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         this.button.setText("");
         break;
      case 1:
         this.imageFile = null;
         this.button.setIcon((Icon)null);
         break;
      case 2:
         this.button.setHorizontalAlignment(0);
         break;
      case 3:
         this.removeAction(0, this.getProperty("action"));
         break;
      default:
         super.setDefaultValue(var1 - 4);
      }

   }

   public Value getValue(int var1) {
      switch(var1) {
      case 0:
      case 1:
      case 2:
      case 3:
         return null;
      default:
         return super.getValue(var1 - 4);
      }
   }
}
