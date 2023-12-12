public class FuncionesParaxialesFC {
   static int tablaElementos() {
      int var0 = 0;
      AnteproyectoFC.elemento[0] = 0;
      AnteproyectoFC.numUltimaLente = 0;
      AnteproyectoFC.numUltimoElemento = 0;

      for(int var1 = 1; var1 < 7; ++var1) {
         if (SistemaFC.existe[var1] == 1) {
            AnteproyectoFC.numUltimoElemento = var1;
            ++var0;
            AnteproyectoFC.elemento[var0] = var1;
         }

         if (SistemaFC.existe[var1] == 1 && SistemaFC.tipoE[var1] == 1) {
            AnteproyectoFC.numUltimaLente = var1;
         }
      }

      AnteproyectoFC.numElementos = var0;
      AnteproyectoFC.elemento[var0 + 1] = 7;
      if (var0 < 1) {
         return 0;
      } else {
         return 1;
      }
   }

   static int tablaTramos(int var0) {
      int var2 = 0;
      int var1;
      if (SistemaFC.real[var0] != 0 && !(SistemaFC.zObjeto[var0] >= 1.0E10D)) {
         AnteproyectoFC.tramo[var0][0] = 0;

         for(var1 = 1; var1 <= AnteproyectoFC.numElementos; ++var1) {
            int var3 = AnteproyectoFC.elemento[var1];
            if (!(SistemaFC.zObjeto[var0] >= SistemaFC.valZE[var3])) {
               ++var2;
               AnteproyectoFC.tramo[var0][var2] = AnteproyectoFC.elemento[var1];
            }
         }

         AnteproyectoFC.numTramos[var0] = var2 + 1;
         AnteproyectoFC.tramo[var0][var2 + 1] = 7;
      } else {
         for(var1 = 0; var1 <= AnteproyectoFC.numElementos; ++var1) {
            AnteproyectoFC.tramo[var0][var1] = AnteproyectoFC.elemento[var1];
         }

         AnteproyectoFC.numTramos[var0] = AnteproyectoFC.numElementos + 1;
         AnteproyectoFC.tramo[var0][var1] = 7;
      }

      return AnteproyectoFC.numTramos[var0] < 2 ? 0 : 1;
   }

   static int marchaParaxial(int var0) {
      int var4 = AnteproyectoFC.numTramos[var0];
      if (var4 < 1) {
         return 0;
      } else if (SistemaFC.existeObjeto[var0] == 0) {
         return 0;
      } else {
         double var11 = SistemaFC.valZE[AnteproyectoFC.tramo[var0][1]];
         double var19;
         AnteproyectoFC.zImag[var0][0] = var19 = SistemaFC.zObjeto[var0];
         AnteproyectoFC.yImag[var0][0] = SistemaFC.valYObj[var0] = (var11 - var19) * SistemaFC.campoDib[var0] * 3.141592D / 180.0D / 8.0D;

         for(int var3 = 1; var3 <= var4; ++var3) {
            int var1 = AnteproyectoFC.tramo[var0][var3];
            int var2 = AnteproyectoFC.tramo[var0][var3 - 1];
            if (SistemaFC.tipoE[var1] == 0) {
               AnteproyectoFC.zImag[var0][var1] = AnteproyectoFC.zImag[var0][var2];
               AnteproyectoFC.yImag[var0][var1] = AnteproyectoFC.yImag[var0][var2];
            } else {
               double var5 = AnteproyectoFC.zImag[var0][var2];
               double var7 = AnteproyectoFC.yImag[var0][var2];
               double var13 = var5 - SistemaFC.valZE[var1];
               double var15 = 1000.0D / SistemaFC.valPE[var1];
               double var9;
               double var17;
               if (var13 == -var15) {
                  var17 = 1.0E10D;
                  var9 = var7 * var17 / var13;
               } else {
                  var17 = var13 * var15 / (var13 + var15);
                  var9 = var7 * var15 / (var13 + var15);
               }

               AnteproyectoFC.zImag[var0][var1] = ExactoFC.valZParax = SistemaFC.valZE[var1] + var17;
               AnteproyectoFC.yImag[var0][var1] = var9;
            }
         }

         AnteproyectoFC.zImag[var0][7] = 1600.0D;
         return 1;
      }
   }

   static int preparapRayos(int var0) {
      boolean var3 = true;
      double var27 = 0.5D;
      double var7 = AnteproyectoFC.zImag[var0][0];
      double var9 = AnteproyectoFC.yImag[var0][0];
      int var29 = AnteproyectoFC.tramo[var0][1];
      if (var29 > 6) {
         AnteproyectoFC.pintarRayos[var0] = 0;
         return 0;
      } else {
         double var11 = SistemaFC.valZE[var29];
         int var4 = SistemaFC.tipoE[var29];
         double var5 = SistemaFC.valPE[var29];
         double var15 = SistemaFC.valDE[var29] / 2.0D;
         if (var15 == 0.0D) {
            AnteproyectoFC.pintarRayos[var0] = 0;
            return 0;
         } else if (var5 == 0.0D && var4 == 1) {
            AnteproyectoFC.pintarRayos[var0] = 0;
            return 0;
         } else if (var15 == (double)AnteproyectoFC.diamMax / 2.0D && var4 == 0) {
            AnteproyectoFC.pintarRayos[var0] = 0;
            return 0;
         } else {
            double var25 = Math.abs(var15 / (var11 - var7));
            double var23;
            if (var25 > var27) {
               var23 = var27 / var25;
            } else {
               var23 = 1.0D;
            }

            var15 *= var23;

            for(int var1 = 0; var1 < 11; ++var1) {
               double var13 = (double)(var1 - 5) * var15 / 5.5D;
               double var19;
               AnteproyectoFC.apRayo[var0][0][var1] = var19 = var13 / (var11 - var7);
               AnteproyectoFC.bpRayo[var0][0][var1] = var13 - var11 * var19;
               AnteproyectoFC.zpRayo[var0][0][var1] = 0.0D;
               AnteproyectoFC.ypRayo[var0][0][var1] = AnteproyectoFC.bpRayo[var0][0][var1];
               AnteproyectoFC.hRayo[var0][var29][var1] = var13;
               AnteproyectoFC.pasa[var0][0][var1] = 1;
               AnteproyectoFC.apRayo[var0][0][var1 + 11] = var19 = (var13 - var9) / (var11 - var7);
               AnteproyectoFC.bpRayo[var0][0][var1 + 11] = var13 - var11 * var19;
               AnteproyectoFC.zpRayo[var0][0][var1 + 11] = 0.0D;
               AnteproyectoFC.ypRayo[var0][0][var1 + 11] = AnteproyectoFC.bpRayo[var0][0][var1 + 11];
               AnteproyectoFC.hRayo[var0][var29][var1 + 11] = var13;
               AnteproyectoFC.pasa[var0][0][var1 + 11] = 1;
            }

            return 1;
         }
      }
   }

   static int coeficientesRayos(int var0) {
      int var6 = AnteproyectoFC.numTramos[var0];
      if (preparapRayos(var0) == 0) {
         AnteproyectoFC.pintarRayos[var0] = 0;
         return 0;
      } else {
         for(int var4 = 0; var4 < 22; ++var4) {
            for(int var5 = 1; var5 < var6; ++var5) {
               int var1 = AnteproyectoFC.tramo[var0][var5];
               int var3 = AnteproyectoFC.tramo[var0][var5 - 1];
               int var2 = AnteproyectoFC.tramo[var0][var5 + 1];
               double var11 = SistemaFC.valZE[var1];
               double var17 = SistemaFC.valZE[var2];
               double var19 = AnteproyectoFC.zImag[var0][var1];
               double var7;
               double var9;
               if (var19 == var11) {
                  var7 = AnteproyectoFC.apRayo[var0][var3][var4];
                  var9 = AnteproyectoFC.bpRayo[var0][var3][var4];
               } else {
                  double var21;
                  if (var4 < 11) {
                     var21 = 0.0D;
                  } else {
                     var21 = AnteproyectoFC.yImag[var0][var1];
                  }

                  double var27 = AnteproyectoFC.hRayo[var0][var1][var4];
                  if (Math.abs(var27) <= SistemaFC.valDE[var1] / 2.0D) {
                     AnteproyectoFC.pasa[var0][var1][var4] = 1;
                  } else {
                     AnteproyectoFC.pasa[var0][var1][var4] = 0;
                  }

                  var7 = (var21 - var27) / (var19 - var11);
                  var9 = var21 - var19 * var7;
               }

               double var29 = var7 * var17 + var9;
               AnteproyectoFC.apRayo[var0][var1][var4] = var7;
               AnteproyectoFC.bpRayo[var0][var1][var4] = var9;
               AnteproyectoFC.hRayo[var0][var2][var4] = var29;
            }
         }

         AnteproyectoFC.pintarRayos[var0] = 1;
         return 1;
      }
   }

   static int interseccionesRayos(int var0) {
      int var5 = AnteproyectoFC.numTramos[var0];

      for(int var3 = 0; var3 < 22; ++var3) {
         for(int var4 = 0; var4 < var5; ++var4) {
            int var1 = AnteproyectoFC.tramo[var0][var4];
            int var2 = AnteproyectoFC.tramo[var0][var4 + 1];
            double var6 = AnteproyectoFC.apRayo[var0][var1][var3];
            double var8 = AnteproyectoFC.bpRayo[var0][var1][var3];
            double var10 = SistemaFC.valZE[var1];
            double var16 = SistemaFC.valZE[var2];
            if (var1 == 0) {
               AnteproyectoFC.zpRayo[var0][0][var3] = var10;
               AnteproyectoFC.ypRayo[var0][0][var3] = AnteproyectoFC.hRayo[var0][0][var3];
            } else {
               Caja.doble1 = var6;
               Caja.doble2 = var8;
               Caja.doble3 = var10 + SistemaFC.espesor[var1] / 2.0D;
               Caja.doble4 = -SistemaFC.radioDib[var1];
               if (rectaEsfera() == 0) {
                  AnteproyectoFC.pasa[var0][var1][var3] = 0;
               }

               AnteproyectoFC.zpRayo[var0][var1][var3] = Caja.doble1;
               AnteproyectoFC.ypRayo[var0][var1][var3] = Caja.doble2;
            }

            if (var2 == 7) {
               AnteproyectoFC.zaRayo[var0][var2][var3] = 1600.0D;
               AnteproyectoFC.yaRayo[var0][var2][var3] = AnteproyectoFC.hRayo[var0][var2][var3];
            } else {
               Caja.doble1 = var6;
               Caja.doble2 = var8;
               Caja.doble3 = var16 - SistemaFC.espesor[var2] / 2.0D;
               Caja.doble4 = SistemaFC.radioDib[var2];
               if (rectaEsfera() == 0) {
                  AnteproyectoFC.pasa[var0][var2][var3] = 0;
               }

               AnteproyectoFC.zaRayo[var0][var2][var3] = Caja.doble1;
               AnteproyectoFC.yaRayo[var0][var2][var3] = Caja.doble2;
            }
         }
      }

      return 1;
   }

   static int rectaEsfera() {
      double var1 = Caja.doble1;
      double var3 = Caja.doble2;
      double var5 = Caja.doble3;
      double var9 = Caja.doble4;
      byte var0;
      double var21;
      if (Math.abs(var9) > 1.0E8D) {
         var21 = var5;
         var0 = 1;
      } else {
         double var7 = var5 + var9;
         double var11 = var1 * var1 + 1.0D;
         double var13 = var1 * var3 - var7;
         double var15 = var7 * var7 + var3 * var3 - var9 * var9;
         double var25;
         if ((var25 = var13 * var13 - var11 * var15) < 0.0D) {
            var21 = var5;
            var0 = 0;
         } else {
            double var27 = Math.sqrt(var25);
            double var17 = (-var13 + var27) / var11;
            double var19 = (-var13 - var27) / var11;
            if (var9 > 0.0D) {
               var21 = var19;
            } else {
               var21 = var17;
            }

            var0 = 1;
         }
      }

      double var23;
      if (Math.abs(var1) < 1.0E-5D) {
         var23 = var3;
      } else {
         var23 = var1 * var21 + var3;
      }

      Caja.doble1 = var21;
      Caja.doble2 = var23;
      Caja.int1 = var0;
      return var0;
   }

   static int calculaPupilas() {
      int var6 = 1;
      int var7 = 1;
      int var8 = AnteproyectoFC.elemento[1];
      double var11 = 0.0D;
      AnteproyectoFC.numDiafragma = AnteproyectoFC.elemento[1];

      int var2;
      int var3;
      for(var2 = 1; var2 <= AnteproyectoFC.numElementos; ++var2) {
         var3 = AnteproyectoFC.elemento[var2];
         double var9 = Math.abs(AnteproyectoFC.hRayo[0][var3][6]) / SistemaFC.valDE[var3];
         if (var9 > var11) {
            var11 = var9;
            AnteproyectoFC.numDiafragma = var3;
            var6 = var3;
            AnteproyectoFC.numRelDiafragma = var2;
            var7 = var2;
         }
      }

      var3 = AnteproyectoFC.elemento[var7];
      double var15 = 0.0D;
      double var19 = SistemaFC.valDE[var6];

      int var5;
      double var13;
      double var21;
      double var23;
      for(var2 = var7 - 1; var2 > 0; --var2) {
         var3 = AnteproyectoFC.elemento[var2];
         var5 = AnteproyectoFC.elemento[var2 + 1];
         var23 = SistemaFC.valZE[var5] - SistemaFC.valZE[var3];
         var13 = var15 - var23;
         if (SistemaFC.tipoE[var3] == 0) {
            var15 = var13;
            var19 = var19;
         } else {
            var21 = 1000.0D / SistemaFC.valPE[var3];
            if (var13 == -var21) {
               var15 = 1.0E10D;
               var19 = var19 * var15 / var13;
            } else {
               var15 = var13 * var21 / (var13 + var21);
               var19 = var19 * var21 / (var13 + var21);
            }
         }
      }

      AnteproyectoFC.zPE = SistemaFC.valZE[var3] - var15;
      AnteproyectoFC.DPE = Math.abs(var19);
      var3 = AnteproyectoFC.elemento[var7];
      var15 = 0.0D;
      var19 = SistemaFC.valDE[var6];

      for(var2 = var7 + 1; var2 <= AnteproyectoFC.numElementos; ++var2) {
         var3 = AnteproyectoFC.elemento[var2];
         var5 = AnteproyectoFC.elemento[var2 - 1];
         var23 = SistemaFC.valZE[var3] - SistemaFC.valZE[var5];
         var13 = var15 - var23;
         if (SistemaFC.tipoE[var3] == 0) {
            var15 = var13;
            var19 = var19;
         } else {
            var21 = 1000.0D / SistemaFC.valPE[var3];
            if (var13 == -var21) {
               var15 = 1.0E10D;
               var19 = var19 * var15 / var13;
            } else {
               var15 = var13 * var21 / (var13 + var21);
               var19 = var19 * var21 / (var13 + var21);
            }
         }
      }

      AnteproyectoFC.zPS = SistemaFC.valZE[var3] + var15;
      AnteproyectoFC.DPS = Math.abs(var19);
      return 1;
   }
}
