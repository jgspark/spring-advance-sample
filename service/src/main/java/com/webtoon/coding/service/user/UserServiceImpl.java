package com.webtoon.coding.service.user;

import com.webtoon.coding.domain.user.UserRemover;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRemover userRemover;

    @Override
    @Transactional
    public void removeUser(Long id) {
        userRemover.remove(id);
    }

}
