package com.webtoon.coding.dto.entity;

import com.webtoon.coding.domain.contents.Policy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("무료 및 코인 클레스에서")
class PolicyCoinTest {

    @Nested
    @DisplayName("생성 메소드는")
    public class Of {

        @Test
        @DisplayName("성공적이다.")
        public void testSuccess() {

            final Policy type = Policy.PAGAR;

            final String coin = "1000";

            PolicyCoin entity = PolicyCoin.of(type, coin);

            assertEquals(entity.getType(), type);
            assertEquals(entity.getCoin(), coin);
        }

    }

}
