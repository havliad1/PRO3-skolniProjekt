package WildFoodRegister.controller;

public class Uzivatel {

    private String jmeno;
    private String prijmeni;
    private Long telCislo;
    private String email;
    private int administrator;

    public Uzivatel(String jmeno, String prijmeni, Long telCislo, String email, int administrator) {
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.telCislo = telCislo;
        this.email = email;
        this.administrator = administrator;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public Long getTelCislo() {
        return telCislo;
    }

    public void setTelCislo(Long telCislo) {
        this.telCislo = telCislo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAdministrator() {
        return administrator;
    }

    public void setAdministrator(int administrator) {
        this.administrator = administrator;
    }
}
