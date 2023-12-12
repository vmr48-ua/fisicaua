package org.opensourcephysics.ejs.control;

import java.awt.Frame;
import java.util.Collection;
import org.opensourcephysics.controls.Control;
import org.opensourcephysics.controls.ParsableTextArea;
import org.opensourcephysics.controls.VariableNotFoundException;
import org.opensourcephysics.ejs.control.swing.ControlInputArea;
import org.opensourcephysics.ejs.control.swing.ControlTextArea;
import org.opensourcephysics.ejs.control.value.StringValue;
import org.opensourcephysics.ejs.control.value.Value;

public class EjsControl extends GroupControl implements Control {
   static String _RETURN_ = System.getProperty("line.separator");
   private ControlTextArea messageArea = null;
   private ParsableTextArea inputArea = null;
   private StringValue strValue = new StringValue("");

   public EjsControl(Object var1) {
      super(var1);
   }

   public EjsControl(Object var1, String var2, Frame var3) {
      super(var1, var2, var3);
   }

   public EjsControl() {
   }

   public ControlElement addObject(Object var1, String var2, String var3) {
      ControlElement var4 = super.addObject(var1, var2, var3);
      if (var4 instanceof ControlTextArea) {
         this.messageArea = (ControlTextArea)var4;
      } else if (var4 instanceof ControlInputArea) {
         this.inputArea = (ParsableTextArea)((ControlInputArea)var4).getVisual();
      }

      return var4;
   }

   public void reset() {
      this.clearValues();
      this.clearMessages();
      super.reset();
   }

   public void setLockValues(boolean var1) {
   }

   public Collection getPropertyNames() {
      return super.variableTable.keySet();
   }

   public void clearValues() {
      if (this.inputArea != null) {
         this.inputArea.setText("");
         this.inputArea.setCaretPosition(this.inputArea.getText().length());
      }

   }

   public void clearMessages() {
      if (this.messageArea != null) {
         this.messageArea.clear();
      }

   }

   public void println(String var1) {
      this.print(var1 + _RETURN_);
   }

   public void println() {
      this.println("");
   }

   public void print(String var1) {
      if (this.messageArea != null) {
         this.messageArea.print(var1);
      } else {
         System.out.print(var1);
      }

   }

   public void calculationDone(String var1) {
      this.println(var1);
   }

   public void setValue(String var1, Value var2) {
      if (!this.isVariableRegistered(var1) && this.inputArea != null) {
         this.inputArea.setValue(var1, var2.getString());
      } else {
         super.setValue(var1, var2);
      }

   }

   public Value getValue(String var1) {
      if (!this.isVariableRegistered(var1) && this.inputArea != null) {
         try {
            this.strValue.value = this.inputArea.getValue(var1);
            return this.strValue;
         } catch (VariableNotFoundException var3) {
         }
      }

      return super.getValue(var1);
   }
}
