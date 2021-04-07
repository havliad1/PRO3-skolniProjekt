package WildFoodRegister.ReadAndWrite;


import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "DESKTOP-78JVBME\\SQLEXPRESS";
        String databaseUser = "Admin";
        String databasePassword = "Admin";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return databaseLink;
    }
}
