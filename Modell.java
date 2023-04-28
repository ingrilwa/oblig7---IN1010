public class Modell {

    private GUI gui;
    private Kontroller kontroller;
    private boolean kjor;
    private Koe<Slange> slange = new Koe<>();
    private Skatt[] skatter = new Skatt[10];

    public Modell(GUI gui, Kontroller kontroller) {
        this.gui = gui;
        this.kontroller = kontroller;
    }

    // starter spillet
    public void startSpill() {
        kjor = true;
        kontroller.tegnStartBrett();
    }

    // henter slange
    public Koe<Slange> hentSlange() {
        return slange;
    }

    // henter hodet
    public Slange hentHode() {
        for (Slange del : slange) {
            if (del.harHode()) return del;
        }

        return null;
    }

    // henter halelengde
    public int hentHalelengde() {
        return slange.storrelse();
    }

    // legger til skatter
    public void leggTilSkatt(int posisjon, Skatt skatt) {
        skatter[posisjon] = skatt;
    }

    // henter skatter
    public Skatt[] hentSkatter() {
        return skatter;
    }

    // fjerner skatter
    public void fjernSkatt(int posisjon) {
        skatter[posisjon] = null;
    }

    // legger til Slange-objektet i slangen
    public void forlengSlange(Slange del) {
        slange.leggTil(del);
    }

    // fjerner Slange-objektet fra slangen
    public void forkortSlange() {
        slange.fjern();
    }


    // sjekker om slangen treffer sin egen hale
    public void treffHale() {
        if (slange.storrelse() > 1) {
            for (Slange del : slange) {
                if (hentHode().hentRad() == del.hentRad() && hentHode().hentKolonne() == del.hentKolonne() && !del.harHode()) avsluttSpill();
            }
        }
    }

    // legger til skatt
    public void leggTilSkatt(int posisjon) {
        int skattPaaRad = 0;
        int skattPaaKolonne = 0;
        boolean trefferIkke = false;

        while (true) {
            for (Slange del : slange) {
                for (Skatt skatt : skatter) {
                    if (skatt != null) {
                        skattPaaRad = Skatt.trekk(0, 11);
                        skattPaaKolonne = Skatt.trekk(0, 11);
                        if (skattPaaRad != del.hentRad() && skattPaaRad != skatt.hentRad() && skattPaaKolonne != del.hentKolonne() && skattPaaKolonne != skatt.hentKolonne()) trefferIkke = true;
                        else trefferIkke = false;
                    }
                }
            }

            if (trefferIkke) break;
        }

        skatter[posisjon] = new Skatt(skattPaaRad, skattPaaKolonne);
    }

    // sjekk etter kollisjon mellom slange og skatt
    public boolean kollisjon() {
        for (int i = 0; i < skatter.length; i++) {
            for (Slange del : slange) {
                if (skatter[i] != null) {
                    if (skatter[i].hentRad() == del.hentRad() && skatter[i].hentKolonne() == del.hentKolonne()) {
                        fjernSkatt(i);
                        leggTilSkatt(i);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // beveger paa slangen
    public void beveg(String retning) {
        Slange ny;

        switch (retning) {
            case "hoyre":
                if (hentHode().hentKolonne() + 1 > 11) avsluttSpill();

                if (slange.storrelse() == 1) {
                    ny = new Slange(hentHode().hentRad(), hentHode().hentKolonne() + 1, true);
                    if (!kollisjon()) slange.fjern();
                    for (Slange del : slange) del.fjernHode();
                    slange.leggTil(ny);
                    return;
                }

                    ny = new Slange(hentHode().hentRad(), hentHode().hentKolonne() + 1, true);
                    for (Slange del : slange) del.fjernHode();
                    if (!kollisjon()) slange.fjern();
                    slange.leggTil(ny);
                    break;

            case "venstre":
                if (hentHode().hentKolonne() - 1 < 0) avsluttSpill();

                if (slange.storrelse() == 1) {
                    ny = new Slange(hentHode().hentRad(), hentHode().hentKolonne() - 1, true);
                    if (!kollisjon()) slange.fjern();
                    for (Slange del : slange) del.fjernHode();
                    slange.leggTil(ny);
                    return;
                }

                ny = new Slange(hentHode().hentRad(), hentHode().hentKolonne() - 1, true);
                for (Slange del : slange) del.fjernHode();
                if (!kollisjon()) slange.fjern();
                slange.leggTil(ny);
                break;

            case "opp":
                if (hentHode().hentRad() - 1 < 0) avsluttSpill();

                if (slange.storrelse() == 1) {
                    ny = new Slange(hentHode().hentRad() - 1, hentHode().hentKolonne(), true);
                    if (!kollisjon()) slange.fjern();
                    for (Slange del : slange) del.fjernHode();
                    slange.leggTil(ny);
                    return;
                }

                ny = new Slange(hentHode().hentRad() - 1, hentHode().hentKolonne(), true);
                for (Slange del : slange) del.fjernHode();
                if (!kollisjon()) slange.fjern();
                slange.leggTil(ny);
                break;

            case "ned":
                if (hentHode().hentRad() + 1 > 11) avsluttSpill();

                if (slange.storrelse() == 1) {
                    ny = new Slange(hentHode().hentRad() + 1, hentHode().hentKolonne(), true);
                    if (!kollisjon()) slange.fjern();
                    for (Slange del : slange) del.fjernHode();
                    slange.leggTil(ny);
                    return;
                }

                ny = new Slange(hentHode().hentRad() + 1, hentHode().hentKolonne(), true);
                for (Slange del : slange) del.fjernHode();
                if (!kollisjon()) slange.fjern();
                slange.leggTil(ny);
                break;

        }
    }

    // avslutter spillet
    public void avsluttSpill() {
        kjor = false;
    }

    // sjekker om spillet fortsatt kjorer
    public boolean spillKjorer() {
        return kjor;
    }
}
