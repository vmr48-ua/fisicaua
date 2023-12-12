package org.opensourcephysics.display;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.opensourcephysics.controls.ControlFrame;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLControlElement;
import org.opensourcephysics.controls.XMLTreeChooser;
import org.opensourcephysics.controls.XMLTreePanel;
import org.opensourcephysics.tools.DatasetTool;
import org.opensourcephysics.tools.ExportTool;
import org.opensourcephysics.tools.FontSizer;
import org.opensourcephysics.tools.Job;
import org.opensourcephysics.tools.LocalJob;
import org.opensourcephysics.tools.Tool;

public class DrawingFrame extends OSPFrame implements ClipboardOwner {
   protected JMenu fileMenu;
   protected JMenu editMenu;
   protected JMenuItem copyItem;
   protected JMenuItem pasteItem;
   protected JMenuItem replaceItem;
   protected DrawingPanel drawingPanel;
   protected static final int MENU_SHORTCUT_KEY_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
   protected Window customInspector;
   protected Tool reply;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$display$Dataset;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$display$Drawable;

   public DrawingFrame() {
      this("Drawing Frame", new InteractivePanel());
   }

   public DrawingFrame(DrawingPanel var1) {
      this("Drawing Frame", var1);
   }

   public DrawingFrame(String var1, DrawingPanel var2) {
      super(var1);
      this.drawingPanel = var2;
      if (this.drawingPanel != null) {
         this.getContentPane().add(this.drawingPanel, "Center");
      }

      this.getContentPane().add(super.buttonPanel, "South");
      this.pack();
      if (!OSPFrame.appletMode) {
         this.createMenuBar();
      }

      this.reply = new Tool() {
         public void send(Job var1, Tool var2) throws RemoteException {
            XMLControlElement var3 = new XMLControlElement();

            try {
               var3.readXML(var1.getXML());
            } catch (RemoteException var11) {
            }

            ArrayList var4 = DrawingFrame.this.drawingPanel.getObjectOfClass(DrawingFrame.class$org$opensourcephysics$display$Dataset == null ? (DrawingFrame.class$org$opensourcephysics$display$Dataset = DrawingFrame.class$("org.opensourcephysics.display.Dataset")) : DrawingFrame.class$org$opensourcephysics$display$Dataset);
            Iterator var5 = var3.getObjects(DrawingFrame.class$org$opensourcephysics$display$Dataset == null ? (DrawingFrame.class$org$opensourcephysics$display$Dataset = DrawingFrame.class$("org.opensourcephysics.display.Dataset")) : DrawingFrame.class$org$opensourcephysics$display$Dataset).iterator();

            while(true) {
               while(var5.hasNext()) {
                  Dataset var6 = (Dataset)var5.next();
                  int var7 = var6.getID();
                  int var8 = 0;

                  for(int var9 = var4.size(); var8 < var9; ++var8) {
                     if (((Dataset)var4.get(var8)).getID() == var7) {
                        XMLControlElement var10 = new XMLControlElement(var6);
                        Dataset.getLoader().loadObject(var10, var4.get(var8));
                        break;
                     }
                  }
               }

               DrawingFrame.this.drawingPanel.repaint();
               return;
            }
         }
      };
   }

   public void render() {
      if (!this.isIconified() && this.isShowing()) {
         if (this.drawingPanel != null) {
            this.drawingPanel.render();
         } else {
            this.repaint();
         }

      }
   }

   public void invalidateImage() {
      if (this.drawingPanel != null) {
         this.drawingPanel.invalidateImage();
      }

   }

   public DrawingPanel getDrawingPanel() {
      return this.drawingPanel;
   }

   public void setXLabel(String var1) {
      if (this.drawingPanel instanceof PlottingPanel) {
         ((PlottingPanel)this.drawingPanel).setXLabel(var1);
      }

   }

   public void setYLabel(String var1) {
      if (this.drawingPanel instanceof PlottingPanel) {
         ((PlottingPanel)this.drawingPanel).setYLabel(var1);
      }

   }

   public void setPolar(String var1, double var2) {
      if (this.drawingPanel instanceof PlottingPanel) {
         ((PlottingPanel)this.drawingPanel).setPolar(var1, var2);
      }

   }

