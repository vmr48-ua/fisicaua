package org.opensourcephysics.display.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.opensourcephysics.display.DrawingPanel;

public class ScaleInspector extends JDialog {
   protected DrawingPanel plotPanel;
   protected JPanel dataPanel;
   protected JLabel xMinLabel;
   protected JLabel xMaxLabel;
   protected JLabel yMinLabel;
   protected JLabel yMaxLabel;
   protected DecimalField xMinField;
   protected DecimalField xMaxField;
   protected DecimalField yMinField;
   protected DecimalField yMaxField;
   protected JCheckBox xAutoscaleCheckBox;
   protected JCheckBox yAutoscaleCheckBox;
   protected JButton okButton;
   protected JButton cancelButton;

   public ScaleInspector(DrawingPanel var1) {
      super((Frame)null, true);
      this.plotPanel = var1;
      this.setTitle(DialogsRes.SCALE_SCALE);
      this.setResizable(false);
      this.createGUI();
      this.pack();
   }

   private void createGUI() {
      JPanel var1 = new JPanel(new BorderLayout());
      this.setContentPane(var1);
      JPanel var2 = new JPanel(new BorderLayout());
      var1.add(var2, "South");
      this.xMinLabel = new JLabel(DialogsRes.SCALE_MIN);
      this.xMinField = new DecimalField(5, 1);
      this.xMinField.setMaximumSize(this.xMinField.getPreferredSize());
      this.xMinField.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            double var2 = ScaleInspector.this.plotPanel.getPreferredXMax();
            ScaleInspector.this.plotPanel.setPreferredMinMaxX(ScaleInspector.this.xMinField.getValue(), var2);
            ScaleInspector.this.plotPanel.repaint();
            ScaleInspector.this.updateDisplay();
            ScaleInspector.this.xMinField.selectAll();
         }
      });
      this.xMinField.addFocusListener(new FocusListener() {
         public void focusGained(FocusEvent var1) {
            ScaleInspector.this.xMinField.selectAll();
         }

         public void focusLost(FocusEvent var1) {
            double var2 = ScaleInspector.this.plotPanel.getPreferredXMax();
            ScaleInspector.this.plotPanel.setPreferredMinMaxX(ScaleInspector.this.xMinField.getValue(), var2);
            ScaleInspector.this.plotPanel.repaint();
            ScaleInspector.this.updateDisplay();
         }
      });
      this.xAutoscaleCheckBox = new JCheckBox(DialogsRes.SCALE_AUTO);
      this.xAutoscaleCheckBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ScaleInspector.this.xMinField.setEnabled(!ScaleInspector.this.xAutoscaleCheckBox.isSelected());
            ScaleInspector.this.xMaxField.setEnabled(!ScaleInspector.this.xAutoscaleCheckBox.isSelected());
            ScaleInspector.this.plotPanel.setAutoscaleX(ScaleInspector.this.xAutoscaleCheckBox.isSelected());
            ScaleInspector.this.plotPanel.scale();
            ScaleInspector.this.updateDisplay();
            ScaleInspector.this.plotPanel.repaint();
         }
      });
      this.xMaxLabel = new JLabel(DialogsRes.SCALE_MAX);
      this.xMaxField = new DecimalField(5, 1);
      this.xMaxField.setMaximumSize(this.xMaxField.getPreferredSize());
      this.xMaxField.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            double var2 = ScaleInspector.this.plotPanel.getPreferredXMin();
            ScaleInspector.this.plotPanel.setPreferredMinMaxX(var2, ScaleInspector.this.xMaxField.getValue());
            ScaleInspector.this.plotPanel.repaint();
            ScaleInspector.this.updateDisplay();
            ScaleInspector.this.xMaxField.selectAll();
         }
      });
      this.xMaxField.addFocusListener(new FocusListener() {
         public void focusGained(FocusEvent var1) {
            ScaleInspector.this.xMaxField.selectAll();
         }

         public void focusLost(FocusEvent var1) {
            double var2 = ScaleInspector.this.plotPanel.getPreferredXMin();
            ScaleInspector.this.plotPanel.setPreferredMinMaxX(var2, ScaleInspector.this.xMaxField.getValue());
            ScaleInspector.this.plotPanel.repaint();
            ScaleInspector.this.updateDisplay();
         }
      });
      this.yMinLabel = new JLabel(DialogsRes.SCALE_MIN);
      this.yMinField = new DecimalField(5, 1);
      this.yMinField.setMaximumSize(this.yMinField.getPreferredSize());
      this.yMinField.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            double var2 = ScaleInspector.this.plotPanel.getPreferredYMax();
            ScaleInspector.this.plotPanel.setPreferredMinMaxY(ScaleInspector.this.yMinField.getValue(), var2);
            ScaleInspector.this.plotPanel.repaint();
            ScaleInspector.this.updateDisplay();
            ScaleInspector.this.yMinField.selectAll();
         }
      });
      this.yMinField.addFocusListener(new FocusListener() {
         public void focusGained(FocusEvent var1) {
            ScaleInspector.this.yMinField.selectAll();
         }

         public void focusLost(FocusEvent var1) {
            double var2 = ScaleInspector.this.plotPanel.getPreferredYMax();
            ScaleInspector.this.plotPanel.setPreferredMinMaxY(ScaleInspector.this.yMinField.getValue(), var2);
            ScaleInspector.this.plotPanel.repaint();
            ScaleInspector.this.updateDisplay();
         }
      });
      this.yAutoscaleCheckBox = new JCheckBox(DialogsRes.SCALE_AUTO);
      this.yAutoscaleCheckBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ScaleInspector.this.yMinField.setEnabled(!ScaleInspector.this.yAutoscaleCheckBox.isSelected());
            ScaleInspector.this.yMaxField.setEnabled(!ScaleInspector.this.yAutoscaleCheckBox.isSelected());
            ScaleInspector.this.plotPanel.setAutoscaleY(ScaleInspector.this.yAutoscaleCheckBox.isSelected());
            ScaleInspector.this.plotPanel.scale();
            ScaleInspector.this.updateDisplay();
            ScaleInspector.this.plotPanel.repaint();
         }
      });
      this.yMaxLabel = new JLabel(DialogsRes.SCALE_MAX);
      this.yMaxField = new DecimalField(5, 1);
      this.yMaxField.setMaximumSize(this.yMaxField.getPreferredSize());
      this.yMaxField.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            double var2 = ScaleInspector.this.plotPanel.getPreferredYMin();
            ScaleInspector.this.plotPanel.setPreferredMinMaxY(var2, ScaleInspector.this.yMaxField.getValue());
            ScaleInspector.this.plotPanel.repaint();
            ScaleInspector.this.updateDisplay();
            ScaleInspector.this.yMaxField.selectAll();
         }
      });
      this.yMaxField.addFocusListener(new FocusListener() {
         public void focusGained(FocusEvent var1) {
            ScaleInspector.this.yMaxField.selectAll();
         }

         public void focusLost(FocusEvent var1) {
            double var2 = ScaleInspector.this.plotPanel.getPreferredYMin();
            ScaleInspector.this.plotPanel.setPreferredMinMaxY(var2, ScaleInspector.this.yMaxField.getValue());
            ScaleInspector.this.plotPanel.repaint();
            ScaleInspector.this.updateDisplay();
         }
      });
      JPanel var3 = new JPanel(new GridLayout(3, 1));
      String var4 = "x";
      var3.setBorder(BorderFactory.createTitledBorder(var4));
      JPanel var5 = new JPanel(new GridLayout(3, 1));
      var4 = "y";
      var5.setBorder(BorderFactory.createTitledBorder(var4));
      this.dataPanel = new JPanel(new GridLayout(2, 1));
      this.dataPanel.setBorder(BorderFactory.createEtchedBorder());
      var2.add(this.dataPanel, "Center");
      Box var6 = Box.createHorizontalBox();
      var6.add(Box.createHorizontalGlue());
      var6.add(this.xMinLabel);
      var6.add(this.xMinField);
      var3.add(this.xAutoscaleCheckBox);
      var3.add(var6);
      var6 = Box.createHorizontalBox();
      var6.add(Box.createHorizontalGlue());
      var6.add(this.xMaxLabel);
      var6.add(this.xMaxField);
      var3.add(var6);
      var6 = Box.createHorizontalBox();
      var6.add(Box.createHorizontalGlue());
      var6.add(this.yMinLabel);
      var6.add(this.yMinField);
      var5.add(this.yAutoscaleCheckBox);
      var5.add(var6);
      var6 = Box.createHorizontalBox();
      var6.add(Box.createHorizontalGlue());
      var6.add(this.yMaxLabel);
      var6.add(this.yMaxField);
      var5.add(var6);
      this.dataPanel.add(var3);
      this.dataPanel.add(var5);
      this.xMinLabel.setAlignmentX(1.0F);
      this.xMaxLabel.setAlignmentX(1.0F);
      this.yMinLabel.setAlignmentX(1.0F);
      this.yMaxLabel.setAlignmentX(1.0F);
      this.xMinField.setAlignmentX(1.0F);
      this.xMaxField.setAlignmentX(1.0F);
      this.yMinField.setAlignmentX(1.0F);
      this.yMaxField.setAlignmentX(1.0F);
      this.xAutoscaleCheckBox.setAlignmentX(1.0F);
      this.yAutoscaleCheckBox.setAlignmentX(1.0F);
      this.cancelButton = new JButton(DialogsRes.SCALE_CANCEL);
      this.cancelButton.setForeground(new Color(0, 0, 102));
      this.cancelButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ScaleInspector.this.revert();
            ScaleInspector.this.setVisible(false);
         }
      });
      this.okButton = new JButton(DialogsRes.SCALE_OK);
      this.okButton.setForeground(new Color(0, 0, 102));
      this.okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ScaleInspector.this.setVisible(false);
         }
      });
      JPanel var7 = new JPanel();
      var2.add(var7, "South");
      var7.add(this.okButton);
   }

   public void updateDisplay() {
      this.xAutoscaleCheckBox.setSelected(this.plotPanel.isAutoscaleX());
      this.xMinField.setEnabled(!this.xAutoscaleCheckBox.isSelected());
      this.xMaxField.setEnabled(!this.xAutoscaleCheckBox.isSelected());
      this.xMinField.setValue(this.plotPanel.getPreferredXMin());
      this.xMaxField.setValue(this.plotPanel.getPreferredXMax());
      this.yAutoscaleCheckBox.setSelected(this.plotPanel.isAutoscaleY());
      this.yMinField.setEnabled(!this.yAutoscaleCheckBox.isSelected());
      this.yMaxField.setEnabled(!this.yAutoscaleCheckBox.isSelected());
      this.yMinField.setValue(this.plotPanel.getPreferredYMin());
      this.yMaxField.setValue(this.plotPanel.getPreferredYMax());
   }

   private void revert() {
   }
}
