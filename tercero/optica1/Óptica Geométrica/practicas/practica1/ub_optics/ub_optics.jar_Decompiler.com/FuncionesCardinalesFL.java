public class FuncionesCardinalesFL {
   static int tablaTramosCard() {
      int var0;
      for(var0 = 0; var0 <= AnteproyectoFL.numElementos; ++var0) {
         AnteproyectoFL.tramoCard[var0] = AnteproyectoFL.elemento[var0];
      }

      AnteproyectoFL.numTramosCard = AnteproyectoFL.numElementos + 1;
      AnteproyectoFL.tramoCard[var0] = 7;
      return AnteproyectoFL.numTramosCard < 2 ? 0 : 1;
   }

   static int marchaParaxialCard(int var0) {
      int var5 = AnteproyectoFL.numElementos;
      if (var5 < 1) {
         return 0;
      } else {
         int var1;
         int var4;
         double var6;
         double var8;
         double var10;
         double var12;
         double var14;
         double var16;
         double var18;
         double var20;
         double var22;
         double var24;
         double var26;
         if (var0 == 0) {
            AnteproyectoFL.aRayoCard[var0][0] = 0.0D;
            AnteproyectoFL.bRayoCard[var0][0] = 10.0D;
            AnteproyectoFL.apRayoCard[var0][0] = 0.0D;
            AnteproyectoFL.bpRayoCard[var0][0] = 10.0D;

            for(var4 = 1; var4 <= var5 + 1; ++var4) {
               var1 = AnteproyectoFL.elemento[var4];
               int var2 = AnteproyectoFL.elemento[var4 - 1];
               var18 = AnteproyectoFL.apRayoCard[var0][var2];
               var24 = AnteproyectoFL.bpRayoCard[var0][var2];
               if (var18 == 0.0D) {
                  var8 = 1.0E10D;
               } else {
                  var8 = -var24 / var18;
               }

               var6 = SistemaFL.valZE[var1];
               var22 = var18 * var6 + var24;
               AnteproyectoFL.fRayoCard[var0][var1] = Math.abs(var22) / SistemaFL.valDE[var1];
               var12 = var8 - var6;
               if (SistemaFL.tipoE[var1] == 0) {
                  var20 = var18;
                  var26 = var24;
               } else {
                  var14 = 1000.0D / SistemaFL.valPE[var1];
                  if (var12 == -var14) {
                     var16 = 1.0E10D;
                  } else {
                     var16 = var12 * var14 / (var14 + var12);
                  }

                  var10 = var6 + var16;
                  if (var16 == 0.0D) {
                     var20 = 1.0E10D;
                  } else {
                     var20 = -var22 / var16;
                  }

                  var26 = -var20 * var10;
               }

               AnteproyectoFL.aRayoCard[var0][var1] = var18;
               AnteproyectoFL.apRayoCard[var0][var1] = var20;
               AnteproyectoFL.bRayoCard[var0][var1] = var24;
               AnteproyectoFL.bpRayoCard[var0][var1] = var26;
            }

            AnteproyectoFL.zF[var0] = -AnteproyectoFL.bRayoCard[var0][7] / AnteproyectoFL.aRayoCard[var0][7];
            AnteproyectoFL.zH[var0] = -(AnteproyectoFL.bRayoCard[var0][7] - AnteproyectoFL.bpRayoCard[var0][0]) / (AnteproyectoFL.aRayoCard[var0][7] - AnteproyectoFL.apRayoCard[var0][0]);
         } else {
            AnteproyectoFL.aRayoCard[var0][7] = 0.0D;
            AnteproyectoFL.bRayoCard[var0][7] = 10.0D;
            AnteproyectoFL.apRayoCard[var0][7] = 0.0D;
            AnteproyectoFL.bpRayoCard[var0][7] = 10.0D;

            for(var4 = var5; var4 >= 0; --var4) {
               var1 = AnteproyectoFL.elemento[var4];
               int var3 = AnteproyectoFL.elemento[var4 + 1];
               var20 = AnteproyectoFL.aRayoCard[var0][var3];
               var26 = AnteproyectoFL.bRayoCard[var0][var3];
               if (var20 == 0.0D) {
                  var10 = 1.0E10D;
               } else {
                  var10 = -var26 / var20;
               }

               var6 = SistemaFL.valZE[var1];
               var22 = var20 * var6 + var26;
               AnteproyectoFL.fRayoCard[var0][var1] = Math.abs(var22) / SistemaFL.valDE[var1];
               var16 = var10 - var6;
               if (SistemaFL.tipoE[var1] == 0) {
                  var18 = var20;
                  var24 = var26;
               } else {
                  var14 = 1000.0D / SistemaFL.valPE[var1];
                  if (var16 == var14) {
                     var12 = 1.0E10D;
                  } else {
                     var12 = var16 * var14 / (var14 - var16);
                  }

                  var8 = var6 + var12;
                  if (var12 == 0.0D) {
                     var18 = 1.0E10D;
                  } else {
                     var18 = -var22 / var12;
                  }

                  var24 = -var18 * var8;
               }

               AnteproyectoFL.aRayoCard[var0][var1] = var18;
               AnteproyectoFL.apRayoCard[var0][var1] = var20;
               AnteproyectoFL.bRayoCard[var0][var1] = var24;
               AnteproyectoFL.bpRayoCard[var0][var1] = var26;
            }

            AnteproyectoFL.zF[var0] = -AnteproyectoFL.bpRayoCard[var0][0] / AnteproyectoFL.apRayoCard[var0][0];
            AnteproyectoFL.zH[var0] = -(AnteproyectoFL.bRayoCard[var0][7] - AnteproyectoFL.bpRayoCard[var0][0]) / (AnteproyectoFL.aRayoCard[var0][7] - AnteproyectoFL.apRayoCard[var0][0]);
         }

         return 1;
      }
   }

   static int normalizaCoeficientes() {
      int var2 = AnteproyectoFL.numElementos;
      double var4 = 0.0D;

      int var0;
      int var1;
      int var3;
      for(var3 = 0; var3 < 2; ++var3) {
         for(var1 = 1; var1 <= var2; ++var1) {
            var0 = AnteproyectoFL.elemento[var1];
            if (var4 < AnteproyectoFL.fRayoCard[var3][var0]) {
               var4 = AnteproyectoFL.fRayoCard[var3][var0];
            }
         }
      }

      var4 *= 2.0D;

      for(var3 = 0; var3 < 2; ++var3) {
         for(var1 = 0; var1 <= var2 + 1; ++var1) {
            var0 = AnteproyectoFL.elemento[var1];
            AnteproyectoFL.aRayoCard[var3][var0] /= var4;
            AnteproyectoFL.bRayoCard[var3][var0] /= var4;
            AnteproyectoFL.apRayoCard[var3][var0] /= var4;
            AnteproyectoFL.bpRayoCard[var3][var0] /= var4;
         }
      }

      return 1;
   }

   static int interseccionesRayosCard(int var0) {
      int var5 = AnteproyectoFL.numTramosCard;

      for(int var4 = 0; var4 <= var5; ++var4) {
         int var1 = AnteproyectoFL.tramoCard[var4];
         int var2 = AnteproyectoFL.tramoCard[var4 + 1];
         double var8 = AnteproyectoFL.apRayoCard[var0][var1];
         double var10 = AnteproyectoFL.bpRayoCard[var0][var1];
         double var6 = SistemaFL.valZE[var1];
         double var12 = SistemaFL.valZE[var2];
         if (var1 == 0) {
            AnteproyectoFL.zpRayoCard[var0][0] = var6;
            AnteproyectoFL.ypRayoCard[var0][0] = AnteproyectoFL.bpRayoCard[var0][0];
         } else {
            Caja.doble1 = var8;
            Caja.doble2 = var10;
            Caja.doble3 = var6 + SistemaFL.espesor[var1] / 2.0D;
            Caja.doble4 = -SistemaFL.radioDib[var1];
            FuncionesParaxialesFL.rectaEsfera();
            AnteproyectoFL.zpRayoCard[var0][var1] = Caja.doble1;
            AnteproyectoFL.ypRayoCard[var0][var1] = Caja.doble2;
         }

         if (var2 == 7) {
            AnteproyectoFL.zaRayoCard[var0][var2] = 1600.0D;
            AnteproyectoFL.yaRayoCard[var0][var2] = AnteproyectoFL.aRayoCard[var0][var2] * 1600.0D + AnteproyectoFL.bRayoCard[var0][var2];
         } else {
            Caja.doble1 = var8;
            Caja.doble2 = var10;
            Caja.doble3 = var12 - SistemaFL.espesor[var2] / 2.0D;
            Caja.doble4 = SistemaFL.radioDib[var2];
            FuncionesParaxialesFL.rectaEsfera();
            AnteproyectoFL.zaRayoCard[var0][var2] = Caja.doble1;
            AnteproyectoFL.yaRayoCard[var0][var2] = Caja.doble2;
         }
      }

      return 1;
   }
}
