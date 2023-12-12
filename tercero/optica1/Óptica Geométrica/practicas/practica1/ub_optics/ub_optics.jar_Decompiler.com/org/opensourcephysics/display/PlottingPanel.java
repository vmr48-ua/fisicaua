package org.opensourcephysics.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.display.axes.AxisFactory;
import org.opensourcephysics.display.axes.CartesianAxes;
import org.opensourcephysics.display.axes.CustomAxes;
import org.opensourcephysics.display.axes.DrawableAxes;
import org.opensourcephysics.display.axes.PolarType1;
import org.opensourcephysics.display.axes.PolarType2;
import org.opensourcephysics.numerics.FunctionTransform;
import org.opensourcephysics.numerics.LogBase10Function;
import org.opensourcephysics.tools.FontSizer;

public class PlottingPanel extends InteractivePanel {
   boolean logScaleX;
   boolean logScaleY;
   DrawableAxes axes;
   FunctionTransform functionTransform;
   static final double log10 = Math.log(10.0D);
   static final LogBase10Function logBase10Function = new LogBase10Function();

   public PlottingPanel(String var1, String var2, String var3) {
      this(var1, var2, var3, 0, 0);
   }

   public PlottingPanel(int var1, int var2) {
      this("x", "y", "Title", var1, var2);
   }

   public PlottingPanel(String var1, String var2, String var3, int var4, int var5) {
      this.logScaleX = false;
      this.logScaleY = false;
      this.functionTransform = new FunctionTransform();
      this.axes = AxisFactory.createAxesType1(this);
      this.axes.setXLabel(var1, (String)null);
      this.axes.setYLabel(var2, (String)null);
      this.axes.setTitle(var3, (String)null);
      this.functionTransform.setXFunction(logBase10Function);
      this.functionTransform.setYFunction(logBase10Function);
      if (var4 == 1) {
         this.logScaleX = true;
      }

      if (var5 == 1) {
         this.logScaleY = true;
      }

      this.setLogScale(this.logScaleX, this.logScaleY);
      this.axes.resizeFonts(FontSizer.getFactor(FontSizer.getLevel()), this);
   }

   public static PlottingPanel createType1(String var0, String var1, String var2) {
      PlottingPanel var3 = new PlottingPanel(var0, var1, var2);
      var3.axes = AxisFactory.createAxesType1(var3);
      var3.axes.setXLabel(var0, (String)null);
      var3.axes.setYLabel(var1, (String)null);
      var3.axes.setTitle(var2, (String)null);
      return var3;
   }

   public static PlottingPanel createType2(String var0, String var1, String var2) {
      PlottingPanel var3 = new PlottingPanel(var0, var1, var2);
      var3.axes = AxisFactory.createAxesType2(var3);
      var3.axes.setXLabel(var0, (String)null);
      var3.axes.setYLabel(var1, (String)null);
      var3.axes.setTitle(var2, (String)null);
      return var3;
   }

   public static PlottingPanel createPolarType1(String var0, double var1) {
      PlottingPanel var3 = new PlottingPanel((String)null, (String)null, var0);
      PolarType1 var4 = new PolarType1(var3);
      var4.setDeltaR(var1);
      var4.setDeltaTheta(0.39269908169872414D);
      var3.setTitle(var0);
      var3.setSquareAspect(true);
      return var3;
   }

   public static PlottingPanel createPolarType2(String var0, double var1) {
      PlottingPanel var3 = new PlottingPanel((String)null, (String)null, var0);
      PolarType2 var4 = new PolarType2(var3);
      var4.setDeltaR(var1);
      var4.setDeltaTheta(0.39269908169872414D);
      var3.setTitle(var0);
      var3.setSquareAspect(true);
      return var3;
   }

   public static PlottingPanel createType3(String var0, String var1, String var2) {
      PlottingPanel var3 = new PlottingPanel(var0, var1, var2);
      var3.axes = AxisFactory.createAxesType3(var3);
      var3.axes.setXLabel(var0, (String)null);
      var3.axes.setYLabel(var1, (String)null);
      var3.axes.setTitle(var2, (String)null);
      return var3;
   }

   protected void setFontLevel(int var1) {
      super.setFontLevel(var1);
      if (this.axes != null) {
         this.axes.resizeFonts(FontSizer.getFactor(var1), this);
      }

   }

   public Interactive getInteractive() {
      Interactive var1 = null;
      var1 = super.getInteractive();
      if (var1 == null && this.axes instanceof Interactive) {
         var1 = ((Interactive)this.axes).findInteractive(this, super.mouseEvent.getX(), super.mouseEvent.getY());
      }

      return var1;
   }

   public DrawableAxes getAxes() {
      return this.axes;
   }

   public void setAxes(DrawableAxes var1) {
      this.axes = var1;
      if (this.axes == null) {
         this.axes = new CustomAxes(this);
         this.setGutters(0, 0, 0, 0);
         this.axes.setVisible(false);
      }

   }

