public class FuncionesCardinalesFC {
   static int tablaTramosCard() {
      int var0;
      for(var0 = 0; var0 <= AnteproyectoFC.numElementos; ++var0) {
         AnteproyectoFC.tramoCard[var0] = AnteproyectoFC.elemento[var0];
      }

      AnteproyectoFC.numTramosCard = AnteproyectoFC.numElementos + 1;
      AnteproyectoFC.tramoCard[var0] = 7;
      return AnteproyectoFC.numTramosCard < 2 ? 0 : 1;
   }

   static int marchaParaxialCard(int var0) {
      int var5 = AnteproyectoFC.numElementos;
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
            AnteproyectoFC.aRayoCard[var0][0] = 0.0D;
            AnteproyectoFC.bRayoCard[var0][0] = 10.0D;
            AnteproyectoFC.apRayoCard[var0][0] = 0.0D;
            AnteproyectoFC.bpRayoCard[var0][0] = 10.0D;

            for(var4 = 1; var4 <= var5 + 1; ++var4) {
               var1 = AnteproyectoFC.elemento[var4];
               int var2 = AnteproyectoFC.elemento[var4 - 1];
               var18 = AnteproyectoFC.apRayoCard[var0][var2];
               var24 = AnteproyectoFC.bpRayoCard[var0][var2];
               if (var18 == 0.0D) {
                  var8 = 1.0E10D;
               } else {
                  var8 = -var24 / var18;
               }

               var6 = SistemaFC.valZE[var1];
               var22 = var18 * var6 + var24;
               AnteproyectoFC.fRayoCard[var0][var1] = Math.abs(var22) / SistemaFC.valDE[var1];
               var12 = var8 - var6;
               if (SistemaFC.tipoE[var1] == 0) {
                  var20 = var18;
                  var26 = var24;
               } else {
                  var14 = 1000.0D / SistemaFC.valPE[var1];
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

               AnteproyectoFC.aRayoCard[var0][var1] = var18;
               AnteproyectoFC.apRayoCard[var0][var1] = var20;
               AnteproyectoFC.bRayoCard[var0][var1] = var24;
               AnteproyectoFC.bpRayoCard[var0][var1] = var26;
            }

            AnteproyectoFC.zF[var0] = -AnteproyectoFC.bRayoCard[var0][7] / AnteproyectoFC.aRayoCard[var0][7];
            AnteproyectoFC.zH[var0] = -(AnteproyectoFC.bRayoCard[var0][7] - AnteproyectoFC.bpRayoCard[var0][0]) / (AnteproyectoFC.aRayoCard[var0][7] - AnteproyectoFC.apRayoCard[var0][0]);
         } else {
            AnteproyectoFC.aRayoCard[var0][7] = 0.0D;
            AnteproyectoFC.bRayoCard[var0][7] = 10.0D;
            AnteproyectoFC.apRayoCard[var0][7] = 0.0D;
            AnteproyectoFC.bpRayoCard[var0][7] = 10.0D;

            for(var4 = var5; var4 >= 0; --var4) {
               var1 = AnteproyectoFC.elemento[var4];
               int var3 = AnteproyectoFC.elemento[var4 + 1];
               var20 = AnteproyectoFC.aRayoCard[var0][var3];
               var26 = AnteproyectoFC.bRayoCard[var0][var3];
               if (var20 == 0.0D) {
                  var10 = 1.0E10D;
               } else {
                  var10 = -var26 / var20;
               }

               var6 = SistemaFC.valZE[var1];
               var22 = var20 * var6 + var26;
               AnteproyectoFC.fRayoCard[var0][var1] = Math.abs(var22) / SistemaFC.valDE[var1];
               var16 = var10 - var6;
               if (SistemaFC.tipoE[var1] == 0) {
                  var18 = var20;
                  var24 = var26;
               } else {
                  var14 = 1000.0D / SistemaFC.valPE[var1];
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

               AnteproyectoFC.aRayoCard[var0][var1] = var18;
               AnteproyectoFC.apRayoCard[var0][var1] = var20;
               AnteproyectoFC.bRayoCard[var0][var1] = var24;
               AnteproyectoFC.bpRayoCard[var0][var1] = var26;
            }

            AnteproyectoFC.zF[var0] = -AnteproyectoFC.bpRayoCard[var0][0] / AnteproyectoFC.apRayoCard[var0][0];
            AnteproyectoFC.zH[var0] = -(AnteproyectoFC.bRayoCard[var0][7] - AnteproyectoFC.bpRayoCard[var0][0]) / (AnteproyectoFC.aRayoCard[var0][7] - AnteproyectoFC.apRayoCard[var0][0]);
         }

         return 1;
      }
   }

