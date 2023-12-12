package org.opensourcephysics.ejs.control;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import org.opensourcephysics.ejs.Simulation;
import org.opensourcephysics.ejs.control.value.BooleanValue;
import org.opensourcephysics.ejs.control.value.DoubleValue;
import org.opensourcephysics.ejs.control.value.ExpressionValue;
import org.opensourcephysics.ejs.control.value.IntegerValue;
import org.opensourcephysics.ejs.control.value.ObjectValue;
import org.opensourcephysics.ejs.control.value.StringValue;
import org.opensourcephysics.ejs.control.value.Value;

public abstract class ControlElement {
   protected GroupControl myGroup = null;
   protected Hashtable myPropertiesTable = new Hashtable();
   protected Object myObject = null;
   private boolean myActiveState = true;
   private Vector myActionsList = new Vector();
   private GroupVariable[] myProperties = null;
   private String[] myPropertiesNames = null;
   protected boolean isUnderEjs = false;
   MethodWithOneParameter[] myMethodsForProperties = null;
   ExpressionValue[] myExpressionsForProperties = null;
   public static final int NAME = 0;
   public static final int ACTION = 0;
   public static final int VARIABLE_CHANGED = 1;
   public static final int METHOD_FOR_VARIABLE = 2;
   public static final String METHOD_TRIGGER = "_expr_";

   public ControlElement(Object var1) {
      ArrayList var2 = this.getPropertyList();
      this.myObject = var1;
      this.myPropertiesNames = new String[var2.size()];
      this.myProperties = new GroupVariable[var2.size()];
      this.myMethodsForProperties = new MethodWithOneParameter[var2.size()];
      this.myExpressionsForProperties = new ExpressionValue[var2.size()];

      for(int var3 = 0; var3 < var2.size(); ++var3) {
         String var4 = (String)var2.get(var3);
         this.myPropertiesNames[var3] = var4;
         this.myProperties[var3] = null;
         this.myMethodsForProperties[var3] = null;
         this.myExpressionsForProperties[var3] = null;
      }

   }

   public Object getObject() {
      return this.myObject;
   }

   public abstract ArrayList getPropertyList();

   public abstract String getPropertyInfo(String var1);

   public Value parseConstant(String var1, String var2) {
      if (var2 == null) {
         return null;
      } else {
         Value var3;
         if (var1.indexOf("boolean") >= 0) {
            var3 = ConstantParser.booleanConstant(var2);
            if (var3 != null) {
               return var3;
            }
         }

         if (var1.indexOf("Color") >= 0) {
            var3 = ConstantParser.colorConstant(var2);
            if (var3 != null) {
               return var3;
            }
         }

         if (var1.indexOf("File") >= 0) {
            String var4 = null;
            if (this.getProperty("_ejs_codebase") != null) {
               var4 = this.getProperty("_ejs_codebase");
            } else if (this.getSimulation() != null && this.getSimulation().getCodebase() != null) {
               var4 = this.getSimulation().getCodebase().toString();
            }

            if (Utils.fileExists(var4, var2)) {
               return new StringValue(var2);
            }
         }

         if (var1.indexOf("Font") >= 0) {
            Font var5 = null;
            if (this.getVisual() != null) {
               var5 = this.getVisual().getFont();
            }

            var3 = ConstantParser.fontConstant(var5, var2);
            if (var3 != null) {
               return var3;
            }
         }

         if (var1.indexOf("Format") >= 0) {
            var3 = ConstantParser.formatConstant(var2);
            if (var3 != null) {
               return var3;
            }
         }

         if (var1.indexOf("Margins") >= 0 || var1.indexOf("Rectangle") >= 0) {
            var3 = ConstantParser.rectangleConstant(var2);
            if (var3 != null) {
               return var3;
            }
         }

         return null;
      }
   }

   public void setValue(int var1, Value var2) {
      switch(var1) {
      case 0:
         if (this.myGroup != null) {
            this.myGroup.rename(this, var2.toString());
         }
      default:
      }
   }

   public void setDefaultValue(int var1) {
      switch(var1) {
      case 0:
         if (this.myGroup != null) {
            this.myGroup.rename(this, (String)null);
         }
      default:
      }
   }

