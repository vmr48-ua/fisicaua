package org.opensourcephysics.controls;

public class OSPApplication {
   Control control;
   Object model;

   public OSPApplication(Control var1, Object var2) {
      this.control = var1;
      this.model = var2;
   }

   public static XML.ObjectLoader getLoader() {
      return new OSPApplication.OSPAppLoader();
   }

   static class OSPAppLoader implements XML.ObjectLoader {
      public void saveObject(XMLControl var1, Object var2) {
         OSPApplication var3 = (OSPApplication)var2;
         var1.setValue("control", var3.control);
         var1.setValue("model", var3.model);
      }

      public Object createObject(XMLControl var1) {
         Object var2 = var1.getObject("model");
         Control var3 = (Control)var1.getObject("control");
         return new OSPApplication(var3, var2);
      }

      public Object loadObject(XMLControl var1, Object var2) {
         OSPApplication var3 = (OSPApplication)var2;
         XMLControlElement var4 = (XMLControlElement)var1.getChildControl("control");
         if (var4 != null) {
            var4.loadObject(var3.control, true, true);
         }

         XMLControl var5 = var1.getChildControl("model");
         if (var5 != null) {
            var5.loadObject(var3.model);
         }

         return var3;
      }
   }
}
