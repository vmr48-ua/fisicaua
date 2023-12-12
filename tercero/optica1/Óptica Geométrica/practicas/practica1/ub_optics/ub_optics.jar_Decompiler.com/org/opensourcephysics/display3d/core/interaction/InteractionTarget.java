package org.opensourcephysics.display3d.core.interaction;

public interface InteractionTarget {
   void setEnabled(boolean var1);

   boolean isEnabled();

   String getActionCommand();

   void setActionCommand(String var1);

   void setAffectsGroup(boolean var1);

   boolean getAffectsGroup();
}
