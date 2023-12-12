package org.opensourcephysics.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import org.opensourcephysics.controls.XMLControlElement;
import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.FunctionDrawer;
import org.opensourcephysics.display.HighlightableDataset;
import org.opensourcephysics.display.PlottingPanel;
import org.opensourcephysics.display.dialogs.ScaleInspector;

public class DatasetTab extends JPanel {
   protected final Dataset original;
   protected HighlightableDataset dataset = new HighlightableDataset();
   protected HighlightableDataset displayData = new HighlightableDataset();
   protected JSplitPane[] splitPanes;
   protected DatasetTab.Plot plot;
   protected DatasetDataTable dataTable;
   protected DatasetStatisticsTable statsTable;
   protected DatasetCurveFitter curveFitter;
   protected String[] shapeNames;
   protected int[] shapeNumbers;
   protected JSpinner shapeSpinner;
   protected JSpinner sizeSpinner;
   protected JCheckBox markerCheckBox;
   protected JCheckBox lineCheckBox;
   protected JCheckBox fitCheckBox;
   protected JCheckBox statsCheckBox;
   protected JButton markerColorButton;
   protected JButton lineColorButton;
   protected JButton fitColorButton;
   protected int buttonHeight = 28;
   protected DatasetTab.SelectionBox selectionBox = new DatasetTab.SelectionBox();
   protected Point zoomPoint;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$display$Dataset;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$display$FunctionDrawer;

   public DatasetTab(Dataset var1) {
      this.original = var1;
      XMLControlElement var2 = new XMLControlElement(this.original);
      var2.loadObject(this.dataset, true, true);
      this.createGUI();
   }

   protected Dataset getDataset() {
      this.original.setMarkerShape(this.dataset.getMarkerShape());
      this.original.setMarkerSize(this.dataset.getMarkerSize());
      this.original.setConnected(this.dataset.isConnected());
      this.original.setLineColor(this.dataset.getLineColor());
      this.original.setMarkerColor(this.dataset.getFillColor(), this.dataset.getEdgeColor());
      return this.original;
   }

   protected void updateData() {
      XMLControlElement var1 = new XMLControlElement(this.original);
      var1.loadObject(this.dataset, true, true);
      this.refresh();
      this.dataTable.refreshTable();
   }

