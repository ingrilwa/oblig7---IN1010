public class Skatt extends Rute {

    public Skatt(int rad, int kolonne) {
        super(rad, kolonne);
    }

    static int trekk (int a, int b) {
        return (int)(Math.random()*(b - a + 1)) + a;
    }

}
