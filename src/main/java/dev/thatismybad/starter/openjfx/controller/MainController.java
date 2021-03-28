package dev.thatismybad.starter.openjfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class represents main controller for business logic and work with UI
 */
public class MainController implements Initializable {

    public Produkt currentProdukt;

    public ObservableList<Produkt> listProduktu = FXCollections.observableArrayList(
            new Produkt(1,"Srnčí kližka", 16.4),
            new Produkt(2,"Srnčí kližka", 14.2),
            new Produkt(3,"Srnčí ořez - libový", 6.55),
            new Produkt(4,"Daněk - ...", 21.4),
            new Produkt(5,"Daněk - ...", 31.2),
            new Produkt(6,"tofu", 22.16)

    );
    // okok

    @FXML
    private TableView wholeTable;
    @FXML
    private TableColumn clID;
    @FXML
    private TableColumn clDruh;
    @FXML
    private TableColumn clVaha;
    @FXML
    private TextField txtID;
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


    

    private void updateWholeTable(){
        clID.setCellValueFactory(new PropertyValueFactory<Produkt, Integer>("ID"));
        clDruh.setCellValueFactory(new PropertyValueFactory<Produkt, String>("druh"));
        clVaha.setCellValueFactory(new PropertyValueFactory<Produkt, Integer>("vaha"));

        this.wholeTable.setItems(listProduktu);
    }


    int id;
    int barcodeID;
    String Druh;
    String barcodeDruh;
    Double Vaha;
    String barcodeVaha;

    public void odeber(){
        listProduktu.remove(wholeTable.getSelectionModel().getSelectedItem());



    }

    String barcode = "";

    public void BarcodeToText(){
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
                lblDruh.setText("neznámé");
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

        }

    public int idCounter(){
        int countedID = 0;
        for (int i = 0; i < listProduktu.size(); i++) {
            if(countedID < listProduktu.get(i).getID()){
                countedID = listProduktu.get(i).getID();
            }

        }
        countedID = countedID + 1;
        return countedID;
    }

    public void addMeat(){
        id = Integer.parseInt(txtID.getText());
        Druh = txtDruh.getText();
        Vaha = Double.parseDouble(txtVaha.getText());



        listProduktu.add(new Produkt(id,Druh, Vaha));
    }

    public void addBarcodeMeat(){
        id = idCounter();
        Druh = lblDruh.getText();
        Vaha = Double.parseDouble(lblVaha.getText());


        listProduktu.add(new Produkt(id,Druh, Vaha));
    }

    public void removeWithBarcode(){

        String removeBarcode = "";
        String removeDruh = "";
        Double removeVaha = 0.00;
        String stringRemoveVaha = "";

        removeBarcode = txtRemoveBarcode.getText();

        char[] barcodeDruhArray = new char[6];

        for (int i = 0; i < 6; i++) {
            barcodeDruhArray[i] = removeBarcode.charAt(i);
        }

        barcodeDruh = String.valueOf(barcodeDruhArray);

        if (barcodeDruh.equals("420101")){
            removeDruh.equals("Srnčí kližka");
        } else if( barcodeDruh.equals("210001")){
            removeDruh.equals("Srnčí ořez - libový");
        }
        else {
            removeDruh.equals("neznámé");
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

        System.out.println("Odebíraný druh je" + removeDruh);
        System.out.println("_____________________________________");
        System.out.println("Odebíraná váha je" + stringRemoveVaha);



        for (int i = 0; i < listProduktu.size() ; i++) {
            if (listProduktu.get(i).getDruh() == removeDruh && listProduktu.get(i).getVaha() == removeVaha){
                listProduktu.remove(i);
            }

        }

    }

    private void vypocitaniHmotnosti(){
        double hmotnost = 0;
        for (int i = 0; i < listProduktu.size() ; i++) {


        }
    }


    
    private void nacteniPrikladu(){

/*
        for (int i = 0; i < 10; i++) {
            listPrikladu.add(readFile.nacti());
        }

 */
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateWholeTable();

    }
}
