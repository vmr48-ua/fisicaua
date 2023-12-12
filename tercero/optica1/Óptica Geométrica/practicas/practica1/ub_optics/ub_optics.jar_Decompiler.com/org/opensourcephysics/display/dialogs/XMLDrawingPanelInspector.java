package org.opensourcephysics.display.dialogs;

import java.awt.Dimension;
import javax.swing.JDialog;
import org.opensourcephysics.controls.XMLControlElement;
import org.opensourcephysics.controls.XMLTreePanel;
import org.opensourcephysics.display.DrawingPanel;

public class XMLDrawingPanelInspector extends JDialog {
   static XMLDrawingPanelInspector inspector;

   public XMLDrawingPanelInspector() {
      this.setSize(new Dimension(600, 300));
   }

   public static XMLDrawingPanelInspector getInspector(DrawingPanel var0) {
      if (inspector == null) {
         inspector = new XMLDrawingPanelInspector();
      }

      XMLControlElement var1 = new XMLControlElement(var0);
      XMLTreePanel var2 = new XMLTreePanel(var1);
      inspector.setContentPane(var2);
      inspector.setVisible(true);
      return inspector;
   }
}
