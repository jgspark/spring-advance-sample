package com.webtoon.coding.dto.entity;

import com.webtoon.coding.domain.contents.Policy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("페이스 컨텐츠 엔티티는")
class PageContentsTest {

    @Nested
    @DisplayName("생성자 메소드는")
    public class Of {

        @Test
        @DisplayName("성공적으로 실행이된다.")
        public void testSuccess() {

            final Policy type = Policy.PAGAR;

            PageRequest pageRequest = PageRequest.of(10, 1000);

            PageContents entity = PageContents.of(type, pageRequest);

            assertEquals(type, entity.getType());
            assertEquals(pageRequest, entity.getPageRequest());
        }
    }
}
