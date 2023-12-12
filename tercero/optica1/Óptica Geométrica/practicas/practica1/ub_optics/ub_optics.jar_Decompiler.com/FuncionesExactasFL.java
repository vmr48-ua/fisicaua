public class FuncionesExactasFL {
   static int numSup;
   static int numColores;
   static double imagParax;
   static double[] curv = new double[15];
   static double[] r = new double[15];
   static double[] v = new double[15];
   static double[] d = new double[15];
   static double[] hmax = new double[15];
   static double[][] ene = new double[3][15];
   static int[][][][] pasam = new int[2][3][15][251];
   static int[][][][] pasa = new int[2][3][15][251];
   static double[][][][] cmRayo = new double[2][3][15][21];
   static double[][][][] dmRayo = new double[2][3][15][21];
   static double[][][][] aRayo = new double[2][3][15][251];
   static double[][][][] bRayo = new double[2][3][15][251];
   static double[][][][] cRayo = new double[2][3][15][251];
   static double[][][][] dRayo = new double[2][3][15][251];
   static double[][][][] zmRayo = new double[2][3][15][21];
   static double[][][][] ymRayo = new double[2][3][15][21];
   static double[][][][] zRayo = new double[2][3][15][251];
   static double[][][][] xRayo = new double[2][3][15][251];
   static double[][][][] yRayo = new double[2][3][15][251];
   static double[][][] hRayo = new double[3][15][251];

   static int preparaSistema() {
      int var1 = 0;
      if (SistemaFL.existeObjeto[0] == 0) {
         ExactoFL.dibuja = 0;
         return 0;
      } else {
         numColores = ExactoFL.numColores;

         for(int var0 = 1; var0 < 7; ++var0) {
            if (SistemaFL.existe[var0] != 0) {
               if (SistemaFL.real[0] == 1 && SistemaFL.zObjeto[0] >= SistemaFL.valZE[var0] - ExactoFL.espesor[var0] / 2.0D && SistemaFL.zObjeto[0] <= SistemaFL.valZE[var0] + ExactoFL.espesor[var0] / 2.0D) {
                  ExactoFL.dibuja = 0;
                  return 0;
               }

               if (SistemaFL.real[0] != 1 || !(SistemaFL.zObjeto[0] > SistemaFL.valZE[var0])) {
                  ++var1;
                  ene[0][var1] = 1.0D;
                  if (numColores != 0) {
                     ene[1][var1] = ene[2][var1] = 1.0D;
                  }

                  if (SistemaFL.tipoE[var0] == 0) {
                     ExactoFL.dze1[var0] = 0.0D;
                     ExactoFL.dze2[var0] = 0.0D;
                     ExactoFL.dzb1[var0] = 0.0D;
                     ExactoFL.dzb2[var0] = 0.0D;
                     v[var1] = SistemaFL.valZE[var0];
                     curv[var1] = 0.0D;
                     r[var1] = 1.0E10D;
                     hmax[var1] = SistemaFL.altura[var0];
                     ene[0][var1 + 1] = 1.0D;
                     if (numColores != 0) {
                        ene[1][var1 + 1] = ene[2][var1 + 1] = 1.0D;
                     }
                  } else {
                     ExactoFL.dze1[var0] = -ExactoFL.espesor[var0] / 2.0D;
                     ExactoFL.dze2[var0] = ExactoFL.espesor[var0] / 2.0D;
                     v[var1] = SistemaFL.valZE[var0] + ExactoFL.dze1[var0];
                     curv[var1] = ExactoFL.curv1[var0];
                     r[var1] = ExactoFL.radio1[var0];
                     hmax[var1] = SistemaFL.altura[var0];
                     v[var1 + 1] = SistemaFL.valZE[var0] + ExactoFL.dze2[var0];
                     curv[var1 + 1] = ExactoFL.curv2[var0];
                     r[var1 + 1] = ExactoFL.radio2[var0];
                     hmax[var1 + 1] = SistemaFL.altura[var0];
                     ene[0][var1 + 1] = ExactoFL.indice[var0];
                     if (numColores != 1) {
                        double var2 = (ExactoFL.indice[var0] - 1.0D) / ExactoFL.abbe[var0];
                        ene[1][var1 + 1] = ExactoFL.indice[var0] + var2;
                        ene[2][var1 + 1] = ExactoFL.indice[var0] - var2;
                     }

                     ++var1;
                  }
               }
            }
         }

         if (var1 == 0) {
            ExactoFL.dibuja = 0;
            return 0;
         } else {
            numSup = var1;
            r[numSup + 1] = 1.0E13D;
            v[numSup + 1] = 1600.0D;
            ene[0][numSup + 1] = 1.0D;
            if (numColores != 0) {
               ene[1][numSup + 1] = ene[2][numSup + 1] = 1.0D;
            }

            v[0] = SistemaFL.zObjeto[0];

            for(var1 = 1; var1 <= numSup; ++var1) {
               d[var1 - 1] = v[var1] - v[var1 - 1];
            }

            ExactoFL.dibuja = 1;
            return 1;
         }
      }
   }

   static int marchaParaxial() {
      double var17 = 10.0D;
      if (SistemaFL.existeObjeto[0] == 0) {
         return 0;
      } else if (ExactoFL.dibuja == 0) {
         return 0;
      } else {
         double var3 = Math.tan(SistemaFL.campo[0] * 3.151592D / 180.0D);
         SistemaFL.valYObj[0] = d[0] * var3;
         double var5 = 0.0D;
         double var9 = var17;

         int var0;
         double var7;
         double var11;
         for(var0 = 1; var0 <= numSup; ++var0) {
            var7 = var9 - d[var0 - 1] * var5;
            var11 = (var7 * (ene[0][var0 + 1] - ene[0][var0]) * curv[var0] + var5 * ene[0][var0]) / ene[0][var0 + 1];
            var9 = var7;
            var5 = var11;
         }

         double var10000 = var17 / var5;
         var5 = var17 / d[0];
         var9 = 0.0D;

         for(var0 = 1; var0 <= numSup; ++var0) {
            var7 = var9 - d[var0 - 1] * var5;
            var11 = (var7 * (ene[0][var0 + 1] - ene[0][var0]) * curv[var0] + var5 * ene[0][var0]) / ene[0][var0 + 1];
            var9 = var7;
            var5 = var11;
         }

         double var15 = var9 / var5;
         imagParax = v[numSup] + var15;
         return 1;
      }
   }

   static int preparaRayos() {
      double var11 = 0.5D;
      double var13 = SistemaFL.zObjeto[0];
      double var15 = SistemaFL.valYObj[0];
      if (ExactoFL.dibuja == 0) {
         return 0;
      } else if (d[0] == 0.0D) {
         return 0;
      } else {
         double var9 = Math.abs(hmax[1] * ExactoFL.hpr[ExactoFL.nir] / d[0]);
         double var7;
         if (var9 > var11) {
            var7 = var11 / var9;
         } else {
            var7 = 1.0D;
         }

         double var3 = hmax[1] * var7;

         int var1;
         for(var1 = 0; var1 <= ExactoFL.nir; ++var1) {
            cmRayo[0][0][1][var1] = cmRayo[0][1][1][var1] = cmRayo[0][2][1][var1] = var3 * ExactoFL.hpr[var1] / d[0];
            dmRayo[0][0][1][var1] = dmRayo[0][1][1][var1] = dmRayo[0][2][1][var1] = -var13 * cmRayo[0][0][1][var1];
            pasam[0][0][0][var1] = pasam[0][1][0][var1] = pasam[0][2][0][var1] = 1;
         }

         for(var1 = ExactoFL.nir + 1; var1 <= ExactoFL.nir * 2; ++var1) {
            cmRayo[0][0][1][var1] = cmRayo[0][1][1][var1] = cmRayo[0][2][1][var1] = -var3 * ExactoFL.hpr[var1 - ExactoFL.nir] / d[0];
            dmRayo[0][0][1][var1] = dmRayo[0][1][1][var1] = dmRayo[0][2][1][var1] = -var13 * cmRayo[0][0][1][var1];
            pasam[0][0][0][var1] = pasam[0][1][0][var1] = pasam[0][2][0][var1] = 1;
         }

         for(var1 = 0; var1 <= ExactoFL.nir; ++var1) {
            cmRayo[1][0][1][var1] = cmRayo[1][1][1][var1] = cmRayo[1][2][1][var1] = (var3 * ExactoFL.hpr[var1] - var15) / d[0];
            dmRayo[1][0][1][var1] = dmRayo[1][1][1][var1] = dmRayo[1][2][1][var1] = var15 - var13 * cmRayo[1][0][1][var1];
            pasam[1][0][0][var1] = pasam[1][1][0][var1] = pasam[1][2][0][var1] = 1;
         }

         for(var1 = ExactoFL.nir + 1; var1 <= ExactoFL.nir * 2; ++var1) {
            cmRayo[1][0][1][var1] = cmRayo[1][1][1][var1] = cmRayo[1][2][1][var1] = (-var3 * ExactoFL.hpr[var1 - ExactoFL.nir] - var15) / d[0];
            dmRayo[1][0][1][var1] = dmRayo[1][1][1][var1] = dmRayo[1][2][1][var1] = var15 - var13 * cmRayo[1][0][1][var1];
            pasam[1][0][0][var1] = pasam[1][1][0][var1] = pasam[1][2][0][var1] = 1;
         }

         for(var1 = 0; var1 < ExactoFL.numRayos; ++var1) {
            aRayo[0][0][1][var1] = aRayo[0][1][1][var1] = aRayo[0][2][1][var1] = var3 * ExactoFL.xpr[var1] / d[0];
            bRayo[0][0][1][var1] = bRayo[0][1][1][var1] = bRayo[0][2][1][var1] = -var13 * aRayo[0][0][1][var1];
            cRayo[0][0][1][var1] = cRayo[0][1][1][var1] = cRayo[0][2][1][var1] = var3 * ExactoFL.ypr[var1] / d[0];
            dRayo[0][0][1][var1] = dRayo[0][1][1][var1] = dRayo[0][2][1][var1] = -var13 * cRayo[0][0][1][var1];
            pasa[0][0][0][var1] = pasa[0][1][0][var1] = pasa[0][2][0][var1] = 1;
            aRayo[1][0][1][var1] = aRayo[1][1][1][var1] = aRayo[1][2][1][var1] = aRayo[0][0][1][var1];
            bRayo[1][0][1][var1] = bRayo[1][1][1][var1] = bRayo[1][2][1][var1] = bRayo[0][0][1][var1];
            cRayo[1][0][1][var1] = cRayo[1][1][1][var1] = cRayo[1][2][1][var1] = (var3 * ExactoFL.ypr[var1] - var15) / d[0];
            dRayo[1][0][1][var1] = dRayo[1][1][1][var1] = dRayo[1][2][1][var1] = var15 - var13 * cRayo[1][0][1][var1];
            pasa[1][0][0][var1] = pasa[1][1][0][var1] = pasa[1][2][0][var1] = 1;
         }

         return 1;
      }
   }

   static int propagaHazRayos() {
      if (ExactoFL.dibuja == 0) {
         return 0;
      } else {
         int var0;
         int var1;
         int var2;
         int var3;
         for(var3 = 0; var3 < 2; ++var3) {
            for(var2 = 0; var2 < numColores; ++var2) {
               for(var1 = 0; var1 <= ExactoFL.nir * 2; ++var1) {
                  for(var0 = 1; var0 <= numSup + 1; ++var0) {
                     refractaRayoMerid(var3, var2, var0, var1);
                  }
               }
            }
         }

         for(var3 = 0; var3 < 2; ++var3) {
            for(var2 = 0; var2 < numColores; ++var2) {
               for(var1 = 0; var1 <= ExactoFL.numRayos; ++var1) {
                  for(var0 = 1; var0 <= numSup + 1; ++var0) {
                     refractaRayoCruzado(var3, var2, var0, var1);
                  }
               }
            }
         }

         return 1;
      }
   }

   static int refractaRayoMerid(int var0, int var1, int var2, int var3) {
      double var34 = r[var2];
      double var40 = cmRayo[var0][var1][var2][var3];
      if (pasam[var0][var1][var2 - 1][var3] == 0) {
         pasam[var0][var1][var2][var3] = 0;
         return 0;
      } else {
         double var42 = var40 * v[var2] + dmRayo[var0][var1][var2][var3];
         double var36 = hmax[var2] * hmax[var2];
         double var8 = 1.0D / Math.sqrt(1.0D + var40 * var40);
         double var4 = var34 - var40 * var42;
         double var6 = 1.0D + var40 * var40;
         double var14 = var42 * var42;
         double var12 = var4 * var4 - var6 * var14;
         if (var12 < 0.0D) {
            pasam[var0][var1][var2 - 1][var3] = 0;
            return 0;
         } else {
            double var16 = Math.sqrt(var12);
            double var20;
            if (var4 < 0.0D) {
               var20 = (var4 + var16) / var6;
            } else {
               var20 = (var4 - var16) / var6;
            }

            double var18 = var40 * var20 + var42;
            ymRayo[var0][var1][var2][var3] = var18;
            zmRayo[var0][var1][var2][var3] = v[var2] + var20;
            double var38 = var18 * var18;
            if (var38 > var36) {
               pasam[var0][var1][var2][var3] = 0;
               return 0;
            } else {
               pasam[var0][var1][var2][var3] = 1;
               double var24 = ene[var1][var2] / ene[var1][var2 + 1];
               if (var24 == 1.0D) {
                  cmRayo[var0][var1][var2 + 1][var3] = cmRayo[var0][var1][var2][var3];
                  dmRayo[var0][var1][var2 + 1][var3] = dmRayo[var0][var1][var2][var3];
               } else {
                  double var22 = (var34 - var20 - var40 * var18) * var8 * var24 / var34;
                  double var26 = 1.0D + var22 * var22 - var24 * var24;
                  if (var26 < 0.0D) {
                     pasam[var0][var1][var2][var3] = 0;
                     return 0;
                  }

                  double var28 = (Math.sqrt(var26) - var22) / var34;
                  double var30 = var24 * var8;
                  double var10 = (var34 - var20) * var28 + var30;
                  double var32 = var30 * var40 - var18 * var28;
                  var40 = var32 / var10;
                  var42 = (-v[var2] - var20) * var40 + var18;
                  cmRayo[var0][var1][var2 + 1][var3] = var40;
                  dmRayo[var0][var1][var2 + 1][var3] = var42;
               }

               return 1;
            }
         }
      }
   }

   static int refractaRayoCruzado(int var0, int var1, int var2, int var3) {
      double var38 = r[var2];
      double var44 = aRayo[var0][var1][var2][var3];
      double var46 = cRayo[var0][var1][var2][var3];
      if (pasa[var0][var1][var2 - 1][var3] == 0) {
         pasa[var0][var1][var2][var3] = 0;
         return 0;
      } else {
         double var48 = var44 * v[var2] + bRayo[var0][var1][var2][var3];
         double var50 = var46 * v[var2] + dRayo[var0][var1][var2][var3];
         double var40 = hmax[var2] * hmax[var2];
         double var8 = 1.0D / Math.sqrt(1.0D + var44 * var44 + var46 * var46);
         double var4 = var38 - var44 * var48 - var46 * var50;
         double var6 = 1.0D + var44 * var44 + var46 * var46;
         double var14 = var48 * var48 + var50 * var50;
         double var12 = var4 * var4 - var6 * var14;
         if (var12 < 0.0D) {
            pasa[var0][var1][var2 - 1][var3] = 0;
            return 0;
         } else {
            double var16 = Math.sqrt(var12);
            double var22;
            if (var4 < 0.0D) {
               var22 = (var4 + var16) / var6;
            } else {
               var22 = (var4 - var16) / var6;
            }

            double var18 = var44 * var22 + var48;
            double var20 = var46 * var22 + var50;
            xRayo[var0][var1][var2][var3] = var18;
            yRayo[var0][var1][var2][var3] = var20;
            zRayo[var0][var1][var2][var3] = var22;
            double var42 = var18 * var18 + var20 * var20;
            if (var42 > var40) {
               pasa[var0][var1][var2][var3] = 0;
               return 0;
            } else {
               pasa[var0][var1][var2][var3] = 1;
               double var26 = ene[var1][var2] / ene[var1][var2 + 1];
               if (var26 == 1.0D) {
                  aRayo[var0][var1][var2 + 1][var3] = aRayo[var0][var1][var2][var3];
                  bRayo[var0][var1][var2 + 1][var3] = bRayo[var0][var1][var2][var3];
                  cRayo[var0][var1][var2 + 1][var3] = cRayo[var0][var1][var2][var3];
                  dRayo[var0][var1][var2 + 1][var3] = dRayo[var0][var1][var2][var3];
               } else {
                  double var24 = (var38 - var22 - var44 * var18 - var46 * var20) * var8 * var26 / var38;
                  double var28 = 1.0D + var24 * var24 - var26 * var26;
                  if (var28 < 0.0D) {
                     pasa[var0][var1][var2][var3] = 0;
                     return 0;
                  }

                  double var30 = (Math.sqrt(var28) - var24) / var38;
                  double var32 = var26 * var8;
                  double var10 = (var38 - var22) * var30 + var32;
                  double var34 = var32 * var44 - var18 * var30;
                  double var36 = var32 * var46 - var20 * var30;
                  var44 = var34 / var10;
                  var46 = var36 / var10;
                  var48 = (-v[var2] - var22) * var44 + var18;
                  var50 = (-v[var2] - var22) * var46 + var20;
                  aRayo[var0][var1][var2 + 1][var3] = var44;
                  bRayo[var0][var1][var2 + 1][var3] = var48;
                  cRayo[var0][var1][var2 + 1][var3] = var46;
                  dRayo[var0][var1][var2 + 1][var3] = var50;
               }

               return 1;
            }
         }
      }
   }
}
