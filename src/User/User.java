package User;

import LoginInterface.LoginPage;

import java.sql.SQLException;
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

    public User(String username, String password, String firstName, String lastName, String email, String phone) throws SQLException {
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

    public void setPassword(String password) throws SQLException {
        String pwdRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

        Pattern pattern = Pattern.compile(pwdRegex);

        if(pattern.matcher(password).matches()){
            this.password = password;
        } else {
            System.out.println("Password must contain at least: one digit, one lowercase and" +
                    " one uppercase latin letters and must be between 8 and 20 characters!");
            LoginPage menu = new LoginPage();
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

    public void setEmail(String email) throws SQLException {

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern pattern = Pattern.compile(emailRegex);

        if(pattern.matcher(email).matches()){
            this.email = email;
        } else {
            System.out.println("Email is not valid!");
            LoginPage menu = new LoginPage();
            menu.menu();
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
