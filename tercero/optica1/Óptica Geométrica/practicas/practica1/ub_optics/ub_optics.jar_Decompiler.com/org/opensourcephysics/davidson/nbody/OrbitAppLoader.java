package org.opensourcephysics.davidson.nbody;

import java.util.ArrayList;
import org.opensourcephysics.controls.AnimationControl;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.display.Drawable;

class OrbitAppLoader implements XML.ObjectLoader {
   // $FF: synthetic field
   static Class class$org$opensourcephysics$davidson$nbody$OrbitParticle;

   public Object createObject(XMLControl var1) {
      OrbitApp var2 = new OrbitApp();
      AnimationControl var3 = new AnimationControl(var2);
      var3.addButton("addMass", "Add Mass", "Adds a mass with the given intial conditions");
      var2.setControl(var3);
      return var2;
   }

   public void saveObject(XMLControl var1, Object var2) {
      OrbitApp var3 = (OrbitApp)var2;
      var1.setValue("particles", var3.plottingPanel.getDrawables(class$org$opensourcephysics$davidson$nbody$OrbitParticle == null ? (class$org$opensourcephysics$davidson$nbody$OrbitParticle = class$("org.opensourcephysics.davidson.nbody.OrbitParticle")) : class$org$opensourcephysics$davidson$nbody$OrbitParticle));
   }

   public Object loadObject(XMLControl var1, Object var2) {
      OrbitApp var3 = (OrbitApp)var2;
      var3.stopAnimation();
      ArrayList var4 = (ArrayList)var1.getObject("particles");
      var3.plottingPanel.removeObjectsOfClass(class$org$opensourcephysics$davidson$nbody$OrbitParticle == null ? (class$org$opensourcephysics$davidson$nbody$OrbitParticle = class$("org.opensourcephysics.davidson.nbody.OrbitParticle")) : class$org$opensourcephysics$davidson$nbody$OrbitParticle);
      int var5 = var4.size();

      for(int var6 = 0; var6 < var5; ++var6) {
         var3.plottingPanel.addDrawable((Drawable)var4.get(var6));
      }

      var3.initializeAnimation();
      var3.plottingPanel.repaint();
      return var2;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
