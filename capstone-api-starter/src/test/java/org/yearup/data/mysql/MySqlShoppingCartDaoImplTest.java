package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.service.ShoppingCartService;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class MySqlShoppingCartDaoImplTest extends BaseDaoTestClass{
    private ShoppingCartService shoppingCartService;
    private MySqlShoppingCartDaoImpl mySqlShoppingCartDao;

    @Autowired
    public MySqlShoppingCartDaoImplTest(DataSource dataSource) {
        super(dataSource);
        this.dataSource = dataSource;
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
    }

    @BeforeEach
    public void setup() {
        mySqlShoppingCartDao = new MySqlShoppingCartDaoImpl(dataSource);
    }

    @DisplayName("Get user by ID")
    @Test
    public void getByUserId() {
        //arrange
        Integer userId = 9;
        Integer expected = userId;
        //act
        ShoppingCart actual = shoppingCartService.getByUserId(expected);
        //assert
        assertEquals(expected, actual);
    }

    /*
    @Test
    public void addProduct() {
        //arrange
        Integer userId = 9;
        Integer productId = 1;
        Integer quantity = 2;
        String name = "Organic Bananas";
        BigDecimal price = BigDecimal.valueOf(3.99);
        Integer categoryId = 1;
        String description = "Fresh organic bananas, perfect for snacking or smoothies.";
        String subCategory = "Organic";
        Integer stock = 100;
        boolean featured = true;
        String imageUrl = "bananas.jpg";
        Product expected = new Product(productId, name, price, categoryId, description, subCategory, stock, featured, imageUrl);
        //act
        addProduct();
        //assert
        assertEquals(expected, addProduct());
    }
    */

    @DisplayName("Get cart after adding products")
    @Test
    public void getCart(Principal principal) {
        //arrange
        Integer productId = 15;
        Integer quantity = 2;
        //act
        shoppingCartService.addProduct(productId, quantity, principal);
        //assert
        assertEquals(productId, shoppingCartService.getCart(principal));
    }

    @Test
    void updateCart() {
        //arrange

        //act

        //assert
    }

    @Test
    void getItemsByUserId() {
        //arrange

        //act

        //assert
    }

    @Test
    void clearCart() {
        //arrange

        //act

        //assert
    }
}