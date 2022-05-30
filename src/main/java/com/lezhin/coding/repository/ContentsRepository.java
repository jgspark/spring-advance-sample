package com.lezhin.coding.repository;

import com.lezhin.coding.constants.ContentsType;
import com.lezhin.coding.domain.Contents;
import com.lezhin.coding.repository.suport.ContentsSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentsRepository extends JpaRepository<Contents, Long>, ContentsSupport {
  <T> Page<T> findByType(Pageable pageable, ContentsType type, Class<T> classType);

  <T> Page<T> findAllProjectedBy(Pageable pageable, Class<T> classType);
}
