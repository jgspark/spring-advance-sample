package com.lezhin.coding.repository;

import com.lezhin.coding.domain.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
  <T> Page<T> findAllProjectedBy(Pageable pageable, Class<T> classType);
}
