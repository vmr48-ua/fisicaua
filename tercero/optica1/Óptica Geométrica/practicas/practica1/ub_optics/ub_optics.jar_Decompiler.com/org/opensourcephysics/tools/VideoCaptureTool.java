package org.opensourcephysics.tools;

import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import org.opensourcephysics.controls.OSPLog;
import org.opensourcephysics.controls.XMLControlElement;

public class VideoCaptureTool implements Tool {
   private VideoCaptureTool mediaCap;

   public VideoCaptureTool() {
   }

   protected VideoCaptureTool(boolean var1) {
   }

   public boolean addFrame(BufferedImage var1) {
      return this.mediaCap != null ? this.mediaCap.addFrame(var1) : false;
   }

   public void setVisible(boolean var1) {
      if (this.mediaCap == null) {
         try {
            Class var2 = Class.forName("org.opensourcephysics.media.core.VideoGrabber");
            this.mediaCap = (VideoCaptureTool)var2.newInstance();
         } catch (Exception var3) {
            OSPLog.info(var3.getMessage());
         }
      }

      if (this.mediaCap != null) {
         this.mediaCap.setVisible(var1);
      }

   }

   public boolean isVisible() {
      return this.mediaCap != null && this.mediaCap.isVisible();
   }

   public boolean canCapture() {
      return this.mediaCap != null;
   }

   public void send(Job var1, Tool var2) throws RemoteException {
      if (var1 != null) {
         XMLControlElement var3 = new XMLControlElement(var1.getXML());
         String var4 = var3.getString("imagepath");
         if (var4 != null) {
            BufferedImage var5 = ResourceLoader.getBufferedImage(var4);
            if (var5 != null) {
               this.addFrame(var5);
            }
         }

      }
   }
}
