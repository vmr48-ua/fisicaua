package org.opensourcephysics.frames;

import java.awt.Color;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.display.DataTable;
import org.opensourcephysics.display.DataTableFrame;
import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.display.DatasetManager;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.PlottingPanel;
import org.opensourcephysics.display.dialogs.LogAxesInspector;
import org.opensourcephysics.display.dialogs.ScaleInspector;

public class PlotFrame extends DrawingFrame {
   protected DatasetManager datasetManager = new DatasetManager();
   protected DataTable dataTable = new DataTable();
   protected DataTableFrame tableFrame;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$display$DatasetManager;

   public PlotFrame(String var1, String var2, String var3) {
      super(new PlottingPanel(var1, var2, (String)null));
      this.setTitle(var3);
      super.drawingPanel.addDrawable(this.datasetManager);
      this.datasetManager.setXPointsLinked(true);
      this.dataTable.add(this.datasetManager);
      this.setAnimated(true);
      this.setAutoclear(true);
      this.addMenuItems();
   }

   protected void addMenuItems() {
      JMenuBar var1 = this.getJMenuBar();
      if (var1 != null) {
         JMenu var2 = this.removeMenu("Help");
         JMenu var3 = this.getMenu("Views");
         if (var3 == null) {
            var3 = new JMenu("Views");
            var1.add(var3);
            var1.validate();
         } else {
            var3.addSeparator();
         }

         var1.add(var2);
         JMenuItem var4 = new JMenuItem("Scale");
         ActionListener var5 = new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               PlotFrame.this.scale();
            }
         };
         var4.addActionListener(var5);
         var3.add(var4);
         JMenuItem var6 = new JMenuItem("Log Axes");
         var5 = new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               PlotFrame.this.logAxes();
            }
         };
         var6.addActionListener(var5);
         var3.add(var6);
         var3.addSeparator();
         JMenuItem var7 = new JMenuItem("Data Table");
         var7.setAccelerator(KeyStroke.getKeyStroke(84, DrawingFrame.MENU_SHORTCUT_KEY_MASK));
         var5 = new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               PlotFrame.this.showDataTable(true);
            }
         };
         var7.addActionListener(var5);
         var3.add(var7);
         if (super.drawingPanel != null && super.drawingPanel.getPopupMenu() != null) {
            JMenuItem var8 = new JMenuItem("Data Table");
            var8.addActionListener(var5);
            super.drawingPanel.getPopupMenu().add(var8);
         }

      }
   }

   public void setLogScaleX(boolean var1) {
      if (super.drawingPanel instanceof PlottingPanel) {
         ((PlottingPanel)super.drawingPanel).setLogScaleX(var1);
      }

   }

   public void setLogScaleY(boolean var1) {
      if (super.drawingPanel instanceof PlottingPanel) {
         ((PlottingPanel)super.drawingPanel).setLogScaleY(var1);
      }

   }

   protected void scale() {
      ScaleInspector var1 = new ScaleInspector(super.drawingPanel);
      var1.setLocationRelativeTo(super.drawingPanel);
      var1.updateDisplay();
      var1.setVisible(true);
   }

   protected void logAxes() {
      if (super.drawingPanel instanceof PlottingPanel) {
         LogAxesInspector var1 = new LogAxesInspector((PlottingPanel)super.drawingPanel);
         var1.setLocationRelativeTo(super.drawingPanel);
         var1.updateDisplay();
         var1.setVisible(true);
      }
   }

   public void append(int var1, double var2, double var4) {
      this.datasetManager.append(var1, var2, var4);
      if (this.tableFrame != null && this.tableFrame.isShowing()) {
         this.dataTable.refreshTable();
      }

   }

   public void append(int var1, double var2, double var4, double var6, double var8) {
      this.datasetManager.append(var1, var2, var4, var6, var8);
      if (this.tableFrame != null && this.tableFrame.isShowing()) {
         this.dataTable.refreshTable();
      }

   }

   public void append(int var1, double[] var2, double[] var3) {
      this.datasetManager.append(var1, var2, var3);
      if (this.tableFrame != null && this.tableFrame.isShowing()) {
         this.dataTable.refreshTable();
      }

   }

   public void append(int var1, double[] var2, double[] var3, double[] var4, double[] var5) {
      this.datasetManager.append(var1, var2, var3, var4, var5);
      if (this.tableFrame != null && this.tableFrame.isShowing()) {
         this.dataTable.refreshTable();
      }

   }

   public void setConnected(boolean var1) {
      this.datasetManager.setConnected(var1);
   }

   public void setCustomMarker(int var1, Shape var2) {
      this.datasetManager.setCustomMarker(var1, var2);
   }

   public void setMarkerShape(int var1, int var2) {
      this.datasetManager.setMarkerShape(var1, var2);
   }

   public void setMarkerSize(int var1, int var2) {
      this.datasetManager.setMarkerSize(var1, var2);
   }

   public void setMarkerColor(int var1, Color var2) {
      this.datasetManager.setMarkerColor(var1, var2);
   }

   public void setLineColor(int var1, Color var2) {
      this.datasetManager.setLineColor(var1, var2);
   }

   public void setBackground(Color var1) {
      super.setBackground(var1);
      if (super.drawingPanel != null) {
         super.drawingPanel.setBackground(var1);
      }

   }

   public void setMarkerColor(int var1, Color var2, Color var3) {
      this.datasetManager.setMarkerColor(var1, var2, var3);
   }

   public void setConnected(int var1, boolean var2) {
      this.datasetManager.setConnected(var1, var2);
   }

   public void setXPointsLinked(boolean var1) {
      this.datasetManager.setXPointsLinked(var1);
   }

   public void setXYColumnNames(int var1, String var2, String var3, String var4) {
      this.datasetManager.setXYColumnNames(var1, var2, var3, var4);
   }

   public void setXYColumnNames(int var1, String var2, String var3) {
      this.datasetManager.setXYColumnNames(var1, var2, var3);
   }

   public void setMaximumFractionDigits(int var1) {
      this.dataTable.setMaximumFractionDigits(var1);
   }

   public void setMaximumFractionDigits(String var1, int var2) {
      this.dataTable.setMaximumFractionDigits(var1, var2);
   }

   public void setRowNumberVisible(boolean var1) {
      this.dataTable.setRowNumberVisible(var1);
   }

   public void clearDrawables() {
      super.drawingPanel.clear();
      super.drawingPanel.addDrawable(this.datasetManager);
   }

   public Dataset getDataset(int var1) {
      return this.datasetManager.getDataset(var1);
   }

   public DatasetManager getDatasetManager() {
      return this.datasetManager;
   }

   public synchronized ArrayList getDrawables() {
      ArrayList var1 = super.getDrawables();
      var1.remove(this.datasetManager);
      return var1;
   }

   public synchronized ArrayList getDrawables(Class var1) {
      ArrayList var2 = super.getDrawables(var1);
      var2.remove(this.datasetManager);
      return var2;
   }

   public void clearData() {
      this.datasetManager.clear();
      this.dataTable.refreshTable();
      if (super.drawingPanel != null) {
         super.drawingPanel.invalidateImage();
      }

   }

   public synchronized void showDataTable(boolean var1) {
      if (var1) {
         if (this.tableFrame == null || !this.tableFrame.isDisplayable()) {
            this.tableFrame = new DataTableFrame(this.getTitle() + " Data", this.dataTable);
            this.tableFrame.setDefaultCloseOperation(2);
         }

         this.dataTable.refreshTable();
         this.tableFrame.setVisible(true);
      } else {
         this.tableFrame.setVisible(false);
         this.tableFrame.dispose();
         this.tableFrame = null;
      }

   }

   public static XML.ObjectLoader getLoader() {
      return new PlotFrame.PlotFrameLoader();
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   protected static class PlotFrameLoader extends DrawingFrame.DrawingFrameLoader {
      public Object createObject(XMLControl var1) {
         PlotFrame var2 = new PlotFrame("x", "y", "Plot Frame");
         return var2;
      }

      public Object loadObject(XMLControl var1, Object var2) {
         super.loadObject(var1, var2);
         PlotFrame var3 = (PlotFrame)var2;
         ArrayList var4 = var3.getObjectOfClass(PlotFrame.class$org$opensourcephysics$display$DatasetManager == null ? (PlotFrame.class$org$opensourcephysics$display$DatasetManager = PlotFrame.class$("org.opensourcephysics.display.DatasetManager")) : PlotFrame.class$org$opensourcephysics$display$DatasetManager);
         if (var4.size() > 0) {
            var3.datasetManager = (DatasetManager)var4.get(0);
            var3.dataTable.clear();
            var3.dataTable.add(var3.datasetManager);
         }

         return var2;
      }
   }
}
