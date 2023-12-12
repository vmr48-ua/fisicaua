package ull;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JPanel;

public class EsqUll extends JPanel {
   Image img;
   PanelImagen panelImagen;
   BorderLayout borderLayout1 = new BorderLayout();
   int s;
   int s_prima;
   int s_primaX;
   int s_primaY;
   int temp;
   int sretina = 672;
   double kk;
   int y1a = 70;
   int y1b = 120;
   int y2 = 95;
   int x1 = 505;
   String[] puntremot = new String[]{"Punt remot", "Punto remoto", "Near point"};
   String[] puntproper = new String[]{"Punt proper", "Punto próximo", "Far point"};

   public EsqUll() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public void paint(Graphics g) {
      g.setColor(new Color(0, 0, 0));
      g.fillRect(0, 0, 775, 550);
      g.drawImage(this.img, 471, 0, this);
      g.setColor(new Color(153, 153, 204));
      g.drawLine(0, this.y2, 775, this.y2);
      g.drawString("Retina 22 mm", this.sretina + 10, this.y2 - 20);
      g.setColor(Color.white);
      g.drawLine(this.sretina, this.y2, this.sretina + 10, this.y2 - 20);
      g.setColor(Color.lightGray);
      g.drawLine(this.x1, 0, this.x1, 200);
      g.drawString("cm", this.x1 - 20, 180);
      g.drawString("mm", this.x1 + 3, 180);
      g.setColor(new Color(153, 153, 204));
      g.drawString("0", this.x1 + 2, this.y2);
      this.kk = 189.0495867768595D;
      this.temp = this.x1 - (int)this.kk;
      g.drawString("25 cm", this.temp, this.y2);
      g.drawLine(this.temp, this.y2 + 3, this.temp, this.y2 - 3);
      this.kk = 378.099173553719D;
      this.temp = this.x1 - (int)this.kk;
      g.drawString("50 cm", this.temp, this.y2);
      g.drawLine(this.temp, this.y2 + 3, this.temp, this.y2 - 3);
      g.setColor(Color.red);
      g.drawLine(this.x1, 60, this.x1, 130);
      g.drawLine(506, 60, 506, 130);
      int[] coordx = new int[]{this.x1, 511, 500};
      int[] coordy = new int[]{60, 67, 67};
      g.fillPolygon(coordx, coordy, 3);
      coordy[0] = 130;
      coordy[1] = 123;
      coordy[2] = 123;
      g.fillPolygon(coordx, coordy, 3);
      g.setColor(Color.red);
      double puntoremoto;
      double puntocercano;
      boolean radi;
      int radi;
      if (!VentanaSliders.ast) {
         puntoremoto = Calculo.sremoto / 10.0D;
         puntoremoto *= 10.0D;
         this.temp = (int)Math.round(puntoremoto);
         puntoremoto = (double)this.temp / 10.0D;
         puntocercano = Calculo.scercano / 10.0D;
         puntocercano *= 10.0D;
         this.temp = (int)Math.round(puntocercano);
         puntocercano = (double)this.temp / 10.0D;
         g.setColor(Color.white);
         g.drawString(this.puntproper[Ull.lang] + " = " + puntocercano + " cm", 10, 40);
         if (Calculo.miopia == 0.0D) {
            g.drawString(this.puntremot[Ull.lang] + " = " + VentanaSliders.inf[Ull.lang], 10, 20);
         } else {
            g.drawString(this.puntremot[Ull.lang] + " = " + puntoremoto + " cm", 10, 20);
         }

         g.setColor(Color.red);
         radi = false;
         if (Math.abs(Calculo.s_eix) > 5000.0D) {
            g.drawLine(this.x1, this.y1a, 0, this.y1a);
            g.drawLine(this.x1, this.y1b, 0, this.y1b);
            this.s_prima = this.x1 + (int)(Math.abs(Calculo.s_prima) * 183.0D / 24.2D);
            g.drawLine(this.x1, this.y1a, this.s_prima, this.y2);
            g.drawLine(this.x1, this.y1b, this.s_prima, this.y2);
         } else {
            this.s = this.x1 - (int)(Math.abs(Calculo.s_eix) / 10.0D * 183.0D / 24.2D);
            this.s_prima = this.x1 + (int)(Math.abs(Calculo.s_prima) * 183.0D / 24.2D);
            g.drawLine(this.x1, this.y1a, this.s, this.y2);
            g.drawLine(this.x1, this.y1b, this.s, this.y2);
            g.drawLine(this.x1, this.y1a, this.s_prima, this.y2);
            g.drawLine(this.x1, this.y1b, this.s_prima, this.y2);
         }

         if (this.s_prima < this.sretina) {
            this.kk = (double)(this.y2 - this.y1a) / (double)(this.s_prima - this.x1) * (double)(this.sretina - this.s_prima);
            radi = this.y2 + (int)this.kk;
            g.drawLine(this.s_prima, this.y2, this.sretina, radi);
            this.kk = (double)(this.y2 - this.y1b) / (double)(this.s_prima - this.x1) * (double)(this.sretina - this.s_prima);
            radi = this.y2 + (int)this.kk;
            g.drawLine(this.s_prima, this.y2, this.sretina, radi);
         }

         g.setColor(new Color(255, 255, 255));
         g.drawString("PSF", 10, 158);
         if (Math.abs(Calculo.r) < 1.0D) {
            g.fillOval(74, 158, 1, 1);
         } else {
            g.fillOval(74 - (int)Math.abs(Calculo.r), 158 - (int)Math.abs(Calculo.r), (int)Math.abs(2.0D * Calculo.r), (int)Math.abs(2.0D * Calculo.r));
         }
      } else {
         puntoremoto = Calculo.sremotoX / 10.0D;
         puntoremoto *= 10.0D;
         this.temp = (int)Math.round(puntoremoto);
         puntoremoto = (double)this.temp / 10.0D;
         puntocercano = Calculo.scercanoX / 10.0D;
         puntocercano *= 10.0D;
         this.temp = (int)Math.round(puntocercano);
         puntocercano = (double)this.temp / 10.0D;
         g.setColor(Color.white);
         g.drawString(this.puntproper[Ull.lang] + " X = " + puntocercano + " cm", 10, 40);
         if (Calculo.astx == 0.0D) {
            g.drawString(this.puntremot[Ull.lang] + " X = " + VentanaSliders.inf[Ull.lang], 10, 20);
         } else {
            g.drawString(this.puntremot[Ull.lang] + " X = " + puntoremoto + " cm", 10, 20);
         }

         puntoremoto = Calculo.sremotoY / 10.0D;
         puntoremoto *= 10.0D;
         this.temp = (int)Math.round(puntoremoto);
         puntoremoto = (double)this.temp / 10.0D;
         puntocercano = Calculo.scercanoY / 10.0D;
         puntocercano *= 10.0D;
         this.temp = (int)Math.round(puntocercano);
         puntocercano = (double)this.temp / 10.0D;
         g.drawString(this.puntproper[Ull.lang] + " Y = " + puntocercano + " cm", 200, 40);
         if (Calculo.asty == 0.0D) {
            g.drawString(this.puntremot[Ull.lang] + " Y = " + VentanaSliders.inf[Ull.lang], 200, 20);
         } else {
            g.drawString(this.puntremot[Ull.lang] + " Y = " + puntoremoto + " cm", 200, 20);
         }

         g.setColor(new Color(2, 205, 17));
         g.drawString("X", this.x1 - 30, this.y1a);
         g.drawLine(this.x1 - 17, this.y1a - 5, this.x1 + 17, this.y1b + 5);
         g.drawLine(this.x1 - 16, this.y1a - 5, this.x1 + 18, this.y1b + 5);
         coordx[0] = this.x1 - 17;
         coordy[0] = this.y1a - 5;
         coordx[1] = this.x1 - 17;
         coordy[1] = this.y1a + 5;
         coordx[2] = this.x1 - 12;
         coordy[2] = this.y1a - 5;
         g.fillPolygon(coordx, coordy, 3);
         coordx[0] = this.x1 + 17;
         coordy[0] = this.y1b + 5;
         coordx[1] = this.x1 + 18;
         coordy[1] = this.y1b - 5;
         coordx[2] = this.x1 + 12;
         coordy[2] = this.y1b + 5;
         g.fillPolygon(coordx, coordy, 3);
         g.setColor(Color.red);
         g.drawString("Y", this.x1 - 15, this.y1b + 15);
         g.setColor(Color.red);
         radi = false;
         if (Math.abs(Calculo.s_eix) > 5000.0D) {
            g.drawLine(this.x1, this.y1a, 0, this.y1a);
            g.drawLine(this.x1, this.y1b, 0, this.y1b);
            this.s_primaY = this.x1 + (int)(Math.abs(Calculo.s_primaY) * 183.0D / 24.2D);
            g.drawLine(this.x1, this.y1a, this.s_primaY, this.y2);
            g.drawLine(this.x1, this.y1b, this.s_primaY, this.y2);
         } else {
            this.s = this.x1 - (int)(Math.abs(Calculo.s_eix) / 10.0D * 183.0D / 24.2D);
            this.s_primaY = this.x1 + (int)(Math.abs(Calculo.s_primaY) * 183.0D / 24.2D);
            g.drawLine(this.x1, this.y1a, this.s, this.y2);
            g.drawLine(this.x1, this.y1b, this.s, this.y2);
            g.drawLine(this.x1, this.y1a, this.s_primaY, this.y2);
            g.drawLine(this.x1, this.y1b, this.s_primaY, this.y2);
         }

         if (this.s_primaY < this.sretina) {
            this.kk = (double)(this.y2 - this.y1a) / (double)(this.s_primaY - this.x1) * (double)(this.sretina - this.s_primaY);
            radi = this.y2 + (int)this.kk;
            g.drawLine(this.s_primaY, this.y2, this.sretina, radi);
            this.kk = (double)(this.y2 - this.y1b) / (double)(this.s_primaY - this.x1) * (double)(this.sretina - this.s_primaY);
            radi = this.y2 + (int)this.kk;
            g.drawLine(this.s_primaY, this.y2, this.sretina, radi);
         }

         g.setColor(new Color(2, 205, 17));
         int xx1a = this.x1 - 13;
         int yy1a = this.y1a + 5;
         int xx1b = this.x1 + 13;
         int yy1b = this.y1b - 5;
         if (Math.abs(Calculo.s_eix) > 5000.0D) {
            g.drawLine(xx1a, yy1a, 0, yy1a);
            g.drawLine(xx1b, yy1b, 0, yy1b);
            this.s_primaX = this.x1 + (int)(Math.abs(Calculo.s_primaX) * 183.0D / 24.2D);
            g.drawLine(xx1a, yy1a, this.s_primaX, this.y2);
            g.drawLine(xx1b, yy1b, this.s_primaX, this.y2);
         } else {
            this.s = this.x1 - (int)(Math.abs(Calculo.s_eix) / 10.0D * 183.0D / 24.2D);
            this.s_primaX = this.x1 + (int)(Math.abs(Calculo.s_primaX) * 183.0D / 24.2D);
            g.drawLine(xx1a, yy1a, this.s, this.y2);
            g.drawLine(xx1a, yy1b, this.s, this.y2);
            g.drawLine(xx1b, yy1a, this.s_primaX, this.y2);
            g.drawLine(xx1b, yy1b, this.s_primaX, this.y2);
         }

         if (this.s_primaX < this.sretina) {
            this.kk = (double)(this.y2 - yy1a) / (double)(this.s_primaX - xx1a) * (double)(this.sretina - this.s_primaX);
            radi = this.y2 + (int)this.kk;
            g.drawLine(this.s_primaX, this.y2, this.sretina, radi);
            this.kk = (double)(this.y2 - yy1b) / (double)(this.s_primaX - xx1b) * (double)(this.sretina - this.s_primaX);
            radi = this.y2 + (int)this.kk;
            g.drawLine(this.s_primaX, this.y2, this.sretina, radi);
         }

         g.setColor(new Color(255, 255, 255));
         g.drawString("PSF", 10, 158);
         if (Math.abs(Calculo.rX) < 1.0D && Math.abs(Calculo.rY) < 1.0D) {
            g.fillOval(74, 158, 1, 1);
         } else {
            g.fillOval(74 - (int)Math.abs(Calculo.rX), 158 - (int)Math.abs(Calculo.rY), (int)Math.abs(2.0D * Calculo.rX), (int)Math.abs(2.0D * Calculo.rY));
         }
      }

   }

   private void jbInit() throws Exception {
      this.setBackground(new Color(0, 0, 0));
      this.setMaximumSize(new Dimension(775, 190));
      this.setMinimumSize(new Dimension(775, 190));
      this.setPreferredSize(new Dimension(775, 190));
      this.setLayout(this.borderLayout1);
      String nom_imagen = "ullg.jpg";
      URL url_imagen = this.getClass().getResource(nom_imagen);
      System.out.println("La url es" + url_imagen.toString());
      this.img = Toolkit.getDefaultToolkit().getImage(url_imagen);
      this.panelImagen = new PanelImagen(this.img);
      this.panelImagen.setMinimumSize(new Dimension(304, 190));
      this.panelImagen.setOpaque(true);
      this.panelImagen.setPreferredSize(new Dimension(304, 190));
      this.add(this.panelImagen, "East");
   }
}
