package com.webtoon.coding.domain.contents;

import com.webtoon.coding.domain.core.Verifier;
import com.webtoon.coding.exception.DomainException;
import com.webtoon.coding.exception.MsgType;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ContentsVerifier implements Verifier<Contents> {

    @Override
    public void verify(Contents contents) {

        String coin = contents.getCoin();

        Policy type = contents.getType();

        if (ObjectUtils.isEmpty(type)) throw new RuntimeException("Policy Data Is Not Null");

        if (Policy.FREE.equals(type) && !"0".equals(coin)) {
            throw new DomainException(MsgType.CoinDataException);
        } else if (Policy.PAGAR.equals(type) && !Objects.isNull(coin)) {

            int coinNum = Integer.parseInt(coin);

            if (100 > coinNum || 500 < coinNum) {
                throw new DomainException(MsgType.CoinDataException);
            }

        } else {
            throw new RuntimeException("Not Free And Coin Data is Empty");
        }

    }
}
