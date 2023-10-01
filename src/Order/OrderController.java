package Order;

import Book.Book;
import Database.BookDB;
import Database.OrderDB;
import Database.UserDB;
import User.User;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class OrderController implements IOrderController{

    private Order order = new Order();
    private User user = new User();
    private BookDB bookDB = new BookDB();
    private UserDB userDB = new UserDB();
    private OrderDB orderDB = new OrderDB();

    public OrderController() {
    }

    @Override
    public void makeOrder() {
        Random orderId = new Random();
        Scanner input = new Scanner(System.in);

        System.out.print("Enter you username again: ");
        user.setUsername(input.nextLine());
        user.setUserID(userDB.getUserID(user.getUsername()));

        order = new Order(orderId.nextInt(1000), user.getUserID(), new Date(System.currentTimeMillis()));

        addBookToOrder();

        System.out.print("\n1.Add another book.\n2.Finalize order.\nEnter your choice: ");
        Scanner input2 = new Scanner(System.in);
        int choice = input2.nextInt();

        switch (choice){
            case(1):
                addBookToOrder();

            case(2):
                orderDB.addOrder(order);

                for (Book book : order.getItems()){
                    bookDB.updateQuantity(book, book.getQuantity() - 1);
                    orderDB.addItemToDB(book, order);
                }
                break;

            default:
                System.out.println("Wrong input!");
        }
    }

    @Override
    public void addBookToOrder(){
        System.out.print("\nEnter the book's isbn: ");
        Scanner isbn = new Scanner(System.in);
        int bookIsbn = isbn.nextInt();

        Book book = bookDB.getBook(bookIsbn);

        if(book.getQuantity() <= 0) {
            System.out.println("This book is not available now!\nPick another.");
            addBookToOrder();
        }
        order.addItem(book);

        System.out.print("\nDo you want to:\n1.Buy the book.\n2.Rent the book.\nEnter your choice: ");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        switch (choice){
            case(1):
                book.setRented(false);
                order.setTotal(order.getTotal() + book.getPrice());
                break;

            case(2):
                book.setRented(true);
                book.setPrice(0);
                order.setTotal(order.getTotal() + book.getPrice());
                break;

            default:
                System.out.println("Wrong input!");
        }
    }

    @Override
    public void showOrderHistory() {
        Scanner input3 = new Scanner(System.in);

        System.out.print("Enter you username again: ");
        user.setUsername(input3.nextLine());
        user.setUserID(userDB.getUserID(user.getUsername()));

        System.out.printf("\n---- %s's order history ----\n", user.getUsername());

        orderDB.getOrderHistory(user);
    }
}
