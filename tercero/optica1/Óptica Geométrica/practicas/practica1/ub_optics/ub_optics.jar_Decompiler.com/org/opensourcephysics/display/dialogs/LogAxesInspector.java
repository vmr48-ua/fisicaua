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
import org.opensourcephysics.display.PlottingPanel;

public class LogAxesInspector extends JDialog {
   protected PlottingPanel plotPanel;
   protected JPanel dataPanel;
   protected JCheckBox logXCheckBox;
   protected JCheckBox logYCheckBox;
   protected JButton okButton;

   public LogAxesInspector(PlottingPanel var1) {
      super((Frame)null, true);
      this.plotPanel = var1;
      this.setTitle(DialogsRes.LOG_SCALE);
      this.setResizable(false);
      this.createGUI();
      this.pack();
   }

   private void createGUI() {
      this.logXCheckBox = new JCheckBox(DialogsRes.LOG_X);
      this.logXCheckBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            LogAxesInspector.this.plotPanel.setLogScaleX(LogAxesInspector.this.logXCheckBox.isSelected());
            LogAxesInspector.this.plotPanel.scale();
            LogAxesInspector.this.updateDisplay();
            LogAxesInspector.this.plotPanel.repaint();
         }
      });
      this.logYCheckBox = new JCheckBox(DialogsRes.LOG_Y);
      this.logYCheckBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            LogAxesInspector.this.plotPanel.setLogScaleY(LogAxesInspector.this.logYCheckBox.isSelected());
            LogAxesInspector.this.plotPanel.scale();
            LogAxesInspector.this.updateDisplay();
            LogAxesInspector.this.plotPanel.repaint();
         }
      });
      JPanel var1 = new JPanel(new BorderLayout());
      this.setContentPane(var1);
      JPanel var2 = new JPanel(new BorderLayout());
      var1.add(var2, "South");
      JPanel var3 = new JPanel(new GridLayout(1, 2));
      var3.setBorder(BorderFactory.createTitledBorder(DialogsRes.LOG_WARNING));
      this.dataPanel = new JPanel(new GridLayout(1, 1));
      this.dataPanel.setBorder(BorderFactory.createEtchedBorder());
      var2.add(this.dataPanel, "Center");
      Box var4 = Box.createHorizontalBox();
      var4.add(Box.createHorizontalGlue());
      var3.add(this.logXCheckBox);
      var3.add(var4);
      var4 = Box.createHorizontalBox();
      var4.add(Box.createHorizontalGlue());
      var3.add(var4);
      var4 = Box.createHorizontalBox();
      var4.add(Box.createHorizontalGlue());
      var3.add(this.logYCheckBox);
      this.dataPanel.add(var3);
      this.logXCheckBox.setAlignmentX(1.0F);
      this.logYCheckBox.setAlignmentX(1.0F);
      this.okButton = new JButton(DialogsRes.LOG_OK);
      this.okButton.setForeground(new Color(0, 0, 102));
      this.okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            LogAxesInspector.this.setVisible(false);
         }
      });
      JPanel var5 = new JPanel();
      var2.add(var5, "South");
      var5.add(this.okButton);
   }

   public void updateDisplay() {
      this.logXCheckBox.setSelected(this.plotPanel.isLogScaleX());
      this.logYCheckBox.setSelected(this.plotPanel.isLogScaleY());
   }

   private void revert() {
   }
}
