package com.webtoon.coding.domain.comment;

import com.webtoon.coding.domain.common.Writer;
import com.webtoon.coding.infra.repository.comment.CommentRepository;
import com.webtoon.coding.mock.CommentMock;
import com.webtoon.coding.mock.ContentsMock;
import com.webtoon.coding.mock.UserMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("코멘트 쓰기 클레스에서")
@ExtendWith(MockitoExtension.class)
class CommentWriterTest {

    @Mock
    private CommentRepository commentRepository;

    private Writer<Comment> commentWriter;

    @BeforeEach
    public void init() {
        commentWriter = new CommentWriter(commentRepository);
    }

    @Nested
    @DisplayName("저장 메소드는")
    public class Save {

        @Test
        @DisplayName("성공적으로 실행이 된다.")
        public void testSuccess() {

            Comment mock = CommentMock.createdMock(UserMock.createdMock(), ContentsMock.createdMock());

            when(commentRepository.save(any())).thenReturn(mock);

            Comment entity = commentWriter.save(mock);

            assertEquals(entity.getId(), mock.getId());
            assertEquals(entity.getType(), mock.getType());
            assertEquals(entity.getUser(), mock.getUser());
            assertEquals(entity.getContents(), mock.getContents());
        }
    }
}
