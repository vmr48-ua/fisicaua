package org.opensourcephysics.davidson.applets;

import java.awt.Container;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ObjectManager {
   Map myObjects = new HashMap();
   Map myViews = new HashMap();

   public void addObject(String var1, Object var2) {
      this.myObjects.put(var1, var2);
   }

   public Object getObject(String var1) {
      Object var2 = this.myObjects.get(var1);
      if (var2 == null) {
         var2 = this.myViews.get(var1);
      }

      return var2;
   }

   public void addView(String var1, Container var2) {
      this.myViews.put(var1, var2);
   }

   public void clearAll() {
      this.myObjects.clear();
      this.myViews.clear();
   }

   public void clearObjects() {
      this.myObjects.clear();
   }

   public void clearViews() {
      this.myViews.clear();
   }

   public boolean containsView(Container var1) {
      return this.myViews.containsValue(var1);
   }

   public Container getView(String var1) {
      return (Container)this.myViews.get(var1);
   }

   public Collection getViews() {
      return this.myViews.values();
   }

   public Collection getObjects(Class var1) {
      HashMap var2 = new HashMap(this.myObjects);
      Iterator var3 = var2.values().iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         if (!var1.isInstance(var4)) {
            var3.remove();
         }
      }

      return var2.values();
   }

   public Collection getObjects() {
      return this.myObjects.values();
   }

   public void removeObjects(Class var1) {
      HashMap var2 = new HashMap(this.myObjects);
      Iterator var3 = var2.values().iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         if (!var1.isInstance(var4)) {
            var3.remove();
         }
      }

   }

   public synchronized void removeObject(Object var1) {
      this.myObjects.remove(var1);
   }

   public void printObjectsAndViews() {
      Iterator var1 = this.myObjects.keySet().iterator();
      System.out.println("Objects");

      while(var1.hasNext()) {
         System.out.println(var1.next().toString());
      }

      var1 = this.myViews.keySet().iterator();
      System.out.println("Views");

      while(var1.hasNext()) {
         System.out.println(var1.next().toString());
      }

   }
}
