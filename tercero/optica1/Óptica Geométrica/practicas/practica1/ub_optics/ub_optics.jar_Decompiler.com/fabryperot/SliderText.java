package fabryperot;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderText extends JPanel {
   public double valmax;
   public double valmin;
   public double valcur;
   private int step = 1000;
   DecimalFormat DF = new DecimalFormat("#.##");
   private transient Vector changeListeners;
   JPanel jPanelLabel = new JPanel();
   JLabel label_titol = new JLabel("SL_TEXT");
   JPanel jPanelSlider = new JPanel();
   JLabel label_vmin = new JLabel("0");
   JSlider slider;
   JLabel label_vmax;
   JPanel jPanelText;
   JLabel label_units;
   private FlowLayout flowLayout1;
   private FlowLayout flowLayout2;
   private Border border1;
   JLabel texte;

   private void jbInit() throws Exception {
      this.border1 = BorderFactory.createEmptyBorder();
      this.setLayout(this.flowLayout2);
      this.setMaximumSize(new Dimension(330, 50));
      this.setMinimumSize(new Dimension(10, 34));
      this.setPreferredSize(new Dimension(330, 50));
      this.label_vmin.setFont(new Font("Dialog", 0, 10));
      this.label_vmin.setAlignmentY(0.0F);
      this.label_vmin.setIconTextGap(0);
      this.slider.setOrientation(0);
      this.slider.setAlignmentX(0.0F);
      this.slider.setAlignmentY(0.0F);
      this.slider.setMaximumSize(new Dimension(60, 30));
      this.slider.setMinimumSize(new Dimension(60, 30));
      this.slider.setPreferredSize(new Dimension(60, 30));
      this.label_vmax.setFont(new Font("Dialog", 0, 10));
      this.label_vmax.setAlignmentY(0.0F);
      this.label_vmax.setIconTextGap(0);
      this.jPanelLabel.setAlignmentX(0.0F);
      this.jPanelLabel.setAlignmentY(0.0F);
      this.jPanelSlider.setMaximumSize(new Dimension(120, 40));
      this.jPanelSlider.setMinimumSize(new Dimension(120, 40));
      this.jPanelSlider.setPreferredSize(new Dimension(120, 40));
      this.jPanelText.setLayout(this.flowLayout1);
      this.flowLayout1.setAlignment(0);
      this.flowLayout2.setAlignment(0);
      this.flowLayout2.setHgap(1);
      this.flowLayout2.setVgap(1);
      this.jPanelText.setBorder(this.border1);
      this.label_titol.setFont(new Font("Dialog", 0, 10));
      this.label_units.setFont(new Font("Dialog", 0, 10));
      this.texte.setText(this.DF.format(this.valcur));
      this.texte.setFont(new Font("Dialog", 0, 10));
      this.add(this.jPanelLabel, (Object)null);
      this.jPanelLabel.add(this.label_titol, (Object)null);
      this.add(this.jPanelSlider, (Object)null);
      this.jPanelSlider.add(this.label_vmin, (Object)null);
      this.jPanelSlider.add(this.slider, (Object)null);
      this.jPanelSlider.add(this.label_vmax, (Object)null);
      this.add(this.jPanelText, (Object)null);
      this.jPanelText.add(this.texte, (Object)null);
      this.jPanelText.add(this.label_units, (Object)null);
   }

   public synchronized void removeChangeListener(ChangeListener l) {
      if (this.changeListeners != null && this.changeListeners.contains(l)) {
         Vector v = (Vector)this.changeListeners.clone();
         v.removeElement(l);
         this.changeListeners = v;
      }

   }

   public synchronized void addChangeListener(ChangeListener l) {
      Vector v = this.changeListeners == null ? new Vector(2) : (Vector)this.changeListeners.clone();
      if (!v.contains(l)) {
         v.addElement(l);
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

   public SliderText(double vmin, double vmax, double vcur, String Titol, String Units, String Vmin, String Vmax) {
      this.slider = new JSlider(0, this.step, this.step / 2);
      this.label_vmax = new JLabel("100");
      this.jPanelText = new JPanel();
      this.label_units = new JLabel("Units");
      this.flowLayout1 = new FlowLayout();
      this.flowLayout2 = new FlowLayout();
      this.texte = new JLabel();
      this.valmin = vmin;
      this.valmax = vmax;
      this.valcur = vcur;
      double ample = vmax - vmin;
      if (ample == 0.0D) {
         ample = 1.0E-100D;
      }

      int pos_slider = (int)((vcur - vmin) / ample * (double)this.step);
      this.slider = new JSlider(0, this.step, pos_slider);
      this.texte = new JLabel(this.DF.format(vcur));
      this.label_titol = new JLabel(Titol);
      this.label_units = new JLabel(Units);
      this.label_vmin = new JLabel(Vmin);
      this.label_vmax = new JLabel(Vmax);
      this.slider.addChangeListener(new SliderText.SliderListener());

      try {
         this.jbInit();
      } catch (Exception var15) {
         var15.printStackTrace();
      }

      DecimalFormatSymbols DFS = new DecimalFormatSymbols();
      DFS.setDecimalSeparator('.');
      this.DF.setDecimalFormatSymbols(DFS);
   }

   public SliderText() {
      this.slider = new JSlider(0, this.step, this.step / 2);
      this.label_vmax = new JLabel("100");
      this.jPanelText = new JPanel();
      this.label_units = new JLabel("Units");
      this.flowLayout1 = new FlowLayout();
      this.flowLayout2 = new FlowLayout();
      this.texte = new JLabel();
      this.valmin = 0.0D;
      this.valmax = 100.0D;
      this.valcur = 50.0D;
      double ample = this.valmax - this.valmin;
      if (ample == 0.0D) {
         ample = 1.0E-100D;
      }

      int var10000 = (int)((this.valcur - this.valmin) / ample * (double)this.step);
      this.slider.addChangeListener(new SliderText.SliderListener());

      try {
         this.jbInit();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      DecimalFormatSymbols DFS = new DecimalFormatSymbols();
      DFS.setDecimalSeparator('.');
      this.DF.setDecimalFormatSymbols(DFS);
   }

   void setLabelFont(Font F) {
      this.label_titol.setFont(F);
      this.label_vmin.setFont(F);
      this.label_vmax.setFont(F);
      this.label_units.setFont(F);
      this.texte.setFont(F);
   }

   public void setValcur(double v) {
      double ample = this.valmax - this.valmin;
      if (ample == 0.0D) {
         ample = 1.0E-100D;
      }

      this.valcur = v;
      int pos_slider = (int)((this.valcur - this.valmin) / ample * (double)this.step);
      this.slider.setValue(pos_slider);
   }

   public void setRange(double vmin, double vmax) {
      this.valmin = vmin;
      this.valmax = vmax;
      this.label_vmin.setText(String.valueOf((int)vmin));
      this.label_vmax.setText(String.valueOf((int)vmax));
      double ample = this.valmax - this.valmin;
      if (ample == 0.0D) {
         ample = 1.0E-100D;
      }

      int pos_slider = (int)((this.valcur - this.valmin) / ample * (double)this.step);
      this.slider.setValue(pos_slider);
   }

   public void setEnabled(boolean t) {
      super.setEnabled(t);
      this.slider.setEnabled(t);
      this.texte.setEnabled(t);
      this.label_titol.setEnabled(t);
      this.label_units.setEnabled(t);
      this.label_vmax.setEnabled(t);
      this.label_vmin.setEnabled(t);
   }

   public void setSliderSize(Dimension d1, Dimension d2, Dimension d3) {
      this.label_vmin.setMinimumSize(d1);
      this.label_vmin.setMaximumSize(d1);
      this.label_vmin.setPreferredSize(d1);
      this.slider.setMinimumSize(d2);
      this.slider.setMaximumSize(d2);
      this.slider.setPreferredSize(d2);
      this.label_vmax.setMinimumSize(d3);
      this.label_vmax.setMaximumSize(d3);
      this.label_vmax.setPreferredSize(d3);
      int sizex = d1.width + d2.width + d3.width + 20;
      int sizey = Math.max(d1.height, Math.max(d2.height, d3.height)) + 10;
      this.jPanelSlider.setPreferredSize(new Dimension(sizex, sizey));
      this.jPanelSlider.setMaximumSize(new Dimension(sizex, sizey));
      this.jPanelSlider.setMinimumSize(new Dimension(sizex, sizey));
   }

   public void setLabelSize(Dimension d) {
      this.label_titol.setMinimumSize(d);
      this.label_titol.setMaximumSize(d);
      this.label_titol.setPreferredSize(d);
   }

   public void setUnitsSize(Dimension d) {
      this.label_units.setMinimumSize(d);
      this.label_units.setMaximumSize(d);
      this.label_units.setPreferredSize(d);
   }

   public void setStep(int myStep) {
      this.step = myStep;
      int pos_slider = (int)((this.valcur - this.valmin) / (this.valmax - this.valmin) * (double)this.step);
      this.slider.setMaximum(this.step);
      this.slider.setValue(pos_slider);
   }

   public void setDecimalFormat(DecimalFormat newDF) {
      this.DF = newDF;
   }

   class TextListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         JTextField tf = (JTextField)e.getSource();
         SliderText.this.valcur = Double.parseDouble(tf.getText());
         int pos_slider = (int)((SliderText.this.valcur - SliderText.this.valmin) / (SliderText.this.valmax - SliderText.this.valmin) * (double)SliderText.this.step);
         SliderText.this.slider.setValue(pos_slider);
      }
   }

   class SliderListener implements ChangeListener {
      public void stateChanged(ChangeEvent e) {
         int fps = ((JSlider)e.getSource()).getValue();
         SliderText.this.valcur = SliderText.this.valmin + (SliderText.this.valmax - SliderText.this.valmin) / (double)SliderText.this.step * (double)fps;
         SliderText.this.texte.setText(SliderText.this.DF.format(SliderText.this.valcur));
         SliderText.this.fireStateChanged(e);
      }
   }
}
