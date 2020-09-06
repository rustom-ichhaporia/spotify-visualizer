package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    File file = new File("src/main/resources/categories.json");

    ObjectMapper mapper = new ObjectMapper();
    CategoriesWrapper categoriesWrapper = mapper.readValue(file, CategoriesWrapper.class);
    Categories categoriesObj = categoriesWrapper.getCategories();
    List<Category> categories = categoriesObj.getAllCategories();
    for (Category category : categories) {
      System.out.println(category.getName());
    }
  }
}
