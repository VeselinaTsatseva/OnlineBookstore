package Order;

import Book.Book;

import java.util.Date;
import java.util.HashSet;

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

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HashSet<Book> getItems() {
        return items;
    }

    public void setItems(HashSet<Book> items) {
        this.items = items;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }


    public void addItem(Book book){
        items.add(book);
    }
}
