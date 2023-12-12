package appletfibra;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FibraFrame extends JFrame {
   private transient Vector changeListeners;
   public boolean sliders_updated = false;
   private JPanel jPanel2 = new JPanel();
   private JPanel jPanel1 = new JPanel();
   private JButton jButtonOK = new JButton();
   private JPanel jPanelRadius = new JPanel();
   private JPanel jPanelCoreIndex = new JPanel();
   private JPanel jPanelCladIndex = new JPanel();
   private JPanel jPanelExp = new JPanel();
   public SliderText sliderTextFiberRadius = new SliderText();
   private FlowLayout flowLayout5 = new FlowLayout();
   public SliderText sliderTextCladdingIndex = new SliderText();
   public SliderText sliderTextExponent = new SliderText();
   public SliderText sliderTextCoreIndex = new SliderText();
   private BorderLayout borderLayout1 = new BorderLayout();
   Graph2DPanel graph2DPanel1 = new Graph2DPanel();
   JPanel jPanel3 = new JPanel();
   JTabbedPane jTabbedPane1 = new JTabbedPane();
   JPanel jPanelLength = new JPanel();
   SliderText sliderTextLength = new SliderText();
   public DataFibra DF;
   int num_punts_graf = 30;
   double[] n;
   double[] r;

   public FibraFrame() throws HeadlessException {
      this.n = new double[this.num_punts_graf];
      this.r = new double[this.num_punts_graf];

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setResizable(false);
      this.setTitle("Configura Fibra");
      this.addWindowListener(new WindowAdapter() {
         public void windowOpened(WindowEvent e) {
            FibraFrame.this.this_windowOpened(e);
         }
      });
      this.jButtonOK.setText("Ok");
      this.jButtonOK.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            FibraFrame.this.jButtonOK_actionPerformed(e);
         }
      });
      this.jPanelRadius.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanelRadius.setMinimumSize(new Dimension(350, 30));
      this.jPanelRadius.setPreferredSize(new Dimension(342, 30));
      this.jPanelRadius.setLayout(this.flowLayout5);
      this.jPanelCoreIndex.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanelCoreIndex.setMaximumSize(new Dimension(340, 55));
      this.jPanelCoreIndex.setMinimumSize(new Dimension(340, 55));
      this.jPanelCoreIndex.setPreferredSize(new Dimension(340, 55));
      this.jPanelCladIndex.setPreferredSize(new Dimension(340, 55));
      this.jPanelCladIndex.setMinimumSize(new Dimension(340, 55));
      this.jPanelCladIndex.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanelCladIndex.setMaximumSize(new Dimension(340, 55));
      this.jPanelExp.setPreferredSize(new Dimension(340, 55));
      this.jPanelExp.setMinimumSize(new Dimension(340, 55));
      this.jPanelExp.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanelExp.setMaximumSize(new Dimension(340, 55));
      this.sliderTextFiberRadius.setMaximumSize(new Dimension(330, 45));
      this.sliderTextFiberRadius.setPreferredSize(new Dimension(330, 45));
      this.sliderTextFiberRadius.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            FibraFrame.this.sliderTextFiberRadius_stateChanged(e);
         }
      });
      this.sliderTextFiberRadius.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            FibraFrame.this.sliderTextFiberRadius_stateChanged(e);
         }
      });
      this.sliderTextCladdingIndex.setMaximumSize(new Dimension(330, 45));
      this.sliderTextCladdingIndex.setPreferredSize(new Dimension(330, 45));
      this.sliderTextCladdingIndex.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            FibraFrame.this.sliderTextCladdingIndex_stateChanged(e);
         }
      });
      this.sliderTextExponent.setMaximumSize(new Dimension(330, 45));
      this.sliderTextExponent.setPreferredSize(new Dimension(330, 45));
      this.sliderTextExponent.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            FibraFrame.this.sliderTextExponent_stateChanged(e);
         }
      });
      this.sliderTextCoreIndex.setMaximumSize(new Dimension(330, 45));
      this.sliderTextCoreIndex.setPreferredSize(new Dimension(330, 45));
      this.sliderTextCoreIndex.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            FibraFrame.this.sliderTextCoreIndex_stateChanged(e);
         }
      });
      this.jPanel2.setLayout(this.borderLayout1);
      this.graph2DPanel1.setPreferredSize(new Dimension(340, 230));
      this.jPanelLength.setMaximumSize(new Dimension(340, 55));
      this.jPanelLength.setBorder(BorderFactory.createLineBorder(Color.black));
      this.jPanelLength.setMinimumSize(new Dimension(340, 55));
      this.jPanelLength.setPreferredSize(new Dimension(340, 55));
      this.sliderTextLength.setMaximumSize(new Dimension(330, 45));
      this.sliderTextLength.setPreferredSize(new Dimension(330, 45));
      this.sliderTextLength.setLabelSize(new Dimension(90, 30));
      this.sliderTextLength.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            FibraFrame.this.sliderTextLength_stateChanged(e);
         }
      });
      this.sliderTextLength.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            FibraFrame.this.sliderTextLength_stateChanged(e);
         }
      });
      this.jTabbedPane1.setOpaque(true);
      this.jTabbedPane1.setPreferredSize(new Dimension(347, 87));
      this.getContentPane().add(this.jPanel2, "North");
      this.jTabbedPane1.add(this.jPanelRadius, "Radi Nucli");
      this.jPanelRadius.add(this.sliderTextFiberRadius, (Object)null);
      this.jTabbedPane1.add(this.jPanelCoreIndex, "Index Nucli");
      this.jPanelCoreIndex.add(this.sliderTextCoreIndex, (Object)null);
      this.jTabbedPane1.add(this.jPanelCladIndex, "Index Clad");
      this.jPanelCladIndex.add(this.sliderTextCladdingIndex, (Object)null);
      this.jTabbedPane1.add(this.jPanelExp, "Exponent");
      this.jPanelExp.add(this.sliderTextExponent, (Object)null);
      this.jTabbedPane1.add(this.jPanelLength, "Longitud");
      this.jPanelLength.add(this.sliderTextLength, (Object)null);
      this.getContentPane().add(this.jPanel1, "South");
      this.jPanel1.add(this.jButtonOK, (Object)null);
      this.jPanel2.add(this.jPanel3, "North");
      this.jPanel3.add(this.graph2DPanel1, (Object)null);
      this.jPanel2.add(this.jTabbedPane1, "Center");
      this.sliderTextFiberRadius.setSliderSize(new Dimension(25, 30), new Dimension(70, 30), new Dimension(30, 30));
      this.sliderTextCladdingIndex.setSliderSize(new Dimension(25, 30), new Dimension(70, 30), new Dimension(30, 30));
      this.sliderTextExponent.setSliderSize(new Dimension(25, 30), new Dimension(70, 30), new Dimension(30, 30));
      this.sliderTextCoreIndex.setSliderSize(new Dimension(25, 30), new Dimension(70, 30), new Dimension(30, 30));
      this.sliderTextFiberRadius.setLabelSize(new Dimension(90, 30));
      this.sliderTextCladdingIndex.setLabelSize(new Dimension(90, 30));
      this.sliderTextExponent.setLabelSize(new Dimension(90, 30));
      this.sliderTextCoreIndex.setLabelSize(new Dimension(90, 30));
      this.sliderTextLength.setSliderSize(new Dimension(25, 30), new Dimension(70, 30), new Dimension(30, 30));
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

   void sliderTextFiberRadius_stateChanged(ChangeEvent e) {
      this.fireStateChanged(e);
      if (this.isVisible()) {
         this.UpdateGrafica();
      }

   }

   void sliderTextCoreIndex_stateChanged(ChangeEvent e) {
      this.fireStateChanged(e);
      if (this.isVisible()) {
         this.UpdateGrafica();
      }

   }

   void sliderTextCladdingIndex_stateChanged(ChangeEvent e) {
      this.fireStateChanged(e);
      if (this.isVisible()) {
         this.UpdateGrafica();
      }

   }

   void sliderTextExponent_stateChanged(ChangeEvent e) {
      this.fireStateChanged(e);
      if (this.isVisible()) {
         this.UpdateGrafica();
      }

   }

   void sliderTextLength_stateChanged(ChangeEvent e) {
      this.fireStateChanged(e);
      if (this.isVisible()) {
         this.UpdateGrafica();
      }

   }

   public static void main(String[] args) throws HeadlessException {
      new FibraFrame();
   }

   void UpdateGrafica() {
      this.graph2DPanel1.resetData();
      int num1 = (int)((double)this.num_punts_graf / 1.1D);
      int num2 = this.num_punts_graf - num1;
      double a = this.DF.radi * 1.1D;

      int i;
      for(i = 0; i < num1 - 1; ++i) {
         this.r[i] = this.DF.radi / (double)(num1 - 1) * (double)i;
         this.n[i] = this.DF.index(this.r[i]);
      }

      this.r[num1 - 1] = this.DF.radi;
      this.n[num1 - 1] = this.DF.index(this.r[i] - 1.0E-4D);
      this.r[num1] = this.DF.radi;
      this.n[num1] = this.DF.index(this.r[i] + 1.0E-4D);

      for(i = 0; i < num2 - 1; ++i) {
         this.r[i + num1 + 1] = this.DF.radi + this.DF.radi * 0.1D / (double)(num2 - 2) * (double)i;
         this.n[i + num1 + 1] = this.DF.index(this.r[i + num1 + 1]);
      }

      Graph2DPanel var10004 = this.graph2DPanel1;
      this.graph2DPanel1.addData(this.r, this.n, this.num_punts_graf, 1, Color.blue);
      this.graph2DPanel1.LimitsX(0);
      this.graph2DPanel1.LimitsY(0);
      Graph2DPanel var10000 = this.graph2DPanel1;
      var10000.ymax += 0.01D;
      var10000 = this.graph2DPanel1;
      var10000.ymin -= 0.01D;
      this.graph2DPanel1.TexteX = AppletFibra.textGraphProfileX[AppletFibra.lang];
      this.graph2DPanel1.TexteY = "n";
      this.graph2DPanel1.repaint();
   }

   void this_windowOpened(WindowEvent e) {
      if (this.isVisible()) {
         this.UpdateGrafica();
      }

   }

   public void SetTexts() {
      this.setTitle(AppletFibra.textTitleConfig[AppletFibra.lang]);
      this.jTabbedPane1.setTitleAt(0, AppletFibra.textFiberRadius[AppletFibra.lang]);
      this.jTabbedPane1.setTitleAt(1, AppletFibra.textCoreIndex[AppletFibra.lang]);
      this.jTabbedPane1.setTitleAt(2, AppletFibra.textCladIndex[AppletFibra.lang]);
      this.jTabbedPane1.setTitleAt(3, AppletFibra.textExponent[AppletFibra.lang]);
      this.jTabbedPane1.setTitleAt(4, AppletFibra.textLength[AppletFibra.lang]);
   }
}
