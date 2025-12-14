package org.yearup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.security.Principal;
import java.util.List;

@Service
public class ShoppingCartService {
    private ShoppingCartDao shoppingCartDao;

    @Autowired
    public ShoppingCartService(ShoppingCartDao shoppingCartDao) {
        this.shoppingCartDao = shoppingCartDao;
    }

    public ShoppingCart getByUserId(Integer userId){
        return shoppingCartDao.getByUserId(userId);
    }
    public List<ShoppingCart> getCart(Principal principal){
        return shoppingCartDao.getCart();
    }
    public ShoppingCart addProduct(Integer userId, Integer productId, Integer quantity){
        return shoppingCartDao.addProduct(userId, productId, quantity);
    }
    public void updateCart(Integer productId, ShoppingCartItem shoppingCartItem, Principal principal){
        shoppingCartDao.updateCart(productId, shoppingCartItem);
    }
    public void deleteCart(ShoppingCart shoppingCart, Principal principal){
        shoppingCartDao.deleteCart(shoppingCart);
    }
}
