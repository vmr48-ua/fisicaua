package org.opensourcephysics.controls;

import java.awt.Frame;
import java.text.DecimalFormat;

public abstract class AbstractAnimation implements Animation, Runnable {
   protected Control control;
   protected volatile Thread animationThread;
   protected int delayTime = 100;
   protected DecimalFormat decimalFormat = new DecimalFormat("0.00E0");

   public void setControl(Control var1) {
      this.control = var1;
      if (var1 != null) {
         var1.setLockValues(true);
         this.resetAnimation();
         var1.setLockValues(false);
         if (var1 instanceof Frame) {
            ((Frame)var1).pack();
         }
      }

   }

   public Control getControl() {
      return this.control;
   }

   public void initializeAnimation() {
      this.control.clearMessages();
   }

   protected abstract void doStep();

   public synchronized void stopAnimation() {
      if (this.animationThread != null) {
         Thread var1 = this.animationThread;
         this.animationThread = null;
         if (Thread.currentThread() != var1) {
            if (var1 != null && var1 != Thread.currentThread()) {
               try {
                  var1.interrupt();
                  var1.join(2000L);
               } catch (InterruptedException var3) {
               }
            }

         }
      }
   }

   public final boolean isRunning() {
      return this.animationThread != null;
   }

   public synchronized void stepAnimation() {
      if (this.animationThread != null) {
         this.stopAnimation();
      }

      this.doStep();
   }

   public synchronized void startAnimation() {
      if (this.animationThread == null) {
         this.animationThread = new Thread(this);
         this.animationThread.setPriority(1);
         this.animationThread.setDaemon(true);
         this.animationThread.start();
      }
   }

   public void resetAnimation() {
      if (this.animationThread != null) {
         this.stopAnimation();
      }

      this.control.clearMessages();
   }

   public void run() {
      long var1 = (long)this.delayTime;

      while(this.animationThread == Thread.currentThread()) {
         long var3 = System.currentTimeMillis();
         this.doStep();
         var1 = Math.max(10L, (long)this.delayTime - (System.currentTimeMillis() - var3));

         try {
            Thread.sleep(var1);
         } catch (InterruptedException var6) {
         }
      }

   }
}
