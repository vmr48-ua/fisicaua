package org.nfunk.jep.type;

public class DoubleNumberFactory implements NumberFactory {
   public Object createNumber(double var1) {
      return new Double(var1);
   }
}
