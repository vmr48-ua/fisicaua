package optfourier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

class Dialogos$1OptPreguntas$1DialogHandler extends WindowAdapter implements ActionListener {
   // $FF: synthetic field
   private final OptPreguntas this$0;

   Dialogos$1OptPreguntas$1DialogHandler(OptPreguntas var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().equals(this.this$0.ok[this.this$0.lang])) {
         Dialogos.s = true;
      }

      if (e.getActionCommand().equals(this.this$0.cancel[this.this$0.lang])) {
         Dialogos.s = false;
      }

      this.this$0.setVisible(false);
   }
}
