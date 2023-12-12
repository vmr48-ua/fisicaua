package org.opensourcephysics.controls;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class Password extends JDialog {
   private static Password dialog;
   private JLabel messageLabel;
   private JPasswordField passwordField;
   private String password;
   private boolean pass;

   public static boolean verify(String var0, String var1) {
      if (var0 != null && !var0.equals("")) {
         if (dialog == null) {
            dialog = new Password();
         }

         dialog.password = var0;
         if (var1 != null && !var1.equals("")) {
            dialog.messageLabel.setText(ControlsRes.getString("Password.Message.File") + "\"" + XML.getName(var1) + "\".");
         } else {
            dialog.messageLabel.setText(ControlsRes.getString("Password.Message.Short"));
         }

         dialog.pack();
         Dimension var2 = Toolkit.getDefaultToolkit().getScreenSize();
         int var3 = (var2.width - dialog.getBounds().width) / 2;
         int var4 = (var2.height - dialog.getBounds().height) / 2;
         dialog.setLocation(var3, var4);
         dialog.pass = false;
         dialog.passwordField.setText("");
         dialog.setVisible(true);
         return dialog.pass;
      } else {
         return true;
      }
   }

   private Password() {
      super((Frame)null, true);
      this.setTitle(ControlsRes.getString("Password.Title"));
      this.createGUI();
      this.setResizable(false);
      this.passwordField.requestFocusInWindow();
   }

   private void createGUI() {
      GridBagLayout var1 = new GridBagLayout();
      JPanel var2 = new JPanel(var1);
      this.messageLabel = new JLabel();
      JLabel var3 = new JLabel(ControlsRes.getString("Password.Label"));
      this.passwordField = new JPasswordField(20);
      this.passwordField.setToolTipText(ControlsRes.getString("Password.Tooltip"));
      this.passwordField.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            String var2 = String.copyValueOf(Password.this.passwordField.getPassword());
            if (Password.this.password != null && !var2.equals(Password.this.password)) {
               Toolkit.getDefaultToolkit().beep();
               Password.this.passwordField.setText("");
            } else {
               Password.this.pass = true;
               Password.this.setVisible(false);
            }

         }
      });
      this.passwordField.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent var1) {
            if (var1.getKeyCode() == 27) {
               Password.this.passwordField.requestFocusInWindow();
               Password.this.setVisible(false);
            }

         }
      });
      JButton var4 = new JButton(ControlsRes.getString("Password.Button.Cancel"));
      var4.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Password.this.passwordField.requestFocusInWindow();
            Password.this.setVisible(false);
         }
      });
      JButton var5 = new JButton(ControlsRes.getString("Password.Button.Enter"));
      var5.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            String var2 = String.copyValueOf(Password.this.passwordField.getPassword());
            if (Password.this.password != null && !var2.equals(Password.this.password)) {
               Toolkit.getDefaultToolkit().beep();
               Password.this.passwordField.setText("");
               Password.this.passwordField.requestFocusInWindow();
            } else {
               Password.this.pass = true;
               Password.this.setVisible(false);
            }

         }
      });
      Container var6 = this.getContentPane();
      var6.add(var2, "Center");
      GridBagConstraints var7 = new GridBagConstraints();
      var7.insets = new Insets(20, 15, 10, 15);
      var1.setConstraints(this.messageLabel, var7);
      var2.add(this.messageLabel);
      JPanel var8 = new JPanel();
      var8.add(var3);
      var8.add(this.passwordField);
      var7.gridy = 1;
      var7.insets = new Insets(0, 10, 10, 10);
      var1.setConstraints(var8, var7);
      var2.add(var8);
      JPanel var9 = new JPanel();
      var6.add(var9, "South");
      var9.setLayout(new BoxLayout(var9, 0));
      var9.setBorder(BorderFactory.createEmptyBorder(0, 10, 4, 4));
      var9.add(Box.createHorizontalGlue());
      var9.add(var5);
      var9.add(Box.createRigidArea(new Dimension(4, 0)));
      var9.add(var4);
   }
}
