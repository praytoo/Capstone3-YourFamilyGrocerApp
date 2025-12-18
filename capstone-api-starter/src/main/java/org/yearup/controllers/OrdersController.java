package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Orders;
import org.yearup.models.User;
import org.yearup.service.OrderService;
import org.yearup.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("orders")
@CrossOrigin
public class OrdersController {
    private OrderService orderService;
    private UserService userService;

    @Autowired
    public OrdersController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    //method for check out
    //using principal to get the user
    @PostMapping
    public Orders checkOutOrder(Principal principal) {
        String username = principal.getName();
        User user = userService.getByUserName(username);
        return orderService.checkOutOrder(user);
    }

    //method for order history
    @GetMapping("/history")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Orders> orderHistory(){
        return orderService.orderHistory();
    }
}
