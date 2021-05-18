package WildFoodRegister.AddonClasses;

public class Produkt {

    private String vyrobek;
    private int pocet;
    private int sarze;
    private int ID;

    public Produkt(String druh,int pocet, int sarze, int ID){
        this.vyrobek = druh;
        this.pocet = pocet;
        this.sarze = sarze;
        this.ID = ID;

    }

    public String getVyrobek() {
        return vyrobek;
    }

    public void setVyrobek(String vyrobek) {
        this.vyrobek = vyrobek;
    }

    public int getPocet() {
        return pocet;
    }

    public void setPocet(int pocet) {
        this.pocet = pocet;
    }

    public int getSarze() {
        return sarze;
    }

    public void setSarze(int sarze) {
        this.sarze = sarze;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}


