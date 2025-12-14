package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yearup.data.OrderDao;
import org.yearup.models.Orders;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

@Repository
public class MySqlOrderDaoImpl extends MySqlDaoBase implements OrderDao {
    @Autowired
    public MySqlOrderDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
    @Override
    public Integer createOrder(Integer userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders (user_id, date, address, city, state, zip) SELECT user_id, NOW(), address, city, state, zip FROM profiles WHERE user_id = ?;", Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1, userId);

            int rows = preparedStatement.executeUpdate();

            System.out.println("Rows updated: " + rows);

            try (ResultSet keys = preparedStatement.getGeneratedKeys();) {

                while (keys.next()) {
                    System.out.println("Keys added: " + keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void addOrderLineItem(Integer orderId, ShoppingCartItem item) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO order_line_items (order_id, product_id, quantity, sales_price) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, item.getProductId());
            preparedStatement.setInt(3, item.getQuantity());
            preparedStatement.setBigDecimal(4, item.getPrice());

            int rows = preparedStatement.executeUpdate();

            System.out.println("Rows updated: " + rows);

            try (ResultSet keys = preparedStatement.getGeneratedKeys();) {

                while (keys.next()) {
                    System.out.println("Keys added: " + keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
