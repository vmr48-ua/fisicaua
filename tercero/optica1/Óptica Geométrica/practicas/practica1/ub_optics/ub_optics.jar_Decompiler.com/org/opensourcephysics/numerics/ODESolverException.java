package org.opensourcephysics.numerics;

public class ODESolverException extends RuntimeException {
   public ODESolverException() {
      super("ODE solver failed.");
   }

   public ODESolverException(String var1) {
      super(var1);
   }
}
