package org.opensourcephysics.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.opensourcephysics.numerics.DoubleArray;
import org.opensourcephysics.numerics.IntegerArray;
import org.opensourcephysics.numerics.Util;

public class OSPControl extends ControlFrame implements PropertyChangeListener {
   XMLControl control = new XMLControlElement();
   XMLTable table;
   JScrollPane controlScrollPane;
   JTextArea messageTextArea;
   JLabel clearLabel;
   JSplitPane splitPane;
   private boolean lockValues;
   HashMap valueCache;
   static final Color PANEL_BACKGROUND = UIManager.getColor("Panel.background");
   static final Color NOT_EDITABLE_BACKGROUND;
   // $FF: synthetic field
   static Class class$java$lang$Boolean;
   // $FF: synthetic field
   static Class class$java$lang$Double;
   // $FF: synthetic field
   static Class class$java$lang$Integer;
   // $FF: synthetic field
   static Class class$java$lang$String;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$numerics$DoubleArray;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$numerics$IntegerArray;

   public OSPControl(Object var1) {
      super("OSP Control");
      this.table = new XMLTable(this.control);
      this.controlScrollPane = new JScrollPane(this.table);
      this.lockValues = false;
      this.valueCache = new HashMap();
      super.model = var1;
      if (super.model != null) {
         String var2 = super.model.getClass().getName();
         this.setTitle(var2.substring(1 + var2.lastIndexOf(".")) + " Controller");
      }

      Font var11 = new Font("Dialog", 1, 12);
      JLabel var3 = new JLabel("Input Parameters", 0);
      var3.setFont(var11);
      this.messageTextArea = new JTextArea(5, 5);
      JScrollPane var4 = new JScrollPane(this.messageTextArea);
      JPanel var5 = new JPanel(new BorderLayout());
      var5.add(var3, "North");
      var5.add(this.controlScrollPane, "Center");
      super.buttonPanel.setVisible(true);
      var5.add(super.buttonPanel, "South");
      JPanel var6 = new JPanel(new BorderLayout());
      var6.addMouseListener(new OSPControl.ClearMouseAdapter());
      this.clearLabel = new JLabel(" clear");
      this.clearLabel.setFont(new Font(this.clearLabel.getFont().getFamily(), 0, 9));
      this.clearLabel.setForeground(Color.black);
      var6.add(this.clearLabel, "West");
      JPanel var7 = new JPanel(new BorderLayout());
      JLabel var8 = new JLabel("Messages", 0);
      var8.setFont(var11);
      var7.add(var8, "North");
      var7.add(var4, "Center");
      var7.add(var6, "South");
      Container var9 = this.getContentPane();
      this.splitPane = new JSplitPane(0, var5, var7);
      this.splitPane.setOneTouchExpandable(true);
      var9.add(this.splitPane, "Center");
      this.messageTextArea.setEditable(false);
      this.controlScrollPane.setPreferredSize(new Dimension(350, 200));
      this.controlScrollPane.setMinimumSize(new Dimension(0, 50));
      var4.setPreferredSize(new Dimension(350, 75));
      Dimension var10 = Toolkit.getDefaultToolkit().getScreenSize();
      this.setLocation((var10.width - this.getSize().width) / 2, (var10.height - this.getSize().height) / 2);
      this.init();
   }

   public void propertyChange(PropertyChangeEvent var1) {
      this.firePropertyChange(var1.getPropertyName(), var1.getOldValue(), var1.getNewValue());
   }

   protected void init() {
      this.splitPane.setDividerLocation(-1);
      this.setVisible(true);
      this.setDefaultCloseOperation(3);
   }

   public Object getModel() {
      return super.model;
   }

   public void setDividerLocation(int var1) {
      this.splitPane.setDividerLocation(var1);
   }

   public void setEditable(String var1, boolean var2) {
      this.table.setEditable(var1, var2);
   }

   public void setLockValues(boolean var1) {
      this.control.setLockValues(var1);
      this.lockValues = var1;
      if (!this.lockValues) {
         this.table.refresh();
      }

   }

   public String toString() {
      return this.table.toString();
   }

   public void setValue(String var1, Object var2) {
      this.control.setValue(var1, var2);
      if (!this.lockValues) {
         this.table.refresh();
      }

   }

   public void setValue(String var1, boolean var2) {
      this.control.setValue(var1, var2);
   }

   public void setValue(String var1, double var2) {
      this.setValue(var1, Double.toString(var2));
      if (!Double.isNaN(var2)) {
         this.valueCache.put(var1, new Double(var2));
      }

   }

   public void setValue(String var1, int var2) {
      this.setValue(var1, Integer.toString(var2));
      this.valueCache.put(var1, new Double((double)var2));
   }

   public void removeParameter(String var1) {
      this.control.setValue(var1, (Object)null);
   }

