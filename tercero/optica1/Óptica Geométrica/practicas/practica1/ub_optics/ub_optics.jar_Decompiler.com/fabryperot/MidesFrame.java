package fabryperot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MidesFrame extends JFrame {
   private transient Vector changeListeners;
   public boolean sliders_updated = false;
   private JPanel jPanel2 = new JPanel();
   private JPanel jPanel1 = new JPanel();
   private JButton jButtonOK = new JButton();
   private JPanel jPanel19 = new JPanel();
   private JPanel jPanel18 = new JPanel();
   private JPanel jPanel17 = new JPanel();
   private JPanel jPanel16 = new JPanel();
   public SliderText sliderTextSourceSize = new SliderText();
   private JPanel jPanel14 = new JPanel();
   private FlowLayout flowLayout5 = new FlowLayout();
   public SliderText sliderTextScreenSize = new SliderText();
   public SliderText sliderTextScreenFocal = new SliderText();
   public SliderText sliderTextSourceFocal = new SliderText();
   private BorderLayout borderLayout1 = new BorderLayout();

   public MidesFrame(FabryCalc fabry1) {
      DecimalFormat df = new DecimalFormat("####");
      String S2 = df.format(fabry1.sizeSourcemax);
      this.sliderTextSourceSize = new SliderText(1.0D, fabry1.sizeSourcemax, fabry1.sizeSource, "Mida Font", "mm", "1", S2);
      this.sliderTextSourceSize.setStep((int)(fabry1.sizeSourcemax - 1.0D));
      String S1 = df.format(fabry1.fSourcemin);
      S2 = df.format(fabry1.fSourcemax);
      this.sliderTextSourceFocal = new SliderText(fabry1.fSourcemin, fabry1.fSourcemax, fabry1.fSource, "Focal L1", "mm", S1, S2);
      this.sliderTextSourceFocal.setStep((int)(fabry1.fSourcemax - fabry1.fSourcemin));
      S2 = df.format(fabry1.sizeScreenmax);
      this.sliderTextScreenSize = new SliderText(1.0D, fabry1.sizeScreenmax, fabry1.sizeScreen, "Mida Pantalla", "mm", "1", S2);
      this.sliderTextScreenSize.setStep((int)(fabry1.sizeScreenmax - 1.0D));
      S1 = df.format(fabry1.fScreenmin);
      S2 = df.format(fabry1.fScreenmax);
      this.sliderTextScreenFocal = new SliderText(fabry1.fScreenmin, fabry1.fScreenmax, fabry1.fScreen, "Focal L2", "mm", S1, S2);
      this.sliderTextScreenFocal.setStep((int)(fabry1.fScreenmax - fabry1.fScreenmin));

      try {
         this.jbInit();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public boolean postEvent(Event evt) {
      return true;
   }

   public Font getFont() {
      Font F = new Font("Dialog", 0, 12);
      return F;
   }

   private void jbInit() throws Exception {
      this.setResizable(false);
      this.setTitle("Mides i Focals");
      this.jButtonOK.setText("Ok");
      this.jButtonOK.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            MidesFrame.this.jButtonOK_actionPerformed(e);
         }
      });
      this.jPanel19.setMaximumSize(new Dimension(340, 55));
      this.jPanel19.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanel19.setMinimumSize(new Dimension(340, 55));
      this.jPanel19.setLayout(this.flowLayout5);
      this.jPanel19.setPreferredSize(new Dimension(340, 55));
      this.jPanel18.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanel18.setMaximumSize(new Dimension(340, 55));
      this.jPanel18.setMinimumSize(new Dimension(340, 55));
      this.jPanel18.setPreferredSize(new Dimension(340, 55));
      this.jPanel17.setPreferredSize(new Dimension(340, 55));
      this.jPanel17.setMinimumSize(new Dimension(340, 55));
      this.jPanel17.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanel17.setMaximumSize(new Dimension(340, 55));
      this.jPanel16.setPreferredSize(new Dimension(340, 55));
      this.jPanel16.setMinimumSize(new Dimension(340, 55));
      this.jPanel16.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanel16.setMaximumSize(new Dimension(340, 55));
      this.sliderTextSourceSize.setMaximumSize(new Dimension(330, 45));
      this.sliderTextSourceSize.setPreferredSize(new Dimension(330, 45));
      this.sliderTextSourceSize.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            MidesFrame.this.sliderTextSourceSize_stateChanged(e);
         }
      });
      this.sliderTextSourceSize.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            MidesFrame.this.sliderTextSourceSize_stateChanged(e);
         }
      });
      this.jPanel14.setMaximumSize(new Dimension(350, 250));
      this.jPanel14.setMinimumSize(new Dimension(350, 250));
      this.jPanel14.setPreferredSize(new Dimension(350, 250));
      this.sliderTextScreenSize.setMaximumSize(new Dimension(330, 45));
      this.sliderTextScreenSize.setPreferredSize(new Dimension(330, 45));
      this.sliderTextScreenSize.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            MidesFrame.this.sliderTextScreenSize_stateChanged(e);
         }
      });
      this.sliderTextScreenFocal.setMaximumSize(new Dimension(330, 45));
      this.sliderTextScreenFocal.setPreferredSize(new Dimension(330, 45));
      this.sliderTextScreenFocal.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            MidesFrame.this.sliderTextScreenFocal_stateChanged(e);
         }
      });
      this.sliderTextSourceFocal.setMaximumSize(new Dimension(330, 45));
      this.sliderTextSourceFocal.setPreferredSize(new Dimension(330, 45));
      this.sliderTextSourceFocal.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            MidesFrame.this.sliderTextSourceFocal_stateChanged(e);
         }
      });
      this.jPanel2.setLayout(this.borderLayout1);
      this.getContentPane().add(this.jPanel2, "North");
      this.jPanel14.add(this.jPanel19, (Object)null);
      this.jPanel19.add(this.sliderTextSourceSize, (Object)null);
      this.jPanel14.add(this.jPanel18, (Object)null);
      this.jPanel18.add(this.sliderTextSourceFocal, (Object)null);
      this.jPanel14.add(this.jPanel17, (Object)null);
      this.jPanel17.add(this.sliderTextScreenSize, (Object)null);
      this.jPanel14.add(this.jPanel16, (Object)null);
      this.jPanel16.add(this.sliderTextScreenFocal, (Object)null);
      this.jPanel2.add(this.jPanel14, "Center");
      this.jPanel2.add(this.jPanel1, "South");
      this.jPanel1.add(this.jButtonOK, (Object)null);
      this.sliderTextSourceSize.setSliderSize(new Dimension(25, 30), new Dimension(70, 30), new Dimension(30, 30));
      this.sliderTextScreenSize.setSliderSize(new Dimension(25, 30), new Dimension(70, 30), new Dimension(30, 30));
      this.sliderTextScreenFocal.setSliderSize(new Dimension(25, 30), new Dimension(70, 30), new Dimension(30, 30));
      this.sliderTextSourceFocal.setSliderSize(new Dimension(25, 30), new Dimension(70, 30), new Dimension(30, 30));
      this.sliderTextSourceSize.setLabelSize(new Dimension(90, 30));
      this.sliderTextScreenSize.setLabelSize(new Dimension(90, 30));
      this.sliderTextScreenFocal.setLabelSize(new Dimension(90, 30));
      this.sliderTextSourceFocal.setLabelSize(new Dimension(90, 30));
   }

   void jButtonOK_actionPerformed(ActionEvent e) {
      this.hide();
   }

   public synchronized void addChangeListener(ChangeListener l) {
      Vector v = this.changeListeners == null ? new Vector(2) : (Vector)this.changeListeners.clone();
      if (!v.contains(l)) {
         v.addElement(l);
         this.changeListeners = v;
      }

   }

   public synchronized void removeChangeListener(ChangeListener l) {
      if (this.changeListeners != null && this.changeListeners.contains(l)) {
         Vector v = (Vector)this.changeListeners.clone();
         v.removeElement(l);
         this.changeListeners = v;
      }

   }

   protected void fireStateChanged(ChangeEvent e) {
      if (this.changeListeners != null) {
         Vector listeners = this.changeListeners;
         int count = listeners.size();

         for(int i = 0; i < count; ++i) {
            ((ChangeListener)listeners.elementAt(i)).stateChanged(e);
         }
      }

   }

   void sliderTextSourceSize_stateChanged(ChangeEvent e) {
      this.fireStateChanged(e);
   }

   void sliderTextSourceFocal_stateChanged(ChangeEvent e) {
      this.fireStateChanged(e);
   }

   void sliderTextScreenSize_stateChanged(ChangeEvent e) {
      this.fireStateChanged(e);
   }

   void sliderTextScreenFocal_stateChanged(ChangeEvent e) {
      this.fireStateChanged(e);
   }
}
