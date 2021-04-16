package WildFoodRegister.controller;

import WildFoodRegister.ReadAndWrite.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;



/**
 * Class represents main controller for business logic and work with UI
 */
public class MainController implements Initializable {

    public StatisticsWindow statisticsWindow;
    public ObservableList<Surovina> listSurovin = FXCollections.observableArrayList();
    public ObservableList<Produkt> listProduktu = FXCollections.observableArrayList();
    public ObservableList<Surovina> listStatistiky = FXCollections.observableArrayList();



    public void statisticsGenerator() {
    /*
        for (Surovina statistika : listSurovin) {
            listStatistiky.add(new Surovina(statistika));
        }

     */

        Surovina statistika;

        boolean statKontrola = false;


        for (int i = 0; i < listSurovin.size(); i++) {

            // vybírá po jednom položky z listu surovin

            statistika = listSurovin.get(i);

            for (int j = 0; j < listStatistiky.size(); j++) {

                //porovnává položku z listu surovin se všemi položkami z listu statistiky ( po jednom)

                if (statistika.getDruh().equals(listStatistiky.get(j).getDruh())){

                    listStatistiky.get(j).setVaha(listStatistiky.get(j).getVaha() + statistika.getVaha());
                    statTable.refresh();

                    statKontrola = true;
                }
            }
            if (statKontrola == false){

                listStatistiky.add(new Surovina(statistika));
            }
            statKontrola = false;
        }
    }

    public void nacteniListu(){


        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {
            ResultSet resultSet = connectDB.createStatement().executeQuery("SELECT * FROM surovina");

            while (resultSet.next()){
                listSurovin.add(
                    new Surovina(
                    resultSet.getInt("SurovinaID"),
                    resultSet.getString("Druh"),
                    resultSet.getDouble("Vaha")
                ));

            }

        }catch (Exception e){

        }

    }

    @FXML
    private TableView wholeTable;
    @FXML
    private TableView produktTable;
    @FXML
    private TableColumn clVyrobek;
    @FXML
    private TableColumn clPocet;
    @FXML
    private TableColumn clSarze;
    @FXML
    private TableColumn clID;
    @FXML
    private TableColumn clDruh;
    @FXML
    private TableColumn clVaha;
    @FXML
    private TableView statTable;
    @FXML
    private TableColumn statDruh;
    @FXML
    private TableColumn statVaha;
    @FXML
    private Label lblID;
    @FXML
    private TextField txtDruh;
    @FXML
    private TextField txtVaha;
    @FXML
    private TextField txtBarcode;
    @FXML
    private Label lblDruh;
    @FXML
    private Label lblVaha;
    @FXML
    private TextField txtRemoveBarcode;
    @FXML
    private TextField txtProductBarcode;
    @FXML
    private TextField txtSarze;
    @FXML
    private TextField txtPocet;
    @FXML
    private Label lblProdukt;
    @FXML
    private Label lblStatus;
    @FXML
    private Label lblSurovinaStatus;
    @FXML
    private TextField txtOdebiranyPocet;
    @FXML
    private TextField txtSearchProduct;
    @FXML
    private TextField txtSearchSarze;
    @FXML
    private Label lblVysledneSuroviny;
    @FXML
    private Label lblVysledneProdukty;

    int id;
    String Druh;
    String barcodeDruh;
    Double Vaha;
    String barcodeVaha;

    public void updateWholeTable(){

        nacteniListu();

        clID.setCellValueFactory(new PropertyValueFactory<Surovina, Integer>("ID"));
        clDruh.setCellValueFactory(new PropertyValueFactory<Surovina, String>("druh"));
        clVaha.setCellValueFactory(new PropertyValueFactory<Surovina, Integer>("vaha"));

        this.wholeTable.setItems(listSurovin);
        lblID.setText(String.valueOf(idCounter()));


    }

    public void updateStatisticsTable(){

        statisticsGenerator();


        statDruh.setCellValueFactory(new PropertyValueFactory<Surovina, String>("druh"));
        statVaha.setCellValueFactory(new PropertyValueFactory<Surovina, Double>("vaha"));

        this.statTable.setItems(listStatistiky);

        lblVysledneProdukty.setText(String.valueOf(listProduktu.size()));
        lblVysledneSuroviny.setText(String.valueOf(listSurovin.size()));
    }

