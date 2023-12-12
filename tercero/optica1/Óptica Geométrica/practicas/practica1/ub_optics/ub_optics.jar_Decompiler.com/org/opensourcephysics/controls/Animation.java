package org.opensourcephysics.controls;

public interface Animation {
   void setControl(Control var1);

   void startAnimation();

   void stopAnimation();

   void initializeAnimation();

   void resetAnimation();

   void stepAnimation();
}
