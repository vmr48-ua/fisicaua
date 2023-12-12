package org.opensourcephysics.tools;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.opensourcephysics.display.HighlightableDataset;

public class DatasetStatisticsTable extends JTable {
   DatasetDataTable dataTable;
   DatasetStatisticsTable.StatisticsTableModel tableModel;
   DatasetStatisticsTable.LabelRenderer labelRenderer;
   protected Object[][] statsData;
   // $FF: synthetic field
   static Class class$java$lang$Double;
   // $FF: synthetic field
   static Class class$java$lang$String;

   public DatasetStatisticsTable(DatasetDataTable var1) {
      this.dataTable = var1;
      this.tableModel = new DatasetStatisticsTable.StatisticsTableModel();
      this.init();
   }

   protected void init() {
      this.dataTable.getColumnModel().addColumnModelListener(new TableColumnModelListener() {
         public void columnAdded(TableColumnModelEvent var1) {
         }

         public void columnRemoved(TableColumnModelEvent var1) {
         }

         public void columnSelectionChanged(ListSelectionEvent var1) {
         }

         public void columnMarginChanged(ChangeEvent var1) {
            DatasetStatisticsTable.this.refreshTable();
         }

         public void columnMoved(TableColumnModelEvent var1) {
            DatasetStatisticsTable.this.refreshTable();
         }
      });
      HighlightableDataset var1 = this.dataTable.getWorkingData();
      double[] var2 = this.getStatistics(var1.getXPoints());
      double[] var3 = this.getStatistics(var1.getValidYPoints());
      if (this.statsData == null) {
         this.statsData = new Object[var2.length][0];
      }

      for(int var4 = 0; var4 < var2.length; ++var4) {
         String var5 = ToolsRes.getString("Table.Entry.Count");
         if (var4 == 5) {
            this.statsData[var4] = new Object[]{var5, new Integer((int)var2[var4]), new Integer((int)var3[var4])};
         } else {
            switch(var4) {
            case 0:
               var5 = ToolsRes.getString("Table.Entry.Max");
               break;
            case 1:
               var5 = ToolsRes.getString("Table.Entry.Min");
               break;
            case 2:
               var5 = ToolsRes.getString("Table.Entry.Mean");
               break;
            case 3:
               var5 = ToolsRes.getString("Table.Entry.StandardDev");
               break;
            case 4:
               var5 = ToolsRes.getString("Table.Entry.StandardError");
            }

            this.statsData[var4] = new Object[]{var5, new Double(var2[var4]), new Double(var3[var4])};
         }
      }

      this.setModel(this.tableModel);
      this.setGridColor(Color.blue);
      this.setEnabled(false);
      this.setTableHeader((JTableHeader)null);
      this.labelRenderer = new DatasetStatisticsTable.LabelRenderer();
      this.setAutoResizeMode(0);
      this.setDefaultRenderer(class$java$lang$Double == null ? (class$java$lang$Double = class$("java.lang.Double")) : class$java$lang$Double, new DatasetStatisticsTable.ScientificRenderer(3));
      ListSelectionModel var6 = this.dataTable.getSelectionModel();
      var6.addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent var1) {
            HighlightableDataset var2 = DatasetStatisticsTable.this.dataTable.getWorkingData();
            double[] var3 = DatasetStatisticsTable.this.getStatistics(var2.getXPoints());
            double[] var4 = DatasetStatisticsTable.this.getStatistics(var2.getValidYPoints());

            int var5;
            for(var5 = 0; var5 < var3.length - 1; ++var5) {
               DatasetStatisticsTable.this.statsData[var5][1] = new Double(var3[var5]);
               DatasetStatisticsTable.this.statsData[var5][2] = new Double(var4[var5]);
            }

            DatasetStatisticsTable.this.statsData[var5][1] = new Integer((int)var3[var5]);
            DatasetStatisticsTable.this.statsData[var5][2] = new Integer((int)var4[var5]);
            DatasetStatisticsTable.this.refreshTable();
         }
      });
      this.refreshCellWidths();
   }

   public void getXStatistics() {
   }

   private double[] getStatistics(double[] var1) {
      double var2 = -1.7976931348623157E308D;
      double var4 = Double.MAX_VALUE;
      double var6 = 0.0D;
      double var8 = 0.0D;
      int var10 = 0;

      for(int var11 = 0; var11 < var1.length; ++var11) {
         if (!Double.isNaN(var1[var11])) {
            ++var10;
            var2 = Math.max(var2, var1[var11]);
            var4 = Math.min(var4, var1[var11]);
            var6 += var1[var11];
            var8 += var1[var11] * var1[var11];
         }
      }

      double var15 = var6 / (double)var10;
      double var13 = var10 < 2 ? Double.NaN : Math.sqrt((var8 - (double)var10 * var15 * var15) / (double)(var10 - 1));
      if (var2 == -1.7976931348623157E308D) {
         var2 = Double.NaN;
      }

      if (var4 == Double.MAX_VALUE) {
         var4 = Double.NaN;
      }

      return new double[]{var2, var4, var15, var13, var13 / Math.sqrt((double)var10), (double)var10};
   }

   public void refreshTable() {
      Runnable var1 = new Runnable() {
         public synchronized void run() {
            DatasetStatisticsTable.this.tableChanged(new TableModelEvent(DatasetStatisticsTable.this.tableModel, -1));
            DatasetStatisticsTable.this.refreshCellWidths();
         }
      };
      if (SwingUtilities.isEventDispatchThread()) {
         var1.run();
      } else {
         SwingUtilities.invokeLater(var1);
      }

   }

   public void refreshCellWidths() {
      for(int var1 = 0; var1 < this.getColumnCount(); ++var1) {
         String var2 = this.getColumnName(var1);
         TableColumn var3 = this.getColumn(var2);
         var2 = this.dataTable.getColumnName(var1);
         TableColumn var4 = this.dataTable.getColumn(var2);
         var3.setMaxWidth(var4.getWidth());
         var3.setMinWidth(var4.getWidth());
         var3.setWidth(var4.getWidth());
      }

   }

   public TableCellRenderer getCellRenderer(int var1, int var2) {
      int var3 = this.dataTable.convertColumnIndexToModel(var2);
      return (TableCellRenderer)(var3 == 0 ? this.labelRenderer : this.getDefaultRenderer(this.tableModel.getValueAt(var1, var2).getClass()));
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   class ScientificRenderer extends JLabel implements TableCellRenderer {
      NumberFormat format = NumberFormat.getInstance();

      public ScientificRenderer(int var2) {
         var2 = Math.min(var2, 6);
         if (this.format instanceof DecimalFormat) {
            String var3 = "0.0";

            for(int var4 = 0; var4 < var2 - 1; ++var4) {
               var3 = var3 + "0";
            }

            var3 = var3 + "E0";
            ((DecimalFormat)this.format).applyPattern(var3);
         }

      }

      public Component getTableCellRendererComponent(JTable var1, Object var2, boolean var3, boolean var4, int var5, int var6) {
         this.setFont(DatasetStatisticsTable.this.getDefaultRenderer(DatasetStatisticsTable.class$java$lang$String == null ? (DatasetStatisticsTable.class$java$lang$String = DatasetStatisticsTable.class$("java.lang.String")) : DatasetStatisticsTable.class$java$lang$String).getTableCellRendererComponent(DatasetStatisticsTable.this, "a", false, false, 0, 0).getFont());
         this.setText(this.format.format(var2));
         this.setHorizontalAlignment(11);
         return this;
      }
   }

   static class LabelRenderer extends JLabel implements TableCellRenderer {
      public LabelRenderer() {
         this.setHorizontalAlignment(4);
         this.setOpaque(true);
         this.setForeground(Color.black);
         this.setBackground(UIManager.getColor("Panel.background"));
      }

      public Component getTableCellRendererComponent(JTable var1, Object var2, boolean var3, boolean var4, int var5, int var6) {
         this.setText(var2.toString());
         return this;
      }
   }

   class StatisticsTableModel extends AbstractTableModel {
      public String getColumnName(int var1) {
         return DatasetStatisticsTable.this.dataTable.getColumnName(var1);
      }

      public int getRowCount() {
         return DatasetStatisticsTable.this.statsData.length;
      }

      public int getColumnCount() {
         return DatasetStatisticsTable.this.dataTable.getColumnCount();
      }

      public Object getValueAt(int var1, int var2) {
         int var3 = DatasetStatisticsTable.this.dataTable.convertColumnIndexToModel(var2);
         return DatasetStatisticsTable.this.statsData[var1][var3];
      }

      public boolean isCellEditable(int var1, int var2) {
         return false;
      }

      public Class getColumnClass(int var1) {
         return this.getValueAt(0, var1).getClass();
      }
   }
}
