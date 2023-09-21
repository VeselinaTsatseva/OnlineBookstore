package Order;

import java.sql.SQLException;

public interface IOrderController {

    void makeOrder() throws SQLException;

    void addBookToOrder();

    void showOrderHistory();
}
