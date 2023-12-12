package org.opensourcephysics.ejs.control.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import org.opensourcephysics.ejs.control.value.DoubleValue;
import org.opensourcephysics.numerics.ParserException;
import org.opensourcephysics.numerics.SuryonoParser;

public class ControlParsedNumberField extends ControlNumberField {
   protected SuryonoParser parser = new SuryonoParser(0);

   public ControlParsedNumberField(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof JTextField) {
         super.textfield = (JTextField)var1;
      } else {
         super.textfield = new JTextField();
      }

      super.format = ControlNumberField.defaultFormat;
      super.defaultValue = 0.0D;
      super.defaultValueSet = false;
      super.internalValue = new DoubleValue(super.defaultValue);
      super.textfield.setText(super.format.format(super.internalValue.value));
      super.textfield.addActionListener(new ControlParsedNumberField.MyActionListener());
      super.textfield.addKeyListener(new ControlNumberField.MyKeyListener());
      this.decideColors(super.textfield.getBackground());
      return super.textfield;
   }

   private class MyActionListener implements ActionListener {
      private MyActionListener() {
      }

      public void actionPerformed(ActionEvent var1) {
         ControlParsedNumberField.this.setColor(ControlParsedNumberField.super.defaultColor);
         String var2 = ControlParsedNumberField.super.textfield.getText();

         try {
            ControlParsedNumberField.this.setInternalValue(ControlParsedNumberField.super.format.parse(var2).doubleValue());
         } catch (Exception var5) {
            try {
               ControlParsedNumberField.this.parser.parse(var2);
               ControlParsedNumberField.this.setInternalValue(ControlParsedNumberField.this.parser.evaluate());
            } catch (ParserException var4) {
               ControlParsedNumberField.this.setColor(ControlParsedNumberField.super.errorColor);
            }
         }
      }

      // $FF: synthetic method
      MyActionListener(Object var2) {
         this();
      }
   }
}
