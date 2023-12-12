package org.opensourcephysics.display.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import org.opensourcephysics.display.DrawingPanel;

public class DrawingPanelInspector extends JDialog {
   static DrawingPanelInspector inspector;
   DrawingPanel drawingPanel;
   DecimalFormat format;
   JPanel panel1;
   BorderLayout borderLayout1;
   JTabbedPane jTabbedPane1;
   JPanel scalePanel;
   JPanel contentPanel;
   BorderLayout borderLayout3;
   JTextPane contentTextPane;
   JTextField ymaxField;
   JPanel yminmaxpanel;
   JTextField yminField;
   JLabel jLabel4;
   JLabel jLabel3;
   JPanel jPanel3;
   JCheckBox zoomenableBox;
   JCheckBox autoscaleyBox;
   JCheckBox autoscalexBox;
   JTextField xmaxField;
   JTextField xminField;
   JLabel jLabel5;
   JLabel jLabel6;
   JPanel xminmaxpanel;
   JPanel jPanel1;
   JButton applyButton;
   JButton cancelButton;
   JButton okButton;
   JPanel jPanel4;
   JButton measureButton;
   JButton snapButton;
   BoxLayout scaleLayout;

   public DrawingPanelInspector(Frame var1, String var2, boolean var3) {
      super(var1, var2, var3);
      this.format = new DecimalFormat("0.00000E00");
      this.panel1 = new JPanel();
      this.borderLayout1 = new BorderLayout();
      this.jTabbedPane1 = new JTabbedPane();
      this.scalePanel = new JPanel();
      this.contentPanel = new JPanel();
      this.borderLayout3 = new BorderLayout();
      this.contentTextPane = new JTextPane();
      this.ymaxField = new JTextField();
      this.yminmaxpanel = new JPanel();
      this.yminField = new JTextField();
      this.jLabel4 = new JLabel();
      this.jLabel3 = new JLabel();
      this.jPanel3 = new JPanel();
      this.zoomenableBox = new JCheckBox();
      this.autoscaleyBox = new JCheckBox();
      this.autoscalexBox = new JCheckBox();
      this.xmaxField = new JTextField();
      this.xminField = new JTextField();
      this.jLabel5 = new JLabel();
      this.jLabel6 = new JLabel();
      this.xminmaxpanel = new JPanel();
      this.jPanel1 = new JPanel();
      this.applyButton = new JButton();
      this.cancelButton = new JButton();
      this.okButton = new JButton();
      this.jPanel4 = new JPanel();
      this.measureButton = new JButton();
      this.snapButton = new JButton();
      this.scaleLayout = new BoxLayout(this.scalePanel, 1);

      try {
         this.jbInit();
         this.pack();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      this.setVisible(true);
   }

   public DrawingPanelInspector(DrawingPanel var1) {
      this((Frame)null, "Drawing Panel Inspector", false);
      this.drawingPanel = var1;
      this.getValues();
      this.getContent();
   }

   public DrawingPanelInspector() {
      this((Frame)null, "", false);
   }

   void getValues() {
      this.xminField.setText("" + this.format.format((double)((float)this.drawingPanel.getXMin())));
      this.xmaxField.setText("" + this.format.format((double)((float)this.drawingPanel.getXMax())));
      this.yminField.setText("" + this.format.format((double)((float)this.drawingPanel.getYMin())));
      this.ymaxField.setText("" + this.format.format((double)((float)this.drawingPanel.getYMax())));
      this.zoomenableBox.setSelected(this.drawingPanel.isZoom());
      this.autoscalexBox.setSelected(this.drawingPanel.isAutoscaleX());
      this.autoscaleyBox.setSelected(this.drawingPanel.isAutoscaleY());
   }

   void getContent() {
      Iterator var1 = this.drawingPanel.getDrawables().iterator();
      StringBuffer var2 = new StringBuffer();

      while(var1.hasNext()) {
         Object var3 = var1.next();
         var2.append(var3.toString());
         var2.append('\n');
      }

      this.contentTextPane.setText(var2.toString());
   }

   void applyValues() {
      double var1 = this.drawingPanel.getXMin();

      try {
         var1 = Double.parseDouble(this.xminField.getText());
      } catch (Exception var13) {
      }

      double var3 = this.drawingPanel.getXMax();

      try {
         var3 = Double.parseDouble(this.xmaxField.getText());
      } catch (Exception var12) {
      }

      double var5 = this.drawingPanel.getYMin();

      try {
         var5 = Double.parseDouble(this.yminField.getText());
      } catch (Exception var11) {
      }

      double var7 = this.drawingPanel.getYMax();

      try {
         var7 = Double.parseDouble(this.ymaxField.getText());
      } catch (Exception var10) {
      }

      this.drawingPanel.setAutoscaleX(this.autoscalexBox.isSelected());
      this.drawingPanel.setAutoscaleY(this.autoscaleyBox.isSelected());
      this.drawingPanel.setZoom(this.zoomenableBox.isSelected());
      if (!this.drawingPanel.isAutoscaleX() && !this.drawingPanel.isAutoscaleY()) {
         this.drawingPanel.setPreferredMinMax(var1, var3, var5, var7);
      } else if (!this.drawingPanel.isAutoscaleX()) {
         this.drawingPanel.setPreferredMinMaxX(var1, var3);
      } else if (!this.drawingPanel.isAutoscaleY()) {
         this.drawingPanel.setPreferredMinMaxY(var5, var7);
      }

      this.drawingPanel.scale();
      this.getValues();
      this.drawingPanel.repaint();
   }

   void applyButton_actionPerformed(ActionEvent var1) {
      this.applyValues();
   }

   void measureButton_actionPerformed(ActionEvent var1) {
      this.drawingPanel.measure();
      this.getValues();
      this.drawingPanel.repaint();
   }

   void snapButton_actionPerformed(ActionEvent var1) {
      this.drawingPanel.snapshot();
   }

   void cancelButton_actionPerformed(ActionEvent var1) {
      this.setVisible(false);
   }

   void okButton_actionPerformed(ActionEvent var1) {
      this.applyValues();
      this.setVisible(false);
   }

   void jbInit() throws Exception {
      this.panel1.setLayout(this.borderLayout1);
      this.scalePanel.setToolTipText("Set the drawing panel scale.");
      this.scalePanel.setLayout(this.scaleLayout);
      this.contentPanel.setLayout(this.borderLayout3);
      this.contentTextPane.setText("jTextPane1");
      this.ymaxField.setPreferredSize(new Dimension(100, 21));
      this.ymaxField.setText("0");
      this.yminField.setPreferredSize(new Dimension(100, 21));
      this.yminField.setText("0");
      this.jLabel4.setText("  ymax =");
      this.jLabel3.setText("ymin =");
      this.zoomenableBox.setText("enable zoom");
      this.autoscaleyBox.setText("autoscale y");
      this.autoscalexBox.setText("autoscale x");
      this.xmaxField.setText("0");
      this.xmaxField.setPreferredSize(new Dimension(100, 21));
      this.xminField.setText("0");
      this.xminField.setPreferredSize(new Dimension(100, 21));
      this.jLabel5.setText("  xmax =");
      this.jLabel6.setText("xmin =");
      this.xminmaxpanel.setToolTipText("Set the drawing panel scale.");
      this.applyButton.setText("Apply");
      this.applyButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DrawingPanelInspector.this.applyButton_actionPerformed(var1);
         }
      });
      this.cancelButton.setText("Cancel");
      this.cancelButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DrawingPanelInspector.this.cancelButton_actionPerformed(var1);
         }
      });
      this.okButton.setText("OK");
      this.okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DrawingPanelInspector.this.okButton_actionPerformed(var1);
         }
      });
      this.measureButton.setFont(new Font("Dialog", 0, 10));
      this.measureButton.setText("Measure");
      this.measureButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DrawingPanelInspector.this.measureButton_actionPerformed(var1);
         }
      });
      this.snapButton.setFont(new Font("Dialog", 0, 10));
      this.snapButton.setText("Snapshot");
      this.snapButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DrawingPanelInspector.this.snapButton_actionPerformed(var1);
         }
      });
      this.getContentPane().add(this.panel1);
      this.panel1.add(this.jTabbedPane1, "Center");
      this.jPanel3.add(this.zoomenableBox, (Object)null);
      this.jPanel3.add(this.autoscalexBox, (Object)null);
      this.jPanel3.add(this.autoscaleyBox, (Object)null);
      this.xminmaxpanel.add(this.jLabel6, (Object)null);
      this.xminmaxpanel.add(this.xminField, (Object)null);
      this.xminmaxpanel.add(this.jLabel5, (Object)null);
      this.xminmaxpanel.add(this.xmaxField, (Object)null);
      this.scalePanel.add(this.xminmaxpanel, (Object)null);
      this.scalePanel.add(this.yminmaxpanel, (Object)null);
      this.yminmaxpanel.add(this.jLabel3, (Object)null);
      this.yminmaxpanel.add(this.yminField, (Object)null);
      this.yminmaxpanel.add(this.jLabel4, (Object)null);
      this.yminmaxpanel.add(this.ymaxField, (Object)null);
      this.scalePanel.add(this.jPanel4, (Object)null);
      this.jPanel4.add(this.measureButton, (Object)null);
      this.jPanel4.add(this.snapButton, (Object)null);
      this.scalePanel.add(this.jPanel3, (Object)null);
      this.jTabbedPane1.add(this.scalePanel, "scale");
      this.jTabbedPane1.add(this.contentPanel, "content");
      this.contentPanel.add(this.contentTextPane, "Center");
      this.scalePanel.add(this.jPanel1, (Object)null);
      this.jPanel1.add(this.okButton, (Object)null);
      this.jPanel1.add(this.cancelButton, (Object)null);
      this.jPanel1.add(this.applyButton, (Object)null);
   }

   public static DrawingPanelInspector getInspector(DrawingPanel var0) {
      if (inspector == null) {
         inspector = new DrawingPanelInspector(var0);
      } else {
         inspector.drawingPanel = var0;
         inspector.getValues();
         inspector.getContent();
         inspector.setVisible(true);
      }

      return inspector;
   }

   public static void hideInspector() {
      if (inspector != null) {
         inspector.setVisible(false);
      }

   }

   public static void updateValues(DrawingPanel var0) {
      if (inspector != null && inspector.drawingPanel == var0 && inspector.isShowing()) {
         inspector.drawingPanel.scale();
         inspector.getValues();
      }
   }
}
