package org.opensourcephysics.ejs.control;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Window;
import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JDialog;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.ejs.Simulation;
import org.opensourcephysics.ejs.control.swing.ControlContainer;
import org.opensourcephysics.ejs.control.swing.ControlDialog;
import org.opensourcephysics.ejs.control.swing.ControlFrame;
import org.opensourcephysics.ejs.control.swing.ControlWindow;
import org.opensourcephysics.ejs.control.value.BooleanValue;
import org.opensourcephysics.ejs.control.value.DoubleValue;
import org.opensourcephysics.ejs.control.value.IntegerValue;
import org.opensourcephysics.ejs.control.value.ObjectValue;
import org.opensourcephysics.ejs.control.value.StringValue;
import org.opensourcephysics.ejs.control.value.Value;

public class GroupControl {
   public static final int DEBUG_NONE = 0;
   public static final int DEBUG_SET_AND_GET = 1;
   public static final int DEBUG_ELEMENTS = 2;
   public static final int DEBUG_CONTROL = 4;
   public static final int DEBUG_CONTROL_VERBOSE = 8;
   public static final int DEBUG_DRAWING = 16;
   public static final int DEBUG_DRAWING_VERBOSE = 32;
   public static final int DEBUG_SYSTEM = 64;
   public static final int DEBUG_SYSTEM_VERBOSE = 128;
   public static final int DEBUG_ALL = 255;
   private int debugLevel;
   private String debugPrefix;
   protected String replaceOwnerName;
   private Frame ownerFrame;
   protected Frame replaceOwnerFrame;
   private Vector prefixList;
   private Simulation mySimulation;
   private Hashtable targetTable;
   private Hashtable elementTable;
   Hashtable variableTable;
   private Vector elementList;
   private Vector updateList;
   GroupVariable methodTriggerVariable;
   private BooleanValue booleanValue;
   private IntegerValue integerValue;
   private DoubleValue doubleValue;
   private StringValue stringValue;
   private ObjectValue objectValue;
   // $FF: synthetic field
   static Class class$java$lang$Object;

   public GroupControl() {
      this.debugLevel = 0;
      this.debugPrefix = "";
      this.replaceOwnerName = null;
      this.ownerFrame = null;
      this.replaceOwnerFrame = null;
      this.prefixList = new Vector();
      this.mySimulation = null;
      this.targetTable = new Hashtable();
      this.elementTable = new Hashtable();
      this.variableTable = new Hashtable();
      this.elementList = new Vector();
      this.updateList = new Vector();
      this.methodTriggerVariable = null;
      this.booleanValue = new BooleanValue(false);
      this.integerValue = new IntegerValue(0);
      this.doubleValue = new DoubleValue(0.0D);
      this.stringValue = new StringValue("");
      this.objectValue = new ObjectValue((Object)null);
      this.debugPrefix = this.getClass().getName();
      int var1 = this.debugPrefix.lastIndexOf(".");
      if (var1 >= 0) {
         this.debugPrefix = this.debugPrefix.substring(var1 + 1);
      }

      this.appendPrefixPath("org.opensourcephysics.ejs.control.swing.Control");
      this.appendPrefixPath("org.opensourcephysics.ejs.control.drawables.Control");
      this.appendPrefixPath("org.opensourcephysics.ejs.control.displayejs.Control");
      this.setValue("_expr_", (Value)(new BooleanValue(false)));
      this.methodTriggerVariable = (GroupVariable)this.variableTable.get("_expr_");
   }

   public GroupControl(Object var1) {
      this();
      this.addTarget("_default_", var1);
      if (var1 instanceof Simulation) {
         this.setSimulation((Simulation)var1);
      }

   }

   public GroupControl(Object var1, String var2, Frame var3) {
      this(var1);
      this.replaceOwnerFrame(var2, var3);
   }

   public void setOwnerFrame(Frame var1) {
      this.ownerFrame = var1;
   }

