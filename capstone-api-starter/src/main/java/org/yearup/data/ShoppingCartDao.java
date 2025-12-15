package org.yearup.data;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.security.Principal;
import java.util.List;

@Component
public interface ShoppingCartDao
{
    ShoppingCart getByUserId(Integer userId);
    // add additional method signatures here
    List<ShoppingCartItem> getCart(Integer userId);
    ShoppingCart addProduct(Integer userId, Integer productId, Integer quantity);
    void updateCart(Integer userId, Integer productId, ShoppingCartItem shoppingCartItem);
    void deleteCart(Integer userId, ShoppingCart shoppingCart);
    List<ShoppingCartItem> getItemsByUserId(Integer userId);
    void clearCart(Integer userId);
}
