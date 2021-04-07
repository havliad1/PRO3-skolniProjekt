package WildFoodRegister.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StatisticsWindow {

    public MainController mainController;
    public ObservableList<Produkt> listStatistiky;


    public StatisticsWindow() {
        this.listStatistiky = FXCollections.observableArrayList();
        this.mainController = new MainController();

    }

    // TODO Nevytvářet duplicitní druhy, ale instantně přidat mezi již existující druhy

    public void statisticsGenerator() {
        try {
            for (int i = 0; i < mainController.listProduktu.size(); i++) {
                listStatistiky.add(mainController.listProduktu.get(i));
            }


            } catch (Exception e){
                System.out.println("Chyba pri vygenerovani statistiky");
            }


        }




    public ObservableList<Produkt> getListStatistiky() {
        return listStatistiky;
    }

    public void setListStatistiky(ObservableList<Produkt> listStatistiky) {
        this.listStatistiky = listStatistiky;


    }
}

