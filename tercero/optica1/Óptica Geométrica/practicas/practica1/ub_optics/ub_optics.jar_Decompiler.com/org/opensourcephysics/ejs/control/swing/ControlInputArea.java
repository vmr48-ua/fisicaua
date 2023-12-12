package org.opensourcephysics.ejs.control.swing;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import org.opensourcephysics.controls.ParsableTextArea;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlInputArea extends ControlSwingElement {
   protected ParsableTextArea inputarea;
   private JScrollPane pane;
   private TitledBorder titledBorder;
   private EtchedBorder etchedBorder;
   private static ArrayList infoList = null;

   public ControlInputArea(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      if (var1 instanceof ParsableTextArea) {
         this.inputarea = (ParsableTextArea)var1;
      } else {
         this.inputarea = new ParsableTextArea();
         this.inputarea.setEditable(true);
      }

      this.pane = new JScrollPane(this.inputarea);
      this.etchedBorder = new EtchedBorder(1);
      this.titledBorder = new TitledBorder(this.etchedBorder, "");
      this.titledBorder.setTitleJustification(2);
      this.pane.setBorder(this.etchedBorder);
      return this.inputarea;
   }

   public Component getComponent() {
      return this.pane;
   }

   public void reset() {
      this.inputarea.setText("");
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
         if (this.titledBorder.getTitle() == var2.getString()) {
            return;
         }

         this.titledBorder.setTitle(var2.getString());
         this.pane.setBorder(this.titledBorder);
         this.pane.repaint();
         break;
      default:
         super.setValue(var1 - 1, var2);
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         this.pane.setBorder(this.etchedBorder);
         this.pane.repaint();
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
}
