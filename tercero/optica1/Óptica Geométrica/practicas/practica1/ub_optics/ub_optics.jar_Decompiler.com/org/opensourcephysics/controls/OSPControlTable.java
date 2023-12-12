package org.opensourcephysics.controls;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.opensourcephysics.numerics.DoubleArray;
import org.opensourcephysics.numerics.IntegerArray;
import org.opensourcephysics.numerics.Util;

public class OSPControlTable extends XMLTable implements Control {
   static Color ERROR_COLOR;
   private HashMap valueCache;
   private boolean lockValues;
   private DecimalFormat format;
   // $FF: synthetic field
   static Class class$java$lang$Boolean;
   // $FF: synthetic field
   static Class class$java$lang$Double;
   // $FF: synthetic field
   static Class class$java$lang$Integer;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$numerics$DoubleArray;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$numerics$IntegerArray;

   public OSPControlTable() {
      this(new XMLControlElement());
   }

   public OSPControlTable(XMLControlElement var1) {
      super(var1);
      this.valueCache = new HashMap();
      this.lockValues = false;
   }

   public void setLockValues(boolean var1) {
      super.tableModel.control.setLockValues(var1);
      this.lockValues = var1;
      if (!this.lockValues) {
         this.refresh();
      }

   }

   public void setValue(String var1, Object var2) {
      if (this.getBackgroundColor(var1) == ERROR_COLOR) {
         this.setBackgroundColor(var1, Color.WHITE);
      }

      super.tableModel.control.setValue(var1, var2);
      if (!this.lockValues) {
         this.refresh();
      }

   }

   public void setDecimalFormat(String var1) {
      if (var1 == null) {
         this.format = null;
      } else {
         this.format = new DecimalFormat(var1);
      }

   }

   public void setValue(String var1, double var2) {
      if (this.format == null) {
         this.setValue(var1, Double.toString(var2));
      } else {
         this.setValue(var1, this.format.format(var2));
      }

      if (!Double.isNaN(var2)) {
         this.valueCache.put(var1, new Double(var2));
      }

   }

   public void setValue(String var1, int var2) {
      this.setValue(var1, Integer.toString(var2));
      this.valueCache.put(var1, new Double((double)var2));
   }

   public void setValue(String var1, boolean var2) {
      if (this.getBackgroundColor(var1) == ERROR_COLOR) {
         this.setBackgroundColor(var1, Color.WHITE);
      }

      super.tableModel.control.setValue(var1, var2);
   }

   public int getInt(String var1) {
      String var2 = super.tableModel.control.getString(var1);
      Color var3 = (Color)super.cellColors.get(var1);
      boolean var4 = this.isEditable(var1);

      int var9;
      try {
         var9 = Integer.parseInt(var1);
         if (var4 && var3 != Color.WHITE) {
            this.setBackgroundColor(var1, Color.WHITE);
            this.refresh();
         } else if (!var4 && var3 != OSPControl.NOT_EDITABLE_BACKGROUND) {
            this.setBackgroundColor(var1, OSPControl.NOT_EDITABLE_BACKGROUND);
            this.refresh();
         }

         this.valueCache.put(var1, new Double((double)var9));
         return var9;
      } catch (NumberFormatException var8) {
         try {
            var9 = (int)Double.parseDouble(var1);
            if (var4 && var3 != Color.WHITE) {
               this.setBackgroundColor(var1, Color.WHITE);
               this.refresh();
            } else if (!var4 && var3 != OSPControl.NOT_EDITABLE_BACKGROUND) {
               this.setBackgroundColor(var1, OSPControl.NOT_EDITABLE_BACKGROUND);
               this.refresh();
            }

            this.valueCache.put(var1, new Double((double)var9));
            return var9;
         } catch (NumberFormatException var7) {
            double var5 = Util.evalMath(var2);
            if (Double.isNaN(var5) && var3 != ERROR_COLOR) {
               this.setBackgroundColor(var1, ERROR_COLOR);
               this.refresh();
               return this.valueCache.containsKey(var1) ? (int)(Double)this.valueCache.get(var1) : 0;
            } else {
               if (var4 && var3 != Color.WHITE) {
                  this.setBackgroundColor(var1, Color.WHITE);
                  this.refresh();
               } else if (!var4 && var3 != OSPControl.NOT_EDITABLE_BACKGROUND) {
                  this.setBackgroundColor(var1, OSPControl.NOT_EDITABLE_BACKGROUND);
                  this.refresh();
               }

               this.valueCache.put(var1, new Double(var5));
               return (int)var5;
            }
         }
      }
   }

   public boolean inputError(String var1) {
      return this.getBackgroundColor(var1) == ERROR_COLOR;
   }

