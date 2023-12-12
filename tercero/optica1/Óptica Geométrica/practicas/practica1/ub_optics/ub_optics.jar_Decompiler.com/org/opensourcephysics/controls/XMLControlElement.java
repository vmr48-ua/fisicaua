package org.opensourcephysics.controls;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import org.opensourcephysics.tools.Resource;
import org.opensourcephysics.tools.ResourceLoader;

public class XMLControlElement implements XMLControl {
   public static final int ALWAYS_DECRYPT = 0;
   public static final int PASSWORD_DECRYPT = 3;
   public static final int NEVER_DECRYPT = 5;
   public static int compactArraySize = 0;
   protected static String encoding = "UTF-8";
   protected String className;
   protected Class theClass;
   protected String name;
   protected Map counts;
   protected Object object;
   protected XMLProperty parent;
   protected int level;
   protected ArrayList propNames;
   protected ArrayList props;
   protected BufferedReader input;
   protected BufferedWriter output;
   public boolean canWrite;
   protected boolean valid;
   protected boolean readFailed;
   protected String version;
   protected String doctype;
   private String basepath;
   private String password;
   private int decryptPolicy;
   // $FF: synthetic field
   static Class class$java$lang$Object;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$controls$Cryptic;

   public XMLControlElement() {
      this.className = (class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object).getName();
      this.theClass = null;
      this.counts = new HashMap();
      this.propNames = new ArrayList();
      this.props = new ArrayList();
      this.valid = false;
      this.readFailed = false;
      this.doctype = "osp10.dtd";
      this.decryptPolicy = 0;
   }

   public XMLControlElement(Class var1) {
      this();
      this.setObjectClass(var1);
   }

   public XMLControlElement(Object var1) {
      this();
      this.setObjectClass(var1.getClass());
      this.saveObject(var1);
   }

   public XMLControlElement(XMLProperty var1) {
      this();
      this.parent = var1;
      this.level = var1.getLevel();
   }

   public XMLControlElement(String var1) {
      this();
      if (var1.startsWith("<?xml")) {
         this.readXML(var1);
      } else {
         this.read(var1);
      }

   }

   public XMLControlElement(XMLControl var1) {
      this();
      this.readXML(var1.toXML());
   }

   public void setLockValues(boolean var1) {
   }

   public void setValue(String var1, boolean var2) {
      if (var1 != null) {
         this.setXMLProperty(var1, "boolean", String.valueOf(var2));
      }
   }

   public void setValue(String var1, double var2) {
      if (var1 != null) {
         this.setXMLProperty(var1, "double", String.valueOf(var2));
      }
   }

   public void setValue(String var1, int var2) {
      if (var1 != null) {
         this.setXMLProperty(var1, "int", String.valueOf(var2));
      }
   }

   public void setValue(String var1, Object var2) {
      if (var1 != null) {
         if (var2 == null) {
            Iterator var5 = this.props.iterator();

            while(var5.hasNext()) {
               XMLProperty var4 = (XMLProperty)var5.next();
               if (var1.equals(var4.getPropertyName())) {
                  var5.remove();
                  this.propNames.remove(var1);
                  break;
               }
            }

         } else {
            String var3 = XML.getDataType(var2);
            if (var3 != null) {
               if (var3.equals("int") || var3.equals("double")) {
                  var2 = var2.toString();
               }

               this.setXMLProperty(var1, var3, var2);
            }

         }
      }
   }

   public boolean getBoolean(String var1) {
      XMLProperty var2 = this.getXMLProperty(var1);
      return var2 != null && var2.getPropertyType().equals("boolean") ? "true".equals(var2.getPropertyContent().get(0)) : false;
   }

   public double getDouble(String var1) {
      XMLProperty var2 = this.getXMLProperty(var1);
      return var2 == null || !var2.getPropertyType().equals("double") && !var2.getPropertyType().equals("int") ? Double.NaN : Double.parseDouble((String)var2.getPropertyContent().get(0));
   }

   public int getInt(String var1) {
      XMLProperty var2 = this.getXMLProperty(var1);
      return var2 != null && var2.getPropertyType().equals("int") ? Integer.parseInt((String)var2.getPropertyContent().get(0)) : Integer.MIN_VALUE;
   }