   public Frame getOwnerFrame() {
      return this.ownerFrame;
   }

   public void replaceOwnerFrame(String var1, Frame var2) {
      this.replaceOwnerName = var1;
      this.replaceOwnerFrame = var2;
   }

   public String getReplaceOwnerName() {
      return this.replaceOwnerName;
   }

   public Frame getReplaceOwnerFrame() {
      return this.replaceOwnerFrame;
   }

   public void clearPrefixPath() {
      this.prefixList.clear();
   }

   public void appendPrefixPath(String var1) {
      this.prefixList.add(var1);
   }

   public Vector getDefaultPrefixList() {
      return this.prefixList;
   }

   public void setSimulation(Simulation var1) {
      this.mySimulation = var1;
   }

   public Simulation getSimulation() {
      return this.mySimulation;
   }

   public void setDebugLevel(int var1) {
      this.debugLevel = var1;
   }

   public int getDebugLevel() {
      return this.debugLevel;
   }

   public Object getTarget(String var1) {
      return this.targetTable.get(var1);
   }

   public void addTarget(String var1, Object var2) {
      this.targetTable.put(var1, var2);
   }

   public void removeTarget(String var1) {
      this.targetTable.remove(var1);
   }

   public void setValue(String var1, Value var2) {
      GroupVariable var3 = (GroupVariable)this.variableTable.get(var1);
      if (var3 == null) {
         var3 = new GroupVariable(var1, var2);
         this.variableTable.put(var1, var3);
      } else {
         var3.setValue(var2);
         var3.propagateValue((ControlElement)null);
      }

   }

   public Value getValue(String var1) {
      GroupVariable var2 = (GroupVariable)this.variableTable.get(var1);
      return var2 == null ? null : var2.getValue();
   }

   public GroupVariable registerVariable(String var1, ControlElement var2, int var3, Value var4) {
      if (var1 == null) {
         return null;
      } else {
         GroupVariable var5 = (GroupVariable)this.variableTable.get(var1);
         if (var5 == null) {
            var5 = new GroupVariable(var1, var4);
            this.variableTable.put(var1, var5);
            if ((this.debugLevel & 1) > 0) {
               System.out.print("   Created new variable <" + var1 + "> with value = <" + var4 + "> ...");
            }
         }

         if ((this.debugLevel & 1) > 0) {
            System.out.println("   Variable <" + var1 + "> registered for element <" + var2 + ">");
         }

         var5.addElementListener(var2, var3);
         var5.propagateValue((ControlElement)null);
         return var5;
      }
   }

   public boolean isVariableRegistered(String var1) {
      if (var1 == null) {
         return false;
      } else {
         return this.variableTable.get(var1) != null;
      }
   }

   public void variableChanged(GroupVariable var1, ControlElement var2, Value var3) {
      if (var1 != null) {
         var1.setValue(var3);
         var1.propagateValue(var2);
         var1.invokeListeners(var2);
      }
   }

   public void addListener(String var1, String var2) {
      this.addListener(var1, var2, (Object)null);
   }

   public void addListener(String var1, String var2, Object var3) {
      if ((this.debugLevel & 1) > 0) {
         System.out.print(this.debugPrefix + ": Adding listener for variable <" + var1 + "> to <" + var2 + "> ...");
      }

      if (var1 != null) {
         String[] var4 = MethodWithOneParameter.splitMethodName(var2);
         if (var4 == null) {
            System.err.println(this.getClass().getName() + " : Error! Listener <" + var2 + "> not assigned");
         } else {
            if (var4[0] == null) {
               var4[0] = "_default_";
            }

            Object var5 = this.getTarget(var4[0]);
            if (var5 == null) {
               System.err.println(this.getClass().getName() + " : Error! Target <" + var4[0] + "> not assigned");
            } else {
               if ((this.debugLevel & 1) > 0) {
                  System.out.print(this.debugPrefix + ": Target <" + var4[0] + "> found. Method is <" + var2 + "> ...");
               }

               GroupVariable var6 = (GroupVariable)this.variableTable.get(var1);
               if (var6 == null) {
                  var6 = new GroupVariable(var1, this.doubleValue);
                  this.variableTable.put(var1, var6);
                  if ((this.debugLevel & 1) > 0) {
                     System.out.print("   Created new variable <" + var1 + "> for listener <" + var2 + "> ...");
                  }
               }

               if (var4[2] == null) {
                  var6.addListener(var5, var4[1] + "()", var3);
               } else {
                  var6.addListener(var5, var4[1] + "(" + var4[2] + ")", var3);
               }

            }
         }
      }
   }

