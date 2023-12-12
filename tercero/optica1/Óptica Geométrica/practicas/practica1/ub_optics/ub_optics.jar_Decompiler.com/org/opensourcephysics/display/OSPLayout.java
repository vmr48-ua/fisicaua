package org.opensourcephysics.display;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.ArrayList;

public class OSPLayout extends BorderLayout {
   static int macOffset;
   ArrayList list;
   public static final String TOP_LEFT_CORNER = "TopLeftCorner";
   public static final String TOP_RIGHT_CORNER = "TopRightCorner";
   public static final String BOTTOM_LEFT_CORNER = "BottomLeftCorner";
   public static final String BOTTOM_RIGHT_CORNER = "BottomRightCorner";
   public static final String CENTERED = "Centered";
   Component topLeftCorner;
   Component topRightCorner;
   Component bottomLeftCorner;
   Component bottomRightCorner;
   Component centeredComp;
   Rectangle layoutRect;
   Component[] components;

   public OSPLayout() {
      this(0, 0);
   }

   public OSPLayout(int var1, int var2) {
      super(var1, var2);
      this.list = new ArrayList();
      this.layoutRect = new Rectangle(0, 0, 0, 0);
      this.components = new Component[0];
   }

   public void addLayoutComponent(Component var1, Object var2) {
      if (!this.list.contains(var1)) {
         this.list.add(var1);
         this.components = (Component[])((Component[])this.list.toArray(new Component[0]));
      }

      synchronized(var1.getTreeLock()) {
         if (var2 instanceof String && "TopLeftCorner".equals(var2)) {
            this.list.remove(this.topLeftCorner);
            this.topLeftCorner = var1;
         } else if (var2 instanceof String && "TopRightCorner".equals(var2)) {
            this.list.remove(this.topRightCorner);
            this.topRightCorner = var1;
         } else if (var2 instanceof String && "BottomLeftCorner".equals(var2)) {
            this.list.remove(this.bottomLeftCorner);
            this.bottomLeftCorner = var1;
         } else if (var2 instanceof String && "BottomRightCorner".equals(var2)) {
            this.list.remove(this.bottomRightCorner);
            this.bottomRightCorner = var1;
         } else if (var2 instanceof String && "Centered".equals(var2)) {
            this.list.remove(this.centeredComp);
            this.centeredComp = var1;
         } else {
            super.addLayoutComponent(var1, var2);
         }

      }
   }

   public void removeLayoutComponent(Component var1) {
      if (this.list.contains(var1)) {
         this.list.remove(var1);
         this.components = (Component[])((Component[])this.list.toArray(new Component[0]));
      }

      synchronized(var1.getTreeLock()) {
         if (var1 == this.topLeftCorner) {
            this.topLeftCorner = null;
         } else if (var1 == this.topRightCorner) {
            this.topRightCorner = null;
         } else if (var1 == this.bottomLeftCorner) {
            this.bottomLeftCorner = null;
         } else if (var1 == this.bottomRightCorner) {
            this.bottomRightCorner = null;
         } else if (var1 == this.centeredComp) {
            this.centeredComp = null;
         } else {
            super.removeLayoutComponent(var1);
         }

      }
   }

   public void layoutContainer(Container var1) {
      super.layoutContainer(var1);
      this.doMyLayout(var1);
   }

   public boolean quickLayout(Container var1, Component var2) {
      if (var1 != null && var2 != null) {
         Insets var3 = var1.getInsets();
         int var4 = var3.top;
         int var5 = var1.getHeight() - var3.bottom;
         int var6 = var3.left;
         int var7 = var1.getWidth() - var3.right;
         Dimension var8;
         if (this.topLeftCorner == var2) {
            var8 = var2.getPreferredSize();
            var2.setSize(var8.width, var8.height);
            var2.setBounds(var6, var4, var8.width, var8.height);
         } else if (this.topRightCorner == var2) {
            var8 = var2.getPreferredSize();
            var2.setSize(var8.width, var8.height);
            var2.setBounds(var7 - var8.width, var4, var8.width, var8.height);
         } else if (this.bottomLeftCorner == var2) {
            var8 = var2.getPreferredSize();
            var2.setSize(var8.width, var8.height);
            var2.setBounds(var6, var5 - var8.height, var8.width, var8.height);
         } else if (this.bottomRightCorner == var2) {
            var8 = var2.getPreferredSize();
            var2.setSize(var8.width, var8.height);
            var2.setBounds(var7 - var8.width - macOffset, var5 - var8.height, var8.width, var8.height);
         } else {
            if (this.centeredComp != var2) {
               return false;
            }

            var8 = var2.getPreferredSize();
            var2.setSize(var8.width, var8.height);
            var2.setBounds((var7 - var6 - var8.width) / 2, (var5 - var4 - var8.height) / 2, var8.width, var8.height);
         }

         return true;
      } else {
         return false;
      }
   }

   public void checkLayoutRect(Container var1, Rectangle var2) {
      if (!this.layoutRect.equals(var2)) {
         this.layoutContainer(var1);
      }
   }

   public Component[] getComponents() {
      return this.components;
   }

   void doMyLayout(Container var1) {
      Insets var2 = var1.getInsets();
      int var3 = var2.top;
      int var4 = var1.getHeight() - var2.bottom;
      int var5 = var2.left;
      int var6 = var1.getWidth() - var2.right;
      Component var7 = null;
      Dimension var8;
      if (this.topLeftCorner != null) {
         var7 = this.topLeftCorner;
         var8 = var7.getPreferredSize();
         var7.setSize(var8.width, var8.height);
         var7.setBounds(var5, var3, var8.width, var8.height);
      }

      if (this.topRightCorner != null) {
         var7 = this.topRightCorner;
         var8 = var7.getPreferredSize();
         var7.setSize(var8.width, var8.height);
         var7.setBounds(var6 - var8.width, var3, var8.width, var8.height);
      }

      if (this.bottomLeftCorner != null) {
         var7 = this.bottomLeftCorner;
         var8 = var7.getPreferredSize();
         var7.setSize(var8.width, var8.height);
         var7.setBounds(var5, var4 - var8.height, var8.width, var8.height);
      }

      if (this.bottomRightCorner != null) {
         var7 = this.bottomRightCorner;
         var8 = var7.getPreferredSize();
         var7.setSize(var8.width + macOffset, var8.height);
         var7.setBounds(var6 - var8.width - macOffset, var4 - var8.height, var8.width, var8.height);
      }

      if (this.centeredComp != null) {
         var7 = this.centeredComp;
         var8 = var7.getPreferredSize();
         var7.setSize(var8.width, var8.height);
         var7.setBounds((var6 - var5 - var8.width) / 2, (var4 - var3 - var8.height) / 2, var8.width, var8.height);
      }

   }

   static {
      try {
         macOffset = "Mac OS X".equals(System.getProperty("os.name")) ? 16 : 0;
      } catch (SecurityException var1) {
      }

   }
}
