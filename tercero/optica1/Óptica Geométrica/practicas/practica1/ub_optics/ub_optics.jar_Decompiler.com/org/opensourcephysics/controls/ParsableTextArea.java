package org.opensourcephysics.controls;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.StringTokenizer;
import javax.swing.JTextArea;

public class ParsableTextArea extends JTextArea {
   HashMap pendingMap = new LinkedHashMap();
   HashMap currentMap = new LinkedHashMap();
   HashMap lockedMap = new LinkedHashMap();
   boolean locked = false;

   public ParsableTextArea() {
      super(10, 10);
      this.setFont(new Font("Monospaced", 0, 12));
   }

   public String getValue(String var1) throws VariableNotFoundException {
      String var3;
      if (this.locked) {
         synchronized(this.lockedMap) {
            var3 = (String)this.lockedMap.get(var1);
            if (var3 != null) {
               return (String)this.lockedMap.get(var1);
            } else {
               throw new VariableNotFoundException("Variable " + var1 + " not found.");
            }
         }
      } else {
         synchronized(this.currentMap) {
            this.updateCurrentMap();
            synchronized(this.pendingMap) {
               this.currentMap.putAll(this.pendingMap);
            }

            var3 = (String)this.currentMap.get(var1);
            if (var3 != null) {
               return var3;
            }
         }

         throw new VariableNotFoundException("Variable " + var1 + " not found.");
      }
   }

   public synchronized void setLockValues(boolean var1) {
      if (this.locked != var1) {
         this.locked = var1;
         if (this.locked) {
            synchronized(this.lockedMap) {
               this.lockedMap.clear();
               synchronized(this.currentMap) {
                  this.updateCurrentMap();
                  this.lockedMap.putAll(this.currentMap);
               }

               synchronized(this.pendingMap) {
                  this.lockedMap.putAll(this.pendingMap);
               }
            }
         } else {
            this.setValue((String)null, (String)null);
         }

      }
   }

   public void setValue(String var1, String var2) {
      Runnable var3 = new Runnable() {
         public void run() {
            ParsableTextArea.this.updateText();
         }
      };
      if (var1 != null) {
         synchronized(this.pendingMap) {
            this.pendingMap.put(var1, var2);
         }
      }

      if (this.locked && var1 != null) {
         synchronized(this.lockedMap) {
            this.lockedMap.put(var1, var2);
         }
      }

      if (!this.locked) {
         EventQueue.invokeLater(var3);
      }

   }

   public HashMap getCurrentMap() {
      synchronized(this.currentMap) {
         this.updateCurrentMap();
         synchronized(this.pendingMap) {
            this.currentMap.putAll(this.pendingMap);
         }

         return (HashMap)this.currentMap.clone();
      }
   }

   private synchronized void updateText() {
      synchronized(this.currentMap) {
         label40: {
            synchronized(this.pendingMap) {
               if (this.pendingMap.size() != 0) {
                  this.updateCurrentMap();
                  this.currentMap.putAll(this.pendingMap);
                  this.pendingMap.clear();
                  break label40;
               }
            }

            return;
         }

         Set var2 = this.currentMap.keySet();
         Iterator var3 = var2.iterator();
         StringBuffer var4 = new StringBuffer(var2.size() * 25);

         while(var3.hasNext()) {
            String var5 = (String)var3.next();
            var4.append(var5);
            var4.append('=');
            var4.append(this.currentMap.get(var5));
            var4.append('\n');
         }

         this.setText(var4.toString());
      }
   }

   private void updateCurrentMap() {
      this.currentMap.clear();
      String var1 = this.getText();
      StringTokenizer var2 = new StringTokenizer(var1, "\n");

      while(var2.hasMoreTokens()) {
         String var3 = var2.nextToken().trim();
         int var4 = var3.indexOf("=");
         if (var3 != null && var4 != -1) {
            this.currentMap.put(var3.subSequence(0, var4), var3.subSequence(var4 + 1, var3.length()));
         }
      }

   }

   public static XML.ObjectLoader getLoader() {
      return new ParsableTextArea.ParsableTextAreaLoader();
   }

   static class ParsableTextAreaLoader implements XML.ObjectLoader {
      public void saveObject(XMLControl var1, Object var2) {
         HashMap var3 = ((ParsableTextArea)var2).getCurrentMap();
         Iterator var4 = var3.keySet().iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            var1.setValue(var5, var3.get(var5));
         }

      }

      public Object createObject(XMLControl var1) {
         return new ParsableTextArea();
      }

      public Object loadObject(XMLControl var1, Object var2) {
         ParsableTextArea var3 = (ParsableTextArea)var2;
         Iterator var4 = var1.getPropertyNames().iterator();
         var3.setLockValues(true);

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            var3.setValue(var5, var1.getString(var5));
         }

         var3.setLockValues(false);
         return var2;
      }
   }
}
