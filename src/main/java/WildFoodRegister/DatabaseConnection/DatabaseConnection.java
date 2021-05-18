package WildFoodRegister.DatabaseConnection;


import WildFoodRegister.controllers.LoginController;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    LoginController loginController = new LoginController();



    public Connection getConnection(){
       // String databaseName = "wild_food";
      //  String databaseUser = "root";
      //  String databasePassword = "Sch00lDatabase";
     //   String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
          //  databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            databaseLink = DriverManager.getConnection(loginController.listPripojeni.get(0).getUrl(), loginController.listPripojeni.get(0).getUzivatelDatabaze(), loginController.listPripojeni.get(0).getHeslo());


        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return databaseLink;
    }
}