   public Value getValue(int var1) {
      return null;
   }

   public ControlElement setProperty(String var1, String var2) {
      var1 = var1.trim();
      if (var1.equals("_ejs_")) {
         this.isUnderEjs = true;
      }

      int var3 = this.propertyIndex(var1);
      if (var3 < 0) {
         if (var2 == null) {
            this.myPropertiesTable.remove(var1);
         } else {
            this.myPropertiesTable.put(var1, var2);
         }

         return this;
      } else {
         this.myMethodsForProperties[var3] = null;
         this.myExpressionsForProperties[var3] = null;
         if (this.myProperties[var3] != null) {
            this.myProperties[var3].removeElementListener(this, var3);
            this.myProperties[var3] = null;
         }

         if (var2 == null) {
            if (this.myProperties[var3] != null) {
               this.myProperties[var3].removeElementListener(this, var3);
               this.myProperties[var3] = null;
            }

            this.setDefaultValue(var3);
            this.myPropertiesTable.remove(var1);
            return this;
         } else {
            if (!this.propertyIsTypeOf(var1, "NotTrimmed")) {
               var2 = var2.trim();
            }

            String var4 = var2;
            Object var5 = null;
            if (var2.startsWith("%") && var2.endsWith("%") && var2.length() > 2) {
               var2 = var2.substring(1, var2.length() - 1);
            } else if ((!var2.startsWith("@") || !var2.endsWith("@") || var2.length() <= 2) && (!var2.startsWith("#") || !var2.endsWith("#") || var2.length() <= 2)) {
               if (!var2.startsWith("\"") && !var2.startsWith("'")) {
                  if (this.propertyIsTypeOf(var1, "CONSTANT")) {
                     var5 = new StringValue(var2);
                  }

                  if (var5 == null && this.propertyType(var1).equals("String") && !this.propertyIsTypeOf(var1, "VARIABLE_EXPECTED")) {
                     var5 = new StringValue(var2);
                  }
               }

               if (var5 == null) {
                  var5 = this.parseConstant(this.propertyType(var1), var2);
               }

               if (var5 == null) {
                  var5 = Value.parseConstantOrArray(var2, true);
               }
            }

            if (var5 != null) {
               this.setValue(var3, (Value)var5);
            } else if (this.myGroup != null) {
               boolean var6 = true;
               boolean var7 = false;
               if (var2.startsWith("#") && var2.endsWith("#") && var2.length() > 2) {
                  var2 = var2.substring(1, var2.length() - 1);
                  var6 = true;
               } else if (var2.startsWith("@") && var2.endsWith("@") && var2.length() > 2) {
                  var2 = var2.substring(1, var2.length() - 1);
                  var4 = var2;
                  var6 = false;
                  var7 = true;
               } else if (var2.indexOf(40) >= 0) {
                  var6 = false;
               }

               Object var8;
               if (var6) {
                  var8 = null;
                  if (this.getProperty("_ejs_") == null) {
                     var8 = this.getValue(var3);
                  }

                  if (var8 == null) {
                     if (this.propertyIsTypeOf(var1, "double")) {
                        var8 = new DoubleValue(0.0D);
                     } else if (this.propertyIsTypeOf(var1, "boolean")) {
                        var8 = new BooleanValue(false);
                     } else if (this.propertyIsTypeOf(var1, "int")) {
                        var8 = new IntegerValue(0);
                     } else if (this.propertyIsTypeOf(var1, "String")) {
                        var8 = new StringValue(var2);
                     } else {
                        var8 = new ObjectValue((Object)null);
                     }
                  }

                  this.myProperties[var3] = this.myGroup.registerVariable(var2, this, var3, (Value)var8);
               } else {
                  String var11;
                  if (var7) {
                     var8 = null;
                     if (this.propertyIsTypeOf(var1, "double")) {
                        var11 = "double";
                     } else if (this.propertyIsTypeOf(var1, "boolean")) {
                        var11 = "boolean";
                     } else if (this.propertyIsTypeOf(var1, "int")) {
                        var11 = "int";
                     } else if (this.propertyIsTypeOf(var1, "String")) {
                        var11 = "String";
                     } else {
                        if (!this.propertyIsTypeOf(var1, "Action")) {
                           System.out.println("Error for property " + var1 + " of the element " + this.toString() + ". Cannot be set to : " + var4);
                           this.myPropertiesTable.put(var1, var4);
                           return this;
                        }

                        var11 = "Action";
                     }

                     if (!var11.equals("Action")) {
                        this.myExpressionsForProperties[var3] = new ExpressionValue(var2, this.myGroup);
                        this.myGroup.methodTriggerVariable.addElementListener(this, var3);
                        this.myProperties[var3] = this.myGroup.methodTriggerVariable;
                     }
                  } else if (this.getProperty("_ejs_") == null) {
                     var8 = null;
                     if (this.propertyIsTypeOf(var1, "double")) {
                        var11 = "double";
                     } else if (this.propertyIsTypeOf(var1, "boolean")) {
                        var11 = "boolean";
                     } else if (this.propertyIsTypeOf(var1, "int")) {
                        var11 = "int";
                     } else {
                        if (!this.propertyIsTypeOf(var1, "String")) {
                           System.out.println("Error for property " + var1 + " of the element " + this.toString() + ". Cannot be set to : " + var4);
                           this.myPropertiesTable.put(var1, var4);
                           return this;
                        }

                        var11 = "String";
                     }

                     String[] var9 = MethodWithOneParameter.splitMethodName(var2);
                     if (var9 == null) {
                        System.err.println(this.getClass().getName() + " : Error! method <" + var4 + "> not found");
                        this.myPropertiesTable.put(var1, var4);
                        return this;
                     }

                     if (var9[0] == null) {
                        var9[0] = "_default_";
                     }

                     Object var10 = this.myGroup.getTarget(var9[0]);
                     if (var10 == null) {
                        System.err.println(this.getClass().getName() + " : Error! Target <" + var9[0] + "> not assigned");
                        this.myPropertiesTable.put(var1, var4);
                        return this;
                     }

                     if (var9[2] == null) {
                        var2 = var9[1] + "()";
                     } else {
                        var2 = var9[1] + "(" + var9[2] + ")";
                     }

                     this.myMethodsForProperties[var3] = new MethodWithOneParameter(2, var10, var2, var11, (MethodWithOneParameter)null, this);
                     this.myGroup.methodTriggerVariable.addElementListener(this, var3);
                     this.myProperties[var3] = this.myGroup.methodTriggerVariable;
                  }
               }
            }

            this.myPropertiesTable.put(var1, var4);
            return this;
         }
      }
   }

