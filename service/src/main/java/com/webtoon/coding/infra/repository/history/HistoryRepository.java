package com.webtoon.coding.infra.repository.history;

import com.webtoon.coding.domain.history.History;
import com.webtoon.coding.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long>, HistorySupport {

    <T> Page<T> findAllProjectedBy(Pageable pageable, Class<T> classType);

    List<History> findByUser(User user);

    void deleteByUser_Id(Long userId);

}
