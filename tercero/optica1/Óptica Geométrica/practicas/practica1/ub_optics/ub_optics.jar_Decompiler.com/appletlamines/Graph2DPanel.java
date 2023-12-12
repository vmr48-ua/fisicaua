package appletlamines;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JPanel;

public class Graph2DPanel extends JPanel {
   public String TexteX = new String("Eix X");
   public String TexteY = new String("Eix Y");
   public String Title;
   public DecimalFormat FormatX = new DecimalFormat("#.##");
   public DecimalFormat FormatY = new DecimalFormat("#.##");
   int xini;
   int yini;
   int ample;
   int alt;
   public double xmin;
   public double xmax;
   public double ymin;
   public double ymax;
   int x0clip;
   int y0clip;
   int x1clip;
   int y1clip;
   public int numdivx = 10;
   public int numdivy = 10;
   Vector datasets;
   public int numsets = 0;
   public boolean flag_grid = false;
   public boolean flag_title;
   public boolean flag_eixx = true;
   public boolean flag_eixy = true;
   public boolean flag_limits;
   public boolean flag_graf = false;
   public boolean flag_logscalex;
   public boolean flag_logscaley;
   public int flag_legend;
   public Graph2DPanel.Func FuncX;
   public Graph2DPanel.Func FuncY;
   public Color backColor;
   public Color axisColor;
   public Color textColor;
   public Color gridColor;
   public final Graph2DPanel.Linear linear = new Graph2DPanel.Linear();
   public final Graph2DPanel.Logar logar = new Graph2DPanel.Logar();
   public static final int LINES = 1;
   public static final int BARS = 2;
   public static final int DOTS = 3;
   public static final int SQUARES = 4;
   public static final int CIRCLES = 5;
   public static final int NOLEG = 0;
   public static final int TOPLEFT = 1;
   public static final int TOPRIGHT = 2;
   public static final int BOTLEFT = 3;
   public static final int BOTRIGHT = 4;
   public static final int OVER = 5;

   public Graph2DPanel() {
      this.flag_logscalex = this.flag_logscaley = false;
      this.alt = 1000;
      this.ample = 1000;
      this.xini = 0;
      this.yini = 0;
      this.datasets = new Vector();
      this.flag_limits = false;
      this.xmin = -1.0D;
      this.xmax = 1.0D;
      this.ymin = -1.0D;
      this.ymax = 1.0D;
      this.x0clip = 200;
      this.y0clip = 10;
      this.x1clip = 990;
      this.y1clip = 800;
      this.backColor = Color.white;
      this.axisColor = Color.black;
      this.textColor = Color.black;
      this.gridColor = Color.darkGray;
      this.FuncX = this.linear;
      this.FuncY = this.linear;
   }

   public int addData(double[] X, double[] Y, int numdata, int PlotType, Color PlotColor) {
      Graph2DPanel.DataSet ds = new Graph2DPanel.DataSet();
      ds.X = X;
      ds.Y = Y;
      ds.numd = numdata;
      ds.plot_Type = PlotType;
      ds.plot_Color = PlotColor;
      this.datasets.add(this.numsets, ds);
      ++this.numsets;
      return this.numsets - 1;
   }

   public void updateData(double[] X, double[] Y, int N, int index) {
      if (index < this.numsets) {
         Graph2DPanel.DataSet ds = (Graph2DPanel.DataSet)this.datasets.get(index);
         ds.X = X;
         ds.Y = Y;
         ds.numd = N;
         this.datasets.set(index, ds);
      }
   }

   public void resetData() {
      this.datasets.clear();
      this.numsets = 0;
   }

