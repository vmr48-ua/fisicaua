package org.opensourcephysics.displayejs;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import org.opensourcephysics.controls.Control;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Interactive;

public abstract class AbstractInteractiveElement implements InteractiveElement {
   protected static final int SENSIBILITY = 5;
   protected boolean canBeMeasured = true;
   protected boolean visible = true;
   protected boolean positionEnabled = false;
   protected boolean sizeEnabled = false;
   protected boolean positionGroupEnabled = true;
   protected boolean sizeGroupEnabled = false;
   protected double x = 0.0D;
   protected double y = 0.0D;
   protected double z = 0.0D;
   protected double sizex = 0.1D;
   protected double sizey = 0.1D;
   protected double sizez = 0.1D;
   protected final Style style = new Style(this);
   protected Resolution resolution = null;
   protected Group group = null;
   protected ElementSet set = null;
   protected int setIndex = -1;
   protected Control control = null;
   protected Object dataObject = null;
   protected boolean hasChanged = true;
   protected DrawingPanel panelWithValidProjection = null;
   private ArrayList listeners = new ArrayList();

   public void canBeMeasured(boolean var1) {
      this.canBeMeasured = var1;
   }

   public void copyFrom(InteractiveElement var1) {
      this.setX(var1.getX());
      this.setY(var1.getY());
      this.setZ(var1.getZ());
      this.setSizeX(var1.getSizeX());
      this.setSizeY(var1.getSizeY());
      this.setSizeZ(var1.getSizeZ());
      this.setVisible(var1.isVisible());
      this.setEnabled(0, var1.isEnabled(0));
      this.setEnabled(1, var1.isEnabled(1));
      this.getStyle().copyFrom(var1.getStyle());
      this.setResolution(var1.getResolution());
      this.setGroup(var1.getGroup());
      this.setGroupEnabled(0, var1.isGroupEnabled(0));
      this.setGroupEnabled(1, var1.isGroupEnabled(1));
      this.setControl(var1.getControl());
   }

   public void setX(double var1) {
      this.x = var1;
      this.hasChanged = true;
   }

   public double getX() {
      return this.x;
   }

   public void setY(double var1) {
      this.y = var1;
      this.hasChanged = true;
   }

   public double getY() {
      return this.y;
   }

   public void setZ(double var1) {
      this.z = var1;
      this.hasChanged = true;
   }

   public double getZ() {
      return this.z;
   }

   public void setXY(double var1, double var3) {
      this.x = var1;
      this.y = var3;
      this.hasChanged = true;
   }

