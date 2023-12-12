import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class ExactoFC extends JFrame implements ActionListener {
   static int lang;
   static String[][] text;
   static ExactoFC exacto;
   static Color grisAzulado;
   static double lado;
   static double escritura;
   static JPanel panelBase;
   static JPanel panelObj;
   static JPanel panelImag;
   static JPanel panelMerito;
   static JPanel[] panelE;
   static JPanel[] panelVE;
   static JPanel panelDiagramas;
   static DibujoExactoFC canvasExacto;
   static DibujoDiagramasFC canvasDiagramas;
   static JButton buttonMP;
   static JButton buttonObj;
   static JButton buttonImag;
   static JButton[] buttonE;
   static String[] nomBoton;
   static JLabel labelObj;
   static JLabel labelZObj;
   static JLabel labelHObj;
   static JLabel labelValZObj;
   static JLabel labelValHObj;
   static JRadioButton buttonR;
   static JRadioButton buttonV;
   static ButtonGroup grupoO;
   static JSlider sliderValZObj;
   static ChangeListener listenerValZObj;
   static JLabel[] valPosObj;
   static String[] leyendaPosObj;
   static ChangeListener listenerValHObj;
   static JSlider sliderValHObj;
   static JLabel[] valCampo;
   static String[] leyendaCampo;
   static JLabel labelImag;
   static JLabel labelZImag;
   static JLabel labelZParax;
   static JLabel labelHImag;
   static JLabel labelValZImag;
   static JLabel labelValZParax;
   static JLabel labelValHImag;
   static JRadioButton buttonMono;
   static JRadioButton buttonColor;
   static ButtonGroup grupo1;
   static JSlider sliderValZImag;
   static ChangeListener listenerValZImag;
   static JLabel[] valPosImag;
   static String[] leyendaPosImag;
   static JSlider sliderValHImag;
   static ChangeListener listenerValHImag;
   static JLabel labelDiagramas1;
   static JLabel labelDiagramas2;
   static JLabel labelDiagramas3;
   static JLabel[] labelMerito;
   static JLabel[] labelValMerito;
   static JRadioButton[] buttonL;
   static JRadioButton[] buttonD;
   static ButtonGroup[] grupo;
   static JLabel[] labelE;
   static JLabel[] labelZE;
   static JLabel[] labelHE;
   static JLabel[] labelFE;
   static JLabel[] labelBE;
   static JLabel[] labelValZE;
   static JLabel[] labelValHE;
   static JLabel[] labelValFE;
   static JLabel[] labelValBE;
   static JLabel[] labelValR1E;
   static JLabel[] labelValR2E;
   static JLabel[] labelNE;
   static JLabel[] labelAE;
   static JLabel[] labelValNE;
   static JLabel[] labelValAE;
   static JSlider[] sliderValZE;
   static JSlider[] sliderValHE;
   static JSlider[] sliderValFE;
   static JSlider[] sliderValBE;
   static ChangeListener[] listenerValZE;
   static ChangeListener[] listenerValHE;
   static ChangeListener[] listenerValFE;
   static ChangeListener[] listenerValBE;
   static JLabel[][] valPos;
   static JLabel[][] valPot;
   static JLabel[][] valDiam;
   static String[] leyendaPos;
   static String[] leyendaPot;
   static String[] leyendaDiam;
   static JSlider[] sliderValNE;
   static JSlider[] sliderValAE;
   static ChangeListener[] listenerValNE;
   static ChangeListener[] listenerValAE;
   static String texto1;
   static String texto2;
   static String[] unidad;
   static int numLentes;
   static int numSup;
   static int nir;
   static int nia;
   static int[] elemento;
   static int numElementos;
   static int[][] tramo;
   static int[] numTramos;
   static int numColores;
   static double valZParax;
   static double valZImag;
   static double escala;
   static double[] espesor;
   static double[] valBE;
   static double[] curv1;
   static double[] curv2;
   static double[] radio1;
   static double[] radio2;
   static double[] radioDib1;
   static double[] radioDib2;
   static double[] valDib1PE;
   static double[] valDib2PE;
   static double[] dze1;
   static double[] dze2;
   static double[] dzb1;
   static double[] dzb2;
   static double[] diste;
   static double[] distb;
   static int diamMax;
   static int numRayos;
   static double[] xpr;
   static double[] ypr;
   static double[] hpr;
   static double indiceDib;
   static double[] indice;
   static double[] abbe;
   static int[] pintarRayos;
   int cuadro;
   static int dibuja;
   static int calcula;
   int i;
   static int copiar;
   static Font fuente1;
   static Font fuente2;
   static Font fuente3;
   static Font fuente4;
   Color colorTexto1;
   Color colorTexto2;

   public static void abrir() {
      SistemaFC.panelBase.add(panelBase);
      SistemaFC.panelBase.add(canvasExacto);
      SistemaFC.panelBase.add(canvasDiagramas);
      SistemaFC.panelBase.add(panelDiagramas);
      int var1;
      if (SistemaFC.valZObj[0] == -1600.0D) {
         SistemaFC.existeObjeto[0] = 0;
         labelValZObj.setText("----");
         sliderValHObj.setEnabled(false);

         for(var1 = 0; var1 < 5; ++var1) {
            valCampo[var1].setVisible(false);
         }
      } else {
         SistemaFC.existeObjeto[0] = 1;
         sliderValHObj.setEnabled(true);

         for(var1 = 0; var1 < 5; ++var1) {
            valCampo[var1].setVisible(true);
         }

         if (!(SistemaFC.valZObj[0] <= -1500.0D) && !(SistemaFC.valZObj[0] >= 1600.0D)) {
            escritura = (double)((int)(10.0D * SistemaFC.valZObj[0] / SistemaFC.factorFO)) / 10.0D;
            labelValZObj.setText(escritura + " mm");
            SistemaFC.zObjeto[0] = SistemaFC.valZObj[0];
         } else {
            labelValZObj.setText(text[13][lang]);
            SistemaFC.zObjeto[0] = -1.0E10D;
         }
      }

      if (SistemaFC.real[0] == 1) {
         buttonR.setSelected(true);
      } else {
         buttonV.setSelected(true);
      }

      labelValHObj.setText(SistemaFC.campo[0] + "º");
      sliderValZObj.setValue((int)(SistemaFC.valZObj[0] * SistemaFC.factorFO));
      sliderValHObj.setValue((int)(SistemaFC.campo[0] * 10.0D));
      if (numColores == 1) {
         buttonMono.setSelected(true);
      } else {
         buttonColor.setSelected(true);
      }

      escritura = (double)((int)(10.0D * valZImag / SistemaFC.factorFO)) / 10.0D;
      labelValZImag.setText(escritura + " mm");
      sliderValZImag.setValue((int)valZImag);
      escritura = (double)((int)(10.0D * escala / SistemaFC.factorFO)) / 10.0D;
      labelValHImag.setText(escritura + " mm");
      sliderValHImag.setValue((int)(escala * 100.0D));

      for(int var0 = 1; var0 < 7; ++var0) {
         if (SistemaFC.tipoE[var0] == 1) {
            buttonL[var0].setSelected(true);
         } else {
            buttonD[var0].setSelected(true);
         }

         escritura = (double)((int)(10.0D * SistemaFC.valZE[var0] / SistemaFC.factorFO)) / 10.0D;
         labelValZE[var0].setText(escritura + " mm");
         sliderValZE[var0].setValue((int)SistemaFC.valZE[var0]);
         if (SistemaFC.tipoE[var0] == 1) {
            unidad[var0] = " diop";
            labelHE[var0].setText(text[14][lang]);
            labelFE[var0].setText(text[15][lang]);
            labelBE[var0].setText(text[9][lang]);
            panelE[var0].add(sliderValBE[var0]);

            for(var1 = 0; var1 < 5; ++var1) {
               valPos[var0][var1].setVisible(true);
            }

            for(var1 = 0; var1 < 4; ++var1) {
               valDiam[var0][var1].setVisible(false);
            }

            for(var1 = 0; var1 < 5; ++var1) {
               valPot[var0][var1].setVisible(true);
            }

            datosCorrederaHLente(var0);
            if (SistemaFC.valPE[var0] != 0.0D) {
               sliderValZE[var0].setEnabled(true);
               sliderValBE[var0].setEnabled(true);
               sliderValNE[var0].setEnabled(true);
               sliderValAE[var0].setEnabled(true);
               labelValNE[var0].setText(indice[var0] + "");
               labelValAE[var0].setText(abbe[var0] + "");
               sliderValNE[var0].setValue((int)(indice[var0] * 100.0D));
               sliderValAE[var0].setValue((int)(abbe[var0] * 10.0D));
            } else {
               sliderValZE[var0].setEnabled(false);
               sliderValBE[var0].setEnabled(false);
               sliderValNE[var0].setEnabled(false);
               sliderValAE[var0].setEnabled(false);
               labelValNE[var0].setText("---");
               labelValAE[var0].setText("---");
               sliderValNE[var0].setValue(160);
               sliderValAE[var0].setValue(500);
            }
         } else {
            labelHE[var0].setText(text[13][lang]);
            unidad[var0] = " mm";
            labelFE[var0].setText(" ");
            labelValFE[var0].setText(" ");
            labelBE[var0].setText(" ");
            labelValR1E[var0].setText(" ");
            labelValR2E[var0].setText(" ");
            panelE[var0].remove(sliderValBE[var0]);
            labelNE[var0].setText(" ");
            labelValNE[var0].setText(" ");
            panelVE[var0].remove(sliderValNE[var0]);
            labelAE[var0].setText(" ");
            labelValAE[var0].setText(" ");
            panelVE[var0].remove(sliderValAE[var0]);
            if (SistemaFC.valDE[var0] != (double)diamMax) {
               escritura = (double)((int)(10.0D * SistemaFC.valDE[var0] / SistemaFC.factorFO)) / 10.0D + 0.5D;
               labelValHE[var0].setText(escritura + " mm");
            } else {
               labelValHE[var0].setText("---");
            }

            for(var1 = 0; var1 < 5; ++var1) {
               valPot[var0][var1].setVisible(false);
            }

            for(var1 = 0; var1 < 4; ++var1) {
               valDiam[var0][var1].setVisible(true);
            }

            datosCorrederaHDiafragma(var0);
            if (SistemaFC.valDE[var0] != (double)diamMax) {
               sliderValZE[var0].setEnabled(true);
            } else {
               sliderValZE[var0].setEnabled(false);
            }
         }
      }

      boolean var5 = true;

      int var3;
      double var12;
      for(var3 = 0; var3 <= nir; ++var3) {
         var12 = 0.98D * (double)((float)var3) / (double)((float)nir);
         hpr[var3] = var12;
      }

      int var14 = -1;

      for(var3 = 0; var3 <= nir; ++var3) {
         var12 = 0.98D * (double)((float)var3) / (double)((float)nir);
         int var4 = nia * var3 / nir;

         for(var1 = 0; var1 <= var4; ++var1) {
            ++var14;
            double var6;
            if (var4 == 0) {
               var6 = 0.0D;
            } else {
               var6 = (double)var1 * 3.141592D / (double)var4;
            }

            double var8;
            double var10;
            if (var1 == var4) {
               var8 = -1.0D;
               var10 = 0.0D;
            } else {
               var8 = Math.cos(var6);
               var10 = Math.sin(var6);
            }

            xpr[var14] = var12 * var10;
            ypr[var14] = var12 * var8;
         }
      }

      numRayos = var14 + 1;
      SistemaFC.panelBase.repaint();
      calculaSistema(1);
   }

   public ExactoFC() {
      this.colorTexto1 = Color.white;
      this.colorTexto2 = Color.black;
      this.setSize(800, 450);
      panelBase = new JPanel();
      panelBase.setLayout((LayoutManager)null);
      panelBase.setBackground(Color.gray);
      panelBase.setBounds(0, 230, 150, 140);
      JLabel var1 = new JLabel(text[0][lang]);
      var1.setBounds(15, 5, 140, 20);
      var1.setForeground(this.colorTexto1);
      var1.setFont(fuente3);
      panelBase.add(var1);
      buttonObj = new JButton(text[1][lang]);
      buttonObj.setBounds(0, 25, 75, 20);
      buttonObj.setForeground(this.colorTexto2);
      buttonObj.setFont(fuente4);
      buttonObj.addActionListener(this);
      buttonObj.setActionCommand("buttonObj");
      panelBase.add(buttonObj);
      buttonImag = new JButton(text[3][lang]);
      buttonImag.setBounds(75, 25, 75, 20);
      buttonImag.setForeground(this.colorTexto2);
      buttonImag.setFont(fuente4);
      buttonImag.addActionListener(this);
      buttonImag.setActionCommand("buttonImag");
      panelBase.add(buttonImag);
      JLabel var2 = new JLabel(text[2][lang]);
      var2.setBounds(10, 50, 150, 20);
      var2.setForeground(this.colorTexto1);
      var2.setFont(fuente3);
      panelBase.add(var2);

      for(this.i = 1; this.i < 4; ++this.i) {
         nomBoton[this.i] = new String("" + this.i);
         buttonE[this.i] = new JButton(nomBoton[this.i]);
         buttonE[this.i].setBounds((this.i - 1) * 50, 75, 50, 20);
         buttonE[this.i].setForeground(this.colorTexto2);
         buttonE[this.i].setFont(fuente3);
         buttonE[this.i].addActionListener(this);
         buttonE[this.i].setActionCommand(nomBoton[this.i]);
         panelBase.add(buttonE[this.i]);
      }

      for(this.i = 4; this.i < 7; ++this.i) {
         nomBoton[this.i] = new String("" + this.i);
         buttonE[this.i] = new JButton(nomBoton[this.i]);
         buttonE[this.i].setBounds((this.i - 4) * 50, 95, 50, 20);
         buttonE[this.i].setForeground(this.colorTexto2);
         buttonE[this.i].setFont(fuente3);
         buttonE[this.i].addActionListener(this);
         buttonE[this.i].setActionCommand(nomBoton[this.i]);
         panelBase.add(buttonE[this.i]);
      }

      buttonMP = new JButton(text[4][lang]);
      buttonMP.setBounds(0, 115, 150, 20);
      buttonMP.setForeground(this.colorTexto2);
      buttonMP.setFont(fuente3);
      buttonMP.addActionListener(this);
      buttonMP.setActionCommand("buttonMP");
      panelBase.add(buttonMP);
      panelObj = new JPanel();
      panelObj.setLayout((LayoutManager)null);
      panelObj.setBounds(150, 230, 500, 220);
      labelObj = new JLabel(text[1][lang]);
      labelObj.setBounds(80, 10, 100, 30);
      labelObj.setForeground(Color.black);
      labelObj.setFont(fuente1);
      panelObj.add(labelObj);
      buttonR = new JRadioButton("Real");
      buttonR.setBounds(40, 40, 100, 30);
      buttonR.setFont(fuente3);
      buttonR.addActionListener(this);
      buttonR.setActionCommand("R");
      buttonR.setSelected(true);
      buttonV = new JRadioButton("Virtual");
      buttonV.setBounds(180, 40, 100, 30);
      buttonV.setFont(fuente3);
      buttonV.addActionListener(this);
      buttonV.setActionCommand("V");
      grupoO = new ButtonGroup();
      grupoO.add(buttonR);
      grupoO.add(buttonV);
      panelObj.add(buttonR);
      panelObj.add(buttonV);
      labelZObj = new JLabel(text[7][lang]);
      labelZObj.setBounds(5, 80, 70, 20);
      labelZObj.setForeground(grisAzulado);
      labelZObj.setFont(fuente3);
      panelObj.add(labelZObj);
      labelValZObj = new JLabel("---");
      labelValZObj.setBounds(5, 95, 70, 20);
      labelValZObj.setForeground(grisAzulado);
      labelValZObj.setFont(fuente3);
      panelObj.add(labelValZObj);
      sliderValZObj = new JSlider(-4480, 4480, -4480);
      sliderValZObj.setPaintTicks(true);
      sliderValZObj.setMajorTickSpacing(2240);
      sliderValZObj.setMinorTickSpacing(280);
      sliderValZObj.setPaintLabels(false);
      sliderValZObj.setBounds(70, 80, 200, 30);
      valPosObj[0] = new JLabel(leyendaPosObj[0]);
      valPosObj[0].setBounds(55, 100, 60, 30);
      valPosObj[0].setForeground(grisAzulado);
      valPosObj[0].setFont(fuente3);
      valPosObj[0].setVisible(true);
      panelObj.add(valPosObj[0]);
      valPosObj[1] = new JLabel(leyendaPosObj[1]);
      valPosObj[1].setBounds(110, 100, 60, 30);
      valPosObj[1].setForeground(grisAzulado);
      valPosObj[1].setFont(fuente3);
      valPosObj[1].setVisible(true);
      panelObj.add(valPosObj[1]);
      valPosObj[2] = new JLabel(leyendaPosObj[2]);
      valPosObj[2].setBounds(168, 100, 60, 30);
      valPosObj[2].setForeground(grisAzulado);
      valPosObj[2].setFont(fuente3);
      valPosObj[2].setVisible(true);
      panelObj.add(valPosObj[2]);
      valPosObj[3] = new JLabel(leyendaPosObj[3]);
      valPosObj[3].setBounds(205, 100, 60, 30);
      valPosObj[3].setForeground(grisAzulado);
      valPosObj[3].setFont(fuente3);
      valPosObj[3].setVisible(true);
      panelObj.add(valPosObj[3]);
      valPosObj[4] = new JLabel(leyendaPosObj[4]);
      valPosObj[4].setBounds(240, 100, 60, 30);
      valPosObj[4].setForeground(grisAzulado);
      valPosObj[4].setFont(fuente3);
      valPosObj[4].setVisible(true);
      panelObj.add(valPosObj[4]);
      listenerValZObj = new ExactoFC.SliderListener(labelValZObj);
      sliderValZObj.addChangeListener(listenerValZObj);
      panelObj.add(sliderValZObj);
      labelHObj = new JLabel(text[8][lang]);
      labelHObj.setBounds(5, 140, 70, 20);
      labelHObj.setForeground(grisAzulado);
      labelHObj.setFont(fuente3);
      panelObj.add(labelHObj);
      labelValHObj = new JLabel("0.");
      labelValHObj.setBounds(5, 160, 70, 20);
      labelValHObj.setForeground(grisAzulado);
      labelValHObj.setFont(fuente3);
      panelObj.add(labelValHObj);
      sliderValHObj = new JSlider(0, 200, 0);
      sliderValHObj.setPaintTicks(true);
      sliderValHObj.setMajorTickSpacing(50);
      sliderValHObj.setMinorTickSpacing(25);
      sliderValHObj.setPaintLabels(false);
      sliderValHObj.setBounds(80, 140, 190, 30);
      sliderValHObj.setEnabled(false);
      valCampo[0] = new JLabel(leyendaCampo[0]);
      valCampo[0].setBounds(85, 160, 60, 30);
      valCampo[0].setForeground(grisAzulado);
      valCampo[0].setFont(fuente3);
      valCampo[0].setVisible(false);
      panelObj.add(valCampo[0]);
      valCampo[1] = new JLabel(leyendaCampo[1]);
      valCampo[1].setBounds(130, 160, 60, 30);
      valCampo[1].setForeground(grisAzulado);
      valCampo[1].setFont(fuente3);
      valCampo[1].setVisible(false);
      panelObj.add(valCampo[1]);
      valCampo[2] = new JLabel(leyendaCampo[2]);
      valCampo[2].setBounds(168, 160, 60, 30);
      valCampo[2].setForeground(grisAzulado);
      valCampo[2].setFont(fuente3);
      valCampo[2].setVisible(false);
      panelObj.add(valCampo[2]);
      valCampo[3] = new JLabel(leyendaCampo[3]);
      valCampo[3].setBounds(211, 160, 60, 30);
      valCampo[3].setForeground(grisAzulado);
      valCampo[3].setFont(fuente3);
      valCampo[3].setVisible(false);
      panelObj.add(valCampo[3]);
      valCampo[4] = new JLabel(leyendaCampo[4]);
      valCampo[4].setBounds(255, 160, 60, 30);
      valCampo[4].setForeground(grisAzulado);
      valCampo[4].setFont(fuente3);
      valCampo[4].setVisible(false);
      panelObj.add(valCampo[4]);
      listenerValHObj = new ExactoFC.SliderListener(labelValHObj);
      sliderValHObj.addChangeListener(listenerValHObj);
      panelObj.add(sliderValHObj);
      panelImag = new JPanel();
      panelImag.setLayout((LayoutManager)null);
      panelImag.setBounds(150, 230, 500, 220);
      labelImag = new JLabel(text[3][lang]);
      labelImag.setBounds(80, 0, 100, 30);
      labelImag.setForeground(Color.black);
      labelImag.setFont(fuente1);
      panelImag.add(labelImag);
      buttonMono = new JRadioButton(text[21][lang]);
      buttonMono.setBounds(30, 30, 130, 30);
      buttonMono.setFont(fuente3);
      buttonMono.addActionListener(this);
      buttonMono.setActionCommand("M");
      buttonMono.setSelected(true);
      buttonColor = new JRadioButton(text[22][lang]);
      buttonColor.setBounds(180, 30, 150, 30);
      buttonColor.setFont(fuente3);
      buttonColor.addActionListener(this);
      buttonColor.setActionCommand("C");
      grupo1 = new ButtonGroup();
      grupo1.add(buttonMono);
      grupo1.add(buttonColor);
      panelImag.add(buttonMono);
      panelImag.add(buttonColor);
      labelZParax = new JLabel(text[23][lang]);
      labelZParax.setBounds(5, 55, 200, 20);
      labelZParax.setForeground(grisAzulado);
      labelZParax.setFont(fuente3);
      panelImag.add(labelZParax);
      labelValZParax = new JLabel("---");
      labelValZParax.setBounds(200, 55, 90, 20);
      labelValZParax.setForeground(grisAzulado);
      labelValZParax.setFont(fuente3);
      panelImag.add(labelValZParax);
      labelZImag = new JLabel(text[17][lang]);
      labelZImag.setBounds(5, 80, 260, 20);
      labelZImag.setForeground(grisAzulado);
      labelZImag.setFont(fuente3);
      panelImag.add(labelZImag);
      labelValZImag = new JLabel("---");
      labelValZImag.setBounds(5, 105, 100, 20);
      labelValZImag.setForeground(grisAzulado);
      labelValZImag.setFont(fuente3);
      panelImag.add(labelValZImag);
      sliderValZImag = new JSlider(-120, 120, 0);
      sliderValZImag.setPaintTicks(true);
      sliderValZImag.setMajorTickSpacing(60);
      sliderValZImag.setMinorTickSpacing(6);
      sliderValZImag.setPaintLabels(false);
      sliderValZImag.setBounds(60, 100, 210, 30);
      valPosImag[0] = new JLabel(leyendaPosImag[0]);
      valPosImag[0].setBounds(55, 120, 60, 30);
      valPosImag[0].setForeground(grisAzulado);
      valPosImag[0].setFont(fuente3);
      valPosImag[0].setVisible(true);
      panelImag.add(valPosImag[0]);
      valPosImag[1] = new JLabel(leyendaPosImag[1]);
      valPosImag[1].setBounds(110, 120, 60, 30);
      valPosImag[1].setForeground(grisAzulado);
      valPosImag[1].setFont(fuente3);
      valPosImag[1].setVisible(true);
      panelImag.add(valPosImag[1]);
      valPosImag[2] = new JLabel(leyendaPosImag[2]);
      valPosImag[2].setBounds(161, 120, 60, 30);
      valPosImag[2].setForeground(grisAzulado);
      valPosImag[2].setFont(fuente3);
      valPosImag[2].setVisible(true);
      panelImag.add(valPosImag[2]);
      valPosImag[3] = new JLabel(leyendaPosImag[3]);
      valPosImag[3].setBounds(210, 120, 60, 30);
      valPosImag[3].setForeground(grisAzulado);
      valPosImag[3].setFont(fuente3);
      valPosImag[3].setVisible(true);
      panelImag.add(valPosImag[3]);
      valPosImag[4] = new JLabel(leyendaPosImag[4]);
      valPosImag[4].setBounds(250, 120, 60, 30);
      valPosImag[4].setForeground(grisAzulado);
      valPosImag[4].setFont(fuente3);
      valPosImag[4].setVisible(true);
      panelImag.add(valPosImag[4]);
      listenerValZImag = new ExactoFC.SliderListener(labelValZImag);
      sliderValZImag.addChangeListener(listenerValZImag);
      panelImag.add(sliderValZImag);
      labelHImag = new JLabel(text[18][lang]);
      labelHImag.setBounds(5, 150, 70, 20);
      labelHImag.setForeground(grisAzulado);
      labelHImag.setFont(fuente3);
      panelImag.add(labelHImag);
      labelValHImag = new JLabel("1.");
      labelValHImag.setBounds(5, 170, 70, 20);
      labelValHImag.setForeground(grisAzulado);
      labelValHImag.setFont(fuente3);
      panelImag.add(labelValHImag);
      sliderValHImag = new JSlider(-200, 100, 1);
      sliderValHImag.setPaintTicks(true);
      sliderValHImag.setMajorTickSpacing(20);
      sliderValHImag.setPaintLabels(false);
      sliderValHImag.setBounds(70, 160, 190, 30);
      listenerValHImag = new ExactoFC.SliderListener(labelValHImag);
      sliderValHImag.addChangeListener(listenerValHImag);
      panelImag.add(sliderValHImag);
      panelMerito = new JPanel();
      panelMerito.setLayout((LayoutManager)null);
      panelMerito.setBounds(0, 365, 150, 115);
      labelMerito[0] = new JLabel(text[24][lang]);
      labelMerito[0].setBounds(5, 10, 150, 15);
      labelMerito[0].setForeground(grisAzulado);
      labelMerito[0].setFont(fuente3);
      panelMerito.add(labelMerito[0]);
      labelValMerito[0] = new JLabel("---");
      labelValMerito[0].setBounds(50, 25, 100, 15);
      labelValMerito[0].setForeground(grisAzulado);
      labelValMerito[0].setFont(fuente3);
      panelMerito.add(labelValMerito[0]);
      labelMerito[1] = new JLabel(text[25][lang]);
      labelMerito[1].setBounds(5, 45, 150, 15);
      labelMerito[1].setForeground(grisAzulado);
      labelMerito[1].setFont(fuente3);
      panelMerito.add(labelMerito[1]);
      labelValMerito[1] = new JLabel("---");
      labelValMerito[1].setBounds(50, 60, 100, 15);
      labelValMerito[1].setForeground(grisAzulado);
      labelValMerito[1].setFont(fuente3);
      panelMerito.add(labelValMerito[1]);

      for(this.i = 1; this.i < 7; ++this.i) {
         panelE[this.i] = new JPanel();
         panelE[this.i].setLayout((LayoutManager)null);
         panelE[this.i].setBounds(150, 230, 500, 220);
         panelVE[this.i] = new JPanel();
         panelVE[this.i].setLayout((LayoutManager)null);
         panelVE[this.i].setBounds(0, 365, 150, 85);
         SistemaFC.valZE[this.i] = 200.0D + 200.0D * (double)this.i;
         SistemaFC.valPE[this.i] = 0.0D;
         valBE[this.i] = 0.0D;
         SistemaFC.valDibPE[this.i] = 0.0D;
         SistemaFC.existe[this.i] = 0;
         SistemaFC.valDE[this.i] = (double)diamMax;
         SistemaFC.dist[this.i] = 0.0D;
         diste[this.i] = 0.0D;
         distb[this.i] = 0.0D;
         SistemaFC.tipoE[this.i] = 1;
         SistemaFC.altura[this.i] = (double)diamMax / 2.0D;
         unidad[this.i] = " diop.";
         indice[this.i] = 1.6D;
         abbe[this.i] = 50.0D;
         labelE[this.i] = new JLabel(text[10][lang] + " " + this.i);
         labelE[this.i].setBounds(20, 10, 200, 30);
         labelE[this.i].setForeground(Color.black);
         labelE[this.i].setFont(fuente3);
         panelE[this.i].add(labelE[this.i]);
         buttonL[this.i] = new JRadioButton(text[11][lang]);
         buttonL[this.i].setBounds(110, 10, 70, 30);
         buttonL[this.i].setFont(fuente3);
         buttonL[this.i].addActionListener(this);
         buttonL[this.i].setActionCommand("L");
         buttonL[this.i].setSelected(true);
         buttonD[this.i] = new JRadioButton(text[12][lang]);
         buttonD[this.i].setBounds(180, 10, 100, 30);
         buttonD[this.i].setFont(fuente3);
         buttonD[this.i].addActionListener(this);
         buttonD[this.i].setActionCommand("D");
         grupo[this.i] = new ButtonGroup();
         grupo[this.i].add(buttonL[this.i]);
         grupo[this.i].add(buttonD[this.i]);
         panelE[this.i].add(buttonL[this.i]);
         panelE[this.i].add(buttonD[this.i]);
         labelZE[this.i] = new JLabel(text[7][lang]);
         labelZE[this.i].setBounds(5, 45, 70, 15);
         labelZE[this.i].setForeground(grisAzulado);
         labelZE[this.i].setFont(fuente3);
         panelE[this.i].add(labelZE[this.i]);
         labelValZE[this.i] = new JLabel(SistemaFC.valZE[this.i] + " mm");
         labelValZE[this.i].setBounds(5, 60, 70, 15);
         labelValZE[this.i].setForeground(grisAzulado);
         labelValZE[this.i].setFont(fuente3);
         panelE[this.i].add(labelValZE[this.i]);
         sliderValZE[this.i] = new JSlider(0, 1600, 200 + 200 * this.i);
         sliderValZE[this.i].setPaintTicks(true);
         sliderValZE[this.i].setMajorTickSpacing(400);
         sliderValZE[this.i].setMinorTickSpacing(50);
         sliderValZE[this.i].setBounds(60, 45, 210, 30);
         sliderValZE[this.i].setEnabled(false);
         listenerValZE[this.i] = new ExactoFC.SliderListener(labelValZE[this.i]);
         sliderValZE[this.i].addChangeListener(listenerValZE[this.i]);
         sliderValZE[this.i].setEnabled(false);
         panelE[this.i].add(sliderValZE[this.i]);
         valPos[this.i][0] = new JLabel(leyendaPos[0]);
         valPos[this.i][0].setBounds(65, 65, 60, 30);
         valPos[this.i][0].setForeground(grisAzulado);
         valPos[this.i][0].setFont(fuente3);
         panelE[this.i].add(valPos[this.i][0]);
         valPos[this.i][1] = new JLabel(leyendaPos[1]);
         valPos[this.i][1].setBounds(105, 65, 60, 30);
         valPos[this.i][1].setForeground(grisAzulado);
         valPos[this.i][1].setFont(fuente3);
         panelE[this.i].add(valPos[this.i][1]);
         valPos[this.i][2] = new JLabel(leyendaPos[2]);
         valPos[this.i][2].setBounds(155, 65, 60, 30);
         valPos[this.i][2].setForeground(grisAzulado);
         valPos[this.i][2].setFont(fuente3);
         panelE[this.i].add(valPos[this.i][2]);
         valPos[this.i][3] = new JLabel(leyendaPos[3]);
         valPos[this.i][3].setBounds(200, 65, 60, 30);
         valPos[this.i][3].setForeground(grisAzulado);
         valPos[this.i][3].setFont(fuente3);
         panelE[this.i].add(valPos[this.i][3]);
         valPos[this.i][4] = new JLabel(leyendaPos[4]);
         valPos[this.i][4].setBounds(240, 65, 60, 30);
         valPos[this.i][4].setForeground(grisAzulado);
         valPos[this.i][4].setFont(fuente3);
         panelE[this.i].add(valPos[this.i][4]);
         panelE[this.i].add(sliderValZE[this.i]);
         labelHE[this.i] = new JLabel(text[14][lang]);
         labelHE[this.i].setBounds(5, 95, 70, 10);
         labelHE[this.i].setForeground(grisAzulado);
         labelHE[this.i].setFont(fuente3);
         panelE[this.i].add(labelHE[this.i]);
         SistemaFC.altura[this.i] = (double)diamMax / 2.0D;
         texto1 = "0 diop";
         labelValHE[this.i] = new JLabel(texto1);
         labelValHE[this.i].setBounds(5, 110, 70, 10);
         labelValHE[this.i].setForeground(grisAzulado);
         labelValHE[this.i].setFont(fuente3);
         panelE[this.i].add(labelValHE[this.i]);
         labelFE[this.i] = new JLabel(text[15][lang]);
         labelFE[this.i].setBounds(5, 125, 70, 10);
         labelFE[this.i].setForeground(grisAzulado);
         labelFE[this.i].setFont(fuente3);
         panelE[this.i].add(labelFE[this.i]);
         texto1 = "---";
         labelValFE[this.i] = new JLabel(texto1);
         labelValFE[this.i].setBounds(5, 140, 70, 10);
         labelValFE[this.i].setForeground(grisAzulado);
         labelValFE[this.i].setFont(fuente3);
         panelE[this.i].add(labelValFE[this.i]);
         sliderValHE[this.i] = new JSlider(-200, 200, 0);
         datosCorrederaHLente(this.i);
         listenerValHE[this.i] = new ExactoFC.SliderListener(labelValHE[this.i]);
         sliderValHE[this.i].addChangeListener(listenerValHE[this.i]);
         valPot[this.i][0] = new JLabel(leyendaPot[0]);
         valPot[this.i][0].setBounds(75, 120, 60, 30);
         valPot[this.i][0].setForeground(grisAzulado);
         valPot[this.i][0].setFont(fuente3);
         panelE[this.i].add(valPot[this.i][0]);
         valPot[this.i][1] = new JLabel(leyendaPot[1]);
         valPot[this.i][1].setBounds(120, 120, 60, 30);
         valPot[this.i][1].setForeground(grisAzulado);
         valPot[this.i][1].setFont(fuente3);
         panelE[this.i].add(valPot[this.i][1]);
         valPot[this.i][2] = new JLabel(leyendaPot[2]);
         valPot[this.i][2].setBounds(170, 120, 60, 30);
         valPot[this.i][2].setForeground(grisAzulado);
         valPot[this.i][2].setFont(fuente3);
         panelE[this.i].add(valPot[this.i][2]);
         valPot[this.i][3] = new JLabel(leyendaPot[3]);
         valPot[this.i][3].setBounds(210, 120, 60, 30);
         valPot[this.i][3].setForeground(grisAzulado);
         valPot[this.i][3].setFont(fuente3);
         panelE[this.i].add(valPot[this.i][3]);
         valPot[this.i][4] = new JLabel(leyendaPot[4]);
         valPot[this.i][4].setBounds(250, 120, 60, 30);
         valPot[this.i][4].setForeground(grisAzulado);
         valPot[this.i][4].setFont(fuente3);
         panelE[this.i].add(valPot[this.i][4]);
         panelE[this.i].add(sliderValHE[this.i]);
         valDiam[this.i][0] = new JLabel(leyendaDiam[0]);
         valDiam[this.i][0].setBounds(80, 120, 60, 30);
         valDiam[this.i][0].setForeground(grisAzulado);
         valDiam[this.i][0].setFont(fuente3);
         panelE[this.i].add(valDiam[this.i][0]);
         valDiam[this.i][1] = new JLabel(leyendaDiam[1]);
         valDiam[this.i][1].setBounds(125, 120, 60, 30);
         valDiam[this.i][1].setForeground(grisAzulado);
         valDiam[this.i][1].setFont(fuente3);
         panelE[this.i].add(valDiam[this.i][1]);
         valDiam[this.i][2] = new JLabel(leyendaDiam[2]);
         valDiam[this.i][2].setBounds(180, 120, 60, 30);
         valDiam[this.i][2].setForeground(grisAzulado);
         valDiam[this.i][2].setFont(fuente3);
         panelE[this.i].add(valDiam[this.i][2]);
         valDiam[this.i][3] = new JLabel(leyendaDiam[3]);
         valDiam[this.i][3].setBounds(230, 120, 60, 30);
         valDiam[this.i][3].setForeground(grisAzulado);
         valDiam[this.i][3].setFont(fuente3);
         panelE[this.i].add(valDiam[this.i][3]);
         panelE[this.i].add(sliderValHE[this.i]);
         labelBE[this.i] = new JLabel(text[9][lang]);
         labelBE[this.i].setBounds(5, 155, 70, 15);
         labelBE[this.i].setForeground(grisAzulado);
         labelBE[this.i].setFont(fuente3);
         panelE[this.i].add(labelBE[this.i]);
         labelValBE[this.i] = new JLabel(" ");
         panelE[this.i].add(labelValBE[this.i]);
         labelValR1E[this.i] = new JLabel("---");
         labelValR1E[this.i].setBounds(5, 170, 70, 15);
         labelValR1E[this.i].setForeground(grisAzulado);
         labelValR1E[this.i].setFont(fuente3);
         panelE[this.i].add(labelValR1E[this.i]);
         labelValR2E[this.i] = new JLabel("---");
         labelValR2E[this.i].setBounds(5, 185, 70, 15);
         labelValR2E[this.i].setForeground(grisAzulado);
         labelValR2E[this.i].setFont(fuente3);
         panelE[this.i].add(labelValR2E[this.i]);
         sliderValBE[this.i] = new JSlider(-100, 100, 0);
         sliderValBE[this.i].setPaintTicks(true);
         sliderValBE[this.i].setMajorTickSpacing(50);
         sliderValBE[this.i].setMinorTickSpacing(50);
         sliderValBE[this.i].setPaintLabels(false);
         sliderValBE[this.i].setBounds(80, 160, 190, 30);
         sliderValBE[this.i].setEnabled(false);
         listenerValBE[this.i] = new ExactoFC.SliderListener(labelValBE[this.i]);
         sliderValBE[this.i].addChangeListener(listenerValBE[this.i]);
         sliderValBE[this.i].setEnabled(false);
         panelE[this.i].add(sliderValBE[this.i]);
         labelNE[this.i] = new JLabel(text[19][lang]);
         labelNE[this.i].setBounds(5, 10, 60, 15);
         labelNE[this.i].setForeground(grisAzulado);
         labelNE[this.i].setFont(fuente3);
         panelVE[this.i].add(labelNE[this.i]);
         labelValNE[this.i] = new JLabel(indice[this.i] + " ");
         labelValNE[this.i].setBounds(5, 25, 60, 15);
         labelValNE[this.i].setForeground(grisAzulado);
         labelValNE[this.i].setFont(fuente3);
         panelVE[this.i].add(labelValNE[this.i]);
         sliderValNE[this.i] = new JSlider(140, 180, 150);
         sliderValNE[this.i].setBounds(50, 15, 100, 30);
         sliderValNE[this.i].setEnabled(false);
         listenerValNE[this.i] = new ExactoFC.SliderListener(labelValNE[this.i]);
         sliderValNE[this.i].addChangeListener(listenerValNE[this.i]);
         sliderValNE[this.i].setEnabled(false);
         panelVE[this.i].add(sliderValNE[this.i]);
         labelAE[this.i] = new JLabel(text[20][lang]);
         labelAE[this.i].setBounds(5, 45, 60, 15);
         labelAE[this.i].setForeground(grisAzulado);
         labelAE[this.i].setFont(fuente3);
         panelVE[this.i].add(labelAE[this.i]);
         labelValAE[this.i] = new JLabel(abbe[this.i] + " ");
         labelValAE[this.i].setBounds(5, 60, 65, 15);
         labelValAE[this.i].setForeground(grisAzulado);
         labelValAE[this.i].setFont(fuente3);
         panelVE[this.i].add(labelValAE[this.i]);
         sliderValAE[this.i] = new JSlider(400, 600, 500);
         sliderValAE[this.i].setBounds(50, 45, 100, 30);
         sliderValAE[this.i].setEnabled(false);
         listenerValAE[this.i] = new ExactoFC.SliderListener(labelValAE[this.i]);
         sliderValAE[this.i].addChangeListener(listenerValAE[this.i]);
         sliderValAE[this.i].setEnabled(false);
         panelVE[this.i].add(sliderValAE[this.i]);
      }

      panelDiagramas = new JPanel();
      panelDiagramas.setLayout((LayoutManager)null);
      panelDiagramas.setBackground(Color.gray);
      panelDiagramas.setBounds(420, 420, 380, 20);
      labelDiagramas1 = new JLabel(text[5][lang]);
      labelDiagramas1.setBounds(80, 0, 100, 20);
      labelDiagramas1.setForeground(this.colorTexto1);
      labelDiagramas1.setFont(fuente3);
      panelDiagramas.add(labelDiagramas1);
      labelDiagramas2 = new JLabel(text[6][lang] + " = " + lado + " mm");
      labelDiagramas2.setBounds(130, 0, 120, 20);
      labelDiagramas2.setForeground(this.colorTexto1);
      labelDiagramas2.setFont(fuente3);
      panelDiagramas.add(labelDiagramas2);
      labelDiagramas3 = new JLabel(text[8][lang]);
      labelDiagramas3.setBounds(250, 0, 150, 20);
      labelDiagramas3.setForeground(this.colorTexto1);
      labelDiagramas3.setFont(fuente3);
      panelDiagramas.add(labelDiagramas3);
      canvasExacto = new DibujoExactoFC();
      canvasExacto.setBackground(Color.black);
      canvasExacto.setBounds(30, 0, 800, 210);
      canvasDiagramas = new DibujoDiagramasFC();
      canvasDiagramas.setBackground(Color.black);
      canvasDiagramas.setBounds(420, 230, 380, 190);
   }

   public void actionPerformed(ActionEvent var1) {
      String var4 = var1.getActionCommand();
      int var2;
      if (var4 != "L" && var4 != "D" && var4 != "R" && var4 != "V" && var4 != "M" && var4 != "C") {
         if (this.cuadro == 0) {
            SistemaFC.panelBase.remove(panelObj);
         }

         if (this.cuadro == 1) {
            SistemaFC.panelBase.remove(panelImag);
            SistemaFC.panelBase.remove(panelMerito);
         }

         for(var2 = 0; var2 < 7; ++var2) {
            if (this.cuadro == 20 + var2) {
               SistemaFC.panelBase.remove(panelE[var2]);
               SistemaFC.panelBase.remove(panelVE[var2]);
            }
         }
      }

      if (var4 == "buttonObj") {
         SistemaFC.panelBase.add(panelObj);
         this.cuadro = 0;
      }

      if (var4 == "R") {
         SistemaFC.real[0] = 1;
      }

      if (var4 == "V") {
         SistemaFC.real[0] = 0;
      }

      if (var4 == "buttonImag") {
         SistemaFC.panelBase.add(panelImag);
         SistemaFC.panelBase.add(panelMerito);
         this.cuadro = 1;
      }

      if (var4 == "M") {
         numColores = 1;
      }

      if (var4 == "C") {
         numColores = 3;
         if (FuncionesExactasFC.preparaSistema() == 1) {
            FuncionesExactasFC.marchaParaxial();
            if (FuncionesExactasFC.preparaRayos() == 1) {
               FuncionesExactasFC.propagaHazRayos();
            }
         }
      }

      for(var2 = 0; var2 < 7; ++var2) {
         if (var4 == nomBoton[var2]) {
            SistemaFC.panelBase.add(panelE[var2]);
            SistemaFC.panelBase.add(panelVE[var2]);
            if (numColores == 3 && SistemaFC.valPE[var2] != 0.0D) {
               labelValAE[var2].setText(abbe[var2] + "");
               sliderValAE[var2].setEnabled(true);
            } else {
               labelValAE[var2].setText("---");
               sliderValAE[var2].setEnabled(false);
            }

            this.cuadro = 20 + var2;
         }

         if (this.cuadro == 20 + var2) {
            int var3;
            for(var3 = 0; var3 < 5; ++var3) {
               valPos[var2][var3].setVisible(true);
            }

            if (var4 == "L") {
               SistemaFC.tipoE[var2] = 1;
               unidad[var2] = " diop.";
               SistemaFC.existe[var2] = 0;

               for(var3 = 0; var3 < 4; ++var3) {
                  valDiam[var2][var3].setVisible(false);
               }

               datosCorrederaHLente(var2);
               sliderValZE[var2].setEnabled(false);
               sliderValBE[var2].setEnabled(false);
               labelHE[var2].setText(text[14][lang]);
               labelValHE[var2].setText(0.0D + unidad[var2]);
               labelFE[var2].setText(text[15][lang]);
               labelValFE[var2].setText("---");
               labelBE[var2].setText(text[9][lang]);
               labelValR1E[var2].setText("---");
               labelValR2E[var2].setText("---");
               panelE[var2].add(sliderValBE[var2]);
               labelNE[var2].setText(text[19][lang]);
               labelAE[var2].setText(text[20][lang]);
               labelValNE[var2].setText("---");
               labelValAE[var2].setText("---");
               sliderValNE[var2].setValue(160);
               sliderValAE[var2].setValue(500);
               sliderValNE[var2].setEnabled(false);
               sliderValAE[var2].setEnabled(false);
               panelVE[var2].add(sliderValNE[var2]);
               panelVE[var2].add(sliderValAE[var2]);
               SistemaFC.altura[var2] = (double)diamMax / 2.0D;

               for(var3 = 0; var3 < 5; ++var3) {
                  valPot[var2][var3].setVisible(true);
               }

               SistemaFC.valPE[var2] = 0.0D;
               SistemaFC.valDibPE[var2] = 0.0D;
               SistemaFC.valDE[var2] = (double)diamMax;
            }

            if (var4 == "D") {
               SistemaFC.tipoE[var2] = 0;
               unidad[var2] = " mm";
               SistemaFC.existe[var2] = 0;

               for(var3 = 0; var3 < 5; ++var3) {
                  valPot[var2][var3].setVisible(false);
               }

               datosCorrederaHDiafragma(var2);
               sliderValZE[var2].setEnabled(false);
               panelE[var2].remove(sliderValBE[var2]);
               panelVE[var2].remove(sliderValNE[var2]);
               panelVE[var2].remove(sliderValAE[var2]);
               labelHE[var2].setText(text[13][lang]);
               labelValHE[var2].setText("---");
               labelFE[var2].setText(" ");
               labelBE[var2].setText(" ");
               labelNE[var2].setText(" ");
               labelAE[var2].setText(" ");
               labelValFE[var2].setText(" ");
               labelValR1E[var2].setText(" ");
               labelValR2E[var2].setText(" ");
               labelValNE[var2].setText(" ");
               labelValAE[var2].setText(" ");

               for(var3 = 0; var3 < 4; ++var3) {
                  valDiam[var2][var3].setVisible(true);
               }

               SistemaFC.valPE[var2] = 0.0D;
               SistemaFC.valDibPE[var2] = 0.0D;
               SistemaFC.valDE[var2] = (double)diamMax;
               SistemaFC.altura[var2] = (double)diamMax / 2.0D;
               espesor[var2] = 0.0D;
            }
         }
      }

      if (var4 == "buttonMP") {
         SistemaFC.panelBase.remove(panelBase);
         SistemaFC.panelBase.remove(canvasExacto);
         SistemaFC.panelBase.remove(canvasDiagramas);
         SistemaFC.panelBase.remove(panelDiagramas);
         PanelBaseFC var10001 = SistemaFC.panelBase;
         SistemaFC.panelBase.add(PanelBaseFC.panelMP);
      }

      SistemaFC.panelBase.repaint();
      calculaSistema(1);
   }

   static void calculaSistema(int var0) {
      switch(var0) {
      case 0:
         canvasExacto.redraw();
         canvasDiagramas.redraw();
         break;
      case 1:
         if (FuncionesExactasFC.preparaSistema() == 1) {
            FuncionesExactasFC.marchaParaxial();
            double var1 = (double)((int)(FuncionesExactasFC.imagParax * 10.0D)) / 10.0D;
            escritura = (double)((int)(10.0D * var1 / SistemaFC.factorFO)) / 10.0D;
            labelValZParax.setText(escritura + " mm");
            if (FuncionesExactasFC.preparaRayos() == 1) {
               FuncionesExactasFC.propagaHazRayos();
            }
         }

         canvasExacto.redraw();
         canvasDiagramas.redraw();
         break;
      case 2:
         canvasExacto.redraw();
         canvasDiagramas.redraw();
         break;
      case 3:
         canvasDiagramas.redraw();
      }

   }

   void datosElemento(JSlider var1, JLabel var2, int var3) {
      if (var2 == labelValZE[var3]) {
         SistemaFC.valZE[var3] = (double)var1.getValue();
         escritura = (double)((int)(10.0D * SistemaFC.valZE[var3] / SistemaFC.factorFO)) / 10.0D;
         var2.setText(escritura + " mm");
      }

      if (var2 == labelValHE[var3]) {
         if (SistemaFC.tipoE[var3] == 1) {
            SistemaFC.valPE[var3] = (double)var1.getValue() / 10.0D / SistemaFC.factorFO;
            this.datosLente(var3);
         } else {
            SistemaFC.valDE[var3] = (double)var1.getValue();
            SistemaFC.altura[var3] = SistemaFC.valDE[var3] / 2.0D;
            if (SistemaFC.valDE[var3] != (double)diamMax) {
               escritura = (double)((int)(10.0D * SistemaFC.valDE[var3] / SistemaFC.factorFO)) / 10.0D;
               var2.setText(escritura + " mm");
               sliderValZE[var3].setEnabled(true);
               SistemaFC.existe[var3] = 1;
               SistemaFC.radio[var3] = 1.0E13D;
               SistemaFC.radioDib[var3] = 1.0E13D;
            } else {
               var2.setText("---");
               sliderValZE[var3].setEnabled(false);
               SistemaFC.existe[var3] = 0;
            }
         }
      }

      if (var2 == labelValBE[var3]) {
         valBE[var3] = (double)var1.getValue();
         this.datosLente(var3);
      }

      if (var2 == labelValNE[var3]) {
         indice[var3] = (double)var1.getValue() / 100.0D;
         labelValNE[var3].setText(indice[var3] + "");
         SistemaFC.valPE[var3] = 1000.0D * (indice[var3] - 1.0D) * (curv1[var3] - curv2[var3]);
         double var4 = (double)((int)(10.0D * SistemaFC.valPE[var3])) / 10.0D;
         escritura = (double)((int)(10.0D * var4 * SistemaFC.factorFO + 0.5D)) / 10.0D;
         labelValHE[var3].setText(escritura + unidad[var3]);
         double var6 = (double)((int)(10000.0D / escritura)) / 10.0D;
         labelValFE[var3].setText(var6 + " mm");
         labelValFE[var3].setText(var6 + " mm");
      }

      if (var2 == labelValAE[var3]) {
         abbe[var3] = (double)var1.getValue() / 10.0D;
         labelValAE[var3].setText(abbe[var3] + "");
      }

   }

   void datosLente(int var1) {
      double var30 = 0.01D;
      double var32 = 20.0D;
      if (SistemaFC.valPE[var1] > 0.0D) {
         SistemaFC.valDibPE[var1] = SistemaFC.valPE[var1] + 8.0D;
      } else {
         SistemaFC.valDibPE[var1] = SistemaFC.valPE[var1] - 8.0D;
      }

      double var24 = 0.5D;
      escritura = (double)((int)(10.0D * SistemaFC.valPE[var1] * SistemaFC.factorFO + 0.5D)) / 10.0D;
      labelValHE[var1].setText(escritura + unidad[var1]);
      if (SistemaFC.valPE[var1] != 0.0D) {
         double var2 = (double)((int)(10000.0D / SistemaFC.valPE[var1])) / 10.0D;
         escritura = (double)((int)(10000.0D / escritura)) / 10.0D;
         labelValFE[var1].setText(escritura + " mm");
         sliderValZE[var1].setEnabled(true);
         sliderValBE[var1].setEnabled(true);
         labelValNE[var1].setText(indice[var1] + "");
         sliderValNE[var1].setEnabled(true);
         if (numColores == 3) {
            labelValAE[var1].setText(abbe[var1] + "");
            sliderValAE[var1].setEnabled(true);
         } else {
            labelValAE[var1].setText("---");
            sliderValAE[var1].setEnabled(false);
         }

         SistemaFC.existe[var1] = 1;
         SistemaFC.curv[var1] = SistemaFC.valPE[var1] / (2.0D * indiceDib - 2.0D);
         if (Math.abs(SistemaFC.curv[var1]) <= 1.0E-10D) {
            SistemaFC.radio[var1] = 1.0E13D;
         } else {
            SistemaFC.radio[var1] = 2000.0D * (indiceDib - 1.0D) / SistemaFC.valPE[var1];
         }

         SistemaFC.radioDib[var1] = SistemaFC.radio[var1] * var24;
         double var4 = valBE[var1] / 2.0D;
         double var6 = SistemaFC.valPE[var1] / (indice[var1] - 1.0D);
         curv1[var1] = (var4 + var6) / 2000.0D;
         curv2[var1] = (var4 - var6) / 2000.0D;
         double var10 = Math.abs(curv1[var1]);
         double var26 = curv1[var1] / var10;
         if (var10 <= 1.0E-10D) {
            radio1[var1] = 1.0E13D;
         } else {
            radio1[var1] = 1.0D / curv1[var1];
         }

         if (Math.abs(radio1[var1]) < var32 / var24) {
            radio1[var1] = var26 * var32 / var24;
            curv1[var1] = 1.0D / radio1[var1];
            curv2[var1] = curv1[var1] - var6 / 1000.0D;
         }

         double var14 = radio1[var1] * var24;
         double var12 = Math.abs(curv2[var1]);
         double var28 = curv2[var1] / var12;
         if (var12 <= 1.0E-10D) {
            radio2[var1] = 1.0E13D;
         } else {
            radio2[var1] = 1.0D / curv2[var1];
         }

         if (Math.abs(radio2[var1]) < var32 / var24) {
            radio2[var1] = var28 * var32 / var24;
            curv2[var1] = 1.0D / radio2[var1];
            curv1[var1] = curv2[var1] + var6 / 1000.0D;
            var10 = Math.abs(curv1[var1]);
            if (var10 <= 1.0E-10D) {
               radio1[var1] = 1.0E13D;
            } else {
               radio1[var1] = 1.0D / curv1[var1];
            }
         }

         var14 = radio1[var1] * var24;
         double var16 = radio2[var1] * var24;
         radioDib1[var1] = var14;
         valDib1PE[var1] = 500.0D / radioDib1[var1];
         double var8 = (double)((int)(radio1[var1] * 10.0D)) / 10.0D;
         escritura = (double)((int)(10.0D * var8 / SistemaFC.factorFO)) / 10.0D;
         labelValR1E[var1].setText(escritura + " mm");
         radioDib2[var1] = var16;
         valDib2PE[var1] = 500.0D / radioDib2[var1];
         var8 = (double)((int)(radio2[var1] * 10.0D)) / 10.0D;
         escritura = (double)((int)(10.0D * var8 / SistemaFC.factorFO)) / 10.0D;
         labelValR2E[var1].setText(escritura + " mm");
         if (SistemaFC.valPE[var1] < 0.0D) {
            espesor[var1] = 3.0D;
         } else {
            espesor[var1] = SistemaFC.valPE[var1] * 2.0D;
         }
      } else {
         labelValFE[var1].setText("---");
         labelValR1E[var1].setText("---");
         labelValR2E[var1].setText("---");
         labelValNE[var1].setText("---");
         labelValAE[var1].setText("---");
         sliderValZE[var1].setEnabled(false);
         sliderValBE[var1].setEnabled(false);
         sliderValNE[var1].setEnabled(false);
         sliderValAE[var1].setEnabled(false);
         SistemaFC.existe[var1] = 0;
      }

   }

   public static void datosCorrederaHLente(int var0) {
      sliderValHE[var0].setValue((int)(SistemaFC.valPE[var0] * 10.0D * SistemaFC.factorFO + 0.5D));
      sliderValHE[var0].setMinimum(-560);
      sliderValHE[var0].setMaximum(560);
      sliderValHE[var0].setPaintTicks(true);
      sliderValHE[var0].setMajorTickSpacing(280);
      sliderValHE[var0].setMinorTickSpacing(56);
      sliderValHE[var0].setBounds(80, 100, 190, 30);
   }

   public static void datosCorrederaHDiafragma(int var0) {
      sliderValHE[var0].setValue((int)SistemaFC.valDE[var0]);
      sliderValHE[var0].setMinimum(0);
      sliderValHE[var0].setMaximum(diamMax);
      sliderValHE[var0].setPaintTicks(true);
      sliderValHE[var0].setMajorTickSpacing(10);
      sliderValHE[var0].setMinorTickSpacing(2);
      sliderValHE[var0].setBounds(80, 100, 190, 30);
   }

   static {
      lang = SistemaFC.lang;
      text = new String[][]{{"MARXA EXACTA", "MARCHA EXACTA", "EXACT RAY TRACING"}, {"Objecte", "Objeto", "Object"}, {"Lents i diafragmes", "Lentes y diafragmas", "Lenses and stops"}, {"Imatge", "Imagen", "Image"}, {"Menú principal", "Menú principal", "Main menu"}, {"Eix", "Eje", "Axis"}, {"Costat", "Lado", "Side"}, {"Posició", "Posición", "Position"}, {"Semicamp", "Semicampo", "Half field"}, {"Radis", "Radios", "Radii"}, {"Element", "Elemento", "Element"}, {"Lent", "Lente", "Lens"}, {"Diafragma", "Diafragma", "Stop"}, {"Diàmetre", "Diámetro", "Diameter"}, {"Potència", "Potencia", "Power"}, {"Focal", "Focal", "Focal"}, {"Infinit", "Infinito", "Infinite"}, {"Distància des del pla paraxial", "Distancia desde el plano paraxial", "Distance from the paraxial plane"}, {"Escala", "Escala", "Scale"}, {"Index", "Indice", "Index"}, {"Abbe", "Abbe", "Abbe"}, {"Monocromàtica", "Monocromática", "Monochromatic"}, {"Color", "Color", "Color"}, {"Posició de la imatge paraxial", "Posición de la imagen paraxial", "Paraxial image position"}, {"F. de mèrit en eix", "F. de Mérito en eje", "On-axis Merit F."}, {"F. de mèrit en camp", "F. de Mérito en campo", "Off-axis Merit F."}};
      grisAzulado = new Color(102, 102, 153);
      lado = 1.0D;
      panelE = new JPanel[7];
      panelVE = new JPanel[7];
      buttonE = new JButton[7];
      nomBoton = new String[7];
      valPosObj = new JLabel[5];
      leyendaPosObj = new String[]{"-570", "-285", "0", "285", "570"};
      valCampo = new JLabel[5];
      leyendaCampo = new String[]{"0", "5", "10", "15", "20"};
      valPosImag = new JLabel[5];
      leyendaPosImag = new String[]{"-12", "-6", "0", "6", "12"};
      labelMerito = new JLabel[2];
      labelValMerito = new JLabel[2];
      buttonL = new JRadioButton[7];
      buttonD = new JRadioButton[7];
      grupo = new ButtonGroup[7];
      labelE = new JLabel[7];
      labelZE = new JLabel[7];
      labelHE = new JLabel[7];
      labelFE = new JLabel[7];
      labelBE = new JLabel[7];
      labelValZE = new JLabel[7];
      labelValHE = new JLabel[7];
      labelValFE = new JLabel[7];
      labelValBE = new JLabel[7];
      labelValR1E = new JLabel[7];
      labelValR2E = new JLabel[7];
      labelNE = new JLabel[7];
      labelAE = new JLabel[7];
      labelValNE = new JLabel[7];
      labelValAE = new JLabel[7];
      sliderValZE = new JSlider[7];
      sliderValHE = new JSlider[7];
      sliderValFE = new JSlider[7];
      sliderValBE = new JSlider[7];
      listenerValZE = new ChangeListener[7];
      listenerValHE = new ChangeListener[7];
      listenerValFE = new ChangeListener[7];
      listenerValBE = new ChangeListener[7];
      valPos = new JLabel[7][5];
      valPot = new JLabel[7][5];
      valDiam = new JLabel[7][4];
      leyendaPos = new String[]{"0", "142", "285", "427", "570"};
      leyendaPot = new String[]{"-56", "-28", "0", "28", "56"};
      leyendaDiam = new String[]{"0", "7", "14", "21"};
      sliderValNE = new JSlider[7];
      sliderValAE = new JSlider[7];
      listenerValNE = new ChangeListener[7];
      listenerValAE = new ChangeListener[7];
      unidad = new String[7];
      numLentes = 0;
      numSup = 0;
      nir = 10;
      nia = 20;
      elemento = new int[8];
      tramo = new int[2][8];
      numTramos = new int[2];
      numColores = 1;
      valZParax = 0.0D;
      valZImag = 0.0D;
      escala = 1.0D;
      espesor = new double[8];
      valBE = new double[8];
      curv1 = new double[8];
      curv2 = new double[8];
      radio1 = new double[8];
      radio2 = new double[8];
      radioDib1 = new double[8];
      radioDib2 = new double[8];
      valDib1PE = new double[8];
      valDib2PE = new double[8];
      dze1 = new double[8];
      dze2 = new double[8];
      dzb1 = new double[8];
      dzb2 = new double[8];
      diste = new double[8];
      distb = new double[8];
      diamMax = AnteproyectoFC.diamMax;
      xpr = new double[140];
      ypr = new double[140];
      hpr = new double[21];
      indiceDib = 1.5D;
      indice = new double[7];
      abbe = new double[7];
      pintarRayos = new int[]{0, 0};
      dibuja = 0;
      calcula = 0;
      fuente1 = new Font("Dialog", 1, 20);
      fuente2 = new Font("Dialog", 1, 12);
      fuente3 = new Font("Dialog", 1, 11);
      fuente4 = new Font("Dialog", 1, 9);
   }

   class SliderListener implements ChangeListener {
      JLabel tf;

      public SliderListener(JLabel var2) {
         this.tf = var2;
      }

      public void stateChanged(ChangeEvent var1) {
         JSlider var2 = (JSlider)var1.getSource();
         if (this.tf == ExactoFC.labelValZObj) {
            SistemaFC.valZObj[0] = (double)var2.getValue() / SistemaFC.factorFO;
            int var5;
            if (SistemaFC.valZObj[0] == -1600.0D) {
               SistemaFC.existeObjeto[0] = 0;
               this.tf.setText("----");
               ExactoFC.calcula = 0;
               ExactoFC.sliderValHObj.setEnabled(false);

               for(var5 = 0; var5 < 5; ++var5) {
                  ExactoFC.valCampo[var5].setVisible(false);
               }
            } else {
               SistemaFC.existeObjeto[0] = 1;
               ExactoFC.sliderValHObj.setEnabled(true);

               for(var5 = 0; var5 < 5; ++var5) {
                  ExactoFC.valCampo[var5].setVisible(true);
               }

               if (!(SistemaFC.valZObj[0] >= 1600.0D) && !(SistemaFC.valZObj[0] <= -1500.0D)) {
                  SistemaFC.zObjeto[0] = SistemaFC.valZObj[0];
                  ExactoFC.escritura = (double)((int)(10.0D * SistemaFC.valZObj[0] / SistemaFC.factorFO)) / 10.0D;
                  this.tf.setText(ExactoFC.escritura + " mm");
               } else {
                  SistemaFC.zObjeto[0] = -1.0E10D;
                  this.tf.setText(ExactoFC.text[16][ExactoFC.lang]);
               }

               ExactoFC.calcula = 1;
            }
         }

         if (this.tf == ExactoFC.labelValHObj) {
            SistemaFC.campo[0] = (double)var2.getValue() / 10.0D;
            this.tf.setText(SistemaFC.campo[0] + " º");
            ExactoFC.calcula = 1;
         }

         if (this.tf == ExactoFC.labelValZImag) {
            ExactoFC.valZImag = (double)var2.getValue() / 10.0D;
            if (ExactoFC.valZImag >= 1600.0D) {
               ExactoFC.valZImag = 1.0E10D;
               this.tf.setText(ExactoFC.text[16][ExactoFC.lang]);
            } else {
               this.tf.setText(ExactoFC.valZImag + " mm");
            }

            ExactoFC.valZImag = (double)var2.getValue() * SistemaFC.factorFO / 10.0D;
            ExactoFC.calcula = 2;
         }

         if (this.tf == ExactoFC.labelValHImag) {
            ExactoFC.escala = Math.pow(10.0D, (double)var2.getValue() / 100.0D);
            ExactoFC.lado = (double)((int)(ExactoFC.escala * 100.0D / SistemaFC.factorFO)) / 100.0D;
            this.tf.setText(ExactoFC.lado + " mm");
            ExactoFC.labelDiagramas2.setText(ExactoFC.text[6][ExactoFC.lang] + " = " + ExactoFC.lado + " mm");
            ExactoFC.calcula = 3;
         }

         if (this.tf == ExactoFC.labelValZE[1] || this.tf == ExactoFC.labelValHE[1] || this.tf == ExactoFC.labelValBE[1] || this.tf == ExactoFC.labelValNE[1] || this.tf == ExactoFC.labelValAE[1]) {
            ExactoFC.this.datosElemento(var2, this.tf, 1);
            ExactoFC.diste[1] = ExactoFC.dze2[1] - ExactoFC.dze1[2];
            ExactoFC.distb[1] = ExactoFC.dzb2[1] - ExactoFC.dzb1[2];
            if (ExactoFC.diste[1] > ExactoFC.distb[1]) {
               SistemaFC.dist[1] = ExactoFC.diste[1];
            } else {
               SistemaFC.dist[1] = ExactoFC.distb[1];
            }

            if (SistemaFC.valZE[2] < SistemaFC.valZE[1] + SistemaFC.dist[1]) {
               SistemaFC.valZE[2] = SistemaFC.valZE[1] + SistemaFC.dist[1];
            }

            if (SistemaFC.valZE[3] < SistemaFC.valZE[2] + SistemaFC.dist[2]) {
               SistemaFC.valZE[3] = SistemaFC.valZE[2] + SistemaFC.dist[2];
            }

            if (SistemaFC.valZE[4] < SistemaFC.valZE[3] + SistemaFC.dist[3]) {
               SistemaFC.valZE[4] = SistemaFC.valZE[3] + SistemaFC.dist[3];
            }

            if (SistemaFC.valZE[5] < SistemaFC.valZE[4] + SistemaFC.dist[4]) {
               SistemaFC.valZE[5] = SistemaFC.valZE[4] + SistemaFC.dist[4];
            }

            if (SistemaFC.valZE[6] < SistemaFC.valZE[5] + SistemaFC.dist[5]) {
               SistemaFC.valZE[6] = SistemaFC.valZE[5] + SistemaFC.dist[5];
            }

            ExactoFC.sliderValZE[2].setValue((int)SistemaFC.valZE[2]);
            ExactoFC.sliderValZE[3].setValue((int)SistemaFC.valZE[3]);
            ExactoFC.sliderValZE[4].setValue((int)SistemaFC.valZE[4]);
            ExactoFC.sliderValZE[5].setValue((int)SistemaFC.valZE[5]);
            ExactoFC.sliderValZE[6].setValue((int)SistemaFC.valZE[6]);
            ExactoFC.calcula = 1;
         }

         if (this.tf == ExactoFC.labelValZE[2] || this.tf == ExactoFC.labelValHE[2] || this.tf == ExactoFC.labelValBE[2] || this.tf == ExactoFC.labelValNE[2] || this.tf == ExactoFC.labelValAE[2]) {
            ExactoFC.this.datosElemento(var2, this.tf, 2);
            ExactoFC.diste[1] = ExactoFC.dze2[1] - ExactoFC.dze1[2];
            ExactoFC.distb[1] = ExactoFC.dzb2[1] - ExactoFC.dzb1[2];
            if (ExactoFC.diste[1] > ExactoFC.distb[1]) {
               SistemaFC.dist[1] = ExactoFC.diste[1];
            } else {
               SistemaFC.dist[1] = ExactoFC.distb[1];
            }

            ExactoFC.diste[2] = ExactoFC.dze2[2] - ExactoFC.dze1[3];
            ExactoFC.distb[2] = ExactoFC.dzb2[2] - ExactoFC.dzb1[3];
            if (ExactoFC.diste[2] > ExactoFC.distb[2]) {
               SistemaFC.dist[2] = ExactoFC.diste[2];
            } else {
               SistemaFC.dist[2] = ExactoFC.distb[2];
            }

            if (SistemaFC.valZE[1] > SistemaFC.valZE[2] - SistemaFC.dist[1]) {
               SistemaFC.valZE[1] = SistemaFC.valZE[2] - SistemaFC.dist[1];
            }

            if (SistemaFC.valZE[3] < SistemaFC.valZE[2] + SistemaFC.dist[2]) {
               SistemaFC.valZE[3] = SistemaFC.valZE[2] + SistemaFC.dist[2];
            }

            if (SistemaFC.valZE[4] < SistemaFC.valZE[3] + SistemaFC.dist[3]) {
               SistemaFC.valZE[4] = SistemaFC.valZE[3] + SistemaFC.dist[3];
            }

            if (SistemaFC.valZE[5] < SistemaFC.valZE[4] + SistemaFC.dist[4]) {
               SistemaFC.valZE[5] = SistemaFC.valZE[4] + SistemaFC.dist[4];
            }

            if (SistemaFC.valZE[6] < SistemaFC.valZE[5] + SistemaFC.dist[5]) {
               SistemaFC.valZE[6] = SistemaFC.valZE[5] + SistemaFC.dist[5];
            }

            ExactoFC.sliderValZE[1].setValue((int)SistemaFC.valZE[1]);
            ExactoFC.sliderValZE[3].setValue((int)SistemaFC.valZE[3]);
            ExactoFC.sliderValZE[4].setValue((int)SistemaFC.valZE[4]);
            ExactoFC.sliderValZE[5].setValue((int)SistemaFC.valZE[5]);
            ExactoFC.sliderValZE[6].setValue((int)SistemaFC.valZE[6]);
            ExactoFC.calcula = 1;
         }

         if (this.tf == ExactoFC.labelValZE[3] || this.tf == ExactoFC.labelValHE[3] || this.tf == ExactoFC.labelValBE[3] || this.tf == ExactoFC.labelValNE[3] || this.tf == ExactoFC.labelValAE[3]) {
            ExactoFC.this.datosElemento(var2, this.tf, 3);
            ExactoFC.diste[2] = ExactoFC.dze2[2] - ExactoFC.dze1[3];
            ExactoFC.distb[2] = ExactoFC.dzb2[2] - ExactoFC.dzb1[3];
            if (ExactoFC.diste[2] > ExactoFC.distb[2]) {
               SistemaFC.dist[2] = ExactoFC.diste[2];
            } else {
               SistemaFC.dist[2] = ExactoFC.distb[2];
            }

            ExactoFC.diste[3] = ExactoFC.dze2[3] - ExactoFC.dze1[4];
            ExactoFC.distb[3] = ExactoFC.dzb2[3] - ExactoFC.dzb1[4];
            if (ExactoFC.diste[3] > ExactoFC.distb[3]) {
               SistemaFC.dist[3] = ExactoFC.diste[3];
            } else {
               SistemaFC.dist[3] = ExactoFC.distb[3];
            }

            if (SistemaFC.valZE[2] > SistemaFC.valZE[3] - SistemaFC.dist[2]) {
               SistemaFC.valZE[2] = SistemaFC.valZE[3] - SistemaFC.dist[2];
            }

            if (SistemaFC.valZE[1] > SistemaFC.valZE[2] - SistemaFC.dist[1]) {
               SistemaFC.valZE[1] = SistemaFC.valZE[2] - SistemaFC.dist[1];
            }

            if (SistemaFC.valZE[4] < SistemaFC.valZE[3] + SistemaFC.dist[3]) {
               SistemaFC.valZE[4] = SistemaFC.valZE[3] + SistemaFC.dist[3];
            }

            if (SistemaFC.valZE[5] < SistemaFC.valZE[4] + SistemaFC.dist[4]) {
               SistemaFC.valZE[5] = SistemaFC.valZE[4] + SistemaFC.dist[4];
            }

            if (SistemaFC.valZE[6] < SistemaFC.valZE[5] + SistemaFC.dist[5]) {
               SistemaFC.valZE[6] = SistemaFC.valZE[5] + SistemaFC.dist[5];
            }

            ExactoFC.sliderValZE[1].setValue((int)SistemaFC.valZE[1]);
            ExactoFC.sliderValZE[2].setValue((int)SistemaFC.valZE[2]);
            ExactoFC.sliderValZE[4].setValue((int)SistemaFC.valZE[4]);
            ExactoFC.sliderValZE[5].setValue((int)SistemaFC.valZE[5]);
            ExactoFC.sliderValZE[6].setValue((int)SistemaFC.valZE[6]);
            ExactoFC.calcula = 1;
         }

         if (this.tf == ExactoFC.labelValZE[4] || this.tf == ExactoFC.labelValHE[4] || this.tf == ExactoFC.labelValBE[4] || this.tf == ExactoFC.labelValNE[4] || this.tf == ExactoFC.labelValAE[4]) {
            ExactoFC.this.datosElemento(var2, this.tf, 4);
            ExactoFC.diste[3] = ExactoFC.dze2[3] - ExactoFC.dze1[4];
            ExactoFC.distb[3] = ExactoFC.dzb2[3] - ExactoFC.dzb1[4];
            if (ExactoFC.diste[3] > ExactoFC.distb[3]) {
               SistemaFC.dist[3] = ExactoFC.diste[3];
            } else {
               SistemaFC.dist[3] = ExactoFC.distb[3];
            }

            ExactoFC.diste[4] = ExactoFC.dze2[4] - ExactoFC.dze1[5];
            ExactoFC.distb[4] = ExactoFC.dzb2[4] - ExactoFC.dzb1[5];
            if (ExactoFC.diste[4] > ExactoFC.distb[4]) {
               SistemaFC.dist[4] = ExactoFC.diste[4];
            } else {
               SistemaFC.dist[4] = ExactoFC.distb[4];
            }

            if (SistemaFC.valZE[3] > SistemaFC.valZE[4] - SistemaFC.dist[3]) {
               SistemaFC.valZE[3] = SistemaFC.valZE[4] - SistemaFC.dist[3];
            }

            if (SistemaFC.valZE[2] > SistemaFC.valZE[3] - SistemaFC.dist[2]) {
               SistemaFC.valZE[2] = SistemaFC.valZE[3] - SistemaFC.dist[2];
            }

            if (SistemaFC.valZE[1] > SistemaFC.valZE[2] - SistemaFC.dist[1]) {
               SistemaFC.valZE[1] = SistemaFC.valZE[2] - SistemaFC.dist[1];
            }

            if (SistemaFC.valZE[5] < SistemaFC.valZE[4] + SistemaFC.dist[4]) {
               SistemaFC.valZE[5] = SistemaFC.valZE[4] + SistemaFC.dist[4];
            }

            if (SistemaFC.valZE[6] < SistemaFC.valZE[5] + SistemaFC.dist[5]) {
               SistemaFC.valZE[6] = SistemaFC.valZE[5] + SistemaFC.dist[5];
            }

            ExactoFC.sliderValZE[1].setValue((int)SistemaFC.valZE[1]);
            ExactoFC.sliderValZE[2].setValue((int)SistemaFC.valZE[2]);
            ExactoFC.sliderValZE[3].setValue((int)SistemaFC.valZE[3]);
            ExactoFC.sliderValZE[5].setValue((int)SistemaFC.valZE[5]);
            ExactoFC.sliderValZE[6].setValue((int)SistemaFC.valZE[6]);
            ExactoFC.calcula = 1;
         }

         if (this.tf == ExactoFC.labelValZE[5] || this.tf == ExactoFC.labelValHE[5] || this.tf == ExactoFC.labelValBE[5] || this.tf == ExactoFC.labelValNE[5] || this.tf == ExactoFC.labelValAE[5]) {
            ExactoFC.this.datosElemento(var2, this.tf, 5);
            ExactoFC.diste[4] = ExactoFC.dze2[4] - ExactoFC.dze1[5];
            ExactoFC.distb[4] = ExactoFC.dzb2[4] - ExactoFC.dzb1[5];
            if (ExactoFC.diste[4] > ExactoFC.distb[4]) {
               SistemaFC.dist[4] = ExactoFC.diste[4];
            } else {
               SistemaFC.dist[4] = ExactoFC.distb[4];
            }

            ExactoFC.diste[5] = ExactoFC.dze2[5] - ExactoFC.dze1[6];
            ExactoFC.distb[5] = ExactoFC.dzb2[5] - ExactoFC.dzb1[6];
            if (ExactoFC.diste[5] > ExactoFC.distb[5]) {
               SistemaFC.dist[5] = ExactoFC.diste[5];
            } else {
               SistemaFC.dist[5] = ExactoFC.distb[5];
            }

            if (SistemaFC.valZE[4] > SistemaFC.valZE[5] - SistemaFC.dist[4]) {
               SistemaFC.valZE[4] = SistemaFC.valZE[5] - SistemaFC.dist[4];
            }

            if (SistemaFC.valZE[3] > SistemaFC.valZE[4] - SistemaFC.dist[3]) {
               SistemaFC.valZE[3] = SistemaFC.valZE[4] - SistemaFC.dist[3];
            }

            if (SistemaFC.valZE[2] > SistemaFC.valZE[3] - SistemaFC.dist[2]) {
               SistemaFC.valZE[2] = SistemaFC.valZE[3] - SistemaFC.dist[2];
            }

            if (SistemaFC.valZE[1] > SistemaFC.valZE[2] - SistemaFC.dist[1]) {
               SistemaFC.valZE[1] = SistemaFC.valZE[2] - SistemaFC.dist[1];
            }

            if (SistemaFC.valZE[6] < SistemaFC.valZE[5] + SistemaFC.dist[5]) {
               SistemaFC.valZE[6] = SistemaFC.valZE[5] + SistemaFC.dist[5];
            }

            ExactoFC.sliderValZE[1].setValue((int)SistemaFC.valZE[1]);
            ExactoFC.sliderValZE[2].setValue((int)SistemaFC.valZE[2]);
            ExactoFC.sliderValZE[3].setValue((int)SistemaFC.valZE[3]);
            ExactoFC.sliderValZE[4].setValue((int)SistemaFC.valZE[4]);
            ExactoFC.sliderValZE[6].setValue((int)SistemaFC.valZE[6]);
            ExactoFC.calcula = 1;
         }

         if (this.tf == ExactoFC.labelValZE[6] || this.tf == ExactoFC.labelValHE[6] || this.tf == ExactoFC.labelValBE[6] || this.tf == ExactoFC.labelValNE[6] || this.tf == ExactoFC.labelValAE[6]) {
            ExactoFC.this.datosElemento(var2, this.tf, 6);
            ExactoFC.diste[5] = ExactoFC.dze2[5] - ExactoFC.dze1[6];
            ExactoFC.distb[5] = ExactoFC.dzb2[5] - ExactoFC.dzb1[6];
            if (ExactoFC.diste[5] > ExactoFC.distb[5]) {
               SistemaFC.dist[5] = ExactoFC.diste[5];
            } else {
               SistemaFC.dist[5] = ExactoFC.distb[5];
            }

            if (SistemaFC.valZE[5] > SistemaFC.valZE[6] - SistemaFC.dist[5]) {
               SistemaFC.valZE[5] = SistemaFC.valZE[6] - SistemaFC.dist[5];
            }

            if (SistemaFC.valZE[4] > SistemaFC.valZE[5] - SistemaFC.dist[4]) {
               SistemaFC.valZE[4] = SistemaFC.valZE[5] - SistemaFC.dist[4];
            }

            if (SistemaFC.valZE[3] > SistemaFC.valZE[4] - SistemaFC.dist[3]) {
               SistemaFC.valZE[3] = SistemaFC.valZE[4] - SistemaFC.dist[3];
            }

            if (SistemaFC.valZE[2] > SistemaFC.valZE[3] - SistemaFC.dist[2]) {
               SistemaFC.valZE[2] = SistemaFC.valZE[3] - SistemaFC.dist[2];
            }

            if (SistemaFC.valZE[1] > SistemaFC.valZE[2] - SistemaFC.dist[1]) {
               SistemaFC.valZE[1] = SistemaFC.valZE[2] - SistemaFC.dist[1];
            }

            ExactoFC.sliderValZE[1].setValue((int)SistemaFC.valZE[1]);
            ExactoFC.sliderValZE[2].setValue((int)SistemaFC.valZE[2]);
            ExactoFC.sliderValZE[3].setValue((int)SistemaFC.valZE[3]);
            ExactoFC.sliderValZE[4].setValue((int)SistemaFC.valZE[4]);
            ExactoFC.sliderValZE[5].setValue((int)SistemaFC.valZE[5]);
            ExactoFC.calcula = 1;
         }

         ExactoFC.calculaSistema(ExactoFC.calcula);
      }
   }
}
