package org.opensourcephysics.ejs.control.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JTextField;
import org.opensourcephysics.ejs.control.value.DoubleValue;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlNumberField extends ControlSwingElement {
   private static final int VARIABLE = 0;
   private static final int BACKGROUND = 12;
   protected static final DecimalFormat defaultFormat = new DecimalFormat("0.000;-0.000");
   protected JTextField textfield;
   protected DoubleValue internalValue;
   protected double defaultValue;
   protected boolean defaultValueSet;
   protected DecimalFormat format;
   protected Color defaultColor;
   protected Color editingColor;
   protected Color errorColor;
   private static ArrayList infoList = null;

   public ControlNumberField(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof JTextField) {
         this.textfield = (JTextField)var1;
      } else {
         this.textfield = new JTextField();
      }

      this.format = defaultFormat;
      this.defaultValue = 0.0D;
      this.defaultValueSet = false;
      this.internalValue = new DoubleValue(this.defaultValue);
      this.textfield.setText(this.format.format(this.internalValue.value));
      this.textfield.addActionListener(new ControlNumberField.MyActionListener());
      this.textfield.addKeyListener(new ControlNumberField.MyKeyListener());
      this.decideColors(this.textfield.getBackground());
      return this.textfield;
   }

   public void reset() {
      if (this.defaultValueSet) {
         this.setTheValue(this.defaultValue);
         this.setInternalValue(this.defaultValue);
      }

   }

   private void setTheValue(double var1) {
      if (var1 != this.internalValue.value) {
         this.internalValue.value = var1;
         this.textfield.setText(this.format.format(var1));
         this.setColor(this.defaultColor);
      }

   }

   protected void setInternalValue(double var1) {
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
         infoList.add("format");
         infoList.add("action");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("variable")) {
         return "int|double";
      } else if (var1.equals("value")) {
         return "int|double";
      } else if (var1.equals("editable")) {
         return "boolean";
      } else if (var1.equals("format")) {
         return "Format|Object";
      } else {
         return var1.equals("action") ? "Action CONSTANT" : super.getPropertyInfo(var1);
      }
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         this.setTheValue(var2.getDouble());
         break;
      case 1:
         this.defaultValueSet = true;
         this.defaultValue = var2.getDouble();
         this.setActive(false);
         this.reset();
         this.setActive(true);
         break;
      case 2:
         this.textfield.setEditable(var2.getBoolean());
         break;
      case 3:
         if (var2.getObject() instanceof DecimalFormat) {
            if (this.format == (DecimalFormat)var2.getObject()) {
               return;
            }

            this.format = (DecimalFormat)var2.getObject();
            this.setActive(false);

            try {
               this.setInternalValue(this.format.parse(this.textfield.getText()).doubleValue());
            } catch (Exception var4) {
            }

            this.setActive(true);
            this.textfield.setText(this.format.format(this.internalValue.value));
         }
         break;
      case 4:
         this.removeAction(0, this.getProperty("action"));
         this.addAction(0, var2.getString());
         break;
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      default:
         super.setValue(var1 - 5, var2);
         break;
      case 12:
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
         this.format = defaultFormat;
         this.setActive(false);

         try {
            this.setInternalValue(this.format.parse(this.textfield.getText()).doubleValue());
         } catch (Exception var3) {
         }

         this.setActive(true);
         this.textfield.setText(this.format.format(this.internalValue.value));
         break;
      case 4:
         this.removeAction(0, this.getProperty("action"));
         break;
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      default:
         super.setDefaultValue(var1 - 5);
         break;
      case 12:
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
      case 4:
         return null;
      default:
         return super.getValue(var1 - 5);
      }
   }

   protected void setColor(Color var1) {
      if (this.textfield.isEditable()) {
         this.getVisual().setBackground(var1);
      }

   }

   protected void decideColors(Color var1) {
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

   protected class MyKeyListener implements KeyListener {
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
         if (ControlNumberField.this.textfield.isEditable()) {
            if (var1.getKeyChar() != '\n') {
               ControlNumberField.this.setColor(ControlNumberField.this.editingColor);
            }

            if (var1.getKeyCode() == 27) {
               ControlNumberField.this.setValue(0, ControlNumberField.this.internalValue);
            }

         }
      }
   }

   private class MyActionListener implements ActionListener {
      private MyActionListener() {
      }

      public void actionPerformed(ActionEvent var1) {
         ControlNumberField.this.setColor(ControlNumberField.this.defaultColor);

         try {
            ControlNumberField.this.setInternalValue(ControlNumberField.this.format.parse(ControlNumberField.this.textfield.getText()).doubleValue());
         } catch (Exception var3) {
            ControlNumberField.this.setColor(ControlNumberField.this.errorColor);
         }

      }

      // $FF: synthetic method
      MyActionListener(Object var2) {
         this();
      }
   }
}
