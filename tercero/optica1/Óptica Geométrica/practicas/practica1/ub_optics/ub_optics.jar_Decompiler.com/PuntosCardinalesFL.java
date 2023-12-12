import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PuntosCardinalesFL extends JPanel implements ActionListener {
   Font fuente2 = new Font("Courier", 1, 12);
   Color colorTexto2;
   DibujoSistemaFL dibujoSistema;
   JButton buttonFinPC;
   DibujoPCFL dibujoPC;

   public PuntosCardinalesFL(DibujoSistemaFL var1) {
      this.colorTexto2 = Color.black;
      this.dibujoSistema = var1;
      this.setLayout((LayoutManager)null);
      this.setBounds(0, 210, 800, 250);
      this.buttonFinPC = new JButton(SistemaFL.text[1][SistemaFL.lang]);
      this.buttonFinPC.setBounds(2, 180, 140, 30);
      this.buttonFinPC.setForeground(this.colorTexto2);
      this.buttonFinPC.setFont(this.fuente2);
      this.buttonFinPC.addActionListener(this);
      this.buttonFinPC.setActionCommand("buttonFinPC");
      this.add(this.buttonFinPC);
      this.dibujoPC = new DibujoPCFL(this.dibujoSistema);
      this.add(this.dibujoPC);
   }

   public void actionPerformed(ActionEvent var1) {
      String var4 = var1.getActionCommand();
      if (var4 == "buttonFinPC") {
         SistemaFL.panelBase.remove(this);
         AnteproyectoFL var10001 = AnteproyectoFL.anteproyecto;
         SistemaFL.panelBase.add(AnteproyectoFL.panelBase);
         SistemaFL.panelBase.repaint();
      }

   }
}
