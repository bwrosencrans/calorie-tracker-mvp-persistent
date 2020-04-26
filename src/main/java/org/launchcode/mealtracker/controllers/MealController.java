package org.launchcode.mealtracker.controllers;

import org.launchcode.mealtracker.dao.FoodItemRepository;
import org.launchcode.mealtracker.dao.MealRepository;
import org.launchcode.mealtracker.models.FoodItem;
import org.launchcode.mealtracker.models.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("meals")
public class MealController {

    public static class MealForm {
        public final Meal meal;
        public String selectedFoodItemId;

        public MealForm() {
            this.meal = new Meal();
        }

        public MealForm(Meal meal) {
            this.meal = meal;
        }

        public Meal getMeal() {
            return meal;
        }

        public String getSelectedFoodItemId() {
            return selectedFoodItemId;
        }

        public void setSelectedFoodItemId(String selectedFoodItemId) {
            this.selectedFoodItemId = selectedFoodItemId;
        }
    }

    private final FoodItemRepository foodItemRepository;
    private final MealRepository mealRepository;

    @Autowired
    public MealController(
            FoodItemRepository foodItemRepository,
            MealRepository mealRepository
    )  {
        this.foodItemRepository = foodItemRepository;
        this.mealRepository = mealRepository;
    }

    @GetMapping("")
    public String getMealSummaryView() {

        return "meals/mealSummaryView";
    }

    @GetMapping("create")
    public String displayCreateMealView(Model model) {
        model.addAttribute("mealForm", new MealForm());
        return "meals/createMealView";
    }

    @PostMapping("create")
    public String processAddMealForm(
            @ModelAttribute("mealForm") MealForm mealForm,
            Model model
    ) {
        // create an empty meal entity
        Meal newMeal = mealForm.getMeal();

        newMeal = this.mealRepository.save(newMeal);

        MealForm newMealForm = new MealForm(newMeal);

        model.addAttribute("mealForm", newMealForm);

        return "redirect:/meals/edit/" + newMeal.getId();
    }

    @GetMapping("edit/{mealIdStr}")
    public String displayEditMealView(
            @PathVariable("mealIdStr") String mealIdStr,
            Model model
    ){
        int mealId = Integer.parseInt(mealIdStr);
        Optional<Meal> mealOpt = this.mealRepository.findById(mealId);
        Meal meal = mealOpt.get();
        MealForm mealForm = new MealForm(meal);
        model.addAttribute("mealForm", mealForm);

        int totalCals = 0;
        for(FoodItem item: meal.getFoodItems()) {
            totalCals += item.getCalorieCount();
        }

        model.addAttribute("totalCalories", totalCals);

        List<FoodItem> foodItemList = new ArrayList<>();
        this.foodItemRepository.findAll().forEach(foodItemList::add);
        model.addAttribute("foodItemList", foodItemList);

        return "meals/editMealView";
    }

    @PostMapping("edit")
    public String editMeal(
            @RequestParam("action") String action,
            @ModelAttribute("mealForm") MealForm mealForm,
            Model model
    ) {
        Optional<Meal> mealOpt = this.mealRepository.findById(mealForm.meal.getId());
        Meal meal = mealOpt.get();

        meal.setName(mealForm.getMeal().getName());
        meal.setDescription(mealForm.getMeal().getDescription());

        if("Add Meal Item".equals(action)) {
            int foodItemId = Integer.parseInt(mealForm.selectedFoodItemId);
            Optional<FoodItem> newFoodItemOpt = this.foodItemRepository.findById(foodItemId);
            FoodItem newFoodItem = newFoodItemOpt.get();

            meal.getFoodItems().add(newFoodItem);
        }

        this.mealRepository.save(meal);

        return "redirect:/meals/edit/" + meal.getId();
    }

    @GetMapping("{mealId}/deleteMealEntry/{itemNo}")
    public String deleteMealEntry(
            @PathVariable("mealId") String mealIdStr,
            @PathVariable("itemNo") String itemNoStr
    ) {
        int mealId = Integer.parseInt(mealIdStr);
        Optional<Meal> mealOpt = this.mealRepository.findById(mealId);
        Meal meal = mealOpt.get();

        int itemNo = Integer.parseInt(itemNoStr);
        meal.getFoodItems().remove(itemNo);

        this.mealRepository.save(meal);

        return "redirect:/meals/edit/" + mealIdStr;
    }

    // http::/<domain:port>/meals/123/delete?redirectTo=<someurl>
    @GetMapping("{mealId}/delete")
    public String deleteMeal(
            @PathVariable("mealId") String mealIdStr,
            @RequestParam("redirectTo") String redirectTo
    ) {
        int mealId = Integer.parseInt(mealIdStr);
        Optional<Meal> mealOpt = this.mealRepository.findById(mealId);
        Meal meal = mealOpt.get();

        this.mealRepository.delete(meal);

        return "redirect:/" + redirectTo;
    }
}

