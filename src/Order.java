import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

public class Order {

    private int orderID;
    private int userID;
    private double total;
    private Date date;
    private HashSet<Book> items = new HashSet<>();

    public Order() {
        this.orderID = 0;
        this.userID = 0;
        this.total = 0.0;
        this.date = null;
    }

    public Order(int orderID, int userID, Date date) {
        this.orderID = orderID;
        this.userID = userID;
        this.date = date;
    }

    public void makeOrder(){
        System.out.print("\nEnter the book's isbn: ");
        Scanner isbn = new Scanner(System.in);
        int bookIsbn = isbn.nextInt();

        addBookToOrder(bookIsbn);

        System.out.print("\n1.Add another book.\n2.Finalize order.\nEnter your choice: ");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        switch (choice){
            case(1):
                makeOrder();
                break;

            case(2):
                addOrderToDB();

                for (Book book : items){
                    updateQuantity(book, book.getQuantity() - 1);
                    addItemToDB(book);
                }
                break;

            default:
                System.out.println("Wrong input!");
        }
    }

    public void addBookToOrder(int bookIsbn){
        Book book = getBook(bookIsbn);
        checkQuantity(book);

        items.add(book);

        System.out.print("\nDo you want to:\n1.Buy the book.\n2.Rent the book.\nEnter your choice: ");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        switch (choice){
            case(1):
                book.setRented(false);
                total += book.getPrice();
                break;

            case(2):
                book.setRented(true);
                book.setPrice(0);
                total += book.getPrice();
                break;

            default:
                System.out.println("Wrong input!");
        }
    }

    public Book getBook(int isbn){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Book book = new Book();

        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("SELECT * FROM Books WHERE isbn = ?");
            ps.setInt(1, isbn);
            resultSet = ps.executeQuery();

            while (resultSet.next()){
                book = new Book(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4),
                        resultSet.getDouble(5));
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            Database.close(connection, ps, resultSet);
        }

        return book;
    }

    public void addItemToDB(Book book){
        Connection connection = null;
        PreparedStatement psInsert = null;

        try {
            connection = Database.getConnection();
            psInsert = connection.prepareStatement("INSERT INTO OrderedItem(orderID, isbn, price, isRented) " +
                    "VALUES (?, ?, ?, ?)");
            psInsert.setInt(1, orderID);
            psInsert.setInt(2, book.getIsbn());
            psInsert.setDouble(3,book.getPrice());
            psInsert.setBoolean(4, book.getRented());
            psInsert.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            Database.close(connection, psInsert, null);
        }
    }

    public void addOrderToDB(){
        Connection connection = null;
        PreparedStatement psInsert = null;

        try {
            connection = Database.getConnection();
            psInsert = connection.prepareStatement("INSERT INTO Orders(orderID, date_created, total, userID) " +
                    "VALUES (?, ?, ?, ?)");
            psInsert.setInt(1, orderID);
            psInsert.setDate(2, (java.sql.Date) date);
            psInsert.setDouble(3, total);
            psInsert.setInt(4, userID);
            psInsert.executeUpdate();

            System.out.println("Order made successfully!");

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            Database.close(connection, psInsert, null);
        }
    }

    public void checkQuantity(Book book){
        int quantity = book.getQuantity();

        if(quantity <= 0) {
            System.out.println("This book is not available now!\nPick another.");
            makeOrder();
        }
    }

    public void updateQuantity(Book book, int quantity){
        Connection connection = null;
        PreparedStatement psUpdate = null;

        try {
            connection = Database.getConnection();
            psUpdate = connection.prepareStatement("UPDATE Books SET quantity = ? WHERE isbn = ?");
            psUpdate.setInt(1, quantity);
            psUpdate. setInt(2, book.getIsbn());
            psUpdate.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            Database.close(connection, psUpdate, null);
        }
    }
}
