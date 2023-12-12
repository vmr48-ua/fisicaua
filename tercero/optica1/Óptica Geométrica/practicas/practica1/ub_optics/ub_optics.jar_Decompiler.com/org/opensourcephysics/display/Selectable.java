package org.opensourcephysics.display;

import java.awt.Cursor;

public interface Selectable extends Interactive {
   void setSelected(boolean var1);

   void toggleSelected();

   boolean isSelected();

   Cursor getPreferredCursor();
}
