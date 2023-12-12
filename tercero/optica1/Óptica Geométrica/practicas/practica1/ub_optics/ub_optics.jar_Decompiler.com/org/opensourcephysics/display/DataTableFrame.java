package org.opensourcephysics.display;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

public class DataTableFrame extends OSPFrame {
   protected JMenuBar menuBar;
   protected JMenu fileMenu;
   protected JMenu editMenu;
   protected JMenuItem saveAsItem;
   protected DataTable table;

   public DataTableFrame(DataTable var1) {
      this("Data Table", var1);
   }

   public DataTableFrame(String var1, DataTable var2) {
      super(var1);
      this.table = var2;
      JScrollPane var3 = new JScrollPane(this.table);
      Container var4 = this.getContentPane();
      var4.add(var3, "Center");
      this.pack();
      if (!OSPFrame.appletMode) {
         this.createMenuBar();
      }

   }

   private void createMenuBar() {
      this.menuBar = new JMenuBar();
      this.setJMenuBar(this.menuBar);
      this.fileMenu = new JMenu("File");
      this.editMenu = new JMenu("Edit");
      this.menuBar.add(this.fileMenu);
      this.menuBar.add(this.editMenu);
      JMenuItem var1 = new JMenuItem("Save As...");
      JMenuItem var2 = new JMenuItem("Copy");
      JMenuItem var3 = new JMenuItem("Select All");
      this.fileMenu.add(var1);
      this.editMenu.add(var2);
      this.editMenu.add(var3);
      var2.setAccelerator(KeyStroke.getKeyStroke(67, DrawingFrame.MENU_SHORTCUT_KEY_MASK));
      var2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DataTableFrame.this.copy();
         }
      });
      var3.setAccelerator(KeyStroke.getKeyStroke(65, DrawingFrame.MENU_SHORTCUT_KEY_MASK));
      var3.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DataTableFrame.this.table.selectAll();
         }
      });
      var1.setAccelerator(KeyStroke.getKeyStroke(83, DrawingFrame.MENU_SHORTCUT_KEY_MASK));
      var1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DataTableFrame.this.saveAs();
         }
      });
      this.validate();
   }

   public void copy() {
      Clipboard var1 = Toolkit.getDefaultToolkit().getSystemClipboard();
      int[] var2 = this.table.getSelectedRows();
      int[] var3 = this.table.getSelectedColumns();
      StringBuffer var4 = this.getSelectedData(var2, var3);
      StringSelection var5 = new StringSelection(var4.toString());
      var1.setContents(var5, var5);
   }

   public void refreshTable() {
      this.table.refreshTable();
   }

   public StringBuffer getSelectedData(int[] var1, int[] var2) {
      StringBuffer var3 = new StringBuffer();

      for(int var4 = 0; var4 < var1.length; ++var4) {
         for(int var5 = 0; var5 < var2.length; ++var5) {
            int var7 = this.table.convertColumnIndexToModel(var2[var5]);
            if (!this.table.isRowNumberVisible() || var7 != 0) {
               Object var8 = this.table.getValueAt(var4, var2[var5]);
               if (var8 != null) {
                  var3.append(var8);
               }

               var3.append("\t");
            }
         }

         var3.append("\n");
      }

      return var3;
   }

   public void sort(int var1) {
      this.table.sort(var1);
   }

   public void saveAs() {
      File var1 = GUIUtils.showSaveDialog(this);
      if (var1 != null) {
         byte var2 = 0;
         int var3 = this.table.getRowCount() - 1;
         int var4 = this.table.getColumnCount() - 1;
         int var5 = 0;
         if (this.table.isRowNumberVisible()) {
            ++var5;
         }

         int[] var6 = new int[var3 + 1];
         int[] var7 = new int[var4 + 1];

         int var8;
         for(var8 = var2; var8 <= var3; var6[var8] = var8++) {
         }

         for(var8 = var5; var8 <= var4; var7[var8] = var8++) {
         }

         try {
            FileWriter var11 = new FileWriter(var1);
            PrintWriter var9 = new PrintWriter(var11);
            var9.print(this.getSelectedData(var6, var7));
            var9.close();
         } catch (IOException var10) {
            JOptionPane.showMessageDialog(this, "An error occurred while saving your file. Please try again.", "Error", 0);
         }

      }
   }
}
