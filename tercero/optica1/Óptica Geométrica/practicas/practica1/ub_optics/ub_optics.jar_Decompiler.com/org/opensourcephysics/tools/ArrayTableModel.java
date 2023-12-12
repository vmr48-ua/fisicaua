package org.opensourcephysics.tools;

import javax.swing.table.AbstractTableModel;

public class ArrayTableModel extends AbstractTableModel {
   double[] doubleArray1;
   double[][] doubleArray2;
   int[] intArray1;
   int[][] intArray2;
   String[] stringArray1;
   String[][] stringArray2;
   boolean[] booleanArray1;
   boolean[][] booleanArray2;
   boolean editable = false;

   public ArrayTableModel(int[] var1) {
      this.intArray1 = var1;
   }

   public ArrayTableModel(int[][] var1) {
      this.intArray2 = var1;
   }

   public ArrayTableModel(double[] var1) {
      this.doubleArray1 = var1;
   }

   public ArrayTableModel(double[][] var1) {
      this.doubleArray2 = var1;
   }

   public ArrayTableModel(String[] var1) {
      this.stringArray1 = var1;
   }

   public ArrayTableModel(String[][] var1) {
      this.stringArray2 = var1;
   }

   public ArrayTableModel(boolean[] var1) {
      this.booleanArray1 = var1;
   }

   public ArrayTableModel(boolean[][] var1) {
      this.booleanArray2 = var1;
   }

   public void setEditable(boolean var1) {
      this.editable = var1;
   }

   public int getColumnCount() {
      if (this.getRowCount() == 0) {
         return 0;
      } else if (this.intArray1 == null && this.doubleArray1 == null && this.stringArray1 == null && this.booleanArray1 == null) {
         if (this.intArray2 != null) {
            return this.intArray2[0].length + 1;
         } else if (this.doubleArray2 != null) {
            return this.doubleArray2[0].length + 1;
         } else if (this.stringArray2 != null) {
            return this.stringArray2[0].length + 1;
         } else {
            return this.booleanArray2 != null ? this.booleanArray2[0].length + 1 : 0;
         }
      } else {
         return 2;
      }
   }

   public String getColumnName(int var1) {
      if (var1 == 0) {
         return "";
      } else {
         return this.intArray1 == null && this.doubleArray1 == null && this.stringArray1 == null && this.booleanArray1 == null ? "" + (var1 - 1) : "value";
      }
   }

   public int getRowCount() {
      if (this.intArray1 != null) {
         return this.intArray1.length;
      } else if (this.intArray2 != null) {
         return this.intArray2.length;
      } else if (this.doubleArray1 != null) {
         return this.doubleArray1.length;
      } else if (this.doubleArray2 != null) {
         return this.doubleArray2.length;
      } else if (this.stringArray1 != null) {
         return this.stringArray1.length;
      } else if (this.stringArray2 != null) {
         return this.stringArray2.length;
      } else if (this.booleanArray1 != null) {
         return this.booleanArray1.length;
      } else {
         return this.booleanArray2 != null ? this.booleanArray2.length : 0;
      }
   }

   public Object getValueAt(int var1, int var2) {
      if (var2 == 0) {
         return new Integer(var1);
      } else if (this.intArray1 != null) {
         return new Integer(this.intArray1[var1]);
      } else if (this.intArray2 != null) {
         return new Integer(this.intArray2[var1][var2 - 1]);
      } else if (this.doubleArray1 != null) {
         return new Double(this.doubleArray1[var1]);
      } else if (this.doubleArray2 != null) {
         return new Double(this.doubleArray2[var1][var2 - 1]);
      } else if (this.stringArray1 != null) {
         return this.stringArray1[var1];
      } else if (this.stringArray2 != null) {
         return this.stringArray2[var1][var2 - 1];
      } else if (this.booleanArray1 != null) {
         return new Boolean(this.booleanArray1[var1]);
      } else {
         return this.booleanArray2 != null ? new Boolean(this.booleanArray2[var1][var2 - 1]) : null;
      }
   }

   public boolean isCellEditable(int var1, int var2) {
      return this.editable && var2 > 0;
   }

   public void setValueAt(Object var1, int var2, int var3) {
      if (var1 instanceof String) {
         String var4 = (String)var1;
         if (this.intArray1 != null) {
            try {
               this.intArray1[var2] = Integer.parseInt(var4);
            } catch (NumberFormatException var9) {
            }
         } else if (this.intArray2 != null) {
            try {
               this.intArray2[var2][var3 - 1] = Integer.parseInt(var4);
            } catch (NumberFormatException var8) {
            }
         } else if (this.doubleArray1 != null) {
            try {
               this.doubleArray1[var2] = Double.parseDouble(var4);
            } catch (NumberFormatException var7) {
            }
         } else if (this.doubleArray2 != null) {
            try {
               this.doubleArray2[var2][var3 - 1] = Double.parseDouble(var4);
            } catch (NumberFormatException var6) {
            }
         } else if (this.stringArray1 != null) {
            this.stringArray1[var2] = var4;
         } else if (this.stringArray2 != null) {
            this.stringArray2[var2][var3 - 1] = var4;
         } else if (this.booleanArray1 != null) {
            this.booleanArray1[var2] = var4.toLowerCase().startsWith("t");
         } else if (this.booleanArray2 != null) {
            this.booleanArray2[var2][var3 - 1] = var4.toLowerCase().startsWith("t");
         }

         this.fireTableCellUpdated(var2, var3);
      }

   }
}
