package org.opensourcephysics.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ListChooser extends JDialog {
   private JPanel checkPane;
   private Object[] objects;
   private boolean[] selections;
   private JCheckBox[] checkBoxes;
   private boolean applyChanges;

   public ListChooser(String var1, String var2) {
      this(var1, var2, (Component)null);
   }

   public ListChooser(String var1, String var2, Component var3) {
      super(JOptionPane.getFrameForComponent(var3), true);
      this.checkPane = new JPanel();
      this.applyChanges = false;
      this.setTitle(var1);
      JLabel var4 = new JLabel(" " + var2);
      JButton var5 = new JButton("Cancel");
      JButton var6 = new JButton("OK");
      JButton var7 = new JButton("Select_All");
      var5.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ListChooser.this.setVisible(false);
         }
      });
      var6.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            for(int var2 = 0; var2 < ListChooser.this.checkBoxes.length; ++var2) {
               ListChooser.this.selections[var2] = ListChooser.this.checkBoxes[var2].isSelected();
            }

            ListChooser.this.applyChanges = true;
            ListChooser.this.setVisible(false);
         }
      });
      var7.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            for(int var2 = 0; var2 < ListChooser.this.checkBoxes.length; ++var2) {
               ListChooser.this.checkBoxes[var2].setSelected(true);
            }

         }
      });
      this.getRootPane().setDefaultButton(var6);
      JPanel var8 = new JPanel();
      var8.setLayout(new BoxLayout(var8, 0));
      var8.add(var4);
      var8.add(Box.createHorizontalGlue());
      var8.add(var7);
      var8.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
      this.checkPane.setLayout(new BoxLayout(this.checkPane, 1));
      this.checkPane.setBackground(Color.white);
      JScrollPane var9 = new JScrollPane(this.checkPane);
      var9.setPreferredSize(new Dimension(250, 180));
      JPanel var10 = new JPanel(new BorderLayout());
      var10.add(var9, "Center");
      var10.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      JPanel var11 = new JPanel();
      var11.setLayout(new BoxLayout(var11, 0));
      var11.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
      var11.add(Box.createHorizontalGlue());
      var11.add(var5);
      var11.add(Box.createRigidArea(new Dimension(10, 0)));
      var11.add(var6);
      Container var12 = this.getContentPane();
      var12.add(var8, "North");
      var12.add(var10, "Center");
      var12.add(var11, "South");
      this.pack();
      Dimension var13 = Toolkit.getDefaultToolkit().getScreenSize();
      int var14 = (var13.width - this.getBounds().width) / 2;
      int var15 = (var13.height - this.getBounds().height) / 2;
      this.setLocation(var14, var15);
   }

   public boolean choose(Collection var1, Collection var2) {
      this.checkPane.removeAll();
      this.checkBoxes = new JCheckBox[var1.size()];
      this.selections = new boolean[var1.size()];
      this.objects = new Object[var1.size()];
      ArrayList var3 = new ArrayList();
      if (var2 != null) {
         var3.addAll(var2);
      }

      Iterator var4 = var1.iterator();

      int var5;
      for(var5 = 0; var4.hasNext(); ++var5) {
         this.objects[var5] = var4.next();
         this.selections[var5] = false;
         if (var3.get(var5) == null) {
            this.checkBoxes[var5] = new JCheckBox(this.objects[var5].toString());
         } else {
            this.checkBoxes[var5] = new JCheckBox(var3.get(var5).toString());
         }

         this.checkBoxes[var5].setBackground(Color.white);
         this.checkPane.add(this.checkBoxes[var5]);
      }

      this.setVisible(true);
      if (!this.applyChanges) {
         return false;
      } else {
         for(var5 = 0; var5 < this.objects.length; ++var5) {
            if (!this.selections[var5]) {
               var1.remove(this.objects[var5]);
            }
         }

         return true;
      }
   }
}
