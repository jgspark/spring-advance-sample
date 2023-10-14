package com.webtoon.coding.mock;

import com.webtoon.coding.domain.contents.Policy;
import com.webtoon.coding.domain.history.History;
import com.webtoon.coding.dto.view.HistoryInfo;

import java.util.Date;

public class HistoryInfoTemp implements HistoryInfo {

    private final History history;

    HistoryInfoTemp(History history) {
        this.history = history;
    }

    @Override
    public Long getId() {
        return history.getId();
    }

    @Override
    public String getUserName() {
        return history.getUser().getUserName();
    }

    @Override
    public String getContentsName() {
        return history.getContents().getName();
    }

    @Override
    public Policy getContentsType() {
        return history.getContents().getType();
    }

    @Override
    public Date getCreatedDate() {
        return history.getCreatedDate();
    }
}