    public void updateProduktTable(){

        clVyrobek.setCellValueFactory(new PropertyValueFactory<Produkt, String>("vyrobek"));
        clPocet.setCellValueFactory(new PropertyValueFactory<Produkt, Double>("pocet"));
        clSarze.setCellValueFactory(new PropertyValueFactory<Produkt, Double>("sarze"));

        this.produktTable.setItems(listProduktu);
    }


    public void odeber(){


        boolean vysledek = ConfirmBox.display("Upozornění", "Opravdu chcete odebrat tuto položku?");


        if (vysledek == true){

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            int odebiraneID = 0;
            Surovina odebiranyProdukt = (Surovina)wholeTable.getSelectionModel().getSelectedItem();
            odebiraneID = odebiranyProdukt.getID();

            try {

                PreparedStatement odeber = connectDB.prepareStatement("DELETE FROM surovina WHERE SurovinaID = '" + odebiraneID + "'");
                odeber.executeUpdate();

            } catch (Exception e){

            }

            listSurovin.remove(wholeTable.getSelectionModel().getSelectedItem());


        } else {

        }
    }

    String barcode = "";

    public void BarcodeToText(){
        try {
            barcode = txtBarcode.getText();
            char[] barcodeDruhArray = new char[6];

            for (int i = 0; i < 6; i++) {
                barcodeDruhArray[i] = barcode.charAt(i);
            }

            barcodeDruh = String.valueOf(barcodeDruhArray);

            if (barcodeDruh.equals("420101")){
                lblDruh.setText("Srnčí kližka");
            } else if( barcodeDruh.equals("210001")){
                lblDruh.setText("Srnčí ořez - libový");
            }
            else {
                lblDruh.setText("Neznámé");
            }
            char[] barcodeVahaArray = new char[7];


            int j = 0;
            int k = 3;

            for (int i = 7; i < 9; i++) {
                barcodeVahaArray[j] = barcode.charAt(i);
                j = j+1;
            }
            barcodeVahaArray[2] = '.';
            for (int i = 9; i < 12; i++){
                barcodeVahaArray[k] = barcode.charAt(i);
                k = k+1;
            }

            barcodeVaha = String.valueOf(barcodeVahaArray);
            lblVaha.setText(barcodeVaha);

        } catch (Exception e){

        }

        }

    public int idCounter(){
        int countedID = 0;
        for (int i = 0; i < listSurovin.size(); i++) {
            if(countedID < listSurovin.get(i).getID()){
                countedID = listSurovin.get(i).getID();
            }

        }
        countedID = countedID + 1;
        return countedID;
    }

