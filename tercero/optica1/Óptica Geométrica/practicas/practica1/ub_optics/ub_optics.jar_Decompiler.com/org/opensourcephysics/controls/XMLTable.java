package org.opensourcephysics.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import org.opensourcephysics.tools.ArrayInspector;

public class XMLTable extends JTable {
   static final Color LIGHT_BLUE = new Color(204, 204, 255);
   XMLTableModel tableModel;
   XMLTable.XMLCellRenderer xmlRenderer = new XMLTable.XMLCellRenderer();
   XMLTable.XMLValueEditor valueEditor = new XMLTable.XMLValueEditor();
   Color defaultBackgroundColor;
   Map cellColors;
   // $FF: synthetic field
   static Class class$java$lang$String;

   public XMLTable(XMLControl var1) {
      this.defaultBackgroundColor = Color.white;
      this.cellColors = new HashMap();
      this.tableModel = new XMLTableModel(var1);
      this.init();
   }

   public void setEditable(boolean var1) {
      this.tableModel.editable = var1;
   }

   public boolean isEditable() {
      return this.tableModel.editable;
   }

   public void setEditable(String var1, boolean var2) {
      if (!var2) {
         this.tableModel.uneditablePropNames.add(var1);
      } else {
         this.tableModel.uneditablePropNames.remove(var1);
      }

   }

   public boolean isEditable(String var1) {
      return !this.tableModel.uneditablePropNames.contains(var1);
   }

   public boolean isCellEditable(int var1, int var2) {
      return this.tableModel.editable && this.tableModel.isCellEditable(var1, var2);
   }

   public void setFont(Font var1) {
      super.setFont(var1);
      if (this.xmlRenderer != null) {
         this.xmlRenderer.setFont(var1);
         this.valueEditor.field.setFont(var1);
         Runnable var2 = new Runnable() {
            public void run() {
               XMLTable.this.setRowHeight(XMLTable.this.getTableHeader().getHeight());
            }
         };
         SwingUtilities.invokeLater(var2);
      }

   }

   public void setBackgroundColor(String var1, Color var2) {
      this.cellColors.put(var1, var2);
   }

   public Color getBackgroundColor(String var1) {
      Color var2 = (Color)this.cellColors.get(var1);
      return var2 == null ? this.defaultBackgroundColor : var2;
   }

   public TableCellRenderer getCellRenderer(int var1, int var2) {
      return this.xmlRenderer;
   }

   public TableCellEditor getCellEditor(int var1, int var2) {
      return this.valueEditor;
   }

   public void refresh() {
      Runnable var1 = new Runnable() {
         public synchronized void run() {
            XMLTable.this.tableChanged(new TableModelEvent(XMLTable.this.tableModel, -1));
         }
      };
      if (SwingUtilities.isEventDispatchThread()) {
         var1.run();
      } else {
         SwingUtilities.invokeLater(var1);
      }

   }

   public void tableChanged(TableModelEvent var1) {
      this.firePropertyChange("tableData", (Object)null, var1);
      super.tableChanged(var1);
   }

   public void addControlListener(String var1, Object var2) {
      this.addControlListener((String)null, var1, var2);
   }

   public void addControlListener(final String var1, String var2, final Object var3) {
      Class[] var4 = new Class[]{class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String};

      try {
         final Method var5 = var3.getClass().getMethod(var2, var4);
         this.tableModel.addTableModelListener(new TableModelListener() {
            final String par = var1;

            public void tableChanged(TableModelEvent var1x) {
               if (var1x.getType() == 0 && var1x.getColumn() == 1 && var1x.getFirstRow() >= 0) {
                  String var2 = XMLTable.this.getValueAt(var1x.getFirstRow(), 0).toString();
                  if (this.par == null || this.par.equals(var2)) {
                     Object[] var3x = new Object[]{var2};

                     try {
                        var5.invoke(var3, var3x);
                     } catch (IllegalAccessException var5x) {
                     } catch (InvocationTargetException var6) {
                     }
                  }

               }
            }
         });
      } catch (NoSuchMethodException var6) {
         System.err.println("The method " + var2 + "() does not exist.");
      }

   }

