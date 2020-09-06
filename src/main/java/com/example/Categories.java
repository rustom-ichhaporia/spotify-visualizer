package com.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// Used to ignore several auxiliary JSON attributes that come from API call.
@JsonIgnoreProperties(ignoreUnknown = true)
public class Categories {
  public String href;
  public List<Category> items; // = new ArrayList<Category>();
  public int total;

  public String getHref() {
    return href;
  }

  public Category getCategory(int position) {
    return items.get(position);
  }

  public List<Category> getAllCategories() {
    return items;
  }

  public int getTotal() {
    return total;
  }
}
