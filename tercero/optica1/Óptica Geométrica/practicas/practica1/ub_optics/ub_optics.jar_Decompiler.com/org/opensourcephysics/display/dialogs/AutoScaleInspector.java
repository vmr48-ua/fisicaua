package org.opensourcephysics.display.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.opensourcephysics.display.DrawingPanel;

public class AutoScaleInspector extends JDialog {
   protected DrawingPanel plotPanel;
   protected JPanel dataPanel;
   protected JCheckBox xAutoscaleCheckBox;
   protected JCheckBox yAutoscaleCheckBox;
   protected JButton okButton;

   public AutoScaleInspector(DrawingPanel var1) {
      super((Frame)null, true);
      this.plotPanel = var1;
      this.setTitle(DialogsRes.AUTOSCALE_AUTOSCALE);
      this.setResizable(false);
      this.createGUI();
      this.pack();
   }

   private void createGUI() {
      this.xAutoscaleCheckBox = new JCheckBox(DialogsRes.AUTOSCALE_AUTO + " x");
      this.xAutoscaleCheckBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            AutoScaleInspector.this.plotPanel.setAutoscaleX(AutoScaleInspector.this.xAutoscaleCheckBox.isSelected());
            AutoScaleInspector.this.plotPanel.scale();
            AutoScaleInspector.this.updateDisplay();
            AutoScaleInspector.this.plotPanel.repaint();
         }
      });
      this.yAutoscaleCheckBox = new JCheckBox(DialogsRes.AUTOSCALE_AUTO + " y");
      this.yAutoscaleCheckBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            AutoScaleInspector.this.plotPanel.setAutoscaleY(AutoScaleInspector.this.yAutoscaleCheckBox.isSelected());
            AutoScaleInspector.this.plotPanel.scale();
            AutoScaleInspector.this.updateDisplay();
            AutoScaleInspector.this.plotPanel.repaint();
         }
      });
      JPanel var1 = new JPanel(new BorderLayout());
      this.setContentPane(var1);
      JPanel var2 = new JPanel(new BorderLayout());
      var1.add(var2, "South");
      JPanel var3 = new JPanel(new GridLayout(1, 2));
      var3.setBorder(BorderFactory.createTitledBorder(DialogsRes.AUTOSCALE_ZOOM_WARNING));
      this.dataPanel = new JPanel(new GridLayout(1, 1));
      this.dataPanel.setBorder(BorderFactory.createEtchedBorder());
      var2.add(this.dataPanel, "Center");
      Box var4 = Box.createHorizontalBox();
      var4.add(Box.createHorizontalGlue());
      var3.add(this.xAutoscaleCheckBox);
      var3.add(var4);
      var4 = Box.createHorizontalBox();
      var4.add(Box.createHorizontalGlue());
      var3.add(var4);
      var4 = Box.createHorizontalBox();
      var4.add(Box.createHorizontalGlue());
      var3.add(this.yAutoscaleCheckBox);
      this.dataPanel.add(var3);
      this.xAutoscaleCheckBox.setAlignmentX(1.0F);
      this.yAutoscaleCheckBox.setAlignmentX(1.0F);
      this.okButton = new JButton(DialogsRes.AUTOSCALE_OK);
      this.okButton.setForeground(new Color(0, 0, 102));
      this.okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            AutoScaleInspector.this.setVisible(false);
         }
      });
      JPanel var5 = new JPanel();
      var2.add(var5, "South");
      var5.add(this.okButton);
   }

   public void updateDisplay() {
      this.xAutoscaleCheckBox.setSelected(this.plotPanel.isAutoscaleX());
      this.yAutoscaleCheckBox.setSelected(this.plotPanel.isAutoscaleY());
   }

   private void revert() {
   }
}
