package org.opensourcephysics.display2d;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public final class ContourAccumulator {
   private ArrayList accumulator = new ArrayList();

   ContourAccumulator() {
   }

   public synchronized void addLine(int var1, int var2, int var3, int var4) {
      this.accumulator.add(new LineRecord(var1, var2, var3, var4));
   }

   public synchronized void clearAccumulator() {
      this.accumulator.clear();
   }

   public void drawAll(Graphics var1) {
      ArrayList var2 = null;
      synchronized(this) {
         var2 = (ArrayList)this.accumulator.clone();
      }

      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         LineRecord var4 = (LineRecord)var3.next();
         var1.drawLine(var4.x1, var4.y1, var4.x2, var4.y2);
      }

   }
}
