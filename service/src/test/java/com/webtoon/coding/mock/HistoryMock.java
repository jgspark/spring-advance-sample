package com.webtoon.coding.mock;

import com.webtoon.coding.domain.contents.Contents;
import com.webtoon.coding.domain.history.History;
import com.webtoon.coding.domain.user.User;
import com.webtoon.coding.dto.view.HistoryInfo;
import com.webtoon.coding.dto.view.HistoryUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class HistoryMock {

    private static final Long id = 1L;

    public static History createdMock(User user, Contents contents) {
        return History.of(user, contents);
    }

    public static Page<HistoryInfo> createdPageList(User user, Contents contents) {

        List<HistoryInfo> list = List.of(new HistoryInfoTemp(createdMock(user, contents)));

        PageRequest pageable = PageRequest.of(0, 10);

        return new PageImpl<>(list, pageable, 1);
    }

    public static Page<HistoryUser> createPageHistoryUser(User user, Contents contents) {

        List<HistoryUser> list = List.of(new HistoryUserTemp(createdMock(user, contents).getUser()));
        PageRequest pageable = PageRequest.of(0, 10);
        return new PageImpl<>(list, pageable, list.size());
    }

}
