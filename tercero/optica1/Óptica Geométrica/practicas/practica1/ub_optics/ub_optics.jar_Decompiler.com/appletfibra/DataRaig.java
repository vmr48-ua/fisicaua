package appletfibra;

public class DataRaig {
   double h = 0.0D;
   double theta = 0.0D;
   double fi = 0.0D;
   int ID;
   double ri = 0.0D;
   double thetai = 0.0D;
   double phii = 0.0D;
   double rf = 0.0D;
   double thetaf = 0.0D;
   double phif = 0.0D;
   double[] z;
   double[] x;
   double[] y;
   double[] theta_v;
   double[] phi_v;
   int N;
   int Nmax = 1000;
   double I0;

   public DataRaig() {
      this.x = new double[this.Nmax];
      this.y = new double[this.Nmax];
      this.z = new double[this.Nmax];
      this.theta_v = new double[this.Nmax];
      this.phi_v = new double[this.Nmax];
      this.N = 0;
      this.I0 = 1.0D;
   }

   void AugEspai() {
      double[] auxx = new double[2 * this.Nmax];
      double[] auxy = new double[2 * this.Nmax];
      double[] auxz = new double[2 * this.Nmax];
      double[] auxtheta = new double[2 * this.Nmax];
      double[] auxphi = new double[2 * this.Nmax];

      for(int i = 0; i < this.Nmax; ++i) {
         auxx[i] = this.x[i];
         auxy[i] = this.y[i];
         auxz[i] = this.z[i];
         auxtheta[i] = this.theta_v[i];
         auxphi[i] = this.phi_v[i];
      }

      this.x = auxx;
      this.y = auxy;
      this.z = auxz;
      this.theta_v = auxtheta;
      this.phi_v = auxphi;
      this.Nmax = 2 * this.Nmax;
   }
}
