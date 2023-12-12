package org.opensourcephysics.ejs.control.swing;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import org.opensourcephysics.ejs.control.value.Value;

public class ControlSound extends ControlCheckBox {
   static final int VARIABLE = 5;
   private AudioClip clip = null;
   private String audioFile = null;
   private boolean playing = false;
   private static ArrayList infoList = null;

   public ControlSound(Object var1) {
      super(var1);
      super.checkbox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            if (ControlSound.super.checkbox.isSelected()) {
               ControlSound.this.play();
            } else {
               ControlSound.this.stop();
            }

         }
      });
   }

   public void setAudioClip(String var1, String var2) {
      if (var2 == null) {
         this.stop();
         this.clip = null;
      } else {
         try {
            String var3 = "";
            if (var1 == null) {
               var3 = "file:";
            } else {
               var3 = var1.toString();
               if (var3.startsWith("file:")) {
                  var3 = "file:///" + var3.substring(6);
               }

               if (!var3.endsWith("/")) {
                  var3 = var3 + "/";
               }
            }

            String var4 = var3 + var2;
            URL var5 = new URL(var4);
            this.clip = Applet.newAudioClip(var5);
         } catch (Exception var6) {
            var6.printStackTrace();
            this.clip = null;
         }

      }
   }

   public void destroy() {
      if (this.clip != null) {
         this.clip.stop();
      }

      this.clip = null;
      super.destroy();
   }

   public void play() {
      if (this.clip != null) {
         this.clip.loop();
      }
   }

   public void stop() {
      if (this.clip != null) {
         this.clip.stop();
      }

   }

   public ArrayList getPropertyList() {
      if (infoList == null) {
         infoList = new ArrayList();
         infoList.add("audiofile");
         infoList.addAll(super.getPropertyList());
      }

      return infoList;
   }

   public String getPropertyInfo(String var1) {
      return var1.equals("audiofile") ? "File|String" : super.getPropertyInfo(var1);
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         this.setAudioFile(var2.getString());
         break;
      case 5:
         if (var2.getBoolean() != this.playing) {
            this.playing = var2.getBoolean();
            if (this.playing) {
               this.play();
            } else {
               this.stop();
            }
         }

         super.setValue(4, var2);
         break;
      default:
         super.setValue(var1 - 1, var2);
      }

   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         this.setAudioClip((String)null, (String)null);
         this.audioFile = null;
         break;
      default:
         super.setDefaultValue(var1 - 1);
      }

   }

   public Value getValue(int var1) {
      switch(var1) {
      case 0:
         return null;
      default:
         return super.getValue(var1 - 1);
      }
   }

   private void setAudioFile(String var1) {
      if (this.audioFile == null || !this.audioFile.equals(var1)) {
         this.audioFile = var1;
         if (this.getProperty("_ejs_codebase") != null) {
            this.setAudioClip(this.getProperty("_ejs_codebase"), var1);
         } else if (this.getSimulation() != null && this.getSimulation().getCodebase() != null) {
            this.setAudioClip(this.getSimulation().getCodebase().toString(), var1);
         } else {
            this.setAudioClip((String)null, var1);
         }

      }
   }
}
