package org.opensourcephysics.davidson.electrodynamics;

import java.awt.Container;
import org.opensourcephysics.display.OSPFrame;
import org.opensourcephysics.ejs.control.EjsControlFrame;

public class RadiationControl extends EjsControlFrame {
   RadiationWRApp model;

   public RadiationControl(RadiationWRApp var1) {
      super(var1, "name=controlFrame;title=Electric Field;location=400,0;layout=border;exit=true; visible=false");
      this.model = var1;
      this.addTarget("control", this);
      this.addObject(var1.vectorPanel, "Panel", "name=drawingPanel; parent=controlFrame; position=center");
      this.add("Panel", "name=controlPanel; parent=controlFrame; layout=border; position=south");
      this.add("Panel", "name=buttonPanel;position=south;parent=controlPanel;layout=flow");
      this.add("Button", "parent=buttonPanel; text=Start; action=control.runAnimation();name=runButton");
      this.add("Button", "parent=buttonPanel; text=Step; action=stepAnimation()");
      this.add("Button", "parent=buttonPanel; text=Reset; action=resetAnimation()");
      this.add("CheckBox", "parent=buttonPanel;variable=showBField;text=B;selected=false;action=showB();");
      var1.getManager().clearViews();
      var1.getManager().addView("vectorPanel", var1.vectorPanel);
      var1.getManager().addView("contourPanel", var1.contourPanel);
      var1.getManager().addView("contourFrame", var1.contourFrame);
      Container var2 = (Container)this.getElement("controlFrame").getComponent();
      var1.getManager().addView("controlFrame", var2);
      if (!OSPFrame.appletMode) {
         var2.setVisible(true);
      }

      var1.vectorFrame.setKeepHidden(true);
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
