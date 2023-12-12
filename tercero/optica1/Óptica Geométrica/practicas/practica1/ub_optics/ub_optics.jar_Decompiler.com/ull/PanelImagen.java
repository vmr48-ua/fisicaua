package ull;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import javax.swing.JComponent;

public class PanelImagen extends JComponent {
   private Image drawImage;

   public PanelImagen(Image image) {
      this.setImage(image);
   }

   public Image getImage() {
      return this.drawImage;
   }

   public void setImage(Image image) {
      if (image == null) {
         this.drawImage = null;
      } else {
         int width = image.getWidth(this);
         if (width <= 0) {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(image, 0);

            try {
               tracker.waitForID(0);
            } catch (InterruptedException var5) {
            }

            width = image.getWidth(this);
         }

         int height = image.getHeight(this);
         this.setPreferredSize(new Dimension(width, height));
         this.drawImage = image;
      }
   }

   public void paintComponent(Graphics g) {
      g.drawImage(this.drawImage, 0, 0, this);
   }
}
