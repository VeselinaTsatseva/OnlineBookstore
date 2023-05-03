import java.sql.*;

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
        setPassword("");
        setFirstName("");
        setLastName("");
        setEmail("");
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
        this.password = password;
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
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public void signUp(String username, String password, String firstName,
                       String lastName, String email, String phone){
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

            System.out.println("Account created successfully!");
            System.out.println("Please log in to your account!");
            menu.menu();

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

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
