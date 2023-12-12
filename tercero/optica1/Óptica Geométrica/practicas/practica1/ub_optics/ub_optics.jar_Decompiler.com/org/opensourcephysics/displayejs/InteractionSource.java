package org.opensourcephysics.displayejs;

public interface InteractionSource {
   void setEnabled(boolean var1);

   boolean isEnabled();

   void setEnabled(int var1, boolean var2);

   boolean isEnabled(int var1);

   void addListener(InteractionListener var1);

   void removeListener(InteractionListener var1);

   void removeAllListeners();

   void invokeActions(InteractionEvent var1);
}
