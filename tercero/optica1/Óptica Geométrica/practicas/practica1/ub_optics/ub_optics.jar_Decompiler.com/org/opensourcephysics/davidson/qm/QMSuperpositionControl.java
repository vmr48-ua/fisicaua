package org.opensourcephysics.davidson.qm;

import java.awt.Container;
import org.opensourcephysics.display.DrawingFrame;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.GUIUtils;
import org.opensourcephysics.display.OSPFrame;
import org.opensourcephysics.ejs.control.EjsControlFrame;

public class QMSuperpositionControl extends EjsControlFrame {
   QMSuperpositionApp model;

   public QMSuperpositionControl(QMSuperpositionApp var1, String[] var2) {
      super(var1, "name=controlFrame;title=QM Superpostion;location=400,0;layout=border;exit=true; visible=false");
      this.model = var1;
      this.addTarget("control", this);
      if (var1.dataPanel == null) {
         this.addObject(var1.psiPanel, "Panel", "name=drawingPanel; parent=controlFrame; position=center");
         var1.psiFrame.setDrawingPanel((DrawingPanel)null);
         var1.psiFrame.dispose();
      } else {
         this.addObject(var1.dataPanel, "Panel", "name=drawingPanel; parent=controlFrame; position=center");
         var1.dataFrame.dispose();
         if (var1.dataFrame instanceof DrawingFrame) {
            ((DrawingFrame)var1.dataFrame).setDrawingPanel((DrawingPanel)null);
         }
      }

      this.add("Panel", "name=controlPanel; parent=controlFrame; layout=border; position=south");
      this.add("Panel", "name=buttonPanel;position=south;parent=controlPanel;layout=flow");
      this.add("Button", "parent=buttonPanel; text=Start; action=control.runAnimation();name=runButton");
      this.add("Button", "parent=buttonPanel; text=Step; action=control.stepAnimation()");
      this.add("Button", "parent=buttonPanel; text=Reset; action=control.resetAnimation()");
      var1.setControl(this);
      this.loadXML(var2);
      var1.initializeAnimation();
      Container var3 = (Container)this.getElement("controlFrame").getComponent();
      if (!OSPFrame.appletMode) {
         var3.setVisible(true);
      }

      this.addPropertyChangeListener(var1);
      this.getFrame().pack();
      this.getFrame().doLayout();
      GUIUtils.showDrawingAndTableFrames();
   }

   public void resetAnimation() {
      this.model.resetAnimation();
      this.getControl("runButton").setProperty("text", "Start");
      GUIUtils.showDrawingAndTableFrames();
   }

   public void stepAnimation() {
      this.model.stopAnimation();
      this.getControl("runButton").setProperty("text", "Start");
      this.model.stepAnimation();
      GUIUtils.repaintAnimatedFrames();
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
