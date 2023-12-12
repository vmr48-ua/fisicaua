package org.opensourcephysics.tools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLProperty;

public class ArrayInspector extends JDialog implements PropertyChangeListener {
   JTabbedPane tabbedPane;
   ArrayTable[] tables;
   JSpinner spinner;
   JScrollPane scrollpane;
   Object array;
   boolean changed;
   // $FF: synthetic field
   static Class class$java$lang$String;

   public static ArrayInspector getInspector(XMLProperty var0) {
      if (!var0.getPropertyType().equals("array")) {
         return null;
      } else {
         Class var1;
         for(var1 = var0.getPropertyClass(); var1.getComponentType() != null; var1 = var1.getComponentType()) {
         }

         if (!var1.getName().equals("double") && !var1.getName().equals("int") && !var1.getName().equals("boolean") && !var1.equals(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String)) {
            return null;
         } else {
            String var2 = var0.getPropertyName();

            XMLProperty var3;
            for(var3 = var0.getParentProperty(); !(var3 instanceof XMLControl); var3 = var3.getParentProperty()) {
               var2 = var3.getPropertyName();
               var0 = var3;
            }

            var1 = var0.getPropertyClass();

            for(int var4 = 0; var1.getComponentType() != null; ++var4) {
               var1 = var1.getComponentType();
            }

            XMLControl var5 = (XMLControl)var3;
            Object var6 = var5.getObject(var2);
            return var6 == null ? null : getInspector(var6, var2);
         }
      }
   }

   public static ArrayInspector getInspector(Object var0, String var1) {
      ArrayInspector var2 = null;
      if (var0 instanceof double[]) {
         double[] var3 = (double[])((double[])var0);
         var2 = new ArrayInspector(var3, var1);
      } else if (var0 instanceof double[][]) {
         double[][] var4 = (double[][])((double[][])var0);
         var2 = new ArrayInspector(var4, var1);
      } else if (var0 instanceof double[][][]) {
         double[][][] var5 = (double[][][])((double[][][])var0);
         var2 = new ArrayInspector(var5, var1);
      } else if (var0 instanceof int[]) {
         int[] var6 = (int[])((int[])var0);
         var2 = new ArrayInspector(var6, var1);
      } else if (var0 instanceof int[][]) {
         int[][] var7 = (int[][])((int[][])var0);
         var2 = new ArrayInspector(var7, var1);
      } else if (var0 instanceof int[][][]) {
         int[][][] var8 = (int[][][])((int[][][])var0);
         var2 = new ArrayInspector(var8, var1);
      } else if (var0 instanceof String[]) {
         String[] var9 = (String[])((String[])var0);
         var2 = new ArrayInspector(var9, var1);
      } else if (var0 instanceof String[][]) {
         String[][] var10 = (String[][])((String[][])var0);
         var2 = new ArrayInspector(var10, var1);
      } else if (var0 instanceof String[][][]) {
         String[][][] var11 = (String[][][])((String[][][])var0);
         var2 = new ArrayInspector(var11, var1);
      } else if (var0 instanceof boolean[]) {
         boolean[] var12 = (boolean[])((boolean[])var0);
         var2 = new ArrayInspector(var12, var1);
      } else if (var0 instanceof boolean[][]) {
         boolean[][] var13 = (boolean[][])((boolean[][])var0);
         var2 = new ArrayInspector(var13, var1);
      } else if (var0 instanceof boolean[][][]) {
         boolean[][][] var14 = (boolean[][][])((boolean[][][])var0);
         var2 = new ArrayInspector(var14, var1);
      }

      if (var2 != null) {
         var2.array = var0;
      }

      return var2;
   }

   public static boolean canInspect(XMLProperty var0) {
      if (!var0.getPropertyType().equals("array")) {
         return false;
      } else {
         String var1 = var0.getPropertyName();

         XMLProperty var2;
         for(var2 = var0.getParentProperty(); !(var2 instanceof XMLControl); var2 = var2.getParentProperty()) {
            var1 = var2.getPropertyName();
         }

         XMLControl var3 = (XMLControl)var2;
         Object var4 = var3.getObject(var1);
         return canInspect(var4);
      }
   }