   public void rename(ControlElement var1, String var2) {
      String var3 = var1.getProperty("name");
      if (var3 != null) {
         this.elementTable.remove(var3);
      }

      if (var2 != null) {
         this.elementTable.put(var2, var1);
      }

   }

   public final ControlElement addNamed(String var1, String var2) {
      String var3 = "name=" + var2;
      if (this.replaceOwnerName != null && this.replaceOwnerName.equals(var2)) {
         if (!var1.endsWith("ControlFrame") && !var1.endsWith("ControlDrawingFrame")) {
            return this.addObject((Object)null, var1, var3);
         } else {
            this.setOwnerFrame(this.replaceOwnerFrame);
            return this.addObject((Object)null, "org.opensourcephysics.ejs.control.swing.ControlPanel", var3);
         }
      } else {
         return this.addObject((Object)null, var1, var3);
      }
   }

   public final ControlElement add(String var1) {
      return this.addObject((Object)null, var1, (String)null);
   }

   public final ControlElement add(String var1, String var2) {
      return this.addObject((Object)null, var1, var2);
   }

   public final ControlElement addObject(Object var1, String var2) {
      return this.addObject(var1, var2, (String)null);
   }

   public ControlElement addObject(Object var1, String var2, String var3) {
      ControlElement var4 = null;
      if ((this.debugLevel & 2) > 0) {
         System.err.println(this.getClass().getName() + " Adding element of type <" + var2 + "> with properties <" + var3 + ">");
         if (var1 != null) {
            System.err.println(this.getClass().getName() + " using element " + var1);
         }
      }

      if (var2.indexOf(".") < 0) {
         for(Enumeration var5 = this.prefixList.elements(); var5.hasMoreElements() && var4 == null; var4 = this.instantiateClass(var1, (String)var5.nextElement() + var2, false)) {
         }
      }

      if (var4 == null) {
         var4 = this.instantiateClass(var1, var2, true);
      }

      if (var4 == null) {
         return null;
      } else {
         if (var4 instanceof ControlFrame) {
            this.setOwnerFrame((Frame)((ControlFrame)var4).getComponent());
         }

         if (var4 instanceof ControlDialog && this.ownerFrame != null) {
            ((JDialog)((ControlDialog)var4).getComponent()).dispose();
            ((ControlDialog)var4).replaceVisual(this.ownerFrame);
         }

         var4.setGroup(this);
         this.elementList.add(var4);
         if (var4 instanceof NeedsUpdate) {
            this.updateList.add(var4);
         }

         if ((this.debugLevel & 2) > 0) {
            System.err.println(this.getClass().getName() + " Setting properties to <" + var3 + ">");
         }

         if (var3 != null) {
            var4.setProperties(var3);
         }

         if (var4 instanceof ControlWindow && var4.getProperty("visible") == null) {
            var4.setProperty("visible", "true");
         }

         return var4;
      }
   }

