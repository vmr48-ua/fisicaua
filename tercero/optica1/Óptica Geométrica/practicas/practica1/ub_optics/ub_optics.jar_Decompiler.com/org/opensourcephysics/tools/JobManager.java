package org.opensourcephysics.tools;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import org.opensourcephysics.controls.XMLControlElement;

public class JobManager {
   Tool localTool;
   Map replies = new HashMap();
   Map objects = new HashMap();

   public JobManager(Tool var1) {
      this.localTool = var1;
   }

   public void log(Job var1, Tool var2) {
      if (var2 != null) {
         Object var3 = (Collection)this.replies.get(var1);
         if (var3 == null) {
            var3 = new HashSet();
            this.replies.put(var1, var3);
         }

         ((Collection)var3).add(var2);
      }
   }

   public void associate(Job var1, Object var2) {
      if (var2 != null) {
         Object var3 = (Collection)this.objects.get(var1);
         if (var3 == null) {
            var3 = new HashSet();
            this.objects.put(var1, var3);
         }

         ((Collection)var3).add(var2);
      }
   }

   public Job[] getJobs(Object var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.objects.keySet().iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         Collection var5 = (Collection)this.objects.get(var4);
         if (var5 == null) {
            return null;
         }

         if (var5.contains(var1)) {
            var2.add(var4);
         }
      }

      return (Job[])((Job[])var2.toArray(new Job[0]));
   }

   public Object[] getObjects(Job var1) {
      Collection var2 = (Collection)this.objects.get(var1);
      return var2 == null ? new Object[0] : var2.toArray(new Object[0]);
   }

   public void sendReplies(Object var1) {
      Job[] var2 = this.getJobs(var1);
      XMLControlElement var3 = new XMLControlElement(var1);
      String var4 = var3.toXML();

      for(int var5 = 0; var5 < var2.length; ++var5) {
         try {
            var2[var5].setXML(var4);
         } catch (RemoteException var7) {
         }

         this.sendReplies(var2[var5]);
      }

   }

   public void sendReplies(Job var1) {
      Collection var2 = (Collection)this.replies.get(var1);
      if (var2 != null) {
         Iterator var3 = var2.iterator();

         try {
            while(var3.hasNext()) {
               Tool var4 = (Tool)var3.next();
               var4.send(var1, this.localTool);
            }
         } catch (RemoteException var5) {
         }

      }
   }
}
