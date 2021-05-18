package WildFoodRegister.AddonClasses;

public class RegisterProdukt {

    private String nazev;
    private long carovyKod;
    private int ID;


    public RegisterProdukt(String nazev, long carovyKod, int ID) {
        this.nazev = nazev;
        this.carovyKod = carovyKod;
        this.ID = ID;

    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public long getCarovyKod() {
        return carovyKod;
    }

    public void setCarovyKod(long carovyKod) {
        this.carovyKod = carovyKod;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

}
