package org.opensourcephysics.display;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class SortDecorator implements TableModel, TableModelListener {
   private TableModel realModel;
   private int[] indexes;

   public SortDecorator(TableModel var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("null models are not allowed");
      } else {
         this.realModel = var1;
         this.realModel.addTableModelListener(this);
         this.allocate();
      }
   }

   public Object getValueAt(int var1, int var2) {
      if (this.indexes.length <= var1) {
         this.allocate();
      }

      return this.realModel.getValueAt(this.indexes[var1], var2);
   }

   public void setValueAt(Object var1, int var2, int var3) {
      if (this.indexes.length <= var2) {
         this.allocate();
      }

      this.realModel.setValueAt(var1, this.indexes[var2], var3);
   }

   public void tableChanged(TableModelEvent var1) {
      this.allocate();
   }

   public void sort(int var1) {
      int var2 = this.getRowCount();
      if (this.indexes.length <= var2) {
         this.allocate();
      }

      for(int var3 = 0; var3 < var2; ++var3) {
         for(int var4 = var3 + 1; var4 < var2; ++var4) {
            if (this.compare(this.indexes[var3], this.indexes[var4], var1) < 0) {
               this.swap(var3, var4);
            }
         }
      }

   }

   public void swap(int var1, int var2) {
      int var3 = this.indexes[var1];
      this.indexes[var1] = this.indexes[var2];
      this.indexes[var2] = var3;
   }

   public int compare(int var1, int var2, int var3) {
      Object var4 = this.realModel.getValueAt(var1, var3);
      Object var5 = this.realModel.getValueAt(var2, var3);
      int var6;
      if (var4 instanceof Integer) {
         var6 = (Integer)var4;
         int var7 = (Integer)var5;
         return var7 < var6 ? -1 : (var7 > var6 ? 1 : 0);
      } else if (var4 instanceof Double) {
         double var10 = (Double)var4;
         double var8 = (Double)var5;
         return var8 < var10 ? -1 : (var8 > var10 ? 1 : 0);
      } else {
         var6 = var5.toString().compareTo(var4.toString());
         return var6 < 0 ? -1 : (var6 > 0 ? 1 : 0);
      }
   }

   private void allocate() {
      this.indexes = new int[this.getRowCount()];

      for(int var1 = 0; var1 < this.indexes.length; this.indexes[var1] = var1++) {
      }

   }

   public int getRowCount() {
      return this.realModel.getRowCount();
   }

   public int getColumnCount() {
      return this.realModel.getColumnCount();
   }

   public String getColumnName(int var1) {
      return this.realModel.getColumnName(var1);
   }

   public Class getColumnClass(int var1) {
      return this.realModel.getColumnClass(var1);
   }

   public boolean isCellEditable(int var1, int var2) {
      return this.realModel.isCellEditable(var1, var2);
   }

   public void addTableModelListener(TableModelListener var1) {
      this.realModel.addTableModelListener(var1);
   }

   public void removeTableModelListener(TableModelListener var1) {
      this.realModel.removeTableModelListener(var1);
   }
}
