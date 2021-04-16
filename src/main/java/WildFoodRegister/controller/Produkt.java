package WildFoodRegister.controller;

public class Produkt {

    private String vyrobek;
    private int pocet;
    private int sarze;

    public Produkt(String druh,int pocet, int sarze){
        this.vyrobek = druh;
        this.pocet = pocet;
        this.sarze = sarze;

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


}