   public String getString(String var1) {
      XMLProperty var2 = this.getXMLProperty(var1);
      if (var2 != null && var2.getPropertyType().equals("string")) {
         String var3 = (String)var2.getPropertyContent().get(0);
         if (var3.indexOf("<![CDATA[") != -1) {
            var3 = var3.substring(var3.indexOf("<![CDATA[") + "<![CDATA[".length(), var3.indexOf("]]>"));
         }

         return var3;
      } else {
         return var1.equals("basepath") && this.getRootControl() != null ? this.getRootControl().basepath : null;
      }
   }

   public Object getObject(String var1) {
      XMLProperty var2 = this.getXMLProperty(var1);
      if (var2 != null) {
         String var3 = var2.getPropertyType();
         if (var3.equals("object")) {
            return this.objectValue(var2);
         }

         if (var3.equals("array")) {
            return this.arrayValue(var2);
         }

         if (var3.equals("collection")) {
            return this.collectionValue(var2);
         }

         if (var3.equals("int")) {
            return new Integer(this.intValue(var2));
         }

         if (var3.equals("double")) {
            return new Double(this.doubleValue(var2));
         }

         if (var3.equals("boolean")) {
            return new Boolean(this.booleanValue(var2));
         }

         if (var3.equals("string")) {
            return this.stringValue(var2);
         }
      }

      return null;
   }

   public Collection getPropertyNames() {
      synchronized(this.propNames) {
         return (List)this.propNames.clone();
      }
   }

   public String getPropertyType(String var1) {
      XMLProperty var2 = this.getXMLProperty(var1);
      return var2 != null ? var2.getPropertyType() : null;
   }

   public void setPassword(String var1) {
      this.password = var1;
      if (this.getObjectClass() != (class$org$opensourcephysics$controls$Cryptic == null ? (class$org$opensourcephysics$controls$Cryptic = class$("org.opensourcephysics.controls.Cryptic")) : class$org$opensourcephysics$controls$Cryptic)) {
         this.setValue("xml_password", var1);
      }

   }

   public String getPassword() {
      if (this.password == null) {
         this.password = this.getString("xml_password");
      }

      return this.password;
   }

   public void setDecryptPolicy(int var1) {
      if (var1 == 5) {
         this.decryptPolicy = 5;
      } else if (var1 == 3) {
         this.decryptPolicy = 3;
      } else {
         this.decryptPolicy = 0;
      }

   }

   public String read(String var1) {
      OSPLog.finest("reading " + var1);
      Resource var2 = ResourceLoader.getResource(var1);
      if (var2 != null) {
         this.read((Reader)var2.openReader());
         String var3 = XML.getDirectoryPath(var1);
         if (!var3.equals("")) {
            ResourceLoader.addSearchPath(var3);
            this.basepath = var3;
         } else {
            this.basepath = XML.getDirectoryPath(var2.getAbsolutePath());
         }

         File var4 = var2.getFile();
         this.canWrite = var4 != null && var4.canWrite();
         return var2.getAbsolutePath();
      } else {
         this.readFailed = true;
         return null;
      }
   }

   public void readXML(String var1) {
      this.input = new BufferedReader(new StringReader(var1));
      this.readInput();
      if (!this.failedToRead()) {
         this.canWrite = false;
      }

   }

   public void read(Reader var1) {
      if (var1 instanceof BufferedReader) {
         this.input = (BufferedReader)var1;
      } else {
         this.input = new BufferedReader(var1);
      }

      this.readInput();

      try {
         this.input.close();
      } catch (IOException var3) {
      }

   }

   public boolean failedToRead() {
      return this.readFailed;
   }

