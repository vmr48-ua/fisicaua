package org.opensourcephysics.controls;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class XMLTreeChooser extends JDialog {
   private JPanel scrollPane;
   private XMLTree tree;
   private JLabel textLabel;
   private boolean applyChanges;

   public XMLTreeChooser(String var1, String var2) {
      this(var1, var2, (Component)null);
   }

   public XMLTreeChooser(String var1, String var2, Component var3) {
      super(JOptionPane.getFrameForComponent(var3), true);
      this.applyChanges = false;
      this.setTitle(var1);
      this.textLabel = new JLabel(" " + var2);
      this.textLabel.setHorizontalTextPosition(2);
      JButton var4 = new JButton("Cancel");
      JButton var5 = new JButton("OK");
      JButton var6 = new JButton("Select_All");
      var4.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            XMLTreeChooser.this.setVisible(false);
         }
      });
      var5.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            XMLTreeChooser.this.applyChanges = true;
            XMLTreeChooser.this.setVisible(false);
         }
      });
      var6.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            XMLTreeChooser.this.tree.selectHighlightedProperties();
         }
      });
      this.getRootPane().setDefaultButton(var5);
      JPanel var7 = new JPanel();
      var7.setLayout(new BoxLayout(var7, 0));
      var7.add(this.textLabel);
      var7.add(Box.createHorizontalGlue());
      var7.add(var6);
      var7.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
      this.scrollPane = new JPanel(new BorderLayout());
      this.scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      JPanel var8 = new JPanel();
      var8.setLayout(new BoxLayout(var8, 0));
      var8.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
      var8.add(Box.createHorizontalGlue());
      var8.add(var4);
      var8.add(Box.createRigidArea(new Dimension(10, 0)));
      var8.add(var5);
      JPanel var9 = new JPanel(new BorderLayout());
      var9.setPreferredSize(new Dimension(340, 340));
      this.setContentPane(var9);
      var9.add(var7, "North");
      var9.add(this.scrollPane, "Center");
      var9.add(var8, "South");
      this.pack();
      Dimension var10 = Toolkit.getDefaultToolkit().getScreenSize();
      int var11 = (var10.width - this.getBounds().width) / 2;
      int var12 = (var10.height - this.getBounds().height) / 2;
      this.setLocation(var11, var12);
   }

   public List choose(XMLControl var1, Class var2) {
      ArrayList var3 = new ArrayList();
      this.tree = new XMLTree(var1);
      this.tree.setHighlightedClass(var2);
      this.tree.selectHighlightedProperties();
      this.textLabel.setIcon(XMLTree.hiliteIcon);
      this.scrollPane.removeAll();
      this.scrollPane.add(this.tree.getScrollPane(), "Center");
      this.validate();
      this.applyChanges = false;
      this.setVisible(true);
      if (this.applyChanges) {
         List var4 = this.tree.getSelectedProperties();
         Iterator var5 = var4.iterator();

         while(var5.hasNext()) {
            XMLProperty var6 = (XMLProperty)var5.next();
            Class var7 = var6.getPropertyClass();
            if (var7 != null && var2.isAssignableFrom(var7)) {
               var3.add(var6);
            }
         }
      }

      return var3;
   }
}
