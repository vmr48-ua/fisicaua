package org.opensourcephysics.ejs.control.swing;

import java.util.Enumeration;
import java.util.Vector;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.ejs.control.NeedsUpdate;

public abstract class ControlDrawablesParent extends ControlSwingElement implements NeedsUpdate {
   private Vector preupdateList = new Vector();

   public ControlDrawablesParent(Object var1) {
      super(var1);
   }

   public void update() {
      Enumeration var1 = this.preupdateList.elements();

      while(var1.hasMoreElements()) {
         ((NeedsPreUpdate)var1.nextElement()).preupdate();
      }

      ((DrawingPanel)this.getVisual()).render();
   }

   public void addToPreupdateList(NeedsPreUpdate var1) {
      this.preupdateList.add(var1);
   }

   public void removeFromPreupdateList(NeedsPreUpdate var1) {
      this.preupdateList.remove(var1);
   }

   public ControlDrawable getSelectedDrawable() {
      return null;
   }
}
