package org.opensourcephysics.ejs.control.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JTextField;
import org.opensourcephysics.ejs.control.value.StringValue;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlTextField extends ControlSwingElement {
   private static final int VARIABLE = 0;
   private static final int BACKGROUND = 11;
   protected JTextField textfield;
   private StringValue internalValue;
   private boolean defaultValueSet;
   private String defaultValue;
   private Color defaultColor;
   private Color editingColor;
   private Color errorColor;
   private static ArrayList infoList = null;

   public ControlTextField(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof JTextField) {
         this.textfield = (JTextField)var1;
      } else {
         this.textfield = new JTextField();
         this.textfield.setText("");
      }

      this.defaultValue = this.textfield.getText();
      this.textfield.addActionListener(new ControlTextField.MyActionListener());
      this.textfield.addKeyListener(new ControlTextField.MyKeyListener());
      this.defaultValueSet = false;
      this.internalValue = new StringValue(this.defaultValue);
      this.decideColors(this.textfield.getBackground());
      return this.textfield;
   }

   public void reset() {
      if (this.defaultValueSet) {
         this.setTheValue(this.defaultValue);
         this.setInternalValue(this.defaultValue);
      }

   }

   private void setTheValue(String var1) {
      if (!this.internalValue.value.equals(var1)) {
         this.textfield.setText(this.internalValue.value = var1);
         this.setColor(this.defaultColor);
      }
   }

   private void setInternalValue(String var1) {
      this.internalValue.value = var1;
      this.variableChanged(0, this.internalValue);
      this.invokeActions();
   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("variable");
         infoList.add("value");
         infoList.add("editable");
         infoList.add("action");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("variable")) {
         return "String VARIABLE_EXPECTED";
      } else if (var1.equals("value")) {
         return "String CONSTANT";
      } else if (var1.equals("editable")) {
         return "boolean";
      } else {
         return var1.equals("action") ? "Action CONSTANT" : super.getPropertyInfo(var1);
      }
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         this.setTheValue(var2.getString());
         break;
      case 1:
         this.defaultValueSet = true;
         this.defaultValue = var2.getString();
         this.setActive(false);
         this.reset();
         this.setActive(true);
         break;
      case 2:
         this.textfield.setEditable(var2.getBoolean());
         break;
      case 3:
         this.removeAction(0, this.getProperty("action"));
         this.addAction(0, var2.getString());
         break;
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      default:
         super.setValue(var1 - 4, var2);
         break;
      case 11:
         super.setValue(7, var2);
         this.decideColors(this.getVisual().getBackground());
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
         this.textfield.setEditable(true);
         break;
      case 3:
         this.removeAction(0, this.getProperty("action"));
         break;
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      default:
         super.setDefaultValue(var1 - 4);
         break;
      case 11:
         super.setDefaultValue(7);
         this.decideColors(this.getVisual().getBackground());
      }

   }

   public Value getValue(int var1) {
      switch(var1) {
      case 0:
         return this.internalValue;
      case 1:
      case 2:
      case 3:
         return null;
      default:
         return super.getValue(var1 - 4);
      }
   }

   private void setColor(Color var1) {
      if (this.textfield.isEditable()) {
         this.getVisual().setBackground(var1);
      }

   }

   private void decideColors(Color var1) {
      if (var1 != null) {
         this.defaultColor = var1;
         if (this.defaultColor.equals(Color.yellow)) {
            this.editingColor = Color.orange;
         } else {
            this.editingColor = Color.yellow;
         }

         if (this.defaultColor.equals(Color.red)) {
            this.errorColor = Color.magenta;
         } else {
            this.errorColor = Color.red;
         }

      }
   }

   private class MyKeyListener implements KeyListener {
      private MyKeyListener() {
      }

      public void keyPressed(KeyEvent var1) {
         this.processKeyEvent(var1, 0);
      }

      public void keyReleased(KeyEvent var1) {
         this.processKeyEvent(var1, 1);
      }

      public void keyTyped(KeyEvent var1) {
         this.processKeyEvent(var1, 2);
      }

      private void processKeyEvent(KeyEvent var1, int var2) {
         if (ControlTextField.this.textfield.isEditable()) {
            if (var1.getKeyChar() != '\n') {
               ControlTextField.this.setColor(ControlTextField.this.editingColor);
            }

            if (var1.getKeyCode() == 27) {
               ControlTextField.this.setValue(0, ControlTextField.this.internalValue);
            }

         }
      }

      // $FF: synthetic method
      MyKeyListener(Object var2) {
         this();
      }
   }

   private class MyActionListener implements ActionListener {
      private MyActionListener() {
      }

      public void actionPerformed(ActionEvent var1) {
         ControlTextField.this.setInternalValue(ControlTextField.this.textfield.getText());
         ControlTextField.this.setColor(ControlTextField.this.defaultColor);
      }

      // $FF: synthetic method
      MyActionListener(Object var2) {
         this();
      }
   }
}