   protected void paintLegend(Graphics2D g2d, int posicio) {
      int amplemax = 0;
      int numlin = this.numsets;
      int numcol = 1;
      FontRenderContext F = g2d.getFontRenderContext();
      int INTLIN = (int)g2d.getFont().getStringBounds("A", F).getHeight();
      int x0 = this.x0clip + INTLIN;
      int y0 = this.y0clip + INTLIN;
      int i;
      int y1;
      int ampletext;
      String S;
      switch(this.flag_legend) {
      case 1:
         x0 = this.x0clip + INTLIN;
         y0 = this.y0clip + INTLIN;
         break;
      case 2:
         amplemax = 0;

         for(i = 0; i < this.numsets; ++i) {
            if (((Graph2DPanel.DataSet)this.datasets.get(i)).flag_legend) {
               S = ((Graph2DPanel.DataSet)this.datasets.get(i)).Legend;
            } else {
               S = "Set #" + i;
            }

            ampletext = (int)g2d.getFont().getStringBounds(S, F).getWidth() + 2 * INTLIN;
            if (ampletext > amplemax) {
               amplemax = ampletext;
            }
         }

         x0 = this.x1clip - amplemax - 3 * INTLIN;
         y0 = this.y0clip + INTLIN;
         break;
      case 3:
         x0 = this.x0clip + INTLIN;
         y0 = this.y1clip - this.numsets * INTLIN;
         break;
      case 4:
         amplemax = 0;

         for(i = 0; i < this.numsets; ++i) {
            if (((Graph2DPanel.DataSet)this.datasets.get(i)).flag_legend) {
               S = ((Graph2DPanel.DataSet)this.datasets.get(i)).Legend;
            } else {
               S = "Set #" + i;
            }

            ampletext = (int)g2d.getFont().getStringBounds(S, F).getWidth() + 2 * INTLIN;
            if (ampletext > amplemax) {
               amplemax = ampletext;
            }
         }

         x0 = this.x1clip - amplemax - 3 * INTLIN;
         y0 = this.y1clip - this.numsets * INTLIN;
         break;
      case 5:
         amplemax = 0;

         for(i = 0; i < this.numsets; ++i) {
            int var10000 = x0 + (int)(2.3D * (double)INTLIN);
            y1 = y0 + i * INTLIN + INTLIN / 3;
            if (((Graph2DPanel.DataSet)this.datasets.get(i)).flag_legend) {
               S = ((Graph2DPanel.DataSet)this.datasets.get(i)).Legend;
            } else {
               S = "Set #" + i;
            }

            ampletext = (int)g2d.getFont().getStringBounds(S, F).getWidth() + 2 * INTLIN;
            if (ampletext > amplemax) {
               amplemax = ampletext;
            }
         }

         amplemax += (int)(2.3D * (double)INTLIN);
         if (amplemax != 0) {
            numcol = (this.x1clip - this.x0clip) / amplemax;
         } else {
            numcol = 1;
         }

         amplemax = (this.x1clip - this.x0clip) / numcol;
         if (numcol != 0) {
            numlin = (int)Math.ceil((double)this.numsets / (double)numcol);
         } else {
            numlin = this.numsets;
            numcol = 1;
         }

         y0 = this.y0clip - numlin * INTLIN;
         x0 = this.x0clip;
      }

      amplemax += (int)(0.1D * (double)INTLIN);

      int j;
      int x1;
      for(i = 0; i < numlin; ++i) {
         j = 0;

         while(j < numcol && j + i * numcol < this.numsets) {
            switch(((Graph2DPanel.DataSet)this.datasets.get(j + i * numcol)).plot_Type) {
            case 1:
               x1 = x0 + j * amplemax;
               y1 = y0 + i * INTLIN;
               int x2 = x0 + (int)(2.3D * (double)INTLIN) + j * amplemax;
               int y2 = y0 + i * INTLIN;
               g2d.setColor(((Graph2DPanel.DataSet)this.datasets.get(j + i * numcol)).plot_Color);
               g2d.drawLine(x1, y1, x2, y2);
            default:
               ++j;
            }
         }
      }

      for(i = 0; i < numlin; ++i) {
         for(j = 0; j < numcol && j + i * numcol < this.numsets; ++j) {
            x1 = x0 + (int)(2.4D * (double)INTLIN) + j * amplemax + INTLIN / 2;
            y1 = y0 + i * INTLIN + INTLIN / 3;
            if (((Graph2DPanel.DataSet)this.datasets.get(j + i * numcol)).flag_legend) {
               S = ((Graph2DPanel.DataSet)this.datasets.get(j + i * numcol)).Legend;
            } else {
               S = "Set #" + j + i * numcol;
            }

            g2d.setColor(((Graph2DPanel.DataSet)this.datasets.get(j + i * numcol)).plot_Color);
            g2d.drawString(S, x1, y1);
         }
      }

   }

