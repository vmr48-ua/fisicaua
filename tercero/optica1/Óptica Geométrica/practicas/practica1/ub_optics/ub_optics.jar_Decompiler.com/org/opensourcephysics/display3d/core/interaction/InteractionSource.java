package org.opensourcephysics.display3d.core.interaction;

public interface InteractionSource {
   InteractionTarget getInteractionTarget(int var1);

   void addInteractionListener(InteractionListener var1);

   void removeInteractionListener(InteractionListener var1);
}
