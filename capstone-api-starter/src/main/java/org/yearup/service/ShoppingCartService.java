package org.yearup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;
import java.util.List;

@Service
public class ShoppingCartService {
    private ShoppingCartDao shoppingCartDao;
    private UserService userService;

    @Autowired
    public ShoppingCartService(ShoppingCartDao shoppingCartDao, UserService userService) {
        this.shoppingCartDao = shoppingCartDao;
        this.userService = userService;
    }

    public ShoppingCart getByUserId(Integer userId){
        return shoppingCartDao.getByUserId(userId);
    }
    public List<ShoppingCartItem> getCart(Integer userId){
        return shoppingCartDao.getCart(userId);
    }
    public ShoppingCart addProduct(Integer productId, Integer quantity, Principal principal){
       String username = principal.getName();
       User user = userService.getByUserName(username);
       Integer userId = user.getId();
        return shoppingCartDao.addProduct(userId, productId, quantity);
    }
    public void updateCart(Integer productId, ShoppingCartItem shoppingCartItem, Principal principal){
        String username = principal.getName();
        User user = userService.getByUserName(username);
        Integer userId = user.getId();
        shoppingCartDao.updateCart(userId, productId, shoppingCartItem);
    }
    public void deleteCart(ShoppingCart shoppingCart, Principal principal){
        String username = principal.getName();
        User user = userService.getByUserName(username);
        Integer userId = user.getId();
        shoppingCartDao.deleteCart(userId, shoppingCart);
    }
}
