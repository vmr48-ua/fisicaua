package org.opensourcephysics.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.table.AbstractTableModel;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLLoader;

public class DatasetManager extends AbstractTableModel implements Measurable {
   ArrayList datasets;
   boolean connected;
   boolean sorted;
   int markerShape;
   int stride;
   boolean linked;
   static Color[] lineColors;
   static Color[] markerColors;
   String xColumnName;
   String yColumnName;
   // $FF: synthetic field
   static Class class$java$lang$Double;

   public DatasetManager() {
      this(false, false, false, 2);
   }

   public DatasetManager(boolean var1) {
      this(false, false, var1, 2);
   }

   public DatasetManager(boolean var1, boolean var2) {
      this(var1, var2, false, 2);
   }

   public DatasetManager(boolean var1, boolean var2, boolean var3, int var4) {
      this.datasets = new ArrayList();
      this.stride = 1;
      this.linked = false;
      this.xColumnName = "x";
      this.yColumnName = "y";
      this.connected = var1;
      this.sorted = var2;
      this.markerShape = var4;
      this.linked = var3;
   }

   public void setXPointsLinked(boolean var1) {
      this.linked = var1;

      for(int var2 = 1; var2 < this.datasets.size(); ++var2) {
         Dataset var3 = (Dataset)this.datasets.get(var2);
         var3.setXColumnVisible(!this.linked);
      }

   }

   public void setSorted(int var1, boolean var2) {
      this.checkDatasetIndex(var1);
      Dataset var3 = (Dataset)this.datasets.get(var1);
      var3.setSorted(var2);
   }

   public void setSorted(boolean var1) {
      this.sorted = var1;

      for(int var2 = 0; var2 < this.datasets.size(); ++var2) {
         ((Dataset)((Dataset)this.datasets.get(var2))).setSorted(var1);
      }

   }

   public void setConnected(int var1, boolean var2) {
      this.checkDatasetIndex(var1);
      Dataset var3 = (Dataset)this.datasets.get(var1);
      var3.setConnected(var2);
   }

   public void setConnected(boolean var1) {
      this.connected = var1;

      for(int var2 = 0; var2 < this.datasets.size(); ++var2) {
         ((Dataset)((Dataset)this.datasets.get(var2))).setConnected(var1);
      }

   }

   public void setStride(int var1, int var2) {
      this.checkDatasetIndex(var1);
      Dataset var3 = (Dataset)this.datasets.get(var1);
      var3.setStride(var2);
   }

   public void setStride(int var1) {
      this.stride = var1;

      for(int var2 = 0; var2 < this.datasets.size(); ++var2) {
         ((Dataset)((Dataset)this.datasets.get(var2))).setStride(this.stride);
      }

   }

   public void setMarkerColor(int var1, Color var2) {
      this.checkDatasetIndex(var1);
      Dataset var3 = (Dataset)this.datasets.get(var1);
      var3.setMarkerColor(var2);
   }

   public void setMarkerColor(int var1, Color var2, Color var3) {
      this.checkDatasetIndex(var1);
      Dataset var4 = (Dataset)this.datasets.get(var1);
      var4.setMarkerColor(var2, var3);
   }

   public void setMarkerShape(int var1, int var2) {
      this.checkDatasetIndex(var1);
      Dataset var3 = (Dataset)this.datasets.get(var1);
      var3.setMarkerShape(var2);
   }

   public void setCustomMarker(int var1, Shape var2) {
      this.checkDatasetIndex(var1);
      Dataset var3 = (Dataset)this.datasets.get(var1);
      var3.setCustomMarker(var2);
   }

   public void setXColumnVisible(int var1, boolean var2) {
      this.checkDatasetIndex(var1);
      Dataset var3 = (Dataset)this.datasets.get(var1);
      var3.setXColumnVisible(var2);
   }

   public void setYColumnVisible(int var1, boolean var2) {
      this.checkDatasetIndex(var1);
      Dataset var3 = (Dataset)this.datasets.get(var1);
      var3.setYColumnVisible(var2);
   }

   public void setMarkerSize(int var1, int var2) {
      this.checkDatasetIndex(var1);
      Dataset var3 = (Dataset)this.datasets.get(var1);
      var3.setMarkerSize(var2);
   }

   public void setLineColor(int var1, Color var2) {
      this.checkDatasetIndex(var1);
      Dataset var3 = (Dataset)this.datasets.get(var1);
      var3.setLineColor(var2);
   }

   public void setXYColumnNames(int var1, String var2, String var3, String var4) {
      this.checkDatasetIndex(var1);
      Dataset var5 = (Dataset)this.datasets.get(var1);
      var5.setXYColumnNames(var2, var3, var4);
   }

   public void setXYColumnNames(int var1, String var2, String var3) {
      this.checkDatasetIndex(var1);
      Dataset var4 = (Dataset)this.datasets.get(var1);
      var4.setXYColumnNames(var2, var3);
   }

