package org.opensourcephysics.controls;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class XMLTableInspector extends JDialog implements PropertyChangeListener {
   static final String FRAME_TITLE = "Properties of";
   private XMLTable table;

   public XMLTableInspector(XMLControl var1) {
      this(var1, true, true);
   }

   public XMLTableInspector(XMLControl var1, boolean var2) {
      this(var1, var2, true);
   }

   public XMLTableInspector(XMLControl var1, boolean var2, boolean var3) {
      super((Frame)null, var3);
      this.table = new XMLTable(var1);
      this.table.setEditable(var2);
      this.table.addPropertyChangeListener("cell", this);
      this.createGUI();
      String var4 = XML.getExtension(var1.getObjectClassName());
      this.setTitle("Properties of " + var4 + " \"" + var1.getPropertyName() + "\" ");
   }

   public XMLTableInspector(boolean var1, boolean var2) {
      super((Frame)null, var2);
      this.table = new OSPControlTable(new XMLControlElement());
      this.table.setEditable(var1);
      this.table.addPropertyChangeListener("cell", this);
      this.createGUI();
      String var3 = XML.getExtension(this.getXMLControl().getObjectClassName());
      this.setTitle("Properties of " + var3 + " \"" + this.getXMLControl().getPropertyName() + "\" ");
   }

   public Control getControl() {
      return (Control)(this.table instanceof Control ? (Control)this.table : this.table.tableModel.control);
   }

   public void propertyChange(PropertyChangeEvent var1) {
      this.firePropertyChange(var1.getPropertyName(), var1.getOldValue(), var1.getNewValue());
   }

   public XMLTable getTable() {
      return this.table;
   }

   public XMLControl getXMLControl() {
      return this.table.tableModel.control;
   }

   private void createGUI() {
      this.setSize(400, 300);
      this.setContentPane(new JPanel(new BorderLayout()));
      JScrollPane var1 = new JScrollPane(this.table);
      var1.createHorizontalScrollBar();
      this.getContentPane().add(var1, "Center");
   }
}
