public class Slange extends Rute {

      private boolean harHode;

      public Slange(int rad, int kolonne, boolean harHode){
          super(rad, kolonne);
          this.harHode = harHode;
      }

      public void settHode(){
          harHode = true;
      }

      public void fjernHode(){
          harHode = false;
      }

      public boolean harHode(){
          return harHode;
      }
}
