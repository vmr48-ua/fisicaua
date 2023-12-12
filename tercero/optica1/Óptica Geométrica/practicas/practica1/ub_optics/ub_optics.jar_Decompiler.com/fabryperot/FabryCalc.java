package fabryperot;

public class FabryCalc {
   public double thick = 0.5D;
   public double lambda = 450.0D;
   public double angle = 0.0D;
   public double r = 0.9D;
   public double sizeSource = 5.0D;
   public double sizeScreen = 5.0D;
   public double fSource = 100.0D;
   public double fScreen = 1000.0D;
   public double sizeSourcemax = 35.0D;
   public double sizeScreenmax = 35.0D;
   public double fSourcemax = 1000.0D;
   public double fScreenmax = 2000.0D;
   public double fSourcemin = 10.0D;
   public double fScreenmin = 100.0D;
   public int kmin;
   public int kmax;
   public int numint;
   public double thick_max = 25.0D;

   public double[][] getIT() {
      double angfin = Math.atan(this.sizeScreen / this.fScreen);
      double ang_max_font = Math.atan(this.sizeSource / this.fSource);
      double[] MaxPos = this.CalcMax();
      this.numint = (this.kmax - this.kmin + 2) * 10 - 1;
      double[][] IT = new double[2][this.numint + 1];

      for(int j = 0; j < this.kmax - this.kmin + 2; ++j) {
         for(int i = 0; i < 10; ++i) {
            IT[0][i + j * 10] = MaxPos[j] + (MaxPos[j + 1] - MaxPos[j]) / 10.0D * (double)i;
            if (IT[0][i + j * 10] <= ang_max_font) {
               double delta = 4.0D * this.thick / this.lambda * 1000000.0D * Math.cos(IT[0][i + j * 10]) * 3.141592653589793D;
               IT[1][i + j * 10] = (1.0D - this.r * this.r) * (1.0D - this.r * this.r) / ((1.0D - this.r * this.r) * (1.0D - this.r * this.r) + 4.0D * this.r * this.r * Math.sin(delta / 2.0D) * Math.sin(delta / 2.0D));
            } else {
               IT[1][i + j * 10] = 0.0D;
            }

            IT[0][i + j * 10] = this.fScreen * Math.tan(IT[0][i + j * 10]);
         }
      }

      return IT;
   }

   public double[] CalcMax() {
      double[] MaxPos = new double[2];
      double angfin = Math.atan(this.sizeScreen / this.fScreen);
      double ang_max_font = Math.atan(this.sizeSource / this.fSource);
      this.kmax = (int)Math.floor(4.0D * this.thick / this.lambda * 1000000.0D);
      this.kmin = (int)Math.ceil(4.0D * this.thick / this.lambda * 1000000.0D * Math.cos(Math.min(angfin, ang_max_font)));
      MaxPos = new double[this.kmax - this.kmin + 1 + 3];
      MaxPos[0] = 0.0D;

      for(int i = this.kmax; i >= this.kmin; --i) {
         MaxPos[this.kmax - i + 1] = Math.acos((double)i * this.lambda * 1.0E-6D / (4.0D * this.thick));
      }

      MaxPos[this.kmax - this.kmin + 2] = Math.min(angfin, ang_max_font);
      return MaxPos;
   }

   public double IT(double x) {
      if (x > this.sizeScreen) {
         return 0.0D;
      } else {
         double angle = Math.atan(x / this.fScreen);
         double ang_max_font = Math.atan(this.sizeSource / this.fSource);
         double retval;
         if (angle <= ang_max_font) {
            double delta = 4.0D * this.thick / this.lambda * 1000000.0D * Math.cos(angle) * 3.141592653589793D;
            retval = (1.0D - this.r * this.r) * (1.0D - this.r * this.r) / ((1.0D - this.r * this.r) * (1.0D - this.r * this.r) + 4.0D * this.r * this.r * Math.sin(delta / 2.0D) * Math.sin(delta / 2.0D));
         } else {
            retval = 0.0D;
         }

         return retval;
      }
   }
}
