package org.opensourcephysics.controls;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class XMLPropertyElement implements XMLProperty {
   protected XMLProperty parent;
   protected String name;
   protected String type;
   protected String className;
   protected List content;
   // $FF: synthetic field
   static Class class$java$lang$String;

   public XMLPropertyElement(XMLProperty var1) {
      this.content = new ArrayList();
      this.parent = var1;
   }

   public XMLPropertyElement(XMLProperty var1, String var2, String var3, Object var4) {
      this(var1);
      this.name = var2;
      this.type = var3;
      if (this.type.equals("string")) {
         if (XML.requiresCDATA((String)var4)) {
            this.content.add("<![CDATA[" + var4 + "]]>");
         } else {
            this.content.add(var4.toString());
         }
      } else if ("intdoubleboolean".indexOf(this.type) != -1) {
         this.content.add(var4.toString());
      } else if (this.type.equals("object")) {
         this.className = var4.getClass().getName();
         XMLControlElement var5 = new XMLControlElement(this);
         var5.saveObject(var4);
         this.content.add(var5);
      } else if (this.type.equals("collection")) {
         this.className = var4.getClass().getName();
         Iterator var13 = ((Collection)var4).iterator();
         int var6 = 0;

         while(var13.hasNext()) {
            Object var7 = var13.next();
            String var8 = XML.getDataType(var7);
            if (var8 != null) {
               this.content.add(new XMLPropertyElement(this, "item", var8, var7));
               ++var6;
            }
         }
      } else if (this.type.equals("array")) {
         this.className = var4.getClass().getName();
         Class var14 = var4.getClass().getComponentType();
         Object var15 = var4;

         int var16;
         for(var16 = Array.getLength(var4); var16 > 0 && var14.getComponentType() != null; var16 *= Array.getLength(var15)) {
            var14 = var14.getComponentType();
            var15 = Array.get(var15, 0);
            if (var15 == null) {
               break;
            }
         }

         boolean var17 = "intdoubleboolean".indexOf(var14.getName()) != -1;
         if (var17 && var16 > XMLControlElement.compactArraySize) {
            String var18 = this.getArrayString(var4);
            this.content.add(new XMLPropertyElement(this, "array", "string", var18));
         } else {
            int var9 = Array.getLength(var4);

            for(int var10 = 0; var10 < var9; ++var10) {
               Object var11 = Array.get(var4, var10);
               String var12 = XML.getDataType(var11);
               if (var12 != null) {
                  this.content.add(new XMLPropertyElement(this, "[" + var10 + "]", var12, var11));
               }
            }
         }
      }

   }

   public String getPropertyName() {
      return this.name;
   }

   public String getPropertyType() {
      return this.type;
   }

   public Class getPropertyClass() {
      if (this.type.equals("int")) {
         return Integer.TYPE;
      } else if (this.type.equals("double")) {
         return Double.TYPE;
      } else if (this.type.equals("boolean")) {
         return Boolean.TYPE;
      } else if (this.type.equals("string")) {
         return class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String;
      } else {
         try {
            return Class.forName(this.className);
         } catch (ClassNotFoundException var2) {
            return null;
         }
      }
   }

   public XMLProperty getParentProperty() {
      return this.parent;
   }

   public int getLevel() {
      return this.parent.getLevel() + 1;
   }

   public List getPropertyContent() {
      return this.content;
   }

   public XMLControl getChildControl(String var1) {
      XMLControl[] var2 = this.getChildControls();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         if (var2[var3].getPropertyName().equals(var1)) {
            return var2[var3];
         }
      }

      return null;
   }

   public XMLControl[] getChildControls() {
      if (this.type.equals("object")) {
         XMLControl var4 = (XMLControl)this.getPropertyContent().get(0);
         return new XMLControl[]{var4};
      } else if ("arraycollection".indexOf(this.type) != -1) {
         ArrayList var1 = new ArrayList();
         Iterator var2 = this.getPropertyContent().iterator();

         while(var2.hasNext()) {
            XMLProperty var3 = (XMLProperty)var2.next();
            if (var3.getPropertyType().equals("object")) {
               var1.add(var3.getPropertyContent().get(0));
            }
         }

         return (XMLControl[])((XMLControl[])var1.toArray(new XMLControl[0]));
      } else {
         return new XMLControl[0];
      }
   }

   public void setValue(String var1) {
      boolean var2 = true;

      try {
         if (this.type.equals("int")) {
            Integer.parseInt(var1);
         } else if (this.type.equals("double")) {
            Double.parseDouble(var1);
         } else if (this.type.equals("boolean")) {
            var1 = var1.equals("true") ? "true" : "false";
         } else if ("objectarraycollection".indexOf(this.type) != -1) {
            var2 = false;
         } else if (this.type.equals("string") && XML.requiresCDATA(var1)) {
            var1 = "<![CDATA[" + var1 + "]]>";
         }
      } catch (NumberFormatException var4) {
         var2 = false;
      }

      if (var2) {
         this.content.clear();
         this.content.add(var1);
      }

   }

   public String toString() {
      StringBuffer var1 = new StringBuffer(XML.NEW_LINE + this.indent(this.getLevel()) + "<property name=\"" + this.name + "\" type=\"" + this.type + "\"");
      if ("arraycollection".indexOf(this.type) != -1) {
         var1.append(" class=\"" + this.className + "\"");
      }

      List var2 = this.getPropertyContent();
      if (var2.isEmpty()) {
         var1.append("/>");
         return var1.toString();
      } else {
         var1.append(">");
         boolean var3 = false;
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            Object var5 = var4.next();
            var3 = var3 || var5 instanceof XMLProperty;
            var1.append(var5);
         }

         if (var3) {
            var1.append(XML.NEW_LINE + this.indent(this.getLevel()));
         }

         var1.append("</property>");
         return var1.toString();
      }
   }

   protected String indent(int var1) {
      String var2 = "";

      for(int var3 = 0; var3 < 4 * var1; ++var3) {
         var2 = var2 + " ";
      }

      return var2;
   }

   protected String getArrayString(Object var1) {
      StringBuffer var2 = new StringBuffer("{");
      int var3 = Array.getLength(var1);

      for(int var4 = 0; var4 < var3; ++var4) {
         if (var4 > 0) {
            var2.append(',');
         }

         Object var5 = Array.get(var1, var4);
         if (var5 != null && var5.getClass().isArray()) {
            var2.append(this.getArrayString(var5));
         } else {
            var2.append(var5);
         }
      }

      var2.append('}');
      return var2.toString();
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
