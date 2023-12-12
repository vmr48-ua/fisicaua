package org.opensourcephysics.tools;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import org.opensourcephysics.controls.OSPLog;

public class RemoteTool extends UnicastRemoteObject implements Tool {
   Tool child;
   Map replies = new HashMap();
   Map jobs = new HashMap();

   public RemoteTool(Tool var1) throws RemoteException {
      OSPLog.finest("Wrapping tool " + var1.getClass().getName());
      this.child = var1;
   }

   public void send(Job var1, Tool var2) throws RemoteException {
      this.save(var1, var2);
      var1 = this.convert(var1);
      if (this.child.equals(var2)) {
         this.sendReplies(var1);
      } else {
         this.forward(var1);
      }

   }

   private void save(Job var1, Tool var2) {
      if (var2 != null && !this.child.equals(var2)) {
         Object var3 = (Collection)this.replies.get(var1);
         if (var3 == null) {
            var3 = new HashSet();
            this.replies.put(var1, var3);
         }

         ((Collection)var3).add(var2);
      }
   }

   private void sendReplies(Job var1) throws RemoteException {
      Collection var2 = (Collection)this.replies.get(var1);
      if (var2 != null) {
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            Tool var4 = (Tool)var3.next();
            var4.send(var1, this);
         }

      }
   }

   private void forward(Job var1) throws RemoteException {
      this.child.send(var1, this);
   }

   private Job convert(Job var1) throws RemoteException {
      if (var1 instanceof LocalJob) {
         RemoteJob var3 = new RemoteJob(var1);
         this.jobs.put(var3, var1);
         return var3;
      } else {
         Job var2 = (Job)this.jobs.get(var1);
         return var2 == null ? var1 : (Job)var2;
      }
   }
}
