package org.opensourcephysics.tools;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Tool extends Remote {
   void send(Job var1, Tool var2) throws RemoteException;
}
