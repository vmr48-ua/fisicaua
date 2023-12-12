package org.opensourcephysics.display.dialogs;

import java.awt.Toolkit;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.JTextField;

public class DecimalField extends JTextField {
   private NumberFormat format = NumberFormat.getInstance();
   private double prevValue;
   private Double maxValue;
   private Double minValue;

   public DecimalField(int var1, int var2) {
      super(var1);
      this.setDecimalPlaces(var2);
   }

   public double getValue() {
      try {
         double var1 = this.format.parse(this.getText()).doubleValue();
         if (this.minValue != null && var1 < this.minValue) {
            this.setValue(this.minValue);
            return this.minValue;
         } else if (this.maxValue != null && var1 > this.maxValue) {
            this.setValue(this.maxValue);
            return this.maxValue;
         } else {
            return var1;
         }
      } catch (ParseException var4) {
         Toolkit.getDefaultToolkit().beep();
         this.setValue(this.prevValue);
         return this.prevValue;
      }
   }

   public void setValue(double var1) {
      if (this.minValue != null) {
         var1 = Math.max(var1, this.minValue);
      }

      if (this.maxValue != null) {
         var1 = Math.min(var1, this.maxValue);
      }

      this.setText(this.format.format(var1));
      this.prevValue = var1;
   }

   public void setDecimalPlaces(int var1) {
      var1 = Math.abs(var1);
      var1 = Math.min(var1, 5);
      this.format.setMinimumIntegerDigits(1);
      this.format.setMinimumFractionDigits(var1);
      this.format.setMaximumFractionDigits(var1);
   }

   public void setMinValue(double var1) {
      this.minValue = new Double(var1);
   }

   public void setMaxValue(double var1) {
      this.maxValue = new Double(var1);
   }
}
