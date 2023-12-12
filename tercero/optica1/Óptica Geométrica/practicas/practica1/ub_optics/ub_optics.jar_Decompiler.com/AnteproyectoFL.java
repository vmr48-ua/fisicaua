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
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class AnteproyectoFL extends JFrame implements ActionListener {
   static int lang;
   static String[][] text;
   static AnteproyectoFL anteproyecto;
   static JPanel panelBase;
   static JPanel[] panelObj;
   static JPanel panelCard;
   static JPanel[] panelE;
   static Color grisAzulado;
   static DibujoSistemaFL canvasSistema;
   static PuntosCardinalesFL puntosCardinales;
   static JToggleButton buttonVirtual;
   static JToggleButton buttonObjVirtual;
   static JButton buttonCard;
   static JButton buttonMP;
   static JButton[] buttonObj;
   static JButton[] buttonE;
   static String[] nomBoton;
   static JLabel[] labelObj;
   static JLabel[] labelZObj;
   static JLabel[] labelValZObj;
   static JLabel[] labelHObj;
   static JLabel[] labelValHObj;
   static JRadioButton[] buttonR;
   static JRadioButton[] buttonV;
   static ButtonGroup[] grupoO;
   static JSlider[] sliderValZObj;
   static ChangeListener[] listenerValZObj;
   static JLabel[][] valPosObj;
   static String[] leyendaPosObj;
   static JSlider[] sliderValHObj;
   static ChangeListener[] listenerValHObj;
   static JLabel[][] valCampo;
   static String[] leyendaCampo;
   static JRadioButton[] buttonL;
   static JRadioButton[] buttonD;
   static ButtonGroup[] grupo;
   static JLabel[] labelE;
   static JLabel[] labelZE;
   static JLabel[] labelHE;
   static JLabel[] labelFE;
   static JLabel[] labelValZE;
   static JLabel[] labelValHE;
   static JLabel[] labelValFE;
   static JSlider[] sliderValZE;
   static ChangeListener[] listenerValZE;
   static JSlider[] sliderValHE;
   static ChangeListener[] listenerValHE;
   static JLabel[][] valPos;
   static String[] leyendaPos;
   static JLabel[][] valPot;
   static String[] leyendaPot;
   static JLabel[][] valDiam;
   static String[] leyendaDiam;
   static String texto1;
   static String texto2;
   static String[] unidad;
   static int verImagenVirtual;
   static int verObjetoVirtual;
   int cuadro;
   int numLentes = 0;
   int numSup = 0;
   static int[] elemento;
   static int numElementos;
   static int numUltimoElemento;
   static int numUltimaLente;
   static int numPupilaE;
   static int numPupilaS;
   static int numDiafragma;
   static int numRelDiafragma;
   static int[][] tramo;
   static int[] numTramos;
   static int[] tramoCard;
   static int numTramosCard;
   static double[] zF;
   static double[] zH;
   static double[][] zImag;
   static double[][] yImag;
   static double zPE;
   static double DPE;
   static double zPS;
   static double DPS;
   static int[][][] pasa;
   static double[][][] apRayo;
   static double[][][] bpRayo;
   static double[][][] zaRayo;
   static double[][][] yaRayo;
   static double[][][] zpRayo;
   static double[][][] ypRayo;
   static double[][][] hRayo;
   static double[][] aRayoCard;
   static double[][] bRayoCard;
   static double[][] apRayoCard;
   static double[][] bpRayoCard;
   static double[][] zaRayoCard;
   static double[][] yaRayoCard;
   static double[][] zpRayoCard;
   static double[][] ypRayoCard;
   static double[][] hRayoCard;
   static double[][] fRayoCard;
   static int diamMax;
   static double indice;
   static int[] pintarRayos;
   static Font fuente1;
   static Font fuente2;
   static Font fuente3;
   static Font fuente4;
   static Color colorTexto1;
   static Color colorTexto2;

   public static void abrir() {
      SistemaFL.panelBase.add(panelBase);
      SistemaFL.panelBase.add(canvasSistema);

      int var0;
      int var1;
      for(var0 = 0; var0 < 2; ++var0) {
         if (SistemaFL.valZObj[var0] == -1600.0D) {
            SistemaFL.existeObjeto[var0] = 0;
            labelValZObj[var0].setText("----");
            sliderValHObj[var0].setEnabled(false);

            for(var1 = 0; var1 < 5; ++var1) {
               valCampo[var0][var1].setVisible(false);
            }
         } else {
            SistemaFL.existeObjeto[var0] = 1;
            sliderValHObj[var0].setEnabled(true);

            for(var1 = 0; var1 < 5; ++var1) {
               valCampo[var0][var1].setVisible(true);
            }

            if (!(SistemaFL.valZObj[var0] <= -1500.0D) && !(SistemaFL.valZObj[var0] >= 1600.0D)) {
               labelValZObj[var0].setText(SistemaFL.valZObj[var0] + " mm");
               SistemaFL.zObjeto[var0] = SistemaFL.valZObj[var0];
            } else {
               labelValZObj[var0].setText(text[14][lang]);
               SistemaFL.zObjeto[var0] = -1.0E10D;
            }
         }
      }

      for(var0 = 0; var0 < 2; ++var0) {
         if (SistemaFL.real[var0] == 1) {
            buttonR[var0].setSelected(true);
         } else {
            buttonV[var0].setSelected(true);
         }

         labelValHObj[var0].setText(SistemaFL.campo[var0] + "º");
         sliderValZObj[var0].setValue((int)SistemaFL.valZObj[var0]);
         sliderValHObj[var0].setValue((int)(SistemaFL.campo[var0] * 10.0D));
      }

      for(var0 = 1; var0 < 7; ++var0) {
         if (SistemaFL.valPE[var0] < 0.0D) {
            SistemaFL.espesor[var0] = 5.0D;
         } else {
            SistemaFL.espesor[var0] = SistemaFL.valDibPE[var0] * 2.0D;
         }

         if (SistemaFL.valPE[var0] != 0.0D) {
            sliderValZE[var0].setEnabled(true);
            SistemaFL.existe[var0] = 1;
            SistemaFL.curv[var0] = SistemaFL.valPE[var0] / (2.0D * indice - 2.0D);
            if (Math.abs(SistemaFL.curv[var0]) <= 1.0E-10D) {
               SistemaFL.radio[var0] = 1.0E13D;
               SistemaFL.radioDib[var0] = 1.0E13D;
            } else {
               SistemaFL.radio[var0] = 2000.0D * (indice - 1.0D) / SistemaFL.valPE[var0];
               SistemaFL.radioDib[var0] = SistemaFL.radio[var0] * SistemaFL.valPE[var0] / SistemaFL.valDibPE[var0];
            }
         }
      }

      for(var0 = 1; var0 < 7; ++var0) {
         if (SistemaFL.tipoE[var0] == 1) {
            buttonL[var0].setSelected(true);
         } else {
            buttonD[var0].setSelected(true);
         }

         labelValZE[var0].setText(SistemaFL.valZE[var0] + " mm");
         sliderValZE[var0].setValue((int)SistemaFL.valZE[var0]);
         if (SistemaFL.tipoE[var0] == 1) {
            unidad[var0] = " diop";
            labelHE[var0].setText(text[8][lang]);
            labelFE[var0].setText(text[15][lang]);

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
            if (SistemaFL.valPE[var0] != 0.0D) {
               sliderValZE[var0].setEnabled(true);
            } else {
               sliderValZE[var0].setEnabled(false);
            }
         } else {
            labelHE[var0].setText(text[13][lang]);
            unidad[var0] = " mm";
            labelFE[var0].setText(" ");
            labelValFE[var0].setText(" ");
            if (SistemaFL.valDE[var0] != (double)diamMax) {
               labelValHE[var0].setText(SistemaFL.valDE[var0] + " mm");
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
            if (SistemaFL.valDE[var0] != (double)diamMax) {
               sliderValZE[var0].setEnabled(true);
            } else {
               sliderValZE[var0].setEnabled(false);
            }
         }
      }

      for(var0 = 0; var0 < 2; ++var0) {
         FuncionesParaxialesFL.tablaTramos(var0);
         FuncionesParaxialesFL.marchaParaxial(var0);
         FuncionesParaxialesFL.coeficientesRayos(var0);
         FuncionesParaxialesFL.interseccionesRayos(var0);
      }

      FuncionesParaxialesFL.calculaPupilas();
      canvasSistema.redraw();
   }

   public AnteproyectoFL() {
      SistemaFL.valZE[0] = 0.0D;
      SistemaFL.existe[0] = 1;
      SistemaFL.valZE[7] = 1600.0D;
      SistemaFL.existe[7] = 1;

      int var1;
      for(var1 = 0; var1 < 7; ++var1) {
         for(int var2 = 0; var2 < 22; ++var2) {
            pasa[0][var1][var2] = 0;
            pasa[1][var1][var2] = 0;
         }
      }

      for(var1 = 1; var1 < 7; ++var1) {
         SistemaFL.valZE[var1] = 200.0D + 200.0D * (double)var1;
         SistemaFL.valPE[var1] = 0.0D;
         SistemaFL.valDibPE[var1] = 0.0D;
         SistemaFL.existe[var1] = 0;
         SistemaFL.valDE[var1] = (double)diamMax;
         SistemaFL.dist[var1] = 0.0D;
         SistemaFL.tipoE[var1] = 1;
         SistemaFL.altura[var1] = (double)diamMax / 2.0D;
      }

      canvasSistema = new DibujoSistemaFL();
      canvasSistema.setBackground(Color.black);
      canvasSistema.setBounds(30, 0, 800, 210);
      puntosCardinales = new PuntosCardinalesFL(canvasSistema);
      puntosCardinales.setBackground(Color.black);
      puntosCardinales.setBounds(0, 230, 800, 220);
      panelBase = new JPanel();
      panelBase.setLayout((LayoutManager)null);
      panelBase.setBackground(Color.gray);
      panelBase.setBounds(0, 230, 300, 220);
      JLabel var3 = new JLabel(text[0][lang]);
      var3.setBounds(100, 5, 220, 27);
      var3.setForeground(colorTexto1);
      var3.setFont(fuente2);
      panelBase.add(var3);
      buttonObj[0] = new JButton(text[1][lang] + " 1");
      buttonObj[0].setBounds(10, 32, 138, 27);
      buttonObj[0].setForeground(colorTexto2);
      buttonObj[0].setFont(fuente2);
      buttonObj[1] = new JButton(text[1][lang] + " 2");
      buttonObj[1].setBounds(148, 32, 138, 27);
      buttonObj[1].setForeground(colorTexto2);
      buttonObj[1].setFont(fuente2);
      buttonObj[0].addActionListener(this);
      buttonObj[1].addActionListener(this);
      buttonObj[0].setActionCommand("buttonObj0");
      buttonObj[1].setActionCommand("buttonObj1");
      panelBase.add(buttonObj[0]);
      panelBase.add(buttonObj[1]);
      JLabel var4 = new JLabel(text[2][lang]);
      var4.setBounds(80, 59, 200, 27);
      var4.setForeground(colorTexto1);
      var4.setFont(fuente2);
      panelBase.add(var4);

      for(var1 = 1; var1 < 7; ++var1) {
         nomBoton[var1] = new String("" + var1);
         buttonE[var1] = new JButton(nomBoton[var1]);
         buttonE[var1].setBounds(10 + (var1 - 1) * 46, 86, 46, 27);
         buttonE[var1].setForeground(colorTexto2);
         buttonE[var1].setFont(fuente2);
         buttonE[var1].addActionListener(this);
         buttonE[var1].setActionCommand(nomBoton[var1]);
         panelBase.add(buttonE[var1]);
      }

      buttonObjVirtual = new JToggleButton(text[3][lang]);
      buttonObjVirtual.setBounds(10, 113, 138, 27);
      buttonObjVirtual.setForeground(colorTexto2);
      buttonObjVirtual.setFont(fuente4);
      buttonObjVirtual.addActionListener(this);
      buttonObjVirtual.setActionCommand("buttonObjVirtual");
      panelBase.add(buttonObjVirtual);
      buttonVirtual = new JToggleButton(text[4][lang]);
      buttonVirtual.setBounds(148, 113, 138, 27);
      buttonVirtual.setForeground(colorTexto2);
      buttonVirtual.setFont(fuente4);
      buttonVirtual.addActionListener(this);
      buttonVirtual.setActionCommand("buttonVirtual");
      panelBase.add(buttonVirtual);
      buttonCard = new JButton(text[5][lang]);
      buttonCard.setBounds(10, 140, 276, 27);
      buttonCard.setForeground(colorTexto2);
      buttonCard.setFont(fuente2);
      buttonCard.addActionListener(this);
      buttonCard.setActionCommand("buttonCard");
      panelBase.add(buttonCard);
      buttonMP = new JButton(text[6][lang]);
      buttonMP.setBounds(10, 167, 276, 27);
      buttonMP.setForeground(colorTexto2);
      buttonMP.setFont(fuente2);
      buttonMP.addActionListener(this);
      buttonMP.setActionCommand("buttonMP");
      panelBase.add(buttonMP);

      for(var1 = 0; var1 < 2; ++var1) {
         panelObj[var1] = new JPanel();
         panelObj[var1].setLayout((LayoutManager)null);
         panelObj[var1].setBounds(300, 230, 500, 220);
         labelObj[var1] = new JLabel(text[1][lang] + " " + (var1 + 1));
         labelObj[var1].setBounds(20, 10, 200, 30);
         labelObj[var1].setForeground(Color.black);
         labelObj[var1].setFont(fuente2);
         panelObj[var1].add(labelObj[var1]);
         buttonR[var1] = new JRadioButton("Real");
         buttonR[var1].setBounds(200, 10, 100, 30);
         buttonR[var1].setFont(fuente2);
         buttonR[var1].setActionCommand("R");
         buttonR[var1].addActionListener(this);
         buttonR[var1].setSelected(true);
         buttonV[var1] = new JRadioButton("Virtual");
         buttonV[var1].setBounds(350, 10, 100, 30);
         buttonV[var1].setFont(fuente2);
         buttonV[var1].setActionCommand("V");
         buttonV[var1].addActionListener(this);
         grupoO[var1] = new ButtonGroup();
         grupoO[var1].add(buttonR[var1]);
         grupoO[var1].add(buttonV[var1]);
         panelObj[var1].add(buttonR[var1]);
         panelObj[var1].add(buttonV[var1]);
         labelZObj[var1] = new JLabel(text[7][lang]);
         labelZObj[var1].setBounds(20, 70, 100, 30);
         labelZObj[var1].setForeground(grisAzulado);
         labelZObj[var1].setFont(fuente2);
         panelObj[var1].add(labelZObj[var1]);
         labelValZObj[var1] = new JLabel("---");
         labelValZObj[var1].setBounds(100, 70, 100, 30);
         labelValZObj[var1].setForeground(grisAzulado);
         labelValZObj[var1].setFont(fuente2);
         panelObj[var1].add(labelValZObj[var1]);
         sliderValZObj[var1] = new JSlider(-1600, 1600, -1600);
         sliderValZObj[var1].setPaintTicks(true);
         sliderValZObj[var1].setMajorTickSpacing(800);
         sliderValZObj[var1].setMinorTickSpacing(100);
         sliderValZObj[var1].setPaintLabels(false);
         sliderValZObj[var1].setBounds(190, 70, 280, 30);
         valPosObj[var1][0] = new JLabel(leyendaPosObj[0]);
         valPosObj[var1][0].setBounds(180, 90, 60, 30);
         valPosObj[var1][0].setForeground(grisAzulado);
         valPosObj[var1][0].setFont(fuente2);
         valPosObj[var1][0].setVisible(true);
         panelObj[var1].add(valPosObj[var1][0]);
         valPosObj[var1][1] = new JLabel(leyendaPosObj[1]);
         valPosObj[var1][1].setBounds(250, 90, 60, 30);
         valPosObj[var1][1].setForeground(grisAzulado);
         valPosObj[var1][1].setFont(fuente2);
         valPosObj[var1][1].setVisible(true);
         panelObj[var1].add(valPosObj[var1][1]);
         valPosObj[var1][2] = new JLabel(leyendaPosObj[2]);
         valPosObj[var1][2].setBounds(325, 90, 60, 30);
         valPosObj[var1][2].setForeground(grisAzulado);
         valPosObj[var1][2].setFont(fuente2);
         valPosObj[var1][2].setVisible(true);
         panelObj[var1].add(valPosObj[var1][2]);
         valPosObj[var1][3] = new JLabel(leyendaPosObj[3]);
         valPosObj[var1][3].setBounds(388, 90, 60, 30);
         valPosObj[var1][3].setForeground(grisAzulado);
         valPosObj[var1][3].setFont(fuente2);
         valPosObj[var1][3].setVisible(true);
         panelObj[var1].add(valPosObj[var1][3]);
         valPosObj[var1][4] = new JLabel(leyendaPosObj[4]);
         valPosObj[var1][4].setBounds(450, 90, 60, 30);
         valPosObj[var1][4].setForeground(grisAzulado);
         valPosObj[var1][4].setFont(fuente2);
         valPosObj[var1][4].setVisible(true);
         panelObj[var1].add(valPosObj[var1][4]);
         listenerValZObj[var1] = new AnteproyectoFL.SliderListener(labelValZObj[var1]);
         sliderValZObj[var1].addChangeListener(listenerValZObj[var1]);
         panelObj[var1].add(sliderValZObj[var1]);
         labelHObj[var1] = new JLabel(text[9][lang]);
         labelHObj[var1].setBounds(20, 130, 100, 30);
         labelHObj[var1].setForeground(grisAzulado);
         labelHObj[var1].setFont(fuente2);
         panelObj[var1].add(labelHObj[var1]);
         labelValHObj[var1] = new JLabel("0.");
         labelValHObj[var1].setBounds(110, 130, 100, 30);
         labelValHObj[var1].setForeground(grisAzulado);
         labelValHObj[var1].setFont(fuente2);
         panelObj[var1].add(labelValHObj[var1]);
         sliderValHObj[var1] = new JSlider(0, 200, 0);
         sliderValHObj[var1].setPaintTicks(true);
         sliderValHObj[var1].setMajorTickSpacing(50);
         sliderValHObj[var1].setMinorTickSpacing(25);
         sliderValHObj[var1].setPaintLabels(false);
         sliderValHObj[var1].setBounds(190, 135, 280, 30);
         sliderValHObj[var1].setEnabled(false);
         valCampo[var1][0] = new JLabel(leyendaCampo[0]);
         valCampo[var1][0].setBounds(195, 155, 60, 30);
         valCampo[var1][0].setForeground(grisAzulado);
         valCampo[var1][0].setFont(fuente2);
         valCampo[var1][0].setVisible(false);
         panelObj[var1].add(valCampo[var1][0]);
         valCampo[var1][1] = new JLabel(leyendaCampo[1]);
         valCampo[var1][1].setBounds(262, 155, 60, 30);
         valCampo[var1][1].setForeground(grisAzulado);
         valCampo[var1][1].setFont(fuente2);
         valCampo[var1][1].setVisible(false);
         panelObj[var1].add(valCampo[var1][1]);
         valCampo[var1][2] = new JLabel(leyendaCampo[2]);
         valCampo[var1][2].setBounds(325, 155, 60, 30);
         valCampo[var1][2].setForeground(grisAzulado);
         valCampo[var1][2].setFont(fuente2);
         valCampo[var1][2].setVisible(false);
         panelObj[var1].add(valCampo[var1][2]);
         valCampo[var1][3] = new JLabel(leyendaCampo[3]);
         valCampo[var1][3].setBounds(388, 155, 60, 30);
         valCampo[var1][3].setForeground(grisAzulado);
         valCampo[var1][3].setFont(fuente2);
         valCampo[var1][3].setVisible(false);
         panelObj[var1].add(valCampo[var1][3]);
         valCampo[var1][4] = new JLabel(leyendaCampo[4]);
         valCampo[var1][4].setBounds(450, 155, 60, 30);
         valCampo[var1][4].setForeground(grisAzulado);
         valCampo[var1][4].setFont(fuente2);
         valCampo[var1][4].setVisible(false);
         panelObj[var1].add(valCampo[var1][4]);
         listenerValHObj[var1] = new AnteproyectoFL.SliderListener(labelValHObj[var1]);
         sliderValHObj[var1].addChangeListener(listenerValHObj[var1]);
         panelObj[var1].add(sliderValHObj[var1]);
      }

      for(var1 = 1; var1 < 7; ++var1) {
         panelE[var1] = new JPanel();
         panelE[var1].setLayout((LayoutManager)null);
         panelE[var1].setBounds(300, 230, 500, 220);
         unidad[var1] = " diop.";
         labelE[var1] = new JLabel(text[10][lang] + " " + var1);
         labelE[var1].setBounds(20, 10, 200, 30);
         labelE[var1].setForeground(Color.black);
         labelE[var1].setFont(fuente2);
         panelE[var1].add(labelE[var1]);
         buttonL[var1] = new JRadioButton(text[11][lang]);
         buttonL[var1].setBounds(200, 10, 100, 30);
         buttonL[var1].setFont(fuente2);
         buttonL[var1].addActionListener(this);
         buttonL[var1].setActionCommand("L");
         buttonL[var1].setSelected(true);
         buttonD[var1] = new JRadioButton(text[12][lang]);
         buttonD[var1].setBounds(350, 10, 100, 30);
         buttonD[var1].setFont(fuente2);
         buttonD[var1].addActionListener(this);
         buttonD[var1].setActionCommand("D");
         grupo[var1] = new ButtonGroup();
         grupo[var1].add(buttonL[var1]);
         grupo[var1].add(buttonD[var1]);
         panelE[var1].add(buttonL[var1]);
         panelE[var1].add(buttonD[var1]);
         labelZE[var1] = new JLabel(text[7][lang]);
         labelZE[var1].setBounds(20, 70, 100, 30);
         labelZE[var1].setForeground(grisAzulado);
         labelZE[var1].setFont(fuente2);
         panelE[var1].add(labelZE[var1]);
         labelValZE[var1] = new JLabel(SistemaFL.valZE[var1] + " mm");
         labelValZE[var1].setBounds(100, 70, 100, 30);
         labelValZE[var1].setForeground(grisAzulado);
         labelValZE[var1].setFont(fuente2);
         panelE[var1].add(labelValZE[var1]);
         sliderValZE[var1] = new JSlider(0, 1600, 200 + 200 * var1);
         sliderValZE[var1].setPaintTicks(true);
         sliderValZE[var1].setMajorTickSpacing(400);
         sliderValZE[var1].setMinorTickSpacing(50);
         sliderValZE[var1].setPaintLabels(false);
         sliderValZE[var1].setBounds(190, 70, 280, 30);
         sliderValZE[var1].setEnabled(false);
         listenerValZE[var1] = new AnteproyectoFL.SliderListener(labelValZE[var1]);
         sliderValZE[var1].addChangeListener(listenerValZE[var1]);
         sliderValZE[var1].setEnabled(false);
         panelE[var1].add(sliderValZE[var1]);
         valPos[var1][0] = new JLabel(leyendaPos[0]);
         valPos[var1][0].setBounds(190, 90, 60, 30);
         valPos[var1][0].setForeground(grisAzulado);
         valPos[var1][0].setFont(fuente2);
         panelE[var1].add(valPos[var1][0]);
         valPos[var1][1] = new JLabel(leyendaPos[1]);
         valPos[var1][1].setBounds(250, 90, 60, 30);
         valPos[var1][1].setForeground(grisAzulado);
         valPos[var1][1].setFont(fuente2);
         panelE[var1].add(valPos[var1][1]);
         valPos[var1][2] = new JLabel(leyendaPos[2]);
         valPos[var1][2].setBounds(315, 90, 60, 30);
         valPos[var1][2].setForeground(grisAzulado);
         valPos[var1][2].setFont(fuente2);
         panelE[var1].add(valPos[var1][2]);
         valPos[var1][3] = new JLabel(leyendaPos[3]);
         valPos[var1][3].setBounds(380, 90, 60, 30);
         valPos[var1][3].setForeground(grisAzulado);
         valPos[var1][3].setFont(fuente2);
         panelE[var1].add(valPos[var1][3]);
         valPos[var1][4] = new JLabel(leyendaPos[4]);
         valPos[var1][4].setBounds(450, 90, 60, 30);
         valPos[var1][4].setForeground(grisAzulado);
         valPos[var1][4].setFont(fuente2);
         panelE[var1].add(valPos[var1][4]);
         panelE[var1].add(sliderValZE[var1]);
         labelHE[var1] = new JLabel(text[8][lang]);
         labelHE[var1].setBounds(20, 130, 100, 30);
         labelHE[var1].setForeground(grisAzulado);
         labelHE[var1].setFont(fuente2);
         panelE[var1].add(labelHE[var1]);
         SistemaFL.altura[var1] = (double)diamMax / 2.0D;
         texto1 = "0 diop";
         labelValHE[var1] = new JLabel(texto1);
         labelValHE[var1].setBounds(100, 130, 100, 30);
         labelValHE[var1].setForeground(grisAzulado);
         labelValHE[var1].setFont(fuente2);
         panelE[var1].add(labelValHE[var1]);
         labelFE[var1] = new JLabel(text[15][lang]);
         labelFE[var1].setBounds(20, 150, 100, 30);
         labelFE[var1].setForeground(grisAzulado);
         labelFE[var1].setFont(fuente2);
         panelE[var1].add(labelFE[var1]);
         texto1 = "---";
         labelValFE[var1] = new JLabel(texto1);
         labelValFE[var1].setBounds(100, 150, 100, 30);
         labelValFE[var1].setForeground(grisAzulado);
         labelValFE[var1].setFont(fuente2);
         panelE[var1].add(labelValFE[var1]);
         sliderValHE[var1] = new JSlider(-200, 200, 0);
         datosCorrederaHLente(var1);
         listenerValHE[var1] = new AnteproyectoFL.SliderListener(labelValHE[var1]);
         sliderValHE[var1].addChangeListener(listenerValHE[var1]);
         valPot[var1][0] = new JLabel(leyendaPot[0]);
         valPot[var1][0].setBounds(190, 150, 60, 30);
         valPot[var1][0].setForeground(grisAzulado);
         valPot[var1][0].setFont(fuente2);
         panelE[var1].add(valPot[var1][0]);
         valPot[var1][1] = new JLabel(leyendaPot[1]);
         valPot[var1][1].setBounds(255, 150, 60, 30);
         valPot[var1][1].setForeground(grisAzulado);
         valPot[var1][1].setFont(fuente2);
         panelE[var1].add(valPot[var1][1]);
         valPot[var1][2] = new JLabel(leyendaPot[2]);
         valPot[var1][2].setBounds(328, 150, 60, 30);
         valPot[var1][2].setForeground(grisAzulado);
         valPot[var1][2].setFont(fuente2);
         panelE[var1].add(valPot[var1][2]);
         valPot[var1][3] = new JLabel(leyendaPot[3]);
         valPot[var1][3].setBounds(385, 150, 60, 30);
         valPot[var1][3].setForeground(grisAzulado);
         valPot[var1][3].setFont(fuente2);
         panelE[var1].add(valPot[var1][3]);
         valPot[var1][4] = new JLabel(leyendaPot[4]);
         valPot[var1][4].setBounds(450, 150, 60, 30);
         valPot[var1][4].setForeground(grisAzulado);
         valPot[var1][4].setFont(fuente2);
         panelE[var1].add(valPot[var1][4]);
         panelE[var1].add(sliderValHE[var1]);
         valDiam[var1][0] = new JLabel(leyendaDiam[0]);
         valDiam[var1][0].setBounds(190, 150, 60, 30);
         valDiam[var1][0].setForeground(grisAzulado);
         valDiam[var1][0].setFont(fuente2);
         panelE[var1].add(valDiam[var1][0]);
         valDiam[var1][1] = new JLabel(leyendaDiam[1]);
         valDiam[var1][1].setBounds(265, 150, 60, 30);
         valDiam[var1][1].setForeground(grisAzulado);
         valDiam[var1][1].setFont(fuente2);
         panelE[var1].add(valDiam[var1][1]);
         valDiam[var1][2] = new JLabel(leyendaDiam[2]);
         valDiam[var1][2].setBounds(340, 150, 60, 30);
         valDiam[var1][2].setForeground(grisAzulado);
         valDiam[var1][2].setFont(fuente2);
         panelE[var1].add(valDiam[var1][2]);
         valDiam[var1][3] = new JLabel(leyendaDiam[3]);
         valDiam[var1][3].setBounds(420, 150, 60, 30);
         valDiam[var1][3].setForeground(grisAzulado);
         valDiam[var1][3].setFont(fuente2);
         panelE[var1].add(valDiam[var1][3]);
         panelE[var1].add(sliderValHE[var1]);
      }

   }

   public void actionPerformed(ActionEvent var1) {
      String var4 = var1.getActionCommand();
      int var2;
      if (var4 != "L" && var4 != "D" && var4 != "R" && var4 != "V" && var4 != "buttonVirtual" && var4 != "buttonObjVirtual") {
         if (this.cuadro == 0) {
            SistemaFL.panelBase.remove(panelObj[0]);
         }

         if (this.cuadro == 1) {
            SistemaFL.panelBase.remove(panelObj[1]);
         }

         for(var2 = 0; var2 < 7; ++var2) {
            if (this.cuadro == 20 + var2) {
               SistemaFL.panelBase.remove(panelE[var2]);
            }
         }
      }

      if (var4 == "buttonObj0") {
         SistemaFL.panelBase.add(panelObj[0]);
         this.cuadro = 0;
      }

      if (var4 == "buttonObj1") {
         SistemaFL.panelBase.add(panelObj[1]);
         this.cuadro = 1;
      }

      if (this.cuadro == 0) {
         if (var4 == "R") {
            SistemaFL.real[0] = 1;
         }

         if (var4 == "V") {
            SistemaFL.real[0] = 0;
         }
      }

      if (this.cuadro == 1) {
         if (var4 == "R") {
            SistemaFL.real[1] = 1;
         }

         if (var4 == "V") {
            SistemaFL.real[1] = 0;
         }
      }

      for(var2 = 0; var2 < 7; ++var2) {
         if (var4 == nomBoton[var2]) {
            SistemaFL.panelBase.add(panelE[var2]);
            this.cuadro = 20 + var2;
         }

         if (this.cuadro == 20 + var2) {
            int var3;
            if (var4 == "L") {
               SistemaFL.tipoE[var2] = 1;
               unidad[var2] = " diop.";
               SistemaFL.existe[var2] = 0;

               for(var3 = 0; var3 < 4; ++var3) {
                  valDiam[var2][var3].setVisible(false);
               }

               datosCorrederaHLente(var2);
               sliderValZE[var2].setEnabled(false);
               labelHE[var2].setText(text[8][lang]);
               labelValHE[var2].setText(0.0D + unidad[var2]);
               labelFE[var2].setText(text[15][lang]);
               labelValFE[var2].setText("---");
               SistemaFL.altura[var2] = (double)diamMax / 2.0D;

               for(var3 = 0; var3 < 5; ++var3) {
                  valPot[var2][var3].setVisible(true);
               }

               SistemaFL.valPE[var2] = 0.0D;
               SistemaFL.valDibPE[var2] = 0.0D;
               SistemaFL.valDE[var2] = (double)diamMax;
               if (var2 < 6) {
                  SistemaFL.dist[var2] = Math.abs(SistemaFL.valDibPE[var2 + 1]);
               } else {
                  SistemaFL.dist[var2] = 0.0D;
               }

               SistemaFL.dist[var2 - 1] = Math.abs(SistemaFL.valDibPE[var2 - 1]);
            }

            if (var4 == "D") {
               SistemaFL.tipoE[var2] = 0;
               unidad[var2] = " mm";
               SistemaFL.existe[var2] = 0;

               for(var3 = 0; var3 < 5; ++var3) {
                  valPot[var2][var3].setVisible(false);
               }

               datosCorrederaHDiafragma(var2);
               sliderValZE[var2].setEnabled(false);
               labelHE[var2].setText(text[13][lang]);
               labelValHE[var2].setText("---");
               labelFE[var2].setText(" ");
               labelValFE[var2].setText(" ");

               for(var3 = 0; var3 < 4; ++var3) {
                  valDiam[var2][var3].setVisible(true);
               }

               SistemaFL.valPE[var2] = 0.0D;
               SistemaFL.valDibPE[var2] = 0.0D;
               SistemaFL.valDE[var2] = (double)diamMax;
               SistemaFL.altura[var2] = (double)diamMax / 2.0D;
               SistemaFL.espesor[var2] = 0.0D;
               if (var2 < 6) {
                  SistemaFL.dist[var2] = Math.abs(SistemaFL.valDibPE[var2 + 1]);
               } else {
                  SistemaFL.dist[var2] = 0.0D;
               }

               SistemaFL.dist[var2 - 1] = Math.abs(SistemaFL.valDibPE[var2 - 1]);
            }
         }
      }

      if (var4 == "buttonObjVirtual") {
         verObjetoVirtual = -verObjetoVirtual;
      }

      if (var4 == "buttonVirtual") {
         verImagenVirtual = -verImagenVirtual;
      }

      if (var4 == "buttonCard") {
         SistemaFL.panelBase.remove(panelBase);
         SistemaFL.panelBase.add(puntosCardinales);
         puntosCardinales.dibujoPC.redraw();
      }

      if (var4 == "buttonMP") {
         SistemaFL.panelBase.remove(panelBase);
         SistemaFL.panelBase.remove(canvasSistema);
         PanelBaseFL var10001 = SistemaFL.panelBase;
         SistemaFL.panelBase.add(PanelBaseFL.panelMP);
      }

      SistemaFL.panelBase.repaint();

      for(var2 = 0; var2 < 2; ++var2) {
         FuncionesParaxialesFL.tablaTramos(var2);
         FuncionesParaxialesFL.marchaParaxial(var2);
         FuncionesParaxialesFL.coeficientesRayos(var2);
         FuncionesParaxialesFL.interseccionesRayos(var2);
      }

      FuncionesParaxialesFL.calculaPupilas();
      canvasSistema.redraw();
   }

   void datosElemento(JSlider var1, JLabel var2, int var3) {
      if (var2 == labelValZE[var3]) {
         SistemaFL.valZE[var3] = (double)var1.getValue();
         var2.setText(SistemaFL.valZE[var3] + " mm");
      }

      if (var2 == labelValHE[var3]) {
         if (SistemaFL.tipoE[var3] == 1) {
            SistemaFL.valPE[var3] = (double)var1.getValue() / 10.0D;
            if (SistemaFL.valPE[var3] > 0.0D) {
               SistemaFL.valDibPE[var3] = SistemaFL.valPE[var3] + 8.0D;
            } else {
               SistemaFL.valDibPE[var3] = SistemaFL.valPE[var3] - 8.0D;
            }

            var2.setText(SistemaFL.valPE[var3] + unidad[var3]);
            if (SistemaFL.valPE[var3] != 0.0D) {
               double var4 = (double)((int)(10000.0D / SistemaFL.valPE[var3])) / 10.0D;
               labelValFE[var3].setText(var4 + " mm");
               sliderValZE[var3].setEnabled(true);
               SistemaFL.existe[var3] = 1;
               SistemaFL.curv[var3] = SistemaFL.valPE[var3] / (2.0D * indice - 2.0D);
               if (Math.abs(SistemaFL.curv[var3]) <= 1.0E-10D) {
                  SistemaFL.radio[var3] = 1.0E13D;
                  SistemaFL.radioDib[var3] = 1.0E13D;
               } else {
                  SistemaFL.radio[var3] = 2000.0D * (indice - 1.0D) / SistemaFL.valPE[var3];
                  SistemaFL.radioDib[var3] = SistemaFL.radio[var3] * SistemaFL.valPE[var3] / SistemaFL.valDibPE[var3];
               }

               if (SistemaFL.valPE[var3] < 0.0D) {
                  SistemaFL.espesor[var3] = 5.0D;
               } else {
                  SistemaFL.espesor[var3] = SistemaFL.valDibPE[var3] * 2.0D;
               }
            } else {
               labelValFE[var3].setText("---");
               sliderValZE[var3].setEnabled(false);
               SistemaFL.existe[var3] = 0;
            }
         } else {
            SistemaFL.valDE[var3] = (double)var1.getValue();
            SistemaFL.altura[var3] = SistemaFL.valDE[var3] / 2.0D;
            if (SistemaFL.valDE[var3] != (double)diamMax) {
               var2.setText(SistemaFL.valDE[var3] + unidad[var3]);
               sliderValZE[var3].setEnabled(true);
               SistemaFL.existe[var3] = 1;
               SistemaFL.radio[var3] = 1.0E13D;
               SistemaFL.radioDib[var3] = 1.0E13D;
            } else {
               var2.setText("---");
               sliderValZE[var3].setEnabled(false);
               SistemaFL.existe[var3] = 0;
            }
         }
      }

   }

   public static void datosCorrederaHLente(int var0) {
      sliderValHE[var0].setValue((int)(SistemaFL.valPE[var0] * 10.0D));
      sliderValHE[var0].setMinimum(-200);
      sliderValHE[var0].setMaximum(200);
      sliderValHE[var0].setPaintTicks(true);
      sliderValHE[var0].setMajorTickSpacing(100);
      sliderValHE[var0].setMinorTickSpacing(20);
      sliderValHE[var0].setBounds(190, 130, 280, 30);
   }

   public static void datosCorrederaHDiafragma(int var0) {
      sliderValHE[var0].setValue((int)SistemaFL.valDE[var0]);
      sliderValHE[var0].setMinimum(0);
      sliderValHE[var0].setMaximum(diamMax);
      sliderValHE[var0].setPaintTicks(true);
      sliderValHE[var0].setMajorTickSpacing(10);
      sliderValHE[var0].setMinorTickSpacing(2);
      sliderValHE[var0].setBounds(190, 130, 280, 30);
   }

   static {
      lang = SistemaFL.lang;
      text = new String[][]{{"AVANTPROJECTE", "ANTEPROYECTO", "PRELIMINARY SKETCH"}, {"Objecte", "Objeto", "Object"}, {"Lents i diafragmes", "Lentes y diafragmas", "Lenses and stops"}, {"Veure objecte virtual", "Ver objeto virtual", "Show virtual object"}, {"Veure imatge virtual", "Ver imagen virtual", "Show virtual image"}, {"Elements cardinals", "Elementos cardinales", "Cardinal points"}, {"Menú principal", "Menú principal", "Main menu"}, {"Posició", "Posición", "Position"}, {"Potència", "Potencia", "Power"}, {"Semicamp", "Semicampo", "Half field"}, {"Element", "Elemento", "Element"}, {"Lent", "Lente", "Lens"}, {"Diafragma", "Diafragma", "Stop"}, {"Diàmetre", "Diámetro", "Diameter"}, {"Infinit", "Infinito", "Infinite"}, {"Focal", "Focal", "Focal"}};
      panelObj = new JPanel[2];
      panelCard = new JPanel();
      panelE = new JPanel[7];
      grisAzulado = new Color(102, 102, 153);
      buttonObj = new JButton[2];
      buttonE = new JButton[7];
      nomBoton = new String[7];
      labelObj = new JLabel[2];
      labelZObj = new JLabel[2];
      labelValZObj = new JLabel[2];
      labelHObj = new JLabel[2];
      labelValHObj = new JLabel[2];
      buttonR = new JRadioButton[2];
      buttonV = new JRadioButton[2];
      grupoO = new ButtonGroup[2];
      sliderValZObj = new JSlider[2];
      listenerValZObj = new ChangeListener[2];
      valPosObj = new JLabel[2][5];
      leyendaPosObj = new String[]{"-1600", "-800", "0", "800", "1600"};
      sliderValHObj = new JSlider[2];
      listenerValHObj = new ChangeListener[2];
      valCampo = new JLabel[2][5];
      leyendaCampo = new String[]{"0", "5", "10", "15", "20"};
      buttonL = new JRadioButton[7];
      buttonD = new JRadioButton[7];
      grupo = new ButtonGroup[7];
      labelE = new JLabel[7];
      labelZE = new JLabel[7];
      labelHE = new JLabel[7];
      labelFE = new JLabel[7];
      labelValZE = new JLabel[7];
      labelValHE = new JLabel[7];
      labelValFE = new JLabel[7];
      sliderValZE = new JSlider[7];
      listenerValZE = new ChangeListener[7];
      sliderValHE = new JSlider[7];
      listenerValHE = new ChangeListener[7];
      valPos = new JLabel[7][5];
      leyendaPos = new String[]{"0", "400", "800", "1200", "1600"};
      valPot = new JLabel[7][5];
      leyendaPot = new String[]{"-20", "-10", "0", "10", "20"};
      valDiam = new JLabel[7][4];
      leyendaDiam = new String[]{"0", "20", "40", "60"};
      unidad = new String[7];
      verImagenVirtual = -1;
      verObjetoVirtual = -1;
      elemento = new int[8];
      numPupilaE = 1;
      numPupilaS = 1;
      numDiafragma = 1;
      tramo = new int[2][9];
      numTramos = new int[2];
      tramoCard = new int[9];
      zF = new double[2];
      zH = new double[2];
      zImag = new double[2][8];
      yImag = new double[2][8];
      pasa = new int[2][8][22];
      apRayo = new double[2][8][22];
      bpRayo = new double[2][8][22];
      zaRayo = new double[2][8][22];
      yaRayo = new double[2][8][22];
      zpRayo = new double[2][8][22];
      ypRayo = new double[2][8][22];
      hRayo = new double[2][8][22];
      aRayoCard = new double[2][8];
      bRayoCard = new double[2][8];
      apRayoCard = new double[2][8];
      bpRayoCard = new double[2][8];
      zaRayoCard = new double[2][8];
      yaRayoCard = new double[2][8];
      zpRayoCard = new double[2][8];
      ypRayoCard = new double[2][8];
      hRayoCard = new double[2][8];
      fRayoCard = new double[2][8];
      diamMax = 70;
      indice = 1.5D;
      pintarRayos = new int[]{0, 0};
      fuente1 = new Font("Dialog", 1, 24);
      fuente2 = new Font("Dialog", 1, 12);
      fuente3 = new Font("Dialog", 1, 11);
      fuente4 = new Font("Dialog", 1, 9);
      colorTexto1 = Color.white;
      colorTexto2 = Color.black;
   }

   class SliderListener implements ChangeListener {
      JLabel tf;
      int i;

      public SliderListener(JLabel var2) {
         this.tf = var2;
      }

      public void stateChanged(ChangeEvent var1) {
         JSlider var2 = (JSlider)var1.getSource();

         for(this.i = 0; this.i < 2; ++this.i) {
            if (this.tf == AnteproyectoFL.labelValZObj[this.i]) {
               SistemaFL.valZObj[this.i] = (double)var2.getValue();
               int var5;
               if (SistemaFL.valZObj[this.i] == -1600.0D) {
                  SistemaFL.existeObjeto[this.i] = 0;
                  this.tf.setText("----");
                  AnteproyectoFL.sliderValHObj[this.i].setEnabled(false);

                  for(var5 = 0; var5 < 5; ++var5) {
                     AnteproyectoFL.valCampo[this.i][var5].setVisible(false);
                  }
               } else {
                  SistemaFL.existeObjeto[this.i] = 1;
                  if (!(SistemaFL.valZObj[this.i] >= 1600.0D) && !(SistemaFL.valZObj[this.i] <= -1500.0D)) {
                     SistemaFL.zObjeto[this.i] = SistemaFL.valZObj[this.i];
                     this.tf.setText(SistemaFL.valZObj[this.i] + " mm");
                  } else {
                     SistemaFL.zObjeto[this.i] = -1.0E10D;
                     this.tf.setText(AnteproyectoFL.text[14][AnteproyectoFL.lang]);
                  }

                  AnteproyectoFL.sliderValHObj[this.i].setEnabled(true);

                  for(var5 = 0; var5 < 5; ++var5) {
                     AnteproyectoFL.valCampo[this.i][var5].setVisible(true);
                  }
               }
            }

            if (this.tf == AnteproyectoFL.labelValHObj[this.i]) {
               SistemaFL.campo[this.i] = (double)var2.getValue() / 10.0D;
               SistemaFL.campoDib[this.i] = SistemaFL.campo[this.i] * 8.0D;
               this.tf.setText(SistemaFL.campo[this.i] + " º");
            }
         }

         if (this.tf == AnteproyectoFL.labelValZE[1]) {
            AnteproyectoFL.this.datosElemento(var2, this.tf, 1);
         }

         if (this.tf == AnteproyectoFL.labelValHE[1]) {
            AnteproyectoFL.this.datosElemento(var2, this.tf, 1);
            SistemaFL.dist[1] = Math.abs(SistemaFL.valDibPE[1]) + Math.abs(SistemaFL.valDibPE[2]);
         }

         if (this.tf == AnteproyectoFL.labelValZE[1] || this.tf == AnteproyectoFL.labelValHE[1]) {
            if (SistemaFL.valZE[2] < SistemaFL.valZE[1] + SistemaFL.dist[1]) {
               SistemaFL.valZE[2] = SistemaFL.valZE[1] + SistemaFL.dist[1];
            }

            if (SistemaFL.valZE[3] < SistemaFL.valZE[2] + SistemaFL.dist[2]) {
               SistemaFL.valZE[3] = SistemaFL.valZE[2] + SistemaFL.dist[2];
            }

            if (SistemaFL.valZE[4] < SistemaFL.valZE[3] + SistemaFL.dist[3]) {
               SistemaFL.valZE[4] = SistemaFL.valZE[3] + SistemaFL.dist[3];
            }

            if (SistemaFL.valZE[5] < SistemaFL.valZE[4] + SistemaFL.dist[4]) {
               SistemaFL.valZE[5] = SistemaFL.valZE[4] + SistemaFL.dist[4];
            }

            if (SistemaFL.valZE[6] < SistemaFL.valZE[5] + SistemaFL.dist[5]) {
               SistemaFL.valZE[6] = SistemaFL.valZE[5] + SistemaFL.dist[5];
            }

            AnteproyectoFL.sliderValZE[2].setValue((int)SistemaFL.valZE[2]);
            AnteproyectoFL.sliderValZE[3].setValue((int)SistemaFL.valZE[3]);
            AnteproyectoFL.sliderValZE[4].setValue((int)SistemaFL.valZE[4]);
            AnteproyectoFL.sliderValZE[5].setValue((int)SistemaFL.valZE[5]);
            AnteproyectoFL.sliderValZE[6].setValue((int)SistemaFL.valZE[6]);
         }

         if (this.tf == AnteproyectoFL.labelValZE[2]) {
            AnteproyectoFL.this.datosElemento(var2, this.tf, 2);
         }

         if (this.tf == AnteproyectoFL.labelValHE[2]) {
            AnteproyectoFL.this.datosElemento(var2, this.tf, 2);
            SistemaFL.dist[1] = Math.abs(SistemaFL.valDibPE[1]) + Math.abs(SistemaFL.valDibPE[2]);
            SistemaFL.dist[2] = Math.abs(SistemaFL.valDibPE[2]) + Math.abs(SistemaFL.valDibPE[3]);
         }

         if (this.tf == AnteproyectoFL.labelValZE[2] || this.tf == AnteproyectoFL.labelValHE[2]) {
            if (SistemaFL.valZE[1] > SistemaFL.valZE[2] - SistemaFL.dist[1]) {
               SistemaFL.valZE[1] = SistemaFL.valZE[2] - SistemaFL.dist[1];
            }

            if (SistemaFL.valZE[3] < SistemaFL.valZE[2] + SistemaFL.dist[2]) {
               SistemaFL.valZE[3] = SistemaFL.valZE[2] + SistemaFL.dist[2];
            }

            if (SistemaFL.valZE[4] < SistemaFL.valZE[3] + SistemaFL.dist[3]) {
               SistemaFL.valZE[4] = SistemaFL.valZE[3] + SistemaFL.dist[3];
            }

            if (SistemaFL.valZE[5] < SistemaFL.valZE[4] + SistemaFL.dist[4]) {
               SistemaFL.valZE[5] = SistemaFL.valZE[4] + SistemaFL.dist[4];
            }

            if (SistemaFL.valZE[6] < SistemaFL.valZE[5] + SistemaFL.dist[5]) {
               SistemaFL.valZE[6] = SistemaFL.valZE[5] + SistemaFL.dist[5];
            }

            AnteproyectoFL.sliderValZE[1].setValue((int)SistemaFL.valZE[1]);
            AnteproyectoFL.sliderValZE[3].setValue((int)SistemaFL.valZE[3]);
            AnteproyectoFL.sliderValZE[4].setValue((int)SistemaFL.valZE[4]);
            AnteproyectoFL.sliderValZE[5].setValue((int)SistemaFL.valZE[5]);
            AnteproyectoFL.sliderValZE[6].setValue((int)SistemaFL.valZE[6]);
         }

         if (this.tf == AnteproyectoFL.labelValZE[3]) {
            AnteproyectoFL.this.datosElemento(var2, this.tf, 3);
         }

         if (this.tf == AnteproyectoFL.labelValHE[3]) {
            AnteproyectoFL.this.datosElemento(var2, this.tf, 3);
            SistemaFL.dist[2] = Math.abs(SistemaFL.valDibPE[2]) + Math.abs(SistemaFL.valDibPE[3]);
            SistemaFL.dist[3] = Math.abs(SistemaFL.valDibPE[3]) + Math.abs(SistemaFL.valDibPE[4]);
         }

         if (this.tf == AnteproyectoFL.labelValZE[3] || this.tf == AnteproyectoFL.labelValHE[3]) {
            if (SistemaFL.valZE[2] > SistemaFL.valZE[3] - SistemaFL.dist[2]) {
               SistemaFL.valZE[2] = SistemaFL.valZE[3] - SistemaFL.dist[2];
            }

            if (SistemaFL.valZE[1] > SistemaFL.valZE[2] - SistemaFL.dist[1]) {
               SistemaFL.valZE[1] = SistemaFL.valZE[2] - SistemaFL.dist[1];
            }

            if (SistemaFL.valZE[4] < SistemaFL.valZE[3] + SistemaFL.dist[3]) {
               SistemaFL.valZE[4] = SistemaFL.valZE[3] + SistemaFL.dist[3];
            }

            if (SistemaFL.valZE[5] < SistemaFL.valZE[4] + SistemaFL.dist[4]) {
               SistemaFL.valZE[5] = SistemaFL.valZE[4] + SistemaFL.dist[4];
            }

            if (SistemaFL.valZE[6] < SistemaFL.valZE[5] + SistemaFL.dist[5]) {
               SistemaFL.valZE[6] = SistemaFL.valZE[5] + SistemaFL.dist[5];
            }

            AnteproyectoFL.sliderValZE[1].setValue((int)SistemaFL.valZE[1]);
            AnteproyectoFL.sliderValZE[2].setValue((int)SistemaFL.valZE[2]);
            AnteproyectoFL.sliderValZE[4].setValue((int)SistemaFL.valZE[4]);
            AnteproyectoFL.sliderValZE[5].setValue((int)SistemaFL.valZE[5]);
            AnteproyectoFL.sliderValZE[6].setValue((int)SistemaFL.valZE[6]);
         }

         if (this.tf == AnteproyectoFL.labelValZE[4]) {
            AnteproyectoFL.this.datosElemento(var2, this.tf, 4);
         }

         if (this.tf == AnteproyectoFL.labelValHE[4]) {
            AnteproyectoFL.this.datosElemento(var2, this.tf, 4);
            SistemaFL.dist[3] = Math.abs(SistemaFL.valDibPE[3]) + Math.abs(SistemaFL.valDibPE[4]);
            SistemaFL.dist[4] = Math.abs(SistemaFL.valDibPE[4]) + Math.abs(SistemaFL.valDibPE[5]);
         }

         if (this.tf == AnteproyectoFL.labelValZE[4] || this.tf == AnteproyectoFL.labelValHE[4]) {
            if (SistemaFL.valZE[3] > SistemaFL.valZE[4] - SistemaFL.dist[3]) {
               SistemaFL.valZE[3] = SistemaFL.valZE[4] - SistemaFL.dist[3];
            }

            if (SistemaFL.valZE[2] > SistemaFL.valZE[3] - SistemaFL.dist[2]) {
               SistemaFL.valZE[2] = SistemaFL.valZE[3] - SistemaFL.dist[2];
            }

            if (SistemaFL.valZE[1] > SistemaFL.valZE[2] - SistemaFL.dist[1]) {
               SistemaFL.valZE[1] = SistemaFL.valZE[2] - SistemaFL.dist[1];
            }

            if (SistemaFL.valZE[5] < SistemaFL.valZE[4] + SistemaFL.dist[4]) {
               SistemaFL.valZE[5] = SistemaFL.valZE[4] + SistemaFL.dist[4];
            }

            if (SistemaFL.valZE[6] < SistemaFL.valZE[5] + SistemaFL.dist[5]) {
               SistemaFL.valZE[6] = SistemaFL.valZE[5] + SistemaFL.dist[5];
            }

            AnteproyectoFL.sliderValZE[1].setValue((int)SistemaFL.valZE[1]);
            AnteproyectoFL.sliderValZE[2].setValue((int)SistemaFL.valZE[2]);
            AnteproyectoFL.sliderValZE[3].setValue((int)SistemaFL.valZE[3]);
            AnteproyectoFL.sliderValZE[5].setValue((int)SistemaFL.valZE[5]);
            AnteproyectoFL.sliderValZE[6].setValue((int)SistemaFL.valZE[6]);
         }

         if (this.tf == AnteproyectoFL.labelValZE[5]) {
            AnteproyectoFL.this.datosElemento(var2, this.tf, 5);
         }

         if (this.tf == AnteproyectoFL.labelValHE[5]) {
            AnteproyectoFL.this.datosElemento(var2, this.tf, 5);
            SistemaFL.dist[4] = Math.abs(SistemaFL.valDibPE[4]) + Math.abs(SistemaFL.valDibPE[5]);
            SistemaFL.dist[5] = Math.abs(SistemaFL.valDibPE[5]) + Math.abs(SistemaFL.valDibPE[6]);
         }

         if (this.tf == AnteproyectoFL.labelValZE[5] || this.tf == AnteproyectoFL.labelValHE[5]) {
            if (SistemaFL.valZE[4] > SistemaFL.valZE[5] - SistemaFL.dist[4]) {
               SistemaFL.valZE[4] = SistemaFL.valZE[5] - SistemaFL.dist[4];
            }

            if (SistemaFL.valZE[3] > SistemaFL.valZE[4] - SistemaFL.dist[3]) {
               SistemaFL.valZE[3] = SistemaFL.valZE[4] - SistemaFL.dist[3];
            }

            if (SistemaFL.valZE[2] > SistemaFL.valZE[3] - SistemaFL.dist[2]) {
               SistemaFL.valZE[2] = SistemaFL.valZE[3] - SistemaFL.dist[2];
            }

            if (SistemaFL.valZE[1] > SistemaFL.valZE[2] - SistemaFL.dist[1]) {
               SistemaFL.valZE[1] = SistemaFL.valZE[2] - SistemaFL.dist[1];
            }

            if (SistemaFL.valZE[6] < SistemaFL.valZE[5] + SistemaFL.dist[5]) {
               SistemaFL.valZE[6] = SistemaFL.valZE[5] + SistemaFL.dist[5];
            }

            AnteproyectoFL.sliderValZE[1].setValue((int)SistemaFL.valZE[1]);
            AnteproyectoFL.sliderValZE[2].setValue((int)SistemaFL.valZE[2]);
            AnteproyectoFL.sliderValZE[3].setValue((int)SistemaFL.valZE[3]);
            AnteproyectoFL.sliderValZE[4].setValue((int)SistemaFL.valZE[4]);
            AnteproyectoFL.sliderValZE[6].setValue((int)SistemaFL.valZE[6]);
         }

         if (this.tf == AnteproyectoFL.labelValZE[6]) {
            AnteproyectoFL.this.datosElemento(var2, this.tf, 6);
         }

         if (this.tf == AnteproyectoFL.labelValHE[6]) {
            AnteproyectoFL.this.datosElemento(var2, this.tf, 6);
            SistemaFL.dist[5] = Math.abs(SistemaFL.valDibPE[5]) + Math.abs(SistemaFL.valDibPE[6]);
         }

         if (this.tf == AnteproyectoFL.labelValZE[6] || this.tf == AnteproyectoFL.labelValHE[6]) {
            if (SistemaFL.valZE[5] > SistemaFL.valZE[6] - SistemaFL.dist[5]) {
               SistemaFL.valZE[5] = SistemaFL.valZE[6] - SistemaFL.dist[5];
            }

            if (SistemaFL.valZE[4] > SistemaFL.valZE[5] - SistemaFL.dist[4]) {
               SistemaFL.valZE[4] = SistemaFL.valZE[5] - SistemaFL.dist[4];
            }

            if (SistemaFL.valZE[3] > SistemaFL.valZE[4] - SistemaFL.dist[3]) {
               SistemaFL.valZE[3] = SistemaFL.valZE[4] - SistemaFL.dist[3];
            }

            if (SistemaFL.valZE[2] > SistemaFL.valZE[3] - SistemaFL.dist[2]) {
               SistemaFL.valZE[2] = SistemaFL.valZE[3] - SistemaFL.dist[2];
            }

            if (SistemaFL.valZE[1] > SistemaFL.valZE[2] - SistemaFL.dist[1]) {
               SistemaFL.valZE[1] = SistemaFL.valZE[2] - SistemaFL.dist[1];
            }

            AnteproyectoFL.sliderValZE[1].setValue((int)SistemaFL.valZE[1]);
            AnteproyectoFL.sliderValZE[2].setValue((int)SistemaFL.valZE[2]);
            AnteproyectoFL.sliderValZE[3].setValue((int)SistemaFL.valZE[3]);
            AnteproyectoFL.sliderValZE[4].setValue((int)SistemaFL.valZE[4]);
            AnteproyectoFL.sliderValZE[5].setValue((int)SistemaFL.valZE[5]);
         }

         FuncionesParaxialesFL.tablaElementos();
         AnteproyectoFL.pintarRayos[0] = 0;
         AnteproyectoFL.pintarRayos[1] = 0;

         for(this.i = 1; this.i < 7; ++this.i) {
            if (SistemaFL.existe[this.i] != 0) {
               AnteproyectoFL.pintarRayos[0] = 1;
               AnteproyectoFL.pintarRayos[1] = 1;
               break;
            }
         }

         for(this.i = 0; this.i < 2; ++this.i) {
            FuncionesParaxialesFL.tablaTramos(this.i);
            FuncionesParaxialesFL.marchaParaxial(this.i);
            FuncionesParaxialesFL.coeficientesRayos(this.i);
            FuncionesParaxialesFL.interseccionesRayos(this.i);
         }

         FuncionesParaxialesFL.calculaPupilas();
         AnteproyectoFL.canvasSistema.redraw();
      }
   }
}
