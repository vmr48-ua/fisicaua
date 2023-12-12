package org.opensourcephysics.tools;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class ArrayTable extends JTable {
   ArrayTableModel tableModel;
   ArrayTable.ArrayIndexRenderer indexRenderer;

   public ArrayTable(int[] var1) {
      this.tableModel = new ArrayTableModel(var1);
      this.init();
   }

   public ArrayTable(int[][] var1) {
      this.tableModel = new ArrayTableModel(var1);
      this.init();
   }

   public ArrayTable(double[] var1) {
      this.tableModel = new ArrayTableModel(var1);
      this.init();
   }

   public ArrayTable(double[][] var1) {
      this.tableModel = new ArrayTableModel(var1);
      this.init();
   }

   public ArrayTable(String[] var1) {
      this.tableModel = new ArrayTableModel(var1);
      this.init();
   }

   public ArrayTable(String[][] var1) {
      this.tableModel = new ArrayTableModel(var1);
      this.init();
   }

   public ArrayTable(boolean[] var1) {
      this.tableModel = new ArrayTableModel(var1);
      this.init();
   }

   public ArrayTable(boolean[][] var1) {
      this.tableModel = new ArrayTableModel(var1);
      this.init();
   }

   protected void init() {
      this.setModel(this.tableModel);
      this.tableModel.addTableModelListener(new TableModelListener() {
         public void tableChanged(TableModelEvent var1) {
            ArrayTable.this.firePropertyChange("cell", (Object)null, var1);
         }
      });
      this.setColumnSelectionAllowed(true);
      this.indexRenderer = new ArrayTable.ArrayIndexRenderer();
      this.getTableHeader().setReorderingAllowed(false);
      this.getTableHeader().setDefaultRenderer(this.indexRenderer);
      this.setAutoResizeMode(0);
      this.setGridColor(Color.BLACK);
      byte var1 = 24;
      String var2;
      TableColumn var3;
      if (this.getColumnCount() > 0) {
         var2 = this.getColumnName(0);
         var3 = this.getColumn(var2);
         var3.setMinWidth(var1);
         var3.setMaxWidth(2 * var1);
         var3.setWidth(var1);
      }

      var1 = 60;
      int var4 = 1;

      for(int var5 = this.getColumnCount(); var4 < var5; ++var4) {
         var2 = this.getColumnName(var4);
         var3 = this.getColumn(var2);
         var3.setMinWidth(var1);
         var3.setMaxWidth(3 * var1);
         var3.setWidth(var1);
      }

      InputMap var10 = this.getInputMap(1);
      KeyStroke var11 = KeyStroke.getKeyStroke(9, 0);
      final Action var6 = this.getActionMap().get(var10.get(var11));
      AbstractAction var7 = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            var6.actionPerformed(var1);
            JTable var2 = (JTable)var1.getSource();
            int var3 = var2.getRowCount();
            int var4 = var2.getSelectedRow();
            int var5 = var2.getSelectedColumn();

            while(!var2.isCellEditable(var4, var5)) {
               if (var5 == 0) {
                  var5 = 1;
               } else {
                  ++var4;
               }

               if (var4 == var3) {
                  var4 = 0;
               }

               if (var4 == var2.getSelectedRow() && var5 == var2.getSelectedColumn()) {
                  break;
               }
            }

            var2.changeSelection(var4, var5, false, false);
         }
      };
      this.getActionMap().put(var10.get(var11), var7);
      AbstractAction var8 = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            JTable var2 = (JTable)var1.getSource();
            int var3 = var2.getSelectedRow();
            int var4 = var2.getSelectedColumn();
            var2.editCellAt(var3, var4, var1);
            Component var5 = var2.getEditorComponent();
            if (var5 instanceof JTextField) {
               JTextField var6 = (JTextField)var5;
               var6.requestFocus();
               var6.selectAll();
            }

         }
      };
      KeyStroke var9 = KeyStroke.getKeyStroke(10, 0);
      this.getActionMap().put(var10.get(var9), var8);
   }

   public void refreshTable() {
      Runnable var1 = new Runnable() {
         public synchronized void run() {
            ArrayTable.this.tableChanged(new TableModelEvent(ArrayTable.this.tableModel, -1));
         }
      };
      if (SwingUtilities.isEventDispatchThread()) {
         var1.run();
      } else {
         SwingUtilities.invokeLater(var1);
      }

   }

   public void setEditable(boolean var1) {
      this.tableModel.setEditable(var1);
   }

   public TableCellRenderer getCellRenderer(int var1, int var2) {
      int var3 = this.convertColumnIndexToModel(var2);
      return (TableCellRenderer)(var3 == 0 ? this.indexRenderer : this.getDefaultRenderer(this.getColumnClass(var2)));
   }

   static class ArrayIndexRenderer extends JLabel implements TableCellRenderer {
      public ArrayIndexRenderer() {
         this.setBorder(BorderFactory.createRaisedBevelBorder());
         this.setOpaque(true);
         this.setForeground(Color.BLACK);
         this.setBackground(Color.LIGHT_GRAY);
      }

      public Component getTableCellRendererComponent(JTable var1, Object var2, boolean var3, boolean var4, int var5, int var6) {
         if (var6 == 0) {
            this.setHorizontalAlignment(4);
         } else {
            this.setHorizontalAlignment(0);
         }

         this.setText(var2.toString());
         this.setPreferredSize(new Dimension(20, 18));
         return this;
      }
   }
}