   static int normalizaCoeficientes() {
      int var2 = AnteproyectoFC.numElementos;
      double var4 = 0.0D;

      int var0;
      int var1;
      int var3;
      for(var3 = 0; var3 < 2; ++var3) {
         for(var1 = 1; var1 <= var2; ++var1) {
            var0 = AnteproyectoFC.elemento[var1];
            if (var4 < AnteproyectoFC.fRayoCard[var3][var0]) {
               var4 = AnteproyectoFC.fRayoCard[var3][var0];
            }
         }
      }

      var4 *= 2.0D;

      for(var3 = 0; var3 < 2; ++var3) {
         for(var1 = 0; var1 <= var2 + 1; ++var1) {
            var0 = AnteproyectoFC.elemento[var1];
            AnteproyectoFC.aRayoCard[var3][var0] /= var4;
            AnteproyectoFC.bRayoCard[var3][var0] /= var4;
            AnteproyectoFC.apRayoCard[var3][var0] /= var4;
            AnteproyectoFC.bpRayoCard[var3][var0] /= var4;
         }
      }

      return 1;
   }

   static int interseccionesRayosCard(int var0) {
      int var5 = AnteproyectoFC.numTramosCard;

      for(int var4 = 0; var4 <= var5; ++var4) {
         int var1 = AnteproyectoFC.tramoCard[var4];
         int var2 = AnteproyectoFC.tramoCard[var4 + 1];
         double var8 = AnteproyectoFC.apRayoCard[var0][var1];
         double var10 = AnteproyectoFC.bpRayoCard[var0][var1];
         double var6 = SistemaFC.valZE[var1];
         double var12 = SistemaFC.valZE[var2];
         if (var1 == 0) {
            AnteproyectoFC.zpRayoCard[var0][0] = var6;
            AnteproyectoFC.ypRayoCard[var0][0] = AnteproyectoFC.bpRayoCard[var0][0];
         } else {
            Caja.doble1 = var8;
            Caja.doble2 = var10;
            Caja.doble3 = var6 + SistemaFC.espesor[var1] / 2.0D;
            Caja.doble4 = -SistemaFC.radioDib[var1];
            FuncionesParaxialesFC.rectaEsfera();
            AnteproyectoFC.zpRayoCard[var0][var1] = Caja.doble1;
            AnteproyectoFC.ypRayoCard[var0][var1] = Caja.doble2;
         }

         if (var2 == 7) {
            AnteproyectoFC.zaRayoCard[var0][var2] = 1600.0D;
            AnteproyectoFC.yaRayoCard[var0][var2] = AnteproyectoFC.aRayoCard[var0][var2] * 1600.0D + AnteproyectoFC.bRayoCard[var0][var2];
         } else {
            Caja.doble1 = var8;
            Caja.doble2 = var10;
            Caja.doble3 = var12 - SistemaFC.espesor[var2] / 2.0D;
            Caja.doble4 = SistemaFC.radioDib[var2];
            FuncionesParaxialesFC.rectaEsfera();
            AnteproyectoFC.zaRayoCard[var0][var2] = Caja.doble1;
            AnteproyectoFC.yaRayoCard[var0][var2] = Caja.doble2;
         }
      }

      return 1;
   }
}
