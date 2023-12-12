package org.opensourcephysics.controls;

import java.util.logging.Level;

public class ConsoleLevel extends Level {
   public static final ConsoleLevel OUT_CONSOLE = new ConsoleLevel("OUT_CONSOLE", 750);
   public static final ConsoleLevel ERR_CONSOLE = new ConsoleLevel("ERR_CONSOLE", 760);

   ConsoleLevel(String var1, int var2) {
      super(var1, var2);
   }
}