   public String write(String var1) {
      this.canWrite = true;
      int var2 = var1.lastIndexOf("/");
      if (var2 < 0) {
         var2 = var1.lastIndexOf("\\");
      }

      if (var2 > 0) {
         String var3 = var1.substring(0, var2 + 1);
         File var4 = new File(var3);
         if (!var4.exists() && !var4.mkdir()) {
            this.canWrite = false;
            return null;
         }
      }

      try {
         File var8 = new File(var1);
         if (var8.exists() && !var8.canWrite()) {
            JOptionPane.showMessageDialog((Component)null, "File is read-only.");
            this.canWrite = false;
            return null;
         }

         FileOutputStream var9 = new FileOutputStream(var8);
         Charset var5 = Charset.forName(encoding);
         this.write((Writer)(new OutputStreamWriter(var9, var5)));
         if (var8.exists()) {
            String var6 = XML.getDirectoryPath(var8.getCanonicalPath());
            ResourceLoader.addSearchPath(var6);
         }

         if (this.isValid()) {
            if (var1.indexOf("/") != -1) {
               var1 = var1.substring(0, var1.lastIndexOf("/") + 1) + this.getDoctype();
            } else if (var1.indexOf("\\") != -1) {
               var1 = var1.substring(0, var1.lastIndexOf("\\") + 1) + this.getDoctype();
            } else {
               var1 = this.doctype;
            }

            this.writeDocType(new FileWriter(var1));
         }

         if (var8.exists()) {
            return var8.getAbsolutePath();
         }
      } catch (IOException var7) {
         this.canWrite = false;
         OSPLog.warning(var7.getMessage());
      }

      return null;
   }

   public void write(Writer var1) {
      try {
         this.output = new BufferedWriter(var1);
         String var2 = this.toXML();
         if (this.getPassword() != null) {
            Cryptic var3 = new Cryptic(var2);
            XMLControlElement var4 = new XMLControlElement(var3);
            var2 = var4.toXML();
         }

         this.output.write(var2);
         this.output.flush();
         this.output.close();
      } catch (IOException var5) {
         OSPLog.info(var5.getMessage());
      }

   }

   public void writeDocType(Writer var1) {
      try {
         this.output = new BufferedWriter(var1);
         this.output.write(XML.getDTD(this.getDoctype()));
         this.output.flush();
         this.output.close();
      } catch (IOException var3) {
         OSPLog.info(var3.getMessage());
      }

   }

   public String toXML() {
      return this.toString();
   }

   public void setValid(boolean var1) {
      this.valid = var1;
   }

   public boolean isValid() {
      return this.valid && XML.getDTD(this.getDoctype()) != null;
   }

   public void setVersion(String var1) {
      this.version = var1;
   }

   public String getVersion() {
      return this.version;
   }

   public void setDoctype(String var1) {
      if (XML.getDTD(var1) != null) {
      }

   }

   public String getDoctype() {
      return this.doctype;
   }

   public void setObjectClass(Class var1) {
      if (this.object != null && !var1.isInstance(this.object)) {
         throw new RuntimeException("Object " + this.object + " is not instance of " + var1);
      } else {
         this.className = var1.getName();
         this.theClass = var1;
      }
   }

   public Class getObjectClass() {
      if (this.className == null) {
         return null;
      } else if (this.theClass != null && this.theClass.getName().equals(this.className)) {
         return this.theClass;
      } else {
         this.theClass = null;

         try {
            this.theClass = Class.forName(this.className);
         } catch (ClassNotFoundException var4) {
         }

         ClassLoader var1 = XML.getClassLoader();
         if (var1 != null && this.theClass == null) {
            try {
               this.theClass = var1.loadClass(this.className);
            } catch (ClassNotFoundException var3) {
            }
         }

         return this.theClass;
      }
   }

   public String getObjectClassName() {
      return this.className;
   }

   public void saveObject(Object var1) {
      if (var1 == null) {
         var1 = this.object;
      }

      Class var2 = this.getObjectClass();
      if (var2 == null || var2.equals(class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object)) {
         if (var1 == null) {
            return;
         }

         var2 = var1.getClass();
      }

      if (var2.isInstance(var1)) {
         this.object = var1;
         this.className = var1.getClass().getName();
         this.clearValues();
         XML.ObjectLoader var3 = XML.getLoader(var2);
         var3.saveObject(this, var1);
      }

   }

   public Object loadObject(Object var1) {
      return this.loadObject(var1, false, false);
   }

   public Object loadObject(Object var1, boolean var2) {
      return this.loadObject(var1, var2, false);
   }

