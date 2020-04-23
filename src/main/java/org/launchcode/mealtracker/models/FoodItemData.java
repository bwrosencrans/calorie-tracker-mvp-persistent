package org.launchcode.mealtracker.models;

import java.util.ArrayList;


public class FoodItemData {


    /**
     * Returns the results of searching the Jobs data by field and search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column meal field that should be searched.
     * @param value Value of the field to search for.
     * @param allFoodItems The list of food to search.
     * @return List of all meals matching the criteria.
     */
    public static ArrayList<FoodItem> findByColumnAndValue(String column, String value, Iterable<FoodItem> allFoodItems) {

        ArrayList<FoodItem> results = new ArrayList<>();

        if (value.toLowerCase().equals("all")){
            return (ArrayList<FoodItem>) allFoodItems;
        }

        if (column.equals("all")){
            results = findByValue(value, allFoodItems);
            return results;
        }

        for (FoodItem foodItem : allFoodItems) {

            String aValue = getFieldValue(foodItem, column);

            if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                results.add(foodItem);
            }
        }

        return results;
    }

    public static String getFieldValue(FoodItem foodItem, String fieldName){
        String theValue;
        if (fieldName.equals("name")) {
            theValue = foodItem.getName();
        }
        else {
            Integer cal = foodItem.getCalorieCount();
            theValue = cal.toString();
        }

        return theValue;
    }

    /**
     * Search all Meal fields for the given term.
     *
     * @param value The search term to look for.
     * @param allFoodItems The list of meals to search.
     * @return List of all meals with at least one field containing the value.
     */


    public static ArrayList<FoodItem> findByValue(String value, Iterable<FoodItem> allFoodItems) {



        ArrayList<FoodItem> results = new ArrayList<>();

        for (FoodItem foodItem : allFoodItems) {

            if (foodItem.getName().toLowerCase().contains(value.toLowerCase())) {
                results.add(foodItem);
            }
            //else if (foodItem.getMeals().toString().toLowerCase().contains(value.toLowerCase())) {
            //    results.add(foodItem);

            else if (foodItem.toString().toLowerCase().contains(value.toLowerCase())) {
                results.add(foodItem);
            }

        }

        return results;
    }



}

