import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PuntosCardinalesFC extends JPanel implements ActionListener {
   Font fuente2 = new Font("Courier", 1, 12);
   Color colorTexto2;
   DibujoSistemaFC dibujoSistema;
   JButton buttonFinPC;
   DibujoPCFC dibujoPC;

   public PuntosCardinalesFC(DibujoSistemaFC var1) {
      this.colorTexto2 = Color.black;
      this.dibujoSistema = var1;
      this.setLayout((LayoutManager)null);
      this.setBounds(0, 210, 800, 250);
      this.buttonFinPC = new JButton(SistemaFC.text[1][SistemaFC.lang]);
      this.buttonFinPC.setBounds(2, 180, 140, 30);
      this.buttonFinPC.setForeground(this.colorTexto2);
      this.buttonFinPC.setFont(this.fuente2);
      this.buttonFinPC.addActionListener(this);
      this.buttonFinPC.setActionCommand("buttonFinPC");
      this.add(this.buttonFinPC);
      this.dibujoPC = new DibujoPCFC(this.dibujoSistema);
      this.add(this.dibujoPC);
   }

   public void actionPerformed(ActionEvent var1) {
      String var4 = var1.getActionCommand();
      if (var4 == "buttonFinPC") {
         SistemaFC.panelBase.remove(this);
         AnteproyectoFC var10001 = AnteproyectoFC.anteproyecto;
         SistemaFC.panelBase.add(AnteproyectoFC.panelBase);
         SistemaFC.panelBase.repaint();
      }

   }
}
