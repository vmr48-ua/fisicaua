package org.opensourcephysics.ejs.control;

import java.util.Enumeration;
import java.util.Vector;
import org.opensourcephysics.ejs.control.value.DoubleValue;
import org.opensourcephysics.ejs.control.value.Value;

public class GroupVariable {
   private String name;
   private Value value;
   private Vector elementList;
   private Vector methodList;

   public GroupVariable(String var1, Value var2) {
      this.name = var1;
      this.elementList = new Vector();
      this.methodList = new Vector();
      if (var2 != null) {
         this.value = var2.cloneValue();
      } else {
         this.value = new DoubleValue(0.0D);
      }

   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      return this.name;
   }

   public void setValue(Value var1) {
      if (this.value.getClass() != var1.getClass()) {
         this.value = var1.cloneValue();
      } else {
         this.value.copyValue(var1);
      }

   }

   public Value getValue() {
      return this.value;
   }

   public void addElementListener(ControlElement var1, int var2) {
      this.elementList.add(new GroupVariable.Item(var1, var2));
   }

   public void removeElementListener(ControlElement var1, int var2) {
      Enumeration var3 = this.elementList.elements();

      GroupVariable.Item var4;
      do {
         if (!var3.hasMoreElements()) {
            return;
         }

         var4 = (GroupVariable.Item)var3.nextElement();
      } while(var4.element != var1 || var4.index != var2);

      this.elementList.removeElement(var4);
   }

   public void propagateValue(ControlElement var1) {
      Enumeration var2 = this.elementList.elements();

      while(var2.hasMoreElements()) {
         GroupVariable.Item var3 = (GroupVariable.Item)var2.nextElement();
         if (var3.element != var1) {
            var3.element.setActive(false);
            if (var3.element.myMethodsForProperties[var3.index] != null) {
               var3.element.setValue(var3.index, var3.element.myMethodsForProperties[var3.index].invoke(2, (Object)null));
            } else if (var3.element.myExpressionsForProperties[var3.index] != null) {
               var3.element.setValue(var3.index, var3.element.myExpressionsForProperties[var3.index]);
            } else {
               var3.element.setValue(var3.index, this.value);
            }

            var3.element.setActive(true);
         }
      }

   }

   public void addListener(Object var1, String var2) {
      this.addListener(var1, var2, (Object)null);
   }

   public void addListener(Object var1, String var2, Object var3) {
      this.methodList.add(new MethodWithOneParameter(1, var1, var2, (String)null, (MethodWithOneParameter)null, var3));
   }

   public void removeListener(Object var1, String var2) {
      Enumeration var3 = this.methodList.elements();

      MethodWithOneParameter var4;
      do {
         if (!var3.hasMoreElements()) {
            return;
         }

         var4 = (MethodWithOneParameter)var3.nextElement();
      } while(!var4.equals(1, var1, var2));

      this.methodList.removeElement(var4);
   }

   public void invokeListeners(ControlElement var1) {
      Enumeration var2 = this.methodList.elements();

      while(var2.hasMoreElements()) {
         ((MethodWithOneParameter)var2.nextElement()).invoke(1, var1);
      }

   }

   private class Item {
      public ControlElement element;
      public int index;

      Item(ControlElement var2, int var3) {
         this.element = var2;
         this.index = var3;
      }
   }
}