   public final ControlElement setProperties(String var1) {
      Hashtable var2 = new Hashtable();
      StringTokenizer var3 = new StringTokenizer(var1, ";");

      while(var3.hasMoreTokens()) {
         String var4 = var3.nextToken();
         if (var4.trim().length() > 0) {
            int var5 = var4.indexOf("=");
            if (var5 < 0) {
               System.err.println(this.getClass().getName() + " : Error! Token <" + var4 + "> invalid for " + this.toString());
            } else {
               var2.put(var4.substring(0, var5).trim(), var4.substring(var5 + 1));
            }
         }
      }

      return this.setProperties(var2);
   }

   private void preprocess(String var1, Hashtable var2) {
      String var3 = (String)var2.get(var1);
      if (var3 != null) {
         this.setProperty(var1, var3);
         var2.remove(var1);
      }

   }

   private ControlElement setProperties(Hashtable var1) {
      this.preprocess("_ejs_", var1);
      Hashtable var2 = new Hashtable();
      Enumeration var3 = var1.keys();

      String var4;
      while(var3.hasMoreElements()) {
         var4 = (String)var3.nextElement();
         if (this.propertyIsTypeOf(var4, "PREVIOUS")) {
            this.preprocess(var4, var1);
         } else if (this.propertyIsTypeOf(var4, "POSTPROCESS")) {
            String var5 = (String)var1.get(var4);
            var1.remove(var4);
            var2.put(var4, var5);
         }
      }

      var3 = var1.keys();

      while(var3.hasMoreElements()) {
         var4 = (String)var3.nextElement();
         this.setProperty(var4, (String)var1.get(var4));
      }

      var3 = var2.keys();

      while(var3.hasMoreElements()) {
         var4 = (String)var3.nextElement();
         this.setProperty(var4, (String)var2.get(var4));
      }

      return this;
   }

