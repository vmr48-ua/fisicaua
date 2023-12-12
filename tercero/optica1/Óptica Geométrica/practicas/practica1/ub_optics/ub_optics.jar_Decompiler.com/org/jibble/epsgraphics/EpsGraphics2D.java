package org.jibble.epsgraphics;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.RenderingHints.Key;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Hashtable;
import java.util.Map;

public class EpsGraphics2D extends Graphics2D {
   public static final String VERSION = "0.9.0";
   public static final int BLACK_AND_WHITE = 1;
   public static final int GRAYSCALE = 2;
   public static final int RGB = 3;
   private Color _color;
   private AffineTransform _clipTransform;
   private Color _backgroundColor;
   private Paint _paint;
   private Composite _composite;
   private BasicStroke _stroke;
   private Font _font;
   private Shape _clip;
   private AffineTransform _transform;
   private boolean _accurateTextMode;
   private int _colorDepth;
   private EpsDocument _document;
   private static FontRenderContext _fontRenderContext = new FontRenderContext((AffineTransform)null, false, true);

   public EpsGraphics2D() {
      this("Untitled");
   }

   public EpsGraphics2D(String var1) {
      this._document = new EpsDocument(var1);
      this._backgroundColor = Color.white;
      this._clip = null;
      this._transform = new AffineTransform();
      this._clipTransform = new AffineTransform();
      this._accurateTextMode = true;
      this._colorDepth = 3;
      this.setColor(Color.black);
      this.setPaint(Color.black);
      this.setComposite(AlphaComposite.getInstance(1));
      this.setFont(Font.decode((String)null));
      this.setStroke(new BasicStroke());
   }

   public EpsGraphics2D(String var1, File var2, int var3, int var4, int var5, int var6) throws IOException {
      this(var1, (OutputStream)(new FileOutputStream(var2)), var3, var4, var5, var6);
   }

   public EpsGraphics2D(String var1, OutputStream var2, int var3, int var4, int var5, int var6) throws IOException {
      this(var1);
      this._document = new EpsDocument(var1, var2, var3, var4, var5, var6);
   }

   protected EpsGraphics2D(EpsGraphics2D var1) {
      this._document = var1._document;
      this._backgroundColor = var1._backgroundColor;
      this._clip = var1._clip;
      this._clipTransform = (AffineTransform)var1._clipTransform.clone();
      this._transform = (AffineTransform)var1._transform.clone();
      this._color = var1._color;
      this._paint = var1._paint;
      this._composite = var1._composite;
      this._font = var1._font;
      this._stroke = var1._stroke;
      this._accurateTextMode = var1._accurateTextMode;
      this._colorDepth = var1._colorDepth;
   }

   private void methodNotSupported() {
      EpsException var1 = new EpsException("Method not currently supported by EpsGraphics2D version 0.9.0");
      var1.printStackTrace(System.err);
   }

   public void setAccurateTextMode(boolean var1) {
      this._accurateTextMode = var1;
      if (!this.getAccurateTextMode()) {
         this.setFont(this.getFont());
      }

   }

   public boolean getAccurateTextMode() {
      return this._accurateTextMode;
   }

   public void setColorDepth(int var1) {
      if (var1 == 3 || var1 == 2 || var1 == 1) {
         this._colorDepth = var1;
      }

   }

   public int getColorDepth() {
      return this._colorDepth;
   }

   public void flush() throws IOException {
      this._document.flush();
   }

   public void close() throws IOException {
      this.flush();
      this._document.close();
   }

   private void append(String var1) {
      this._document.append(this, var1);
   }

   private Point2D transform(float var1, float var2) {
      Float var3 = new Float(var1, var2);
      Point2D var4 = this._transform.transform(var3, var3);
      var4.setLocation(var4.getX(), -var4.getY());
      return var4;
   }

