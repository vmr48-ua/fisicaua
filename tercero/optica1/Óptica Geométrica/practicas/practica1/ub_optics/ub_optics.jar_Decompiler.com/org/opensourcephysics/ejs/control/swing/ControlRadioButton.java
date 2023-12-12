package org.opensourcephysics.ejs.control.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JRadioButton;
import org.opensourcephysics.ejs.control.value.BooleanValue;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlRadioButton extends ControlSwingElement {
   static final int VARIABLE = 4;
   protected JRadioButton radioButton;
   private ControlRadioButton mySelf = this;
   private ControlContainer parent;
   private BooleanValue internalValue;
   private boolean defaultState;
   private boolean defaultStateSet;
   private String imageFile = null;
   private String selectedimageFile = null;
   private static ArrayList infoList = null;

   public ControlRadioButton(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof JRadioButton) {
         this.radioButton = (JRadioButton)var1;
      } else {
         this.radioButton = new JRadioButton();
      }

      this.internalValue = new BooleanValue(this.radioButton.isSelected());
      this.defaultStateSet = false;
      this.parent = null;
      this.radioButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ControlRadioButton.this.setInternalValue(ControlRadioButton.this.radioButton.isSelected());
         }
      });
      return this.radioButton;
   }

   public void reset() {
      if (this.defaultStateSet) {
         this.radioButton.setSelected(this.defaultState);
         this.setInternalValue(this.defaultState);
      }

   }

   private void setInternalValue(boolean var1) {
      this.internalValue.value = var1;
      if (this.parent != null) {
         this.parent.informRadioGroup(this.mySelf, var1);
      }

      this.variableChanged(4, this.internalValue);
      this.invokeActions();
      if (this.internalValue.value) {
         this.invokeActions(20);
      } else {
         this.invokeActions(21);
      }

   }

   public void setParent(ControlContainer var1) {
      this.parent = var1;
   }

   void reportChanges() {
      this.variableChangedDoNotUpdate(4, this.internalValue);
   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("text");
         infoList.add("image");
         infoList.add("selectedimage");
         infoList.add("alignment");
         infoList.add("variable");
         infoList.add("selected");
         infoList.add("action");
         infoList.add("actionon");
         infoList.add("actionoff");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("text")) {
         return "String NotTrimmed";
      } else if (var1.equals("image")) {
         return "File|String";
      } else if (var1.equals("selectedimage")) {
         return "File|String";
      } else if (var1.equals("alignment")) {
         return "Alignment|int";
      } else if (var1.equals("variable")) {
         return "boolean";
      } else if (var1.equals("selected")) {
         return "boolean CONSTANT POSTPROCESS";
      } else if (var1.equals("action")) {
         return "Action CONSTANT";
      } else if (var1.equals("actionon")) {
         return "Action CONSTANT";
      } else if (var1.equals("actionoff")) {
         return "Action CONSTANT";
      } else {
         return var1.equals("enabled") ? "boolean BASIC" : super.getPropertyInfo(var1);
      }
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         this.radioButton.setText(var2.getString());
         break;
      case 1:
         if (var2.getString().equals(this.imageFile)) {
            return;
         }

         this.radioButton.setIcon(this.getIcon(this.imageFile = var2.getString()));
         break;
      case 2:
         if (var2.getString().equals(this.selectedimageFile)) {
            return;
         }

         this.radioButton.setSelectedIcon(this.getIcon(this.selectedimageFile = var2.getString()));
         break;
      case 3:
         this.radioButton.setHorizontalAlignment(var2.getInteger());
         break;
      case 4:
         this.radioButton.setSelected(this.internalValue.value = var2.getBoolean());
         break;
      case 5:
         this.defaultStateSet = true;
         this.defaultState = var2.getBoolean();
         this.setActive(false);
         this.reset();
         this.setActive(true);
         break;
      case 6:
         this.removeAction(0, this.getProperty("action"));
         this.addAction(0, var2.getString());
         break;
      case 7:
         this.removeAction(20, this.getProperty("actionon"));
         this.addAction(20, var2.getString());
         break;
      case 8:
         this.removeAction(21, this.getProperty("actionoff"));
         this.addAction(21, var2.getString());
         break;
      default:
         super.setValue(var1 - 9, var2);
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         this.radioButton.setText("");
         break;
      case 1:
         this.radioButton.setIcon((Icon)null);
         this.imageFile = null;
         break;
      case 2:
         this.radioButton.setIcon((Icon)null);
         this.selectedimageFile = null;
         break;
      case 3:
         this.radioButton.setHorizontalAlignment(0);
      case 4:
         break;
      case 5:
         this.defaultStateSet = false;
         break;
      case 6:
         this.removeAction(0, this.getProperty("action"));
         break;
      case 7:
         this.removeAction(20, this.getProperty("actionon"));
         break;
      case 8:
         this.removeAction(21, this.getProperty("actionoff"));
         break;
      default:
         super.setDefaultValue(var1 - 9);
      }

   }

   public Value getValue(int var1) {
      switch(var1) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 5:
      case 6:
      case 7:
      case 8:
         return null;
      case 4:
         return this.internalValue;
      default:
         return super.getValue(var1 - 9);
      }
   }
}
