package LoginInterface;

import Book.BookController;
import Order.OrderController;
import User.User;
import User.UserController;

import java.sql.SQLException;
import java.util.Scanner;

public class UserPage {

    private User user = new User();
    private UserController userC = new UserController();
    private OrderController orderC = new OrderController();
    private BookController bookC = new BookController();

    public UserPage() throws SQLException {
    }

    public void accountMenu() throws SQLException {
        System.out.print("\n---- Client Menu ----\n1.View books.\n2.Search books.\n3.Make an order.\n" +
                "4.View order history.\n5.Delete account.\nEnter your choice: ");

        Scanner input = new Scanner(System.in);

        int choice = input.nextInt();

        switch (choice)
        {
            case (1):
                bookC.showBooks();
                accountMenu();
                break;

            case (2):
                bookC.searchBook();
                accountMenu();
                break;

            case (3):
                orderC.makeOrder();
                accountMenu();
                break;

            case (4):
                orderC.showOrderHistory();
                accountMenu();
                break;

            case (5):
                userC.deleteUser();
                break;

            default:
                System.out.println("Wrong input!");
        }
    }

}
