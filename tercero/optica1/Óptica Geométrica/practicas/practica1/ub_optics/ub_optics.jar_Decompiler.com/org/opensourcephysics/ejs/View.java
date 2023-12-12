package org.opensourcephysics.ejs;

import java.awt.Component;
import org.opensourcephysics.controls.Control;

public interface View extends Control {
   void reset();

   void initialize();

   void read();

   void read(String var1);

   void update();

   Component getComponent(String var1);
}
