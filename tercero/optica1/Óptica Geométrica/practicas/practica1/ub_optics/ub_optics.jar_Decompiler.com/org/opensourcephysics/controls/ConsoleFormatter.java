package org.opensourcephysics.controls;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

class ConsoleFormatter extends SimpleFormatter {
   public String format(LogRecord var1) {
      String var2 = this.formatMessage(var1);
      if (var1.getLevel().intValue() != ConsoleLevel.OUT_CONSOLE.intValue() && var1.getLevel().intValue() != ConsoleLevel.ERR_CONSOLE.intValue()) {
         return super.format(var1);
      } else {
         StringBuffer var3 = new StringBuffer();
         if (var2.charAt(0) == '\t') {
            var2 = var2.replaceFirst("\t", "    ");
         } else {
            var3.append("CONSOLE: ");
         }

         var3.append(var2);
         var3.append(OSPLog.eol);
         if (var1.getThrown() != null) {
            try {
               StringWriter var4 = new StringWriter();
               PrintWriter var5 = new PrintWriter(var4);
               var1.getThrown().printStackTrace(var5);
               var5.close();
               var3.append(var4.toString());
            } catch (Exception var6) {
            }
         }

         return var3.toString();
      }
   }
}
