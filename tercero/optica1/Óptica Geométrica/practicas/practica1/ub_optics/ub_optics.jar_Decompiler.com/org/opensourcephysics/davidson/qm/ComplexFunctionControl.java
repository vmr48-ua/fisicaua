package org.opensourcephysics.davidson.qm;

import java.awt.Container;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import org.opensourcephysics.davidson.applets.EmptyPanel;
import org.opensourcephysics.display.OSPFrame;
import org.opensourcephysics.ejs.control.EjsControlFrame;

public class ComplexFunctionControl extends EjsControlFrame {
   ComplexFunctionWRApp model;

   public ComplexFunctionControl(ComplexFunctionWRApp var1) {
      super(var1, "name=controlFrame;title=Control Frame;location=400,0;layout=border;exit=true; visible=false");
      this.model = var1;
      this.addTarget("control", this);
      this.add("Panel", "name=contentPanel; parent=controlFrame; layout=border; position=center");
      Container var2 = (Container)this.getElement("contentPanel").getComponent();
      var2.add(var1.plotPanel, "Center");
      var1.drawingFrame.setKeepHidden(true);
      var1.drawingFrame.setContentPane(new EmptyPanel());
      this.add("Panel", "name=controlPanel; parent=contentPanel; layout=vbox; position=south");
      ((JPanel)this.getElement("controlPanel").getComponent()).setBorder(new EtchedBorder());
      this.add("Panel", "name=functionPanel; parent=controlPanel; layout=border");
      this.add("Panel", "name=buttonPanel; parent=controlPanel; layout=flow");
      this.add("Button", "parent=buttonPanel; text=Start; action=control.runAnimation();name=runButton");
      this.add("Button", "parent=buttonPanel; text=Step; action=stepAnimation()");
      this.add("Button", "parent=buttonPanel; text=Reset; action=resetAnimation()");
      this.getControl("controlFrame").setProperties("size=pack");
      this.addPropertyChangeListener(var1);
      var1.getManager().clearViews();
      var1.getManager().addView("drawingPanel", var1.plotPanel);
      var2 = (Container)this.getElement("controlPanel").getComponent();
      var1.getManager().addView("controlPanel", var2);
      var2 = (Container)this.getElement("contentPanel").getComponent();
      var1.getManager().addView("contentPanel", var2);
      var2 = (Container)this.getElement("controlFrame").getComponent();
      var1.getManager().addView("controlFrame", var2);
      if (!OSPFrame.appletMode) {
         var2.setVisible(true);
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
