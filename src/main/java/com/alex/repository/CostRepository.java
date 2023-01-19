package com.alex.repository;

import com.alex.model.Cost;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.alex.util.TimeUtil.getEndExclusive;
import static com.alex.util.TimeUtil.getStartInclusive;

public interface CostRepository {
    Cost save(Cost cost, int userId);

    boolean delete(int id, int userId);

    Cost get(int id, int userId);

    List<Cost> getAll(int userId);

  //  List<Cost> getBetween(@Nullable LocalDate startDate,@Nullable LocalDate endDate, int userId);

   default List<Cost> getBetween(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId){
       return getBetween(getStartInclusive(startDate), getEndExclusive(endDate), userId);
    }

    // ORDERED dateTime desc
    List<Cost> getBetween(@NonNull LocalDateTime startDate, @NonNull LocalDateTime endDate, int userId);

    default Cost getWithUser(int id, int userId) {
        throw new UnsupportedOperationException();
    }
}
