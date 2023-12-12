package org.opensourcephysics.ejs.control.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import org.opensourcephysics.ejs.control.ControlElement;
import org.opensourcephysics.ejs.control.Utils;
import org.opensourcephysics.ejs.control.value.Value;

public abstract class ControlSwingElement extends ControlElement {
   public static final int NAME = 0;
   public static final int POSITION = 1;
   public static final int PARENT = 2;
   public static final int ENABLED = 3;
   public static final int VISIBLE = 4;
   public static final int SIZE = 5;
   public static final int FOREGROUND = 6;
   public static final int BACKGROUND = 7;
   public static final int FONT = 8;
   public static final int TOOLTIP = 9;
   public static final int ACTION_PRESS = 10;
   public static final int ACTION_ON = 20;
   public static final int ACTION_OFF = 21;
   private static ArrayList myInfoList = null;
   protected Component myVisual;
   private Color myDefaultBkgd = null;
   private Color myDefaultFrgd = null;
   private Font myDefaultFont = null;
   private Dimension mySize = null;

   public ControlSwingElement(Object var1) {
      super(var1);
      this.myVisual = this.createVisual(var1);
      super.myObject = this.myVisual;
      this.myDefaultFrgd = this.myVisual.getForeground();
      this.myDefaultBkgd = this.myVisual.getBackground();
      this.myDefaultFont = this.myVisual.getFont();
      if (this.myVisual instanceof JComponent) {
         this.mySize = ((JComponent)this.myVisual).getPreferredSize();
      }

   }

   protected abstract Component createVisual(Object var1);

   public final Component getVisual() {
      return this.myVisual;
   }

   public Component getComponent() {
      return this.myVisual;
   }

   public ArrayList getPropertyList() {
      if (myInfoList == null) {
         myInfoList = new ArrayList();
         myInfoList.add("name");
         myInfoList.add("position");
         myInfoList.add("parent");
         myInfoList.add("enabled");
         myInfoList.add("visible");
         myInfoList.add("size");
         myInfoList.add("foreground");
         myInfoList.add("background");
         myInfoList.add("font");
         myInfoList.add("tooltip");
      }

      return myInfoList;
   }

   public String getPropertyInfo(String var1) {
      if (var1.equals("name")) {
         return "String         CONSTANT HIDDEN";
      } else if (var1.equals("position")) {
         return "Position       CONSTANT PREVIOUS HIDDEN ";
      } else if (var1.equals("parent")) {
         return "ControlElement CONSTANT HIDDEN";
      } else if (var1.equals("enabled")) {
         return "boolean          BASIC HIDDEN";
      } else if (var1.equals("visible")) {
         return "boolean          BASIC HIDDEN";
      } else if (var1.equals("size")) {
         return "Dimension|Object BASIC";
      } else if (var1.equals("foreground")) {
         return "Color|Object     BASIC";
      } else if (var1.equals("background")) {
         return "Color|Object     BASIC";
      } else if (var1.equals("font")) {
         return "Font|Object      BASIC";
      } else {
         return var1.equals("tooltip") ? "String           BASIC" : null;
      }
   }

   public Value parseConstant(String var1, String var2) {
      if (var2 == null) {
         return null;
      } else {
         Value var3;
         if (var1.indexOf("Alignment") >= 0) {
            var3 = ConstantParser.alignmentConstant(var2);
            if (var3 != null) {
               return var3;
            }
         }

         if (var1.indexOf("Dimension") >= 0) {
            var3 = ConstantParser.dimensionConstant(var2);
            if (var3 != null) {
               return var3;
            }
         }

         if (var1.indexOf("Layout") >= 0) {
            var3 = ConstantParser.layoutConstant(((ControlContainer)this).getContainer(), var2);
            if (var3 != null) {
               return var3;
            }
         }

         if (var1.indexOf("Orientation") >= 0) {
            var3 = ConstantParser.orientationConstant(var2);
            if (var3 != null) {
               return var3;
            }
         }

         if (var1.indexOf("Placement") >= 0) {
            var3 = ConstantParser.placementConstant(var2);
            if (var3 != null) {
               return var3;
            }
         }

         if (var1.indexOf("Point") >= 0) {
            var3 = ConstantParser.pointConstant(var2);
            if (var3 != null) {
               return var3;
            }
         }

         return super.parseConstant(var1, var2);
      }
   }

