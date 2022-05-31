package com.lezhin.coding.repository.suport;

import com.lezhin.coding.constants.AdultType;
import com.lezhin.coding.domain.History;
import com.lezhin.coding.service.dto.HistoryUser;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Date;
import java.util.List;

import static com.lezhin.coding.domain.QContents.contents;
import static com.lezhin.coding.domain.QHistory.history;
import static com.lezhin.coding.domain.QUser.user;

public class HistorySupportImpl extends QuerydslRepositorySupport implements HistorySupport {

  private final JPAQueryFactory jpaQueryFactory;

  public HistorySupportImpl(JPAQueryFactory jpaQueryFactory) {
    super(History.class);
    this.jpaQueryFactory = jpaQueryFactory;
  }

  @Override
  public Page<HistoryUser> findByCreatedDateBetweenAndContents_AdultType(
      Pageable pageable, Date startDate, Date endDate, AdultType adultType, Long count) {

    QueryResults<HistoryUser> query =
        jpaQueryFactory
            .select(
                Projections.fields(
                    HistoryUser.class,
                    user.id,
                    user.userName,
                    user.userEmail,
                    user.gender,
                    user.type,
                    user.registerDate))
            .from(history)
            .where(
                user.registerDate.between(startDate, endDate).and(contents.adultType.eq(adultType)))
            .join(history.user, user)
            .join(history.contents, contents)
            .groupBy(
                user.id, user.userName, user.userEmail, user.gender, user.type, user.registerDate)
            .having(contents.id.count().goe(count))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

    long total = query.getTotal();

    List<HistoryUser> results = query.getResults();

    return new PageImpl<>(results, pageable, total);
  }
}
