import LoginInterface.LoginPage;

import java.sql.SQLException;

public class Bookstore {

    public static void main(String[] args) throws SQLException {

        LoginPage loginPage = new LoginPage();
        loginPage.menu();
    }
}
