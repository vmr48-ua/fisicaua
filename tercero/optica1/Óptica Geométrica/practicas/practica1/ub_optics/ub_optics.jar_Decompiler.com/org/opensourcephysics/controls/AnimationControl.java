package org.opensourcephysics.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import org.opensourcephysics.display.GUIUtils;

public class AnimationControl extends OSPControl {
   static final String resetToolTipText;
   static final String initToolTipText;
   static final String startToolTipText;
   static final String stopToolTipText;
   static final String newToolTipText;
   static final String stepToolTipText;
   boolean stepModeEditing = true;
   ArrayList customButtons = new ArrayList();
   JButton startBtn;
   JButton stepBtn;
   JButton resetBtn;

   public AnimationControl(Animation var1) {
      super(var1);
      this.startBtn = new JButton(ControlsRes.ANIMATION_INIT);
      this.stepBtn = new JButton(ControlsRes.ANIMATION_STEP);
      this.resetBtn = new JButton(ControlsRes.ANIMATION_RESET);
      if (super.model != null) {
         String var2 = super.model.getClass().getName();
         this.setTitle(var2.substring(1 + var2.lastIndexOf(".")) + " Controller");
      }

      this.startBtn.addActionListener(new AnimationControl.StartBtnListener());
      this.startBtn.setToolTipText(initToolTipText);
      this.stepBtn.addActionListener(new AnimationControl.StepBtnListener());
      this.stepBtn.setToolTipText(stepToolTipText);
      this.resetBtn.addActionListener(new AnimationControl.ResetBtnListener());
      this.resetBtn.setToolTipText(resetToolTipText);
      this.stepBtn.setEnabled(false);
      super.buttonPanel.add(this.startBtn);
      super.buttonPanel.add(this.stepBtn);
      super.buttonPanel.add(this.resetBtn);
      this.validate();
      this.pack();
   }

   public JButton addButton(String var1, String var2, String var3) {
      JButton var4 = super.addButton(var1, var2, var3);
      this.customButtons.add(var4);
      return var4;
   }

   public void calculationDone(final String var1) {
      if (super.model instanceof Animation) {
         ((Animation)super.model).stopAnimation();
      }

      Runnable var2 = new Runnable() {
         public void run() {
            AnimationControl.this.startBtnActionPerformed(new ActionEvent(this, 0, ControlsRes.ANIMATION_STOP));
            AnimationControl.this.resetBtnActionPerformed(new ActionEvent(this, 0, ControlsRes.ANIMATION_NEW));
            AnimationControl.this.resetBtn.setEnabled(true);
            GUIUtils.enableMenubars(true);
            if (var1 != null) {
               AnimationControl.this.println(var1);
            }

         }
      };

      try {
         if (SwingUtilities.isEventDispatchThread()) {
            var2.run();
         } else {
            SwingUtilities.invokeAndWait(var2);
         }
      } catch (InvocationTargetException var4) {
      } catch (InterruptedException var5) {
      }

   }

   void startBtnActionPerformed(ActionEvent var1) {
      if (var1.getActionCommand().equals(ControlsRes.ANIMATION_INIT)) {
         this.stepBtn.setEnabled(true);
         this.startBtn.setText(ControlsRes.ANIMATION_START);
         this.startBtn.setToolTipText(startToolTipText);
         this.resetBtn.setText(ControlsRes.ANIMATION_NEW);
         this.resetBtn.setToolTipText(newToolTipText);
         this.resetBtn.setEnabled(true);
         super.readItem.setEnabled(this.stepModeEditing);
         super.table.setEnabled(this.stepModeEditing);
         super.messageTextArea.setEditable(false);
         GUIUtils.clearDrawingFrameData(false);
         if (super.model == null) {
            this.println("This AnimationControl's model is null.");
         } else {
            ((Animation)super.model).initializeAnimation();
         }

         GUIUtils.showDrawingAndTableFrames();
      } else if (var1.getActionCommand().equals(ControlsRes.ANIMATION_START)) {
         this.setCustomButtonsEnabled(false);
         this.startBtn.setText(ControlsRes.ANIMATION_STOP);
         this.startBtn.setToolTipText(stopToolTipText);
         this.stepBtn.setEnabled(false);
         this.resetBtn.setEnabled(false);
         super.readItem.setEnabled(false);
         super.table.setEnabled(false);
         GUIUtils.enableMenubars(false);
         ((Animation)super.model).startAnimation();
      } else {
         this.startBtn.setText(ControlsRes.ANIMATION_START);
         this.setCustomButtonsEnabled(true);
         this.startBtn.setToolTipText(startToolTipText);
         this.stepBtn.setEnabled(true);
         this.resetBtn.setEnabled(true);
         GUIUtils.enableMenubars(true);
         super.readItem.setEnabled(this.stepModeEditing);
         super.table.setEnabled(this.stepModeEditing);
         ((Animation)super.model).stopAnimation();
      }

   }

