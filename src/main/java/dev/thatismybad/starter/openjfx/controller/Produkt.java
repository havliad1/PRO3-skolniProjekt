package dev.thatismybad.starter.openjfx.controller;

public class Produkt {
    private int ID;
    private String druh;
    public double vaha;


    public Produkt(int ID, String druh, double vaha){
        this.ID = ID;
        this.druh = druh;
        this.vaha = vaha;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDruh() {
        return druh;
    }

    public void setDruh(String druh) {
        this.druh = druh;
    }

    public double getVaha() {
        return vaha;
    }

    public void setVaha(int vaha) {
        this.vaha = vaha;
    }
}
