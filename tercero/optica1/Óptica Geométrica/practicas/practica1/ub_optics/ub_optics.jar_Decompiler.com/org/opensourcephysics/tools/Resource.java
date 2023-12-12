package org.opensourcephysics.tools;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import javax.swing.ImageIcon;
import org.opensourcephysics.controls.XML;

public class Resource {
   protected static String encoding = "UTF-8";
   private URL url;
   private File file;
   private boolean isAnImage = true;
   private ImageIcon icon;
   private String string;
   private AudioClip clip;
   private BufferedImage image;
   // $FF: synthetic field
   static Class class$java$lang$String;
   // $FF: synthetic field
   static Class class$javax$swing$ImageIcon;

   public Resource(URL var1) {
      this.url = var1;
   }

   public Resource(File var1) {
      this.file = var1;
   }

   public String getAbsolutePath() {
      if (this.getFile() != null) {
         try {
            return XML.forwardSlash(this.getFile().getCanonicalPath());
         } catch (IOException var5) {
            return this.getFile().getAbsolutePath();
         }
      } else if (this.getURL() == null) {
         return null;
      } else {
         URL var1 = this.getURL();
         String var2 = var1.getPath();
         if (var2.startsWith("file:")) {
            var2 = var2.substring(5);
         }

         if (var2.startsWith("/") && var2.indexOf(":") > -1) {
            var2 = var2.substring(1);
         }

         for(int var3 = var2.indexOf("%20"); var3 > -1; var3 = var2.indexOf("%20")) {
            String var4 = var2.substring(0, var3);
            var2 = var4 + " " + var2.substring(var3 + 3);
         }

         return var2;
      }
   }

   public URL getURL() {
      if (this.url == null && this.file != null) {
         String var1 = this.getAbsolutePath();

         try {
            if (var1.startsWith("/")) {
               this.url = new URL("file:" + var1);
            } else {
               this.url = new URL("file:/" + var1);
            }
         } catch (MalformedURLException var3) {
         }
      }

      return this.url;
   }

   public File getFile() {
      return this.file;
   }

   public Object getObject(Class var1) {
      if ((class$javax$swing$ImageIcon == null ? (class$javax$swing$ImageIcon = class$("javax.swing.ImageIcon")) : class$javax$swing$ImageIcon).equals(var1)) {
         return this.getIcon();
      } else {
         return (class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String).equals(var1) ? this.getString() : null;
      }
   }

   public InputStream openInputStream() {
      if (this.getFile() != null) {
         try {
            return new FileInputStream(this.getFile());
         } catch (FileNotFoundException var3) {
         }
      }

      if (this.getURL() != null) {
         try {
            return this.getURL().openStream();
         } catch (IOException var2) {
         }
      }

      return null;
   }

   public BufferedReader openReader() {
      Charset var1 = Charset.forName(encoding);
      InputStream var2 = this.openInputStream();
      return var2 == null ? null : new BufferedReader(new InputStreamReader(var2, var1));
   }

   public ImageIcon getIcon() {
      if (this.icon == null && this.isAnImage) {
         this.icon = new ImageIcon(this.getURL());
         if (this.icon.getIconWidth() < 1) {
            this.icon = null;
            this.isAnImage = false;
         }
      }

      return this.icon;
   }

   public Image getImage() {
      ImageIcon var1 = this.getIcon();
      return var1 != null ? var1.getImage() : null;
   }

   public BufferedImage getBufferedImage() {
      if (this.image == null && this.isAnImage) {
         Image var1 = this.getImage();
         if (var1 == null) {
            this.isAnImage = false;
         } else {
            this.image = new BufferedImage(var1.getWidth((ImageObserver)null), var1.getHeight((ImageObserver)null), 1);
            Graphics2D var2 = this.image.createGraphics();
            var2.drawImage(var1, 0, 0, (ImageObserver)null);
         }
      }

      return this.image;
   }

   public String getString() {
      if (this.string == null) {
         StringBuffer var1 = new StringBuffer();

         try {
            BufferedReader var2 = new BufferedReader(this.openReader());

            for(String var3 = var2.readLine(); var3 != null; var3 = var2.readLine()) {
               var1.append(var3 + XML.NEW_LINE);
            }

            var2.close();
         } catch (IOException var4) {
         }

         this.string = var1.toString();
      }

      return this.string;
   }

   public AudioClip getAudioClip() {
      if (this.clip == null && this.getURL() != null) {
         this.clip = Applet.newAudioClip(this.getURL());
      }

      return this.clip;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
