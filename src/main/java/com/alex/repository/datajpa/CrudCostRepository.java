package com.alex.repository.datajpa;

import com.alex.model.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudCostRepository extends JpaRepository<Cost, Integer> {
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Cost c WHERE c.id=:id AND c.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    Cost save(Cost item);

    @Query("SELECT c FROM Cost c WHERE c.user.id=:userId ORDER BY c.dateTime DESC")
    List<Cost> getAll(@Param("userId") int userId);

    @Query("SELECT c from Cost c WHERE c.user.id=:userId AND c.dateTime BETWEEN :startDate AND :endDate ORDER BY c.dateTime DESC")
    List<Cost> getBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);

    @Query("SELECT c FROM Cost c JOIN FETCH c.user WHERE c.id = ?1 and c.user.id = ?2")
    Cost getWithUser(int id, int userId);
}
