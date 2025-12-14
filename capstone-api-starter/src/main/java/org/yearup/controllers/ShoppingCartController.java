package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;
import org.yearup.service.ProductService;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import java.security.Principal;
import java.util.List;

// convert this class to a REST controller
// only logged-in users should have access to these actions
@RestController
@RequestMapping("cart")
@PreAuthorize("hasRole('ROLE_USER')")
@CrossOrigin
public class ShoppingCartController {
    // a shopping cart requires
    private ShoppingCartService shoppingCartService;
    private UserService userService;
    private ProductService productService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService, ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.productService = productService;
    }

    // each method in this controller requires a Principal object as a parameter
    @GetMapping
    public List<ShoppingCartItem> getCart(Principal principal) {
            // get the currently logged-in username
            String userName = principal.getName();
            // find database user by userId
            User user = userService.getByUserName(userName);
            // use the shoppingcartDao to get all items in the cart and return the cart
            return shoppingCartService.getCart(user.getId());
    }

    // add a POST method to add a product to the cart - the url should be
    // http://localhost:8080/cart/products/15 (15 is the productId to be added
    //POST http://localhost:8080/cart/products/15?quantity=2
    @PostMapping("/products/{productId}")
    public ShoppingCart addProduct(@PathVariable Integer productId, @RequestParam(defaultValue = "1") Integer quantity, Principal principal) {
        return shoppingCartService.addProduct(productId, quantity, principal);
    }

    // add a PUT method to update an existing product in the cart - the url should be
    // http://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
    @PutMapping("/products/{productId}")
    public void updateCart(@PathVariable Integer productId, @RequestBody ShoppingCartItem shoppingCartItem, Principal principal) {
        shoppingCartService.updateCart(productId, shoppingCartItem, principal);
    }
    // add a DELETE method to clear all products from the current users cart
    // http://localhost:8080/cart
    @DeleteMapping
    public void deleteCart(ShoppingCart shoppingCart, Principal principal){
        shoppingCartService.deleteCart(shoppingCart, principal);
    }
}