package org.opensourcephysics.controls;

import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

public class XMLTreeNode extends DefaultMutableTreeNode {
   protected XMLProperty prop;
   private boolean inspectable = false;

   public XMLTreeNode(XMLProperty var1) {
      this.prop = var1;
      this.setUserObject(this);
      Iterator var2 = var1.getPropertyContent().iterator();

      XMLProperty var4;
      while(var2.hasNext()) {
         Object var3 = var2.next();
         if (var3 instanceof XMLProperty) {
            var4 = (XMLProperty)var3;
            List var5 = var4.getPropertyContent();
            if (var5.size() == 1) {
               var3 = var5.get(0);
               if (var3 instanceof XMLControl) {
                  var4 = (XMLProperty)var3;
               }
            }

            this.add(new XMLTreeNode(var4));
         }
      }

      if (this.prop.getPropertyType().equals("array")) {
         Class var7 = this.prop.getPropertyClass();
         if (var7 == null) {
            return;
         }

         while(var7.getComponentType() != null) {
            var7 = var7.getComponentType();
         }

         if (var7.getName().equals("double") || var7.getName().equals("int")) {
            var4 = this.prop;

            for(XMLProperty var8 = var4.getParentProperty(); !(var8 instanceof XMLControl); var8 = var8.getParentProperty()) {
               var4 = var8;
            }

            var7 = var4.getPropertyClass();

            int var6;
            for(var6 = 0; var7.getComponentType() != null; ++var6) {
               var7 = var7.getComponentType();
            }

            if (var6 <= 3) {
               this.inspectable = true;
            }
         }
      }

   }

   public XMLProperty getProperty() {
      return this.prop;
   }

   public boolean isInspectable() {
      return this.inspectable;
   }

   public String toString() {
      if (this.prop instanceof XMLControl) {
         XMLControl var1 = (XMLControl)this.prop;
         String var2 = var1.getString("name");
         if (var2 != null) {
            return var2;
         }
      }

      return this.prop.getPropertyName();
   }
}
