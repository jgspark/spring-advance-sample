package com.lezhin.coding.repository.suport;

import com.lezhin.coding.constants.EvaluationType;
import com.lezhin.coding.domain.Comment;
import com.lezhin.coding.service.dto.TopContents;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.lezhin.coding.domain.QComment.comment1;
import static com.lezhin.coding.domain.QContents.contents;

public class CommentSupportRepositoryImpl extends QuerydslRepositorySupport
    implements CommentSupportRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public CommentSupportRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    super(Comment.class);
    this.jpaQueryFactory = jpaQueryFactory;
  }

  @Override
  public List<TopContents> findTopByLimitAndType(Integer limit, EvaluationType type) {

    return jpaQueryFactory
        .select(
            Projections.fields(
                TopContents.class,
                contents.id,
                contents.name,
                contents.author,
                contents.type,
                contents.coin,
                contents.openDate,
                contents.id.count()))
        .from(comment1)
        .innerJoin(comment1.contents, contents)
        .where(comment1.type.eq(type))
        .groupBy(
            contents.id,
            contents.name,
            contents.author,
            contents.type,
            contents.coin,
            contents.openDate)
        .limit(limit)
        .fetch();
  }
}
