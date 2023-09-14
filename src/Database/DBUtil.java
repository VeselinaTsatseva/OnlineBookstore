package Database;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;

public class DBUtil {

    private static final String DBurl = "jdbc:mysql://localhost:3306/Bookstore";
    private static final String DBusername = "root";
    private static final String DBpassword = "123456";

    private static ComboPooledDataSource dataSource;

    static {
        try{
            dataSource = new ComboPooledDataSource();

            dataSource.setJdbcUrl(DBurl);
            dataSource.setUser(DBusername);
            dataSource.setPassword(DBpassword);

            dataSource.setMinPoolSize(10);
            dataSource.setMaxPoolSize(100);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource(){
        return dataSource;
    }

}
