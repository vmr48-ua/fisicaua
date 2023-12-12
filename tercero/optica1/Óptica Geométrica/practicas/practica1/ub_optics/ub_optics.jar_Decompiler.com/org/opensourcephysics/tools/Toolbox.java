package org.opensourcephysics.tools;

import java.awt.Component;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import org.opensourcephysics.controls.OSPLog;

public class Toolbox {
   protected static Map tools = new HashMap();
   protected static Registry registry;
   protected static int allowRMI = -1;

   protected Toolbox() {
   }

   public static void addTool(String var0, Tool var1) {
      tools.put(var0, var1);
      OSPLog.fine("Added to toolbox: " + var0);
   }

   public static boolean addRMITool(String var0, Tool var1) {
      initRMI();
      if (allowRMI == 0) {
         return false;
      } else {
         try {
            RemoteTool var2 = new RemoteTool(var1);
            registry.bind(var0, var2);
            OSPLog.fine("Added to RMI registry: " + var0);
            return true;
         } catch (Exception var3) {
            OSPLog.warning("RMI registration failed: " + var0 + " [" + var3 + "]");
            return false;
         }
      }
   }

   public static Tool getTool(String var0) {
      Tool var1;
      if (tools.containsKey(var0)) {
         var1 = (Tool)tools.get(var0);
         OSPLog.fine("Found local tool: " + var0);
         return var1;
      } else {
         initRMI();
         if (allowRMI == 0) {
            return null;
         } else {
            try {
               var1 = (Tool)registry.lookup(var0);
               OSPLog.fine("Found RMI tool " + var0);
               return new RemoteTool(var1);
            } catch (Exception var2) {
               OSPLog.info("RMI lookup failed: " + var0 + " [" + var2 + "]");
               return null;
            }
         }
      }
   }

   private static void initRMI() {
      if (allowRMI != 0) {
         int var0 = JOptionPane.showConfirmDialog((Component)null, "Initialize Remote Method Invocation?", "RMI Toolbox", 0, 3);
         if (var0 == 0) {
            allowRMI = 1;
            if (registry == null) {
               try {
                  registry = LocateRegistry.getRegistry(1099);
                  registry.list();
               } catch (RemoteException var5) {
                  try {
                     registry = LocateRegistry.createRegistry(1099);
                  } catch (RemoteException var4) {
                     OSPLog.info(var4.getMessage());
                  }
               }
            }

            if (System.getSecurityManager() == null) {
               try {
                  String var1 = "file:" + System.getProperty("user.dir");
                  System.setProperty("java.rmi.server.codebase", var1 + "/classes/");
                  System.setProperty("java.security.policy", var1 + "/Remote.policy");
                  System.setSecurityManager(new RMISecurityManager());
               } catch (Exception var3) {
                  OSPLog.info(var3.getMessage());
               }
            }

         } else {
            allowRMI = 0;
         }
      }
   }
}
