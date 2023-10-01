package LoginInterface;

import User.UserController;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginPage {

    private UserController userC = new UserController();

    public LoginPage() {
    }

    public void menu() throws SQLException {

        System.out.print("---- Welcome! ----\n1.Log in.\n2.Create an account.\nEnter your choice: ");

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        switch (choice)
        {
            case (1):
                userC.logIn();
                break;

            case (2):
                userC.signUp();
                break;

            default:
                System.out.println("Wrong input!");
        }
    }
}