   public final String getProperty(String var1) {
      return (String)this.myPropertiesTable.get(var1);
   }

   public final boolean propertyIsTypeOf(String var1, String var2) {
      String var3 = this.getPropertyInfo(var1);
      if (var3 == null) {
         return false;
      } else {
         return var3.toLowerCase().indexOf(var2.toLowerCase()) >= 0;
      }
   }

   public final String propertyType(String var1) {
      String var2 = this.getPropertyInfo(var1);
      if (var2 == null) {
         return "double";
      } else {
         StringTokenizer var3 = new StringTokenizer(var2, " ");
         return var3.countTokens() >= 1 ? var3.nextToken() : "double";
      }
   }

   public Component getComponent() {
      return null;
   }

   public Component getVisual() {
      return null;
   }

   public void reset() {
   }

   public void initialize() {
   }

   private int propertyIndex(String var1) {
      if (this.myPropertiesNames != null) {
         for(int var2 = 0; var2 < this.myPropertiesNames.length; ++var2) {
            if (this.myPropertiesNames[var2].equals(var1)) {
               return var2;
            }
         }
      }

      return -1;
   }

   public boolean implementsProperty(String var1) {
      return this.propertyIndex(var1) >= 0;
   }

   public final void variablePropertiesClear() {
      if (this.myPropertiesNames != null) {
         for(int var1 = 0; var1 < this.myPropertiesNames.length; ++var1) {
            this.setProperty(this.myPropertiesNames[var1], (String)null);
         }
      }

   }

   public String toString() {
      String var1 = (String)this.myPropertiesTable.get("name");
      if (var1 != null) {
         return var1;
      } else {
         String var2 = this.getClass().getName();
         int var3 = var2.lastIndexOf(".");
         if (var3 >= 0) {
            var2 = var2.substring(var3 + 1);
         }

         return "Unnamed element of type " + var2;
      }
   }

   public void destroy() {
      this.setProperty("parent", (String)null);
      if (this.myProperties != null) {
         for(int var1 = 0; var1 < this.myProperties.length; ++var1) {
            if (this.myProperties[var1] != null) {
               this.myProperties[var1].removeElementListener(this, var1);
            }
         }
      }

   }

   public final ControlElement addAction(int var1, Object var2, String var3) {
      this.myActionsList.addElement(new MethodWithOneParameter(var1, var2, var3, (String)null, (MethodWithOneParameter)null, this));
      return this;
   }

   public final ControlElement addAction(int var1, Object var2, String var3, MethodWithOneParameter var4) {
      this.myActionsList.addElement(new MethodWithOneParameter(var1, var2, var3, (String)null, var4, this));
      return this;
   }

   public final ControlElement addAction(int var1, String var2) {
      if (this.getProperty("_ejs_") != null) {
         var2 = "_ejs_.execute(\"" + var2 + "\")";
      }

      Object var3 = null;
      MethodWithOneParameter var4 = null;
      String[] var5 = MethodWithOneParameter.splitMethodName(var2);
      if (var5 == null) {
         System.err.println(this.getClass().getName() + " : Error! Method <" + var2 + "> not assigned");
         return this;
      } else {
         if (var5[0] == null) {
            var5[0] = "_default_";
         }

         if (this.myGroup != null) {
            var3 = this.myGroup.getTarget(var5[0]);
            if (var1 == 0 && this.getProperty("_ejs_SecondAction_") != null && this.myGroup.getTarget("_default_") != null) {
               var4 = new MethodWithOneParameter(var1, this.myGroup.getTarget("_default_"), this.getProperty("_ejs_SecondAction_"), (String)null, (MethodWithOneParameter)null, this);
            }
         }

         if (var3 == null) {
            System.err.println(this.getClass().getName() + " : Error! Target <" + var5[0] + "> not assigned");
            return this;
         } else {
            return var5[2] == null ? this.addAction(var1, var3, var5[1] + "()", var4) : this.addAction(var1, var3, var5[1] + "(" + var5[2] + ")", var4);
         }
      }
   }

