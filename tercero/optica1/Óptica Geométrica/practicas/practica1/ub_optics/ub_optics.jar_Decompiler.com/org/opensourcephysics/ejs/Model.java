package org.opensourcephysics.ejs;

public interface Model {
   Simulation getSimulation();

   View getView();

   void reset();

   void initialize();

   void step();

   void update();
}
