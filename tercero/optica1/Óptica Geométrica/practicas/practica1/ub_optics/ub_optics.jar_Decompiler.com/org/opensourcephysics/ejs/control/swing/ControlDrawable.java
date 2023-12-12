package org.opensourcephysics.ejs.control.swing;

import java.util.ArrayList;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.ejs.control.ControlElement;
import org.opensourcephysics.ejs.control.value.Value;

public abstract class ControlDrawable extends ControlElement {
   public static final int NAME = 0;
   public static final int PARENT = 1;
   protected ControlDrawablesParent myParent;
   private Drawable myDrawable = null;
   private static ArrayList infoList = null;

   public ControlDrawable(Object var1) {
      super(var1);
      this.myDrawable = this.createDrawable(var1);
      super.myObject = this.myDrawable;
   }

   protected abstract Drawable createDrawable(Object var1);

   public final Drawable getDrawable() {
      return this.myDrawable;
   }

   public final void setDrawable(Drawable var1) {
      this.myDrawable = var1;
   }

   public void setParent(ControlDrawablesParent var1) {
      if (this.myParent != null) {
         ((DrawingPanel)this.myParent.getVisual()).removeDrawable(this.myDrawable);
         if (this instanceof NeedsPreUpdate) {
            this.myParent.removeFromPreupdateList((NeedsPreUpdate)this);
         }
      }

      if (var1 != null) {
         ((DrawingPanel)var1.getVisual()).addDrawable(this.myDrawable);
         ((DrawingPanel)var1.getVisual()).render();
         if (this instanceof NeedsPreUpdate) {
            var1.addToPreupdateList((NeedsPreUpdate)this);
         }

         this.myParent = var1;
      }

   }

   public final ControlDrawablesParent getParent() {
      return this.myParent;
   }

   public void destroy() {
      super.destroy();
      if (this.myParent != null) {
         ((DrawingPanel)this.myParent.getVisual()).render();
      }

   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("name");
         infoList.add("parent");
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("name")) {
         return "String         CONSTANT HIDDEN";
      } else {
         return var1.equals("parent") ? "ControlElement CONSTANT HIDDEN" : null;
      }
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         super.setValue(0, var2);
         break;
      case 1:
         ControlElement var3 = super.myGroup.getElement(this.getProperty("parent"));
         if (var3 != null) {
            this.setParent((ControlDrawablesParent)null);
         }

         var3 = super.myGroup.getElement(var2.toString());
         if (var3 == null) {
            System.err.println(this.getClass().getName() + " : Error! Parent <" + var2 + "> not found for " + this.toString());
         } else if (var3 instanceof ControlDrawablesParent) {
            this.setParent((ControlDrawablesParent)var3);
         } else {
            System.err.println(this.getClass().getName() + " : Error! Parent <" + var2 + "> is not a ControlDrawablesParent");
         }
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         super.setDefaultValue(0);
         break;
      case 1:
         ControlElement var2 = super.myGroup.getElement(this.getProperty("parent"));
         if (var2 != null) {
            this.setParent((ControlDrawablesParent)null);
         }
      }

   }

   public Value getValue(int var1) {
      switch(var1) {
      default:
         return null;
      }
   }
}
