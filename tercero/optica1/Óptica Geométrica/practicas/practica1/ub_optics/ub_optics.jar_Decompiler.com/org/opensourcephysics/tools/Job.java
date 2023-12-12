package org.opensourcephysics.tools;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Job extends Remote {
   String getXML() throws RemoteException;

   void setXML(String var1) throws RemoteException;
}