   private ControlElement instantiateClass(Object var1, String var2, boolean var3) {
      if ((this.debugLevel & 2) > 0) {
         System.err.println(this.getClass().getName() + ": Trying to instantiate element of class " + var2);
         if (var1 != null) {
            System.err.println(this.getClass().getName() + " using element " + var1);
         }
      }

      Class var4;
      try {
         var4 = Class.forName(var2);
         Class[] var5 = new Class[]{class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object};
         Object[] var6 = new Object[]{var1};
         Constructor var7 = var4.getDeclaredConstructor(var5);
         return (ControlElement)var7.newInstance(var6);
      } catch (Exception var9) {
         if (var3) {
            var9.printStackTrace();
            return null;
         } else {
            try {
               var4 = Class.forName(var2);
               return (ControlElement)var4.newInstance();
            } catch (Exception var8) {
               if (var3) {
                  var8.printStackTrace();
               }

               return null;
            }
         }
      }
   }

   public ControlElement getElement(String var1) {
      if (var1 == null) {
         return null;
      } else {
         ControlElement var2 = (ControlElement)this.elementTable.get(var1);
         return var2 == null ? null : var2;
      }
   }

   public ControlElement getControl(String var1) {
      return this.getElement(var1);
   }

   public Component getVisual(String var1) {
      ControlElement var2 = this.getElement(var1);
      return var2 == null ? null : var2.getVisual();
   }

   public Component getComponent(String var1) {
      ControlElement var2 = this.getElement(var1);
      return var2 == null ? null : var2.getComponent();
   }

   public Container getContainer(String var1) {
      ControlElement var2 = this.getElement(var1);
      return var2 instanceof ControlContainer ? ((ControlContainer)var2).getContainer() : null;
   }

   public void destroy(String var1) {
      this.destroy(this.getElement(var1), true);
   }

   public void destroy(ControlElement var1) {
      this.destroy(var1, true);
   }

   public void reset() {
      Enumeration var1 = this.elementList.elements();

      while(var1.hasMoreElements()) {
         ((ControlElement)var1.nextElement()).reset();
      }

   }

   public void initialize() {
      Enumeration var1 = this.elementList.elements();

      while(var1.hasMoreElements()) {
         ((ControlElement)var1.nextElement()).initialize();
      }

   }

   public void update() {
      this.methodTriggerVariable.propagateValue((ControlElement)null);
      Enumeration var1 = this.updateList.elements();

      while(var1.hasMoreElements()) {
         ((NeedsUpdate)var1.nextElement()).update();
      }

   }

   public void setActive(boolean var1) {
      Enumeration var2 = this.elementList.elements();

      while(var2.hasMoreElements()) {
         ((ControlElement)var2.nextElement()).setActive(var1);
      }

   }

   public void clearVariables() {
      this.variableTable.clear();
   }

   public void clear() {
      this.variableTable.clear();
      this.setOwnerFrame((Frame)null);
      Enumeration var1 = this.elementList.elements();

      ControlElement var2;
      while(var1.hasMoreElements()) {
         var2 = (ControlElement)var1.nextElement();
         String var3 = var2.getProperty("parent");
         if (var3 == null) {
            this.destroy(var2, false);
         }
      }

      if ((this.debugLevel & 2) > 0) {
         System.err.println(this.getClass().getName() + " Warning!: All element were destroyed!");
         System.err.println("  List of remaining elements follows: ");
         var1 = this.elementList.elements();

         while(var1.hasMoreElements()) {
            var2 = (ControlElement)var1.nextElement();
            System.err.println("    " + var2.toString() + "(class is " + var2.getClass().getName() + ")");
         }
      }

   }

   private void destroy(ControlElement var1, boolean var2) {
      if (var1 != null) {
         if (var2) {
            ControlElement var3 = this.getElement(var1.getProperty("parent"));
            if (var3 != null) {
               if (var3 instanceof ControlContainer) {
                  ((ControlContainer)var3).remove(var1);
               }
            } else {
               Container var4 = var1.getComponent().getParent();
               if (var4 != null) {
                  var4.remove(var1.getComponent());
                  var4.validate();
                  var4.repaint();
               }
            }
         }

         var1.variablePropertiesClear();
         String var6 = var1.getProperty("name");
         if (var6 != null) {
            this.elementTable.remove(var6);
         }

         this.elementList.remove(var1);
         if (var1 instanceof NeedsUpdate) {
            this.updateList.remove(var1);
         }

         if (var1 instanceof ControlContainer) {
            Enumeration var7 = ((ControlContainer)var1).getChildren().elements();

            while(var7.hasMoreElements()) {
               ControlElement var5 = (ControlElement)var7.nextElement();
               this.destroy(var5, false);
            }
         }

         if (var1 instanceof ControlWindow) {
            ((ControlWindow)var1).dispose();
         }

      }
   }

