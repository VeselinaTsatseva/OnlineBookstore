package Database;

import Book.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDB {

    private Connection connection;
    private PreparedStatement ps;
    private ResultSet resultSet;

    public BookDB()  {
    }

    public void updateQuantity(Book book, int quantity){
        PreparedStatement psUpdate;

        try {
            connection = DBUtil.getDataSource().getConnection();
            psUpdate = connection.prepareStatement("UPDATE Books SET quantity = ? WHERE isbn = ?");
            psUpdate.setInt(1, quantity);
            psUpdate.setInt(2, book.getIsbn());
            psUpdate.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Book getBook(int isbn){
        Book book = new Book();

        try {
            connection = DBUtil.getDataSource().getConnection();
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
        }

        return book;
    }

    public void showBooks(){
        try{
            connection = DBUtil.getDataSource().getConnection();
            ps = connection.prepareStatement("SELECT * FROM Books");
            resultSet = ps.executeQuery();

            while(resultSet.next()){
                displayBookInfo(resultSet);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void searchByTitle(String title){
        try {
            connection = DBUtil.getDataSource().getConnection();
            ps = connection.prepareStatement("SELECT * FROM Books WHERE title = ?");
            ps.setString(1, title);
            resultSet = ps.executeQuery();

            while (resultSet.next()){
                displayBookInfo(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchByAuthor(String author){
        try {
            connection = DBUtil.getDataSource().getConnection();
            ps = connection.prepareStatement("SELECT * FROM Books WHERE author = ?");
            ps.setString(1, author);
            resultSet = ps.executeQuery();

            while (resultSet.next()){
                displayBookInfo(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayBookInfo(ResultSet resultSet) throws SQLException {
        System.out.printf("%s, %s - %.2flv,  isbn: %d", resultSet.getString(2),
                resultSet.getString(3), resultSet.getBigDecimal(5),
                resultSet.getInt(1));
        System.out.println();
    }
}
