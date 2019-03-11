package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    int deleteUserByIdAndUserId(int id, int userId);

    @Override
    @Transactional
    Meal save(Meal meal);

    Meal findMealByIdAndUserId(int id, int userId);

    List<Meal> findAllByUserId(int userId, Sort sort);

    @Modifying
    @Query(name = Meal.GET_BETWEEN)
    List<Meal> getAllBetween(@Param("startDate") LocalDateTime startTime, @Param("endDate") LocalDateTime endTime, @Param("userId") int userId);
}