   public Container getTopLevelAncestor(String var1) {
      if (var1 != null) {
         ControlElement var2 = this.getElement(var1);
         Component var3 = var2.getComponent();
         if (var3 instanceof JComponent) {
            return ((JComponent)var3).getTopLevelAncestor();
         }
      } else {
         Enumeration var5 = this.elementList.elements();

         while(var5.hasMoreElements()) {
            ControlElement var6 = (ControlElement)var5.nextElement();
            Component var4 = var6.getComponent();
            if (var4 instanceof Window) {
               return (Window)var4;
            }
         }
      }

      return null;
   }

   public void setValue(String var1, boolean var2) {
      this.booleanValue.value = var2;
      this.setValue(var1, (Value)this.booleanValue);
   }

   public void setValue(String var1, int var2) {
      this.integerValue.value = var2;
      this.setValue(var1, (Value)this.integerValue);
   }

   public void setValue(String var1, double var2) {
      this.doubleValue.value = var2;
      this.setValue(var1, (Value)this.doubleValue);
   }

   public void setValue(String var1, String var2) {
      this.stringValue.value = var2;
      this.setValue(var1, (Value)this.stringValue);
   }

   public void setValue(String var1, Object var2) {
      if (var2 instanceof String) {
         this.setValue(var1, (String)var2);
      } else {
         this.objectValue.value = var2;
         this.setValue(var1, (Value)this.objectValue);
      }

   }

   public boolean getBoolean(String var1) {
      Value var2 = this.getValue(var1);
      return var2 == null ? false : var2.getBoolean();
   }

   public int getInt(String var1) {
      Value var2 = this.getValue(var1);
      return var2 == null ? 0 : var2.getInteger();
   }

   public double getDouble(String var1) {
      Value var2 = this.getValue(var1);
      return var2 == null ? 0.0D : var2.getDouble();
   }

   public String getString(String var1) {
      Value var2 = this.getValue(var1);
      return var2 == null ? "" : var2.getString();
   }

   public Object getObject(String var1) {
      Value var2 = this.getValue(var1);
      return var2 == null ? null : var2.getObject();
   }

   public static XML.ObjectLoader getLoader() {
      return new GroupControl.GroupControlLoader();
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static class GroupControlLoader implements XML.ObjectLoader {
      public void saveObject(XMLControl var1, Object var2) {
         GroupControl var3 = (GroupControl)var2;
         Hashtable var4 = var3.variableTable;
         Iterator var5 = var4.keySet().iterator();

         while(var5.hasNext()) {
            String var6 = (String)var5.next();
            if (!var6.startsWith("_")) {
               var1.setValue(var6, var3.getString(var6));
            }
         }

      }

      public Object createObject(XMLControl var1) {
         return new GroupControl((Object)null);
      }

      public Object loadObject(XMLControl var1, Object var2) {
         GroupControl var3 = (GroupControl)var2;
         Hashtable var4 = var3.variableTable;
         Iterator var5 = var4.keySet().iterator();

         while(var5.hasNext()) {
            String var6 = (String)var5.next();
            if (var1.getString(var6) != null) {
               var3.setValue(var6, var1.getString(var6));
            } else if (var1.getObject(var6) != null) {
               var3.setValue(var6, var1.getObject(var6).toString());
            }
         }

         return var2;
      }
   }
}