   private void init() {
      this.setModel(this.tableModel);
      JTableHeader var1 = this.getTableHeader();
      var1.setReorderingAllowed(false);
      var1.setForeground(Color.BLACK);
      this.setGridColor(Color.BLACK);
      InputMap var2 = this.getInputMap(1);
      KeyStroke var3 = KeyStroke.getKeyStroke(9, 0);
      final Action var4 = this.getActionMap().get(var2.get(var3));
      AbstractAction var5 = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            var4.actionPerformed(var1);
            JTable var2 = (JTable)var1.getSource();
            int var3 = var2.getRowCount();
            int var4x = var2.getSelectedRow();
            int var5 = var2.getSelectedColumn();

            while(!var2.isCellEditable(var4x, var5)) {
               if (var5 == 0) {
                  var5 = 1;
               } else {
                  ++var4x;
               }

               if (var4x == var3) {
                  var4x = 0;
               }

               if (var4x == var2.getSelectedRow() && var5 == var2.getSelectedColumn()) {
                  break;
               }
            }

            var2.changeSelection(var4x, var5, false, false);
         }
      };
      this.getActionMap().put(var2.get(var3), var5);
      AbstractAction var6 = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            JTable var2 = (JTable)var1.getSource();
            int var3 = var2.getSelectedRow();
            int var4 = var2.getSelectedColumn();
            var2.editCellAt(var3, var4, var1);
            Component var5 = var2.getEditorComponent();
            if (var5 instanceof JPanel) {
               JPanel var6 = (JPanel)var5;
               var5 = var6.getComponent(0);
               if (var5 instanceof JTextField) {
                  JTextField var7 = (JTextField)var5;
                  var7.requestFocus();
                  var7.selectAll();
               }
            }

         }
      };
      KeyStroke var7 = KeyStroke.getKeyStroke(10, 0);
      this.getActionMap().put(var2.get(var7), var6);
   }

   private boolean isInspectable(XMLProperty var1) {
      if (var1.getPropertyType().equals("object")) {
         return true;
      } else {
         return var1.getPropertyType().equals("array") ? ArrayInspector.canInspect(var1) : false;
      }
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   class XMLValueEditor extends AbstractCellEditor implements TableCellEditor {
      JPanel panel = new JPanel(new BorderLayout());
      JTextField field = new JTextField();
      Object editObject;

      XMLValueEditor() {
         this.panel.add(this.field, "Center");
         this.panel.setOpaque(false);
         this.panel.setBorder(BorderFactory.createEmptyBorder(0, 1, 1, 2));
         this.field.setBorder((Border)null);
         this.field.setEditable(true);
         this.field.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               XMLValueEditor.this.stopCellEditing();
            }
         });
         this.field.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent var1) {
               if (var1.getKeyCode() == 10) {
                  XMLValueEditor.this.stopCellEditing();
               } else {
                  XMLValueEditor.this.field.setBackground(Color.yellow);
               }

            }
         });
         this.field.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent var1) {
               if (XMLValueEditor.this.field.getBackground() != XMLTable.this.defaultBackgroundColor) {
                  XMLValueEditor.this.stopCellEditing();
               }

               XMLTable.this.requestFocusInWindow();
            }
         });
      }

      public Component getTableCellEditorComponent(JTable var1, Object var2, boolean var3, final int var4, final int var5) {
         this.editObject = null;
         if (var2 instanceof XMLControl) {
            XMLControl var18 = (XMLControl)var2;
            XMLTableInspector var19 = new XMLTableInspector(var18, XMLTable.this.isEditable());
            Container var20 = XMLTable.this.getTopLevelAncestor();
            Point var21 = var20.getLocationOnScreen();
            var19.setLocation(var21.x + 30, var21.y + 30);
            var19.setVisible(true);
            return null;
         } else if (!(var2 instanceof XMLProperty)) {
            this.field.setText(var2.toString());
            return this.panel;
         } else {
            XMLProperty var6 = (XMLProperty)var2;
            XMLProperty var7 = var6.getParentProperty();
            ArrayInspector var8 = ArrayInspector.getInspector(var7);
            if (var8 != null) {
               final String var9 = var7.getPropertyName();

               XMLProperty var10;
               for(var10 = var7.getParentProperty(); !(var10 instanceof XMLControl); var10 = var10.getParentProperty()) {
                  var9 = var10.getPropertyName();
               }

               final XMLControl var11 = (XMLControl)var10;
               final Object var13 = var8.getArray();
               var8.setEditable(XMLTable.this.tableModel.editable);
               var8.addPropertyChangeListener(new PropertyChangeListener() {
                  public void propertyChange(PropertyChangeEvent var1) {
                     if (var1.getPropertyName().equals("cell")) {
                        var11.setValue(var9, var13);
                     } else if (var1.getPropertyName().equals("arrayData")) {
                        XMLTable.this.tableModel.fireTableCellUpdated(var4, var5);
                     }

                  }
               });
               Container var16 = XMLTable.this.getTopLevelAncestor();
               Point var17 = var16.getLocationOnScreen();
               var8.setLocation(var17.x + 30, var17.y + 30);
               var8.setVisible(true);
               var16.transferFocus();
            }

            return null;
         }
      }

      public boolean isCellEditable(EventObject var1) {
         if (var1 instanceof MouseEvent) {
            MouseEvent var2 = (MouseEvent)var1;
            if (var2.getClickCount() == 2) {
               return true;
            }
         } else if (var1 instanceof ActionEvent) {
            return true;
         }

         return false;
      }

      public Object getCellEditorValue() {
         if (this.field.getBackground() != XMLTable.this.defaultBackgroundColor) {
            this.field.setBackground(XMLTable.this.defaultBackgroundColor);
            return this.field.getText();
         } else {
            return this.editObject;
         }
      }
   }

   class XMLCellRenderer extends DefaultTableCellRenderer {
      Color lightGreen = new Color(204, 255, 204);
      Color lightGray = UIManager.getColor("Panel.background");
      Font font = (new JTextField()).getFont();

      public XMLCellRenderer() {
         this.setOpaque(true);
         this.setForeground(Color.black);
         this.setFont(this.font);
      }

      public Component getTableCellRendererComponent(JTable var1, Object var2, boolean var3, boolean var4, int var5, int var6) {
         this.setForeground(Color.BLACK);
         if (var2 == null) {
            var2 = "";
         }

         if (var6 == 0) {
            this.setBackground(this.lightGray);
            this.setHorizontalAlignment(2);
            this.setText(var2.toString());
            this.setBorder(BorderFactory.createEmptyBorder(2, 1, 2, 2));
         } else if (var2 instanceof XMLProperty) {
            XMLProperty var7 = (XMLProperty)var2;
            XMLProperty var8 = var7.getParentProperty();
            String var9 = XML.getSimpleClassName(var8.getPropertyClass());
            if (var8.getPropertyType().equals("array")) {
               XMLControl var10 = (XMLControl)var8.getParentProperty();
               Object var11 = var10.getObject(var8.getPropertyName());
               Class var12 = var11.getClass().getComponentType();
               int var13 = Array.getLength(var11);
               int var14 = var9.indexOf("[]") + 1;

               for(var9 = var9.substring(0, var14) + var13 + var9.substring(var14); var12.getComponentType() != null; var9 = var9.substring(0, var14) + var13 + var9.substring(var14)) {
                  var12 = var12.getComponentType();
                  var11 = Array.get(var11, 0);
                  if (var11 == null) {
                     break;
                  }

                  var13 = Array.getLength(var11);
                  var14 = var9.indexOf("[]", var14) + 1;
               }
            }

            this.setText(var9);
            this.setBackground(XMLTable.this.isInspectable(var8) ? this.lightGreen : this.lightGray);
            this.setBorder(BorderFactory.createEmptyBorder(2, 1, 2, 2));
            this.setHorizontalAlignment(0);
            if (var3 && XMLTable.this.isInspectable(var8)) {
               this.setBackground(XMLTable.LIGHT_BLUE);
               this.setForeground(Color.RED);
            }
         } else {
            String var15 = (String)XMLTable.this.tableModel.getValueAt(var5, 0);
            if (var3) {
               this.setBackground(XMLTable.LIGHT_BLUE);
               this.setForeground(Color.RED);
            } else {
               Color var16 = (Color)XMLTable.this.cellColors.get(var15);
               this.setBackground(var16 == null ? XMLTable.this.defaultBackgroundColor : var16);
            }

            this.setHorizontalAlignment(2);
            this.setText(var2.toString());
            this.setBorder(BorderFactory.createEmptyBorder(2, 1, 2, 2));
            if (!XMLTable.this.tableModel.editable || XMLTable.this.tableModel.uneditablePropNames.contains(var15)) {
               this.setForeground(Color.GRAY);
            }
         }

         return this;
      }
   }
}