   public boolean isMeasured() {
      for(int var1 = 0; var1 < this.datasets.size(); ++var1) {
         Dataset var2 = (Dataset)this.datasets.get(var1);
         if (var2.isMeasured()) {
            return true;
         }
      }

      return false;
   }

   public double getXMin() {
      double var1 = Double.MAX_VALUE;

      for(int var3 = 0; var3 < this.datasets.size(); ++var3) {
         Dataset var4 = (Dataset)this.datasets.get(var3);
         if (var4.isMeasured()) {
            var1 = Math.min(var1, var4.getXMin());
         }
      }

      return var1;
   }

   public double getXMax() {
      double var1 = -1.7976931348623157E308D;

      for(int var3 = 0; var3 < this.datasets.size(); ++var3) {
         Dataset var4 = (Dataset)this.datasets.get(var3);
         if (var4.isMeasured()) {
            var1 = Math.max(var1, var4.getXMax());
         }
      }

      return var1;
   }

   public double getYMin() {
      double var1 = Double.MAX_VALUE;

      for(int var3 = 0; var3 < this.datasets.size(); ++var3) {
         Dataset var4 = (Dataset)this.datasets.get(var3);
         if (var4.isMeasured()) {
            var1 = Math.min(var1, var4.getYMin());
         }
      }

      return var1;
   }

   public double getYMax() {
      double var1 = -1.7976931348623157E308D;

      for(int var3 = 0; var3 < this.datasets.size(); ++var3) {
         Dataset var4 = (Dataset)this.datasets.get(var3);
         if (var4.isMeasured()) {
            var1 = Math.max(var1, var4.getYMax());
         }
      }

      return var1;
   }

   public double[] getXPoints(int var1) {
      this.checkDatasetIndex(var1);
      Dataset var2 = (Dataset)this.datasets.get(var1);
      return var2.getXPoints();
   }

   public double[] getYPoints(int var1) {
      this.checkDatasetIndex(var1);
      Dataset var2 = (Dataset)this.datasets.get(var1);
      return var2.getYPoints();
   }

   public boolean isSorted(int var1) {
      this.checkDatasetIndex(var1);
      Dataset var2 = (Dataset)this.datasets.get(var1);
      return var2.isSorted();
   }

   public boolean isConnected(int var1) {
      this.checkDatasetIndex(var1);
      Dataset var2 = (Dataset)this.datasets.get(var1);
      return var2.isConnected();
   }

   public int getColumnCount() {
      int var1 = 0;

      for(int var2 = 0; var2 < this.datasets.size(); ++var2) {
         Dataset var3 = (Dataset)this.datasets.get(var2);
         var1 += var3.getColumnCount();
      }

      return var1;
   }

   public int getRowCount() {
      int var1 = 0;

      for(int var2 = 0; var2 < this.datasets.size(); ++var2) {
         Dataset var3 = (Dataset)this.datasets.get(var2);
         var1 = Math.max(var1, var3.getRowCount());
      }

      return var1;
   }

   public String getColumnName(int var1) {
      if (this.datasets.size() == 0) {
         return null;
      } else {
         int var2 = 0;

         for(int var3 = 0; var3 < this.datasets.size(); ++var3) {
            Dataset var4 = (Dataset)this.datasets.get(var3);
            int var5 = var4.getColumnCount();
            var2 += var5;
            if (var2 > var1) {
               int var6 = Math.abs(var2 - var5 - var1);
               return var4.getColumnName(var6);
            }
         }

         return null;
      }
   }

   public Object getValueAt(int var1, int var2) {
      if (this.datasets.size() == 0) {
         return null;
      } else {
         int var3 = 0;

         for(int var4 = 0; var4 < this.datasets.size(); ++var4) {
            Dataset var5 = (Dataset)this.datasets.get(var4);
            int var6 = var5.getColumnCount();
            var3 += var6;
            if (var3 > var2) {
               if (var1 >= var5.getRowCount()) {
                  return null;
               }

               int var7 = Math.abs(var3 - var6 - var2);
               return var5.getValueAt(var1, var7);
            }
         }

         return null;
      }
   }

   public Class getColumnClass(int var1) {
      return class$java$lang$Double == null ? (class$java$lang$Double = class$("java.lang.Double")) : class$java$lang$Double;
   }

   public void append(int var1, double var2, double var4) {
      this.checkDatasetIndex(var1);
      Dataset var6 = (Dataset)this.datasets.get(var1);
      var6.append(var2, var4);
   }

   public void append(int var1, double var2, double var4, double var6, double var8) {
      this.checkDatasetIndex(var1);
      Dataset var10 = (Dataset)this.datasets.get(var1);
      var10.append(var2, var4, var6, var8);
   }

