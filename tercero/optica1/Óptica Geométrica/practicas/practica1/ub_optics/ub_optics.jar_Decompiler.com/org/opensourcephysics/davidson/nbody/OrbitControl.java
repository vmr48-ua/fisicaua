package org.opensourcephysics.davidson.nbody;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.opensourcephysics.display.OSPFrame;
import org.opensourcephysics.ejs.control.EjsControlFrame;

public class OrbitControl extends EjsControlFrame {
   OrbitWRApp model;

   public OrbitControl(OrbitWRApp var1) {
      super(var1, "name=controlFrame;title=Classical Trajectories;location=400,0;size=400,500;layout=border;exit=true; visible=false");
      this.model = var1;
      this.addTarget("control", this);
      this.addObject(var1.plottingPanel, "Panel", "name=drawingPanel; parent=controlFrame; position=center");
      this.add("Panel", "name=controlPanel; parent=controlFrame; layout=border; position=south");
      this.add("Panel", "name=buttonPanel;position=south;parent=controlPanel;layout=flow");
      this.add("Button", "parent=buttonPanel; text=Start; action=control.runAnimation(); name=runButton");
      this.add("Button", "parent=buttonPanel; text=Step; action=stepAnimation()");
      this.add("Button", "parent=buttonPanel; text=Reset; action=resetAnimation()");
      var1.drawingFrame.dispose();
      this.addPropertyChangeListener(var1);
      this.getFrame().addWindowListener(new WindowAdapter() {
         public final void windowClosing(WindowEvent var1) {
            OrbitControl.this.model.stopAnimation();
            OrbitControl.this.getControl("runButton").setProperty("text", "Start");
         }
      });
      if (!OSPFrame.appletMode) {
         this.getFrame().setVisible(true);
      }

   }

   public void runAnimation() {
      if (this.model.isRunning()) {
         this.model.stopAnimation();
         this.getControl("runButton").setProperty("text", "Start");
      } else {
         this.getControl("runButton").setProperty("text", "Stop");
         this.model.startAnimation();
      }

   }
}