   protected void createGUI() {
      this.setLayout(new BorderLayout());
      byte var1 = 3;
      this.splitPanes = new JSplitPane[var1];
      this.splitPanes[0] = new JSplitPane(1);
      this.splitPanes[0].setResizeWeight(1.0D);
      this.splitPanes[1] = new JSplitPane(0);
      this.splitPanes[1].setResizeWeight(1.0D);
      this.splitPanes[2] = new JSplitPane(0);
      this.shapeNames = new String[]{ToolsRes.getString("Shape.Circle"), ToolsRes.getString("Shape.Square"), ToolsRes.getString("Shape.Pixel"), ToolsRes.getString("Shape.Bar"), ToolsRes.getString("Shape.Post"), ToolsRes.getString("Shape.Area")};
      this.shapeNumbers = new int[]{1, 2, 6, 7, 8, 5};
      SpinnerListModel var2 = new SpinnerListModel(this.shapeNames);
      this.shapeSpinner = new JSpinner(var2);
      this.shapeSpinner.setToolTipText(ToolsRes.getString("Spinner.MarkerShape.ToolTip"));
      Dimension var3 = this.shapeSpinner.getPreferredSize();
      this.shapeSpinner.setMaximumSize(new Dimension(var3.width + 20, 100));
      this.shapeSpinner.setPreferredSize(new Dimension(var3.width + 10, var3.height));
      int var4 = this.dataset.getMarkerShape();

      for(int var5 = 0; var5 < this.shapeNumbers.length; ++var5) {
         if (this.shapeNumbers[var5] == var4) {
            this.shapeSpinner.setValue(this.shapeNames[var5]);
         }
      }

      this.shapeSpinner.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent var1) {
            String var2 = DatasetTab.this.shapeSpinner.getValue().toString();

            for(int var3 = 0; var3 < DatasetTab.this.shapeNames.length; ++var3) {
               if (DatasetTab.this.shapeNames[var3].equals(var2)) {
                  DatasetTab.this.dataset.setMarkerShape(DatasetTab.this.shapeNumbers[var3]);
                  DatasetTab.this.refresh();
               }
            }

         }
      });
      SpinnerNumberModel var15 = new SpinnerNumberModel(2, 1, 6, 1);
      this.sizeSpinner = new JSpinner(var15);
      this.sizeSpinner.setToolTipText(ToolsRes.getString("Spinner.MarkerSize.ToolTip"));
      var3 = this.sizeSpinner.getPreferredSize();
      this.sizeSpinner.setMaximumSize(new Dimension(var3.width, 100));
      this.sizeSpinner.setMinimumSize(new Dimension(var3.width, 100));
      this.sizeSpinner.setValue(new Integer(this.dataset.getMarkerSize()));
      this.sizeSpinner.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent var1) {
            int var2 = (Integer)DatasetTab.this.sizeSpinner.getValue();
            DatasetTab.this.dataset.setMarkerSize(var2);
            DatasetTab.this.refresh();
         }
      });
      this.markerCheckBox = new JCheckBox(ToolsRes.getString("Checkbox.Markers.Label"));
      boolean var6 = this.dataset.getMarkerShape() != 0;
      this.markerCheckBox.setToolTipText(ToolsRes.getString("Checkbox.Markers.ToolTip"));
      this.markerCheckBox.setSelected(var6);
      this.markerCheckBox.setOpaque(false);
      this.markerCheckBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            if (DatasetTab.this.markerCheckBox.isSelected()) {
               String var2 = DatasetTab.this.shapeSpinner.getValue().toString();

               for(int var3 = 0; var3 < DatasetTab.this.shapeNames.length; ++var3) {
                  if (DatasetTab.this.shapeNames[var3].equals(var2)) {
                     DatasetTab.this.dataset.setMarkerShape(DatasetTab.this.shapeNumbers[var3]);
                     break;
                  }
               }
            } else {
               DatasetTab.this.dataset.setMarkerShape(0);
            }

            DatasetTab.this.refresh();
         }
      });
      this.lineCheckBox = new JCheckBox(ToolsRes.getString("Checkbox.Lines.Label"));
      this.lineCheckBox.setSelected(this.dataset.isConnected());
      this.lineCheckBox.setToolTipText(ToolsRes.getString("Checkbox.Lines.ToolTip"));
      this.lineCheckBox.setOpaque(false);
      this.lineCheckBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DatasetTab.this.dataset.setConnected(DatasetTab.this.lineCheckBox.isSelected());
            DatasetTab.this.refresh();
         }
      });
      AbstractAction var7 = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            JButton var2 = (JButton)var1.getSource();
            String var3 = ToolsRes.getString("ColorChooser.Marker.Title");
            if (var2 == DatasetTab.this.lineColorButton) {
               var3 = ToolsRes.getString("ColorChooser.Line.Title");
            }

            if (var2 == DatasetTab.this.fitColorButton) {
               var3 = ToolsRes.getString("ColorChooser.Fit.Title");
            }

            Color var4 = JColorChooser.showDialog((Component)null, var3, var2.getBackground());
            if (var4 != null) {
               if (var2 == DatasetTab.this.markerColorButton) {
                  Color var5 = new Color(var4.getRed(), var4.getGreen(), var4.getBlue(), 100);
                  DatasetTab.this.dataset.setMarkerColor(var5, var4);
               } else if (var2 == DatasetTab.this.lineColorButton) {
                  DatasetTab.this.dataset.setLineColor(var4);
               } else if (var2 == DatasetTab.this.fitColorButton) {
                  DatasetTab.this.curveFitter.setColor(var4);
               }

               DatasetTab.this.refresh();
            }

         }
      };
      this.markerColorButton = this.createButton(ToolsRes.getString("Button.MarkerColor.Label"), this.buttonHeight);
      this.markerColorButton.setToolTipText(ToolsRes.getString("Button.MarkerColor.ToolTip"));
      this.markerColorButton.addActionListener(var7);
      this.lineColorButton = this.createButton(ToolsRes.getString("Button.LineColor.Label"), this.buttonHeight);
      this.lineColorButton.setToolTipText(ToolsRes.getString("Button.LineColor.ToolTip"));
      this.lineColorButton.addActionListener(var7);
      this.fitColorButton = this.createButton(ToolsRes.getString("Button.FitColor.Label"), this.buttonHeight);
      this.fitColorButton.setToolTipText(ToolsRes.getString("Button.FitColor.ToolTip"));
      this.fitColorButton.addActionListener(var7);
      AbstractAction var8 = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            boolean var2 = DatasetTab.this.statsCheckBox.isSelected();
            DatasetTab.this.splitPanes[2].setDividerLocation(var2 ? DatasetTab.this.statsTable.getHeight() + 4 : 0);
         }
      };
      this.statsCheckBox = new JCheckBox(ToolsRes.getString("Checkbox.Statistics.Label"), false);
      this.statsCheckBox.setOpaque(false);
      this.statsCheckBox.setToolTipText(ToolsRes.getString("Checkbox.Statistics.ToolTip"));
      this.statsCheckBox.addActionListener(var8);
      AbstractAction var9 = new AbstractAction() {
         public void actionPerformed(ActionEvent var1) {
            DatasetTab.this.splitPanes[1].setDividerSize(DatasetTab.this.splitPanes[2].getDividerSize());
            DatasetTab.this.splitPanes[1].setDividerLocation(1.0D);
            DatasetTab.this.plot.removeDrawables(DatasetTab.class$org$opensourcephysics$display$FunctionDrawer == null ? (DatasetTab.class$org$opensourcephysics$display$FunctionDrawer = DatasetTab.class$("org.opensourcephysics.display.FunctionDrawer")) : DatasetTab.class$org$opensourcephysics$display$FunctionDrawer);
            boolean var2 = DatasetTab.this.fitCheckBox.isSelected();
            DatasetTab.this.splitPanes[1].setEnabled(var2);
            if (var2) {
               int var3 = DatasetTab.this.splitPanes[1].getDividerLocation();
               int var4 = DatasetTab.this.curveFitter.getPreferredSize().height;
               DatasetTab.this.splitPanes[1].setDividerSize(DatasetTab.this.splitPanes[0].getDividerSize());
               DatasetTab.this.splitPanes[1].setDividerLocation(var3 - var4);
               DatasetTab.this.plot.addDrawable(DatasetTab.this.curveFitter.getDrawer());
            }

            DatasetTab.this.refresh();
         }
      };
      this.fitCheckBox = new JCheckBox(ToolsRes.getString("Checkbox.Fits.Label"), false);
      this.fitCheckBox.setOpaque(false);
      this.fitCheckBox.setToolTipText(ToolsRes.getString("Checkbox.Fits.ToolTip"));
      this.fitCheckBox.addActionListener(var9);
      this.plot = new DatasetTab.Plot(this.dataset);
      this.plot.addDrawable(this.dataset);
      this.plot.setTitle(this.dataset.getName());
      this.plot.addDrawable(this.selectionBox);
      MouseInputAdapter var10 = new MouseInputAdapter() {
         ArrayList rowsInside = new ArrayList();

         public void mousePressed(MouseEvent var1) {
            Point var2 = var1.getPoint();
            short var3 = 4096;
            boolean var4 = var1.isPopupTrigger() || (var1.getModifiersEx() & var3) == var3 || var1.isControlDown() && System.getProperty("os.name", "").indexOf("Mac") > -1;
            DatasetTab.this.selectionBox.xstart = var2.x;
            DatasetTab.this.selectionBox.ystart = var2.y;
            this.rowsInside.clear();
            if (var4) {
               if (DatasetTab.this.selectionBox.isZoomable()) {
                  DatasetTab.this.plot.zoomInItem.setText(ToolsRes.getString("MenuItem.ZoomToBox"));
               } else {
                  DatasetTab.this.zoomPoint = var1.getPoint();
                  DatasetTab.this.plot.zoomInItem.setText(ToolsRes.getString("MenuItem.ZoomIn"));
               }
            } else {
               DatasetTab.this.selectionBox.setSize(0, 0);
            }

            if (!var1.isControlDown() && !var1.isShiftDown() && !var4) {
               DatasetTab.this.dataTable.clearSelection();
            }

         }

         public void mouseDragged(MouseEvent var1) {
            short var2 = 4096;
            boolean var3 = (var1.getModifiersEx() & var2) == var2 || var1.isControlDown() && System.getProperty("os.name", "").indexOf("Mac") > -1;
            if (!var3) {
               HighlightableDataset var4 = DatasetTab.this.dataTable.getData();
               Point var5 = var1.getPoint();
               DatasetTab.this.selectionBox.visible = true;
               DatasetTab.this.selectionBox.setSize(var5.x - DatasetTab.this.selectionBox.xstart, var5.y - DatasetTab.this.selectionBox.ystart);
               double[] var6 = var4.getXPoints();
               double[] var7 = var4.getYPoints();

               for(int var8 = 0; var8 < var6.length; ++var8) {
                  double var9 = (double)DatasetTab.this.plot.xToPix(var6[var8]);
                  double var11 = (double)DatasetTab.this.plot.yToPix(var7[var8]);
                  Integer var13 = new Integer(var8);
                  if (DatasetTab.this.selectionBox.contains(var9, var11)) {
                     if (!this.rowsInside.contains(var13)) {
                        this.rowsInside.add(var13);
                        DatasetTab.this.dataTable.getSelectionModel().addSelectionInterval(var8, var8);
                     }
                  } else if (this.rowsInside.contains(var13)) {
                     DatasetTab.this.dataTable.getSelectionModel().removeSelectionInterval(var8, var8);
                     this.rowsInside.remove(var13);
                  }
               }

               DatasetTab.this.plot.repaint();
            }
         }

         public void mouseReleased(MouseEvent var1) {
            DatasetTab.this.plot.repaint();
         }
      };
      this.plot.addMouseListener(var10);
      this.plot.addMouseMotionListener(var10);
      JToolBar var11 = new JToolBar();
      var11.setFloatable(false);
      var11.setBorder(BorderFactory.createEtchedBorder());
      var11.addSeparator(new Dimension(4, 4));
      var11.add(this.markerCheckBox);
      var11.add(this.markerColorButton);
      var11.add(this.shapeSpinner);
      var11.add(this.sizeSpinner);
      var11.addSeparator();
      var11.add(this.lineCheckBox);
      var11.add(this.lineColorButton);
      var11.addSeparator();
      var11.add(this.fitCheckBox);
      var11.add(this.fitColorButton);
      var11.addSeparator();
      var11.add(Box.createHorizontalGlue());
      var11.add(this.statsCheckBox);
      this.curveFitter = new DatasetCurveFitter(this.dataset);
      this.curveFitter.addPropertyChangeListener(new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent var1) {
            if (var1.getPropertyName().equals("drawer")) {
               DatasetTab.this.plot.removeDrawables(DatasetTab.class$org$opensourcephysics$display$FunctionDrawer == null ? (DatasetTab.class$org$opensourcephysics$display$FunctionDrawer = DatasetTab.class$("org.opensourcephysics.display.FunctionDrawer")) : DatasetTab.class$org$opensourcephysics$display$FunctionDrawer);
               DatasetTab.this.plot.addDrawable((FunctionDrawer)var1.getNewValue());
            }

            DatasetTab.this.plot.repaint();
         }
      });
      this.dataTable = new DatasetDataTable(this.dataset);
      this.dataTable.setRowNumberVisible(true);
      this.dataTable.setColumnSelectionAllowed(false);
      JScrollPane var12 = new JScrollPane(this.dataTable);
      this.dataTable.refreshTable();
      this.dataTable.getColumnModel().addColumnModelListener(new TableColumnModelListener() {
         public void columnAdded(TableColumnModelEvent var1) {
         }

         public void columnRemoved(TableColumnModelEvent var1) {
         }

         public void columnSelectionChanged(ListSelectionEvent var1) {
         }

         public void columnMarginChanged(ChangeEvent var1) {
         }

         public void columnMoved(TableColumnModelEvent var1) {
            DatasetTab.this.selectionBox.setSize(0, 0);
            DatasetTab.this.refresh();
            String var2 = DatasetTab.this.displayData.getColumnName(1);
            String var3 = DatasetTab.this.displayData.getColumnName(0);
            DatasetTab.this.curveFitter.equation.setText(var2 + " = " + DatasetTab.this.curveFitter.fit.getEquation(var3));
         }
      });
      this.dataTable.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent var1) {
            if (DatasetTab.this.dataTable.getSelectedColumn() == 0) {
               DatasetTab.this.dataTable.removeRowSelectionInterval(0, DatasetTab.this.dataTable.getRowCount() - 1);
               DatasetTab.this.dataTable.data.clearHighlights();
            }

         }
      });
      ListSelectionModel var13 = this.dataTable.getSelectionModel();
      var13.addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent var1) {
            try {
               DatasetTab.this.curveFitter.setData(DatasetTab.this.dataTable.getWorkingData());
            } catch (Exception var3) {
            }

         }
      });
      this.statsTable = new DatasetStatisticsTable(this.dataTable);
      JScrollPane var14 = new JScrollPane(this.statsTable);
      this.add(var11, "North");
      this.add(this.splitPanes[0], "Center");
      this.splitPanes[0].setLeftComponent(this.splitPanes[1]);
      this.splitPanes[0].setRightComponent(this.splitPanes[2]);
      this.splitPanes[1].setTopComponent(this.plot);
      this.splitPanes[1].setBottomComponent(this.curveFitter);
      this.splitPanes[2].setTopComponent(var14);
      this.splitPanes[2].setBottomComponent(var12);
      this.splitPanes[0].setOneTouchExpandable(true);
      this.splitPanes[2].setDividerSize(2);
      this.splitPanes[2].setEnabled(false);
      this.splitPanes[1].setDividerSize(this.splitPanes[2].getDividerSize());
   }

   protected void init() {
      this.splitPanes[0].setDividerLocation(0.7D);
      this.splitPanes[1].setDividerLocation(1.0D);
      this.splitPanes[2].setDividerLocation(0);
   }

   protected JButton createButton(String var1, final int var2) {
      JButton var3 = new JButton(var1) {
         public Dimension getMaximumSize() {
            Dimension var1 = super.getMaximumSize();
            var1.height = var2;
            return var1;
         }
      };
      return var3;
   }

   protected void refresh() {
      this.markerColorButton.setForeground(this.dataset.getEdgeColor());
      this.markerColorButton.setEnabled(this.markerCheckBox.isSelected());
      this.shapeSpinner.setEnabled(this.markerCheckBox.isSelected());
      this.sizeSpinner.setEnabled(this.markerCheckBox.isSelected());
      this.lineColorButton.setForeground(this.dataset.getLineColor());
      this.lineColorButton.setEnabled(this.lineCheckBox.isSelected());
      this.fitColorButton.setForeground(this.curveFitter.color);
      this.fitColorButton.setEnabled(this.fitCheckBox.isSelected());
      this.dataTable.dataset.setName(this.dataset.getName());
      this.curveFitter.setData(this.dataTable.getWorkingData());
      this.plot.removeDrawables(class$org$opensourcephysics$display$Dataset == null ? (class$org$opensourcephysics$display$Dataset = class$("org.opensourcephysics.display.Dataset")) : class$org$opensourcephysics$display$Dataset);
      this.displayData = this.dataTable.getData();
      this.plot.addDrawable(this.displayData);
      if (this.fitCheckBox.isSelected()) {
         this.plot.removeDrawable(this.curveFitter.getDrawer());
         this.plot.addDrawable(this.curveFitter.getDrawer());
      }

      this.plot.setTitle(this.displayData.getName());
      this.plot.setXLabel(this.displayData.getColumnName(0));
      this.plot.setYLabel(this.displayData.getColumnName(1));
      this.repaint();
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   class Plot extends PlottingPanel {
      JMenuItem zoomInItem;

      Plot(Dataset var2) {
         super(var2.getColumnName(0), var2.getColumnName(1), "");
      }

      protected void zoomOut() {
         double var1 = super.xmax - super.xmin;
         double var3 = super.ymax - super.ymin;
         this.setPreferredMinMax(super.xmin - var1 / 2.0D, super.xmax + var1 / 2.0D, super.ymin - var3 / 2.0D, super.ymax + var3 / 2.0D);
         super.validImage = false;
         DatasetTab.this.selectionBox.setSize(0, 0);
         this.repaint();
      }

      protected void zoomIn() {
         int var1 = DatasetTab.this.selectionBox.getBounds().width;
         int var2 = DatasetTab.this.selectionBox.getBounds().height;
         double var5;
         double var7;
         double var9;
         if (DatasetTab.this.selectionBox.isZoomable()) {
            int var3 = DatasetTab.this.selectionBox.getBounds().x;
            int var4 = DatasetTab.this.selectionBox.getBounds().y;
            var5 = this.pixToX(var3);
            var7 = this.pixToX(var3 + var1);
            var9 = this.pixToY(var4);
            double var11 = this.pixToY(var4 + var2);
            this.setPreferredMinMax(var5, var7, var11, var9);
            super.validImage = false;
            DatasetTab.this.selectionBox.setSize(0, 0);
            this.repaint();
         } else if (DatasetTab.this.zoomPoint != null) {
            double var13 = super.xmax - super.xmin;
            var5 = super.ymax - super.ymin;
            var7 = this.pixToX(DatasetTab.this.zoomPoint.x);
            var9 = this.pixToY(DatasetTab.this.zoomPoint.y);
            this.setPreferredMinMax(var7 - var13 / 4.0D, var7 + var13 / 4.0D, var9 - var5 / 4.0D, var9 + var5 / 4.0D);
            super.validImage = false;
            DatasetTab.this.selectionBox.setSize(0, 0);
            this.repaint();
         }

      }

      protected void buildPopupmenu() {
         super.popupmenu.setEnabled(true);
         this.zoomInItem = new JMenuItem(ToolsRes.getString("MenuItem.ZoomIn"));
         this.zoomInItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               DatasetTab.this.plot.zoomIn();
            }
         });
         super.popupmenu.add(this.zoomInItem);
         JMenuItem var1 = new JMenuItem(ToolsRes.getString("MenuItem.ZoomOut"));
         var1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               DatasetTab.this.plot.zoomOut();
            }
         });
         super.popupmenu.add(var1);
         var1 = new JMenuItem(ToolsRes.getString("MenuItem.ZoomToFit"));
         var1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               DatasetTab.this.plot.setAutoscaleX(true);
               DatasetTab.this.plot.setAutoscaleY(true);
               DatasetTab.this.selectionBox.setSize(0, 0);
               DatasetTab.this.refresh();
            }
         });
         super.popupmenu.add(var1);
         var1 = new JMenuItem(ToolsRes.getString("MenuItem.Scale"));
         var1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               ScaleInspector var2 = new ScaleInspector(Plot.this);
               var2.setLocationRelativeTo(Plot.this);
               var2.updateDisplay();
               var2.setVisible(true);
            }
         });
         super.popupmenu.add(var1);
         super.popupmenu.addSeparator();
         var1 = new JMenuItem(ToolsRes.getString("MenuItem.Snapshot"));
         var1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               Plot.this.snapshot();
            }
         });
         super.popupmenu.add(var1);
         super.popupmenu.addSeparator();
         var1 = new JMenuItem(ToolsRes.getString("MenuItem.Inspect"));
         var1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               Plot.this.showInspector();
            }
         });
         super.popupmenu.add(var1);
      }
   }

   class SelectionBox extends Rectangle implements Drawable {
      boolean visible = true;
      int xstart;
      int ystart;
      int zoomSize = 10;
      Color color = new Color(0, 255, 0, 127);

      public void setSize(int var1, int var2) {
         int var3 = Math.min(0, var1);
         int var4 = Math.min(0, var2);
         var1 = Math.abs(var1);
         var2 = Math.abs(var2);
         super.setLocation(this.xstart + var3, this.ystart + var4);
         super.setSize(var1, var2);
      }

      public void draw(DrawingPanel var1, Graphics var2) {
         if (this.visible) {
            Graphics2D var3 = (Graphics2D)var2;
            var3.setColor(this.color);
            var3.draw(this);
         }

      }

      public boolean isZoomable() {
         return this.getBounds().width > this.zoomSize && this.getBounds().height > this.zoomSize;
      }
   }
}
