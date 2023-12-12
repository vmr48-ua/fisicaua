package org.opensourcephysics.ejs.control.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.ArrayList;
import javax.swing.JComponent;
import org.opensourcephysics.ejs.control.NeedsUpdate;
import org.opensourcephysics.ejs.control.value.BooleanValue;
import org.opensourcephysics.ejs.control.value.StringValue;
import org.opensourcephysics.ejs.control.value.Value;

public abstract class ControlWindow extends ControlContainer implements NeedsUpdate {
   public static final int NAME = 3;
   public static final int VISIBLE = 7;
   private static final int SIZE = 8;
   protected BooleanValue internalValue;
   private LayoutManager myLayout = null;
   private Point myLocation = null;
   private Dimension mySize = null;
   protected boolean waitForReset = false;
   protected boolean startingup = true;
   protected boolean shouldShow = true;
   private static ArrayList infoList = null;

   public ControlWindow(Object var1) {
      super(var1);
   }

   public void dispose() {
      ((Window)this.getComponent()).dispose();
   }

   public void show() {
      if (this.startingup) {
         this.shouldShow = true;
         if (this.waitForReset) {
            return;
         }
      }

      Window var1 = (Window)this.getComponent();
      if (!var1.isShowing()) {
         var1.setVisible(true);
      }

   }

   public void hide() {
      if (this.startingup) {
         this.shouldShow = false;
         if (this.waitForReset) {
            return;
         }
      }

      Window var1 = (Window)this.getComponent();
      if (var1.isShowing()) {
         var1.setVisible(false);
      }

   }

   public void destroy() {
      this.dispose();
      super.destroy();
   }

   public void setWaitForReset(boolean var1) {
      this.waitForReset = var1;
      if (this.waitForReset) {
         ((Window)this.getComponent()).setVisible(false);
      }

   }

   public void reset() {
      this.startingup = false;
      if (this.shouldShow) {
         this.show();
      } else {
         this.hide();
      }

      super.reset();
   }

   public void update() {
      this.startingup = false;
   }

   public void adjustSize() {
      String var1 = this.getProperty("size");
      ((Window)this.getComponent()).validate();
      if (var1 != null && var1.trim().toLowerCase().equals("pack")) {
         ((Window)this.getComponent()).pack();
      } else {
         super.adjustSize();
      }

   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("layout");
         infoList.add("location");
         infoList.add("waitForReset");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("location")) {
         return "Point|Object";
      } else if (var1.equals("layout")) {
         return "Layout|Object";
      } else if (var1.equals("waitForReset")) {
         return "boolean HIDDEN";
      } else {
         return var1.equals("tooltip") ? "String HIDDEN" : super.getPropertyInfo(var1);
      }
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         if (var2.getObject() instanceof LayoutManager) {
            LayoutManager var7 = (LayoutManager)var2.getObject();
            if (var7 != this.myLayout) {
               this.getContainer().setLayout(this.myLayout = var7);
            }

            ((Container)this.getComponent()).validate();
         }
         break;
      case 1:
         if (var2.getObject() instanceof Point) {
            Point var6 = (Point)var2.getObject();
            if (var6.equals(this.myLocation)) {
               return;
            }

            this.getComponent().setLocation(this.myLocation = var6);
         }
         break;
      case 2:
         this.setWaitForReset(var2.getBoolean());
         break;
      case 3:
      case 4:
      case 5:
      case 6:
      default:
         super.setValue(var1 - 3, var2);
         break;
      case 7:
         this.internalValue.value = var2.getBoolean();
         if (this.internalValue.value) {
            this.show();
         } else {
            this.hide();
         }
         break;
      case 8:
         Dimension var3 = null;
         if (var2 instanceof StringValue && "pack".equals(var2.getString())) {
            ((Window)this.getComponent()).pack();
            var3 = this.getComponent().getSize();
         } else {
            if (!(var2.getObject() instanceof Dimension)) {
               return;
            }

            var3 = (Dimension)var2.getObject();
            if (var3.equals(this.mySize)) {
               return;
            }

            ((JComponent)this.getContainer()).setPreferredSize(this.mySize = var3);
            ((Container)this.getComponent()).validate();
            ((Window)this.getComponent()).pack();
         }

         String var4 = this.getProperty("location");
         if (var4 != null && var4.trim().toLowerCase().equals("center")) {
            Dimension var5 = Toolkit.getDefaultToolkit().getScreenSize();
            this.getComponent().setLocation((var5.width - var3.width) / 2, (var5.height - var3.height) / 2);
         }
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         this.getContainer().setLayout(this.myLayout = new BorderLayout());
         ((Container)this.getComponent()).validate();
         break;
      case 1:
         this.getComponent().setLocation(this.myLocation = new Point(0, 0));
         break;
      case 2:
         this.setWaitForReset(false);
         break;
      case 3:
      case 4:
      case 5:
      case 6:
      default:
         super.setDefaultValue(var1 - 3);
         break;
      case 7:
         this.internalValue.value = true;
         this.show();
         break;
      case 8:
         ((Window)this.getComponent()).pack();
         Dimension var2 = this.getComponent().getSize();
         String var3 = this.getProperty("location");
         if (var3 != null && var3.trim().toLowerCase().equals("center")) {
            Dimension var4 = Toolkit.getDefaultToolkit().getScreenSize();
            this.getComponent().setLocation((var4.width - var2.width) / 2, (var4.height - var2.height) / 2);
         }
      }

   }

   public Value getValue(int var1) {
      switch(var1) {
      case 0:
         return this.internalValue;
      case 1:
      case 2:
         return null;
      default:
         return super.getValue(var1 - 3);
      }
   }
}
