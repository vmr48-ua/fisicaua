package org.opensourcephysics.controls;

import java.util.Collection;
import java.util.HashSet;
import javax.swing.table.AbstractTableModel;
import org.opensourcephysics.tools.ArrayInspector;

public class XMLTableModel extends AbstractTableModel {
   XMLControl control;
   boolean editable = true;
   Collection uneditablePropNames = new HashSet();

   public XMLTableModel(XMLControl var1) {
      this.control = var1;
   }

   public int getColumnCount() {
      return 2;
   }

   public String getColumnName(int var1) {
      return var1 == 0 ? "Name" : "Value";
   }

   public int getRowCount() {
      return this.control.getPropertyContent().size();
   }

   public Object getValueAt(int var1, int var2) {
      try {
         XMLProperty var3 = (XMLProperty)this.control.getPropertyContent().get(var1);
         return var2 == 0 ? var3.getPropertyName() : var3.getPropertyContent().get(0);
      } catch (Exception var4) {
         return null;
      }
   }

   public boolean isCellEditable(int var1, int var2) {
      if (var2 == 0) {
         return false;
      } else {
         String var3 = (String)this.getValueAt(var1, 0);
         if (this.uneditablePropNames.contains(var3)) {
            return false;
         } else {
            Object var4 = this.getValueAt(var1, var2);
            if (var4 instanceof XMLControl) {
               return true;
            } else if (var4 instanceof XMLProperty) {
               XMLProperty var5 = (XMLProperty)var4;
               XMLProperty var6 = var5.getParentProperty();
               return var6.getPropertyType().equals("array") ? ArrayInspector.canInspect(var6) : false;
            } else {
               return true;
            }
         }
      }
   }

   public void setValueAt(Object var1, int var2, int var3) {
      if (var1 != null) {
         if (var1 instanceof String) {
            String var4 = (String)var1;
            XMLProperty var5 = (XMLProperty)this.control.getPropertyContent().get(var2);
            String var6 = var5.getPropertyType();
            if (var6.equals("string")) {
               this.control.setValue(var5.getPropertyName(), var4);
            } else if (var6.equals("int")) {
               try {
                  int var7 = Integer.parseInt(var4);
                  this.control.setValue(var5.getPropertyName(), var7);
               } catch (NumberFormatException var10) {
               }
            } else if (var6.equals("double")) {
               try {
                  double var11 = Double.parseDouble(var4);
                  this.control.setValue(var5.getPropertyName(), var11);
               } catch (NumberFormatException var9) {
               }
            } else if (var6.equals("boolean")) {
               boolean var12 = var4.toLowerCase().startsWith("t");
               this.control.setValue(var5.getPropertyName(), var12);
            }

            this.fireTableCellUpdated(var2, var3);
         }

      }
   }
}
