package org.opensourcephysics.displayejs;

public class Resolution {
   public static final int DIVISIONS = 0;
   public static final int MAX_LENGTH = 1;
   protected int type = 0;
   protected double maxLength = 1.0D;
   protected int n1 = 1;
   protected int n2 = 1;
   protected int n3 = 1;

   public static Resolution createDivisions(double var0) {
      Resolution var2 = new Resolution(0);
      var2.type = 1;
      var2.maxLength = var0;
      return var2;
   }

   public Resolution(int var1) {
      this.type = 0;
      this.n1 = var1;
   }

   public Resolution(int var1, int var2) {
      this.type = 0;
      this.n1 = var1;
      this.n2 = var2;
   }

   public Resolution(int var1, int var2, int var3) {
      this.type = 0;
      this.n1 = var1;
      this.n2 = var2;
      this.n3 = var3;
   }
}
