package com.webtoon.coding.domain.contents;

import com.webtoon.coding.domain.common.Verifier;
import com.webtoon.coding.dto.entity.PolicyCoin;
import com.webtoon.coding.mock.ContentsMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ContentsTest {

    @Mock
    private Verifier<Contents> contentsVerifier;

    @Test
    public void testChangeDetailByFree() {

        Contents contents = ContentsMock.emptyOf();

        PolicyCoin policyCoin = PolicyCoin.of(Policy.FREE, null);

        contents.changeDetail(contentsVerifier, policyCoin);

        Mockito.verify(contentsVerifier, Mockito.times(1)).verify(any());

        assertEquals(contents.getType(), policyCoin.getType());
        assertEquals(contents.getCoin(), "0");
    }

    @Test
    public void testChangeDetailByPagar() {

        Contents contents = ContentsMock.emptyOf();

        PolicyCoin policyCoin = PolicyCoin.of(Policy.PAGAR, "100");

        contents.changeDetail(contentsVerifier, policyCoin);

        Mockito.verify(contentsVerifier, Mockito.times(1)).verify(any());

        assertEquals(contents.getType(), policyCoin.getType());
        assertEquals(contents.getCoin(), policyCoin.getCoin());
    }

}