   public double getDouble(String var1) {
      String var2 = this.control.getString(var1);
      Color var3 = (Color)this.table.cellColors.get(var1);
      boolean var4 = this.table.isEditable(var1);

      double var5;
      try {
         var5 = Double.parseDouble(var2);
         if (var4 && var3 != Color.WHITE) {
            this.table.setBackgroundColor(var1, Color.WHITE);
            this.table.refresh();
         } else if (!var4 && var3 != NOT_EDITABLE_BACKGROUND) {
            this.table.setBackgroundColor(var1, NOT_EDITABLE_BACKGROUND);
            this.table.refresh();
         }

         this.valueCache.put(var1, new Double(var5));
         return var5;
      } catch (NumberFormatException var7) {
         var5 = Util.evalMath(var2);
         if (Double.isNaN(var5) && var3 != Color.PINK) {
            this.table.setBackgroundColor(var1, Color.PINK);
            this.table.refresh();
         } else if (var4 && var3 != Color.WHITE) {
            this.table.setBackgroundColor(var1, Color.WHITE);
            this.table.refresh();
         } else if (!var4 && var3 != NOT_EDITABLE_BACKGROUND) {
            this.table.setBackgroundColor(var1, NOT_EDITABLE_BACKGROUND);
            this.table.refresh();
         }

         if (Double.isNaN(var5) && this.valueCache.containsKey(var1)) {
            var5 = (Double)this.valueCache.get(var1);
         } else {
            this.valueCache.put(var1, new Double(var5));
         }

         return var5;
      }
   }

   public int getInt(String var1) {
      String var2 = this.control.getString(var1);
      Color var3 = (Color)this.table.cellColors.get(var1);
      boolean var4 = this.table.isEditable(var1);

      int var9;
      try {
         var9 = Integer.parseInt(var1);
         if (var4 && var3 != Color.WHITE) {
            this.table.setBackgroundColor(var1, Color.WHITE);
            this.table.refresh();
         } else if (!var4 && var3 != NOT_EDITABLE_BACKGROUND) {
            this.table.setBackgroundColor(var1, NOT_EDITABLE_BACKGROUND);
            this.table.refresh();
         }

         this.valueCache.put(var1, new Double((double)var9));
         return var9;
      } catch (NumberFormatException var8) {
         try {
            var9 = (int)Double.parseDouble(var1);
            if (var4 && var3 != Color.WHITE) {
               this.table.setBackgroundColor(var1, Color.WHITE);
               this.table.refresh();
            } else if (!var4 && var3 != NOT_EDITABLE_BACKGROUND) {
               this.table.setBackgroundColor(var1, NOT_EDITABLE_BACKGROUND);
               this.table.refresh();
            }

            this.valueCache.put(var1, new Double((double)var9));
            return var9;
         } catch (NumberFormatException var7) {
            double var5 = Util.evalMath(var2);
            if (Double.isNaN(var5) && var3 != Color.PINK) {
               this.table.setBackgroundColor(var1, Color.PINK);
               this.table.refresh();
               return this.valueCache.containsKey(var1) ? (int)(Double)this.valueCache.get(var1) : 0;
            } else {
               if (var4 && var3 != Color.WHITE) {
                  this.table.setBackgroundColor(var1, Color.WHITE);
                  this.table.refresh();
               } else if (!var4 && var3 != NOT_EDITABLE_BACKGROUND) {
                  this.table.setBackgroundColor(var1, NOT_EDITABLE_BACKGROUND);
                  this.table.refresh();
               }

               this.valueCache.put(var1, new Double(var5));
               return (int)var5;
            }
         }
      }
   }

   public Object getObject(String var1) throws UnsupportedOperationException {
      return this.control.getObject(var1);
   }

   public String getString(String var1) {
      return this.control.getString(var1);
   }

   public boolean getBoolean(String var1) {
      return this.control.getBoolean(var1);
   }

   public Collection getPropertyNames() {
      return this.control.getPropertyNames();
   }

   public JButton addButton(String var1, String var2) {
      return this.addButton(var1, var2, (String)null, super.model);
   }

   public JButton addButton(String var1, String var2, String var3) {
      return this.addButton(var1, var2, var3, super.model);
   }

   public void addControlListener(String var1) {
      this.addControlListener(var1, super.model);
   }

