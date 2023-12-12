package org.opensourcephysics.tools;

import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.display2d.GridData;

public class ExportGnuplotFormat implements ExportFormat {
   public String description() {
      return "Text";
   }

   public String extension() {
      return "txt";
   }

   void exportDataset(PrintWriter var1, Dataset var2, int var3) throws IOException {
      double[] var4 = var2.getXPoints();
      double[] var5 = var2.getYPoints();
      var1.print("\n# name: data" + var3 + "\n" + "# type: matrix\n" + "# rows: " + var4.length + "\n" + "# columns: " + 2 + "\n");

      for(int var6 = 0; var6 < var4.length; ++var6) {
         var1.println(var4[var6] + " " + var5[var6]);
      }

   }

   void exportGridData(PrintWriter var1, GridData var2, int var3) throws IOException {
      int var4 = var2.getNx();
      int var5 = var2.getNy();
      double var6 = var2.getLeft();
      double var8 = var2.getDx();
      var1.println("\n# name: col_range" + var3 + "\n" + "# type: matrix\n" + "# rows: 1\n" + "# columns: " + var4);

      for(int var10 = 0; var10 < var4; ++var10) {
         var1.print(var6 + (double)var10 * var8 + " ");
      }

      var1.println("\n");
      double var19 = var2.getTop();
      double var12 = var2.getDy();
      var1.println("# name: row_range" + var3 + "\n" + "# type: matrix\n" + "# rows: 1\n" + "# columns: " + var5);

      int var14;
      for(var14 = 0; var14 < var5; ++var14) {
         var1.print(var19 + (double)var14 * var12 + " ");
      }

      var1.println("\n");
      var14 = var2.getComponentCount();

      for(int var15 = 0; var15 < var14; ++var15) {
         String var16 = var2.getComponentName(var15);
         var1.println("# name: grid_" + var3 + '_' + var16 + '\n' + "# type: matrix\n" + "# rows: " + var5 + '\n' + "# columns: " + var4);

         for(int var17 = 0; var17 < var5; ++var17) {
            for(int var18 = 0; var18 < var4; ++var18) {
               var1.print(var2.getValue(var18, var17, var15) + " ");
            }

            var1.println();
         }
      }

   }

   public void export(File var1, List var2) {
      try {
         FileWriter var3 = new FileWriter(var1);
         PrintWriter var4 = new PrintWriter(var3);
         var4.println("# Created by the Open Source Physics library");
         Iterator var5 = var2.iterator();

         for(int var6 = 0; var5.hasNext(); ++var6) {
            Object var7 = var5.next();
            if (var7 instanceof Dataset) {
               this.exportDataset(var4, (Dataset)var7, var6);
            } else if (var7 instanceof GridData) {
               this.exportGridData(var4, (GridData)var7, var6);
            }
         }

         var4.close();
      } catch (IOException var8) {
         JOptionPane.showMessageDialog((Component)null, "An error occurred while saving your file. Please try again.", "Error", 0);
      }

   }
}
