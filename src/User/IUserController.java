package User;

import java.sql.SQLException;

public interface IUserController {

    User getUserInfo() throws SQLException;

    void signUp() throws SQLException;

    void logIn() throws SQLException;

    void deleteUser() throws SQLException;
}