   protected void paintLinies(Graphics2D g2d, Graph2DPanel.DataSet ds) {
      int i = false;
      double ymaxmin = this.FuncY.F(this.ymax) - this.FuncY.F(this.ymin);
      double xmaxmin = this.FuncX.F(this.xmax) - this.FuncX.F(this.xmin);
      if (xmaxmin != 0.0D && ymaxmin != 0.0D) {
         g2d.setClip(this.x0clip, this.y0clip, this.x1clip - this.x0clip, this.y1clip - this.y0clip);
         int maxx = this.x1clip - this.x0clip;
         int maxy = this.y1clip - this.y0clip;
         g2d.setColor(ds.plot_Color);
         g2d.translate(this.x0clip, this.y0clip);

         for(int i = 1; i < ds.numd; ++i) {
            try {
               int x1 = (int)((double)maxx * (this.FuncX.F(ds.X[i - 1]) - this.FuncX.F(this.xmin)) / xmaxmin);
               int y1 = (int)((double)maxy * (1.0D - (this.FuncY.F(ds.Y[i - 1]) - this.FuncY.F(this.ymin)) / ymaxmin));
               int x2 = (int)((double)maxx * (this.FuncX.F(ds.X[i]) - this.FuncX.F(this.xmin)) / xmaxmin);
               int y2 = (int)((double)maxy * (1.0D - (this.FuncY.F(ds.Y[i]) - this.FuncY.F(this.ymin)) / ymaxmin));
               g2d.drawLine(x1, y1, x2, y2);
            } catch (Exception var15) {
            }
         }

         g2d.translate(-this.x0clip, -this.y0clip);
         g2d.setClip(0, 0, this.ample, this.alt);
      }
   }

   protected void drawStringVertical(Graphics2D g2d, String S, int x0, int y0) {
      g2d.translate(x0, y0);
      g2d.rotate(-1.5707963267948966D);
      g2d.drawString(S, 0, 0);
      g2d.rotate(1.5707963267948966D);
      g2d.translate(-x0, -y0);
   }

   protected void calcClip(Graphics2D g2d) {
      FontRenderContext F = g2d.getFontRenderContext();
      int INTLIN = (int)g2d.getFont().getStringBounds("A", F).getHeight();
      String S;
      int ampletext;
      int amplemax;
      if (this.flag_eixy) {
         double Y = this.FuncY.F(this.ymax);
         S = this.FormatY.format(Y);
         amplemax = (int)g2d.getFont().getStringBounds(S, F).getWidth();
         double pas = (this.FuncY.F(this.ymax) - this.FuncY.F(this.ymin)) / (double)this.numdivy;

         for(int j = 0; j <= this.numdivy; ++j) {
            Y = this.FuncY.F(this.ymax) - (double)j * pas;
            S = this.FormatY.format(Y);
            ampletext = (int)g2d.getFont().getStringBounds(S, F).getWidth();
            if (ampletext > amplemax) {
               amplemax = ampletext;
            }
         }
      } else {
         amplemax = (int)g2d.getFont().getStringBounds("A", F).getHeight();
      }

      if (this.flag_eixy) {
         this.x0clip = INTLIN + (int)((double)amplemax * 1.1D);
      } else {
         this.x0clip = (int)((double)amplemax * 1.1D);
         if (INTLIN > this.x0clip) {
            this.x0clip = INTLIN;
         }
      }

      S = this.FormatX.format(this.FuncX.F(this.xmax));
      ampletext = (int)g2d.getFont().getStringBounds(S, F).getWidth();
      this.x1clip = this.ample - (int)((double)ampletext / 2.0D * 1.4D);
      this.y0clip = INTLIN * 2 / 3;
      if (this.flag_legend == 5) {
         amplemax = 0;

         for(int i = 0; i < this.numsets; ++i) {
            if (((Graph2DPanel.DataSet)this.datasets.get(i)).flag_legend) {
               S = ((Graph2DPanel.DataSet)this.datasets.get(i)).Legend;
            } else {
               S = "Set #" + i;
            }

            ampletext = (int)g2d.getFont().getStringBounds(S, F).getWidth();
            if (ampletext > amplemax) {
               amplemax = ampletext;
            }
         }

         amplemax += (int)(2.3D * (double)INTLIN);
         int numcol;
         if (amplemax != 0) {
            numcol = (this.x1clip - this.x0clip) / amplemax;
         } else {
            numcol = 1;
         }

         int numlin;
         if (numcol != 0) {
            numlin = this.numsets / numcol + 1;
         } else {
            numlin = this.numsets;
            boolean var17 = true;
         }

         this.y0clip += numlin * INTLIN;
      }

      if (this.flag_eixx) {
         this.y1clip = this.alt - (int)(2.5D * (double)INTLIN);
      } else {
         this.y1clip = this.alt - (int)(1.5D * (double)INTLIN);
      }

   }

