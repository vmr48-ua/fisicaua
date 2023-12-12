package org.opensourcephysics.tools;

import org.opensourcephysics.numerics.Function;

public interface KnownFunction extends Function {
   int getParameterCount();

   String getParameterName(int var1);

   double getParameterValue(int var1);

   void setParameterValue(int var1, double var2);

   String getEquation(String var1);
}
