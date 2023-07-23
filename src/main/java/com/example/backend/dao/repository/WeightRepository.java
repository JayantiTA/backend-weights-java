package com.example.backend.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.dao.entity.WeightDO;
import java.time.LocalDateTime;


public interface WeightRepository extends JpaRepository<WeightDO, Long>{
    Optional<WeightDO> findByDate(LocalDateTime date);
}
