package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.security.Principal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MySqlShoppingCartDaoImpl extends MySqlDaoBase implements ShoppingCartDao {
    @Autowired
    public MySqlShoppingCartDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(Integer userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM shopping_cart WHERE user_id = ?;");
        ) { preparedStatement.setInt(1, userId);

            try(ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    return new ShoppingCart(resultSet.getInt("user_id"), resultSet.getInt("product_id"), resultSet.getInt("quantity"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<ShoppingCart> getCart() {
        List<ShoppingCart> sCart = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM shopping_cart;");

             ResultSet resultSet = preparedStatement.executeQuery();) {

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int productId = resultSet.getInt("product_id");
                int quantity = resultSet.getInt("quantity");

                ShoppingCart sCart2 = new ShoppingCart(userId, productId, quantity);
                sCart.add(sCart2);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sCart;
    }

    @Override
    public ShoppingCart addProduct(Integer userId, Integer productId, Integer quantity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, quantity);

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
    public void updateCart(Integer userId, Integer productId, ShoppingCartItem shoppingCartItem) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE shopping_cart SET quantity = ?" + " WHERE user_id = ? AND product_id = ?;")) {

            preparedStatement.setInt(1, shoppingCartItem.getQuantity());
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, productId);

            int rows = preparedStatement.executeUpdate();

            System.out.println("Rows updated: " + rows);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCart(Integer userId, ShoppingCart shoppingCart) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM shopping_cart WHERE user_id = ?;")) {

            preparedStatement.setInt(1, userId);

            int rows = preparedStatement.executeUpdate();

            System.out.println("Rows updated: " + rows);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
