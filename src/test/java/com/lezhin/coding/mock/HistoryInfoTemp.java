package com.lezhin.coding.mock;

import com.lezhin.coding.constants.ContentsType;
import com.lezhin.coding.domain.History;
import com.lezhin.coding.service.dto.HistoryInfo;

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
    public ContentsType getContentsType() {
        return history.getContents().getType();
    }

    @Override
    public Date getCreatedDate() {
        return history.getCreatedDate();
    }
}
