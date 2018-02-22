package com.example.eraky.myapplication;

import java.util.List;

/**
 * Created by mine on 09/02/18.
 */

public class CategoriesResponse {
    private String success;
    private List<Category> Categories;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Category> getCategories() {
        return Categories;
    }

    public void setCategories(List<Category> categories) {
        Categories = categories;
    }
}
