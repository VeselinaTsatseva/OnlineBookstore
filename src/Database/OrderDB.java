package Database;

import Book.Book;
import Order.Order;
import User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDB {

    private Connection connection;
    private PreparedStatement ps;
    private ResultSet resultSet;

    public OrderDB() {
    }

    public void addOrder(Order order){
        PreparedStatement psInsert;

        try {
            connection = DBUtil.getDataSource().getConnection();
            psInsert = connection.prepareStatement("INSERT INTO Orders(orderID, date_created, total, userID) " +
                    "VALUES (?, ?, ?, ?)");
            psInsert.setInt(1, order.getOrderID());
            psInsert.setDate(2, (java.sql.Date) order.getDate());
            psInsert.setDouble(3, order.getTotal());
            psInsert.setInt(4, order.getUserID());
            psInsert.executeUpdate();

            System.out.println("Order made successfully!");

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addItemToDB(Book book, Order order){
        PreparedStatement psInsert;

        try {
            connection = DBUtil.getDataSource().getConnection();
            psInsert = connection.prepareStatement("INSERT INTO OrderedItem(orderID, isbn, price, isRented) " +
                    "VALUES (?, ?, ?, ?)");
            psInsert.setInt(1, order.getOrderID());
            psInsert.setInt(2, book.getIsbn());
            psInsert.setDouble(3,book.getPrice());
            psInsert.setBoolean(4, book.getRented());
            psInsert.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void getOrderHistory(User user){
        try {
            connection = DBUtil.getDataSource().getConnection();
            ps = connection.prepareStatement("SELECT orderID, date_created, SUM(price)" +
                    "FROM orderhistory WHERE userID = ? GROUP BY orderID, date_created ORDER BY date_created" );
            ps.setInt(1, user.getUserID());
            resultSet = ps.executeQuery();

            while(resultSet.next()){
                System.out.printf("\nOrderID: %d --- Created on %tF --- Total: %.2flv\n", resultSet.getInt(1),
                        resultSet.getDate(2), resultSet.getBigDecimal(3));

                displayItemsFromOrder(connection, resultSet.getInt(1));
                System.out.println();
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void displayItemsFromOrder(Connection connection, int orderID) {
        try {
            ps = connection.prepareStatement("SELECT * FROM Books, OrderedItem " +
                    "WHERE OrderedItem.orderID = ? " +
                    "AND Books.isbn = OrderedItem.isbn");
            ps.setInt(1, orderID);
            resultSet = ps.executeQuery();

            while(resultSet.next()){
                System.out.printf("-> %s, %s - %.2flv - %s \n", resultSet.getString(2),
                        resultSet.getString(3), resultSet.getBigDecimal(9),
                        isRented(resultSet.getBoolean(10)));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String isRented(Boolean b) {
        return b ? "rented" : "bought";
    }
}
