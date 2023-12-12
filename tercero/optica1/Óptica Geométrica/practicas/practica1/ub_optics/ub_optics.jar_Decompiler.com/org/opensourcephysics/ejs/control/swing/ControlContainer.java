package org.opensourcephysics.ejs.control.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Vector;
import org.opensourcephysics.ejs.control.ControlElement;
import org.opensourcephysics.ejs.control.value.BooleanValue;

public abstract class ControlContainer extends ControlSwingElement {
   private static final BooleanValue falseValue = new BooleanValue(false);
   protected Vector radioButtons = new Vector();
   protected Vector children = new Vector();

   public ControlContainer(Object var1) {
      super(var1);
   }

   public Container getContainer() {
      return (Container)this.getVisual();
   }

   public void add(ControlElement var1) {
      this.children.add(var1);
      Container var2 = this.getContainer();
      LayoutManager var3 = var2.getLayout();
      String var4 = var1.getProperty("_ejs_indexInParent_");
      int var5 = -1;
      if (var4 != null) {
         var5 = Integer.parseInt(var4);
      }

      var1.setProperty("_ejs_indexInParent_", (String)null);
      if (var3 instanceof BorderLayout) {
         String var6 = var1.getProperty("position");
         if (var6 != null) {
            var2.add(var1.getComponent(), ConstantParser.constraintsConstant(var6).getString(), var5);
         } else {
            var2.add(var1.getComponent(), "Center", var5);
         }
      } else {
         var2.add(var1.getComponent(), var5);
      }

      this.adjustSize();
      if (var1 instanceof ControlRadioButton) {
         this.radioButtons.add(var1);
         ((ControlRadioButton)var1).setParent(this);
      }

      this.propagateProperty(var1, "font", this.getProperty("font"));
      this.propagateProperty(var1, "foreground", this.getProperty("foreground"));
      this.propagateProperty(var1, "background", this.getProperty("background"));
   }

   public void adjustSize() {
      this.getContainer().validate();
      this.getContainer().repaint();
      resizeContainer(this.getContainer());
      resizeContainer(this.getComponent().getParent());
   }

   private static void resizeContainer(Container var0) {
      if (var0 != null) {
         Rectangle var1 = var0.getBounds();
         var0.setBounds(var1.x, var1.y, var1.width + 1, var1.height + 1);
         var0.setBounds(var1.x, var1.y, var1.width, var1.height);
         var0.validate();
         var0.repaint();
      }
   }

   public Vector getChildren() {
      return this.children;
   }

   public void remove(ControlElement var1) {
      this.children.remove(var1);
      Container var2 = this.getContainer();
      var2.remove(var1.getComponent());
      var2.validate();
      var2.repaint();
      if (var1 instanceof ControlRadioButton) {
         this.radioButtons.remove(var1);
         ((ControlRadioButton)var1).setParent((ControlContainer)null);
      }

   }

   public void informRadioGroup(ControlRadioButton var1, boolean var2) {
      if (var2) {
         Enumeration var3 = this.radioButtons.elements();

         while(var3.hasMoreElements()) {
            ControlRadioButton var4 = (ControlRadioButton)var3.nextElement();
            if (var4 != var1) {
               boolean var5 = var4.isActive();
               var4.setActive(false);
               var4.setValue(4, falseValue);
               var4.reportChanges();
               var4.setActive(var5);
            }
         }

      }
   }

   private void propagateProperty(ControlElement var1, String var2, String var3) {
      if (var1.getProperty(var2) == null) {
         var1.setProperty(var2, var3);
      }

   }

   private void propagateProperty(String var1, String var2) {
      for(int var3 = 0; var3 < this.children.size(); ++var3) {
         this.propagateProperty((ControlElement)this.children.elementAt(var3), var1, var2);
      }

   }

   public String getPropertyInfo(String var1) {
      return var1.equals("visible") ? "boolean" : super.getPropertyInfo(var1);
   }

   public ControlElement setProperty(String var1, String var2) {
      ControlElement var3 = super.setProperty(var1, var2);
      if (var1.equals("font") || var1.equals("foreground") || var1.equals("background")) {
         this.propagateProperty(var1, var2);
      }

      return var3;
   }
}
