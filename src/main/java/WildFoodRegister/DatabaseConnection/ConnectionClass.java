package WildFoodRegister.DatabaseConnection;

public class ConnectionClass {

    private String url;
    private String nazevDatabaze;
    private String heslo;
    private String uzivatelDatabaze;

    public ConnectionClass(String url, String nazevDatabaze, String heslo, String uzivatelDatabaze) {
        this.url = url;
        this.nazevDatabaze = nazevDatabaze;
        this.heslo = heslo;
        this.uzivatelDatabaze = uzivatelDatabaze;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNazevDatabaze() {
        return nazevDatabaze;
    }

    public void setNazevDatabaze(String nazevDatabaze) {
        this.nazevDatabaze = nazevDatabaze;
    }

    public String getHeslo() {
        return heslo;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
    }

    public String getUzivatelDatabaze() {
        return uzivatelDatabaze;
    }

    public void setUzivatelDatabaze(String uzivatelDatabaze) {
        this.uzivatelDatabaze = uzivatelDatabaze;
    }
}
