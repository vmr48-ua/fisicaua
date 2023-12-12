package org.opensourcephysics.displayejs;

import org.opensourcephysics.numerics.Transformation;

public interface Body {
   void setOrigin(double var1, double var3, double var5, boolean var7);

   void setTransformation(Transformation var1);

   void toSpaceFrame(double[] var1);

   void toBodyFrame(double[] var1) throws UnsupportedOperationException;
}
