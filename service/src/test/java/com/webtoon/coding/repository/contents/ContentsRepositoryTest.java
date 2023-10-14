package com.webtoon.coding.repository.contents;

import com.webtoon.coding.domain.comment.Comment;
import com.webtoon.coding.domain.comment.Evaluation;
import com.webtoon.coding.domain.common.Verifier;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.domain.contents.ContentsVerifier;
import com.webtoon.coding.domain.contents.Policy;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.dto.entity.PolicyCoin;
import com.webtoon.coding.dto.request.PageContentsRequest;
import com.webtoon.coding.dto.view.ContentsInfo;
import com.webtoon.coding.dto.view.TopContents;
import com.webtoon.coding.infra.config.JPAConfiguration;
import com.webtoon.coding.infra.repository.comment.CommentRepository;
import com.webtoon.coding.infra.repository.contents.ContentsRepository;
import com.webtoon.coding.infra.repository.user.UserRepository;
import com.webtoon.coding.mock.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(JPAConfiguration.class)
class ContentsRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContentsRepository contentsRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Verifier<Contents> contentsVerifier;

    @BeforeEach
    void init() {
        contentsVerifier = new ContentsVerifier();
    }

    @Nested
    @DisplayName("저장을 한다면,")
    class Save {

        @Test
        @DisplayName("정상적으로 동작을 한다.")
        void save() {

            Contents mock = ContentsMock.createdMock();

            Contents entity = contentsRepository.save(mock);

            assertEquals(entity.getId(), mock.getId());
            assertEquals(entity.getName(), mock.getName());
            assertEquals(entity.getAuthor(), mock.getAuthor());
            assertEquals(entity.getType(), mock.getType());
            assertEquals(entity.getCoin(), "0");
            assertEquals(entity.getOpenDate(), mock.getOpenDate());
        }

    }

    @Nested
    @DisplayName("조회를 한다면,")
    class Select {

        private User user;

        private Contents mock;

        private Comment comment;

        @BeforeEach
        void init() {
            user = userRepository.saveAndFlush(UserMock.createdMock());
            mock = contentsRepository.saveAndFlush(ContentsMock.createdMock());
            comment = commentRepository.save(CommentMock.createdMock(user, mock));
            commentRepository.flush();
        }

        @Test
        @DisplayName("좋아요 top 3 로직 테스트 케이스는 정상적으로 동작을 한다.")
        void findTopByLimitAndType_goodComment() {

            List<TopContents> entities = contentsRepository.findTopByLimitAndType(3, Evaluation.GOOD);

            assertEquals(entities.size(), 1);

            TopContents entity = entities.get(0);

            assertEquals(entity.getId(), mock.getId());
            assertEquals(entity.getName(), mock.getName());
            assertEquals(entity.getAuthor(), mock.getAuthor());
            assertEquals(entity.getType(), mock.getType());
            assertEquals(entity.getCoin(), mock.getCoin());
            assertEquals(DateMock.changedFormatDate(entity.getOpenDate()),
                    DateMock.changedFormatDate(mock.getOpenDate()));
            assertEquals(entity.getSum(), 1);
        }

        @Test
        @DisplayName("싫어요 top 3 로직 테스트 케이스는 정상적으로 동작을 한다.")
        void findTopByLimitAndType_badComment() {

            List<TopContents> entities = contentsRepository.findTopByLimitAndType(3, Evaluation.BAD);

            assertEquals(entities.size(), 1);

            TopContents entity = entities.get(0);

            assertEquals(entity.getId(), mock.getId());
            assertEquals(entity.getName(), mock.getName());
            assertEquals(entity.getAuthor(), mock.getAuthor());
            assertEquals(entity.getType(), mock.getType());
            assertEquals(entity.getCoin(), mock.getCoin());
            assertEquals(DateMock.changedFormatDate(entity.getOpenDate()),
                    DateMock.changedFormatDate(mock.getOpenDate()));
            assertEquals(entity.getSum(), 0);
        }

        @Test
        @DisplayName("타입별 조회 로직 free type은 정상적으로 동작을 한다.")
        void findByType_freeType() {

            Page<ContentsInfo> mocks = ContentsMock.getPageContentsInfo();

            PageContentsRequest dto = DtoMock.getSelectContentsStoreDTO();

            Page<ContentsInfo> entities = contentsRepository.findByType(dto.getPageRequest(), Policy.FREE,
                    ContentsInfo.class);

            List<ContentsInfo> mockContent = mocks.getContent();

            List<ContentsInfo> entitiesContent = entities.getContent();

            assertEquals(entitiesContent.size(), mockContent.size());

            ContentsInfo entity = entitiesContent.get(0);

            ContentsInfo mock = mockContent.get(0);

            assertEquals(entity.getName(), mock.getName());
            assertEquals(entity.getAuthor(), mock.getAuthor());
            assertEquals(entity.getType(), mock.getType());
            assertEquals(entity.getCoin(), "0");
            assertEquals(DateMock.changedFormatDate(entity.getOpenDate()),
                    DateMock.changedFormatDate(mock.getOpenDate()));
        }

        @Test
        @DisplayName("전체 조회 로직 테스트 케이스는 정상적으로 동작을 한다.")
        void findAllProjectedBy() {
            Page<ContentsInfo> mocks = ContentsMock.getPageContentsInfo();

            PageContentsRequest dto = DtoMock.getSelectContentsStoreDTO();

            Page<ContentsInfo> entities = contentsRepository.findAllProjectedBy(dto.getPageRequest(),
                    ContentsInfo.class);

            List<ContentsInfo> mockContent = mocks.getContent();

            List<ContentsInfo> entitiesContent = entities.getContent();

            assertEquals(entitiesContent.size(), mockContent.size());

            ContentsInfo entity = entitiesContent.get(0);

            ContentsInfo mock = mockContent.get(0);

            assertEquals(entity.getName(), mock.getName());
            assertEquals(entity.getAuthor(), mock.getAuthor());
            assertEquals(entity.getType(), mock.getType());
            assertEquals(entity.getCoin(), "0");
            assertEquals(DateMock.changedFormatDate(entity.getOpenDate()),
                    DateMock.changedFormatDate(mock.getOpenDate()));
        }

        @Test
        @DisplayName("하나의 컨텐츠 조회를 정상적으로 동작을 한다.")
        void findById() {

            Optional<ContentsInfo> entityOptional = contentsRepository.findById(mock.getId(), ContentsInfo.class);

            assertTrue(entityOptional.isPresent());

            ContentsInfo entity = entityOptional.get();

            assertEquals(entity.getName(), mock.getName());
            assertEquals(entity.getAuthor(), mock.getAuthor());
            assertEquals(entity.getType(), mock.getType());
            assertEquals(entity.getCoin(), "0");
            assertEquals(DateMock.changedFormatDate(entity.getOpenDate()),
                    DateMock.changedFormatDate(mock.getOpenDate()));
        }

        @AfterEach
        void clear() {
            userRepository.deleteAll();
            contentsRepository.deleteAll();
            commentRepository.deleteAll();
            userRepository.flush();
            contentsRepository.flush();
            commentRepository.flush();
        }

    }

    @Nested
    class Update {

        private Contents mock;

        @BeforeEach
        void init() {
            mock = contentsRepository.save(ContentsMock.createdMock());
            contentsRepository.flush();
        }

        @Test
        @DisplayName("무료 타입 컨텐츠 업데이트는 정상적으로 동작을 한다.")
        void updateFreeType() {

            Optional<Contents> entityOptional = contentsRepository.findById(mock.getId());

            Contents entity = entityOptional.get();

            entity.changeDetail(contentsVerifier, PolicyCoin.of(Policy.FREE, ""));

            contentsRepository.flush();

            assertEquals(entity.getName(), mock.getName());
            assertEquals(entity.getAuthor(), mock.getAuthor());
            assertEquals(entity.getType(), mock.getType());
            assertEquals(entity.getCoin(), "0");
            assertEquals(entity.getOpenDate(), mock.getOpenDate());
        }

        @Test
        @DisplayName("유료 타입 컨텐츠 업데이트은 정상적으로 동작을 한다.")
        void updatedPagar() {

            final String mockCoin = "100";

            Optional<Contents> entityOptional = contentsRepository.findById(mock.getId());

            Contents entity = entityOptional.get();

            entity.changeDetail(contentsVerifier, PolicyCoin.of(Policy.PAGAR, "100"));

            contentsRepository.flush();

            assertEquals(entity.getId(), mock.getId());
            assertEquals(entity.getName(), mock.getName());
            assertEquals(entity.getAuthor(), mock.getAuthor());
            assertEquals(entity.getType(), Policy.PAGAR);
            assertEquals(entity.getCoin(), mockCoin);
            assertEquals(entity.getOpenDate(), mock.getOpenDate());
        }

        @AfterEach
        void clear() {
            contentsRepository.delete(mock);
            contentsRepository.flush();
        }

    }

}