   public void setCartesian(String var1, String var2, String var3) {
      if (this.drawingPanel instanceof PlottingPanel) {
         ((PlottingPanel)this.drawingPanel).setCartesian(var1, var2, var3);
      }

   }

   public void limitAutoscaleX(double var1, double var3) {
      this.drawingPanel.limitAutoscaleX(var1, var3);
   }

   public void limitAutoscaleY(double var1, double var3) {
      this.drawingPanel.limitAutoscaleY(var1, var3);
   }

   public void setAutoscaleX(boolean var1) {
      if (this.drawingPanel != null) {
         this.drawingPanel.setAutoscaleX(var1);
      }

   }

   public boolean isAutoscaleX() {
      return this.drawingPanel != null ? this.drawingPanel.isAutoscaleX() : false;
   }

   public void setAutoscaleY(boolean var1) {
      if (this.drawingPanel != null) {
         this.drawingPanel.setAutoscaleY(var1);
      }

   }

   public boolean isAutoscaleY() {
      return this.drawingPanel != null ? this.drawingPanel.isAutoscaleY() : false;
   }

   public void setSquareAspect(boolean var1) {
      if (this.drawingPanel != null) {
         this.drawingPanel.setSquareAspect(var1);
      }

   }

   public void setLogScale(boolean var1, boolean var2) {
      if (this.drawingPanel != null && this.drawingPanel instanceof PlottingPanel) {
         ((PlottingPanel)this.drawingPanel).setLogScale(var1, var2);
      }

   }

   public void setPixelsPerUnit(boolean var1, double var2, double var4) {
      this.drawingPanel.setPixelsPerUnit(var1, var2, var4);
   }

   public void setPreferredMinMax(double var1, double var3, double var5, double var7) {
      if (this.drawingPanel != null) {
         this.drawingPanel.setPreferredMinMax(var1, var3, var5, var7);
      }

   }

   public void setPreferredMinMaxY(double var1, double var3) {
      if (this.drawingPanel != null) {
         this.drawingPanel.setPreferredMinMaxY(var1, var3);
      }

   }

   public void setPreferredMinMaxX(double var1, double var3) {
      if (this.drawingPanel != null) {
         this.drawingPanel.setPreferredMinMaxX(var1, var3);
      }

   }

   public void clearDataAndRepaint() {
      this.clearData();
      this.drawingPanel.repaint();
   }

   public void clearDrawables() {
      this.drawingPanel.clear();
   }

   public synchronized void addDrawable(Drawable var1) {
      if (this.drawingPanel != null) {
         this.drawingPanel.addDrawable(var1);
      }

   }

   public synchronized void replaceDrawable(Drawable var1, Drawable var2) {
      if (this.drawingPanel != null) {
         this.drawingPanel.replaceDrawable(var1, var2);
      }

   }

   public synchronized void removeDrawable(Drawable var1) {
      if (this.drawingPanel != null) {
         this.drawingPanel.removeDrawable(var1);
      }

   }

   public void setMessage(String var1) {
      this.drawingPanel.setMessage(var1);
   }

   public void setMessage(String var1, int var2) {
      this.drawingPanel.setMessage(var1, var2);
   }

   public synchronized ArrayList getObjectOfClass(Class var1) {
      return this.drawingPanel != null ? this.drawingPanel.getObjectOfClass(var1) : null;
   }

   public synchronized ArrayList getDrawables() {
      return this.drawingPanel != null ? this.drawingPanel.getDrawables() : new ArrayList();
   }

   public synchronized ArrayList getDrawables(Class var1) {
      return this.drawingPanel != null ? this.drawingPanel.getDrawables(var1) : new ArrayList();
   }

   public synchronized void removeObjectsOfClass(Class var1) {
      this.drawingPanel.removeObjectsOfClass(var1);
   }

   public void setInteractiveMouseHandler(InteractiveMouseHandler var1) {
      ((InteractivePanel)this.drawingPanel).setInteractiveMouseHandler(var1);
   }

   public void setDrawingPanel(DrawingPanel var1) {
      if (this.drawingPanel != null) {
         this.getContentPane().remove(this.drawingPanel);
      }

      this.drawingPanel = var1;
      if (this.drawingPanel != null) {
         this.getContentPane().add(this.drawingPanel, "Center");
      }

   }

