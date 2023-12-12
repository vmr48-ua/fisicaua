import java.awt.BorderLayout;
import javax.swing.JApplet;

public class Sistema extends JApplet {
   static PanelCompleto panelCompleto;
   static PanelLeyenda panelLeyenda;
   static PanelSistema panelSistema;
   static int lang = 0;
   static int ancho = 800;
   static int altoLeyenda = 32;
   static int altoDibujo = 500;
   static int altoTotal;
   static String[] teoria = new String[]{"DocA_RayCa.html", "DocA_RayEs.html", "DocA_RayEn.html"};
   static String[][] text = new String[][]{{"TRAÇAT DE RAIGS", "TRAZADO DE RAYOS", "RAY TRACING"}, {"Sistemes de focal llarga", "Sistemas de focal larga", "Long focal systems"}, {"Telescopis, microscopis, proyectors ...", "Telescopios, microscopios, proyectores ...", "Telescopes, microscopes, projection systems ..."}, {"Sistemes de focal curta", "Sistemas de focal corta", "Short focal systems"}, {"Cámara fotográfica, ull ...", "Cámara fotográfica, ojo ...", "Photographic camera, eye ..."}, {"En quant a", "Acerca de", "About"}};
   static String[][] acerca_etiqueta = new String[][]{{"Acceptar", "Aceptar", "OK"}, {"Applet de Traçat de raigs versió 1.0 \n\nGrup d'Innovació Docent en Òptica Física i Fotònica \nUniversitat de Barcelona, 2003 \nLa utilització d'aquest programa està sota una llicència de Creative Commons \nVeure condicions a http://www.publicacions.ub.es/doi/licencia/resum-deriv.htm\n\nCurs d'Òptica en Java DOI: 10.1344/401.000000050 \nApplet de Traçat de raigs DOI: 10.1344/203.000000097", "Applet de Trazado de rayos versión 1.0 \n\nGrup d'Innovació Docent en Òptica Física i Fotònica \nUniversitat de Barcelona, 2003 \nLa utilización de este programa está bajo una licencia de Creative Commons \nVer condiciones en http://creativecommons.org/license/by-nc-sa/2.0/ \n \nCurso de Óptica en Java DOI: 10.1344/401.000000050 \nApplet de Trazado de rayos DOI: 10.1344/203.000000097", "Ray tracing Applet version 1.0 \n\nGrup d'Innovació Docent en Òptica Física i Fotònica \nUniversitat de Barcelona, 2003 \nThe use of this program is under a Creative Commons license \nSee conditions in http://creativecommons.org/license/by-nc-sa/2.0/ \n \nJava Optics Course DOI: 10.1344/401.000000050 \n Ray tracing Applet DOI: 10.1344/203.000000097"}};

   public static void main(String[] var0) {
      try {
         String var1 = var0[0].toUpperCase().intern();
         if (var1 == "CA") {
            lang = 0;
         } else if (var1 == "ES") {
            lang = 1;
         } else if (var1 == "EN") {
            lang = 2;
         } else {
            lang = 0;
         }
      } catch (Exception var3) {
         lang = 0;
      }

      Marco var4 = new Marco("");
      Sistema var2 = new Sistema();
      var2.initb();
      var2.start();
      var4.getContentPane().add(var2);
      var4.setSize(ancho, altoTotal);
      var4.show();
   }

   public void init() {
      try {
         lang = Integer.valueOf(this.getParameter("llengua"));
      } catch (Exception var2) {
         lang = 0;
      }

      this.initb();
   }

   public void initb() {
      altoTotal = altoLeyenda + altoDibujo;
      this.getContentPane().setLayout(new BorderLayout());
      panelCompleto = new PanelCompleto();
      this.getContentPane().add("Center", panelCompleto);
   }

   public void start() {
      panelCompleto.setEnabled(true);
   }

   public void stop() {
      panelCompleto.setEnabled(false);
   }

   public void destroy() {
      this.remove(panelCompleto);
   }
}
