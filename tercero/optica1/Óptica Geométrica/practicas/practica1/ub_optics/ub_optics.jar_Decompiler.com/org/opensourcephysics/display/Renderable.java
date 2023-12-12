package org.opensourcephysics.display;

import java.awt.Image;
import java.awt.image.BufferedImage;

public interface Renderable {
   BufferedImage render();

   Image render(Image var1);
}
