package org.launchcode.mealtracker.controllers;

import org.launchcode.mealtracker.dao.FoodItemRepository;
import org.launchcode.mealtracker.models.FoodItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("foodItems")
public class FoodItemController {

    private final FoodItemRepository foodItemRepository;

    @Autowired
    public FoodItemController(
            FoodItemRepository foodItemRepository
    ) {
        this.foodItemRepository = foodItemRepository;
    }

    @GetMapping("")
    public String getFoodItemListView(Model model) {
        List<FoodItem> foodItemList = new ArrayList<>();
        this.foodItemRepository.findAll().forEach(foodItemList::add);

        model.addAttribute("foodItemList", foodItemList);
        return "foodItems/foodItemListView";
    }

    @GetMapping("/add")
    public String getAddFoodItemFormView(Model model) {
        model.addAttribute("newFoodItem", new FoodItem());
        return "foodItems/addFoodItemFormView";
    }

    @PostMapping("/add")
    public String addFoodItem(
            @ModelAttribute("newFoodItem") FoodItem newFoodItem,
            Model model
    ) {
        List<FoodItem> foodItemList = new ArrayList<>();

        // do the actual work of adding this food item to the repository
        this.foodItemRepository.save(newFoodItem);

        return "redirect:";
    }
}

