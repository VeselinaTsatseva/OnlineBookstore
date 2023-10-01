package User;

import Database.OrderDB;
import Database.UserDB;
import LoginInterface.LoginPage;

import java.sql.SQLException;
import java.util.Scanner;

public class UserController implements IUserController {

    private User user = new User();
    private UserDB userDB = new UserDB();
    private OrderDB orderDB = new OrderDB();

    public UserController() {
    }

    @Override
    public User getUserInfo() throws SQLException {
        Scanner input = new Scanner(System.in);

        System.out.print("Username: ");
        user.setUsername(input.nextLine());

        System.out.print("Password: ");
        user.setPassword(input.nextLine());

        System.out.print("First name: ");
        user.setFirstName(input.nextLine());

        System.out.print("Last name: ");
        user.setLastName(input.nextLine());

        System.out.print("Email: ");
        user.setEmail(input.nextLine());

        System.out.print("Phone: ");
        user.setPhone(input.nextLine());

        return user;
    }

    @Override
    public void signUp() throws SQLException {
        LoginPage loginPage = new LoginPage();

        user = getUserInfo();
        userDB.add(user.getUsername(), user.getPassword(), user.getFirstName(),
                user.getLastName(), user.getEmail(), user.getPhone());

        loginPage.menu();
    }

    @Override
    public void logIn() throws SQLException {
        Scanner input = new Scanner(System.in);

        System.out.print("Username: ");
        String username = input.nextLine();

        System.out.print("Password: ");
        String pass = input.nextLine();

        userDB.get(username, pass);
    }

    @Override
    public void deleteUser() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your username again: ");
        String username = input.nextLine();

        userDB.delete(username);

        orderDB.deleteOrder(userDB.getUserID(username));
    }
}
