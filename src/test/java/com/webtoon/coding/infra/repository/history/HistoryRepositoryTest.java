package com.webtoon.coding.infra.repository.history;

import com.webtoon.coding.core.util.DateUtil;
import com.webtoon.coding.domain.contents.Adult;
import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.domain.history.History;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.dto.view.HistoryInfo;
import com.webtoon.coding.dto.view.HistoryUser;
import com.webtoon.coding.infra.config.JPAConfiguration;
import com.webtoon.coding.infra.repository.contents.ContentsRepository;
import com.webtoon.coding.infra.repository.user.UserRepository;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.DateMock;
import com.webtoon.coding.mock.HistoryMock;
import com.webtoon.coding.mock.UserMock;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(JPAConfiguration.class)
@DisplayName("히스토리 레파지토리 클레스 에서")
class HistoryRepositoryTest {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContentsRepository contentsRepository;

    private User user;

    private Contents contents;

    private History entity;

    @BeforeEach
    public void init() {
        user = userRepository.save(UserMock.createdMock());
        contents = contentsRepository.save(ContentsMock.createdMock());
        userRepository.flush();
        contentsRepository.flush();
    }

    @Nested
    @DisplayName("저장하는 메소드는")
    public class Save {

        @Test
        @DisplayName("정상적으로 실행이 된다.")
        public void saveSuccess() {

            History mock = HistoryMock.createdMock(user, contents);

            entity = historyRepository.saveAndFlush(mock);

            assertEquals(entity.getUser(), mock.getUser());
            assertEquals(entity.getContents(), mock.getContents());
            assertNotNull(entity.getCreatedDate());
        }
    }

    @Nested
    public class Select {

        private List<History> mocks;

        @BeforeEach
        public void init() {

            mocks =
                    historyRepository.saveAll(
                            List.of(
                                    HistoryMock.createdMock(user, contents),
                                    HistoryMock.createdMock(user, contents),
                                    HistoryMock.createdMock(user, contents),
                                    HistoryMock.createdMock(user, contents),
                                    HistoryMock.createdMock(user, contents)));

            historyRepository.flush();
        }

        @Test
        @DisplayName("조회 이력 API 테스트 케이스")
        public void findAllProjectedBy() {

            PageRequest pageable = PageRequest.of(0, 10);

            Page<HistoryInfo> entities =
                    historyRepository.findAllProjectedBy(pageable, HistoryInfo.class);

            List<HistoryInfo> entitiesContent = entities.getContent();

            entitiesContent.forEach(
                    entity -> {
                        History mock =
                                mocks.stream()
                                        .filter(m -> m.getId().equals(entity.getId()))
                                        .findFirst()
                                        .orElseThrow();

                        assertEquals(entity.getUserName(), mock.getUser().getUserName());
                        assertEquals(entity.getContentsName(), mock.getContents().getName());
                        assertEquals(entity.getContentsType(), mock.getContents().getType());
                    });
        }

        @AfterEach
        public void after() {
            historyRepository.deleteAll();
        }
    }

    @Nested
    public class SelectHistoryUser {

        private List<History> mocks;

        @BeforeEach
        public void init() {

            mocks =
                    historyRepository.saveAll(
                            List.of(
                                    HistoryMock.createdMock(user, contents),
                                    HistoryMock.createdMock(user, contents),
                                    HistoryMock.createdMock(user, contents),
                                    HistoryMock.createdMock(user, contents),
                                    HistoryMock.createdMock(user, contents)));

            historyRepository.flush();
        }

        @Test
        @DisplayName("최근 1주일 등록된 사용자 중 성인 조회 api 테스트 케이스")
        public void findByCreatedDateBetweenAndContents_AdultType() {

            Date startDate = DateUtil.minus(new Date(), -7);

            Date endDate = DateUtil.plus(new Date(), 1);

            PageRequest pageable = PageRequest.of(0, 10);

            Page<HistoryUser> entities =
                    historyRepository.findByCreatedDateBetweenAndContents_AdultType(
                            pageable, startDate, endDate, Adult.ADULT, 3L);

            entities.getContent().forEach(
                    entity -> {

                        History mock =
                                mocks.stream()
                                        .filter(m -> m.getId().equals(entity.getId()))
                                        .findFirst()
                                        .orElseThrow();

                        assertEquals(entity.getUserName(), mock.getUser().getUserName());
                        assertEquals(entity.getUserEmail(), mock.getUser().getUserEmail());
                        assertEquals(entity.getType(), mock.getContents().getAdult());
                        assertEquals(entity.getGender(), mock.getUser().getGender());
                        assertEquals(DateMock.changedFormatDate(entity.getRegisterDate()), DateMock.changedFormatDate(mock.getUser().getRegisterDate()));
                    });


        }
    }

    @Nested
    public class Delete {

        private History mock;

        @Test
        void init() {
            mock = HistoryMock.createdMock(user, contents);
        }

        @Test
        @DisplayName("히스토리 유저별 삭제")
        void deleteByUser_Id() {
            historyRepository.deleteByUser_Id(user.getId());
            historyRepository.flush();
        }
    }
}

