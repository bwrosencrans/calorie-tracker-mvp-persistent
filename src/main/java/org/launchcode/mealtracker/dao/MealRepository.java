package org.launchcode.mealtracker.dao;

import org.launchcode.mealtracker.models.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface MealRepository extends JpaRepository<Meal, Integer> {

    @Query("SELECT m FROM Meal m WHERE date BETWEEN :startDate and :endDate")
    List<Meal> findAllByDateRange(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);
}