   public static boolean canInspect(Object var0) {
      if (var0 == null) {
         return false;
      } else {
         return var0 instanceof double[] || var0 instanceof double[][] || var0 instanceof double[][][] || var0 instanceof int[] || var0 instanceof int[][] || var0 instanceof int[][][] || var0 instanceof boolean[] || var0 instanceof boolean[][] || var0 instanceof boolean[][][] || var0 instanceof String[] || var0 instanceof String[][] || var0 instanceof String[][][];
      }
   }

   public Object getArray() {
      return this.array;
   }

   public void propertyChange(PropertyChangeEvent var1) {
      this.changed = true;
      this.firePropertyChange(var1.getPropertyName(), var1.getOldValue(), var1.getNewValue());
   }

   public void setEditable(boolean var1) {
      for(int var2 = 0; var2 < this.tables.length; ++var2) {
         this.tables[var2].setEditable(var1);
      }

   }

   public void refreshTable() {
      for(int var1 = 0; var1 < this.tables.length; ++var1) {
         this.tables[var1].refreshTable();
      }

   }

   protected void createGUI() {
      this.setSize(400, 300);
      this.setContentPane(new JPanel(new BorderLayout()));
      this.scrollpane = new JScrollPane(this.tables[0]);
      if (this.tables.length > 1) {
         SpinnerNumberModel var1 = new SpinnerNumberModel(0, 0, this.tables.length - 1, 1);
         this.spinner = new JSpinner(var1);
         NumberEditor var2 = new NumberEditor(this.spinner);
         var2.getTextField().setFont(this.tables[0].indexRenderer.getFont());
         this.spinner.setEditor(var2);
         this.spinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent var1) {
               int var2 = (Integer)ArrayInspector.this.spinner.getValue();
               ArrayInspector.this.scrollpane.setViewportView(ArrayInspector.this.tables[var2]);
            }
         });
         Dimension var3 = this.spinner.getMinimumSize();
         this.spinner.setMaximumSize(var3);
         this.getContentPane().add(this.scrollpane, "Center");
         JToolBar var4 = new JToolBar();
         var4.setFloatable(false);
         var4.add(new JLabel(" index "));
         var4.add(this.spinner);
         var4.add(Box.createHorizontalGlue());
         this.getContentPane().add(var4, "North");
      } else {
         this.scrollpane.createHorizontalScrollBar();
         this.getContentPane().add(this.scrollpane, "Center");
      }

   }

   private ArrayInspector() {
      super((Frame)null, true);
      this.tabbedPane = new JTabbedPane();
      this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent var1) {
            if (ArrayInspector.this.changed) {
               ArrayInspector.this.firePropertyChange("arrayData", (Object)null, (Object)null);
            }

         }
      });
   }

   private ArrayInspector(int[] var1) {
      this();
      this.tables = new ArrayTable[1];
      this.tables[0] = new ArrayTable(var1);
      this.tables[0].addPropertyChangeListener("cell", this);
      this.createGUI();
      this.setTitle("Array: int[row]");
   }

   private ArrayInspector(int[] var1, String var2) {
      this(var1);
      this.setTitle("Array \"" + var2 + "\": int[row]");
   }

   private ArrayInspector(int[][] var1) {
      this();
      this.tables = new ArrayTable[1];
      this.tables[0] = new ArrayTable(var1);
      this.tables[0].addPropertyChangeListener("cell", this);
      this.createGUI();
      this.setTitle("Array: int[row][column]");
   }

   private ArrayInspector(int[][] var1, String var2) {
      this(var1);
      this.setTitle("Array \"" + var2 + "\": int[row][column]");
   }

   private ArrayInspector(int[][][] var1) {
      this();
      this.tables = new ArrayTable[var1.length];

      for(int var2 = 0; var2 < this.tables.length; ++var2) {
         this.tables[var2] = new ArrayTable(var1[var2]);
         this.tables[var2].addPropertyChangeListener("cell", this);
      }

      this.createGUI();
      this.setTitle("Array: int[index][row][column]");
   }

   private ArrayInspector(int[][][] var1, String var2) {
      this(var1);
      this.setTitle("Array \"" + var2 + "\": int[index][row][column]");
   }

   private ArrayInspector(double[] var1) {
      this();
      this.tables = new ArrayTable[1];
      this.tables[0] = new ArrayTable(var1);
      this.tables[0].addPropertyChangeListener("cell", this);
      this.createGUI();
      this.setTitle("Array: double[row]");
   }

   private ArrayInspector(double[] var1, String var2) {
      this(var1);
      this.setTitle("Array \"" + var2 + "\": double[row]");
   }

   private ArrayInspector(double[][] var1) {
      this();
      this.tables = new ArrayTable[1];
      this.tables[0] = new ArrayTable(var1);
      this.tables[0].addPropertyChangeListener("cell", this);
      this.createGUI();
      this.setTitle("Array: double[row][column]");
   }

   private ArrayInspector(double[][] var1, String var2) {
      this(var1);
      this.setTitle("Array \"" + var2 + "\": double[row][column]");
   }

   private ArrayInspector(double[][][] var1) {
      this();
      this.tables = new ArrayTable[var1.length];

      for(int var2 = 0; var2 < this.tables.length; ++var2) {
         this.tables[var2] = new ArrayTable(var1[var2]);
         this.tables[var2].addPropertyChangeListener("cell", this);
      }

      this.createGUI();
      this.setTitle("Array: double[index][row][column]");
   }

   private ArrayInspector(double[][][] var1, String var2) {
      this(var1);
      this.setTitle("Array \"" + var2 + "\": double[index][row][column]");
   }

   private ArrayInspector(String[] var1) {
      this();
      this.tables = new ArrayTable[1];
      this.tables[0] = new ArrayTable(var1);
      this.tables[0].addPropertyChangeListener("cell", this);
      this.createGUI();
      this.setTitle("Array: String[row]");
   }

   private ArrayInspector(String[] var1, String var2) {
      this(var1);
      this.setTitle("Array \"" + var2 + "\": String[row]");
   }

   private ArrayInspector(String[][] var1) {
      this();
      this.tables = new ArrayTable[1];
      this.tables[0] = new ArrayTable(var1);
      this.tables[0].addPropertyChangeListener("cell", this);
      this.createGUI();
      this.setTitle("Array: String[row][column]");
   }

   private ArrayInspector(String[][] var1, String var2) {
      this(var1);
      this.setTitle("Array \"" + var2 + "\": String[row][column]");
   }

   private ArrayInspector(String[][][] var1) {
      this();
      this.tables = new ArrayTable[var1.length];

      for(int var2 = 0; var2 < this.tables.length; ++var2) {
         this.tables[var2] = new ArrayTable(var1[var2]);
         this.tables[var2].addPropertyChangeListener("cell", this);
      }

      this.createGUI();
      this.setTitle("Array: String[index][row][column]");
   }

   private ArrayInspector(String[][][] var1, String var2) {
      this(var1);
      this.setTitle("Array \"" + var2 + "\": String[index][row][column]");
   }

   private ArrayInspector(boolean[] var1) {
      this();
      this.tables = new ArrayTable[1];
      this.tables[0] = new ArrayTable(var1);
      this.tables[0].addPropertyChangeListener("cell", this);
      this.createGUI();
      this.setTitle("Array: boolean[row]");
   }

   private ArrayInspector(boolean[] var1, String var2) {
      this(var1);
      this.setTitle("Array \"" + var2 + "\": boolean[row]");
   }

   private ArrayInspector(boolean[][] var1) {
      this();
      this.tables = new ArrayTable[1];
      this.tables[0] = new ArrayTable(var1);
      this.tables[0].addPropertyChangeListener("cell", this);
      this.createGUI();
      this.setTitle("Array: boolean[row][column]");
   }

   private ArrayInspector(boolean[][] var1, String var2) {
      this(var1);
      this.setTitle("Array \"" + var2 + "\": boolean[row][column]");
   }

   private ArrayInspector(boolean[][][] var1) {
      this();
      this.tables = new ArrayTable[var1.length];

      for(int var2 = 0; var2 < this.tables.length; ++var2) {
         this.tables[var2] = new ArrayTable(var1[var2]);
         this.tables[var2].addPropertyChangeListener("cell", this);
      }

      this.createGUI();
      this.setTitle("Array: boolean[index][row][column]");
   }

   private ArrayInspector(boolean[][][] var1, String var2) {
      this(var1);
      this.setTitle("Array \"" + var2 + "\": boolean[index][row][column]");
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