   public void append(int var1, double[] var2, double[] var3) {
      this.checkDatasetIndex(var1);
      Dataset var4 = (Dataset)this.datasets.get(var1);
      var4.append(var2, var3);
   }

   public void append(int var1, double[] var2, double[] var3, double[] var4, double[] var5) {
      this.checkDatasetIndex(var1);
      Dataset var6 = (Dataset)this.datasets.get(var1);
      var6.append(var2, var3, var4, var5);
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      for(int var3 = 0; var3 < this.datasets.size(); ++var3) {
         ((Dataset)((Dataset)this.datasets.get(var3))).draw(var1, var2);
      }

   }

   public void clear(int var1) {
      this.checkDatasetIndex(var1);
      Dataset var2 = (Dataset)this.datasets.get(var1);
      var2.clear();
   }

   public void clear() {
      for(int var1 = 0; var1 < this.datasets.size(); ++var1) {
         ((Dataset)((Dataset)this.datasets.get(var1))).clear();
      }

   }

   public void removeDatasets() {
      this.clear();
      this.datasets.clear();
   }

   public Dataset getDataset(int var1) {
      this.checkDatasetIndex(var1);
      return (Dataset)this.datasets.get(var1);
   }

   public ArrayList getDatasets() {
      return (ArrayList)this.datasets.clone();
   }

   public String toString() {
      if (this.datasets.size() == 0) {
         return "No data in datasets.";
      } else {
         StringBuffer var1 = new StringBuffer();

         for(int var2 = 0; var2 < this.datasets.size(); ++var2) {
            var1.append("Dataset ");
            var1.append(var2);
            var1.append('\n');
            var1.append(this.datasets.get(var2).toString());
         }

         return var1.toString();
      }
   }

   public void setXYColumnNames(String var1, String var2) {
      this.xColumnName = var1;
      this.yColumnName = var2;
      int var3 = 0;

      for(int var4 = this.datasets.size(); var3 < var4; ++var3) {
         ((Dataset)((Dataset)this.datasets.get(var3))).setXYColumnNames(var1, var2);
      }

   }

   public static Color getLineColor(int var0) {
      return var0 < lineColors.length - 1 ? lineColors[var0] : GUIUtils.randomColor();
   }

   protected void checkDatasetIndex(int var1) {
      while(var1 >= this.datasets.size()) {
         Dataset var2 = null;
         if (var1 < lineColors.length - 1) {
            var2 = new Dataset(markerColors[var1], lineColors[var1], this.connected);
         } else {
            var2 = new Dataset(GUIUtils.randomColor(), GUIUtils.randomColor(), this.connected);
         }

         if (this.linked && this.datasets.size() > 0) {
            var2.setXColumnVisible(false);
         }

         var2.setSorted(this.sorted);
         var2.setXYColumnNames(this.xColumnName, this.yColumnName);
         var2.setMarkerShape(this.markerShape);
         this.datasets.add(var2);
      }

   }

   public static XML.ObjectLoader getLoader() {
      return new DatasetManager.Loader();
   }

   static {
      lineColors = new Color[]{Color.red, Color.green, Color.blue, Color.yellow.darker(), Color.cyan, Color.magenta};
      markerColors = new Color[]{Color.black, Color.blue, Color.red, Color.green, Color.darkGray, Color.lightGray};
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   private static class Loader extends XMLLoader {
      private Loader() {
      }

      public void saveObject(XMLControl var1, Object var2) {
         DatasetManager var3 = (DatasetManager)var2;
         var1.setValue("connected", var3.connected);
         var1.setValue("sorted", var3.sorted);
         var1.setValue("maker_shape", var3.markerShape);
         var1.setValue("stride", var3.stride);
         var1.setValue("linked", var3.linked);
         var1.setValue("x_column_name", var3.xColumnName);
         var1.setValue("y_column_name", var3.yColumnName);
         var1.setValue("datasets", var3.datasets);
      }

      public Object createObject(XMLControl var1) {
         return new DatasetManager();
      }

      public Object loadObject(XMLControl var1, Object var2) {
         DatasetManager var3 = (DatasetManager)var2;
         var3.connected = var1.getBoolean("connected");
         var3.sorted = var1.getBoolean("sorted");
         var3.markerShape = var1.getInt("maker_shape");
         var3.stride = var1.getInt("stride");
         var3.linked = var1.getBoolean("linked");
         var3.xColumnName = var1.getString("x_column_name");
         var3.yColumnName = var1.getString("y_column_name");
         Collection var4 = (Collection)var1.getObject("datasets");
         var3.removeDatasets();
         if (var4 != null) {
            Iterator var5 = var4.iterator();

            while(var5.hasNext()) {
               var3.datasets.add((Dataset)var5.next());
            }
         }

         return var2;
      }

      // $FF: synthetic method
      Loader(Object var1) {
         this();
      }
   }
}