   public Object loadObject(Object var1, boolean var2, boolean var3) {
      Class var4 = this.getObjectClass();
      int var5;
      if (var4 == null) {
         if (var1 == null) {
            return null;
         }

         if (!var2) {
            var5 = JOptionPane.showConfirmDialog((Component)null, "This XML data is for unknown class \"" + this.className + "\"." + XML.NEW_LINE + "Do you wish to import it into " + var1.getClass() + "?", "Mismatched Classes", 0, 2);
            if (var5 != 0) {
               return var1;
            }
         }

         if (!this.importInto(var1, var3)) {
            return var1;
         }

         var4 = var1.getClass();
      }

      if (var1 != null && !var4.isInstance(var1)) {
         if (!var2) {
            var5 = JOptionPane.showConfirmDialog((Component)null, "This XML data is for " + var4 + "." + XML.NEW_LINE + "Do you wish to import it into " + var1.getClass() + "?", "Mismatched Classes", 0, 2);
            if (var5 != 0) {
               return var1;
            }
         }

         if (!this.importInto(var1, var3)) {
            return var1;
         }

         var4 = var1.getClass();
      }

      XML.ObjectLoader var6 = XML.getLoader(var4);
      if (var1 == null) {
         if (this.object == null) {
            this.object = var6.createObject(this);
         }

         var1 = this.object;
      }

      if (var1 == null) {
         return null;
      } else {
         if (var4.isInstance(var1)) {
            var1 = var6.loadObject(this, var1);
            this.object = var1;
         }

         return var1;
      }
   }

   public void clearValues() {
      this.props.clear();
      this.propNames.clear();
   }

   public void println(String var1) {
      System.out.println(var1);
   }

   public void println() {
      System.out.println();
   }

   public void print(String var1) {
      System.out.print(var1);
   }

   public void clearMessages() {
   }

   public void calculationDone(String var1) {
   }

   public String getPropertyName() {
      XMLProperty var1 = this.getParentProperty();
      if (this.className == null) {
         return var1 == null ? "null" : var1.getPropertyName();
      } else if (!this.isArrayOrCollectionItem()) {
         return var1 != null ? var1.getPropertyName() : this.className.substring(this.className.lastIndexOf(".") + 1);
      } else {
         if (this.name == null) {
            Object var2;
            for(var2 = this; ((XMLProperty)var2).getParentProperty() != null; var2 = ((XMLProperty)var2).getParentProperty()) {
            }

            if (var2 instanceof XMLControlElement) {
               XMLControlElement var3 = (XMLControlElement)var2;
               this.name = this.className.substring(this.className.lastIndexOf(".") + 1);
               this.name = var3.addNumbering(this.name);
            }
         }

         return "" + this.name;
      }
   }

   public String getPropertyType() {
      return "object";
   }

   public Class getPropertyClass() {
      return this.getObjectClass();
   }

   public XMLProperty getParentProperty() {
      return this.parent;
   }

   public int getLevel() {
      return this.level;
   }

   public List getPropertyContent() {
      return this.props;
   }

   public XMLControl getChildControl(String var1) {
      XMLControl[] var2 = this.getChildControls();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         if (var2[var3].getPropertyName().equals(var1)) {
            return var2[var3];
         }
      }