   private void draw(Shape var1, String var2) {
      if (var1 != null) {
         if (!this._transform.isIdentity()) {
            var1 = this._transform.createTransformedShape(var1);
         }

         float var6;
         float var7;
         float var9;
         if (!var2.equals("clip")) {
            Rectangle2D var3 = var1.getBounds2D();
            Rectangle2D var4 = var3;
            if (this._clip != null) {
               Rectangle2D var5 = this._clip.getBounds2D();
               var4 = var3.createIntersection(var5);
            }

            float var24 = this._stroke.getLineWidth() / 2.0F;
            var6 = (float)var4.getMinX() - var24;
            var7 = (float)var4.getMinY() - var24;
            float var8 = (float)var4.getMaxX() + var24;
            var9 = (float)var4.getMaxY() + var24;
            this._document.updateBounds((double)var6, (double)(-var7));
            this._document.updateBounds((double)var8, (double)(-var9));
         }

         this.append("newpath");
         boolean var21 = false;
         float[] var23 = new float[6];
         PathIterator var25 = var1.getPathIterator((AffineTransform)null);
         var6 = 0.0F;
         var7 = 0.0F;

         for(int var26 = 0; !var25.isDone(); var25.next()) {
            int var22 = var25.currentSegment(var23);
            var9 = var23[0];
            float var10 = -var23[1];
            float var11 = var23[2];
            float var12 = -var23[3];
            float var13 = var23[4];
            float var14 = -var23[5];
            if (var22 == 4) {
               this.append("closepath");
               ++var26;
            } else if (var22 == 3) {
               this.append(var9 + " " + var10 + " " + var11 + " " + var12 + " " + var13 + " " + var14 + " curveto");
               ++var26;
               var6 = var13;
               var7 = var14;
            } else if (var22 == 1) {
               this.append(var9 + " " + var10 + " lineto");
               ++var26;
               var6 = var9;
               var7 = var10;
            } else if (var22 == 0) {
               this.append(var9 + " " + var10 + " moveto");
               ++var26;
               var6 = var9;
               var7 = var10;
            } else if (var22 == 2) {
               float var15 = var6 + 0.6666667F * (var9 - var6);
               float var16 = var7 + 0.6666667F * (var10 - var7);
               float var17 = var9 + 0.33333334F * (var11 - var9);
               float var18 = var10 + 0.33333334F * (var12 - var10);
               this.append(var15 + " " + var16 + " " + var17 + " " + var18 + " " + var11 + " " + var12 + " curveto");
               ++var26;
               var6 = var11;
               var7 = var12;
            } else if (var22 != 0 && var22 == 1) {
            }
         }

         this.append(var2);
         this.append("newpath");
      }

   }

   private String toHexString(int var1) {
      String var2;
      for(var2 = Integer.toString(var1, 16); var2.length() < 2; var2 = "0" + var2) {
      }

      return var2;
   }

   public void draw3DRect(int var1, int var2, int var3, int var4, boolean var5) {
      Color var6 = this.getColor();
      Stroke var7 = this.getStroke();
      this.setStroke(new BasicStroke(1.0F));
      if (var5) {
         this.setColor(var6.brighter());
      } else {
         this.setColor(var6.darker());
      }

      this.drawLine(var1, var2, var1 + var3, var2);
      this.drawLine(var1, var2, var1, var2 + var4);
      if (var5) {
         this.setColor(var6.darker());
      } else {
         this.setColor(var6.brighter());
      }

      this.drawLine(var1 + var3, var2 + var4, var1, var2 + var4);
      this.drawLine(var1 + var3, var2 + var4, var1 + var3, var2);
      this.setColor(var6);
      this.setStroke(var7);
   }

   public void fill3DRect(int var1, int var2, int var3, int var4, boolean var5) {
      Color var6 = this.getColor();
      if (var5) {
         this.setColor(var6.brighter());
      } else {
         this.setColor(var6.darker());
      }

      this.draw(new Rectangle(var1, var2, var3, var4), "fill");
      this.setColor(var6);
      this.draw3DRect(var1, var2, var3, var4, var5);
   }

   public void draw(Shape var1) {
      this.draw(var1, "stroke");
   }

