package com.webtoon.coding.domain.user;

import com.webtoon.coding.domain.core.Reader;
import com.webtoon.coding.exception.MsgType;
import com.webtoon.coding.exception.NoDataException;
import com.webtoon.coding.infra.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReader implements Reader<User> {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User get(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoDataException(MsgType.NoUserData));
    }

    @Override
    public <T> Optional<T> get(Long id, Class<T> type) {
        return Optional.empty();
    }
}
