package org.jibble.epsgraphics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

public class EpsDocument {
   private float minX;
   private float minY;
   private float maxX;
   private float maxY;
   private boolean _isClipSet = false;
   private String _title;
   private StringWriter _stringWriter;
   private BufferedWriter _bufferedWriter = null;
   private EpsGraphics2D _lastG = null;

   public EpsDocument(String var1) {
      this._title = var1;
      this.minX = Float.POSITIVE_INFINITY;
      this.minY = Float.POSITIVE_INFINITY;
      this.maxX = Float.NEGATIVE_INFINITY;
      this.maxY = Float.NEGATIVE_INFINITY;
      this._stringWriter = new StringWriter();
      this._bufferedWriter = new BufferedWriter(this._stringWriter);
   }

   public EpsDocument(String var1, OutputStream var2, int var3, int var4, int var5, int var6) throws IOException {
      this._title = var1;
      this.minX = (float)var3;
      this.minY = (float)var4;
      this.maxX = (float)var5;
      this.maxY = (float)var6;
      this._bufferedWriter = new BufferedWriter(new OutputStreamWriter(var2));
      this.write(this._bufferedWriter);
   }

   public synchronized String getTitle() {
      return this._title;
   }

   public synchronized void updateBounds(double var1, double var3) {
      if (var1 > (double)this.maxX) {
         this.maxX = (float)var1;
      }

      if (var1 < (double)this.minX) {
         this.minX = (float)var1;
      }

      if (var3 > (double)this.maxY) {
         this.maxY = (float)var3;
      }

      if (var3 < (double)this.minY) {
         this.minY = (float)var3;
      }

   }

   public synchronized void append(EpsGraphics2D var1, String var2) {
      if (this._lastG == null) {
         this._lastG = var1;
      } else if (var1 != this._lastG) {
         EpsGraphics2D var3 = this._lastG;
         this._lastG = var1;
         if (var1.getClip() != var3.getClip()) {
            var1.setClip(var1.getClip());
         }

         if (!var1.getColor().equals(var3.getColor())) {
            var1.setColor(var1.getColor());
         }

         if (!var1.getBackground().equals(var3.getBackground())) {
            var1.setBackground(var1.getBackground());
         }

         if (!var1.getPaint().equals(var3.getPaint())) {
            var1.setPaint(var1.getPaint());
         }

         if (!var1.getComposite().equals(var3.getComposite())) {
            var1.setComposite(var1.getComposite());
         }

         if (!var1.getComposite().equals(var3.getComposite())) {
            var1.setComposite(var1.getComposite());
         }

         if (!var1.getFont().equals(var3.getFont())) {
            var1.setFont(var1.getFont());
         }

         if (!var1.getStroke().equals(var3.getStroke())) {
            var1.setStroke(var1.getStroke());
         }
      }

      this._lastG = var1;

      try {
         this._bufferedWriter.write(var2 + "\n");
      } catch (IOException var4) {
         throw new EpsException("Could not write to the output file: " + var4);
      }
   }

   public synchronized void write(Writer var1) throws IOException {
      float var2 = -this.minX;
      float var3 = -this.minY;
      var1.write("%!PS-Adobe-3.0 EPSF-3.0\n");
      var1.write("%%Creator: EpsGraphics2D 0.9.0 by Paul Mutton, http://www.jibble.org/\n");
      var1.write("%%Title: " + this._title + "\n");
      var1.write("%%CreationDate: " + new Date() + "\n");
      var1.write("%%BoundingBox: 0 0 " + (int)Math.ceil((double)(this.maxX + var2)) + " " + (int)Math.ceil((double)(this.maxY + var3)) + "\n");
      var1.write("%%DocumentData: Clean7Bit\n");
      var1.write("%%DocumentProcessColors: Black\n");
      var1.write("%%ColorUsage: Color\n");
      var1.write("%%Origin: 0 0\n");
      var1.write("%%Pages: 1\n");
      var1.write("%%Page: 1 1\n");
      var1.write("%%EndComments\n\n");
      var1.write("gsave\n");
      if (this._stringWriter != null) {
         var1.write(var2 + " " + var3 + " translate\n");
         this._bufferedWriter.flush();
         StringBuffer var4 = this._stringWriter.getBuffer();

         for(int var5 = 0; var5 < var4.length(); ++var5) {
            var1.write(var4.charAt(var5));
         }

         this.writeFooter(var1);
      } else {
         var1.write(var2 + " " + (this.maxY - this.minY - var3) + " translate\n");
      }

      var1.flush();
   }

   private void writeFooter(Writer var1) throws IOException {
      var1.write("grestore\n");
      if (this.isClipSet()) {
         var1.write("grestore\n");
      }

      var1.write("showpage\n");
      var1.write("\n");
      var1.write("%%EOF");
      var1.flush();
   }

   public synchronized void flush() throws IOException {
      this._bufferedWriter.flush();
   }

   public synchronized void close() throws IOException {
      if (this._stringWriter == null) {
         this.writeFooter(this._bufferedWriter);
         this._bufferedWriter.flush();
         this._bufferedWriter.close();
      }

   }

   public boolean isClipSet() {
      return this._isClipSet;
   }

   public void setClipSet(boolean var1) {
      this._isClipSet = var1;
   }
}
