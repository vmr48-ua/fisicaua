package org.opensourcephysics.controls;

public class XMLLoader implements XML.ObjectLoader {
   public void saveObject(XMLControl var1, Object var2) {
   }

   public Object createObject(XMLControl var1) {
      try {
         return var1.getObjectClass().newInstance();
      } catch (Exception var3) {
         return null;
      }
   }

   public Object loadObject(XMLControl var1, Object var2) {
      return var2;
   }
}
