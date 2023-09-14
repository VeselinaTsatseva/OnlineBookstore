package Book;

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

}
