package org.opensourcephysics.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.display.axes.CoordinateStringBuilder;
import org.opensourcephysics.display.dialogs.AutoScaleInspector;
import org.opensourcephysics.display.dialogs.DrawingPanelInspector;
import org.opensourcephysics.display.dialogs.ScaleInspector;
import org.opensourcephysics.display.dialogs.XMLDrawingPanelInspector;
import org.opensourcephysics.tools.FontSizer;
import org.opensourcephysics.tools.VideoCaptureTool;

public class DrawingPanel extends JPanel implements Printable, ActionListener, Renderable {
   public static final int BOTTOM_LEFT = 0;
   public static final int BOTTOM_RIGHT = 1;
   public static final int TOP_RIGHT = 2;
   public static final int TOP_LEFT = 3;
   protected JPopupMenu popupmenu = new JPopupMenu();
   protected JMenuItem propertiesItem;
   protected int leftGutter = 0;
   protected int topGutter = 0;
   protected int rightGutter = 0;
   protected int bottomGutter = 0;
   protected int width;
   protected int height;
   protected Color bgColor = new Color(239, 239, 255);
   protected boolean antialiasTextOn = false;
   protected boolean antialiasShapeOn = false;
   protected boolean squareAspect = false;
   protected boolean autoscaleX = true;
   protected boolean autoscaleY = true;
   protected double autoscaleMargin = 0.0D;
   protected double xminPreferred = -10.0D;
   protected double xmaxPreferred = 10.0D;
   protected double yminPreferred = -10.0D;
   protected double ymaxPreferred = 10.0D;
   protected double xfloor = Double.NaN;
   protected double xceil = Double.NaN;
   protected double yfloor = Double.NaN;
   protected double yceil = Double.NaN;
   protected double xmin;
   protected double xmax;
   protected double ymin;
   protected double ymax;
   protected boolean fixedPixelPerUnit;
   protected double xPixPerUnit;
   protected double yPixPerUnit;
   protected AffineTransform pixelTransform;
   protected double[] pixelMatrix;
   protected ArrayList drawableList;
   protected boolean validImage;
   protected BufferedImage offscreenImage;
   protected BufferedImage workingImage;
   private boolean buffered;
   protected TextPanel trMessageBox;
   protected TextPanel tlMessageBox;
   protected TextPanel brMessageBox;
   protected TextPanel blMessageBox;
   protected DecimalFormat scientificFormat;
   protected DecimalFormat decimalFormat;
   protected MouseController mouseController;
   protected boolean showCoordinates;
   protected DrawingPanel.OptionController optionController;
   protected DrawingPanel.ZoomBox zoomBox;
   protected boolean enableZoom;
   protected boolean zoomMode;
   protected Window customInspector;
   protected boolean clipAtGutter;
   protected Dimensioned dimensionSetter;
   protected Rectangle viewRect;
   protected CoordinateStringBuilder coordinateStrBuilder;
   protected DrawingPanel.GlassPanel glassPanel;
   protected OSPLayout glassPanelLayout;
   int refreshDelay;
   Timer refreshTimer;
   VideoCaptureTool vidCap;

