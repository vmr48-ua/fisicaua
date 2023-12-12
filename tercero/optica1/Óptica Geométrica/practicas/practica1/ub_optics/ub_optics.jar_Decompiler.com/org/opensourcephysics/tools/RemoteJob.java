package org.opensourcephysics.tools;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteJob extends UnicastRemoteObject implements Job {
   Job wrappedJob;

   public RemoteJob(Job var1) throws RemoteException {
      this.wrappedJob = var1;
   }

   public String getXML() throws RemoteException {
      return this.wrappedJob.getXML();
   }

   public void setXML(String var1) throws RemoteException {
      this.wrappedJob.setXML(var1);
   }
}
