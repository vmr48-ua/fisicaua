package org.opensourcephysics.displayejs;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractInteractionSource implements InteractionSource {
   private ArrayList listeners = new ArrayList();

   public void addListener(InteractionListener var1) {
      if (var1 != null && !this.listeners.contains(var1)) {
         this.listeners.add(var1);
      }
   }

   public void removeListener(InteractionListener var1) {
      this.listeners.remove(var1);
   }

   public void removeAllListeners() {
      this.listeners = new ArrayList();
   }

   public void invokeActions(InteractionEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         InteractionListener var3 = (InteractionListener)var2.next();
         var3.interactionPerformed(var1);
      }

   }

   // $FF: synthetic method
   public abstract boolean isEnabled();

   // $FF: synthetic method
   public abstract boolean isEnabled(int var1);

   // $FF: synthetic method
   public abstract void setEnabled(boolean var1);

   // $FF: synthetic method
   public abstract void setEnabled(int var1, boolean var2);
}
