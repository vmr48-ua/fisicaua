package org.opensourcephysics.ejs.control.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.opensourcephysics.ejs.control.value.BooleanValue;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlFrame extends ControlWindow {
   private static final int NAME = 7;
   protected JFrame frame;
   private static ArrayList infoList = null;

   public ControlFrame(Object var1) {
      super(var1);
   }

   protected Component createVisual(Object var1) {
      super.startingup = true;
      if (var1 instanceof JFrame) {
         this.frame = (JFrame)var1;
      } else {
         this.frame = new JFrame();
         this.frame.getContentPane().setLayout(new BorderLayout());
      }

      this.frame.setDefaultCloseOperation(1);
      super.internalValue = new BooleanValue(true);
      this.frame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent var1) {
            ControlFrame.super.internalValue.value = false;
            ControlFrame.this.variableChanged(11, ControlFrame.super.internalValue);
            if (ControlFrame.this.frame.getDefaultCloseOperation() == 3) {
               ControlFrame.this.invokeActions();
            }

         }
      });
      return this.frame.getContentPane();
   }

   public Component getComponent() {
      return this.frame;
   }

   public Container getContainer() {
      return this.frame.getContentPane();
   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("title");
         infoList.add("resizable");
         infoList.add("exit");
         infoList.add("onExit");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("title")) {
         return "String";
      } else if (var1.equals("resizable")) {
         return "boolean BASIC";
      } else if (var1.equals("exit")) {
         return "boolean CONSTANT HIDDEN";
      } else {
         return var1.equals("onExit") ? "Action CONSTANT HIDDEN" : super.getPropertyInfo(var1);
      }
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         String var3 = this.getProperty("_ejs_window_");
         if (var3 != null) {
            this.frame.setTitle(var2.getString() + " " + var3);
         } else {
            this.frame.setTitle(var2.getString());
         }
         break;
      case 1:
         this.frame.setResizable(var2.getBoolean());
         break;
      case 2:
         if (this.getProperty("_ejs_") == null) {
            if (var2.getBoolean()) {
               this.frame.setDefaultCloseOperation(3);
            } else {
               this.frame.setDefaultCloseOperation(1);
            }
         }
         break;
      case 3:
         this.removeAction(0, this.getProperty("onExit"));
         this.addAction(0, var2.getString());
         break;
      case 4:
      case 5:
      case 6:
      default:
         super.setValue(var1 - 4, var2);
         break;
      case 7:
         super.setValue(3, var2);
         if (this.getGroup() != null && this.getGroup().getOwnerFrame() == this.getComponent()) {
            String var4 = this.getGroup().getReplaceOwnerName();
            if (var4 != null && var4.equals(var2.getString())) {
               this.getGroup().setOwnerFrame(this.getGroup().getReplaceOwnerFrame());
            } else {
               this.getGroup().setOwnerFrame(this.frame);
            }
         }
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         String var2 = this.getProperty("_ejs_window_");
         if (var2 != null) {
            this.frame.setTitle(var2);
         } else {
            this.frame.setTitle("");
         }
         break;
      case 1:
         this.frame.setResizable(true);
         break;
      case 2:
         if (this.getProperty("_ejs_") == null) {
            this.frame.setDefaultCloseOperation(1);
         }
         break;
      case 3:
         this.removeAction(0, this.getProperty("onExit"));
         break;
      case 7:
         super.setDefaultValue(3);
         if (this.getGroup() != null && this.getGroup().getOwnerFrame() == this.getComponent()) {
            this.getGroup().setOwnerFrame(this.frame);
         }
      case 4:
      case 5:
      case 6:
      default:
         super.setDefaultValue(var1 - 4);
      }

   }

   public Value getValue(int var1) {
      switch(var1) {
      case 0:
      case 1:
      case 2:
      case 3:
         return null;
      default:
         return super.getValue(var1 - 4);
      }
   }
}
