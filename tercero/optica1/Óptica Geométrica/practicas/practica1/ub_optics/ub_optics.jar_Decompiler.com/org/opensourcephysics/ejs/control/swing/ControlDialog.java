package org.opensourcephysics.ejs.control.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JDialog;
import org.opensourcephysics.ejs.control.value.BooleanValue;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlDialog extends ControlWindow {
   protected JDialog dialog;
   private static ArrayList infoList = null;

   public ControlDialog(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      return this.createDialog(var1, (Frame)null);
   }

   public void replaceVisual(Frame var1) {
      super.myVisual = this.createDialog((Object)null, var1);
   }

   private Component createDialog(Object var1, Frame var2) {
      super.startingup = true;
      if (var1 instanceof JDialog) {
         this.dialog = (JDialog)var1;
      } else {
         if (var2 != null) {
            this.dialog = new JDialog(var2);
         } else {
            this.dialog = new JDialog();
         }

         this.dialog.getContentPane().setLayout(new BorderLayout());
      }

      super.internalValue = new BooleanValue(true);
      this.dialog.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent var1) {
            ControlDialog.super.internalValue.value = false;
            ControlDialog.this.variableChanged(9, ControlDialog.super.internalValue);
         }
      });
      return this.dialog.getContentPane();
   }

   public Component getComponent() {
      return this.dialog;
   }

   public Container getContainer() {
      return this.dialog.getContentPane();
   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("title");
         infoList.add("resizable");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("title")) {
         return "String";
      } else {
         return var1.equals("resizable") ? "boolean BASIC" : super.getPropertyInfo(var1);
      }
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         String var3 = this.getProperty("_ejs_window_");
         if (var3 != null) {
            this.dialog.setTitle(var2.getString() + " " + var3);
         } else {
            this.dialog.setTitle(var2.getString());
         }
         break;
      case 1:
         this.dialog.setResizable(var2.getBoolean());
         break;
      default:
         super.setValue(var1 - 2, var2);
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         String var2 = this.getProperty("_ejs_window_");
         if (var2 != null) {
            this.dialog.setTitle(var2);
         } else {
            this.dialog.setTitle("");
         }
         break;
      case 1:
         this.dialog.setResizable(true);
         break;
      default:
         super.setDefaultValue(var1 - 2);
      }

   }

   public Value getValue(int var1) {
      switch(var1) {
      case 0:
      case 1:
         return null;
      default:
         return super.getValue(var1 - 2);
      }
   }
}
