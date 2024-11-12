package com.example.myapp;

import com.example.myapp.model.Product;  // Updated import to reflect the correct package

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Test
    public void testGetPrice() {
        Product product = new Product();
        product.setPrice(100.0);
        double expectedPrice = 100.0;
        assertEquals(expectedPrice, product.getPrice(), "The price should be 100.0");
    }
}
