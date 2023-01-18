package com.alex.repository.datajpa;

import com.alex.model.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudCostRepository extends JpaRepository<Cost, Integer> {
}