   public void addControlListener(String var1, final Object var2) {
      Class[] var3 = new Class[]{class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String};

      try {
         final Method var4 = var2.getClass().getMethod(var1, var3);
         this.table.tableModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent var1) {
               if (var1.getType() == 0 && var1.getColumn() == 1 && var1.getFirstRow() >= 0) {
                  String var2x = OSPControl.this.table.getValueAt(var1.getFirstRow(), 0).toString();
                  Object[] var3 = new Object[]{var2x};

                  try {
                     var4.invoke(var2, var3);
                  } catch (IllegalAccessException var5) {
                  } catch (InvocationTargetException var6) {
                  }

               }
            }
         });
      } catch (NoSuchMethodException var5) {
         System.err.println("The method " + var1 + "() does not exist.");
      }

   }

   public void println(String var1) {
      this.print(var1 + "\n");
   }

   public void println() {
      this.print("\n");
   }

   public void print(final String var1) {
      if (!SwingUtilities.isEventDispatchThread() && !Thread.currentThread().getName().equals("main")) {
         Runnable var2 = new Runnable() {
            public void run() {
               OSPControl.this.messageTextArea.append(var1);
            }
         };
         EventQueue.invokeLater(var2);
      } else {
         this.messageTextArea.append(var1);
      }
   }

   public void clearMessages() {
      if (!SwingUtilities.isEventDispatchThread() && !Thread.currentThread().getName().equals("main")) {
         Runnable var1 = new Runnable() {
            public void run() {
               OSPControl.this.messageTextArea.setText("");
            }
         };
         EventQueue.invokeLater(var1);
      } else {
         this.messageTextArea.setText("");
      }
   }

   public void clearValues() {
      this.control.clearValues();
   }

   public void calculationDone(String var1) {
      if (var1 != null) {
         this.println(var1);
      }

   }

   public static XML.ObjectLoader getLoader() {
      return new OSPControl.OSPControlLoader();
   }

   public static OSPControl createApp(Object var0) {
      OSPControl var1 = new OSPControl(var0);
      return var1;
   }

   static {
      NOT_EDITABLE_BACKGROUND = Color.WHITE;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static class OSPControlLoader implements XML.ObjectLoader {
      public void saveObject(XMLControl var1, Object var2) {
         OSPControl var3 = (OSPControl)var2;
         this.saveControlProperites(var1, var3);
         if (var1.getLevel() == 0) {
            var1.setValue("model", var3.model);
         }

      }

      protected void saveControlProperites(XMLControl var1, OSPControl var2) {
         Iterator var3 = var2.getPropertyNames().iterator();

         while(var3.hasNext()) {
            String var4 = (String)var3.next();
            Object var5 = var2.getObject(var4);
            if (var5.getClass() == (OSPControl.class$org$opensourcephysics$numerics$DoubleArray == null ? (OSPControl.class$org$opensourcephysics$numerics$DoubleArray = OSPControl.class$("org.opensourcephysics.numerics.DoubleArray")) : OSPControl.class$org$opensourcephysics$numerics$DoubleArray)) {
               var1.setValue(var4, ((DoubleArray)var5).getArray());
            } else if (var5.getClass() == (OSPControl.class$org$opensourcephysics$numerics$IntegerArray == null ? (OSPControl.class$org$opensourcephysics$numerics$IntegerArray = OSPControl.class$("org.opensourcephysics.numerics.IntegerArray")) : OSPControl.class$org$opensourcephysics$numerics$IntegerArray)) {
               var1.setValue(var4, ((IntegerArray)var5).getArray());
            } else if (var5.getClass() == (OSPControl.class$java$lang$Boolean == null ? (OSPControl.class$java$lang$Boolean = OSPControl.class$("java.lang.Boolean")) : OSPControl.class$java$lang$Boolean)) {
               var1.setValue(var4, (Boolean)var5);
            } else if (var5.getClass() == (OSPControl.class$java$lang$Double == null ? (OSPControl.class$java$lang$Double = OSPControl.class$("java.lang.Double")) : OSPControl.class$java$lang$Double)) {
               var1.setValue(var4, (Double)var5);
            } else if (var5.getClass() == (OSPControl.class$java$lang$Integer == null ? (OSPControl.class$java$lang$Integer = OSPControl.class$("java.lang.Integer")) : OSPControl.class$java$lang$Integer)) {
               var1.setValue(var4, (Integer)var5);
            } else if (var5.getClass().isArray()) {
               var1.setValue(var4, var5);
            } else {
               var1.setValue(var4, var5);
            }
         }

      }

      public Object createObject(XMLControl var1) {
         return new OSPControl((Object)null);
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
         XMLControl var6 = var1.getChildControl("model");
         if (var6 != null) {
            var3.model = var6.loadObject(var3.model);
         }

         return var2;
      }
   }

   class ClearMouseAdapter extends MouseAdapter {
      public void mousePressed(MouseEvent var1) {
         OSPControl.this.clearMessages();
      }

      public void mouseEntered(MouseEvent var1) {
         OSPControl.this.clearLabel.setFont(new Font(OSPControl.this.clearLabel.getFont().getFamily(), 1, 10));
         OSPControl.this.clearLabel.setText(" click here to clear messages");
      }

      public void mouseExited(MouseEvent var1) {
         OSPControl.this.clearLabel.setFont(new Font(OSPControl.this.clearLabel.getFont().getFamily(), 0, 9));
         OSPControl.this.clearLabel.setText(" clear");
      }
   }
}