   public void setPolar(String var1, double var2) {
      if (!this.logScaleX && !this.logScaleY) {
         PolarType2 var4 = new PolarType2(this);
         var4.setDeltaR(var2);
         var4.setDeltaTheta(0.39269908169872414D);
         this.setTitle(var1);
         this.setSquareAspect(true);
      } else {
         System.err.println("The axes type cannot be swithed when using logarithmetic scales.");
      }
   }

   public void setCartesian(String var1, String var2, String var3) {
      this.axes = AxisFactory.createAxesType1(this);
      this.axes.setXLabel(var1, (String)null);
      this.axes.setYLabel(var2, (String)null);
      this.axes.setTitle(var3, (String)null);
   }

   public void setXLabel(String var1) {
      this.axes.setXLabel(var1, (String)null);
   }

   public void setYLabel(String var1) {
      this.axes.setYLabel(var1, (String)null);
   }

   public void setTitle(String var1) {
      this.axes.setTitle(var1, (String)null);
   }

   public void setXLabel(String var1, String var2) {
      this.axes.setXLabel(var1, var2);
   }

   public void setYLabel(String var1, String var2) {
      this.axes.setYLabel(var1, var2);
   }

   public void setTitle(String var1, String var2) {
      this.axes.setTitle(var1, var2);
   }

   public void setAxesVisible(boolean var1) {
      this.axes.setVisible(var1);
   }

   public void setLogScale(boolean var1, boolean var2) {
      if (this.axes instanceof CartesianAxes) {
         ((CartesianAxes)this.axes).setXLog(var1);
         this.logScaleX = var1;
      } else {
         this.logScaleX = false;
      }

      if (this.axes instanceof CartesianAxes) {
         ((CartesianAxes)this.axes).setYLog(var2);
         this.logScaleY = var2;
      } else {
         this.logScaleY = false;
      }

   }

   public void setLogScaleX(boolean var1) {
      if (this.axes instanceof CartesianAxes) {
         ((CartesianAxes)this.axes).setXLog(var1);
         this.logScaleX = var1;
      } else {
         this.logScaleX = false;
      }

   }

   public boolean isLogScaleX() {
      return this.logScaleX;
   }

   public void setLogScaleY(boolean var1) {
      if (this.axes instanceof CartesianAxes) {
         ((CartesianAxes)this.axes).setYLog(var1);
         this.logScaleY = var1;
      } else {
         this.logScaleY = false;
      }

   }

   public boolean isLogScaleY() {
      return this.logScaleY;
   }

   protected void computeGutters() {
      Dimension var1 = null;
      if (super.dimensionSetter != null) {
         var1 = super.dimensionSetter.getInterior(this);
      }

      if (this.axes instanceof Dimensioned) {
         Dimension var2 = ((Dimensioned)this.axes).getInterior(this);
         if (var2 != null) {
            var1 = var2;
         }
      }

      if (var1 != null) {
         super.squareAspect = false;
         super.leftGutter = super.rightGutter = Math.max(0, this.getWidth() - var1.width) / 2;
         super.topGutter = super.bottomGutter = Math.max(0, this.getHeight() - var1.height) / 2;
      }

   }

   protected void paintFirst(Graphics var1) {
      var1.setColor(this.getBackground());
      var1.fillRect(0, 0, this.getWidth(), this.getHeight());
      var1.setColor(Color.black);
      this.axes.draw(this, var1);
   }

   public double pixToX(int var1) {
      return this.logScaleX ? Math.pow(10.0D, super.pixToX(var1)) : super.pixToX(var1);
   }

   public int xToPix(double var1) {
      return this.logScaleX ? super.xToPix(logBase10(var1)) : super.xToPix(var1);
   }

   public double pixToY(int var1) {
      return this.logScaleY ? Math.pow(10.0D, super.pixToY(var1)) : super.pixToY(var1);
   }

   public int yToPix(double var1) {
      return this.logScaleY ? super.yToPix(logBase10(var1)) : super.yToPix(var1);
   }

