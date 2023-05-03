import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Menu {

    private User user = new User();


    public User getUserInfo(){
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

    public int getUserID(String username) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try{
            connection = Database.getConnection();
            ps = connection.prepareStatement("SELECT * FROM Users WHERE username = ?");
            ps.setString(1, username);
            resultSet = ps.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt(1);
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            Database.close(connection, ps, resultSet);
        }
        return 0;
    }

    public void menu() {
        User user = new User();
        System.out.print("---- Welcome! ----\n1.Log in.\n2.Create an account.\nEnter your choice: ");

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        switch (choice)
        {
            case (1):
                Scanner input2 = new Scanner(System.in);
                System.out.print("Username: ");
                user.setUsername(input2.nextLine());

                System.out.print("Password: ");
                user.setPassword(input2.nextLine());

                user.logIn(user.getUsername(), user.getPassword());
                break;

            case (2):
                user = getUserInfo();
                user.signUp(user.getUsername(), user.getPassword(), user.getFirstName(),
                        user.getLastName(), user.getEmail(), user.getPhone());
                break;

            default:
                System.out.println("Wrong input!");
        }
    }

    public void accountMenu() {
        System.out.print("\n---- Menu ----\n1.View books.\n2.Search books.\n3.Make an order.\n4.View order history.\nEnter your choice: ");

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        switch (choice)
        {
            case (1):
                Book.showBooks();
                accountMenu();
                break;
            case (2):
                Book.search();
                accountMenu();
                break;

            case (3):
                Random orderId = new Random();
                Scanner input2 = new Scanner(System.in);

                System.out.print("Enter you username again: ");
                user.setUsername(input2.nextLine());
                user.setUserID(getUserID(user.getUsername()));

                Order order = new Order(orderId.nextInt(1000), user.getUserID(),
                                        new Date(System.currentTimeMillis()));
                order.makeOrder();
                break;

            case (4): // user.getOrderHistory();
                break;

            default:
                System.out.println("Wrong input!");
        }
    }
}