      return null;
   }

   public XMLControl[] getChildControls() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.getPropertyContent().iterator();

      while(var2.hasNext()) {
         XMLProperty var3 = (XMLProperty)var2.next();
         if (var3.getPropertyType().equals("object")) {
            var1.add(var3.getPropertyContent().get(0));
         }
      }

      return (XMLControl[])((XMLControl[])var1.toArray(new XMLControl[0]));
   }

   public XMLControlElement getRootControl() {
      if (this.parent == null) {
         return this;
      } else {
         XMLProperty var1;
         for(var1 = this.parent; var1.getParentProperty() != null; var1 = var1.getParentProperty()) {
         }

         return var1 instanceof XMLControlElement ? (XMLControlElement)var1 : null;
      }
   }

   public String addNumbering(String var1) {
      Integer var2 = (Integer)this.counts.get(var1);
      if (var2 == null) {
         var2 = new Integer(0);
      }

      var2 = new Integer(var2 + 1);
      this.counts.put(var1, var2);
      return var1 + " " + var2.toString();
   }

   public void setValue(String var1) {
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("");
      if (this.getLevel() == 0) {
         var1.append("<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>");
         if (this.isValid()) {
            var1.append(XML.NEW_LINE + "<!DOCTYPE object SYSTEM \"" + this.doctype + "\">");
         }
      }

      var1.append(XML.NEW_LINE + this.indent(this.getLevel()) + "<object class=\"" + this.className + "\"");
      if (this.version != null && this.getLevel() == 0) {
         var1.append(" version=\"" + this.version + "\"");
      }

      List var2 = this.getPropertyContent();
      if (var2.isEmpty()) {
         var1.append("/>");
      } else {
         var1.append(">");
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            var1.append(var3.next().toString());
         }

         var1.append(XML.NEW_LINE + this.indent(this.getLevel()) + "</object>");
      }

      return var1.toString();
   }

   public List getObjects(Class var1) {
      return this.getObjects(var1, false);
   }

   public List getObjects(Class var1, boolean var2) {
      List var3;
      if (var2) {
         String var4 = var1.getName();
         var4 = var4.substring(var4.lastIndexOf(".") + 1);
         XMLTreeChooser var5 = new XMLTreeChooser("Select Objects", "Class " + var4, (Component)null);
         var3 = var5.choose(this, var1);
      } else {
         XMLTree var7 = new XMLTree(this);
         var7.setHighlightedClass(var1);
         var7.selectHighlightedProperties();
         var3 = var7.getSelectedProperties();
      }

      ArrayList var8 = new ArrayList();
      Iterator var9 = var3.iterator();

      while(var9.hasNext()) {
         XMLControl var6 = (XMLControl)var9.next();
         var8.add(var6.loadObject((Object)null));
      }

      return var8;
   }

   public Object clone() {
      return new XMLControlElement(this);
   }

   private boolean isArrayOrCollectionItem() {
      XMLProperty var1 = this.getParentProperty();
      if (var1 == null) {
         return false;
      } else {
         var1 = var1.getParentProperty();
         return var1 != null && "arraycollection".indexOf(var1.getPropertyType()) >= 0;
      }
   }

   private boolean importInto(Object var1, boolean var2) {
      XMLControlElement var3 = new XMLControlElement(var1);
      Collection var4 = var3.getPropertyNames();
      var4.retainAll(this.getPropertyNames());
      ListChooser var5 = new ListChooser("Import", "Select items to import");
      if (!var2 && !var5.choose(var4, var4)) {
         return false;
      } else {
         Iterator var6 = this.getPropertyContent().iterator();

         while(var6.hasNext()) {
            XMLProperty var7 = (XMLProperty)var6.next();
            if (!var4.contains(var7.getPropertyName())) {
               var6.remove();
               this.propNames.remove(var7.getPropertyName());
            }
         }

         var6 = var3.getPropertyNames().iterator();

         while(var6.hasNext()) {
            String var9 = (String)var6.next();
            if (!var4.contains(var9)) {
               String var8 = var3.getPropertyType(var9);
               if (var8.equals("int")) {
                  this.setValue(var9, var3.getInt(var9));
               } else if (var8.equals("double")) {
                  this.setValue(var9, var3.getDouble(var9));
               } else if (var8.equals("boolean")) {
                  this.setValue(var9, var3.getBoolean(var9));
               } else if (var8.equals("string")) {
                  this.setValue(var9, var3.getString(var9));
               } else {
                  this.setValue(var9, var3.getObject(var9));
               }
            }
         }

         return true;
      }
   }

   private void setXMLProperty(String var1, String var2, Object var3) {
      int var4 = -1;
      if (this.propNames.contains(var1)) {
         Iterator var5 = this.props.iterator();

         while(var5.hasNext()) {
            ++var4;
            XMLProperty var6 = (XMLProperty)var5.next();
            if (var6.getPropertyName().equals(var1)) {
               var5.remove();
               break;
            }
         }
      } else {
         this.propNames.add(var1);
      }

      if (var4 > -1) {
         this.props.add(var4, new XMLPropertyElement(this, var1, var2, var3));
      } else {
         this.props.add(new XMLPropertyElement(this, var1, var2, var3));
      }

   }

   private XMLProperty getXMLProperty(String var1) {
      if (var1 == null) {
         return null;
      } else {
         Iterator var2 = this.getPropertyContent().iterator();

         XMLProperty var3;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            var3 = (XMLProperty)var2.next();
         } while(!var1.equals(var3.getPropertyName()));

         return var3;
      }
   }

   private void readInput() {
      this.readFailed = false;

      String var2;
      try {
         String var1 = this.input.readLine();

         while(true) {
            if (var1 == null || var1.indexOf("<object") != -1) {
               if (var1 == null) {
                  this.readFailed = true;
                  return;
               }

               int var3 = var1.indexOf("version=");
               if (var3 != -1) {
                  var2 = var1.substring(var3 + 9);
                  this.version = var2.substring(0, var2.indexOf("\""));
               }

               this.readObject(this, var1);
               break;
            }

            var1 = this.input.readLine();
         }
      } catch (Exception var5) {
         this.readFailed = true;
         OSPLog.warning("Failed to read xml: " + var5.getMessage());
         return;
      }

      if ((class$org$opensourcephysics$controls$Cryptic == null ? (class$org$opensourcephysics$controls$Cryptic = class$("org.opensourcephysics.controls.Cryptic")) : class$org$opensourcephysics$controls$Cryptic).equals(this.getObjectClass())) {
         Cryptic var6 = (Cryptic)this.loadObject((Object)null);
         var2 = var6.decrypt();
         XMLControlElement var7 = new XMLControlElement(var2);
         if (var7.failedToRead()) {
            return;
         }

         String var4 = this.password;
         this.password = var7.getString("xml_password");
         switch(this.decryptPolicy) {
         case 3:
            if (this.password != null && !this.password.equals("") && !this.password.equals(var4) && !Password.verify(this.password, (String)null)) {
               return;
            }
         default:
            this.clearValues();
            this.object = null;
            this.className = (class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object).getName();
            this.theClass = null;
            this.readXML(var2);
            break;
         case 5:
            return;
         }
      }

   }

   private XMLControlElement readObject(XMLControlElement var1, String var2) throws IOException {
      var1.clearValues();
      var2 = var2.substring(var2.indexOf("class=") + 7);
      String var3 = var2.substring(0, var2.indexOf("\""));
      int var4 = var3.lastIndexOf(".");
      if (var4 > -1) {
         String var5 = var3.substring(0, var4);
         if (var5.endsWith("org.opensourcephysics.media")) {
            var3 = var5 + ".core" + var3.substring(var4);
         }
      }

      var1.className = var3;
      if (var2.indexOf("/>") != -1) {
         this.input.readLine();
         return var1;
      } else {
         XMLControlElement var7 = var1;

         for(var2 = this.input.readLine(); var2 != null; var2 = this.input.readLine()) {
            if (var2.indexOf("</object>") != -1) {
               this.input.readLine();
               return var1;
            }

            if (var2.indexOf("<property") != -1) {
               XMLPropertyElement var6 = this.readProperty(new XMLPropertyElement(var7), var2);
               var1.props.add(var6);
               var1.propNames.add(var6.getPropertyName());
            }
         }

         return var1;
      }
   }

   private XMLPropertyElement readProperty(XMLPropertyElement var1, String var2) throws IOException {
      var1.name = var2.substring(var2.indexOf("name=") + 6, var2.indexOf("type=") - 2);
      var2 = var2.substring(var2.indexOf("type=") + 6);
      var1.type = var2.substring(0, var2.indexOf("\""));
      String var3;
      if (!var1.type.equals("array") && !var1.type.equals("collection")) {
         if (var1.type.equals("object")) {
            XMLControlElement var6 = this.readObject(new XMLControlElement(var1), this.input.readLine());
            var1.content.add(var6);
            var1.className = var6.className;
         } else {
            if (var2.indexOf("<![CDATA[") != -1) {
               for(var3 = var2.substring(var2.indexOf("<![CDATA[")); var3.indexOf("]]></property>") == -1; var3 = var3 + XML.NEW_LINE + this.input.readLine()) {
               }

               var2 = var3.substring(0, var3.indexOf("]]></property>") + "]]>".length());
            } else {
               for(var3 = var2.substring(var2.indexOf(">") + 1); var3.indexOf("</property>") == -1; var3 = var3 + XML.NEW_LINE + this.input.readLine()) {
               }

               var2 = var3.substring(0, var3.indexOf("</property>"));
            }

            var1.content.add(var2);
         }
      } else {
         var2 = var2.substring(var2.indexOf("class=") + 7);
         var3 = var2.substring(0, var2.indexOf("\""));
         int var4 = var3.lastIndexOf(".");
         if (var4 > -1) {
            String var5 = var3.substring(0, var4);
            if (var5.endsWith("org.opensourcephysics.media")) {
               var3 = var5 + ".core" + var3.substring(var4);
            }
         }

         var1.className = var3;
         if (var2.indexOf("/>") != -1) {
            return var1;
         }

         for(var2 = this.input.readLine(); var2.indexOf("<property") != -1; var2 = this.input.readLine()) {
            var1.content.add(this.readProperty(new XMLPropertyElement(var1), var2));
         }
      }

      return var1;
   }

   private String indent(int var1) {
      String var2 = "";

      for(int var3 = 0; var3 < 4 * var1; ++var3) {
         var2 = var2 + " ";
      }

      return var2;
   }

   private Object objectValue(XMLProperty var1) {
      if (!var1.getPropertyType().equals("object")) {
         return null;
      } else {
         XMLControl var2 = (XMLControl)var1.getPropertyContent().get(0);
         return var2.loadObject((Object)null);
      }
   }

   private double doubleValue(XMLProperty var1) {
      return !var1.getPropertyType().equals("double") ? Double.NaN : Double.parseDouble((String)var1.getPropertyContent().get(0));
   }

   private int intValue(XMLProperty var1) {
      return !var1.getPropertyType().equals("int") ? Integer.MIN_VALUE : Integer.parseInt((String)var1.getPropertyContent().get(0));
   }

   private boolean booleanValue(XMLProperty var1) {
      return var1.getPropertyContent().get(0).equals("true");
   }

   private String stringValue(XMLProperty var1) {
      return !var1.getPropertyType().equals("string") ? null : (String)var1.getPropertyContent().get(0);
   }

   private Object arrayValue(XMLProperty var1) {
      if (!var1.getPropertyType().equals("array")) {
         return null;
      } else {
         Class var2 = var1.getPropertyClass().getComponentType();
         List var3 = var1.getPropertyContent();
         if (var3.isEmpty()) {
            return Array.newInstance(var2, 0);
         } else {
            XMLProperty var4 = (XMLProperty)var3.get(0);
            if (var4.getPropertyName().equals("array")) {
               Object var14 = var4.getPropertyContent().get(0);
               return var14 instanceof String ? this.arrayValue((String)var14, var2) : null;
            } else {
               XMLProperty var5 = (XMLProperty)var3.get(var3.size() - 1);
               String var6 = var5.getPropertyName();
               int var7 = Integer.parseInt(var6.substring(1, var6.indexOf("]")));
               Object var8 = Array.newInstance(var2, var7 + 1);
               Iterator var9 = var3.iterator();

               while(var9.hasNext()) {
                  XMLProperty var10 = (XMLProperty)var9.next();
                  var6 = var10.getPropertyName();
                  var7 = Integer.parseInt(var6.substring(1, var6.indexOf("]")));
                  String var11 = var10.getPropertyType();
                  if (var11.equals("object")) {
                     Array.set(var8, var7, this.objectValue(var10));
                  } else if (var11.equals("int")) {
                     int var12 = this.intValue(var10);
                     if ((class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object).isAssignableFrom(var2)) {
                        Array.set(var8, var7, new Integer(var12));
                     } else {
                        Array.setInt(var8, var7, var12);
                     }
                  } else if (var11.equals("double")) {
                     double var15 = this.doubleValue(var10);
                     if ((class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object).isAssignableFrom(var2)) {
                        Array.set(var8, var7, new Double(var15));
                     } else {
                        Array.setDouble(var8, var7, var15);
                     }
                  } else if (var11.equals("boolean")) {
                     boolean var16 = this.booleanValue(var10);
                     if ((class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object).isAssignableFrom(var2)) {
                        Array.set(var8, var7, new Boolean(var16));
                     } else {
                        Array.setBoolean(var8, var7, var16);
                     }
                  } else if (var11.equals("string")) {
                     Array.set(var8, var7, this.stringValue(var10));
                  } else if (var11.equals("array")) {
                     Array.set(var8, var7, this.arrayValue(var10));
                  } else if (var11.equals("collection")) {
                     Array.set(var8, var7, this.collectionValue(var10));
                  }
               }

               return var8;
            }
         }
      }
   }

   private Object arrayValue(String var1, Class var2) {
      if (var1.startsWith("{") && var1.endsWith("}")) {
         String var3 = var1.substring(1, var1.length() - 1);
         int var8;
         if (var2.isArray()) {
            String var14 = "";
            String var16 = "";

            for(int var17 = 0; var3.substring(var17, var17 + 1).equals("{"); ++var17) {
               var14 = var14 + "{";
               var16 = var16 + "}";
            }

            ArrayList var18 = new ArrayList();
            Class var19 = var2.getComponentType();
            var8 = var3.indexOf(var14);

            for(int var9 = var3.indexOf(var16); var9 > 0; var9 = var3.indexOf(var16)) {
               String var10 = var3.substring(var8, var9 + var16.length());
               Object var11 = this.arrayValue(var10, var19);
               var18.add(var11);
               var3 = var3.substring(var9 + var16.length());
               var8 = var3.indexOf(var14);
            }

            Object var22 = Array.newInstance(var2, var18.size());
            Iterator var23 = var18.iterator();
            int var12 = 0;

            while(var23.hasNext()) {
               Object var13 = var23.next();
               Array.set(var22, var12++, var13);
            }

            return var22;
         } else {
            ArrayList var4;
            int var5;
            for(var4 = new ArrayList(); !var3.equals(""); var3 = var3.substring(var5 + 1)) {
               var5 = var3.indexOf(",");
               if (var5 <= -1) {
                  var4.add(var3);
                  break;
               }

               var4.add(var3.substring(0, var5));
            }

            Object var15 = Array.newInstance(var2, var4.size());
            Iterator var6 = var4.iterator();
            int var7 = 0;

            while(var6.hasNext()) {
               if (var2 == Integer.TYPE) {
                  var8 = Integer.parseInt((String)var6.next());
                  Array.setInt(var15, var7++, var8);
               } else if (var2 == Double.TYPE) {
                  double var20 = Double.parseDouble((String)var6.next());
                  Array.setDouble(var15, var7++, var20);
               } else if (var2 == Boolean.TYPE) {
                  boolean var21 = var6.next().equals("true");
                  Array.setBoolean(var15, var7++, var21);
               }
            }

            return var15;
         }
      } else {
         return null;
      }
   }

   private Object collectionValue(XMLProperty var1) {
      if (!var1.getPropertyType().equals("collection")) {
         return null;
      } else {
         Class var2 = var1.getPropertyClass();

         try {
            Collection var3 = (Collection)var2.newInstance();
            List var4 = var1.getPropertyContent();
            Iterator var5 = var4.iterator();

            while(var5.hasNext()) {
               XMLProperty var6 = (XMLProperty)var5.next();
               String var7 = var6.getPropertyType();
               if (var7.equals("object")) {
                  var3.add(this.objectValue(var6));
               } else if (var7.equals("string")) {
                  var3.add(this.stringValue(var6));
               } else if (var7.equals("array")) {
                  var3.add(this.arrayValue(var6));
               } else if (var7.equals("collection")) {
                  var3.add(this.collectionValue(var6));
               }
            }

            return var3;
         } catch (IllegalAccessException var8) {
         } catch (InstantiationException var9) {
         }

         return null;
      }
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
