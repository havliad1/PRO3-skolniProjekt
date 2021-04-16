package WildFoodRegister.controller;

public class StatisticsWindow {

    public MainController mainController;
   // public ObservableList<> listStatistiky;


    public StatisticsWindow() {
      //  this.listStatistiky = FXCollections.observableArrayList();
        this.mainController = new MainController();

    }


/*
    public void statisticsGenerator() {
        Surovina temp = null;

        System.out.println("Velikost listu surovin v generatoru statistiky je: " + mainController.listSurovin.size());

        try {
            for (int i = 0; i < mainController.listSurovin.size(); i++) {

                temp = mainController.listSurovin.get(i);
                System.out.println(temp.getDruh());
                //listStatistiky.add(temp);

                if (!listStatistiky.contains(temp)){
                    listStatistiky.add(temp);
                } else {
                    for(int x = 0; x<listStatistiky.size();x++){
                        if(listStatistiky.get(x).equals(temp)){
                            listStatistiky.get(x).setVaha(listStatistiky.get(x).getVaha() + temp.getVaha());
                        }
                    }
                }
            }

        } catch (Exception e){
            System.out.println("Chyba pri vygenerovani statistiky");
        }
    }



    public ObservableList<Surovina> getListStatistiky() {
        return listStatistiky;
    }

    public void setListStatistiky(ObservableList<Surovina> listStatistiky) {
        this.listStatistiky = listStatistiky;

    }

 */
}

