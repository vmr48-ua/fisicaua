package org.opensourcephysics.display2d;

import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLLoader;

public abstract class Plot2DLoader extends XMLLoader {
   public void saveObject(XMLControl var1, Object var2) {
      Plot2D var3 = (Plot2D)var2;
      var1.setValue("grid data", var3.getGridData());
   }

   public abstract Object createObject(XMLControl var1);

   public Object loadObject(XMLControl var1, Object var2) {
      Plot2D var3 = (Plot2D)var2;
      XMLControl var4 = var1.getChildControl("grid data");
      var3.setGridData((GridData)var4.loadObject(var3.getGridData()));
      var3.update();
      return var3;
   }
}