   void resetBtnActionPerformed(ActionEvent var1) {
      if (var1.getActionCommand().equals(ControlsRes.ANIMATION_RESET)) {
         GUIUtils.clearDrawingFrameData(true);
         if (super.model == null) {
            this.println("This AnimationControl's model is null.");
            return;
         }

         ((Animation)super.model).resetAnimation();
         if (super.xmlDefault != null) {
            super.xmlDefault.loadObject(this.getOSPApp());
         }
      } else {
         this.startBtn.setText(ControlsRes.ANIMATION_INIT);
         this.startBtn.setToolTipText(initToolTipText);
         this.resetBtn.setText(ControlsRes.ANIMATION_RESET);
         this.resetBtn.setToolTipText(resetToolTipText);
         this.stepBtn.setEnabled(false);
         super.readItem.setEnabled(true);
         super.table.setEnabled(true);
         super.messageTextArea.setEditable(true);
         this.setCustomButtonsEnabled(true);
      }

   }

   void stepBtnActionPerformed(ActionEvent var1) {
      ((Animation)super.model).stepAnimation();
   }

   private void setCustomButtonsEnabled(boolean var1) {
      if (this.customButtons != null) {
         Iterator var2 = this.customButtons.iterator();

         while(var2.hasNext()) {
            ((JButton)var2.next()).setEnabled(var1);
         }
      }

   }

   public static XML.ObjectLoader getLoader() {
      return new AnimationControl.AnimationControlLoader();
   }

   public static AnimationControl createApp(Animation var0) {
      AnimationControl var1 = new AnimationControl(var0);
      var0.setControl(var1);
      return var1;
   }

   public static AnimationControl createApp(Animation var0, String[] var1) {
      AnimationControl var2 = createApp(var0);
      var2.loadXML(var1);
      return var2;
   }

   static {
      resetToolTipText = ControlsRes.ANIMATION_RESET_TIP;
      initToolTipText = ControlsRes.ANIMATION_INIT_TIP;
      startToolTipText = ControlsRes.ANIMATION_START_TIP;
      stopToolTipText = ControlsRes.ANIMATION_STOP_TIP;
      newToolTipText = ControlsRes.ANIMATION_NEW_TIP;
      stepToolTipText = ControlsRes.ANIMATION_STEP_TIP;
   }

   static class AnimationControlLoader extends OSPControl.OSPControlLoader {
      public void saveObject(XMLControl var1, Object var2) {
         AnimationControl var3 = (AnimationControl)var2;
         if (var3.startBtn.getText().equals(ControlsRes.ANIMATION_STOP)) {
            var3.startBtn.doClick();
         }

         var1.setValue("initialize_mode", var3.startBtn.getText().equals(ControlsRes.ANIMATION_INIT));
         super.saveObject(var1, var2);
      }

      public Object createObject(XMLControl var1) {
         return new AnimationControl((Animation)null);
      }

      public Object loadObject(XMLControl var1, Object var2) {
         AnimationControl var3 = (AnimationControl)var2;
         if (var3.startBtn.getText().equals(ControlsRes.ANIMATION_STOP)) {
            var3.startBtn.doClick();
         }

         boolean var4 = var1.getBoolean("initialize_mode");
         var1.setValue("initialize_mode", (Object)null);
         super.loadObject(var1, var2);
         if (var4) {
            var1.setValue("initialize_mode", true);
         }

         if (var4 && var3.startBtn.getText().equals(ControlsRes.ANIMATION_START)) {
            var3.resetBtn.doClick();
         }

         if (!var4 && var3.startBtn.getText().equals(ControlsRes.ANIMATION_INIT)) {
            var3.startBtn.doClick();
         }

         var3.clearMessages();
         return var2;
      }
   }

   class StepBtnListener implements ActionListener {
      public void actionPerformed(ActionEvent var1) {
         AnimationControl.this.stepBtnActionPerformed(var1);
      }
   }

   class ResetBtnListener implements ActionListener {
      public void actionPerformed(ActionEvent var1) {
         AnimationControl.this.resetBtnActionPerformed(var1);
      }
   }

   class StartBtnListener implements ActionListener {
      public void actionPerformed(ActionEvent var1) {
         AnimationControl.this.startBtnActionPerformed(var1);
      }
   }
}
