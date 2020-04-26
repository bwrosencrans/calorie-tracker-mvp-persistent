package org.launchcode.mealtracker.models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;



@Entity
@Table(name="meal")
public class Meal extends AbstractEntity {
    private String description;

    @ManyToMany()
    @JoinTable(
          name = "meal_item",
          joinColumns = {@JoinColumn(name = "item_id")},
          inverseJoinColumns = {@JoinColumn(name="meal_id")}
    )
    private List<FoodItem> foodItems = new ArrayList<>();

    private Date date;

    public Meal() {
        this.description = "";
        this.date = new Date();
    }

    public int getCalorieCount() {
        int result = 0;
        for(FoodItem item: this.getFoodItems()) {
            result += item.getCalorieCount();
        }

        return result;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}