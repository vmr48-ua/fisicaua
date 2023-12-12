package org.opensourcephysics.ejs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Hashtable;
import java.util.StringTokenizer;

public abstract class Simulation implements Runnable {
   public static final int MAXIMUM_FPS = 25;
   public static final int MINIMUM_FPS = 1;
   private Model model = null;
   private View view = null;
   private Thread thread = null;
   private boolean autoplay = false;
   private boolean isPlaying = false;
   private long delay = 0L;
   private URL codebase = null;
   private static final String dummy = "";
   private static final Class strClass = "".getClass();
   private static Hashtable memory = new Hashtable();

   private void errorMessage(String var1) {
      System.err.println(this.getClass().getName() + ": " + var1);
   }

   private void errorMessage(Exception var1) {
      System.err.println(this.getClass().getName() + ": Exception caught! Text follows:");
      var1.printStackTrace(System.err);
   }

   public Model getModel() {
      return this.model;
   }

   public void setModel(Model var1) {
      this.model = var1;
   }

   public View getView() {
      return this.view;
   }

   public void setView(View var1) {
      this.view = var1;
   }

   public void setCodebase(URL var1) {
      this.codebase = var1;
   }

   public URL getCodebase() {
      return this.codebase;
   }

   public void play() {
      if (this.thread == null) {
         this.thread = new Thread(this);
         this.thread.setPriority(1);
         this.thread.start();
         this.isPlaying = true;
      }
   }

   public void pause() {
      this.thread = null;
      this.isPlaying = false;
   }

   public void run() {
      while(this.thread == Thread.currentThread()) {
         this.step();

         try {
            Thread.sleep(this.delay);
         } catch (InterruptedException var2) {
         }
      }

   }

   public void setFPS(int var1) {
      if (var1 <= 1) {
         this.delay = 1000L;
      } else if (var1 >= 25) {
         this.delay = 0L;
      } else {
         this.delay = (long)(1000.0D / (double)var1);
      }

   }

   public void setDelay(int var1) {
      if (var1 < 0) {
         this.delay = 0L;
      } else {
         this.delay = (long)var1;
      }

   }

   public void setAutoplay(boolean var1) {
      this.autoplay = var1;
   }

   public boolean isPlaying() {
      return this.isPlaying;
   }

   public boolean isPaused() {
      return !this.isPlaying;
   }

   public void reset() {
      this.pause();
      if (this.model != null) {
         this.model.reset();
         this.model.initialize();
         this.model.update();
      }

      if (this.view != null) {
         this.view.reset();
         this.view.initialize();
         this.view.update();
      }

      System.gc();
      if (this.autoplay) {
         this.play();
      }

   }

   public void initialize() {
      if (this.view != null) {
         this.view.read();
      }

      if (this.model != null) {
         this.model.initialize();
         this.model.update();
      }

      if (this.view != null) {
         this.view.initialize();
         this.view.update();
      }

   }

   public void apply() {
      if (this.view != null) {
         this.view.read();
      }

      this.update();
   }

   public void applyAll() {
      this.view.read();
      this.update();
   }

   public void apply(String var1) {
      if (this.view != null) {
         this.view.read(var1);
      }

   }

   public void update() {
      if (this.model != null) {
         this.model.update();
      }

      if (this.view != null) {
         this.view.update();
      }

   }

   public void step() {
      this.model.step();
      this.update();
   }

   public void updateAfterModelAction() {
      this.update();
   }

   public String getVariable(String var1) {
      return this.getVariable(var1, ",");
   }

   public String getVariable(String var1, String var2) {
      if (this.model == null) {
         return null;
      } else {
         try {
            Field var3 = this.model.getClass().getField(var1);
            if (var3.getType().isArray()) {
               String var4 = "";
               Object var5 = var3.get(this.model);
               int var6 = Array.getLength(var5);

               for(int var7 = 0; var7 < var6; ++var7) {
                  if (var7 > 0) {
                     var4 = var4 + var2 + Array.get(var5, var7).toString();
                  } else {
                     var4 = var4 + Array.get(var5, var7).toString();
                  }
               }

               return var4;
            } else {
               return var3.get(this.model).toString();
            }
         } catch (Exception var8) {
            this.errorMessage(var8);
            return null;
         }
      }
   }

   public boolean setVariable(String var1, String var2) {
      return this.setVariable(var1, var2, ",");
   }