   public void paint(Graphics var1) {
      if (!OSPFrame.appletMode) {
         super.paint(var1);
      } else {
         try {
            super.paint(var1);
         } catch (Exception var3) {
            System.err.println("OSPFrame paint error: " + var3.toString());
            System.err.println("Title: " + this.getTitle());
         }

      }
   }

   public void setEnabledPaste(boolean var1) {
      this.pasteItem.setEnabled(var1);
   }

   protected void pasteAction(XMLControlElement var1) {
      XMLTreeChooser var2 = new XMLTreeChooser("Select Drawables", "Select one or more drawables.", this);
      List var3 = var2.choose(var1, class$org$opensourcephysics$display$Drawable == null ? (class$org$opensourcephysics$display$Drawable = class$("org.opensourcephysics.display.Drawable")) : class$org$opensourcephysics$display$Drawable);
      if (!var3.isEmpty()) {
         Iterator var4 = var3.iterator();

         while(var4.hasNext()) {
            XMLControl var5 = (XMLControl)var4.next();
            Drawable var6 = (Drawable)var5.loadObject((Object)null);
            this.addDrawable(var6);
         }
      }

      this.drawingPanel.repaint();
   }

   public void setEnabledReplace(boolean var1) {
      this.replaceItem.setEnabled(var1);
   }

   public void replaceAction(XMLControlElement var1) {
      this.clearDrawables();
      this.pasteAction(var1);
   }

   protected void copyAction(XMLControlElement var1) {
      StringSelection var2 = new StringSelection(var1.toXML());
      Clipboard var3 = Toolkit.getDefaultToolkit().getSystemClipboard();
      var3.setContents(var2, this);
   }

   public void lostOwnership(Clipboard var1, Transferable var2) {
   }

   public void setEnabledCopy(boolean var1) {
      this.copyItem.setEnabled(var1);
   }

