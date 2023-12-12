package org.opensourcephysics.numerics;

import java.util.ArrayList;

public class Hermite {
   static final ArrayList hermiteList = new ArrayList();
   static final Polynomial twoX = new Polynomial(new double[]{0.0D, 2.0D});

   private Hermite() {
   }

   public static synchronized Polynomial getPolynomial(int var0) {
      if (var0 < hermiteList.size()) {
         return (Polynomial)hermiteList.get(var0);
      } else {
         Polynomial var1 = getPolynomial(var0 - 1).multiply(twoX);
         Polynomial var2 = getPolynomial(var0 - 2).multiply((double)(2 * (var0 - 1)));
         Polynomial var3 = var1.subtract(var2);
         hermiteList.add(var3);
         return var3;
      }
   }

   static {
      Polynomial var0 = new Polynomial(new double[]{1.0D});
      hermiteList.add(var0);
      var0 = new Polynomial(new double[]{0.0D, 2.0D});
      hermiteList.add(var0);
   }
}
