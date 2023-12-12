package org.opensourcephysics.display;

import javax.swing.table.TableModel;

public interface DataTableModel extends TableModel {
   void setColumnVisible(TableModel var1, int var2, boolean var3);

   void remove(TableModel var1);

   void clear();

   void add(TableModel var1);

   void setStride(TableModel var1, int var2);

   void setRowNumberVisible(boolean var1);

   boolean isRowNumberVisible();
}
