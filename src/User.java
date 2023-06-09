import java.sql.*;
import java.util.regex.Pattern;

public class User {

    private int userID;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public User() {
        setUsername("");
        this.password = "";
        setFirstName("");
        setLastName("");
        this.email = "";
        setPhone("");
    }

    public User(String username, String password, String firstName, String lastName, String email, String phone) {
        setUsername(username);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhone(phone);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String pwdRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

        Pattern pattern = Pattern.compile(pwdRegex);

        if(pattern.matcher(password).matches()){
            this.password = password;
        } else {
            System.out.println("Password must contain at least: one digit, one lowercase and" +
                    " one uppercase latin letters and must be between 8 and 20 characters!");
            Menu menu = new Menu();
            menu.menu();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern pattern = Pattern.compile(emailRegex);

        if(pattern.matcher(email).matches()){
            this.email = email;
        } else {
            System.out.println("Email is not valid!");
            Menu menu = new Menu();
            menu.menu();
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public void signUp(String username, String password, String firstName,
                       String lastName, String email, String phone)  {
        Connection connection = null;
        PreparedStatement psInsert = null;
        Menu menu = new Menu();

        try {
            connection = Database.getConnection();
            psInsert =  connection.prepareStatement("INSERT INTO Users(username, pwd, firstName, lastName, email, phone) " +
                                                       "VALUES (?, ?, ?, ?, ?, ?)");
            psInsert.setString(1, username);
            psInsert.setString(2, password);
            psInsert.setString(3, firstName);
            psInsert.setString(4, lastName);
            psInsert.setString(5, email);
            psInsert.setString(6, phone);
            psInsert.executeUpdate();

            System.out.println("\nAccount created successfully!");
            System.out.println("Please log in to your account!\n");
            menu.menu();

        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Username or email is already taken!");
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            Database.close(connection, psInsert, null);
        }
    }

    public void logIn(String username, String password){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        Menu menu = new Menu();

        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("SELECT pwd FROM Users WHERE username = ?");
            ps.setString(1, username);
            resultSet = ps.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("Wrong username or password!");
            } else {
                while(resultSet.next()){
                    String pwd = resultSet.getString("pwd");
                    if(pwd.equals(password)){
                        menu.accountMenu();
                    } else {
                        System.out.println("Wrong username or password!");
                    }
                }
            }

        } catch (SQLException e){
            e.printStackTrace();

        } finally {
            Database.close(connection, ps, resultSet);
        }
    }

    public void showOrderHistory(){
        System.out.printf("\n---- %s's order history ----\n", getUsername());

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("SELECT orderID, date_created, SUM(price)" +
                                                 "FROM orderhistory WHERE userID = ? GROUP BY orderID, date_created ORDER BY date_created" );
            ps.setInt(1, userID);
            resultSet = ps.executeQuery();

            while(resultSet.next()){
                System.out.printf("\nOrderID: %d --- Created on %tF --- Total: %.2flv\n", resultSet.getInt(1),
                        resultSet.getDate(2), resultSet.getBigDecimal(3));

                displayItems(connection, resultSet.getInt(1));
                System.out.println();
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            Database.close(connection, ps, resultSet);
        }
    }

    public void displayItems(Connection connection, int orderID){
        PreparedStatement ps;
        ResultSet resultSet2;

        try {
            ps = connection.prepareStatement("SELECT * FROM Books, OrderedItem " +
                                                   "WHERE OrderedItem.orderID = ? " +
                                                   "AND Books.isbn = OrderedItem.isbn");
            ps.setInt(1, orderID);
            resultSet2 = ps.executeQuery();

            while(resultSet2.next()){
                System.out.printf("-> %s, %s - %.2flv - %s \n", resultSet2.getString(2),
                        resultSet2.getString(3), resultSet2.getBigDecimal(9),
                        isRented(resultSet2.getBoolean(10)));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String isRented(Boolean b) {
        return b ? "rented" : "bought";
    }

    public void deleteUser(){
        Connection connection = null;
        PreparedStatement ps = null;

        try{
            connection = Database.getConnection();
            ps = connection.prepareStatement("DELETE FROM Users WHERE userID = ?");
            ps.setInt(1, userID);
            ps.executeUpdate();

            System.out.println("Account deleted successfully!");

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            Database.close(connection, ps, null);
        }
    }

    }
