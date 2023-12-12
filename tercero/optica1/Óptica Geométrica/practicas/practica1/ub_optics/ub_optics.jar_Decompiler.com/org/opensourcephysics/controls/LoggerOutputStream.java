package org.opensourcephysics.controls;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.LogRecord;

class LoggerOutputStream extends OutputStream {
   StringBuffer buffer = new StringBuffer();
   OutputStream oldStream;
   ConsoleLevel level;

   LoggerOutputStream(ConsoleLevel var1, OutputStream var2) {
      this.level = var1;
      this.oldStream = var2;
   }

   public void write(int var1) throws IOException {
      this.oldStream.write(var1);
      if (var1 == 10) {
         LogRecord var2 = new LogRecord(this.level, this.buffer.toString());
         OSPLog.getOSPLog().getLogger().log(var2);
         this.buffer = new StringBuffer();
      } else {
         this.buffer.append((char)var1);
      }

   }
}
