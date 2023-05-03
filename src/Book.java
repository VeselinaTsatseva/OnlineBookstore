import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Book {

    private int isbn;
    private String title;
    private String author;
    private int quantity;
    private double price;
    private Boolean isRented;

    public Book(){
        setIsbn(0);
        setTitle("");
        setAuthor("");
        setQuantity(0);
        setPrice(0);
        setRented(false);
    }

    public Book(int isbn, String title, String author, int quantity, double price) {
        setIsbn(isbn);
        setTitle(title);
        setAuthor(author);
        setQuantity(quantity);
        setPrice(price);
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Boolean getRented() {
        return isRented;
    }

    public void setRented(Boolean rented) {
        isRented = rented;
    }

    public static void showBooks(){
        System.out.println("---- Books ----");

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try{
            connection = Database.getConnection();
            ps = connection.prepareStatement("SELECT * FROM Books");
            resultSet = ps.executeQuery();

            while(resultSet.next()){
                displayBook(resultSet);
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            Database.close(connection, ps, resultSet);
        }
    }

    public static void displayBook(ResultSet resultSet) throws SQLException {
        System.out.printf("%s, %s - %.2flv,  isbn: %d", resultSet.getString(2), resultSet.getString(3), resultSet.getBigDecimal(5), resultSet.getInt(1));
        System.out.println();
    }

    public static void search(){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        System.out.print("\nSearch by: \n1.Book title.\n2.Author.\nEnter your choice: ");
        Scanner input = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);

        try {
            connection = Database.getConnection();
            int choice = input.nextInt();

            switch (choice) {
                case (1):
                    System.out.print("Enter book title: ");
                    String title = input2.nextLine();

                    ps = connection.prepareStatement("SELECT * FROM Books WHERE title = ?");
                    ps.setString(1, title);
                    resultSet = ps.executeQuery();

                    while (resultSet.next()){
                        displayBook(resultSet);
                    }
                    break;
                case (2):
                    System.out.print("Enter book author: ");
                    String author = input2.nextLine();

                    ps = connection.prepareStatement("SELECT * FROM Books WHERE author = ?");
                    ps.setString(1, author);
                    resultSet = ps.executeQuery();

                    while (resultSet.next()){
                        displayBook(resultSet);
                    }
                    break;
                default:
                    System.out.print("Wrong input!");
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            Database.close(connection, ps, resultSet);
        }
    }


    @Override
    public String toString() {
        return "Book{" +
                "isbn=" + isbn +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", isRented=" + isRented +
                '}';
    }
}