   protected void paintAxes(Graphics2D g2d) {
      FontRenderContext F = g2d.getFontRenderContext();
      if (this.xmin == this.xmax) {
         this.xmin *= 0.99D;
         this.xmax *= 1.01D;
      }

      if (this.ymin == this.ymax) {
         this.ymin *= 0.99D;
         this.ymax *= 1.01D;
      }

      if (this.xmin == this.xmax) {
         this.xmin = 0.0D;
         this.xmax = 1.0D;
      }

      if (this.ymin == this.ymax) {
         this.ymin = 0.0D;
         this.ymax = 1.0D;
      }

      int INTLIN = (int)g2d.getFont().getStringBounds("A", F).getHeight();
      Rectangle Clip0 = this.getVisibleRect();
      this.ample = (int)Clip0.getWidth();
      this.alt = (int)Clip0.getHeight();
      this.calcClip(g2d);
      int Ample = this.x1clip - this.x0clip;
      int Alt = this.y1clip - this.y0clip;
      g2d.setColor(this.backColor);
      g2d.fillRect(0, 0, this.ample, this.alt);
      g2d.setColor(this.axisColor);
      g2d.drawRect(0, 0, this.ample - 1, this.alt - 1);
      g2d.drawRect(this.x0clip, this.y0clip, Ample, Alt);
      if (this.numdivx <= 0) {
         this.numdivx = 1;
      }

      if (this.numdivy <= 0) {
         this.numdivy = 1;
      }

      int x;
      double pas;
      int j;
      int ampletext;
      String S;
      if (this.flag_eixx) {
         pas = (this.FuncX.F(this.xmax) - this.FuncX.F(this.xmin)) / (double)this.numdivx;

         for(j = 0; j <= this.numdivx; ++j) {
            x = this.x0clip + (int)((double)j * (double)Ample / (double)this.numdivx);
            double X = this.FuncX.F(this.xmin) + (double)j * pas;
            S = this.FormatX.format(X);
            g2d.drawLine(x, this.y1clip, x, this.y1clip - INTLIN / 2);
            g2d.drawLine(x, this.y0clip, x, this.y0clip + INTLIN / 2);
            ampletext = (int)g2d.getFont().getStringBounds(S, F).getWidth();
            g2d.drawString(S, x - (int)((double)ampletext / 2.0D), this.y1clip + INTLIN);
         }

         if (this.FuncX.F(1.0D) == 0.0D) {
            S = "Log(" + this.TexteX + ")";
         } else {
            S = this.TexteX;
         }

         ampletext = (int)g2d.getFont().getStringBounds(S, F).getWidth();
         g2d.drawString(S, this.x0clip + Ample / 2 - ampletext / 2, this.y1clip + 2 * INTLIN);
      } else {
         pas = (this.FuncX.F(this.xmax) - this.FuncX.F(this.xmin)) / (double)this.numdivx;

         for(j = 0; j <= this.numdivx; ++j) {
            x = this.x0clip + (int)((double)j * (double)Ample / (double)this.numdivx);
            g2d.drawLine(x, this.y1clip, x, this.y1clip - INTLIN / 2);
            g2d.drawLine(x, this.y0clip, x, this.y0clip + INTLIN / 2);
         }

         S = this.FormatX.format(this.FuncX.F(this.xmin));
         ampletext = (int)g2d.getFont().getStringBounds(S, F).getWidth();
         g2d.drawString(S, this.x0clip - (int)((double)ampletext / 2.0D), this.y1clip + INTLIN);
         S = this.FormatX.format(this.FuncX.F(this.xmax));
         ampletext = (int)g2d.getFont().getStringBounds(S, F).getWidth();
         g2d.drawString(S, this.x0clip + Ample - (int)((double)ampletext / 2.0D), this.y1clip + INTLIN);
         if (this.FuncX.F(1.0D) == 0.0D) {
            S = "Log(" + this.TexteX + ")";
         } else {
            S = this.TexteX;
         }

         ampletext = (int)g2d.getFont().getStringBounds(S, F).getWidth();
         g2d.drawString(S, this.x0clip + Ample / 2 - ampletext / 2, this.y1clip + INTLIN);
      }

      int y;
      double Y;
      int alttext;
      if (this.flag_eixy) {
         pas = (this.FuncY.F(this.ymax) - this.FuncY.F(this.ymin)) / (double)this.numdivy;

         for(j = 0; j <= this.numdivy; ++j) {
            y = this.y0clip + (int)((double)j * (double)Alt / (double)this.numdivy);
            Y = this.FuncY.F(this.ymax) - (double)j * pas;
            S = this.FormatY.format(Y);
            g2d.drawLine(this.x0clip, y, this.x0clip + INTLIN / 2, y);
            g2d.drawLine(this.x1clip, y, this.x1clip - INTLIN / 2, y);
            ampletext = (int)g2d.getFont().getStringBounds(S, F).getWidth();
            alttext = (int)g2d.getFont().getStringBounds(S, F).getHeight();
            g2d.drawString(S, this.x0clip - ampletext, y + alttext / 3);
         }

         if (this.FuncY.F(1.0D) == 0.0D) {
            S = "Log(" + this.TexteY + ")";
         } else {
            S = this.TexteY;
         }

         ampletext = (int)g2d.getFont().getStringBounds(S, F).getWidth();
         alttext = (int)g2d.getFont().getStringBounds(S, F).getHeight();
         this.drawStringVertical(g2d, S, 0 + (int)((double)alttext * 0.9D), this.y0clip + Alt / 2 + ampletext / 2);
      } else {
         pas = (this.FuncY.F(this.ymax) - this.FuncY.F(this.ymin)) / (double)this.numdivy;

         for(j = 0; j <= this.numdivy; ++j) {
            y = this.y0clip + (int)((double)j * (double)Alt / (double)this.numdivy);
            g2d.drawLine(this.x0clip, y, this.x0clip + INTLIN / 2, y);
            g2d.drawLine(this.x1clip, y, this.x1clip - INTLIN / 2, y);
         }

         Y = this.FuncY.F(this.ymax);
         S = this.FormatY.format(Y);
         ampletext = (int)g2d.getFont().getStringBounds(S, F).getWidth();
         alttext = (int)g2d.getFont().getStringBounds(S, F).getHeight();
         this.drawStringVertical(g2d, S, 0 + alttext, this.y0clip + ampletext);
         Y = this.FuncY.F(this.ymin);
         S = this.FormatY.format(Y);
         ampletext = (int)g2d.getFont().getStringBounds(S, F).getWidth();
         alttext = (int)g2d.getFont().getStringBounds(S, F).getHeight();
         this.drawStringVertical(g2d, S, 0 + alttext, this.y1clip);
         if (this.FuncY.F(1.0D) == 0.0D) {
            S = "Log(" + this.TexteY + ")";
         } else {
            S = this.TexteY;
         }

         ampletext = (int)g2d.getFont().getStringBounds(S, F).getWidth();
         alttext = (int)g2d.getFont().getStringBounds(S, F).getHeight();
         this.drawStringVertical(g2d, S, 0 + (int)((double)alttext * 0.9D), this.y0clip + Alt / 2 + ampletext / 2);
      }

   }

