package org.opensourcephysics.controls;

import java.util.Collection;

public interface Control {
   void setLockValues(boolean var1);

   void setValue(String var1, Object var2);

   void setValue(String var1, double var2);

   void setValue(String var1, int var2);

   void setValue(String var1, boolean var2);

   int getInt(String var1);

   double getDouble(String var1);

   Object getObject(String var1);

   String getString(String var1);

   boolean getBoolean(String var1);

   Collection getPropertyNames();

   void println(String var1);

   void println();

   void print(String var1);

   void clearMessages();

   void clearValues();

   void calculationDone(String var1);
}
