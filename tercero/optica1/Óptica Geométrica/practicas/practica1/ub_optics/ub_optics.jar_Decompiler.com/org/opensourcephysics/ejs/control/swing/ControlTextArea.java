package org.opensourcephysics.ejs.control.swing;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlTextArea extends ControlSwingElement {
   static String _RETURN_ = System.getProperty("line.separator");
   protected JTextArea textarea;
   private JScrollPane panel;
   private TitledBorder titledBorder;
   private EtchedBorder etchedBorder;
   private static ArrayList infoList = null;

   public ControlTextArea(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof JTextArea) {
         this.textarea = (JTextArea)var1;
      } else {
         this.textarea = new JTextArea(5, 5);
         this.textarea.setEditable(false);
      }

      this.panel = new JScrollPane(this.textarea);
      this.etchedBorder = new EtchedBorder(1);
      this.titledBorder = new TitledBorder(this.etchedBorder, "");
      this.titledBorder.setTitleJustification(2);
      this.panel.setBorder(this.etchedBorder);
      return this.textarea;
   }

   public Component getComponent() {
      return this.panel;
   }

   public void reset() {
      this.textarea.setText("");
   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("title");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      return var1.equals("title") ? "String" : super.getPropertyInfo(var1);
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         if (!var2.getString().equals(this.titledBorder.getTitle())) {
            this.titledBorder.setTitle(var2.getString());
            this.panel.setBorder(this.titledBorder);
            this.panel.repaint();
         }
         break;
      default:
         super.setValue(var1 - 1, var2);
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         this.panel.setBorder(this.etchedBorder);
         this.panel.repaint();
         break;
      default:
         super.setDefaultValue(var1 - 1);
      }

   }

   public Value getValue(int var1) {
      switch(var1) {
      case 0:
         return null;
      default:
         return super.getValue(var1 - 1);
      }
   }

   public void clear() {
      this.textarea.setText("");
      this.textarea.setCaretPosition(this.textarea.getText().length());
   }

   public void println(String var1) {
      this.print(var1 + _RETURN_);
   }

   public void print(String var1) {
      this.textarea.append(var1);
      this.textarea.setCaretPosition(this.textarea.getText().length());
   }
}
