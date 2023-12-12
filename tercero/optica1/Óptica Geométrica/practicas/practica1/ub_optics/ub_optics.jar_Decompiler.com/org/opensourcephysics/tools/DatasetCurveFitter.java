package org.opensourcephysics.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractCellEditor;
import javax.swing.AbstractSpinnerModel;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.FunctionDrawer;
import org.opensourcephysics.numerics.PolynomialLeastSquareFit;

public class DatasetCurveFitter extends JPanel {
   Dataset dataset;
   KnownFunction fit;
   FunctionDrawer drawer;
   Color color;
   JCheckBox autofitCheckBox;
   String[] fitNames;
   JComboBox fitDropDown;
   JTextField equation;
   JToolBar toolbar;
   JTable paramTable;
   TableCellRenderer cellRenderer;
   DatasetCurveFitter.SpinCellEditor spinCellEditor;
   Map namedFits;

   public DatasetCurveFitter(Dataset var1) {
      this.color = Color.red;
      this.toolbar = new JToolBar();
      this.namedFits = new HashMap();
      this.createGUI();
      this.setData(var1);
   }

   public Drawable getDrawer() {
      return this.drawer;
   }

   public Dataset getData() {
      return this.dataset;
   }

   public void setData(Dataset var1) {
      this.dataset = var1;
      this.fit();
   }

   public void setColor(Color var1) {
      this.color = var1;
      if (this.drawer != null) {
         this.drawer.setColor(var1);
      }

   }

   public void fit() {
      if (this.drawer == null) {
         this.createFit();
      }

      if (this.autofitCheckBox.isSelected()) {
         if (this.fit instanceof DatasetCurveFitter.KnownPolynomial) {
            DatasetCurveFitter.KnownPolynomial var1 = (DatasetCurveFitter.KnownPolynomial)this.fit;
            var1.fitData(this.dataset.getValidXPoints(), this.dataset.getValidYPoints());
         }

         this.drawer.functionChanged = true;
         this.paramTable.repaint();
      }

      this.firePropertyChange("fit", (Object)null, (Object)null);
   }

