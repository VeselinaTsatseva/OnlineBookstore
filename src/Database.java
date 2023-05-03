import java.sql.*;

public class Database {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/Bookstore",
                "root", "123456");
    }

    public static void close(Connection connection, PreparedStatement ps, ResultSet resultSet) {
        if(ps != null){
            try {
                ps.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        if(resultSet != null){
            try{
                resultSet.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