   public void setPixelScale() {
      super.xmin = super.xminPreferred;
      super.xmax = super.xmaxPreferred;
      super.ymin = super.yminPreferred;
      super.ymax = super.ymaxPreferred;
      if (this.logScaleX) {
         super.xmin = logBase10(super.xmin);
         super.xmax = logBase10(super.xmax);
         if (super.xmin == 0.0D) {
            super.xmin = 1.0E-8D;
         }

         if (super.xmax == 0.0D) {
            super.xmax = Math.max(super.xmin + 1.0E-8D, 1.0E-8D);
         }
      }

      if (this.logScaleY) {
         super.ymin = logBase10(super.ymin);
         super.ymax = logBase10(super.ymax);
         if (super.ymin == 0.0D) {
            super.ymin = 1.0E-8D;
         }

         if (super.ymax == 0.0D) {
            super.ymax = Math.max(super.ymin + 1.0E-8D, 1.0E-8D);
         }
      }

      super.width = this.getWidth();
      super.height = this.getHeight();
      if (super.fixedPixelPerUnit) {
         super.xmin = (super.xmaxPreferred + super.xminPreferred) / 2.0D - (double)Math.max(super.width - super.leftGutter - super.rightGutter - 1, 1) / super.xPixPerUnit / 2.0D;
         super.xmax = (super.xmaxPreferred + super.xminPreferred) / 2.0D + (double)Math.max(super.width - super.leftGutter - super.rightGutter - 1, 1) / super.xPixPerUnit / 2.0D;
         super.ymin = (super.ymaxPreferred + super.yminPreferred) / 2.0D - (double)Math.max(super.height - super.bottomGutter - super.topGutter - 1, 1) / super.yPixPerUnit / 2.0D;
         super.ymax = (super.ymaxPreferred + super.yminPreferred) / 2.0D + (double)Math.max(super.height - super.bottomGutter - super.topGutter - 1, 1) / super.yPixPerUnit / 2.0D;
         this.functionTransform.setTransform(super.xPixPerUnit, 0.0D, 0.0D, -super.yPixPerUnit, -super.xmin * super.xPixPerUnit + (double)super.leftGutter, super.ymax * super.yPixPerUnit + (double)super.topGutter);
         this.functionTransform.setApplyXFunction(false);
         this.functionTransform.setApplyYFunction(false);
         this.functionTransform.getMatrix(super.pixelMatrix);
      } else {
         super.xPixPerUnit = (double)(super.width - super.leftGutter - super.rightGutter - 1) / (super.xmax - super.xmin);
         super.yPixPerUnit = (double)(super.height - super.bottomGutter - super.topGutter - 1) / (super.ymax - super.ymin);
         if (super.squareAspect) {
            double var1 = Math.abs(super.xPixPerUnit / super.yPixPerUnit);
            if (var1 >= 1.0D) {
               var1 = Math.min(var1, (double)super.width);
               super.xmin = super.xminPreferred - (super.xmaxPreferred - super.xminPreferred) * (var1 - 1.0D) / 2.0D;
               super.xmax = super.xmaxPreferred + (super.xmaxPreferred - super.xminPreferred) * (var1 - 1.0D) / 2.0D;
               super.xPixPerUnit = (double)(super.width - super.leftGutter - super.rightGutter - 1) / (super.xmax - super.xmin);
            } else {
               var1 = Math.max(var1, 1.0D / (double)super.height);
               super.ymin = super.yminPreferred - (super.ymaxPreferred - super.yminPreferred) * (1.0D / var1 - 1.0D) / 2.0D;
               super.ymax = super.ymaxPreferred + (super.ymaxPreferred - super.yminPreferred) * (1.0D / var1 - 1.0D) / 2.0D;
               super.yPixPerUnit = (double)(super.height - super.bottomGutter - super.topGutter - 1) / (super.ymax - super.ymin);
            }
         }

         this.functionTransform.setTransform(super.xPixPerUnit, 0.0D, 0.0D, -super.yPixPerUnit, -super.xmin * super.xPixPerUnit + (double)super.leftGutter, super.ymax * super.yPixPerUnit + (double)super.topGutter);
         if (this.logScaleX) {
            this.functionTransform.setApplyXFunction(true);
         } else {
            this.functionTransform.setApplyXFunction(false);
         }

         if (this.logScaleY) {
            this.functionTransform.setApplyYFunction(true);
         } else {
            this.functionTransform.setApplyYFunction(false);
         }

         this.functionTransform.getMatrix(super.pixelMatrix);
      }
   }

   public AffineTransform getPixelTransform() {
      return (AffineTransform)this.functionTransform.clone();
   }

   static double logBase10(double var0) {
      return Math.log(var0) / log10;
   }

   public static XML.ObjectLoader getLoader() {
      return new PlottingPanel.PlottingPanelLoader();
   }

   static class PlottingPanelLoader extends DrawingPanel.DrawingPanelLoader {
      public void saveObject(XMLControl var1, Object var2) {
         PlottingPanel var3 = (PlottingPanel)var2;
         var1.setValue("title", var3.axes.getTitle());
         var1.setValue("x axis label", var3.axes.getXLabel());
         var1.setValue("y axis label", var3.axes.getYLabel());
         super.saveObject(var1, var2);
      }

      public Object createObject(XMLControl var1) {
         String var2 = var1.getString("title");
         String var3 = var1.getString("x axis label");
         String var4 = var1.getString("y axis label");
         return new PlottingPanel(var3, var4, var2);
      }

      public Object loadObject(XMLControl var1, Object var2) {
         PlottingPanel var3 = (PlottingPanel)var2;
         var3.setTitle(var1.getString("title"));
         var3.setXLabel(var1.getString("x axis label"));
         var3.setYLabel(var1.getString("y axis label"));
         super.loadObject(var1, var2);
         return var2;
      }
   }
}
