package khalid.com.example.demo5;


import java.sql.Connection;
import java.sql.DriverManager;



public class Databaseconnect {
public Connection databaseLink;

public Connection getConnection() {
    String databaseName = "mindmotion";
    String databaseUser = "root";
    String databasePassword = "Nc6xF76b!";
    String Url = "jdbc:mysql://localhost:3306/" + databaseName;


    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        databaseLink = DriverManager.getConnection(Url, databaseUser, databasePassword);

    } catch (Exception e) {
        e.printStackTrace();
        e.getCause();
    }
    return databaseLink;
}


}

