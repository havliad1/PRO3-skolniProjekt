package WildFoodRegister.controllers;

import WildFoodRegister.AddonClasses.Produkt;
import WildFoodRegister.AddonClasses.RegisterProdukt;
import WildFoodRegister.AddonClasses.RegisterSurovina;
import WildFoodRegister.AddonClasses.Surovina;
import WildFoodRegister.DatabaseConnection.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;



public class MainController implements Initializable {

    public StatisticsWindow statisticsWindow;
    public LoginController loginController;
    public ObservableList<Surovina> listSurovin = FXCollections.observableArrayList();
    public ObservableList<Produkt> listProduktu = FXCollections.observableArrayList();
    public ObservableList<Surovina> listStatistiky = FXCollections.observableArrayList();
    public ObservableList<RegisterSurovina> listRegistrovanychSurovin = FXCollections.observableArrayList();
    public ObservableList<RegisterProdukt> listRegistrovanychProduktu = FXCollections.observableArrayList();
    public ObservableList<String> choice = FXCollections.observableArrayList("Produkty", "Suroviny");



    int id;
    String Druh;
    String barcodeDruh;
    Double Vaha;
    String barcodeVaha;
    String barcode = "";


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
    @FXML
    private TextField txtRegSurNazev;
    @FXML
    private TextField txtRegSurKod;
    @FXML
    private TextField txtRegProNazev;
    @FXML
    private TextField txtRegProKod;
    @FXML
    private Label lblRegistraceStatus;
    @FXML
    private Button logoffButton;
    @FXML
    private TableView registerTable;
    @FXML
    private TableColumn clPolozka;
    @FXML
    private TableColumn clCarovyKod;
    @FXML
    private ComboBox chooseBox;


    public void zobrazeniRegistraci(){


        String vyber = chooseBox.getSelectionModel().getSelectedItem().toString();

        if (vyber.equals("Produkty")){

            clPolozka.setCellValueFactory(new PropertyValueFactory<RegisterProdukt, String> ("nazev"));
            clCarovyKod.setCellValueFactory(new PropertyValueFactory<RegisterProdukt, Long> ("carovyKod"));
            this.registerTable.setItems(listRegistrovanychProduktu);

        } else if (vyber.equals("Suroviny")){

            clPolozka.setCellValueFactory(new PropertyValueFactory<RegisterSurovina, String> ("nazev"));
            clCarovyKod.setCellValueFactory(new PropertyValueFactory<RegisterSurovina, Long> ("carovyKod"));
            this.registerTable.setItems(listRegistrovanychSurovin);

        }

    }

    public void odeberRegistraci(){

        boolean vysledek = ConfirmBox.display("Upozornění", "Opravdu chcete odebrat tuto položku?");

        if (vysledek == true){

            if (chooseBox.getValue().equals("Produkty")){

                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDB = connectNow.getConnection();

                RegisterProdukt odebiranyProdukt = (RegisterProdukt) registerTable.getSelectionModel().getSelectedItem();

                try {

                    PreparedStatement odeber = connectDB.prepareStatement("DELETE FROM Registrovanyprodukt WHERE Nazev = '" + odebiranyProdukt.getNazev() + "'AND Carovykod = '" + odebiranyProdukt.getCarovyKod() + "'");
                    odeber.executeUpdate();
                    listRegistrovanychProduktu.remove(registerTable.getSelectionModel().getSelectedItem());

                } catch (Exception e){

                }

            } else if (chooseBox.getValue().equals("Suroviny")){

                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDB = connectNow.getConnection();

                RegisterSurovina odebiranaSurovina = (RegisterSurovina) registerTable.getSelectionModel().getSelectedItem();

                try {

                    PreparedStatement odeber = connectDB.prepareStatement("DELETE FROM Registrovanasurovina WHERE Nazev = '" + odebiranaSurovina.getNazev() + "'AND Carovykod = '" + odebiranaSurovina.getCarovyKod() + "'");
                    odeber.executeUpdate();
                    listRegistrovanychSurovin.remove(registerTable.getSelectionModel().getSelectedItem());

                } catch (Exception e){

                }
            }

        }

    }


