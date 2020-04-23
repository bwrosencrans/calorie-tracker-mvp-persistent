package org.launchcode.mealtracker.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Meal extends AbstractEntity {
    private String description;

    @ManyToMany()
    @JoinTable(
          name = "meal_item",
          joinColumns = {@JoinColumn(name = "item_id")},
          inverseJoinColumns = {@JoinColumn(name="meal_id")}
    )
    private List<FoodItem> foodItems = new ArrayList<>();

    public Meal() {
        this.description = "";
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String location) {
        this.description = location;
    }
}