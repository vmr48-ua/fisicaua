package local.dialogos;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OptDialogos {
   static String s = "";

   public static String optPreguntas(Frame parent, String[] pregTexto, String[] respTexto) {
      Font fuente = new Font("Dialog", 1, 12);

      class OptPreguntas extends Dialog {
         Label[] pregunta;
         TextField[] respuesta;
         int pregLines;
         int numLetras = 0;
         int ancho;
         Font fuente;

         public OptPreguntas(Frame parent, String[] pregTexto, String[] respTexto) {
            super(parent, " ", true);
            this.setLayout((LayoutManager)null);
            this.pregLines = pregTexto.length;

            int i;
            for(i = 0; i < this.pregLines; ++i) {
               int j = pregTexto[i].length();
               if (j > this.numLetras) {
                  this.numLetras = j;
               }
            }

            this.ancho = this.numLetras * 9;
            if (this.ancho < 130) {
               this.ancho = 130;
            }

            this.pregunta = new Label[this.pregLines];
            this.respuesta = new TextField[this.pregLines];

            for(i = 0; i < this.pregLines; ++i) {
               this.pregunta[i] = new Label(pregTexto[i]);
               this.pregunta[i].setBounds(30, 40 + i * 40, this.ancho, 30);
               this.add(this.pregunta[i]);
               this.respuesta[i] = new TextField(respTexto[i], 10);
               this.respuesta[i].setBounds(this.ancho + 60, 40 + i * 40, 150, 30);
               this.add(this.respuesta[i]);
            }

            Button aceptar = new Button("D'acord");

            class DialogHandler extends WindowAdapter implements ActionListener {
               public void actionPerformed(ActionEvent e) {
                  OptDialogos.s = e.getActionCommand();
                  OptPreguntas.this.hide();
               }
            }

            aceptar.addActionListener(new DialogHandler());
            aceptar.setBounds(30, this.pregLines * 40 + 50, 90, 30);
            this.add(aceptar);
            Button cancelar = new Button("Cancel");
            cancelar.addActionListener(new DialogHandler());
            cancelar.setBounds(this.ancho + 20, this.pregLines * 40 + 50, 90, 30);
            this.add(cancelar);
         }
      }

      OptPreguntas optPreguntas = new OptPreguntas(parent, pregTexto, respTexto);
      int pregLines = pregTexto.length;
      optPreguntas.setSize(optPreguntas.ancho + 240, (pregLines + 2) * 40 + 20);
      optPreguntas.setFont(fuente);
      optPreguntas.setLocation(400, 100);
      optPreguntas.show();
      if (s == "D'acord") {
         for(int i = 0; i < pregLines; ++i) {
            respTexto[i] = optPreguntas.respuesta[i].getText();
         }
      }

      return s;
   }

   public static boolean optPreguntaSiNo(Frame parent, String pregTexto) {
      Font fuente = new Font("Dialog", 1, 12);

      class OptPreguntaSiNo extends Dialog {
         int numLetras;
         int ancho;

         public OptPreguntaSiNo(Frame parent, String pregTexto) {
            super(parent, " ", true);
            this.setLayout((LayoutManager)null);
            this.numLetras = pregTexto.length();
            this.ancho = this.numLetras * 9;
            if (this.ancho < 170) {
               this.ancho = 170;
            }

            Label pregunta = new Label(pregTexto);
            pregunta.setBounds(30, 40, this.ancho, 30);
            this.add(pregunta);
            Button aceptar = new Button("Si");

            class DialogHandler extends WindowAdapter implements ActionListener {
               public void actionPerformed(ActionEvent e) {
                  OptDialogos.s = e.getActionCommand();
                  OptPreguntaSiNo.this.hide();
               }
            }

            aceptar.addActionListener(new DialogHandler());
            aceptar.setBounds(30, 90, 50, 30);
            this.add(aceptar);
            Button cancelar = new Button("No");
            cancelar.addActionListener(new DialogHandler());
            cancelar.setBounds(this.ancho - 20, 90, 50, 30);
            this.add(cancelar);
         }
      }

      OptPreguntaSiNo optPreguntaSiNo = new OptPreguntaSiNo(parent, pregTexto);
      optPreguntaSiNo.setSize(optPreguntaSiNo.ancho + 60, 140);
      optPreguntaSiNo.setFont(fuente);
      optPreguntaSiNo.setLocation(400, 100);
      optPreguntaSiNo.show();
      return s == "Si";
   }

   public static void optEscribeFlash(Frame parent, String pregTexto) {
      Font fuente = new Font("Dialog", 1, 12);

      class OptEscribeFlash extends Dialog {
         int numLetras;
         int ancho;

         public OptEscribeFlash(Frame parent, String pregTexto) {
            super(parent, " ", true);
            this.setLayout((LayoutManager)null);
            this.numLetras = pregTexto.length();
            this.ancho = this.numLetras * 9;
            if (this.ancho < 100) {
               this.ancho = 100;
            }

            class WindowEventHandler extends WindowAdapter {
               public void windowClosing(WindowEvent e) {
                  OptEscribeFlash.this.hide();
               }
            }

            this.addWindowListener(new WindowEventHandler());
            Label mensaje = new Label(pregTexto);
            mensaje.setBounds(50, 40, this.ancho, 30);
            this.add(mensaje);
            Button aceptar = new Button("D'acord");

            class DialogHandler extends WindowAdapter implements ActionListener {
               public void actionPerformed(ActionEvent e) {
                  OptDialogos.s = e.getActionCommand();
                  OptEscribeFlash.this.hide();
               }
            }

            aceptar.addActionListener(new DialogHandler());
            aceptar.setBounds(50, 90, 80, 30);
            this.add(aceptar);
         }
      }

      OptEscribeFlash optEscribeFlash = new OptEscribeFlash(parent, pregTexto);
      optEscribeFlash.setSize(optEscribeFlash.ancho + 50, 160);
      optEscribeFlash.setFont(fuente);
      optEscribeFlash.setLocation(100, 100);
      optEscribeFlash.show();

      for(int i = 0; i < 1000; ++i) {
         Math.sqrt((double)i);
      }

      optEscribeFlash.hide();
   }
}
