package org.opensourcephysics.display;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent.EventType;
import org.opensourcephysics.controls.OSPLog;
import org.opensourcephysics.tools.Resource;
import org.opensourcephysics.tools.ResourceLoader;

public class TextFrame extends JFrame {
   JTextPane textPane;
   JScrollPane textScroller;

   public TextFrame() {
      this((String)null, (Class)null);
   }

   public TextFrame(String var1) {
      this(var1, (Class)null);
   }

   public TextFrame(String var1, Class var2) {
      this.textPane = new JTextPane();
      this.setSize(300, 300);
      this.textPane.setEditable(false);
      this.textScroller = new JScrollPane(this.textPane);
      this.setContentPane(this.textScroller);
      if (var1 != null) {
         this.loadResource(var1, var2);
      }

   }

   public JTextPane getTextPane() {
      return this.textPane;
   }

   public void enableHyperlinks() {
      this.textPane.addHyperlinkListener(new HyperlinkListener() {
         public void hyperlinkUpdate(HyperlinkEvent var1) {
            if (var1.getEventType() == EventType.ACTIVATED) {
               try {
                  TextFrame.this.textPane.setPage(var1.getURL());
               } catch (IOException var3) {
               }
            }

         }
      });
   }

   public boolean loadResource(String var1, Class var2) {
      Resource var3 = null;

      try {
         var3 = ResourceLoader.getResource(var1, var2);
      } catch (Exception var6) {
         OSPLog.fine("Error getting resource: " + var1);
         return false;
      }

      if (var3 == null) {
         OSPLog.fine("Resource not found: " + var1);
         return false;
      } else {
         try {
            this.textPane.setPage(var3.getURL());
         } catch (IOException var5) {
            OSPLog.fine("Resource not loadeded: " + var1);
            return false;
         }

         this.setTitle(var1);
         return true;
      }
   }
}