   public boolean setVariable(String var1, String var2, String var3) {
      if (this.model == null) {
         return false;
      } else {
         try {
            Field var4 = this.model.getClass().getField(var1);
            if (!var4.getType().isArray()) {
               Class var13 = var4.getType();
               if (var13.equals(Double.TYPE)) {
                  var4.setDouble(this.model, Double.parseDouble(var2));
               } else if (var13.equals(Float.TYPE)) {
                  var4.setFloat(this.model, Float.parseFloat(var2));
               } else if (var13.equals(Byte.TYPE)) {
                  var4.setByte(this.model, Byte.parseByte(var2));
               } else if (var13.equals(Short.TYPE)) {
                  var4.setShort(this.model, Short.parseShort(var2));
               } else if (var13.equals(Integer.TYPE)) {
                  var4.setInt(this.model, Integer.parseInt(var2));
               } else if (var13.equals(Long.TYPE)) {
                  var4.setLong(this.model, Long.parseLong(var2));
               } else if (var13.equals(Boolean.TYPE)) {
                  if (var2.trim().toLowerCase().equals("true")) {
                     var4.setBoolean(this.model, true);
                  } else {
                     var4.setBoolean(this.model, false);
                  }
               } else if (var13.equals(Character.TYPE)) {
                  var4.setChar(this.model, var2.charAt(0));
               } else {
                  if (!var13.equals(strClass)) {
                     return false;
                  }

                  var4.set(this.model, var2);
               }

               return true;
            } else {
               boolean var5 = true;
               Object var6 = var4.get(this.model);
               int var7 = 0;
               int var8 = Array.getLength(var6);
               Class var9 = var4.getType().getComponentType();
               StringTokenizer var10 = new StringTokenizer(var2, var3);
               if (var8 < var10.countTokens()) {
                  this.errorMessage("Warning: there are less elements in the array than values provided!");
               } else if (var8 > var10.countTokens()) {
                  this.errorMessage("Warning: there are more elements in the array than values provided!");
               }

               for(; var10.hasMoreTokens() && var7 < var8; ++var7) {
                  String var11 = var10.nextToken();
                  if (var9.equals(Double.TYPE)) {
                     Array.setDouble(var6, var7, Double.parseDouble(var11));
                  } else if (var9.equals(Float.TYPE)) {
                     Array.setFloat(var6, var7, Float.parseFloat(var11));
                  } else if (var9.equals(Byte.TYPE)) {
                     Array.setByte(var6, var7, Byte.parseByte(var11));
                  } else if (var9.equals(Short.TYPE)) {
                     Array.setShort(var6, var7, Short.parseShort(var11));
                  } else if (var9.equals(Integer.TYPE)) {
                     Array.setInt(var6, var7, Integer.parseInt(var11));
                  } else if (var9.equals(Long.TYPE)) {
                     Array.setLong(var6, var7, Long.parseLong(var11));
                  } else if (var9.equals(Boolean.TYPE)) {
                     if (var11.trim().toLowerCase().equals("true")) {
                        Array.setBoolean(var6, var7, true);
                     } else {
                        Array.setBoolean(var6, var7, false);
                     }
                  } else if (var9.equals(Character.TYPE)) {
                     Array.setChar(var6, var7, var11.charAt(0));
                  } else if (var9.equals(strClass)) {
                     Array.set(var6, var7, var11);
                  } else {
                     var5 = false;
                  }
               }

               return var5;
            }
         } catch (Exception var12) {
            this.errorMessage(var12);
            return false;
         }
      }
   }

   public boolean setVariables(String var1) {
      return this.setVariables(var1, ";", ",");
   }

   public boolean setVariables(String var1, String var2, String var3) {
      boolean var4 = true;
      String var5 = "";
      String var6 = "";
      StringTokenizer var7 = new StringTokenizer(var1, var2);

      while(var7.hasMoreTokens()) {
         String var8 = var7.nextToken();
         int var9 = var8.indexOf(61);
         if (var9 < 0) {
            var4 = false;
         } else {
            var5 = var8.substring(0, var9).trim();
            var6 = var8.substring(var9 + 1).trim();
            boolean var10 = this.setVariable(var5, var6, var3);
            if (!var10) {
               var4 = false;
            }
         }
      }

      this.update();
      return var4;
   }

   public boolean saveState(String var1) {
      if (this.model == null) {
         return false;
      } else {
         try {
            Object var2;
            if (var1.startsWith("ejs:")) {
               var2 = new ByteArrayOutputStream();
            } else {
               var2 = new FileOutputStream(var1);
            }

            BufferedOutputStream var3 = new BufferedOutputStream((OutputStream)var2);
            ObjectOutputStream var4 = new ObjectOutputStream(var3);
            Field[] var5 = this.model.getClass().getFields();

            for(int var6 = 0; var6 < var5.length; ++var6) {
               if (var5[var6].get(this.model) instanceof Serializable) {
                  var4.writeObject(var5[var6].get(this.model));
               }
            }

            var4.close();
            if (var1.startsWith("ejs:")) {
               memory.put(var1, ((ByteArrayOutputStream)var2).toByteArray());
            }

            return true;
         } catch (Exception var7) {
            this.errorMessage("Error when trying to save" + var1);
            var7.printStackTrace(System.err);
            return false;
         }
      }
   }

   public boolean readState(String var1) {
      return this.readState(var1, (URL)null);
   }

   public boolean readState(String var1, URL var2) {
      if (this.model == null) {
         return false;
      } else {
         try {
            Object var3;
            if (var1.startsWith("ejs:")) {
               var3 = new ByteArrayInputStream((byte[])((byte[])memory.get(var1)));
            } else if (var1.startsWith("url:")) {
               String var4 = var1.substring(4);
               if (var2 != null && !var4.startsWith("http:")) {
                  var4 = var2 + var4;
               }

               var3 = (new URL(var4)).openStream();
            } else {
               var3 = new FileInputStream(var1);
            }

            BufferedInputStream var9 = new BufferedInputStream((InputStream)var3);
            ObjectInputStream var5 = new ObjectInputStream(var9);
            Field[] var6 = this.model.getClass().getFields();

            for(int var7 = 0; var7 < var6.length; ++var7) {
               if (var6[var7].get(this.model) instanceof Serializable) {
                  var6[var7].set(this.model, var5.readObject());
               }
            }

            var5.close();
            if (this.view != null) {
               this.view.initialize();
            }

            this.update();
            return true;
         } catch (Exception var8) {
            this.errorMessage("Error when trying to read " + var1);
            var8.printStackTrace(System.err);
            return false;
         }
      }
   }
}
