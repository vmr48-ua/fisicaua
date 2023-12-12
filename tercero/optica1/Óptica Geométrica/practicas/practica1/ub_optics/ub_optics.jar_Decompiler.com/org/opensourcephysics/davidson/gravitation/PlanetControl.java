package org.opensourcephysics.davidson.gravitation;

import java.awt.Container;
import org.opensourcephysics.display.OSPFrame;
import org.opensourcephysics.ejs.control.EjsControlFrame;

public class PlanetControl extends EjsControlFrame {
   PlanetApp model;

   public PlanetControl(PlanetApp var1) {
      super(var1, "name=controlFrame;title=Classical Trajectories;location=400,0;layout=border;exit=true; visible=false");
      this.model = var1;
      this.addTarget("control", this);
      this.addObject(var1.plottingPanel, "Panel", "name=drawingPanel; parent=controlFrame; position=center");
      this.add("Panel", "name=controlPanel; parent=controlFrame; layout=border; position=south");
      this.add("Panel", "name=buttonPanel;position=south;parent=controlPanel;layout=flow");
      this.add("Button", "parent=buttonPanel; text=Start; action=control.runAnimation();name=runButton");
      this.add("Button", "parent=buttonPanel; text=Step; action=stepAnimation()");
      this.add("Button", "parent=buttonPanel; text=Reset; action=resetAnimation()");
      var1.getManager().clearViews();
      var1.getManager().addView("plottingPanel", var1.plottingPanel);
      Container var2 = (Container)this.getElement("controlFrame").getComponent();
      var1.getManager().addView("controlFrame", var2);
      if (!OSPFrame.appletMode) {
         var2.setVisible(true);
      }

      var1.drawingFrame.setKeepHidden(true);
      this.addPropertyChangeListener(var1);
      this.getFrame().pack();
      this.getFrame().doLayout();
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
