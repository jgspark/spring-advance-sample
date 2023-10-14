package com.webtoon.coding.infra.repository.history;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.webtoon.coding.domain.contents.Adult;
import com.webtoon.coding.domain.history.History;
import com.webtoon.coding.dto.view.HistoryUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Date;

import static com.webtoon.coding.domain.contents.QContents.contents;
import static com.webtoon.coding.domain.history.QHistory.history;
import static com.webtoon.coding.domain.user.QUser.user;


public class HistorySupportImpl extends QuerydslRepositorySupport implements HistorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public HistorySupportImpl(JPAQueryFactory jpaQueryFactory) {
        super(History.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<HistoryUser> findByCreatedDateBetweenAndContents_AdultType(
            Pageable pageable,
            Date startDate,
            Date endDate,
            Adult adult,
            Long count
    ) {

        var query =
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
                                user.registerDate.between(startDate, endDate).and(contents.adult.eq(adult)))
                        .join(history.user, user)
                        .join(history.contents, contents)
                        .groupBy(
                                user.id, user.userName, user.userEmail, user.gender, user.type, user.registerDate)
                        .having(contents.id.count().goe(count))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetchResults();

        var total = query.getTotal();
        var results = query.getResults();
        return new PageImpl<>(results, pageable, total);
    }
}