   protected void createGUI() {
      this.setLayout(new BorderLayout());
      this.autofitCheckBox = new JCheckBox(ToolsRes.getString("Checkbox.Autofit.Label"), true);
      this.autofitCheckBox.setOpaque(false);
      this.autofitCheckBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DatasetCurveFitter.this.fit();
         }
      });
      this.fitNames = new String[]{ToolsRes.getString("Function.Poly1.Name"), ToolsRes.getString("Function.Poly2.Name"), ToolsRes.getString("Function.Poly.Name") + " 3", ToolsRes.getString("Function.Poly.Name") + " 4"};
      this.fitDropDown = new JComboBox(this.fitNames);
      this.fitDropDown.setMaximumSize(this.fitDropDown.getMinimumSize());
      this.fitDropDown.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DatasetCurveFitter.this.createFit();
         }
      });
      this.equation = new JTextField();
      this.equation.setEditable(false);
      this.equation.setEnabled(true);
      this.equation.setBackground(Color.white);
      this.toolbar = new JToolBar();
      this.toolbar.setFloatable(false);
      this.add(this.toolbar, "North");
      this.cellRenderer = new DatasetCurveFitter.ParamCellRenderer();
      this.spinCellEditor = new DatasetCurveFitter.SpinCellEditor();
      this.paramTable = new DatasetCurveFitter.ParamTable(new DatasetCurveFitter.ParamTableModel());
      this.paramTable.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent var1) {
            if (DatasetCurveFitter.this.paramTable.getSelectedColumn() == 0) {
               DatasetCurveFitter.this.paramTable.clearSelection();
            }

         }
      });
      this.add(new JScrollPane(this.paramTable), "Center");
   }

   protected void createFit() {
      String var1 = (String)this.fitDropDown.getSelectedItem();
      this.fit = (KnownFunction)this.namedFits.get(var1);
      if (this.fit == null) {
         for(int var2 = 0; var2 < this.fitNames.length; ++var2) {
            if (this.fitNames[var2].equals(var1)) {
               switch(var2) {
               case 1:
                  this.fit = new DatasetCurveFitter.KnownPolynomial(new double[]{0.0D, 0.0D, 0.0D});
                  break;
               case 2:
                  this.fit = new DatasetCurveFitter.KnownPolynomial(new double[]{0.0D, 0.0D, 0.0D, 0.0D});
                  break;
               case 3:
                  this.fit = new DatasetCurveFitter.KnownPolynomial(new double[]{0.0D, 0.0D, 0.0D, 0.0D, 0.0D});
                  break;
               default:
                  this.fit = new DatasetCurveFitter.KnownPolynomial(new double[]{0.0D, 0.0D});
               }

               this.namedFits.put(var1, this.fit);
            }
         }
      }

      this.toolbar.removeAll();
      this.toolbar.add(this.fitDropDown);
      this.toolbar.addSeparator();
      this.toolbar.add(this.equation);
      this.toolbar.add(this.autofitCheckBox);
      FunctionDrawer var5 = this.drawer;
      this.drawer = new FunctionDrawer(this.fit);
      this.drawer.setColor(this.color);
      this.paramTable.tableChanged((TableModelEvent)null);
      String var3 = this.dataset.getColumnName(1);
      String var4 = this.dataset.getColumnName(0);
      this.equation.setText(var3 + " = " + this.fit.getEquation(var4));
      this.firePropertyChange("drawer", var5, this.drawer);
      this.fit();
   }

   class NumberField extends JTextField {
      protected NumberFormat format = NumberFormat.getInstance();
      protected double prevValue;

      public NumberField(int var2) {
         super(var2);
         if (this.format instanceof DecimalFormat) {
            ((DecimalFormat)this.format).applyPattern("0.000E0");
         }

         this.setForeground(Color.black);
      }

      public double getValue() {
         if (this.getText().equals(this.format.format(this.prevValue))) {
            return this.prevValue;
         } else {
            try {
               double var1 = this.format.parse(this.getText()).doubleValue();
               return var1;
            } catch (ParseException var4) {
               Toolkit.getDefaultToolkit().beep();
               this.setValue(this.prevValue);
               return this.prevValue;
            }
         }
      }

      public void setValue(double var1) {
         if (this.isVisible()) {
            this.setText(this.format.format(var1));
            this.prevValue = var1;
         }
      }
   }

   class KnownPolynomial extends PolynomialLeastSquareFit implements KnownFunction {
      String[] names = new String[]{"a", "b", "c", "d", "e", "f"};

      KnownPolynomial(double[] var2, double[] var3, int var4) {
         super(var2, var3, var4);
      }

      KnownPolynomial(double[] var2) {
         super(var2);
      }

      public int getParameterCount() {
         return super.coefficients.length;
      }

      public String getParameterName(int var1) {
         return this.names[var1];
      }

      public double getParameterValue(int var1) {
         return super.coefficients[super.coefficients.length - var1 - 1];
      }

      public void setParameterValue(int var1, double var2) {
         super.coefficients[super.coefficients.length - var1 - 1] = var2;
      }

      public String getEquation(String var1) {
         StringBuffer var2 = new StringBuffer();
         int var3 = super.coefficients.length - 1;

         for(int var4 = 0; var4 <= var3; ++var4) {
            var2.append(this.getParameterName(var4));
            if (var3 - var4 > 0) {
               var2.append("*");
               var2.append(var1);
               if (var3 - var4 > 1) {
                  var2.append("^");
                  var2.append(var3 - var4);
               }

               var2.append(" + ");
            }
         }

         return var2.toString();
      }
   }

   class SpinnerNumberCrawlerModel extends AbstractSpinnerModel {
      double val = 0.0D;
      double delta;
      double percentDelta = 10.0D;

      public SpinnerNumberCrawlerModel(double var2) {
         this.delta = var2;
      }

      public Object getValue() {
         return new Double(this.val);
      }

      public Object getNextValue() {
         return new Double(this.val + this.delta);
      }

      public Object getPreviousValue() {
         return new Double(this.val - this.delta);
      }

      public void setValue(Object var1) {
         if (var1 != null) {
            this.val = (Double)var1;
            this.fireStateChanged();
         }

      }

      public void setPercentDelta(double var1) {
         this.percentDelta = var1;
      }

      public double getPercentDelta() {
         return this.percentDelta;
      }

      public void refreshDelta() {
         if (this.val != 0.0D) {
            this.delta = Math.abs(this.val * this.percentDelta / 100.0D);
         }

      }
   }

   class SpinCellEditor extends AbstractCellEditor implements TableCellEditor {
      JPanel panel = new JPanel(new BorderLayout());
      DatasetCurveFitter.SpinnerNumberCrawlerModel crawlerModel = DatasetCurveFitter.this.new SpinnerNumberCrawlerModel(1.0D);
      JSpinner spinner;
      DatasetCurveFitter.NumberField field;
      int index;

      SpinCellEditor() {
         this.panel.setOpaque(false);
         this.spinner = new JSpinner(this.crawlerModel);
         this.spinner.setToolTipText(ToolsRes.getString("Table.Spinner.ToolTip"));
         this.spinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent var1) {
               DatasetCurveFitter.this.autofitCheckBox.setSelected(false);
               double var2 = (Double)SpinCellEditor.this.spinner.getValue();
               SpinCellEditor.this.field.setValue(var2);
               DatasetCurveFitter.this.fit.setParameterValue(SpinCellEditor.this.index, var2);
               DatasetCurveFitter.this.drawer.functionChanged = true;
               DatasetCurveFitter.this.firePropertyChange("fit", (Object)null, (Object)null);
            }
         });
         this.field = DatasetCurveFitter.this.new NumberField(10);
         this.spinner.setEditor(this.field);
         this.field.addMouseListener(new MouseInputAdapter() {
            public void mousePressed(MouseEvent var1) {
               short var2 = 4096;
               if (var1.isPopupTrigger() || (var1.getModifiersEx() & var2) == var2 || var1.isControlDown()) {
                  JPopupMenu var3 = new JPopupMenu();
                  ActionListener var4 = new ActionListener() {
                     public void actionPerformed(ActionEvent var1) {
                        double var2 = Double.parseDouble(var1.getActionCommand());
                        SpinCellEditor.this.crawlerModel.setPercentDelta(var2);
                        SpinCellEditor.this.crawlerModel.refreshDelta();
                     }
                  };
                  ButtonGroup var5 = new ButtonGroup();
                  JMenu var6 = new JMenu(ToolsRes.getString("Menu.StepSize"));
                  var3.add(var6);
                  double var7 = 10.0D;

                  for(int var9 = 0; var9 < 3; ++var9) {
                     String var10 = String.valueOf(var7);
                     JRadioButtonMenuItem var11 = new JRadioButtonMenuItem(var10 + "%");
                     var11.setActionCommand(var10);
                     var11.addActionListener(var4);
                     var6.add(var11);
                     var5.add(var11);
                     if (var7 == SpinCellEditor.this.crawlerModel.getPercentDelta()) {
                        var11.setSelected(true);
                     }

                     var7 /= 10.0D;
                  }

                  var3.show(SpinCellEditor.this.field, var1.getX(), var1.getY());
               }

            }
         });
         this.field.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent var1) {
               JComponent var2 = (JComponent)var1.getSource();
               if (var1.getKeyCode() == 10) {
                  SpinCellEditor.this.spinner.setValue(new Double(SpinCellEditor.this.field.getValue()));
                  var2.setBackground(Color.white);
                  SpinCellEditor.this.crawlerModel.refreshDelta();
               } else {
                  var2.setBackground(Color.yellow);
               }

            }
         });
         this.panel.add(this.spinner, "Center");
      }

      public Component getTableCellEditorComponent(JTable var1, Object var2, boolean var3, int var4, int var5) {
         this.spinner.setValue(var2);
         this.crawlerModel.refreshDelta();
         return this.panel;
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
         if (this.field.getBackground() == Color.yellow) {
            DatasetCurveFitter.this.fit.setParameterValue(this.index, this.field.getValue());
            DatasetCurveFitter.this.drawer.functionChanged = true;
            DatasetCurveFitter.this.firePropertyChange("fit", (Object)null, (Object)null);
            this.field.setBackground(Color.white);
         }

         return null;
      }
   }

   class ParamCellRenderer extends JLabel implements TableCellRenderer {
      Color lightBlue = new Color(204, 204, 255);
      Color lightGray = UIManager.getColor("Panel.background");
      Font labelFont = this.getFont();
      Font fieldFont = (new JTextField()).getFont();

      public ParamCellRenderer() {
         this.setOpaque(true);
         this.setBorder(BorderFactory.createEmptyBorder(2, 1, 2, 2));
      }

      public Component getTableCellRendererComponent(JTable var1, Object var2, boolean var3, boolean var4, int var5, int var6) {
         this.setHorizontalAlignment(var6 == 0 ? 4 : 2);
         if (var2 instanceof String) {
            this.setFont(this.labelFont);
            this.setBackground(this.lightGray);
            this.setForeground(Color.black);
            this.setText(var2.toString());
         } else {
            this.setFont(this.fieldFont);
            this.setBackground(var3 ? this.lightBlue : Color.white);
            this.setForeground(var3 ? Color.red : Color.black);
            NumberFormat var7 = DatasetCurveFitter.this.spinCellEditor.field.format;
            this.setText(var7.format(var2));
         }

         return this;
      }
   }

   class ParamTableModel extends AbstractTableModel {
      public String getColumnName(int var1) {
         return var1 == 0 ? ToolsRes.getString("Table.Heading.Parameter") : ToolsRes.getString("Table.Heading.Value");
      }

      public int getRowCount() {
         return DatasetCurveFitter.this.fit == null ? 0 : DatasetCurveFitter.this.fit.getParameterCount();
      }

      public int getColumnCount() {
         return 2;
      }

      public Object getValueAt(int var1, int var2) {
         return var2 == 0 ? DatasetCurveFitter.this.fit.getParameterName(var1) : new Double(DatasetCurveFitter.this.fit.getParameterValue(var1));
      }

      public boolean isCellEditable(int var1, int var2) {
         return var2 != 0;
      }

      public Class getColumnClass(int var1) {
         return this.getValueAt(0, var1).getClass();
      }
   }

   class ParamTable extends JTable {
      public ParamTable(DatasetCurveFitter.ParamTableModel var2) {
         super(var2);
         this.setPreferredScrollableViewportSize(new Dimension(60, 50));
         this.setGridColor(Color.blue);
         JTableHeader var3 = this.getTableHeader();
         var3.setForeground(Color.blue);
      }

      public TableCellRenderer getCellRenderer(int var1, int var2) {
         return DatasetCurveFitter.this.cellRenderer;
      }

      public TableCellEditor getCellEditor(int var1, int var2) {
         DatasetCurveFitter.this.spinCellEditor.index = var1;
         return DatasetCurveFitter.this.spinCellEditor;
      }
   }
}