   public void setXYZ(double var1, double var3, double var5) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
      this.hasChanged = true;
   }

   public void setSizeX(double var1) {
      this.sizex = var1;
      this.hasChanged = true;
   }

   public double getSizeX() {
      return this.sizex;
   }

   public void setSizeY(double var1) {
      this.sizey = var1;
      this.hasChanged = true;
   }

   public double getSizeY() {
      return this.sizey;
   }

   public void setSizeZ(double var1) {
      this.sizez = var1;
      this.hasChanged = true;
   }

   public double getSizeZ() {
      return this.sizez;
   }

   public void setSizeXY(double var1, double var3) {
      this.sizex = var1;
      this.sizey = var3;
      this.hasChanged = true;
   }

   public void setSizeXYZ(double var1, double var3, double var5) {
      this.sizex = var1;
      this.sizey = var3;
      this.sizez = var5;
      this.hasChanged = true;
   }

   public void setVisible(boolean var1) {
      this.visible = var1;
   }

   public boolean isVisible() {
      return this.visible;
   }

   public Style getStyle() {
      return this.style;
   }

   public void setResolution(Resolution var1) {
      this.resolution = var1;
      this.hasChanged = true;
   }

   public Resolution getResolution() {
      return this.resolution;
   }

   public void setGroup(Group var1) {
      if (this.group != null) {
         this.group.removeElement(this);
      }

      this.group = var1;
      if (this.group != null) {
         this.group.addElement(this);
      }

      this.hasChanged = true;
   }

   public Group getGroup() {
      return this.group;
   }

   public void setGroupEnabled(boolean var1) {
      this.positionGroupEnabled = this.sizeGroupEnabled = var1;
   }

   public boolean isGroupEnabled() {
      return this.positionGroupEnabled || this.sizeGroupEnabled;
   }

   public void setGroupEnabled(int var1, boolean var2) {
      switch(var1) {
      case 0:
         this.positionGroupEnabled = var2;
         break;
      case 1:
         this.sizeGroupEnabled = var2;
      }

   }

   public boolean isGroupEnabled(int var1) {
      switch(var1) {
      case 0:
         return this.positionGroupEnabled;
      case 1:
         return this.sizeGroupEnabled;
      default:
         return false;
      }
   }

   public void setSet(ElementSet var1, int var2) {
      this.set = var1;
      this.setIndex = var2;
   }

   public ElementSet getSet() {
      return this.set;
   }

   public int getSetIndex() {
      return this.setIndex;
   }

   public void setControl(Control var1) {
      this.control = var1;
   }

   public Control getControl() {
      return this.control;
   }

   public String toXML() {
      return this.toString();
   }

   public void setDataObject(Object var1) {
      this.dataObject = var1;
   }

   public Object getDataObject() {
      return this.dataObject;
   }

   public abstract Interactive findInteractive(DrawingPanel var1, int var2, int var3);

   public void needsToProject(DrawingPanel var1) {
      this.panelWithValidProjection = null;
   }

   public abstract Object3D[] getObjects3D(DrawingPanel3D var1);

   public abstract void draw(DrawingPanel3D var1, Graphics2D var2, int var3);

   public void drawQuickly(DrawingPanel3D var1, Graphics2D var2) {
      this.draw(var1, var2);
   }

   public abstract void draw(DrawingPanel var1, Graphics var2);

   public void setEnabled(boolean var1) {
      this.positionEnabled = this.sizeEnabled = var1;
   }

   public boolean isEnabled() {
      return this.positionEnabled || this.sizeEnabled;
   }

   public void setEnabled(int var1, boolean var2) {
      switch(var1) {
      case 0:
         this.positionEnabled = var2;
         break;
      case 1:
         this.sizeEnabled = var2;
      }

   }

   public boolean isEnabled(int var1) {
      switch(var1) {
      case 0:
         return this.positionEnabled;
      case 1:
         return this.sizeEnabled;
      default:
         return false;
      }
   }

   public void addListener(InteractionListener var1) {
      if (this.set != null) {
         System.out.println("Warning: elements in sets should not add listeners! " + this.toString());
      }

      if (var1 != null && !this.listeners.contains(var1)) {
         this.listeners.add(var1);
      }
   }

   public void removeListener(InteractionListener var1) {
      this.listeners.remove(var1);
   }

   public void removeAllListeners() {
      this.listeners = new ArrayList();
   }

   public void invokeActions(InteractionEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         InteractionListener var3 = (InteractionListener)var2.next();
         var3.interactionPerformed(var1);
      }

   }

   public boolean isMeasured() {
      return this.canBeMeasured && this.visible;
   }

   public double getXMin() {
      return this.group == null ? this.x - Math.abs(this.sizex) : this.group.x + this.x - Math.abs(this.sizex * this.group.sizex);
   }

   public double getXMax() {
      return this.group == null ? this.x + Math.abs(this.sizex) : this.group.x + this.x + Math.abs(this.sizex * this.group.sizex);
   }

   public double getYMin() {
      return this.group == null ? this.y - Math.abs(this.sizey) : this.group.y + this.y - Math.abs(this.sizey * this.group.sizey);
   }

   public double getYMax() {
      return this.group == null ? this.y + Math.abs(this.sizey) : this.group.y + this.y + Math.abs(this.sizey * this.group.sizey);
   }

   public double getZMin() {
      return this.group == null ? this.z - Math.abs(this.sizez) : this.group.z + this.z - Math.abs(this.sizez * this.group.sizez);
   }

   public double getZMax() {
      return this.group == null ? this.z + Math.abs(this.sizez) : this.group.z + this.z + Math.abs(this.sizez * this.group.sizez);
   }
}
