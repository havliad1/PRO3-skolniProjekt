package WildFoodRegister.AddonClasses;

public class Surovina {
    private int ID;
    private String druh;
    public double vaha;


    public Surovina(int ID, String druh, double vaha){
        this.ID = ID;
        this.druh = druh;
        this.vaha = vaha;

    }

    public Surovina(Surovina statistika){
        this.ID = statistika.ID;
        this.druh = statistika.druh;
        this.vaha = statistika.vaha;
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

    public void setVaha(double vaha) {
        this.vaha = vaha;
    }
/*
    @Override
    public boolean equals(Object object)
    {
        boolean isEqual= false;

        if (object != null && object instanceof Surovina)
        {
            isEqual = (this.druh == ((Surovina) object).druh);
        }

        return isEqual;
    }

 */
}