   public boolean drawImage(Image var1, AffineTransform var2, ImageObserver var3) {
      AffineTransform var4 = this.getTransform();
      this.transform(var2);
      boolean var5 = this.drawImage(var1, 0, 0, var3);
      this.setTransform(var4);
      return var5;
   }

   public void drawImage(BufferedImage var1, BufferedImageOp var2, int var3, int var4) {
      BufferedImage var5 = var2.filter(var1, (BufferedImage)null);
      this.drawImage(var5, new AffineTransform(1.0F, 0.0F, 0.0F, 1.0F, (float)var3, (float)var4), (ImageObserver)null);
   }

   public void drawRenderedImage(RenderedImage var1, AffineTransform var2) {
      Hashtable var3 = new Hashtable();
      String[] var4 = var1.getPropertyNames();

      for(int var5 = 0; var5 < var4.length; ++var5) {
         var3.put(var4[var5], var1.getProperty(var4[var5]));
      }

      ColorModel var9 = var1.getColorModel();
      WritableRaster var6 = var1.copyData((WritableRaster)null);
      BufferedImage var7 = new BufferedImage(var9, var6, var9.isAlphaPremultiplied(), var3);
      AffineTransform var8 = AffineTransform.getTranslateInstance((double)var1.getMinX(), (double)var1.getMinY());
      var8.preConcatenate(var2);
      this.drawImage(var7, var8, (ImageObserver)null);
   }

   public void drawRenderableImage(RenderableImage var1, AffineTransform var2) {
      this.drawRenderedImage(var1.createDefaultRendering(), var2);
   }

   public void drawString(String var1, int var2, int var3) {
      this.drawString(var1, (float)var2, (float)var3);
   }

   public void drawString(String var1, float var2, float var3) {
      if (var1 != null && var1.length() > 0) {
         AttributedString var4 = new AttributedString(var1);
         var4.addAttribute(TextAttribute.FONT, this.getFont());
         this.drawString(var4.getIterator(), var2, var3);
      }

   }

   public void drawString(AttributedCharacterIterator var1, int var2, int var3) {
      this.drawString(var1, (float)var2, (float)var3);
   }

   public void drawString(AttributedCharacterIterator var1, float var2, float var3) {
      if (this.getAccurateTextMode()) {
         TextLayout var4 = new TextLayout(var1, this.getFontRenderContext());
         Shape var5 = var4.getOutline(AffineTransform.getTranslateInstance((double)var2, (double)var3));
         this.draw(var5, "fill");
      } else {
         this.append("newpath");
         Point2D var7 = this.transform(var2, var3);
         this.append(var7.getX() + " " + var7.getY() + " moveto");
         StringBuffer var8 = new StringBuffer();

         for(char var6 = var1.first(); var6 != '\uffff'; var6 = var1.next()) {
            if (var6 == '(' || var6 == ')') {
               var8.append('\\');
            }

            var8.append(var6);
         }

         this.append("(" + var8.toString() + ") show");
      }

   }

   public void drawGlyphVector(GlyphVector var1, float var2, float var3) {
      Shape var4 = var1.getOutline(var2, var3);
      this.draw(var4, "fill");
   }

   public void fill(Shape var1) {
      this.draw(var1, "fill");
   }

   public boolean hit(Rectangle var1, Shape var2, boolean var3) {
      return var2.intersects(var1);
   }

   public GraphicsConfiguration getDeviceConfiguration() {
      Object var1 = null;
      GraphicsEnvironment var2 = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice[] var3 = var2.getScreenDevices();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         GraphicsDevice var5 = var3[var4];
         GraphicsConfiguration[] var6 = var5.getConfigurations();
         if (var6.length > 0) {
            return var6[0];
         }
      }

