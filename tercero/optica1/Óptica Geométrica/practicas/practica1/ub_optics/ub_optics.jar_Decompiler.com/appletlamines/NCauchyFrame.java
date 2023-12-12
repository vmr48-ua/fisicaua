package appletlamines;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
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

public class NCauchyFrame extends JFrame {
   public String[] EixXGraph = new String[]{"Longitud d'ona (nm)", "Longitud de onda (nm)", "Wavelngth (nm)"};
   public String[] WindowTitle = new String[]{"Paràmetres de l'índex de refracció n", "Parámetros del índice de refracción n", "Refraction index n parameters"};
   private JPanel jPanel1 = new JPanel();
   private JPanel jPanel2 = new JPanel();
   private Graph2DPanel graph2DPanel1 = new Graph2DPanel();
   private BorderLayout borderLayout1 = new BorderLayout();
   private transient Vector changeListeners;
   private double lambdai;
   private double lambdaf;
   private double[] N = new double[50];
   private double[] Lambda = new double[50];
   double A;
   double B;
   double C;
   int a = 0;
   protected NCauchyFrame.SliderTextListener stlisten = new NCauchyFrame.SliderTextListener();
   private SliderText sliderText1 = new SliderText(1.0D, 3.0D, 2.71D, "A ", "-         ", "1", "3    ");
   private SliderText sliderText2 = new SliderText(0.0D, 100.0D, 2.64D, "B ", "e3 (nm^2) ", "0", "100  ");
   private SliderText sliderText3 = new SliderText(0.0D, 1000.0D, 1.3D, "C ", "e6 (nm^4)", "0", "1000 ");
   private JButton jButtonOK = new JButton();

   public NCauchyFrame() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      this.graph2DPanel1.backColor = Color.blue;
      this.graph2DPanel1.axisColor = Color.white;
      this.sliderText1.addChangeListener(this.stlisten);
      this.sliderText2.addChangeListener(this.stlisten);
      this.sliderText3.addChangeListener(this.stlisten);
      this.A = 2.71D;
      this.B = 2640.0D;
      this.C = 1.3E9D;
      this.lambdai = 200.0D;
      this.lambdaf = 800.0D;
      this.setLambdaNK();
      this.graph2DPanel1.TexteX = this.EixXGraph[Lamines.lang];
      this.graph2DPanel1.TexteY = "n";
      this.graph2DPanel1.addData(this.Lambda, this.N, 50, 1, Color.yellow);
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
      this.jPanel2.setMaximumSize(new Dimension(300, 190));
      this.jPanel2.setMinimumSize(new Dimension(300, 190));
      this.jPanel2.setPreferredSize(new Dimension(300, 190));
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
      this.sliderText3.setBorder(BorderFactory.createEtchedBorder());
      this.sliderText3.setMaximumSize(new Dimension(300, 45));
      this.sliderText3.setMinimumSize(new Dimension(300, 45));
      this.sliderText3.setPreferredSize(new Dimension(300, 45));
      this.jButtonOK.setText("Ok");
      this.jButtonOK.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            NCauchyFrame.this.jButtonOK_actionPerformed(e);
         }
      });
      this.getContentPane().add(this.jPanel2, "South");
      this.jPanel2.add(this.sliderText1, (Object)null);
      this.jPanel2.add(this.sliderText2, (Object)null);
      this.jPanel2.add(this.sliderText3, (Object)null);
      this.jPanel2.add(this.jButtonOK, (Object)null);
      this.getContentPane().add(this.jPanel1, "North");
      this.jPanel1.add(this.graph2DPanel1, "Center");
      this.sliderText1.slider.setPreferredSize(new Dimension(90, 30));
      this.sliderText2.slider.setPreferredSize(new Dimension(90, 30));
      this.sliderText3.slider.setPreferredSize(new Dimension(90, 30));
      this.sliderText1.jPanelLabel.setMaximumSize(new Dimension(20, 30));
      this.sliderText2.jPanelLabel.setMaximumSize(new Dimension(20, 30));
      this.sliderText3.jPanelLabel.setMaximumSize(new Dimension(20, 30));
      this.sliderText1.jPanelSlider.setMaximumSize(new Dimension(145, 35));
      this.sliderText2.jPanelSlider.setMaximumSize(new Dimension(145, 35));
      this.sliderText3.jPanelSlider.setMaximumSize(new Dimension(145, 35));
      this.sliderText1.jPanelSlider.setPreferredSize(new Dimension(145, 35));
      this.sliderText2.jPanelSlider.setPreferredSize(new Dimension(145, 35));
      this.sliderText3.jPanelSlider.setPreferredSize(new Dimension(145, 35));
      this.sliderText1.jPanelText.setPreferredSize(new Dimension(120, 30));
      this.sliderText2.jPanelText.setPreferredSize(new Dimension(120, 30));
      this.sliderText3.jPanelText.setPreferredSize(new Dimension(120, 30));
      this.sliderText1.texte.setColumns(4);
      this.sliderText2.texte.setColumns(4);
      this.sliderText3.texte.setColumns(4);
      this.sliderText1.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            NCauchyFrame.this.sliderText1_stateChanged(e);
         }
      });
   }

   void sliderText1_stateChanged(ChangeEvent e) {
      ++this.a;
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
         this.graph2DPanel1.updateData(this.Lambda, this.N, 50, 0);
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
         this.N[i] = this.A + 1.0D / (lambda * lambda) * (this.B + this.C / (lambda * lambda));
      }

   }

   void jButtonOK_actionPerformed(ActionEvent e) {
      this.hide();
   }

   void SetSlidersEnabled(boolean a) {
      this.sliderText3.setEnabled(a);
   }

   void ZeroCSlider() {
      this.sliderText3.setValcur(0.0D);
   }

   void SetXAxis(String text) {
      this.graph2DPanel1.TexteX = text;
   }

   public void UpdateValues(double a, double b, double c) {
      this.A = a;
      this.B = b;
      this.C = c;
      this.sliderText1.valcur = this.A;
      this.sliderText2.valcur = this.B * 0.001D;
      this.sliderText3.valcur = this.C * 1.0E-6D;
      this.sliderText1.setValcur(this.A);
      this.sliderText2.setValcur(this.B * 0.001D);
      this.sliderText3.setValcur(this.C * 1.0E-6D);
      this.setLambdaNK();
      this.graph2DPanel1.updateData(this.Lambda, this.N, 50, 0);
   }

   class SliderTextListener implements ChangeListener {
      public void stateChanged(ChangeEvent e) {
         NCauchyFrame.this.A = NCauchyFrame.this.sliderText1.valcur;
         NCauchyFrame.this.B = NCauchyFrame.this.sliderText2.valcur * 1000.0D;
         NCauchyFrame.this.C = NCauchyFrame.this.sliderText3.valcur * 1000000.0D;
         NCauchyFrame.this.setLambdaNK();
         NCauchyFrame.this.graph2DPanel1.updateData(NCauchyFrame.this.Lambda, NCauchyFrame.this.N, 50, 0);
         NCauchyFrame.this.graph2DPanel1.xmin = NCauchyFrame.this.lambdai;
         NCauchyFrame.this.graph2DPanel1.xmax = NCauchyFrame.this.lambdaf;
         NCauchyFrame.this.graph2DPanel1.LimitsY(0);
         NCauchyFrame.this.graph2DPanel1.repaint();
         NCauchyFrame.this.fireStateChanged(e);
      }
   }
}
