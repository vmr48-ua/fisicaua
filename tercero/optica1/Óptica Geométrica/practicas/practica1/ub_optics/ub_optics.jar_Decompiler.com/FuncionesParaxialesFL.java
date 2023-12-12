public class FuncionesParaxialesFL {
   static int tablaElementos() {
      int var0 = 0;
      AnteproyectoFL.elemento[0] = 0;
      AnteproyectoFL.numUltimaLente = 0;
      AnteproyectoFL.numUltimoElemento = 0;

      for(int var1 = 1; var1 < 7; ++var1) {
         if (SistemaFL.existe[var1] == 1) {
            AnteproyectoFL.numUltimoElemento = var1;
            ++var0;
            AnteproyectoFL.elemento[var0] = var1;
         }

         if (SistemaFL.existe[var1] == 1 && SistemaFL.tipoE[var1] == 1) {
            AnteproyectoFL.numUltimaLente = var1;
         }
      }

      AnteproyectoFL.numElementos = var0;
      AnteproyectoFL.elemento[var0 + 1] = 7;
      if (var0 < 1) {
         return 0;
      } else {
         return 1;
      }
   }

   static int tablaTramos(int var0) {
      int var2 = 0;
      int var1;
      if (SistemaFL.real[var0] != 0 && !(SistemaFL.zObjeto[var0] >= 1.0E10D)) {
         AnteproyectoFL.tramo[var0][0] = 0;

         for(var1 = 1; var1 <= AnteproyectoFL.numElementos; ++var1) {
            int var3 = AnteproyectoFL.elemento[var1];
            if (!(SistemaFL.zObjeto[var0] >= SistemaFL.valZE[var3])) {
               ++var2;
               AnteproyectoFL.tramo[var0][var2] = AnteproyectoFL.elemento[var1];
            }
         }

         AnteproyectoFL.numTramos[var0] = var2 + 1;
         AnteproyectoFL.tramo[var0][var2 + 1] = 7;
      } else {
         for(var1 = 0; var1 <= AnteproyectoFL.numElementos; ++var1) {
            AnteproyectoFL.tramo[var0][var1] = AnteproyectoFL.elemento[var1];
         }

         AnteproyectoFL.numTramos[var0] = AnteproyectoFL.numElementos + 1;
         AnteproyectoFL.tramo[var0][var1] = 7;
      }

      return AnteproyectoFL.numTramos[var0] < 2 ? 0 : 1;
   }

   static int marchaParaxial(int var0) {
      int var4 = AnteproyectoFL.numTramos[var0];
      if (var4 < 1) {
         return 0;
      } else if (SistemaFL.existeObjeto[var0] == 0) {
         return 0;
      } else {
         double var11 = SistemaFL.valZE[AnteproyectoFL.tramo[var0][1]];
         double var19;
         AnteproyectoFL.zImag[var0][0] = var19 = SistemaFL.zObjeto[var0];
         AnteproyectoFL.yImag[var0][0] = SistemaFL.valYObj[var0] = (var11 - var19) * SistemaFL.campoDib[var0] * 3.141592D / 180.0D / 8.0D;

         for(int var3 = 1; var3 <= var4; ++var3) {
            int var1 = AnteproyectoFL.tramo[var0][var3];
            int var2 = AnteproyectoFL.tramo[var0][var3 - 1];
            if (SistemaFL.tipoE[var1] == 0) {
               AnteproyectoFL.zImag[var0][var1] = AnteproyectoFL.zImag[var0][var2];
               AnteproyectoFL.yImag[var0][var1] = AnteproyectoFL.yImag[var0][var2];
            } else {
               double var5 = AnteproyectoFL.zImag[var0][var2];
               double var7 = AnteproyectoFL.yImag[var0][var2];
               double var13 = var5 - SistemaFL.valZE[var1];
               double var15 = 1000.0D / SistemaFL.valPE[var1];
               double var9;
               double var17;
               if (var13 == -var15) {
                  var17 = 1.0E10D;
                  var9 = var7 * var17 / var13;
               } else {
                  var17 = var13 * var15 / (var13 + var15);
                  var9 = var7 * var15 / (var13 + var15);
               }

               AnteproyectoFL.zImag[var0][var1] = ExactoFL.valZParax = SistemaFL.valZE[var1] + var17;
               AnteproyectoFL.yImag[var0][var1] = var9;
            }
         }

         AnteproyectoFL.zImag[var0][7] = 1600.0D;
         return 1;
      }
   }

   static int preparapRayos(int var0) {
      boolean var3 = true;
      double var27 = 0.5D;
      double var7 = AnteproyectoFL.zImag[var0][0];
      double var9 = AnteproyectoFL.yImag[var0][0];
      int var29 = AnteproyectoFL.tramo[var0][1];
      if (var29 > 6) {
         AnteproyectoFL.pintarRayos[var0] = 0;
         return 0;
      } else {
         double var11 = SistemaFL.valZE[var29];
         int var4 = SistemaFL.tipoE[var29];
         double var5 = SistemaFL.valPE[var29];
         double var15 = SistemaFL.valDE[var29] / 2.0D;
         if (var15 == 0.0D) {
            AnteproyectoFL.pintarRayos[var0] = 0;
            return 0;
         } else if (var5 == 0.0D && var4 == 1) {
            AnteproyectoFL.pintarRayos[var0] = 0;
            return 0;
         } else if (var15 == (double)AnteproyectoFL.diamMax / 2.0D && var4 == 0) {
            AnteproyectoFL.pintarRayos[var0] = 0;
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
               AnteproyectoFL.apRayo[var0][0][var1] = var19 = var13 / (var11 - var7);
               AnteproyectoFL.bpRayo[var0][0][var1] = var13 - var11 * var19;
               AnteproyectoFL.zpRayo[var0][0][var1] = 0.0D;
               AnteproyectoFL.ypRayo[var0][0][var1] = AnteproyectoFL.bpRayo[var0][0][var1];
               AnteproyectoFL.hRayo[var0][var29][var1] = var13;
               AnteproyectoFL.pasa[var0][0][var1] = 1;
               AnteproyectoFL.apRayo[var0][0][var1 + 11] = var19 = (var13 - var9) / (var11 - var7);
               AnteproyectoFL.bpRayo[var0][0][var1 + 11] = var13 - var11 * var19;
               AnteproyectoFL.zpRayo[var0][0][var1 + 11] = 0.0D;
               AnteproyectoFL.ypRayo[var0][0][var1 + 11] = AnteproyectoFL.bpRayo[var0][0][var1 + 11];
               AnteproyectoFL.hRayo[var0][var29][var1 + 11] = var13;
               AnteproyectoFL.pasa[var0][0][var1 + 11] = 1;
            }

            return 1;
         }
      }
   }

   static int coeficientesRayos(int var0) {
      int var6 = AnteproyectoFL.numTramos[var0];
      if (preparapRayos(var0) == 0) {
         AnteproyectoFL.pintarRayos[var0] = 0;
         return 0;
      } else {
         for(int var4 = 0; var4 < 22; ++var4) {
            for(int var5 = 1; var5 < var6; ++var5) {
               int var1 = AnteproyectoFL.tramo[var0][var5];
               int var3 = AnteproyectoFL.tramo[var0][var5 - 1];
               int var2 = AnteproyectoFL.tramo[var0][var5 + 1];
               double var11 = SistemaFL.valZE[var1];
               double var17 = SistemaFL.valZE[var2];
               double var19 = AnteproyectoFL.zImag[var0][var1];
               double var7;
               double var9;
               if (var19 == var11) {
                  var7 = AnteproyectoFL.apRayo[var0][var3][var4];
                  var9 = AnteproyectoFL.bpRayo[var0][var3][var4];
               } else {
                  double var21;
                  if (var4 < 11) {
                     var21 = 0.0D;
                  } else {
                     var21 = AnteproyectoFL.yImag[var0][var1];
                  }

                  double var27 = AnteproyectoFL.hRayo[var0][var1][var4];
                  if (Math.abs(var27) <= SistemaFL.valDE[var1] / 2.0D) {
                     AnteproyectoFL.pasa[var0][var1][var4] = 1;
                  } else {
                     AnteproyectoFL.pasa[var0][var1][var4] = 0;
                  }

                  var7 = (var21 - var27) / (var19 - var11);
                  var9 = var21 - var19 * var7;
               }

               double var29 = var7 * var17 + var9;
               AnteproyectoFL.apRayo[var0][var1][var4] = var7;
               AnteproyectoFL.bpRayo[var0][var1][var4] = var9;
               AnteproyectoFL.hRayo[var0][var2][var4] = var29;
            }
         }

         AnteproyectoFL.pintarRayos[var0] = 1;
         return 1;
      }
   }

   static int interseccionesRayos(int var0) {
      int var5 = AnteproyectoFL.numTramos[var0];

      for(int var3 = 0; var3 < 22; ++var3) {
         for(int var4 = 0; var4 < var5; ++var4) {
            int var1 = AnteproyectoFL.tramo[var0][var4];
            int var2 = AnteproyectoFL.tramo[var0][var4 + 1];
            double var6 = AnteproyectoFL.apRayo[var0][var1][var3];
            double var8 = AnteproyectoFL.bpRayo[var0][var1][var3];
            double var10 = SistemaFL.valZE[var1];
            double var16 = SistemaFL.valZE[var2];
            if (var1 == 0) {
               AnteproyectoFL.zpRayo[var0][0][var3] = var10;
               AnteproyectoFL.ypRayo[var0][0][var3] = AnteproyectoFL.hRayo[var0][0][var3];
            } else {
               Caja.doble1 = var6;
               Caja.doble2 = var8;
               Caja.doble3 = var10 + SistemaFL.espesor[var1] / 2.0D;
               Caja.doble4 = -SistemaFL.radioDib[var1];
               if (rectaEsfera() == 0) {
                  AnteproyectoFL.pasa[var0][var1][var3] = 0;
               }

               AnteproyectoFL.zpRayo[var0][var1][var3] = Caja.doble1;
               AnteproyectoFL.ypRayo[var0][var1][var3] = Caja.doble2;
            }

            if (var2 == 7) {
               AnteproyectoFL.zaRayo[var0][var2][var3] = 1600.0D;
               AnteproyectoFL.yaRayo[var0][var2][var3] = AnteproyectoFL.hRayo[var0][var2][var3];
            } else {
               Caja.doble1 = var6;
               Caja.doble2 = var8;
               Caja.doble3 = var16 - SistemaFL.espesor[var2] / 2.0D;
               Caja.doble4 = SistemaFL.radioDib[var2];
               if (rectaEsfera() == 0) {
                  AnteproyectoFL.pasa[var0][var2][var3] = 0;
               }

               AnteproyectoFL.zaRayo[var0][var2][var3] = Caja.doble1;
               AnteproyectoFL.yaRayo[var0][var2][var3] = Caja.doble2;
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
      int var8 = AnteproyectoFL.elemento[1];
      double var11 = 0.0D;
      AnteproyectoFL.numDiafragma = AnteproyectoFL.elemento[1];

      int var2;
      int var3;
      for(var2 = 1; var2 <= AnteproyectoFL.numElementos; ++var2) {
         var3 = AnteproyectoFL.elemento[var2];
         double var9 = Math.abs(AnteproyectoFL.hRayo[0][var3][6]) / SistemaFL.valDE[var3];
         if (var9 > var11) {
            var11 = var9;
            AnteproyectoFL.numDiafragma = var3;
            var6 = var3;
            AnteproyectoFL.numRelDiafragma = var2;
            var7 = var2;
         }
      }

      var3 = AnteproyectoFL.elemento[var7];
      double var15 = 0.0D;
      double var19 = SistemaFL.valDE[var6];

      int var5;
      double var13;
      double var21;
      double var23;
      for(var2 = var7 - 1; var2 > 0; --var2) {
         var3 = AnteproyectoFL.elemento[var2];
         var5 = AnteproyectoFL.elemento[var2 + 1];
         var23 = SistemaFL.valZE[var5] - SistemaFL.valZE[var3];
         var13 = var15 - var23;
         if (SistemaFL.tipoE[var3] == 0) {
            var15 = var13;
            var19 = var19;
         } else {
            var21 = 1000.0D / SistemaFL.valPE[var3];
            if (var13 == -var21) {
               var15 = 1.0E10D;
               var19 = var19 * var15 / var13;
            } else {
               var15 = var13 * var21 / (var13 + var21);
               var19 = var19 * var21 / (var13 + var21);
            }
         }
      }

      AnteproyectoFL.zPE = SistemaFL.valZE[var3] - var15;
      AnteproyectoFL.DPE = Math.abs(var19);
      var3 = AnteproyectoFL.elemento[var7];
      var15 = 0.0D;
      var19 = SistemaFL.valDE[var6];

      for(var2 = var7 + 1; var2 <= AnteproyectoFL.numElementos; ++var2) {
         var3 = AnteproyectoFL.elemento[var2];
         var5 = AnteproyectoFL.elemento[var2 - 1];
         var23 = SistemaFL.valZE[var3] - SistemaFL.valZE[var5];
         var13 = var15 - var23;
         if (SistemaFL.tipoE[var3] == 0) {
            var15 = var13;
            var19 = var19;
         } else {
            var21 = 1000.0D / SistemaFL.valPE[var3];
            if (var13 == -var21) {
               var15 = 1.0E10D;
               var19 = var19 * var15 / var13;
            } else {
               var15 = var13 * var21 / (var13 + var21);
               var19 = var19 * var21 / (var13 + var21);
            }
         }
      }

      AnteproyectoFL.zPS = SistemaFL.valZE[var3] + var15;
      AnteproyectoFL.DPS = Math.abs(var19);
      return 1;
   }
}
