package Database;

import LoginInterface.UserPage;
import User.UserController;

import java.sql.*;

public class UserDB {

    private Connection connection;
    private PreparedStatement ps;
    private ResultSet resultSet;

    public void add(String username, String password, String firstName, String lastName,
                    String email, String phone) throws SQLException {

        PreparedStatement psInsert;

        try {
            connection = DBUtil.getDataSource().getConnection();
            psInsert = connection.prepareStatement("INSERT INTO Users(username, pwd, firstName, lastName, email, phone) " +
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
        }
        catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Username or email is already taken!");
        }
    }

    public void get(String username, String password) throws SQLException {
        UserPage userPage = new UserPage();
        UserController userController = new UserController();

        try {
            connection = DBUtil.getDataSource().getConnection();
            ps = connection.prepareStatement("SELECT pwd FROM Users WHERE username = ?");
            ps.setString(1, username);
            resultSet = ps.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("\nUser not found! Try again!\n");
                userController.logIn();
            } else {
                while(resultSet.next()){
                    String pwd = resultSet.getString("pwd");
                    if(pwd.equals(password)){
                        userPage.accountMenu();
                    } else {
                        System.out.println("\nWrong password! Try again!\n");
                        userController.logIn();
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(String username) {
        UserController userController = new UserController();

        try{
            connection = DBUtil.getDataSource().getConnection();
            ps = connection.prepareStatement("DELETE FROM Users WHERE username = ?");
            ps.setString(1, username);
            ps.executeUpdate();

            if (resultSet == null){
                System.out.println("User not found! Try again!\n");
                userController.deleteUser();
            } else {
                System.out.println("Account deleted successfully!");
            }
        } catch (SQLException e){
           // e.getSQLState();
        }
    }

    public int getUserID(String username) {
        try{
            connection = DBUtil.getDataSource().getConnection();
            ps = connection.prepareStatement("SELECT * FROM Users WHERE username = ?");
            ps.setString(1, username);
            resultSet = ps.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt(1);
            }

        } catch (SQLException e){
            e.getMessage();
        }
        return 0;
    }
}