    public void addMeat(){
        id = idCounter();
        Druh = txtDruh.getText();
        Vaha = Double.parseDouble(txtVaha.getText());

        //přidání suroviny do databáze


        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {
            PreparedStatement pridej = connectDB.prepareStatement("INSERT INTO surovina (Druh, Vaha, SurovinaID, DodavatelID, SkladID) VALUES ('"+Druh+"', "+Vaha+" , "+id+", "+1+", "+1+")");

            pridej.executeUpdate();

            String zkouskaPridani = "SELECT count(1) FROM surovina WHERE Druh = '" + Druh + "'";
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(zkouskaPridani);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    System.out.println("Polozka uspesne pridana");
                } else {
                    System.out.println("Pridani polozky se nezdarilo");
                }

            }

        } catch (Exception e) {

        }



        listSurovin.add(new Surovina(id,Druh, Vaha));


        lblID.setText(String.valueOf(idCounter()));
        txtDruh.setText("");
        txtVaha.setText("");
    }

    public void addBarcodeMeat(){


        try {
            id = idCounter();
            Druh = lblDruh.getText();
            Vaha = Double.parseDouble(lblVaha.getText());

            if (lblDruh.getText().equals("Neznámé")){
                lblSurovinaStatus.setTextFill(Color.web("#ff4d4d"));
                lblSurovinaStatus.setText("Neznámá položka, zkuste znova");


            } else {

                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDB = connectNow.getConnection();

                try {
                    PreparedStatement pridej = connectDB.prepareStatement("INSERT INTO surovina (Druh, Vaha, SurovinaID, DodavatelID, SkladID) VALUES ('" + Druh + "', " + Vaha + " , " + id + ", " + 1 + ", " + 1 + ")");

                    pridej.executeUpdate();

                    String zkouskaPridani = "SELECT count(1) FROM surovina WHERE Druh = '" + Druh + "'";
                    Statement statement = connectDB.createStatement();
                    ResultSet queryResult = statement.executeQuery(zkouskaPridani);

                    while (queryResult.next()) {
                        if (queryResult.getInt(1) > 0) {
                            System.out.println("Polozka uspesne pridana");
                        } else {
                            System.out.println("Pridani polozky se nezdarilo");
                        }

                    }

                } catch (Exception e) {



                }

                listSurovin.add(new Surovina(id, Druh, Vaha));
                lblDruh.setText("");
                lblVaha.setText("");
                txtBarcode.setText("");
                lblID.setText(String.valueOf(idCounter()));
                txtBarcode.requestFocus();
                lblSurovinaStatus.setTextFill(Color.web("#29ae00"));
                lblSurovinaStatus.setText("Polozka uspesne pridana");


            }

        } catch (Exception e){

            lblSurovinaStatus.setTextFill(Color.web("#ff4d4d"));
            lblSurovinaStatus.setText("Neznámá položka, zkuste znova");

        }


    }

    public void removeWithBarcode(){

        try {
            String removeBarcode = "";
            String removeDruh = "";
            Double removeVaha = 0.000;
            String stringRemoveVaha = "";

            removeBarcode = txtRemoveBarcode.getText();

            char[] barcodeDruhArray = new char[6];

            for (int i = 0; i < 6; i++) {
                barcodeDruhArray[i] = removeBarcode.charAt(i);
            }

            barcodeDruh = String.valueOf(barcodeDruhArray);

            if (barcodeDruh.equals("420101")){
                removeDruh = "Srnčí kližka";
            } else if( barcodeDruh.equals("210001")){
                removeDruh = "Srnčí ořez - libový";
            }
            else {
                removeDruh = "neznámé";
            }

            char[] barcodeVahaArray = new char[7];

            int j = 0;
            int k = 3;

            for (int i = 7; i < 9; i++) {
                barcodeVahaArray[j] = removeBarcode.charAt(i);
                j = j+1;
            }
            barcodeVahaArray[2] = '.';
            for (int i = 9; i < 12; i++){
                barcodeVahaArray[k] = removeBarcode.charAt(i);
                k = k+1;
            }

            stringRemoveVaha = String.valueOf(barcodeVahaArray);
            removeVaha = Double.parseDouble(stringRemoveVaha);


            for (int i = 0; i < listSurovin.size() ; i++) {
                if (listSurovin.get(i).getDruh().equals(removeDruh) && listSurovin.get(i).getVaha() == removeVaha){

                    wholeTable.getSelectionModel().select(listSurovin.get(i));
                    wholeTable.scrollTo(wholeTable.getSelectionModel().getSelectedItem());

                    break;
                }

            }

            txtRemoveBarcode.setText("");

        } catch (Exception e){

        }
    }

    public void scanProduct(){
        String barcode = txtProductBarcode.getText();

        //Paštiky
        try {
            if (barcode == "8596066005728"){ lblProdukt.setText("Jelení paštika se švestkami");
            } else if (barcode.equals( "8596066005735" )){ lblProdukt.setText("Jelení paštika s brusinkami");
            } else if (barcode.equals( "8596066005742" )){ lblProdukt.setText("Dančí paštika se švestkami");
            } else if (barcode.equals( "8596066005759" )){ lblProdukt.setText("Dančí paštika s brusinkami");
            } else if (barcode.equals( "8596066005711" )){ lblProdukt.setText("Kančí paštika s pepřem");
            } else if (barcode.equals( "8596066005704" )){ lblProdukt.setText("Kančí paštika se škvarky");
            } else if (barcode.equals( "8596066005766" )){ lblProdukt.setText("Srnčí paštika s mandlemi");

                //Jerky 50g

            } else if (barcode.equals( "8596066005537" )){ lblProdukt.setText("jelení jerky s worchesterem 50 g");
            } else if (barcode.equals( "8596066005568" )){ lblProdukt.setText("dančí jerky s worchesterem 50 g");
            } else if (barcode.equals( "8596066005599" )){ lblProdukt.setText("kančí jerky s worchesterem 50 g ");
            } else if (barcode.equals( "8596066005810" )){ lblProdukt.setText("bažantí jerky solené 50 g");
            } else if (barcode.equals( "8596066005551" )){ lblProdukt.setText("dančí jerky solené 50 g");
            } else if (barcode.equals( "8596066005520" )){ lblProdukt.setText("jelení jerky solené 50 g");
            } else if (barcode.equals( "8596066005575" )){ lblProdukt.setText("dančí jerky pepř a chilli 50 g ");
            } else if (barcode.equals( "8596066005544" )){ lblProdukt.setText("jelení jerky pepř a chilli 50 g");
            } else if (barcode.equals( "8596066005605" )){ lblProdukt.setText("kančí jerky pepř a chilli 50 g");
            } else if (barcode.equals( "8596066005582" )){ lblProdukt.setText("kančí jerky solené 50 g");

                //Jerky 30g

            } else if (barcode.equals( "8596066005629" )){ lblProdukt.setText("jelení jerky s worchesterem 30 g");
            } else if (barcode.equals( "8596066005650" )){ lblProdukt.setText("dančí jerky s worchesterem 30 g");
            } else if (barcode.equals( "8596066005681" )){ lblProdukt.setText("kančí jerky s worchesterem 30 g");
            } else if (barcode.equals( "8596066005803" )){ lblProdukt.setText("bažantí jerky solené 30 g");
            } else if (barcode.equals( "8596066005643" )){ lblProdukt.setText("dančí jerky solené 30 g");
            } else if (barcode.equals( "8596066005612" )){ lblProdukt.setText("jelení jerky solené 30 g");
            } else if (barcode.equals( "8596066005667" )){ lblProdukt.setText("dančí jerky pepř a chilli 30 g");
            } else if (barcode.equals( "8596066005636" )){ lblProdukt.setText("jelení jerky pepř a chilli 30 g");
            } else if (barcode.equals( "8596066005698" )){ lblProdukt.setText("kančí jerky pepř a chilli 30 g");
            } else if (barcode.equals( "8596066005674" )){ lblProdukt.setText("kančí jerky solené 30 g");

            } else {
                lblProdukt.setText("Neznámá položka");
            }

        } catch (Exception e){

        }
    }

    public void addProductWithBarcode() {

        String barcodeProdukt = "";
        int barcodePocet = 0;
        int barcodeSarze = 0;
    /*
        String barcodeProdukt = lblProdukt.getText();
        int barcodePocet = Integer.parseInt(txtPocet.getText());
        int barcodeSarze = Integer.parseInt(txtSarze.getText());

     */

        boolean kontrola = false;

        if (lblProdukt.getText().isEmpty()){
            barcodeProdukt.equals("");
        } else {
            barcodeProdukt = lblProdukt.getText();
        }

        if (txtPocet.getText().isEmpty()){
            barcodePocet = 0;
        } else {
            barcodePocet = Integer.parseInt(txtPocet.getText());
        }

        if (txtSarze.getText().isEmpty()){
            barcodeSarze = 0;
        } else {
            barcodeSarze = Integer.parseInt(txtSarze.getText());
        }


        if (barcodeProdukt.equals("") && barcodePocet == 0 && barcodeSarze == 0){

            lblStatus.setText("Doplňte prosím požadované údaje");
            lblStatus.setTextFill(Color.web("#ff4d4d"));

        } else if (barcodeProdukt.equals("") && barcodePocet != 0 && barcodeSarze != 0) {

            lblStatus.setText("Oskenujte prosím čárový kód");
            lblStatus.setTextFill(Color.web("#ff4d4d"));
            txtProductBarcode.requestFocus();

        } else if (barcodeProdukt.equals("") && barcodePocet == 0 && barcodeSarze != 0) {

            lblStatus.setText("Oskenujte prosím čárový kód a doplňte počet");
            lblStatus.setTextFill(Color.web("#ff4d4d"));
            txtProductBarcode.requestFocus();

        } else if (!barcodeProdukt.equals("") && barcodePocet != 0 && barcodeSarze == 0){

            lblStatus.setText("Doplňte prosím šarži");
            lblStatus.setTextFill(Color.web("#ff4d4d"));
            txtSarze.requestFocus();

        } else if (!barcodeProdukt.equals("") && barcodePocet == 0 && barcodeSarze != 0){

            lblStatus.setText("Doplňte prosím počet a šarži");
            lblStatus.setTextFill(Color.web("#ff4d4d"));
            txtPocet.requestFocus();

        } else if (barcodeProdukt.equals("") && barcodePocet != 0 && barcodeSarze == 0){

            lblStatus.setText("Oskenujte prosím čárový kód a doplńte šarži");
            lblStatus.setTextFill(Color.web("#ff4d4d"));
            txtProductBarcode.requestFocus();

        } else if (!barcodeProdukt.equals("") && barcodePocet == 0 && barcodeSarze == 0) {

            lblStatus.setText("Doplňte prosím počet produktu");
            lblStatus.setTextFill(Color.web("#ff4d4d"));
            txtPocet.requestFocus();

        } else if (barcodeProdukt.equals("Neznámá položka")) {

            lblStatus.setText("Chyba při oskenování, zkuste prosím znovu");
            lblStatus.setTextFill(Color.web("#ff4d4d"));
            txtProductBarcode.setText("");
            txtProductBarcode.requestFocus();

        } else {

            if(listProduktu.size() == 0) {
            listProduktu.add(new Produkt(barcodeProdukt, barcodePocet, barcodeSarze));
            lblStatus.setText("Položka úspěšně přidána!");
            lblStatus.setTextFill(Color.web("#29ae00"));


             } else {
                 for (int i = 0; i < listProduktu.size(); i++) {

                    if (listProduktu.get(i).getVyrobek().equals(barcodeProdukt) && listProduktu.get(i).getSarze() == barcodeSarze) {

                        int meziPocet = listProduktu.get(i).getPocet();
                        listProduktu.get(i).setPocet(meziPocet + barcodePocet);
                        produktTable.refresh();
                        kontrola = true;
                        lblStatus.setText("Položka úspěšně přidána!");
                        lblStatus.setTextFill(Color.web("#29ae00"));

                        break;

                        }
                 }
                if (kontrola == false) {
                    listProduktu.add(new Produkt(barcodeProdukt, barcodePocet, barcodeSarze));
                    lblStatus.setText("Položka úspěšně přidána!");
                    lblStatus.setTextFill(Color.web("#29ae00"));


                     }
                }
            lblProdukt.setText("");
            txtProductBarcode.setText("");
            txtSarze.setText("");
            txtPocet.setText("");

            txtProductBarcode.requestFocus();

            }



    }

    public void removeProduct() {


        boolean vysledek = ConfirmBox.display("Upozornění", "Opravdu chcete odebrat tuto položku?");

        if (txtOdebiranyPocet.getText().isEmpty()){

        } else {

            if (vysledek == true){

                int pocetKodebrani = 0;
                pocetKodebrani = Integer.parseInt(txtOdebiranyPocet.getText());

                Produkt odebiranyProdukt = (Produkt)produktTable.getSelectionModel().getSelectedItem();

                if (odebiranyProdukt.getPocet() - pocetKodebrani < 0){

                } else if (odebiranyProdukt.getPocet() - pocetKodebrani == 0){
                    listProduktu.remove(odebiranyProdukt);
                } else {
                    odebiranyProdukt.setPocet(odebiranyProdukt.getPocet() - pocetKodebrani);
                    produktTable.refresh();
                }

                txtOdebiranyPocet.setText("");

            }

        }

    }

    public void searchProduct(){

        String vyhledavaciKod = "";
        String vyhledavanyProdukt = "";

        vyhledavaciKod = txtSearchProduct.getText();

        if (vyhledavaciKod .equals("8596066005728")){ vyhledavanyProdukt = "Jelení paštika se švestkami";
        } else if (vyhledavaciKod .equals("8596066005735")){ vyhledavanyProdukt = "Jelení paštika s brusinkami";
        } else if (vyhledavaciKod .equals("8596066005742")){ vyhledavanyProdukt = "Dančí paštika se švestkami";
        } else if (vyhledavaciKod .equals("8596066005759")){ vyhledavanyProdukt = "Dančí paštika s brusinkami";
        } else if (vyhledavaciKod .equals("8596066005711")){ vyhledavanyProdukt = "Kančí paštika s pepřem";
        } else if (vyhledavaciKod .equals("8596066005704")){ vyhledavanyProdukt = "Kančí paštika se škvarky";
        } else if (vyhledavaciKod .equals("8596066005766")){ vyhledavanyProdukt = "Srnčí paštika s mandlemi";

            //Jerky 50g

        } else if (vyhledavaciKod .equals("8596066005537")){ vyhledavanyProdukt = "jelení jerky s worchesterem 50 g";
        } else if (vyhledavaciKod .equals("8596066005568")){ vyhledavanyProdukt = "dančí jerky s worchesterem 50 g";
        } else if (vyhledavaciKod .equals("8596066005599")){ vyhledavanyProdukt = "kančí jerky s worchesterem 50 g ";
        } else if (vyhledavaciKod .equals("8596066005810")){ vyhledavanyProdukt = "bažantí jerky solené 50 g";
        } else if (vyhledavaciKod .equals("8596066005551")){ vyhledavanyProdukt = "dančí jerky solené 50 g";
        } else if (vyhledavaciKod .equals("8596066005520")){ vyhledavanyProdukt = "jelení jerky solené 50 g";
        } else if (vyhledavaciKod .equals("8596066005575")){ vyhledavanyProdukt = "dančí jerky pepř a chilli 50 g ";
        } else if (vyhledavaciKod .equals("8596066005544")){ vyhledavanyProdukt = "jelení jerky pepř a chilli 50 g";
        } else if (vyhledavaciKod .equals("8596066005605")){ vyhledavanyProdukt = "kančí jerky pepř a chilli 50 g";
        } else if (vyhledavaciKod .equals("8596066005582")){ vyhledavanyProdukt = "kančí jerky solené 50 g";

            //Jerky 30g

        } else if (vyhledavaciKod .equals("8596066005629")){ vyhledavanyProdukt = "jelení jerky s worchesterem 30 g";
        } else if (vyhledavaciKod .equals("8596066005650")){ vyhledavanyProdukt = "dančí jerky s worchesterem 30 g" ;
        } else if (vyhledavaciKod .equals("8596066005681")){ vyhledavanyProdukt = "kančí jerky s worchesterem 30 g";
        } else if (vyhledavaciKod .equals("8596066005803")){ vyhledavanyProdukt = "bažantí jerky solené 30 g";
        } else if (vyhledavaciKod .equals("8596066005643")){ vyhledavanyProdukt = "dančí jerky solené 30 g";
        } else if (vyhledavaciKod .equals("8596066005612")){ vyhledavanyProdukt = "jelení jerky solené 30 g";
        } else if (vyhledavaciKod .equals("8596066005667")){ vyhledavanyProdukt = "dančí jerky pepř a chilli 30 g";
        } else if (vyhledavaciKod .equals("8596066005636")){ vyhledavanyProdukt = "jelení jerky pepř a chilli 30 g";
        } else if (vyhledavaciKod .equals("8596066005698")){ vyhledavanyProdukt = "kančí jerky pepř a chilli 30 g";
        } else if (vyhledavaciKod .equals("8596066005674")){ vyhledavanyProdukt = "kančí jerky solené 30 g";

        } else {
            vyhledavanyProdukt.equals("Neznámá položka");
        }



        if (!txtSearchSarze.getText().isEmpty()){

            for (int i = 0; i < listProduktu.size(); i++) {

                if (listProduktu.get(i).getVyrobek().equals(vyhledavanyProdukt) && listProduktu.get(i).getSarze() == Integer.parseInt(txtSearchSarze.getText())){
                    produktTable.getSelectionModel().select(listProduktu.get(i));
                    produktTable.scrollTo(produktTable.getSelectionModel().getSelectedItem());

                    txtSearchSarze.setText("");
                    txtSearchProduct.setText("");

                    break;

                }

            }

        } else if (txtSearchSarze.getText().isEmpty()){

            for (int i = 0; i < listProduktu.size(); i++) {

                if (listProduktu.get(i).getVyrobek().equals(vyhledavanyProdukt)){

                    produktTable.getSelectionModel().select(listProduktu.get(i));
                    produktTable.scrollTo(produktTable.getSelectionModel().getSelectedItem());

                    txtSearchSarze.setText("");
                    txtSearchProduct.setText("");

                    break;

                }

            }

        } else {
            // neudělá nic
        }

    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateWholeTable();
        updateProduktTable();

        this.statisticsWindow = new StatisticsWindow();
        updateStatisticsTable();


      //  System.out.println("Reálná velikost listu surovin je: " + listSurovin.size());



    }

}