   public DrawingPanel() {
      this.xmin = this.xminPreferred;
      this.xmax = this.xmaxPreferred;
      this.ymin = this.yminPreferred;
      this.ymax = this.xmaxPreferred;
      this.fixedPixelPerUnit = false;
      this.xPixPerUnit = 1.0D;
      this.yPixPerUnit = 1.0D;
      this.pixelTransform = new AffineTransform();
      this.pixelMatrix = new double[6];
      this.drawableList = new ArrayList();
      this.validImage = false;
      this.offscreenImage = new BufferedImage(1, 1, 1);
      this.workingImage = this.offscreenImage;
      this.buffered = false;
      this.trMessageBox = new TextPanel();
      this.tlMessageBox = new TextPanel();
      this.brMessageBox = new TextPanel();
      this.blMessageBox = new TextPanel();
      this.scientificFormat = new DecimalFormat("0.###E0");
      this.decimalFormat = new DecimalFormat("0.00");
      this.mouseController = new DrawingPanel.CMController();
      this.showCoordinates = false;
      this.optionController = new DrawingPanel.OptionController();
      this.zoomBox = new DrawingPanel.ZoomBox();
      this.enableZoom = false;
      this.zoomMode = false;
      this.clipAtGutter = true;
      this.dimensionSetter = null;
      this.viewRect = null;
      this.coordinateStrBuilder = CoordinateStringBuilder.createCartesian();
      this.glassPanel = new DrawingPanel.GlassPanel();
      this.glassPanelLayout = new OSPLayout();
      this.refreshDelay = 100;
      this.refreshTimer = new Timer(this.refreshDelay, this);
      this.glassPanel.setLayout(this.glassPanelLayout);
      super.setLayout(new BorderLayout());
      this.glassPanel.add(this.trMessageBox, "TopRightCorner");
      this.glassPanel.add(this.tlMessageBox, "TopLeftCorner");
      this.glassPanel.add(this.brMessageBox, "BottomRightCorner");
      this.glassPanel.add(this.blMessageBox, "BottomLeftCorner");
      this.glassPanel.setOpaque(false);
      super.add(this.glassPanel, "Center");
      this.setBackground(this.bgColor);
      this.setPreferredSize(new Dimension(300, 300));
      this.showCoordinates = true;
      this.addMouseListener(this.mouseController);
      this.addMouseMotionListener(this.mouseController);
      this.addMouseListener(this.optionController);
      this.addMouseMotionListener(this.optionController);
      this.addComponentListener(new ComponentAdapter() {
         public void componentResized(ComponentEvent var1) {
            DrawingPanel.this.validImage = false;
         }
      });
      this.buildPopupmenu();
      this.refreshTimer.setRepeats(false);
      this.refreshTimer.setCoalesce(true);
      this.setFontLevel(FontSizer.getLevel());
      FontSizer.addPropertyChangeListener("level", new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent var1) {
            int var2 = (Integer)var1.getNewValue();
            DrawingPanel.this.setFontLevel(var2);
         }
      });
   }

   protected void setFontLevel(int var1) {
      Font var2 = FontSizer.getResizedFont(this.trMessageBox.font, var1);
      this.trMessageBox.font = var2;
      this.tlMessageBox.font = var2;
      this.brMessageBox.font = var2;
      this.blMessageBox.font = var2;
   }

   protected void buildPopupmenu() {
      this.popupmenu.setEnabled(true);
      DrawingPanel.PopupmenuListener var1 = new DrawingPanel.PopupmenuListener();
      JMenuItem var2 = new JMenuItem("Snapshot");
      var2.addActionListener(var1);
      this.popupmenu.add(var2);
      var2 = new JMenuItem("Scale");
      var2.addActionListener(var1);
      this.popupmenu.add(var2);
      var2 = new JMenuItem("Zoom In");
      var2.addActionListener(var1);
      this.popupmenu.add(var2);
      var2 = new JMenuItem("Zoom Out");
      var2.addActionListener(var1);
      this.popupmenu.add(var2);
      this.propertiesItem = new JMenuItem("Inspect");
      this.propertiesItem.addActionListener(var1);
      this.popupmenu.add(this.propertiesItem);
   }

   public void setAutoscaleMargin(double var1) {
      this.autoscaleMargin = var1;
   }

   public void setClipAtGutter(boolean var1) {
      this.clipAtGutter = var1;
   }

   public boolean isClipAtGutter() {
      return this.clipAtGutter;
   }

   public void setGutters(int var1, int var2, int var3, int var4) {
      this.leftGutter = var1;
      this.topGutter = var2;
      this.rightGutter = var3;
      this.bottomGutter = var4;
   }

   public void setMouseCursor(Cursor var1) {
      Container var2 = this.getTopLevelAncestor();
      this.setCursor(var1);
      if (var2 != null) {
         var2.setCursor(var1);
      }

   }

   protected synchronized boolean checkWorkingImage() {
      Rectangle var1 = this.getBounds();
      int var2 = (int)var1.getWidth();
      int var3 = (int)var1.getHeight();
      if (var2 > 2 && var3 > 2) {
         if (this.workingImage == null || var2 != this.workingImage.getWidth() || var3 != this.workingImage.getHeight()) {
            this.workingImage = this.getGraphicsConfiguration().createCompatibleImage(var2, var3);
            this.validImage = false;
         }

         if (this.workingImage == null) {
            this.validImage = false;
            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public void actionPerformed(ActionEvent var1) {
      if (!this.validImage) {
         this.render();
      }

   }

   public boolean isIconified() {
      Container var1 = this.getTopLevelAncestor();
      if (var1 instanceof Frame) {
         return (((Frame)var1).getExtendedState() & 1) == 1;
      } else {
         return false;
      }
   }

   public BufferedImage render() {
      if (this.isShowing() && !this.isIconified()) {
         if (this.buffered && this.checkWorkingImage()) {
            this.validImage = true;
            this.render(this.workingImage);
            BufferedImage var1 = this.offscreenImage;
            this.offscreenImage = this.workingImage;
            this.workingImage = var1;
         }

         Runnable var5 = new Runnable() {
            public void run() {
               DrawingPanel.this.paintImmediately(DrawingPanel.this.getVisibleRect());
            }
         };

         try {
            if (SwingUtilities.isEventDispatchThread()) {
               this.paintImmediately(this.getVisibleRect());
            } else {
               SwingUtilities.invokeAndWait(var5);
            }
         } catch (InvocationTargetException var3) {
         } catch (InterruptedException var4) {
         }

         if (this.buffered && this.vidCap != null) {
            this.vidCap.addFrame(this.offscreenImage);
         }

         return this.offscreenImage;
      } else {
         return this.offscreenImage;
      }
   }

   public synchronized Image render(Image var1) {
      Graphics var2 = var1.getGraphics();
      if (var2 != null) {
         this.paintEverything(var2);
         if (var1 == this.workingImage) {
            this.zoomBox.paint(var2);
         }

         Rectangle var3 = this.viewRect;
         if (var3 != null) {
            Rectangle var4 = new Rectangle(0, 0, var1.getWidth((ImageObserver)null), var1.getHeight((ImageObserver)null));
            this.glassPanel.setBounds(var4);
            this.glassPanelLayout.checkLayoutRect(this.glassPanel, var4);
            this.glassPanel.render(var2);
            this.glassPanel.setBounds(var3);
            this.glassPanelLayout.checkLayoutRect(this.glassPanel, var3);
         } else {
            this.glassPanel.render(var2);
         }

         var2.dispose();
      }

      return var1;
   }

   public void invalidateImage() {
      this.validImage = false;
   }

   public void paintComponent(Graphics var1) {
      this.viewRect = this.findViewRect();
      if (this.buffered) {
         if (this.validImage && this.getWidth() == this.offscreenImage.getWidth() && this.getHeight() == this.offscreenImage.getHeight()) {
            var1.drawImage(this.offscreenImage, 0, 0, (ImageObserver)null);
         } else {
            if (this.getWidth() == this.offscreenImage.getWidth() && this.getHeight() == this.offscreenImage.getHeight()) {
               var1.drawImage(this.offscreenImage, 0, 0, (ImageObserver)null);
            } else {
               var1.setColor(Color.WHITE);
               var1.fillRect(0, 0, this.getWidth(), this.getHeight());
            }

            this.refreshTimer.start();
         }
      } else {
         this.paintEverything(var1);
      }

      if (this.enableZoom || this.zoomMode) {
         this.zoomBox.paint(var1);
      }

   }

   protected Rectangle getViewRect() {
      return this.viewRect;
   }

   protected Rectangle findViewRect() {
      Rectangle var1 = null;

      for(Container var2 = this.getParent(); var2 != null; var2 = var2.getParent()) {
         if (var2 instanceof JViewport) {
            var1 = ((JViewport)var2).getViewRect();
            this.glassPanel.setBounds(var1);
            this.glassPanelLayout.checkLayoutRect(this.glassPanel, var1);
            break;
         }
      }

      return var1;
   }

   protected void computeGutters() {
      if (this.dimensionSetter != null) {
         Dimension var1 = this.dimensionSetter.getInterior(this);
         if (var1 != null) {
            this.squareAspect = false;
            this.leftGutter = this.rightGutter = Math.max(0, this.getWidth() - var1.width) / 2;
            this.topGutter = this.bottomGutter = Math.max(0, this.getHeight() - var1.height) / 2;
         }
      }

   }

   protected void paintFirst(Graphics var1) {
      var1.setColor(this.getBackground());
      var1.fillRect(0, 0, this.getWidth(), this.getHeight());
      var1.setColor(Color.black);
   }

   protected void paintLast(Graphics var1) {
   }

   protected void paintEverything(Graphics var1) {
      this.computeGutters();
      ArrayList var2 = this.getDrawables();
      this.scale(var2);
      this.setPixelScale();
      if (this.antialiasTextOn) {
         ((Graphics2D)var1).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      } else {
         ((Graphics2D)var1).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
      }

      if (this.antialiasShapeOn) {
         ((Graphics2D)var1).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      } else {
         ((Graphics2D)var1).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
      }

      this.paintFirst(var1);
      this.paintDrawableList(var1, var2);
      this.paintLast(var1);
   }

   public void setAutoscaleX(boolean var1) {
      this.autoscaleX = var1;
   }

   public boolean isAutoscaleX() {
      return this.autoscaleX;
   }

   public void setAutoscaleY(boolean var1) {
      this.autoscaleY = var1;
   }

   public boolean isAutoscaleY() {
      return this.autoscaleY;
   }

   public void setBounds(int var1, int var2, int var3, int var4) {
      super.setBounds(var1, var2, var3, var4);
      this.validImage = false;
   }

   public void setBounds(Rectangle var1) {
      super.setBounds(var1);
      this.validImage = false;
   }

   public void setBuffered(boolean var1) {
      this.buffered = var1;
      if (this.buffered) {
         this.setDoubleBuffered(false);
      } else {
         this.workingImage = new BufferedImage(1, 1, 1);
         this.offscreenImage = this.workingImage;
         this.setDoubleBuffered(true);
      }

      this.validImage = false;
   }

   public boolean isBuffered() {
      return this.buffered;
   }

   public void setVisible(boolean var1) {
      super.setVisible(var1);
      if (var1 && this.buffered) {
         this.validImage = false;
      }

   }

   public void limitAutoscaleX(double var1, double var3) {
      this.xfloor = var1;
      this.xceil = var3;
   }

   public void limitAutoscaleY(double var1, double var3) {
      this.yfloor = var1;
      this.yceil = var3;
   }

   public void setPixelsPerUnit(boolean var1, double var2, double var4) {
      this.fixedPixelPerUnit = var1;
      this.xPixPerUnit = var2;
      this.yPixPerUnit = var4;
   }

   public void setPreferredMinMax(double var1, double var3, double var5, double var7) {
      if (!Double.isNaN(var1) && !Double.isNaN(var3)) {
         this.autoscaleX = false;
         if (var1 == var3) {
            var1 = 0.9D * var1 - 0.5D;
            var3 = 1.1D * var3 + 0.5D;
         }

         this.xminPreferred = var1;
         this.xmaxPreferred = var3;
      }

      if (!Double.isNaN(var5) && !Double.isNaN(var7)) {
         this.autoscaleY = false;
         if (var5 == var7) {
            var5 = 0.9D * var5 - 0.5D;
            var7 = 1.1D * var7 + 0.5D;
         }

         this.yminPreferred = var5;
         this.ymaxPreferred = var7;
      }

   }

   public void setPreferredMinMaxX(double var1, double var3) {
      this.autoscaleX = false;
      if (var1 == var3) {
         var1 = 0.9D * var1 - 0.5D;
         var3 = 1.1D * var3 + 0.5D;
      }

      this.xminPreferred = var1;
      this.xmaxPreferred = var3;
   }

   public void setPreferredMinMaxY(double var1, double var3) {
      this.autoscaleY = false;
      if (var1 == var3) {
         var1 = 0.9D * var1 - 0.5D;
         var3 = 1.1D * var3 + 0.5D;
      }

      this.yminPreferred = var1;
      this.ymaxPreferred = var3;
   }

   public void setSquareAspect(boolean var1) {
      if (this.squareAspect != var1) {
         this.squareAspect = var1;
         this.validImage = false;
         this.repaint();
      }
   }

   public boolean isSquareAspect() {
      return this.squareAspect;
   }

   public void setAntialiasTextOn(boolean var1) {
      this.antialiasTextOn = var1;
   }

   public boolean isAntialiasTextOn() {
      return this.antialiasTextOn;
   }

   public void setAntialiasShapeOn(boolean var1) {
      this.antialiasShapeOn = var1;
   }

   public boolean isAntialiasShapeOn() {
      return this.antialiasShapeOn;
   }

   public boolean isPointInside(double var1, double var3) {
      if (this.xmin < this.xmax) {
         if (var1 < this.xmin) {
            return false;
         }

         if (var1 > this.xmax) {
            return false;
         }
      } else {
         if (var1 > this.xmin) {
            return false;
         }

         if (var1 < this.xmax) {
            return false;
         }
      }

      if (this.ymin < this.ymax) {
         if (var3 < this.ymin) {
            return false;
         }

         if (var3 > this.ymax) {
            return false;
         }
      } else {
         if (var3 > this.ymin) {
            return false;
         }

         if (var3 < this.ymax) {
            return false;
         }
      }

      return true;
   }

   public boolean isZoom() {
      return this.enableZoom;
   }

   public void setZoom(boolean var1) {
      this.enableZoom = var1;
   }

   protected void zoomOut() {
      double var1 = this.xmax - this.xmin;
      double var3 = this.ymax - this.ymin;
      this.setPreferredMinMax(this.xmin - var1 / 2.0D, this.xmax + var1 / 2.0D, this.ymin - var3 / 2.0D, this.ymax + var3 / 2.0D);
      this.validImage = false;
      if (!this.getIgnoreRepaint()) {
         this.repaint();
      }

   }

   protected void zoomIn() {
      this.setMessage("Click drag to zoom in.", 0);
      this.zoomMode = true;
   }

   public void snapshot() {
      DrawingPanel var1 = new DrawingPanel();
      DrawingFrame var2 = new DrawingFrame(var1);
      var2.setDefaultCloseOperation(2);
      var2.setKeepHidden(false);
      var1.setSquareAspect(false);
      int var3 = this.isVisible() ? this.getWidth() : this.getPreferredSize().width;
      int var4 = this.isVisible() ? this.getHeight() : this.getPreferredSize().height;
      if (var3 != 0 && var4 != 0) {
         BufferedImage var5 = new BufferedImage(var3, var4, 2);
         this.render(var5);
         MeasuredImage var6 = new MeasuredImage(var5, this.pixToX(0), this.pixToX(var3), this.pixToY(var4), this.pixToY(0));
         var1.addDrawable(var6);
         var1.setPreferredMinMax(this.pixToX(0), this.pixToX(var3), this.pixToY(var4), this.pixToY(0));
         var1.setPreferredSize(new Dimension(var3, var4));
         var2.setTitle("Snapshot");
         var2.pack();
         var2.setVisible(true);
      }
   }

   public boolean hasInspector() {
      return this.popupmenu != null && this.popupmenu.isEnabled();
   }

   public void enableInspector(boolean var1) {
      this.popupmenu.setEnabled(var1);
   }

   public JPopupMenu getPopupMenu() {
      return this.popupmenu;
   }

   public void setPopupMenu(JPopupMenu var1) {
      this.popupmenu = var1;
   }

   public void showInspector() {
      if (this.customInspector == null) {
         XMLDrawingPanelInspector.getInspector(this);
      } else {
         this.customInspector.setVisible(true);
      }

   }

   public void hideInspector() {
      if (this.customInspector == null) {
         DrawingPanelInspector.hideInspector();
      } else {
         this.customInspector.setVisible(false);
      }

   }

   public void setCustomInspector(Window var1) {
      if (this.customInspector != null) {
         this.customInspector.setVisible(false);
      }

      this.customInspector = var1;
   }

   public void setVideoCaptureTool(VideoCaptureTool var1) {
      if (this.vidCap != null) {
         this.vidCap.setVisible(false);
      }

      this.vidCap = var1;
      if (this.vidCap != null) {
         this.setBuffered(true);
      }

   }

   public VideoCaptureTool getVideoCaptureTool() {
      return this.vidCap;
   }

   public double getAspectRatio() {
      return this.pixelMatrix[3] == 1.0D ? 1.0D : Math.abs(this.pixelMatrix[0] / this.pixelMatrix[3]);
   }

   public double getXPixPerUnit() {
      return this.pixelMatrix[0];
   }

   public double getYPixPerUnit() {
      return -this.pixelMatrix[3];
   }

   public double getMaxPixPerUnit() {
      return Math.max(Math.abs(this.pixelMatrix[0]), Math.abs(this.pixelMatrix[3]));
   }

   public double getXMin() {
      return this.xmin;
   }

   public double getPreferredXMin() {
      return this.xminPreferred;
   }

   public double getXMax() {
      return this.xmax;
   }

   public double getPreferredXMax() {
      return this.xmaxPreferred;
   }

   public double getYMax() {
      return this.ymax;
   }

   public double getPreferredYMax() {
      return this.ymaxPreferred;
   }

   public double getYMin() {
      return this.ymin;
   }

   public double getPreferredYMin() {
      return this.yminPreferred;
   }

   public CoordinateStringBuilder getCoordinateStringBuilder() {
      return this.coordinateStrBuilder;
   }

   public void setCoordinateStringBuilder(CoordinateStringBuilder var1) {
      this.coordinateStrBuilder = var1;
   }

   public Rectangle2D getScale() {
      this.setPixelScale();
      return new java.awt.geom.Rectangle2D.Double(this.xmin, this.ymin, this.xmax - this.xmin, this.ymax - this.ymin);
   }

   public Rectangle2D getMeasure() {
      double var1 = Double.MAX_VALUE;
      double var3 = -1.7976931348623157E308D;
      double var5 = Double.MAX_VALUE;
      double var7 = -1.7976931348623157E308D;
      boolean var9 = false;
      ArrayList var10 = this.getDrawables();
      Iterator var11 = var10.iterator();

      while(var11.hasNext()) {
         Object var12 = var11.next();
         if (var12 instanceof Measurable && ((Measurable)var12).isMeasured()) {
            Measurable var13 = (Measurable)var12;
            if (!Double.isNaN(var13.getXMax()) && !Double.isNaN(var13.getXMin()) && !Double.isNaN(var13.getYMax()) && !Double.isNaN(var13.getYMin())) {
               var1 = Math.min(var1, var13.getXMin());
               var3 = Math.max(var3, var13.getXMax());
               var5 = Math.min(var5, var13.getYMin());
               var7 = Math.max(var7, var13.getYMax());
               var9 = true;
            }
         }
      }

      if (var9) {
         return new java.awt.geom.Rectangle2D.Double(var1, var5, var3 - var1, var7 - var5);
      } else {
         return new java.awt.geom.Rectangle2D.Double(0.0D, 0.0D, 0.0D, 0.0D);
      }
   }

   public AffineTransform getPixelTransform() {
      return (AffineTransform)this.pixelTransform.clone();
   }

   public double[] getPixelMatrix() {
      return this.pixelMatrix;
   }

   public void setPixelScale() {
      this.xmin = this.xminPreferred;
      this.xmax = this.xmaxPreferred;
      this.ymin = this.yminPreferred;
      this.ymax = this.ymaxPreferred;
      this.width = this.getWidth();
      this.height = this.getHeight();
      if (this.fixedPixelPerUnit) {
         this.xmin = (this.xmaxPreferred + this.xminPreferred) / 2.0D - (double)Math.max(this.width - this.leftGutter - this.rightGutter - 1, 1) / this.xPixPerUnit / 2.0D;
         this.xmax = (this.xmaxPreferred + this.xminPreferred) / 2.0D + (double)Math.max(this.width - this.leftGutter - this.rightGutter - 1, 1) / this.xPixPerUnit / 2.0D;
         this.ymin = (this.ymaxPreferred + this.yminPreferred) / 2.0D - (double)Math.max(this.height - this.bottomGutter - this.topGutter - 1, 1) / this.yPixPerUnit / 2.0D;
         this.ymax = (this.ymaxPreferred + this.yminPreferred) / 2.0D + (double)Math.max(this.height - this.bottomGutter - this.topGutter - 1, 1) / this.yPixPerUnit / 2.0D;
         this.pixelTransform = new AffineTransform(this.xPixPerUnit, 0.0D, 0.0D, -this.yPixPerUnit, -this.xmin * this.xPixPerUnit + (double)this.leftGutter, this.ymax * this.yPixPerUnit + (double)this.topGutter);
         this.pixelTransform.getMatrix(this.pixelMatrix);
      } else {
         this.xPixPerUnit = (double)Math.max(this.width - this.leftGutter - this.rightGutter - 1, 1) / (this.xmax - this.xmin);
         this.yPixPerUnit = (double)Math.max(this.height - this.bottomGutter - this.topGutter - 1, 1) / (this.ymax - this.ymin);
         if (this.squareAspect) {
            double var1 = Math.abs(this.xPixPerUnit / this.yPixPerUnit);
            if (var1 >= 1.0D) {
               var1 = Math.min(var1, (double)this.width);
               this.xmin = this.xminPreferred - (this.xmaxPreferred - this.xminPreferred) * (var1 - 1.0D) / 2.0D;
               this.xmax = this.xmaxPreferred + (this.xmaxPreferred - this.xminPreferred) * (var1 - 1.0D) / 2.0D;
               this.xPixPerUnit = (double)Math.max(this.width - this.leftGutter - this.rightGutter - 1, 1) / (this.xmax - this.xmin);
            } else {
               var1 = Math.max(var1, 1.0D / (double)this.height);
               this.ymin = this.yminPreferred - (this.ymaxPreferred - this.yminPreferred) * (1.0D / var1 - 1.0D) / 2.0D;
               this.ymax = this.ymaxPreferred + (this.ymaxPreferred - this.yminPreferred) * (1.0D / var1 - 1.0D) / 2.0D;
               this.yPixPerUnit = (double)Math.max(this.height - this.bottomGutter - this.topGutter - 1, 1) / (this.ymax - this.ymin);
            }
         }

         this.pixelTransform = new AffineTransform(this.xPixPerUnit, 0.0D, 0.0D, -this.yPixPerUnit, -this.xmin * this.xPixPerUnit + (double)this.leftGutter, this.ymax * this.yPixPerUnit + (double)this.topGutter);
         this.pixelTransform.getMatrix(this.pixelMatrix);
      }
   }

   public double[] project(double[] var1, double[] var2) {
      switch(var1.length) {
      case 2:
      case 3:
         var2[0] = (double)this.xToPix(var1[0]);
         var2[1] = (double)this.yToPix(var1[1]);
         break;
      case 4:
         var2[0] = (double)this.xToPix(var1[0]);
         var2[1] = (double)this.yToPix(var1[1]);
         var2[2] = this.xPixPerUnit * var1[2];
         var2[3] = this.yPixPerUnit * var1[3];
         break;
      case 5:
      default:
         throw new IllegalArgumentException("Method project not supported for this length.");
      case 6:
         var2[0] = (double)this.xToPix(var1[0]);
         var2[1] = (double)this.yToPix(var1[1]);
         var2[2] = this.xPixPerUnit * var1[3];
         var2[3] = this.yPixPerUnit * var1[4];
      }

      return var2;
   }

   public double pixToX(int var1) {
      return this.xmin + (double)(var1 - this.leftGutter) / this.xPixPerUnit;
   }

   public int xToPix(double var1) {
      double var3 = this.pixelMatrix[0] * var1 + this.pixelMatrix[4];
      if (var3 > 2.147483647E9D) {
         return Integer.MAX_VALUE;
      } else {
         return var3 < -2.147483648E9D ? Integer.MIN_VALUE : (int)Math.floor((double)((float)var3));
      }
   }

   public double pixToY(int var1) {
      return this.ymax - (double)(var1 - this.topGutter) / this.yPixPerUnit;
   }

   public int yToPix(double var1) {
      double var3 = this.pixelMatrix[3] * var1 + this.pixelMatrix[5];
      if (var3 > 2.147483647E9D) {
         return Integer.MAX_VALUE;
      } else {
         return var3 < -2.147483648E9D ? Integer.MIN_VALUE : (int)Math.floor((double)((float)var3));
      }
   }

   public void scale() {
      ArrayList var1 = this.getDrawables();
      this.scale(var1);
   }

   protected void scale(ArrayList var1) {
      if (this.autoscaleX) {
         this.scaleX(var1);
      }

      if (this.autoscaleY) {
         this.scaleY(var1);
      }

   }

   public void measure() {
      ArrayList var1 = this.getDrawables();
      this.scaleX(var1);
      this.scaleY(var1);
      this.setPixelScale();
      this.validImage = false;
   }

   protected void scaleX() {
      ArrayList var1 = this.getDrawables();
      this.scaleX(var1);
   }

   private void scaleX(ArrayList var1) {
      double var2 = Double.MAX_VALUE;
      double var4 = -1.7976931348623157E308D;
      boolean var6 = false;
      Iterator var7 = var1.iterator();

      while(var7.hasNext()) {
         Object var8 = var7.next();
         if (var8 instanceof Measurable) {
            Measurable var9 = (Measurable)var8;
            if (var9.isMeasured()) {
               double var10 = var9.getXMin();
               double var12 = var9.getXMax();
               if (!Double.isNaN(var10) && !Double.isNaN(var12)) {
                  var2 = Math.min(var2, var10);
                  var2 = Math.min(var2, var12);
                  var4 = Math.max(var4, var12);
                  var4 = Math.max(var4, var10);
                  var6 = true;
               }
            }
         }
      }

      if (var6) {
         if (var4 - var2 < 1.401298464324817E-45D) {
            if (Double.isNaN(this.xfloor)) {
               var2 = 0.9D * var2 - 0.5D;
            } else {
               var2 = this.xfloor;
            }

            if (Double.isNaN(this.xceil)) {
               var4 = 1.1D * var4 + 0.5D;
            } else {
               var4 = this.xceil;
            }
         }

         double var14;
         for(var14 = var4 - var2; Math.abs((var4 + var14) / var14) > 100000.0D; var4 += var14) {
            var14 *= 2.0D;
            var2 -= var14;
         }

         this.xminPreferred = var2 - this.autoscaleMargin * var14;
         this.xmaxPreferred = var4 + this.autoscaleMargin * var14;
      } else {
         if (!Double.isNaN(this.xfloor)) {
            this.xminPreferred = this.xfloor;
         }

         if (!Double.isNaN(this.xceil)) {
            this.xmaxPreferred = this.xceil;
         }
      }

      if (!Double.isNaN(this.xfloor)) {
         this.xminPreferred = Math.min(this.xfloor, this.xminPreferred);
      }

      if (!Double.isNaN(this.xceil)) {
         this.xmaxPreferred = Math.max(this.xceil, this.xmaxPreferred);
      }

   }

   protected void scaleY() {
      ArrayList var1 = this.getDrawables();
      this.scaleY(var1);
   }

   private void scaleY(ArrayList var1) {
      double var2 = Double.MAX_VALUE;
      double var4 = -1.7976931348623157E308D;
      boolean var6 = false;
      Iterator var7 = var1.iterator();

      while(var7.hasNext()) {
         Object var8 = var7.next();
         if (var8 instanceof Measurable) {
            Measurable var9 = (Measurable)var8;
            if (var9.isMeasured()) {
               double var10 = var9.getYMin();
               double var12 = var9.getYMax();
               if (!Double.isNaN(var10) && !Double.isNaN(var12)) {
                  var2 = Math.min(var2, var10);
                  var2 = Math.min(var2, var12);
                  var4 = Math.max(var4, var12);
                  var4 = Math.max(var4, var10);
                  var6 = true;
               }
            }
         }
      }

      if (var6) {
         if (var4 - var2 < 1.401298464324817E-45D) {
            if (Double.isNaN(this.yfloor)) {
               var2 = 0.9D * var2 - 0.5D;
            } else {
               var2 = this.yfloor;
            }

            if (Double.isNaN(this.yceil)) {
               var4 = 1.1D * var4 + 0.5D;
            } else {
               var4 = this.yceil;
            }
         }

         double var14;
         for(var14 = var4 - var2; Math.abs((var4 + var14) / var14) > 100000.0D; var4 += var14) {
            var14 *= 2.0D;
            var2 -= var14;
         }

         this.yminPreferred = var2 - this.autoscaleMargin * var14;
         this.ymaxPreferred = var4 + this.autoscaleMargin * var14;
      } else {
         if (!Double.isNaN(this.yfloor)) {
            this.yminPreferred = this.yfloor;
         }

         if (!Double.isNaN(this.yceil)) {
            this.ymaxPreferred = this.yceil;
         }
      }

      if (!Double.isNaN(this.yfloor)) {
         this.yminPreferred = Math.min(this.yfloor, this.yminPreferred);
      }

      if (!Double.isNaN(this.yceil)) {
         this.ymaxPreferred = Math.max(this.yceil, this.ymaxPreferred);
      }

   }

   protected void paintDrawableList(Graphics var1, ArrayList var2) {
      Graphics2D var3 = (Graphics2D)var1;
      Iterator var4 = var2.iterator();
      Shape var5 = var3.getClip();
      int var6 = this.getWidth() - this.leftGutter - this.rightGutter;
      int var7 = this.getHeight() - this.bottomGutter - this.topGutter;
      if (var6 >= 0 && var7 >= 0) {
         if (this.clipAtGutter) {
            var3.clipRect(this.leftGutter, this.topGutter, var6, var7);
         }

         if (var2 != null && !var2.isEmpty() && var2.get(0) instanceof False3D) {
            ((Drawable)var2.get(0)).draw(this, var3);
         } else {
            while(var4.hasNext()) {
               Drawable var8 = (Drawable)var4.next();
               var8.draw(this, var3);
            }
         }

         var3.setClip(var5);
      }
   }

   public JPanel getGlassPanel() {
      return this.glassPanel;
   }

   public void setIgnoreRepaint(boolean var1) {
      super.setIgnoreRepaint(var1);
      this.glassPanel.setIgnoreRepaint(var1);
   }

   public Dimensioned getDimensionSetter() {
      return this.dimensionSetter;
   }

   public synchronized void addDrawable(Drawable var1) {
      if (var1 != null && !this.drawableList.contains(var1)) {
         this.drawableList.add(var1);
         this.validImage = false;
      }

      if (var1 instanceof Dimensioned) {
         this.dimensionSetter = (Dimensioned)var1;
      }

   }

   public synchronized void addDrawables(Collection var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         if (var3 instanceof Drawable) {
            this.addDrawable((Drawable)var3);
         }
      }

   }

   public synchronized void replaceDrawable(Drawable var1, Drawable var2) {
      if (var1 != null && this.drawableList.contains(var1)) {
         int var3 = this.drawableList.indexOf(var1);
         this.drawableList.set(var3, var2);
         if (var2 instanceof Dimensioned) {
            this.dimensionSetter = (Dimensioned)var2;
         }
      } else {
         this.addDrawable(var2);
      }

   }

   public synchronized void removeDrawable(Drawable var1) {
      this.drawableList.remove(var1);
      if (var1 instanceof Dimensioned) {
         this.dimensionSetter = null;
      }

   }

   public synchronized void removeObjectsOfClass(Class var1) {
      Iterator var2 = this.drawableList.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         if (var3.getClass() == var1) {
            var2.remove();
            if (var3 instanceof Dimensioned) {
               this.dimensionSetter = null;
            }
         }
      }

   }

   public synchronized void removeDrawables(Class var1) {
      Iterator var2 = this.drawableList.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         if (var1.isInstance(var3)) {
            var2.remove();
            if (var3 instanceof Dimensioned) {
               this.dimensionSetter = null;
            }
         }
      }

   }

   public void removeOptionController() {
      this.removeMouseListener(this.optionController);
      this.removeMouseMotionListener(this.optionController);
   }

   public synchronized void clear() {
      this.drawableList.clear();
      this.dimensionSetter = null;
   }

   public synchronized ArrayList getDrawables() {
      return (ArrayList)this.drawableList.clone();
   }

   public synchronized ArrayList getDrawables(Class var1) {
      ArrayList var2 = (ArrayList)this.drawableList.clone();
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         if (!var1.isInstance(var4)) {
            var3.remove();
         }
      }

      return var2;
   }

   public synchronized ArrayList getObjectOfClass(Class var1) {
      ArrayList var2 = (ArrayList)this.drawableList.clone();
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         if (var4.getClass() != var1) {
            var3.remove();
         }
      }

      return var2;
   }

   public int print(Graphics var1, PageFormat var2, int var3) throws PrinterException {
      if (var3 >= 1) {
         return 1;
      } else if (var1 == null) {
         return 1;
      } else {
         Graphics2D var4 = (Graphics2D)var1;
         double var5 = var2.getImageableWidth() / (double)this.getWidth();
         double var7 = var2.getImageableHeight() / (double)this.getHeight();
         double var9 = Math.min(var5, var7);
         var4.translate((int)var2.getImageableX(), (int)var2.getImageableY());
         var4.scale(var9, var9);
         this.paintEverything(var4);
         return 0;
      }
   }

   public int[] getGutters() {
      return new int[]{this.leftGutter, this.topGutter, this.rightGutter, this.bottomGutter};
   }

   public void setGutters(int[] var1) {
      this.leftGutter = var1[0];
      this.topGutter = var1[1];
      this.rightGutter = var1[2];
      this.bottomGutter = var1[3];
   }

   public int getBottomGutter() {
      return this.bottomGutter;
   }

   public int getTopGutter() {
      return this.topGutter;
   }

   public int getLeftGutter() {
      return this.leftGutter;
   }

   public int getRightGutter() {
      return this.rightGutter;
   }

   public void setMessage(String var1) {
      this.brMessageBox.setText(var1);
   }

   public void setMessage(String var1, int var2) {
      switch(var2) {
      case 0:
         this.blMessageBox.setText(var1);
         break;
      case 1:
         this.brMessageBox.setText(var1);
         break;
      case 2:
         this.trMessageBox.setText(var1);
         break;
      case 3:
         this.tlMessageBox.setText(var1);
      }

   }

   public void setShowCoordinates(boolean var1) {
      if (this.showCoordinates && !var1) {
         this.removeMouseListener(this.mouseController);
         this.removeMouseMotionListener(this.mouseController);
      } else if (!this.showCoordinates && var1) {
         this.addMouseListener(this.mouseController);
         this.addMouseMotionListener(this.mouseController);
      }

      this.showCoordinates = var1;
   }

   public static XML.ObjectLoader getLoader() {
      return new DrawingPanel.DrawingPanelLoader();
   }

   static class DrawingPanelLoader implements XML.ObjectLoader {
      public void saveObject(XMLControl var1, Object var2) {
         DrawingPanel var3 = (DrawingPanel)var2;
         var1.setValue("preferred x min", var3.getPreferredXMin());
         var1.setValue("preferred x max", var3.getPreferredXMax());
         var1.setValue("preferred y min", var3.getPreferredYMin());
         var1.setValue("preferred y max", var3.getPreferredYMax());
         var1.setValue("autoscale x", var3.isAutoscaleX());
         var1.setValue("autoscale y", var3.isAutoscaleY());
         var1.setValue("square aspect", var3.isSquareAspect());
         var1.setValue("drawables", var3.getDrawables());
      }

      public Object createObject(XMLControl var1) {
         DrawingPanel var2 = new DrawingPanel();
         double var3 = var1.getDouble("preferred x min");
         double var5 = var1.getDouble("preferred x max");
         double var7 = var1.getDouble("preferred y min");
         double var9 = var1.getDouble("preferred y max");
         var2.setPreferredMinMax(var3, var5, var7, var9);
         if (var1.getBoolean("autoscale x")) {
            var2.setAutoscaleX(true);
         }

         if (var1.getBoolean("autoscale y")) {
            var2.setAutoscaleY(true);
         }

         return var2;
      }

      public Object loadObject(XMLControl var1, Object var2) {
         DrawingPanel var3 = (DrawingPanel)var2;
         double var4 = var1.getDouble("preferred x min");
         double var6 = var1.getDouble("preferred x max");
         double var8 = var1.getDouble("preferred y min");
         double var10 = var1.getDouble("preferred y max");
         var3.setPreferredMinMax(var4, var6, var8, var10);
         var3.squareAspect = var1.getBoolean("square aspect");
         if (var1.getBoolean("autoscale x")) {
            var3.setAutoscaleX(true);
         }

         if (var1.getBoolean("autoscale y")) {
            var3.setAutoscaleY(true);
         }

         Collection var12 = (Collection)var1.getObject("drawables");
         if (var12 != null) {
            var3.clear();
            Iterator var13 = var12.iterator();

            while(var13.hasNext()) {
               var3.addDrawable((Drawable)var13.next());
            }
         }

         return var2;
      }
   }

   class GlassPanel extends JPanel {
      public void render(Graphics var1) {
         Component[] var2 = DrawingPanel.this.glassPanelLayout.getComponents();
         int var3 = 0;

         for(int var4 = var2.length; var3 < var4; ++var3) {
            if (var2[var3] != null) {
               var1.translate(var2[var3].getX(), var2[var3].getY());
               var2[var3].print(var1);
               var1.translate(-var2[var3].getX(), -var2[var3].getY());
            }
         }

      }
   }

   class OptionController extends MouseController {
      public void mousePressed(MouseEvent var1) {
         if (var1.isPopupTrigger() && DrawingPanel.this.popupmenu != null && DrawingPanel.this.popupmenu.isEnabled()) {
            DrawingPanel.this.popupmenu.show(var1.getComponent(), var1.getX(), var1.getY());
         } else if (var1.isPopupTrigger() && DrawingPanel.this.popupmenu == null && DrawingPanel.this.customInspector != null) {
            DrawingPanel.this.customInspector.setVisible(true);
         } else if (DrawingPanel.this.enableZoom && var1.isControlDown()) {
            DrawingPanel.this.zoomBox.endZoom(var1.getX(), var1.getY());
            if (!DrawingPanel.this.autoscaleX && !DrawingPanel.this.autoscaleY) {
               DrawingPanel.this.scaleX();
               DrawingPanel.this.scaleY();
            } else if (!DrawingPanel.this.autoscaleX) {
               DrawingPanel.this.scaleX();
            } else if (!DrawingPanel.this.autoscaleY) {
               DrawingPanel.this.scaleY();
            }

            DrawingPanel.this.setPixelScale();
            if (DrawingPanel.this.customInspector == null) {
               DrawingPanelInspector.updateValues(DrawingPanel.this);
            }

         } else {
            if (DrawingPanel.this.enableZoom && var1.isShiftDown() || DrawingPanel.this.zoomMode) {
               DrawingPanel.this.zoomBox.startZoom(var1.getX(), var1.getY());
            }

         }
      }

      public void mouseDragged(MouseEvent var1) {
         if (DrawingPanel.this.zoomBox.visible) {
            DrawingPanel.this.zoomBox.drag(var1.getX(), var1.getY());
         }

      }

      public void mouseReleased(MouseEvent var1) {
         if (var1.isPopupTrigger() && DrawingPanel.this.popupmenu != null && DrawingPanel.this.popupmenu.isEnabled()) {
            DrawingPanel.this.popupmenu.show(var1.getComponent(), var1.getX(), var1.getY());
         } else if (var1.isPopupTrigger() && DrawingPanel.this.popupmenu == null && DrawingPanel.this.customInspector != null) {
            DrawingPanel.this.customInspector.setVisible(true);
         } else {
            if (DrawingPanel.this.zoomBox.visible) {
               DrawingPanel.this.zoomBox.endZoom(var1.getX(), var1.getY());
               if (DrawingPanel.this.customInspector == null) {
                  DrawingPanelInspector.updateValues(DrawingPanel.this);
               }
            }

         }
      }

      public void mouseClicked(MouseEvent var1) {
         if (var1.isPopupTrigger() && DrawingPanel.this.popupmenu != null && DrawingPanel.this.popupmenu.isEnabled()) {
            DrawingPanel.this.popupmenu.show(var1.getComponent(), var1.getX(), var1.getY());
         } else {
            if (var1.isPopupTrigger() && DrawingPanel.this.popupmenu == null && DrawingPanel.this.customInspector != null) {
               DrawingPanel.this.customInspector.setVisible(true);
            }

         }
      }

      public void mouseEntered(MouseEvent var1) {
      }

      public void mouseExited(MouseEvent var1) {
      }

      public void mouseMoved(MouseEvent var1) {
      }
   }

   class PopupmenuListener implements ActionListener {
      public void actionPerformed(ActionEvent var1) {
         DrawingPanel.this.zoomMode = false;
         String var2 = var1.getActionCommand();
         if (var2.equals("Inspect")) {
            DrawingPanel.this.showInspector();
         } else if (var2.equals("Snapshot")) {
            DrawingPanel.this.snapshot();
         } else {
            AutoScaleInspector var3;
            if (var2.equals("Zoom In")) {
               if (DrawingPanel.this.autoscaleX || DrawingPanel.this.autoscaleY) {
                  var3 = new AutoScaleInspector(DrawingPanel.this);
                  var3.setLocationRelativeTo(DrawingPanel.this);
                  var3.updateDisplay();
                  var3.setVisible(true);
               }

               DrawingPanel.this.zoomIn();
            } else if (var2.equals("Zoom Out")) {
               if (DrawingPanel.this.autoscaleX || DrawingPanel.this.autoscaleY) {
                  var3 = new AutoScaleInspector(DrawingPanel.this);
                  var3.setLocationRelativeTo(DrawingPanel.this);
                  var3.updateDisplay();
                  var3.setVisible(true);
               }

               DrawingPanel.this.zoomOut();
            } else if (var2.equals("Scale")) {
               ScaleInspector var4 = new ScaleInspector(DrawingPanel.this);
               var4.setLocationRelativeTo(DrawingPanel.this);
               var4.updateDisplay();
               var4.setVisible(true);
            }
         }

      }
   }

   public class ZoomBox {
      boolean visible = false;
      int xstart;
      int ystart;
      int xstop;
      int ystop;
      int xlast;
      int ylast;

      public void startZoom(int var1, int var2) {
         this.xlast = this.xstop = this.xstart = var1;
         this.ylast = this.ystop = this.ystart = var2;
         this.visible = true;
      }

      public void endZoom(int var1, int var2) {
         DrawingPanel.this.zoomMode = false;
         DrawingPanel.this.setMessage((String)null, 0);
         if (this.visible) {
            this.xstop = var1;
            this.ystop = var2;
            this.visible = false;
            if (this.xstart != this.xstop && this.ystart != this.ystop) {
               double var3 = DrawingPanel.this.pixToX(this.xstart);
               double var5 = DrawingPanel.this.pixToX(this.xstop);
               double var7 = DrawingPanel.this.pixToY(this.ystart);
               double var9 = DrawingPanel.this.pixToY(this.ystop);
               if (!DrawingPanel.this.autoscaleX && !DrawingPanel.this.autoscaleY) {
                  DrawingPanel.this.setPreferredMinMax(var3, var5, var9, var7);
               } else if (!DrawingPanel.this.autoscaleX) {
                  DrawingPanel.this.setPreferredMinMaxX(var3, var5);
               } else if (!DrawingPanel.this.autoscaleY) {
                  DrawingPanel.this.setPreferredMinMaxY(var9, var7);
               }

               this.xlast = this.xstop = this.xstart = 0;
               this.ylast = this.ystop = this.ystart = 0;
               DrawingPanel.this.validImage = false;
               if (!DrawingPanel.this.getIgnoreRepaint()) {
                  DrawingPanel.this.repaint();
               }

            }
         }
      }

      public synchronized void drag(int var1, int var2) {
         if (this.visible) {
            this.xstop = var1;
            this.ystop = var2;
            Graphics var3 = DrawingPanel.this.getGraphics();
            if (var3 != null) {
               var3.setXORMode(Color.green);
               var3.drawRect(Math.min(this.xstart, this.xlast), Math.min(this.ystart, this.ylast), Math.abs(this.xlast - this.xstart), Math.abs(this.ylast - this.ystart));
               this.xlast = this.xstop;
               this.ylast = this.ystop;
               var3.drawRect(Math.min(this.xstart, this.xlast), Math.min(this.ystart, this.ylast), Math.abs(this.xlast - this.xstart), Math.abs(this.ylast - this.ystart));
               var3.setPaintMode();
               var3.dispose();
            }
         }
      }

      synchronized void paint(Graphics var1) {
         if (this.visible) {
            var1.setXORMode(Color.green);
            this.xlast = this.xstop;
            this.ylast = this.ystop;
            var1.drawRect(Math.min(this.xstart, this.xlast), Math.min(this.ystart, this.ylast), Math.abs(this.xlast - this.xstart), Math.abs(this.ylast - this.ystart));
            var1.setPaintMode();
         }
      }
   }

   private class CMController extends MouseController {
      protected DecimalFormat scientificFormat;
      protected DecimalFormat decimalFormat;

      private CMController() {
         this.scientificFormat = new DecimalFormat("0.###E0");
         this.decimalFormat = new DecimalFormat("0.00");
      }

      public void mousePressed(MouseEvent var1) {
         String var2 = DrawingPanel.this.coordinateStrBuilder.getCoordinateString(DrawingPanel.this, var1);
         DrawingPanel.this.blMessageBox.setText(var2);
      }

      public void mouseReleased(MouseEvent var1) {
         DrawingPanel.this.blMessageBox.setText((String)null);
      }

      public void mouseEntered(MouseEvent var1) {
         if (DrawingPanel.this.showCoordinates) {
            DrawingPanel.this.setMouseCursor(Cursor.getPredefinedCursor(1));
         }

      }

      public void mouseExited(MouseEvent var1) {
         DrawingPanel.this.setMouseCursor(Cursor.getPredefinedCursor(0));
      }

      public void mouseClicked(MouseEvent var1) {
      }

      public void mouseDragged(MouseEvent var1) {
         String var2 = DrawingPanel.this.coordinateStrBuilder.getCoordinateString(DrawingPanel.this, var1);
         DrawingPanel.this.blMessageBox.setText(var2);
      }

      public void mouseMoved(MouseEvent var1) {
      }

      // $FF: synthetic method
      CMController(Object var2) {
         this();
      }
   }
}
