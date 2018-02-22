package com.example.eraky.myapplication;

import java.util.List;

/**
 * Created by mine on 09/02/18.
 */

public class ProductsResponse {

    private String success;
    private List<Product> Products;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        Products = products;
    }
}
