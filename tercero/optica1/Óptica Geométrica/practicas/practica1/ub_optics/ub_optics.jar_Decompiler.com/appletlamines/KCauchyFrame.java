package appletlamines;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class KCauchyFrame extends JFrame {
   public String[] EixXGraph = new String[]{"Longitud d'ona (nm)", "Longitud de onda (nm)", "Wavelngth (nm)"};
   public String[] WindowTitle = new String[]{"Paràmetres del coeficient d'absorció k", "Parámetros del coeficiente de absorción k", "Absorption coefficient k parameters"};
   private JPanel jPanel1 = new JPanel();
   private JPanel jPanel2 = new JPanel();
   private Graph2DPanel graph2DPanel1 = new Graph2DPanel();
   private BorderLayout borderLayout1 = new BorderLayout();
   private transient Vector changeListeners;
   private double lambdai;
   private double lambdaf;
   private double[] K = new double[50];
   private double[] Lambda = new double[50];
   double k0;
   double D;
   int a = 0;
   protected KCauchyFrame.SliderTextListener stlisten = new KCauchyFrame.SliderTextListener();
   private FlowLayout flowLayout1 = new FlowLayout();
   private SliderText sliderText1 = new SliderText(0.0D, 20.0D, 0.0D, "k0", "e-3      ", "0", "20  ");
   private SliderText sliderText2 = new SliderText(0.0D, 5000.0D, 300.0D, "D ", "nm       ", "0", "5000");
   private JButton jButtonOK = new JButton();

   public KCauchyFrame() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      this.graph2DPanel1.backColor = Color.blue;
      this.graph2DPanel1.axisColor = Color.white;
      this.sliderText1.addChangeListener(this.stlisten);
      this.sliderText2.addChangeListener(this.stlisten);
      this.k0 = 0.001D;
      this.D = 3000.0D;
      this.lambdai = 200.0D;
      this.lambdaf = 800.0D;
      this.setLambdaNK();
      this.graph2DPanel1.TexteX = this.EixXGraph[Lamines.lang];
      this.graph2DPanel1.TexteY = "k";
      this.graph2DPanel1.addData(this.Lambda, this.K, 50, 1, Color.yellow);
      this.graph2DPanel1.xmin = this.lambdai;
      this.graph2DPanel1.xmax = this.lambdaf;
      this.graph2DPanel1.LimitsY(0);
      this.graph2DPanel1.repaint();
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
      this.setTitle(this.WindowTitle[Lamines.lang]);
      this.jPanel1.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel1.setMaximumSize(new Dimension(300, 300));
      this.jPanel1.setMinimumSize(new Dimension(300, 300));
      this.jPanel1.setPreferredSize(new Dimension(300, 300));
      this.jPanel1.setLayout(this.borderLayout1);
      this.jPanel2.setBorder(BorderFactory.createEtchedBorder());
      this.jPanel2.setMaximumSize(new Dimension(300, 150));
      this.jPanel2.setMinimumSize(new Dimension(300, 150));
      this.jPanel2.setPreferredSize(new Dimension(300, 150));
      this.jPanel2.setLayout(this.flowLayout1);
      this.graph2DPanel1.setMaximumSize(new Dimension(300, 300));
      this.graph2DPanel1.setMinimumSize(new Dimension(300, 300));
      this.graph2DPanel1.setPreferredSize(new Dimension(300, 300));
      this.sliderText1.setBorder(BorderFactory.createEtchedBorder());
      this.sliderText1.setMaximumSize(new Dimension(300, 45));
      this.sliderText1.setMinimumSize(new Dimension(300, 45));
      this.sliderText1.setPreferredSize(new Dimension(300, 45));
      this.sliderText2.setBorder(BorderFactory.createEtchedBorder());
      this.sliderText2.setMaximumSize(new Dimension(300, 45));
      this.sliderText2.setMinimumSize(new Dimension(300, 45));
      this.sliderText2.setPreferredSize(new Dimension(300, 45));
      this.jButtonOK.setText("Ok");
      this.jButtonOK.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            KCauchyFrame.this.jButtonOK_actionPerformed(e);
         }
      });
      this.getContentPane().add(this.jPanel2, "South");
      this.jPanel2.add(this.sliderText1, (Object)null);
      this.jPanel2.add(this.sliderText2, (Object)null);
      this.jPanel2.add(this.jButtonOK, (Object)null);
      this.getContentPane().add(this.jPanel1, "North");
      this.jPanel1.add(this.graph2DPanel1, "Center");
      this.sliderText1.slider.setPreferredSize(new Dimension(90, 30));
      this.sliderText2.slider.setPreferredSize(new Dimension(90, 30));
      this.sliderText1.jPanelLabel.setMaximumSize(new Dimension(20, 30));
      this.sliderText2.jPanelLabel.setMaximumSize(new Dimension(20, 30));
      this.sliderText1.jPanelSlider.setMaximumSize(new Dimension(145, 35));
      this.sliderText2.jPanelSlider.setMaximumSize(new Dimension(145, 35));
      this.sliderText1.jPanelSlider.setPreferredSize(new Dimension(145, 35));
      this.sliderText2.jPanelSlider.setPreferredSize(new Dimension(145, 35));
      this.sliderText1.jPanelText.setPreferredSize(new Dimension(120, 30));
      this.sliderText2.jPanelText.setPreferredSize(new Dimension(120, 30));
      this.sliderText1.texte.setColumns(4);
      this.sliderText2.texte.setColumns(4);
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

   public void setRange(double li, double lf) {
      if (!(li >= lf)) {
         this.lambdai = li;
         this.lambdaf = lf;
         this.setLambdaNK();
         this.graph2DPanel1.updateData(this.Lambda, this.K, 50, 0);
         this.graph2DPanel1.xmin = this.lambdai;
         this.graph2DPanel1.xmax = this.lambdaf;
         this.graph2DPanel1.LimitsY(0);
         this.graph2DPanel1.LimitsY(0);
         this.graph2DPanel1.repaint();
      }
   }

   private void setLambdaNK() {
      for(int i = 0; i < 50; ++i) {
         double lambda = this.Lambda[i] = this.lambdai + (this.lambdaf - this.lambdai) / 49.0D * (double)i;
         this.K[i] = this.k0 * Math.exp(this.D / lambda);
      }

   }

   void jButtonOK_actionPerformed(ActionEvent e) {
      this.hide();
   }

   void setSlidersEnabled(boolean a) {
      this.sliderText1.setEnabled(a);
      this.sliderText2.setEnabled(a);
   }

   void SetXAxis(String text) {
      this.graph2DPanel1.TexteX = text;
   }

   void UpdateValues(double k, double d) {
      this.k0 = k;
      this.D = d;
      this.sliderText1.valcur = k * 1000.0D;
      this.sliderText2.valcur = this.D;
      this.sliderText1.setValcur(k * 1000.0D);
      this.sliderText2.setValcur(this.D);
      this.setLambdaNK();
      this.graph2DPanel1.updateData(this.Lambda, this.K, 50, 0);
   }

   class SliderTextListener implements ChangeListener {
      public void stateChanged(ChangeEvent e) {
         KCauchyFrame.this.k0 = KCauchyFrame.this.sliderText1.valcur * 0.001D;
         KCauchyFrame.this.D = KCauchyFrame.this.sliderText2.valcur;
         KCauchyFrame.this.setLambdaNK();
         KCauchyFrame.this.graph2DPanel1.updateData(KCauchyFrame.this.Lambda, KCauchyFrame.this.K, 50, 0);
         KCauchyFrame.this.graph2DPanel1.xmin = KCauchyFrame.this.lambdai;
         KCauchyFrame.this.graph2DPanel1.xmax = KCauchyFrame.this.lambdaf;
         KCauchyFrame.this.graph2DPanel1.LimitsY(0);
         KCauchyFrame.this.graph2DPanel1.repaint();
         KCauchyFrame.this.fireStateChanged(e);
      }
   }
}
