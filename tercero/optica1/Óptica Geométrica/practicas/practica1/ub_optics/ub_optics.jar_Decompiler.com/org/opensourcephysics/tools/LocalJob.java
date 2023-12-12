package org.opensourcephysics.tools;

import org.opensourcephysics.controls.XMLControlElement;

public class LocalJob implements Job {
   String xml;

   public LocalJob() {
      this.setXML(new Object());
   }

   public LocalJob(String var1) {
      this.setXML(var1);
   }

   public LocalJob(Object var1) {
      this.setXML(var1);
   }

   public String getXML() {
      return this.xml;
   }

   public void setXML(String var1) {
      if (var1 != null) {
         this.xml = var1;
      }

   }

   public void setXML(Object var1) {
      XMLControlElement var2 = new XMLControlElement(var1);
      this.setXML(var2.toXML());
   }
}