   public final void removeAction(int var1, Object var2, String var3) {
      if (var3 != null) {
         Enumeration var4 = this.myActionsList.elements();

         MethodWithOneParameter var5;
         do {
            if (!var4.hasMoreElements()) {
               return;
            }

            var5 = (MethodWithOneParameter)var4.nextElement();
         } while(!var5.equals(var1, var2, var3));

         if (!this.myActionsList.removeElement(var5)) {
            System.err.println(this.getClass().getName() + ": Error! Action " + var3 + " not removed");
         }

      }
   }

   public final void removeAction(int var1, String var2) {
      if (var2 != null) {
         if (this.getProperty("_ejs_") != null) {
            var2 = "_ejs_.execute(\"" + var2 + "\")";
         }

         String[] var3 = MethodWithOneParameter.splitMethodName(var2);
         if (var3 == null) {
            System.err.println(this.getClass().getName() + " : Error! Method <" + var2 + "> not removed");
         } else {
            if (var3[0] == null) {
               var3[0] = "_default_";
            }

            Object var4 = null;
            if (this.myGroup != null) {
               var4 = this.myGroup.getTarget(var3[0]);
            }

            if (var4 == null) {
               System.err.println(this.getClass().getName() + " : Error! Target <" + var3[0] + "> not assigned");
            } else {
               this.removeAction(var1, var4, var3[1] + "(" + var3[2] + ")");
            }
         }
      }
   }

   public final void invokeActions() {
      this.invokeActions(0);
   }

   public final void invokeActions(int var1) {
      if (this.myActiveState) {
         Enumeration var2 = this.myActionsList.elements();

         while(var2.hasMoreElements()) {
            ((MethodWithOneParameter)var2.nextElement()).invoke(var1, this);
         }
      }

   }

   public final void variableChangedDoNotUpdate(int var1, Value var2) {
      if (this.myGroup != null && this.myProperties != null) {
         this.myGroup.variableChanged(this.myProperties[var1], this, var2);
      }

      if (this.myActiveState) {
         Enumeration var3 = this.myActionsList.elements();

         while(var3.hasMoreElements()) {
            MethodWithOneParameter var4 = (MethodWithOneParameter)var3.nextElement();
            var4.invoke(1, this);
         }
      }

   }

   public final void variableChanged(int var1, Value var2) {
      if (this.myMethodsForProperties[var1] == null) {
         this.variableChangedDoNotUpdate(var1, var2);
         if (this.myGroup != null && this.myGroup.getSimulation() != null) {
            this.myGroup.getSimulation().update();
         }

      }
   }

   public final void variablesChanged(int[] var1, Value[] var2) {
      boolean var3 = false;
      if (this.myGroup != null && this.myProperties != null) {
         for(int var4 = 0; var4 < var1.length; ++var4) {
            if (this.myMethodsForProperties[var1[var4]] == null) {
               this.myGroup.variableChanged(this.myProperties[var1[var4]], this, var2[var4]);
               var3 = true;
            }
         }
      }

      if (var3) {
         if (this.myActiveState) {
            Enumeration var6 = this.myActionsList.elements();

            while(var6.hasMoreElements()) {
               MethodWithOneParameter var5 = (MethodWithOneParameter)var6.nextElement();
               var5.invoke(1, this);
            }
         }

         if (this.myGroup != null && this.myGroup.getSimulation() != null) {
            this.myGroup.getSimulation().update();
         }

      }
   }

   public final void setActive(boolean var1) {
      this.myActiveState = var1;
   }

   public final boolean isActive() {
      return this.myActiveState;
   }

   public final void setGroup(GroupControl var1) {
      this.myGroup = var1;
   }

   public final GroupControl getGroup() {
      return this.myGroup;
   }

   public final Simulation getSimulation() {
      return this.myGroup == null ? null : this.myGroup.getSimulation();
   }
}