   public void setValue(int var1, Value var2) {
      ControlElement var5;
      switch(var1) {
      case 0:
         super.setValue(0, var2);
         this.getComponent().setName(var2.toString());
         break;
      case 1:
         var5 = super.myGroup.getElement(this.getProperty("parent"));
         if (var5 != null && var5 instanceof ControlContainer) {
            ((ControlContainer)var5).remove(this);
         }

         super.myPropertiesTable.put("position", var2.toString());
         if (var5 != null && var5 instanceof ControlContainer) {
            ((ControlContainer)var5).add(this);
         }
         break;
      case 2:
         var5 = super.myGroup.getElement(this.getProperty("parent"));
         if (var5 != null && var5 instanceof ControlContainer) {
            ((ControlContainer)var5).remove(this);
         }

         var5 = super.myGroup.getElement(var2.toString());
         if (var5 == null) {
            if (!(this instanceof ControlWindow)) {
               System.err.println(this.getClass().getName() + " : Error! Parent <" + var2 + "> not found for " + this.toString());
            }
         } else if (var5 instanceof ControlContainer) {
            ((ControlContainer)var5).add(this);
         } else {
            System.err.println(this.getClass().getName() + " : Error! Parent <" + var2 + "> is not a ControlContainer");
         }
         break;
      case 3:
         this.getVisual().setEnabled(var2.getBoolean());
         break;
      case 4:
         this.getVisual().setVisible(var2.getBoolean());
         break;
      case 5:
         if (this.getComponent() instanceof JComponent) {
            Dimension var3 = (Dimension)var2.getObject();
            if (var3.width == this.mySize.width && var3.height == this.mySize.height) {
               return;
            }

            ((JComponent)this.getComponent()).setPreferredSize(this.mySize = var3);
            if (this instanceof ControlContainer) {
               ((ControlContainer)this).getContainer().validate();
            }

            ControlElement var4 = super.myGroup.getElement(this.getProperty("parent"));
            if (var4 != null) {
               ((ControlContainer)var4).adjustSize();
            }
         }
         break;
      case 6:
         if (var2.getObject() instanceof Color) {
            this.getVisual().setForeground((Color)var2.getObject());
         }
         break;
      case 7:
         if (var2.getObject() instanceof Color) {
            this.getVisual().setBackground((Color)var2.getObject());
         }
         break;
      case 8:
         if (var2.getObject() instanceof Font) {
            this.getVisual().setFont((Font)var2.getObject());
         }
         break;
      case 9:
         if (this.getVisual() instanceof JComponent) {
            ((JComponent)this.getVisual()).setToolTipText(var2.toString());
         }
      }

   }

   public void setDefaultValue(int var1) {
      ControlElement var2;
      switch(var1) {
      case 0:
         super.setDefaultValue(0);
         break;
      case 1:
         var2 = super.myGroup.getElement(this.getProperty("parent"));
         if (var2 != null && var2 instanceof ControlContainer) {
            ((ControlContainer)var2).remove(this);
         }

         super.myPropertiesTable.remove("position");
         if (var2 != null && var2 instanceof ControlContainer) {
            ((ControlContainer)var2).add(this);
         }
         break;
      case 2:
         var2 = super.myGroup.getElement(this.getProperty("parent"));
         if (var2 != null && var2 instanceof ControlContainer) {
            ((ControlContainer)var2).remove(this);
         }
         break;
      case 3:
         this.getVisual().setEnabled(true);
         break;
      case 4:
         this.getVisual().setVisible(true);
         break;
      case 5:
         if (this.getComponent() instanceof JComponent) {
            ((JComponent)this.getComponent()).setPreferredSize((Dimension)null);
            if (this instanceof ControlContainer) {
               ((ControlContainer)this).getContainer().validate();
            }

            var2 = super.myGroup.getElement(this.getProperty("parent"));
            if (var2 != null) {
               ((ControlContainer)var2).adjustSize();
            }
         }
         break;
      case 6:
         this.getVisual().setForeground(this.myDefaultFrgd);
         break;
      case 7:
         this.getVisual().setBackground(this.myDefaultBkgd);
         break;
      case 8:
         this.getVisual().setFont(this.myDefaultFont);
         break;
      case 9:
         if (this.getComponent() instanceof JComponent) {
            ((JComponent)this.getVisual()).setToolTipText((String)null);
         }
      }

   }

   public Value getValue(int var1) {
      return null;
   }

   protected ImageIcon getIcon(String var1) {
      ImageIcon var2;
      if (this.getProperty("_ejs_codebase") != null) {
         var2 = Utils.icon(this.getProperty("_ejs_codebase"), var1);
      } else if (this.getSimulation() != null && this.getSimulation().getCodebase() != null) {
         var2 = Utils.icon(this.getSimulation().getCodebase().toString(), var1);
      } else {
         var2 = Utils.icon((String)null, var1);
      }

      return var2;
   }
}