   public double getDouble(String var1) {
      String var2 = super.tableModel.control.getString(var1);
      Color var3 = (Color)super.cellColors.get(var1);
      boolean var4 = this.isEditable(var1);

      double var5;
      try {
         var5 = Double.parseDouble(var2);
         if (var4 && var3 != Color.WHITE) {
            this.setBackgroundColor(var1, Color.WHITE);
            this.refresh();
         } else if (!var4 && var3 != OSPControl.NOT_EDITABLE_BACKGROUND) {
            this.setBackgroundColor(var1, OSPControl.NOT_EDITABLE_BACKGROUND);
            this.refresh();
         }

         this.valueCache.put(var1, new Double(var5));
         return var5;
      } catch (NumberFormatException var7) {
         var5 = Util.evalMath(var2);
         if (Double.isNaN(var5) && var3 != ERROR_COLOR) {
            this.setBackgroundColor(var1, ERROR_COLOR);
            this.refresh();
         } else if (var4 && var3 != Color.WHITE) {
            this.setBackgroundColor(var1, Color.WHITE);
            this.refresh();
         } else if (!var4 && var3 != OSPControl.NOT_EDITABLE_BACKGROUND) {
            this.setBackgroundColor(var1, OSPControl.NOT_EDITABLE_BACKGROUND);
            this.refresh();
         }

         if (Double.isNaN(var5) && this.valueCache.containsKey(var1)) {
            var5 = (Double)this.valueCache.get(var1);
         } else {
            this.valueCache.put(var1, new Double(var5));
         }

         return var5;
      }
   }

   public Object getObject(String var1) throws UnsupportedOperationException {
      return super.tableModel.control.getObject(var1);
   }

   public String getString(String var1) {
      return super.tableModel.control.getString(var1);
   }

   public boolean getBoolean(String var1) {
      return super.tableModel.control.getBoolean(var1);
   }

   public Collection getPropertyNames() {
      return super.tableModel.control.getPropertyNames();
   }

   public void removeParameter(String var1) {
      super.tableModel.control.setValue(var1, (Object)null);
      this.setBackgroundColor(var1, Color.WHITE);
   }

   public void println(String var1) {
      super.tableModel.control.println(var1);
   }

   public void println() {
      super.tableModel.control.println();
   }

   public void print(String var1) {
      super.tableModel.control.print(var1);
   }

   public void clearMessages() {
      super.tableModel.control.clearMessages();
   }

   public void clearValues() {
      super.tableModel.control.clearValues();
   }

   public void calculationDone(String var1) {
      if (var1 != null) {
         super.tableModel.control.calculationDone(var1);
      }

   }

   public static XML.ObjectLoader getLoader() {
      return new OSPControlTable.OSPControlTableLoader();
   }

   static {
      ERROR_COLOR = Color.PINK;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static class OSPControlTableLoader implements XML.ObjectLoader {
      public void saveObject(XMLControl var1, Object var2) {
         OSPControl var3 = (OSPControl)var2;
         Iterator var4 = var3.getPropertyNames().iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            Object var6 = var3.getObject(var5);
            if (var6.getClass() == (OSPControlTable.class$org$opensourcephysics$numerics$DoubleArray == null ? (OSPControlTable.class$org$opensourcephysics$numerics$DoubleArray = OSPControlTable.class$("org.opensourcephysics.numerics.DoubleArray")) : OSPControlTable.class$org$opensourcephysics$numerics$DoubleArray)) {
               var1.setValue(var5, ((DoubleArray)var6).getArray());
            } else if (var6.getClass() == (OSPControlTable.class$org$opensourcephysics$numerics$IntegerArray == null ? (OSPControlTable.class$org$opensourcephysics$numerics$IntegerArray = OSPControlTable.class$("org.opensourcephysics.numerics.IntegerArray")) : OSPControlTable.class$org$opensourcephysics$numerics$IntegerArray)) {
               var1.setValue(var5, ((IntegerArray)var6).getArray());
            } else if (var6.getClass() == (OSPControlTable.class$java$lang$Boolean == null ? (OSPControlTable.class$java$lang$Boolean = OSPControlTable.class$("java.lang.Boolean")) : OSPControlTable.class$java$lang$Boolean)) {
               var1.setValue(var5, (Boolean)var6);
            } else if (var6.getClass() == (OSPControlTable.class$java$lang$Double == null ? (OSPControlTable.class$java$lang$Double = OSPControlTable.class$("java.lang.Double")) : OSPControlTable.class$java$lang$Double)) {
               var1.setValue(var5, (Double)var6);
            } else if (var6.getClass() == (OSPControlTable.class$java$lang$Integer == null ? (OSPControlTable.class$java$lang$Integer = OSPControlTable.class$("java.lang.Integer")) : OSPControlTable.class$java$lang$Integer)) {
               var1.setValue(var5, (Integer)var6);
            } else if (var6.getClass().isArray()) {
               var1.setValue(var5, var6);
            } else {
               var1.setValue(var5, var6);
            }
         }

      }

      public Object createObject(XMLControl var1) {
         return new OSPControlTable();
      }

      public Object loadObject(XMLControl var1, Object var2) {
         OSPControl var3 = (OSPControl)var2;
         Iterator var4 = var1.getPropertyNames().iterator();
         var3.control.setLockValues(true);

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            if (var1.getPropertyType(var5).equals("string")) {
               var3.setValue(var5, var1.getString(var5));
            } else if (var1.getPropertyType(var5).equals("int")) {
               var3.setValue(var5, var1.getInt(var5));
            } else if (var1.getPropertyType(var5).equals("double")) {
               var3.setValue(var5, var1.getDouble(var5));
            } else if (var1.getPropertyType(var5).equals("boolean")) {
               var3.setValue(var5, var1.getBoolean(var5));
            } else {
               var3.setValue(var5, var1.getObject(var5));
            }
         }

         var3.control.setLockValues(false);
         return var2;
      }
   }
}