   protected void paintGrid(Graphics2D g2d) {
      FontRenderContext F = g2d.getFontRenderContext();
      if (this.xmin == this.xmax) {
         this.xmin *= 0.99D;
         this.xmax *= 1.01D;
      }

      if (this.ymin == this.ymax) {
         this.ymin *= 0.99D;
         this.ymax *= 1.01D;
      }

      if (this.xmin == this.xmax) {
         this.xmin = 0.0D;
         this.xmax = 1.0D;
      }

      if (this.ymin == this.ymax) {
         this.ymin = 0.0D;
         this.ymax = 1.0D;
      }

      int INTLIN = (int)g2d.getFont().getStringBounds("A", F).getHeight();
      Rectangle Clip0 = this.getVisibleRect();
      this.ample = (int)Clip0.getWidth();
      this.alt = (int)Clip0.getHeight();
      this.calcClip(g2d);
      int Ample = this.x1clip - this.x0clip;
      int Alt = this.y1clip - this.y0clip;
      g2d.setColor(this.gridColor);

      int j;
      for(j = 0; j <= this.numdivy; ++j) {
         int y = this.y0clip + (int)((double)j * (double)Alt / (double)this.numdivy);
         g2d.drawLine(this.x0clip, y, this.x1clip, y);
      }

      for(j = 0; j <= this.numdivx; ++j) {
         int x = this.x0clip + (int)((double)j * (double)Ample / (double)this.numdivx);
         g2d.drawLine(x, this.y1clip, x, this.y0clip);
      }

   }

   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D)g;
      this.paintAxes(g2d);
      int i = 0;

      while(i < this.numsets) {
         Graph2DPanel.DataSet ds = (Graph2DPanel.DataSet)this.datasets.get(i);
         switch(ds.plot_Type) {
         case 1:
            this.paintLinies(g2d, ds);
         default:
            ++i;
         }
      }

      if (this.flag_legend != 0) {
         this.paintLegend(g2d, this.flag_legend);
      }

      if (this.flag_grid) {
         this.paintGrid(g2d);
      }

   }

   public void LimitsX(int index_data) {
      if (index_data >= 0 && index_data < this.numsets) {
         Graph2DPanel.DataSet ds = (Graph2DPanel.DataSet)this.datasets.get(index_data);
         this.xmin = this.xmax = ds.X[0];

         for(int i = 0; i < ds.numd; ++i) {
            if (this.xmin > ds.X[i]) {
               this.xmin = ds.X[i];
            }

            if (this.xmax < ds.X[i]) {
               this.xmax = ds.X[i];
            }
         }

      }
   }

   public void LimitsY(int index_data) {
      if (index_data >= 0 && index_data < this.numsets) {
         Graph2DPanel.DataSet ds = (Graph2DPanel.DataSet)this.datasets.get(index_data);
         this.ymin = this.ymax = ds.Y[0];

         for(int i = 0; i < ds.numd; ++i) {
            if (this.ymin > ds.Y[i]) {
               this.ymin = ds.Y[i];
            }

            if (this.ymax < ds.Y[i]) {
               this.ymax = ds.Y[i];
            }
         }

      }
   }

   public void LimitsY() {
      if (this.numsets > 0) {
         this.ymin = this.ymax = ((Graph2DPanel.DataSet)this.datasets.get(0)).Y[0];

         for(int i = 0; i < this.numsets; ++i) {
            Graph2DPanel.DataSet ds = (Graph2DPanel.DataSet)this.datasets.get(i);

            for(int j = 0; j < ds.numd; ++j) {
               if (this.ymin > ds.Y[j]) {
                  this.ymin = ds.Y[j];
               }

               if (this.ymax < ds.Y[j]) {
                  this.ymax = ds.Y[j];
               }
            }
         }

      }
   }

   public void Limits(int index_data) {
      if (index_data >= 0 && index_data < this.numsets) {
         Graph2DPanel.DataSet ds = (Graph2DPanel.DataSet)this.datasets.get(index_data);
         this.xmin = this.xmax = ds.X[0];
         this.ymin = this.ymax = ds.Y[0];

         for(int i = 0; i < ds.numd; ++i) {
            if (this.xmin > ds.X[i]) {
               this.xmin = ds.X[i];
            }

            if (this.xmax < ds.X[i]) {
               this.xmax = ds.X[i];
            }

            if (this.ymin > ds.Y[i]) {
               this.ymin = ds.Y[i];
            }

            if (this.ymax < ds.Y[i]) {
               this.ymax = ds.Y[i];
            }
         }

      }
   }

   public void setLegend(String Leg, int index_data) {
      if (index_data >= 0 && index_data < this.numsets) {
         Graph2DPanel.DataSet ds = (Graph2DPanel.DataSet)this.datasets.get(index_data);
         ds.SetLegend(Leg);
      }
   }

   public boolean getXY(int xpos, int ypos, double[] xyval) {
      if (xpos >= this.x0clip && xpos <= this.x1clip && ypos >= this.y0clip && ypos <= this.y1clip) {
         int deltax = xpos - this.x0clip;
         int deltay = ypos - this.y0clip;
         xyval[0] = this.xmin + (double)deltax / (double)(this.x1clip - this.x0clip) * (this.xmax - this.xmin);
         xyval[1] = this.ymax + (double)deltay / (double)(this.y1clip - this.y0clip) * (this.ymin - this.ymax);
         return true;
      } else {
         return false;
      }
   }

   public class Logar extends Graph2DPanel.Func {
      public Logar() {
         super();
      }

      double F(double x) {
         return x > 0.0D ? Math.log(x) / Math.log(10.0D) : -100.0D;
      }
   }

   public class Linear extends Graph2DPanel.Func {
      public Linear() {
         super();
      }

      double F(double x) {
         return x;
      }
   }

   public abstract class Func {
      abstract double F(double var1);
   }

   public class DataSet {
      double[] X;
      double[] Y;
      int numd;
      int plot_Type = 1;
      Color plot_Color;
      String Legend;
      boolean flag_legend;

      public DataSet() {
         this.plot_Color = Color.blue;
         this.Legend = "Legend";
         this.flag_legend = false;
      }

      public void SetLegend(String Leg) {
         this.Legend = Leg;
         this.flag_legend = true;
      }
   }
}