   private void createMenuBar() {
      JMenuBar var1 = new JMenuBar();
      this.fileMenu = new JMenu("File");
      JMenuItem var2 = new JMenuItem("Print...");
      var2.setAccelerator(KeyStroke.getKeyStroke(80, MENU_SHORTCUT_KEY_MASK));
      var2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            PrinterJob var2 = PrinterJob.getPrinterJob();
            var2.setPrintable(DrawingFrame.this.drawingPanel);
            if (var2.printDialog()) {
               try {
                  var2.print();
               } catch (PrinterException var4) {
                  JOptionPane.showMessageDialog(DrawingFrame.this, "A printing error occurred. Please try again.", "Error", 0);
               }
            }

         }
      });
      JMenuItem var3 = new JMenuItem("Save XML...");
      var3.setAccelerator(KeyStroke.getKeyStroke(83, MENU_SHORTCUT_KEY_MASK));
      var3.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DrawingFrame.this.saveXML();
         }
      });
      JMenuItem var4 = new JMenuItem("Export...");
      var4.setAccelerator(KeyStroke.getKeyStroke(69, MENU_SHORTCUT_KEY_MASK));
      var4.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            try {
               ExportTool.getTool().send(new LocalJob(DrawingFrame.this.drawingPanel), (Tool)null);
            } catch (RemoteException var3) {
            }

         }
      });
      JMenu var5 = new JMenu("Save Image");
      JMenuItem var6 = new JMenuItem("eps...");
      JMenuItem var7 = new JMenuItem("jpeg...");
      JMenuItem var8 = new JMenuItem("png...");
      var5.add(var6);
      var5.add(var7);
      var5.add(var8);
      var6.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            GUIUtils.saveImage(DrawingFrame.this.drawingPanel, (String)"eps", (Component)DrawingFrame.this);
         }
      });
      var7.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            GUIUtils.saveImage(DrawingFrame.this.drawingPanel, (String)"jpeg", (Component)DrawingFrame.this);
         }
      });
      var8.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            GUIUtils.saveImage(DrawingFrame.this.drawingPanel, (String)"png", (Component)DrawingFrame.this);
         }
      });
      JMenuItem var9 = new JMenuItem("Inspect");
      var9.setAccelerator(KeyStroke.getKeyStroke(73, MENU_SHORTCUT_KEY_MASK));
      var9.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DrawingFrame.this.inspectXML();
         }
      });
      this.fileMenu.add(var2);
      this.fileMenu.add(var3);
      this.fileMenu.add(var4);
      this.fileMenu.add(var5);
      this.fileMenu.add(var9);
      var1.add(this.fileMenu);
      this.editMenu = new JMenu("Edit");
      var1.add(this.editMenu);
      this.copyItem = new JMenuItem("Copy");
      this.copyItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            XMLControlElement var2 = new XMLControlElement(DrawingFrame.this);
            var2.saveObject((Object)null);
            DrawingFrame.this.copyAction(var2);
         }
      });
      this.editMenu.add(this.copyItem);
      this.pasteItem = new JMenuItem("Paste");
      this.pasteItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            try {
               Clipboard var2 = Toolkit.getDefaultToolkit().getSystemClipboard();
               Transferable var3 = var2.getContents((Object)null);
               XMLControlElement var4 = new XMLControlElement();
               var4.readXML((String)var3.getTransferData(DataFlavor.stringFlavor));
               DrawingFrame.this.pasteAction(var4);
            } catch (UnsupportedFlavorException var5) {
            } catch (IOException var6) {
            } catch (HeadlessException var7) {
            }

         }
      });
      this.pasteItem.setEnabled(false);
      this.editMenu.add(this.pasteItem);
      this.replaceItem = new JMenuItem("Replace");
      this.replaceItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            try {
               Clipboard var2 = Toolkit.getDefaultToolkit().getSystemClipboard();
               Transferable var3 = var2.getContents((Object)null);
               XMLControlElement var4 = new XMLControlElement();
               var4.readXML((String)var3.getTransferData(DataFlavor.stringFlavor));
               DrawingFrame.this.replaceAction(var4);
            } catch (UnsupportedFlavorException var5) {
            } catch (IOException var6) {
            } catch (HeadlessException var7) {
            }

         }
      });
      this.replaceItem.setEnabled(false);
      this.editMenu.add(this.replaceItem);
      JMenu var10 = new JMenu("Anti alias");
      this.editMenu.add(var10);
      final JCheckBoxMenuItem var11 = new JCheckBoxMenuItem("Text", false);
      var11.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DrawingFrame.this.drawingPanel.antialiasTextOn = var11.isSelected();
            DrawingFrame.this.drawingPanel.repaint();
         }
      });
      var10.add(var11);
      final JCheckBoxMenuItem var12 = new JCheckBoxMenuItem("Drawing", false);
      var12.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            DrawingFrame.this.drawingPanel.antialiasShapeOn = var12.isSelected();
            DrawingFrame.this.drawingPanel.repaint();
         }
      });
      var10.add(var12);
      JMenu var13 = new JMenu();
      var13.setText("Display");
      var1.add(var13);
      JMenuItem var14 = new JMenuItem();
      var14.setText("Increase Font Size");
      var14.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            FontSizer.levelUp();
         }
      });
      var13.add(var14);
      JMenuItem var15 = new JMenuItem();
      var15.setText("Decrease Font Size");
      var15.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            FontSizer.levelDown();
         }
      });
      var13.add(var15);
      var1.add(var13);
      this.setJMenuBar(var1);
      this.loadToolsMenu();
      JMenu var16 = new JMenu("Help");
      var1.add(var16);
      JMenuItem var17 = new JMenuItem("About OSP...");
      var17.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            ControlFrame.showAboutDialog(DrawingFrame.this);
         }
      });
      var16.add(var17);
   }

   protected void setFontLevelOLD(int var1) {
      super.setFontLevel(var1);
      if (this.drawingPanel != null) {
         Font var2 = FontSizer.getResizedFont(this.drawingPanel.trMessageBox.font, var1);
         this.drawingPanel.trMessageBox.font = var2;
         this.drawingPanel.tlMessageBox.font = var2;
         this.drawingPanel.brMessageBox.font = var2;
         this.drawingPanel.blMessageBox.font = var2;
         if (this.drawingPanel instanceof PlottingPanel) {
            PlottingPanel var3 = (PlottingPanel)this.drawingPanel;
            var3.axes.resizeFonts(FontSizer.getFactor(var1), var3);
         }

      }
   }

   protected void loadToolsMenu() {
      JMenuBar var1 = this.getJMenuBar();
      if (var1 != null) {
         JMenu var2 = new JMenu("Tools");
         var1.add(var2);
         JMenuItem var3 = new JMenuItem("Dataset Tool");
         var2.add(var3);
         var3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               try {
                  DatasetTool var2 = DatasetTool.getTool();
                  var2.send(new LocalJob(DrawingFrame.this.drawingPanel), DrawingFrame.this.reply);
                  var2.setVisible(true);
               } catch (RemoteException var3) {
               }

            }
         });
      }
   }

   public void setCustomInspector(Window var1) {
      if (this.customInspector != null) {
         this.customInspector.setVisible(false);
      }

      this.customInspector = var1;
   }

   public void inspectXML() {
      if (this.customInspector != null) {
         this.customInspector.setVisible(true);
      } else {
         XMLControlElement var1 = null;

         try {
            Method var2 = this.drawingPanel.getClass().getMethod("getLoader", (Class[])null);
            if (var2 != null && Modifier.isStatic(var2.getModifiers())) {
               var1 = new XMLControlElement(this.drawingPanel);
            }
         } catch (NoSuchMethodException var4) {
            return;
         }

         XMLTreePanel var5 = new XMLTreePanel(var1);
         JDialog var3 = new JDialog((Frame)null, true);
         var3.setContentPane(var5);
         var3.setSize(new Dimension(600, 300));
         var3.setVisible(true);
      }
   }

   public void saveXML() {
      JFileChooser var1 = OSPFrame.getChooser();
      int var2 = var1.showSaveDialog((Component)null);
      if (var2 == 0) {
         OSPFrame.chooserDir = var1.getCurrentDirectory().toString();
         File var3 = var1.getSelectedFile();
         if (var3.exists()) {
            int var4 = JOptionPane.showConfirmDialog((Component)null, "Replace existing " + var3.getName() + "?", "Replace File", 1);
            if (var4 != 0) {
               return;
            }
         }

         String var9 = XML.getRelativePath(var3.getAbsolutePath());
         if (var9 == null || var9.trim().equals("")) {
            return;
         }

         int var5 = var9.toLowerCase().lastIndexOf(".xml");
         if (var5 != var9.length() - 4) {
            var9 = var9 + ".xml";
         }

         try {
            Method var6 = this.drawingPanel.getClass().getMethod("getLoader", (Class[])null);
            if (var6 != null && Modifier.isStatic(var6.getModifiers())) {
               XMLControlElement var7 = new XMLControlElement(this.drawingPanel);
               var7.write(var9);
            }
         } catch (NoSuchMethodException var8) {
            return;
         }
      }

   }

   public static XML.ObjectLoader getLoader() {
      return new DrawingFrame.DrawingFrameLoader();
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   protected static class DrawingFrameLoader implements XML.ObjectLoader {
      public Object createObject(XMLControl var1) {
         DrawingFrame var2 = new DrawingFrame();
         var2.setTitle(var1.getString("title"));
         var2.setLocation(var1.getInt("location x"), var1.getInt("location y"));
         var2.setSize(var1.getInt("width"), var1.getInt("height"));
         if (var1.getBoolean("showing")) {
            var2.setVisible(true);
         }

         return var2;
      }

      public void saveObject(XMLControl var1, Object var2) {
         DrawingFrame var3 = (DrawingFrame)var2;
         var1.setValue("title", var3.getTitle());
         var1.setValue("showing", var3.isShowing());
         var1.setValue("location x", var3.getLocation().x);
         var1.setValue("location y", var3.getLocation().y);
         var1.setValue("width", var3.getSize().width);
         var1.setValue("height", var3.getSize().height);
         var1.setValue("drawing panel", var3.getDrawingPanel());
      }

      public Object loadObject(XMLControl var1, Object var2) {
         DrawingFrame var3 = (DrawingFrame)var2;
         DrawingPanel var4 = var3.getDrawingPanel();
         var4.clear();
         XMLControl var5 = var1.getChildControl("drawing panel");
         var5.loadObject(var4);
         var4.repaint();
         var3.setTitle(var1.getString("title"));
         var3.setLocation(var1.getInt("location x"), var1.getInt("location y"));
         var3.setSize(var1.getInt("width"), var1.getInt("height"));
         if (var1.getBoolean("showing")) {
            var3.setVisible(true);
         }

         return var2;
      }
   }
}
