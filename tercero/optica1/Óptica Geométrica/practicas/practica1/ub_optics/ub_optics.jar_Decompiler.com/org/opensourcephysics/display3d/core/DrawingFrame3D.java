package org.opensourcephysics.display3d.core;

import javax.swing.JFrame;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.display.DrawingFrame;

public interface DrawingFrame3D {
   void setDrawingPanel3D(DrawingPanel3D var1);

   DrawingPanel3D getDrawingPanel3D();

   JFrame getJFrame();

   void setVisible(boolean var1);

   public static class Loader implements XML.ObjectLoader {
      public Object createObject(XMLControl var1) {
         DrawingFrame var2 = new DrawingFrame();
         var2.setTitle(var1.getString("title"));
         var2.setLocation(var1.getInt("location x"), var1.getInt("location y"));
         var2.setSize(var1.getInt("width"), var1.getInt("height"));
         if (var1.getBoolean("showing")) {
            var2.setVisible(true);
         }

         return var2;
      }

      public void saveObject(XMLControl var1, Object var2) {
         DrawingFrame3D var3 = (DrawingFrame3D)var2;
         JFrame var4 = var3.getJFrame();
         var1.setValue("title", var4.getTitle());
         var1.setValue("showing", var4.isShowing());
         var1.setValue("location x", var4.getLocation().x);
         var1.setValue("location y", var4.getLocation().y);
         var1.setValue("width", var4.getSize().width);
         var1.setValue("height", var4.getSize().height);
         var1.setValue("drawing panel", var3.getDrawingPanel3D());
      }

      public Object loadObject(XMLControl var1, Object var2) {
         DrawingFrame3D var3 = (DrawingFrame3D)var2;
         JFrame var4 = var3.getJFrame();
         DrawingPanel3D var5 = var3.getDrawingPanel3D();
         var5.removeAllElements();
         XMLControl var6 = var1.getChildControl("drawing panel");
         var6.loadObject(var5);
         var5.repaint();
         var4.setTitle(var1.getString("title"));
         var4.setLocation(var1.getInt("location x"), var1.getInt("location y"));
         var4.setSize(var1.getInt("width"), var1.getInt("height"));
         if (var1.getBoolean("showing")) {
            var4.setVisible(true);
         }

         return var2;
      }
   }
}
