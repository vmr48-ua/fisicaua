package org.opensourcephysics.display3d.core;

import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLLoader;

public class Resolution {
   public static final int DIVISIONS = 0;
   public static final int MAX_LENGTH = 1;
   private int type = 0;
   private double maxLength = 1.0D;
   private int n1 = 1;
   private int n2 = 1;
   private int n3 = 1;

   public Resolution(double var1) {
      this.type = 1;
      this.maxLength = var1;
   }

   public Resolution(int var1, int var2, int var3) {
      this.type = 0;
      this.n1 = var1;
      this.n2 = var2;
      this.n3 = var3;
   }

   public final int getType() {
      return this.type;
   }

   public final double getMaxLength() {
      return this.maxLength;
   }

   public final int getN1() {
      return this.n1;
   }

   public final int getN2() {
      return this.n2;
   }

   public final int getN3() {
      return this.n3;
   }

   public static XML.ObjectLoader getLoader() {
      return new Resolution.ResolutionLoader();
   }

   public static class ResolutionLoader extends XMLLoader {
      public void saveObject(XMLControl var1, Object var2) {
         Resolution var3 = (Resolution)var2;
         var1.setValue("type", var3.type);
         var1.setValue("max length", var3.maxLength);
         var1.setValue("n1", var3.n1);
         var1.setValue("n2", var3.n2);
         var1.setValue("n3", var3.n3);
      }

      public Object createObject(XMLControl var1) {
         return new Resolution(1, 1, 1);
      }

      public Object loadObject(XMLControl var1, Object var2) {
         Resolution var3 = (Resolution)var2;
         var3.type = var1.getInt("type");
         var3.maxLength = var1.getDouble("max length");
         var3.n1 = var1.getInt("n1");
         var3.n2 = var1.getInt("n2");
         var3.n3 = var1.getInt("n3");
         return var2;
      }
   }
}
