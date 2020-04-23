package org.launchcode.mealtracker.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class FoodItem extends AbstractEntity {

    @NotNull
    @Column(name="calorie_count")
    private int calorieCount;

    public FoodItem() {
    }

    public FoodItem(String name, int calorieCount) {
        super();
        this.calorieCount = calorieCount;
        this.setName(name);
    }

    public int getCalorieCount() {
        return calorieCount;
    }

    public void setCalorieCount(int calorieCount) {
        this.calorieCount = calorieCount;
    }
}
