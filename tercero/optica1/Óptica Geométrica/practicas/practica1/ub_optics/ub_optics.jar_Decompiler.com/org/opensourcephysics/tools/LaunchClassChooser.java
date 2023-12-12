package org.opensourcephysics.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LaunchClassChooser extends JDialog {
   private static Pattern pattern;
   private static Matcher matcher;
   private static Map classMaps = new TreeMap();
   private JTextField searchField;
   private String defaultSearch = "";
   private String currentSearch;
   private JScrollPane scroller;
   private JList choices;
   private LaunchableClassMap classMap;
   private boolean applyChanges;
   private JButton okButton;

   public LaunchClassChooser(Component var1) {
      super(JOptionPane.getFrameForComponent(var1), true);
      this.currentSearch = this.defaultSearch;
      this.applyChanges = false;
      this.setTitle(LaunchRes.getString("ClassChooser.Frame.Title"));
      JLabel var2 = new JLabel(LaunchRes.getString("ClassChooser.Search.Label") + " ");
      this.okButton = new JButton(LaunchRes.getString("ClassChooser.Button.Accept"));
      this.okButton.setEnabled(false);
      this.okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            LaunchClassChooser.this.applyChanges = true;
            LaunchClassChooser.this.setVisible(false);
         }
      });
      JButton var3 = new JButton(LaunchRes.getString("ClassChooser.Button.Cancel"));
      var3.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            LaunchClassChooser.this.setVisible(false);
         }
      });
      this.searchField = new JTextField(this.defaultSearch);
      this.searchField.addKeyListener(new KeyAdapter() {
         public void keyReleased(KeyEvent var1) {
            Object var2 = LaunchClassChooser.this.choices.getSelectedValue();
            LaunchClassChooser.this.search();
            LaunchClassChooser.this.choices.setSelectedValue(var2, true);
         }
      });
      this.getRootPane().setDefaultButton(this.okButton);
      JPanel var4 = new JPanel();
      var4.setLayout(new BoxLayout(var4, 0));
      var4.add(var2);
      var4.add(Box.createHorizontalGlue());
      var4.add(this.searchField);
      var4.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
      JPanel var5 = new JPanel(new BorderLayout());
      var5.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      JPanel var6 = new JPanel();
      var6.setLayout(new BoxLayout(var6, 0));
      var6.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
      var6.add(Box.createHorizontalGlue());
      var6.add(this.okButton);
      var6.add(Box.createRigidArea(new Dimension(10, 0)));
      var6.add(var3);
      Container var7 = this.getContentPane();
      var7.add(var4, "North");
      var7.add(var5, "Center");
      var7.add(var6, "South");
      this.scroller = new JScrollPane();
      this.scroller.setPreferredSize(new Dimension(400, 300));
      var5.add(this.scroller, "Center");
      this.pack();
      Dimension var8 = Toolkit.getDefaultToolkit().getScreenSize();
      int var9 = (var8.width - this.getBounds().width) / 2;
      int var10 = (var8.height - this.getBounds().height) / 2;
      this.setLocation(var9, var10);
   }

   public boolean setPath(String var1) {
      String[] var2 = parsePath(var1, true);
      this.classMap = null;
      if (var2 != null && var2.length != 0) {
         String var3 = "";

         for(int var4 = 0; var4 < var2.length; ++var4) {
            if (!var3.equals("")) {
               var3 = var3 + ";";
            }

            var3 = var3 + var2[var4];
         }

         this.classMap = (LaunchableClassMap)classMaps.get(var3);
         if (this.classMap == null) {
            this.classMap = new LaunchableClassMap(var2);
            classMaps.put(var3, this.classMap);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isLoaded(String var1) {
      if (this.classMap == null) {
         return false;
      } else {
         String[] var2 = parsePath(var1, true);

         for(int var3 = 0; var3 < var2.length; ++var3) {
            if (!this.classMap.includesJar(var2[var3])) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean chooseClassFor(LaunchNode var1) {
      this.search();
      this.choices.setSelectedValue(var1.launchClassName, true);
      this.applyChanges = false;
      this.setVisible(true);
      if (!this.applyChanges) {
         return false;
      } else {
         Object var2 = this.choices.getSelectedValue();
         if (var2 == null) {
            return false;
         } else {
            String var3 = var2.toString();
            var1.launchClass = (Class)this.classMap.get(var3);
            var1.launchClassName = var3;
            return true;
         }
      }
   }

   public Class getClass(String var1) {
      return this.classMap == null ? null : this.classMap.getClass(var1);
   }

   public static Class getClass(String var0, String var1) {
      if (var0 != null && var1 != null) {
         String[] var2 = parsePath(var0, true);
         LaunchableClassMap var3 = getClassMap(var2);
         return var3.getClass(var1);
      } else {
         return null;
      }
   }

   public static ClassLoader getClassLoader(String var0) {
      if (var0 != null && !var0.equals("")) {
         String[] var1 = parsePath(var0, true);
         LaunchableClassMap var2 = getClassMap(var1);
         return var2.classLoader;
      } else {
         return null;
      }
   }

   private static LaunchableClassMap getClassMap(String[] var0) {
      String var1 = "";

      for(int var2 = 0; var2 < var0.length; ++var2) {
         if (!var1.equals("")) {
            var1 = var1 + ";";
         }

         var1 = var1 + var0[var2];
      }

      LaunchableClassMap var3 = (LaunchableClassMap)classMaps.get(var1);
      if (var3 == null) {
         var3 = new LaunchableClassMap(var0);
         classMaps.put(var1, var3);
      }

      return var3;
   }

   private void search() {
      if (this.classMap != null) {
         this.classMap.loadAllLaunchables();
         if (this.search(this.searchField.getText())) {
            this.currentSearch = this.searchField.getText();
            this.searchField.setBackground(Color.white);
         } else {
            JOptionPane.showMessageDialog(this, LaunchRes.getString("Dialog.InvalidRegex.Message") + " \"" + this.searchField.getText() + "\"", LaunchRes.getString("Dialog.InvalidRegex.Title"), 2);
            this.searchField.setText(this.currentSearch);
         }

      }
   }

   private boolean search(String var1) {
      var1 = var1.toLowerCase();
      this.okButton.setEnabled(false);

      try {
         pattern = Pattern.compile(var1);
      } catch (Exception var5) {
         return false;
      }

      ArrayList var2 = new ArrayList();
      Iterator var3 = this.classMap.keySet().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         matcher = pattern.matcher(var4.toLowerCase());
         if (matcher.find()) {
            var2.add(var4);
         }
      }

      Object[] var6 = var2.toArray();
      this.choices = new JList(var6);
      this.choices.setSelectionMode(0);
      this.choices.setFont(this.searchField.getFont());
      this.choices.addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent var1) {
            JList var2 = (JList)var1.getSource();
            LaunchClassChooser.this.okButton.setEnabled(!var2.isSelectionEmpty());
         }
      });
      this.choices.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent var1) {
            JList var2 = (JList)var1.getSource();
            if (var1.getClickCount() == 2 && !var2.isSelectionEmpty()) {
               LaunchClassChooser.this.okButton.doClick();
            }

         }
      });
      this.scroller.getViewport().setView(this.choices);
      return true;
   }

   static String[] parsePath(String var0, boolean var1) {
      ArrayList var2 = new ArrayList();
      String var3 = var0;
      int var4 = var0.indexOf(";");
      if (var4 != -1) {
         var3 = var0.substring(0, var4);
         var0 = var0.substring(var4 + 1);
      } else {
         var0 = "";
      }

      while(!var3.equals("")) {
         var3 = var3.trim();
         if (!var1 || var3.endsWith(".jar")) {
            var2.add(var3);
         }

         var4 = var0.indexOf(";");
         if (var4 == -1) {
            var3 = var0;
            var0 = "";
         } else {
            var3 = var0.substring(0, var4);
            var0 = var0.substring(var4 + 1);
         }
      }

      return (String[])((String[])var2.toArray(new String[0]));
   }
}
