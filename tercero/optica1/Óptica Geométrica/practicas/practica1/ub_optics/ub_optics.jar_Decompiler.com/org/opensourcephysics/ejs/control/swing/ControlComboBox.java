package org.opensourcephysics.ejs.control.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JComboBox;
import org.opensourcephysics.ejs.control.value.StringValue;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlComboBox extends ControlSwingElement {
   private static final int VARIABLE = 0;
   private static final int BACKGROUND = 13;
   private static final int FOREGROUND = 12;
   protected JComboBox combo;
   private Component editorComponent;
   private String optionsString;
   private StringValue internalValue;
   private boolean defaultValueSet;
   private boolean defaultEditable;
   private boolean doNotUpdate = false;
   private String defaultValue;
   private Color defaultColor;
   private Color editingColor;
   private Color errorColor;
   private static ArrayList infoList = null;

   public ControlComboBox(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof JComboBox) {
         this.combo = (JComboBox)var1;
      } else {
         this.combo = new JComboBox();
      }

      this.defaultEditable = this.combo.isEditable();
      this.combo.addActionListener(new ControlComboBox.MyActionListener());
      this.editorComponent = this.combo.getEditor().getEditorComponent();
      this.editorComponent.addKeyListener(new ControlComboBox.MyKeyListener());
      this.defaultValue = "";
      this.defaultValueSet = false;
      this.internalValue = new StringValue(this.defaultValue);
      this.decideColors(this.editorComponent.getBackground());
      return this.combo;
   }

   public void reset() {
      if (this.defaultValueSet) {
         this.setTheValue(this.defaultValue);
         this.setInternalValue(this.defaultValue);
      }

   }

   private void setTheValue(String var1) {
      if (this.internalValue.value == null || !this.internalValue.value.equals(var1)) {
         this.combo.setSelectedItem(this.internalValue.value = var1);
         this.setColor(this.defaultColor);
      }
   }

   private void setInternalValue(String var1) {
      this.internalValue.value = var1;
      this.variableChanged(0, this.internalValue);
      this.invokeActions();
   }

   private void setTheOptions(String var1) {
      if (var1 == null) {
         if (this.optionsString != null) {
            this.combo.removeAllItems();
            this.optionsString = null;
         }

      } else if (!var1.equals(this.optionsString)) {
         this.doNotUpdate = true;
         this.combo.removeAllItems();
         StringTokenizer var2 = new StringTokenizer(var1, ";");

         while(var2.hasMoreTokens()) {
            this.combo.addItem(var2.nextToken());
         }

         this.optionsString = var1;
         this.doNotUpdate = false;
         if (this.combo.getItemCount() > 0) {
            this.setTheValue(this.combo.getItemAt(0).toString());
         }

      }
   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("variable");
         infoList.add("options");
         infoList.add("value");
         infoList.add("editable");
         infoList.add("editBackground");
         infoList.add("action");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("variable")) {
         return "String VARIABLE_EXPECTED";
      } else if (var1.equals("options")) {
         return "String PREVIOUS";
      } else if (var1.equals("value")) {
         return "String CONSTANT";
      } else if (var1.equals("editable")) {
         return "boolean";
      } else if (var1.equals("editBackground")) {
         return "Color|Object";
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
         this.setTheOptions(var2.getString());
         break;
      case 2:
         this.defaultValueSet = true;
         this.defaultValue = var2.getString();
         this.setActive(false);
         this.reset();
         this.setActive(true);
         break;
      case 3:
         this.combo.setEditable(var2.getBoolean());
         break;
      case 4:
         if (var2.getObject() instanceof Color) {
            this.editorComponent.setBackground((Color)var2.getObject());
         }

         this.decideColors(this.editorComponent.getBackground());
         break;
      case 5:
         this.removeAction(0, this.getProperty("action"));
         this.addAction(0, var2.getString());
         break;
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      default:
         super.setValue(var1 - 6, var2);
         break;
      case 12:
         super.setValue(6, var2);
         if (var2.getObject() instanceof Color) {
            this.editorComponent.setForeground((Color)var2.getObject());
         }
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         break;
      case 1:
         this.setTheOptions((String)null);
         break;
      case 2:
         this.defaultValueSet = false;
         break;
      case 3:
         this.combo.setEditable(this.defaultEditable);
         break;
      case 4:
         this.editorComponent.setBackground(Color.white);
         this.decideColors(this.editorComponent.getBackground());
         break;
      case 5:
         this.removeAction(0, this.getProperty("action"));
         break;
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      default:
         super.setDefaultValue(var1 - 6);
         break;
      case 12:
         super.setDefaultValue(6);
         this.editorComponent.setForeground(Color.black);
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
         return null;
      default:
         return super.getValue(var1 - 6);
      }
   }

   private void setColor(Color var1) {
      if (this.combo.isEditable()) {
         this.editorComponent.setBackground(var1);
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
         if (ControlComboBox.this.combo.isEditable()) {
            if (var1.getKeyChar() != '\n') {
               ControlComboBox.this.setColor(ControlComboBox.this.editingColor);
            }

            if (var1.getKeyCode() == 27) {
               ControlComboBox.this.setValue(0, ControlComboBox.this.internalValue);
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
         if (!ControlComboBox.this.doNotUpdate) {
            ControlComboBox.this.setInternalValue((String)ControlComboBox.this.combo.getSelectedItem());
            ControlComboBox.this.setColor(ControlComboBox.this.defaultColor);
         }
      }

      // $FF: synthetic method
      MyActionListener(Object var2) {
         this();
      }
   }
}
