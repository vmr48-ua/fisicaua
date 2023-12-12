package org.opensourcephysics.controls;

import java.awt.Rectangle;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;

class OSPLogHandler extends Handler {
   JTextPane log;

   public OSPLogHandler(JTextPane var1) {
      this.log = var1;
   }

   public void publish(LogRecord var1) {
      if (this.isLoggable(var1)) {
         String var2 = this.getFormatter().format(var1);
         Style var3 = OSPLog.green;
         int var4 = var1.getLevel().intValue();
         if (var4 == ConsoleLevel.ERR_CONSOLE.intValue()) {
            var3 = OSPLog.magenta;
         } else if (var4 == ConsoleLevel.OUT_CONSOLE.intValue()) {
            var3 = OSPLog.gray;
         } else if (var4 >= Level.WARNING.intValue()) {
            var3 = OSPLog.red;
         } else if (var4 >= Level.INFO.intValue()) {
            var3 = OSPLog.black;
         } else if (var4 >= Level.CONFIG.intValue()) {
            var3 = OSPLog.green;
         } else if (var4 >= Level.FINEST.intValue()) {
            var3 = OSPLog.blue;
         }

         try {
            Document var5 = this.log.getDocument();
            var5.insertString(var5.getLength(), var2 + '\n', var3);
            Rectangle var6 = this.log.getBounds();
            var6.y = var6.height;
            this.log.scrollRectToVisible(var6);
         } catch (BadLocationException var7) {
            System.err.println(var7);
         }

      }
   }

   public void flush() {
   }

   public void close() {
   }
}
