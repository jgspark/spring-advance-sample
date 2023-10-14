package com.webtoon.coding.infra.repository.contents;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.dto.view.TopContents;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.webtoon.coding.domain.comment.QComment.comment1;
import static com.webtoon.coding.domain.contents.QContents.contents;

public class ContentsSupportImpl extends QuerydslRepositorySupport implements ContentsSupport {

    private final JPAQueryFactory jpaQueryFactory;

    public ContentsSupportImpl(JPAQueryFactory jpaQueryFactory) {
        super(Contents.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<TopContents> findTopByLimitAndType(Integer limit, Evaluation type) {

        var sum = new CaseBuilder().when(comment1.type.eq(type)).then(1).otherwise(0).sum();

        return jpaQueryFactory
            .select(Projections.fields(TopContents.class, contents.id, contents.name, contents.author, contents.type,
                    contents.coin, contents.openDate, sum.as("sum")))
            .from(contents)
            .innerJoin(comment1)
            .on(contents.id.eq(comment1.id.contentsId))
            .groupBy(contents.id, contents.name, contents.author, contents.type, contents.coin, contents.openDate)
            .orderBy(sum.desc())
            .limit(limit)
            .fetch();
    }

}
