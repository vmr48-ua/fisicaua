package org.opensourcephysics.tools;

import org.opensourcephysics.display.DataTable;
import org.opensourcephysics.display.HighlightableDataset;

public class DatasetDataTable extends DataTable {
   final HighlightableDataset dataset;
   HighlightableDataset data = new HighlightableDataset();
   HighlightableDataset workingData = new HighlightableDataset();

   public DatasetDataTable(HighlightableDataset var1) {
      this.dataset = var1;
      this.add(var1);
      this.setRowNumberVisible(true);
      this.setSelectionMode(2);
   }

   protected HighlightableDataset getData() {
      this.data.clear();
      if (this.isXYColumnsReversed()) {
         this.data.append(this.dataset.getYPoints(), this.dataset.getXPoints());
         this.data.setXYColumnNames(this.dataset.getColumnName(1), this.dataset.getColumnName(0));
      } else {
         this.data.append(this.dataset.getXPoints(), this.dataset.getYPoints());
         this.data.setXYColumnNames(this.dataset.getColumnName(0), this.dataset.getColumnName(1));
      }

      this.data.setMarkerShape(this.dataset.getMarkerShape());
      this.data.setMarkerSize(this.dataset.getMarkerSize());
      this.data.setConnected(this.dataset.isConnected());
      this.data.setLineColor(this.dataset.getLineColor());
      this.data.setName(this.dataset.getName());
      this.data.setMarkerColor(this.dataset.getFillColor(), this.dataset.getEdgeColor());

      for(int var1 = 0; var1 < this.dataset.getRowCount(); ++var1) {
         this.data.setHighlighted(var1, this.dataset.isHighlighted(var1));
      }

      return this.data;
   }

   protected HighlightableDataset getWorkingData() {
      double[] var3 = this.dataset.getXPoints();
      double[] var4 = this.dataset.getYPoints();
      this.dataset.clearHighlights();
      this.data.clearHighlights();
      double[] var1;
      double[] var2;
      if (this.getSelectedRowCount() == 0) {
         var1 = var3;
         var2 = var4;
      } else {
         int[] var5 = this.getSelectedRows();
         var1 = new double[var5.length];
         var2 = new double[var5.length];

         for(int var6 = 0; var6 < var5.length; ++var6) {
            var1[var6] = var3[var5[var6]];
            var2[var6] = var4[var5[var6]];
            this.dataset.setHighlighted(var5[var6], true);
            this.data.setHighlighted(var5[var6], true);
         }
      }

      this.workingData.clear();
      if (this.isXYColumnsReversed()) {
         this.workingData.append(var2, var1);
         this.workingData.setXYColumnNames(this.dataset.getColumnName(1), this.dataset.getColumnName(0));
      } else {
         this.workingData.append(var1, var2);
         this.workingData.setXYColumnNames(this.dataset.getColumnName(0), this.dataset.getColumnName(1));
      }

      this.workingData.setMarkerShape(this.dataset.getMarkerShape());
      this.workingData.setMarkerSize(this.dataset.getMarkerSize());
      this.workingData.setConnected(this.dataset.isConnected());
      this.workingData.setLineColor(this.dataset.getLineColor());
      this.workingData.setName(this.dataset.getName());
      this.workingData.setMarkerColor(this.dataset.getFillColor(), this.dataset.getEdgeColor());
      return this.workingData;
   }

   protected boolean isXYColumnsReversed() {
      int var1 = this.isRowNumberVisible() ? 1 : 0;
      return this.convertColumnIndexToView(var1) > this.convertColumnIndexToView(var1 + 1);
   }

   public void clearSelection() {
      if (this.dataset != null) {
         this.dataset.clearHighlights();
         this.data.clearHighlights();
      }

      super.clearSelection();
   }
}
