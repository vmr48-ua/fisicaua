package org.opensourcephysics.display;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class DataTable extends JTable implements ActionListener {
   static final Color PANEL_BACKGROUND = UIManager.getColor("Panel.background");
   static final Color LIGHT_BLUE = new Color(204, 204, 255);
   final SortDecorator decorator;
   Map renderersByColumnName;
   DataTableModel dataTableModel;
   DataTable.RowNumberRenderer rowNumberRenderer;
   int refreshDelay;
   Timer refreshTimer;
   // $FF: synthetic field
   static Class class$java$lang$Double;
   // $FF: synthetic field
   static Class class$java$lang$Integer;

   public DataTable() {
      this(new DataTable.DefaultDataTableModel());
   }

   public DataTable(DataTableModel var1) {
      this.renderersByColumnName = new HashMap();
      this.refreshDelay = 0;
      this.refreshTimer = new Timer(this.refreshDelay, this);
      this.refreshTimer.setRepeats(false);
      this.refreshTimer.setCoalesce(true);
      this.setModel(var1);
      this.setColumnSelectionAllowed(true);
      this.setGridColor(Color.blue);
      this.setSelectionBackground(LIGHT_BLUE);
      JTableHeader var2 = this.getTableHeader();
      var2.setForeground(Color.blue);
      this.setSelectionForeground(Color.red);
      this.setColumnModel(new DataTable.DataTableColumnModel());
      this.setSelectionMode(1);
      this.setColumnSelectionAllowed(true);
      this.decorator = new SortDecorator(this.getModel());
      this.setModel(this.decorator);
      JTableHeader var3 = this.getTableHeader();
      var3.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent var1) {
            TableColumnModel var2 = DataTable.this.getColumnModel();
            int var3 = var2.getColumnIndexAtX(var1.getX());
            int var4 = DataTable.this.convertColumnIndexToModel(var3);
            DataTable.this.decorator.sort(var4);
         }
      });
   }

   public void setMaximumFractionDigits(String var1, int var2) {
      this.renderersByColumnName.put(var1, new DataTable.PrecisionRenderer(var2));
   }

   public void sort(int var1) {
      this.decorator.sort(var1);
   }

   public void setMaximumFractionDigits(int var1) {
      this.setDefaultRenderer(class$java$lang$Double == null ? (class$java$lang$Double = class$("java.lang.Double")) : class$java$lang$Double, new DataTable.PrecisionRenderer(var1));
   }

   public void setRowNumberVisible(boolean var1) {
      if (this.dataTableModel.isRowNumberVisible() != var1) {
         if (var1 && this.rowNumberRenderer == null) {
            this.rowNumberRenderer = new DataTable.RowNumberRenderer(this);
         }

         this.dataTableModel.setRowNumberVisible(var1);
      }

   }

   public void setModel(DataTableModel var1) {
      super.setModel(var1);
      this.dataTableModel = var1;
   }

   public void setStride(TableModel var1, int var2) {
      this.dataTableModel.setStride(var1, var2);
   }

   public void setColumnVisible(TableModel var1, int var2, boolean var3) {
      this.dataTableModel.setColumnVisible(var1, var2, var3);
   }

   public boolean isRowNumberVisible() {
      return this.dataTableModel.isRowNumberVisible();
   }

   public TableCellRenderer getCellRenderer(int var1, int var2) {
      int var3 = this.convertColumnIndexToModel(var2);
      if (var3 == 0 && this.dataTableModel.isRowNumberVisible()) {
         return this.rowNumberRenderer;
      } else {
         try {
            TableColumn var4 = this.getColumnModel().getColumn(var2);
            TableCellRenderer var5 = var4.getCellRenderer();
            if (var5 != null) {
               return var5;
            }

            Iterator var6 = this.renderersByColumnName.keySet().iterator();

            while(var6.hasNext()) {
               String var7 = (String)var6.next();
               if (var4.getHeaderValue().equals(var7)) {
                  return (TableCellRenderer)this.renderersByColumnName.get(var7);
               }
            }
         } catch (Exception var8) {
         }

         return this.getDefaultRenderer(this.getColumnClass(var2));
      }
   }

   public void setRefreshDelay(int var1) {
      if (var1 > 0) {
         this.refreshTimer.setDelay(var1);
         this.refreshTimer.setInitialDelay(var1);
      } else if (var1 <= 0) {
         this.refreshTimer.stop();
      }

      this.refreshDelay = var1;
   }

   public void refreshTable() {
      if (this.refreshDelay > 0) {
         this.refreshTimer.start();
      } else {
         Runnable var1 = new Runnable() {
            public synchronized void run() {
               DataTable.this.tableChanged(new TableModelEvent(DataTable.this.dataTableModel, -1));
            }
         };
         if (SwingUtilities.isEventDispatchThread()) {
            var1.run();
         } else {
            SwingUtilities.invokeLater(var1);
         }
      }

   }

   public void actionPerformed(ActionEvent var1) {
      this.tableChanged(new TableModelEvent(this.dataTableModel, -1));
   }

   public void add(TableModel var1) {
      this.dataTableModel.add(var1);
   }

   public void remove(TableModel var1) {
      this.dataTableModel.remove(var1);
   }

   public void clear() {
      this.dataTableModel.clear();
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   private static class RowNumberRenderer extends JLabel implements TableCellRenderer {
      JTable table;

      public RowNumberRenderer(JTable var1) {
         this.table = var1;
         this.setHorizontalAlignment(4);
         this.setOpaque(true);
         this.setForeground(Color.black);
         this.setBackground(DataTable.PANEL_BACKGROUND);
      }

      public Component getTableCellRendererComponent(JTable var1, Object var2, boolean var3, boolean var4, int var5, int var6) {
         if (var1.isRowSelected(var5)) {
            int[] var7 = var1.getSelectedColumns();
            if (var7.length == 1 && var1.convertColumnIndexToModel(var7[0]) == 0) {
               this.setBackground(DataTable.PANEL_BACKGROUND);
            } else {
               this.setBackground(Color.gray);
            }
         } else {
            this.setBackground(DataTable.PANEL_BACKGROUND);
         }

         this.setText(var2.toString());
         return this;
      }
   }

   private static class PrecisionRenderer extends DefaultTableCellRenderer {
      NumberFormat numberFormat = NumberFormat.getInstance();

      public PrecisionRenderer(int var1) {
         this.numberFormat.setMaximumFractionDigits(var1);
         this.setHorizontalAlignment(4);
      }

      public void setValue(Object var1) {
         this.setText(var1 == null ? "" : this.numberFormat.format(var1));
      }

      public void setPrecision(int var1) {
         this.numberFormat.setMaximumFractionDigits(var1);
      }
   }

   private class DataTableColumnModel extends DefaultTableColumnModel {
      private DataTableColumnModel() {
      }

      public TableColumn getColumn(int var1) {
         TableColumn var2;
         try {
            var2 = super.getColumn(var1);
         } catch (Exception var4) {
            return new TableColumn();
         }

         String var3 = (String)var2.getHeaderValue();
         if (var3 == null) {
            return var2;
         } else {
            if (var3.equals("row")) {
               var2.setMaxWidth(40);
               var2.setMinWidth(40);
               var2.setResizable(false);
            }

            return var2;
         }
      }

      // $FF: synthetic method
      DataTableColumnModel(Object var2) {
         this();
      }
   }

   private static class ModelFilterResult {
      DataTable.DataTableElement tableElement;
      int column;

      public ModelFilterResult(DataTable.DataTableElement var1, int var2) {
         this.tableElement = var1;
         this.column = var2;
      }

      public static DataTable.ModelFilterResult find(boolean var0, ArrayList var1, int var2) {
         if (var0) {
            --var2;
         }

         int var3 = 0;

         for(int var4 = 0; var4 < var1.size(); ++var4) {
            DataTable.DataTableElement var5 = (DataTable.DataTableElement)var1.get(var4);
            int var6 = var5.getColumnCount();
            var3 += var6;
            if (var3 > var2) {
               int var7 = var6 + var2 - var3;
               boolean[] var8 = var5.getColumnVisibilities();

               for(int var9 = 0; var9 < var2; ++var9) {
                  if (!var8[var9]) {
                     ++var7;
                  }
               }

               return new DataTable.ModelFilterResult(var5, var7);
            }
         }

         return null;
      }
   }

   private static class DefaultDataTableModel implements DataTableModel {
      ArrayList dataTableElements;
      boolean rowNumberVisible;

      private DefaultDataTableModel() {
         this.dataTableElements = new ArrayList();
         this.rowNumberVisible = false;
      }

      public void setColumnVisible(TableModel var1, int var2, boolean var3) {
         DataTable.DataTableElement var4 = this.findElementContaining(var1);
         var4.setColumnVisible(var2, var3);
      }

      public void setStride(TableModel var1, int var2) {
         DataTable.DataTableElement var3 = this.findElementContaining(var1);
         var3.setStride(var2);
      }

      public void setRowNumberVisible(boolean var1) {
         this.rowNumberVisible = var1;
      }

      public void setValueAt(Object var1, int var2, int var3) {
      }

      public boolean isRowNumberVisible() {
         return this.rowNumberVisible;
      }

      public String getColumnName(int var1) {
         if (this.dataTableElements.size() == 0 && !this.rowNumberVisible) {
            return null;
         } else if (this.rowNumberVisible && var1 == 0) {
            return "row";
         } else {
            DataTable.ModelFilterResult var2 = DataTable.ModelFilterResult.find(this.rowNumberVisible, this.dataTableElements, var1);
            DataTable.DataTableElement var3 = var2.tableElement;
            return var3.getColumnName(var2.column);
         }
      }

      public int getRowCount() {
         int var1 = 0;

         for(int var2 = 0; var2 < this.dataTableElements.size(); ++var2) {
            DataTable.DataTableElement var3 = (DataTable.DataTableElement)this.dataTableElements.get(var2);
            int var4 = var3.getStride();
            var1 = Math.max(var1, (var3.getRowCount() + var4 - 1) / var4);
         }

         return var1;
      }

      public int getColumnCount() {
         int var1 = 0;

         for(int var2 = 0; var2 < this.dataTableElements.size(); ++var2) {
            DataTable.DataTableElement var3 = (DataTable.DataTableElement)this.dataTableElements.get(var2);
            var1 += var3.getColumnCount();
         }

         if (this.rowNumberVisible) {
            ++var1;
         }

         return var1;
      }

      public Object getValueAt(int var1, int var2) {
         if (this.dataTableElements.size() == 0) {
            return null;
         } else if (this.rowNumberVisible && var2 == 0) {
            return new Integer(var1);
         } else {
            DataTable.ModelFilterResult var3 = DataTable.ModelFilterResult.find(this.rowNumberVisible, this.dataTableElements, var2);
            DataTable.DataTableElement var4 = var3.tableElement;
            int var5 = var4.getStride();
            var1 *= var5;
            return var1 >= var4.getRowCount() ? null : var4.getValueAt(var1, var3.column);
         }
      }

      public Class getColumnClass(int var1) {
         if (this.rowNumberVisible && var1 == 0) {
            return DataTable.class$java$lang$Integer == null ? (DataTable.class$java$lang$Integer = DataTable.class$("java.lang.Integer")) : DataTable.class$java$lang$Integer;
         } else {
            if (var1 == 0 && this.rowNumberVisible) {
               --var1;
            }

            DataTable.ModelFilterResult var2 = DataTable.ModelFilterResult.find(this.rowNumberVisible, this.dataTableElements, var1);
            DataTable.DataTableElement var3 = var2.tableElement;
            return var3.getColumnClass(var2.column);
         }
      }

      public boolean isCellEditable(int var1, int var2) {
         return false;
      }

      public void remove(TableModel var1) {
         DataTable.DataTableElement var2 = this.findElementContaining(var1);
         this.dataTableElements.remove(var2);
      }

      public void clear() {
         this.dataTableElements.clear();
      }

      public void add(TableModel var1) {
         this.dataTableElements.add(new DataTable.DataTableElement(var1));
      }

      public void addTableModelListener(TableModelListener var1) {
      }

      public void removeTableModelListener(TableModelListener var1) {
      }

      private DataTable.DataTableElement findElementContaining(TableModel var1) {
         for(int var2 = 0; var2 < this.dataTableElements.size(); ++var2) {
            DataTable.DataTableElement var3 = (DataTable.DataTableElement)this.dataTableElements.get(var2);
            if (var3.tableModel == var1) {
               return var3;
            }
         }

         return null;
      }

      // $FF: synthetic method
      DefaultDataTableModel(Object var1) {
         this();
      }
   }

   private static class DataTableElement {
      TableModel tableModel;
      boolean[] columnVisibilities;
      int stride = 1;

      public DataTableElement(TableModel var1) {
         this.tableModel = var1;
      }

      public void setStride(int var1) {
         this.stride = var1;
      }

      public void setColumnVisible(int var1, boolean var2) {
         this.ensureCapacity(var1 + 1);
         this.columnVisibilities[var1] = var2;
      }

      public int getStride() {
         return this.stride;
      }

      public boolean[] getColumnVisibilities() {
         return this.columnVisibilities;
      }

      public int getColumnCount() {
         int var1 = 0;
         int var2 = this.tableModel.getColumnCount();
         this.ensureCapacity(var2);

         for(int var3 = 0; var3 < var2; ++var3) {
            boolean var4 = this.columnVisibilities[var3];
            if (var4) {
               ++var1;
            }
         }

         return var1;
      }

      public Object getValueAt(int var1, int var2) {
         return this.tableModel.getValueAt(var1, var2);
      }

      public String getColumnName(int var1) {
         return this.tableModel.getColumnName(var1);
      }

      public Class getColumnClass(int var1) {
         return this.tableModel.getColumnClass(var1);
      }

      public int getRowCount() {
         return this.tableModel.getRowCount();
      }

      private void ensureCapacity(int var1) {
         if (this.columnVisibilities == null) {
            this.columnVisibilities = new boolean[var1 * 3 / 2 + 1];
            Arrays.fill(this.columnVisibilities, true);
         } else if (this.columnVisibilities.length < var1) {
            boolean[] var2 = this.columnVisibilities;
            this.columnVisibilities = new boolean[var1 * 3 / 2 + 1];
            System.arraycopy(var2, 0, this.columnVisibilities, 0, var2.length);
            Arrays.fill(this.columnVisibilities, var2.length, this.columnVisibilities.length, true);
         }

      }
   }
}