    public void statisticsGenerator() {

        listStatistiky.clear();

        Surovina statistika;

        boolean statKontrola = false;


        for (int i = 0; i < listSurovin.size(); i++) {

            statistika = listSurovin.get(i);

            for (int j = 0; j < listStatistiky.size(); j++) {

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

    public void registraceSuroviny(){

        try {
            if (txtRegSurNazev.getText().isEmpty() && txtRegSurKod.getText().isEmpty()){
                lblRegistraceStatus.setText("Prosím doplňtě název položky a čárový kód");
                lblRegistraceStatus.setTextFill(Color.web("#ff4d4d"));
            } else if (!txtRegSurNazev.getText().isEmpty() && txtRegSurKod.getText().isEmpty()){
                lblRegistraceStatus.setText("Prosím doplňte čárový kód položky");
                lblRegistraceStatus.setTextFill(Color.web("#ff4d4d"));
            } else if (txtRegSurNazev.getText().isEmpty() && !txtRegSurKod.getText().isEmpty()){
                lblRegistraceStatus.setText("Prosím doplňte název položky");
                lblRegistraceStatus.setTextFill(Color.web("#ff4d4d"));
            } else if (txtRegSurKod.getText().length() != 13){
                lblRegistraceStatus.setText("Prosím oskenujte validní čárový kód");
                lblRegistraceStatus.setTextFill(Color.web("#ff4d4d"));
            } else {

                boolean vysledek = ConfirmBox.display("Upozornění", "Opravdu chcete registrovat tuto položku?");

                if (vysledek == true){

                    String nazevSuroviny = txtRegSurNazev.getText();
                    String carovyKod = txtRegSurKod.getText();
                    char[] castKodu = new char[6];
                    int vyslednyKod = 0;
                    int id = idCounter3();

                    if (listRegistrovanychSurovin.size() == 0){

                        for (int i = 0; i < 6; i++) {
                            castKodu[i] = carovyKod.charAt(i);
                        }
                        vyslednyKod = Integer.parseInt(String.valueOf(castKodu));

                        listRegistrovanychSurovin.add(new RegisterSurovina(nazevSuroviny,vyslednyKod,id));

                        lblRegistraceStatus.setText("Položka byla úspěšně registrována!");
                        lblRegistraceStatus.setTextFill(Color.web("#29ae00"));
                        txtRegSurKod.setText("");
                        txtRegSurNazev.setText("");
                        txtRegSurNazev.requestFocus();

                        DatabaseConnection connectNow = new DatabaseConnection();
                        Connection connectDB = connectNow.getConnection();

                        try {

                            PreparedStatement pridej = connectDB.prepareStatement("INSERT INTO Registrovanasurovina (Carovykod, Nazev, RegistrovanasurovinaID) VALUES (" + vyslednyKod + ", '" + nazevSuroviny + "' , " + id + ")");
                            pridej.executeUpdate();

                        } catch (Exception e){

                        }

                    } else {

                        for (int i = 0; i < 6; i++) {
                            castKodu[i] = carovyKod.charAt(i);
                        }
                        vyslednyKod = Integer.parseInt(String.valueOf(castKodu));
                        boolean kontrola = false;

                        for (int i = 0; i < listRegistrovanychSurovin.size(); i++) {

                            if (listRegistrovanychSurovin.get(i).getCarovyKod() == vyslednyKod){

                                kontrola = true;
                                break;
                            } else {
                                kontrola = false;
                            }

                        }

                        if (kontrola == false){

                            for (int i = 0; i < 6; i++) {
                                castKodu[i] = carovyKod.charAt(i);
                            }
                            vyslednyKod = Integer.parseInt(String.valueOf(castKodu));

                            listRegistrovanychSurovin.add(new RegisterSurovina(nazevSuroviny,vyslednyKod,id));

                            lblRegistraceStatus.setText("Položka byla úspěšně registrována!");
                            lblRegistraceStatus.setTextFill(Color.web("#29ae00"));
                            txtRegSurKod.setText("");
                            txtRegSurNazev.setText("");
                            txtRegSurNazev.requestFocus();

                            DatabaseConnection connectNow = new DatabaseConnection();
                            Connection connectDB = connectNow.getConnection();

                            try {

                                PreparedStatement pridej = connectDB.prepareStatement("INSERT INTO Registrovanasurovina (Carovykod, Nazev, RegistrovanasurovinaID) VALUES (" + vyslednyKod + ", '" + nazevSuroviny + "' , " + id + ")");
                                pridej.executeUpdate();

                            } catch (Exception e){

                            }

                        } else {

                            lblRegistraceStatus.setText("Položka je již registrována");
                            lblRegistraceStatus.setTextFill(Color.web("#ff4d4d"));

                        }

                    }

                } else {

                }

            }

        }catch (Exception e){

        }

    }

    public void registraceProduktu(){

        try {
            if (txtRegProNazev.getText().isEmpty() && txtRegProKod.getText().isEmpty()){
                lblRegistraceStatus.setText("Prosím doplňtě název položky a čárový kód");
                lblRegistraceStatus.setTextFill(Color.web("#ff4d4d"));
            } else if (!txtRegProNazev.getText().isEmpty() && txtRegProKod.getText().isEmpty()){
                lblRegistraceStatus.setText("Prosím doplňte čárový kód položky");
                lblRegistraceStatus.setTextFill(Color.web("#ff4d4d"));
            } else if (txtRegProNazev.getText().isEmpty() && !txtRegProKod.getText().isEmpty()){
                lblRegistraceStatus.setText("Prosím doplňte název položky");
                lblRegistraceStatus.setTextFill(Color.web("#ff4d4d"));
            } else if (txtRegProKod.getText().length() != 13){
                lblRegistraceStatus.setText("Prosím oskenujte validní čárový kód");
                lblRegistraceStatus.setTextFill(Color.web("#ff4d4d"));
            } else {

                boolean vysledek = ConfirmBox.display("Upozornění", "Opravdu chcete registrovat tuto položku?");

                if (vysledek == true){

                    String nazevProduktu = txtRegProNazev.getText();
                    long carovyKod = Long.parseLong(txtRegProKod.getText());
                    int id = idCounter4();

                    if (listRegistrovanychProduktu.size() == 0){

                        listRegistrovanychProduktu.add(new RegisterProdukt(nazevProduktu,carovyKod,id));

                        lblRegistraceStatus.setText("Položka byla úspěšně registrována!");
                        lblRegistraceStatus.setTextFill(Color.web("#29ae00"));
                        txtRegProKod.setText("");
                        txtRegProNazev.setText("");
                        txtRegProNazev.requestFocus();

                        DatabaseConnection connectNow = new DatabaseConnection();
                        Connection connectDB = connectNow.getConnection();

                        try {

                            PreparedStatement pridej = connectDB.prepareStatement("INSERT INTO Registrovanyprodukt (Carovykod, Nazev, RegistrovanyproduktID) VALUES (" + carovyKod + ", '" + nazevProduktu + "' , " + id + ")");
                            pridej.executeUpdate();

                        } catch (Exception e){

                        }

                    } else {

                        boolean kontrola = false;

                        for (int i = 0; i < listRegistrovanychProduktu.size(); i++) {

                            if (listRegistrovanychProduktu.get(i).getCarovyKod() == carovyKod){
                                kontrola = true;
                                break;
                            } else {
                                kontrola = false;
                            }

                        }

                        if (kontrola == false){

                            listRegistrovanychProduktu.add(new RegisterProdukt(nazevProduktu,carovyKod,id));

                            lblRegistraceStatus.setText("Položka byla úspěšně registrována!");
                            lblRegistraceStatus.setTextFill(Color.web("#29ae00"));
                            txtRegProKod.setText("");
                            txtRegProNazev.setText("");
                            txtRegProNazev.requestFocus();

                            DatabaseConnection connectNow = new DatabaseConnection();
                            Connection connectDB = connectNow.getConnection();

                            try {

                                PreparedStatement pridej = connectDB.prepareStatement("INSERT INTO Registrovanyprodukt (Carovykod, Nazev, RegistrovanyproduktID) VALUES (" + carovyKod + ", '" + nazevProduktu + "' , " + id + ")");
                                pridej.executeUpdate();

                            } catch (Exception e){

                            }

                        } else {

                            lblRegistraceStatus.setText("Položka je již registrována");
                            lblRegistraceStatus.setTextFill(Color.web("#ff4d4d"));

                        }

                    }

                } else {

                }

            }

        }catch (Exception e){

        }
    }


    public void nacteniRegistrovanychSurovin(){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {
            ResultSet resultSet = connectDB.createStatement().executeQuery("SELECT * FROM Registrovanasurovina");

            while (resultSet.next()){
                listRegistrovanychSurovin.add(
                        new RegisterSurovina(
                                resultSet.getString("Nazev"),
                                resultSet.getLong("CarovyKod"),
                                resultSet.getInt("RegistrovanasurovinaID")
                                )
                        );
            }

        }catch (Exception e){

        }

    }


    public void nacteniRegistrovanychProduktu(){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {
            ResultSet resultSet = connectDB.createStatement().executeQuery("SELECT * FROM Registrovanyprodukt");

            while (resultSet.next()){
                listRegistrovanychProduktu.add(
                        new RegisterProdukt(
                                resultSet.getString("Nazev"),
                                resultSet.getLong("Carovykod"),
                                resultSet.getInt("RegistrovanyproduktID")
                        )
                );
            }

        }catch (Exception e){

        }

    }

    public void nacteniSurovin(){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {
            ResultSet resultSet = connectDB.createStatement().executeQuery("SELECT * FROM Surovina");

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

    public void nacteniProduktu(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {
            ResultSet resultSet = connectDB.createStatement().executeQuery("SELECT * FROM Produkt");

            while (resultSet.next()){
                listProduktu.add(
                        new Produkt(
                                resultSet.getString("Nazev"),
                                resultSet.getInt("Pocet"),
                                resultSet.getInt("Sarze"),
                                resultSet.getInt("ProduktID")
                        ));

            }

        }catch (Exception e){

        }
    }

    public void updateWholeTable(){

        nacteniSurovin();
        nacteniRegistrovanychSurovin();
        nacteniRegistrovanychProduktu();


        clID.setCellValueFactory(new PropertyValueFactory<Surovina, Integer>("ID"));
        clDruh.setCellValueFactory(new PropertyValueFactory<Surovina, String>("druh"));
        clVaha.setCellValueFactory(new PropertyValueFactory<Surovina, Integer>("vaha"));

        this.wholeTable.setItems(listSurovin);
        lblID.setText(String.valueOf(idCounter()));

    }

    public void vypocetStatistiky() {
        int velikostSurovin = listSurovin.size();
        int velikostProduktu = 0;

        for (int i = 0; i < listProduktu.size(); i++) {
            velikostProduktu =  velikostProduktu + listProduktu.get(i).getPocet();
        }

        lblVysledneSuroviny.setText(String.valueOf(velikostSurovin));
        lblVysledneProdukty.setText(String.valueOf(velikostProduktu));
    }


    public void updateStatisticsTable(){

        statisticsGenerator();

        statDruh.setCellValueFactory(new PropertyValueFactory<Surovina, String>("druh"));
        statVaha.setCellValueFactory(new PropertyValueFactory<Surovina, Double>("vaha"));

        this.statTable.setItems(listStatistiky);

        vypocetStatistiky();

    }

    public void updateProduktTable(){

        nacteniProduktu();

        clVyrobek.setCellValueFactory(new PropertyValueFactory<Produkt, String>("vyrobek"));
        clPocet.setCellValueFactory(new PropertyValueFactory<Produkt, Double>("pocet"));
        clSarze.setCellValueFactory(new PropertyValueFactory<Produkt, Double>("sarze"));

        this.produktTable.setItems(listProduktu);

    }


    public void odeber(){

        try {

            boolean vysledek = ConfirmBox.display("Upozornění", "Opravdu chcete odebrat tuto položku?");


            if (vysledek == true){

                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDB = connectNow.getConnection();

                int odebiraneID = 0;
                Surovina odebiranyProdukt = (Surovina)wholeTable.getSelectionModel().getSelectedItem();
                odebiraneID = odebiranyProdukt.getID();

                try {

                    PreparedStatement odeber = connectDB.prepareStatement("DELETE FROM Surovina WHERE SurovinaID = '" + odebiraneID + "'");
                    odeber.executeUpdate();

                } catch (Exception e){

                }

                listSurovin.remove(wholeTable.getSelectionModel().getSelectedItem());


            } else {

            }

            vypocetStatistiky();
            statisticsGenerator();
            statTable.refresh();

        } catch (Exception e){

        }


    }


    public void BarcodeToText(){
     try {

         barcode = txtBarcode.getText();
         char[] barcodeDruhArray = new char[6];

         for (int i = 0; i < 6; i++) {
             barcodeDruhArray[i] = barcode.charAt(i);
         }

         barcodeDruh = String.valueOf(barcodeDruhArray);


         if (listRegistrovanychSurovin.size() == 0){
             lblDruh.setText("Neznámé");
         } else {
             for (int i = 0; i < listRegistrovanychSurovin.size(); i++) {

                 if (barcodeDruh.equals(String.valueOf(listRegistrovanychSurovin.get(i).getCarovyKod()))) {
                     lblDruh.setText(listRegistrovanychSurovin.get(i).getNazev());
                     break;
                 } else {
                     lblDruh.setText("Neznámé");
                 }
             }
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
         lblDruh.setText("Neznámé");

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

    public int idCounter2(){
        int countedID = 0;
        for (int i = 0; i < listProduktu.size(); i++) {
            if(countedID < listProduktu.get(i).getID()){
                countedID = listProduktu.get(i).getID();
            }

        }
        countedID = countedID + 1;

        return countedID;
    }

    public int idCounter3(){
        int countedID = 0;
        for (int i = 0; i < listRegistrovanychSurovin.size(); i++) {
            if(countedID < listRegistrovanychSurovin.get(i).getID()){
                countedID = listRegistrovanychSurovin.get(i).getID();
            }

        }
        countedID = countedID + 1;
        return countedID;
    }

    public int idCounter4(){
        int countedID = 0;
        for (int i = 0; i <listRegistrovanychProduktu.size(); i++) {
            if(countedID < listRegistrovanychProduktu.get(i).getID()){
                countedID =listRegistrovanychProduktu.get(i).getID();
            }

        }
        countedID = countedID + 1;
        return countedID;
    }

    public void addMeat(){
        try {

            id = idCounter();
            Druh = txtDruh.getText();
            Vaha = Double.parseDouble(txtVaha.getText());

            if (!txtDruh.getText().isEmpty() && !txtVaha.getText().isEmpty()){

                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDB = connectNow.getConnection();

                try {
                    PreparedStatement pridej = connectDB.prepareStatement("INSERT INTO Surovina (Druh, Vaha, SurovinaID) VALUES ('"+Druh+"', "+Vaha+" , "+id+")");
                    pridej.executeUpdate();

                    String zkouskaPridani = "SELECT count(1) FROM Surovina WHERE Druh = '" + Druh + "'";
                    Statement statement = connectDB.createStatement();
                    ResultSet queryResult = statement.executeQuery(zkouskaPridani);

                    while (queryResult.next()) {
                        if (queryResult.getInt(1) > 0) {
                            lblSurovinaStatus.setTextFill(Color.web("#29ae00"));
                            lblSurovinaStatus.setText("Položka úspěšně přidána");
                        } else {
                            lblSurovinaStatus.setTextFill(Color.web("#ff4d4d"));
                            lblSurovinaStatus.setText("Pridani polozky se nezdarilo");
                        }

                    }

                } catch (Exception e) {

                }
                listSurovin.add(new Surovina(id,Druh, Vaha));

                lblID.setText(String.valueOf(idCounter()));
                txtDruh.setText("");
                txtVaha.setText("");
                vypocetStatistiky();
                statisticsGenerator();
                statTable.refresh();

            } else {

            }

        } catch (Exception e){

        }


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

                    PreparedStatement pridej = connectDB.prepareStatement("INSERT INTO Surovina (Druh, Vaha, SurovinaID) VALUES ('" + Druh + "', " + Vaha + " , " + id + ")");
                    pridej.executeUpdate();

                    // testování přidání suroviny
                    String zkouskaPridani = "SELECT count(1) FROM Surovina WHERE Druh = '" + Druh + "'";
                    Statement statement = connectDB.createStatement();
                    ResultSet queryResult = statement.executeQuery(zkouskaPridani);

                    while (queryResult.next()) {
                        if (queryResult.getInt(1) > 0) {
                            lblSurovinaStatus.setTextFill(Color.web("#29ae00"));
                            lblSurovinaStatus.setText("Položka úspěšně přidána");
                        } else {
                            lblSurovinaStatus.setTextFill(Color.web("#ff4d4d"));
                            lblSurovinaStatus.setText("Pridani polozky se nezdarilo");
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


                vypocetStatistiky();
                statisticsGenerator();
                statTable.refresh();

            }

        } catch (Exception e){

            lblSurovinaStatus.setTextFill(Color.web("#ff4d4d"));
            lblSurovinaStatus.setText("Neznámá položka, zkuste znova");

        }


    }

    public void searchWithBarcode(){

        try {
            String removeBarcode = "";
            String removeDruh = "";
            Double removeVaha = 0.000;
            String stringRemoveVaha = "";
            String upravovanyKod = "";

            removeBarcode = txtRemoveBarcode.getText();

            char[] barcodeDruhArray = new char[6];

            for (int i = 0; i < 6; i++) {
                barcodeDruhArray[i] = removeBarcode.charAt(i);
            }

            barcodeDruh = String.valueOf(barcodeDruhArray);

            for (int i = 0; i < listRegistrovanychSurovin.size(); i++) {
                upravovanyKod = String.valueOf(listRegistrovanychSurovin.get(i).getCarovyKod());


                if(barcodeDruh.equals(upravovanyKod)){
                    removeDruh = listRegistrovanychSurovin.get(i).getNazev();
                    break;
                } else {
                    removeDruh = "Neznámé";
                }

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

        try {
            if (listRegistrovanychProduktu.size() == 0){
                lblProdukt.setText("Neznámá položka");
            } else {

                for (int i = 0; i < listRegistrovanychProduktu.size(); i++) {

                    if (barcode.equals(String.valueOf(listRegistrovanychProduktu.get(i).getCarovyKod()))){
                        lblProdukt.setText(listRegistrovanychProduktu.get(i).getNazev());
                        break;
                    } else {
                        lblProdukt.setText("Neznámá položka");
                    }
                }
            }

        } catch (Exception e){

        }

    }

    public void addProductWithBarcode() {

        try {

            String barcodeProdukt = "";
            int barcodePocet = 0;
            int barcodeSarze = 0;

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

            } else if (barcodeSarze < 0) {

                lblStatus.setText("Doplňte prosím validní šarži");
                lblStatus.setTextFill(Color.web("#ff4d4d"));
                txtSarze.requestFocus();

            } else if (barcodePocet < 0) {

                lblStatus.setText("Doplňte prosím validní počet");
                lblStatus.setTextFill(Color.web("#ff4d4d"));
                txtPocet.requestFocus();

            } else {

                if(listProduktu.size() == 0) {

                    int IDprodukt = idCounter2();

                    listProduktu.add(new Produkt(barcodeProdukt, barcodePocet, barcodeSarze,IDprodukt));


                    DatabaseConnection connectNow = new DatabaseConnection();
                    Connection connectDB = connectNow.getConnection();

                    try {

                        PreparedStatement myStmt = connectDB.prepareStatement("INSERT INTO Produkt (Nazev, Pocet, Sarze, ProduktID) VALUES ('"+barcodeProdukt+"', "+barcodePocet+" , "+barcodeSarze+" , "+IDprodukt+")");
                        myStmt.executeUpdate();
                        lblStatus.setText("Položka úspěšně přidána!");
                        lblStatus.setTextFill(Color.web("#29ae00"));

                    }catch (Exception e){

                        lblStatus.setText("Položku se nepodařilo přidat");
                        lblStatus.setTextFill(Color.web("#ff4d4d"));

                    }


                } else {
                    for (int i = 0; i < listProduktu.size(); i++) {

                        if (listProduktu.get(i).getVyrobek().equals(barcodeProdukt) && listProduktu.get(i).getSarze() == barcodeSarze) {

                            int meziPocet = listProduktu.get(i).getPocet();
                            int vypocet = meziPocet + barcodePocet;
                            listProduktu.get(i).setPocet(vypocet);
                            produktTable.refresh();
                            kontrola = true;


                            DatabaseConnection connectNow = new DatabaseConnection();
                            Connection connectDB = connectNow.getConnection();

                            try {

                                PreparedStatement uprav = connectDB.prepareStatement("UPDATE Produkt SET Pocet = '" + vypocet + "' WHERE Nazev = '" + listProduktu.get(i).getVyrobek() + "'AND Sarze = '" + listProduktu.get(i).getSarze() + "'");
                                uprav.executeUpdate();

                                lblStatus.setText("Položka úspěšně přidána!");
                                lblStatus.setTextFill(Color.web("#29ae00"));


                            }catch (Exception e){

                                lblStatus.setText("Položku se nepodařilo přidat");
                                lblStatus.setTextFill(Color.web("#ff4d4d"));

                            }


                            break;

                        }
                    }
                    if (kontrola == false) {

                        int IDprodukt = idCounter2();


                        DatabaseConnection connectNow = new DatabaseConnection();
                        Connection connectDB = connectNow.getConnection();

                            try {

                                PreparedStatement pridej = connectDB.prepareStatement("INSERT INTO Produkt (Nazev, Pocet, Sarze, ProduktID) VALUES ('" + barcodeProdukt + "', " + barcodePocet + " , " + barcodeSarze + " , " + IDprodukt + ")");
                                pridej.executeUpdate();
                                listProduktu.add(new Produkt(barcodeProdukt, barcodePocet, barcodeSarze, IDprodukt));

                                lblStatus.setText("Položka úspěšně přidána!");
                                lblStatus.setTextFill(Color.web("#29ae00"));

                            }catch (Exception e){

                                lblStatus.setText("Položku se nepodařilo přidat");
                                lblStatus.setTextFill(Color.web("#ff4d4d"));

                            }


                    }
                }
                lblProdukt.setText("");
                txtProductBarcode.setText("");
                txtSarze.setText("");
                txtPocet.setText("");

                txtProductBarcode.requestFocus();
                vypocetStatistiky();
                statisticsGenerator();
                statTable.refresh();

            }

        } catch (Exception e){

        }
    }

    public void removeProduct() {
        try {

            boolean vysledek = ConfirmBox.display("Upozornění", "Opravdu chcete odebrat tuto položku?");

            if (txtOdebiranyPocet.getText().isEmpty()){

            } else if (Integer.parseInt(txtOdebiranyPocet.getText()) < 0) {


            } else {

                if (vysledek == true){

                    int pocetKodebrani = 0;
                    pocetKodebrani = Integer.parseInt(txtOdebiranyPocet.getText());

                    Produkt odebiranyProdukt = (Produkt)produktTable.getSelectionModel().getSelectedItem();

                    if (odebiranyProdukt.getPocet() - pocetKodebrani < 0){

                    } else if (odebiranyProdukt.getPocet() - pocetKodebrani == 0){
                        listProduktu.remove(odebiranyProdukt);

                        DatabaseConnection connectNow = new DatabaseConnection();
                        Connection connectDB = connectNow.getConnection();

                        String odebiranyNazev = odebiranyProdukt.getVyrobek();
                        int odebiranyPocet = odebiranyProdukt.getPocet();
                        int odebiranaSarze = odebiranyProdukt.getSarze();

                        try {

                            PreparedStatement odeber = connectDB.prepareStatement("DELETE FROM Produkt WHERE Nazev = '" + odebiranyNazev + "' AND Pocet = '" + odebiranyPocet + "'AND Sarze = '" + odebiranaSarze + "'");
                            odeber.executeUpdate();

                        } catch (Exception e){

                        }


                    } else {

                        int vyslednyPocet = odebiranyProdukt.getPocet() - pocetKodebrani;
                        odebiranyProdukt.setPocet(vyslednyPocet);
                        produktTable.refresh();

                        DatabaseConnection connectNow = new DatabaseConnection();
                        Connection connectDB = connectNow.getConnection();

                        String upravenyNazev = odebiranyProdukt.getVyrobek();
                        int upravenaSarze = odebiranyProdukt.getSarze();


                        try {

                            PreparedStatement uprav = connectDB.prepareStatement("UPDATE Produkt SET Pocet = '" + vyslednyPocet + "' WHERE Nazev = '" + upravenyNazev + "'AND Sarze = '" + upravenaSarze + "'");
                            uprav.executeUpdate();

                        } catch (Exception e){

                        }

                    }

                    txtOdebiranyPocet.setText("");
                }

            }


            vypocetStatistiky();
            statisticsGenerator();
            statTable.refresh();

        } catch (Exception e){

        }
    }

    public void searchProduct(){

        String vyhledavaciKod = "";
        String vyhledavanyProdukt = "";

        vyhledavaciKod = txtSearchProduct.getText();


        for (int i = 0; i < listRegistrovanychProduktu.size(); i++) {

            if (vyhledavaciKod.equals(String.valueOf(listRegistrovanychProduktu.get(i).getCarovyKod()))){
                vyhledavanyProdukt = listRegistrovanychProduktu.get(i).getNazev();
            } else {
                vyhledavanyProdukt.equals("Neznámá položka");
            }

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

        }

    }

    public void odhlasit(){

        try {
            Stage cancelMainStage = (Stage) logoffButton.getScene().getWindow();
            Stage stage1;

            Parent root1 = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));

            stage1 = new Stage();

            stage1.setScene(new Scene(root1));
            stage1.setResizable(false);
            stage1.setTitle("WildFood login v1.0");
            stage1.show();

            cancelMainStage.close();




        }catch (Exception e){

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateWholeTable();
        updateProduktTable();

        this.statisticsWindow = new StatisticsWindow();
        updateStatisticsTable();

        chooseBox.setItems(choice);

    }

}