      return (GraphicsConfiguration)var1;
   }

   public void setComposite(Composite var1) {
      this._composite = var1;
   }

   public void setPaint(Paint var1) {
      this._paint = var1;
      if (var1 instanceof Color) {
         this.setColor((Color)var1);
      }

   }

   public void setStroke(Stroke var1) {
      if (var1 instanceof BasicStroke) {
         this._stroke = (BasicStroke)var1;
         this.append(this._stroke.getLineWidth() + " setlinewidth");
         float var2 = this._stroke.getMiterLimit();
         if (var2 < 1.0F) {
            var2 = 1.0F;
         }

         this.append(var2 + " setmiterlimit");
         this.append(this._stroke.getLineJoin() + " setlinejoin");
         this.append(this._stroke.getEndCap() + " setlinecap");
         StringBuffer var3 = new StringBuffer();
         var3.append("[ ");
         float[] var4 = this._stroke.getDashArray();
         if (var4 != null) {
            for(int var5 = 0; var5 < var4.length; ++var5) {
               var3.append(var4[var5] + " ");
            }
         }

         var3.append("]");
         this.append(var3.toString() + " 0 setdash");
      }

   }

   public void setRenderingHint(Key var1, Object var2) {
   }

   public Object getRenderingHint(Key var1) {
      return null;
   }

   public void setRenderingHints(Map var1) {
   }

   public void addRenderingHints(Map var1) {
   }

   public RenderingHints getRenderingHints() {
      return new RenderingHints((Map)null);
   }

   public void translate(int var1, int var2) {
      this.translate((double)var1, (double)var2);
   }

   public void translate(double var1, double var3) {
      this.transform(AffineTransform.getTranslateInstance(var1, var3));
   }

   public void rotate(double var1) {
      this.rotate(var1, 0.0D, 0.0D);
   }

   public void rotate(double var1, double var3, double var5) {
      this.transform(AffineTransform.getRotateInstance(var1, var3, var5));
   }

   public void scale(double var1, double var3) {
      this.transform(AffineTransform.getScaleInstance(var1, var3));
   }

   public void shear(double var1, double var3) {
      this.transform(AffineTransform.getShearInstance(var1, var3));
   }

   public void transform(AffineTransform var1) {
      this._transform.concatenate(var1);
      this.setTransform(this.getTransform());
   }

   public void setTransform(AffineTransform var1) {
      if (var1 == null) {
         this._transform = new AffineTransform();
      } else {
         this._transform = new AffineTransform(var1);
      }

      this.setStroke(this.getStroke());
      this.setFont(this.getFont());
   }

   public AffineTransform getTransform() {
      return new AffineTransform(this._transform);
   }

   public Paint getPaint() {
      return this._paint;
   }

   public Composite getComposite() {
      return this._composite;
   }

   public void setBackground(Color var1) {
      if (var1 == null) {
         var1 = Color.black;
      }

      this._backgroundColor = var1;
   }

   public Color getBackground() {
      return this._backgroundColor;
   }

   public Stroke getStroke() {
      return this._stroke;
   }

   public void clip(Shape var1) {
      if (this._clip == null) {
         this.setClip(var1);
      } else {
         Area var2 = new Area(this._clip);
         var2.intersect(new Area(var1));
         this.setClip(var2);
      }

   }

   public FontRenderContext getFontRenderContext() {
      return _fontRenderContext;
   }

   public Graphics create() {
      return new EpsGraphics2D(this);
   }

   public Graphics create(int var1, int var2, int var3, int var4) {
      Graphics var5 = this.create();
      var5.translate(var1, var2);
      var5.clipRect(0, 0, var3, var4);
      return var5;
   }

   public Color getColor() {
      return this._color;
   }

   public void setColor(Color var1) {
      if (var1 == null) {
         var1 = Color.black;
      }

      this._color = var1;
      float var2;
      if (this.getColorDepth() == 1) {
         var2 = 0.0F;
         if ((double)(var1.getRed() + var1.getGreen() + var1.getBlue()) > 381.5D) {
            var2 = 1.0F;
         }

         this.append(var2 + " setgray");
      } else if (this.getColorDepth() == 2) {
         var2 = (float)(var1.getRed() + var1.getGreen() + var1.getBlue()) / 765.0F;
         this.append(var2 + " setgray");
      } else {
         this.append((float)var1.getRed() / 255.0F + " " + (float)var1.getGreen() / 255.0F + " " + (float)var1.getBlue() / 255.0F + " setrgbcolor");
      }

   }

   public void setPaintMode() {
   }

   public void setXORMode(Color var1) {
      this.methodNotSupported();
   }

   public Font getFont() {
      return this._font;
   }

   public void setFont(Font var1) {
      if (var1 == null) {
         var1 = Font.decode((String)null);
      }

      this._font = var1;
      if (!this.getAccurateTextMode()) {
         this.append("/" + this._font.getPSName() + " findfont " + this._font.getSize() + " scalefont setfont");
      }

   }

   public FontMetrics getFontMetrics() {
      return this.getFontMetrics(this.getFont());
   }

   public FontMetrics getFontMetrics(Font var1) {
      BufferedImage var2 = new BufferedImage(1, 1, 1);
      Graphics var3 = var2.getGraphics();
      return var3.getFontMetrics(var1);
   }

   public Rectangle getClipBounds() {
      if (this._clip == null) {
         return null;
      } else {
         Rectangle var1 = this.getClip().getBounds();
         return var1;
      }
   }

   public void clipRect(int var1, int var2, int var3, int var4) {
      this.clip(new Rectangle(var1, var2, var3, var4));
   }

   public void setClip(int var1, int var2, int var3, int var4) {
      this.setClip(new Rectangle(var1, var2, var3, var4));
   }

   public Shape getClip() {
      if (this._clip == null) {
         return null;
      } else {
         try {
            AffineTransform var1 = this._transform.createInverse();
            var1.concatenate(this._clipTransform);
            return var1.createTransformedShape(this._clip);
         } catch (Exception var2) {
            throw new EpsException("Unable to get inverse of matrix: " + this._transform);
         }
      }
   }

   public void setClip(Shape var1) {
      if (var1 != null) {
         if (this._document.isClipSet()) {
            this.append("grestore");
            this.append("gsave");
         } else {
            this._document.setClipSet(true);
            this.append("gsave");
         }

         this.draw(var1, "clip");
         this._clip = var1;
         this._clipTransform = (AffineTransform)this._transform.clone();
      } else {
         if (this._document.isClipSet()) {
            this.append("grestore");
            this._document.setClipSet(false);
         }

         this._clip = null;
      }

   }

   public void copyArea(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.methodNotSupported();
   }

   public void drawLine(int var1, int var2, int var3, int var4) {
      java.awt.geom.Line2D.Float var5 = new java.awt.geom.Line2D.Float((float)var1, (float)var2, (float)var3, (float)var4);
      this.draw(var5);
   }

   public void fillRect(int var1, int var2, int var3, int var4) {
      Rectangle var5 = new Rectangle(var1, var2, var3, var4);
      this.draw(var5, "fill");
   }

   public void drawRect(int var1, int var2, int var3, int var4) {
      Rectangle var5 = new Rectangle(var1, var2, var3, var4);
      this.draw(var5);
   }

   public void clearRect(int var1, int var2, int var3, int var4) {
      Color var5 = this.getColor();
      this.setColor(this.getBackground());
      Rectangle var6 = new Rectangle(var1, var2, var3, var4);
      this.draw(var6, "fill");
      this.setColor(var5);
   }

   public void drawRoundRect(int var1, int var2, int var3, int var4, int var5, int var6) {
      java.awt.geom.RoundRectangle2D.Float var7 = new java.awt.geom.RoundRectangle2D.Float((float)var1, (float)var2, (float)var3, (float)var4, (float)var5, (float)var6);
      this.draw(var7);
   }

   public void fillRoundRect(int var1, int var2, int var3, int var4, int var5, int var6) {
      java.awt.geom.RoundRectangle2D.Float var7 = new java.awt.geom.RoundRectangle2D.Float((float)var1, (float)var2, (float)var3, (float)var4, (float)var5, (float)var6);
      this.draw(var7, "fill");
   }

   public void drawOval(int var1, int var2, int var3, int var4) {
      java.awt.geom.Ellipse2D.Float var5 = new java.awt.geom.Ellipse2D.Float((float)var1, (float)var2, (float)var3, (float)var4);
      this.draw(var5);
   }

   public void fillOval(int var1, int var2, int var3, int var4) {
      java.awt.geom.Ellipse2D.Float var5 = new java.awt.geom.Ellipse2D.Float((float)var1, (float)var2, (float)var3, (float)var4);
      this.draw(var5, "fill");
   }

   public void drawArc(int var1, int var2, int var3, int var4, int var5, int var6) {
      java.awt.geom.Arc2D.Float var7 = new java.awt.geom.Arc2D.Float((float)var1, (float)var2, (float)var3, (float)var4, (float)var5, (float)var6, 0);
      this.draw(var7);
   }

   public void fillArc(int var1, int var2, int var3, int var4, int var5, int var6) {
      java.awt.geom.Arc2D.Float var7 = new java.awt.geom.Arc2D.Float((float)var1, (float)var2, (float)var3, (float)var4, (float)var5, (float)var6, 2);
      this.draw(var7, "fill");
   }

   public void drawPolyline(int[] var1, int[] var2, int var3) {
      if (var3 > 0) {
         GeneralPath var4 = new GeneralPath();
         var4.moveTo((float)var1[0], (float)var2[0]);

         for(int var5 = 1; var5 < var3; ++var5) {
            var4.lineTo((float)var1[var5], (float)var2[var5]);
         }

         this.draw(var4);
      }

   }

   public void drawPolygon(int[] var1, int[] var2, int var3) {
      Polygon var4 = new Polygon(var1, var2, var3);
      this.draw(var4);
   }

   public void drawPolygon(Polygon var1) {
      this.draw(var1);
   }

   public void fillPolygon(int[] var1, int[] var2, int var3) {
      Polygon var4 = new Polygon(var1, var2, var3);
      this.draw(var4, "fill");
   }

   public void fillPolygon(Polygon var1) {
      this.draw(var1, "fill");
   }

   public void drawChars(char[] var1, int var2, int var3, int var4, int var5) {
      String var6 = new String(var1, var2, var3);
      this.drawString(var6, var4, var5);
   }

   public void drawBytes(byte[] var1, int var2, int var3, int var4, int var5) {
      String var6 = new String(var1, var2, var3);
      this.drawString(var6, var4, var5);
   }

   public boolean drawImage(Image var1, int var2, int var3, ImageObserver var4) {
      return this.drawImage(var1, var2, var3, Color.white, var4);
   }

   public boolean drawImage(Image var1, int var2, int var3, int var4, int var5, ImageObserver var6) {
      return this.drawImage(var1, var2, var3, var4, var5, Color.white, var6);
   }

   public boolean drawImage(Image var1, int var2, int var3, Color var4, ImageObserver var5) {
      int var6 = var1.getWidth((ImageObserver)null);
      int var7 = var1.getHeight((ImageObserver)null);
      return this.drawImage(var1, var2, var3, var6, var7, var4, var5);
   }

   public boolean drawImage(Image var1, int var2, int var3, int var4, int var5, Color var6, ImageObserver var7) {
      return this.drawImage(var1, var2, var3, var2 + var4, var3 + var5, 0, 0, var4, var5, var6, var7);
   }

   public boolean drawImage(Image var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, ImageObserver var10) {
      return this.drawImage(var1, var2, var3, var4, var5, var6, var7, var8, var9, Color.white, var10);
   }

   public boolean drawImage(Image var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, Color var10, ImageObserver var11) {
      if (var2 >= var4) {
         throw new IllegalArgumentException("dx1 >= dx2");
      } else if (var6 >= var8) {
         throw new IllegalArgumentException("sx1 >= sx2");
      } else if (var3 >= var5) {
         throw new IllegalArgumentException("dy1 >= dy2");
      } else if (var7 >= var9) {
         throw new IllegalArgumentException("sy1 >= sy2");
      } else {
         this.append("gsave");
         int var12 = var8 - var6;
         int var13 = var9 - var7;
         int var14 = var4 - var2;
         int var15 = var5 - var3;
         int[] var16 = new int[var12 * var13];
         PixelGrabber var17 = new PixelGrabber(var1, var6, var7, var8 - var6, var9 - var7, var16, 0, var12);

         try {
            var17.grabPixels();
         } catch (InterruptedException var27) {
            return false;
         }

         AffineTransform var18 = new AffineTransform(this._transform);
         var18.translate((double)var2, (double)var3);
         var18.scale((double)var14 / (double)var12, (double)var15 / (double)var13);
         double[] var19 = new double[6];

         try {
            var18 = var18.createInverse();
         } catch (Exception var26) {
            throw new EpsException("Unable to get inverse of matrix: " + var18);
         }

         var18.scale(1.0D, -1.0D);
         var18.getMatrix(var19);
         String var20 = "8";
         this.append(var12 + " " + var13 + " " + var20 + " [" + var19[0] + " " + var19[1] + " " + var19[2] + " " + var19[3] + " " + var19[4] + " " + var19[5] + "]");
         Color var21 = this.getColor();
         this.setColor(this.getBackground());
         this.fillRect(var2, var3, var14, var15);
         this.setColor(var21);
         if (this.getColorDepth() == 1) {
            this.append("{currentfile " + var12 + " string readhexstring pop} bind");
            this.append("image");
         } else if (this.getColorDepth() == 2) {
            this.append("{currentfile " + var12 + " string readhexstring pop} bind");
            this.append("image");
         } else {
            this.append("{currentfile 3 " + var12 + " mul string readhexstring pop} bind");
            this.append("false 3 colorimage");
         }

         StringBuffer var22 = new StringBuffer();

         for(int var23 = 0; var23 < var13; ++var23) {
            for(int var24 = 0; var24 < var12; ++var24) {
               Color var25 = new Color(var16[var24 + var12 * var23]);
               if (this.getColorDepth() == 1) {
                  if ((double)(var25.getRed() + var25.getGreen() + var25.getBlue()) > 381.5D) {
                     var22.append("ff");
                  } else {
                     var22.append("00");
                  }
               } else if (this.getColorDepth() == 2) {
                  var22.append(this.toHexString((var25.getRed() + var25.getGreen() + var25.getBlue()) / 3));
               } else {
                  var22.append(this.toHexString(var25.getRed()) + this.toHexString(var25.getGreen()) + this.toHexString(var25.getBlue()));
               }

               if (var22.length() > 64) {
                  this.append(var22.toString());
                  var22 = new StringBuffer();
               }
            }
         }

         if (var22.length() > 0) {
            this.append(var22.toString());
         }

         this.append("grestore");
         return true;
      }
   }

   public void dispose() {
      this._document = null;
   }

   public void finalize() {
      super.finalize();
   }

   public String toString() {
      StringWriter var1 = new StringWriter();

      try {
         this._document.write(var1);
         this._document.flush();
         this._document.close();
      } catch (IOException var3) {
         throw new EpsException(var3.toString());
      }

      return var1.toString();
   }

   public boolean hitClip(int var1, int var2, int var3, int var4) {
      if (this._clip == null) {
         return true;
      } else {
         Rectangle var5 = new Rectangle(var1, var2, var3, var4);
         return this.hit(var5, this._clip, true);
      }
   }

   public Rectangle getClipBounds(Rectangle var1) {
      if (this._clip == null) {
         return var1;
      } else {
         Rectangle var2 = this.getClipBounds();
         var1.setLocation((int)var2.getX(), (int)var2.getY());
         var1.setSize((int)var2.getWidth(), (int)var2.getHeight());
         return var1;
      }
   }
}
