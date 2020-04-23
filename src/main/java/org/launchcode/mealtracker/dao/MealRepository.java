package org.launchcode.mealtracker.dao;

import org.launchcode.mealtracker.models.FoodItem;
import org.launchcode.mealtracker.models.Meal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface MealRepository extends CrudRepository<Meal, Integer> {
}
