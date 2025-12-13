package org.yearup.data;

import org.springframework.stereotype.Component;
import org.yearup.models.ShoppingCart;

@Component
public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here
}
