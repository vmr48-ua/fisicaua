package org.opensourcephysics.controls;

import java.io.Reader;
import java.io.Writer;

public interface XMLControl extends Control, XMLProperty {
   String getPropertyType(String var1);

   String getObjectClassName();

   Class getObjectClass();

   void saveObject(Object var1);

   Object loadObject(Object var1);

   String read(String var1);

   void read(Reader var1);

   void readXML(String var1);

   boolean failedToRead();

   String write(String var1);

   void write(Writer var1);

   String toXML();
}
