public class Kontroller {

    private GUI gui;
    private Modell modell;
    private boolean gaaHoyre;
    private boolean gaaVenstre;
    private boolean gaaOpp;
    private boolean gaaNed;

    private final int GRID = 12;
    private final int DELAY = 500;

    public Kontroller(){
        gui = new GUI(this);
        modell = new Modell(gui, this);
    }

    // starter spillet
    public void init() throws InterruptedException {
        modell.startSpill();
        gameLoop();
    }

    // gameloop
    public void gameLoop() throws InterruptedException {
        while (modell.spillKjorer()) {
            Thread.sleep(DELAY);
            oppdater();
            if (!modell.spillKjorer()) break;
            gui.tegnBrettOmIgjen();
        }
    }

    // tegner spillbrett
    public void tegnStartBrett() {
        gui.tegnStartBrett();
    }

    // henter slange
    public Koe<Slange> hentSlange() {
        return modell.hentSlange();
    }

    // henter hode
    public Slange hentHode() {
        return modell.hentHode();
    }

    // henter halelengde
    public int hentHalelengde() {
        return modell.hentHalelengde();
    }

    // henter skatter
    public Skatt[] hentSkatter() {
       return modell.hentSkatter();
    }

    // legg til skatt
    public void leggTilSkatt(int pos, Skatt skatt) {
       modell.leggTilSkatt(pos, skatt);
    }

    // fjern skatt
    public void fjernSkatt(int pos) {
       modell.fjernSkatt(pos);
    }

    // forlenger slangen
    public void forlengSlange(Slange del) {
        modell.forlengSlange(del);
    }

    // sjekker om kollisjon med egen hale
    public void treffHale() {
        modell.treffHale();
    }

    // avslutter spill
    public void avsluttSpill() {
        System.exit(0);
    }



    // oppdaterer elementer i spillet
    public void oppdater() {
        beveg();
        treffHale();
    }


    // beveger slangen
    public void beveg() {
        if (gaaHoyre) modell.beveg("hoyre");
        if (gaaVenstre) modell.beveg("venstre");
        if (gaaOpp) modell.beveg("opp");
        if (gaaNed) modell.beveg("ned");
    }

    // oppdaterer bevegelsen til slangen
    public void oppdaterBevegelse(String retning) {
        switch (retning) {
            case "hoyre":
                if (gaaVenstre) return;
                gaaOpp = false;
                gaaHoyre = true;
                gaaNed = false;
                gaaVenstre = false;
                break;
            case "venstre":
                if (gaaHoyre) return;
                gaaOpp = false;
                gaaHoyre = false;
                gaaNed = false;
                gaaVenstre = true;
                break;
            case "opp":
                if (gaaNed) return;
                gaaOpp = true;
                gaaHoyre = false;
                gaaNed = false;
                gaaVenstre = false;
                break;
            case "ned":
                if (gaaOpp) return;
                gaaOpp = false;
                gaaHoyre = false;
                gaaNed = true;
                gaaVenstre = false;
                break;
        }
    }
}
